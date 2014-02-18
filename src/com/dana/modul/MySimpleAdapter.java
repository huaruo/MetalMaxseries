package com.dana.modul;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.dana.startapp.CustomList;
import com.dana.startapp.R;

public class MySimpleAdapter extends BaseAdapter
{
	//Debug
	private static final String TAG = "MySimpleAdapter";
	
	//声明一个LayoutInflater对象，其作用是用来实例化布局
	private LayoutInflater mInflater;
	private ArrayList<HashMap<String,Object>> list; //声明List容器对象
	private int layoutID; //声明布局ID
	private String flag[]; //声明ListView项中所有组件映射索引
	private int ItemIDs[]; //声明ListView项中所有组件ID数组
//	private TextView tv;
	
	
	public MySimpleAdapter(Context context, ArrayList<HashMap<String, Object>> list,
			int layoutID, String flag[], int ItemIDs[]
//			,TextView tv		
			)
	{
		//利用构造来实例化成员 变量对象
		this.mInflater = LayoutInflater.from(context);
		this.list = list;
		this.layoutID = layoutID;
		this.flag=flag;
		this.ItemIDs = ItemIDs;
//		this.tv = tv;
	}
	@Override
	public int getCount()
	{
		return list.size();//返回ListView项的长度
	}
	@Override
	public Object getItem(int arg0)
	{
		return 0;
	}
	@Override
	public long getItemId(int arg0) {
		return 0;
	}
	//实例化布局与组件及设置组件数据
	/**
	 * getView(int position, View convertView, ViewGroup parent)
	 * 第一个参数：绘制的行数
	 * 第二个参数：绘制的视图，这里指的是ListView中每一项的布局
	 * 第三个参数：view的合集，
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		//将 布局通过mInflater对象实例化为一个View
		convertView = mInflater.inflate(layoutID, null);
		for(int i =0; i<flag.length; i++)
		{
			//遍历每一项的所有组件
			//每个组件都做匹配判断，得到组件的正确类型
			if(convertView.findViewById(ItemIDs[i]) instanceof ImageView)
			{
				//findViewById()函数作用是实例化布局中的组件
				//当组件为ImageView类型，则为其实例化一个ImageView对象
				ImageView iv = (ImageView) convertView.findViewById(ItemIDs[i]);
				//为其组件设置数据
				iv.setBackgroundResource((Integer)list.get(position).get(flag[i]));
			}
			else if(convertView.findViewById(ItemIDs[i])  instanceof TextView)
			{
				//当组件为TextView类型，则为其实例化一个TextView对象
				TextView tv=(TextView) convertView.findViewById(ItemIDs[i]);
				//为其组件设置数据
				tv.setText((String) list.get(position).get(flag[i]));
			}
		}
//		//为按钮设置监听
//		((Button)convertView.findViewById(R.id.btn)).setOnClickListener(
//				new View.OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						//这里弹出一个对话框，后文有详细讲述
//						new AlertDialog.Builder(CustomList.CL)
//						.setTitle("自定义SimpleAdapter")
//						.setMessage("按钮成功触发监听事件！")
//						.show();
//					} 
//				});
		//为复选框设置监听
		((CheckBox)convertView.findViewById(R.id.cli_cb)).setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
//				new AlertDialog.Builder(CustomList.CL)
//				.setTitle("自定义SimpleAdapter")
//				.setMessage("CheckBox成功触发状态改变监听事件！")
//				.show();
			}
		});
		
		//增加事件监听要在getView中
		addListener(convertView, position);		
		
		return convertView;
	}	
	
	public void addListener(View convertView, final int position)
	{
		ImageView del =(ImageView) convertView.findViewById(R.id.cli_itemimg);
		del.setClickable(true);
		
		del.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				//从集合中删除所删除项的EditText的内容
				list.remove(position);
				Log.i(TAG,""+list.size());
				notifyDataSetChanged();
//				showDeleteDialog(position, list);
//				showDeleteDialog(position, list,  tv);
			}
		});
	}
}