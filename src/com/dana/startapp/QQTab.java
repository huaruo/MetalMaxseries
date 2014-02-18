package com.dana.startapp;

import android.app.ActivityGroup;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

public class QQTab extends ActivityGroup
{
	//Debug
	private static final String TAG = "QQTab";
	
	private RelativeLayout layout, layout1, layout2, layout3, bodylayout;
	
	private ImageView tab1, tab2, tab3, first;
	
	private int current = 1; //默认选中第一个，可以动态的改变此参数值
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qqtab);
		initUI();
	}
	
	private void initUI()
	{
		layout = (RelativeLayout)findViewById(R.id.qqtab_root);
		layout1= (RelativeLayout)findViewById(R.id.qqtab_rl1);
		layout2= (RelativeLayout)findViewById(R.id.qqtab_rl2);
		layout3=(RelativeLayout)findViewById(R.id.qqtab_rl3);
		bodylayout=(RelativeLayout) findViewById(R.id.qqtab_bodylayout);
		
		tab1 = (ImageView) findViewById(R.id.qqtab_iv1);
		tab2 = (ImageView) findViewById(R.id.qqtab_iv2);
		tab3 = (ImageView) findViewById(R.id.qqtab_iv3);
		
		tab1.setOnClickListener(listener);
		tab2.setOnClickListener(listener);
		tab3.setOnClickListener(listener);
		
		RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		rlp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		
		first = new ImageView(this);
		first.setTag("first");
		first.setImageResource(R.drawable.topbar_select);
		
		//默认选中项
		switch(current)
		{
			case 1:
				layout1.addView(first, rlp);
				current = R.id.qqtab_iv1;
				break;
			case 2:
				layout2.addView(first, rlp);
				current = R.id.qqtab_iv2;
				break;
			case 3:
				layout3.addView(first,rlp);
				current = R.id.qqtab_iv3;
				break;
			default:
				break;
		}
		
		View view = getLocalActivityManager().startActivity("index", new Intent(QQTab.this, QQTab1.class)).getDecorView();
		bodylayout.addView(view);
	}
	
	private boolean isAdd = false; //是否添加过top_select
	private int select_width; // top_select_width
	private int select_height; // top_select_height
	private int firstLeft; // 第一次添加后的左边距
	private int startLeft; // 起始左边距
	
	//添加一个view， 移除一个view
	private void replace()
	{
		switch(current)
		{
			case R.id.qqtab_iv1:
				changeTop(layout1);
				break;
			case R.id.qqtab_iv2:
				changeTop(layout2);
				break;
			case R.id.qqtab_iv3:
				changeTop(layout3);
				break;
			default:
				break;
		}
	}
	
	private void changeTop(RelativeLayout rl)
	{
		ImageView old = (ImageView) rl.findViewWithTag("first");
		select_width = old.getWidth();
		select_height= old.getHeight();
		
		RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(select_width, select_height);
		rlp.leftMargin = old.getLeft() + ((RelativeLayout) old.getParent()).getLeft();
		rlp.topMargin = old.getTop() + ((RelativeLayout) old.getParent()).getTop();
		
		//获取起始位置
		firstLeft = old.getLeft() + ((RelativeLayout) old.getParent()).getLeft();
		
		ImageView iv = new ImageView(this);
		iv.setTag("move");
		iv.setImageResource(R.drawable.topbar_select);
		
		layout.addView(iv, rlp);
		rl.removeView(old);
	}
	
	private OnClickListener listener = new OnClickListener()
	{
		public void onClick(View v)
		{
			if(!isAdd)
			{
				replace();//初次使用移除old 添加新的top_select为RelativeLayout所使用
				isAdd = true;
			}
			
			ImageView top_select = (ImageView) layout.findViewWithTag("move");
			int tabLeft;
			int endLeft = 0;
			
			boolean run = false;
			
			switch(v.getId())
			{
				case R.id.qqtab_iv1:
					if(current !=R.id.qqtab_iv1)
					{
						//中心位置
						tabLeft = ((RelativeLayout) tab1.getParent()).getLeft() + tab1.getLeft() + tab1.getWidth() / 2;
						//最终位置
						endLeft = tabLeft - select_width /2;
						current = R.id.qqtab_iv1;
						run = true;
						bodylayout.removeAllViews();
						View view = getLocalActivityManager().startActivity("index", new Intent(QQTab.this, QQTab1.class)).getDecorView();
						bodylayout.addView(view);
						Toast.makeText(QQTab.this, "tab1", Toast.LENGTH_SHORT).show();
					}
					break;
				case R.id.qqtab_iv2:
					if(current !=R.id.qqtab_iv2)
					{
						tabLeft = ((RelativeLayout) tab2.getParent()).getLeft() +tab2.getLeft() + tab2.getWidth() / 2;
						endLeft = tabLeft - select_width / 2;
						current = R.id.qqtab_iv2;
						run = true;
						bodylayout.removeAllViews();
						View view = getLocalActivityManager().startActivity("index", new Intent (QQTab.this, QQTab1.class)).getDecorView();
						bodylayout.addView(view);
						Toast.makeText(QQTab.this, "tab2", Toast.LENGTH_SHORT).show();
					}
					break;
				case R.id.qqtab_iv3:
					if(current != R.id.qqtab_iv3)
					{
						tabLeft = ((RelativeLayout)tab3.getParent()).getLeft() + tab3.getLeft() + tab3.getWidth() / 2;
						endLeft = tabLeft - select_width /2;
						current = R.id.qqtab_iv3;
						run = true;
						bodylayout.removeAllViews();
						View view = getLocalActivityManager().startActivity("index", new Intent(QQTab.this, QQTab1.class)).getDecorView();
						bodylayout.addView(view);
						Toast.makeText(QQTab.this, "tab3", Toast.LENGTH_SHORT).show();
					}
					break;
				default:
					break;
			}
			
			if(run)
			{
				TranslateAnimation animation = new TranslateAnimation(startLeft, endLeft - firstLeft, 0, 0);
				startLeft = endLeft -  firstLeft; //重新设定起始位置
				animation.setDuration(400);
				animation.setFillAfter(true);
				top_select.bringToFront();
				top_select.startAnimation(animation);
			}
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		super.onCreateOptionsMenu(menu);
		
		// This is our one standard application action -- inserting a
        // new note into the list.
		menu.add(0,0,0, R.string.app_name).setShortcut('3','a')
		.setIcon(android.R.drawable.ic_menu_add);
		
		// Generate any additional actions that can be performed on the
        // overall list.  In a normal install, there are no additional
        // actions found here, but this allows other applications to extend
        // our menu with their own actions.
		Intent intent = new Intent(null, getIntent().getData());
		intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
		menu.addIntentOptions(Menu.CATEGORY_ALTERNATIVE, 0, 0,
				new ComponentName(this, QQTab.class), null, intent, 0, null);
		return true;
	}
	
}