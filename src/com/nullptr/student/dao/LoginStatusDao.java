package com.nullptr.student.dao;

import com.nullptr.student.utils.DBUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * LoginStatusDao
 * 用于和logstatus表交互
 * created by 梁文俊
 * date:2018-4-1
 */
public class LoginStatusDao {

    private DBUtils dbUtils;

    //初始化DBUtils
    public LoginStatusDao() {
        dbUtils = DBUtils.getInstance();
    }

    /**
     * 改变当前登陆的用户
     *
     * @param current_username:现在登陆的用户名
     * @param permission_id:所登陆用户的权限
     */
    public void changeUser(String current_username, int permission_id) {
        String sql = "DELETE FROM logstatus;";
        dbUtils.excute(sql);
        sql = "INSERT logstatus(current_username,permission_id) VALUES(?,?);";
        List<Object> paramList = new ArrayList<>();
        paramList.add(current_username);
        paramList.add(permission_id);
        dbUtils.excute(sql, paramList);
    }

    /**
     * 注销登陆
     */
    public void logout() {
        String sql = "DELETE FROM logstatus;";
        dbUtils.excute(sql);
    }

    /**
     * 检测是否已登陆
     *
     * @return true表示已登陆, false表示未登录
     */
    public boolean isLogin() {
        String sql = "SELECT * FROM logstatus;";
        List<Object> paramList = new ArrayList<>();
        List<Map<String, Object>> resultList = dbUtils.query(sql, paramList); //搜索结果List
        if (resultList == null || resultList.size() == 0) {
            //为空则代表没有登陆返回false
            return false;
        } else {
            //不为空则说明已登陆，返回true
            return true;
        }
    }

    /**
     * 返回当前登陆者的权限id
     *
     * @return 当前登陆的人的权限id
     */
    public int getPermission() {
        String sql = "SELECT * FROM logstatus;";
        List<Object> paramList = new ArrayList<>();
        List<Map<String, Object>> resultList = dbUtils.query(sql, paramList); //搜索结果List
        if (resultList == null || resultList.size() == 0) {
            //为空则代表没有登陆返回-1
            return -1;
        } else {
            //不为空则说明已登陆，返回当前权限id
            return Integer.valueOf(resultList.get(0).get("permission_id").toString());
        }
    }

    /**
     * 获取当前登录用户名
     * @return 当前登录用户名
     */
    public String getUsername(){
        String sql = "SELECT * FROM logstatus;";
        List<Object> paramList = new ArrayList<>();
        List<Map<String, Object>> resultList = dbUtils.query(sql, paramList); //搜索结果List
        if (resultList == null || resultList.size() == 0) {
            //为空则代表没有登陆返回""
            return "";
        } else {
            //不为空则说明已登陆，返回当前username
            return resultList.get(0).get("current_username").toString();
        }
    }


    /**
     * 返回当前登陆者的身份
     *
     * @return 当前登陆者身份
     */
    public String getIdentity() {
        int permissionId = getPermission();
        String sql = "SELECT * FROM permission WHERE id = ?;";
        List<Object> paramList = new ArrayList<>();
        paramList.add(permissionId);
        List<Map<String, Object>> resultList = dbUtils.query(sql, paramList); //搜索结果List
        if (resultList == null || resultList.size() == 0) {
            //为空则代表没有登陆返回空
            return "";
        } else {
            //不为空则说明已登陆，返回当前身份名
            return resultList.get(0).get("name").toString();
        }
    }

}
