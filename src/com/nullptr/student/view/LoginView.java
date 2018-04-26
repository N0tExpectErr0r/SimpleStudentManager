package com.nullptr.student.view;

import com.nullptr.student.factory.ServiceFactory;
import com.nullptr.student.service.LoginService;
import com.nullptr.student.service.LoginServiceImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 登陆界面
 * created by 梁文俊
 * date:2018-4-01
 */
public class
LoginView extends BaseView {
    private JTextField usernameEdit;
    private JPasswordField passwordEdit;

    public LoginView() {
        //调用父类方法初始化界面
        init("学生管理系统登陆", 400, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     //关闭这个窗口则直接关闭整个程序

        //用户名
        JLabel usernameText = new JLabel("用户名:");
        usernameText.setBounds(80, 60, 50, 30);
        add(usernameText);

        //用户名输入栏
        usernameEdit = new JTextField(20);
        usernameEdit.setBounds(140, 60, 150, 30);
        add(usernameEdit);

        //密码
        JLabel passwordText = new JLabel("密码:");
        passwordText.setBounds(80, 110, 50, 30);
        add(passwordText);

        //密码输入栏
        passwordEdit = new JPasswordField(20);
        passwordEdit.setBounds(140, 110, 150, 30);
        passwordEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //当在密码栏按下回车
                login();    //执行登陆动作
            }
        });
        add(passwordEdit);

        //登录按钮
        JButton loginButton = new JButton("登陆");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        loginButton.setBounds(110, 160, 80, 30);
        add(loginButton);

        //重置按钮
        JButton resetButton = new JButton("重置");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usernameEdit.setText("");
                passwordEdit.setText("");
            }
        });
        resetButton.setBounds(200, 160, 80, 30);
        add(resetButton);

        setVisible(true);
    }

    //执行登陆操作
    private void login() {
        LoginService loginService = ServiceFactory.getInstance().createService(LoginService.class);
        String username = usernameEdit.getText();
        String password = String.valueOf(passwordEdit.getPassword());
        if (usernameEdit.getText().equals("") || String.valueOf(passwordEdit.getPassword()).equals("")) {
            //没有填写完，弹出对话框
            JOptionPane.showMessageDialog(this, "请输入用户名及密码", "提示", JOptionPane.INFORMATION_MESSAGE);
        } else {
            if (loginService.login(username, password)) {
                //登陆成功
                StudentManageView studentManageView = new StudentManageView();
                dispose();
            } else {
                //登陆失败
                JOptionPane.showMessageDialog(this, "用户名或密码错误", "错误", JOptionPane.ERROR_MESSAGE);
                passwordEdit.setText("");
            }
        }
    }
}
