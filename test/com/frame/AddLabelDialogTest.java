package com.frame;

import java.awt.Frame;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.data.Label;

class AddLabelDialogTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testShow() {
		AddLabelDialog dialog = new AddLabelDialog();
		Frame frame = new Frame();
		
		dialog.show(frame);
	}

	@Test
	void testGetLabel() {
		AddLabelDialog dialog = new AddLabelDialog();
		Frame frame = new Frame();
		
		dialog.show(frame);
		
		Label labeltest = dialog.getLabel();
		Assert.assertEquals("测试", labeltest.getContent());
		Assert.assertEquals("hello", labeltest.getOptions().get(0));
		Assert.assertEquals("world", labeltest.getOptions().get(1));
	}

}
