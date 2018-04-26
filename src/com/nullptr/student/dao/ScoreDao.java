package com.nullptr.student.dao;

import com.nullptr.student.po.Score;
import com.nullptr.student.utils.DBUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ScoreDao
 * 用于和score表交互
 * created by 梁文俊
 * date:2018-3-31
 */
public class ScoreDao {
    private DBUtils dbUtils;

    //初始化DBUtils
    public ScoreDao() {
        dbUtils = DBUtils.getInstance();
    }

    /**
     * 添加成绩
     *
     * @param score:添加的成绩
     */
    public void add(Score score) {
        if (score == null) {
             throw new IllegalArgumentException("Score to add cannot be null");
        }
        String sql = "INSERT score(score,student_id,subject_id,index_num) VALUES(?,?,?,?);";
        List<Object> paramList = new ArrayList<>();

        //计算当前到第几条成绩
        int indexNum = findScore(score.getSubjectId(), score.getStudentId()).size() + 1;

        paramList.add(score.getScore());
        paramList.add(score.getStudentId());
        paramList.add(score.getSubjectId());
        paramList.add(indexNum);
        dbUtils.excute(sql, paramList);
    }

    /**
     * 更新成绩信息
     *
     * @param score:用于更新的成绩
     */
    public void update(Score score) {
        if (score == null || score.getId() == 0) {
            throw new IllegalArgumentException("Score to update cannot be null or id equals 0");
        }
        String sql = "UPDATE score SET score=?,student_id=?,subject_id=? WHERE id=?;";
        List<Object> paramList = new ArrayList<>();

        paramList.add(score.getScore());
        paramList.add(score.getStudentId());
        paramList.add(score.getSubjectId());
        paramList.add(score.getId());
        dbUtils.excute(sql, paramList);
    }

    public void delete(int scoreId) {
        String sql = "DELETE FROM score WHERE id=?;";
        List<Object> paramList = new ArrayList<>();
        paramList.add(scoreId);
        dbUtils.excute(sql, paramList);
    }

    /**
     * 删除科目全部成绩
     *
     * @param subject_id:删除成绩对应的科目id
     */
    public void deleteSubjectScores(int subject_id) {
        //删除成绩
        String sql = "DELETE FROM score WHERE subject_id=?;";
        List<Object> paramList = new ArrayList<>();

        paramList.add(subject_id);
        dbUtils.excute(sql, paramList);
    }

    /**
     * 根据学生id删除成绩信息
     *
     * @param student_id:删除的成绩对应学生的id
     */
    public void deleteScoreByStudentId(int student_id) {
        //删除成绩
        String sql = "DELETE FROM score WHERE student_id=?;";
        List<Object> paramList = new ArrayList<>();

        paramList.add(student_id);
        dbUtils.excute(sql, paramList);
    }


    /**
     * 通过科目id及学生id查找成绩
     *
     * @param subject_id:要查找的科目id
     * @param student_id:要查找的科目成绩对应的学生id
     * @return 找到的Score的分数
     */
    public List<Integer> findScore(int subject_id, int student_id) {
        String sql = "SELECT * FROM score WHERE subject_id=? AND student_id=?;";
        List<Object> paramList = new ArrayList<>();

        paramList.add(subject_id);
        paramList.add(student_id);
        List<Map<String, Object>> resultList = dbUtils.query(sql, paramList); //搜索结果List

        if (resultList == null || resultList.size() == 0) {
            //为空则返回空对象
            return new ArrayList<>();
        } else {
            //返回对应的成绩
            List<Integer> scoreList = new ArrayList<>();
            for (Map<String, Object> item : resultList) {
                scoreList.add(Integer.valueOf(item.get("score").toString()));
            }
            return scoreList;
        }
    }

    /**
     * 通过成绩id查找成绩
     *
     * @param id:该成绩的id
     * @return 找到的Score
     */
    public Score findScoreById(int id) {
        String sql = "SELECT * FROM score WHERE id=?;";
        List<Object> paramList = new ArrayList<>();
        paramList.add(id);
        List<Map<String, Object>> resultList = dbUtils.query(sql, paramList); //搜索结果List

        if (resultList == null || resultList.size() == 0) {
            //为空则返回空对象
            return null;
        } else {
            //返回对应的成绩
            Score returnScore = new Score();
            returnScore.setId(Integer.valueOf(resultList.get(0).get("id").toString()));
            returnScore.setScore(Integer.valueOf(resultList.get(0).get("score").toString()));
            returnScore.setStudentId(Integer.valueOf(resultList.get(0).get("student_id").toString()));
            returnScore.setSubjectId(Integer.valueOf(resultList.get(0).get("subject_id").toString()));
            returnScore.setIndexNum(Integer.valueOf(resultList.get(0).get("index_num").toString()));
            return returnScore;
        }
    }

    /**
     * 通过学生id查找成绩列表
     *
     * @param student_id:要查找的科目成绩对应的学生id
     * @return 找到的Score列表
     */
    public List<Score> findScoreByStudentId(int student_id) {
        String sql = "SELECT * FROM score WHERE student_id=?;";
        List<Object> paramList = new ArrayList<>();
        paramList.add(student_id);
        List<Map<String, Object>> resultList = dbUtils.query(sql, paramList); //搜索结果List

        if (resultList == null || resultList.size() == 0) {
            //为空则返回空列表
            return new ArrayList<>();
        } else {
            //返回对应的成绩
            List<Score> scoreList = new ArrayList<>();
            for (Map<String, Object> item : resultList) {
                Score score = new Score();
                score.setId(Integer.valueOf(item.get("id").toString()));
                score.setScore(Integer.valueOf(item.get("score").toString()));
                score.setSubjectId(Integer.valueOf(item.get("subject_id").toString()));
                score.setStudentId(student_id);
                score.setIndexNum(Integer.valueOf(resultList.get(0).get("index_num").toString()));
                scoreList.add(score);
            }
            return scoreList;
        }
    }

    /**
     * 通过index及学生id查找成绩id列表
     *
     * @param index:列表中的索引
     * @param student_id:要查找的科目成绩对应的学生id
     * @return 找到的id列表
     */
    public List<Integer> findScoreIdByIndex(int student_id, int index) {
        String sql = "SELECT * FROM score WHERE student_id=? AND index_num=?;";
        List<Object> paramList = new ArrayList<>();
        paramList.add(student_id);
        paramList.add(index);
        List<Map<String, Object>> resultList = dbUtils.query(sql, paramList); //搜索结果List

        if (resultList == null || resultList.size() == 0) {
            //为空则返回空列表
            return new ArrayList<>();
        } else {
            //返回对应的成绩
            List<Integer> returnList = new ArrayList<>();
            for (Map<String, Object> item : resultList) {
                returnList.add(Integer.valueOf(item.get("id").toString()));
            }
            return returnList;
        }

    }

}
