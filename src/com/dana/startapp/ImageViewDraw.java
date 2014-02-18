/**
 * @author Huaruo.W
 * ����btn3�ĸ�ʽ,���������ť��btn3�ָ�ԭ����
 * 
 */



package com.dana.startapp;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class ImageViewDraw extends Activity
{
	private ImageView iv;
	private Button btn1, btn2, btn3, btn4;
	private Resources rs;
	private Bitmap bmp, newb;
	private  Canvas canvasTemp;
	private Typeface font;
	private Paint p;
	private String familyName;
	private Drawable drawable;
	
	@Override 
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imageviewdrawable);
		
		iv =(ImageView) findViewById(R.id.ivdr_iv);
		btn1 = (Button) findViewById(R.id.ivdr_btn1);
		btn2 = (Button) findViewById(R.id.ivdr_btn2);
		btn3 = (Button) findViewById(R.id.ivdr_btn3);
		
		rs = getResources();
		
		btn1.setOnClickListener(listener);
		btn2.setOnClickListener(listener);
		btn3.setOnClickListener(listener);
	}
	
	private OnClickListener listener = new OnClickListener()
	{
		@Override 
		public void onClick(View v)
		{
			switch(v.getId())
			{
				case R.id.ivdr_btn1:
					//��ʾ��ԴͼƬ
					bmp = BitmapFactory.decodeResource(rs, R.drawable.image);//����ԴͼƬ
					iv.setImageBitmap(bmp);
					break;
				case R.id.ivdr_btn2:
					//��ʾ���滭��ԴͼƬ
					bmp = BitmapFactory.decodeResource(rs,  R.drawable.image);//ֻ��������ֱ����bmp�ϻ�
					Bitmap newb = Bitmap.createBitmap(300, 300, Config.ARGB_8888);
					
					Canvas canvasTemp = new Canvas(newb);
					canvasTemp.drawColor(Color.TRANSPARENT);
					
					p = new Paint();
					familyName = "����";
					font = Typeface.create(familyName, Typeface.BOLD);
					p.setColor(Color.RED);
					p.setTypeface(font);
					p.setTextSize(22);
					canvasTemp.drawText("д��", 50, 50, p);
					canvasTemp.drawBitmap(bmp, 50, 50, p);
					iv.setImageBitmap(newb);
					break;
				case R.id.ivdr_btn3:
					//ֱ����Button�ϻ�ͼ
					newb = Bitmap.createBitmap(btn3.getWidth(), btn3.getHeight(), Config.ARGB_8888);
					canvasTemp = new Canvas(newb);
					canvasTemp.drawColor(Color.WHITE);
					p = new Paint();
					familyName = "����";
					font = Typeface.create(familyName, Typeface.BOLD);
					p.setColor(Color.RED);
					p.setTypeface(font);
					p.setTextSize(20);
					canvasTemp.drawText("д�ڡ�",30,30,p);
					drawable = new BitmapDrawable(newb);
					btn3.setBackgroundDrawable(drawable);
					iv.setImageBitmap(null);
					break;
				default:
					break;
			}
		}
	};
} 