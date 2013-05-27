package com.gypsai;
/*
 * ====================================================================
 *
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * A example that demonstrates how HttpClient APIs can be used to perform
 * form-based logon.
 */
public class ClientFormLogin {

	
	private static DefaultHttpClient httppostclient = null;
	private static String formhash = null;
    public  static void main(String[] args) throws Exception {

        DefaultHttpClient httpclient = new DefaultHttpClient();
        httppostclient = new DefaultHttpClient();
        httppostclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BEST_MATCH);
     
        
        try {
        	
        	
        	String loginUrl="http://www.cnsfk.com/member.php?mod=logging&action=login&loginsubmit=yes&infloat=yes&lssubmit=yes";
        	//String loginUrl = "http://renren.com/PLogin.do";
        	
    		String testurl = "http://www.baidu.com";
            HttpGet httpget = new HttpGet(testurl);

            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();

            
            //System.out.println(istostring(entity.getContent()));
            
            System.out.println("Login form get: " + response.getStatusLine());
            EntityUtils.consume(entity);

            System.out.println("Initial set of cookies:");
            List<Cookie> cookies = httpclient.getCookieStore().getCookies();
            if (cookies.isEmpty()) {
                System.out.println("None");
            } else {
                for (int i = 0; i < cookies.size(); i++) {
               //     System.out.println("- " + cookies.get(i).toString());
                }
            }

            HttpPost httpost = new HttpPost(loginUrl);

           
    		String password = MD5.MD5Encode("luom1ng");
    		String redirectURL = "http://www.renren.com/home";  
            List <NameValuePair> nvps = new ArrayList <NameValuePair>();
            nvps.add(new BasicNameValuePair("username", "gypsai@foxmail.com"));
            nvps.add(new BasicNameValuePair("password", password));
           // nvps.add(new BasicNameValuePair("origURL", redirectURL));  
           // nvps.add(new BasicNameValuePair("domain", "renren.com"));  
          //  nvps.add(new BasicNameValuePair("autoLogin", "true"));  
          //  nvps.add(new BasicNameValuePair("formName", ""));  
          //  nvps.add(new BasicNameValuePair("method", ""));  
          //  nvps.add(new BasicNameValuePair("submit", "登录"));  
            
            httpost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
            httpost.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.28 (KHTML, like Gecko) Chrome/26.0.1397.2 Safari/537.28");
            
            
           //新建一个posthttpclient
            //DefaultHttpClient httppostclient = new DefaultHttpClient();
            
            //打印postheader
            Header[] pm = httpost.getAllHeaders();
            for (Header header : pm) {
				System.out.println("%%%%->"+header.toString());
			}
            
            
            
            //开始请求
            response = httppostclient.execute(httpost);
            
            EntityUtils.consume(response.getEntity());
            
            
            doget();
            //返回页面
            //
            
            //httppostclient.getConnectionManager().shutdown();
            
            //打印cookie
            List<Cookie> cncookies = httppostclient.getCookieStore().getCookies();
            System.out.println("Post logon cookies:");
           
            if (cncookies.isEmpty()) {
                System.out.println("None");
            } else {
                for (int i = 0; i < cncookies.size(); i++) {
                    System.out.println("- " + cncookies.get(i).getName().toString()+"   ---->"+cncookies.get(i).getValue().toString());
                }
            }
            
            
            //发帖
            submit();
            
            
            //打印httpheader
            entity = response.getEntity();
            Header[] m = response.getAllHeaders();
            for (Header header : m) {
				//System.out.println("+++->"+header.toString());
			}
            //System.out.println(response.getAllHeaders());
            System.out.println(entity.getContentEncoding());
            
            
            //打印statusline
            System.out.println("Login form get: " + response.getStatusLine());
            
            
            

        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
          //  httpclient.getConnectionManager().shutdown();
        	//httppostclient.getConnectionManager().shutdown();
        }
    }
    
    public static void doget() throws ClientProtocolException, IOException
    {
    	
    	String loginUrl="http://www.cnsfk.com/thread-187338-1-1.html";
    	//String loginUrl = "http://renren.com/PLogin.do";
    	
		//String testurl = "http://www.baidu.com";
        HttpGet httpget = new HttpGet(loginUrl);

        httpget.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.28 (KHTML, like Gecko) Chrome/26.0.1397.2 Safari/537.28");
        
        
        HttpResponse response = httppostclient.execute(httpget);
        HttpEntity entity = response.getEntity();

        
       // System.out.println(istostring(entity.getContent()));
        
        Document doc = Jsoup.parse(istostring(entity.getContent()));
        
        Elements elements = doc.getElementsByAttributeValue("name","formhash");
        
        formhash = elements.get(0).attr("value");
        
        System.out.println(formhash);
        
        
        System.out.println("Login form get: " + response.getStatusLine());
        
        System.out.println("Request page cookies:");
        List<Cookie> cookies = httppostclient.getCookieStore().getCookies();
        if (cookies.isEmpty()) {
            System.out.println("None");
        } else {
            for (int i = 0; i < cookies.size(); i++) {
           //     System.out.println("- " + cookies.get(i).toString());
            }
            EntityUtils.consume(entity);

        }
    	
    }
    
    
    public static void submit( ) throws IOException
	{
		
		// DefaultHttpClient httpclient =client;
	        try {
	        	
	        	
	        	String loginUrl="http://www.cnsfk.com/forum.php?mod=post&action=reply&fid=27&tid=187464&extra=page%3D1&replysubmit=yes&infloat=yes&handlekey=fastpost&inajax=1";

	        	//String loginUrl = "http://shell.renren.com/225349430/status";
	        	
	            HttpPost httpost = new HttpPost(loginUrl);
	            HttpResponse response;
	            HttpEntity entity;
	           
	            String cookie = "bdshare_firstime=1355909610661; PHPSESSID=e91e56bb62a771795827d3c22f139873; NMpz_2132_saltkey=qI441EaJ; NMpz_2132_lastvisit=1359035483; NMpz_2132_auth=3f1c8s6TaYsXy3P%2FicYLZJnjFSe9c1Sf%2FftT9eVKXrkkBLeiNf7NECg0lhyfY5ksfvyHFt7aQp3B4U9few3KJ4AA3YU; NMpz_2132_home_readfeed=1359067342; NMpz_2132_ulastactivity=e043gih7Pf%2BJrknAl2H%2B1HHsX7ruA0H3Vl4L7d7UGaC8H4nnYYDb; NMpz_2132_smile=1D1; NMpz_2132_home_diymode=1; NMpz_2132_visitedfid=72D71D8D228D4; NMpz_2132_connect_is_bind=0; NMpz_f8c6_saltkey=M9D2z3u3; NMpz_f8c6_lastvisit=1360708925; NMpz_f8c6_visitedfid=27; NMpz_f8c6_lastcheckfeed=202424%7C1360713637; NMpz_f8c6_myrepeat_rr=R0; NMpz_f8c6_sina_bind_202424=-1; NMpz_f8c6_auth=93fc1bshRF2PYzmeIfOehbfKTnF7RrCSbzIyMmjx8IxF2o%2F%2FhpIodJKsYa5qxyBvkECOqpetCxExu0JJmDxb2vDBaXQ; NMpz_f8c6_security_cookiereport=b539bqPnSMU%2F9QZrstdercLs4EoYUSND8wLAZDVXHjo%2Bq1sqZElj; NMpz_f8c6_ulastactivity=c6228kqG9Y4ltGhVJBgZbOwQgz%2Bw3fYJySpNN%2Fgo2HyQDKvHhqh1; NMpz_f8c6_sendmail=1; tjpctrl=1360766557658; NMpz_f8c6_baduowabaosecqaaSW4Gv=9385%2B536Y0Cw7qkigLv1GLUY90WRddybpSf6QL%2FcU23b5dfthRgANMcLAvK5clQ9llROx12uf%2BH7WBxtnFXXG0I88mLgN34upLaab2oL1ms6c1o0qENu; NMpz_f8c6_viewid=tid_187464; NMpz_f8c6_sid=eQrIiP; NMpz_f8c6_connect_is_bind=0; NMpz_f8c6_lastact=1360764935%09xwb.php%09; NMpz_f8c6_smile=1D1; pgv_pvi=7540007865; pgv_info=ssi=s8684604180; CNZZDATA1870554=cnzz_eid=80712295-1360709493-http%253A%252F%252Fwww.cnsfk.com%252F&ntime=1360764751&cnzz_a=19&retime=1360764815957&sin=none&ltime=1360764815957&rtime=1; Hm_lvt_d442b1725262a919eeb6d39cccf915f6=1360709495; Hm_lpvt_d442b1725262a919eeb6d39cccf915f6=1360764816; NMpz_f8c6_noticeTitle=1";
	            
	            List <NameValuePair> nvps = new ArrayList <NameValuePair>();
	            nvps.add(new BasicNameValuePair("message", "{:5_142:}{:5_142:}{:5_142:}"));
	            nvps.add(new BasicNameValuePair("formhash", formhash));
	            nvps.add(new BasicNameValuePair("posttime", "1360764934"));
	            nvps.add(new BasicNameValuePair("usesig", ""));
	            nvps.add(new BasicNameValuePair("subject", ""));
			       
			      
	            httpost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
	            httpost.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.28 (KHTML, like Gecko) Chrome/26.0.1397.2 Safari/537.28");
	           // httpost.setHeader("Cookie",cookie);
	          //新建一个posthttpclient
	          //  DefaultHttpClient httppostclient = new DefaultHttpClient();
	          //  httppostclient.setCookieStore(client.getCookieStore());
	          //  httppostclient = client;
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
    		
    		if (io==null) {
				System.out.println("content is null!");
			} else {
				
				try {
					
					while ((line=br.readLine())!=null) {
						
						buffer.append(line+"\n");
					}

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
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

