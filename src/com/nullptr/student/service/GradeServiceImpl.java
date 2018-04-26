package com.nullptr.student.service;

import com.nullptr.student.dao.GradeDao;
import com.nullptr.student.factory.ServiceFactory;
import com.nullptr.student.po.TheClass;
import com.nullptr.student.po.Grade;

import java.util.List;

/**
 * GradeServiceImpl 实现了接口
 * created by 梁文俊
 * date:2018-3-31
 */
public class GradeServiceImpl implements GradeService {

    //添加年级
    public void add(Grade grade) {
        new GradeDao().add(grade);
    }

    //修改年级信息
    public void update(Grade grade) {
        new GradeDao().update(grade);
    }

    //删除年级信息
    public void delete(int id) {
        //删除该年级的班级
        ClassService classService = ServiceFactory.getInstance().createService(ClassService.class);
        List<TheClass> classList = classService.findListByGradeId(id);
        for (TheClass theClass : classList) {
            classService.delete(theClass.getId());
        }
        //删除该年级
        new GradeDao().delete(id);
    }

    //通过id查找年级
    public Grade findGradeById(int id) {
        return new GradeDao().findGradeById(id);
    }

    //通过年级名查找年级
    public Grade findGradeByName(String name) {
        return new GradeDao().findGradeByName(name);
    }

    //通过学校id获得年级列表
    public List<Grade> findListBySchoolId(int school_id) {
        return new GradeDao().findListBySchoolId(school_id);
    }

    //获取年级列表
    public List<Grade> getGradeList() {
        return new GradeDao().getGradeList();
    }
}
