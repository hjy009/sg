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
	public void testCode() {
		String code = register.Code();
		assert(code.length()==4);
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
	
}
