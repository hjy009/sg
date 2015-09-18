package fuzhu.ip;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Before;
import org.junit.Test;

public class IP138Test {
	protected IP138 ip=new IP138();
	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testMyip() throws ClientProtocolException, IOException {
		assertEquals("61.132.89.154",ip.myip());
	}

	@Test
	public void testipLocate() throws ClientProtocolException, IOException {
		assertEquals("江苏省常州市 电信",ip.ipLocate("61.132.89.154"));
	}
}
