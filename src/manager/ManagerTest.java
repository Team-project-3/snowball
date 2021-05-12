﻿package manager;

import static org.junit.Assert.*;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class ManagerTest {
	
	String code1="SH600415";
	String code2="SZ002353";
	String code[]= {"SH600415","SZ002353"};

	@Before
	//@Ignore
	public void setUp() throws Exception {
		for(int i=0;i<2;i++) {
			File src = new File("./评论/"+code[i]+".xls");
			if(src.exists()) {
				File dest = new File("./评论/"+code[i]+"(2).xls");
				src.renameTo(dest);
			}
		}
	}

	@After
	//@Ignore
	public void tearDown() throws Exception {
		for(int i=0;i<2;i++) {
			File f=new File("./评论/"+code[i]+".xls");
			f.delete();
			File src = new File("./评论/"+code[i]+"(2).xls");
			if(src.exists()) {
				File dest = new File("./评论/"+code[i]+".xls");
				src.renameTo(dest);
			}
		}
	}
	

	@Test
	public void testManager() {

		Manager t1 = new Manager(code[0]);
		Manager t2 = new Manager(code[1]);
		t1.start();
		t2.start();
		
		try {
			//t1.join();
			t2.join();
			TimeUnit.SECONDS.sleep(1);
            t1.pauseThread();
            TimeUnit.SECONDS.sleep(10);
            t1.resumeThread();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int num1 = t1.getResult();
		int num2 = t2.getResult();
		//System.out.println(code[0]+" "+num1);
		//System.out.println(code[1]+" "+num2);
		
		assertEquals(1, num1*num2);
	}


}
