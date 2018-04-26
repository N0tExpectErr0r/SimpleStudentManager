package com.nullptr.student.service;

import com.nullptr.student.AppConstants;
import com.nullptr.student.dao.ScoreDao;
import com.nullptr.student.dao.StudentDao;
import com.nullptr.student.factory.ServiceFactory;
import com.nullptr.student.po.Student;
import com.nullptr.student.po.User;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * StudentServiceImpl 实现了接口
 * created by 梁文俊
 * date:2018-3-31
 */
public class StudentServiceImpl implements StudentService {

    //添加学生,返回添加的学生的id
    public void add(Student student) {
        new StudentDao().add(student);

        int studentId = getStudentList().get(getStudentList().size()-1).getId();    //获取最后一个学生的id即为刚刚添加的id
        //为学生注册一个用户
        UserService userService = ServiceFactory.getInstance().createService(UserService.class);
        User user = new User();
        user.setUsername(String.valueOf(studentId));    //用户名为学生学号
        user.setPassword("000000");     //密码为默认密码
        user.setPermissionId(AppConstants.STUDENT_PERMISSION);  //权限为学生
        userService.register(user);     //注册
    }

    //检查Student数据是否正确,不正确返回对应的错误信息
    public String check(Student student){
        //姓名Pattern
        Pattern namePattern = Pattern.compile("^[\\u4E00-\\u9FA5A-Za-z]+$");
        if (!namePattern.matcher(student.getName()).matches()){
            //如果姓名格式不正确
            return "您输入的姓名格式有误或未输入";
        }
        //电话号码Pattern
        Pattern phonePattern = Pattern.compile("^(13[0-9]|14[0-9]|15[0-9]|166|17[0-9]|18[0-9]|19[8|9])\\d{8}$");
        if (!student.getPhone().equals("")&&!phonePattern.matcher(student.getPhone()).matches()){
            //如果电话格式不正确且电话号码不为空
            return "您输入的电话号码格式有误";
        }
        //邮箱Pattern
        Pattern emailPattern = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
        if (!student.getEmail().equals("")&&!emailPattern.matcher(student.getEmail()).matches()){
            //如果邮箱格式不正确且邮箱不为空
            return "您输入的邮箱格式有误";
        }
        return "ok";
    }

    //修改学生
    public void update(Student student) {
        new StudentDao().update(student);
    }

    //删除学生
    public void delete(int id) {
        //删除学生id对应成绩
        ScoreDao scoreDao = new ScoreDao();
        scoreDao.deleteScoreByStudentId(id);
        //删除学生的用户
        UserService userService = ServiceFactory.getInstance().createService(UserService.class);
        userService.deleteUser(String.valueOf(id));
        //删除id对应学生
        new StudentDao().delete(id);
    }

    //通过id获取唯一学生
    public Student findStudentById(int id) {
        return new StudentDao().findStudentById(id);
    }

    //通过关键词模糊查询学生
    public List<Student> findListWithKeyword(String column, String keyword) {
        return new StudentDao().findListWithKeyword(column, keyword);
    }

    //通过班级id查询学生
    public List<Student> findListByClassId(int class_id) {
        return new StudentDao().findListByClassId(class_id);
    }

    //获取所有学生列表
    public List<Student> getStudentList() {
        return new StudentDao().getStudentList();
    }
}
