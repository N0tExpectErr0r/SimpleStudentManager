package com.nullptr.student.view;

import com.nullptr.student.AppConstants;
import com.nullptr.student.factory.ServiceFactory;
import com.nullptr.student.model.StudentTableModel;
import com.nullptr.student.po.Teacher;
import com.nullptr.student.po.TheClass;
import com.nullptr.student.po.Student;
import com.nullptr.student.service.*;
import com.nullptr.student.utils.LogUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * 学生信息查看界面
 * created by 梁文俊
 * date:2018-4-01
 */
public class StudentManageView extends BaseView {
    private JTextField keyWordEdit;
    private JTable table;
    private JComboBox keyWordBox;
    private StudentTableModel model;

    public StudentManageView() {
        //调用父类方法初始化界面
        init("学生管理界面", 800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //关闭则关闭整个软件

        //获取权限
        LoginService loginService = ServiceFactory.getInstance().createService(LoginService.class);
        int permission = loginService.getPermission();

        //初始化菜单栏
        initMenuBar();

        //标题文字
        JLabel titleText = new JLabel("广东供液大学");
        titleText.setFont(new Font("微软雅黑", Font.PLAIN, 24));
        titleText.setBounds(20, 10, 150, 30);
        add(titleText);

        //显示当前登陆名称标签
        JLabel statusText = new JLabel();
        statusText.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        statusText.setBounds(170, 10, 200, 30);
        String name = loginService.getCurrentUserName();

        statusText.setText("欢迎，"+name+"。您的身份："+loginService.getIdentity());
        add(statusText);

        //关键词标签
        JLabel keyWordText = new JLabel("关键词：");
        keyWordText.setBounds(375, 10, 60, 30);

        //关键词选择框
        keyWordBox = new JComboBox();
        keyWordBox.setBounds(425, 10, 80, 30);
        String[] keywords = {"学号", "姓名", "性别", "电话", "邮箱", "班级"};
        //添加关键词选择
        for (String keyword : keywords) {
            keyWordBox.addItem(keyword);
        }


        //关键词编辑框
        keyWordEdit = new JTextField(15);
        keyWordEdit.setBounds(515, 10, 150, 30);
        //按下回车时，执行搜索
        keyWordEdit.addActionListener(new SearchStudentListener());


        //关键词检索键
        JButton searchButton = new JButton("搜索学生");
        searchButton.setBounds(675, 10, 100, 30);
        searchButton.addActionListener(new SearchStudentListener());    //太长了，写成内部类


        if (permission >= AppConstants.TEACHER_GRADE_PERMISSION || permission==AppConstants.TEACHER_SUBJECT_PERMISSION) {
            //班主任和学生看到的数目太少，不用搜索功能
            add(keyWordText);
            add(keyWordBox);
            add(keyWordEdit);
            add(searchButton);
        }

        //表格
        //初始化表格数据
        List<Student> studentList = new ArrayList<>();
        if (permission >= AppConstants.TEACHER_GRADE_PERMISSION || permission  == AppConstants.TEACHER_SUBJECT_PERMISSION){
            //如果权限大于级长或等于科任老师，能看到所有学生
            StudentService studentService = ServiceFactory.getInstance().createService(StudentService.class);
            studentList = studentService.getStudentList();
        }else if (permission == AppConstants.TEACHER_CLASS_PERMISSION){
            //如果是班主任,先获取他教授的班级
            ClassService classService = ServiceFactory.getInstance().createService(ClassService.class);
            StudentService studentService = ServiceFactory.getInstance().createService(StudentService.class);
            List<TheClass> classList = classService.findClassByTeacherId(Integer.valueOf(loginService.getUsername()));
            studentList = new ArrayList<>();
            for (TheClass theClass:classList){
                List<Student> tempList = studentService.findListByClassId(theClass.getId());
                studentList.addAll(tempList);
            }
            //设置标题标签文字
            titleText.setText("您教授的班级");
        }else if (permission == AppConstants.STUDENT_PERMISSION){
            //如果是学生，显示他们班级的所有学生
            StudentService studentService = ServiceFactory.getInstance().createService(StudentService.class);
            int studentId = Integer.valueOf(loginService.getUsername());
            Student student = studentService.findStudentById(studentId);
            studentList = studentService.findListByClassId(student.getClassId());

            //设置标题标签文字为班级名
            ClassService classService = ServiceFactory.getInstance().createService(ClassService.class);
            TheClass theClass = classService.findClassById(student.getClassId());
            titleText.setText(theClass.getName());
        }

        //用数据初始化表格，并设置不能更改数据
        table = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setSize(760, 380);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        model = new StudentTableModel();
        model.setStudents(studentList);
        table.setModel(model);

        //将table加入ScrollPane中，这样才可以见到表头
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(15, 50, 760, 380);
        add(scrollPane);

        //设置该View可见
        setVisible(true);
    }

    //初始化菜单栏
    private void initMenuBar() {
        LoginService loginService = ServiceFactory.getInstance().createService(LoginService.class);
        int permission = loginService.getPermission();
        //菜单栏
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        //编辑菜单
        JMenu editMenu = new JMenu("编辑");
        if (permission != AppConstants.TEACHER_SUBJECT_PERMISSION) {
            //如果是科任老师，不能修改学生信息
            menuBar.add(editMenu);
        }

        //添加学生菜单项
        JMenuItem addItem = new JMenuItem("添加学生");
        addItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //点击添加学生，跳转添加学生页面
                AddStudentView addStudentView = new AddStudentView();   //打开添加学生界面
                dispose();  //关闭自身
            }
        });
        if (permission >= AppConstants.TEACHER_GRADE_PERMISSION) {
            //如果权限是级长以上,显示这条菜单
            editMenu.add(addItem);
        }

        //编辑学生菜单项
        JMenuItem editItem = new JMenuItem("编辑信息");
        editItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //点击编辑学生，跳转编辑学生页面
                if (table.getSelectedRow() != -1) {
                    //如果当前有选中
                    //获取当前选中id
                    int rowNum = table.getSelectedRow();
                    //获取当前选中(要更改的)学生id
                    int editId = Integer.valueOf(table.getValueAt(rowNum, 0).toString());
                    if (permission == AppConstants.STUDENT_PERMISSION){
                        //如果是学生
                        //如果编辑的是自己
                        if (editId == Integer.valueOf(loginService.getUsername())){
                            EditStudentView editStudentView = new EditStudentView(editId);   //打开编辑学生界面
                            dispose();  //关闭
                        }else{
                            //没有权限编辑其他人的信息
                            JOptionPane.showMessageDialog(null, "不能修改其他人的信息", "提示", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }else {
                        EditStudentView editStudentView = new EditStudentView(editId);   //打开编辑学生界面
                        dispose();  //关闭
                    }
                } else {
                    //没有选中学生，弹出提示
                    JOptionPane.showMessageDialog(null, "您还没有选中学生", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        editMenu.add(editItem);

        //开除学生菜单项
        JMenuItem deleteItem = new JMenuItem("开除学生");
        deleteItem.addActionListener(new deleteStudentListener());  //太长了，写成内部类
        if (permission >= AppConstants.TEACHER_GRADE_PERMISSION) {
            //如果权限是级长以上,显示这条菜单
            editMenu.add(deleteItem);
        }

        //查询菜单
        JMenu queryMenu = new JMenu("查询");
        menuBar.add(queryMenu);

        //查询菜单项
        JMenuItem queryScoreItem = new JMenuItem("查询成绩");
        queryScoreItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //按下查询该生成绩
                if (table.getSelectedRow() != -1) {
                    //如果当前有选中
                    //获取当前选中id
                    int rowNum = table.getSelectedRow();
                    //获取当前选中(要查询的)学生id
                    int queryId = Integer.valueOf(table.getValueAt(rowNum, 0).toString());
                    if (permission == AppConstants.STUDENT_PERMISSION) {
                        //如果是学生
                        if (queryId == Integer.valueOf(loginService.getUsername())) {
                            //如果查询的是自己
                            ScoreView scoreView = new ScoreView(queryId);
                        } else {
                            //没有权限查询其他人成绩
                            JOptionPane.showMessageDialog(null, "无法查询其他人的成绩", "提示", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }else{
                        //不是学生直接打开
                        ScoreView scoreView = new ScoreView(queryId);
                    }
                } else {
                    //没有选中学生，弹出提示
                    JOptionPane.showMessageDialog(null, "您还没有选中学生", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        queryMenu.add(queryScoreItem);

        //学校管理菜单
        JMenu manageMenu = new JMenu("管理");
        if (permission >= AppConstants.TEACHER_SUBJECT_PERMISSION) {
            //如果是科任老师以上,显示
            menuBar.add(manageMenu);
        }

        //查看老师列表菜单项
        JMenuItem queryTeacherItem = new JMenuItem("查看老师列表");
        queryTeacherItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //打开老师列表
                TeacherView teacherView = new TeacherView();
            }
        });
        manageMenu.add(queryTeacherItem);

        //管理学习科目菜单项
        JMenuItem editSubjectItem = new JMenuItem("管理学习科目");
        editSubjectItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //按下编辑科目后
                SubjectView subjectView = new SubjectView();
            }
        });
        if (permission >= AppConstants.TEACHER_CLASS_PERMISSION){
            //如果是班主任以上，显示
            manageMenu.add(editSubjectItem);
        }

        //管理班级菜单项
        JMenuItem editClassItem = new JMenuItem("管理班级");
        editClassItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //按下管理班级
                LoginService loginService = ServiceFactory.getInstance().createService(LoginService.class);
                ClassView classView = new ClassView();
            }
        });
        if (permission >= AppConstants.TEACHER_GRADE_PERMISSION) {
            //如果是级长以上,显示
            manageMenu.add(editClassItem);
        }

        //管理年级菜单项
        JMenuItem editGradeItem = new JMenuItem("管理年级");
        editGradeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //按下管理年级
                GradeView gradeView = new GradeView();
            }
        });
        if (permission >= AppConstants.PRINCIPAL_PERMISSION) {
            //如果是校长以上
            manageMenu.add(editGradeItem);
        }

        //选项菜单
        JMenu optionMenu = new JMenu("选项");
        menuBar.add(optionMenu);

        //修改密码菜单项
        JMenuItem changePasswordItem = new JMenuItem("修改密码");
        changePasswordItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //打开修改密码菜单
                ChangePasswordView changePasswordView = new ChangePasswordView();
            }
        });
        optionMenu.add(changePasswordItem);

        //注销登录菜单项
        JMenuItem logoutItem = new JMenuItem("注销登陆");
        logoutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginService loginService = ServiceFactory.getInstance().createService(LoginService.class);
                //按下注销登录后
                loginService.logout();
                LoginView loginView = new LoginView();  //打开登陆界面
                dispose();  //关闭自己
            }
        });
        optionMenu.add(logoutItem);

        //查看日志菜单
        JMenu logMenu = new JMenu("查看日志");
        if (permission >= AppConstants.TEACHER_GRADE_PERMISSION) {
            //级长以上才能查看日志
            menuBar.add(logMenu);
        }

        JMenuItem logItem = new JMenuItem("查看敏感操作日志");
        logItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //打开查看日志界面
                LogView logView = new LogView();
            }
        });
        logMenu.add(logItem);
    }

    //单击搜索的回调
    private class SearchStudentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //按下搜索后
            StudentService studentService = ServiceFactory.getInstance().createService(StudentService.class);
            String keyword = keyWordEdit.getText();
            List<Student> newStudentList;
            switch (keyWordBox.getSelectedIndex()) {
                case 0:
                    //学号
                    newStudentList = studentService.findListWithKeyword("id", keyword);
                    model.setStudents(newStudentList);
                    break;
                case 1:
                    //姓名
                    newStudentList = studentService.findListWithKeyword("name", keyword);
                    model.setStudents(newStudentList);
                    break;
                case 2:
                    //性别
                    newStudentList = studentService.findListWithKeyword("sex", keyword);
                    model.setStudents(newStudentList);
                    break;
                case 3:
                    //电话
                    newStudentList = studentService.findListWithKeyword("phone", keyword);
                    model.setStudents(newStudentList);
                    break;
                case 4:
                    //邮箱
                    newStudentList = studentService.findListWithKeyword("email", keyword);
                    model.setStudents(newStudentList);
                    break;
                case 5:
                    //班级
                    ClassService classService = ServiceFactory.getInstance().createService(ClassService.class);
                    List<Student> students = new ArrayList<>();
                    //通过关键字搜索班级列表
                    List<TheClass> classList = classService.findListWithKeyword("name", keyword);
                    for (TheClass theclass : classList) {
                        //遍历班级列表。获取学生列表
                        int id = theclass.getId();
                        List<Student> currentList = studentService.findListWithKeyword("class_id", String.valueOf(id));
                        students.addAll(currentList);   //把符合的学生列表加入结果列表中
                    }
                    model.setStudents(students);
                    break;
            }
        }
    }

    //按下开除学生回调
    private class deleteStudentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //判断是否有选中
            if (table.getSelectedRow() != -1) {
                //如果当前有选中,弹出是否要开除这个学生
                Object[] options = {"确定", "取消"};
                int response = JOptionPane.showOptionDialog(null, "是否要开除你选中的学生?", "是否删除",
                        JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (response == 0) {
                    //如果确认要开除
                    //删除选中学生
                    int rowNum = table.getSelectedRow();
                    //获取当前选中(要删除的)学生id
                    int deleteId = Integer.valueOf(table.getValueAt(rowNum, 0).toString());
                    StudentService studentService = ServiceFactory.getInstance().createService(StudentService.class);
                    studentService.delete(deleteId);    //删除选中学生
                    //记录开除学生日志
                    LoginService loginService = ServiceFactory.getInstance().createService(LoginService.class);
                    LogUtils.getInstance().log(loginService.getIdentity()+"<"+loginService.getCurrentUserName()+">开除了学生<" + table.getValueAt(rowNum, 1)+">");
                    //刷新表格数据
                    model.setStudents(studentService.getStudentList());
                }
            } else {
                //没有选中学生，弹出提示
                JOptionPane.showMessageDialog(null, "您还没有选中学生", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
