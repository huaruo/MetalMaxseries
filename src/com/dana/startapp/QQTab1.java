package com.dana.startapp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class QQTab1 extends Activity
{
	//Debug
	private String QQTab1 ="QQTab1";
	
	private ImageView tabiv;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qqtabbody);
		
		tabiv = (ImageView) findViewById(R.id.qqtabbody_iv1);
		tabiv.setVisibility(View.VISIBLE);
	}
	
	public boolean onCreateOptionMenu(Menu menu)
	{
		super.onCreateOptionsMenu(menu);
		
		//This is our one standard application action -- inserting a
		//new note into the list.
		menu.add(0,0,0,"1").setShortcut('3', 'a')
		.setIcon(android.R.drawable.ic_menu_add);
		
		//Generate any additional actions that can be performed on the
		//overall list. In a normal install,  there are no additional 
		//actions found here, but this allows other applications to extend
		//our menu with their own actions.
		Intent intent = new Intent(null, getIntent().getData());
		intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
		menu.addIntentOptions(Menu.CATEGORY_ALTERNATIVE, 0, 0, new ComponentName(this, QQTab.class), null, intent, 0, null);
		return true;
	}
}