package com.dana.modul;

import android.app.Application;
import android.view.WindowManager;

public class MyApplication extends Application
{
	/**
	 * ����ȫ�ֱ���
	 * ȫ�ֱ���һ�㶼�Ƚ������ڴ���һ���������������ļ�����ʹ��static��̬����
	 * 
	 * ����ʹ������Application�� ������ݵķ���ʵ��ȫ�ֱ���
	 * ע����AndroidManifest.xml�е�Application�е�
	 * 
	 */
	private WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
	
	public WindowManager.LayoutParams getMywmParams()
	{
		return wmParams;
	}
	
}