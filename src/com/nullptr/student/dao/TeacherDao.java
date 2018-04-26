package com.nullptr.student.dao;

import com.nullptr.student.po.Teacher;
import com.nullptr.student.utils.DBUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TeacherDao
 * 用于和teacher表交互
 * created by 梁文俊
 * date:2018-3-30
 */

public class TeacherDao {

    private DBUtils dbUtils;

    //初始化DBUtils
    public TeacherDao() {
        dbUtils = DBUtils.getInstance();
    }

    /**
     * 增加老师
     *
     * @param teacher:要添加的老师
     */
    public void add(Teacher teacher) {
        if (teacher == null) {
            throw new IllegalArgumentException("Teacher to add cannot be null");
        }
        String sql = "INSERT teacher(name,phone,email) VALUES(?,?,?);";
        List<Object> paramList = new ArrayList<>();

        paramList.add(teacher.getName());
        paramList.add(teacher.getPhone());
        paramList.add(teacher.getEmail());
        dbUtils.excute(sql, paramList);
    }

    /**
     * 更新老师信息
     *
     * @param teacher:Teacher类，包含更新后的值
     */
    public void update(Teacher teacher) {
        if (teacher == null || teacher.getId() == 0) {
            throw new IllegalArgumentException("Teacher to update cannot be null or id equals 0");
        }
        String sql = "UPDATE teacher SET name=?,phone=?,email=? WHERE id=?;";
        List<Object> paramList = new ArrayList<>();

        paramList.add(teacher.getName());
        paramList.add(teacher.getPhone());
        paramList.add(teacher.getEmail());
        paramList.add(teacher.getId());
        dbUtils.excute(sql, paramList);
    }

    /**
     * 删除老师信息
     *
     * @param id:要删除的老师的id号
     */
    public void delete(int id) {
        String sql = "DELETE FROM teacher WHERE id=?;";
        List<Object> paramList = new ArrayList<>();

        paramList.add(id);
        dbUtils.excute(sql, paramList);
    }

    /**
     * 通过id查找老师信息
     *
     * @param id:要查找的老师id
     * @return 找到的Teacher
     */
    public Teacher findTeacherById(int id) {
        String sql = "SELECT * FROM teacher WHERE id=?;";
        List<Object> paramList = new ArrayList<>();

        paramList.add(id);
        List<Map<String, Object>> resultList = dbUtils.query(sql, paramList); //搜索结果List
        if (resultList == null || resultList.size() == 0) {
            //为空则返回空对象
            return null;
        } else {
            //不为空，获取第一个老师(id相同的只会有一个)，并返回其Teacher对象
            //注意，要类型转换(Object转为相应类型)
            Teacher teacher = new Teacher();
            teacher.setId(Integer.valueOf(resultList.get(0).get("id").toString()));
            teacher.setName((String) resultList.get(0).get("name"));
            teacher.setPhone((String) resultList.get(0).get("phone"));
            teacher.setEmail((String) resultList.get(0).get("email"));
            return teacher;
        }
    }

    /**
     * 通过名称查找老师
     *
     * @param name:要查找的老师名
     * @return 找到的Teacher
     */
    public Teacher findTeacherByName(String name) {
        String sql = "SELECT * FROM teacher WHERE name=?;";
        List<Object> paramList = new ArrayList<>();

        paramList.add(name);
        List<Map<String, Object>> resultList = dbUtils.query(sql, paramList); //搜索结果List
        if (resultList == null || resultList.size() == 0) {
            //为空则返回空对象
            return null;
        } else {
            //不为空，获取第一个老师(id相同的只会有一个)，并返回其Teacher对象
            //注意，要类型转换(Object转为相应类型)
            Teacher teacher = new Teacher();
            teacher.setId(Integer.valueOf(resultList.get(0).get("id").toString()));
            teacher.setName((String) resultList.get(0).get("name"));
            teacher.setPhone((String) resultList.get(0).get("phone"));
            teacher.setEmail((String) resultList.get(0).get("email"));
            return teacher;
        }
    }

    /**
     * 获取所有老师List
     *
     * @return 一个含有所有老师的List
     */
    public List<Teacher> getTeacherList() {
        String sql = "SELECT * FROM teacher;";
        List<Object> paramList = new ArrayList<>();
        List<Map<String, Object>> resultList = dbUtils.query(sql, paramList); //搜索结果List
        List<Teacher> returnList = new ArrayList<>();   //返回的老师List

        if (resultList == null || resultList.size() == 0) {
            //为空则返回空List
            return new ArrayList<>();
        } else {
            //不为空，获取第一个老师(id相同的只会有一个)，并返回其Teacher对象
            //注意，要类型转换(Object转为相应类型)
            for (Map<String, Object> item : resultList) {
                Teacher teacher = new Teacher();
                teacher.setId(Integer.valueOf(item.get("id").toString()));
                teacher.setName((String) item.get("name"));
                teacher.setPhone((String) item.get("phone"));
                teacher.setEmail((String) item.get("email"));
                returnList.add(teacher);
            }
            return returnList;
        }
    }

}
