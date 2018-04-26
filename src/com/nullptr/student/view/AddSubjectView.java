package com.nullptr.student.view;

import com.nullptr.student.factory.ServiceFactory;
import com.nullptr.student.service.LoginService;
import com.nullptr.student.service.LoginServiceImpl;
import com.nullptr.student.service.SubjectService;
import com.nullptr.student.service.SubjectServiceImpl;
import com.nullptr.student.utils.LogUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * 添加科目界面
 * created by 梁文俊
 * date:2018-4-05
 */
public class AddSubjectView extends BaseView {
    private JTextField subjectEdit;

    public AddSubjectView() {
        //调用父类方法初始化界面
        init("添加科目", 340, 120);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);   //设置不能关闭

        //科目名编辑框
        subjectEdit = new JTextField();
        subjectEdit.setBounds(20, 20, 150, 30);
        add(subjectEdit);

        JButton addSubjectButton = new JButton("添加科目");
        addSubjectButton.setBounds(180, 20, 120, 30);
        addSubjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SubjectService subjectService = ServiceFactory.getInstance().createService(SubjectService.class);

                String msg = subjectService.check(subjectEdit.getText());
                if (!msg.equals("ok")) {
                    //如果填写有错
                    JOptionPane.showMessageDialog(null, msg, "提示", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    //已填写，添加科目
                    subjectService.add(subjectEdit.getText());  //添加科目
                    //记录添加科目日志
                    LoginService loginService = ServiceFactory.getInstance().createService(LoginService.class);
                    LogUtils.getInstance().log(loginService.getIdentity()+"<"+loginService.getCurrentUserName()+">添加了科目<" + subjectEdit.getText()+">");
                    //添加完毕，弹出对话框，并关闭
                    JOptionPane.showMessageDialog(null, "成功添加科目", "提示", JOptionPane.PLAIN_MESSAGE);
                    SubjectView subjectView = new SubjectView();
                    dispose();
                }
            }
        });
        add(addSubjectButton);

        setVisible(true);
    }
}
