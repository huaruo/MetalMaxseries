package com.dana.startapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData.Item;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;

public class Main extends Activity {
	//Debug
	private static final String TAG= "Main";
	
	//控件
	private LinearLayout imagebtn1, imagebtn2;
	
	//定义网格菜单的图片和文字
	private int[] menuImage = null;
	private String[] menuName = null;
	private Intent intent;
	private List<String> items = fillArray();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "++ 创建视图 ++");
		/*
		 * 设置窗口布局
		 */
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
		Log.i(TAG, "自定义标题");
/*		
		//无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		//设置全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
*/		 	
		
		//将ImageView和TextView封装在LinearLayout内，设置点击属性，形成按钮
		imagebtn1 = (LinearLayout)findViewById(R.id.imageButton1);
		//设置按钮是否可见, View.VISIBLE = 0x00000000,表示可见； View.INVISIBLE = 0x00000004， 表示隐藏； View.GONE = 0x00000008， 表示不存在。
		imagebtn1.setVisibility(View.VISIBLE);
		
		//改变按钮图标
		//imagebtn1.setImageDrawable(getResources().getDrawable(R.drawable.back));
		//动态重画按钮
		//Bitmap btm1 = Bitmapfactory.decodeResource(start.this.getResources(), R.drawable.back);
		//BitmapDrawable bd1 = new BitmapDrawable(btm1);
		//imagebtn1.setBackgroundDrawable(bd1);
		
		//动态改变大小
		/*
		 RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams)imagebtn1.getLayoutParams();
		 linearParams.width = 400;
		 linearParams.height = 200;
		 imagebtn1.setLayoutParams(linearParams);		 
		 */
		
		/*
		 imageView1 = (ImageView)findViewById(R.id.imageView1);
		 LinearLayout LayoutParams linearParams1 = ( LinearLayout.LayoutParams) imageView1.getLayoutParams();
		 1inearParams1.width = 160;
		 linearParams1.height = 90;
		 imageView1.setLayoutParams(linearParams1);
		 */
		
		imagebtn1.setClickable(true);
		//imagebtn1.setOntouchListener(otl);
		imagebtn1.setOnClickListener(listener);
		
		menuName = getResources().getStringArray(R.array.items_array);//获取字符串数组内容
		int size = menuName.length;
		menuImage = new int[size];
		//初始化网格菜单的图片和文字
		for (int k=0; k<size;k++)
		{
			menuImage[k] = R.drawable.image;
		}
		
		//建立网格
		GridView gridView =(GridView) findViewById(R.id.main_gridview);
		gridView.setVisibility(View.VISIBLE);
		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
		Log.i(TAG, "显示图样");
		
		for (int i= 0; i< size; i++)
		{
			HashMap<String,Object>map=new HashMap<String, Object>();
			map.put("ItemImage", menuImage[i]);
			map.put("ItemText", menuName[i]);
			//将map显示到屏幕
			lstImageItem.add(map);
		}
		//初始化网格，设置点击操作
				SimpleAdapter saImageItems = new SimpleAdapter(this, lstImageItem, R.layout.grid_item,
						new String[] {"ItemImage", "ItemText"}, new int[]{R.id.itemImage, R.id.itemText});
				gridView.setAdapter(saImageItems);
				gridView.setOnItemClickListener(ItemListener);
	}
	
	private List<String> fillArray()
	{
		List<String> items = new ArrayList<String>();
		for(int i =0; i<6; i++)
		{
			items.add(String.valueOf(i));
		}
		return items;
	}

	//按钮点击操作
			private OnClickListener listener = new OnClickListener()
			{
				public void onClick(View arg0)
				{
					switch(arg0.getId())
					{
						case R.id.imageButton1:
							Log.i(TAG, "返回");
							finish();
							break;
						default:
							break;
					}
				}
			};
	
	//网格点击操作
			
			private OnItemClickListener ItemListener = new OnItemClickListener()
			{
				public void onItemClick(AdapterView<?> arg0, //The AdapterView where the click happened
						View v, //The view within the AdapterView that was clicked
						int position, //The position of the view in the adapter
						long id //The row id of the item that was clicked
						)
				{
					Log.i(TAG,menuName[position]);
					switch(position)
					{
						case 0:
							intent = new Intent(Main.this, DoTransaction.class);
							startActivity(intent);
							break;
						case 1:
							intent = new Intent(Main.this, ViewAnimatorTest.class);
							startActivity(intent);
							break;
						case 2:
							intent = new Intent(Main.this, SpinnerTest.class);
							startActivity(intent);
							break;
						case 3:
							intent = new Intent(Main.this, ScrollTest.class);
							startActivity(intent);
							break;
						case 4:
							intent = new Intent(Main.this, LongClick.class);
							startActivity(intent);
							break;
						case 5:
							intent = new Intent(Main.this, ExListView.class);
							startActivity(intent);
							break;
						case 6:
							intent = new Intent(Main.this, ShowPopupWindow.class);
							startActivity(intent);
							break;
						case 7:
							intent = new Intent(Main.this, ToastTest.class);
							startActivity(intent);
							break;
						case 8:
							intent = new Intent(Main.this, AdjustBitmap.class);
							startActivity(intent);
							break;
						case 9:
							intent = new Intent(Main.this, HandcentList.class);
							startActivity(intent);
							break;
						case 10:
							intent = new Intent(Main.this, ListSort.class);
							startActivity(intent);
							break;
						case 11:
							intent = new Intent(Main.this, CustomList.class);
							startActivity(intent);
							break;
						case 12:
							intent = new Intent(Main.this, AppInfoDisplay.class);
							startActivity(intent);
							break;
						case 13:
							intent = new Intent(Main.this, SetStyle.class);
							startActivity(intent);
							break;
						case 14:
							intent = new Intent(Main.this, DoubleClick.class);
							startActivity(intent);
							break;
						case 15:
							intent = new Intent(Main.this, BshSOView.class);
							startActivity(intent);
							break;
						case 16:
							intent = new Intent(Main.this, HttpDemo.class);
							startActivity(intent);
							break;
						case 17:
							intent = new Intent(Main.this, Keyevent.class);
							startActivity(intent);
							break;
						case 18:
							intent = new Intent(Main.this, Motionevent.class);
							startActivity(intent);
							break;
						case 19:
							intent = new Intent(Main.this, TableLayoutDemo.class);
							startActivity(intent);
							break;
						case 20:
							intent = new Intent(Main.this, MTabTest.class);
							startActivity(intent);
							break;
						case 21:
							intent = new Intent(Main.this, ImgSwitcherTest.class);
							startActivity(intent);
							break;
						case 22:
							intent = new Intent(Main.this, ImageViewDraw.class);
							startActivity(intent);
							break;
						case 23:
							intent = new Intent(Main.this, MediaTest.class);
							startActivity(intent);
							break;
						case 24:
							intent = new Intent(Main.this, SurfaceViewTest.class);
							startActivity(intent);
							break;
						case 25:
							intent = new Intent(Main.this, MultiSurfaceView.class);
							startActivity(intent);
							break;
						case 26:
							intent = new Intent(Main.this, ActivityIntent1.class);
							startActivity(intent);
							break;
						case 27:
							intent = new Intent(Main.this, ServiceTest.class);
							startActivity(intent);
							break;
						case 28:
							intent = new Intent(Main.this, BroadcastReceiverTest.class);
							startActivity(intent);
							break;
						case 29:
							intent = new Intent(Main.this, ParserXML.class);
							startActivity(intent);
							break;
						case 30:
							intent = new Intent(Main.this, SQLiteTest.class);
							startActivity(intent);
							break;
						case 31:
							intent = new Intent(Main.this, SQLiteListTest.class);
							startActivity(intent);
							break;
						case 32:
							intent = new Intent(Main.this, AudioRecordTest.class);
							startActivity(intent);
							break;
						case 33:
							intent = new Intent(Main.this, OscilloscopeTest.class);
							startActivity(intent);
							break;
						case 34:
							intent = new Intent(Main.this, BluetoothReflect.class);
							startActivity(intent);
							break;
						case 35:
							intent = new Intent(Main.this, TelephonyManagerTest.class);
							startActivity(intent);
							break;
						case 36:
							intent = new Intent(Main.this, GridTab.class);
							startActivity(intent);
							break;
						case 37:
							intent = new Intent(Main.this, AutoListView.class);
							startActivity(intent);
							break;
						case 38:
							intent = new Intent(Main.this, ImageGalleryTest.class);
							startActivity(intent);
							break;
						case 39:
							intent = new Intent(Main.this, SlidingDrawTest.class);
							startActivity(intent);
							break;
						case 40:
							intent = new Intent(Main.this, AnimationTest.class);
							startActivity(intent);
							break;
						case 41:
							intent = new Intent(Main.this, WindowManagerTest.class);
							startActivity(intent);
							break;
						case 42:
							intent = new Intent(Main.this, QQTab.class);
							startActivity(intent);
							break;
						case 43:
							intent = new Intent(Main.this, ProgressBarTest.class);
							startActivity(intent);
							break;
						case 44:
							intent = new Intent(Main.this, SeekBarTest.class);
							startActivity(intent);
							break;
						case 45:
							intent = new Intent(Main.this, DialogTest.class);
							startActivity(intent);
							break;
						case 46:
							intent = new Intent(Main.this, ScreenSwitch.class);
							startActivity(intent);
							break;
						case 47:
							intent = new Intent(Main.this, GameView.class);
							startActivity(intent);
							break;
						case 48:
							intent = new Intent(Main.this, GameSurface.class);
							startActivity(intent);
							break;
						case 49:
							intent = new Intent(Main.this, QPathDraw.class);
							startActivity(intent);
							break;
						case 50:
							intent = new Intent(Main.this, HandleMessage.class);
							startActivity(intent);
							break;
						default:
							break;
					}
				}
	
			};
			
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//调用基类的方法以便调出系统菜单(如果有的话)
		super.onCreateOptionsMenu(menu);
