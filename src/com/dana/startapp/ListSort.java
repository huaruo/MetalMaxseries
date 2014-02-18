package com.dana.startapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.dana.modul.ListSortAdapter;
import com.dana.modul.PinYinContent;
import com.dana.modul.PinYinContent.PinyinComparator;
import com.dana.modul.SideBar;

public class ListSort extends Activity {

	private ListView mListView;
	private SideBar indexBar;
	private WindowManager mWindowManager;
	private TextView mDialogText;
	private View head;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		 //启动activity时不自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_view);
		mListView = (ListView) this.findViewById(R.id.list);
		indexBar = (SideBar) findViewById(R.id.sideBar);
		mDialogText = (TextView) LayoutInflater.from(this).inflate(R.layout.listsort_position, null);
		head = LayoutInflater.from(this).inflate(R.layout.listsort_head, null);
		mListView.addHeaderView(head);
		mDialogText.setVisibility(View.INVISIBLE);
		mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_APPLICATION,
				WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
						| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSLUCENT);
		mWindowManager.addView(mDialogText, lp);
		indexBar.setVisibility(View.VISIBLE);
		indexBar.setTextView(mDialogText);
		//初始化数据
		List<PinYinContent> list = new ArrayList<PinYinContent>();
		for (int i = 0; i < 10; i++) {
			PinYinContent m;
			if (i < 3)
				m = new PinYinContent("A", "选项" + i);
			else if (i < 6)
				m = new PinYinContent("F", "选项" + i);
			else
				m = new PinYinContent("D", "选项" + i);
			list.add(m);
		}
		//根据a-z进行排序
		PinyinComparator comp = new PinyinComparator();
		Collections.sort(list, comp);
		// 实例化自定义内容适配类		
		ListSortAdapter adapter = new ListSortAdapter(this, list);
		// 为listView设置适配
		mListView.setAdapter(adapter);
		//设置SideBar的ListView内容实现点击a-z中任意一个进行定位
	    indexBar.setListView(mListView);		
	}

}
