package com.dana.startapp;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.Toast;

import com.dana.modul.TableAdapter;
import com.dana.modul.TableAdapter.TableCell;
import com.dana.modul.TableAdapter.tTableRow;

public class AutoListView extends Activity
{
	//Debug
	private static final String TAG = "AutoListView";
	
	private ListView lv;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.autolistview);
		
		this.setTitle("ListView����Ӧʵ�ֱ��");
		lv= (ListView) findViewById(R.id.alv_lv);
		ArrayList<tTableRow> table = new ArrayList<tTableRow>();
		TableCell[] titles = new TableCell[5]; //ÿ��5����Ԫ
		int width = getWindowManager().getDefaultDisplay().getWidth()/titles.length;
		
		//�������
		for(int i = 0; i<titles.length; i++)
		{
			titles[i] = new TableCell("����" + String.valueOf(i), width+8*i, LayoutParams.FILL_PARENT,TableCell.STRING);
		}
		table.add(new tTableRow(titles));
		//ÿ�е�����
		TableCell[] cells = new TableCell[5];//ÿ��5����Ԫ
		for(int i=0; i<cells.length - 1; i++)
		{
			cells[i] = new TableCell("No." + String.valueOf(i), titles[i].width, LayoutParams.FILL_PARENT, TableCell.STRING);
		}
		
		cells[cells.length - 1] = new TableCell(R.drawable.image,titles[cells.length-1].width,LayoutParams.WRAP_CONTENT, TableCell.IMAGE);
		//�ѱ�������ӵ����
		for(int i = 0; i < 12; i++)
			table.add(new tTableRow(cells));
		
		TableAdapter tableAdapter = new TableAdapter(this, table);
		lv.setAdapter(tableAdapter);
		lv.setOnItemClickListener(itemClickListener);
	}
	
	private OnItemClickListener itemClickListener = new OnItemClickListener()
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View v, int position, long Id)
		{
			Toast.makeText(AutoListView.this, "ѡ�е�" + String.valueOf(position) + "��", Toast.LENGTH_SHORT).show();
		}
	};
}