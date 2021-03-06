package com.frame;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.data.Comment;
import com.data.Conflict;
import com.data.DataBank;
import com.data.Label;
import com.data.Tools;
import com.frame.MaintainFrame.MyRenderer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

import static javax.swing.WindowConstants.HIDE_ON_CLOSE;

public class MarkFrame {
    public static JFrame markFrame = new JFrame("数据标注者");
    private DataBank db;
    private Tools tools;
    private AnalyseDialog analyseDialog;
    private JList<String> jList;
    private JPanel jPanel;
    private JPanel labelPanel;
    private JPanel ConflictPanel;
    private ArrayList<ArrayList<JRadioButton>> labelMap;
    private ArrayList<Integer> redCols = new ArrayList<>(); 
    private int index=-1;
    
    public void buildFrame() {
    	 Logger logger8 = LogManager.getLogger(AddLabelDialog.class.getName());
	        logger8.info("用户进入数据标注对话框");
    	db = DataBank.getInstence();
    	tools = new Tools(db);
    	
        //1.设置maintainFrame参数
        markFrame.setBounds(500,200,870,540);
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
        JMenuItem jMenuTitle = new JMenuItem("统计");
        jMenuTitle.addActionListener(new AnalyseActionListener(markFrame));
        jmb.add(jMenuTitle);
        markFrame.setJMenuBar(jmb);


        //3.面板评论内容
        jPanel = new JPanel(null);
        jPanel.setVisible(true);
        jPanel.setBounds(0,0,840,540);
        
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
             
                if(index < 0 || index >= len) {
//                	System.out.print(index);
                	return;
                }
                reloadLabels();
                
                jTextArea.setText(strData[index]);
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
        markFrame.setContentPane(jPanel);

    }
    
    private class ImportActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
           ImportDialog id = new ImportDialog(markFrame);
           id.show();
           
           reloadComments();
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
    
    
    private void reloadComments() {
    	DefaultListModel<String> listModel = new DefaultListModel<>();
        ArrayList<Comment> comments = db.getCommentList();
        
        redCols.clear();
        int len = comments.size();
        for(int i = 0; i < len; i++){
            listModel.addElement(comments.get(i).getContent());
            
            // 判断是否未标注
            for(int tmp : comments.get(i).getLabelList()) {
            	if(tmp < 0) {
            		redCols.add(i);
            		break;
            	}
            }
        }

        jList.setModel(listModel);
        jList.setCellRenderer(new MyRenderer(redCols, Color.RED));
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
    	// 第i个标签类
        for(int i=0; i < labelSize; i++){
        	JPanel jp1 = new JPanel(), jp2 = new JPanel();
        	jp1.setSize(145, 10);
        	jp2.setSize(145, 10);
            JLabel label = new JLabel(labelList.get(i).getContent());
            label.setVisible(true);
            jp1.add(label);
            
            ArrayList<JRadioButton> jrbList = new ArrayList<>();
            ButtonGroup BG = new ButtonGroup();
            // 第j个选项
            for(int j=0; j < labelList.get(i).getOptions().size(); j++){
                JRadioButton jrb = new JRadioButton(labelList.get(i).getOptions().get(j));
                if(labelSelect.get(i) == j) {
                	jrb.setSelected(true);
                } else {
                	jrb.setSelected(false);
                }
                jrb.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						for (int i = 0; i < labelMap.size(); ++i) {
							for (int j = 0; j < labelMap.get(i).size(); ++j) {
								if (e.getSource() == labelMap.get(i).get(j)) {
									db.getCommentList().get(index).getLabelList().set(i, j);
									
									// 更新是否红底
									boolean flag = true;
									for(int tmp : db.getCommentList().get(index).getLabelList()) {
						            	if(tmp < 0) {
						            		flag = false;
						            		break;
						            	}
						            }
									if(flag) {
										redCols.remove((Object)index);
										jList.setCellRenderer(new MyRenderer(redCols, Color.RED));
								        jList.repaint();
									}
									
									return;
								}
							}
						}
						
					}
                	
                });
                
                BG.add(jrb);
                jrbList.add(jrb);
                jp2.add(jrb);
                
                labelPanel.add(jp1);
                labelPanel.add(jp2);
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
    class MyRenderer extends DefaultListCellRenderer {
    	private Color rowcolor;
		private ArrayList<Integer> rows;

		public MyRenderer(ArrayList<Integer> rows, Color color) {
            this.rowcolor = color;
            this.rows = rows;
        }
		
		public Component getListCellRendererComponent(
				JList list, Object value,
	            int index, boolean isSelected, 
	            boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			for (int i = 0; i < rows.size(); i++) {
				if (index == rows.get(i)) {
					setBackground(this.rowcolor);
//	                setFont(getFont());
	            }
	        }
	 
	        return this;
	    }
    }
}
