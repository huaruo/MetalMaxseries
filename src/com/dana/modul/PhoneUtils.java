package com.dana.modul;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ITelephony;

public class PhoneUtils
{
	//Debug
	private static final String TAG = "PhoneUtils";
	/**
	 * 从TelephoneyManager中实例化ITelephony,并返回
	 */
	public static ITelephony getITelephony(TelephonyManager telMgr) throws Exception
	{
		Method getITelephonyMethod = telMgr.getClass().getDeclaredMethod("getITelephony");
		getITelephonyMethod.setAccessible(true);//私有化函数也能使用
		return (ITelephony)getITelephonyMethod.invoke(telMgr);
	}
	
	public static void printAllInfo(Class clsShow)
	{
		try
		{
			//取得所有方法
			Method[] hideMethod = clsShow.getDeclaredMethods();
			int i = 0;
			for(; i < hideMethod.length; i++)
			{
				Log.i(TAG, "method name: " + hideMethod[i].getName());
			}
			
			//取得所有常量
			Field[] allFields =clsShow.getFields();
			for(i=0; i<allFields.length; i++)
			{
				Log.i(TAG,"Field name:" + allFields[i].getName());
			}
		}
		catch(SecurityException e)
		{
			e.printStackTrace();
		}
		catch(IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}