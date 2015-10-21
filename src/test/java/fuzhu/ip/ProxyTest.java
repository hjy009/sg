package fuzhu.ip;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

public class ProxyTest {
	
	private Proxy proxy;

	@Before
	public void setUp() throws Exception {
//		proxy = new Proxy("183.207.232.119",8080);
		proxy = new Proxy("183.207.232.194",8080);
	}
	
	@Test
	public void testIsAlive() {
		try {
			boolean b = proxy.isAlive();
			assertTrue(b );
			double d = proxy.getDelay();
			assertTrue(d>0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetGeo() {
		String ct;
		try {
			ct = proxy.getCountry();
			assertEquals("", ct);
			ct = proxy.getCity();
			assertEquals("", ct);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetAnonLevel() {
		try {
			int l = proxy.getAnonLevel();
			assertTrue(l==5);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
