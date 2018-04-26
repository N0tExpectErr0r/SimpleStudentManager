package com.nullptr.student.view;

import com.nullptr.student.AppConstants;
import com.nullptr.student.factory.ServiceFactory;
import com.nullptr.student.po.TheClass;
import com.nullptr.student.po.Grade;
import com.nullptr.student.po.Teacher;
import com.nullptr.student.service.*;
import com.nullptr.student.utils.LogUtils;
import sun.rmi.runtime.Log;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * 添加班级界面
 * created by 梁文俊
 * date:2018-4-07
 */
public class AddClassView extends BaseView {
    private JTextField nameEdit;
    private JComboBox gradeBox;
    private JComboBox teacherBox;

    public AddClassView() {
        //调用父类方法初始化界面
        init("添加班级", 270, 300);

        //班级名称标签
        JLabel nameText = new JLabel("班级名称:");
        nameText.setBounds(20, 20, 80, 30);
        add(nameText);

        //班级名称编辑框
        nameEdit = new JTextField();
        nameEdit.setBounds(100, 20, 140, 30);
        add(nameEdit);

        //所属年级标签
        JLabel gradeText = new JLabel("所属年级:");
        gradeText.setBounds(20, 70, 80, 30);
        add(gradeText);

        //所属年级选择框
        gradeBox = new JComboBox();
        gradeBox.setBounds(100, 70, 140, 30);
        //初始化数据
        GradeService gradeService = ServiceFactory.getInstance().createService(GradeService.class);
        List<Grade> gradeList = gradeService.getGradeList();
        for (Grade grade : gradeList) {
            gradeBox.addItem(grade.getName());
        }
        add(gradeBox);

        //班主任标签
        JLabel teacherText = new JLabel("班主任:");
        teacherText.setBounds(20, 120, 60, 30);
        add(teacherText);

        //班主任选择框
        teacherBox = new JComboBox();
        teacherBox.setBounds(100, 120, 140, 30);

        //初始化数据
        TeacherService teacherService = ServiceFactory.getInstance().createService(TeacherService.class);
        List<Teacher> teacherList = teacherService.getTeacherList();
        for (Teacher teacher : teacherList) {
            teacherBox.addItem(teacher.getName());
        }
        add(teacherBox);

        //添加班级按钮
        JButton addButton = new JButton("添加班级");
        addButton.setBounds(70, 180, 120, 40);
        addButton.addActionListener(new AddClassListener());
        add(addButton);

        setVisible(true);
    }

    private class AddClassListener implements ActionListener {
        //按下添加班级按钮回调
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!nameEdit.getText().equals("")) {
                //输入不为空
                ClassService classService = ServiceFactory.getInstance().createService(ClassService.class);
                GradeService gradeService = ServiceFactory.getInstance().createService(GradeService.class);
                UserService userService = ServiceFactory.getInstance().createService(UserService.class);
                TeacherService teacherService = ServiceFactory.getInstance().createService(TeacherService.class);

                TheClass theClass = new TheClass();
                theClass.setName(nameEdit.getText());
                theClass.setGradeId(gradeService.findGradeByName(gradeBox.getSelectedItem().toString()).getId());
                theClass.setTeacherId(teacherService.findTeacherByName(teacherBox.getSelectedItem().toString()).getId());
                classService.add(theClass);
                //将选中的老师的权限设置为班主任
                userService.changePermission(String.valueOf(theClass.getTeacherId()), AppConstants.TEACHER_CLASS_PERMISSION);
                //记录添加班级日志
                LoginService loginService = ServiceFactory.getInstance().createService(LoginService.class);
                LogUtils.getInstance().log(loginService.getIdentity()+"<"+loginService.getCurrentUserName()+">添加了班级<" + nameEdit.getText()+">");
                //结束处理
                JOptionPane.showMessageDialog(null, "成功添加班级！", "提示", JOptionPane.PLAIN_MESSAGE);
                ClassView classView = new ClassView();  //添加完毕,重新打开班级管理页面
                dispose();  //添加完毕，关闭添加界面
            } else {
                //班级信息不完整，弹出对话框
                JOptionPane.showMessageDialog(null, "请将班级信息填写完整", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
