package com.dana.startapp; 
import java.util.ArrayList; 
import java.util.List; 

import com.dana.modul.MySpinnerAdapter;
 
import android.app.Activity; 
import android.os.Bundle; 
import android.view.MotionEvent; 
import android.view.View; 
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener; 
import android.view.animation.Animation; 
import android.view.animation.AnimationUtils; 
import android.widget.AdapterView; 
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter; 
import android.widget.Spinner;
import android.widget.TextView; 
 
public class SpinnerTest extends Activity { 
	//Debug
	private static final String TAG = "SpinnerTest";	
	
    /** Called when the activity is first created. */ 
    private List<String> list = new ArrayList<String>(); 
    private TextView myTextView; 
    private Spinner mySpinner, customSpinner; 
    private ArrayAdapter<String> adapter; 
    private Animation myAnimation; 
    
    private static final String[] item={"北京","上海"};
    
    @Override 
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.spinner); 
        
        myTextView = (TextView)findViewById(R.id.TextView_Show); 
        mySpinner = (Spinner)findViewById(R.id.spinner_City); 
        customSpinner = (Spinner) findViewById(R.id.spinner_custom);
/*        //第一步：添加一个下拉列表项的list，这里添加的项就是下拉列表的菜单项 
        list.add("北京"); 
        list.add("上海"); 
        list.add("深圳"); 
        list.add("南京"); 
        list.add("重庆"); 
        //第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。 
 //       adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list); 
  */
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, item); 
        //第三步：为适配器设置下拉列表下拉时的菜单样式。 
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
        //第四步：将适配器添加到下拉列表上 
        mySpinner.setAdapter(adapter); 
        //第五步：为下拉列表设置选中菜单的事件响应， 
        mySpinner.setOnItemSelectedListener(itemSelectedListener);
        /*下拉菜单弹出的内容选项触屏事件处理*/ 
        mySpinner.setOnTouchListener(touchListener);
        /*下拉菜单弹出的内容选项焦点改变事件处理*/ 
        mySpinner.setOnFocusChangeListener(focusChangeListener); 
        
        MySpinnerAdapter adapterCustom = new MySpinnerAdapter(this, android.R.layout.simple_spinner_item, item);
        customSpinner.setAdapter(adapterCustom);
    } 
    private OnItemSelectedListener itemSelectedListener = new OnItemSelectedListener(){ 
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) { 
        	if(parent == mySpinner)
        	{
        		// TODO Auto-generated method stub 
                /* 将所选mySpinner 的值带入myTextView 中*/ 
                myTextView.setText("您选择的是:"+ adapter.getItem(position)); 
                /* 将mySpinner 显示*/ 
                parent.setVisibility(View.VISIBLE); 
        	}            
        } 
        public void onNothingSelected(AdapterView<?> parent) { 
        	if(parent == mySpinner)
        	{
        		// TODO Auto-generated method stub 
                myTextView.setText("您选择的是:"); 
                parent.setVisibility(View.VISIBLE);
        	} 
        } 
    }; 
    
    private OnTouchListener touchListener = new OnTouchListener(){ 
        public boolean onTouch(View v, MotionEvent event) { 
            // TODO Auto-generated method stub 
            /* 将mySpinner 隐藏，不隐藏也可以，看自己爱好*/ 
//             v.setVisibility(View.INVISIBLE); 
            return false; 
        } 
    };
    
    private OnFocusChangeListener focusChangeListener = new OnFocusChangeListener(){ 
        public void onFocusChange(View v, boolean hasFocus) { 
        // TODO Auto-generated method stub 
            v.setVisibility(View.VISIBLE); 
        } 
        };
} 

