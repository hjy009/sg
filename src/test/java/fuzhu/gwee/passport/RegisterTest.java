package fuzhu.gwee.passport;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
		// 通行证 hjy111 已被使用, 请重新指定
		assertEquals(false, register.CheckUserName("hjy111"));
	}

	@Test
	public void testconvertUnicode(){
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
			//decode = register.convertUnicode(codename);
			//decode = new JSONTokener(codename).nextValue().toString();
		
		
	}
	
	@Test
	public void testCode() {
		String code = register.Code();
		assert(code.length()>0);
	}

	@Test
	public void testNewUser() {
		assertEquals(true, register.NewUser("hjy145", "bbbb1111"));
	}

	@Test
	public final void testNewUsers() {
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader("注册信息表.csv"));
			String s = new String();
			while ((s = in.readLine()) != null)
				if (register.CheckUserName(s)){
					if (register.NewUser(s, "bbbb1111")){
						System.out.println(s+",ok");
					} else {
						System.out.println(s+",false");
					}
				} else {
					System.out.println(s+",false");
				}
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSetCWSSESSID() {
		assert(register.SetCWSSESSID().length()>0);
	}

}
