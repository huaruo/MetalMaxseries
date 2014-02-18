package com.dana.startapp;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;
public class AutoAnswerCall extends Activity {  
    TextView tv1;  
    TelephonyManager telManager;  
    
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.main);  
          
        telManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);  
        telManager.listen(new TelListener(), PhoneStateListener.LISTEN_CALL_STATE);  
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
                    	 answerRingingCall();//自动接通电话
                       
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
        public synchronized void answerRingingCall() {  //接听  
            try {  
                Intent localIntent1 = new Intent(Intent.ACTION_HEADSET_PLUG);  
                localIntent1.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);  
                localIntent1.putExtra("state", 1);  
                localIntent1.putExtra("microphone", 1);  
                localIntent1.putExtra("name", "Headset");  
                sendOrderedBroadcast(localIntent1,  
                        "android.permission.CALL_PRIVILEGED");  
                Intent localIntent2 = new Intent(Intent.ACTION_MEDIA_BUTTON);  
                KeyEvent localKeyEvent1 = new KeyEvent(KeyEvent.ACTION_DOWN,  
                        KeyEvent.KEYCODE_HEADSETHOOK);  
                localIntent2.putExtra("android.intent.extra.KEY_EVENT",  
                        localKeyEvent1);  
                sendOrderedBroadcast(localIntent2,  
                        "android.permission.CALL_PRIVILEGED");  
                Intent localIntent3 = new Intent(Intent.ACTION_MEDIA_BUTTON);  
                KeyEvent localKeyEvent2 = new KeyEvent(KeyEvent.ACTION_UP,  
                        KeyEvent.KEYCODE_HEADSETHOOK);  
                localIntent3.putExtra("android.intent.extra.KEY_EVENT",  
                        localKeyEvent2);  
                sendOrderedBroadcast(localIntent3,  
                        "android.permission.CALL_PRIVILEGED");  
                Intent localIntent4 = new Intent(Intent.ACTION_HEADSET_PLUG);  
                localIntent4.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);  
                localIntent4.putExtra("state", 0);  
                localIntent4.putExtra("microphone", 1);  
                localIntent4.putExtra("name", "Headset");  
                sendOrderedBroadcast(localIntent4,  
                        "android.permission.CALL_PRIVILEGED");  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
    }  
   
    
}  
