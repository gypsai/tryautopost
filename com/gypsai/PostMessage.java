package com.gypsai;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class PostMessage {
	
	
	public static void submit(DefaultHttpClient client) throws IOException
	{
		
		// DefaultHttpClient httpclient =client;
	        try {
	        	
	        	
	        	String loginUrl="http://www.cnsfk.com/forum.php?mod=post&action=reply&fid=27&tid=187464&extra=page%3D1&replysubmit=yes&infloat=yes&handlekey=fastpost&inajax=1";

	            HttpPost httpost = new HttpPost(loginUrl);
	            HttpResponse response;
	            HttpEntity entity;
	           
	            List <NameValuePair> nvps = new ArrayList <NameValuePair>();
	            nvps.add(new BasicNameValuePair("message", "楼主好牛！"));
	            
	            httpost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
	            httpost.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.28 (KHTML, like Gecko) Chrome/26.0.1397.2 Safari/537.28");
	           
	          //新建一个posthttpclient
	            DefaultHttpClient httppostclient = new DefaultHttpClient();
	            httppostclient.setCookieStore(client.getCookieStore());
	            httppostclient = client;
	          //打印cookie
	            List<Cookie> cncookies = httppostclient.getCookieStore().getCookies();
	            System.out.println("SendMessage cookies:");
	            if (cncookies.isEmpty()) {
	                System.out.println("None");
	            } else {
	                for (int i = 0; i < cncookies.size(); i++) {
	                    System.out.println("- " + cncookies.get(i).getName().toString()+"   ---->"+cncookies.get(i).getValue().toString());
	                }
	            }
	            
	            //开始请求
	            response = httppostclient.execute(httpost);
	            
	            entity=response.getEntity();
	            //打印内容
	            System.out.println(istostring(entity.getContent()));
	            
	            
	            
	            
	            
	            //打印statusline
	            System.out.println("Login form get: " + response.getStatusLine());
	            
	            
	            
	            

	        } finally {
	         //  httpclient.getConnectionManager().shutdown();
	        }
	}

	
    public static String istostring(InputStream io) throws IOException
    {
    	BufferedReader br =  new BufferedReader(new InputStreamReader(io,"gbk"));
    	StringBuffer buffer = new StringBuffer();
    	
    	String line="";
    	
    	
    	try {
			while ((line=br.readLine())!=null) {
				buffer.append(line+"\n");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	/*
    	System.out.println("转码前，输出Java系统属性如下："); 
        System.out.println("user.country:" + System.getProperty("user.country")); 
        System.out.println("user.language:" + System.getProperty("user.language")); 
        System.out.println("sun.jnu.encoding:" + System.getProperty("sun.jnu.encoding")); 
        System.out.println("file.encoding:" + System.getProperty("file.encoding")); 
		*/
    	
    	String html = buffer.toString();
    	//html = new String(html.getBytes("EUC_CN"),"utf-8");
    	
    	return html;
    }

}
