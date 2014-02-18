/**
 * Issue: android  2.3及以上不支持 自动接听
 */

package com.dana.startapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ToggleButton;

import com.dana.modul.PhoneUtils;

public class TelephonyManagerTest extends Activity
{
	//Debug
	private static final String TAG = "TelephonyManagerTest";
	
	private RadioGroup rg;//来电操作单选框
	private ToggleButton tbtnRadioSwitch;//Radio开关
	private ToggleButton tbtnDataConn;//数据连接 的开关
	private TelephonyManager telMgr;
	
	private CallStateListener stateListener;
	int checkedId = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.telephonymanagertest);
		
		telMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
//		telMgr.listen(new CallStateListener(), CallStateListener.LISTEN_CALL_STATE);
		telMgr.listen(new TelListener(), PhoneStateListener.LISTEN_CALL_STATE);
		
		PhoneUtils.printAllInfo(TelephonyManager.class);
		
		rg = (RadioGroup) findViewById(R.id.tpm_rgrpSelect);
		rg.setOnCheckedChangeListener(checkedChangeListener);
		tbtnRadioSwitch=(ToggleButton) findViewById(R.id.tpm_tbtnRadioSwitch);
		tbtnRadioSwitch.setOnClickListener(listener);
		
		try{
			tbtnRadioSwitch.setChecked(PhoneUtils.getITelephony(telMgr).isRadioOn());
		}
		catch(Exception e)
		{
			Log.i(TAG, "error: " + e.getMessage());
		}
		tbtnDataConn = (ToggleButton) findViewById(R.id.tpm_tbtnDataConn);
		tbtnDataConn.setOnClickListener(listener);
		try{
			tbtnDataConn.setChecked(PhoneUtils.getITelephony(telMgr).isDataConnectivityPossible());
		}
		catch(Exception e)
		{
			Log.i(TAG, "error: " + e.getMessage());
		}
	}
	
	/**
	 * 来电时的操作
	 */
	public OnCheckedChangeListener checkedChangeListener = new OnCheckedChangeListener()
	{

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId)
		{
			TelephonyManagerTest.this.checkedId=checkedId;
		}
	};
	
	/**
	 * Radio和数据连接的开关
	 */
	public OnClickListener listener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			switch(v.getId())
			{
				case R.id.tpm_tbtnRadioSwitch:
					try
					{
						PhoneUtils.getITelephony(telMgr).setRadio(tbtnRadioSwitch.isChecked());
					}
					catch(Exception e)
					{
						Log.i(TAG, "error: " + e.getMessage());
					}
					break;
				case R.id.tpm_tbtnDataConn:
					try
					{
						if(tbtnDataConn.isChecked())
							PhoneUtils.getITelephony(telMgr).enableDataConnectivity();
						else if(!tbtnDataConn.isChecked())
							PhoneUtils.getITelephony(telMgr).disableDataConnectivity();
					}
					catch(Exception e)
					{
						Log.i(TAG, "error: " + e.getMessage());
					}
			}
		}
	};
	
	/**
	 * 监视电话状态
	 */
	
	public class CallStateListener extends PhoneStateListener
	{
		@Override
		public void onCallStateChanged(int state, String incomingNumber)
		{
			switch(state)
			{
				case TelephonyManager.CALL_STATE_IDLE: //挂断
					Log.i(TAG, "IDLE: " + incomingNumber);
					break;
				case TelephonyManager.CALL_STATE_OFFHOOK: //接听
					Log.i(TAG, "OFFHOOK: " + incomingNumber);
					break;
				case TelephonyManager.CALL_STATE_RINGING: //来电
					if(TelephonyManagerTest.this.checkedId == R.id.tpm_rbtnAutoAccept)
					{
						try
						{
							//需要<uses-permission android:name = "android.permission.MODIFY_PHONE_STATE"/>
							PhoneUtils.getITelephony(telMgr).silenceRinger();//铃声静音
							PhoneUtils.getITelephony(telMgr).answerRingingCall();//自动接听
						}
						catch(Exception e)
						{
							Log.i(TAG, "error: "+e.getMessage());
						}
					}
					else if(TelephonyManagerTest.this.checkedId==R.id.tpm_rbtnAutoReject)
					{
						try
						{
							PhoneUtils.getITelephony(telMgr).endCall();//挂断
							PhoneUtils.getITelephony(telMgr).cancelMissedCallsNotification();//取消未接显示
						}
						catch(Exception e)
						{
							Log.i(TAG, "error: " +e.getMessage());
						}
					}
					break;
				default:
					break;
			}
			super.onCallStateChanged(state,incomingNumber);
		}
	}
	
	
	private class TelListener extends PhoneStateListener{  
        @Override  
        public void onCallStateChanged(int state, String incomingNumber) {  
            super.onCallStateChanged(state, incomingNumber);  
            Log.v("Phone State","state:" + state);  
            switch(state){  
                case TelephonyManager.CALL_STATE_RINGING:  //监听电话状态
                    Log.v("Phone State","incoming number:" + incomingNumber + " received"); 
                    try {  
                    	 tanswerRingingCall();//自动接通电话
                       
                    } catch (Exception e) {  
                    	e.printStackTrace();  
                    }  
                    break;  
                   
                case TelephonyManager.CALL_STATE_OFFHOOK:  
                    Log.v("Phone State","incoming number:" + incomingNumber + " picked up");  
                    break;  
                case TelephonyManager.CALL_STATE_IDLE:  
                    Log.v("Phone State", "incoming number:" + incomingNumber + " ended");  
                    break;  
                default:  
                        break;  
            }  
     
        }
	}

	
	
	public synchronized void tanswerRingingCall()
	{//据说该方法只能用于Android2.3及2.3以上的版本上，但本人在2.2上测试可以使用
	    try
	    {
	        Intent localIntent1 = new Intent(Intent.ACTION_HEADSET_PLUG);
	        localIntent1.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
	        localIntent1.putExtra("state", 1);
	        localIntent1.putExtra("microphone", 1);
	        localIntent1.putExtra("name", "Headset");
	        sendOrderedBroadcast(localIntent1, "android.permission.CALL_PRIVILEGED");
	        Intent localIntent2 = new Intent(Intent.ACTION_MEDIA_BUTTON);
	        KeyEvent localKeyEvent1 = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_HEADSETHOOK);
	        localIntent2.putExtra("android.intent.extra.KEY_EVENT", localKeyEvent1);
	        sendOrderedBroadcast(localIntent2, "android.permission.CALL_PRIVILEGED");
	        Intent localIntent3 = new Intent(Intent.ACTION_MEDIA_BUTTON);
	        KeyEvent localKeyEvent2 = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK);
	        localIntent3.putExtra("android.intent.extra.KEY_EVENT", localKeyEvent2);
	        sendOrderedBroadcast(localIntent3, "android.permission.CALL_PRIVILEGED");
	        Intent localIntent4 = new Intent(Intent.ACTION_HEADSET_PLUG);
	        localIntent4.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
	        localIntent4.putExtra("state", 0);
	        localIntent4.putExtra("microphone", 1);
	        localIntent4.putExtra("name", "Headset");
	        sendOrderedBroadcast(localIntent4, "android.permission.CALL_PRIVILEGED");
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	} 
}