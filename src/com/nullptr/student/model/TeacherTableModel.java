package com.nullptr.student.model;

import com.nullptr.student.factory.ServiceFactory;
import com.nullptr.student.po.Teacher;
import com.nullptr.student.service.TeacherService;
import com.nullptr.student.service.TeacherServiceImpl;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * 老师列表模型
 * created by 梁文俊
 * date:2018-04-08
 */
public class TeacherTableModel extends AbstractTableModel {
    private List<Teacher> teacherList;

    public void setTeacherList(List<Teacher> teacherList) {
        this.teacherList = teacherList;
        fireTableDataChanged();     //强制更新列表信息
    }

    @Override
    public int getRowCount() {
        return teacherList.size();
    }

    @Override
    public int getColumnCount() {
        return 4;   //四列信息 教师编号 姓名 电话 邮箱
    }

    @Override
    public String getColumnName(int column) {
        //获取表头名
        String[] columnNames = {"教师编号","姓名", "电话", "邮箱"};
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        TeacherService teacherService = ServiceFactory.getInstance().createService(TeacherService.class);

        Teacher teacher = teacherList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                //教师编号
                return teacher.getId();
            case 1:
                //姓名
                return teacher.getName();
            case 2:
                //电话
                if (!teacher.getPhone().equals("")) return teacher.getPhone();
                else return "无";
            case 3:
                //邮箱
                if (!teacher.getEmail().equals("")) return teacher.getEmail();
                else return "无";
            default:
                return "";
        }
    }
}
