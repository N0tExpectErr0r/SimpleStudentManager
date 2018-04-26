package com.nullptr.student.view;

import com.nullptr.student.AppConstants;
import com.nullptr.student.factory.ServiceFactory;
import com.nullptr.student.model.GradeTableModel;
import com.nullptr.student.po.Grade;
import com.nullptr.student.service.*;
import com.nullptr.student.utils.LogUtils;
import sun.rmi.runtime.Log;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GradeView extends BaseView {
    private JTable table;
    private GradeTableModel model;

    public GradeView() {
        //通过父类方法初始化界面
        init("年级列表", 750, 530);

        initMenu(); //初始化菜单栏

        //年级表
        table = new JTable() {
            @Override
            public boolean isEditing() {
                return false;
            }
        };  //设置不可修改
        GradeService gradeService = ServiceFactory.getInstance().createService(GradeService.class);
        model = new GradeTableModel();
        model.setGradeList(gradeService.getGradeList());
        table.setModel(model);
        table.setRowHeight(30);     //每行高度40(看起来大气点)
        table.setSize(720, 450);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 720, 450);
        add(scrollPane);

        setVisible(true);
    }

    public void initMenu() {
        //菜单栏
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        //管理年级菜单
        JMenu menu = new JMenu("管理年级");
        menuBar.add(menu);
        //添加年级菜单项
        JMenuItem addItem = new JMenuItem("添加年级");
        addItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //打开添加年级页面
                AddGradeView addGradeView = new AddGradeView();
                dispose();
            }
        });
        menu.add(addItem);

        //编辑年级菜单项
        JMenuItem editItem = new JMenuItem("编辑年级");
        editItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //打开编辑年级界面
                if (table.getSelectedRow() != -1) {
                    //如果有选择
                    //获取当前选中的年级id
                    GradeService gradeService = ServiceFactory.getInstance().createService(GradeService.class);
                    int index = table.getSelectedRow();
                    Grade grade = gradeService.getGradeList().get(index);
                    //通过年级id打开编辑界面
                    EditGradeView editGradeView = new EditGradeView(grade.getId());
                    dispose();
                } else {
                    //没有选中年级，弹出提示
                    JOptionPane.showMessageDialog(null, "您还没有选中年级", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        menu.add(editItem);

        //删除年级菜单项
        JMenuItem deleteItem = new JMenuItem("删除年级");
        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //按下删除年级
                if (table.getSelectedRow() != -1) {
                    //如果有选择,弹出是否要删除这个年级
                    Object[] options = {"确定", "取消"};
                    int response = JOptionPane.showOptionDialog(null, "是否要删除这个年级?", "是否删除",
                            JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if (response == 0) {
                        //如果按下确定。删除
                        //获取当前选中的年级id
                        GradeService gradeService = ServiceFactory.getInstance().createService(GradeService.class);
                        UserService userService = ServiceFactory.getInstance().createService(UserService.class);
                        int index = table.getSelectedRow();
                        Grade grade = gradeService.getGradeList().get(index);
                        //将年级老师权限设置为科任老师
                        userService.changePermission(String.valueOf(grade.getTeacherId()), AppConstants.TEACHER_SUBJECT_PERMISSION);
                        //记录删除班级日志
                        LoginService loginService = ServiceFactory.getInstance().createService(LoginService.class);
                        LogUtils.getInstance().log(loginService.getIdentity()+"<"+loginService.getCurrentUserName()+">删除了年级<" + grade.getName()+">");
                        //删除年级
                        gradeService.delete(grade.getId());
                        //更新列表
                        model.setGradeList(gradeService.getGradeList());
                    }
                } else {
                    //没有选中班级，弹出提示
                    JOptionPane.showMessageDialog(null, "您还没有选中年级", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        menu.add(deleteItem);
    }
}
