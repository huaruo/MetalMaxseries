package com.dana.modul;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
	//Debug
	private static final String TAG = "MyService";
	
	public static String ServiceState = "";
	@Override
	public IBinder onBind(Intent arg0)
	{
		Log.i(TAG, "onBind");
		ServiceState = "onBind";
		return null;
	}
	@Override
	public boolean onUnbind(Intent intent)
	{
		super.onUnbind(intent);
		Log.i(TAG,"onUnbind");
		ServiceState="onUnbind";
		return false;
	}
	@Override
	public void onCreate()
	{
		super.onCreate();
		Log.i(TAG,"onCreate");
		ServiceState="onCreate";
	}
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		Log.i(TAG, "onDestroy");
		ServiceState="onDestroy";
	}
	@Override
	public void onStart(Intent intent, int startid)
	{
		super.onStart(intent, startid);
		Log.i(TAG, "onStart");
		ServiceState = "onStart";
	}
	
}