//		getMenuInflater().inflate(R.menu.start, menu); //调用R.menu.start文件
		menu.add(0,1, 0, "重新开始").setIcon(R.drawable.image);
		menu.add(0,2,0,"游戏指南").setIcon(R.drawable.child_image);
		menu.add(0,3,0,"关于游戏").setIcon(R.drawable.image);
		menu.add(0,4,0,"退出").setIcon(R.drawable.child_image);
		//返回值为true,表示菜单可见，即显示菜单
		return true;
	}
	/**
	 * menu.add(int groupId, int itemId, int order, CharSequence title)
	 * group ID参数代表组概念，可以用到的方法有：
	 * removeGroup(id)
	 * setGroupCheckable(id, checkable, exclusive)
	 * setGroupEnabled(id, boolean enabled)
	 * setGroupVisible(id,visible)
	 * item ID参数代表项目编号， 使用菜单时，通过这个item ID来判断，选中的是哪个选项。
	 * orderID参数代表的是菜单项的显示顺序。默认是0，表示菜单的显示顺序就是按照add的顺序来显示
	 * title参数表示选项中显示的文字
	 * setIcon 为菜单添加图标显示
	 */
	public boolean onOptionsItemSelected(MenuItem item)
	{
		super.onOptionsItemSelected(item);
		int id = item.getItemId();
		switch(id)
		{
			case 1:
//				showDialog();
				new AlertDialog.Builder(this).setTitle("标题").setMessage("开始").show();
				break;
			case 2:
				break;
			case 3:
				finish();
				break;
			case 4:
				System.exit(0);
				break;
			default:
				break;

		}
		return true;
	}
