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
		
		/*ȡ��Gallery���Ե�Index id*/
		mGalleryItemBackground = typed_array.getResourceId(R.styleable.Gallery_android_galleryItemBackground, 0);
		/*�ö����styleable�����ܹ�����ʹ��*/
		 typed_array.recycle();
	}
	
	/*����ͼƬ��Ŀ*/
	@Override
	public int getCount()
	{
		return myImageIds.length;
	}
	
	/*����ͼ�������id*/
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
	 
	 /*����View����*/
	 @Override
	 public View getView(int position, View convertView, ViewGroup parent)
	 {
		 //����ImageView����
		 ImageView imgView = new ImageView(context);
		 //����ͼƬ��imageView�Ķ���
		 imgView.setImageResource(myImageIds[position]);
		 //��������ͼƬ�Ŀ��
		 imgView.setScaleType(ImageView.ScaleType.FIT_XY);
		 //��������Layout�Ŀ��
		 imgView.setLayoutParams(new Gallery.LayoutParams(128,128));
		 //����Gallery����ͼ
		 imgView.setBackgroundResource(mGalleryItemBackground);
		 //����imageView ����
		 return imgView;
	 }
	 
}