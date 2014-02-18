/*
 * startService��bindService����������Service����ô����֮����ʲô�����أ���startService������Service����Ҫ��stopService������Service��������stopService������Activity�����˶�Service�������š�bindService������Service������unbindService��������Ҳ������Activity����֮��(onDestroy)�Զ�������
 */

/**
 * @author Huaruo.W
 * issue:���ơ�ʹ�ó�����̫���ף� ��bind���˳���������
 */



package com.dana.startapp;

import com.dana.modul.MyService;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ServiceTest extends Activity
{
	//Debug
	private static final String TAG = "ServiceTest";
	
	private Button btnStartService, btnStopService, btnBindService, btnUnbindService, btnExit;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.servicetest);
		
		btnStartService = (Button) findViewById(R.id.svt_btn1);
		btnStopService = (Button) findViewById(R.id.svt_btn2);
		btnBindService = (Button) findViewById(R.id.svt_btn3);
		btnUnbindService = (Button) findViewById(R.id.svt_btn4);
		btnExit = (Button) findViewById(R.id.svt_btn5);
		
		btnStartService.setOnClickListener(listener);
		btnStopService.setOnClickListener(listener);
		btnBindService.setOnClickListener(listener);
		btnUnbindService.setOnClickListener(listener);
		btnExit.setOnClickListener(listener);
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		Log.i(TAG, "++ onDestroy ++");
	}
	
	private OnClickListener listener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent(ServiceTest.this, MyService.class);
			switch(v.getId())
			{
				case R.id.svt_btn1:
					startService(intent);
					break;
				case R.id.svt_btn2:
					stopService(intent);
					break;
				case R.id.svt_btn3:
					bindService(intent, _connection, Service.BIND_AUTO_CREATE);
					break;
				case R.id.svt_btn4:
					if(MyService.ServiceState.equals("onBind"))//Service�󶨺���ܽ��
						unbindService(_connection);
					break;
				case R.id.svt_btn5:
					finish();
					break;
				default:
					break;
			}
		}
	};
	
	private ServiceConnection _connection = new ServiceConnection()
	{
		@Override
		public void onServiceConnected(ComponentName arg0, IBinder arg1)
		{
		}
		@Override
		public void onServiceDisconnected(ComponentName name){
			
		}
	};

}