package com.frame;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.frame.*;
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
		fail("Not yet implemented");
	}

	@Test
	void testGetLabel() {
		AddLabelDialog dialog = new AddLabelDialog();
		dialog.show(null);
		Label labeltest = dialog.getLabel();
		String ceshi="测试";
		ArrayList<String> array=new ArrayList<String>();
		array.add("hello");
		array.add("world");
		Label expecteds = new Label(0,ceshi,array);
		Assert.assertEquals(ceshi, labeltest.getContent());
	}

}
