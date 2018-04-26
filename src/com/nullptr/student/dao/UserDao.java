package com.nullptr.student.dao;

import com.nullptr.student.po.User;
import com.nullptr.student.utils.DBUtils;
import com.nullptr.student.utils.MD5Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * UserDao
 * 用于和user表交互
 * created by 梁文俊
 * date:2018-4-1
 */
public class UserDao {
    private DBUtils dbUtils;

    //初始化DBUtils
    public UserDao() {
        dbUtils = DBUtils.getInstance();
    }

    /**
     * 检测账号密码
     *
     * @param username:用于检查的用户名
     * @param password:用于检查的密码
     * @return 权限id或错误
     */
    public int checkPassword(String username, String password) {
        //先对拿到的密码进行加密,以用户名为盐值
        String md5Password = MD5Utils.generate(password,username);
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?;";
        List<Object> paramList = new ArrayList<>();
        paramList.add(username);
        paramList.add(md5Password);

        List<Map<String, Object>> resultList = dbUtils.query(sql, paramList); //搜索结果List
        if (resultList == null || resultList.size() == 0) {
            //为空则返回-1，代表用户名或密码错误
            return -1;
        } else {
            //不为空返回对应的权限id
            return Integer.valueOf(resultList.get(0).get("permission_id").toString());
        }
    }

    /**
     * 注册账号
     * @param user:携带注册账号信息的User
     */
    public void register(User user){
        if (user == null){
            throw new IllegalArgumentException("User to register cannot be null");
        }
        //用用户名作为盐值 加密密码
        String md5Password = MD5Utils.generate(user.getPassword(),user.getUsername());
        String sql = "INSERT user(username,password,permission_id) VALUES(?,?,?);";
        List<Object> paramList = new ArrayList<>();
        paramList.add(user.getUsername());
        paramList.add(md5Password);
        paramList.add(user.getPermissionId());

        dbUtils.excute(sql,paramList);
    }

    /**
     * 修改密码
     * @param username:用户名
     * @param password:修改的密码
     **/
    public void changePassword(String username,String password){
        //用用户名作为盐值 加密密码
        String md5Password = MD5Utils.generate(password,username);
        String sql = "UPDATE user SET password=? WHERE username=?;";
        List<Object> paramList = new ArrayList<>();
        paramList.add(md5Password );
        paramList.add(username);

        dbUtils.excute(sql,paramList);
    }

    /**
     * 删除账户
     * @param username:删除账户的用户名
     */
    public void deleteUser(String username){
        String sql = "DELETE FROM user WHERE username=?;";
        List<Object> paramList = new ArrayList<>();
        paramList.add(username);

        dbUtils.excute(sql,paramList);
    }

    /**
     * 修改对应用户的权限
     * @param username:要修改的用户名
     * @param permission:权限id
     */
    public void changePermission(String username,int permission){
        String sql = "UPDATE user SET permission_id=? WHERE username=?;";
        List<Object> paramList = new ArrayList<>();
        paramList.add(permission);
        paramList.add(username);

        dbUtils.excute(sql,paramList);
    }
}
