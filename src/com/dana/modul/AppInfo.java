/**
 * ����һ�����ݽṹ��������Ӧ�ó�����Ϣ(icon,name,packageName,versionName,versionCode,��)
 */

package com.dana.modul;

import android.graphics.drawable.Drawable;
import android.util.Log;


public class AppInfo{
	//Debug
	private static final String TAG = "AppInfo";
	
	public String appName = "";
	public String packageName = "";
	public String versionName = "";
	public int versionCode = 0;
	public Drawable appIcon = null;
	
	public void print()
	{
		Log.v(TAG, "Name: " + appName + ", Package: " + packageName);
		Log.v(TAG, "Name: " + appName + ", versionName: " + versionName);
		Log.v(TAG, "Name: " + appName + ", versionCode: " + versionCode);
	}
}