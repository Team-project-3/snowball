package com.data;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.junit.Ignore;
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

	//@Test
	@Ignore
	void testDownloadData() throws InterruptedException {
		Tools t = new Tools(null);
		t.downloadData("SZ002353");
		t.downloadData("SH600415");
		TimeUnit.SECONDS.sleep(30);//让主线程睡个30秒，不然主线程直接结束，测试失败

	}

	@Test
	void testGetDownloading() throws InterruptedException {
		Tools t = new Tools(null);
		System.out.println(t.getDownloading());
		t.downloadData("SZ002353");
		System.out.println(t.getDownloading());
		TimeUnit.SECONDS.sleep(30);//让主线程睡个30秒，不然主线程直接结束，测试失败
		System.out.println(t.getDownloading());
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
