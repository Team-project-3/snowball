package com.frame;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.data.Comment;
import com.data.DataBank;
import com.data.Label;
import com.data.Tools;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

import static javax.swing.WindowConstants.HIDE_ON_CLOSE;

public class MarkFrame {
    private JFrame markFrame = new JFrame("数据标注者");
    private DataBank db;
    private Tools tools;
    private AnalyseDialog analyseDialog;
    private JList<String> jList;
    private JPanel jPanel;
    
    public void buildFrame() {
    	db = DataBank.getInstence();
    	tools = new Tools(db);
    	
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
        jMenuItem1.addActionListener(new ImportActionListener());
        jMenuItem2.addActionListener(new ExportActionListener());

        jMenuData.add(jMenuItem1);
        jMenuData.add(jMenuItem2);
        jMenuData.addSeparator();
        jmb.add(jMenuData);


        //2.3.统计菜单
        JMenu jMenuTitle = new JMenu("统计");
        jMenuTitle.addActionListener(new AnalyseActionListener(markFrame));
        jmb.add(jMenuTitle);
        markFrame.setJMenuBar(jmb);


      //3.面板评论内容
        jPanel = new JPanel(null);
        jPanel.setVisible(true);
        jPanel.setBounds(0,0,720,540);
        
        ArrayList<String> arrData = new ArrayList<>();
        ArrayList<Comment> comments = db.getCommentList();
        
        int len = comments.size();
        for(int i = 0; i < len; i++){
            arrData.add(comments.get(i).getContent());
        }

        String[] strData = arrData.toArray(new String[len]);
        JTextArea jTextArea = new JTextArea("评论内容\n");
        jList = new JList<String>();
        Border border = BorderFactory.createLineBorder(Color.BLACK,2);

        jList.setBounds(5,0,540,270);
        jList.setBackground(Color.lightGray);
        jList.setFont(Font.getFont("楷体"));
        jList.setListData(strData);
        jList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
            	ArrayList<String> arrData = new ArrayList<>();
                ArrayList<Comment> comments = db.getCommentList();
                
                int len = comments.size();
                for(int i = 0; i < len; i++){
                    arrData.add(comments.get(i).getContent());
                }

                String[] strData = arrData.toArray(new String[len]);
            	
                int index = jList.getSelectedIndex();
                if(index < 0 || index >= len) {
                	return;
                }
                jTextArea.setText(strData[index]);
            }
        });
        jList.setBorder(border);
        JScrollPane jList2 = new JScrollPane(jList);
        jList2.setBounds(5,0,540,270);
        jPanel.add(jList2);

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
    
    private class ImportActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
           ImportDialog id = new ImportDialog(markFrame);
           id.show();
           
           reloadDataBank();
        }
    }
    
    private class ExportActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	ExportDialog ed = new ExportDialog(markFrame);
        	ed.show();
        }
    }

    private class AnalyseActionListener implements ActionListener {
    	private Frame frame;
    	
    	public AnalyseActionListener(Frame frame) {
    		this.frame = frame;
    	}
    	
        @Override
        public void actionPerformed(ActionEvent e) {
        	ArrayList<Label> labels = db.getLabelList();
        	Map<Label, ArrayList<Integer>> table = tools.analyse();
        	
        	analyseDialog = new AnalyseDialog(labels, table); 
        	analyseDialog.show(frame);
        }
    }
    
    
    private void reloadDataBank() {
    	ArrayList<String> arrData = new ArrayList<>();
        ArrayList<Comment> comments = db.getCommentList();
        
        int len = comments.size();
        System.out.println(len);
        for(int i = 0; i < len; i++){
            arrData.add(comments.get(i).getContent());
        }

        String[] strData = arrData.toArray(new String[len]);
        jList.setListData(strData);
        jList.repaint();
    }
}
