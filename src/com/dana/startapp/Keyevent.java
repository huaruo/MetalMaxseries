package com.dana.startapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

public class Keyevent extends Activity{
	private static final String TAG = "KeyEvent";
	
	private ImageView mImage;
	private TextView mAlphavalueText;
	private int mAlphavalue;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.keyevent);
		mImage = (ImageView) findViewById(R.id.key_image);
		mAlphavalueText = (TextView) findViewById(R.id.key_alphavalue);
		mAlphavalue = 100;
		mImage.setAlpha(mAlphavalue);
		mAlphavalueText.setText("Alpha = " + mAlphavalue * 100 /0xff + "%");
	}
	
	@SuppressWarnings("deprecation")
	public boolean onKeyDown (int keyCode, KeyEvent msg) {
		Log.i(TAG, "onKeyDown: keyCode = " + keyCode);
		Log.i(TAG, "onKeyDown: String = " + msg.toString());
		
		switch(keyCode)
		{
			case KeyEvent.KEYCODE_DPAD_UP:
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				mAlphavalue +=20;
				break;
			case KeyEvent.KEYCODE_DPAD_DOWN:
			case KeyEvent.KEYCODE_DPAD_LEFT:
				mAlphavalue -= 20;
				break;
			default:
				break;		
		}
		if(mAlphavalue >= 0xFF) 
			mAlphavalue =0xFF;
		if(mAlphavalue <= 0x0) 
			mAlphavalue =0x0;
		mImage.setAlpha(mAlphavalue);
		mAlphavalueText.setText("Alpha = " + mAlphavalue*100/0xff + "%");
		return super.onKeyDown(keyCode, msg);
	}
}