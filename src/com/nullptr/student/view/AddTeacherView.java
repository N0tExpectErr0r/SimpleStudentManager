package com.nullptr.student.view;

import com.nullptr.student.AppConstants;
import com.nullptr.student.factory.ServiceFactory;
import com.nullptr.student.po.Teacher;
import com.nullptr.student.po.User;
import com.nullptr.student.service.*;
import com.nullptr.student.utils.LogUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AddTeacherView extends BaseView {
    private JTextField nameEdit;
    private JTextField phoneEdit;
    private JTextField emailEdit;

    public AddTeacherView() {
        //父类方法初始化
        init("添加老师", 230, 280);

        //老师名称标签
        JLabel nameText = new JLabel("老师名称*:");
        nameText.setBounds(20, 20, 80, 30);
        add(nameText);

        //老师名称编辑框
        nameEdit = new JTextField();
        nameEdit.setBounds(90, 20, 120, 30);
        nameEdit.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                //设置只能输入中文或英文
                int keyChar = e.getKeyChar();
                if ((keyChar >= '\u4E00' && keyChar <= '\u9FA5') || (keyChar >= 'A' && keyChar <= 'Z') || (keyChar >= 'a' && keyChar <= 'z')) {
                    //如果是中文或英文
                } else {
                    //不是中文或英文
                    e.consume();
                }
            }
        });
        add(nameEdit);

        //老师电话标签
        JLabel phoneText = new JLabel("电话:");
        phoneText.setBounds(20, 70, 50, 30);
        add(phoneText);

        //老师电话编辑框
        phoneEdit = new JTextField();
        phoneEdit.setBounds(60, 70, 150, 30);
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

        //老师邮箱标签
        JLabel emailText = new JLabel("邮箱:");
        emailText.setBounds(20, 120, 50, 30);
        add(emailText);

        //老师邮箱编辑框
        emailEdit = new JTextField();
        emailEdit.setBounds(60, 120, 150, 30);
        add(emailEdit);

        //添加老师按钮
        JButton addButton = new JButton("添加老师");
        addButton.setBounds(40, 170, 150, 40);
        addButton.addActionListener(new AddTeacherListener());
        add(addButton);

        setVisible(true);
    }

    private class AddTeacherListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //按下添加老师
            TeacherService teacherService = ServiceFactory.getInstance().createService(TeacherService.class);

            Teacher teacher = new Teacher();
            teacher.setName(nameEdit.getText());
            teacher.setPhone(phoneEdit.getText());
            teacher.setEmail(emailEdit.getText());

            String msg = teacherService.check(teacher);
            if (!msg.equals("ok")){
                //如果输入信息有误
                JOptionPane.showMessageDialog(null, msg, "提示", JOptionPane.INFORMATION_MESSAGE);
            }else {
                int teacherId = teacherService.add(teacher);
                //为老师注册一个用户
                UserService userService = ServiceFactory.getInstance().createService(UserService.class);
                User user = new User();
                user.setUsername(String.valueOf(teacherId));    //用户名为学生学号
                user.setPassword("000000");     //密码为默认密码
                user.setPermissionId(AppConstants.TEACHER_SUBJECT_PERMISSION);  //权限为老师
                userService.register(user);     //注册
                //记录添加老师日志
                LoginService loginService = ServiceFactory.getInstance().createService(LoginService.class);
                LogUtils.getInstance().log(loginService.getIdentity() + "<" + loginService.getCurrentUserName() + ">添加了老师<" + nameEdit.getText() + ">");
                //结束处理
                JOptionPane.showMessageDialog(null, "成功添加老师！", "提示", JOptionPane.PLAIN_MESSAGE);
                TeacherView teacherView = new TeacherView();    //添加完毕，重新打开老师列表
                dispose();  //添加完毕，关闭添加界面
            }
        }
    }
}
