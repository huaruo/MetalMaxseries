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
	
	private ListView list;// �����б���ͼ����
	private ArrayList<HashMap<String, Object>> listItem;// �����б�����
//	private SimpleAdapter listItemAdapter;// ��������������
	
	private int[] itemMenu = {R.drawable.personal_info, R.drawable.password, R.drawable.settings, R.drawable.settings, R.drawable.go_back};
	private String[] itemTitle = {"������Ϣ", "�޸�����", "��������", "��ӡ����","����"};
	
	private LinearLayout layout;
	
	public static CustomList CL;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_view);
		CL=this;
		layout = (LinearLayout) findViewById(R.id.ll_list_view);
		//��Layout�����ListView
		list = (ListView)  findViewById(R.id.list);
/*		
		LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.FILL_PARENT);//���岼�ֹ������Ĳ���
		layout.setLayoutParams(param);
*/		
		layout.setBackgroundColor(Color.WHITE);
		LinearLayout.LayoutParams lp_text = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		list.setLayoutParams(lp_text);
		list.setDivider(getResources().getDrawable(R.drawable.divider_color));
		list.setDividerHeight(3);
		list.setCacheColorHint(Color.BLACK);
		//���ɶ�̬���ݣ���������
		listItem = new ArrayList<HashMap<String, Object>>();
		
		Log.i(TAG, "��ʾͼ��");
		for (int i=0; i<itemTitle.length; i++)
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemImage", itemMenu[i]);
			map.put("BigItemTitle", itemTitle[i]);
			map.put("SmallItemTitle", itemTitle[i]);
			map.put("LastImage",  R.drawable.lastimage);
			//��map��ʾ����Ļ
			listItem.add(map);
		}
		
		// ʹ��Android �ṩ��SimpleAdapter������,�޷�ʵ�����������
		//������������Item�Ͷ�̬�����Ӧ��Ԫ��
//		listItemAdapter = new SimpleAdapter(this, listItem, //����Դ
//				R.layout.customlist_item, //ListItem��XMLʵ��
//				//��̬������ImageItem��Ӧ������
//				new String[] {"ItemImage", "BigItemTitle", "SmallItemTitle", "LastImage"},
//				//ImageItem��XML�ļ������һ��ImageView, ����TextView ID
//				new int[] {R.id.cli_itemimg, R.id.cli_bigitemtitle, R.id.cli_smallitemtitle, R.id.cli_lastimg}
//				);
		// --ʹ���Զ������������ɼ�����ListView��ÿһ����¼�����
		MySimpleAdapter listItemAdapter = new MySimpleAdapter(this, listItem, //����Դ
				R.layout.customlist_item, //ListItem��XMLʵ��
				//��̬������ImageItem��Ӧ������
				new String[] {"ItemImage", "BigItemTitle", "SmallItemTitle", "LastImage"},
				//ImageItem��XML�ļ������һ��ImageView, ����TextView ID
				new int[] {R.id.cli_itemimg, R.id.cli_bigitemtitle, R.id.cli_smallitemtitle, R.id.cli_lastimg}
				);
		//��Ӳ�����ʾ
		list.setAdapter(listItemAdapter);
		//��ӵ��
		list.setOnItemClickListener(itemClickListener);
		//��ӳ������
		list.setOnCreateContextMenuListener(createContextMenuListener);
		
		
	}
	
	private OnItemClickListener itemClickListener = new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
		{
			setTitle("�����" + arg2 +"����Ŀ");
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
			menu.setHeaderTitle("�����˵���ContextMenu");
			menu.add(0,0,0, "���������˵�0");
			menu.add(0,1,0, "���������˵�1");
		}
	};
	
	//�����˵���Ӧ����
	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		setTitle("����˳����˵�����ĵ�" + item.getItemId() + "����Ŀ");
		return super.onContextItemSelected(item);
	}
}