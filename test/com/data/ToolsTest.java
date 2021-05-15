package com.data;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
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
		Label l1=new Label();
		l1.setId(0);
		l1.setContent("绗竴涓爣绛�");
		l1.getOptions().add("hello");
		l1.getOptions().add("why");
		db.addLabel(l1);
		Label l2=new Label();
		l2.setId(1);
		l2.setContent("绗簩涓爣绛�");
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
