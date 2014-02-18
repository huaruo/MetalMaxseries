package com.dana.startapp;

import java.util.ArrayList;
import java.util.List;

import com.dana.modul.AppInfo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

public class AppInfoDisplay extends Activity {
	//Debug
	private static final String TAG = "App Info";
	//Use ArrayList to store the installed non-system apps
	ArrayList<AppInfo> appList = new ArrayList<AppInfo>();
	
	private TextView list_Tv, appName;
	private ListView app_listView;
	private ImageView appIcon;
	//ListView app_listView;
	/** Called when the activity is first created.*/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_view);
		Log.i(TAG, " ++ On Created ++");
		
		list_Tv = (TextView) findViewById(R.id.list_tv);
		list_Tv.setVisibility(View.VISIBLE);
		list_Tv.setTextSize(25);
		
		
		/* ͨ��PackageManager��ȡ�Ѱ�װ��Ӧ�ð���Ϣ */
		List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
		
		for (int i=0; i<packages.size(); i++)
		{
			PackageInfo packageInfo = packages.get(i);
			AppInfo tmpInfo = new AppInfo();
			tmpInfo.appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
			tmpInfo.packageName = packageInfo.packageName;
			tmpInfo.versionName = packageInfo.versionName;
			tmpInfo.versionCode = packageInfo.versionCode;
			tmpInfo.appIcon = packageInfo.applicationInfo.loadIcon(getPackageManager());
			//Only display the non-system app info
			if((packageInfo.applicationInfo.flags&ApplicationInfo.FLAG_SYSTEM)<=0)
			{
				appList.add(tmpInfo);
			}
			
		}
		for(int i =0; i<appList.size(); i++)
		{
			appList.get(i).print();
		}
		
		//Popilate data to listView
		app_listView = (ListView) findViewById(R.id.list);
		app_listView.setClickable(true);
		app_listView.setCacheColorHint(0);
		app_listView.setDivider(null);
		app_listView.setDrawSelectorOnTop(false);
		
		AppAdapter appAdapter = new AppAdapter(AppInfoDisplay.this, appList);
		
		app_listView.setDividerHeight(5);
		if(app_listView !=null)
		{
			app_listView.setAdapter(appAdapter);
		}
		
	}
	
	public class AppAdapter extends BaseAdapter
	{
		Context context;
		ArrayList<AppInfo> dataList = new ArrayList<AppInfo>();
		
		public AppAdapter(Context context, ArrayList<AppInfo> inputDataList)
		{
			this.context = context;
			dataList.clear();
			for(int i=0; i<inputDataList.size(); i++)
			{
				dataList.add(inputDataList.get(i));
			}
		}
		
		@Override
		public int getCount() {
			//TODO Auto-generated method stub
			return dataList.size();
		}
		
		@Override
		public Object getItem(int position) {
			//TODO Auto-generated method stub
			return dataList.get(position);
		}
		
		@Override
		public long getItemId(int position) {
			//TODO Auto-generated method stub
			return position;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View v =convertView;
			final AppInfo appUnit = dataList.get(position);
			if(v==null)
			{
				LayoutInflater vi= (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v=vi.inflate(R.layout.appinfo_list_item, null);
				v.setClickable(true);
			}
			appName = (TextView) v.findViewById(R.id.appinfo_name);
			appIcon = (ImageView) v.findViewById(R.id.appinfo_icon);
			
			if(appName!=null)
			{
				appName.setText(appUnit.appName + "V"+ appUnit.versionName +","+ appUnit.versionCode);
			}
			if(appIcon !=null)
			{
				appIcon.setImageDrawable(appUnit.appIcon);
			}
			return v;
		}
	}
	
	/**  
	  * ��ѯ�ֻ��ڷ�ϵͳӦ��  
	  * @param context  
	  * @return  
	  */    
	 public static List<PackageInfo> getAllAppsNoSystem(Context context) {    
	    List<PackageInfo> apps = new ArrayList<PackageInfo>();    
	    PackageManager pManager = context.getPackageManager();    
	    //��ȡ�ֻ�������Ӧ��    
	    List<PackageInfo> paklist = pManager.getInstalledPackages(0);    
	    for (int i = 0; i < paklist.size(); i++) {    
	        PackageInfo pak = (PackageInfo) paklist.get(i);    
	        //�ж��Ƿ�Ϊ��ϵͳԤװ��Ӧ�ó���    
	        if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) <= 0) {    
	            // customs applications    
	            apps.add(pak);    
	        }    
	    }    
	    return apps;    
	 }    
	 
	  
	 /**  
	  * ��ѯ�ֻ�������֧�ַ����Ӧ��  
	  * @param context  
	  * @return  
	  */    
	 public static List<ResolveInfo> getShareApps(Context context){    
	    List<ResolveInfo> mApps = new ArrayList<ResolveInfo>();      
	    Intent intent=new Intent(Intent.ACTION_SEND,null);      
	    intent.addCategory(Intent.CATEGORY_DEFAULT);      
	    intent.setType("text/plain");      
	    PackageManager pManager = context.getPackageManager();    
	    mApps = pManager.queryIntentActivities(intent,PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);      
	          
	    return mApps;      
	 } 
	 
	 /* ��ע�� 
	    ͨ�� PackageInfo  ��ȡ������Ϣ������
	  ������ȡ������packageInfo.packageName
	  icon��ȡ��ȡ������packageManager.getApplicationIcon(applicationInfo)
	  Ӧ�����ƻ�ȡ������packageManager.getApplicationLabel(applicationInfo)
	  ʹ��Ȩ�޻�ȡ������packageManager.getPackageInfo(packageName,PackageManager.GET_PERMISSIONS).requestedPermissions
	    ͨ�� ResolveInfo ��ȡ������Ϣ������
	  ������ȡ������resolve.activityInfo.packageName
	  icon��ȡ��ȡ������resolve.loadIcon(packageManager)
	  Ӧ�����ƻ�ȡ������resolve.loadLabel(packageManager).toString()
	  */
}