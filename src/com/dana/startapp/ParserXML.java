/**
 * @author Huaruo.W
 * @Issue  �������������ʵ�廯���
 * @�Ƽ�ʹ��pull�����ĵ�
 */


package com.dana.startapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.dana.iface.DomXmlParser;
import com.dana.iface.PullXmlParser;
import com.dana.iface.SAXXmlParser;
import com.dana.iface.XmlImpl;

public class ParserXML extends Activity
{
	//Debug
	private static final String TAG = "SAXTest";
	
	private Button btnSaxp, btnSaxc, btnDomp, btnDomc, btnPullp, btnPullc;
	private TextView memo;
	private SAXXmlParser xmlSaxParser  = new SAXXmlParser();
	private DomXmlParser xmlDomParser  = new DomXmlParser();
	private PullXmlParser xmlPullParser  = new PullXmlParser();
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parserxml);
		
		btnSaxp = (Button) findViewById(R.id.parxml_saxp);
		btnSaxc = (Button) findViewById(R.id.parxml_saxc);
		btnDomp = (Button) findViewById(R.id.parxml_domp);
		btnDomc = (Button) findViewById(R.id.parxml_domc);
		btnPullp = (Button) findViewById(R.id.parxml_pullp);
		btnPullc = (Button) findViewById(R.id.parxml_pullc);
		memo = (TextView) findViewById(R.id.parxml_show);
		
		btnSaxp.setOnClickListener(listener);
		btnSaxc.setOnClickListener(listener);	
		btnDomp.setOnClickListener(listener);	
		btnDomc.setOnClickListener(listener);	
		btnPullp.setOnClickListener(listener);	
		btnPullc.setOnClickListener(listener);	
	}
	
	private OnClickListener listener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			String filepath = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator+"POSDB"+File.separator;
			String filename = filepath + "xmltest.xml";
			switch(v.getId())
			{
				case R.id.parxml_saxp://SAX����XML�������ֱ�����Ե�ֵ
					try
					{
						//���������
						InputStream is = new FileInputStream(filename);
//						File file = new File(filename);
//						InputStream is = new FileInputStream(file);
						//InputStream is = getResources().getAssets().open("xmltest.xml"); //��ȡ��Ŀ��Ŀ¼��assets�ļ��е�xmltest.xml�ļ���
						//InputStream is = getResources().openRawResource(R.raw.xmltest); //��ȡ��Ŀres/raw�ļ����µ�xmltest.xml�ļ���
						xmlSaxParser.parse(is);  
					}
					catch(Exception e)
					{
						Log.e(TAG, e.getMessage());
						e.printStackTrace();
					}
					XmlImpl ixml=new XmlImpl();

			        String str= filepath + "grade.xml";

			        ixml.init();

			        ixml.createXml(str);    //����xml

			        ixml.parserXml(str);    //��ȡxml
					
					break;
				case R.id.parxml_saxc://SAX����XML
					try
					{
						String text = xmlSaxParser.serialize();
						memo.setText(text);	
						File file = new File(filepath, "ctest.xml");
						FileOutputStream fos = new FileOutputStream(file);
//						FileOutputStream fos = openFileOutput("ctest.xml", Context.MODE_PRIVATE);
						fos.write(text.getBytes("UTF-8"));
						fos.close();
					}
					catch(Exception e)
					{
						Log.e(TAG, e.getMessage());
						e.printStackTrace();
					}
					break;
				case R.id.parxml_domp://Dom����XML�������ֱ�����Ե�ֵ
					try
					{
						//���������
						InputStream is = new FileInputStream(filename);
						//InputStream is = getResources().getAssets().open("xmltest.xml"); 
		//				InputStream is = getResources().openRawResource(R.raw.xmltest);
						xmlDomParser.parse(is);
						
					}
					catch(Exception e)
					{
						Log.e(TAG, e.getMessage());
						e.printStackTrace();
					}
					break;
				case R.id.parxml_domc://Dom����XML
					try
					{
						String text = xmlDomParser.serialize();
						memo.setText(text);	
						File file = new File(filepath, "ctest.xml");
						FileOutputStream fos = new FileOutputStream(file);
//						FileOutputStream fos = openFileOutput("ctest.xml", Context.MODE_PRIVATE);
						fos.write(text.getBytes("UTF-8"));
						fos.close();
					}
					catch(Exception e)
					{
						Log.e(TAG, e.getMessage());
					}
					break;
				case R.id.parxml_pullp://Pull����XML�������ֱ�����Ե�ֵ
					try
					{
						//���������
						InputStream is = new FileInputStream(filename);
						//InputStream is = getResources().getAssets().open("xmltest.xml"); 
		//				InputStream is = getResources().openRawResource(R.raw.xmltest);
						xmlPullParser.parse(is);
					}
					catch(Exception e)
					{
						Log.e(TAG, e.getMessage());
						e.printStackTrace();
					}
					break;
				case R.id.parxml_pullc://Pull����XML
					try
					{
						String text = xmlPullParser.serialize();
						memo.setText(text);
						File file = new File(filepath, "ctest.xml");
						FileOutputStream fos = new FileOutputStream(file);
//						FileOutputStream fos = openFileOutput("ctest.xml", Context.MODE_PRIVATE);
						fos.write(text.getBytes("UTF-8"));
						fos.close();
					}
					catch(Exception e)
					{
						Log.e(TAG, e.getMessage());
					}
					break;
				default:
					break;
			}
		}
	};
}