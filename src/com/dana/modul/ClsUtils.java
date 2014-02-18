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
	 * 与设备配对
	 */
	public static boolean createBond(Class btClass, BluetoothDevice btDevice) throws Exception
	{
		Method createBondMethod = btClass.getMethod("createBond");
		Boolean returnValue = (Boolean) createBondMethod.invoke(btDevice);
		return returnValue.booleanValue();
	}
	
	/**
	 * 与设备解除配对
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
			//取得所有方法
			Method[] hideMethod = clsShow.getMethods();
			int i = 0;
			for(;i<hideMethod.length; i++)
			{
				Log.i(TAG, "method name: " + hideMethod[i].getName());
			}
			//取得所有常量
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