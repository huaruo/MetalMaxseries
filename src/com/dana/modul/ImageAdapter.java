package com.dana.modul;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter
{
	//Debug
	private static final String TAG = "ImageAdapter";
	
	private Context mContext;
	private ImageView[] imgItems;
	private int selResId;
	
	public ImageAdapter (Context c, int[] picIds, int width, int height, int selResId)
	{
		mContext = c;
		this.selResId = selResId;
		imgItems = new ImageView[picIds.length];
		for(int i = 0; i<picIds.length; i++)
		{
			imgItems[i] = new ImageView(mContext);
			imgItems[i].setLayoutParams(new GridView.LayoutParams(width,height));//����ImageView���
			imgItems[i].setAdjustViewBounds(false);
			//imgItems[i].setScaleType(ImageView.ScaleType��CENTEFR_CROP;
			imgItems[i].setPadding(2, 2, 2, 2);
			imgItems[i].setImageResource(picIds[i]);
		}
	}
	
	public int getCount()
	{
		return imgItems.length;
	}
	
	public Object getItem(int position)
	{
		return position;
	}
	
	public long getItemId(int position)
	{
		return position;
	}
	
	/**
	 * ����ѡ�е�Ч��
	 */
	public void SetFocus(int index)
	{
		for(int i=0;i<imgItems.length; i++)
		{
			if(i!=index)
			{
				imgItems[i].setBackgroundResource(0);//�ָ�δѡ�е���ʽ
			}
		}
		imgItems[index].setBackgroundResource(selResId);//����ѡ�е���ʽ
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ImageView imgView;
		if(convertView == null)
		{
			imgView = imgItems[position];
		}
		else
		{
			imgView = (ImageView) convertView;
		}
		return imgView;
	}
}