package fuzhu.gwee.passport;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class sgClient {
	protected CloseableHttpClient httpclient;
	protected String serverAddress;
	protected String CWSSESSID;
    BasicCookieStore cookieStore;
	
	public sgClient() {
        cookieStore = new BasicCookieStore();
		httpclient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .build();
        
		//httpclient = HttpClients.createDefault();
		serverAddress = "https://passport.9wee.com/";
		SetCWSSESSID();
	}
	
	protected String GetTime(){
		long mtime;
		String stime;

		mtime = System.currentTimeMillis();
		stime = String.valueOf(mtime);
		return stime;
	};

	protected void printCookies(){
        List<Cookie> cookies = cookieStore.getCookies();
        if (cookies.isEmpty()) {
            System.out.println("None cookie");
        } else {
            for (int i = 0; i < cookies.size(); i++) {
            	System.out.println("- " + cookies.get(i).toString());
            }       
         }
	};
	
	protected String GetSetCookie(String name){
		String id="";
		
//		System.out.println("Initial set of cookies:");
        List<Cookie> cookies = cookieStore.getCookies();
        if (cookies.isEmpty()) {
            System.out.println("None cookie");
        } else {
            for (int i = 0; i < cookies.size(); i++) {
            	if (cookies.get(i).getName().equals("CWSSESSID")){
            		id=cookies.get(i).getValue();
            	}
            	//System.out.println("- " + cookies.get(i).toString());
            }       
            //id = cookies.get(0).getValue();
            //System.out.println(id);
         }
        
        
		return id;
	}
	
	protected String GetSetCookie(CloseableHttpResponse response , String name){
		Header h1 = response.getFirstHeader("Set-Cookie");
		String str = h1.toString();
        //System.out.println(id);
        String[] strs = str.split(":|=|;");
        String value=strs[2];
		return value;
	}
	
	protected boolean SaveImage(CloseableHttpResponse response, String filename){
		boolean r=false;
		
		HttpEntity entity1 = response.getEntity();
	    InputStream in;
		try {
			in = entity1.getContent();
			FileOutputStream output = new FileOutputStream(new File(filename));
			BufferedImage img = ImageIO.read(in);
			ImageIO.write(img, "BMP", output);
			r=true;
		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return r;
	}
	
	protected String recognitionImg(String filename){
		String id="";
		ImgIdent imgident;

		try {
			imgident = new ImgIdent();
			id = imgident.getImageNumber(filename);
//			System.out.println(id);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return id;
	}
	
	public String SetCWSSESSID(){
		String url;
		String str;

		url = serverAddress+"app/" + "register.php?step=2";
		HttpGet httpGet = new HttpGet(url);
/*
		httpGet.setHeader("Cookie", "__utma=1831918.1379181802.1441183404.1441959515.1442553530.6;"
				+ " __utmz=1831918.1442553530.6.6.utmcsr=wz24.sg.9wee.com|utmccn=(referral)|utmcmd=referral|utmcct=/main.php; "
				+ "registerTime=Am5UYAAxBzQAPlQ1B2EANQY0ADQ%3D;"
				+ " CWSSESSID=def886b778d0bd82bf51c6391677bb00");//设置cookie
*/
		/*
		POST /app/register.php?ac=add HTTP/1.1

				Host: passport.9wee.com

				User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:40.0) Gecko/20100101 Firefox/40.0
*/
	//			Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
/*
				Accept-Language: zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3

				Accept-Encoding: gzip, deflate

				Referer: http://passport.9wee.com/app/register.php?step=2

				Cookie: db_idx_session=0; CWSSESSID=6377b597ddc09229ddc3b7f451155521; verifyCode=5229

				Connection: keep-alive
*/
		CloseableHttpResponse response1;
		try {
			response1 = httpclient.execute(httpGet);

            CWSSESSID=GetSetCookie(response1,"CWSSESSID");
            //System.out.println(id);
            
			HttpEntity entity1 = response1.getEntity();
			EntityUtils.consume(entity1);
			response1.close();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return CWSSESSID;
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

	
}
