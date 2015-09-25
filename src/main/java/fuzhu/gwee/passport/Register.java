package fuzhu.gwee.passport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class Register extends sgClient{

	public Register() {
       
	}

        public boolean Check(String aact, String aname, String avalue) {
		String url;

		// http://passport.9wee.com/app/check.php?act=user&username=hjy111
		// 通行证 hjy111 已被使用, 请重新指定
		// OK
		// http://passport.9wee.com/app/check.php?act=nickname&nickname=hjy112
		// http://passport.9wee.com/app/check.php?act=code&code=8879

		url = serverAddress +"app/"+ "check.php?act=" + aact + "&" + aname + "=" + avalue;
		return getResponseStr(url).equals("OK");
	}

    public String VerifyCode() {
		String url;
		//String filename = "code.bmp";
		//http://passport.9wee.com/common/verify_code.php?1442737436071
		
		url = serverAddress + "common/"+"verify_code.php?" + GetTime();
		return getResponseCookie(url, "verifyCode");
    }
	public boolean CheckUserName(String username) {
		return Check("user", "username", username);
	}
	public boolean CheckNickName(String nickname) {
		return  Check("nickname", "nickname", nickname);
	}

	public String Code() {
		String r = "";
		for (int i=0;i<3;i++){
			r = VerifyCode();
			if (Check("code","code",r)){
				break;
			}
		}
		 
		return r;
	}

	public boolean NewUser(String username, String password)  {
		String url;
		String code, str;
		boolean r = false;

		try {
			/*
			agree=yes
			code=4157
			force=
			password=	hhhh1111
			passwordcfm=	hhhh1111
			user_idcard=110101197707072913
			user_truename=张三
			user_type=	0
			username=hjy105
			*/
			r = CheckUserName(username);
			if (r) {
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				/*
					try {
						Thread.currentThread().sleep(5000);//毫秒 
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					*/
					code = Code();
					
					nvps.clear();
					nvps.add(new BasicNameValuePair("username", username));
					nvps.add(new BasicNameValuePair("password", password));
					nvps.add(new BasicNameValuePair("passwordcfm", password));
					nvps.add(new BasicNameValuePair("user_truename", "张三"));
					nvps.add(new BasicNameValuePair("user_idcard","110101197707072913"));
					nvps.add(new BasicNameValuePair("code", code));
					nvps.add(new BasicNameValuePair("agree", "yes"));
					nvps.add(new BasicNameValuePair("user_type", "0"));
					nvps.add(new BasicNameValuePair("force", ""));
					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nvps, "UTF-8");
					//*
				    str = EntityUtils.toString(entity);
//				    System.out.println(str);
					//*/
//					printCookies();
					url = serverAddress+"app/" + "register.php?ac=add";
					HttpPost httpPost = new HttpPost(url);
					httpPost.setEntity(entity);

					addRef(httpPost,"http://passport.9wee.com/app/register.php?step=2");
					CloseableHttpResponse response1;
					response1 = httpclient.execute(httpPost);
				    HttpEntity entity1 = response1.getEntity();
				    str = EntityUtils.toString(entity1);
				    //System.out.println(str);
				    //r=str.indexOf("恭喜")>=0;
				    EntityUtils.consume(entity1);
				    response1.close();
					r = !Check("user", "username", username);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return r;
	}
}
