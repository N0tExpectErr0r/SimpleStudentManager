package com.nullptr.student.model;

import com.nullptr.student.factory.ServiceFactory;
import com.nullptr.student.po.TheClass;
import com.nullptr.student.po.Grade;
import com.nullptr.student.service.*;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * 年级列表模型
 * created by 梁文俊
 * date:2018-04-08
 */
public class GradeTableModel extends AbstractTableModel {
    private List<Grade> gradeList;

    public void setGradeList(List<Grade> gradeList) {
        this.gradeList = gradeList;
        fireTableDataChanged();     //通知更新数据并刷新
    }

    @Override
    public int getRowCount() {
        return gradeList.size();
    }

    @Override
    public int getColumnCount() {
        return 4;   //四列，分别是 年级名 班级数 学生数 级长
    }

    @Override
    public String getColumnName(int column) {
        //获取表头名
        String[] columnNames = {"年级", "班级数量", "学生数量", "级长"};
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ClassService classService = ServiceFactory.getInstance().createService(ClassService.class);
        StudentService studentService = ServiceFactory.getInstance().createService(StudentService.class);
        TeacherService teacherService = ServiceFactory.getInstance().createService(TeacherService.class);

        Grade grade = gradeList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                //年级名
                return grade.getName();
            case 1:
                //班级数量
                return String.valueOf(classService.findListByGradeId(grade.getId()).size());
            case 2:
                //学生数量
                List<TheClass> classList = classService.findListByGradeId(grade.getId());
                int studentNum = 0;
                for (TheClass theClass : classList) {
                    int tempNum = studentService.findListByClassId(theClass.getId()).size();
                    studentNum += tempNum;
                }
                return String.valueOf(studentNum);
            case 3:
                //级长名
                return teacherService.findTeacherById(grade.getTeacherId()).getName();
            default:
                return "";
        }
    }
}
