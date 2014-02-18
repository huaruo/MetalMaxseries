package com.dana.startapp;

import java.util.ArrayList;
import java.util.List;

import com.dana.modul.ClsUtils;
import com.dana.util.Algorithm;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class BluetoothReflect extends Activity
{
	//Debug
	private static final String TAG = "BluetoothReflect";
	
	private Button btnSearch, btnShow, btnExit;
	private ListView lvBtDevices;
	private ArrayAdapter<String> adtDevices;
	List<String> lstDevices = new ArrayList<String>();
	
	private BluetoothDevice btDevice;
	private BluetoothAdapter btAdapter;
	
	private boolean btrflag = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bluetoothapi);
		
		btnSearch = (Button) findViewById(R.id.bta_search);
		btnShow = (Button) findViewById(R.id.bta_show);
		btnExit = (Button) findViewById(R.id.bta_exit);
		btnSearch.setOnClickListener(listener);
		btnShow.setOnClickListener(listener);
		btnExit.setOnClickListener(listener);
		
		lvBtDevices = (ListView) findViewById(R.id.bta_lv);
		adtDevices = new ArrayAdapter<String>(BluetoothReflect.this, 
				android.R.layout.simple_list_item_1, lstDevices);
		lvBtDevices.setAdapter(adtDevices);
		lvBtDevices.setOnItemClickListener(itemClickListener);
		
		btAdapter = BluetoothAdapter.getDefaultAdapter(); //��ʼ��������������
		if(btAdapter.getState() == BluetoothAdapter.STATE_OFF)//������
		{
			btAdapter.enable();
			Algorithm.delay(2);
		}
		
		
		//ע��Receiver����ȡ�����豸��صĽ��
		IntentFilter intent = new IntentFilter();
		intent.addAction(BluetoothDevice.ACTION_FOUND);
		intent.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
		registerReceiver(searchDevices, intent);
	}
	
	private BroadcastReceiver searchDevices = new BroadcastReceiver()
	{
		public void onReceive(Context c, Intent intent)
		{
			btrflag = true;
			String action = intent.getAction();
			Bundle b = intent.getExtras();
			Object[] lstName = b.keySet().toArray();
			
			//��ʾ�����յ�����Ϣ��ϸ��
			for(int i = 0; i<lstName.length; i++)
			{
				String keyName = lstName[i].toString();
				Log.i(TAG, keyName + ": " + String.valueOf(b.get(keyName)));
			}
			
			//�����豸ʱ��ȡ���豸��MAC��ַ
			if(BluetoothDevice.ACTION_FOUND.equals(action))
			{
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				
				if(device.getBondState() == BluetoothDevice.BOND_NONE)
				{
					String str = "δ���|" +device.getName()+ "|" +device.getAddress();
					lstDevices.add(str);//��ȡ�豸���ƺ�mac��ַ
					adtDevices.notifyDataSetChanged();
				}
			}
		}
	};
	
	private OnItemClickListener itemClickListener = new OnItemClickListener()
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View v, int position, long id)
		{
			btAdapter.cancelDiscovery();
			String str = lstDevices.get(position);
			String[] values = str.split("\\|");
			String address = values[2];
			
			btDevice = btAdapter.getRemoteDevice(address);
			try
			{
				if(values[0].equals("δ���"))
				{
					Toast.makeText(BluetoothReflect.this, "��δ���תΪ�����", Toast.LENGTH_SHORT).show();
					ClsUtils.createBond(btDevice.getClass(), btDevice);				
				}
				else if (values[0].equals("�����"))
				{
					Toast.makeText(BluetoothReflect.this, "�������תΪδ���",Toast.LENGTH_SHORT).show();
					ClsUtils.removeBond(btDevice.getClass(), btDevice);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	};
	
	private OnClickListener listener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			switch(v.getId())
			{
				case R.id.bta_search://���������������豸
					if(btAdapter.getState() == BluetoothAdapter.STATE_OFF)
					{
						btAdapter.enable();
						Algorithm.delay(2);
					}
						lstDevices.clear();
						
						Object[] lstDevice = btAdapter.getBondedDevices().toArray();
						for (int i =0; i<lstDevice.length; i++)
						{
							BluetoothDevice device = (BluetoothDevice) lstDevice[i];
							String str = "�����|" +device.getName()+"|" +device.getAddress();
							lstDevices.add(str);//��ȡ�豸���ƺ�mac��ַ
							adtDevices.notifyDataSetChanged();
						}
						//��ʼ����
						setTitle("����������ַ: " +btAdapter.getAddress());
						btAdapter.startDiscovery();
					break;
				case R.id.bta_show://��ʾBluetoothDevice�����з����ͳ�������������API
					if(btDevice != null)
						ClsUtils.printAllInfo(btDevice.getClass());
					else
						Toast.makeText(BluetoothReflect.this, "��ѡ��һ �������豸�������", Toast.LENGTH_SHORT).show();
					break;
				case R.id.bta_exit:
					if(btrflag)
						unregisterReceiver(searchDevices);
					btAdapter.disable();
				default:
					break;
			}
		}
	};
	
}