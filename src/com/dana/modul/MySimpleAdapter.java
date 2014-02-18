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
	
	//����һ��LayoutInflater����������������ʵ��������
	private LayoutInflater mInflater;
	private ArrayList<HashMap<String,Object>> list; //����List��������
	private int layoutID; //��������ID
	private String flag[]; //����ListView�����������ӳ������
	private int ItemIDs[]; //����ListView�����������ID����
//	private TextView tv;
	
	
	public MySimpleAdapter(Context context, ArrayList<HashMap<String, Object>> list,
			int layoutID, String flag[], int ItemIDs[]
//			,TextView tv		
			)
	{
		//���ù�����ʵ������Ա ��������
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
		return list.size();//����ListView��ĳ���
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
	//ʵ��������������������������
	/**
	 * getView(int position, View convertView, ViewGroup parent)
	 * ��һ�����������Ƶ�����
	 * �ڶ������������Ƶ���ͼ������ָ����ListView��ÿһ��Ĳ���
	 * ������������view�ĺϼ���
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		//�� ����ͨ��mInflater����ʵ����Ϊһ��View
		convertView = mInflater.inflate(layoutID, null);
		for(int i =0; i<flag.length; i++)
		{
			//����ÿһ����������
			//ÿ���������ƥ���жϣ��õ��������ȷ����
			if(convertView.findViewById(ItemIDs[i]) instanceof ImageView)
			{
				//findViewById()����������ʵ���������е����
				//�����ΪImageView���ͣ���Ϊ��ʵ����һ��ImageView����
				ImageView iv = (ImageView) convertView.findViewById(ItemIDs[i]);
				//Ϊ�������������
				iv.setBackgroundResource((Integer)list.get(position).get(flag[i]));
			}
			else if(convertView.findViewById(ItemIDs[i])  instanceof TextView)
			{
				//�����ΪTextView���ͣ���Ϊ��ʵ����һ��TextView����
				TextView tv=(TextView) convertView.findViewById(ItemIDs[i]);
				//Ϊ�������������
				tv.setText((String) list.get(position).get(flag[i]));
			}
		}
//		//Ϊ��ť���ü���
//		((Button)convertView.findViewById(R.id.btn)).setOnClickListener(
//				new View.OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						//���ﵯ��һ���Ի��򣬺�������ϸ����
//						new AlertDialog.Builder(CustomList.CL)
//						.setTitle("�Զ���SimpleAdapter")
//						.setMessage("��ť�ɹ����������¼���")
//						.show();
//					} 
//				});
		//Ϊ��ѡ�����ü���
		((CheckBox)convertView.findViewById(R.id.cli_cb)).setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
//				new AlertDialog.Builder(CustomList.CL)
//				.setTitle("�Զ���SimpleAdapter")
//				.setMessage("CheckBox�ɹ�����״̬�ı�����¼���")
//				.show();
			}
		});
		
		//�����¼�����Ҫ��getView��
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
				//�Ӽ�����ɾ����ɾ�����EditText������
				list.remove(position);
				Log.i(TAG,""+list.size());
				notifyDataSetChanged();
//				showDeleteDialog(position, list);
//				showDeleteDialog(position, list,  tv);
			}
		});
	}
}