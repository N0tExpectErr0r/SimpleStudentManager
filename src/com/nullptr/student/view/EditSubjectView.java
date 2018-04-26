package com.nullptr.student.view;

import com.nullptr.student.factory.ServiceFactory;
import com.nullptr.student.po.Subject;
import com.nullptr.student.service.LoginService;
import com.nullptr.student.service.LoginServiceImpl;
import com.nullptr.student.service.SubjectService;
import com.nullptr.student.service.SubjectServiceImpl;
import com.nullptr.student.utils.LogUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 修改科目名界面
 * created by 梁文俊
 * date:2018-4-05
 */
public class EditSubjectView extends BaseView {
    private JTextField subjectEdit;

    public EditSubjectView(int id) {
        //调用父类方法初始化界面
        init("编辑科目", 340, 120);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);   //设置不能关闭

        //科目名编辑框
        subjectEdit = new JTextField();
        subjectEdit.setBounds(20, 20, 150, 30);
        add(subjectEdit);
        subjectEdit.setColumns(10);

        //修改科目按钮
        JButton editSubjectButton = new JButton("修改科目");
        editSubjectButton.setBounds(180, 20, 120, 30);
        editSubjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //按下修改按钮
                SubjectService subjectService = ServiceFactory.getInstance().createService(SubjectService.class);

                String msg = subjectService.check(subjectEdit.getText());
                if (!msg.equals("ok")) {
                    //如果填写有误
                    JOptionPane.showMessageDialog(null, msg, "提示", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    //如果填写完了
                    //记录修改科目日志
                    LoginService loginService = ServiceFactory.getInstance().createService(LoginService.class);
                    LogUtils.getInstance().log(loginService.getIdentity()+"<"+loginService.getCurrentUserName()+">修改了科目<" + subjectService.findSubjectNameById(id)+">的信息");
                    //修改科目
                    Subject subject = new Subject();
                    subject.setId(id);
                    subject.setName(subjectEdit.getText());
                    subjectService.update(subject);
                    //添加完毕，弹出对话框，并关闭
                    JOptionPane.showMessageDialog(null, "成功修改科目", "提示", JOptionPane.PLAIN_MESSAGE);
                    SubjectView subjectView = new SubjectView();
                    dispose();
                }
            }
        });
        add(editSubjectButton);

        SubjectService subjectService = ServiceFactory.getInstance().createService(SubjectService.class);
        subjectEdit.setText(subjectService.findSubjectNameById(id));

        setVisible(true);
    }
}
