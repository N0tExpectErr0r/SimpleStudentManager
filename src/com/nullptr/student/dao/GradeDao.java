package com.nullptr.student.dao;

import com.nullptr.student.po.Grade;
import com.nullptr.student.utils.DBUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * GradeDao
 * 用于和grade表交互
 * created by 梁文俊
 * date:2018-3-30
 */
public class GradeDao {
    private DBUtils dbUtils;

    //初始化DBUtils
    public GradeDao() {
        dbUtils = DBUtils.getInstance();
    }

    /**
     * 添加年级
     *
     * @param grade:要添加的年级
     */
    public void add(Grade grade) {
        if (grade == null) {
            throw new IllegalArgumentException("Grade to add cannot be null");
        }
        String sql = "INSERT grade(name,school_id,teacher_id) VALUES(?,?,?);";
        List<Object> paramList = new ArrayList<>();

        paramList.add(grade.getName());
        paramList.add(grade.getSchoolId());
        paramList.add(grade.getTeacherId());
        dbUtils.excute(sql, paramList);
    }

    /**
     * 更新年级信息
     *
     * @param grade:用于更新的年级
     */
    public void update(Grade grade) {
        if (grade == null || grade.getId() == 0) {
            throw new IllegalArgumentException("Grade to update cannot be null or id equals 0");
        }
        String sql = "UPDATE grade SET name=?,school_id=?,teacher_id=? WHERE id=?;";
        List<Object> paramList = new ArrayList<>();

        paramList.add(grade.getName());
        paramList.add(grade.getSchoolId());
        paramList.add(grade.getTeacherId());
        paramList.add(grade.getId());
        dbUtils.excute(sql, paramList);
    }

    /**
     * 删除年级信息
     *
     * @param id:要删除的年级id
     */
    public void delete(int id) {
        String sql = "DELETE FROM grade WHERE id=?;";
        List<Object> paramList = new ArrayList<>();

        paramList.add(id);
        dbUtils.excute(sql, paramList);
    }

    /**
     * 通过id查找年级信息
     *
     * @param id:要查找的年级id
     * @return 找到的Grade
     */
    public Grade findGradeById(int id) {
        String sql = "SELECT * FROM grade WHERE id=?;";
        List<Object> paramList = new ArrayList<>();
        paramList.add(id);
        List<Map<String, Object>> resultList = dbUtils.query(sql, paramList); //搜索结果List

        if (resultList == null || resultList.size() == 0) {
            //为空则返回空对象
            return null;
        } else {
            //不为空，获取第一个年级(id相同的只会有一个)，并返回其Grade对象
            //注意，要类型转换(Object转为相应类型)
            Grade grade = new Grade();
            grade.setId(Integer.valueOf(resultList.get(0).get("id").toString()));
            grade.setName((String) resultList.get(0).get("name"));
            grade.setSchoolId(Integer.valueOf(resultList.get(0).get("school_id").toString()));
            grade.setTeacherId(Integer.valueOf(resultList.get(0).get("teacher_id").toString()));
            return grade;
        }
    }

    /**
     * 通过年级名查找年级信息
     *
     * @param name:要查找的年级名
     * @return 找到的Grade
     */
    public Grade findGradeByName(String name) {
        String sql = "SELECT * FROM grade WHERE name=?;";

        List<Object> paramList = new ArrayList<>();
        paramList.add(name);
        List<Map<String, Object>> resultList = dbUtils.query(sql, paramList); //搜索结果List

        if (resultList == null || resultList.size() == 0) {
            //为空则返回空对象
            return null;
        } else {
            //不为空，获取第一个年级(id相同的只会有一个)，并返回其Grade对象
            //注意，要类型转换(Object转为相应类型)
            Grade grade = new Grade();
            grade.setId(Integer.valueOf(resultList.get(0).get("id").toString()));
            grade.setName((String) resultList.get(0).get("name"));
            grade.setSchoolId(Integer.valueOf(resultList.get(0).get("school_id").toString()));
            grade.setTeacherId(Integer.valueOf(resultList.get(0).get("teacher_id").toString()));
            return grade;
        }
    }

    /**
     * 通过学校id获取年级列表
     *
     * @param school_id:学校id
     * @return 该学校的年级组成的ArrayList
     */
    public List<Grade> findListBySchoolId(int school_id) {
        String sql = "SELECT * FROM grade WHERE school_id=?";
        List<Object> paramList = new ArrayList<>();
        paramList.add(school_id);
        List<Map<String, Object>> resultList = dbUtils.query(sql, paramList); //搜索结果List
        List<Grade> returnList = new ArrayList<>();   //返回的年级List

        if (resultList == null || resultList.size() == 0) {
            //为空返回空表
            return new ArrayList<>();
        } else {
            //不为空，遍历resultList，获取每个年级，组成Grade对象集合并返回
            //注意，要类型转换(Object转为相应类型)
            for (Map<String, Object> item : resultList) {
                Grade grade = new Grade();
                grade.setId(Integer.valueOf(item.get("id").toString()));
                grade.setName((String) item.get("name"));
                grade.setSchoolId(Integer.valueOf(item.get("school_id").toString()));
                grade.setTeacherId(Integer.valueOf(item.get("teacher_id").toString()));
                returnList.add(grade);
            }
            return returnList;
        }
    }

    /**
     * 获取年级列表
     *
     * @return 所有年级组成的ArrayList
     */
    public List<Grade> getGradeList() {
        String sql = "SELECT * FROM grade;";
        List<Object> paramList = new ArrayList<>();
        List<Map<String, Object>> resultList = dbUtils.query(sql, paramList); //搜索结果List
        List<Grade> returnList = new ArrayList<>();   //返回的年级List

        if (resultList == null || resultList.size() == 0) {
            //为空返回空表
            return new ArrayList<>();
        } else {
            //不为空，遍历resultList，获取每个年级，组成Grade对象集合并返回
            //注意，要类型转换(Object转为相应类型)
            for (Map<String, Object> item : resultList) {
                Grade grade = new Grade();
                grade.setId(Integer.valueOf(item.get("id").toString()));
                grade.setName((String) item.get("name"));
                grade.setSchoolId(Integer.valueOf(item.get("school_id").toString()));
                grade.setTeacherId(Integer.valueOf(item.get("teacher_id").toString()));
                returnList.add(grade);
            }
            return returnList;
        }
    }
}
