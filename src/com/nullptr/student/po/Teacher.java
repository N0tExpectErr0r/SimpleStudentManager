package com.nullptr.student.po;

/**
 * 教师类
 * created by 梁文俊
 * date:2018-3-30
 */
public class Teacher {
    private int id;         //老师id
    private String name;    //姓名
    private String phone;   //电话
    private String email;   //邮箱

    /**
     * Getter 与 Setter
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
