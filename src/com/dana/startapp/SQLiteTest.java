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
	private EditText edtSQL; //显示分页数据
	private GridView gridview;
	private SQLiteDatabase db;
	
	private int id;//添加记录时的id累加标记。必须全局
	private static final int PageSize = 10; //分页时，每页的数据总数
	private static final String TABLE_NAME = "stu";
	private static final String ID = "id";
	private static final String NAME = "name";
	
	private SimpleAdapter saPageId; //分页栏适配器
	private ArrayList<HashMap<String,String>> lstPageId; //分页栏的数据源，与PageSize和数据总数相关
	
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
		
		//生成动态数组，并且转入数据
		lstPageId = new ArrayList<HashMap<String, String>>();
		
		//生成适配器的ImageItem<=====>动态数组的元素，两者一一对应
		saPageId = new SimpleAdapter(SQLiteTest.this, lstPageId, R.layout.pagebuttons, new String[]{"ItemText"}, new int[]{R.id.pagebtn_itemtv});
		//添加并显示
		gridview.setAdapter(saPageId);
		//添加消息处理
		gridview.setOnItemClickListener(itemClickListener);
	}
	
	private OnItemClickListener itemClickListener = new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3)
		{
			LoadPage(position);//根据所选分页读取对应的数据
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
					InsertRecord(16);//插入16条记录
					RefreshPage();
				}
				else
					Toast.makeText(SQLiteTest.this, "请创建数据库", Toast.LENGTH_SHORT).show();
				break;
			case R.id.sql_closedb:
				if(tabIsExist(TABLE_NAME))
					db.close();
				else
					Toast.makeText(SQLiteTest.this, "请创建数据库", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}
	};
	
	/*
	 * 读取指定ID的分页数据
	 * SQL:Select * From TABLE_NAME Limit 9 Offset 10;
	 * 表示从TABLE_NAME表获取数据， 跳过10行，取9行
	 */
	private void LoadPage(int pageID)
	{
		String sql = "select * from " + TABLE_NAME + 
				" Limit " + String.valueOf(PageSize) + " OffSet " + String.valueOf(pageID * PageSize);
		Cursor rec = db.rawQuery(sql, null);
		
		setTitle("当前分页的数据总数： " + String.valueOf(rec.getCount()));
		
		//取得字段名称
		String title = "";
		int colCount = rec.getColumnCount();
		for (int i = 0; i < colCount; i++)
		{
			title = title + rec.getColumnName(i) + "    ";
		}
		
		//列举出所有数据
		String content = "";
		int recCount= rec.getCount();
		for(int i = 0; i < recCount; i++)
		{
			//定位到一条数据
			rec.moveToPosition(i);
			for(int ii=0; ii<colCount; ii++)
			{
				//定位到一条数据中的每个字段
				content = content + rec.getString(ii) + "   ";
			}
			content = content + "\r\n";
		}
		
		edtSQL.setText(title + "\r\n" +content);//显示出来
		rec.close();
	}
	
	/*
	 * 在内存创建数据库和数据表
	 */
	void CreateDB()
	{
		//在内存创建数据库
		db = SQLiteDatabase.create(null);
		Log.i(TAG, "DB Path: " + db.getPath());
		String amount = String.valueOf(databaseList().length);
		Log.i(TAG, "DB amount: " + amount);
		//创建数据表 
		String sql = "CREATE TABLE " + TABLE_NAME + "(" + ID + " text not null, " + NAME + " text not null " + ");";
		try{
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			db.execSQL(sql);
		}
		catch(SQLException e)
		{}
	}
	
	/*
	 * 插入N条数据
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
	 * 插入之后刷新分页
	 */
	
	private void RefreshPage()
	{
		String sql = "select count(*) from " + TABLE_NAME;
		Cursor rec  = db.rawQuery(sql,  null);
		rec.moveToLast();
		long recSize = rec.getLong(0); //取得总数
		rec.close();
		int pageNum;
		if(recSize % PageSize !=0)
			 pageNum= (int) (recSize/PageSize) + 1; //取得分页数
		else
			pageNum= (int) (recSize/PageSize); //取得分页数
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
     * 判断某张表是否存在
    * @param tabName 表名
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