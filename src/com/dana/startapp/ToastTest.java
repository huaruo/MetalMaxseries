package com.dana.startapp;

import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

public class ToastTest extends Activity{
	//Debug
	private static final String TAG = "ToastTest";
	
	private Button btnST, btnSTWCP, btnSTWI, btnCT, btnRTFOT;
	
	Handler handler = new Handler();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.toast);
		
		btnST = (Button) findViewById(R.id.btnSimpleToast);
		btnSTWCP = (Button) findViewById(R.id.btnSimpleToastWithCustomPosition);
		btnSTWI = (Button) findViewById(R.id.btnSimpleToastWithImage);
		btnCT = (Button) findViewById(R.id.btnCustomToast);
		btnRTFOT = (Button) findViewById(R.id.btnRunToastFromOtherThread);
		
		btnST.setOnClickListener(listener);
		btnSTWCP.setOnClickListener(listener);
		btnSTWI.setOnClickListener(listener);
		btnCT.setOnClickListener(listener);
		btnRTFOT.setOnClickListener(listener);
	}
	
	private OnClickListener listener = new OnClickListener(){
		
		@Override
		public void onClick(View v){
			Toast toast = null;
			switch(v.getId())
			{
			case R.id.btnSimpleToast:
				Toast.makeText(getApplicationContext(), "默认Toast样式", Toast.LENGTH_LONG).show();
				break;
			case R.id.btnSimpleToastWithCustomPosition:
				toast = Toast.makeText(getApplicationContext(), "自定义位置Toast", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				break;
			case R.id.btnSimpleToastWithImage:
				toast = Toast.makeText(getApplicationContext(), "带图片的Toast", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER,0,0);
				LinearLayout toastView = (LinearLayout) toast.getView();
				ImageView imageCodeProject = new ImageView(getApplicationContext());
				imageCodeProject.setImageResource(R.drawable.child_image);
				toastView.addView(imageCodeProject, 0);
				toast.show();
				break;
			case R.id.btnCustomToast:
				LayoutInflater inflater = getLayoutInflater();
				View layout = inflater.inflate(R.layout.toast_prompt, (ViewGroup) findViewById(R.id.llToast));
				ImageView image = (ImageView)layout.findViewById(R.id.tvImageToast);
				image.setImageResource(R.drawable.child_image);
				TextView title = (TextView) layout.findViewById(R.id.tvTitleToast);
				title.setText("Attention");
				TextView text = (TextView) layout.findViewById(R.id.tvTextToast);
				text.setText("完全自定义Toast");
				toast = new Toast(getApplicationContext());
				toast.setGravity(Gravity.RIGHT | Gravity.TOP, 12, 40);
				toast.setDuration(Toast.LENGTH_LONG);
				toast.setView(layout);
				toast.show();
				break;
			 case R.id.btnRunToastFromOtherThread:
				 new Thread(new Runnable(){
					 public void run(){
						 showToast();
					 }
				 }).start();
				 break;
				default:
					break;
			}
		}
	};
	
	private void showToast(){
		handler.post(new Runnable()
		{
			@Override
			public void run(){
				Toast.makeText(getApplicationContext(), "我来自其他线程", Toast.LENGTH_SHORT).show();
			}
		});
	}
}