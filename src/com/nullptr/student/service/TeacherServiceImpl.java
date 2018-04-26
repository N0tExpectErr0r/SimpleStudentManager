package com.nullptr.student.service;

import com.nullptr.student.dao.TeacherDao;
import com.nullptr.student.po.Teacher;

import java.util.List;
import java.util.regex.Pattern;

/**
 * TeacherServiceImpl 实现了接口
 * created by 梁文俊
 * date:2018-3-31
 */
public class TeacherServiceImpl implements TeacherService {
    //添加老师，并返回老师id
    public int add(Teacher teacher) {
        new TeacherDao().add(teacher);
        return getTeacherList().get(getTeacherList().size()-1).getId(); //一定对应最后一个老师
    }

    //检查Teacher数据是否正确,不正确返回对应的错误信息
    public String check(Teacher teacher){
        //姓名Pattern
        Pattern namePattern = Pattern.compile("^[\\u4E00-\\u9FA5A-Za-z]+$");
        if (!namePattern.matcher(teacher.getName()).matches()){
            //如果姓名格式不正确
            return "您输入的姓名格式有误或未输入";
        }
        //电话号码Pattern
        Pattern phonePattern = Pattern.compile("^(13[0-9]|14[0-9]|15[0-9]|166|17[0-9]|18[0-9]|19[8|9])\\d{8}$");
        if (!teacher.getPhone().equals("")&&!phonePattern.matcher(teacher.getPhone()).matches()){
            //如果电话格式不正确且电话号码不为空
            return "您输入的电话号码格式有误";
        }
        //邮箱Pattern
        Pattern emailPattern = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
        if (!teacher.getEmail().equals("")&&!emailPattern.matcher(teacher.getEmail()).matches()){
            //如果邮箱格式不正确且邮箱不为空
            return "您输入的邮箱格式有误";
        }
        return "ok";
    }

    //修改老师信息
    public void update(Teacher teacher) {
        new TeacherDao().update(teacher);
    }

    //删除老师
    public void delete(int id) {
        new TeacherDao().delete(id);
    }

    //通过id获得老师对象
    public Teacher findTeacherById(int id) {
        return new TeacherDao().findTeacherById(id);
    }

    //通过名称获取老师
    public Teacher findTeacherByName(String name) {
        return new TeacherDao().findTeacherByName(name);
    }

    //获得老师列表
    public List<Teacher> getTeacherList() {
        return new TeacherDao().getTeacherList();
    }

}
