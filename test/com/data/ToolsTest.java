package com.data;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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
	void testDownloadData() throws InterruptedException {
		Tools t = new Tools(null);
		t.downloadData("SZ002353");
		t.downloadData("SH600415");
		TimeUnit.SECONDS.sleep(30);
		t.getDownloading("1");
		//int num1= 
		//int num2
		//assertEquals(1, num1);
		//assertEquals(1, num2);
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
		fail("Not yet implemented");
	}

	@Test
	void testAnalyse() {
		DataBank db = new DataBank();
		
		Comment c1 = new Comment();
		c1.setContent("comment 1");
		
		Label l1 = new Label();
		l1.setContent("label 1");
		l1.getOptions().add("是");
		l1.getOptions().add("否");

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
