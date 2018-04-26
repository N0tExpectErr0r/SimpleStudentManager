package com.nullptr.student.service;

/**
 * LoginService接口
 * 用于处理登陆
 * created by 梁文俊
 * date:2018-4-1
 */
public interface LoginService {

    //登陆账户
    public boolean login(String username, String password);

    //注销登录
    public void logout();

    //检测是否登陆
    public boolean isLogin();

    //获取当前登陆的用户的身分
    public String getIdentity();

    //获取当前登录用户名
    public String getUsername();

    //获取当前登录用户的权限id
    public int getPermission();

    //获取当前登陆的用户的姓名
    public String getCurrentUserName();
}
