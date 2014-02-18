package com.dana.startapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.app.Activity;

public class HandcentList extends Activity {
	//Debug
	private static final String TAG = "HandcentList";
	
	/** Called when the activity is first created. */
	ListView itemlist = null;
	List<Map<String, Object>> list;
	
	final String[] str={"A", "B", "C", "D", "E", "F"};
	/** Called when the activity is first created. */
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.handcentlist);
		Log.i(TAG, "++ On Created ++");
		
		itemlist = (ListView) findViewById(R.id.handcent_itemlist);
		refreshListItems();	
	}
	
	/* Ë¢ÐÂÁÐ±í */
	private void refreshListItems() {
		list = buildListForSimpleAdapter();
		SimpleAdapter notes = new SimpleAdapter(this, list, R.layout.handcentlist_item,
				new String[] {"str"}, new int[] {R.id.TextView01,});
		itemlist.setAdapter(notes);
		itemlist.setSelection(0);
	}
	
	private List<Map<String, Object>> buildListForSimpleAdapter(){
		list = new ArrayList<Map<String, Object>>(2);
		//Build a map for the attributes
		Map<String, Object> map = new HashMap<String, Object>();
		
		for (int i=0; i<str.length; i++)
		{
			map.put("str", "ºÇºÇ");
			list.add(map);
		}
		return list;
	
	}
}