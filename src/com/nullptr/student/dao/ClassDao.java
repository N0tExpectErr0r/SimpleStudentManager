package com.nullptr.student.dao;

import com.nullptr.student.po.TheClass;
import com.nullptr.student.utils.DBUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClassDao
 * 用于和class表交互
 * created by 梁文俊
 * date:2018-3-30
 */

public class ClassDao {
    private DBUtils dbUtils;

    //初始化DBUtils
    public ClassDao() {
        dbUtils = DBUtils.getInstance();
    }

    /**
     * 添加班级
     *
     * @param theClass:要添加的班级
     */
    public void add(TheClass theClass) {
        if (theClass == null) {
            throw new IllegalArgumentException("Class to add cannot be null");
        }
        String sql = "INSERT class(name,grade_id,teacher_id) VALUES(?,?,?);";
        List<Object> paramList = new ArrayList<>();

        paramList.add(theClass.getName());
        paramList.add(theClass.getGradeId());
        paramList.add(theClass.getTeacherId());
        dbUtils.excute(sql, paramList);
    }

    /**
     * 更新班级信息
     *
     * @param theClass:用于更新的班级
     */
    public void update(TheClass theClass) {
        if (theClass == null || theClass.getId() == 0) {
            throw new IllegalArgumentException("Class to update cannot be null or id equals 0");
        }
        String sql = "UPDATE class SET name=?,grade_id=?,teacher_id=? WHERE id=?;";
        List<Object> paramList = new ArrayList<>();

        paramList.add(theClass.getName());
        paramList.add(theClass.getGradeId());
        paramList.add(theClass.getTeacherId());
        paramList.add(theClass.getId());
        dbUtils.excute(sql, paramList);
    }

    /**
     * 删除班级信息
     *
     * @param id:要删除的班级id
     */
    public void delete(int id) {
        String sql = "DELETE FROM class WHERE id=?;";
        List<Object> paramList = new ArrayList<>();
        paramList.add(id);
        dbUtils.excute(sql, paramList);
    }

    /**
     * 通过id查找班级信息
     *
     * @param id:要查找的班级id
     * @return 找到的Classes
     */
    public TheClass findClassById(int id) {
        String sql = "SELECT * FROM class WHERE id=?;";
        List<Object> paramList = new ArrayList<>();
        paramList.add(id);
        List<Map<String, Object>> resultList = dbUtils.query(sql, paramList); //搜索结果List

        if (resultList == null || resultList.size() == 0) {
            //为空则返回空对象
            return null;
        } else {
            //不为空，获取第一个班级(id相同的只会有一个)，并返回其Classes对象
            //注意，要类型转换(Object转为相应类型)
            TheClass theClass = new TheClass();
            theClass.setId(Integer.valueOf(resultList.get(0).get("id").toString()));
            theClass.setName((String) resultList.get(0).get("name"));
            theClass.setGradeId(Integer.valueOf(resultList.get(0).get("grade_id").toString()));
            theClass.setTeacherId(Integer.valueOf(resultList.get(0).get("teacher_id").toString()));
            return theClass;
        }
    }

    /**
     * 通过班级名获取班级
     *
     * @param name:班级名
     * @return 班级名对应的班级
     */
    public TheClass findClassByName(String name) {
        String sql = "SELECT * FROM class WHERE name=?;";
        List<Object> paramList = new ArrayList<>();
        paramList.add(name);
        List<Map<String, Object>> resultList = dbUtils.query(sql, paramList); //搜索结果List

        if (resultList == null || resultList.size() == 0) {
            //为空返回空对象
            return null;
        } else {
            //不为空，获取班级
            TheClass theClass = new TheClass();
            theClass.setId(Integer.valueOf(resultList.get(0).get("id").toString()));
            theClass.setName((String) resultList.get(0).get("name"));
            theClass.setGradeId(Integer.valueOf(resultList.get(0).get("grade_id").toString()));
            theClass.setTeacherId(Integer.valueOf(resultList.get(0).get("teacher_id").toString()));
            return theClass;
        }
    }

