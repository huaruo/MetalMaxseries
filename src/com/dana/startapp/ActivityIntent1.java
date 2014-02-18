/*
 * Intent可以分为显式Intent和隐式Intent：显式Intent用于启动明确的目标组件(前面所说的三大组件)，同一个Application内的多个Activity调用也是显式Intent；隐式Intent就是调用没有明确的目标组件，可以是系统也可以是第三方程序。
 */
/**
 * @author Huaruo.Wauthor
 * issue:onActivityResult中不能setText
 */
package com.dana.startapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ActivityIntent1 extends Activity{
	//Debug
	private static final String TAG = "ActivityIntent1";
	
	private Button btnToInternalActivity;
	private Button btnToExternalActivity;
	private Button btnExitSYS;
	private EditText tbBundle;
	
	private Intent intent = null;
	private static final int Get_Code = 0;
	private static final int Send_Code = 1;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Log.i(TAG, "++ onCreate ++"); //显示当前状态，onCreate与onDestroy对应
		setContentView(R.layout.actint1);
		
		btnToInternalActivity = (Button) findViewById(R.id.actint1_btn1);
		btnToExternalActivity = (Button) findViewById(R.id.actint1_btn2);
		btnExitSYS = (Button) findViewById(R.id.actint1_exitsys);
		btnToInternalActivity.setOnClickListener(listener);
		btnToExternalActivity.setOnClickListener(listener);
		btnExitSYS.setOnClickListener(listener);
		
		tbBundle = (EditText) findViewById(R.id.actint1_et1);		
	}
	
	public void onDestroy()
	{
		super.onDestroy();
		Log.i(TAG, "++ onDestroy ++"); //显示当前状态，onCreate与onDestroy对应
	}
	@Override
	public void onStart()
	{
		super.onStart();
		Log.i(TAG,"++ onStart ++"); //显示当前状态，onStart与onStop对应
	}
	
	@Override
	public void onStop()
	{
		super.onStop();
		Log.i(TAG, "++ onStop ++"); //显示当前状态onStart与onStop对应
	}
	
	@Override
	public void onRestart()
	{
		super.onRestart();
		Log.i(TAG, "++ onRestart ++");
	}
	
	@Override
	public  void onResume()
	{
		super.onResume(); 
		Log.i(TAG, "++ onResume ++");//显示当前状态，onPause与onResume对应
		SharedPreferences prefs = getPreferences(0); //SharedPreferences用于存储数据
		String restoredText = prefs.getString(TAG, null);
		if(restoredText !=null)
		{
			tbBundle.setText(restoredText);
		}
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		Log.i(TAG, "++ onPause ++"); //显示当前状态， onPause与onResume对应
		//保存文本框的内容，使得重回本Activity的时候可以恢复
		SharedPreferences.Editor editor = getPreferences(0).edit();//SharedPreferences用于存储数据
		editor.putString(TAG, tbBundle.getText().toString());
		editor.commit();
	}
	
	private OnClickListener listener = new OnClickListener(){
		@Override
		public void onClick(View v)
		{
			switch(v.getId())
			{
				case R.id.actint1_btn1:
					intent = new Intent();
					intent.setClass(ActivityIntent1.this, ActivityIntent2.class);
					
					String str = tbBundle.getText().toString();
					//new 一个Bundle对象，并将要传递的数据传入
					Bundle bundle = new Bundle();
					bundle.putString("text", str);
					
					//将Bundle对象assign给Intent
					intent.putExtras(bundle);
					
					intent.putExtra("Main", " ##*" + str + " *## ");
//					//调用Activity2
//					startActivity(intent);
					startActivityForResult(intent, Get_Code);
					
//					ActivityIntent1.this.finish();//会触发onDestroy() //使用startActivityForResult时需屏蔽此句代码
					break;
				case R.id.actint1_btn2:
					//有些外部调用需要开启权限
					Uri uri = Uri.parse("http://google.com");
					intent = new Intent(Intent.ACTION_VIEW, uri);
					startActivity(intent);
					//startActivityForResult(intent, Send_Code);
					break;
				case R.id.actint1_exitsys:
					System.exit(0);//退出程序, 会退出当前activity。和activity。finish()有什么区别，待确认。???
				default:
					break;
			}
		}
	};
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
		switch(requestCode)
		{
			case Get_Code:
				Bundle extra = intent.getExtras();
				String str1 = extra.getString("test");
				//不起作用，待fixed
//				tbBundle.setText("");
//				tbBundle.setText(str1);
				Log.i(TAG, "TTT" + str1);
				break;
			case Send_Code:
				break;
			default:
				break;
		}
	}
	
}