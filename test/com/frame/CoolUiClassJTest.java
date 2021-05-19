package com.frame;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;

import javax.swing.JFrame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JTextAreaOperator;

class CoolUiClassJTest {
	
	private JFrame frm;
    
    private JFrameOperator frmOpr;

	@BeforeEach
	void setUp() throws Exception {
		frm = new JFrame("CoolUiClassTest");
        frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frm.setSize(new Dimension(400, 320));
        frm.getContentPane().add(new CoolUiClass());
        frm.pack();
        frm.setVisible(true);
        
        frmOpr = new JFrameOperator("CoolUiClassTest");
	}

	@AfterEach
	void tearDown() throws Exception {
		frmOpr.dispose();
	}

	@Test
	void test() {
		JTextAreaOperator txtOpr = new JTextAreaOperator(frmOpr, 0);
        
        assertEquals("", txtOpr.getText());
        
        txtOpr.append("abcde");
        
        assertEquals("abcde", txtOpr.getText());
        
        txtOpr.clearText();
        
        assertEquals("", txtOpr.getText());
        
        JButtonOperator btnOpr = new JButtonOperator(frmOpr, "Cool");
        btnOpr.doClick();
        
        assertEquals("cool ", txtOpr.getText());
        
        txtOpr.append("cool ");
        
        assertEquals("cool cool ", txtOpr.getText());
	}

}
