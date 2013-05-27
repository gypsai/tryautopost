package com.tianya;

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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Example how to use multipart/form encoded POST request.
 */
public class ClientMultipartFormPost {

	
	
	private static String csrf_token = null;
	
    public static void main(String[] args) throws Exception {
    	
    	/*
        if (args.length != 1)  {
            System.out.println("File path not given");
            System.exit(1);
        }
        */
    	
        HttpClient httpclient = new DefaultHttpClient();
        String posturl = "http://letushow.com/submit";
        
        preGet(httpclient);
        
        
        try {
            HttpPost httppost = new HttpPost(posturl);

            FileBody bin = new FileBody(new File("/Users/gypsai/Desktop/letushow/let.gif"));
            StringBody title = new StringBody("let");

            MultipartEntity reqEntity = new MultipartEntity();
            reqEntity.addPart("imgfile",bin);
            reqEntity.addPart("csrf_token", new StringBody(csrf_token));
            reqEntity.addPart("title", title);
            reqEntity.addPart("type", new StringBody("local"));
            

            httppost.setEntity(reqEntity);

            System.out.println("executing request " + httppost.getRequestLine());
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();

            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            if (resEntity != null) {
               // System.out.println("Response content length: " + resEntity.getContentLength());
                System.out.println(iotostring(response.getEntity().getContent()));
            }
            EntityUtils.consume(resEntity);
        } finally {
            try { httpclient.getConnectionManager().shutdown(); } catch (Exception ignore) {}
        }
    }
    
    
   //Ö´ÐÐget 
    public static void preGet(HttpClient httpClient) throws ClientProtocolException, IOException
    {
    	String url = "http://letushow.com/submit";
    	HttpGet getReq = new HttpGet(url);
    	
    	HttpResponse res = httpClient.execute(getReq);
    	
    	getCsrfToken(res);
    	
    	EntityUtils.consume(res.getEntity());
    }
    
    public static void getCsrfToken(HttpResponse response) throws UnsupportedEncodingException, IllegalStateException, IOException
    {
    	
    	if (response!=null) {
			Document doc = Jsoup.parse(iotostring(response.getEntity().getContent()));
			
			csrf_token=doc.getElementsByAttributeValue("name", "csrf_token").get(0).attr("value");
			
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
