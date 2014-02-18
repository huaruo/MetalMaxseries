package com.dana.modul;

import java.util.ArrayList;
import java.util.HashMap;

import com.dana.startapp.R;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;


public class GirdViewTable extends LinearLayout
{
	//Debug
	private static final String TAG = "GirdViewTable";
	
	protected GridView gvTable, gvPage;
	protected SimpleAdapter saPageId, saTable; //������
	protected ArrayList<HashMap<String, String>> srcPageId, srcTable; //����Դ
	
	protected int TableRowCount = 10; //��ҳʱ��ÿҳ��Row����
	protected int TableColCount = 0; //ÿҳcol������
	protected SQLiteDatabase db;
	protected String rawSQL="";
	protected Cursor curTable; //��ҳʱʹ�õ�Cursor
	protected OnTableClickListener clickListener;//������ҳ�ؼ������ʱ�Ļص�����
	protected OnPageSwitchListener switchListener; //��ҳ�л�ʱ�Ļص�����
	
	public GirdViewTable(Context context)
	{
		super(context);
		setOrientation(VERTICAL);//��ֱ
		
		//-------------------------------------
		gvTable = new GridView(context);
		addView(gvTable, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));//��ʽ��
		
		srcTable = new ArrayList<HashMap<String, String>>();
		saTable = new SimpleAdapter(context, 
				srcTable, //������Դ
				R.layout.sqlite_item,
				new String[] {"ItemText"},//��̬������ImageItem��Ӧ������
				new int[]{R.id.sqlitelist_tv});
		
		//��Ӳ���ʾ
		gvTable.setAdapter(saTable);
//		gvTable.setOnItemClickListener(itemClickListener);
		gvTable.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				int y=arg2/curTable.getColumnCount()-1;//�������Ĳ���
				int x=arg2 % curTable.getColumnCount();
				if (clickListener != null//��ҳ���ݱ����
						&& y!=-1) {//���еĲ��Ǳ�����ʱ
					clickListener.onTableClickListener(x,y,curTable);
				}
			}
		});
		
		//-------------------------
		gvPage=new GridView(context);
		gvPage.setColumnWidth(40);//����ÿ����ҳ��ť�Ŀ��
		gvPage.setNumColumns(GridView.AUTO_FIT);//��ҳ��ť�����Զ�����
		addView(gvPage, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));//
		srcPageId = new ArrayList<HashMap<String, String>>();
		saPageId = new SimpleAdapter(context, srcPageId, R.layout.sqlite_item, new  String[]{"ItemText"}, new int[]{R.id.sqlitelist_tv});
		//��Ӳ�����ʾ
		gvPage.setAdapter(saPageId);
		//�����Ϣ����
//		gvPage.setOnItemClickListener(itemClickListener);
		gvPage.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				LoadTable(arg2);//������ѡ��ҳ��ȡ��Ӧ������
			    if(switchListener!=null){//��ҳ�л�ʱ
			    	switchListener.onPageSwitchListener(arg2,srcPageId.size());
			    }
			}
	    });
	}
	
