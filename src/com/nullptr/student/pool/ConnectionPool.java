package com.nullptr.student.pool;

import com.nullptr.student.AppConstants;


import javax.sql.DataSource;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.*;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Logger;

/**
 * 学习而来，非原创
 * 连接池类
 * created by 梁文俊
 * date:2018-4-12
 */
public class ConnectionPool implements DataSource {

    private static final int INITIAL_SIZE = 2;

    private int size;

    //空闲连接队列(阻塞队列)
    private ArrayBlockingQueue<Connection> idleConnectionQueue;

    //繁忙(正在使用)的连接集合
    //注:此处使用Vector的原因:
    //Vector支持线程的同步，避免多线程同时写引起的不一致性
    //带来的代价是访问它比访问ArrayList慢
    private Vector<Connection> busyConnectionVector;

    public ConnectionPool(){
        idleConnectionQueue = new ArrayBlockingQueue<>(AppConstants.MAX_CONNECTION);    //设置最大长度
        busyConnectionVector = new Vector<>();
        initConnection();
    }

    /**
     * 初始化线程池
     * 默认给线程池加入2个线程
     */
    private void initConnection(){
        int initialSize = INITIAL_SIZE;
        try{
            Class.forName(AppConstants.DRIVER);
            for (int i=0;i<initialSize;i++){
                idleConnectionQueue.put(newConnection());
                size++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 从连接池中获取连接
     * @return 返回获取到的连接
     */
    @Override
    public Connection getConnection() throws SQLException{
        //拿出空闲队列的一个Connection
        Connection connection = idleConnectionQueue.poll();
        if (connection == null){
            //为空，说明容量不够，扩容
            ensureCapacity();
            return null;
        }
        busyConnectionVector.add(connection);
        //动态代理，主要为了把Connection的close方法覆盖掉
        return (Connection) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{Connection.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (!method.getName().equals("close")){
                    //如果不是close方法
                    return method.invoke(connection,args);
                }else {
                    //是close方法，覆盖close方法
                    //用完了。返回空闲队列
                    idleConnectionQueue.offer(connection);
                    //从繁忙队列中删除
                    busyConnectionVector.remove(connection);
                    return null;
                }
            }
        });
    }

    /**
     * 新建连接
     * @return 返回新建的连接
     */
    private Connection newConnection() throws SQLException{
        return DriverManager.getConnection(AppConstants.URL,AppConstants.USERNAME,AppConstants.PASSWORD);
    }

    protected int size(){
        return size;
    }

    protected int idleConnectionQuantity(){
        return idleConnectionQueue.size();
    }

    protected int busyConnectionQuantity(){
        return busyConnectionVector.size();
    }

    /**
     * 为连接池扩容，每次扩容2
     */
    private void ensureCapacity() throws SQLException{
        int newCapacity = size + 2; //每次扩容2
        newCapacity = newCapacity>AppConstants.MAX_CONNECTION? AppConstants.MAX_CONNECTION:newCapacity; //最大只能到最大连接数那么多
        int growCount = 0;  //记录增加的数量，用于计算新的size
        if (size<newCapacity){
            //扩容
            try {
                for (int i=0;i<newCapacity-size;i++){
                    //每个新开的都放一个新的连接
                    idleConnectionQueue.put(newConnection());
                    growCount++;
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        size = size + growCount;
    }

    //下面的方法都是DataSource必须实现的方法

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
