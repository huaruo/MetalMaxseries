package com.dana.startapp;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.app.Activity;

public class ShowPopupWindow extends Activity {
	//Debug
	private static final String TAG = "PopupWindow";
	
	View view;
	PopupWindow pop;
	Button btnShowAsDrawDown;
	Button btnShowAsDrawDown1;
	Button btnShowAtLocation;
	Button btnClose;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//TODO Auto-generatedmethod stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup_activity);
		
		btnShowAsDrawDown = (Button) findViewById(R.id.btnShowAsDrawDown);
		btnShowAsDrawDown1= (Button) findViewById(R.id.btnShowAsDrawDown1);
		btnShowAtLocation = (Button) findViewById(R.id.btnShowAt);
		
		btnShowAsDrawDown.setOnClickListener(listener);
		btnShowAsDrawDown1.setOnClickListener(listener);
		btnShowAtLocation.setOnClickListener(listener);
		
		initPopupWindow();
	}
	
	private OnClickListener listener = new OnClickListener()
	{
		public void onClick(View v){
			//TODO Auto-generated method stub
			switch(v.getId()){
				case R.id.btnShowAsDrawDown:
					if(pop.isShowing()){
						pop.dismiss();
					}
					else
					{
						pop.showAsDropDown(v);
					}
					break;
				case R.id.btnShowAsDrawDown1:
					if(pop.isShowing()){
						pop.dismiss();
					}
					else
					{
						pop.showAsDropDown(v, 0, -160);
					}
					break;
				case R.id.btn_pop:
					pop.dismiss();
					break;
				default:
					if(pop.isShowing())
					{
						pop.dismiss();
					}
					else
					{
						pop.showAtLocation(findViewById(R.id.pop_activity), Gravity.CENTER_HORIZONTAL, 0, 0);
					}
					break;
			}
		}
	};
	
	
	private void initPopupWindow()
	{
		view = this.getLayoutInflater().inflate(R.layout.popup_window, null);
		pop = new PopupWindow(view, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		Log.i(TAG, "init PopupWindow");
		pop.setOutsideTouchable(true);
		
		btnClose = (Button) view.findViewById(R.id.btn_pop);
		btnClose.setOnClickListener(listener);
		
		//点击窗口，窗口消失。
		view.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				//TODO Auto-generated method stub
				pop.dismiss();
			}
		});
	}
}