package com.dana.startapp;


import android.app.TabActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MTabTest extends TabActivity
{
	//Debug
	private static final String TAG = "mTabTest";
	
	private Button btnTab1, btnTab2;
	private EditText etTab1, etTab2;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabtest);
		TabHost tabs = this.getTabHost();  //实例(分页)菜单
		//利用LayoutInflater将布局与分页菜单一起显示(与setContentView冲突，使用其中一句即可)
//		LayoutInflater.from(this).inflate(R.layout.tabtest, tabs.getTabContentView());
		//设置Tab1
		TabSpec tab1 = tabs.newTabSpec("tab1");//实例化一个分页
		tab1.setIndicator("tab1"); //设置此分页显示的标题
		tab1.setContent(R.id.tabt_ll1);//设置此分页的资源id
		tabs.addTab(tab1); //添加tab1
		
		btnTab1 = (Button)findViewById(R.id.tabt_btn1);
		etTab1 = (EditText)findViewById(R.id.tabt_et1);
		btnTab1.setOnClickListener(listener);
		
		//设置Tab2
		TabSpec tab2 = tabs.newTabSpec("tab2");
		//设置此分页显示的标题和图标
		tab2.setIndicator("tab2",getResources().getDrawable(R.drawable.image));
		tab2.setContent(R.id.tabt_ll2);
		tabs.addTab(tab2);
		
		btnTab2 = (Button) this.findViewById(R.id.tabt_btn2);
		etTab2 = (EditText) this.findViewById(R.id.tabt_et2);
		btnTab2.setOnClickListener(listener);
		
		tabs.setCurrentTab(0);//设置当前tab页
	}
	
	private OnClickListener listener = new OnClickListener(){
		@Override
		public void onClick(View v){
			if(v==btnTab1)
			{
				etTab1.setText("tab1");
			}
			else if(v==btnTab2)
			{
				etTab2.setText("tab2");
			}
		}
	};
}


