package com.frame;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DownloadDialog {
	private JDialog dialog;
	private String downloadID;

	public DownloadDialog() {
	}

	// 该方法显示对话框
	public void show(Frame frame) {
		dialog = new JDialog(frame, "下载", true);
		dialog.setBounds(600, 250, 300, 200);
		
		JPanel jp1 = new JPanel();
		jp1.setSize(250, 100);
		JLabel jl = new JLabel("请输入股票代码：");
		JTextField jtf = new JTextField(20);
		jp1.add(jl);
		jp1.add(jtf);
		
		JPanel jp2 = new JPanel();
		JButton yes = new JButton("下载");
		yes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				downloadID = jtf.getText();
				
				String str = "^[A-Z][A-Z][0-9]+";
		        Pattern pattern = Pattern.compile(str);
		        
		        // 如果代码符合规范
		        if(pattern.matcher(downloadID).matches()) {
		        	dialog.dispose();
		        } else {
		        	JOptionPane.showMessageDialog(null, "股票代码错误，请重输");
		        }
			}
			
		});
		jp2.add(yes);
		
		dialog.getContentPane().add(jp1, BorderLayout.CENTER);
		dialog.getContentPane().add(jp2, BorderLayout.SOUTH);
		dialog.setVisible(true);
	}
	
	// 该方法获得要下载的股票的代码
	public String getDownloadID() {
		return downloadID;
	}
}
