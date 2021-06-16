package com.frame;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.data.Comment;
import com.data.Conflict;
import com.data.DataBank;
import com.data.Label;
import com.data.Tools;

import jxl.read.biff.BiffException;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static javax.swing.WindowConstants.HIDE_ON_CLOSE;

public class MaintainFrame {
    public static JFrame maintainFrame = new JFrame("数据维护者");
    private DataBank db;
    private Tools tools;
    private AddLabelDialog addLabelDialog;
    private RemoveLabelDialog removeLabelDialog;
    private ManagerDialog managerDialog;
    private DownloadDialog downloadDialog;
    private AnalyseDialog analyseDialog;
    private JList<String> jList;
    private JPanel jPanel;
    private JPanel labelPanel;
    private JPanel ConflictPanel;//记录标签冲突
    private ArrayList<ArrayList<JRadioButton>> labelMap;   
    private int index=-1;

    public void buildFrame() {
    	db = DataBank.getInstence();
    	tools = new Tools(db);
    	
        //1.设置maintainFrame参数
        maintainFrame.setBounds(500,200,870,540);
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
        jMenuItem4.addActionListener(new ManagerActionListener(maintainFrame));
        jMenuItem3.addActionListener(new DownLoadActionListener(maintainFrame));
      
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
        delete.addActionListener(new DeleteActionListener(maintainFrame));
        jMenuEdit.add(build);
        jMenuEdit.add(delete);
        jmb.add(jMenuEdit);

        //2.4.统计菜单
        JMenuItem jMenuTitle = new JMenuItem("统计");
        jMenuTitle.addActionListener(new AnalyseActionListener(maintainFrame));
        jmb.add(jMenuTitle);
        maintainFrame.setJMenuBar(jmb);


        //3.面板评论内容
        jPanel = new JPanel(null);
        jPanel.setVisible(true);
        jPanel.setBounds(0,0,870,540);
        
        ArrayList<String> arrData = new ArrayList<>();
        ArrayList<Comment> comments = db.getCommentList();
        
        int len = comments.size();
        for(int i = 0; i < len; i++){
            arrData.add(comments.get(i).getContent());
        }

        String[] strData = arrData.toArray(new String[len]);
        JTextArea jTextArea = new JTextArea("评论内容\n");
        jList = new JList<String>();
        Border border = BorderFactory.createLineBorder(Color.BLACK,1);

        jList.setBounds(5,0,540,270);
        jList.setBackground(Color.lightGray);
        jList.setFont(Font.getFont("楷体"));
        jList.setListData(strData);
        jList.addListSelectionListener(new ListSelectionListener() {
        	private boolean flag = true;
        	
            @Override
            public void valueChanged(ListSelectionEvent e) {
            	ArrayList<String> arrData = new ArrayList<>();
                ArrayList<Comment> comments = db.getCommentList();
                
                int len = comments.size();
                for(int i = 0; i < len; i++){
                    arrData.add(comments.get(i).getContent());
                }

                String[] strData = arrData.toArray(new String[len]);
            	
                index = jList.getSelectedIndex();
                if(!flag) {
                	flag = !flag;
                	return;
                }
                reloadLabels();
                reloadConflict();
                
                if(index < 0 || index >= len) {
                	System.out.print(index);
                	return;
                }
                jTextArea.setText(strData[index]);
                flag = !flag;
               
            }
        });
        jList.setBorder(border);
        JScrollPane jList2 = new JScrollPane(jList);
        jList2.setBounds(5,0,540,270);
        jPanel.add(jList2);

        //4.面板标签内容
        labelPanel = new JPanel();
        labelPanel.setBorder(border);
        labelPanel.setBounds(547,0,150,268);
       
        labelPanel.setVisible(true);
        jPanel.add(labelPanel);
        
        //5.面板标签冲突
        ConflictPanel = new JPanel();
        ConflictPanel.setBorder(border);
        ConflictPanel.setBounds(700,0,150,268);
        ConflictPanel.setVisible(true);
        jPanel.add(ConflictPanel);

        //6.面板内容文本域
        jTextArea.setBorder(border);
        jTextArea.setBounds(5,270,845,190);
        jTextArea.setLineWrap(true);
        jPanel.add(jTextArea);
             
        maintainFrame.setContentPane(jPanel);
    }

    private class ImportActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
           ImportDialog id = new ImportDialog(maintainFrame);
           id.show();
           
           reloadComments();
           reloadLabels();
           
           System.out.println("import");
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
    	private Frame frame;
    	private String downloadID;
    	
    	public DownLoadActionListener(Frame frame) {
    		this.frame = frame;
    	}
    	@Override
        public void actionPerformed(ActionEvent e) {
        	downloadDialog = new DownloadDialog();
        	downloadDialog.show(frame);
        	
        	downloadID = downloadDialog.getDownloadID();
        	if (downloadID != null) {
        		try {
					tools.downloadData(downloadID);
					Ask ask = new Ask();
					ask.start();
				} catch (BiffException | InterruptedException | IOException e1) {
					e1.printStackTrace();
				}
        	}
        }
    	
