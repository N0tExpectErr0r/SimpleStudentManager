package com.nullptr.student.service;

import com.nullptr.student.AppConstants;
import com.nullptr.student.dao.LoginStatusDao;
import com.nullptr.student.dao.UserDao;
import com.nullptr.student.factory.ServiceFactory;
import com.nullptr.student.po.Student;
import com.nullptr.student.po.Teacher;

/**
 * LoginServiceImpl 实现了接口
 * created by 梁文俊
 * date:2018-4-1
 */
public class LoginServiceImpl implements LoginService {
    //登陆账户
    public boolean login(String username, String password) {
        int permissionId = new UserDao().checkPassword(username, password);
        if (permissionId != -1) {
            //登陆成功,修改当前登陆者
            new LoginStatusDao().changeUser(username, permissionId);
            return true;
        } else {
            //登陆不成功，用户名或密码错误
            return false;
        }
    }

    //注销登录
    public void logout() {
        new LoginStatusDao().logout();
    }

    //检测是否已登录
    public boolean isLogin() {
        return new LoginStatusDao().isLogin();
    }

    //获取当前登录用户名
    public String getUsername() {
        return new LoginStatusDao().getUsername();
    }

    //获取当前登陆的用户的权限
    public int getPermission() {
        return new LoginStatusDao().getPermission();
    }

    //获取当前登陆的用户的身分
    public String getIdentity() {
        return new LoginStatusDao().getIdentity();
    }

    //获取当前登陆的用户的姓名
    public String getCurrentUserName(){
        int permission = getPermission();
        String name;
        if (permission == AppConstants.STUDENT_PERMISSION){
            //如果是学生，在学生表中找
            StudentService studentService = ServiceFactory.getInstance().createService(StudentService.class);
            Student student = studentService.findStudentById(Integer.valueOf(getUsername()));
            name = student.getName();
        }else{
            //不是学生，在老师表找
            TeacherService teacherService = ServiceFactory.getInstance().createService(TeacherService.class);
            Teacher teacher = teacherService.findTeacherById(Integer.valueOf(getUsername()));
            name = teacher.getName();
        }
        return name;
    }
}
