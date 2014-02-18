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
		//��ȡ���������ȡʧ��,������ΪϵͳĬ������
		mtheme = getSharedPreferences("cons", MODE_PRIVATE).getInt("theme", android.R.style.Theme);
		//�趨����
		setTheme(mtheme);
		//���ø��෽����һ��Ҫ�ŵ��趨����֮��
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
					 * ���ñ���ͼƬ��ͼƬ��Դ��drawable��
					 * flightInfoPanel.setBackgroundDrawable(getResources().getDrawable(R.drawable.search_label_click));
					 * ת���ַ���Ϊint����ɫ����
					 * listItemView.deleteFilghtBg.setBackgroundColor(Color.parseColor("#F5F5DC"));
					 */
						case 0:
							setStyle_et1.setTextAppearance(getApplicationContext(), R.style.et0);
							setStyle_et1.setBackgroundColor(Color.parseColor("#FFFFFF"));
							break;
						case 1:
							//setTextAppearance��ʵ�ֶ�̬�޸�style�����ؼ���background���ᶯ̬���³�style�ж����background
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
						//�����Ᵽ�浽sharedPreference�У��Ա��´�����ʱ��ȡ����
						activity.getSharedPreferences("cons", Activity.MODE_PRIVATE).edit().putInt("theme", R.style.MyTheme1).commit();
					}
					else
					{
						activity.getSharedPreferences("cons", Activity.MODE_PRIVATE).edit().putInt("theme", R.style.MyTheme2).commit();
					}
					//�˳�Ӧ��
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