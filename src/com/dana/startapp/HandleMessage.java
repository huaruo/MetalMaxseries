/**
 * Handlerһ����������;��1)ִ�мƻ�����2)�̼߳�ͨ�š�
 */

package com.dana.startapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

//public class HandleMessage extends Activity
//{
//	//Debug
//	private final static String TAG = "HandleMessage";
//	
//	private Button btn;
//	private static TextView tv;
//	private MyHandler mHandler;
//	private Thread mThread = null;
//	private Handler thandler;
//	private Thread tthread = null;
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState)
//	{
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.main);
//		
////		thandler = new Handler();//����Handler, ͨ��Runnable�����߳��и��½���
//		thandler = new mtHandler();//��Message�����߳��������½���
//		
//		btn = (Button)findViewById(R.id.main_btn);
//		tv = (TextView)findViewById(R.id.main_tv);
//		btn.setVisibility(View.VISIBLE);
//		tv.setVisibility(View.VISIBLE);
//		btn.setText("����handle");
//		btn.setOnClickListener(listener);
//	}
//	
//	private OnClickListener listener = new OnClickListener()
//	{
//		@Override
//		public void onClick(View v)
//		{
//			switch(v.getId())
//			{
//			case R.id.main_btn:
////				sendMsgInSameThread();
////				sendMsgInDfThread();
////				updateUIByRunnable();
//				updateByMessage();
//				break;
//			default:
//				break;
//			}
//		}
//	};
//	
//	
//	private static class MyHandler extends Handler
//	{
////		private final WeakReference<Activity> mActivity;
////	    public MyHandler(Activity activity) {
////	        mActivity = new WeakReference<Activity>(activity);
////	    }
//
//		public MyHandler(Looper looper)
//		{
//			super(looper);
//		}
//		
//		@Override
//		public void handleMessage(Message msg)
//		{
//			//������Ϣ
//			tv.setText(msg.obj.toString());
//		}
//	}
//	
//	private void sendMsgInSameThread()
//	{
//		/**
//		 * ͬ�߳��ڲ�ͬ��������Ϣ����
//		 */
//		Looper looper = Looper.myLooper();//ȡ�õ�ǰ�߳����looper
//		MyHandler mHandler = new MyHandler(looper);//����һ��handlerʹ֮����looperͨ��
//		//Button�����������mHandler����Ϣ����looper���ٷ���messageQueue�У�ͬʱmHandlerҲ���Խ�������looper��Ϣ
//		mHandler.removeMessages(0);
//		String msgStr = "���̲߳�ͬ�����Ϣ����Ϣ����Button";
//		Message m = mHandler.obtainMessage();
//		m.what = 1;
//		m.obj = msgStr;
////		Message m = mHandler.obtainMessage(1,1,1,msgStr);//����Ҫ���ݵ���Ϣ
//		/*
//		 * mHandler.obtainMessage(what, arg1, arg2, obj)
//		 * what  Value to assign to the returned Message.what field.
//		 * arg1  Value to assign to the returned Message.arg1 field. 
//		 * arg2  Value to assign to the returned Message.arg2 field. 
//		 * obj  Value to assign to the returned Message.obj field. 
//		 */
//
//		mHandler.sendMessage(m);
//	}
//	
//	private void sendMsgInDfThread()
//	{
//		/**
//		 * ���̴߳�����Ϣ�����߳�
//		 */
//		mThread = new MyThread();
//		mThread.start();
//	}
//
//	private class MyThread extends Thread{
//		@Override
//		public void run()
//		{
//			Looper curLooper = Looper.myLooper();
//			Looper mainLooper = Looper.getMainLooper();
//			String msg;
//			if(curLooper == null)
//			{
//				mHandler = new MyHandler(mainLooper);
//				msg = "curLooper is null";
//			}
//			else
//			{
//				mHandler = new MyHandler(curLooper);
//				msg = "This is curLooper";
//			}
//			mHandler.removeMessages(0);
//			Message m = mHandler.obtainMessage();
//			m.what = 2;
//			m.obj = msg;
//			mHandler.sendMessage(m);
//		}
//	}
//
//	
//	private void updateUIByRunnable()
//	{
//		/**
//		 * ͨ��Runnable�����߳��и��½��������
//		 */
//		//�������̣߳������̵߳���Ϣ���з���runnable�����½���
//		tthread = new Thread()
//		{
//			public void run()
//			{
//				thandler.post(mRunn);
//			}
//		};
//		tthread.start();
//	}
//	
//	private Runnable mRunn = new Runnable()
//	{
//		@Override
//		public void run()
//		{
//			tv.setText("This is Update from other thrad, Mouse Down");
//		}
//	};
//
//	private class mtHandler extends Handler
//	{
//		@Override
//		public void handleMessage(Message msg)
//		{
//			super.handleMessage(msg);
//			switch(msg.what)
//			{
//				case 99://���յ���Ϣʱ���Խ�����и���
//					tv.setText("This update by message");
//					break;
//				default:
//					break;
//			}
//		}
//	}
//	
//	private void updateByMessage()
//	{
//		//��������
//		tthread = new Thread()
//		{
//			@Override
//			public void run()
//			{
//				//����һ�� ������������ϢID
//				Message msg = thandler.obtainMessage(99);
//				thandler.sendMessage(msg);
//			}
//		};
//		tthread.start();
//	}
//
//}

/**
 * ���̸߳������̷߳���Message
 * @author Huaruo.W
 *
 */
