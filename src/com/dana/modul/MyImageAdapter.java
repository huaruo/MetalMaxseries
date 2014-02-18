package com.dana.modul;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.dana.startapp.R;

public class MyImageAdapter extends BaseAdapter
{
	//Debug
	private static final String TAG = "myImageAdapter";
	
	private int mGalleryItemBackground;
	private Context context;
	
	public Integer[] myImageIds={R.drawable.image, R.drawable.password};
	
	public MyImageAdapter(Context context)
	{
		this.context = context;
		TypedArray typed_array = context.obtainStyledAttributes(R.styleable.Gallery);
		
		/*取得Gallery属性的Index id*/
		mGalleryItemBackground = typed_array.getResourceId(R.styleable.Gallery_android_galleryItemBackground, 0);
		/*让对象的styleable属性能够反复使用*/
		 typed_array.recycle();
	}
	
	/*返回图片数目*/
	@Override
	public int getCount()
	{
		return myImageIds.length;
	}
	
	/*返回图像的数组id*/
	 @Override
	 public Object getItem(int position)
	 {
		 return position;
	 }
	 
	 @Override
	 public long getItemId(int position)
	 {
		 return position;
	 }
	 
	 /*返回View对象*/
	 @Override
	 public View getView(int position, View convertView, ViewGroup parent)
	 {
		 //产生ImageView对象
		 ImageView imgView = new ImageView(context);
		 //设置图片给imageView的对象
		 imgView.setImageResource(myImageIds[position]);
		 //重新设置图片的宽高
		 imgView.setScaleType(ImageView.ScaleType.FIT_XY);
		 //重新设置Layout的宽高
		 imgView.setLayoutParams(new Gallery.LayoutParams(128,128));
		 //设置Gallery背景图
		 imgView.setBackgroundResource(mGalleryItemBackground);
		 //返回imageView 对象
		 return imgView;
	 }
	 
}