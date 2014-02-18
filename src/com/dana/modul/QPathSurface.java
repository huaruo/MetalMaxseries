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
 * ����������
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
	//--------������SurfaceView��Ϸ���
	//���������߳�Ա����(��ʼ�㣬����(������)����ֹ�㡣3������)
	private int startX, startY, controlX, controlY, endX, endY;
	//Path
	private Path  path;
	//Ϊ�˲�Ӱ�������ʣ����� ���Ʊ��������ߵ�����һ���»���
	private Paint paintQ;
	//�����(�ñ��������߸�����)
	private Random random;
	
	/**
	 * SurfaceView��ʼ������
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
		//--------������SurfaceView��Ϸ���
		//������������س�ʼ��
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
		 * SurfaceView��ͼ����,��Ӧ�˺���
		 */
		public void surfaceCreated(SurfaceHolder holder)
		{
			screenW = QPathSurface.this.getWidth();
			screenH = QPathSurface.this.getHeight();
			flag = true;
			//ʵ���߳�
			th = new Thread(runn);
			//�����߳�
			th.start();
			// -----------������SurfaceView��Ϸ���
		}
		
		/**
		 * SurfaceView��ͼ״̬�����ı䣬��Ӧ�˺���
		 */
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
		{
			
		}
		/**
		 * SurfaceView��ͼ����ʱ����Ӧ�˺���
		 */
		@Override
		public void surfaceDestroyed(SurfaceHolder holder)
		{
			flag = false;
		}
	};
	
	/**
	 * ��Ϸ��ͼ
	 */
	public  void myDraw()
	{
		try
		{
			canvas = sfh.lockCanvas();
			if(canvas != null)
			{
				//-----------������仭����ˢ��
				canvas.drawColor(Color.BLACK);
				// -----------������SurfaceView��Ϸ���
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
	 * ���Ʊ���������
	 * 
	 * @param canvas������
	 */
	public void drawQpath(Canvas canvas)
	{
		path.reset();//����path
		//���������ߵ���ʼ��
		path.moveTo(startX, startY);
		//���ñ��������ߵĲ������Լ���ֹ��
		path.quadTo(controlX, controlY, endX, endY);
		//���Ʊ���������(Path)
		canvas.drawPath(path,  paintQ);
	}
	
	/**
	 * �����¼�����
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		endX = (int) event.getX();
		endY = (int) event.getY();
		return true;
	}
	
	/**
	 * ��Ϸ �߼�
	 */
	private void logic()
	{
		if(endX!=0 && endY!=0)
		{
			//���ò�����Ϊ�߶�X/Y��һ��
			controlX = random.nextInt((endX - startX)/2);
			controlY = random.nextInt((endY - startY)/2);
		}
	}
	
	/**
	 * �����¼�����
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