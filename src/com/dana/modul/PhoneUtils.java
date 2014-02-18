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
	 * ��TelephoneyManager��ʵ����ITelephony,������
	 */
	public static ITelephony getITelephony(TelephonyManager telMgr) throws Exception
	{
		Method getITelephonyMethod = telMgr.getClass().getDeclaredMethod("getITelephony");
		getITelephonyMethod.setAccessible(true);//˽�л�����Ҳ��ʹ��
		return (ITelephony)getITelephonyMethod.invoke(telMgr);
	}
	
	public static void printAllInfo(Class clsShow)
	{
		try
		{
			//ȡ�����з���
			Method[] hideMethod = clsShow.getDeclaredMethods();
			int i = 0;
			for(; i < hideMethod.length; i++)
			{
				Log.i(TAG, "method name: " + hideMethod[i].getName());
			}
			
			//ȡ�����г���
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