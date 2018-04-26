package com.nullptr.student.service;

import com.nullptr.student.po.Grade;

import java.util.List;

/**
 * GradeService接口
 * 用于操作Grade
 * created by 梁文俊
 * date:2018-3-31
 */
public interface GradeService {

    //添加年级
    public void add(Grade grade);

    //修改年级信息
    public void update(Grade grade);

    //删除年级信息
    public void delete(int id);

    //通过id查找年级
    public Grade findGradeById(int id);

    //通过年级名查找年级
    public Grade findGradeByName(String name);

    //通过学校id获得年级列表
    public List<Grade> findListBySchoolId(int school_id);

    //获取年级列表
    public List<Grade> getGradeList();

}
