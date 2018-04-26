package com.nullptr.student.model;

import com.nullptr.student.po.Subject;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * 科目列表模型
 * created by 梁文俊
 * date:2018-04-05
 */
public class SubjectTableModel extends AbstractTableModel {
    List<Subject> subjectList;

    public void setSubjects(List<Subject> subjects) {
        subjectList = subjects;
        fireTableDataChanged();
    }

    @Override
    public String getColumnName(int column) {
        return "科目列表";
    }

    @Override
    public int getRowCount() {
        return subjectList.size();
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return subjectList.get(rowIndex).getName();
    }
}
