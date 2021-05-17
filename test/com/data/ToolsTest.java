package com.data;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.Map;

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
	void testExportData() throws RowsExceededException, WriteException, IOException {	
		String dir = "D:\\Documents\\xueqiu\\data";
		String filename = "commentData.xls";
		Tools tool =new Tools(null);
	    tool.exportData(dir, filename);
	}

	@Test
	void testAddLabel() {
		DataBank db = new DataBank();
		
		Tools tool = new Tools(db);
		Comment comment = new Comment();
		comment.setId(0);
		comment.setContent("��һ������");
		Comment comment_1 = new Comment();
		comment_1.setId(1);
		comment_1.setContent("�ڶ�������");
		
		ArrayList<Comment> list = new ArrayList<>();
		list.add(comment);
		list.add(comment_1);
		db.setCommentList(list);
		
		Label l1=new Label();
		l1.setId(0);
		l1.setContent("第一个标签");
		ArrayList<String> options= new ArrayList<>();
		options=l1.getOptions();
		options.add("hello");
		options.add("why");
		l1.setOptions(options);
		tool.addLabel(l1);
		
		assertEquals(db.getLabelList().get(0).getId(),0);
		assertEquals(db.getLabelList().get(0).getContent(),"第一个标签");
		assertEquals(db.getLabelList().get(0).getOptions().get(0),"hello");
		assertEquals(db.getLabelList().get(0).getOptions().get(1),"why");
		
		
		assertEquals(db.getCommentList().get(0).getId(),0);
		assertEquals(db.getCommentList().get(1).getId(),1);
		assertEquals(db.getCommentList().get(0).getContent(),"��һ������");
		assertEquals(db.getCommentList().get(1).getContent(),"�ڶ�������");
		assertEquals(db.getCommentList().get(0).getLabelList().get(0),-1);
		assertEquals(db.getCommentList().get(1).getLabelList().get(0),-1);
	}

	@Test
	void testRemoveLabel() {
		DataBank db = new DataBank();
		Tools tool = new Tools(db);
		
		Comment comment = new Comment();
		comment.setId(0);
		comment.setContent("第一个评论");
		ArrayList<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);
		comment.setLabelArrayList(list);
		ArrayList<Comment> com = new ArrayList<>();
		com.add(comment);
		db.setCommentList(com);
		
		Label l1=new Label();
		l1.setId(0);
		l1.setContent("第一个标签");
		ArrayList<String> options = new ArrayList<>();
		options=l1.getOptions();
		options.add("hello");
		options.add("why");
		l1.setOptions(options);
		
		Label l2=new Label();
		l2.setId(1);
		l2.setContent("第二个标签");
		ArrayList<String> options_1 = new ArrayList<>();
		options_1=l2.getOptions();
		options_1.add("what");
		options_1.add("and you");
		l2.setOptions(options_1);
		ArrayList<Label> lab = new ArrayList<>();
		lab.add(l1);
		lab.add(l2);
		db.setLabelList(lab);
		
		tool.removeLabel(l1);
		for(int i = 0 ; i < db.getLabelList().size() ; i++) {
			assertNotEquals(db.getLabelList().get(i).getId(),l1.getId());
			assertNotEquals(db.getLabelList().get(i).getContent(),l1.getContent());
			assertNotEquals(db.getLabelList().get(i).getOptions().get(0),l1.getOptions().get(0));
			assertNotEquals(db.getLabelList().get(i).getOptions().get(1),l1.getOptions().get(1));
		}
		for(int i = 0 ; i < db.getCommentList().get(0).getLabelList().size() ; i++) {
			assertNotEquals(db.getCommentList().get(0).getLabelList().get(i),1);
		}
	}

	@Test
	void testAnalyse() {
		DataBank db = new DataBank();
		
		Comment c1 = new Comment();
		c1.setContent("comment 1");
		
		com.data.Label l1 = new com.data.Label();
		l1.setContent("label 1");
		
		l1.getOptions().add("是");
		l1.getOptions().add("否");

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
