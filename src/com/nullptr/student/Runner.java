package com.nullptr.student;

import com.nullptr.student.factory.ServiceFactory;
import com.nullptr.student.po.Student;
import com.nullptr.student.service.LoginService;
import com.nullptr.student.service.LoginServiceImpl;
import com.nullptr.student.service.StudentServiceImpl;
import com.nullptr.student.utils.LogUtils;
import com.nullptr.student.utils.LookManager;
import com.nullptr.student.view.LoginView;
import com.nullptr.student.view.StudentManageView;

/**
 * Runner类
 * Runner作为整个程序的主入口
 * 判断是否登陆，并到达相应页面
 */
public class Runner {
    public static void main(String[] args) {
        LookManager.initLookAndFeel();    //设置皮肤
        LoginService loginService = ServiceFactory.getInstance().createService(LoginService.class);
        //删除超过三十天的文件
        LogUtils.getInstance().checkLogFile(30);
        //判断是否已经登录，已登陆就直接到达管理界面
        if (!loginService.isLogin()) {
            LoginView loginView = new LoginView();
        } else {
            StudentManageView studentManageView = new StudentManageView();
        }
    }
}
