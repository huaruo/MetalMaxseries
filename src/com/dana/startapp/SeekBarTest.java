package com.dana.startapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class SeekBarTest extends Activity
{
	//Debug
	private static final String TAG = "SeekBarTest";
	
	private SeekBar sb;
	private TextView tv;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seekbar);
		
		sb=(SeekBar)findViewById(R.id.sb_sb);
		tv=(TextView)findViewById(R.id.sb_tv);
		sb.setSecondaryProgress(20);
		sb.setOnSeekBarChangeListener(seekBarChangeListener);
	}
	private OnSeekBarChangeListener seekBarChangeListener = new OnSeekBarChangeListener()
	{
		//当用户对拖动条的拖动的动作完成时触发
		@Override
		public void onStopTrackingTouch(SeekBar sb)
		{
			tv.setText("<拖动条>完成拖动");
			Log.i(TAG, "完成拖动");
		}
		//当用户对拖动条进行拖动时触发
		@Override
		public void onStartTrackingTouch(SeekBar sb)
		{
			Log.i(TAG, "开始拖动");
		}
		//当拖动条的值发生改变的时触发
		@Override
		public void onProgressChanged(SeekBar sb, int progress, boolean fromUser)
		{
			tv.setText("当前<拖动条>的值为："+progress);
			Log.i(TAG,"拖动值为： " + progress);
		}
	};
}