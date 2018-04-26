package com.nullptr.student.service;

import com.nullptr.student.dao.ScoreDao;
import com.nullptr.student.po.Score;

import java.util.List;

/**
 * ScoreServiceImpl 实现了接口
 * created by 梁文俊
 * date:2018-3-31
 */
public class ScoreServiceImpl implements ScoreService {

    //添加成绩
    public void add(Score score) {
        new ScoreDao().add(score);
    }

    //修改成绩
    public void update(Score score) {
        new ScoreDao().update(score);
    }

    //删除科目下的成绩
    public void deleteSubjectScores(int subject_id) {
        new ScoreDao().deleteSubjectScores(subject_id);
    }

    //删除学生的成绩
    public void deleteScoreByStudentId(int student_id) {
        new ScoreDao().deleteScoreByStudentId(student_id);
    }

    //通过索引删除学生成绩
    public void deleteScoreWithIndex(int studentId, int index) {
        List<Integer> idList = findScoreIdByIndex(studentId, index);
        for (int id : idList) {
            new ScoreDao().delete(id);
        }
    }

    //通过科目查找成绩
    public List<Integer> findScore(int subject_id, int student_id) {
        return new ScoreDao().findScore(subject_id, student_id);
    }

    //通过学生id查找成绩
    public List<Score> findScoreByStudentId(int student_id) {
        return new ScoreDao().findScoreByStudentId(student_id);
    }

    //通过索引获取成绩列表(用来用列表修改成绩)
    public List<Integer> findScoreIdByIndex(int studentId, int index) {
        return new ScoreDao().findScoreIdByIndex(studentId, index);
    }

    //通过id获取对应成绩
    public Score findScoreById(int id) {
        return new ScoreDao().findScoreById(id);
    }
}
