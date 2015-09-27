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
		str = postResponseStr(url, ref,  
				"_REFERER","http://passport.9wee.com/app/user_info.php",
				"username", username,
				"password", password
				);
		refreshCookies();
		
		if(weeObj.getString("username").equals(username)){
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
	
	public boolean loginServer(String id){
		/*
		//get http://wz25.sg.9wee.com/
		//Referer	http://game.9wee.com/?mod=api&ctl=login&server_id=102001287
		//Host		wz25.sg.9wee.com//<title>激活游戏</title>
		http://wz25.sg.9wee.com/index.php?p=NoData
		Referer	http://wz25.sg.9wee.com/login.php?logout
		https://passport.9wee.com/login
		Referer	http://wz25.sg.9wee.com/index.php?p=NoData
		http://wz25.sg.9wee.com/index.php
		Location	http://passport.9wee.com/api/x_verify.php?appId=102&p=B2EBOFFiC2QCKQd3CTcFMgY%2BWSUKf1gxXDRVZFNxU28G
cwcmAzkBJwBqAzQDNgsxVXUNO1ckD3dbJQV0UzBSSgclATBRFgt7AmAHQgl6BX8GNlkyCiNYJ1w1VS9TOlN9BmQHYQMsATcAPwNtAyILOVURDTpXPg9nWzAFKVMtUnsHaAFyUXILZQIhBz4JOQU
%2FBiZZdwpbWDVcIFUjUzhTeQY7BzUDOAF2ACADIgM8C3Y%3D
		Referer	http://wz25.sg.9wee.com/index.php?p=NoData
		http://passport.9wee.com/api/x_verify.php?appId=102&p=B2EBOFFiC2QCKQd3CTcFMgY%2BWSUKf1gxXDRVZFNxU28GcwcmAzkBJwBqAzQDNgsxVXUNO1ckD3dbJQV0UzBSSgclATBRFgt7AmAHQgl6BX8GNlkyCiNYJ1w1VS9TOlN9BmQHYQMsATcAPwNtAyILOVURDTpXPg9nWzAFKVMtUnsHaAFyUXILZQIhBz4JOQU%2FBiZZdwpbWDVcIFUjUzhTeQY7BzUDOAF2ACADIgM8C3Y%3D
		Location	http://wz25.sg.9wee.com/index.php?p=B2EKMwA4VzgFLgV1CDZQYQEzCm5QdVolDWZRdgAjVmYDa1M%2BCVdSbgA0U3JSbV
UmAjpaNQIzCD4IJAhtABtZSwdECl0AMldWBRkFVAhZUB0BQQoRUBxaAg1bUScAa1Z8Az5TaAkyUiUAJVMjUjNVJwJuWmUCaAhhCCQIZwAjWToHNgozACNXagU
%2FBX8IPVBgATsKdlBsWiUNOVE9AGpWLQNqUzkJa1JsAD5TMVI7VTACIlo%2FAnYIPgg3CGQAalkiByUKTAA0VycFFwVFCClQEQE
zCnFQElpiDSZRRwBoVioDPFNpCSpSPAAjU2pSY1VvAiJaYQJoCGUIbwgwAHJZOwdzCjMAMVc4BXcFJAg3UCMBOQpsUG1adA1zUWQ
AI1Z8A3NTPwl6UmMAclNrUiVVbwIzWjYCPwgmCDQIbgBpWWEHNwo7ADhXYAVtBTQIb1A1ATMKYlBuWmENMVEyAGlWOgNhUzQJP1I
2ADNTYFJmVTMCMVpgAmYIZggkCGcAI1k6BzEKOAA7VyAFNAV2CHxQDwF2CidQMlokDVxRbAA0Vi0DP1MjCTJSNwBqU3JSdFVuAnNaPgI0CDEIPAh
%2BACVZcwdlCnsAXldwBTAFZwhgUA8BYQo9UCVaIg1rUScAa1Z8Az5TYQk4Uj0AclNhUm9VbQI5WikCNQg8CCsIbQBiWSIHOwp6ADtXMwVkBTwILlAmAWYKJlA
%2BWjANelFaACRWZgNpUzUJKlI8ACNTalJnVWECOlomAjcINAg3CGkAYFk5BzIKPwAzVzMFYQUxCDlQYQEhCm9QJFpsDTJRNABqV
i0DclM1CXpSbgA2UylSCVU2Am9aYAJgCCYIPQgvAGpZMwcyCjMAI1c6BTMFMwg7UGUBNgplUGdaNw1iUWcAYlZrAzVTNAlqUmYAYlM1UmZVMAI5WjYCMwgzCGQIPgBoWWIHMQptADVXIAVuBXs
%3D
		Referer	http://wz25.sg.9wee.com/index.php?p=NoData
		http://wz25.sg.9wee.com/index.php?p=B2EKMwA4VzgFLgV1CDZQYQEzCm5QdVolDWZRdgAjVmYDa1M%2BCVdSbgA0U3JSbVUmAjpaNQIzCD4IJAhtABtZSwdECl0AMldWBRkFVAhZUB0BQQoRUBxaAg1bUScAa1Z8Az5TaAkyUiUAJVMjUjNVJwJuWmUCaAhhCCQIZwAjWToHNgozACNXagU%2FBX8IPVBgATsKdlBsWiUNOVE9AGpWLQNqUzkJa1JsAD5TMVI7VTACIlo%2FAnYIPgg3CGQAalkiByUKTAA0VycFFwVFCClQEQEzCnFQElpiDSZRRwBoVioDPFNpCSpSPAAjU2pSY1VvAiJaYQJoCGUIbwgwAHJZOwdzCjMAMVc4BXcFJAg3UCMBOQpsUG1adA1zUWQAI1Z8A3NTPwl6UmMAclNrUiVVbwIzWjYCPwgmCDQIbgBpWWEHNwo7ADhXYAVtBTQIb1A1ATMKYlBuWmENMVEyAGlWOgNhUzQJP1I2ADNTYFJmVTMCMVpgAmYIZggkCGcAI1k6BzEKOAA7VyAFNAV2CHxQDwF2CidQMlokDVxRbAA0Vi0DP1MjCTJSNwBqU3JSdFVuAnNaPgI0CDEIPAh%2BACVZcwdlCnsAXldwBTAFZwhgUA8BYQo9UCVaIg1rUScAa1Z8Az5TYQk4Uj0AclNhUm9VbQI5WikCNQg8CCsIbQBiWSIHOwp6ADtXMwVkBTwILlAmAWYKJlA%2BWjANelFaACRWZgNpUzUJKlI8ACNTalJnVWECOlomAjcINAg3CGkAYFk5BzIKPwAzVzMFYQUxCDlQYQEhCm9QJFpsDTJRNABqVi0DclM1CXpSbgA2UylSCVU2Am9aYAJgCCYIPQgvAGpZMwcyCjMAI1c6BTMFMwg7UGUBNgplUGdaNw1iUWcAYlZrAzVTNAlqUmYAYlM1UmZVMAI5WjYCMwgzCGQIPgBoWWIHMQptADVXIAVuBXs%3D
		Location	http://wz25.sg.9wee.com/index.php
		Referer	http://wz25.sg.9wee.com/index.php?p=NoData
		http://wz25.sg.9wee.com/index.php
		Location	main.php
		已收到的 cookie
		Referer	http://wz25.sg.9wee.com/index.php?p=NoData
		http://wz25.sg.9wee.com/main.php
		Referer	http://wz25.sg.9wee.com/index.php?p=NoData
		post http://wz25.sg.9wee.com/modules/user_info.php?user_add2
		Referer	http://wz25.sg.9wee.com/main.php
		post ajaxId=&act=d&type=e&cache=false&x=1280&y=800&r=1443275287602
		post http://wz25.sg.9wee.com/modules/gateway.php
		post ajaxId=city_build_resource&act=city_build_resource&type=e&cache=false&r=1443275287976
		http://wz25.sg.9wee.com/modules/gateway.php?ajaxId=_1443275287617&act=get_queue&type=o&cache=false&r=1443275287986
		[{"id":"_1443275287617","type":"o","num":7,"message":null,"error":0},{"is_building":null,"is_military"
:[],"is_soldier":[[],[]],"city_army":[[{"army_type":"1","army_num":"1","army_content":"3279","map_id"
:"467496","general_flag":"1","general_type":"15","general_name":"\u5b59\u6b66sfa6","general_rank":"1"
}],[]],"new_msg":[{"msg_num":"7","military_num":"0","union_msg_num":"0","sys_num":"0"}],"time":"new Date
(2015,8,26,21,47,59)","other_login":false},"\u767b\u5f55\u6b21\u6570:2\nCOOKIE\u6b21\u6570:1\nthis_login_ip
:60.223.225.124"]
		http://wz25.sg.9wee.com/modules/gateway.php?ajaxId=_1443275287593&act=set_city_resource&type=o&cache=false&r=1443275288000
		[{"id":"_1443275287593","type":"o","num":4,"message":null,"error":0},{"depot_row":["5120","5120","5120"
,"5120"],"user_base":[["5120","48"],["5120","48"],["5120","48"],["5120","68"],["1443274118",1443275279
]],"consumption_row":["72","14","4"],"general_append":1.75}]
		post http://wz25.sg.9wee.com/modules/gateway.php
		ajaxId=updateUserLoginTime&act=updateUserLoginTime&type=o&cache=0&cla=Build_City&operateType=class&r
=1443275288008
		http://wz25.sg.9wee.com/modules/gateway.php?ajaxId=_1443275287596&act=resource_add&type=o&cache=false&r=1443275288012
		[{"id":"_1443275287596","type":"o","num":5,"message":null,"error":0},{"1":{"has":true,"prop_id":1,"green_server"
:1,"key":"35d85bcaf0ef1eacaec5ac43356e5315","time":"2015-09-29 21:28:38"},"2":{"has":true,"prop_id":2
,"green_server":1,"key":"6c0a68c9df6bf7d0b6d6719bc2df6aad","time":"2015-09-29 21:28:38"},"3":{"has":true
,"prop_id":3,"green_server":1,"key":"32062a5555be09ce6d64a81a93aae02d","time":"2015-09-29 21:28:38"}
,"4":{"has":true,"prop_id":4,"green_server":1,"key":"9c949c98f94fc4302ae50c1c6abb1959","time":"2015-09-29
 21:28:38"},"41":{"has":true,"prop_id":41,"green_server":1,"key":"2c7f513be1750019a61359c13a8ca28f","time"
:"2017-09-25 21:28:38"}}]
		http://wz25.sg.9wee.com/modules/gateway.php?ajaxId=city_relationship&act=city_relationship&type=e&cache=false&r=1443275288017
<div class="new_city_no">
	<ul>
				<li><a href="/switch.php?map_id=467496" onclick="return switchCity('新的城池zmhn', '/switch.php?map_id
=467496');">新的城池zmhn (67|96)</a></li>
			</ul>
</div>
		http://wz25.sg.9wee.com/modules/gateway.php?ajaxId=_1443275287617&act=get_money&type=o&cache=false&r=1443275288027
		[{"id":"_1443275287617","type":"o","num":2,"message":null,"error":0},{"gift":30,"resource":45}]
		http://wz25.sg.9wee.com/modules/gateway.php?module=task&ajaxId=_1443275287619&act=check_task&type=o&cache=false&r=1443275288033
		[{"id":"_1443275287619","type":"o","num":2,"message":null,"error":0},{"task_id":null,"task_state":false
}]
		http://www.google-analytics.com/r/__utm.gif?utmwv=5.6.7&utms=2&utmn=1278827808&utmhn=wz25.sg.9wee.com&utmcs=UTF-8&utmsr=1280x800&utmvp=1263x647&utmsc=24-bit&utmul=zh-cn&utmje=0&utmfl=19.0%20r0&utmdt=%E6%AD%A6%E6%9E%97%E4%B8%89%E5%9B%BD%20-%20%E7%8E%8B%E8%80%85%E7%BB%BF%E8%89%B2%E6%9C%8D25%E5%8C%BA&utmhid=2013814517&utmr=0&utmp=%2Fmain.php&utmht=1443275288393&utmac=UA-35958849-1&utmcc=__utma%3D131896521.449217404.1443274577.1443274577.1443274577.1%3B%2B__utmz%3D131896521.1443274577.1.1.utmcsr%3D(direct)%7Cutmccn%3D(direct)%7Cutmcmd%3D(none)%3B&utmjid=1181170085&utmredir=1&utmu=qAAAAAAAAAAAAAAAAAAAAAAE~
		http://wz25.sg.9wee.com/modules/gateway.php?module=im
		ajaxId=_1443275291297&act=getLoginKey&type=e&cache=false&nickname=%E5%BC%A0%E4%B8%89%E5%AD%99&r=1443
275291297
		{"key":"b5146b1fa7ef5c62b6cb67f45f17d612","host":"s1.chat.9wee.com","port":"3081"}
		
		
		
		
		
		*/
		return false;
	}
	
	public boolean reg(String host,int area,int c,int g){
		String url,ref,str;
		/*
		 * 
		http://wz24.sg.9wee.com/index.php
		Referer	http://wz24.sg.9wee.com/index.php?p=NoData
		http://wz24.sg.9wee.com/reg.php?c=1
		http://wz24.sg.9wee.com/reg.php?c=2
		http://wz24.sg.9wee.com/reg.php?c=3
		Referer	http://game.9wee.com/?mod=api&ctl=login&server_id=102001286
		
		http://wz25.sg.9wee.com/reg.php?user_nickname=%E5%BC%A0%E4%B8%89&x=63&y=30&c=3&g=15&send=1&area=1		
		Referer		http://wz24.sg.9wee.com/reg.php?p=1
		area:1 		--东北
		c:3	吴
		g:15 	孙武
		send:1
		user_nickname"张三
		x:63
		y:30		
		
<title>激活游戏</title>
		<script language="javascript">
			alert("玩家昵称已被占用，请换个重新再试!");
		</script>	
<title>武林三国 - 王者绿色服24区</title>
		alert ( h + '“' + str + '”\n已经复制到您的剪贴板中\n您可以使用Ctrl+V快捷键粘贴到需要的地方' )

		*/

		
		url = "http://"+host+"/index.php";
		ref = "http://"+host+"/index.php"+"?p=NoData";
		str = getResponseStr(url, ref);
		
		/*
		url = "http://wz24.sg.9wee.com/reg.php?c=3";
		ref = "http://game.9wee.com/?mod=api&ctl=login&server_id=102001286";
		str = getResponseStr(url, ref);
		*/
		url = "http://wz24.sg.9wee.com/reg.php?user_nickname=%E5%BC%A0%E4%B8%89&x=44&y=28&c=3&g=15&send=1&area=1";
		ref = "http://wz24.sg.9wee.com/reg.php?p=1";
		str = getResponseStr(host,"/reg.php",ref,"user_nickname","张三孙","x","44","y","28","c",Integer.toString(c),"g",Integer.toString(g),"send","1","area",Integer.toString(area));
		System.out.println(str);
		
		int from = str.indexOf("alert");
		int to = str.indexOf(";", from);
		String r = str.substring(from, to);
		System.out.println(r);
		
		if(str.indexOf("alert")<0){
			return true;
		}
		
		return false;
	}
}
