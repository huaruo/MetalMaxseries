package com.dana.startapp;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class GetData {
	public static byte[] getStream(String urlpath) throws Exception {//得到字节形式的流数据
		InputStream inputstream;
		//生成一个URL对象
		URL url = new URL(urlpath);
		//打开连接
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		//得到数据流
		inputstream = conn.getInputStream();
		//生成一个字节数组输出流对象
		ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while((len = inputstream.read(buffer)) != -1){
			outputstream.write(buffer,0,len);
		}
		inputstream.close();
		outputstream.close();
		//返回字节数组流
		return outputstream.toByteArray();
	}
}
