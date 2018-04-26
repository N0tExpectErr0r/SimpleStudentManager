package com.nullptr.student.view;

import com.nullptr.student.AppConstants;
import com.nullptr.student.factory.ServiceFactory;
import com.nullptr.student.po.TheClass;
import com.nullptr.student.po.Student;
import com.nullptr.student.service.*;
import com.nullptr.student.utils.LogUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * 科目列表界面
 * created by 梁文俊
 * date:2018-4-05
 */
public class EditStudentView extends BaseView {
    private JTextField nameEdit;
    private JTextField phoneEdit;
    private JTextField emailEdit;
    private JComboBox classBox;
    private JComboBox sexBox;

    public EditStudentView(int id) {
        //调用父类方法初始化界面
        init("添加班级", 350, 400);

        //获取权限
        LoginService loginService = ServiceFactory.getInstance().createService(LoginService.class);
        int permission = loginService.getPermission();

        //标题文字
        JLabel titleText = new JLabel("编辑学生");
        titleText.setFont(new Font("微软雅黑", Font.PLAIN, 24));
        titleText.setBounds(120, 20, 120, 40);
        add(titleText);

        //姓名标签
        JLabel nameText = new JLabel("姓名*:");
        nameText.setBounds(20, 80, 50, 30);
        add(nameText);

        //姓名编辑框
        nameEdit = new JTextField(10);
        nameEdit.setBounds(70, 80, 140, 30);
        nameEdit.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                //设置只能输入中文或英文
                int keyChar = e.getKeyChar();
                if ((keyChar >= '\u4E00' && keyChar <= '\u9FA5') || (keyChar >= 'A' && keyChar <= 'z')) {
                    //如果是中文或英文
                } else {
                    //不是中文或英文
                    e.consume();
                }
            }
        });
        add(nameEdit);

        //性别标签
        JLabel sexText = new JLabel("性别*:");
        sexText.setBounds(20, 130, 50, 30);
        add(sexText);

        //性别选择框
        sexBox = new JComboBox();
        sexBox.setBounds(70, 130, 50, 30);
        sexBox.addItem("男");
        sexBox.addItem("女");
        add(sexBox);

        //班级标签
        JLabel classText = new JLabel("班级*:");
        classText.setBounds(130, 130, 50, 30);
        add(classText);

        //班级选择框
        classBox = new JComboBox();
        classBox.setBounds(180, 130, 100, 30);
        initClassBox();
        if (permission < AppConstants.TEACHER_GRADE_PERMISSION) {
            //如果权限不足，不能修改班级
            classBox.setEnabled(false);
        }
        add(classBox);

        //电话标签
        JLabel phoneText = new JLabel("电话:");
        phoneText.setBounds(20, 180, 50, 30);
        add(phoneText);

        //电话编辑框
        phoneEdit = new JTextField(10);
        phoneEdit.setBounds(70, 180, 180, 30);
        phoneEdit.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                //设置只能输入数字
                int keyChar = e.getKeyChar();
                if (keyChar < KeyEvent.VK_0 || keyChar > KeyEvent.VK_9) {
                    //如果不是数字，屏蔽输入
                    e.consume();
                }
                if (phoneEdit.getText().length() >= 11) {
                    //如果超过电话号码最大长度，屏蔽输入
                    e.consume();
                }
            }
        });
        add(phoneEdit);

        //邮箱标签
        JLabel emailText = new JLabel("邮箱:");
        emailText.setBounds(20, 230, 50, 30);
        add(emailText);

        //邮箱编辑框
        emailEdit = new JTextField(10);
        emailEdit.setBounds(70, 230, 200, 30);
        add(emailEdit);

        initData(id);

        //更改学生按钮
        JButton addButton = new JButton("修改信息");
        addButton.setBounds(70, 300, 100, 30);
        addButton.addActionListener(new EditStudentListener(id));
        add(addButton);

        //取消按钮
        JButton cancelButton = new JButton("取消");
        cancelButton.setBounds(180, 300, 100, 30);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //回到原来的界面
                StudentManageView studentManageView = new StudentManageView();
                dispose();
            }
        });
        add(cancelButton);

        setVisible(true);
    }

    //初始化班级选择数据
    private void initClassBox() {
        ClassService classService = ServiceFactory.getInstance().createService(ClassService.class);
        List<TheClass> classList = classService.getClassList();
        for (TheClass theClass : classList) {
            classBox.addItem(theClass.getName());
        }
    }

    //初始化数据
    private void initData(int id) {
        StudentService studentService = ServiceFactory.getInstance().createService(StudentService.class);
        ClassService classService = ServiceFactory.getInstance().createService(ClassService.class);
        Student student = studentService.findStudentById(id);
        nameEdit.setText(student.getName());
        sexBox.setSelectedItem(student.getSex());
        TheClass theClass = classService.findClassById(student.getClassId());
        classBox.setSelectedItem(theClass.getName());
        phoneEdit.setText(student.getPhone());
        emailEdit.setText(student.getEmail());
    }

    private class EditStudentListener implements ActionListener {
        //按下编辑学生回调
        private int id;

        public EditStudentListener(int id) {
            this.id = id;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            StudentService studentService = ServiceFactory.getInstance().createService(StudentService.class);
            //记录修改学生日志
            LoginService loginService = ServiceFactory.getInstance().createService(LoginService.class);
            LogUtils.getInstance().log(loginService.getIdentity()+"<"+loginService.getCurrentUserName()+">修改了学生<" + nameEdit.getText() + ">的个人信息");

            ClassService classService = ServiceFactory.getInstance().createService(ClassService.class);
            int classId = classService.findClassByName(classBox.getSelectedItem().toString()).getId();

            //修改学生
            Student student = new Student();
            student.setId(id);
            student.setName(nameEdit.getText());
            student.setSex((String) sexBox.getSelectedItem());
            student.setClassId(classId);   //班级id一定是与选中的索引+1对应(选择框列出了所有班级)
            student.setPhone(phoneEdit.getText());
            student.setEmail(emailEdit.getText());
            String msg = studentService.check(student);
            if (!msg.equals("ok")){
                //输入信息有误，弹出输出信息对话框
                JOptionPane.showMessageDialog(null, msg, "提示", JOptionPane.PLAIN_MESSAGE);
            }

            studentService.update(student);//更新学生信息

            JOptionPane.showMessageDialog(null, "成功编辑学生！", "提示", JOptionPane.PLAIN_MESSAGE);
            StudentManageView studentManageView = new StudentManageView();  //添加完毕,重新打开学生管理页面
            dispose();  //添加完毕，关闭添加界面
        }
    }
}
