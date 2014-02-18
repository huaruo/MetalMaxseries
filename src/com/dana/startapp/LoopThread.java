package com.dana.startapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class  LoopThread extends Thread
{
	public interface HandlerCreater
	{
		Handler createHandler();
	}
	
	private Handler mHandler = null;
	private HandlerCreater mHandlerCreater;
	public void Loopthread(HandlerCreater hc)
	{
		mHandlerCreater = hc;
	}
	
	@Override
	public void run()
	{
		Looper.prepare();
		mHandler = mHandlerCreater.createHandler();
		Looper.loop();
		mHandler =null;
	}
	
	public void sendMessage(Message msg)
	{
		if(null != mHandler)
		{
			mHandler.sendMessage(msg);
		}
	}
	
	//跳出消息循环使用：Looper.myLooper().quit();
}
