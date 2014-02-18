package com.dana.startapp;

import android.app.ActivityGroup;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.dana.modul.ImageAdapter;

public class GridTab extends ActivityGroup
{
	//Debug
	private static final String TAG = "GridTab";
	
	private GridView gvTopBar;
	private ImageAdapter topImgAdapter;
	private LinearLayout container; //װ��sub Activity������
	
	//������ťͼƬ
	int[] topbar_img_array = {R.drawable.image,R.drawable.image,R.drawable.image,R.drawable.image};
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridtab);
		
		gvTopBar = (GridView) findViewById(R.id.gridtab_gvTopBar);
		gvTopBar.setNumColumns(topbar_img_array.length); //����ÿ������
		gvTopBar.setSelector(new ColorDrawable(Color.TRANSPARENT));//ѡ�е� ʱ��Ϊ͸��ɫ
		gvTopBar.setGravity(Gravity.CENTER);//λ�þ���
		gvTopBar.setVerticalSpacing(0);//��ֱ���
		
		int width = getWindowManager().getDefaultDisplay().getWidth()/ topbar_img_array.length;
		topImgAdapter = new ImageAdapter(this, topbar_img_array, width, 48, R.drawable.grid_selector_background);
		
		gvTopBar.setAdapter(topImgAdapter);//���ò˵�Adapter
		gvTopBar.setOnItemClickListener(itemClickListener);
		container=(LinearLayout) findViewById(R.id.gridtab_Container);
		SwitchActivity(0);//Ĭ�ϴ򿪵�0ҳ
	}
	
	private OnItemClickListener itemClickListener = new OnItemClickListener()
	{
		public void onItemClick(AdapterView<?> parent, View v, int position, long id)
		{
			SwitchActivity(position);
		}
	};
	
	
	/**
	 * ����ID��ָ����Activity
	 * @param id GridViewѡ��������
	 */
	
	private void SwitchActivity(int id)
	{
		topImgAdapter.SetFocus(id);//ѡ�����ø���
		container.removeAllViews(); //������������������е�View
		Intent intent = null;
		if(id==0 || id==2)
		{
			intent = new Intent(GridTab.this, AppInfoDisplay.class);
		}
		else if (id==1 || id==3)
		{
			intent = new Intent(GridTab.this, ExListView.class);
		}
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		//Activity תΪView
		Window subActivity = getLocalActivityManager().startActivity("subActivity", intent);
		//�������View
		container.addView(subActivity.getDecorView(), LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
	}
}