package com.frame;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DownloadDialogTest {
	private ManagerDialog dialog;
	
	
	@BeforeEach
	void setUp() throws Exception {
		dialog = new ManagerDialog();
		
		Map<String, String> map = new HashMap();
		map.put("股票1", "下载中");
		
		dialog.setDownLoadList(map);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testShow() {
		JFrame frame = new JFrame();
		dialog.show(frame);
	}

	@Test
	void testGetDownloadID() {
//		fail("Not yet implemented");
	}

}
