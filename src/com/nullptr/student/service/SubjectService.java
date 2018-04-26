package com.nullptr.student.service;

import com.nullptr.student.po.Subject;

import java.util.List;

/**
 * SubjectService接口
 * 用于操作Subject
 * created by 梁文俊
 * date:2018-3-31
 */
public interface SubjectService {
    //添加科目
    public void add(String name);

    //检查name数据是否正确,不正确返回对应的错误信息
    public String check(String name);

    //修改科目
    public void update(Subject subject);

    //删除科目
    public void delete(int id);

    //通过科目id获取科目名
    public String findSubjectNameById(int id);

    //通过科目名获取科目id
    public int findSubjectIdByName(String name);

    //获取科目列表
    public List<Subject> getSubjectList();
}
