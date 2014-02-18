package com.dana.startapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class ProgressBarTest extends Activity
{
	//Debug
	private static final String TAG = "ProgressBarTest";
	
	private Thread mthread; //声明一条线程
	private ProgressBar pb; //声明一个进度条对象
	private boolean stateChange; //标识进度值最大最小的状态
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progressbar);
		//实力进度条对象
		pb = (ProgressBar) findViewById(R.id.pb_pb);
		mthread = new Thread(runn); //实例线程对象
		mthread.start();//启动线程
	}
	
	private Runnable runn = new Runnable()
	{
		@Override
		public void run()
		{
			//实现Runnable接口抽象函数
			while(true)
			{
				int current = pb.getProgress();//得到当前进度值
				int currentMax = pb.getMax();//得到进度条的最大进度值
				int secCurrent = pb.getSecondaryProgress();//得到底层当前进度值
				//实现进度值越来越大，越来越小的动态效果
				if(stateChange == false)
				{
					if(current>=currentMax)
					{
						stateChange =true;
					}
					else
					{
						//设置进度值
						pb.setProgress(current+1);
						//设置底层进度值
						pb.setSecondaryProgress(current+1);
					}
				}
				else
				{
					if(current<=0)
					{
						stateChange=false;
					}
					else
					{
						pb.setProgress(current-1);
						pb.setSecondaryProgress(current-1);
					}
				}
				try{
					Thread.sleep(100);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	};
}