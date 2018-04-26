package com.nullptr.student.view;

import com.nullptr.student.factory.ServiceFactory;
import com.nullptr.student.po.Score;
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
 * 修改成绩界面
 * created by 梁文俊
 * date:2018-4-03
 */
public class EditScoreView extends BaseView {
    private JTextField scoreEdit;
    private JLabel titleText;
    private JLabel subjectText;
    private JButton continueButton;
    private int subjectId;

    public EditScoreView(List<Integer> idList, int studentId, int index) {
        //调用父类方法初始化界面
        init("编辑成绩", 400, 150);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);   //设置不能关闭

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
        scoreEdit.addActionListener(new continueListener(idList, studentId, index)); //按下回车后
        add(scoreEdit);

        //分字标签
        JLabel simpleText = new JLabel("分");
        simpleText.setBounds(190, 50, 30, 30);
        add(simpleText);

        //继续按钮
        continueButton = new JButton();
        continueButton.setBounds(230, 50, 130, 30);
        continueButton.addActionListener(new continueListener(idList, studentId, index));
        add(continueButton);

        initData(idList, index);
        //设置可见
        setVisible(true);
    }

    //初始化数据
    public void initData(List<Integer> idList, int index) {
        //设置标题文字
        SubjectService subjectService = ServiceFactory.getInstance().createService(SubjectService.class);
        titleText.setText("第" + (index + 1) + "科");
        //设置科目名
        subjectText.setText(subjectService.findSubjectNameById(subjectId) + "：");

        ScoreService scoreService = ServiceFactory.getInstance().createService(ScoreService.class);
        int score = scoreService.findScoreById(idList.get(index)).getScore();
        scoreEdit.setText(score + "");

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
        List<Integer> idList;
        int studentId;

        private continueListener(List<Integer> idList, int studentId, int index) {
            this.idList = idList;
            this.studentId = studentId;
            this.index = index;
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
                    //编辑成绩
                    ScoreService scoreService = ServiceFactory.getInstance().createService(ScoreService.class);
                    int scoreNum = Integer.valueOf(scoreEdit.getText());
                    Score score = scoreService.findScoreById(idList.get(index));    //获取要改变的成绩
                    score.setScore(scoreNum);
                    scoreService.update(score);
                    //记录修改成绩日志
                    StudentService studentService = ServiceFactory.getInstance().createService(StudentService.class);
                    LoginService loginService = ServiceFactory.getInstance().createService(LoginService.class);
                    LogUtils.getInstance().log(loginService.getIdentity()+"<"+loginService.getCurrentUserName()+">修改了学生<" + studentService.findStudentById(studentId).getName() + ">的一条成绩");
                    JOptionPane.showMessageDialog(null, "成功修改成绩", "提示", JOptionPane.PLAIN_MESSAGE);
                    //结束
                    ScoreView scoreView = new ScoreView(studentId);   //打开成绩界面
                    dispose();  //关闭自己
                } else {
                    //编辑成绩
                    ScoreService scoreService = ServiceFactory.getInstance().createService(ScoreService.class);
                    int scoreNum = Integer.valueOf(scoreEdit.getText());
                    Score score = scoreService.findScoreById(idList.get(index));
                    score.setScore(scoreNum);
                    scoreService.update(score);
                    //下一个科目
                    EditScoreView editScoreView = new EditScoreView(idList, studentId, index + 1);    //到达下一个科目
                    dispose();  //关闭
                }
            }
        }
    }
}
