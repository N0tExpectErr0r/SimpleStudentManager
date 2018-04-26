package com.nullptr.student.service;

import com.nullptr.student.dao.ClassDao;
import com.nullptr.student.factory.ServiceFactory;
import com.nullptr.student.po.TheClass;
import com.nullptr.student.po.Student;

import java.util.List;

/**
 * ClassServiceImpl 实现了接口
 * created by 梁文俊
 * date:2018-3-31
 */
public class ClassServiceImpl implements ClassService {

    //添加班级
    public void add(TheClass theClass) {
        new ClassDao().add(theClass);
    }

    //修改班级信息
    public void update(TheClass theClass) {
        new ClassDao().update(theClass);
    }

    //删除班级信息
    public void delete(int id) {
        //向下删除(删除学生=>删除学生成绩)
        StudentService studentService = ServiceFactory.getInstance().createService(StudentService.class);
        List<Student> students = studentService.findListByClassId(id);
        for (Student student : students) {
            studentService.delete(student.getId());
        }
        //删除班级信息
        new ClassDao().delete(id);
    }

    //通过id查找班级
    public TheClass findClassById(int id) {
        return new ClassDao().findClassById(id);
    }

    //通过班级名查找班级
    public TheClass findClassByName(String name) {
        return new ClassDao().findClassByName(name);
    }

    //通过年级id获取班级列表
    public List<TheClass> findListByGradeId(int grade_id) {
        return new ClassDao().findListByGradeId(grade_id);
    }

    //通过教授id获取班级列表
    public List<TheClass> findClassByTeacherId(int teacher_id){
        return new ClassDao().findClassByTeacherId(teacher_id);
    }

    //获取班级列表
    public List<TheClass> getClassList() {
        return new ClassDao().getClassList();
    }

    //通过关键词获取班级列表
    public List<TheClass> findListWithKeyword(String column, String keyword) {
        return new ClassDao().findListWithKeyword(column, keyword);
    }
}
