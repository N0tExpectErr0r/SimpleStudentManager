package com.nullptr.student.service;

import com.nullptr.student.dao.ScoreDao;
import com.nullptr.student.dao.SubjectDao;
import com.nullptr.student.factory.ServiceFactory;
import com.nullptr.student.po.Score;
import com.nullptr.student.po.Student;
import com.nullptr.student.po.Subject;

import java.util.List;
import java.util.regex.Pattern;

/**
 * SubjectServiceImpl 实现了接口
 * created by 梁文俊
 * date:2018-3-31
 */
public class SubjectServiceImpl implements SubjectService {


    //添加科目
    public void add(String name) {
        new SubjectDao().add(name);
        //获取添加的科目的id
        int index = new SubjectDao().getSubjectList().size() - 1;
        int id = new SubjectDao().getSubjectList().get(index).getId();

        //为每个学生添加该科目成绩并置为0
        StudentService studentService = ServiceFactory.getInstance().createService(StudentService.class);
        ScoreService scoreService = ServiceFactory.getInstance().createService(ScoreService.class);
        List<Student> studentList = studentService.getStudentList();
        for (Student student : studentList) {
            for (int i = 0; i < scoreService.findScore(1, student.getId()).size(); i++) {
                //添加对应数量的该科成绩
                Score score = new Score();
                score.setScore(0);
                score.setSubjectId(id);
                score.setStudentId(student.getId());
                scoreService.add(score);
            }
        }
    }

    //检查name数据是否正确,不正确返回对应的错误信息
    public String check(String name){
        //名称Pattern
        Pattern namePattern = Pattern.compile("^[\\u4E00-\\u9FA5A-Za-z0-9]+$");
        if (!namePattern.matcher(name).matches()){
            //如果名称格式不正确
            return "您输入的科目名格式有误或未输入";
        }
        return "ok";
    }

    //修改科目
    public void update(Subject subject) {
        new SubjectDao().update(subject);
    }

    //删除科目
    public void delete(int id) {
        //删除该科目的成绩
        ScoreDao scoreDao = new ScoreDao();
        scoreDao.deleteSubjectScores(id);
        //删除科目
        new SubjectDao().delete(id);
    }

    //通过科目id获取科目名
    public String findSubjectNameById(int id) {
        return new SubjectDao().findSubjectNameById(id);
    }

    //通过科目名获取科目id
    public int findSubjectIdByName(String name) {
        return new SubjectDao().findSubjectIdByName(name);
    }

    //获取科目列表
    public List<Subject> getSubjectList() {
        return new SubjectDao().getSubjectList();
    }
}
