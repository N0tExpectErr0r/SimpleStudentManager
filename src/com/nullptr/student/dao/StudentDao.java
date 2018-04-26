package com.nullptr.student.dao;

import com.nullptr.student.po.Student;
import com.nullptr.student.utils.DBUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * StudentDao
 * 用于和student表交互
 * created by 梁文俊
 * date:2018-3-30
 */

public class StudentDao {

    private DBUtils dbUtils;

    //初始化DBUtils
    public StudentDao() {
        dbUtils = DBUtils.getInstance();
    }

    /**
     * 增加学生
     *
     * @param student:要添加的学生
     */
    public void add(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student to add cannot be null");
        }
        String sql = "INSERT student(name,sex,phone,email,class_id) VALUES(?,?,?,?,?);";
        List<Object> paramList = new ArrayList<>();
        paramList.add(student.getName());
        paramList.add(student.getSex());
        paramList.add(student.getPhone());
        paramList.add(student.getEmail());
        paramList.add(student.getClassId());
        dbUtils.excute(sql, paramList);
    }

    /**
     * 更新学生信息
     *
     * @param student:要用于更新的学生
     */
    public void update(Student student) {
        if (student == null || student.getId() == 0) {
            throw new IllegalArgumentException("Student to update cannot be null or id equals 0");
        }
        String sql = "UPDATE student SET name=?,sex=?,phone=?,email=?,class_id=? WHERE id=?;";
        List<Object> paramList = new ArrayList<>();
        paramList.add(student.getName());
        paramList.add(student.getSex());
        paramList.add(student.getPhone());
        paramList.add(student.getEmail());
        paramList.add(student.getClassId());
        paramList.add(student.getId());
        dbUtils.excute(sql, paramList);
    }

    /**
     * 删除学生信息
     *
     * @param id:要删除的学生的id号
     */
    public void delete(int id) {
        String sql = "DELETE FROM student WHERE id=?;";
        List<Object> paramList = new ArrayList<>();
        paramList.add(id);
        dbUtils.excute(sql, paramList);
    }

    /**
     * 通过id查找学生信息
     *
     * @param id:要查找的学生的id
     * @return 找到的Student
     */
    public Student findStudentById(int id) {
        String sql = "SELECT * FROM student WHERE id=?;";
        List<Object> paramList = new ArrayList<>();
        paramList.add(id);
        List<Map<String, Object>> resultList = dbUtils.query(sql, paramList); //搜索结果List

        if (resultList == null || resultList.size() == 0) {
            //为空则返回空对象
            return null;
        } else {
            //不为空，获取第一个学生(id相同的只会有一个)，并返回其Student对象
            //注意，要类型转换(Object转为相应类型)
            Student student = new Student();
            student.setId(Integer.valueOf(resultList.get(0).get("id").toString()));
            student.setName((String) resultList.get(0).get("name"));
            student.setSex((String) resultList.get(0).get("sex"));
            student.setPhone((String) resultList.get(0).get("phone"));
            student.setEmail((String) resultList.get(0).get("email"));
            student.setClassId(Integer.valueOf(resultList.get(0).get("class_id").toString()));
            return student;
        }
    }

    /**
     * 通过关键词模糊查询
     *
     * @param column:列名(如 name id等)
     * @param keyword:关键词
     * @return
     */
    public List<Student> findListWithKeyword(String column, String keyword) {
        //查找column里含有param的Student 如name中含有'三'
        String sql = "SELECT * FROM student WHERE " + column + " LIKE  \"%\"?\"%\";";
        List<Object> paramList = new ArrayList<>();
        paramList.add(keyword);
        List<Map<String, Object>> resultList = dbUtils.query(sql, paramList); //搜索结果List
        List<Student> returnList = new ArrayList<>();

        if (resultList == null || resultList.size() == 0) {
            //为空返回空List
            return new ArrayList<>();
        } else {
            //不为空，遍历resultList，获取每个学生，组成集合返回
            for (Map<String, Object> item : resultList) {
                Student student = new Student();
                student.setId(Integer.valueOf(item.get("id").toString()));
                student.setName((String) item.get("name"));
                student.setSex((String) item.get("sex"));
                student.setPhone((String) item.get("phone"));
                student.setEmail((String) item.get("email"));
                student.setClassId(Integer.valueOf(item.get("class_id").toString()));
                returnList.add(student);
            }
            return returnList;
        }

    }

    /**
     * 通过班级id查找学生列表
     *
     * @param class_id:班级id
     * @return 该班级的学生组成的ArrayList
     */
    public List<Student> findListByClassId(int class_id) {
        String sql = "SELECT * FROM student WHERE class_id=?";
        List<Object> paramList = new ArrayList<>();
        paramList.add(class_id);
        List<Map<String, Object>> resultList = dbUtils.query(sql, paramList); //搜索结果List
        List<Student> returnList = new ArrayList<>();   //返回的学生List

        if (resultList == null || resultList.size() == 0) {
            //为空返回空表;
            return new ArrayList<>();
        } else {
            //不为空，遍历resultList，获取每个学生，组成Student对象集合并返回
            //注意，要类型转换(Object转为相应类型)
            for (Map<String, Object> item : resultList) {
                Student student = new Student();
                student.setId(Integer.valueOf(item.get("id").toString()));
                student.setName((String) item.get("name"));
                student.setSex((String) item.get("sex"));
                student.setPhone((String) item.get("phone"));
                student.setEmail((String) item.get("email"));
                student.setClassId(Integer.valueOf(item.get("class_id").toString()));
                returnList.add(student);
            }
            return returnList;
        }
    }

    /**
     * 获取所有学生List
     *
     * @return 一个含有所有学生的List
     */
    public List<Student> getStudentList() {
        String sql = "SELECT * FROM student;";
        List<Object> paramList = new ArrayList<>();
        List<Map<String, Object>> resultList = dbUtils.query(sql, paramList); //搜索结果List
        List<Student> returnList = new ArrayList<>();   //返回的学生List

        if (resultList == null || resultList.size() == 0) {
            //为空则返回空List
            return new ArrayList<>();
        } else {
            //不为空，获取第一个学生(id相同的只会有一个)，并返回其Student集合
            //注意，要类型转换(Object转为相应类型)
            for (Map<String, Object> item : resultList) {
                Student student = new Student();
                student.setId(Integer.valueOf(item.get("id").toString()));
                student.setName((String) item.get("name"));
                student.setSex((String) item.get("sex"));
                student.setPhone((String) item.get("phone"));
                student.setEmail((String) item.get("email"));
                student.setClassId(Integer.valueOf(item.get("class_id").toString()));
                returnList.add(student);
            }
            return returnList;
        }
    }
}
