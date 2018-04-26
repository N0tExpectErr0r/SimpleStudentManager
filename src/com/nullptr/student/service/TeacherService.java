package com.nullptr.student.service;

import com.nullptr.student.po.Teacher;

import java.util.List;

/**
 * TeacherService接口
 * 用于操作Teacher
 * created by 梁文俊
 * date:2018-3-31
 */
public interface TeacherService {

    //添加老师
    public int add(Teacher teacher);

    //检查Teacher数据是否正确,不正确返回对应的错误信息
    public String check(Teacher teacher);

    //修改老师信息
    public void update(Teacher teacher);

    //删除老师
    public void delete(int id);

    //通过id获得老师对象
    public Teacher findTeacherById(int id);

    //通过名称获取老师
    public Teacher findTeacherByName(String name);

    //获得老师列表
    public List<Teacher> getTeacherList();

}
