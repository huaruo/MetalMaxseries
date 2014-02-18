package com.dana.startapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

public class AdjustBitmap extends Activity {
	 @Override
	 public void onCreate(Bundle icicle) {
		 /*
		  * 在实际应用中，当一个Activity结束前，如果需要保存状态，就在onsaveInsanceState中，
		  * 将状态数据以key-value的形式放入到saveInsanceState中。这样，当一个Activity被创建时，
		  * 就能从onCreate的参数saveInsanceState中获得状态数据。
		  * 
		  * icicle是通过savedInstanceState.getBundle(ICICLE_KEY);获得的 
		  */
	 super.onCreate(icicle);
	 LinearLayout linLayout = new LinearLayout(this);
	 LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
			 ViewGroup.LayoutParams.MATCH_PARENT,
			 ViewGroup.LayoutParams.MATCH_PARENT);
	 linLayout.setOrientation(LinearLayout.VERTICAL);
	 linLayout.setPadding(50,0,50,0);
	 param.setMargins(5,0,5,0);
	 linLayout.setLayoutParams(param);

	// load the origial BitMap (500 x 500 px)
	 Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(),
	 R.drawable.child_image);

	 int width = bitmapOrg.getWidth();
	 int height = bitmapOrg.getHeight();
	 int newWidth = 400;
	 int newHeight = 400;

	// calculate the scale - in this case = 0.4f
	 float scaleWidth = ((float) newWidth) / width;
	 float scaleHeight = ((float) newHeight) / height;

	// createa matrix for the manipulation
	 Matrix matrix = new Matrix();
	 // resize the bit map
	 matrix.postScale(scaleWidth, scaleHeight);
	 // rotate the Bitmap
	 matrix.postRotate(45);

	// recreate the new Bitmap
	 Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,
	 width, height, matrix, true);

	// make a Drawable from Bitmap to allow to set the BitMap
	 // to the ImageView, ImageButton or what ever
	 BitmapDrawable bmd = new BitmapDrawable(resizedBitmap);

	ImageView imageView = new ImageView(this);

	// set the Drawable on the ImageView
	 imageView.setImageDrawable(bmd);

	// center the Image
	 imageView.setScaleType(ScaleType.CENTER);

	// add ImageView to the Layout
	 linLayout.addView(imageView,
	 new LinearLayout.LayoutParams(
	 LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 1
	 )
	 );
	 /*
	  * Bitmap.createScaledBitmap(Bitmap,width,height,true);
	  * Bitmap是待修改的图片
	  * width是修改后的宽度
	  * height是修改后的高度
	  * true不详???
	  */
	 Bitmap resizedBitmap1 = Bitmap.createScaledBitmap(bitmapOrg,500,100,true);
	 BitmapDrawable bmd1 = new BitmapDrawable(resizedBitmap1);
	 ImageView imageView1 = new ImageView(this);
	 imageView1.setImageDrawable(bmd1);
	 imageView1.setScaleType(ScaleType.CENTER);
	 linLayout.addView(imageView1,
			 new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));
	 
	 //LinLayout已加载了一个child View(imageView), 需要移除，才能看到imageView1
//	 linLayout.removeView(imageView);
	// set LinearLayout as ContentView
	 setContentView(linLayout);
	 }
	 }
