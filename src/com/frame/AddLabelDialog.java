package com.frame;

import java.awt.Container;
import java.awt.Dialog.ModalityType;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.data.Label;

public class AddLabelDialog {
	private static Label label1;
	
	
	public AddLabelDialog() {
		
	}

	// 该方法显示对话框
	public static void show() {
		  //1.弹出对话框
        JDialog jDialog;
        Frame maintainFrame = null;

		jDialog = new JDialog(maintainFrame,"添加标签",true);
        jDialog.setBounds(600,250,300,400);
        

        //2.设置对话框面板内容
        JPanel jPanel = new JPanel();
   
        jPanel.setSize(300,300);
        jPanel.setLayout(null);
   
       JTextField labelName = new JTextField(10);
        labelName.setLocation(5,10);
        labelName.setHorizontalAlignment(JTextField.LEFT);
        JLabel labName = new JLabel("标签 ");
       
     
        jPanel.add(labName);
        jPanel.add(labelName);
        
        
      
       
        JButton yes = new JButton("添加");
       
        
    //    JPanel jp1 = new JPanel();
      //  jp1.setSize(300,400);
        

    //    jp1.add(jPanel);
       
       // jDialog.add(jp1);
        jDialog.add(jPanel);
        
        JTextField options1= new JTextField(10);
        JTextField options2= new JTextField(10);
        labelName.setBounds(10, 0, 100, 50);
        yes.setBounds(10, 50, 100, 50);
        options1.setBounds(10, 100, 100, 50);
        options2.setBounds(10, 150, 100, 50);
        jPanel.add(options1);
        jPanel.add(options2);
        jPanel.add(yes);
    
 
	    jDialog.setVisible(true);		
	    
	    yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jDialog.show(false);
                String Labelcontent = labelName.getText();
                label1 = new Label();
                label1.setContent(Labelcontent);
                System.out.println(label1.getContent());
                
                String Labeloptions1 = options1.getText();
                String Labeloptions2 = options2.getText();
                label1.getOptions().add(Labeloptions1);
                label1.getOptions().add(Labeloptions2);
                
                System.out.println(label1.getOptions()) ; 
            }
        });
        
	}
	
	// 该方法获得要添加的标签的信息
	public static Label getLabel() {
		return label1;
	}
	
}
