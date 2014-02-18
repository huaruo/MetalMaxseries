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
	
	SAXHandler handler = new SAXHandler(); //实例化自定义Handler 
	@Override
	public void parse(InputStream is) throws Exception
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();//取得SAXParserFactory实例
		SAXParser parser = factory.newSAXParser();//从factory获取SAXParser实例  
		XMLReader reader = parser.getXMLReader(); 
		reader.setContentHandler(handler);  //根据自定义Handler规则解析输入流  
		InputSource isource = new InputSource(is);
		reader.parse(isource);
		Log.i(TAG,handler.GetValues().toString());
	}
	
	@Override
	public String serialize() throws Exception
	{
//		SAXTransformerFactory factory = (SAXTransformerFactory) TransformerFactory.newInstance();//取得SAXTransformerFactory实例   
//        TransformerHandler handler = factory.newTransformerHandler();           //从factory获取TransformerHandler实例   
//        Transformer transformer = handler.getTransformer();                     //从handler获取Transformer实例   
//        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");            // 设置输出采用的编码方式   
//        transformer.setOutputProperty(OutputKeys.INDENT, "yes");                // 是否自动添加额外的空白   
//        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");   // 是否忽略XML声明   
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