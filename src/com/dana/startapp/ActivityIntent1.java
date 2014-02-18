/*
 * Intent���Է�Ϊ��ʽIntent����ʽIntent����ʽIntent����������ȷ��Ŀ�����(ǰ����˵���������)��ͬһ��Application�ڵĶ��Activity����Ҳ����ʽIntent����ʽIntent���ǵ���û����ȷ��Ŀ�������������ϵͳҲ�����ǵ���������
 */
/**
 * @author Huaruo.Wauthor
 * issue:onActivityResult�в���setText
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
		Log.i(TAG, "++ onCreate ++"); //��ʾ��ǰ״̬��onCreate��onDestroy��Ӧ
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
		Log.i(TAG, "++ onDestroy ++"); //��ʾ��ǰ״̬��onCreate��onDestroy��Ӧ
	}
	@Override
	public void onStart()
	{
		super.onStart();
		Log.i(TAG,"++ onStart ++"); //��ʾ��ǰ״̬��onStart��onStop��Ӧ
	}
	
	@Override
	public void onStop()
	{
		super.onStop();
		Log.i(TAG, "++ onStop ++"); //��ʾ��ǰ״̬onStart��onStop��Ӧ
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
		Log.i(TAG, "++ onResume ++");//��ʾ��ǰ״̬��onPause��onResume��Ӧ
		SharedPreferences prefs = getPreferences(0); //SharedPreferences���ڴ洢����
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
		Log.i(TAG, "++ onPause ++"); //��ʾ��ǰ״̬�� onPause��onResume��Ӧ
		//�����ı�������ݣ�ʹ���ػر�Activity��ʱ����Իָ�
		SharedPreferences.Editor editor = getPreferences(0).edit();//SharedPreferences���ڴ洢����
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
					//new һ��Bundle���󣬲���Ҫ���ݵ����ݴ���
					Bundle bundle = new Bundle();
					bundle.putString("text", str);
					
					//��Bundle����assign��Intent
					intent.putExtras(bundle);
					
					intent.putExtra("Main", " ##*" + str + " *## ");
//					//����Activity2
//					startActivity(intent);
					startActivityForResult(intent, Get_Code);
					
//					ActivityIntent1.this.finish();//�ᴥ��onDestroy() //ʹ��startActivityForResultʱ�����δ˾����
					break;
				case R.id.actint1_btn2:
					//��Щ�ⲿ������Ҫ����Ȩ��
					Uri uri = Uri.parse("http://google.com");
					intent = new Intent(Intent.ACTION_VIEW, uri);
					startActivity(intent);
					//startActivityForResult(intent, Send_Code);
					break;
				case R.id.actint1_exitsys:
					System.exit(0);//�˳�����, ���˳���ǰactivity����activity��finish()��ʲô���𣬴�ȷ�ϡ�???
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
				//�������ã���fixed
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