package com.dana.startapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class Webview extends Activity
{
	private static final String TAG = "HIPPO_DEBUG";
	private ImageButton mImageButton1;
	private EditText mEditText1;
	private WebView mWebView1;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		
		mImageButton1 = (ImageButton)findViewById(R.id.webview_IB1);
		mEditText1 = (EditText)findViewById(R.id.webview_ET1);
		mEditText1.setText("http://www.baidu.com");
		mWebView1 = (WebView) findViewById(R.id.webview_WV1);
		
		mWebView1.setWebViewClient(new WebViewClient()
		{
			/*�Ӻ�ѧϰ
		      @Override
		      public void onPageFinished(WebView view, String url)
		      {
		        // TODO Auto-generated method stub
		        super.onPageFinished(view, url);
		      }
		      */
		});
		
		/*�����¼�ͷʱ���¼�*/
		mImageButton1.setOnClickListener(new ImageButton.OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				//TODO Auto-generated method stub
				mImageButton1.setImageResource(R.drawable.go_2);
				/*�趨ץȡEditText���������*/
				String strURL =  mEditText1.getText().toString();
				/*?WebView������ʾ��ҳ����*/
				mWebView1.loadUrl(strURL);
				Log.i(TAG, "loadUrl");
				Toast.makeText(Webview.this, getString(R.string.load)+strURL , Toast.LENGTH_LONG).show();
			}
		});
	}
}