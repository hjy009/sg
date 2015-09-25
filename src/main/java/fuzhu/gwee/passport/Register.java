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
		String url,ref;
		String code, str;
		boolean r = false;

		r = CheckUserName(username);
		if (r) {
			code = Code();
			url = serverAddress+"app/" + "register.php?ac=add";
			ref = "http://passport.9wee.com/app/register.php?step=2";
			str = postResponseStr(url,ref,
					"username", username,
					"password", password,
					"passwordcfm", password,
					"user_truename", "张三",
					"user_idcard","110101197707072913",
					"code", code,
					"agree", "yes",
					"user_type", "0",
					"force", ""
					);
			r = !Check("user", "username", username);
		}
		return r;
	}
}
