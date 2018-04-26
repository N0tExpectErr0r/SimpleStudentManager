package com.nullptr.student.view;

import com.nullptr.student.factory.ServiceFactory;
import com.nullptr.student.po.User;
import com.nullptr.student.service.LoginService;
import com.nullptr.student.service.LoginServiceImpl;
import com.nullptr.student.service.UserService;
import com.nullptr.student.service.UserServiceImpl;
import com.nullptr.student.utils.LogUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 修改密码界面
 * created by 梁文俊
 * date:2018-04-09
 */
public class ChangePasswordView extends BaseView{
    private JTextField usernameEdit;
    private JTextField originPasswordEdit;
    private JLabel newPasswordText;
    private JTextField newPasswordEdit;
    public ChangePasswordView() {
        //用父类方法初始化界面
        init("修改密码",340,330);

        //用户名标签
        JLabel usernameText = new JLabel("用户名：");
        usernameText.setBounds(60, 40, 80, 30);
        add(usernameText);

        //用户名编辑框
        LoginService loginService = ServiceFactory.getInstance().createService(LoginService.class);
        usernameEdit = new JTextField(loginService.getUsername());
        usernameEdit.setBounds(120, 40, 140, 30);
        usernameEdit.setEnabled(false);
        add(usernameEdit);

        //原密码标签
        JLabel originPasswordText = new JLabel("原密码：");
        originPasswordText.setBounds(60, 90, 80, 30);
        add(originPasswordText);

        //原密码编辑框
        originPasswordEdit = new JTextField();
        originPasswordEdit.setBounds(120, 90, 140, 30);
        add(originPasswordEdit);

        //新密码标签
        newPasswordText = new JLabel("新密码：");
        newPasswordText.setBounds(60, 140, 80, 30);
        add(newPasswordText);

        //新密码编辑框
        newPasswordEdit = new JTextField();
        newPasswordEdit.setBounds(120, 140, 140, 30);
        add(newPasswordEdit);

        //更改密码按钮
        JButton changeButton = new JButton("确认修改密码");
        changeButton.setBounds(90, 200, 140, 50);
        changeButton.addActionListener(new changeListener());
        add(changeButton);

        setVisible(true);
    }

    public class changeListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            //按下修改密码按钮
            if ((!originPasswordEdit.getText().equals("")) && (!newPasswordEdit.getText().equals(""))) {
                //如果密码填写完整
                UserService userService = ServiceFactory.getInstance().createService(UserService.class);
                if (userService.checkPassword(usernameEdit.getText(),originPasswordEdit.getText())){
                    //如果密码正确
                    userService.changePassword(usernameEdit.getText(),newPasswordEdit.getText());
                    //写入日志
                    LoginService loginService = ServiceFactory.getInstance().createService(LoginService.class);
                    LogUtils.getInstance().log(loginService.getIdentity()+"<"+loginService.getCurrentUserName()+">将自己的密码被修改为了"+newPasswordEdit.getText());
                    //结果处理
                    JOptionPane.showMessageDialog(null, "成功修改密码!", "提示", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }else {
                    //如果密码不正确
                    JOptionPane.showMessageDialog(null, "原密码输入错误!", "错误", JOptionPane.ERROR_MESSAGE);
                    originPasswordEdit.setText(""); //清空
                }
            }else{
                //密码填写不完整
                JOptionPane.showMessageDialog(null, "请将密码全部填写!", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
