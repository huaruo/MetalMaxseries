package com.dana.startapp;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ScreenSwitch extends Activity
{
	//Debug
	private static final String TAG = "ScreenSwitch";
	
	private TextView tv;
	private Button btnSwitch;
	private int clickNum = 0;//标记button点击次数
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Log.i(TAG,"++onCreate++");
		tv = (TextView)findViewById(R.id.main_tv);
		btnSwitch = (Button) findViewById(R.id.main_btn);
		tv.setVisibility(View.VISIBLE);
		tv.setText("当前屏幕为竖屏");
		btnSwitch.setVisibility(View.VISIBLE);
		btnSwitch.setText("切换屏幕");
		btnSwitch.setOnClickListener(listener);
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		Log.i(TAG,"++onCOnfigurationCHanged++");
		if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE)
		{
			tv.setText("当前屏幕为横屏");
		}
		else
		{
			tv.setText("当前屏幕为竖屏");
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		Log.e(TAG,"++ ONSAVE ++");
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);
		Log.e(TAG, "++ ONRESTORE ++");
	}
	
	private OnClickListener listener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			switch(v.getId())
			{
				case R.id.main_btn:
					if(clickNum%2 ==0)//设置屏幕为横屏
					{
						ScreenSwitch.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
					}
					else
					{
						ScreenSwitch.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
					}
					clickNum++;
					break;
				default:
					break;
			}
		}
	};
}