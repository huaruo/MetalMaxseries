package com.dana.startapp;

import com.dana.modul.QPathSurface;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class QPathDraw extends Activity
{
	//Debug
	private static final String TAG = "QPathDraw";
	public static QPathDraw  instance;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		instance = this;
		//设置全屏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//显示自定义的SurfaceView视图
		setContentView(new QPathSurface(this));
	}
}