//	private OnItemClickListener itemClickListener = new OnItemClickListener()
//	{
//		@Override
//		public void onItemClick(AdapterView<?> parent,//The AdapterView where the click happened
//				View v, //The view within the AdapterView that was clicked (this will be a view provided by the adapter)
//				int position, //The position of the view in the adapter. 
//				long id //The row id of the item that was clicked.
//				)
//		{
//			if(v == gvTable)
//			{
//				Log.i(TAG, "1");
//				int y = position/curTable.getColumnCount()-1;//�������Ĳ���
//				int x = position % curTable.getColumnCount();
//				if(clickListener !=null//��ҳ���ݱ����
//						&& y!=-1)
//				{
//					//���еĲ��Ǳ�����ʱ
//					clickListener.onTableClickListener(x,y,curTable);
//				}
//			}
//			else if(v == gvPage)
//			{
//				Log.i(TAG, "2");
//				LoadTable(position);//������ѡ��ҳ��ȡ��Ӧ������
//				if(switchListener!=null)
//				{
//					//��ҳ�л�ʱ
//					switchListener.onPageSwitchListener(position,srcPageId.size());
//				}
//			}
//		}
//	};
	
	/**
	 * �����������
	 */
	public  void gvRemoveAll()
	{
		if(curTable!=null)
			curTable.close();
		srcTable.clear();
		saTable.notifyDataSetChanged();
		
		srcPageId.clear();
		saPageId.notifyDataSetChanged();
	}
	
	/**
	 * ��ȡָ��ID�ķ�ҳ���ݣ����ص�ǰҳ��������
	 * SQL:Select * From TABLE_NAME Limit 9 Offset 10;
	 * ��ʾ��TABLE_NAME���ȡ���ݣ�����10�У�ȡ9��
	 * @param pageId ָ���ķ�ҳID
	 */
	protected void LoadTable(int pageID)
	{
		if(curTable != null)//�ͷ��ϴε�����
			curTable.close();
		
		String sql = rawSQL + " Limit " + String.valueOf(TableRowCount) + " Offset " + String.valueOf(pageID*TableRowCount);
		curTable = db.rawQuery(sql, null);
		
		gvTable.setNumColumns(curTable.getColumnCount());//����Ϊ���Ĺؼ���
		TableColCount = curTable.getColumnCount();
		srcTable.clear();
		//ȡ���ֶ�����
		int colCount = curTable.getColumnCount();
		for(int i=0; i<colCount; i++)
		{
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("ItemText", curTable.getColumnName(i));
			srcTable.add(map);
		}
		//�оٳ���������
		int recCount = curTable.getCount();
		for(int i = 0; i <recCount; i++)
		{
			//��λ��һ������
			curTable.moveToPosition(i);
			for(int ii=0; ii<colCount; ii++)
			{
				//��λ��һ�������е�ÿ���ֶ�
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("ItemText", curTable.getString(ii));
				srcTable.add(map);	
			}
		}
		saTable.notifyDataSetChanged();		
	}
	/**
	 * ���ñ��������ʾ������
	 * @param row ��������
	 */
	public void gvSetTableRowCount(int row)
	{
		TableRowCount = row;
	}
	
	/**
	 * ȡ�ñ����������
	 * @return ����
	 */
	public int gvGetTableRowCount()
	{
		return TableRowCount;
	}
	
	/**
	 * ȡ�õ�ǰ��ҳ��Cursor
	 * @return ��ǰ��ҳ��Cursor
	 */
	
	public Cursor gvGetCurrentTable()
	{
		return curTable;
	}
	
	/**
	 * ׼����ҳ��ʾ����
	 * @param rawSQL sql���
	 * @param db ���ݿ�
	 */
	public void gvReadyTable(String rawSQL, SQLiteDatabase db)
	{
		this.rawSQL = rawSQL;
		this.db = db;
		LoadTable(0);
	}
	
	/**
	 * ˢ�·�ҳ�������°�ť����
	 * @param sql sql���
	 * @param db ���ݿ�
	 */
	
	public void gvUpdatePageBar(String sql, SQLiteDatabase db)
	{
		Cursor rec = db.rawQuery(sql, null);
		rec.moveToLast();
		long recSize = rec.getLong(0);//ȡ������
		rec.close();
		int pageNum;
		if(recSize % TableRowCount !=0)
			pageNum = (int)(recSize/TableRowCount) +1; //ȡ�÷�ҳ��
		else
			pageNum = (int)(recSize/TableRowCount); //ȡ�÷�ҳ��
		
		srcPageId.clear();
		for(int i=0; i<pageNum; i++)
		{
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("ItemText",  "No." + String.valueOf(i));//���ͼ����Դ��ID
			srcPageId.add(map);
		}
		saPageId.notifyDataSetChanged();
	}
	//--------------------
	/**
	 * ��񱻵��ʱ�Ļص�����
	 */
	public void setTableOnClickListener(OnTableClickListener click)
	{
		this.clickListener = click;
	}
	public interface OnTableClickListener
	{
		public void onTableClickListener(int x, int y, Cursor c);
	}
	
	//---------------------------
	/**
	 * ��ҳ�������ʱ�Ļص�����
	 */
	public void setOnPageSwitchListener(OnPageSwitchListener pageSwitch)
	{
		this.switchListener = pageSwitch;
	}
	public interface OnPageSwitchListener{
		public void onPageSwitchListener(int pageId, int pageCount);
	}
	
}