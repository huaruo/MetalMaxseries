package com.dana.startapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class ProgressBarTest extends Activity
{
	//Debug
	private static final String TAG = "ProgressBarTest";
	
	private Thread mthread; //����һ���߳�
	private ProgressBar pb; //����һ������������
	private boolean stateChange; //��ʶ����ֵ�����С��״̬
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progressbar);
		//ʵ������������
		pb = (ProgressBar) findViewById(R.id.pb_pb);
		mthread = new Thread(runn); //ʵ���̶߳���
		mthread.start();//�����߳�
	}
	
	private Runnable runn = new Runnable()
	{
		@Override
		public void run()
		{
			//ʵ��Runnable�ӿڳ�����
			while(true)
			{
				int current = pb.getProgress();//�õ���ǰ����ֵ
				int currentMax = pb.getMax();//�õ���������������ֵ
				int secCurrent = pb.getSecondaryProgress();//�õ��ײ㵱ǰ����ֵ
				//ʵ�ֽ���ֵԽ��Խ��Խ��ԽС�Ķ�̬Ч��
				if(stateChange == false)
				{
					if(current>=currentMax)
					{
						stateChange =true;
					}
					else
					{
						//���ý���ֵ
						pb.setProgress(current+1);
						//���õײ����ֵ
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