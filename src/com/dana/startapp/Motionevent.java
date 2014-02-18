package com.dana.startapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class Motionevent extends Activity
{
	private static final String TAG = "MotionEvent";
	
	private TextView mAction;
	private TextView mPosition;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.motionevent);
		setContentView(new TestMotionView(this));
		mAction = (TextView)findViewById(R.id.motionevent_tv);
		mPosition = (TextView)findViewById(R.id.position);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		int Action = event.getAction();
		float X = event.getX();
		float Y = event.getY();
		Log.i(TAG, "Action = " + Action);
		Log.i(TAG,"(" + X + "," + Y + ")");
		mAction.setText("Action = " + Action);
		mPosition.setText("Position = (" + X + "," + Y +")");
		return true;
	}
	
	public class TestMotionView extends View {
		private Paint mPaint = new Paint();
		private int mAction;
		private float mX;
		private float mY;
		public TestMotionView(Context c) {
		super(c);
		mAction = MotionEvent.ACTION_UP;
		mX = 0;
		mY = 0;
		}
		@Override
		protected void onDraw(Canvas canvas) {
		Paint paint = mPaint;
		canvas.drawColor(Color.WHITE);
		if(MotionEvent.ACTION_MOVE == mAction) { // 移动动作
		paint.setColor(Color.RED);
		}else if(MotionEvent.ACTION_UP == mAction) { // 抬起动作
		paint.setColor(Color.GREEN);
		}else if(MotionEvent.ACTION_DOWN == mAction) { // 按下动作
		paint.setColor(Color.BLUE);
		}
		canvas.drawCircle(mX, mY,10, paint);
		setTitle("A = " + mAction + " ["+ mX +","+ mY +"]");
		}
		@Override
		public boolean onTouchEvent(MotionEvent event) {
		mAction = event.getAction(); // 获得动作
		mX = event.getX(); // 获得坐标
		mY = event.getY();
		Log.v(TAG, "Action = "+ mAction );
		Log.v(TAG, "("+mX+","+mY+")");
		invalidate(); // 重新绘制
		return true;
		}
		}
	
	
}