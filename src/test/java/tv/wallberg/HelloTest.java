package tv.wallberg;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HelloTest {

	@Test
	void test() throws Exception {
		assertEquals("earth", new Hello().getPlanet());
	}

}
