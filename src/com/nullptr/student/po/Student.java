package com.nullptr.student.po;

/**
 * 学生类
 * created by 梁文俊
 * date:2018-3-30
 */
public class Student {
    private int id;         //学生id
    private String name;    //学生名
    private String sex;     //性别
    private String phone;   //电话
    private String email;   //邮箱
    private int classId;    //所属班级id

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }
}
