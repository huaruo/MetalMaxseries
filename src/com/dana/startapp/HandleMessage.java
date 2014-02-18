/**
 * Handler一般游两种用途：1)执行计划任务，2)线程间通信。
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
////		thandler = new Handler();//创建Handler, 通过Runnable在子线程中更新界面
//		thandler = new mtHandler();//用Message在子线程中来更新界面
//		
//		btn = (Button)findViewById(R.id.main_btn);
//		tv = (TextView)findViewById(R.id.main_tv);
//		btn.setVisibility(View.VISIBLE);
//		tv.setVisibility(View.VISIBLE);
//		btn.setText("启动handle");
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
//			//处理消息
//			tv.setText(msg.obj.toString());
//		}
//	}
//	
//	private void sendMsgInSameThread()
//	{
//		/**
//		 * 同线程内不同组件间的消息传递
//		 */
//		Looper looper = Looper.myLooper();//取得当前线程里的looper
//		MyHandler mHandler = new MyHandler(looper);//构造一个handler使之可与looper通信
//		//Button等组件可以由mHandler将消息传给looper后，再放入messageQueue中，同时mHandler也可以接受来自looper消息
//		mHandler.removeMessages(0);
//		String msgStr = "主线程不同组件消息：消息来自Button";
//		Message m = mHandler.obtainMessage();
//		m.what = 1;
//		m.obj = msgStr;
////		Message m = mHandler.obtainMessage(1,1,1,msgStr);//构造要传递的消息
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
//		 * 子线程传递消息给主线程
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
//		 * 通过Runnable在子线程中更新界面的例子
//		 */
//		//创建子线程，像主线程的消息队列发送runnable来更新界面
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
//				case 99://在收到消息时，对界面进行更新
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
//		//匿名对象
//		tthread = new Thread()
//		{
//			@Override
//			public void run()
//			{
//				//定义一个 整数，代表消息ID
//				Message msg = thandler.obtainMessage(99);
//				thandler.sendMessage(msg);
//			}
//		};
//		tthread.start();
//	}
//
//}

/**
 * 主线程给其他线程发送Message
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
//		btn.setText("启动handle");
//		//启动线程
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
//		//主线程给子线程发message
//		public void run()
//		{
//			Looper.prepare();//创建该线程的Looper对象，用于接收消息
//			//注意：这里的handler是定义在主线程的
//			//开始时不能实例化，因为该线程的Looper对象还不存在呢。现在可以实例化了
//			//这里Looper.myLooper()获得的就是该线程的Looper对象
//			thandler = new ThreadHandler(Looper.myLooper());
//			
//			//循环从MessageQueue中取消息.
//			Looper.loop();
//			
//		}
//	}
//	
//	//定义线程类中的消息处理类
//	private class ThreadHandler extends Handler{
//		public ThreadHandler(Looper looper)
//		{
//			super(looper);
//		}
//		
//		public void handleMessage(Message msg)
//		{
//			//这里对该线程中的MessageQueue中的Message进行处理
//			//这里我们再返回给主线程一个消息
//			thandler = new MyHandler(Looper.getMainLooper());
//			Message msg2 = thandler.obtainMessage();
//			msg2.what = 1;
//			msg2.obj = "子线程收到：" + (String)msg.obj;
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
//			tv.setText("我是主线程的Handler,收到了消息： " + (String)msg.obj);
//		}
//	}
//
//	private void maintochild()
//	{
//		//这里handler的实例化在线程中
//		//线程启动的时候就已经实例化了
//		Message msg = thandler.obtainMessage();
//		msg.what = 1;
//		msg.obj = "主线程发送的消息";
//		thandler.sendMessage(msg);
//	}
//}

/**
 * 其他线程给自己发送Message
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
		btn.setText("启动handle");;
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
			Looper.prepare();//创建该线程的Looper对象，用于接收消息
			//这里Looper.myLooper()获得的就是该线程的Looper对象
			thandler = new ThreadHandler(Looper.myLooper());
			Message msg = thandler.obtainMessage();
			msg.what = 1;
			msg.obj = "我自己";
			thandler.sendMessage(msg);
			
			//循环从MessageQueue中取消息.
			Looper.loop();
			
		}
	}
	
	//定义线程类中的消息处理类
	private class ThreadHandler extends Handler{
		public ThreadHandler(Looper looper)
		{
			super(looper);
		}
		
		public void handleMessage(Message msg)
		{
			//这里对该线程中的MessageQueue中的Message进行处理
			//这里我们再返回给主线程一个消息
			//假如判断看看是不是该线程自己发的信息
			if(msg.what == 1 && msg.obj.equals("我自己"))
			{
				thandler = new MyHandler(Looper.getMainLooper());
				Message msg2 = thandler.obtainMessage();
				msg2.what = 1;
				msg2.obj="禀告主线程:我收到了自己发给自己的Message";
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