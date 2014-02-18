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
		Log.i(TAG, "++ onCreate ++");//��ʾ��ǰ״̬��onCreeate��onDestroy��Ӧ
		setContentView(R.layout.actint2);
		
		//ȡ��Intent�е�Bundle����
		Bundle mbundle = getIntent().getExtras();
		//ȡ��Bundle�����е�����
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
		Log.i(TAG, "++ onDestroy ++");// ��ʾ��ǰ״̬�� onCreate��onDestroy��Ӧ
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		Log.i(TAG, "++ onStart ++"); //��ʾ��ǰ״̬�� onStart��onStop��Ӧ
	}
	
	@Override
	public void onStop()
	{
		super.onStop();
		Log.i(TAG, "++ onStop ++"); //��ʾ��ǰ״̬�� onStart��onStop��Ӧ
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
		Log.i(TAG, "++ onResume ++"); //��ʾ��ǰ״̬�� onResume��onPause��Ӧ
	}
	
	@Override
	public void onPause()
	{
		super.onResume();
		Log.i(TAG, "++ onPause ++"); //��ʾ��ǰ״̬��onResume��onPasuse��Ӧ
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
//					//����Activity1
//					startActivity(intent);
					String test = "1233";
					intent.putExtra("test", test);
					setResult(RESULT_OK, intent);
					ActivityIntent2.this.finish(); //����onDestroy();
					break;
				default:
					break;
			}
		}
	};
}