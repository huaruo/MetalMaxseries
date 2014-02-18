package com.dana.iface;

import java.io.InputStream;

public interface XmlParser
{
	/**
	 * 解析输入流
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public void parse(InputStream is) throws Exception;
	
	/**
	 * 序列化Xml对象集合，得到XML字符串
	 * @param
	 * @return
	 * @throws Exception
	 */
	public String serialize() throws Exception;
	
}