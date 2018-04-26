package com.nullptr.student.service;

import com.nullptr.student.po.Score;

import java.util.List;

/**
 * ScoreService接口
 * 用于操作Score
 * created by 梁文俊
 * date:2018-3-31
 */
public interface ScoreService {
    //添加成绩
    public void add(Score score);

    //修改成绩
    public void update(Score score);

    //删除科目下的成绩
    public void deleteSubjectScores(int subject_id);

    //删除学生的成绩
    public void deleteScoreByStudentId(int student_id);

    //通过索引删除学生成绩
    public void deleteScoreWithIndex(int studentId, int index);

    //通过科目查找成绩
    public List<Integer> findScore(int subject_id, int student_id);

    //通过学生id查找成绩
    public List<Score> findScoreByStudentId(int student_id);

    //通过索引获取成绩列表(用来用列表修改成绩)
    public List<Integer> findScoreIdByIndex(int studentId, int index);

    //通过id获取对应成绩
    public Score findScoreById(int id);

}
