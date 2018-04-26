package com.nullptr.student.po;

/**
 * 分数类
 * created by 梁文俊
 * date:2018-3-30
 */
public class Score {
    private int id;             //分数id
    private int score;          //分值
    private int studentId;     //所属学生id
    private int subjectId;     //所属科目id
    private int indexNum;         //成绩条数

    /**
     * Getter 与 Setter
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getIndexNum() {
        return indexNum;
    }

    public void setIndexNum(int index) {
        this.indexNum = index;
    }
}
