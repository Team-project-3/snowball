package com.frame;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.data.Label;

public class RemoveLabelDialog {
    private JDialog jDialog;
    private ArrayList<Label> labels;
    private Label label;
    private JButton yes;

	public RemoveLabelDialog(ArrayList<Label> labels) {
		this.labels = labels;
	}

	// 该方法显示对话框
	public void show(Frame frame, boolean model) {
		 Logger logger9 = LogManager.getLogger(AddLabelDialog.class.getName());
	        logger9.info("用户进入删除标签对话框");
		//1.弹出对话框
        jDialog = new JDialog(frame, "删除标签", model);
        jDialog.setBounds(600,250,300,400);
        

        //2.设置对话框面板内容
        JPanel jPanel = new JPanel();
        jPanel.setSize(300,400);
        jPanel.setBounds(0,0,720,540);

        JComboBox<String> jComboBox = new JComboBox<String>();
        for (Label label : labels) {
        	jComboBox.addItem(label.getContent());
        }
        jComboBox.setSize(200, 20);
        label = labels.get(0);
        jComboBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					 int index = jComboBox.getSelectedIndex();
					 label = labels.get(index);
				}
			}
        	
        });
        
        yes = new JButton("删除");
        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jDialog.setVisible(false);;
            }
        });
        
        // jPanel.setLayout(new GridLayout(2,1));
        jPanel.add(yes);
        jPanel.add(jComboBox);
        jDialog.add(jPanel);
        
        jDialog.setVisible(true);
	}
		
	// 该方法获得要删除的标签的信息
	public Label getLabel() {
		return label;
	}
}
