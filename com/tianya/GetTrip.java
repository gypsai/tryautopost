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
			
			//����
			System.out.println(elements.get(0).child(0).text());
			
			
			//¥������
			Elements ename = doc.getElementsContainingOwnText("¥����");
			System.out.println(ename.get(0).child(0).text());
			
			//����ʱ��
			Elements etime = doc.getElementsContainingOwnText("ʱ�䣺");
			System.out.println(etime.get(0).text());
			
			//����
			Elements econtent = doc.getElementsByAttributeValueContaining("class", "bbs-content clearfix");
			System.out.println(econtent);
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
}
