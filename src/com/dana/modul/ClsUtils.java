package com.dana.modul;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.bluetooth.BluetoothDevice;
import android.util.Log;

public class ClsUtils
{
	//Debug
	private static final String TAG = "ClsUtils";
	
	/**
	 * ���豸���
	 */
	public static boolean createBond(Class btClass, BluetoothDevice btDevice) throws Exception
	{
		Method createBondMethod = btClass.getMethod("createBond");
		Boolean returnValue = (Boolean) createBondMethod.invoke(btDevice);
		return returnValue.booleanValue();
	}
	
	/**
	 * ���豸������
	 */
	public static boolean removeBond(Class btClass, BluetoothDevice btDevice) throws Exception
	{
		Method removeBondMethod = btClass.getMethod("removeBond");
		Boolean returnValue = (Boolean) removeBondMethod.invoke(btDevice);
		return returnValue.booleanValue();
	}
	
	/**
	 * @param clsShow
	 */
	public static void printAllInfo(Class clsShow)
	{
		try
		{
			//ȡ�����з���
			Method[] hideMethod = clsShow.getMethods();
			int i = 0;
			for(;i<hideMethod.length; i++)
			{
				Log.i(TAG, "method name: " + hideMethod[i].getName());
			}
			//ȡ�����г���
			Field[] allFields = clsShow.getFields();
			for(i=0; i< allFields.length; i++)
			{
				Log.i(TAG, "Field name: " + allFields[i].getName());
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