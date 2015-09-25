package fuzhu.gwee.passport;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class sgClientTest {
	sgClient sgclient;

	@Before
	public void setUp() throws Exception {
		sgclient = new sgClient();
	}

	@Test
	public void testSetCWSSESSID() {
		assert(sgclient.SetCWSSESSID().length()>0);
	}

	@Test
	public void testConvertUnicode() {
		String str;
		Date date;
		String nickname,codename,encode,decode;

		/*		
		date = new Date();
		long now = System.currentTimeMillis();
		str = String.valueOf(now);
		System.out.println("date:" + date);
		System.out.println("TimeMillis:" + str);

		// https://passport.9wee.com/common/verify_code?1246865246411
		str = register.VerifyCode(str);
		System.out.println("verify_code:" + str);

		// function SetCookie ( name, value )
		// 8{
		// 9 expires = new Date();
		// 10 expires.setTime(expires.getTime() + (1000 * 86400 * 365));
		// 11 document.cookie = name + "=" + escape(value) + "; expires=" +
		// expires.toGMTString() + "; path=/";
		// 12}
*/
		
/**/
			
			try {
				nickname = "万骨哭城";
				codename = "%E4%B8%87%E9%AA%A8%E5%93%AD%E5%9F%8E";
				decode = java.net.URLDecoder.decode(codename,"UTF-8");
//				System.out.println("utf8:" + decode);
				assertEquals(true, decode.equals(nickname));
				encode = java.net.URLEncoder.encode(nickname,"UTF-8");
//				System.out.println("utf8:" + encode);
				assertEquals(true, encode.equals(codename));
				encode = java.net.URLEncoder.encode(nickname,"unicode");
//				System.out.println("unicode:" + encode);
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
/**/
			
			try {
				nickname = "登录次数";
				codename = "\u767b\u5f55\u6b21\u6570";
				decode = java.net.URLDecoder.decode(codename,"UTF8");
//				System.out.println("UTF8:" + decode);
				decode = java.net.URLDecoder.decode(codename,"unicode");
//				System.out.println("unicode:" + decode);
				decode = new String(codename.getBytes("UTF-8"),  "utf-8");
//				System.out.println("unicode:" + decode);
				assertEquals(true, decode.equals(nickname));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
/**/
			
			//codename = "\""+codename+"\"";
			try {
				nickname = "王虎0mvj";
				codename = "\u738b\u864e0mvj";
				decode = java.net.URLDecoder.decode(codename,"UTF8");
//				System.out.println("decode:" + decode);
				assertEquals(true, decode.equals(nickname));
				decode = new String(codename.getBytes("UTF-8"),  "utf-8");
//				System.out.println("unicode:" + decode);
				assertEquals(true, decode.equals(nickname));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//decode = sgclient.convertUnicode(codename);
			//decode = new JSONTokener(codename).nextValue().toString();
		
		
	}

}
