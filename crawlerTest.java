import static org.junit.Assert.*;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class crawlerTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testRun() throws Exception {
		Crawler c = new Crawler();
		assertEquals("D:\\baka.txt", c.run());
	}

}
