package com.gypsai;

import java.util.HashMap;
import java.util.Map;

public class PostData {
	
	public static void main(String args[])
	{
		
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", "gypsai@foxmail.com");
		
		String password = MD5.MD5Encode("luom1ng");
		params.put("password", password);
		
		String loginUrl="http://www.cnsfk.com/member.php?mod=logging&action=login&loginsubmit=yes&infloat=yes&lssubmit=yes";
			
		String xml = HttpPostClient.post(loginUrl, params);
		System.out.println(xml);
	}
	

}
