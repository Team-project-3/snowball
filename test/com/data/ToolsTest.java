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
		DataBank db = new DataBank();
		
		Tools tool = new Tools(db);
		Comment comment = new Comment();
		comment.setId(0);
		comment.setContent("第一个评论");
		Comment comment_1 = new Comment();
		comment_1.setId(1);
		comment_1.setContent("第二个评论");
		
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
		assertEquals(db.getCommentList().get(0).getContent(),"第一个评论");
		assertEquals(db.getCommentList().get(1).getContent(),"第二个评论");
		assertEquals(db.getCommentList().get(0).getLabelList().get(0),-1);
		assertEquals(db.getCommentList().get(1).getLabelList().get(0),-1);
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
