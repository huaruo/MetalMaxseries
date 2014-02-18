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
	//�������Ĵ���
	private int count;
	//��һ�ε����ʱ��long��
	private long firstClick;
	//���һ�ε����ʱ��
	private long lastClick;
	//��һ�ε����button��id
	private int firstId;
	//���Ի����Ƿ���
	private boolean isDebugOpen = false;
	//��־�����Ƿ���
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
				//����ڶ��ε�� �����һ�ε��ʱ�����   ��ô���ڶ��ε����Ϊ��һ�ε��
				if(firstClick!=0 && firstId!=0 && System.currentTimeMillis()-firstClick>300){ 
                    count = 0; 
                    firstId = 0; 
			}
			count++;
			if(count==1){ 
                firstClick = System.currentTimeMillis(); 
                //��¼��һ�ε�ð�ť��id 
                firstId = v.getId(); 
            }
			else if(count==2){ 
                lastClick = System.currentTimeMillis(); 
                //���ε��С��300ms Ҳ����������� 
                if(lastClick-firstClick<300){ 
                    //�ڶ��ε����button��id 
                    int id = v.getId(); 
                    //�ж����ε����button�Ƿ���ͬһ��button 
                    if(id == firstId){ 
                        switch(id){ 
                            case R.id.dc_leftUp: 
                                break; 
                             
                            case R.id.dc_rightUp: 
                                break; 
                                 
                            case R.id.dc_leftDown: 
                                if(isLogOpen){//�ر���־���� 
                                    Toast.makeText(activity, "��־�ر�", 0).show(); 
                                }else{//����־���� 
                                    Toast.makeText(activity, "��־����", 0).show(); 
                                } 
                                isLogOpen = !isLogOpen; 
                                break; 
                                 
                            case R.id.dc_rightDown: 
                                if(isDebugOpen){//�رյ��Ի��� 
                                    Toast.makeText(activity, "���Թر�", 0).show(); 
                                }else{//�򿪵��Ի��� 
                                    Toast.makeText(activity, "���Կ���", 0).show(); 
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

		//���״̬ 
        private void clear(){ 
            count = 0; 
            firstClick = 0; 
            lastClick = 0; 
            firstId = 0; 
        } 
	};
	
}