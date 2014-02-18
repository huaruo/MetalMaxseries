package com.dana.startapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;

public class DoubleClick extends Activity {
	//Debug
	private static final String TAG = "DoubleClick";
	
	private Button leftUp;
	private Button rightUp;
	private Button leftDown;
	private Button rightDown;
	
	private Context activity;
	//计算点击的次数
	private int count;
	//第一次点击的时间long型
	private long firstClick;
	//最后一次点击的时间
	private long lastClick;
	//第一次点击的button的id
	private int firstId;
	//调试环境是否开启
	private boolean isDebugOpen = false;
	//日志环境是否开启
	private boolean isLogOpen = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.doubleclick);
		Log.i(TAG, "++ On Create ++");
		
		activity = DoubleClick.this;
		leftUp = (Button) findViewById(R.id.dc_leftUp);
		rightUp = (Button) findViewById(R.id.dc_rightUp);
		leftDown = (Button) findViewById(R.id.dc_leftDown);
		rightDown = (Button) findViewById(R.id.dc_rightDown);
		
		leftUp.setOnTouchListener(listener);
		rightUp.setOnTouchListener(listener); 
        leftDown.setOnTouchListener(listener); 
        rightDown.setOnTouchListener(listener); 
		
	}
	
	private OnTouchListener listener = new OnTouchListener()
	{
		@Override
		public boolean onTouch(View v, MotionEvent event)
		{
			if(event.getAction() == MotionEvent.ACTION_DOWN)
			{
				//如果第二次点击 距离第一次点击时间过长   那么将第二次点击看为第一次点击
				if(firstClick!=0 && firstId!=0 && System.currentTimeMillis()-firstClick>300){ 
                    count = 0; 
                    firstId = 0; 
			}
			count++;
			if(count==1){ 
                firstClick = System.currentTimeMillis(); 
                //记录第一次点得按钮的id 
                firstId = v.getId(); 
            }
			else if(count==2){ 
                lastClick = System.currentTimeMillis(); 
                //两次点击小于300ms 也就是连续点击 
                if(lastClick-firstClick<300){ 
                    //第二次点击的button的id 
                    int id = v.getId(); 
                    //判断两次点击的button是否是同一个button 
                    if(id == firstId){ 
                        switch(id){ 
                            case R.id.dc_leftUp: 
                                break; 
                             
                            case R.id.dc_rightUp: 
                                break; 
                                 
                            case R.id.dc_leftDown: 
                                if(isLogOpen){//关闭日志环境 
                                    Toast.makeText(activity, "日志关闭", 0).show(); 
                                }else{//打开日志环境 
                                    Toast.makeText(activity, "日志开启", 0).show(); 
                                } 
                                isLogOpen = !isLogOpen; 
                                break; 
                                 
                            case R.id.dc_rightDown: 
                                if(isDebugOpen){//关闭调试环境 
                                    Toast.makeText(activity, "调试关闭", 0).show(); 
                                }else{//打开调试环境 
                                    Toast.makeText(activity, "调试开启", 0).show(); 
                                } 
                                isDebugOpen = !isDebugOpen; 
                                break; 
                       		}
                    	}
                	}
                	clear();
				}
			}
			return false;
		}

		//清空状态 
        private void clear(){ 
            count = 0; 
            firstClick = 0; 
            lastClick = 0; 
            firstId = 0; 
        } 
	};
	
}