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
    
    private static final String[] item={"����","�Ϻ�"};
    
    @Override 
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.spinner); 
        
        myTextView = (TextView)findViewById(R.id.TextView_Show); 
        mySpinner = (Spinner)findViewById(R.id.spinner_City); 
        customSpinner = (Spinner) findViewById(R.id.spinner_custom);
/*        //��һ�������һ�������б����list��������ӵ�����������б�Ĳ˵��� 
        list.add("����"); 
        list.add("�Ϻ�"); 
        list.add("����"); 
        list.add("�Ͼ�"); 
        list.add("����"); 
        //�ڶ�����Ϊ�����б���һ����������������õ���ǰ�涨���list�� 
 //       adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list); 
  */
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, item); 
        //��������Ϊ���������������б�����ʱ�Ĳ˵���ʽ�� 
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
        //���Ĳ�������������ӵ������б��� 
        mySpinner.setAdapter(adapter); 
        //���岽��Ϊ�����б�����ѡ�в˵����¼���Ӧ�� 
        mySpinner.setOnItemSelectedListener(itemSelectedListener);
        /*�����˵�����������ѡ����¼�����*/ 
        mySpinner.setOnTouchListener(touchListener);
        /*�����˵�����������ѡ���ı��¼�����*/ 
        mySpinner.setOnFocusChangeListener(focusChangeListener); 
        
        MySpinnerAdapter adapterCustom = new MySpinnerAdapter(this, android.R.layout.simple_spinner_item, item);
        customSpinner.setAdapter(adapterCustom);
    } 
    private OnItemSelectedListener itemSelectedListener = new OnItemSelectedListener(){ 
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) { 
        	if(parent == mySpinner)
        	{
        		// TODO Auto-generated method stub 
                /* ����ѡmySpinner ��ֵ����myTextView ��*/ 
                myTextView.setText("��ѡ�����:"+ adapter.getItem(position)); 
                /* ��mySpinner ��ʾ*/ 
                parent.setVisibility(View.VISIBLE); 
        	}            
        } 
        public void onNothingSelected(AdapterView<?> parent) { 
        	if(parent == mySpinner)
        	{
        		// TODO Auto-generated method stub 
                myTextView.setText("��ѡ�����:"); 
                parent.setVisibility(View.VISIBLE);
        	} 
        } 
    }; 
    
    private OnTouchListener touchListener = new OnTouchListener(){ 
        public boolean onTouch(View v, MotionEvent event) { 
            // TODO Auto-generated method stub 
            /* ��mySpinner ���أ�������Ҳ���ԣ����Լ�����*/ 
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

