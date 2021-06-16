package com.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.data.Tools;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExportDialog {
	private JDialog jDialog=null;
	private JFrame maintainFrame = null;
	private JTextField filePath = null;

	public ExportDialog(JFrame frame) {
		 Logger logger4 = LogManager.getLogger(AddLabelDialog.class.getName());
	        logger4.info("用户进入数据导出对话框"); 
		// TODO Auto-generated constructor stub
		//1.弹出对话框
		maintainFrame = frame;
        jDialog = new JDialog(maintainFrame,"数据导出", true);
        jDialog.setBounds(600,250,300,200);
        

        //2.设置对话框面板内容
        JPanel jPanel = new JPanel();
        jPanel.setSize(300,200);
        filePath = new JTextField(20);
        filePath.setLocation(5,10);
        filePath.setHorizontalAlignment(JTextField.LEFT);
        filePath.setText("D:\\");

        jPanel.add(filePath);

        JButton jButton = new JButton("导出");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jf = new JFileChooser();
                jf.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
                jf.showOpenDialog(maintainFrame);//显示打开的文件对话框
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
                jDialog.show(false);
                Tools tool = new Tools(null);
                if(getDirPath()==null) {
                	System.out.print("导出失败！");
                	return;
                }
            	try {
					tool.exportData(getDirPath(), getFileName());
				} catch (RowsExceededException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (WriteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	System.out.print("导出成功！");
                
            }
        });
        JButton cancel = new JButton("取消");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jDialog.show(false);
                System.out.print("导出取消！");
            }
        });
        jPanel.add(jButton);
        jPanel.add(yes);
        jPanel.add(cancel);
        jDialog.add(jPanel);
    
	}

	// 调用该方法显示对话框
	public void show() {
		jDialog.setVisible(true);
	}
	
	// 调用该方法获得文件夹路径
	public String getDirPath() {
		return filePath.getText();
		
	}
	
	// 调用该方法获得文件名
	public String getFileName() {
		return "commentData.xls";	
	}
}
