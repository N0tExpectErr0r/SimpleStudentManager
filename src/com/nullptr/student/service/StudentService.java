package com.nullptr.student.service;

import com.nullptr.student.po.Student;

import java.util.List;

/**
 * StudentService接口
 * 用于操作Student
 * created by 梁文俊
 * date:2018-3-31
 */
public interface StudentService {
    //添加学生
    public void add(Student student);

    //检查Student数据是否正确,不正确返回对应的错误信息
    public String check(Student student);

    //修改学生
    public void update(Student student);

    //删除学生
    public void delete(int id);

    //通过id获取唯一学生
    public Student findStudentById(int id);

    //通过关键词模糊查询学生
    public List<Student> findListWithKeyword(String column, String keyword);

    //通过班级id查询学生
    public List<Student> findListByClassId(int class_id);

    //获取所有学生列表
    public List<Student> getStudentList();
}
