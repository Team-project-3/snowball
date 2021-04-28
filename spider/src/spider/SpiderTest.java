package spider;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class SpiderTest {
	
	private static Spider spider = new Spider();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	//@Ignore
	public void testMain() throws IOException, InterruptedException {
		spider.main(null);
		assertEquals(1, spider.getResult());
	}

	@Test
	public void testCheck() throws IOException {
		spider.check();
		assertEquals(2, spider.getCheck());
	}

}
