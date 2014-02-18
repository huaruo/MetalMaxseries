/**
 * @author Huaruo.W
 * Issue:  停止按钮不起作用，退出activity时会退出整个程序。 声音太刺耳。
 */

package com.dana.startapp;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

public class AudioRecordTest extends Activity
{
	//Debug
	private static final String TAG = "AudioRecordTest";
	
	private Button btnRecord, btnStop, btnExit;
	private SeekBar skbVolume; //调节音量
	boolean isRecording = false; //是否录放的标记
	private static final int frequency = 44100;
	private static final int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
	private static final int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
	private int recBufSize, playBufSize;
	private AudioRecord audioRecord;
	private AudioTrack audioTrack;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.audiorecordtest);
		setTitle("助听器");
		
		recBufSize = AudioRecord.getMinBufferSize(frequency, channelConfiguration, audioEncoding);
		playBufSize = AudioTrack.getMinBufferSize(frequency, channelConfiguration, audioEncoding);
		
		//----------------------
		audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,frequency,
				channelConfiguration, audioEncoding, recBufSize);
		
		audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, frequency, 
				channelConfiguration, audioEncoding, 
				playBufSize, AudioTrack.MODE_STREAM);
		//-------------------------
		
		btnRecord = (Button) findViewById(R.id.audior_record);
		btnStop = (Button) findViewById(R.id.audior_stop);
		btnExit = (Button) findViewById(R.id.audior_exit);
		skbVolume = (SeekBar) findViewById(R.id.audior_skbvolume);
		
		btnRecord.setOnClickListener(listener);
		btnStop.setOnClickListener(listener);
		btnExit.setOnClickListener(listener);
		
		skbVolume.setMax(100); //设置音量调节的极限
		skbVolume.setProgress(70);//设置seekbar色位置
		audioTrack.setStereoVolume(0.7f, 0.7f); //设置当前音量大小
		skbVolume.setOnSeekBarChangeListener(seekBarChangeListener);
	}
	
	private OnClickListener listener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			switch(v.getId())
			{
				case R.id.audior_record:
					if(!isRecording)
					{
						isRecording = true;
						new RecordPlayThread().start();//开一条线程边录边放
					}
					break;
				case R.id.audior_stop:
					if(!isRecording)
					{
						isRecording = true;
					}
					break;
				case R.id.audior_exit:
					isRecording = false;
					AudioRecordTest.this.finish();
					break;
				default:
					break;
			}
		}
	};
	
	private OnSeekBarChangeListener seekBarChangeListener = new OnSeekBarChangeListener()
	{
		@Override
		public void onStopTrackingTouch(SeekBar seekBar)
		{
			float vol =(float)(seekBar.getProgress())/(float)(seekBar.getMax());
			audioTrack.setStereoVolume(vol, vol); //设置音量
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar)
		{
		}
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
		{
		}
	};
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		android.os.Process.killProcess(android.os.Process.myPid());
	}
	
	private class RecordPlayThread extends Thread
	{
		public void run()
		{
			try
			{
				byte[] buffer = new byte[recBufSize];
				audioRecord.startRecording();//开始录制
				audioTrack.play();//开始播放
				
				while(isRecording)
				{
					//从MIC保存数据到缓冲区
					int bufferReadResult = audioRecord.read(buffer, 0,recBufSize);
					byte[] tmpBuf = new byte[bufferReadResult];
					System.arraycopy(buffer, 0, tmpBuf, 0, bufferReadResult);
					//写入数据即播放
					audioTrack.write(tmpBuf, 0, tmpBuf.length);
				}
				audioTrack.stop();
				audioRecord.stop();
			}
			catch(Throwable t)
			{
				Toast.makeText(AudioRecordTest.this, t.getMessage(),Toast.LENGTH_SHORT).show();
			}
		}
	}
}