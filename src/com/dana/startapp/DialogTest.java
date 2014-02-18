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
	private Builder builder;//����Builder����
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btnDialog = (Button) findViewById(R.id.main_btn);
		btnDialog.setVisibility(View.VISIBLE);
		btnDialog.setText("����Dialog");
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
		//ʵ����Builder����
				builder = new Builder(DialogTest.this);
				//���öԻ����ͼ��
				builder.setIcon(android.R.drawable.ic_dialog_info);
				//���� �Ի���ı���
				builder.setTitle("Dialog");
				//���öԻ������ʾ�ı�
				//builder.setMessage("Dialog�Ի���");
				//������ఴť
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						//����dialog��dismiss
						try
						{
							Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
							field.setAccessible(true);
							//����mshowingֵ,��ƭandroidϵͳ,true�ر�,false���ر�
							field.set(dialog,true);
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
					}
				});
				//�����Ҳఴť
				builder.setNegativeButton("No", new DialogInterface.OnClickListener()
				{
					@Override
					public  void onClick(DialogInterface dialog, int which)
					{
						//����dialog��dismiss
						try
						{
							Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
							field.setAccessible(true);
							//����mShowingֵ����ƭandroidϵͳ
							field.set(dialog,false);
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
					}
				});
				builder.setView(new CheckBox(this));
				//��ӵ�ѡ��
						//		builder.setSingleChoiceItems(new String[]{"��ѡ","��ѡ"}, 1, new OnClickListener() {
						//			@Override
						//			public void onClick(DialogInterface dialog, int which) {
						//				//which :ѡ�����±� 
						//			}
						//		});
						//��Ӹ�ѡ��
						//		builder.setMultiChoiceItems(new String[] { "��ѡ", "��ѡ" }, new boolean[] { false, true }, new OnMultiChoiceClickListener() {
						//			@Override
						//			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
						//				//which��ѡ�����±�
						//				//isChecked��ѡ����Ĺ�ѡ״̬
						//			}
						//		});
						//����б�
						//		builder.setItems(new String[] { "�б���1", "�б���2", "�б���3" }, new OnClickListener() {
						//			@Override
						//			public void onClick(DialogInterface dialog, int which) {
						//				//which:ѡ�����±� 
						//			}
						//		});
						//����Զ��岼��
						//ʵ��layout����
				LayoutInflater inflater = getLayoutInflater();
				View layout = inflater.inflate(R.layout.dialogmain, (ViewGroup)findViewById(R.id.myLayout));
				builder.setView(layout);
				//����show()������ʾ���Ի���
				builder.show();
	}
	
	
	private void baseDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(DialogTest.this);
		builder.setIcon(android.R.drawable.btn_star);
		builder.setTitle("��ʾ");
		builder.setMessage("ȷ���˳�?");
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
//				dialog.dismiss();
				//����dialog��dismiss
				try
				{
					Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
					field.setAccessible(true);
					//����mshowingֵ,��ƭandroidϵͳ,true�ر�,false���ر�
					field.set(dialog,true);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				DialogTest.this.finish();
			}
		});
		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				//����dialog��dismiss
				try
				{
					Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
					field.setAccessible(true);
					//����mShowingֵ����ƭandroidϵͳ
					field.set(dialog,true);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		builder.setNeutralButton("��ֹ", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				//����dialog��dismiss
				try
				{
					Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
					field.setAccessible(true);
					//����mShowingֵ����ƭandroidϵͳ
					field.set(dialog,false);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		builder.setCancelable(false);//����Ӧback��ť
		
		AlertDialog alert = builder.create();
		alert.show();
	}

	private void editDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(DialogTest.this);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setView(new EditText(this));
		builder.setPositiveButton("ȷ��", null);
		builder.setNegativeButton("ȡ��", null);
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
		 * setSingleChoiceItems() �ĵڶ���������һ��checkedItem������ֵ��ָʾ�˻���0��ȱʡѡ�����λ�á���-1����������Ĭ��ѡ���
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
				Toast.makeText(getApplicationContext(), "ѡ����" + items[item] +"ѡ��, " +String.valueOf(flag), Toast.LENGTH_SHORT).show();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
		/*
		 * ��Ҫ��ȡlist����Щ���Ǳ�ѡ�еģ�����Ҫ��
		 * //���ListView
		 * ListView list=dlg.getListView();
		 * //�жϵ�i���Ƿ�ѡ�У�Ϊ���ʾ��ѡ�У�Ϊ�ٱ�ʾû��ѡ��
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
				Toast.makeText(DialogTest.this, year+"��" +  (month+1) + "��" + dayOfMonth + "��", Toast.LENGTH_SHORT).show();
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
//		ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());//ʹ�ô˾�ᱨ��
		ProgressDialog progressDialog = new ProgressDialog(DialogTest.this);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setIcon(android.R.drawable.progress_horizontal);
		progressDialog.setMessage("Loading...");
		progressDialog.setCancelable(false);
		progressDialog.show();
	}
}