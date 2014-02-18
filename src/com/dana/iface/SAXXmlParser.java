package com.dana.iface;

import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlSerializer;

import android.util.Log;
import android.util.Xml;

import com.dana.modul.SAXHandler;

public class SAXXmlParser implements XmlParser
{
	//Debug
	private static final String TAG = "SAXXmlParser";
	
	SAXHandler handler = new SAXHandler(); //ʵ�����Զ���Handler 
	@Override
	public void parse(InputStream is) throws Exception
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();//ȡ��SAXParserFactoryʵ��
		SAXParser parser = factory.newSAXParser();//��factory��ȡSAXParserʵ��  
		XMLReader reader = parser.getXMLReader(); 
		reader.setContentHandler(handler);  //�����Զ���Handler�������������  
		InputSource isource = new InputSource(is);
		reader.parse(isource);
		Log.i(TAG,handler.GetValues().toString());
	}
	
	@Override
	public String serialize() throws Exception
	{
//		SAXTransformerFactory factory = (SAXTransformerFactory) TransformerFactory.newInstance();//ȡ��SAXTransformerFactoryʵ��   
//        TransformerHandler handler = factory.newTransformerHandler();           //��factory��ȡTransformerHandlerʵ��   
//        Transformer transformer = handler.getTransformer();                     //��handler��ȡTransformerʵ��   
//        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");            // ����������õı��뷽ʽ   
//        transformer.setOutputProperty(OutputKeys.INDENT, "yes");                // �Ƿ��Զ���Ӷ���Ŀհ�   
//        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");   // �Ƿ����XML����   
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
//		Result result = new StreamResult(writer);  
//        handler.setResult(result);  
			serializer.setOutput(writer);
			serializer.startDocument("UTF-8", true);
			
			for(int i=0; i<handler.GetKeys().size(); i++)
			{
				if(handler.GetKeys().get(i).equals("startTag"))
				{
					serializer.startTag("", (String)handler.GetValues().get(i));
				}
				else if(handler.GetKeys().get(i).equals("Attr"))
				{
					String[] temp = (String[]) handler.GetValues().get(i);
					serializer.attribute("", temp[0], temp[1]);
				}
				else if(handler.GetKeys().get(i).equals("text"))
				{
					serializer.text((String) handler.GetValues().get(i));
				}
				else if(handler.GetKeys().get(i).equals("endTag"))
				{
					serializer.endTag("",  (String) handler.GetValues().get(i));
				}
			}
			serializer.endDocument();
			String text = writer.toString();
			text=text.replace("><", ">\r\n<");
		return text;
	}
}