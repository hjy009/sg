package fuzhu.gwee.passport;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.yccheok.numrecognition.FeatureParameter;
import org.yccheok.numrecognition.NumberImageFeatureFactory;
import org.yccheok.numrecognition.NumberImageLRTBHVFeatureFactory;
import org.yccheok.numrecognition.NumberNeuralNetworkRecognizer;

public class Register extends sgClient{

	public Register() {
       
	}

        public boolean Check(String aact, String aname, String avalue) {
		String str;
		String url;
		boolean r = false;

		// http://passport.9wee.com/app/check.php?act=user&username=hjy111
		// 通行证 hjy111 已被使用, 请重新指定
		// OK
		// http://passport.9wee.com/app/check.php?act=nickname&nickname=hjy112
		// http://passport.9wee.com/app/check.php?act=code&code=8879

		url = serverAddress +"app/"+ "check.php?act=" + aact + "&" + aname + "=" + avalue;
/*
		responseBody = httpsession.doget(url);
		if (responseBody.equals("OK")) {
			r = true;
		} else {
			r = false;
		}
		*/
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response1 = null;
		try {
			response1 = httpclient.execute(httpGet);
		    HttpEntity entity1 = response1.getEntity();
			if (response1.getStatusLine().getReasonPhrase().equals("OK")) {
				str = EntityUtils.toString(entity1);
//				System.out.println(str);
				r = str.equals("OK");
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
		
		return r;
	}

    public String VerifyCode() {
    	String code="";
    	
		String url;
		//String filename = "code.bmp";
		//http://passport.9wee.com/common/verify_code.php?1442737436071
		
		url = serverAddress + "common/"+"verify_code.php?" + GetTime();

		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response1 = null;
		try {
			response1 = httpclient.execute(httpGet);
/*
			SaveImage(response1, filename);
			r = recognitionImg(filename);
			System.out.println(r);
			*/
			code=GetSetCookie(response1,"");
			//System.out.println(code);
		    response1.close();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

  	return code;
    }
	public boolean CheckUserName(String username) {
		boolean r;

		r = Check("user", "username", username);
		/*
		if (r) {
			r = Check("nickname", "nickname", username);
		}
		*/
		return r;
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

					/*
					httpPost.addHeader("POST /app/register.php?ac=add HTTP/1.1","");
					httpPost.setHeader("Host","passport.9wee.com");
					httpPost.setHeader("User-Agent","Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:40.0) Gecko/20100101 Firefox/40.0");
					*/
					//httpPost.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
					/*
					httpPost.setHeader("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
					httpPost.setHeader("Accept-Encoding","gzip, deflate");
					httpPost.addHeader("Cookie","db_idx_session=0; CWSSESSID=6377b597ddc09229ddc3b7f451155521; verifyCode=5229");
					httpPost.setHeader("Cookie","db_idx_session=0; "+"CWSSESSID="+CWSSESSID+"; verifyCode="+code);
					httpPost.setHeader("Connection","keep-alive");
					*/
					httpPost.setHeader("Referer","http://passport.9wee.com/app/register.php?step=2");
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
