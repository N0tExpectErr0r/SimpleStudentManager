package com.nullptr.student.model;

import com.nullptr.student.factory.ServiceFactory;
import com.nullptr.student.po.TheClass;
import com.nullptr.student.service.*;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * 班级列表模型
 * created by 梁文俊
 * date:2018-04-07
 */
public class ClassTableModel extends AbstractTableModel {
    //保存班级列表的List
    private List<TheClass> classList;

    public void setClassList(List<TheClass> classList) {
        this.classList = classList;
        fireTableDataChanged();     //通知更新数据并重新绘制
    }

    @Override
    public int getRowCount() {
        return classList.size();
    }

    @Override
    public int getColumnCount() {
        return 4;   //四列，分别是 班级名称 所属年级 学生人数 班主任
    }


    @Override
    public String getColumnName(int column) {
        //获取表头名
        String[] columnNames = {"班级名称", "所属年级", "学生人数", "班主任"};
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        TheClass theClass = classList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                //班级名称
                return theClass.getName();
            case 1:
                //所属年级
                GradeService gradeService = ServiceFactory.getInstance().createService(GradeService.class);
                return gradeService.findGradeById(theClass.getGradeId()).getName();
            case 2:
                //班级人数
                StudentService studentService = ServiceFactory.getInstance().createService(StudentService.class);
                return String.valueOf(studentService.findListByClassId(theClass.getId()).size());
            case 3:
                //班主任
                TeacherService teacherService = ServiceFactory.getInstance().createService(TeacherService.class);
                return teacherService.findTeacherById(theClass.getTeacherId()).getName();
            default:
                return "";
        }
    }
}
