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
	//���ڿ���SurfaceView
	private SurfaceHolder sfh;
	//����һ������
	private Paint paint;
	//�ı�������
	private int textX=10, textY=10;
	//����һ���߳�
	private Thread th;
	//�߳������ı�ʶλ
	private boolean flag;
	//����һ������
	private Canvas canvas;
	//������Ļ�Ŀ���
	private  int screenW, screenH;
	
	/**
	 * SurfaceView��ʼ������
	 */
	public MySurfaceView(Context context)
	{
		super(context);
		//ʵ��SurfaceHolder
		sfh=this.getHolder();
		//ΪSurfaceView����״̬����
		sfh.addCallback(callback);
		//ʵ��һ������
		paint = new Paint();
		//���û�����ɫΪ��ɫ
		paint.setColor(Color.WHITE);
		//���ý���
		setFocusable(true);
	}
	
	private Callback callback = new Callback()
	{
		/**
		 * SurfaceView��ͼ��������Ӧ�˺���
		 */
		@Override
		public void surfaceCreated(SurfaceHolder holder)
		{
			screenW = MySurfaceView.this.getWidth();
			screenH = MySurfaceView.this.getHeight();
			flag = true;
			//ʵ���߳�
			th = new Thread(runn);
			//�����߳�
			th.start();
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
	public void myDraw()
	{
		try
		{
			canvas = sfh.lockCanvas();
			if(canvas!=null)
			{
				//-----------���������εķ�ʽ��ˢ��
				////���ƾ���
				//canvas.drawRect(0,0,this.getWidth(),
				//this.getHeight(), paint);
				//-----------������仭����ˢ��
				//		canvas.drawColor(Color.BLACK);
				//-----------������仭��ָ������ɫ������ˢ��
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
	 * �����¼�����
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		textX=(int)event.getX();
		textY=(int)event.getY();
		return true;
	}
	
	/**
	 * �����¼�����
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		return super.onKeyDown(keyCode,  event);
	}
	
	/**
	 * ��Ϸ�߼�
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