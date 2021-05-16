package com.frame;

import  java.awt.Dimension;
import  java.awt.GridBagConstraints;
import  java.awt.GridBagLayout;
import  java.awt.Insets;
import  java.awt.event.ActionEvent;
import  java.awt.event.ActionListener;

import  javax.swing.JButton;
import  javax.swing.JFrame;
import  javax.swing.JPanel;
import  javax.swing.JScrollPane;
import  javax.swing.JTextArea;

/**
* @author Administrator
*/
public   class  CoolUiClass  extends  JPanel
{
   private JTextArea msgTxt = new JTextArea(400, 320);
   
   private JButton coolBtn = new JButton("Cool");
   
   public CoolUiClass()
   {
       this.setLayout(new GridBagLayout());
       this.setMinimumSize(new Dimension(400, 350));
       this.setPreferredSize(new Dimension(400, 350));
       
       coolBtn.setMinimumSize(new Dimension(80, 21));
       coolBtn.setPreferredSize(new Dimension(80, 21));
       
       JScrollPane scrollPane = new JScrollPane(msgTxt);
       scrollPane.setMinimumSize(new Dimension(400, 320));
       scrollPane.setPreferredSize(new Dimension(400, 320));
       
       this.add(scrollPane, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
       this.add(coolBtn, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
       
       coolBtn.addActionListener(new ActionListener()
       {

           public void actionPerformed(ActionEvent arg0)
           {
               appendText();
           }
           
       });
   }

   protected void appendText()
   {
       msgTxt.append("cool ");
   }

   /**
    * test the calss in main,use your eye to test.
    * @param args
    */
   public static void main(String[] args)
   {
       JFrame frm = new JFrame("CoolUiClass");
       frm.getContentPane().add(new CoolUiClass());
       frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       frm.pack();
       frm.setVisible(true);
   }

}