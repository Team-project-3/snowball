package com.frame;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ManagerDialog {
	private JDialog dialog;
	private Map<String, String> downloadList = new HashMap();
	JTable table;
	
	public void show(Frame frame) {
		 Logger logger7 = LogManager.getLogger(AddLabelDialog.class.getName());
	        logger7.info("用户进入下载管理对话框");
		dialog = new JDialog(frame, "下载管理", true);
		dialog.setBounds(600, 250, 300, 400);
        
        String[][] datas = {};
        String[] titles = { "股票", "状态" };
        DefaultTableModel model = new DefaultTableModel(datas, titles);
        table = new JTable(model);
        for(String name : downloadList.keySet()) {
        	model.addRow(new String[] {name, downloadList.get(name)});
        }
        table.setSize(250, 350);
        
        JScrollPane jp = new JScrollPane(table);
        jp.setSize(250, 350);
        jp.setViewportView(table);
        
        dialog.getContentPane().add(jp, BorderLayout.NORTH);
		
		dialog.setVisible(true);
	}
	
	void setDownLoadList(Map<String, String> downloadList) {
		this.downloadList = downloadList;
	}
}
