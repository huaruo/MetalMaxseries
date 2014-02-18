/**
 * @author Huaruo.W
 * @issue: 1. ��xml���ж���ӽ��ʱ��2.xml�еĽ���ж��attribute��3.�����ҳ�bugԭ��
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
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();//ȡ��DocumentBuilderFactoryʵ��
		factory.setIgnoringElementContentWhitespace(true);//����ȥ���ո�ķ���
		DocumentBuilder builder = factory.newDocumentBuilder();//��factory��ȡ������ 
		Document doc = builder.parse(is);//����xml�ļ�
		Element rootElement = doc.getDocumentElement(); //��ȡ��������
		String tagName = rootElement.getTagName();//��ȡ�����Ԫ������
//		Log.i(TAG,"�����������ǣ� " + tagName);
		keys.add("tagName");
		values.add(tagName);
		Log.i(TAG,"startTag: "+tagName);
		
		NodeList nList = rootElement.getChildNodes(); //����Node�е�getChildNodes()��ȡ�������ӽ��
		String childTagName = nList.item(1).getNodeName();
//		System.out.println("��ȡ���ڵ���"+ childTagName +"����");
		keys.add("childTagName");
		values.add(childTagName);
		Log.i(TAG,"startTag: "+childTagName);
		NodeList nodeList = rootElement.getElementsByTagName(childTagName);
		if(null != nodeList)
			display(nodeList);
	
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
	
	
	
	//???��bug
	public String serializebug() throws Exception
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument(); //��builder���� ���ĵ�\
		
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
		///?????�ᱨ����Default buffer size used in BufferedWriter constructor. It would be better to be explicit if an 8k-char buffer is required. 
		String resultStr = transDoc(doc);
		return resultStr;
		
	}
	
	
	//ʹ�õݹ�������ӽ���е��ӽ��
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
			//��ȡ n �ڵ������е��ӽڵ㡣�˴�ֵ��ע�⣬��DOM����ʱ�Ὣ���лس�����Ϊ n �ڵ���ӽڵ㡣
			if(node.hasChildNodes())
			{
				NodeList nl2 = node.getChildNodes();
				//��Ϊ�����ԭ���ڴ����е�һ�� n �ڵ��� 2 ���ӽڵ㣬���ڶ��� n �ڵ����� 5 ���ӽڵ㣨��Ϊ����3���س�����
				int size2 = nl2.getLength();
				for(int j =0; j<size2; j++)
				{
					Node n2 = nl2.item(j);
					//������Ϊ�����ԭ�򣬹ʴ�Ҫ���жϵ� n2 �ڵ����ӽڵ��ʱ�������
					if(n2.hasChildNodes())//�жϸý���Ƿ����ӽ��
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
		TransformerFactory transFactory = TransformerFactory.newInstance();//ȡ��TransformerFactoryʵ��   
        Transformer transformer = transFactory.newTransformer();    //��transFactory��ȡTransformerʵ��   
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");            // ����������õı��뷽ʽ   
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");                // �Ƿ��Զ���Ӷ���Ŀհ�   
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");   // �Ƿ����XML����   
          
        StringWriter writer = new StringWriter();  
          
        Source source = new DOMSource(doc); //�����ĵ���Դ��doc   
        Result result = new StreamResult(writer);//����Ŀ����Ϊwriter   
        transformer.transform(source, result);  //��ʼת��   
        
        return writer.toString(); 
	}

}