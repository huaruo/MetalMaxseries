package  com.dana.startapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class HttpDemo extends ListActivity{
	//Debug
	private static final String TAG = "HttpDemo";
	
	private ListView lv;
	
	private List<HashMap<String,  Object>> myData;
	
	private String[] mStrings = {
			"Http GET/POST 传递参数",
			"WebView.loadUrl 浏览网页",
			"手机文件上传至服务器",
			"远程下载安装APK",
			"下载显示图片文件"};
	
	private Intent intent;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//使用R.layout.list_view报错"Your content must have a ListView whose id attribute is 'android.R.id.list'"
//		setContentView(R.layout.list_view);
		setContentView(R.layout.http_list);
		
		lv = (ListView) findViewById(R.id.list);
		myData = getData();
		mAdapter adapter = new mAdapter(this);
		setListAdapter(adapter);
	}
	
	private List<HashMap<String,Object>> getData(){
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
		for (int i=0; i<mStrings.length; i++)
		{
			HashMap<String,Object> map = new HashMap<String, Object>();
			map.put("title", mStrings[i]);
			list.add(map);
		}
		
		return list;
	}
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		switch(position)
		{
			case 0:
				intent = new Intent(HttpDemo.this, HttpGetPost.class);
				startActivity(intent);
				break;
			case 1:
				intent = new Intent(HttpDemo.this, Webview.class);
				startActivity(intent);
				break;
			case 2:
				intent = new Intent(HttpDemo.this, Upload.class);
				startActivity(intent);
				break;
			case 3:
				intent = new Intent(HttpDemo.this, Downloadapk.class);
				startActivity(intent);
				break;
			case 4:
				intent = new Intent(HttpDemo.this, GetImage.class);
				startActivity(intent);
				break;
			default:
				break;
		}
	}
	
	public final class ViewHolder{
		public ImageView img;
		public TextView title;
		public TextView info;
		public Button viewBtn;
	}
	
	public class mAdapter extends BaseAdapter{
		private LayoutInflater mInflater;
		
		public mAdapter(Context c)
		{
			this.mInflater = LayoutInflater.from(c);
		}
		public int getCount() {
			//TODO Auto-generated method stub
			return myData.size();
		}
		
		public Object getItem(int arg0) {
			//TODO Auto-generated method stub
			return null;
		}
		
		public long getItemId(int arg0)
		{
			//TODO Auto-generated method stub
			return 0;
		}
		
		public View getView(int position, View convertView, ViewGroup parent)
		{
			ViewHolder holder = null;
			if(convertView == null)
			{
				holder = new ViewHolder();
				
				convertView = mInflater.inflate(R.layout.http_listitem, null);
				holder.title = (TextView) convertView.findViewById(R.id.http_title);
				convertView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.title.setText((String) myData.get(position).get("title"));
			
			return convertView;
		}
	}
}