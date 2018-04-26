package com.nullptr.student.service;

import com.nullptr.student.dao.UserDao;
import com.nullptr.student.po.User;

public class UserServiceImpl implements UserService{
    //注册账号
    public void register(User user) {
        new UserDao().register(user);
    }

    //修改密码
    public void changePassword(String username,String password) {
        new UserDao().changePassword(username,password);
    }

    //删除账号
    public void deleteUser(String username){
        new UserDao().deleteUser(username);
    }

    //修改对应权限
    public void changePermission(String username,int permission){
        new UserDao().changePermission(username,permission);
    }

    //检查密码
    public boolean checkPassword(String username,String password){
        int permissionId = new UserDao().checkPassword(username, password);
        if (permissionId != -1) {
            //密码正确
            return true;
        } else {
            //密码不正确
            return false;
        }
    }
}