    	private class Ask implements Runnable{
    		private Thread t;

			@Override
			public void run() {
				try {
			         while(!tools.getDownloading().get(downloadID).equals("已完成")) {
			            // 让线程睡眠一会
			            Thread.sleep(500);
			         }
			         reloadComments();
			      }catch (InterruptedException e) {
			         System.out.println("Thread " +  "Ask" + " interrupted.");
			      }
			}
			
			public void start () {
			      System.out.println("Starting asking");
			      if (t == null) {
			         t = new Thread (this, "ask");
			         t.start ();
			      }
			   }
    		
    	}
    }
    
    private class ManagerActionListener implements ActionListener {
    	private Frame frame;
    	
    	ManagerActionListener(Frame frame){
    		this.frame = frame;
    	}
    	
        @Override
        public void actionPerformed(ActionEvent e) {
            Map<String, String> table = tools.getDownloading();
            managerDialog = new ManagerDialog();
            
            managerDialog.setDownLoadList(table);
            managerDialog.show(frame);
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
    	private Frame frame;
    	
    	public DeleteActionListener(Frame frame) {
    		this.frame = frame;
    	}
    	
        @Override
        public void actionPerformed(ActionEvent e) {
        	removeLabelDialog = new RemoveLabelDialog(db.getLabelList()); 
        	removeLabelDialog.show(frame, true);
        	
        	Label label = removeLabelDialog.getLabel();
        	if (label != null) {
        		tools.removeLabel(label);
        		reloadLabels();
        	}
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
        	addLabelDialog.show(frame, true);
        	
        	Label label = addLabelDialog.getLabel();
        	if (label != null) {
        		tools.addLabel(label);
        		reloadLabels();
        	}
        }
    }

    private void reloadComments() {
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
    
    private void reloadLabels(){
    	ArrayList<Label> labelList = db.getLabelList();
    	int labelSize = labelList.size();
    	ArrayList<Integer> labelSelect;
    	if (index < 0) {
    		labelSelect = new ArrayList<>();
    		for (int i=0; i<labelSize; ++i) {
    			labelSelect.add(-1);
    		}
    	} else {
    		labelSelect = db.getCommentList().get(index).getLabelList();
    	}
    	labelPanel.removeAll();
    	labelMap = new ArrayList<>();
//    	System.out.println(labelSize);
    	// 第i个标签类
        for(int i=0; i < labelSize; i++){
            JLabel label = new JLabel(labelList.get(i).getContent());
            label.setVisible(true);
            labelPanel.add(label);
            
            ArrayList<JRadioButton> jrbList = new ArrayList<>();
            ButtonGroup BG = new ButtonGroup();
            // 第j个选项
//            System.out.println(labelList.get(i).getOptions().size());
            for(int j=0; j < labelList.get(i).getOptions().size(); j++){
                JRadioButton jrb = new JRadioButton(labelList.get(i).getOptions().get(j));
                if(labelSelect.get(i) == j) {
                	jrb.setSelected(true);
                } else {
                	jrb.setSelected(false);
                	jrb.setEnabled(false);
                }
                jrb.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						for (int i = 0; i < labelMap.size(); ++i) {
							for (int j = 0; j < labelMap.get(i).size(); ++j) {
								if (e.getSource() == labelMap.get(i).get(j)) {
									db.getCommentList().get(index).getLabelList().set(i, j);
									return;
								}
							}
						}
						
					}
                	
                });
                
                BG.add(jrb);
                jrbList.add(jrb);
                labelPanel.add(jrb);
            }
            
            labelMap.add(jrbList);
        }
        labelPanel.repaint();
    }
    
    
    private void reloadConflict(){
    	ArrayList<Label> labelList = db.getLabelList();    	
    	ArrayList<Conflict> conflictLst = db.getConflictList();
    	int labelSize = labelList.size();
    	ArrayList<Integer> labelSelect;
    	if (index < 0) {
    		return;
    	} else {
    		labelSelect = db.getCommentList().get(index).getLabelList();
    	}
    	ConflictPanel.removeAll();
    	JLabel conflict = new JLabel("冲突标签：          ");
    	conflict.setVisible(true);
		ConflictPanel.add(conflict);
    	
        for(int i=0; i < labelSize; i++){
        	//值小于0，i处标签有冲突
        	if(labelSelect.get(i)<0) {
        		JLabel label = new JLabel(labelList.get(i).getContent());
        		label.setVisible(true);
        		ConflictPanel.add(label);
        		for(int j=0;j<labelList.get(i).getOptions().size();j++) {
        			String optionName = labelList.get(i).getOptions().get(j);
        			Integer optionCount = conflictLst.get(index).getOptionCount().get(i).get(j);
        			JLabel option = new JLabel(optionName+":"+optionCount.toString());
        			option.setVisible(true);
            		ConflictPanel.add(option);
        		}
        	}
        }                                    
        ConflictPanel.updateUI();             
    }
}
