package com.dana.startapp;


/*必需引用apache.http相关类别来建立HTTP联机*/


/*必需引用java.io 与java.util相关类来读写文件*/

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HttpGetPost extends Activity
{
	//Debug
	private static final String TAG = "HttpGetPost";
	
	private Button httpgp_btn1, httpgp_btn2;
	private TextView httpgp_tv1;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.httpgetpost);
		
		httpgp_btn1 = (Button) findViewById(R.id.http_myButton1);
		httpgp_btn2 = (Button) findViewById(R.id.http_myButton2);
		httpgp_tv1 = (TextView) findViewById(R.id.http_myTextView1);
		
		httpgp_btn1.setOnClickListener(listener);
		httpgp_btn2.setOnClickListener(listener);
	}
	
	private OnClickListener listener = new OnClickListener(){
		/*重写onClick事件*/
		@Override
		public void onClick(View v)
		{
			switch(v.getId())
			{
			case R.id.http_myButton1:
				/*声明网址字符串*/
		        String uriAPI1 = "http://www.dubblogs.cc:8751/Android/Test/API/Post/index.php";
		        /*建立Http Post联机*/
		        HttpPost httpPostRequest = new HttpPost(uriAPI1);
		        /*
		         * Post运作传送变量必须用NameValuePair[]数组储存
		         */
		        List <NameValuePair> params = new ArrayList<NameValuePair>();
		        params.add(new BasicNameValuePair("str", "I am Post String"));
		        try
		        {
		        	/*发出HTTP request*/
		        	httpPostRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		        	/*取得HTTP response*/
		        	HttpResponse httpPostResponse = new DefaultHttpClient().execute(httpPostRequest);
		        	/*若状态码为200 ok*/
		        	if (httpPostResponse.getStatusLine().getStatusCode()== 200)
		        	{
		        		/*取出响应字符串*/
		        		String strResult = EntityUtils.toString(httpPostResponse.getEntity());
		        		httpgp_tv1.setText(strResult);
		        	}
		        	else
		        	{
		        		httpgp_tv1.setText("Error Response: " + httpPostResponse.getStatusLine().toString());
		        	}
		        }
		        catch(ClientProtocolException e)
		        {
		        	httpgp_tv1.setText(e.getMessage().toString());
		        	e.printStackTrace();
		        }
		        catch(IOException e)
		        {
		        	httpgp_tv1.setText(e.getMessage().toString());
		        	e.printStackTrace();
		        }
		        catch(Exception e)
		        {
		        	httpgp_tv1.setText(e.getMessage().toString());
		        	e.printStackTrace();
		        }
		        break;
			case R.id.http_myButton2:
				/*声明网址字符串*/
		        String uriAPI2 = "http://www.dubblogs.cc:8751/Android/Test/API/Get/index.php?str=I+am+Get+String"; 
		        /*建立HTTP Get联机*/
		        HttpGet httpGetRequest = new HttpGet(uriAPI2); 
		        try 
		        { 
		          /*发出HTTP request*/
		          HttpResponse httpGetResponse = new DefaultHttpClient().execute(httpGetRequest); 
		          /*若状态码为200 ok*/
		          if(httpGetResponse.getStatusLine().getStatusCode() == 200)  
		          { 
		            /*取出响应字符串*/
		            String strResult = EntityUtils.toString(httpGetResponse.getEntity());
		            /*删除多余字符*/
		            strResult = eregi_replace("(\r\n|\r|\n|\n\r)","",strResult);
		            httpgp_tv1.setText(strResult); 
		          } 
		          else 
		          { 
		        	  httpgp_tv1.setText("Error Response: "+httpGetResponse.getStatusLine().toString()); 
		          } 
		        } 
		        catch (ClientProtocolException e) 
		        {  
		        	httpgp_tv1.setText(e.getMessage().toString()); 
		        	e.printStackTrace(); 
		        } 
		        catch (IOException e) 
		        {  
		        	httpgp_tv1.setText(e.getMessage().toString()); 
		        	e.printStackTrace(); 
		        } 
		        catch (Exception e) 
		        {  
		        	httpgp_tv1.setText(e.getMessage().toString()); 
		        	e.printStackTrace();  
		        } 
		        break;
		    default:
		    	break;
		        
			}
		}
	};
	
	/* 自定义字符串取代函数 */
    public String eregi_replace(String strFrom, String strTo, String strTarget)
    {
      String strPattern = "(?i)"+strFrom;
      Pattern p = Pattern.compile(strPattern);
      Matcher m = p.matcher(strTarget);
      if(m.find())
      {
        return strTarget.replaceAll(strFrom, strTo);
      }
      else
      {
        return strTarget;
      }
    }
}