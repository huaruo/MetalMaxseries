package com.dana.iface;

import java.io.InputStream;

public interface XmlParser
{
	/**
	 * ����������
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public void parse(InputStream is) throws Exception;
	
	/**
	 * ���л�Xml���󼯺ϣ��õ�XML�ַ���
	 * @param
	 * @return
	 * @throws Exception
	 */
	public String serialize() throws Exception;
	
}