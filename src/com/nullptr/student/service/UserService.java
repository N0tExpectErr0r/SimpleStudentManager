package com.nullptr.student.service;

import com.nullptr.student.po.User;

/**
 * UserService接口
 * 用于操作User
 * created by 梁文俊
 * date:2018-4-08
 */
public interface UserService {

    //注册账号
    public void register(User user);

    //修改密码
    public void changePassword(String username,String password);

    //删除账号
    public void deleteUser(String username);

    //修改对应的权限
    public void changePermission(String username,int permission);

    //检查密码
    public boolean checkPassword(String username,String password);
}
