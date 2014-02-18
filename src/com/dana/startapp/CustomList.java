package com.dana.startapp;

import java.util.ArrayList;
import java.util.HashMap;

import com.dana.modul.MySimpleAdapter;

import android.R.color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.app.Activity;
import android.graphics.Color;

public class CustomList extends Activity{
	//Debug
	private static final String TAG = "CustomList";
	
	private ListView list;// 声明列表视图对象
	private ArrayList<HashMap<String, Object>> listItem;// 声明列表容器
//	private SimpleAdapter listItemAdapter;// 声明适配器对象
	
	private int[] itemMenu = {R.drawable.personal_info, R.drawable.password, R.drawable.settings, R.drawable.settings, R.drawable.go_back};
	private String[] itemTitle = {"个人信息", "修改密码", "网络设置", "打印设置","返回"};
	
	private LinearLayout layout;
	
	public static CustomList CL;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_view);
		CL=this;
		layout = (LinearLayout) findViewById(R.id.ll_list_view);
		//绑定Layout里面的ListView
		list = (ListView)  findViewById(R.id.list);
/*		
		LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.FILL_PARENT);//定义布局管理器的参数
		layout.setLayoutParams(param);
*/		
		layout.setBackgroundColor(Color.WHITE);
		LinearLayout.LayoutParams lp_text = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		list.setLayoutParams(lp_text);
		list.setDivider(getResources().getDrawable(R.drawable.divider_color));
		list.setDividerHeight(3);
		list.setCacheColorHint(Color.BLACK);
		//生成动态数据，加入数据
		listItem = new ArrayList<HashMap<String, Object>>();
		
		Log.i(TAG, "显示图样");
		for (int i=0; i<itemTitle.length; i++)
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemImage", itemMenu[i]);
			map.put("BigItemTitle", itemTitle[i]);
			map.put("SmallItemTitle", itemTitle[i]);
			map.put("LastImage",  R.drawable.lastimage);
			//将map显示到屏幕
			listItem.add(map);
		}
		
		// 使用Android 提供的SimpleAdapter适配器,无法实现组件监听；
		//生成适配器的Item和动态数组对应的元素
//		listItemAdapter = new SimpleAdapter(this, listItem, //数据源
//				R.layout.customlist_item, //ListItem的XML实现
//				//动态数据与ImageItem对应的子项
//				new String[] {"ItemImage", "BigItemTitle", "SmallItemTitle", "LastImage"},
//				//ImageItem的XML文件里面的一个ImageView, 两个TextView ID
//				new int[] {R.id.cli_itemimg, R.id.cli_bigitemtitle, R.id.cli_smallitemtitle, R.id.cli_lastimg}
//				);
		// --使用自定义适配器，可监听其ListView中每一项的事件监听
		MySimpleAdapter listItemAdapter = new MySimpleAdapter(this, listItem, //数据源
				R.layout.customlist_item, //ListItem的XML实现
				//动态数据与ImageItem对应的子项
				new String[] {"ItemImage", "BigItemTitle", "SmallItemTitle", "LastImage"},
				//ImageItem的XML文件里面的一个ImageView, 两个TextView ID
				new int[] {R.id.cli_itemimg, R.id.cli_bigitemtitle, R.id.cli_smallitemtitle, R.id.cli_lastimg}
				);
		//添加并且显示
		list.setAdapter(listItemAdapter);
		//添加点击
		list.setOnItemClickListener(itemClickListener);
		//添加长按点击
		list.setOnCreateContextMenuListener(createContextMenuListener);
		
		
	}
	
	private OnItemClickListener itemClickListener = new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
		{
			setTitle("点击第" + arg2 +"个项目");
			if(arg2 == 4)
			{
				CustomList.this.finish();
			}
		}
	};
	
	private OnCreateContextMenuListener createContextMenuListener = new OnCreateContextMenuListener() {
		@Override
		public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) 
		{
			menu.setHeaderTitle("长按菜单—ContextMenu");
			menu.add(0,0,0, "弹出长按菜单0");
			menu.add(0,1,0, "弹出长按菜单1");
		}
	};
	
	//长按菜单响应函数
	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		setTitle("点击了长按菜单里面的第" + item.getItemId() + "个项目");
		return super.onContextItemSelected(item);
	}
}