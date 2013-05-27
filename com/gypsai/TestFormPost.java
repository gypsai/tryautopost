package com.gypsai;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class TestFormPost {

	public static void main(String args[])
	{
		
		String url = new String("http://chinayouthhostel.sinaapp.com/testform.php");
		Map<String, String> param = new HashMap<String, String>();
		param.put("fname", "tristan");
		param.put("lname", "robin");
		
		TestFormPost tf = new TestFormPost();
		tf.PostData(url, param);
		
		
	}
	
	
	public void PostData(String url,Map<String, String> data)
	{
		
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpEntity entity = null;
		HttpResponse response = null;
		HttpPost httppost = new HttpPost(url);
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		
		Set<String> keySet = data.keySet();
		
		for(String key : keySet) {
			nvps.add(new BasicNameValuePair(key, data.get(key)));
		}
        
		httppost.setEntity(new UrlEncodedFormEntity(nvps,Consts.UTF_8));
		
		try {
			response = httpclient.execute(httppost);
			//打印结果
			System.out.println(iotostring(response.getEntity().getContent()));
			log(response, httpclient);
			
			//关闭此请求
			EntityUtils.consume(response.getEntity());
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			httpclient.getConnectionManager().shutdown();
			
		}
		
	}
	
	
	public void log(HttpResponse reponse,DefaultHttpClient httpclient)
	{
		
		//打印cookie信息
		List<Cookie> cookies = httpclient.getCookieStore().getCookies();
		for (Cookie cookie : cookies) {
			System.out.println("-"+cookie.toString());
		}
		
	}
	
	public String iotostring(InputStream is) throws UnsupportedEncodingException
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
