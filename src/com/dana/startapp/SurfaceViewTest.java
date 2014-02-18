/*
 * 1.������:���ʵ�����ĸ��дTestSurfaceView ��
 * 2.���Ժͷ�����һ�����ʵ�����ĸСд,֮��ÿ�����ʵ�����ĸ��дvoid clearDraw() �ȵȡ�
 */
/**
 * @author Huaruo.W
 * Issue: ��ΰ���ʱ���滭�˳�����ʱ�ͻ���ֿ�ָ�����
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
	private int Y_axis[],//�������Ҳ���Y���ϵĵ�
	centerY, //������
	oldX, oldY, //��һ��XY��
	currentX; //��ǰ���Ƶ���X���ϵĵ�
	
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
		
		//��̬�������Ҳ��Ķ�ʱ��
		mTimer = new Timer();
		mTimerTask = new MyTimerTask();
		
		//��ʼ��y������
		centerY = (getWindowManager().getDefaultDisplay().getHeight() - sfv.getTop())/2;
		Y_axis = new int[getWindowManager().getDefaultDisplay().getWidth()];
		for(int i = 1; i<Y_axis.length; i++)
		{
			//�������Ҳ�
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
					//ֱ�ӻ������Ҳ�
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
			//��̬�������Ҳ�
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
				//��������յ㣬����������
				ClearDraw();
				currentX = 0;
				oldY = centerY;
			}
		}
	}
	
	
	/*
	 * ����ָ������
	 */
	private void SimpleDraw(int length)
	{
		if(length != 0)
			oldX = 0;
		Canvas canvas = sfh.lockCanvas(new Rect(oldX, 0, oldX + length,
				getWindowManager().getDefaultDisplay().getHeight()));//�ؼ�����ȡ����
		Log.i(TAG, String.valueOf(oldX) + "," + String.valueOf(oldX + length));
		
		Paint mPaint = new Paint();
		mPaint.setColor(Color.GREEN);//����Ϊ��ɫ
		mPaint.setStrokeWidth(2);//���û��ʴ�ϸ
		
		int y;
		for(int i = oldX +1; i<length; i++)
		{
			//�滭���Ҳ�, ��.lockCanvas()ָ��Rect�ڼ���ѭ�����ߵĴ�����������߻�ͼЧ�ʡ�
			y = Y_axis[i-1];
			//???������
			canvas.drawLine(oldX,  oldY,  i, y,  mPaint);
			oldX = i;
			oldY = y;
		}
		sfh.unlockCanvasAndPost(canvas);//�����������ύ���õ�ͼ��
	}
	
	private void ClearDraw()
	{
		Canvas canvas = sfh.lockCanvas(null);
		canvas.drawColor(Color.BLACK);//�������
		sfh.unlockCanvasAndPost(canvas);
	}
	
}