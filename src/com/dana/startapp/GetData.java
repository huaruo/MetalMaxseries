package com.dana.startapp;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class GetData {
	public static byte[] getStream(String urlpath) throws Exception {//�õ��ֽ���ʽ��������
		InputStream inputstream;
		//����һ��URL����
		URL url = new URL(urlpath);
		//������
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		//�õ�������
		inputstream = conn.getInputStream();
		//����һ���ֽ��������������
		ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while((len = inputstream.read(buffer)) != -1){
			outputstream.write(buffer,0,len);
		}
		inputstream.close();
		outputstream.close();
		//�����ֽ�������
		return outputstream.toByteArray();
	}
}
