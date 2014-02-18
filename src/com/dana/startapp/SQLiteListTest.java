/**
 * @author Huaruo.W
 * issue: GridViewTable.java��gvTable��gvPage��setOnItemClickListener�ϲ�, ����ر����ݿⲻ�����ʵ�����ݡ��˳�����ȴ����������ݣ�Ϊʲô�� sdk�汾����3ʱ��No.1������ʾ.
 */
package com.dana.startapp;

import com.dana.modul.GirdViewTable;
import com.dana.modul.GirdViewTable.OnPageSwitchListener;
import com.dana.modul.GirdViewTable.OnTableClickListener;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SQLiteListTest extends Activity
{
	//Debug
	private static final String TAG = "SQLiteListTest";
	
	private Button btnCreateDB, btnInsertData, btnCloseDB;
	private SQLiteDatabase db;
	private GirdViewTable table;
	private int id;//��Ӽ�¼ʱ��id�ۼӱ�ǣ�����ȫ�֡�
	
	private static final String TABLE_NAME = "stu";
	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String PHONE = "phone";
	private static final String ADDRESS = "address";
	private static final String AGE = "age";
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sqlitetest);
		
		btnCreateDB = (Button) findViewById(R.id.sql_createdb);
		btnInsertData = (Button) findViewById(R.id.sql_insertdata);
		btnCloseDB = (Button) findViewById(R.id.sql_closedb);
		
		btnCreateDB.setOnClickListener(listener);
		btnInsertData.setOnClickListener(listener);
		btnCloseDB.setOnClickListener(listener);
		
		table = new GirdViewTable(this);
		table.gvSetTableRowCount(8); //����ÿ����ҳ��ROW����
		
		LinearLayout ll =(LinearLayout) findViewById(R.id.sql_ll);
		table.setTableOnClickListener(tableClickListener);
		table.setOnPageSwitchListener(pageSwitchListener);
		
		ll.addView(table);
	}
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
						table.gvUpdatePageBar("select count(*) from " + TABLE_NAME, db);
						table.gvReadyTable("select * from "+ TABLE_NAME, db);
						
					}
					else
						Toast.makeText(SQLiteListTest.this, "�봴�����ݿ�", Toast.LENGTH_SHORT).show();
					break;
				case R.id.sql_closedb:
					if(tabIsExist(TABLE_NAME))
					{
						table.gvRemoveAll();
						db.close();
					}
					else
						Toast.makeText(SQLiteListTest.this, "�봴�����ݿ�", Toast.LENGTH_SHORT).show();
					break;
				default:
					break;
			}
		}
	};
	
	private OnTableClickListener tableClickListener = new OnTableClickListener()
	{
		@Override
		public void onTableClickListener(int x, int y, Cursor c)
		{
			c.moveToPosition(y);
			String str = c.getString(x) + "λ��:(" + String.valueOf(x) + "," + String.valueOf(y) + ")";
			Toast.makeText(SQLiteListTest.this, str, Toast.LENGTH_SHORT).show();
			
		}
	};
	
	private OnPageSwitchListener pageSwitchListener = new  OnPageSwitchListener()
	{
		@Override
		public void onPageSwitchListener(int pageId, int pageCount)
		{
			String str = "����" +  String.valueOf(pageCount) + "ҳ,��ǰ��" +String.valueOf(pageId+1) + "ҳ";
			Toast.makeText(SQLiteListTest.this, str, Toast.LENGTH_SHORT).show();
		}
	};
	
	/*
	 * ���ڴ洴�����ݿ�����ݱ�
	 */
	private void CreateDB(){
		//���ڴ洴�����ݿ�
		db = SQLiteDatabase.create(null);
		Log.i(TAG, "DB Path: " + db.getPath());
		String amount = String.valueOf(databaseList().length);
		Log.i(TAG, "DB amount: " + amount);
		//�������ݱ�
		String sql = "CREATE TABLE " + TABLE_NAME + " (" +
		ID + " text not null, " + NAME +" text not null, " + ADDRESS + " text not null, " + PHONE + 
		" text not null, " + AGE + " text not null " + ");";
		try{
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			db.execSQL(sql);
		}
		catch(SQLException e)
		{}
	}
	
	/**
	 * ��������
	 */
	private void InsertRecord(int n)
	{
		int total = id + n;
		for(; id < total; id ++)
		{
			String sql = "insert into " +TABLE_NAME + " (" + ID +
					", " + NAME + ", " +ADDRESS + ", " + PHONE + "," 
					+ AGE + ") values('" + String.valueOf(id) + "', 'man', 'address', '123456789', '18');";
			try
			{
				db.execSQL(sql);
			}
			catch(SQLException e)
			{}
		}
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