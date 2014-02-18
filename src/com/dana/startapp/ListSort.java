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
		 //����activityʱ���Զ����������
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
		//��ʼ������
		List<PinYinContent> list = new ArrayList<PinYinContent>();
		for (int i = 0; i < 10; i++) {
			PinYinContent m;
			if (i < 3)
				m = new PinYinContent("A", "ѡ��" + i);
			else if (i < 6)
				m = new PinYinContent("F", "ѡ��" + i);
			else
				m = new PinYinContent("D", "ѡ��" + i);
			list.add(m);
		}
		//����a-z��������
		PinyinComparator comp = new PinyinComparator();
		Collections.sort(list, comp);
		// ʵ�����Զ�������������		
		ListSortAdapter adapter = new ListSortAdapter(this, list);
		// ΪlistView��������
		mListView.setAdapter(adapter);
		//����SideBar��ListView����ʵ�ֵ��a-z������һ�����ж�λ
	    indexBar.setListView(mListView);		
	}

}
