package com.dana.startapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SetStyle extends Activity{
	//Debug
	private static final String TAG = "SetStyle";
	
	private SetStyle activity = SetStyle.this;
	
	private EditText setStyle_et1;
	private Button setStyle_btn1, setStyle_btn2;
	private int mtheme = -1;
	@Override
	public void onCreate(Bundle savedInstanceState){
		//读取主题如果读取失败,则设置为系统默认主题
		mtheme = getSharedPreferences("cons", MODE_PRIVATE).getInt("theme", android.R.style.Theme);
		//设定主题
		setTheme(mtheme);
		//调用父类方法，一定要放到设定主题之后
		super.onCreate(savedInstanceState);
		setContentView(R.layout.style_theme);
		
		setStyle_et1 = (EditText) findViewById(R.id.style_et1);
		setStyle_btn1 = (Button) findViewById(R.id.style_btn1);
		setStyle_btn2 = (Button) findViewById(R.id.style_btn2);
		setStyle_btn1.setOnClickListener(listener);
		setStyle_btn2.setOnClickListener(listener);
	}
	
	private OnClickListener listener = new OnClickListener(){
		private int m = 0;
		@Override
		public void onClick(View v)
		{
			switch(v.getId())
			{
				case R.id.style_btn1:
					m++;
					switch (m%3)
					{
					/*
					 * 设置背景图片，图片来源于drawable；
					 * flightInfoPanel.setBackgroundDrawable(getResources().getDrawable(R.drawable.search_label_click));
					 * 转换字符串为int（颜色）；
					 * listItemView.deleteFilghtBg.setBackgroundColor(Color.parseColor("#F5F5DC"));
					 */
						case 0:
							setStyle_et1.setTextAppearance(getApplicationContext(), R.style.et0);
							setStyle_et1.setBackgroundColor(Color.parseColor("#FFFFFF"));
							break;
						case 1:
							//setTextAppearance可实现动态修改style，但控件的background不会动态更新成style中定义的background
							setStyle_et1.setTextAppearance(getApplicationContext(), R.style.et1);
							setStyle_et1.setBackgroundColor(Color.parseColor("#4EA41A"));
//							setStyle_et1.setTextAppearance(SetStyle.this, R.style.et1);
							break;
						case 2:
							setStyle_et1.setTextAppearance(getApplicationContext(),R.style.et2);
							setStyle_et1.setBackgroundColor(Color.parseColor("#A6C60F"));
							break;
						default:
							break;
					}
					break;
				case R.id.style_btn2:
					if(R.style.MyTheme1 != mtheme)
					{
						//将主题保存到sharedPreference中，以便下次启动时读取主题
						activity.getSharedPreferences("cons", Activity.MODE_PRIVATE).edit().putInt("theme", R.style.MyTheme1).commit();
					}
					else
					{
						activity.getSharedPreferences("cons", Activity.MODE_PRIVATE).edit().putInt("theme", R.style.MyTheme2).commit();
					}
					//退出应用
					activity.finish();
					Intent intent = new Intent(SetStyle.this, SetStyle.class);
					startActivity(intent);
	//				android.os.Process.killProcess(android.os.Process.myPid());
					break;
				default:
					break;
			}
		}
	};
}