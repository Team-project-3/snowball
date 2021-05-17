package com.frame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.Assert;

class DownloadDialogTest {
	DownloadDialog dialog;

	@BeforeEach
	void setUp() throws Exception {
		dialog = new DownloadDialog();


	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testShow() {
		dialog.show(null);
	}

	@Test
	void testGetDownloadID() {
		dialog.show(null);
		String downloadID = dialog.getDownloadID();
		
		Assert.assertEquals("AB123456", downloadID);
	}

}
