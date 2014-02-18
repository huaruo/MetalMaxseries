/*
 * 1.类命名:单词的首字母大写TestSurfaceView 。
 * 2.属性和方法第一个单词的首字母小写,之后每个单词的首字母大写void clearDraw() 等等。
 */
/**
 * @author Huaruo.W
 * Issue: 多次按定时器绘画退出程序时就会出现空指针错误，
 */

package com.dana.startapp;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SurfaceViewTest extends Activity{
	//Debug
	private static final String TAG = "SurfaceViewTest";
	
	private Button btnSimpleDraw, btnTimerDraw;
	private SurfaceView sfv;
	private SurfaceHolder sfh;
	
	private Timer mTimer;
	private MyTimerTask mTimerTask;
	private int Y_axis[],//保存正弦波的Y轴上的点
	centerY, //中心线
	oldX, oldY, //上一个XY点
	currentX; //当前绘制到的X轴上的点
	
	private final boolean[] threadFlag = {false};
	private Thread mthread = null;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.surfaceviewtest);
		
		btnSimpleDraw = (Button) findViewById(R.id.sfvt_btn1);
		btnTimerDraw = (Button) findViewById(R.id.sfvt_btn2);
		btnSimpleDraw.setOnClickListener(listener);
		btnTimerDraw.setOnClickListener(listener);
		sfv = (SurfaceView) findViewById(R.id.sfvt_sfv1);
		sfh = sfv.getHolder();
		
		//动态绘制正弦波的定时器
		mTimer = new Timer();
		mTimerTask = new MyTimerTask();
		
		//初始化y轴数据
		centerY = (getWindowManager().getDefaultDisplay().getHeight() - sfv.getTop())/2;
		Y_axis = new int[getWindowManager().getDefaultDisplay().getWidth()];
		for(int i = 1; i<Y_axis.length; i++)
		{
			//计算正弦波
			Y_axis[i-1] = centerY - (int)(100*Math.sin(i*2*Math.PI/180));
		}
	}
	
	private OnClickListener listener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			switch(v.getId())
			{
				case R.id.sfvt_btn1:
					ClearDraw();
					//直接绘制正弦波
					oldY = centerY;
					SimpleDraw(Y_axis.length-1);
					break;
				case R.id.sfvt_btn2:
					if(!threadFlag[0])
					{
						threadFlag[0] =  true;
						if(mthread == null)
						{
							mthread = new Thread(runn);
							mthread.start();
						}
					}
					break;
				default:
					break;
			}
		}
	};
	
	private Runnable runn = new Runnable()
	{
		@Override
		public void run()
		{
			ClearDraw();
			oldY= centerY;
			//动态绘制正弦波
			mTimer.schedule(mTimerTask, 0, 5);
			mthread = null;
			threadFlag[0] = true;
		}
	};
	
	private class MyTimerTask extends TimerTask{
		@Override
		public void run(){
			SimpleDraw(currentX);
			currentX++;
			if(currentX == Y_axis.length -1)
			{
				//如果到了终点，则清屏重来
				ClearDraw();
				currentX = 0;
				oldY = centerY;
			}
		}
	}
	
	
	/*
	 * 绘制指定区域
	 */
	private void SimpleDraw(int length)
	{
		if(length != 0)
			oldX = 0;
		Canvas canvas = sfh.lockCanvas(new Rect(oldX, 0, oldX + length,
				getWindowManager().getDefaultDisplay().getHeight()));//关键：获取画布
		Log.i(TAG, String.valueOf(oldX) + "," + String.valueOf(oldX + length));
		
		Paint mPaint = new Paint();
		mPaint.setColor(Color.GREEN);//画笔为绿色
		mPaint.setStrokeWidth(2);//设置画笔粗细
		
		int y;
		for(int i = oldX +1; i<length; i++)
		{
			//绘画正弦波, 在.lockCanvas()指定Rect内减少循环画线的次数，可以提高绘图效率。
			y = Y_axis[i-1];
			//???有问题
			canvas.drawLine(oldX,  oldY,  i, y,  mPaint);
			oldX = i;
			oldY = y;
		}
		sfh.unlockCanvasAndPost(canvas);//解锁画布，提交画好的图像
	}
	
	private void ClearDraw()
	{
		Canvas canvas = sfh.lockCanvas(null);
		canvas.drawColor(Color.BLACK);//清除画布
		sfh.unlockCanvasAndPost(canvas);
	}
	
}