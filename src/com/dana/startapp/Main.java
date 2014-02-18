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
	
	//�ؼ�
	private LinearLayout imagebtn1, imagebtn2;
	
	//��������˵���ͼƬ������
	private int[] menuImage = null;
	private String[] menuName = null;
	private Intent intent;
	private List<String> items = fillArray();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "++ ������ͼ ++");
		/*
		 * ���ô��ڲ���
		 */
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
		Log.i(TAG, "�Զ������");
/*		
		//�ޱ���
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		//����ȫ��
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
*/		 	
		
		//��ImageView��TextView��װ��LinearLayout�ڣ����õ�����ԣ��γɰ�ť
		imagebtn1 = (LinearLayout)findViewById(R.id.imageButton1);
		//���ð�ť�Ƿ�ɼ�, View.VISIBLE = 0x00000000,��ʾ�ɼ��� View.INVISIBLE = 0x00000004�� ��ʾ���أ� View.GONE = 0x00000008�� ��ʾ�����ڡ�
		imagebtn1.setVisibility(View.VISIBLE);
		
		//�ı䰴ťͼ��
		//imagebtn1.setImageDrawable(getResources().getDrawable(R.drawable.back));
		//��̬�ػ���ť
		//Bitmap btm1 = Bitmapfactory.decodeResource(start.this.getResources(), R.drawable.back);
		//BitmapDrawable bd1 = new BitmapDrawable(btm1);
		//imagebtn1.setBackgroundDrawable(bd1);
		
		//��̬�ı��С
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
		
		menuName = getResources().getStringArray(R.array.items_array);//��ȡ�ַ�����������
		int size = menuName.length;
		menuImage = new int[size];
		//��ʼ������˵���ͼƬ������
		for (int k=0; k<size;k++)
		{
			menuImage[k] = R.drawable.image;
		}
		
		//��������
		GridView gridView =(GridView) findViewById(R.id.main_gridview);
		gridView.setVisibility(View.VISIBLE);
		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
		Log.i(TAG, "��ʾͼ��");
		
		for (int i= 0; i< size; i++)
		{
			HashMap<String,Object>map=new HashMap<String, Object>();
			map.put("ItemImage", menuImage[i]);
			map.put("ItemText", menuName[i]);
			//��map��ʾ����Ļ
			lstImageItem.add(map);
		}
		//��ʼ���������õ������
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

	//��ť�������
			private OnClickListener listener = new OnClickListener()
			{
				public void onClick(View arg0)
				{
					switch(arg0.getId())
					{
						case R.id.imageButton1:
							Log.i(TAG, "����");
							finish();
							break;
						default:
							break;
					}
				}
			};
	
	//����������
			
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
		//���û���ķ����Ա����ϵͳ�˵�(����еĻ�)
		super.onCreateOptionsMenu(menu);
//		getMenuInflater().inflate(R.menu.start, menu); //����R.menu.start�ļ�
		menu.add(0,1, 0, "���¿�ʼ").setIcon(R.drawable.image);
		menu.add(0,2,0,"��Ϸָ��").setIcon(R.drawable.child_image);
		menu.add(0,3,0,"������Ϸ").setIcon(R.drawable.image);
		menu.add(0,4,0,"�˳�").setIcon(R.drawable.child_image);
		//����ֵΪtrue,��ʾ�˵��ɼ�������ʾ�˵�
		return true;
	}
	/**
	 * menu.add(int groupId, int itemId, int order, CharSequence title)
	 * group ID�����������������õ��ķ����У�
	 * removeGroup(id)
	 * setGroupCheckable(id, checkable, exclusive)
	 * setGroupEnabled(id, boolean enabled)
	 * setGroupVisible(id,visible)
	 * item ID����������Ŀ��ţ� ʹ�ò˵�ʱ��ͨ�����item ID���жϣ�ѡ�е����ĸ�ѡ�
	 * orderID����������ǲ˵������ʾ˳��Ĭ����0����ʾ�˵�����ʾ˳����ǰ���add��˳������ʾ
	 * title������ʾѡ������ʾ������
	 * setIcon Ϊ�˵����ͼ����ʾ
	 */
	public boolean onOptionsItemSelected(MenuItem item)
	{
		super.onOptionsItemSelected(item);
		int id = item.getItemId();
		switch(id)
		{
			case 1:
//				showDialog();
				new AlertDialog.Builder(this).setTitle("����").setMessage("��ʼ").show();
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
//		 * // �������ַ��������� // //LayoutInflater inflater = getLayoutInflater();
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
//		textView.setText("�����ˣ�");
//		ImageView imageView = (ImageView) layout.findViewById(R.id.image);
//		imageView.setImageResource(R.drawable.tap);
//		builder = new AlertDialog.Builder(MainActivity.this);// ����һ�������Ի�������
//		builder.setView(layout);
//		alertDialog = builder.create();// ͨ������������һ���Ի���
//		alertDialog.show();
//	}

}
