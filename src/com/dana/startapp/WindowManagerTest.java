package com.dana.startapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;

import com.dana.modul.FloatView;
import com.dana.modul.MyApplication;

public class WindowManagerTest extends Activity
{
	//Debug
	private static final String TAG = "WindowManagerTest";
	
	private WindowManager mWindowManager;
	private WindowManager.LayoutParams param;
	private FloatView mLayout;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.floatview);
		
		showView();
	}
	
	private void showView()
	{
		mLayout = new FloatView(getApplicationContext());
		mLayout.setBackgroundResource(R.drawable.bg);
		//��ȡWindowManager
		mWindowManager=(WindowManager)getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		//����LayoutParams(ȫ�ֱ���)��ز���
		param = ((MyApplication) getApplication()).getMywmParams();
		param.type=WindowManager.LayoutParams.TYPE_SYSTEM_ALERT; //ϵͳ��ʾ���ͣ���Ҫ
		param.format=1;
		param.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;//������ռ�۽���
		param.flags = param.flags | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
		param.flags = param.flags | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;//�Ű治������
		
		param.alpha = 1.0f;
		
		param.gravity = Gravity.LEFT|Gravity.TOP;//�����������������Ͻ�
		//����Ļ���Ͻ�Ϊԭ��,����x��y��ʼֵ
		param.x=0;
		param.y=0;
		
		//�����������ڳ�������
		param.width=140;
		param.height=140;
		
		//��ʾmyFloatViewͼ��
		mWindowManager.addView(mLayout, param);
		
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		//�ڳ����˳���Activity���٣�ʱ������������
		mWindowManager.removeView(mLayout);
		
	}
}