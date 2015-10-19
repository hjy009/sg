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
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

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
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
//            HttpHost target = new HttpHost("www.stilllistener.com", 80, "http");
            HttpHost target = new HttpHost("www.baidu.com", 80, "http");
            HttpHost proxy = new HttpHost(ip, 8080, "http");

            RequestConfig config = RequestConfig.custom()
                    .setProxy(proxy)
                    .build();
//            HttpGet request = new HttpGet("/checkpoint1/test2/");
            HttpGet request = new HttpGet("/");
            request.setConfig(config);

            System.out.println("Executing request " + request.getRequestLine() + " to " + target + " via " + proxy);

            CloseableHttpResponse response = httpclient.execute(target, request);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                System.out.println(EntityUtils.toString(response.getEntity()));
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            try {
				httpclient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
/*
        HttpHost proxy = new HttpHost(ip, Integer.parseInt(port),"http");
		DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
		CloseableHttpClient httpclient = HttpClients.custom()
		        .setRoutePlanner(routePlanner)
		        .setProxy(proxy)
		        .build();
*/		
		String r="";
		HttpGet httpGet = new HttpGet(url);

//		httpGet.setHeader("GET http://www.stilllistener.com/checkpoint1/test2/ HTTP/1.1","");
		httpGet.setHeader("Host","www.stilllistener.com");
		httpGet.setHeader("User-Agent","	Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:41.0) Gecko/20100101 Firefox/41.0");
		httpGet.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpGet.setHeader("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		httpGet.setHeader("Accept-Encoding","gzip, deflate");
		httpGet.setHeader("Referer",url);
		httpGet.setHeader("Connection","keep-alive");
		httpGet.setHeader("Cache-Control","max-age=0");
		
		Header[] hds = httpGet.getAllHeaders();
		for(int i=0;i<hds.length;i++){
			System.out.println(hds[i].toString());
		}
		
		HttpParams hp =  httpGet.getParams();
		Double d = hp.getDoubleParameter("org.apache.http.params.BasicHttpParams", 0);
		System.out.println(d.toString());
		hp.removeParameter("org.apache.http.params.BasicHttpParams");
		System.out.println(hp.toString());
		
		CloseableHttpResponse response1 = null;
		try {
			response1 = httpclient.execute(httpGet);
		    HttpEntity entity1 = response1.getEntity();
		    if(entity1.isChunked()){
				System.out.println("isChunked");	    	
		    };
		    
			for(int i=0;i<hds.length;i++){
				System.out.println(hds[i].toString());
			}
			
		    String line = response1.getStatusLine().getReasonPhrase();
			System.out.println(line);
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
