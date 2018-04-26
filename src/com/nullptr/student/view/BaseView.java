package com.nullptr.student.view;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

//所有View的基类，里面有方法来对自己初始化
public class BaseView extends JFrame {

    //所有View的初始化方法
    protected void init(String title, int width, int height) {
        initGlobalFont(new Font("微软雅黑",Font.PLAIN,14)); //设置默认字体为微软雅黑
        //计算窗体在屏幕中心出现的x与y
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2;
        //初始化该界面
        setTitle(title);   //设置标题
        setResizable(false);    //设置大小不可变
        setLayout(null);    //设置为绝对布局
        setBounds(x, y, width, height);
    }

    //设置统一默认字体
    private static void initGlobalFont(Font font) {
        FontUIResource fontRes = new FontUIResource(font);
        for (Enumeration<Object> keys = UIManager.getDefaults().keys();
             keys.hasMoreElements(); ) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontRes);
            }
        }
    }
}
