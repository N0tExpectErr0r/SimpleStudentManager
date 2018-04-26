package com.nullptr.student.model;

import com.nullptr.student.factory.ServiceFactory;
import com.nullptr.student.po.Subject;
import com.nullptr.student.service.ScoreService;
import com.nullptr.student.service.ScoreServiceImpl;
import com.nullptr.student.service.SubjectService;
import com.nullptr.student.service.SubjectServiceImpl;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * 学生分数表模型
 * created by 梁文俊
 * date:2018-04-02
 */
public class StudentScoreModel extends AbstractTableModel {
    TableModelListener tableModelListener = new TableModelListener() {
        @Override
        public void tableChanged(TableModelEvent e) {

        }
    };

    private int studentId;

    // 设置StudentId, 同时通知JTabel数据对象更改, 重绘界面
    public void setStudentId(int id) {
        this.studentId = id;
        this.fireTableDataChanged();// 同时通知JTabel数据对象更改, 重绘界面
    }

    public int getColumnCount() {
        SubjectService subjectService = ServiceFactory.getInstance().createService(SubjectService.class);
        return subjectService.getSubjectList().size() + 1;    //多出一列显示平均分
    }

    public int getRowCount() {
        ScoreService scoreService = ServiceFactory.getInstance().createService(ScoreService.class);
        SubjectService subjectService = ServiceFactory.getInstance().createService(SubjectService.class);
        if (subjectService.getSubjectList().size() > 0)
            return scoreService.findScore(1, studentId).size();
        else return 0;
    }

    public String getColumnName(int column) {
        SubjectService subjectService = ServiceFactory.getInstance().createService(SubjectService.class);
        if (column < subjectService.getSubjectList().size())
            return subjectService.getSubjectList().get(column).getName();
        else
            return "平均分";
    }

    // 从list中拿出rowIndex行columnIndex列显示的值
    public Object getValueAt(int rowIndex, int columnIndex) {
        SubjectService subjectService = ServiceFactory.getInstance().createService(SubjectService.class);
        ScoreService scoreService = new ScoreServiceImpl();
        int n = subjectService.getSubjectList().size();
        if (columnIndex != n) {
            //不为平均分时，返会该科成绩
            int subjectId = subjectService.getSubjectList().get(columnIndex).getId();
            //一定有成绩，所以此处不判空
            return String.valueOf(scoreService.findScore(subjectId, studentId).get(rowIndex));
        } else {
            //计算平均分
            List<Subject> subjectList = subjectService.getSubjectList();
            int sumScore = 0;
            for (Subject subject : subjectList) {
                sumScore += scoreService.findScore(subject.getId(), studentId).get(rowIndex);
            }
            return sumScore / n;
        }
    }
}