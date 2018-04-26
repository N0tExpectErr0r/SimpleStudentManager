package com.nullptr.student.view;

import com.nullptr.student.factory.ServiceFactory;
import com.nullptr.student.model.SubjectTableModel;
import com.nullptr.student.service.LoginService;
import com.nullptr.student.service.LoginServiceImpl;
import com.nullptr.student.service.SubjectService;
import com.nullptr.student.service.SubjectServiceImpl;
import com.nullptr.student.utils.LogUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 科目列表界面
 * created by 梁文俊
 * date:2018-04-05
 */
public class SubjectView extends BaseView {
    private JTable list;
    private SubjectTableModel model;

    public SubjectView() {
        //调用父类方法初始化界面
        init("管理科目", 250, 360);

        initMenu(); //初始化菜单栏

        //科目列表
        list = new JTable();
        list.setSize(220, 280);
        SubjectService subjectService = ServiceFactory.getInstance().createService(SubjectService.class);
        model = new SubjectTableModel();
        model.setSubjects(subjectService.getSubjectList());
        list.setModel(model);

        //包含列表的容器
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(0, 0, 245, 300);
        add(scrollPane);

        //设置该view可见
        setVisible(true);
    }

    //初始化菜单栏
    public void initMenu() {
        //菜单栏
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        //管理菜单
        JMenu menu = new JMenu("管理科目");
        menuBar.add(menu);

        //添加科目菜单项
        JMenuItem addItem = new JMenuItem("添加科目");
        addItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //按下添加科目后
                AddSubjectView addSubjectView = new AddSubjectView();
                dispose();
            }
        });
        menu.add(addItem);

        //编辑科目菜单项
        JMenuItem editItem = new JMenuItem("编辑科目名称");
        editItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //按下编辑科目后
                if (list.getSelectedRow() != -1) {
                    //如果有选中
                    SubjectService subjectService = ServiceFactory.getInstance().createService(SubjectService.class);
                    int index = list.getSelectedRow();
                    String subjectName = subjectService.getSubjectList().get(index).getName();
                    int id = subjectService.findSubjectIdByName(subjectName);
                    EditSubjectView editSubjectView = new EditSubjectView(id);
                    dispose();
                } else {
                    //没有选中科目，弹出提示
                    JOptionPane.showMessageDialog(null, "您还没有选中科目", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        menu.add(editItem);

        //删除科目菜单项
        JMenuItem deleteItem = new JMenuItem("删除科目");
        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //按下删除按钮后
                if (list.getSelectedRow() != -1) {
                    //如果有选中
                    Object[] options = {"确定", "取消"};
                    int response = JOptionPane.showOptionDialog(null, "是否要删除这门科目?", "是否删除",
                            JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if (response == 0) {
                        //如果按下确定
                        SubjectService subjectService = ServiceFactory.getInstance().createService(SubjectService.class);
                        int index = list.getSelectedRow();
                        String subjectName = subjectService.getSubjectList().get(index).getName();
                        int id = subjectService.findSubjectIdByName(subjectName);
                        //记录修改科目日志
                        LoginService loginService = ServiceFactory.getInstance().createService(LoginService.class);
                        LogUtils.getInstance().log(loginService.getIdentity()+"<"+loginService.getCurrentUserName()+">删除了科目<" + subjectService.findSubjectNameById(id)+">");
                        //删除科目
                        subjectService.delete(id);
                        model.setSubjects(subjectService.getSubjectList());
                    }
                } else {
                    //没有选中科目，弹出提示
                    JOptionPane.showMessageDialog(null, "您还没有选中科目", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        menu.add(deleteItem);
    }
}
