package com.nullptr.student.dao;


import com.nullptr.student.po.Grade;
import com.nullptr.student.po.School;
import com.nullptr.student.utils.DBUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 暂时不用(不需要用到学校的处理)
 * SchoolDao
 * 用于和school表交互
 * created by 梁文俊
 * date:2018-3-31
 */
public class SchoolDao {
    private DBUtils dbUtils;

    //初始化DBUtils
    public SchoolDao() {
        dbUtils = DBUtils.getInstance();
    }

    /**
     * 添加学校
     *
     * @param school:要添加的学校
     */
    public void add(School school) {
        if (school == null) {
            throw new IllegalArgumentException("School to add cannot be null");
        }
        String sql = "INSERT school(name,teacher_id) VALUES(?,?);";
        List<Object> paramList = new ArrayList<>();

        paramList.add(school.getName());
        paramList.add(school.getTeacherId());
        dbUtils.excute(sql, paramList);
    }

    /**
     * 更新学校信息
     *
     * @param school:用于更新的学校
     */
    public void update(School school) {
        if (school == null || school.getId() == 0) {
            throw new IllegalArgumentException("School to update cannot be null or id equals 0");
        }
        String sql = "UPDATE school SET name=?,teacher_id=? WHERE id=?;";
        List<Object> paramList = new ArrayList<>();

        paramList.add(school.getName());
        paramList.add(school.getTeacherId());
        paramList.add(school.getId());
        dbUtils.excute(sql, paramList);
    }

    /**
     * 删除学校信息
     *
     * @param id:要删除的学校id
     */
    public void delete(int id) {
        //删除该学校的年级
        GradeDao gradeDao = new GradeDao();
        List<Grade> grades = gradeDao.findListBySchoolId(id);
        for (Grade grade : grades) {
            gradeDao.delete(grade.getId());
        }
        //删除学校
        String sql = "DELETE FROM school WHERE id=?;";
        List<Object> paramList = new ArrayList<>();
        paramList.add(id);
        dbUtils.excute(sql, paramList);
    }

    /**
     * 通过id查找学校信息
     *
     * @param id:要查找的学校id
     * @return 找到的School
     */
    public School findSchoolById(int id) {
        String sql = "SELECT * FROM school WHERE id=?;";
        List<Object> paramList = new ArrayList<>();
        paramList.add(id);
        List<Map<String, Object>> resultList = dbUtils.query(sql, paramList); //搜索结果List
        if (resultList == null || resultList.size() == 0) {
            //为空则返回空对象
            return null;
        } else {
            //不为空，获取第一个学校(id相同的只会有一个)，并返回其School对象
            //注意，要类型转换(Object转为相应类型)
            School school = new School();
            school.setId(Integer.valueOf(resultList.get(0).get("id").toString()));
            school.setName((String) resultList.get(0).get("name"));
            school.setTeacherId(Integer.valueOf(resultList.get(0).get("teacher_id").toString()));
            return school;
        }
    }
}
