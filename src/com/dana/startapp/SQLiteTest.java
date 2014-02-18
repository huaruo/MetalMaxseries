package com.dana.startapp;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class SQLiteTest extends Activity
{
	//Debug
	private static final String TAG = "SQLiteTest";
	
	private Button btnCreateDB, btnInsert, btnCloseDB;
	private EditText edtSQL; //��ʾ��ҳ����
	private GridView gridview;
	private SQLiteDatabase db;
	
	private int id;//��Ӽ�¼ʱ��id�ۼӱ�ǡ�����ȫ��
	private static final int PageSize = 10; //��ҳʱ��ÿҳ����������
	private static final String TABLE_NAME = "stu";
	private static final String ID = "id";
	private static final String NAME = "name";
	
	private SimpleAdapter saPageId; //��ҳ��������
	private ArrayList<HashMap<String,String>> lstPageId; //��ҳ��������Դ����PageSize�������������
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sqlitetest);
		
		btnCreateDB = (Button) findViewById(R.id.sql_createdb);
		btnInsert = (Button) findViewById(R.id.sql_insertdata);
		btnCloseDB = (Button) findViewById(R.id.sql_closedb);
		edtSQL =(EditText) findViewById(R.id.sql_et1);
		gridview = (GridView) findViewById(R.id.sql_gv);
		
		edtSQL.setVisibility(View.VISIBLE);
		gridview.setVisibility(View.VISIBLE);
		
		edtSQL.setEnabled(false);
		edtSQL.setFocusable(false);
		edtSQL.setBackgroundColor(Color.BLUE);
		
		
		btnCreateDB.setOnClickListener(listener);
		btnInsert.setOnClickListener(listener);
		btnCloseDB.setOnClickListener(listener);
		
		//���ɶ�̬���飬����ת������
		lstPageId = new ArrayList<HashMap<String, String>>();
		
		//������������ImageItem<=====>��̬�����Ԫ�أ�����һһ��Ӧ
		saPageId = new SimpleAdapter(SQLiteTest.this, lstPageId, R.layout.pagebuttons, new String[]{"ItemText"}, new int[]{R.id.pagebtn_itemtv});
		//��Ӳ���ʾ
		gridview.setAdapter(saPageId);
		//�����Ϣ����
		gridview.setOnItemClickListener(itemClickListener);
	}
	
	private OnItemClickListener itemClickListener = new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3)
		{
			LoadPage(position);//������ѡ��ҳ��ȡ��Ӧ������
		}
	};
	
	private OnClickListener listener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			switch(v.getId())
			{
			case R.id.sql_createdb:
				CreateDB();
				break;
			case R.id.sql_insertdata:
				if(tabIsExist(TABLE_NAME))
				{
					InsertRecord(16);//����16����¼
					RefreshPage();
				}
				else
					Toast.makeText(SQLiteTest.this, "�봴�����ݿ�", Toast.LENGTH_SHORT).show();
				break;
			case R.id.sql_closedb:
				if(tabIsExist(TABLE_NAME))
					db.close();
				else
					Toast.makeText(SQLiteTest.this, "�봴�����ݿ�", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}
	};
	
	/*
	 * ��ȡָ��ID�ķ�ҳ����
	 * SQL:Select * From TABLE_NAME Limit 9 Offset 10;
	 * ��ʾ��TABLE_NAME���ȡ���ݣ� ����10�У�ȡ9��
	 */
	private void LoadPage(int pageID)
	{
		String sql = "select * from " + TABLE_NAME + 
				" Limit " + String.valueOf(PageSize) + " OffSet " + String.valueOf(pageID * PageSize);
		Cursor rec = db.rawQuery(sql, null);
		
		setTitle("��ǰ��ҳ������������ " + String.valueOf(rec.getCount()));
		
		//ȡ���ֶ�����
		String title = "";
		int colCount = rec.getColumnCount();
		for (int i = 0; i < colCount; i++)
		{
			title = title + rec.getColumnName(i) + "    ";
		}
		
		//�оٳ���������
		String content = "";
		int recCount= rec.getCount();
		for(int i = 0; i < recCount; i++)
		{
			//��λ��һ������
			rec.moveToPosition(i);
			for(int ii=0; ii<colCount; ii++)
			{
				//��λ��һ�������е�ÿ���ֶ�
				content = content + rec.getString(ii) + "   ";
			}
			content = content + "\r\n";
		}
		
		edtSQL.setText(title + "\r\n" +content);//��ʾ����
		rec.close();
	}
	
	/*
	 * ���ڴ洴�����ݿ�����ݱ�
	 */
	void CreateDB()
	{
		//���ڴ洴�����ݿ�
		db = SQLiteDatabase.create(null);
		Log.i(TAG, "DB Path: " + db.getPath());
		String amount = String.valueOf(databaseList().length);
		Log.i(TAG, "DB amount: " + amount);
		//�������ݱ� 
		String sql = "CREATE TABLE " + TABLE_NAME + "(" + ID + " text not null, " + NAME + " text not null " + ");";
		try{
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			db.execSQL(sql);
		}
		catch(SQLException e)
		{}
	}
	
	/*
	 * ����N������
	 */
	private void InsertRecord(int n)
	{
		int mtotal = id + n;
		String sql = "";
		for( ; id < mtotal; id++)
		{
			if(id<10)
				sql = "insert into " + TABLE_NAME + " (" + ID + "," + NAME + ") values('" + "0" + id + "', 'test');";
			else 
				sql = "insert into " + TABLE_NAME + " (" + ID + "," + NAME + ") values('" + String.valueOf(id) + "', 'test');";
			try
			{
				db.execSQL(sql);
			}
			catch(SQLException e)
			{
				
			}
		}
	}
	
	/*
	 * ����֮��ˢ�·�ҳ
	 */
	
	private void RefreshPage()
	{
		String sql = "select count(*) from " + TABLE_NAME;
		Cursor rec  = db.rawQuery(sql,  null);
		rec.moveToLast();
		long recSize = rec.getLong(0); //ȡ������
		rec.close();
		int pageNum;
		if(recSize % PageSize !=0)
			 pageNum= (int) (recSize/PageSize) + 1; //ȡ�÷�ҳ��
		else
			pageNum= (int) (recSize/PageSize); //ȡ�÷�ҳ��
		lstPageId.clear();
		for(int i = 0; i < pageNum; i++)
		{
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("ItemText", "No. " + String.valueOf(i));
			
			lstPageId.add(map);
		}
		saPageId.notifyDataSetChanged();
	}
	
	
	/**
     * �ж�ĳ�ű��Ƿ����
    * @param tabName ����
    * @return
     */
	 public boolean tabIsExist(String tabName){
         boolean result = false;
         if(tabName == null){
                 return false;
         }
         Cursor cursor = null;
         try {
                
                 String sql = "select count(*) as c from sqlite_master where type ='table' and name ='"+tabName.trim()+"' ";
                 cursor = db.rawQuery(sql, null);
                 if(cursor.moveToNext()){
                         int count = cursor.getInt(0);
                         if(count>0){
                                 result = true;
                         }
                 }
                 
         } catch (Exception e) {
                 // TODO: handle exception
         }                
         return result;
 }
}