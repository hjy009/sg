package fuzhu.gwee.passport;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PassportTest {
	Passport passport;
	@Before
	public void setUp() throws Exception {
		passport = new Passport();
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testLogin() throws ClientProtocolException, IOException {
		passport.username = "hjy140";
		passport.password = "bbbb1111";
		passport.Login();
		assertEquals(true,passport.loginFlag);
		passport.logout();
		assertEquals(false,passport.loginFlag);
		
	}
	
	@Test
	public void testLogins() throws ClientProtocolException, IOException {
		BufferedReader in;
		String line;
		int from,to;
		try {
			in = new BufferedReader(new FileReader("人员信息表.csv"));
			while ((line = in.readLine()) != null){
				from = 0;
				to = line.indexOf(",");
				passport.username = line.substring(from, to);
				passport.password = line.substring(to+1,line.length());
				if (passport.Login()){
					System.out.println(passport.username+",login,ok");
					passport.logout();
				} else {
					System.out.println(passport.username+",login,false");
				}
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
	public void testReg() throws ClientProtocolException, IOException {
		boolean r;
		passport.username = "hjy108";
		passport.password = "bbbb1111";
		passport.Login();
		assertEquals("login",true,passport.loginFlag);
		
		r = passport.reg("wz24.sg.9wee.com",1,3,15);
		assertEquals("reg",true,r);
		
		passport.logout();
		assertEquals("logout",false,passport.loginFlag);
		
	}
	
}
