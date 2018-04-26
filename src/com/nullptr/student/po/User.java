package com.nullptr.student.po;

/**
 * 用户类
 * created by 梁文俊
 * date:2018-4-10
 */
public class User {
    private String username;    //用户名
    private String password;    //密码
    private int permissionId;   //权限id

    /**
     * Getter 与 Setter
     */

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }
}
