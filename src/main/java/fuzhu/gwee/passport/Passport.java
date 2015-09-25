package fuzhu.gwee.passport;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class Passport extends sgClient{

	public String username;
	public String password;
	public boolean isLogin;

	public Passport()  {
		username = "";
		password = "";
		isLogin = false;
	}

	public Passport(String username, String password) {
		httpclient = HttpClients.createDefault();
		this.username = username;
		this.password = password;
		isLogin = false;
	}
	public boolean Login() {
		String url,ref,str;
		
		
		url = "http://passport.9wee.com/app/login.php?ac=login";
		ref = "http://www.9wee.com/";
		str = postResponseCookie(url, ref, 
				"_REFERER","http://passport.9wee.com/app/user_info.php",
				"username", username,
				"password", password
				);
		
		if(str.length()>0){
			isLogin = true;
		}else{
			isLogin = false;
		}
		
		return isLogin;

	}

	public boolean logout() {
		String url,ref,cookie,str;

		url = "http://passport.9wee.com/app/logout.php";
		ref = "http://www.9wee.com/";
		cookie = getResponseCookie(url, ref, "passportSession");
		refreshCookies();
		if(passportSession.length()==0){
			isLogin = false;
		}
		return !isLogin;
	}
}
