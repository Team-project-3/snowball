package com.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.data.Label;

import snowball.Main;

public class AddLabelDialog {
	private Label label;
    private JDialog jDialog;
    private JButton yes;
	
	public AddLabelDialog() {
	}

	// 该方法显示对话框
	public void show(Frame maintainFrame, boolean model) {
		  //1.弹出对话框
		jDialog = new JDialog(maintainFrame, "添加标签", model);
        jDialog.setBounds(600,250,300,300);
        Logger logger1 = LogManager.getLogger(AddLabelDialog.class.getName());
        logger1.info("用户进入添加标签对话框"); 

        //2.设置对话框面板内容
        // 给标签名的jp
        JPanel jPanel1 = new JPanel();
        jPanel1.setSize(250, 30);
        
        JTextField labelName = new JTextField(20);
        labelName.setHorizontalAlignment(JTextField.LEFT);
        JLabel labName = new JLabel("标签 ");
        
        jPanel1.add(labName);
        jPanel1.add(labelName);
        
        // 给各项标签选项的jp
        JPanel jPanel2 = new JPanel();
        jPanel2.setSize(250, 200);
        
        JTextArea labelOptions = new JTextArea(5, 10);
        labelOptions.setLineWrap(true);
        labelOptions.setSize(250, 200);
        JLabel labOptions = new JLabel("选项 ");
        labOptions.setSize(30,30);
//        labelOptions.setPreferredSize(new Dimension(250, 300));
        
        jPanel2.setLayout(new FlowLayout());
        jPanel2.add(labOptions);
        jPanel2.add(labelOptions);
        
        // 按钮
        yes = new JButton("添加");

	    yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	label = new Label();
            	
                String labelContent = labelName.getText(); 
                label.setContent(labelContent);

                String options[] = labelOptions.getText().split("\n");
                List<String> result = Arrays.asList(options);
                label.setOptions(new ArrayList<>(result));
                
//                jDialog.setVisible(false);
                jDialog.dispose();
            }
        });


        jDialog.getContentPane().add(jPanel1, BorderLayout.NORTH);
        jDialog.getContentPane().add(jPanel2, BorderLayout.CENTER);
        jDialog.getContentPane().add(yes, BorderLayout.SOUTH);
        
        jDialog.setVisible(true);	
	}
	
	// 该方法获得要添加的标签的信息
	public Label getLabel() {
		return label;
	}
	
}
