package fuzhu.ip;

/*******************************************************************************
 * Copyright (c) 2009 Ale46.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 ******************************************************************************/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.InputStreamFactory;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.RequestAcceptEncoding;
import org.apache.http.client.protocol.RequestAddCookies;
import org.apache.http.client.protocol.RequestAuthCache;
import org.apache.http.client.protocol.RequestClientConnControl;
import org.apache.http.client.protocol.RequestDefaultHeaders;
import org.apache.http.client.protocol.RequestExpectContinue;
import org.apache.http.client.protocol.ResponseContentEncoding;
import org.apache.http.client.protocol.ResponseProcessCookies;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.ProxyClient;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpProcessorBuilder;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;
import org.apache.http.util.EntityUtils;
import org.apache.http.util.VersionInfo;

public class WebUrl{
	
	private String url,ip;
	private int port;
	private boolean useProxy;
	
	WebUrl(String url){
		this.url = url;
		
	}
	
	WebUrl(String url, String ip, int port){
		this.url = url;
		this.ip = ip;
		this.port = port;
		useProxy = true;
	}
	
  	public String getResponseStr() {
        String userAgentCopy = VersionInfo.getUserAgent("Apache-HttpClient",
                        "org.apache.http.client", getClass());

        HttpProcessor httpprocessorCopy = null ;
        if (httpprocessorCopy == null) {
            final HttpProcessorBuilder b = HttpProcessorBuilder.create();
            b.addAll(
                    new RequestDefaultHeaders(null),
                    new RequestContent(),
                    new RequestTargetHost(),
//                    new RequestClientConnControl(),
                    new RequestUserAgent(userAgentCopy),
                    new RequestExpectContinue());
            b.add(new RequestAddCookies());
            b.add(new RequestAcceptEncoding());
            b.add(new RequestAuthCache());
            b.add(new ResponseProcessCookies());
            b.add(new ResponseContentEncoding());
            httpprocessorCopy = b.build();
        }

        HttpHost proxy = new HttpHost(ip, port,"http");
//		DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
		CloseableHttpClient httpclient = HttpClients.custom()
//		        .setRoutePlanner(routePlanner)
		        .setProxy(proxy)
		        .setHttpProcessor(httpprocessorCopy)
		        .build();

		String r="";
		HttpGet httpGet = new HttpGet(url);

//		httpGet.setHeader("GET http://www.stilllistener.com/checkpoint1/test2/ HTTP/1.1","");
//		httpGet.setHeader("Host","www.stilllistener.com");
//		httpGet.setHeader("User-Agent","Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:41.0) Gecko/20100101 Firefox/41.0");
//		httpGet.setHeader("User-Agent","Baiduspider+(+http://www.baidu.com/search/spider.htm)");
//		httpGet.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//		httpGet.setHeader("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
//		httpGet.setHeader("Accept-Encoding","gzip, deflate");
//		httpGet.setHeader("Referer",url);
//		httpGet.setHeader("Connection","keep-alive");
//		httpGet.setHeader("Cache-Control","max-age=0");
		
		CloseableHttpResponse response1 = null;
		try {
			response1 = httpclient.execute(httpGet);
		    HttpEntity entity1 = response1.getEntity();
		    String line = response1.getStatusLine().getReasonPhrase();
//			System.out.println(line);
			if (line.equals("OK")) {
				r = EntityUtils.toString(entity1);
//				System.out.println(r);
			    // do something useful with the response body
			    // and ensure it is fully consumed
//			    InputStream in = entity1.getContent();
//			    System.out.println(EntityUtils.toString(entity1,"GB2312"));
			    //System.out.println(in.toString());
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
	
	public ArrayList<String> getData() {
		ArrayList<String> data = new ArrayList<String>();
		String line=null;
		URL address = null;
		try {
			address = new URL(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		URLConnection urlconn = null;


		if (useProxy) {
			SocketAddress addr = new InetSocketAddress(ip,port);
			java.net.Proxy httpProxy = new java.net.Proxy(java.net.Proxy.Type.HTTP, addr);
			try {
				urlconn = address.openConnection(httpProxy);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			try {
				urlconn = address.openConnection();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			urlconn.connect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}
		BufferedReader buffreader = null;
		try {
			buffreader = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			line = buffreader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (line!=null) {
			
			data.add(line);
			
			try {
				line = buffreader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		return data;
	}
}
