package com.data;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

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
		fail("Not yet implemented");
	}

	@Test
	void testRemoveLabel() {
		DataBank db = new DataBank();
		Label l1=new Label();
		l1.setId(0);
		l1.setContent("第一个标签");
		l1.getOptions().add("hello");
		l1.getOptions().add("why");
		db.addLabel(l1);
		Label l2=new Label();
		l2.setId(1);
		l2.setContent("第二个标签");
		l2.getOptions().add("what");
		l2.getOptions().add("and you");
		db.addLabel(l2);
		db.removeLabel(l1);
		for(int i = 0 ; i < db.getLabelList().size() ; i++) {
			assertNotEquals(db.getLabelList().get(i).getId(),l1.getId());
			assertNotEquals(db.getLabelList().get(i).getContent(),l1.getContent());
			assertNotEquals(db.getLabelList().get(i).getOptions().get(0),l1.getOptions().get(0));
			assertNotEquals(db.getLabelList().get(i).getOptions().get(1),l1.getOptions().get(1));
		}
		db.addLabel(l1);
		db.removeLabel(l2);
		for(int i = 0 ; i < db.getLabelList().size() ; i++) {
			assertNotEquals(db.getLabelList().get(i).getId(),l2.getId());
			assertNotEquals(db.getLabelList().get(i).getContent(),l2.getContent());
			assertNotEquals(db.getLabelList().get(i).getOptions().get(0),l2.getOptions().get(0));
			assertNotEquals(db.getLabelList().get(i).getOptions().get(1),l2.getOptions().get(1));
		}
	}

	@Test
	void testAnalyse() {
		DataBank db = new DataBank();
		
		Comment c1 = new Comment();
		c1.setContent("comment 1");
		
		Label l1 = new Label();
		l1.setContent("label 1");
		l1.getOptions().add("鏄�");
		l1.getOptions().add("鍚�");

		c1.getLabelList().add(1);
				
		db.addComment(c1);
		db.addLabel(l1);
		
		Tools tools = new Tools(db);
		ArrayList<Integer> result = tools.analyse(l1);
		
		assertEquals(result.get(0), 0);
		assertEquals(result.get(1), 1);
		assertEquals(result.size(), 2);
	}

}
