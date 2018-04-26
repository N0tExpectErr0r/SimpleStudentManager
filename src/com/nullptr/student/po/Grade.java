package com.nullptr.student.po;

/**
 * 年级类
 * created by 梁文俊
 * date:2018-3-30
 */
public class Grade {
    private int id;         //年级id
    private String name;    //年级名
    private int schoolId;  //所属学校id
    private int teacherId; //级长id

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

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
}
