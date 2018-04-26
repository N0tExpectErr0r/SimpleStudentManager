package com.nullptr.student.po;

/**
 * 班级类
 * created by 梁文俊
 * date:2018-3-30
 */
public class TheClass {
    private int id;         //班级id
    private String name;    //班级名
    private int gradeId;   //所属年级id
    private int teacherId; //班主任id

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

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int grade_id) {
        this.gradeId = grade_id;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacher_id) {
        this.teacherId = teacher_id;
    }

    public int compareTo(TheClass arg0) {
        return this.getName().compareTo(arg0.getName());
    }
}
