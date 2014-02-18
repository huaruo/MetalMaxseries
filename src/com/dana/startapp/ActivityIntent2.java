package com.dana.startapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ActivityIntent2 extends Activity
{
	//Debug
	private static final String TAG = "ActivityIntent2";
	
	private TextView tv;
	private Button btnBackActivity1;
	private Intent intent = null;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Log.i(TAG, "++ onCreate ++");//显示当前状态，onCreeate与onDestroy对应
		setContentView(R.layout.actint2);
		
		//取得Intent中的Bundle对象
		Bundle mbundle = getIntent().getExtras();
		//取得Bundle对象中的数据
		String str ="";
		str = mbundle.getString("text");
		intent = this.getIntent();
		str  += intent.getStringExtra("Main");
		tv = (TextView) findViewById(R.id.actint2_tv);
		tv.setText(str);
		
		btnBackActivity1 = (Button) findViewById(R.id.actint2_btn1);
		btnBackActivity1.setOnClickListener(listener);
	}
	
	public void onDestroy()
	{
		super.onDestroy();
		Log.i(TAG, "++ onDestroy ++");// 显示当前状态， onCreate与onDestroy对应
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		Log.i(TAG, "++ onStart ++"); //显示当前状态， onStart与onStop对应
	}
	
	@Override
	public void onStop()
	{
		super.onStop();
		Log.i(TAG, "++ onStop ++"); //显示当前状态， onStart与onStop对应
	}
	
	@Override
	public void onRestart()
	{
		super.onRestart();
		Log.i(TAG, "++ onRestart ++"); 
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		Log.i(TAG, "++ onResume ++"); //显示当前状态， onResume与onPause对应
	}
	
	@Override
	public void onPause()
	{
		super.onResume();
		Log.i(TAG, "++ onPause ++"); //显示当前状态，onResume与onPasuse对应
	}
	
	private OnClickListener listener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			switch(v.getId())
			{
				case R.id.actint2_btn1:
					intent = new Intent();
					intent.setClass(ActivityIntent2.this, ActivityIntent1.class);
//					//调用Activity1
//					startActivity(intent);
					String test = "1233";
					intent.putExtra("test", test);
					setResult(RESULT_OK, intent);
					ActivityIntent2.this.finish(); //触发onDestroy();
					break;
				default:
					break;
			}
		}
	};
}