//public class HandleMessage extends Activity
//{
//	//Debug
//	private final static String TAG = "HandleMessage";
//	
//	private Button btn;
//	private static TextView tv;
//	private Handler thandler;
//	private Thread tthread = null;
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState)
//	{
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.main);
//		
//		btn = (Button)findViewById(R.id.main_btn);
//		tv = (TextView)findViewById(R.id.main_tv);
//		btn.setVisibility(View.VISIBLE);
//		tv.setVisibility(View.VISIBLE);
//		btn.setText("����handle");
//		//�����߳�
//		tthread = new mThread();
//		tthread.start();
//		btn.setOnClickListener(listener);
//	}
//	
//	private OnClickListener listener = new OnClickListener()
//	{
//		@Override
//		public void onClick(View v)
//		{
//			switch(v.getId())
//			{
//			case R.id.main_btn:
//				maintochild();
//				break;
//			default:
//				break;
//			}
//		}
//	};
//	
//	
//	private class mThread extends Thread
//	{
//		//���̸߳����̷߳�message
//		public void run()
//		{
//			Looper.prepare();//�������̵߳�Looper�������ڽ�����Ϣ
//			//ע�⣺�����handler�Ƕ��������̵߳�
//			//��ʼʱ����ʵ��������Ϊ���̵߳�Looper���󻹲������ء����ڿ���ʵ������
//			//����Looper.myLooper()��õľ��Ǹ��̵߳�Looper����
//			thandler = new ThreadHandler(Looper.myLooper());
//			
//			//ѭ����MessageQueue��ȡ��Ϣ.
//			Looper.loop();
//			
//		}
//	}
//	
//	//�����߳����е���Ϣ������
//	private class ThreadHandler extends Handler{
//		public ThreadHandler(Looper looper)
//		{
//			super(looper);
//		}
//		
//		public void handleMessage(Message msg)
//		{
//			//����Ը��߳��е�MessageQueue�е�Message���д���
//			//���������ٷ��ظ����߳�һ����Ϣ
//			thandler = new MyHandler(Looper.getMainLooper());
//			Message msg2 = thandler.obtainMessage();
//			msg2.what = 1;
//			msg2.obj = "���߳��յ���" + (String)msg.obj;
//			thandler.sendMessage(msg2);
//		}
//	}
//
//	private class MyHandler extends Handler
//	{
//		public MyHandler(Looper looper)
//		{
//			super(looper);
//		}
//		
//		public void  handleMessage(Message msg)
//		{
//			super.handleMessage(msg);
//			tv.setText("�������̵߳�Handler,�յ�����Ϣ�� " + (String)msg.obj);
//		}
//	}
//
//	private void maintochild()
//	{
//		//����handler��ʵ�������߳���
//		//�߳�������ʱ����Ѿ�ʵ������
//		Message msg = thandler.obtainMessage();
//		msg.what = 1;
//		msg.obj = "���̷߳��͵���Ϣ";
//		thandler.sendMessage(msg);
//	}
//}

/**
 * �����̸߳��Լ�����Message
 */

public class HandleMessage extends Activity
{
	//Debug
	private final static String TAG = "HandleMessage";
	
	private Button btn;
	private static TextView tv;
	private Handler thandler;
	private Thread tthread = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		btn = (Button)findViewById(R.id.main_btn);
		tv = (TextView)findViewById(R.id.main_tv);
		btn.setVisibility(View.VISIBLE);
		tv.setVisibility(View.VISIBLE);
		btn.setText("����handle");;
		btn.setOnClickListener(listener);
	}
	
	private OnClickListener listener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			switch(v.getId())
			{
			case R.id.main_btn:
				childtochild();
				break;
			default:
				break;
			}
		}
	};
	
	
	private class mThread extends Thread
	{
		public void run()
		{
			Looper.prepare();//�������̵߳�Looper�������ڽ�����Ϣ
			//����Looper.myLooper()��õľ��Ǹ��̵߳�Looper����
			thandler = new ThreadHandler(Looper.myLooper());
			Message msg = thandler.obtainMessage();
			msg.what = 1;
			msg.obj = "���Լ�";
			thandler.sendMessage(msg);
			
			//ѭ����MessageQueue��ȡ��Ϣ.
			Looper.loop();
			
		}
	}
	
	//�����߳����е���Ϣ������
	private class ThreadHandler extends Handler{
		public ThreadHandler(Looper looper)
		{
			super(looper);
		}
		
		public void handleMessage(Message msg)
		{
			//����Ը��߳��е�MessageQueue�е�Message���д���
			//���������ٷ��ظ����߳�һ����Ϣ
			//�����жϿ����ǲ��Ǹ��߳��Լ�������Ϣ
			if(msg.what == 1 && msg.obj.equals("���Լ�"))
			{
				thandler = new MyHandler(Looper.getMainLooper());
				Message msg2 = thandler.obtainMessage();
				msg2.what = 1;
				msg2.obj="�������߳�:���յ����Լ������Լ���Message";
				thandler.sendMessage(msg2);
			}
		}
	}

	private class MyHandler extends Handler
	{
		public MyHandler(Looper looper)
		{
			super(looper);
		}
		
		public void  handleMessage(Message msg)
		{
			super.handleMessage(msg);
			tv.setText((String)msg.obj);
		}
	}

	private void childtochild()
	{
		tthread = new mThread();
		tthread.start();
	}
}