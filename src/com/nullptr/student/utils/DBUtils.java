package com.nullptr.student.utils;

import com.nullptr.student.pool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 单例模式的jdbc封装类
 * 方便用MySQL语句操作sql
 * created by 梁文俊
 * date:2018-3-29
 */
public class DBUtils {
    private static DBUtils instance = new DBUtils();
    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private static ConnectionPool connectionPool = null;

    /**
     * 获取实例
     *
     * @return DBUtils的实例
     */
    public static DBUtils getInstance() {
        if (connectionPool == null){
            connectionPool = new ConnectionPool();
        }
        return instance;
    }


    /**
     * 执行MySQL语句
     * @param sql:带参数语句
     * @param params:参数列表
     * @return 受到SQL调用影响的行数
     */
    public int excute(String sql, List<Object> params) {
        //受影响行数
        int line = 0;

        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            if (params != null) {
                for (int i = 0; i < params.size(); i++) {
                    //遍历参数,添加到PreparedStatement
                    preparedStatement.setObject(i + 1, params.get(i));    //数据是从1开始，所以i+1
                }
            }

            line = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            releaseResource();
        }
        return line;
    }


    /**
     * 无参数执行MySQL语句
     * @param sql:语句
     * @return 受到SQL调用影响的行数
     */
    public int excute(String sql) {
        //受影响行数
        int line = 0;

        try {
            connection = connectionPool.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
             {
                 releaseResource();
            }
        }
        return line;
    }

    /**
     * 查询数据,返回一个元素为数据类型及其对应值的Map的ArrayList
     * @param sql:带参数语句
     * @param params:参数列表
     * @return 一个保存了数据名和数据的集合的ArrayList
     */
    public List<Map<String, Object>> query(String sql, List<Object> params) {
        List<Map<String, Object>> resultList = null;
        ResultSetMetaData resultSetMetaData = null;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            if (params != null) {
                //遍历参数，添加到preparedStatement中
                for (int i = 0; i < params.size(); i++) {
                    preparedStatement.setObject(i + 1, params.get(i));   //数据是从1开始，所以i+1
                }
            }
            resultSet = preparedStatement.executeQuery();   //获取值
            resultSetMetaData = resultSet.getMetaData();    //获取对应的列名
            resultList = new ArrayList<>();
            while (resultSet.next()) {
                //将得到的列名对应其值放入HashMap中，以便获得值
                Map<String, Object> map = new HashMap<>();
                for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
                    //从ResultSetMetaData中获取当前列名
                    //列从1开始，所以i+1
                    map.put(resultSetMetaData.getColumnName(i + 1), resultSet.getString(i + 1));
                }
                resultList.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            releaseResource();
        }
        return resultList;
    }

    /**
     * 释放资源
     */
    private void releaseResource() {
        //关闭ResultSet
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //关闭Statement
        if (statement != null) {
            try {
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //关闭preparedstatement
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //释放Connection(变为闲置状态)
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
