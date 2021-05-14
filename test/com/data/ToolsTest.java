package com.data;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

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
	void testExportData() throws RowsExceededException, WriteException, IOException {	
		String dir = "D:\\Documents\\xueqiu\\data";
		String filename = "commentData.xls";
		Tools tool =new Tools(null);
	    tool.exportData(dir, filename);
	}

	@Test
	void testAddLabel() {
		DataBank db = new DataBank();
		Label l1=new Label();
		l1.setId(0);
		l1.setContent("第一个标签");
		l1.getOptions().add("hello");
		l1.getOptions().add("why");
		db.addLabel(l1);
		
		assertEquals(db.getLabelList().get(0).getId(),0);
		assertEquals(db.getLabelList().get(0).getContent(),"第一个标签");
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
		
		com.data.Label l1 = new com.data.Label();
		l1.setContent("label 1");
<<<<<<< HEAD
		l1.getOptions().add("閺勶拷");
		l1.getOptions().add("閸氾拷");
=======
		l1.getOptions().add("是");
		l1.getOptions().add("否");
>>>>>>> 79857c4ecc88080705134fda40b95ddca8c6966d

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
