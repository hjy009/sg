package fuzhu.gwee.passport;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.yccheok.numrecognition.FeatureParameter;
import org.yccheok.numrecognition.NumberImageFeatureFactory;
import org.yccheok.numrecognition.NumberImageLRTBHVFeatureFactory;
import org.yccheok.numrecognition.NumberNeuralNetworkRecognizer;

public class Register {

	CloseableHttpClient httpclient;
//	private HttpSession httpsession;
	private String serverAddress;

	public Register() {
//		httpsession = new HttpSession();
		httpclient = HttpClients.createDefault();
		serverAddress = "https://passport.9wee.com/";
	}

	public static String convertUnicode(String ori){
        char aChar;
        int len = ori.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len;) {
            aChar = ori.charAt(x++);
            if (aChar == '\\') {
                aChar = ori.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = ori.charAt(x++);
                        switch (aChar) {
                        case '0':
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                            value = (value << 4) + aChar - '0';
                            break;
                        case 'a':
                        case 'b':
                        case 'c':
                        case 'd':
                        case 'e':
                        case 'f':
                            value = (value << 4) + 10 + aChar - 'a';
                            break;
                        case 'A':
                        case 'B':
                        case 'C':
                        case 'D':
                        case 'E':
                        case 'F':
                            value = (value << 4) + 10 + aChar - 'A';
                            break;
                        default:
                            throw new IllegalArgumentException(
                                    "Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
 
        }
        
        return outBuffer.toString();
	}
        public boolean Check(String aact, String aname, String avalue) {
		String str;
		String url;
		boolean r = false;

		// https://passport.9wee.com/check?act=user&username=hjy111
		// 通行证 hjy111 已被使用, 请重新指定
		// OK
		// https://passport.9wee.com/check?act=nickname&nickname=hjy112
		url = serverAddress + "check?act=" + aact + "&" + aname + "=" + avalue;
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

	public boolean CheckUserName(String username) {
		boolean r;

		r = Check("user", "username", username);
		if (r) {
			r = Check("nickname", "nickname", username);
		}
		return r;
	}

	public String VerifyCode(String atime) {
		String url;
		String r = "";
		String filename = "code.bmp";
		ImgIdent imgident;

		// https://passport.9wee.com/common/verify_code?1246865246411
		url = serverAddress + "common/verify_code?" + atime;

		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response1 = null;
		try {
			response1 = httpclient.execute(httpGet);
		    HttpEntity entity1 = response1.getEntity();
		    InputStream in = entity1.getContent();
			FileOutputStream output = new FileOutputStream(new File(filename));
			BufferedImage img = ImageIO.read(in);
			ImageIO.write(img, "BMP", output);
			imgident = new ImgIdent();
			r = imgident.getImageNumber(filename);
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

	public boolean NewUser(String username, String password)  {
		String url;
		long mtime;
		String stime, code, responseBody;
		boolean r = false;
		int i;

		url = serverAddress + "register";
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response1;
		try {
			response1 = httpclient.execute(httpGet);
			//	    HttpEntity entity1 = response1.getEntity();

			mtime = System.currentTimeMillis();
			stime = String.valueOf(mtime);
			// agree = yes
			// code = 1111
			// nickname = hjy122
			// password = 123456
			// passwordcfm = 123456
			// username = hjy122
			// x = 35
			// y = 15
			r = CheckUserName(username);
			if (r) {
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (i = 0; i < 3; i++) {
					code = VerifyCode(stime);
					
					nvps.clear();
					nvps.add(new BasicNameValuePair("agree", "yes"));
					nvps.add(new BasicNameValuePair("code", code));
					nvps.add(new BasicNameValuePair("nickname", username));
					nvps.add(new BasicNameValuePair("password", password));
					nvps.add(new BasicNameValuePair("passwordcfm", password));
					nvps.add(new BasicNameValuePair("username", username));
					nvps.add(new BasicNameValuePair("x", "35"));
					nvps.add(new BasicNameValuePair("y", "15"));
					// cookie
					// __utma=247480783.1622237590364704500.1241495630.1246931873.1246935667.22;
					// __utmz=247480783.1246509771
					// .18.9.utmcsr=h15.sg.9wee.com|utmccn=(referral)|utmcmd=referral|utmcct=/main.php;
					// registerTime=VjpTYVVkAjVVa1M2AGNeZwQ3Djs
					// %3D; verifyCode=df6b9b8b6b0d60a09a53a06814cf5831;
					// db_idx_session=0;
					// __utmc=247480783; __utmb=247480783
					// .2.10.1246935667;
					// CWSSESSID=8bbfa02ebb85f772fc700b8afa4e126b

					nvps.add(new BasicNameValuePair("param1", "value1"));
					nvps.add(new BasicNameValuePair("param2", "value2"));
					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nvps, "UTF-8");

					HttpPost httpPost = new HttpPost(url);
					httpPost.setEntity(entity);
					CloseableHttpResponse response2;
					response2 = httpclient.execute(httpPost);
				    HttpEntity entity1 = response2.getEntity();
				    r=EntityUtils.toString(entity1).indexOf("恭喜")>=0;
					r = !Check("user", "username", username);
					if (r) {
						break;
					}
				    EntityUtils.consume(entity1);
				    response1.close();
				    response2.close();
				}
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
