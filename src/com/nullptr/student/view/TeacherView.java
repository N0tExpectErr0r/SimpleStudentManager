package com.nullptr.student.view;

import com.nullptr.student.AppConstants;
import com.nullptr.student.factory.ServiceFactory;
import com.nullptr.student.model.TeacherTableModel;
import com.nullptr.student.service.LoginService;
import com.nullptr.student.service.LoginServiceImpl;
import com.nullptr.student.service.TeacherService;
import com.nullptr.student.service.TeacherServiceImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeacherView extends BaseView {
    private JTable table;
    private TeacherTableModel model;

    public TeacherView() {
        //用父类方法初始化
        init("老师列表", 600, 520);

        //老师列表
        table = new JTable();
        table.setSize(570, 450);
        TeacherService teacherService = ServiceFactory.getInstance().createService(TeacherService.class);
        model = new TeacherTableModel();
        model.setTeacherList(teacherService.getTeacherList());
        table.setModel(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);    //设置单选

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 570, 450);
        add(scrollPane);

        initMenu();

        setVisible(true);
    }

    private void initMenu() {
        //获取权限
        LoginService loginService = ServiceFactory.getInstance().createService(LoginService.class);
        int permission = loginService.getPermission();

        //菜单栏
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        //管理菜单
        JMenu menu = new JMenu("管理老师");
        if (permission >= AppConstants.PRINCIPAL_PERMISSION) {
            //只有校长能管理老师
            menuBar.add(menu);
        }

        //添加老师菜单项
        JMenuItem addItem = new JMenuItem("添加老师");
        addItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取权限
                LoginService loginService = ServiceFactory.getInstance().createService(LoginService.class);
                if (loginService.getPermission() >= AppConstants.PRINCIPAL_PERMISSION) {
                    //如果权限是校长以上
                    //打开添加老师界面
                    AddTeacherView addTeacherView = new AddTeacherView();
                    dispose();
                } else {
                    //弹出对话框，权限不足
                    JOptionPane.showMessageDialog(null, "执行此项的权限不足", "提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        menu.add(addItem);
    }
}
