package com.dana.modul;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;


public class SAXHandler extends DefaultHandler
{
	//Debug
	private static final String TAG = "SAXHandler";
	
	private ArrayList<String> keys = new ArrayList<String>();
	private ArrayList<Object> values = new ArrayList<Object>();
	
	@Override
	public void startDocument() throws SAXException
	{
		super.startDocument();
	}
	
	@Override
	public void endDocument() throws SAXException
	{
		super.endDocument();
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
	{
		//���濪ʼ���
		keys.add("startTag");
		values.add(localName);
		Log.i(TAG,"startTag: "+localName);
		
		//��������ֵ
		for(int i = 0; i<attributes.getLength(); i++)
		{
			keys.add("attr");
			String[] temp = new String[2];
			temp[0] = attributes.getLocalName(i);
			temp[1] = attributes.getValue(i);
			values.add(temp);
			Log.i(TAG, temp[0] + " = " + temp[1]);
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		//����������
		keys.add("endTag");
		values.add(localName);
		Log.i(TAG,"endTag: " + localName);
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException
	{
		String value = new String(ch, start, length);
		value = value.trim();
		if(value.length() ==0)
			return;
		//�����ı�
		keys.add("text");
		values.add(value);
		Log.i(TAG, "text: " + value);		
	}
	
	public ArrayList<String> GetKeys()
	{
		return keys;
	}
	
	public ArrayList<Object> GetValues()
	{
		return values;
	}
}