package com.frame;

import javax.swing.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class MainFrame {
    private JFrame mainFrame;

    public MainFrame(JFrame jFrame) {
        this.mainFrame = jFrame;
    }

    public void buildFrame(){
    	 Logger logger6 = LogManager.getLogger(AddLabelDialog.class.getName());
	        logger6.info("用户进入主界面对话框");
        //1.设置frame参数
        mainFrame.setBounds(500,200,720,540);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //2.设置面板
        JPanel mainPanel = new JPanel(null);
        mainPanel.setBounds(0,0,720,520);
        mainPanel.setVisible(true);

        //3.设置面板内容 一个Text 两个button
        /*JTextArea jTextArea = new JTextArea("你是？");
        jTextArea.setBounds(300,100,50,50);*/


        JButton maintainButton =new JButton("数据维护者");
        JButton markButton =new JButton("数据标注者");
        maintainButton.setBounds(100,250,150,100);
        markButton.setBounds(470,250,150,100);
        maintainButton.addActionListener(new myButtonListener());
        markButton.addActionListener(new myButtonListener());

        //4.向面板添加组件
        //mainPanel.add(jTextArea);
        mainPanel.add(maintainButton);
        mainPanel.add(markButton);

        //5.向frame添加面板
        mainFrame.add(mainPanel);

    }

    private class myButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("数据维护者")){
                //mainFrame.setVisible(false);
                new MaintainFrame().buildFrame();
                mainFrame.dispose();
            }else{
                //mainFrame.setVisible(false);
                new MarkFrame().buildFrame();
                mainFrame.dispose();
            }
        }
    }
}
