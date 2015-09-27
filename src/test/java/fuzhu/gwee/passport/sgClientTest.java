package fuzhu.gwee.passport;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
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
	public void testJSON() {
		String nickname,codename,decode;
		
		nickname="{\"loginFlag\":true,\"uid\":\"131213093\",\"username\":\"hjy140\",\"nickname\":\"张三\",\"usertype\":\"0\",\"email\":\"\",\"urb\":\"1977-07-07\",\"mac\":\"d45fc3e881be83c82db6cfdb49a6c056\"}";
		codename="%7B%22loginFlag%22%3Atrue%2C%22uid%22%3A%22131213093%22%2C%22username%22%3A%22hjy140%22%2C%22nickname%22%3A%22%5Cu5f20%5Cu4e09%22%2C%22usertype%22%3A%220%22%2C%22email%22%3A%22%22%2C%22urb%22%3A%221977-07-07%22%2C%22mac%22%3A%22d45fc3e881be83c82db6cfdb49a6c056%22%7D";
//		JSONArray jsonarray = new JSONArray(nickname);
		JSONObject jsonobj = new JSONObject(nickname);
        decode = jsonobj.getString("nickname");  
		System.out.println("json:" + decode);
//		assertEquals(true, decode.equals(nickname));
	}

	@Test
	public void testConvertUnicode() {
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
//				System.out.println("getBytes:" + decode);
				assertEquals(true, decode.equals(nickname));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
/**/
			
			//codename = "\""+codename+"\"";
			try {
				nickname="{\"loginFlag\":true,\"uid\":\"131213093\",\"username\":\"hjy140\",\"nickname\":\"张三\",\"usertype\":\"0\",\"email\":\"\",\"urb\":\"1977-07-07\",\"mac\":\"d45fc3e881be83c82db6cfdb49a6c056\"}";
				codename="%7B%22loginFlag%22%3Atrue%2C%22uid%22%3A%22131213093%22%2C%22username%22%3A%22hjy140%22%2C%22nickname%22%3A%22%5Cu5f20%5Cu4e09%22%2C%22usertype%22%3A%220%22%2C%22email%22%3A%22%22%2C%22urb%22%3A%221977-07-07%22%2C%22mac%22%3A%22d45fc3e881be83c82db6cfdb49a6c056%22%7D";
				//nickname = "王虎0mvj";
				//codename = "\u738b\u864e0mvj";
				decode = java.net.URLDecoder.decode(codename,"UTF8");
				System.out.println("decode:" + decode);
				decode = sgClient.convertUnicode(decode);
//				decode = java.net.URLDecoder.decode(decode1,"UTF8");
				System.out.println("conver:" + decode);
				assertEquals(true, decode.equals(nickname));
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	}

	@Test
	public void testURLEncodedUtils() {
		
		String url = "http://wz24.sg.9wee.com/reg.php?user_nickname=%E5%BC%A0%E4%B8%89&x=44&y=28&c=3&g=15&send=1&area=1";
		
		try {
			URI uri = new URIBuilder()
			        .setScheme("http")
			        .setHost("wz24.sg.9wee.com")
			        .setPath("/reg.php")
			        .setParameter("user_nickname", "张三")
			        .setParameter("x", "44")
			        .setParameter("y", "28")
			        .setParameter("c", "3")
			        .setParameter("g", "15")
			        .setParameter("send", "1")
			        .setParameter("area", "1")
			        .build();
//			System.out.println(uri.toString());
//			System.out.println(url);
			assertEquals("uri",true, url.equals(uri.toString()));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
