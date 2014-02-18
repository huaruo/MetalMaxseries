/**
 * 尽量在Canvas上处理图像，surface创建Canvas，管理view在surface上的绘图操作。
 */

package com.dana.startapp;

import com.dana.modul.MySurfaceView;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

public class GameSurface extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//设置全屏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//显示自定义的SurfaceView视图
		setContentView(new MySurfaceView(this));
	}
}