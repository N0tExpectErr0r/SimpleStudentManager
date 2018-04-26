package com.nullptr.student.dao;

import com.nullptr.student.po.Subject;
import com.nullptr.student.utils.DBUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * SubjecDao
 * 用于和subject表交互
 * created by 梁文俊
 * date:2018-3-31
 */

public class SubjectDao {
    private DBUtils dbUtils;

    //初始化DBUtils
    public SubjectDao() {
        dbUtils = DBUtils.getInstance();
    }

    /**
     * 添加科目
     *
     * @param name:添加的科目名称
     */
    public void add(String name) {
        if (name.equals("")) {
            throw new IllegalArgumentException("Subject's name to add cannot be empty");
        }
        String sql = "INSERT subject(name) VALUES(?);";
        List<Object> paramList = new ArrayList<>();

        paramList.add(name);
        dbUtils.excute(sql, paramList);
    }

    /**
     * 更新科目信息
     *
     * @param subject:用于更新的科目
     */
    public void update(Subject subject) {
        if (subject == null || subject.getId() == 0) {
            throw new IllegalArgumentException("Subject to update cannot be null or id equals 0");

        }
        String sql = "UPDATE subject SET name=? WHERE id=?;";
        List<Object> paramList = new ArrayList<>();

        paramList.add(subject.getName());
        paramList.add(subject.getId());
        dbUtils.excute(sql, paramList);
    }

    /**
     * 删除科目
     *
     * @param id:要删除的科目id
     */
    public void delete(int id) {
        String sql = "DELETE FROM subject WHERE id=?;";
        List<Object> paramList = new ArrayList<>();

        paramList.add(id);
        dbUtils.excute(sql, paramList);
    }

    /**
     * 通过科目id查找科目名
     *
     * @param id:要查找的科目id
     * @return 找到的科目名
     */
    public String findSubjectNameById(int id) {
        String sql = "SELECT * FROM subject WHERE id=?;";
        List<Object> paramList = new ArrayList<>();
        paramList.add(id);
        List<Map<String, Object>> resultList = dbUtils.query(sql, paramList); //搜索结果List

        if (resultList == null || resultList.size() == 0) {
            //为空则返回空名字
            return "";
        } else {
            //不为空，返回第一个科目名
            return resultList.get(0).get("name").toString();
        }
    }

    //通过科目名获取科目id
    public int findSubjectIdByName(String name) {
        String sql = "SELECT * FROM subject WHERE name=?;";
        List<Object> paramList = new ArrayList<>();
        paramList.add(name);
        List<Map<String, Object>> resultList = dbUtils.query(sql, paramList); //搜索结果List

        if (resultList == null || resultList.size() == 0) {
            //为空则返回0
            return 0;
        } else {
            //不为空，返回第一个科目id
            return Integer.valueOf(resultList.get(0).get("id").toString());
        }
    }

    /**
     * 获取所有科目List
     *
     * @return 一个含有所有科目的List
     */
    public List<Subject> getSubjectList() {
        String sql = "SELECT * FROM subject;";
        List<Object> paramList = new ArrayList<>();
        List<Map<String, Object>> resultList = dbUtils.query(sql, paramList); //搜索结果List
        List<Subject> returnList = new ArrayList<>();   //返回的老师List

        if (resultList == null || resultList.size() == 0) {
            //为空则返回空List
            return new ArrayList<>();
        } else {
            //不为空，获取第一个老师(id相同的只会有一个)，并返回其Teacher对象
            //注意，要类型转换(Object转为相应类型)
            for (Map<String, Object> item : resultList) {
                Subject subject = new Subject();
                subject.setId(Integer.valueOf(item.get("id").toString()));
                subject.setName((String) item.get("name"));
                returnList.add(subject);
            }
            return returnList;
        }
    }
}
