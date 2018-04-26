package com.nullptr.student.po;

/**
 * 学校类
 * created by 梁文俊
 * date:2018-3-30
 */
public class School {
    private int id;         //学校id
    private String name;    //学校名
    private int teacherId; //校长id

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

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
}
