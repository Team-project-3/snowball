﻿package spider;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SpiderTest {

	String code="SH600415";
	/*
	@Before
	public void setUp() throws Exception {
		File src = new File("./评论/"+code+".xls");
		if(src.exists()) {
			File dest = new File("./评论/"+code+"(2).xls");
			src.renameTo(dest);
		}
	}

	@After
	public void tearDown() throws Exception {
		File f=new File("./评论/"+code+".xls");
		f.delete();
		File src = new File("./评论/"+code+"(2).xls");
		if(src.exists()) {
			File dest = new File("./评论/"+code+".xls");
			src.renameTo(dest);
		}
	}
	*/
	@Test(timeout = 600000)
	public void testRun() throws IOException, InterruptedException {
		Spider2 s = new Spider2();
		int num=s.run(code);
		assertEquals(1, num);
	}

}
