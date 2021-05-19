package com.frame;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.data.Label;


class RemoveLabelDialogTest {
	RemoveLabelDialog dialog;

	@BeforeEach
	void setUp() throws Exception {
		ArrayList<Label> labels = new ArrayList<>();
		
		Label label = new Label();
		label.setContent("标签1");
		ArrayList<String> options = new ArrayList<>();
		options.add("1");
		options.add("2");
		label.setOptions(options);
		labels.add(label);
		
		label = new Label();
		label.setContent("标签2");
		options = new ArrayList<>();
		options.add("3");
		options.add("4");
		label.setOptions(options);
		labels.add(label);
		
		dialog = new RemoveLabelDialog(labels);
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
		dialog.show(null, true);
		Label label = dialog.getLabel();
		
		label.getContent();
		
		Assert.assertEquals("标签2", label.getContent());
		Assert.assertEquals("3", label.getOptions().get(0));
		Assert.assertEquals("4", label.getOptions().get(1));
	}

}
