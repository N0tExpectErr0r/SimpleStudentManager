package com.nullptr.student.po;

/**
 * 科目类
 * created by 梁文俊
 * date:2018-3-30
 */
public class Subject {
    private int id;         //科目id
    private String name;    //科目名称

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
}
