/**
 * 
 */
package fuzhu.sg.pinyin;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author yellow
 *
 */
public class PinyinUtilTest {

	private PinyinUtil py;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		py = new PinyinUtil();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link fuzhu.sg.pinyin.PinyinUtil#getPinYin(java.lang.String)}.
	 */
	@Test
	public void testGetPinYin() {
        String cnStr = "中华人民共和国（A-C）";
        String cnPinYin = "zhonghuarenmingongheguo（A-C）";
		assertEquals(cnPinYin,py.getPinYin(cnStr));
        String cnPinYinHeadChar = "zhrmghg（A-C）";
		assertEquals(cnPinYinHeadChar,py.getPinYinHeadChar(cnStr));
		String cnASCII = "e4b8ade58d8ee4babae6b091e585b1e5928ce59bbdefbc88412d43efbc89";
		assertEquals(cnASCII,py.getCnASCII(cnStr));
	}

}
