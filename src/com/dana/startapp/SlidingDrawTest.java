package com.dana.startapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.SlidingDrawer.OnDrawerScrollListener;
import android.widget.TextView;

public class SlidingDrawTest extends Activity
{
	//Debug
	private static final String TAG = "SlidingDrawTest";
	
	private SlidingDrawer mDrawer;
	private ImageButton imbg;
	private boolean flag = false;
	private TextView tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.slidingdrawer);
		
		imbg = (ImageButton)findViewById(R.id.sd_handle);
		mDrawer = (SlidingDrawer) findViewById(R.id.sd_sd);
		tv = (TextView)findViewById(R.id.sd_tv);
		
		mDrawer.setOnDrawerOpenListener(drawerOpenListener);
		mDrawer.setOnDrawerCloseListener(drawerCloseListener);
		mDrawer.setOnDrawerScrollListener(drawerScrollListener);	
	}
	
	private OnDrawerOpenListener drawerOpenListener = new OnDrawerOpenListener()
	{
		@Override
		public void onDrawerOpened()
		{
			flag = true;
			imbg.setImageResource(R.drawable.bg);
		}
	};
	
	private OnDrawerCloseListener drawerCloseListener = new OnDrawerCloseListener()
	{
		@Override
		public void onDrawerClosed()
		{
			flag = false;
			imbg.setImageResource(R.drawable.image);
		}
	};
	
	private OnDrawerScrollListener  drawerScrollListener = new OnDrawerScrollListener()
	{
		@Override
		public void onScrollEnded()
		{
			tv.setText("结束拖动");
		}
		
		@Override
		public void onScrollStarted()
		{
			tv.setText("开始拖动");
		}
	};
	
}