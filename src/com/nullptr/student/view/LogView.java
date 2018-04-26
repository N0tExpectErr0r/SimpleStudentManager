package com.nullptr.student.view;

import com.nullptr.student.utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.util.List;

/**
 * 查看日志信息界面
 * created by 梁文俊
 * date:2018-4-09
 */
public class LogView extends BaseView{
    private JTextArea textArea;
    private JComboBox selectBox;
    public LogView() {
        //用父类方法初始化界面
        init("查看日志界面",690,600);

        //头部标签
        JLabel titleText = new JLabel("查看敏感操作日志");
        titleText.setFont(new Font("微软雅黑",Font.PLAIN,26));
        titleText.setBounds(10, 10, 250, 30);
        add(titleText);

        //选择日志标签
        JLabel selectText = new JLabel("选择日志：");
        selectText.setBounds(290, 10, 100, 30);
        add(selectText);

        //日志选择框
        selectBox = new JComboBox();
        List<String> filenameList = FileUtils.getFileNames("./Logs/");
        for (String filename : filenameList) {
            selectBox.addItem(filename);
        }
        selectBox.setSelectedIndex(selectBox.getItemCount()-1); //设置选中最后一天的日志
        selectBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                textArea.setText("");   //清空查看日志框
                String path = "./Logs/"+selectBox.getSelectedItem().toString();
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(path));
                    String lineText;
                    // 一次读入一行，直到读入null为文件结束
                    while ((lineText = reader.readLine()) != null) {
                        textArea.append(lineText+"\n");
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        selectBox.setBounds(370, 10, 300, 30);
        add(selectBox);

        //日志查看框
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("微软雅黑",Font.PLAIN,14));
        textArea.setSize(660, 500);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(10, 50, 660, 500);
        add(scrollPane);

        initLogText();

        setVisible(true);
    }

    private void initLogText(){
        textArea.setText("");   //清空查看日志框
        String path = "./Logs/"+selectBox.getItemAt(selectBox.getItemCount()-1);    //设置默认查看最后一天的日志
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String lineText;
            // 一次读入一行，直到读入null为文件结束
            while ((lineText = reader.readLine()) != null) {
                textArea.append(lineText+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
