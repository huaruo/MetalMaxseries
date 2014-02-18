package com.dana.modul;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;


/**
 * 赛贝尔曲线
 * @author Huaruo.W
 */

public class QPathSurface extends SurfaceView
{
	private SurfaceHolder sfh;
	private Paint paint;
	private Thread th;
	private boolean flag;
	private Canvas canvas;
	public static int screenW, screenH;
	//--------以上是SurfaceView游戏框架
	//贝塞尔曲线成员变量(起始点，控制(操作点)，终止点。3点坐标)
	private int startX, startY, controlX, controlY, endX, endY;
	//Path
	private Path  path;
	//为了不影响主画笔，这里 绘制贝塞尔曲线单独用一个新画笔
	private Paint paintQ;
	//随机库(让贝塞尔曲线更明显)
	private Random random;
	
	/**
	 * SurfaceView初始化函数
	 */
	public  QPathSurface(Context context)
	{
		super(context);
		sfh=this.getHolder();
		sfh.addCallback(callback);
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		setFocusable(true);
		//--------以上是SurfaceView游戏框架
		//贝塞尔曲线相关初始化
		path= new Path();
		paintQ = new Paint();
		paintQ.setAntiAlias(true);
		paintQ.setStyle(Style.STROKE);
		paintQ.setStrokeWidth(5);
		paintQ.setColor(Color.WHITE);
		random = new Random();
	}
	
	private Callback callback = new Callback()
	{
		/**
		 * SurfaceView视图创建,响应此函数
		 */
		public void surfaceCreated(SurfaceHolder holder)
		{
			screenW = QPathSurface.this.getWidth();
			screenH = QPathSurface.this.getHeight();
			flag = true;
			//实例线程
			th = new Thread(runn);
			//启动线程
			th.start();
			// -----------以上是SurfaceView游戏框架
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
	public  void myDraw()
	{
		try
		{
			canvas = sfh.lockCanvas();
			if(canvas != null)
			{
				//-----------利用填充画布，刷屏
				canvas.drawColor(Color.BLACK);
				// -----------以上是SurfaceView游戏框架
				drawQpath(canvas);
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
	 * 绘制贝塞尔曲线
	 * 
	 * @param canvas主画布
	 */
	public void drawQpath(Canvas canvas)
	{
		path.reset();//重置path
		//贝塞尔曲线的起始点
		path.moveTo(startX, startY);
		//设置贝塞尔曲线的操作点以及终止点
		path.quadTo(controlX, controlY, endX, endY);
		//绘制贝塞尔曲线(Path)
		canvas.drawPath(path,  paintQ);
	}
	
	/**
	 * 触屏事件监听
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		endX = (int) event.getX();
		endY = (int) event.getY();
		return true;
	}
	
	/**
	 * 游戏 逻辑
	 */
	private void logic()
	{
		if(endX!=0 && endY!=0)
		{
			//设置操作点为线段X/Y的一半
			controlX = random.nextInt((endX - startX)/2);
			controlY = random.nextInt((endY - startY)/2);
		}
	}
	
	/**
	 * 按键事件监听
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		return super.onKeyDown(keyCode, event);
	}
	
	private Runnable runn = new Runnable()
	{
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