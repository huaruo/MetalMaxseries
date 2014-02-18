/**
 * @author Huaruo.W
 * Issue:zoomInClickListener��zoomOutClickListener�������ã� �����ؼ�ͷ�˳�activity���˳���������
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
	
	private static final int frequency = 8000; //�ֱ���
	private static final int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
	private static final int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
	private static final int xMax = 16; //X����С�������ֵ��X������ ���޴����ײ���ˢ����ʱ
	private static final int xMin = 8; //X����С������Сֵ
	private static final int yMax = 10; //Y����С�������ֵ
	private static final int yMin = 1; //Y����С������Сֵ
	
	int recBufSize; //¼����Сbuffer��С
	
	private AudioRecord audioRecord; 
	Paint mPaint;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.oscilloscope);
		
		//¼�����
		recBufSize = AudioRecord.getMinBufferSize(frequency, channelConfiguration, audioEncoding);
		audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, frequency, channelConfiguration, audioEncoding, recBufSize);
		
		btnStart = (Button) findViewById(R.id.osc_start);
		btnStop = (Button) findViewById(R.id.osc_stop);
		btnStart.setOnClickListener(listener);
		btnStop.setOnClickListener(listener);
		
		//����ͻ���
		sfv = (SurfaceView) findViewById(R.id.osc_sfv);
		sfv.setOnTouchListener(touchListener);
		mPaint = new Paint();
		mPaint.setColor(Color.GREEN);//����Ϊ��ɫ
		mPaint.setStrokeWidth(1);//���û��ʴ�ϸ 
		
		//ʾ�������
		clsOscilloscope.initOscilloscope(xMax/2, yMax/2, sfv.getHeight()/2);
		
		//���ſؼ��� X���������С�ı��ʸ�Щ
		zctlX = (ZoomControls) findViewById(R.id.osc_zctlX);
//		zctlX.setOnZoomInClickListener(zoomInClickListener);
//		zctlX.setOnZoomOutClickListener(zoomOutClickListener);
		zctlX.setOnZoomInClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(clsOscilloscope.rateX>xMin)
					clsOscilloscope.rateX--;
				setTitle("X����С"+String.valueOf(clsOscilloscope.rateX)+"��"
						+","+"Y����С"+String.valueOf(clsOscilloscope.rateY)+"��");
				Log.i(TAG, "X����С" + String. valueOf(clsOscilloscope.rateX)+ "��, Y����С" + String.valueOf(clsOscilloscope.rateY) + "��");
			}
		});
		zctlX.setOnZoomOutClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(clsOscilloscope.rateX<xMax)
					clsOscilloscope.rateX++;	
				setTitle("X����С"+String.valueOf(clsOscilloscope.rateX)+"��"
						+","+"Y����С"+String.valueOf(clsOscilloscope.rateY)+"��");
				Log.i(TAG, "X����С" + String. valueOf(clsOscilloscope.rateX)+ "��, Y����С" + String.valueOf(clsOscilloscope.rateY) + "��");
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
				setTitle("X����С"+String.valueOf(clsOscilloscope.rateX)+"��"
						+","+"Y����С"+String.valueOf(clsOscilloscope.rateY)+"��");
				Log.i(TAG, "X����С" + String. valueOf(clsOscilloscope.rateX)+ "��, Y����С" + String.valueOf(clsOscilloscope.rateY) + "��");
			}
		});
		
		zctlY.setOnZoomOutClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(clsOscilloscope.rateY<yMax)
					clsOscilloscope.rateY++;	
				setTitle("X����С"+String.valueOf(clsOscilloscope.rateX)+"��"
						+","+"Y����С"+String.valueOf(clsOscilloscope.rateY)+"��");
				Log.i(TAG, "X����С" + String. valueOf(clsOscilloscope.rateX)+ "��, Y����С" + String.valueOf(clsOscilloscope.rateY) + "��");
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
					setTitle("X����С" + String. valueOf(clsOscilloscope.rateX)+ "��, Y����С" + String.valueOf(clsOscilloscope.rateY) + "��");
					Log.i(TAG, "X����С" + String. valueOf(clsOscilloscope.rateX)+ "��, Y����С" + String.valueOf(clsOscilloscope.rateY) + "��");
					break;
				case R.id.osc_zctlY:
					if(clsOscilloscope.rateY > yMin)
						clsOscilloscope.rateY--;
					setTitle("X����С" + String.valueOf(clsOscilloscope.rateX)+ "��, Y����С" + String.valueOf(clsOscilloscope.rateY) + "��");
					Log.i(TAG, "X����С" + String. valueOf(clsOscilloscope.rateX)+ "��, Y����С" + String.valueOf(clsOscilloscope.rateY) + "��");
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
					setTitle("X����С" + String. valueOf(clsOscilloscope.rateX)+ "��, Y����С" + String.valueOf(clsOscilloscope.rateY) + "��");
					Log.i(TAG, "X����С" + String. valueOf(clsOscilloscope.rateX)+ "��, Y����С" + String.valueOf(clsOscilloscope.rateY) + "��");
					break;
				case R.id.osc_zctlY:
					if(clsOscilloscope.rateY < yMax)
						clsOscilloscope.rateY++;
					setTitle("X����С" + String. valueOf(clsOscilloscope.rateX)+ "��, Y����С" + String.valueOf(clsOscilloscope.rateY) + "��");
					Log.i(TAG, "X����С" + String. valueOf(clsOscilloscope.rateX)+ "��, Y����С" + String.valueOf(clsOscilloscope.rateY) + "��");
					break;
				default:
					break;
			}
		}
	};
	
}