    /**
     * 通过年级id获取班级列表
     *
     * @param grade_id:年级id
     * @return 该年级的班级组成的ArrayList
     */
    public List<TheClass> findListByGradeId(int grade_id) {
        String sql = "SELECT * FROM class WHERE grade_id=?;";
        List<Object> paramList = new ArrayList<>();
        paramList.add(grade_id);
        List<Map<String, Object>> resultList = dbUtils.query(sql, paramList); //搜索结果List
        List<TheClass> returnList = new ArrayList<>();   //返回的班级List

        if (resultList == null || resultList.size() == 0) {
            //为空返回空表
            return new ArrayList<>();
        } else {
            //不为空，遍历resultList，获取每个班级，组成Classes对象集合并返回
            //注意，要类型转换(Object转为相应类型)
            for (Map<String, Object> item : resultList) {
                TheClass theClass = new TheClass();
                theClass.setId(Integer.valueOf(item.get("id").toString()));
                theClass.setName((String) item.get("name"));
                theClass.setGradeId(Integer.valueOf(item.get("grade_id").toString()));
                theClass.setTeacherId(Integer.valueOf(item.get("teacher_id").toString()));
                returnList.add(theClass);
            }
            return returnList;
        }
    }

    /**
     * 通过教师id获取班级列表
     * @param teacher_id:教师id
     * @return 教师教授年级列表
     */
    public List<TheClass> findClassByTeacherId(int teacher_id){
        String sql = "SELECT * FROM class WHERE teacher_id=?;";
        List<Object> paramList = new ArrayList<>();
        paramList.add(teacher_id);
        List<Map<String, Object>> resultList = dbUtils.query(sql, paramList); //搜索结果List
        List<TheClass> returnList = new ArrayList<>();   //返回的班级List

        if (resultList == null || resultList.size() == 0) {
            //为空返回空表
            return new ArrayList<>();
        } else {
            //不为空，遍历resultList，获取每个班级，组成Classes对象集合并返回
            //注意，要类型转换(Object转为相应类型)
            for (Map<String, Object> item : resultList) {
                TheClass theClass = new TheClass();
                theClass.setId(Integer.valueOf(item.get("id").toString()));
                theClass.setName((String) item.get("name"));
                theClass.setGradeId(Integer.valueOf(item.get("grade_id").toString()));
                theClass.setTeacherId(Integer.valueOf(item.get("teacher_id").toString()));
                returnList.add(theClass);
            }
            return returnList;
        }
    }

    /**
     * 获取班级列表
     *
     * @return 班级列表
     */
    public List<TheClass> getClassList() {
        String sql = "SELECT * FROM class;";
        List<Object> paramList = new ArrayList<>();
        List<Map<String, Object>> resultList = dbUtils.query(sql, paramList); //搜索结果List
        List<TheClass> returnList = new ArrayList<>();   //返回的班级List
        if (resultList == null || resultList.size() == 0) {
            //为空返回空表
            return new ArrayList<>();
        } else {
            //不为空，遍历resultList，获取每个班级，组成Classes对象集合并返回
            //注意，要类型转换(Object转为相应类型)
            for (Map<String, Object> item : resultList) {
                TheClass theClass = new TheClass();
                theClass.setId(Integer.valueOf(item.get("id").toString()));
                theClass.setName((String) item.get("name"));
                theClass.setGradeId(Integer.valueOf(item.get("grade_id").toString()));
                theClass.setTeacherId(Integer.valueOf(item.get("teacher_id").toString()));
                returnList.add(theClass);
            }
            return returnList;
        }
    }

    /**
     * 通过关键词模糊查询班级
     *
     * @param column:列名(如 name id等)
     * @param keyword:关键词
     * @return 找到的班级列表
     */
    public List<TheClass> findListWithKeyword(String column, String keyword) {
        //查找column里含有param的Classes 如name中含有'三'
        String sql = "SELECT * FROM class WHERE " + column + " LIKE  \"%\"?\"%\";";
        List<Object> paramList = new ArrayList<>();
        paramList.add(keyword);
        List<Map<String, Object>> resultList = dbUtils.query(sql, paramList); //搜索结果List
        List<TheClass> returnList = new ArrayList<>();

        if (resultList == null || resultList.size() == 0) {
            //为空返回空List
            return new ArrayList<>();
        } else {
            //不为空，遍历resultList，获取每个班级，组成集合返回
            for (Map<String, Object> item : resultList) {
                TheClass theClass = new TheClass();
                theClass.setId(Integer.valueOf(item.get("id").toString()));
                theClass.setName(item.get("name").toString());
                theClass.setGradeId(Integer.valueOf(item.get("grade_id").toString()));
                theClass.setTeacherId(Integer.valueOf(item.get("teacher_id").toString()));
                returnList.add(theClass);
            }
            return returnList;
        }
    }
}
