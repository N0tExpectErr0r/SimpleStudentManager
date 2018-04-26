package com.nullptr.student;

/**
 * 常量类
 * 用来存放用到的常量
 */
public class AppConstants {
    //jdbc常量
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String URL = "jdbc:mysql://localhost:3306/student_manager?useUnicode=true&characterEncoding=UTF-8";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "root";

    //权限常量
    public static final int STUDENT_PERMISSION = 1;             //学生权限值
    public static final int TEACHER_SUBJECT_PERMISSION = 2;     //科任老师权限值
    public static final int TEACHER_CLASS_PERMISSION = 3;       //班主任权限值
    public static final int TEACHER_GRADE_PERMISSION = 4;      //级长权限值
    public static final int PRINCIPAL_PERMISSION = 5;           //校长权限值

    //广东供液大学学校id
    public static final int GDUT_SCHOOL_ID = 1;

    //连接池相关
    public static final int MAX_CONNECTION = 10;        //最大连接数

    public static final String POOL_NAME = "pool";   //连接池名
}
