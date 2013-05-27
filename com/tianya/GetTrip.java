package com.tianya;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class GetTrip {

	
	public static void main(String args[])
	{
		
		String url = "http://bbs.tianya.cn/post-685-252254-1.shtml";
		String useragent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.28 (KHTML, like Gecko) Chrome/26.0.1397.2 Safari/537.28";
		try {
			Document doc = Jsoup.connect(url).userAgent(useragent).timeout(100000).get();
			Elements elements = doc.getElementsByAttributeValue("class", "s_title");
			
			//标题
			System.out.println(elements.get(0).child(0).text());
			
			
			//楼主名字
			Elements ename = doc.getElementsContainingOwnText("楼主：");
			System.out.println(ename.get(0).child(0).text());
			
			//发布时间
			Elements etime = doc.getElementsContainingOwnText("时间：");
			System.out.println(etime.get(0).text());
			
			//内容
			Elements econtent = doc.getElementsByAttributeValueContaining("class", "bbs-content clearfix");
			System.out.println(econtent);
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
}
