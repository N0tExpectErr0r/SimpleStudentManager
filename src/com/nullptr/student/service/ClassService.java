package com.nullptr.student.service;

import com.nullptr.student.po.TheClass;

import java.util.List;

/**
 * ClassService接口
 * 用于操作Class
 * created by 梁文俊
 * date:2018-3-31
 */
public interface ClassService {
    //添加班级
    public void add(TheClass theClass);

    //修改班级信息
    public void update(TheClass theClass);

    //删除班级信息
    public void delete(int id);

    //通过id查找班级
    public TheClass findClassById(int id);

    //通过班级名查找班级
    public TheClass findClassByName(String name);

    //通过年级id获取班级列表
    public List<TheClass> findListByGradeId(int grade_id);

    //通过教授id获取班级列表
    public List<TheClass> findClassByTeacherId(int teacher_id);

    //获取班级列表
    public List<TheClass> getClassList();

    //通过关键词获取班级列表
    public List<TheClass> findListWithKeyword(String column, String keyword);
}
