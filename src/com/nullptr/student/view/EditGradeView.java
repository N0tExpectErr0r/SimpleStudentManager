package com.nullptr.student.view;

import com.nullptr.student.AppConstants;
import com.nullptr.student.factory.ServiceFactory;
import com.nullptr.student.po.Grade;
import com.nullptr.student.po.Teacher;
import com.nullptr.student.service.*;
import com.nullptr.student.utils.LogUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * 编辑年级界面
 * created by 梁文俊
 * date:2018-4-08
 */
public class EditGradeView extends BaseView {
    private JTextField nameEdit;
    private JComboBox teacherBox;

    public EditGradeView(int gradeId) {
        //调用父类方法初始化
        init("添加年级", 260, 220);

        //年级名标签
        JLabel nameText = new JLabel("年级名:");
        nameText.setBounds(20, 20, 80, 30);
        add(nameText);

        //年级名编辑框
        nameEdit = new JTextField();
        nameEdit.setBounds(90, 20, 130, 30);
        add(nameEdit);

        //级长标签
        JLabel teacherText = new JLabel("负责级长:");
        teacherText.setBounds(20, 60, 80, 30);
        add(teacherText);

        //级长选择框
        teacherBox = new JComboBox();
        //初始化数据
        TeacherService teacherService = ServiceFactory.getInstance().createService(TeacherService.class);
        List<Teacher> teacherList = teacherService.getTeacherList();
        for (Teacher teacher : teacherList) {
            teacherBox.addItem(teacher.getName());
        }
        teacherBox.setBounds(100, 60, 120, 30);
        add(teacherBox);

        //添加年级按钮
        JButton addButton = new JButton("编辑年级");
        addButton.addActionListener(new EditGradeListener(gradeId));
        addButton.setBounds(60, 120, 120, 40);
        add(addButton);

        //初始化界面数据
        initData(gradeId);

        setVisible(true);
    }

    public void initData(int gradeId) {
        GradeService gradeService = ServiceFactory.getInstance().createService(GradeService.class);
        TeacherService teacherService = ServiceFactory.getInstance().createService(TeacherService.class);

        //初始化年纪名编辑框
        Grade grade = gradeService.findGradeById(gradeId);
        nameEdit.setText(grade.getName());
        //设置级长选择框
        teacherBox.setSelectedItem(teacherService.findTeacherById(grade.getTeacherId()).getName());
    }

    private class EditGradeListener implements ActionListener {
        private int gradeId;

        public EditGradeListener(int gradeId) {
            this.gradeId = gradeId;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //按下编辑按钮
            if (!nameEdit.getText().equals("")) {
                //输入不为空
                GradeService gradeService = ServiceFactory.getInstance().createService(GradeService.class);
                TeacherService teacherService = ServiceFactory.getInstance().createService(TeacherService.class);
                UserService userService = ServiceFactory.getInstance().createService(UserService.class);

                Grade grade = gradeService.findGradeById(gradeId);
                //将原来年级的老师设置为科任老师
                userService.changePermission(String.valueOf(grade.getTeacherId()), AppConstants.TEACHER_SUBJECT_PERMISSION);
                //记录修改年级日志
                LoginService loginService = ServiceFactory.getInstance().createService(LoginService.class);
                LogUtils.getInstance().log(loginService.getIdentity()+"<"+loginService.getCurrentUserName()+">修改了年级<" + grade.getName() + ">的信息");
                grade.setName(nameEdit.getText());
                //获取当前选中老师id
                Teacher teacher = teacherService.findTeacherByName(teacherBox.getSelectedItem().toString());
                grade.setTeacherId(teacher.getId());
                gradeService.update(grade);
                //将选中的老师的权限设置为级长
                userService.changePermission(String.valueOf(grade.getTeacherId()), AppConstants.TEACHER_GRADE_PERMISSION);
                //结束处理
                JOptionPane.showMessageDialog(null, "成功修改年级！", "提示", JOptionPane.PLAIN_MESSAGE);
                GradeView gradeView = new GradeView();  //修改完毕,重新打开年级管理界面
                dispose();  //修改完毕，关闭修改界面
            } else {
                //年级信息不完整，弹出对话框
                JOptionPane.showMessageDialog(null, "请将年级信息填写完整", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
