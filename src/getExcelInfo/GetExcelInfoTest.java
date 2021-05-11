package getExcelInfo;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GetExcelInfoTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testReadExcel() {
        GetExcelInfo obj = new GetExcelInfo();  
        File file = new File("./评论/SH600415.xls");  
        obj.readExcel(file);  
	}

}
