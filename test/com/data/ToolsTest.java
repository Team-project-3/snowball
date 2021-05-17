package com.data;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jxl.read.biff.BiffException;
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
	void testImportData() throws BiffException, IOException {
		String filename = "D:\\Documents\\xueqiu\\data\\commentData.xls";
		Tools tool =new Tools(null);
		tool.importData(filename);
	    
	}

	@Test
	void testExportData() throws RowsExceededException, WriteException, IOException, BiffException {	
		testImportData();
		String dir = "D:\\Documents\\xueqiu\\newData";
		String filename = "commentData.xls";
		Tools tool =new Tools(null);
	    tool.exportData(dir, filename);
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
		
		com.data.Label l1 = new com.data.Label();
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
