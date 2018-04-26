package com.nullptr.student.factory;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

/**
 * Service工厂
 * 用于生产对应Service
 * created by 梁文俊
 * date:2018-4-17
 */
public class ServiceFactory {

    private static ServiceFactory instance = new ServiceFactory();
    //配置文件(包含了各个Service及其对应的实现类)
    private Properties config = new Properties();

    private ServiceFactory(){
        //读取配置文件
        InputStream in = ServiceFactory.class.getClassLoader().getResourceAsStream("factory.properties");

        try {
            config.load(in);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static ServiceFactory getInstance(){
        return instance;
    }


    public <T> T createService(Class<T> theClass){
        String interfaceName = theClass.getSimpleName();    //获取接口的名称
        String className = config.getProperty(interfaceName);   //通过接口名获取对应的实现类的名称

        try {
        T bean = (T) Class.forName(className).newInstance();    //实例化对应的实现类并返回
            return bean;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