//	private void showDialog() {
//		AlertDialog.Builder builder;
//		AlertDialog alertDialog;
//		//Context mContext = MainActivity.this;
//
//		/*
//		 * // 下面俩种方法都可以 // //LayoutInflater inflater = getLayoutInflater();
//		 * LayoutInflater inflater = (LayoutInflater) mContext
//		 * .getSystemService(LAYOUT_INFLATER_SERVICE); View layout =
//		 * inflater.inflate(R.layout.dialog, null); TextView text = (TextView)
//		 * layout.findViewById(R.id.text);
//		 * text.setText("Hello, Welcome to Mr Wei's blog!"); ImageView image =
//		 * (ImageView) layout.findViewById(R.id.image);
//		 * image.setImageResource(R.drawable.tap); builder = new
//		 * AlertDialog.Builder(mContext); builder.setView(layout); alertDialog =
//		 * builder.create(); alertDialog.show();
//		 */
//		LayoutInflater inflater = (LayoutInflater)  MainActivity.this
//				.getSystemService(LAYOUT_INFLATER_SERVICE);
//		View layout = inflater.inflate(R.layout.dialog, null);
//		TextView textView = (TextView) layout.findViewById(R.id.text);
//		textView.setText("出错了！");
//		ImageView imageView = (ImageView) layout.findViewById(R.id.image);
//		imageView.setImageResource(R.drawable.tap);
//		builder = new AlertDialog.Builder(MainActivity.this);// 创建一个弹出对话框构造器
//		builder.setView(layout);
//		alertDialog = builder.create();// 通过构造器产生一个对话框
//		alertDialog.show();
//	}

}
