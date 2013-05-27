package com.trip;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class PostTrip {
	
	public static String csrf_token = null;
	public static HttpClient httpClient;
	
	
	
	public static void main(String args[]) throws ClientProtocolException, IOException
	{
		String url = new String("http://10000km.cn/login");
		httpClient = new DefaultHttpClient();
		PostTrip tf = new PostTrip();
		
		tf.preGet(httpClient,"http://10000km.cn");
		
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("login_email", "gypsai@163.com");
		param.put("password", "luom1ng");
		param.put("csrf_token", csrf_token);
		
		
		tf.PostData(url, param);
		
		tf.uploadCover();
		tf.dopostTrip();
		//tf.preGet(httpClient,"http://10000km.cn/home");
	}
	
	public void dopostTrip( )
	{
		String posturl = "http://10000km.cn/trip/create";
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("title", "86GG3月4号从新疆到北京");
		param.put("dsts", "北京");
		param.put("csrf_token", csrf_token);
		param.put("trip_way[]", "1");
		param.put("content", "<p>本人86GG，三月四号左右从新疆去北京。可以穷游，有意者也可以商量。计划在路上停上两三站。有通往的请联系QQ531402825，留言天涯。<br></p><p><br></p><p>来自：http://bbs.tianya.cn/post-685-252302-1.shtml</p>");
		//param.put("cover", "http://10000km.oss.aliyuncs.com/image/trip_cover/m/2013021508330522215.png");
		
		
		PostData(posturl, param);
		
		
	}
	
	public void uploadCover() throws ClientProtocolException, IOException
	{
		
		 HttpClient httpclient = httpClient;
	        String posturl = "http://10000km.cn/trip/uploadCover";  
	        try {
	            HttpPost httppost = new HttpPost(posturl);

	            FileBody bin = new FileBody(new File("/Users/gypsai/Desktop/letushow/let.gif"));
	           // StringBody title = new StringBody("let");

	            MultipartEntity reqEntity = new MultipartEntity();
	            reqEntity.addPart("cover",bin);
	            reqEntity.addPart("csrf_token", new StringBody(csrf_token));
	           // reqEntity.addPart("title", title);
	           // reqEntity.addPart("type", new StringBody("local"));
	            

	            httppost.setEntity(reqEntity);

	           // System.out.println("executing request " + httppost.getRequestLine());
	            HttpResponse response = httpClient.execute(httppost);
	            HttpEntity resEntity = response.getEntity();

	            System.out.println("----------------------------------------");
	            System.out.println(response.getStatusLine());
	            if (resEntity != null) {
	               // System.out.println("Response content length: " + resEntity.getContentLength());
	                System.out.println(iotostring(response.getEntity().getContent()));
	            }
	            EntityUtils.consume(resEntity);
	        } finally {
	            //try { httpclient.getConnectionManager().shutdown(); } catch (Exception ignore) {}
	        }
		
	}
	
	public void PostData(String url,Map<String, String> data)
	{
		
		HttpClient httpclient = httpClient;
		HttpResponse response = null;
		HttpPost httppost = new HttpPost(url);
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		
		Set<String> keySet = data.keySet();
		
		for(String key : keySet) {
			nvps.add(new BasicNameValuePair(key, data.get(key)));
		}
        httppost.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.28 (KHTML, like Gecko) Chrome/26.0.1397.2 Safari/537.28");
		httppost.setEntity(new UrlEncodedFormEntity(nvps,Consts.UTF_8));
		
		try {
			response = httpclient.execute(httppost);
			//打印结果
			String html = iotostring(response.getEntity().getContent());
			//JSONObject json = ;
			System.out.println(html);
			//log(response, httpclient);
			
			//关闭此请求
			EntityUtils.consume(response.getEntity());
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			//httpclient.getConnectionManager().shutdown();
			
		}
		
	}
	
	
	
	 //执行get 
    public void preGet(HttpClient httpClient,String url) throws ClientProtocolException, IOException
    {
    	System.out.println(" ------------------Start Get ------------------------------");
    	HttpGet getReq = new HttpGet(url);
    	
    	HttpResponse res = httpClient.execute(getReq);
    	
    	//为什么getEntity() 不能调用2次？
    	String html = iotostring(res.getEntity().getContent()); 
    	getCsrfToken(html);
    	//System.out.println(html);
    	
    	EntityUtils.consume(res.getEntity());
    	
    	System.out.println(" ------------------end Get ------------------------------");
    }
    
    
    
    public  void getCsrfToken(String html) throws UnsupportedEncodingException, IllegalStateException, IOException
    {
    	
    	if (html!=null) {
			Document doc = Jsoup.parse(html);
			
			//System.out.println(doc.getElementsByAttributeValue("name", "csrf_token"));
			csrf_token=doc.getElementsByAttributeValue("name", "csrf_token").get(0).attr("value");
			
			System.out.println("csrf_token:"+csrf_token);
		}
    	
    }
    
    public static String  iotostring(InputStream is) throws UnsupportedEncodingException
	{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
		StringBuffer sb = new StringBuffer();
		String line = "";
		
		try {
			while ((line=br.readLine())!=null) {
				sb.append(line+"\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(sb.toString());
		return sb.toString();
	}
	
	
    
}
	
