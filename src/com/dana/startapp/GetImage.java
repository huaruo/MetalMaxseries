package com.dana.startapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class GetImage extends Activity implements OnClickListener {
	private EditText edittext = null;
	private Button button = null;
	private ImageView imageview = null;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.downloadimage);
        edittext = (EditText) findViewById(R.id.path);
        button = (Button) findViewById(R.id.getimage);
        button.setOnClickListener( this);
        imageview  = (ImageView) findViewById(R.id.imageview);
    }
	@Override
	public void onClick(View button) {
		// TODO Auto-generated method stub
		String urlpath = edittext.getText().toString();
		try {
			byte[] data = GetData.getStream(urlpath);
			//从字节数组流里创建一个Bitmap对象
			Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
			imageview.setImageBitmap(bm);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}