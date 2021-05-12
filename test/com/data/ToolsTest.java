package com.data;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ToolsTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testDownloadData() {
		Tools t = new Tools(null);
		int num1=t.downloadData("SH600415");
		assertEquals(1, num1);
	}

	@Test
	void testGetDownloading() {
		fail("Not yet implemented");
	}

	@Test
	void testImportData() {
		fail("Not yet implemented");
	}

	@Test
	void testExportData() {
		fail("Not yet implemented");
	}

	@Test
	void testAddLabel() {
		DataBank db = new DataBank();
		Label l1=new Label();
		l1.setId(0);
		l1.setContent("��һ����ǩ");
		l1.getOptions().add("hello");
		l1.getOptions().add("why");
		db.addLabel(l1);
		
		assertEquals(db.getLabelList().get(0).getId(),0);
		assertEquals(db.getLabelList().get(0).getContent(),"��һ����ǩ");
		assertEquals(db.getLabelList().get(0).getOptions().get(0),"hello");
		assertEquals(db.getLabelList().get(0).getOptions().get(1),"why");
	}

	@Test
	void testRemoveLabel() {
		fail("Not yet implemented");
	}

	@Test
	void testAnalyse() {
		DataBank db = new DataBank();
		
		Comment c1 = new Comment();
		c1.setContent("comment 1");
		
		Label l1 = new Label();
		l1.setContent("label 1");
		l1.getOptions().add("��");
		l1.getOptions().add("��");

		c1.getLabelList().add(1);
				
		db.addComment(c1);
		db.addLabel(l1);
		
		Tools tools = new Tools(db);
		Map<Label, ArrayList<Integer>> table = tools.analyse();
		
		assertEquals(table.get(l1).get(0), 0);
		assertEquals(table.get(l1).get(1), 1);
		assertEquals(table.get(l1).size(), 2);
		assertEquals(table.size(), 1);
	}

}
