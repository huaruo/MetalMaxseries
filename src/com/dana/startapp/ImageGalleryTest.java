package com.dana.startapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import com.dana.modul.MyImageAdapter;

public class ImageGalleryTest extends Activity
{
	//Debug
	private static final String TAG = "ImageGalleryTest";
	
	private Gallery gallery;
	private ImageView imgview;
	private MyImageAdapter imgadapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imgviewgallery);
		
		imgadapter = new MyImageAdapter(this);
		
		gallery = (Gallery) findViewById(R.id.ivgallery_gypreview);
		imgview = (ImageView) findViewById(R.id.ivgallery_ivphoto);
		gallery.setAdapter(imgadapter);
		gallery.setOnItemClickListener(itemClickListener);
	}
	
	private OnItemClickListener itemClickListener = new OnItemClickListener()
	{
		@Override
		public void onItemClick (AdapterView<?> parent, View v, int position, long id)
		{
			Toast.makeText(ImageGalleryTest.this, "’‚ «Õº∆¨: " + (position +1) + "∫≈", Toast.LENGTH_SHORT).show();
			imgview.setBackgroundResource(imgadapter.myImageIds[position]);
		}
	};
}