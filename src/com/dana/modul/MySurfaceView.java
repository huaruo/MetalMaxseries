package com.dana.modul;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView
{
	//用于控制SurfaceView
	private SurfaceHolder sfh;
	//声明一个画笔
	private Paint paint;
	//文本的坐标
	private int textX=10, textY=10;
	//声明一条线程
	private Thread th;
	//线程消亡的标识位
	private boolean flag;
	//声明一个画布
	private Canvas canvas;
	//声明屏幕的宽高
	private  int screenW, screenH;
	
	/**
	 * SurfaceView初始化函数
	 */
	public MySurfaceView(Context context)
	{
		super(context);
		//实例SurfaceHolder
		sfh=this.getHolder();
		//为SurfaceView添加状态监听
		sfh.addCallback(callback);
		//实例一个画笔
		paint = new Paint();
		//设置画笔颜色为白色
		paint.setColor(Color.WHITE);
		//设置焦点
		setFocusable(true);
	}
	
	private Callback callback = new Callback()
	{
		/**
		 * SurfaceView视图创建，响应此函数
		 */
		@Override
		public void surfaceCreated(SurfaceHolder holder)
		{
			screenW = MySurfaceView.this.getWidth();
			screenH = MySurfaceView.this.getHeight();
			flag = true;
			//实例线程
			th = new Thread(runn);
			//启动线程
			th.start();
		}
		
		/**
		 * SurfaceView视图状态发生改变，响应此函数
		 */
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
		{
			
		}
		/**
		 * SurfaceView视图消亡时，响应此函数
		 */
		@Override
		public void surfaceDestroyed(SurfaceHolder holder)
		{
			flag = false;
		}
	};
	
	
	/**
	 * 游戏绘图
	 */
	public void myDraw()
	{
		try
		{
			canvas = sfh.lockCanvas();
			if(canvas!=null)
			{
				//-----------利用填充矩形的方式，刷屏
				////绘制矩形
				//canvas.drawRect(0,0,this.getWidth(),
				//this.getHeight(), paint);
				//-----------利用填充画布，刷屏
				//		canvas.drawColor(Color.BLACK);
				//-----------利用填充画布指定的颜色分量，刷屏
				canvas.drawRGB(0, 0, 0);
				canvas.drawText("Game", textX, textY, paint);
			}
		}
		catch(Exception e)
		{
			// TODO: handle exception
		}
		finally
		{
			if(canvas !=null)
				sfh.unlockCanvasAndPost(canvas);
		}
	}
	
	/**
	 * 触屏事件监听
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		textX=(int)event.getX();
		textY=(int)event.getY();
		return true;
	}
	
	/**
	 * 按键事件监听
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		return super.onKeyDown(keyCode,  event);
	}
	
	/**
	 * 游戏逻辑
	 */
	private void logic()
	{
	}
	
	private Runnable runn = new Runnable()
	{
		@Override
		public void run()
		{
			while(flag)
			{
				long start = System.currentTimeMillis();
				myDraw();
				logic();
				long end = System.currentTimeMillis();
				try
				{
					if(end-start<50)
					{
						Thread.sleep(50-(end-start));
					}
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	};
}