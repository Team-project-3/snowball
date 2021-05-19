package com.frame;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JDialogOperator;
import org.netbeans.jemmy.operators.JTextAreaOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;

import com.data.Label;

class AddLabelDialogTest {
	AddLabelDialog dialog;
    JDialogOperator dlgOpr;

	@BeforeEach
	void setUp() throws Exception {
		dialog = new AddLabelDialog();
	}

	@AfterEach
	void tearDown() throws Exception {
		
	}

	@Test
	void testShow() {
		dialog.show(null, true);
	}

	@Test
	void testGetLabel() throws InterruptedException {
		dialog.show(null, false);
		
		dlgOpr = new JDialogOperator("添加标签");
		JTextFieldOperator labelOpr = new JTextFieldOperator(dlgOpr, 0);
		
		Assert.assertEquals("", labelOpr.getText());
		labelOpr.setText("是否与股票相关");
		Assert.assertEquals("是否与股票相关", labelOpr.getText());
		
		JTextAreaOperator optionOpr = new JTextAreaOperator(dlgOpr, 0);
		Assert.assertEquals("", optionOpr.getText());
		optionOpr.append("是\n否");
		Assert.assertEquals("是\n否", optionOpr.getText());
		
		JButtonOperator btnOpr = new JButtonOperator(dlgOpr, "添加");
		btnOpr.doClick();
		
		Label labeltest = dialog.getLabel();
		Assert.assertEquals("是否与股票相关", labeltest.getContent());
		Assert.assertEquals("是", labeltest.getOptions().get(0));
		Assert.assertEquals("否", labeltest.getOptions().get(1));
	}

}
