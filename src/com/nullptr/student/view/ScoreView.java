package com.nullptr.student.view;

import com.nullptr.student.AppConstants;
import com.nullptr.student.factory.ServiceFactory;
import com.nullptr.student.model.StudentScoreModel;
import com.nullptr.student.po.Student;
import com.nullptr.student.service.*;
import com.nullptr.student.utils.LogUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * 单人成绩查询界面
 * created by 梁文俊
 * date:2018-04-02
 */
public class ScoreView extends BaseView {
    private JLabel nameText;
    private JLabel classText;
    private StudentScoreModel model;
    private JTable table;

    public ScoreView(int id) {
        //调用父类方法初始化界面
        init("", 600, 480);  //标题由初始化时设定

        //初始化菜单栏
        initMenu(id);

        //姓名标签
        nameText = new JLabel();
        nameText.setBounds(20, 10, 300, 30);
        nameText.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        add(nameText);
        //班级标签
        classText = new JLabel();
        classText.setBounds(430, 10, 150, 30);
        classText.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        add(classText);

        //学生成绩表格
        //设置表格不可修改
        table = new JTable() {
            @Override
            public boolean isEditing() {
                return false;
            }
        };
        model = new StudentScoreModel();
        model.setStudentId(id);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);   //设置只能单选
        table.setModel(model);
        table.setSize(560, 370);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 50, 560, 370);
        add(scrollPane);

        //初始化初始数据(学生数据)
        initData(id);

        setVisible(true);
    }

    //初始化菜单栏
    private void initMenu(int id) {
        //获取权限
        LoginService loginService = ServiceFactory.getInstance().createService(LoginService.class);
        int permission = loginService.getPermission();

        //菜单栏
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);


        //管理成绩菜单
        JMenu menu = new JMenu("管理成绩");
        if (permission >= AppConstants.TEACHER_SUBJECT_PERMISSION) {
            //如果权限大于授课老师，显示管理成绩菜单
            menuBar.add(menu);
        }

        //添加成绩菜单项
        JMenuItem addItem = new JMenuItem("添加成绩");
        addItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //按下添加成绩
                //获取权限
                LoginService loginService = ServiceFactory.getInstance().createService(LoginService.class);
                AddScoreView editScoreView = new AddScoreView(id, 0); //打开添加学生界面
                dispose();  //关闭自己

            }
        });
        menu.add(addItem);
        //修改成绩菜单项
        JMenuItem editItem = new JMenuItem("修改成绩");
        editItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //按下编辑成绩
                //获取权限
                LoginService loginService = ServiceFactory.getInstance().createService(LoginService.class);
                //获取当前选中的成绩列表
                if (table.getSelectedRow() != -1) {
                    //如果有选择
                    ScoreService scoreService = ServiceFactory.getInstance().createService(ScoreService.class);
                    int index = table.getSelectedRow() + 1;
                    List<Integer> scoreIdList = scoreService.findScoreIdByIndex(id, index);
                    //打开EditScoreView
                    EditScoreView editStudentView = new EditScoreView(scoreIdList, id, 0);
                    dispose();
                } else {
                    //没有选中成绩，弹出提示
                    JOptionPane.showMessageDialog(null, "您还没有选中成绩", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        menu.add(editItem);
        //删除成绩菜单项
        JMenuItem deleteItem = new JMenuItem("删除成绩");
        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //按下删除成绩
                //获取权限
                LoginService loginService = ServiceFactory.getInstance().createService(LoginService.class);
                if (table.getSelectedRow() != -1) {
                    //如果当前有选中,弹出是否要开除这个学生
                    Object[] options = {"确定", "取消"};
                    int response = JOptionPane.showOptionDialog(null, "是否要删除这条成绩?", "是否删除",
                            JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if (response == 0) {
                        //如果选择确定
                        ScoreService scoreService = ServiceFactory.getInstance().createService(ScoreService.class);
                        int index = table.getSelectedRow() + 1;
                        scoreService.deleteScoreWithIndex(id, index);
                        //记录删除成绩日志
                        StudentService studentService = ServiceFactory.getInstance().createService(StudentService.class);
                        LogUtils.getInstance().log(loginService.getIdentity()+"<"+loginService.getCurrentUserName()+">删除了学生<" + studentService.findStudentById(id).getName() + ">的一条成绩");
                        //刷新列表
                        model.setStudentId(id);
                    }
                } else {
                    //没有选中成绩，弹出提示
                    JOptionPane.showMessageDialog(null, "您还没有选中成绩", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        menu.add(deleteItem);
    }

    //初始化数据
    private void initData(int id) {
        StudentService studentService = ServiceFactory.getInstance().createService(StudentService.class);
        ClassService classService = ServiceFactory.getInstance().createService(ClassService.class);

        Student student = studentService.findStudentById(id);
        String studentName = student.getName();     //获取学生名
        String className = classService.findClassById(student.getClassId()).getName();  //获取班级名

        setTitle("正在查看来自" + className + "的" + studentName + "同学的成绩");
        nameText.setText("姓名：" + studentName);
        classText.setText("来自班级：" + className);
    }
}
