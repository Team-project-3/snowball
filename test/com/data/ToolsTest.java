package com.data;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

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
		num1 = downloadData(SH600415);
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
		fail("Not yet implemented");
	}

	@Test
	void testRemoveLabel() {
		fail("Not yet implemented");
	}

	@Test
	void testAnalyse() {
		fail("Not yet implemented");
	}

}
