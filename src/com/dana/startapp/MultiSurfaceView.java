/**
 * @author Huaruo.W
 * @issue:�˳������������߳�û�رգ�  ���һ����ť�������һ����ť������ʾ��ͼƬ
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
	private Bitmap bitmap;//�����̶߳�ȡ�������̻߳�ͼ
	
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
		sfh.addCallback(mcallBack); // �Զ�����surfaceCreated�Լ�surfaceChanged
		
	}
	
	private OnClickListener listener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			switch(v.getId())
			{
			case R.id.msfv_btn1:
				new Load_DrawImage(0,0).start();//��һ��һ���̶߳�ȡ����ͼ
				break;
			case R.id.msfv_btn2:
				new LoadImage().start();//��һ���̶߳�ȡ
				new DrawImage(imgWidth + 10, 0).start();//��һ���̻߳�ͼ
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
			//���÷����������ȡ��Դ��ͼƬID�ͳߴ�
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
					//����ͼƬID
					imgList.add(index);
				}
			}
			
			//ȡ��ͼ���С
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
	 * ��ȡ����ʾͼƬ���߳�
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
				//������Ļ��ʾ����
				sfh.unlockCanvasAndPost(c);
			}
		}
	};
	
	/*
	 * ֻ�����ͼ���߳�
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
					//���ͼ����Ч
					Canvas c = sfh.lockCanvas(new Rect(this.x, this.y, this.x + imgWidth, this.y+ imgHeight));
					c.drawBitmap(bitmap, this.x, this.y, new Paint());//???�˳�ʱ������java.lang.NullPointerException
					sfh.unlockCanvasAndPost(c);//������Ļ��ʾ����
				}
			}
		}
	};
	
	/*
	 * ֻ�����ȡͼƬ���߳�
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
				if(imgIndex == imgList.size())//�������ͷ�����¶�ȡ
					imgIndex =0;
			}
		}
	}
}