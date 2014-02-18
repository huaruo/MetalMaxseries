package com.dana.startapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;


public class ViewAnimatorTest extends Activity {
	//Debug
	private static final String TAG = "ViewAnimator";
	
	private ViewFlipper mViewFlipper;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewanimator);
		
		Button buttonNext1 = (Button) findViewById(R.id.Button_next1);   
		mViewFlipper = (ViewFlipper) findViewById(R.id.details);   
		buttonNext1.setOnClickListener(new View.OnClickListener() {    
			public void onClick(View view) {    
					//��layout�ж�������ԣ�Ҳ�����ڴ�����ָ��    
		//             mViewFlipper.setInAnimation(getApplicationContext(), R.anim.push_left_in);    
		//             mViewFlipper.setOutAnimation(getApplicationContext(), R.anim.push_left_out);    
		//             mViewFlipper.setPersistentDrawingCache(ViewGroup.PERSISTENT_ALL_CACHES);    
		//             mViewFlipper.setFlipInterval(1000);    
		                mViewFlipper.showNext();   
		                //��������ĺ�������ѭ����ʾmViewFlipper�ڵ�����View��    
		//             mViewFlipper.startFlipping();    
		        }   
		        });   
		    
		        Button buttonNext2 = (Button) findViewById(R.id.Button_next2);   
		        buttonNext2.setOnClickListener(new View.OnClickListener() {    
		            public void onClick(View view) {    
		                mViewFlipper.showNext();   
		        }   
		    
		        });      
		        Button buttonNext3 = (Button) findViewById(R.id.Button_next3);   
		        buttonNext3.setOnClickListener(new View.OnClickListener() {    
		            public void onClick(View view) {    
		                mViewFlipper.showNext();   
		        }   
		    
		       });   
		    
		  }
}