package fuzhu.gwee.passport;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONTokener;
import org.junit.Before;
import org.junit.Test;

import fuzhu.gwee.passport.Register;

public class RegisterTest {
	Register register;

	@Before
	public void setUp() throws Exception {
		register = new Register();
	}

	@Test
	public void testCheck() {
		// https://passport.9wee.com/check?act=user&username=hjy111
		// 通行证 hjy111 已被使用, 请重新指定
		assertEquals(false, register.Check("user", "username", "hjy111"));
		// OK
		assertEquals(true, register.Check("user", "username", "hjy911"));
		// https://passport.9wee.com/check?act=nickname&nickname=hjy112
		assertEquals(true, register.Check("nickname", "nickname", "hjy911"));
		// https://passport.9wee.com/common/verify_code?1246865246411
	}

	@Test
	public void testCheckUserName() {
		fail("Not yet implemented");
	}

	@Test
	public void testVerifyCode() {
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
		
/*
			nickname = "万骨哭城";
			codename = "%E4%B8%87%E9%AA%A8%E5%93%AD%E5%9F%8E";
			
			decode = java.net.URLDecoder.decode(codename,"UTF-8");
			System.out.println("decode:" + decode);
			assertEquals(true, decode.equals(nickname));
			encode = java.net.URLEncoder.encode(nickname,"UTF-8");
			System.out.println("encode:" + encode);
			assertEquals(true, encode.equals(codename));
			
			nickname = "登录次数";
			codename = "\u767b\u5f55\u6b21\u6570";
			
			decode = java.net.URLDecoder.decode(codename,"UTF-8");
			System.out.println("decode:" + decode);
			assertEquals(true, decode.equals(nickname));
*/
			nickname = "王虎0mvj";
			codename = "\\u738b\\u864e0mvj";
			
			//codename = "\""+codename+"\"";
			decode = register.convertUnicode(codename);
			System.out.println("decode:" + decode);
			assertEquals(true, decode.equals(nickname));
//			decode = new JSONTokener(codename).nextValue().toString();
		
		
	}

	@Test
	public void testNewUser() {
		fail("Not yet implemented");
	}

}
