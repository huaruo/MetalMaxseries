/**
 * @author Huaruo.W
 * Issue:zoomInClickListener和zoomOutClickListener不起作用， 按返回箭头退出activity会退出整个程序。
 */

package com.dana.startapp;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ZoomControls;

import com.dana.modul.ClsOscilloscope;

public class OscilloscopeTest extends Activity
{
	//Debug
	private static final String TAG = "OscilloscopeTest";
	
	private Button btnStart, btnStop;
	private SurfaceView sfv;
	private ZoomControls zctlX, zctlY;
	
	ClsOscilloscope clsOscilloscope = new ClsOscilloscope();
	
	private static final int frequency = 8000; //分辨率
	private static final int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
	private static final int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
	private static final int xMax = 16; //X轴缩小比例最大值，X轴数据 量巨大。容易产生刷新延时
	private static final int xMin = 8; //X轴缩小比例最小值
	private static final int yMax = 10; //Y轴缩小比例最大值
	private static final int yMin = 1; //Y轴缩小比例最小值
	
	int recBufSize; //录音最小buffer大小
	
	private AudioRecord audioRecord; 
	Paint mPaint;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.oscilloscope);
		
		//录音组件
		recBufSize = AudioRecord.getMinBufferSize(frequency, channelConfiguration, audioEncoding);
		audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, frequency, channelConfiguration, audioEncoding, recBufSize);
		
		btnStart = (Button) findViewById(R.id.osc_start);
		btnStop = (Button) findViewById(R.id.osc_stop);
		btnStart.setOnClickListener(listener);
		btnStop.setOnClickListener(listener);
		
		//画板和画笔
		sfv = (SurfaceView) findViewById(R.id.osc_sfv);
		sfv.setOnTouchListener(touchListener);
		mPaint = new Paint();
		mPaint.setColor(Color.GREEN);//画笔为绿色
		mPaint.setStrokeWidth(1);//设置画笔粗细 
		
		//示波器类库
		clsOscilloscope.initOscilloscope(xMax/2, yMax/2, sfv.getHeight()/2);
		
		//缩放控件， X轴的数据缩小的比率高些
		zctlX = (ZoomControls) findViewById(R.id.osc_zctlX);
//		zctlX.setOnZoomInClickListener(zoomInClickListener);
//		zctlX.setOnZoomOutClickListener(zoomOutClickListener);
		zctlX.setOnZoomInClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(clsOscilloscope.rateX>xMin)
					clsOscilloscope.rateX--;
				setTitle("X轴缩小"+String.valueOf(clsOscilloscope.rateX)+"倍"
						+","+"Y轴缩小"+String.valueOf(clsOscilloscope.rateY)+"倍");
				Log.i(TAG, "X轴缩小" + String. valueOf(clsOscilloscope.rateX)+ "倍, Y轴缩小" + String.valueOf(clsOscilloscope.rateY) + "倍");
			}
		});
		zctlX.setOnZoomOutClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(clsOscilloscope.rateX<xMax)
					clsOscilloscope.rateX++;	
				setTitle("X轴缩小"+String.valueOf(clsOscilloscope.rateX)+"倍"
						+","+"Y轴缩小"+String.valueOf(clsOscilloscope.rateY)+"倍");
				Log.i(TAG, "X轴缩小" + String. valueOf(clsOscilloscope.rateX)+ "倍, Y轴缩小" + String.valueOf(clsOscilloscope.rateY) + "倍");
			}
		});
		zctlY = (ZoomControls) findViewById(R.id.osc_zctlY);
