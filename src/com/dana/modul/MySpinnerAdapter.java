package com.dana.modul;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MySpinnerAdapter extends ArrayAdapter<String>
{
	Context context;
	String[] items = new String[]{};
	
	public MySpinnerAdapter(final Context context, final int textViewResourceId, final String[] objects)
	{
		super(context, textViewResourceId, objects);
		this.items = objects;
		this.context = context;
	}
	
	/**
	 * �������б���ʽ
	 * 
	 * �ı��ӵ�ѡ��ť��ʽ ��android.R.layout.simple_spinner_dropdown_item
	 * ���ı���ʽ��android.R.layout.simple_spinner_item
	 * ���кܶ�......
	 * */
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent)
	{
		if(convertView == null)
		{
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
		}
		TextView tv = (TextView) convertView.findViewById(android.R.id.text1); 
		tv.setText(items[position]);
		tv.setTextColor(Color.CYAN);
		tv.setTextSize(26);
		
		return convertView;
	}
	
	/**
	 * Spinner����ʽ
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if(convertView == null)
		{
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
		}
		TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
		tv.setText(items[position]);
		tv.setTextColor(Color.BLUE);
		tv.setBackgroundColor(Color.GREEN);
		tv.setTextSize(26);
		
		return convertView;
	}
}