package com.nullptr.student.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 静态方法的文件信息工具类
 * created by 梁文俊
 * date:2018-4-09
 */
public class FileUtils {
    /**
     * 获取文件夹下文件名称列表
     * @param path:文件夹路径
     * @return 文件名列表
     */
    public static List<String> getFileNames(String path){
        // 获得指定文件对象
        File file = new File(path);
        // 获得指定文件对象
        File[] array = file.listFiles();

        List<String> filenameList = new ArrayList<>();

        for(int i=0;i<array.length;i++){
            //把文件名加入list
            filenameList.add(array[i].getName());
        }

        return filenameList;
    }

    /**
     * 获取文件夹下文件列表
     * @param path:文件夹路径
     * @return 文件列表
     */
    public static File[] getFiles(String path){
        // 获得指定文件对象
        File file = new File(path);
        // 获得指定文件对象
        return file.listFiles();
    }
}
