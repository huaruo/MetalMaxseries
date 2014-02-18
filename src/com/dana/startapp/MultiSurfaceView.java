/**
 * @author Huaruo.W
 * @issue:退出程序会崩溃，线程没关闭？  点击一个按钮，清除另一个按钮控制显示的图片
 */

package com.dana.startapp;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MultiSurfaceView extends Activity
{
	//Debug
	private static final String TAG =  "MultiSurfaceView";
	
	private Button btnSingleThread,btnDoubleThread;
	private SurfaceView sfv;
	private SurfaceHolder sfh;
	private ArrayList<Integer> imgList = new ArrayList<Integer>();
	private int imgWidth, imgHeight;
	private Bitmap bitmap;//独立线程读取，独立线程绘图
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.multisurfaceview);
		
		btnSingleThread = (Button) findViewById(R.id.msfv_btn1);
		btnDoubleThread = (Button) findViewById(R.id.msfv_btn2);
		btnSingleThread.setOnClickListener(listener);
		btnDoubleThread.setOnClickListener(listener);
		
		sfv = (SurfaceView) findViewById(R.id.msfv_sfv1);
		sfh = sfv.getHolder();
		sfh.addCallback(mcallBack); // 自动运行surfaceCreated以及surfaceChanged
		
	}
	
	private OnClickListener listener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			switch(v.getId())
			{
			case R.id.msfv_btn1:
				new Load_DrawImage(0,0).start();//开一提一条线程读取并绘图
				break;
			case R.id.msfv_btn2:
				new LoadImage().start();//开一条线程读取
				new DrawImage(imgWidth + 10, 0).start();//开一条线程绘图
				break;
			default:
				break;
			}
		}
	};
	
	private Callback mcallBack = new Callback(){
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
		{
			Log.i(TAG, "Change");
		}
		@Override
		public void surfaceCreated(SurfaceHolder holder)
		{
			Log.i(TAG, "Create");
			//利用反射机制来获取资源中图片ID和尺寸
			Field[] fields = R.drawable.class.getDeclaredFields();
			for (Field field: fields)
			{
				if("image".equals(field.getName()) || ("password".equals(field.getName())))
				{
					int index = 0;
					try{
						index= field.getInt(R.drawable.class);
					}
					catch (IllegalArgumentException e)
					{
						e.printStackTrace();
					}
					catch (IllegalAccessException e)
					{
						e.printStackTrace();
					}
					//保存图片ID
					imgList.add(index);
				}
			}
			
			//取得图像大小
			Bitmap bmImg = BitmapFactory.decodeResource(getResources(), imgList.get(0));
			imgWidth = bmImg.getWidth();
			imgHeight = bmImg.getHeight();
		}
		
		@Override
		public void surfaceDestroyed(SurfaceHolder holder)
		{
			Log.i(TAG, "Destroy");
		}
	};
	
	/*
	 * 读取并显示图片的线程
	 */
	
	private class Load_DrawImage extends Thread
	{
		int x,y;
		int imgIndex = 0;
		
		public Load_DrawImage(int x, int y){
			this.x = x;
			this.y = y;
		}
		
		public void run(){
			while(true)
			{
				Canvas c = sfh.lockCanvas(new Rect(this.x, this.y, this.x +imgWidth, this.y + imgHeight));
				Bitmap bmImg = BitmapFactory.decodeResource(getResources(), imgList.get(imgIndex));
				c.drawBitmap(bmImg, this.x,  this.y, new Paint());
				imgIndex ++;
				if(imgIndex == imgList.size())
					imgIndex = 0;
				//更新屏幕显示内容
				sfh.unlockCanvasAndPost(c);
			}
		}
	};
	
	/*
	 * 只负责绘图的线程
	 */
	private class DrawImage extends Thread {
		int x, y;
		public DrawImage(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		
		public void run(){
			while(true)
			{
				if(bitmap !=null)
				{
					//如果图像有效
					Canvas c = sfh.lockCanvas(new Rect(this.x, this.y, this.x + imgWidth, this.y+ imgHeight));
					c.drawBitmap(bitmap, this.x, this.y, new Paint());//???退出时崩溃报java.lang.NullPointerException
					sfh.unlockCanvasAndPost(c);//更新屏幕显示内容
				}
			}
		}
	};
	
	/*
	 * 只负责读取图片的线程
	 */
	private class LoadImage extends Thread
	{
		int imgIndex = 0;
		public void run()
		{
			while(true)
			{
				bitmap =BitmapFactory.decodeResource(getResources(), imgList.get(imgIndex));
				imgIndex++;
				if(imgIndex == imgList.size())//如果到尽头则重新读取
					imgIndex =0;
			}
		}
	}
}