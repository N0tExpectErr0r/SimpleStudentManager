package com.nullptr.student.view;

import com.nullptr.student.AppConstants;
import com.nullptr.student.factory.ServiceFactory;
import com.nullptr.student.model.ClassTableModel;
import com.nullptr.student.po.TheClass;
import com.nullptr.student.service.*;
import com.nullptr.student.utils.LogUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 班级列表界面
 * created by 梁文俊
 * date:2018-04-07
 */
public class ClassView extends BaseView {
    private JTable table;
    private ClassTableModel model;

    public ClassView() {
        //调用父类方法初始化界面
        init("班级列表", 700, 550);

        //初始化菜单栏
        initMenu();

        //班级列表,设置不能修改
        table = new JTable() {
            @Override
            public boolean isEditing() {
                return false;
            }
        };
        ClassService classService = ServiceFactory.getInstance().createService(ClassService.class);
        model = new ClassTableModel();
        List<TheClass> classList = classService.getClassList();
        model.setClassList(classList);
        table.setModel(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(25); //设置每一列高度为25
        table.setSize(650, 470);

        //班级列表容器
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 670, 470);
        add(scrollPane);

        setVisible(true);   //设置可见
    }

    //初始化菜单栏
    public void initMenu() {
        //菜单栏
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        //管理班级菜单
        JMenu menu = new JMenu("管理班级");
        menuBar.add(menu);

        //添加班级菜单项
        JMenuItem addItem = new JMenuItem("添加班级");
        addItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //打开添加班级页面
                AddClassView addClassView = new AddClassView();
                dispose();
            }
        });
        menu.add(addItem);

        //编辑班级
        JMenuItem editItem = new JMenuItem("修改班级");
        editItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //打开编辑班级页面
                if (table.getSelectedRow() != -1) {
                    //如果有选择
                    //获取当前选中的班级id
                    ClassService classService = ServiceFactory.getInstance().createService(ClassService.class);
                    int index = table.getSelectedRow();
                    TheClass theClass = classService.getClassList().get(index);
                    //通过班级id打开编辑班级页面
                    EditClassView editClassView = new EditClassView(theClass.getId());
                    dispose();
                } else {
                    //没有选中班级，弹出提示
                    JOptionPane.showMessageDialog(null, "您还没有选中班级", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        menu.add(editItem);

        //删除班级
        JMenuItem deleteItem = new JMenuItem("删除班级");
        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedRow() != -1) {
                    //如果有选择,弹出是否要删除这个班级
                    Object[] options = {"确定", "取消"};
                    int response = JOptionPane.showOptionDialog(null, "是否要删除这个班级?", "是否删除",
                            JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if (response == 0) {
                        //如果点击了确定
                        //获取当前选中的班级id
                        ClassService classService = ServiceFactory.getInstance().createService(ClassService.class);
                        UserService userService = ServiceFactory.getInstance().createService(UserService.class);
                        int index = table.getSelectedRow();
                        TheClass theClass = classService.getClassList().get(index);
                        //记录删除班级日志
                        LoginService loginService = ServiceFactory.getInstance().createService(LoginService.class);
                        LogUtils.getInstance().log(loginService.getIdentity()+"<"+loginService.getCurrentUserName()+">删除了班级<" + theClass.getName()+">");
                        //将班级老师权限设置为科任老师
                        userService.changePermission(String.valueOf(theClass.getTeacherId()), AppConstants.TEACHER_SUBJECT_PERMISSION);
                        //删除班级
                        classService.delete(theClass.getId());
                        //更新画面
                        model.setClassList(classService.getClassList());
                    }
                } else {
                    //没有选中班级，弹出提示
                    JOptionPane.showMessageDialog(null, "您还没有选中班级", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        menu.add(deleteItem);
    }
}
