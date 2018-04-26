package com.nullptr.student.view;

import com.nullptr.student.factory.ServiceFactory;
import com.nullptr.student.po.Score;
import com.nullptr.student.po.Subject;
import com.nullptr.student.service.*;
import com.nullptr.student.utils.LogUtils;
import sun.rmi.runtime.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * 添加成绩界面
 * created by 梁文俊
 * date:2018-4-02
 */
public class AddScoreView extends BaseView {
    private JTextField scoreEdit;
    private JLabel titleText;
    private JLabel subjectText;
    private JButton continueButton;
    private int subjectId;

    public AddScoreView(int studentId, int index) {
        //调用父类方法初始化界面
        init("添加成绩", 400, 150);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);   //设置不能关闭

        //初始化subjectId
        SubjectService subjectService = ServiceFactory.getInstance().createService(SubjectService.class);
        subjectId = subjectService.getSubjectList().get(index).getId();

        //标题文字
        titleText = new JLabel();
        titleText.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        titleText.setBounds(160, 0, 100, 40);
        add(titleText);

        //科目名标签
        subjectText = new JLabel();
        subjectText.setBounds(20, 50, 80, 30);
        add(subjectText);

        //科目成绩编辑框
        scoreEdit = new JTextField();
        scoreEdit.setBounds(100, 50, 80, 30);
        scoreEdit.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                //设置只能输入数字
                int keyChar = e.getKeyChar();
                if (keyChar < KeyEvent.VK_0 || keyChar > KeyEvent.VK_9) {
                    //如果不是数字，屏蔽输入
                    e.consume();
                }
                if (scoreEdit.getText().length() > 2) {
                    //如果超过三个数，屏蔽输入
                    e.consume();
                }
            }
        });
        scoreEdit.addActionListener(new continueListener(studentId, index)); //按下回车后
        add(scoreEdit);

        //分字
        JLabel simpleText = new JLabel("分");
        simpleText.setBounds(190, 50, 30, 30);
        add(simpleText);

        //继续按钮
        continueButton = new JButton();
        continueButton.setBounds(230, 50, 130, 30);
        continueButton.addActionListener(new continueListener(studentId, index));
        add(continueButton);

        initData(index);
        //设置可见
        setVisible(true);
    }

    //初始化数据
    public void initData(int index) {
        //设置标题文字
        SubjectService subjectService = ServiceFactory.getInstance().createService(SubjectService.class);
        titleText.setText("第" + (index + 1) + "科");
        //设置科目名
        subjectText.setText(subjectService.findSubjectNameById(subjectId) + "：");

        if (index + 1 == subjectService.getSubjectList().size()) {
            //如果是最后一科的话，设置按钮文本为完成
            continueButton.setText("完成");
        } else {
            //如果不是最后一科，设置按钮文本为继续
            continueButton.setText("继续");
        }
    }

    //继续按钮按下监听
    private class continueListener implements ActionListener {
        int index;
        int studentId;

        private continueListener(int studentId, int index) {
            this.index = index;
            this.studentId = studentId;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            SubjectService subjectService = ServiceFactory.getInstance().createService(SubjectService.class);
            if (scoreEdit.getText().equals("")) {
                //如果没有填写
                JOptionPane.showMessageDialog(null, "请填写完整成绩", "提示", JOptionPane.INFORMATION_MESSAGE);
            } else {
                //填写完整
                if (subjectService.getSubjectList().size() == index + 1) {
                    //如果是最后一个科目
                    //添加成绩
                    ScoreService scoreService = ServiceFactory.getInstance().createService(ScoreService.class);
                    int scoreNum = Integer.valueOf(scoreEdit.getText());
                    Score score = new Score();
                    score.setScore(scoreNum);
                    score.setSubjectId(subjectId);
                    score.setStudentId(studentId);
                    scoreService.add(score);
                    //记录添加成绩日志
                    StudentService studentService = ServiceFactory.getInstance().createService(StudentService.class);
                    LoginService loginService = ServiceFactory.getInstance().createService(LoginService.class);
                    LogUtils.getInstance().log(loginService.getIdentity()+"<"+loginService.getCurrentUserName()+">为学生<" + studentService.findStudentById(studentId).getName() + ">添加了一条成绩");
                    JOptionPane.showMessageDialog(null, "成功添加成绩", "提示", JOptionPane.PLAIN_MESSAGE);
                    //结束
                    ScoreView scoreView = new ScoreView(studentId);   //打开成绩界面
                    dispose();  //关闭自己
                } else {
                    //添加成绩
                    ScoreService scoreService = ServiceFactory.getInstance().createService(ScoreService.class);
                    int scoreNum = Integer.valueOf(scoreEdit.getText());
                    Score score = new Score();
                    score.setScore(scoreNum);
                    score.setSubjectId(subjectId);
                    score.setStudentId(studentId);
                    scoreService.add(score);
                    //下一个科目
                    AddScoreView editScoreView = new AddScoreView(studentId, index + 1);    //到达下一个科目
                    dispose();  //关闭
                }
            }
        }
    }
}
