package com.frame;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.data.DataBank;
import com.data.Label;
import com.data.Tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import static javax.swing.WindowConstants.HIDE_ON_CLOSE;

public class MaintainFrame {
    public static JFrame maintainFrame = new JFrame("数据维护者");
    private DataBank db;
    private Tools tools;
    private AddLabelDialog addLabelDialog;
    private AnalyseDialog analyseDialog;

    public void buildFrame() {
        //1.设置maintainFrame参数
        maintainFrame.setBounds(500,200,720,540);
        maintainFrame.setVisible(true);
        maintainFrame.setDefaultCloseOperation(HIDE_ON_CLOSE);

        //2.设置菜单栏
        JMenuBar jmb = new JMenuBar();
        //2.1. 数据菜单
        JMenu jMenuData =new JMenu("数据");
        JMenuItem jMenuItem1 = new JMenuItem("导入");
        JMenuItem jMenuItem2 = new JMenuItem("导出");
        JMenu downLoad = new JMenu("下载");
        JMenuItem jMenuItem3 = new JMenuItem("通过代码方式下载");
        JMenuItem jMenuItem4 = new JMenuItem("下载管理");
        downLoad.add(jMenuItem3);
        downLoad.add(jMenuItem4);

        //2.2向子菜单添加响应事件
        jMenuItem1.addActionListener(new ImportActionListener());
        jMenuItem2.addActionListener(new ExportActionListener());
        jMenuItem3.addActionListener(new DownLoadActionListener());
        jMenuItem4.addActionListener(new ManagerActionListener());
        
        jMenuData.add(jMenuItem1);
        jMenuData.add(jMenuItem2);
        jMenuData.addSeparator();
        jMenuData.add(downLoad);
        jmb.add(jMenuData);

        //2.3. 编辑菜单
        JMenu jMenuEdit = new JMenu("编辑");
        JMenuItem build = new JMenuItem("新建标签");
        JMenuItem delete = new JMenuItem("删除标签");
        build.addActionListener(new AddActionListener(maintainFrame));
        delete.addActionListener(new DeleteActionListener());
        jMenuEdit.add(build);
        jMenuEdit.add(delete);
        jmb.add(jMenuEdit);

        //2.4.统计菜单
        JMenuItem jMenuTitle = new JMenuItem("统计");
        jMenuTitle.addActionListener(new AnalyseActionListener(maintainFrame));
        jmb.add(jMenuTitle);
        maintainFrame.setJMenuBar(jmb);


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
        maintainFrame.setContentPane(jPanel);


    }

    private class ImportActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //1.弹出对话框
            JDialog jDialog;
            jDialog = new JDialog(maintainFrame,"数据导入");
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
                }
            });
            JButton cancel = new JButton("取消");
            cancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jDialog.show(false);
                }
            });
            jPanel.add(jButton);
            jPanel.add(yes);
            jPanel.add(cancel);
            jDialog.add(jPanel);
            
            jDialog.setVisible(true);
        }
    }
    
    private class ExportActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	ExportDialog ed = new ExportDialog(maintainFrame);
        	ed.show();
        }
    }
    
    private class DownLoadActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	//1.弹出对话框
            JDialog jDialog;
            jDialog = new JDialog(maintainFrame,"数据导入");
            jDialog.setBounds(600,250,300,200);
            

            //2.设置对话框面板内容
            JPanel jPanel = new JPanel();
            jPanel.setSize(300,200);
            JTextField filePath = new JTextField(20);
            filePath.setLocation(5,10);
            filePath.setHorizontalAlignment(JTextField.LEFT);

            jPanel.add(filePath);

            JButton jButton = new JButton("下载");
            jButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                }
            });
            JButton yes = new JButton("确定");
            yes.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jDialog.show(false);
                }
            });
            JButton cancel = new JButton("取消");
            cancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jDialog.show(false);
                }
            });
            jPanel.add(jButton);
            jPanel.add(yes);
            jPanel.add(cancel);
            jDialog.add(jPanel);
            
            jDialog.setVisible(true);
        }
    }
    
    private class ManagerActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //1.弹出对话框
            JDialog jDialog;
            jDialog = new JDialog(maintainFrame,"下载管理");
            jDialog.setBounds(600,250,300,400);
            

            //2.设置对话框面板内容
            JPanel jPanel = new JPanel();
            jPanel.setSize(300,400);
            jPanel.setBounds(0,0,720,540);
            String[] dataList = new String[10];
            
            String[] str = {"股票1"};
            JList<String> jList1=new JList(str);

            JList<JProgressBar> jList2=new JList();
            JProgressBar jProgressBar =  new JProgressBar();
            jProgressBar.setStringPainted(true);
            jProgressBar.setIndeterminate(true);
            jProgressBar.setString("下载中......");
            jList2.add(jProgressBar);
            
            Border border = BorderFactory.createLineBorder(Color.BLACK,1);
            jList1.setBorder(border);
            jList2.setBorder(border);
            jPanel.setLayout(new GridLayout(1, 2));
            jPanel.add(jList1);
            jPanel.add(jList2);
            jDialog.add(jPanel);
            
            jDialog.setVisible(true);
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
    
    private class DeleteActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //1.弹出对话框
            JDialog jDialog;
            jDialog = new JDialog(maintainFrame,"统计分析");
            jDialog.setBounds(600,250,300,400);
            

            //2.设置对话框面板内容
            JPanel jPanel = new JPanel();
            jPanel.setSize(300,400);
            jPanel.setBounds(0,0,720,540);

            JComboBox jComboBox = new JComboBox<String>();
            jComboBox.addItem(new String("标签1"));
            jComboBox.setSize(200, 20);
            
            JButton yes = new JButton("删除");
            yes.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jDialog.show(false);
                }
            });
            
            // jPanel.setLayout(new GridLayout(2,1));
            jPanel.add(yes);
            jPanel.add(jComboBox);
            jDialog.add(jPanel);
            
            jDialog.setVisible(true);
        }
    }
    
    private class AddActionListener implements ActionListener {
    	private Frame frame;
    	
    	public AddActionListener(Frame frame) {
    		this.frame = frame;
    	}
    	
        @Override
        public void actionPerformed(ActionEvent e) {
        	addLabelDialog = new AddLabelDialog(); 
        	addLabelDialog.show(frame);
        	
        	Label label = addLabelDialog.getLabel();
        	if (label != null)
        		tools.addLabel(label);
        }
    }
}
