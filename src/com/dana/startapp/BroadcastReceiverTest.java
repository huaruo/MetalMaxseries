/**
 * @author Huaruo.W
 * Issue: �ж�BroadcastReceiver������Ҫ�Ż�, ��̬ע���BroadcastReceiver�Ƿ�Ҫunregister?
 */

package com.dana.startapp;

import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class BroadcastReceiverTest extends Activity
{
	//Debug
	private static final String TAG = "BroadcastReceiver";
	
	private Button btnInternal1, btnInternal2, btnSystem, btnExit;
	private static final String INTERNAL_ACTION_1 = "com.dana.startapp.Internal_1";
	private static final String INTERNAL_ACTION_2 = "com.dana.startapp.Internal_2";
	private static final String INTERNAL_ACTION_3 = "com.dana.startapp.Internal_3";
	
	private Intent intent = null;
	
	//��ʶbroadcastreceiver�Ƿ����
	private boolean brcflag1 = false;
	private boolean brcflag2 = false;
	private boolean brcflag3 = false;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.broadcastreceivertest);
		
		btnInternal1 = (Button) findViewById(R.id.bcr_btn1);
		btnInternal2 = (Button) findViewById(R.id.bcr_btn2);
		btnSystem = (Button) findViewById(R.id.bcr_btn3);
		btnExit = (Button) findViewById(R.id.bcr_btn4);
		
		btnInternal1.setOnClickListener(listener);
		btnInternal2.setOnClickListener(listener);
		btnSystem.setOnClickListener(listener);
		btnExit.setOnClickListener(listener);
		
		//��̬ע��㲥��Ϣ
		registerReceiver(bcrInternal1, new IntentFilter(INTERNAL_ACTION_1));
		
	}
	
	private OnClickListener listener = new  OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			switch(v.getId())
			{
				case R.id.bcr_btn1://����̬ע���BroadcastReceiver��������	
					brcflag1 = true;
					intent = new Intent(INTERNAL_ACTION_1);
					sendBroadcast(intent);
					break;
				case R.id.bcr_btn2://����̬ע���BroadcastReceiver��������
					brcflag2 = true;
					intent = new Intent(INTERNAL_ACTION_2);
					sendBroadcast(intent);
					break;
				case R.id.bcr_btn3://��̬ע�ᣬ ����2����Ϣ��BroadcastReceiver
					brcflag3 = true;
					IntentFilter ifilter = new IntentFilter();
					ifilter.addAction(Intent.ACTION_BATTERY_CHANGED);//ϵͳ���������Ϣ
					ifilter.addAction(INTERNAL_ACTION_3);
					registerReceiver(batInfoReceiver, ifilter);
					
					intent = new Intent(INTERNAL_ACTION_3);
					intent.putExtra("Name", "Hello");
					intent.putExtra("Blog", "http://blog.csdn.net/hellogv");
					sendBroadcast(intent);//���� ��ȥ
					break;
				case R.id.bcr_btn4:
//					if(bcrExist(INTERNAL_ACTION_1))
//						unregisterReceiver(bcrInternal1);
//					if(bcrExist(INTERNAL_ACTION_3))
//						unregisterReceiver(batInfoReceiver);
//					if(brcflag1)
						unregisterReceiver(bcrInternal1);
					if(brcflag3)
						unregisterReceiver(batInfoReceiver);
					BroadcastReceiverTest.this.finish();
				default:
					break;
			}
		}
	};
	
	/*
	 * ���ն�̬ע��㲥��BroadcastReceiver
	 */
	
	private BroadcastReceiver bcrInternal1 = new BroadcastReceiver(){
		public void onReceive(Context context, Intent intent)
		{
			String action = intent.getAction();
			Toast.makeText(context, "��̬:" + action, Toast.LENGTH_SHORT).show();
		}
	};
	
	private BroadcastReceiver batInfoReceiver = new BroadcastReceiver()
	{
		public void  onReceive(Context context, Intent intent)
		{
			String action = intent.getAction();
			//�����׽����action��ACTION_BATTERY_CHANGED
			if(Intent.ACTION_BATTERY_CHANGED.equals(action))
			{
				//��δ֪Intent���������ݣ�����Ҫͨ�����·������о�
				Bundle b = intent.getExtras();
				Object[] lstName = b.keySet().toArray();
				
				for(int i=0; i<lstName.length; i++)
				{
					String keyName = lstName[i].toString();
					Log.i(TAG, keyName + ": " + String.valueOf(b.get(keyName)));
				}
			}
			
			//�����׽����action��INTERNAL_ACTION_3
			if(INTERNAL_ACTION_3.equals(action))
			{
				//��δ֪Intent���������ݣ�����Ҫͨ�����·����о�
				Bundle b = intent.getExtras();
				Object[] lstName = b.keySet().toArray();
				
				for(int i=0; i<lstName.length; i++)
				{
					String keyName = lstName[i].toString();
					Log.i(keyName,b.getString(keyName));
				}
			}
		}
	};
	//�ж�BroadCastReceiver�Ƿ����
	//???��Ҫ����
	private boolean bcrExist(String action)
	{
		boolean ret;
		PackageManager manager = getPackageManager();
		//Ҫ���ҵ�BroadcastReceiver
		Intent intent = new Intent(action);
		List<ResolveInfo> resolveInfos = manager.queryBroadcastReceivers(intent, PackageManager.GET_INTENT_FILTERS);
		
		if(resolveInfos.size() ==0)
		{
			Log.i("TAG", action + ": " + "��BroadCast������");
			ret = false;
		}
		else
			ret = true;
		
		return ret;
		
	}
	
}