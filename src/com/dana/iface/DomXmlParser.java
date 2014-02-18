/**
 * @author Huaruo.W
 * @issue: 1. 当xml中有多层子结点时，2.xml中的结点有多个attribute，3.试着找出bug原因。
 */

package com.dana.iface;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlSerializer;

import android.util.Log;
import android.util.Xml;

public class DomXmlParser implements XmlParser
{
	//Debug
	private static final String TAG = "DomXmlParser";
	private ArrayList<String> keys = new  ArrayList<String>();
	private ArrayList<Object> values = new ArrayList<Object>();
	
	@Override
	public void parse(InputStream is) throws Exception
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();//取得DocumentBuilderFactory实例
		factory.setIgnoringElementContentWhitespace(true);//设置去掉空格的方法
		DocumentBuilder builder = factory.newDocumentBuilder();//从factory获取解析器 
		Document doc = builder.parse(is);//解析xml文件
		Element rootElement = doc.getDocumentElement(); //获取根结点对象
		String tagName = rootElement.getTagName();//获取根结点元素名称
//		Log.i(TAG,"根结点的名称是： " + tagName);
		keys.add("tagName");
		values.add(tagName);
		Log.i(TAG,"startTag: "+tagName);
		
		NodeList nList = rootElement.getChildNodes(); //调用Node中的getChildNodes()获取根结点的子结点
		String childTagName = nList.item(1).getNodeName();
//		System.out.println("获取根节点下"+ childTagName +"对象");
		keys.add("childTagName");
		values.add(childTagName);
		Log.i(TAG,"startTag: "+childTagName);
		NodeList nodeList = rootElement.getElementsByTagName(childTagName);
		if(null != nodeList)
			display(nodeList);
	
	}
	
	
	public String serialize() throws Exception
	{
		XmlSerializer serializer = Xml.newSerializer();//创建实例
		StringWriter writer = new StringWriter();
		serializer.setOutput(writer);//设置输出方向为writer   
		serializer.startDocument("UTF-8", true);
		
		String rootTag = (String)values.get(0);
		String childRoot = (String)values.get(1);
		serializer.startTag("", rootTag);
		int elementLength = values.size();
		int nodeCount =3; //计算一组结点的元素个数？？？用元素名不同法去比较？
		for (int i=2; i<elementLength; i++)
		{
			serializer.startTag("", childRoot);
			//当有多个属性时？？？
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
	
	
	
	//???有bug
	public String serializebug() throws Exception
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument(); //由builder创建 新文档\
		
		Element rootElement = doc.createElement((String)values.get(0));
		int elementLength = values.size();
		int nodeCount =3;
		for(int i=2; i<elementLength; i++)
		{
			Element childRootElement = doc.createElement((String)values.get(1));
			if(keys.get(i).equals("attr"))
			{
				String[] temp = (String[])values.get(i);
				childRootElement.setAttribute(temp[0], temp[1] + "");
			}
			for(int j=1; j < nodeCount; j++)
			{
				Element childElement = doc.createElement(keys.get(i+j));
				childElement.setTextContent(values.get(i+j) +"");
				childRootElement.appendChild(childElement);
			}
			i = i+nodeCount -1;
			rootElement.appendChild(childRootElement);
		}
		///?????会报错误：Default buffer size used in BufferedWriter constructor. It would be better to be explicit if an 8k-char buffer is required. 
		String resultStr = transDoc(doc);
		return resultStr;
		
	}
	
	
	//使用递归遍历个子结点中的子结点
	public void display(NodeList nList)
	{
		
		int nLength = nList.getLength();
		for(int i =0; i<nLength; i++)
		{
			Node node = nList.item(i);
			NamedNodeMap nodeAttr = node.getAttributes();
			if(nodeAttr!=null)
			{
				int nodeAttrLength = nodeAttr.getLength();
				for(int k=0; k<nodeAttrLength; k++)
				{
					String[] temp = new String[2];
					temp[0] = nodeAttr.item(k).getNodeName();
					temp[1] = nodeAttr.item(k).getNodeValue();
					keys.add("attr");					
					values.add(temp);
					Log.i(TAG, temp[0] + " = " + temp[1]);
				}
			}
			//获取 n 节点下所有的子节点。此处值得注意，在DOM解析时会将所有回车都视为 n 节点的子节点。
			if(node.hasChildNodes())
			{
				NodeList nl2 = node.getChildNodes();
				//因为上面的原因，在此例中第一个 n 节点有 2 个子节点，而第二个 n 节点则有 5 个子节点（因为多了3个回车）。
				int size2 = nl2.getLength();
				for(int j =0; j<size2; j++)
				{
					Node n2 = nl2.item(j);
					//还是因为上面的原因，故此要处判断当 n2 节点有子节点的时才输出。
					if(n2.hasChildNodes())//判断该结点是否还有子结点
					{
						 String str1 = n2.getNodeName();
						 String str2 = n2.getFirstChild().getNodeValue();
						 keys.add(str1);
						 values.add(str2);
						 Log.i(TAG, str1 + " = " + str2);
					}
				}
			}
		}
	}

	public String transDoc(Document doc) throws Exception
	{
		TransformerFactory transFactory = TransformerFactory.newInstance();//取得TransformerFactory实例   
        Transformer transformer = transFactory.newTransformer();    //从transFactory获取Transformer实例   
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");            // 设置输出采用的编码方式   
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");                // 是否自动添加额外的空白   
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");   // 是否忽略XML声明   
          
        StringWriter writer = new StringWriter();  
          
        Source source = new DOMSource(doc); //表明文档来源是doc   
        Result result = new StreamResult(writer);//表明目标结果为writer   
        transformer.transform(source, result);  //开始转换   
        
        return writer.toString(); 
	}

}