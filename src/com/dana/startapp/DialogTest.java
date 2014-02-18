package com.dana.startapp;

import java.lang.reflect.Field;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class DialogTest extends Activity
{
	//Debug
	private static final String TAG = "DialogTest";
	
	private Button btnDialog;
	private Builder builder;//声明Builder对象
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btnDialog = (Button) findViewById(R.id.main_btn);
		btnDialog.setVisibility(View.VISIBLE);
		btnDialog.setText("启动Dialog");
		btnDialog.setOnClickListener(listener);
	}
	private OnClickListener listener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			switch(v.getId())
			{
				case R.id.main_btn:
					customDialog();
//					editDialog();
//					singleDialog();
//					dateDialog();
//					timeDialog();
					break;
				default:
					break;
			}
		}
	};
	
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() ==0)
		{
			baseDialog();
//			listDialog();
//			multiDialog();
//			progressDialog();
		}
		return false;
	}
	
	
	private void customDialog()
	{
		//实例化Builder对象
				builder = new Builder(DialogTest.this);
				//设置对话框的图标
				builder.setIcon(android.R.drawable.ic_dialog_info);
				//设置 对话框的标题
				builder.setTitle("Dialog");
				//设置对话框的提示文本
				//builder.setMessage("Dialog对话框");
				//监听左侧按钮
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						//控制dialog的dismiss
						try
						{
							Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
							field.setAccessible(true);
							//设置mshowing值,欺骗android系统,true关闭,false不关闭
							field.set(dialog,true);
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
					}
				});
				//监听右侧按钮
				builder.setNegativeButton("No", new DialogInterface.OnClickListener()
				{
					@Override
					public  void onClick(DialogInterface dialog, int which)
					{
						//控制dialog的dismiss
						try
						{
							Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
							field.setAccessible(true);
							//设置mShowing值，欺骗android系统
							field.set(dialog,false);
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
					}
				});
				builder.setView(new CheckBox(this));
				//添加单选框
						//		builder.setSingleChoiceItems(new String[]{"单选","单选"}, 1, new OnClickListener() {
						//			@Override
						//			public void onClick(DialogInterface dialog, int which) {
						//				//which :选中项下标 
						//			}
						//		});
						//添加复选框
						//		builder.setMultiChoiceItems(new String[] { "多选", "多选" }, new boolean[] { false, true }, new OnMultiChoiceClickListener() {
						//			@Override
						//			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
						//				//which：选中项下标
						//				//isChecked：选中项的勾选状态
						//			}
						//		});
						//添加列表
						//		builder.setItems(new String[] { "列表项1", "列表项2", "列表项3" }, new OnClickListener() {
						//			@Override
						//			public void onClick(DialogInterface dialog, int which) {
						//				//which:选中项下标 
						//			}
						//		});
						//添加自定义布局
						//实例layout布局
				LayoutInflater inflater = getLayoutInflater();
				View layout = inflater.inflate(R.layout.dialogmain, (ViewGroup)findViewById(R.id.myLayout));
				builder.setView(layout);
				//调用show()方法显示出对话框
				builder.show();
	}
	
	
	private void baseDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(DialogTest.this);
		builder.setIcon(android.R.drawable.btn_star);
		builder.setTitle("提示");
		builder.setMessage("确认退出?");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
//				dialog.dismiss();
				//控制dialog的dismiss
				try
				{
					Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
					field.setAccessible(true);
					//设置mshowing值,欺骗android系统,true关闭,false不关闭
					field.set(dialog,true);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				DialogTest.this.finish();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				//控制dialog的dismiss
				try
				{
					Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
					field.setAccessible(true);
					//设置mShowing值，欺骗android系统
					field.set(dialog,true);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		builder.setNeutralButton("中止", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				//控制dialog的dismiss
				try
				{
					Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
					field.setAccessible(true);
					//设置mShowing值，欺骗android系统
					field.set(dialog,false);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		builder.setCancelable(false);//不响应back按钮
		
		AlertDialog alert = builder.create();
		alert.show();
	}

	private void editDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(DialogTest.this);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setView(new EditText(this));
		builder.setPositiveButton("确定", null);
		builder.setNegativeButton("取消", null);
		AlertDialog alert = builder.create();
		alert.show();
	}

	private void listDialog()
	{
		final CharSequence[] items = {"Red", "Green", "Blue"};
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Pick a color");
		builder.setItems(items, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int item)
			{
				Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	private void singleDialog()
	{
		final CharSequence[] items = {"Red", "Green", "Blue"};
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		/*
		 * setSingleChoiceItems() 的第二个参数是一个checkedItem整型数值，指示了基于0的缺省选择项的位置。“-1”代表不会有默认选择项。
		 */
		builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int item)
			{
				Toast.makeText(getApplicationContext(),  items[item], Toast.LENGTH_SHORT).show();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	private void multiDialog()
	{
		final CharSequence[] items = {"Red","Green","Blue"};
		int size = items.length;
		boolean[] ischecked = new boolean[size];
		for(int i = 0; i<size; i++)
		{
			ischecked[i] = false;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(DialogTest.this);
		builder.setTitle("Pick a color");
		builder.setMultiChoiceItems(items, ischecked, new DialogInterface.OnMultiChoiceClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int item, boolean flag)
			{
				Toast.makeText(getApplicationContext(), "选择了" + items[item] +"选项, " +String.valueOf(flag), Toast.LENGTH_SHORT).show();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
		/*
		 * 想要获取list中哪些项是被选中的，你需要：
		 * //获得ListView
		 * ListView list=dlg.getListView();
		 * //判断第i项是否被选中，为真表示被选中，为假表示没有选中
		 * list.getCheckedItemPositions().get(i)
		 */
	}
	
	private void dateDialog()
	{
		Calendar calendar = Calendar.getInstance();
		DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener()
		{
			@Override
			public  void  onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth)
			{
				Toast.makeText(DialogTest.this, year+"年" +  (month+1) + "月" + dayOfMonth + "日", Toast.LENGTH_SHORT).show();
			}
		};
		DatePickerDialog dlg = new DatePickerDialog(DialogTest.this,
				dateListener,
				calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		dlg.show();
		//return dlg;
	}

	private void timeDialog()
	{
		Calendar calendar =  Calendar.getInstance();
		TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener()
		{
			@Override
			public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute)
			{
				Toast.makeText(DialogTest.this, hourOfDay + ": " + minute, Toast.LENGTH_SHORT).show();
			}
		};
		TimePickerDialog dlg = new TimePickerDialog(DialogTest.this,
				timeListener,
				calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE),
				true);
		dlg.show();
		//return dlg;
	}
	
	private void progressDialog()
	{
//		ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());//使用此句会报错。
		ProgressDialog progressDialog = new ProgressDialog(DialogTest.this);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setIcon(android.R.drawable.progress_horizontal);
		progressDialog.setMessage("Loading...");
		progressDialog.setCancelable(false);
		progressDialog.show();
	}
}