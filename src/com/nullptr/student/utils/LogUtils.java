package com.nullptr.student.utils;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 单例模式的日志工具，封装了IO流，用于记录日志，保存为 日期.log 的日志文件
 * created by 梁文俊
 * date:2018-4-01
 */
public class LogUtils {
    private static LogUtils instance = new LogUtils();

    /**
     * 获取实例
     *
     * @return LogUtils的实例
     */
    public static LogUtils getInstance() {
        return instance;
    }

    /**
     * log函数，会在根目录的Logs文件夹下创建名为当天日期的.log文件
     * 然后将当前时间与content的信息写入日志文件
     *
     * @param content:写入日志的信息
     */
    public void log(String content) {
        BufferedWriter writer = null;
        //获取当前日期
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String dateName = dateFormat.format(date);

        DateFormat nameFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fileName = nameFormat.format(date);
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./Logs/" + fileName + ".log", true)));
            writer.write("[" + dateName + "] " + content + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断日志是否已超过一定天数，超过删除文件
     * @param dayNum:超过dayNum天则删除日志
     */
    public void checkLogFile(int dayNum){

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();

        File[] files = FileUtils.getFiles("./Logs/");
        for (File file:files){
            //将每个文件的名称转成Date对象
            Date date = null;
            try {
                date = dateFormat.parse(file.getName().replace(".log", ""));
            }catch (ParseException e){
                e.printStackTrace();
            }

            //判断是否到达dayNum天
            //如果到达dayNum天，删除该文件
            if ((currentDate.getTime()-date.getTime())/(24*3600*1000) >= dayNum) {
                if (file.isFile()) file.delete();
            }
        }
    }
}
