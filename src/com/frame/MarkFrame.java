package com.frame;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static javax.swing.WindowConstants.HIDE_ON_CLOSE;

public class MarkFrame {
    private JFrame markFrame = new JFrame("数据标注者");

    public void buildFrame() {
        //1.设置maintainFrame参数
        markFrame.setBounds(500,200,720,540);
        markFrame.setVisible(true);
        markFrame.setDefaultCloseOperation(HIDE_ON_CLOSE);

        //2.设置菜单栏
        JMenuBar jmb = new JMenuBar();
        //2.1. 数据菜单
        JMenu jMenuData =new JMenu("数据");
        JMenuItem jMenuItem1 = new JMenuItem("导入");
        JMenuItem jMenuItem2 = new JMenuItem("导出");


        //2.2向数据菜单添加响应事件
        jMenuItem1.addActionListener(new myActionListener());
        jMenuItem2.addActionListener(new myActionListener());

        jMenuData.add(jMenuItem1);
        jMenuData.add(jMenuItem2);
        jMenuData.addSeparator();
        jmb.add(jMenuData);


        //2.3.统计菜单
        JMenu jMenuTitle = new JMenu("统计");
        jmb.add(jMenuTitle);
        markFrame.setJMenuBar(jmb);


        //3.面板评论内容
        JPanel jPanel = new JPanel(null);
        jPanel.setVisible(true);
        jPanel.setBounds(0,0,720,540);
        String[] dataList = new String[10];
        for(int i = 0;i<10;i++){
            dataList[i] = "评论                                    "+i;
        }

        JTextArea jTextArea = new JTextArea("评论内容\n");
        JList<String> jList = new JList<>(dataList);
        Border border = BorderFactory.createLineBorder(Color.BLACK,2);

        jList.setBounds(5,0,540,270);
        jList.setBackground(Color.lightGray);
        jList.setFont(Font.getFont("楷体"));
        jList.setListData(dataList);
        jList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index = jList.getSelectedIndex();
                jTextArea.setText(dataList[index]);
            }
        });
        jList.setBorder(border);
        jPanel.add(jList);

        //4.面板标签内容
        JTextArea labelTextArea = new JTextArea("标签");
        labelTextArea.setBorder(border);
        labelTextArea.setBounds(545,0,150,270);
        jPanel.add(labelTextArea);

        //5.面板内容文本域
        jTextArea.setBorder(border);
        jTextArea.setBounds(5,270,690,190);
        jPanel.add(jTextArea);
        markFrame.setContentPane(jPanel);

    }

    private class myActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //1.弹出对话框
            JDialog jDialog;
            if(e.getActionCommand().equals("导入")){
                jDialog = new JDialog(markFrame,"数据导入");
            }else {
                jDialog = new JDialog(markFrame,"数据导出");
            }
            jDialog.setBounds(600,250,300,200);

            //2.设置对话框面板内容
            JPanel jPanel = new JPanel();
            jPanel.setSize(300,200);
            JTextField filePath = new JTextField(20);
            filePath.setLocation(5,10);
            filePath.setHorizontalAlignment(JTextField.LEFT);

            jPanel.add(filePath);

            JButton jButton = new JButton("浏览");
            jButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser jf = new JFileChooser();
                    jf.showOpenDialog(markFrame);//显示打开的文件对话框
                    File f =  jf.getSelectedFile();//使用文件类获取选择器选择的文件
                    if(f==null){
                        return;
                    }
                    String s = f.getAbsolutePath();//返回路径名
                    filePath.setText(s);
                }
            });
            JButton yes = new JButton("确定");
            yes.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jDialog.setVisible(false);
                }
            });
            JButton cancel = new JButton("取消");
            cancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jDialog.setVisible(false);
                }
            });
            jPanel.add(jButton);
            jPanel.add(yes);
            jPanel.add(cancel);
            jDialog.add(jPanel);
            
            jDialog.setVisible(true);
        }
    }
}
