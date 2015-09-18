package fuzhu.ip;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class IP138 {

	public String myip(){
		String ip="";
		Document doc;
		try {
			doc = Jsoup.connect("http://1111.ip138.com/ic.asp").get();
			String str = doc.select("center").text();
			String[] strs = str.split("：|\\[|\\]");
			if(strs.length>2){
				ip =  strs[2];
			}
 			
	        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ip;		
	}
	
	public String ipLocate(String ip){
		String loc="";
		try {
			Document doc = Jsoup.connect("http://www.ip138.com/ips138.asp")
					  .data("ip",ip)
					  .data("action", "2")
					  .userAgent("Mozilla")
					  .timeout(3000)
					  .get();
//			System.out.println(doc.toString());
			String str = doc.body().select("table").get(2)
					.select("tr").get(2).select("td").select("ul").select("li").first().text();
			String[] strs = str.split("：");
/*			for(int i=0;i<strs.length;i++){
				System.out.println(strs[i]);
			}
*/			
			if(strs.length>1){
				loc =  strs[1];
			}
			/*
			Elements trs = doc.select("table").select("tr");
	        for(int i = 0;i<trs.size();i++){
	            Elements tds = trs.get(i).select("td");
	            for(int j = 0;j<tds.size();j++){
	                String text = tds.get(j).text();
	                System.out.println(text);
	            }
	        }
	        */
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loc;		
	}
	
	public String checkip() throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://www.ip138.com/");
		CloseableHttpResponse response1 = httpclient.execute(httpGet);
		// The underlying HTTP connection is still held by the response object
		// to allow the response content to be streamed directly from the network socket.
		// In order to ensure correct deallocation of system resources
		// the user MUST call CloseableHttpResponse#close() from a finally clause.
		// Please note that if response content is not fully consumed the underlying
		// connection cannot be safely re-used and will be shut down and discarded
		// by the connection manager. 
		try {
		    System.out.println(response1.getStatusLine());
		    HttpEntity entity1 = response1.getEntity();
		    // do something useful with the response body
		    // and ensure it is fully consumed
		    InputStream in = entity1.getContent();
		    System.out.println(EntityUtils.toString(entity1,"GB2312"));
		    //System.out.println(in.toString());

		    //System.out.println(EntityUtils.toString(entity1));
		    EntityUtils.consume(entity1);
		} finally {
		    response1.close();
		}		
		
		return null;		
	};
}
