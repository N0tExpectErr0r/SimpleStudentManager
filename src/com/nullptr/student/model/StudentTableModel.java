package com.nullptr.student.model;

import com.nullptr.student.factory.ServiceFactory;
import com.nullptr.student.po.TheClass;
import com.nullptr.student.po.Student;
import com.nullptr.student.service.ClassService;
import com.nullptr.student.service.ClassServiceImpl;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * 学生信息表模型
 * created by 梁文俊
 * date:2018-04-01
 */
public class StudentTableModel extends AbstractTableModel {
    // 保存一个Student的列表
    private List<Student> studentList;

    // 设置Student列表, 同时通知JTabel数据对象更改, 重绘界面
    public void setStudents(List<Student> studentList) {
        this.studentList = studentList;
        this.fireTableDataChanged();// 同时通知JTabel数据对象更改, 重绘界面
    }

    //列数
    public int getColumnCount() {
        return 6;
    }

    //行数
    public int getRowCount() {
        return studentList.size();
    }

    //列名
    public String getColumnName(int column) {
        String[] columnNames = {"学号", "姓名", "班级", "性别", "电话", "邮箱"};
        return columnNames[column];
    }

    // 从list中拿出rowIndex行columnIndex列显示的值
    public Object getValueAt(int rowIndex, int columnIndex) {
        Student student = studentList.get(rowIndex);
        switch (columnIndex) {
            //因为直接return了，所以不break了
            case 0:
                //学号
                return String.valueOf(student.getId());
            case 1:
                //姓名
                return student.getName();
            case 2:
                //班级
                ClassService classService = ServiceFactory.getInstance().createService(ClassService.class);
                TheClass theClass = classService.findClassById(student.getClassId());
                return theClass.getName();
            case 3:
                //性别
                return student.getSex();
            case 4:
                //号码
                if (!student.getPhone().equals(""))
                    return student.getPhone();
                else return "无";
            case 5:
                //邮箱
                if (!student.getEmail().equals(""))
                    return student.getEmail();
                else return "无";
            default:
                return "";
        }
    }
}
