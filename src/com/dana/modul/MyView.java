package com.dana.modul;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View
{
	//Debug
	private static final String TAG = "MyView";
	
	private int textX = 20; 
	private int textY = 20;
	
	/**
	 * ��д���๹�캯��
	 * @param context
	 */
	
	public MyView (Context context)
	{
		super(context);
		setFocusable(true);
//		setFocusableInTouchMode(true);
	}
	
	/**
	 * ��д�����ͼ����
	 */
	@Override
	protected void onDraw(Canvas canvas)
	{
		//����һ�����ʵ�ʵ��
		Paint paint = new Paint();
		//���û��ʵ���ɫ
		paint.setColor(Color.WHITE);
		//�����ı�
		canvas.drawText("Game", textX, textY, paint);
		super.onDraw(canvas);
	}
	
	/**
	 * ��д���������¼�����
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		//�ж��û����µļ�ֵ�Ƿ�Ϊ������ġ��������ҡ���
		switch(keyCode)
		{
			case KeyEvent.KEYCODE_DPAD_UP:
				//���ϣ�Y�����С
				textY-=2;
				break;
			case KeyEvent.KEYCODE_DPAD_DOWN:
				//����,Y������
				textY+=2;
				break;
			case KeyEvent.KEYCODE_DPAD_LEFT:
				//����,X�����С
				textX-=2;
				break;
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				//����,X������
				textX+=2;
				break;
			default:
				break;
		}
		//�ػ滭��
		invalidate();
		//postInvalidate();
		return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * ��д����̧���¼�����
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		return super.onKeyUp(keyCode, event);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		//��ȡ�û���ָ������X���긳ֵ���ı���X����
		textX=(int)event.getX();
		//��ȡ�û���ָ������Y���긳ֵ���ı���Y����
		textY=(int)event.getY();
		//�ػ滭��
		invalidate();
		//postInvalidate();
		return true;
	}
	
	
//	/**
//	 * ��д�����¼�����
//	 */
//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		int x = (int)event.getX();
//		int y = (int)event.getY();
//		//�����ָ�����Ļ�Ķ���
//		if (event.getAction() == MotionEvent.ACTION_DOWN) {
//			textX = x;
//			textY = y;
//			//�����ָ̧���뿪��Ļ�Ķ���
//		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
//			textX = x;
//			textY = y;
//			//�����ָ����Ļ���ƶ��Ķ���
//		} else if (event.getAction() == MotionEvent.ACTION_UP) {
//			textX = x;
//			textY = y;
//		}
//		//�ػ滭��
//		invalidate();
//		//postInvalidate();
//		return true;
//	}
	
}