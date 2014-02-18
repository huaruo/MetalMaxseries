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
		//���û����϶������϶��Ķ������ʱ����
		@Override
		public void onStopTrackingTouch(SeekBar sb)
		{
			tv.setText("<�϶���>����϶�");
			Log.i(TAG, "����϶�");
		}
		//���û����϶��������϶�ʱ����
		@Override
		public void onStartTrackingTouch(SeekBar sb)
		{
			Log.i(TAG, "��ʼ�϶�");
		}
		//���϶�����ֵ�����ı��ʱ����
		@Override
		public void onProgressChanged(SeekBar sb, int progress, boolean fromUser)
		{
			tv.setText("��ǰ<�϶���>��ֵΪ��"+progress);
			Log.i(TAG,"�϶�ֵΪ�� " + progress);
		}
	};
}