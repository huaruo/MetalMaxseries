package com.dana.iface;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

public class PullXmlParser implements XmlParser
{
	//Debug
	private static final String TAG = "PullXmlParser";
	
	private ArrayList<String> keys = new ArrayList<String>();
	private ArrayList<Object> values = new ArrayList<Object>();
	
	@Override
	public void parse(InputStream is) throws Exception
	{
//		//����XmlPullParser,�����ַ�ʽ   
//	    //��ʽһ:ʹ�ù�����XmlPullParserFactory   
//	    XmlPullParserFactory pullFactory = XmlPullParserFactory.newInstance();   
//	    XmlPullParser xmlPullParser = pullFactory.newPullParser();   
//	    //��ʽ��:ʹ��Android�ṩ��ʵ�ù�����android.util.Xml  
//	    XmlPullParser xmlPullParser = Xml.newPullParser(); 
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance(); 
		XmlPullParser parser =factory.newPullParser();//����һ��XmlPullParserʵ�� 
		parser.setInput(is, "UTF-8"); //���������� ��ָ�����뷽ʽ
		
		int eventType = parser.getEventType();
		while(eventType!=XmlPullParser.END_DOCUMENT)
		{
			switch(eventType)
			{
				case XmlPullParser.START_DOCUMENT:
					break;
				case XmlPullParser.START_TAG:
					String str1 = parser.getName();

					int attrLength = parser.getAttributeCount();
					if(attrLength!=0)
					{
						if(keys.size()<2)
						{
							keys.add(str1);
							values.add(str1);
							Log.i(TAG, str1 + " = " + str1);
						}
						else
						{
							if(!str1.equals(keys.get(1)))
							{
								keys.add(str1);
								values.add(str1);
								Log.i(TAG, str1 + " = " + str1);
							}
						}
						for(int i =0; i< attrLength; i++)
						{
							String[] temp = new String[2];
							temp[0] = parser.getAttributeName(i);
							temp[1] = parser.getAttributeValue(i);
							keys.add("attr");
							values.add(temp);
							Log.i(TAG, temp[0] + "=" + temp[1]);
						}
					}
					else
					{
						eventType = parser.next();
						String str2 = parser.getText();
						if(str2 == null || str2.trim().equals(""))
							str2 = str1;
						keys.add(str1);
						values.add(str2);
						Log.i(TAG, str1 + " = " + str2);
					}
					break;
				case XmlPullParser.END_TAG:
					break;
				default:
					break;
			}
			eventType = parser.next();
		}
		
	}
	public String serialize() throws Exception
	{
		XmlSerializer serializer = Xml.newSerializer();//����ʵ��
		StringWriter writer = new StringWriter();
		serializer.setOutput(writer);//�����������Ϊwriter   
		serializer.startDocument("UTF-8", true);
		
		String rootTag = (String)values.get(0);
		String childRoot = (String)values.get(1);
		serializer.startTag("", rootTag);
		int elementLength = values.size();
		int nodeCount =3; //����һ�����Ԫ�ظ�����������Ԫ������ͬ��ȥ�Ƚϣ�
		for (int i=2; i<elementLength; i++)
		{
			serializer.startTag("", childRoot);
			//���ж������ʱ������
			if(keys.get(i).equals("attr"))
			{
				String[] temp = (String[]) values.get(i);
				serializer.attribute("", temp[0], temp[1]);
			}
			
			for(int j=1; j<nodeCount; j++)
			{
				String str1 =keys.get(i+j);
				String str2 = (String)values.get(i+j);
				serializer.startTag("", str1);
				serializer.text(str2);
				serializer.endTag("", str1);
			}
			
			i=i+nodeCount-1;
			serializer.endTag("", childRoot);
		}
		serializer.endTag("", rootTag);
		serializer.endDocument();
		
		String text = writer.toString();
//		Log.i(TAG,text);
		text = text.replace("><", ">\r\n<");
		return text;
	}
}