//		zctlY.setOnZoomInClickListener(zoomInClickListener);
//		zctlY.setOnZoomOutClickListener(zoomOutClickListener);
		zctlY.setOnZoomInClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(clsOscilloscope.rateY>yMin)
					clsOscilloscope.rateY--;
				setTitle("X轴缩小"+String.valueOf(clsOscilloscope.rateX)+"倍"
						+","+"Y轴缩小"+String.valueOf(clsOscilloscope.rateY)+"倍");
				Log.i(TAG, "X轴缩小" + String. valueOf(clsOscilloscope.rateX)+ "倍, Y轴缩小" + String.valueOf(clsOscilloscope.rateY) + "倍");
			}
		});
		
		zctlY.setOnZoomOutClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(clsOscilloscope.rateY<yMax)
					clsOscilloscope.rateY++;	
				setTitle("X轴缩小"+String.valueOf(clsOscilloscope.rateX)+"倍"
						+","+"Y轴缩小"+String.valueOf(clsOscilloscope.rateY)+"倍");
				Log.i(TAG, "X轴缩小" + String. valueOf(clsOscilloscope.rateX)+ "倍, Y轴缩小" + String.valueOf(clsOscilloscope.rateY) + "倍");
			}
		});
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		android.os.Process.killProcess(android.os.Process.myPid());
	}
	
	private OnClickListener listener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			switch(v.getId())
			{
				case R.id.osc_start:
					clsOscilloscope.baseLine = sfv.getHeight()/2;
					clsOscilloscope.Start(audioRecord, recBufSize, sfv, mPaint);
					break;
				case R.id.osc_stop:
					clsOscilloscope.Stop();
					break;
				default:
					break;
			}
		}
	};
	
	private OnTouchListener touchListener = new OnTouchListener()
	{
		@Override
		public boolean onTouch(View v, MotionEvent event)
		{
			clsOscilloscope.baseLine = (int)event.getY();
			return true;
		}
	};
	
	private View.OnClickListener zoomInClickListener = new View.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			switch(v.getId())
			{
				case R.id.osc_zctlX:
					if(clsOscilloscope.rateX > xMin)
						clsOscilloscope.rateX--;
					setTitle("X轴缩小" + String. valueOf(clsOscilloscope.rateX)+ "倍, Y轴缩小" + String.valueOf(clsOscilloscope.rateY) + "倍");
					Log.i(TAG, "X轴缩小" + String. valueOf(clsOscilloscope.rateX)+ "倍, Y轴缩小" + String.valueOf(clsOscilloscope.rateY) + "倍");
					break;
				case R.id.osc_zctlY:
					if(clsOscilloscope.rateY > yMin)
						clsOscilloscope.rateY--;
					setTitle("X轴缩小" + String.valueOf(clsOscilloscope.rateX)+ "倍, Y轴缩小" + String.valueOf(clsOscilloscope.rateY) + "倍");
					Log.i(TAG, "X轴缩小" + String. valueOf(clsOscilloscope.rateX)+ "倍, Y轴缩小" + String.valueOf(clsOscilloscope.rateY) + "倍");
					break;
				default:
					break;
			}
		}
	};
	
	private View.OnClickListener zoomOutClickListener = new View.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			switch(v.getId())
			{
				case R.id.osc_zctlX:
					if(clsOscilloscope.rateX < xMax)
						clsOscilloscope.rateX++;
					setTitle("X轴缩小" + String. valueOf(clsOscilloscope.rateX)+ "倍, Y轴缩小" + String.valueOf(clsOscilloscope.rateY) + "倍");
					Log.i(TAG, "X轴缩小" + String. valueOf(clsOscilloscope.rateX)+ "倍, Y轴缩小" + String.valueOf(clsOscilloscope.rateY) + "倍");
					break;
				case R.id.osc_zctlY:
					if(clsOscilloscope.rateY < yMax)
						clsOscilloscope.rateY++;
					setTitle("X轴缩小" + String. valueOf(clsOscilloscope.rateX)+ "倍, Y轴缩小" + String.valueOf(clsOscilloscope.rateY) + "倍");
					Log.i(TAG, "X轴缩小" + String. valueOf(clsOscilloscope.rateX)+ "倍, Y轴缩小" + String.valueOf(clsOscilloscope.rateY) + "倍");
					break;
				default:
					break;
			}
		}
	};
	
}