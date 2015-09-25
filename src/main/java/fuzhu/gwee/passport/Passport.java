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
		String url;

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("_REFERER",
				"http://passport.9wee.com/app/user_info.php"));
		nvps.add(new BasicNameValuePair("username", username));
		nvps.add(new BasicNameValuePair("password", password));
		//http://passport.9wee.com/app/login.php?ac=login
		url = "http://passport.9wee.com/app/login.php?ac=login";

		HttpPost httppost = new HttpPost(url);
			try {
				httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
				httppost.setHeader("Referer","http://passport.9wee.com/app/login.php");
				httpclient.execute(httppost);

				CloseableHttpResponse response1;
				response1 = httpclient.execute(httppost);
				
				Header h1 = response1.getFirstHeader("Set-Cookie");
				System.out.println(h1.toString());
				if (h1.toString().indexOf("passportSession")>0) {
					isLogin = true;
				} else {
					isLogin = false;
				}
				
				/*
				url = "http://passport.9wee.com/app/user_info.php";
				responseBody = httpclient.doget(url);
				isLogin = (responseBody.indexOf("欢迎您回到") >= 0);
				*/
					} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	return isLogin;

	}

	public void logout() {
		String url,str;
		Cookie cookie;

		url = "http://passport.9wee.com/app/logout.php";
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Referer","http://passport.9wee.com/app/user_info.php");
		CloseableHttpResponse response1;
		try {
			response1 = httpclient.execute(httpGet);
		    HttpEntity entity1 = response1.getEntity();
			if (response1.getStatusLine().getReasonPhrase().equals("OK")) {
				str = EntityUtils.toString(entity1);
//				System.out.println(str);
				isLogin = !str.equals("<script type='text/javascript'>window.location = '/app/login.php';</script>");
			} 
		    // do something useful with the response body
		    // and ensure it is fully consumed
//		    InputStream in = entity1.getContent();
//		    System.out.println(EntityUtils.toString(entity1,"GB2312"));
		    //System.out.println(in.toString());

		    //System.out.println(EntityUtils.toString(entity1));
		    EntityUtils.consume(entity1);
		    response1.close();
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
