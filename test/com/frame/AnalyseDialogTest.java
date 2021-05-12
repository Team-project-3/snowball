package com.frame;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.data.Label;

class AnalyseDialogTest {
	AnalyseDialog dialog;

	@BeforeEach
	void setUp() throws Exception {
		ArrayList<Integer> labelSum;
		ArrayList<String> options;
		Label label;
		ArrayList<Label> labels = new ArrayList<>();
		Map<Label, ArrayList<Integer>> table = new HashMap<>();
		
		//创建label
		label = new Label();
		label.setContent("是否股票相关");

		// 创建label统计结果
		labelSum = new ArrayList<>();
		labelSum.add(100);
		labelSum.add(200);
		labelSum.add(300);
		
		// 创建标签选项
		options = new ArrayList<>();
		options.add("是");
		options.add("否");
		options.add("不知道");
		
		// 完成labels、table
		label.setOptions(options);
		labels.add(label);
		table.put(label, labelSum);
		
		
		//创建label
		label = new Label();
		label.setContent("是否股票相关呢");

		// 创建label统计结果
		labelSum = new ArrayList<>();
		labelSum.add(100);
		labelSum.add(100);
		
		// 创建标签选项
		options = new ArrayList<>();
		options.add("是的");
		options.add("否的");
				
		// 完成labels、table
		label.setOptions(options);
		labels.add(label);
		table.put(label, labelSum);
		
		dialog = new AnalyseDialog(labels, table);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testShow() throws InterruptedException {
		Frame frame = new Frame("test");
		frame.setVisible(true);
		dialog.show(frame, true);
	}

}
