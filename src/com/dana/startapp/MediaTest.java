/**
 * @author Huaruo.W
 * @Issue: 1.��sd����ȡ��Ƶ�ļ�,��Ƶ�ļ����룬2.������Ƶ��3. ���sd���ļ�;
 */


package com.dana.startapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

public class MediaTest extends Activity
{
	//Debug
	private static final String TAG = "MediaTest";
	
	private SeekBar skb_audio = null;
	private Button btn_start_audio = null;
	private Button btn_stop_audio = null;
	
	private SeekBar skb_video = null;
	private Button btn_start_video = null;
	private Button btn_stop_video = null;
	private SurfaceView surfaceView;
	private SurfaceHolder surfaceHolder;
	private MediaPlayer mp = null;
	private Timer mTimer;
	private TimerTask mTimerTask;
	
	private boolean isChanging = false;//�����������ֹ��ʱ����SeekBar�϶�ʱ���ȳ�ͻ 
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mediaplayertest);
		
		//-----Media�ؼ�����-----//
		mp = new MediaPlayer();
		
		btn_start_audio = (Button) findViewById(R.id.media_btn1);
		btn_stop_audio = (Button) findViewById(R.id.media_btn2);
		btn_start_audio.setOnClickListener(listener);
		btn_stop_audio.setOnClickListener(listener);
		skb_audio=(SeekBar) findViewById(R.id.media_SeekBar1);
		skb_audio.setOnSeekBarChangeListener(seekBarChangeListener);
		
		btn_start_video = (Button) findViewById(R.id.media_btn3);
		btn_stop_video = (Button)  findViewById(R.id.media_btn4);
		btn_start_video.setOnClickListener(listener);
		btn_stop_video.setOnClickListener(listener);
		skb_video = (SeekBar) findViewById(R.id.media_SeekBar2);
		skb_video.setOnSeekBarChangeListener(seekBarChangeListener);
		
		surfaceView = (SurfaceView) findViewById(R.id.media_sfv1);
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.setFixedSize(100, 100);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);		
	}
	
	private OnCompletionListener completionListener = new OnCompletionListener()
	{
		@Override
		public void onCompletion(MediaPlayer arg0)
		{
			Toast.makeText(MediaTest.this, "����", 1000).show();
			mp.release();
		}
	};
	
	
	/*
	 * �����¼�����
	 */
	
	private OnClickListener listener = new OnClickListener(){
		@Override
		public void onClick(View v)
		{
			switch(v.getId())
			{
				case R.id.media_btn1:
					mp.reset();//�ָ���δ��ʼ����״̬
					//��ȡ��Ƶ
					try{
						//׼��
//						//mp=MediaPlayer.create(MediaTest.this,R.raw.big);//�û���Ӧ���������Դ���resource��Դ
//						//skb_audio.setMax(mp.getDuration());//����SeekBar�ĳ���
//						//mp.prepare();
						mp.setDataSource("/storage/sdcard0/Music/�ſɶ�  - ����.mp3");//�洢��SD���������ļ�·���µ�ý���ļ�,
//						mp.setDataSource("http://www.citynorth.cn/music/confucius.mp3");//���ϵ�ý���ļ�
						/**
						 * 1��  ��һ��MediaPlayer�����½������reset()����֮�������ڿ���״̬���ڵ���release����֮�󣬲Żᴦ�ڽ���״̬��
						 * 2��  һ���½���MediaPlayer�����ڵ���getCurrenProgress()��getDuration��getVideoHeight()��getVideoWith()��setAudioStreamType(int)��setLooping(boolean)��setVolume(float,float)��pause()��start()��stop()��seekTo()��prepare()��prepareAsync()����ʱ�����ᴥ��OnErrorListenerError()�¼�������MediaPlayer�������������reset()��������ʹ����Щ������ᴥ��OnErrorListenerError�����¼���
						 * ���ԣ����������reset()�������ֵ���getDuration()ʱ���ͻᱨ�쳣��
						 */
						mp.prepare();
						skb_audio.setMax(mp.getDuration());//����SeekBar�ĳ���
						mp.prepare();
					}
					catch(IllegalStateException e)
					{
//						Log.i(TAG, e.getMessage());
						e.printStackTrace();
					}
					catch(IOException e)
					{
						Log.i(TAG, e.getMessage());
						e.printStackTrace();
					}
					mp.start();//����
					recordMp(mp);
					break;
				case R.id.media_btn2:
				case R.id.media_btn4:
					mp.stop();
					break;
				case R.id.media_btn3:
					mp.reset(); //�ָ���δ��ʼ����״̬
					skb_video.setMax(mp.getDuration());//����SeekBar�ĳ���
					mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
					mp.setDisplay(surfaceHolder);//������Ļ
					try{
//						mp=MediaPlayer.create(MediaTest.this, R.raw.test);//��ȡ��Ƶ
						mp.setDataSource("/storage/sdcard0/Movies/֣Դvs֣��Ʒ-����ѩ(��).mpg");
						//׼��
						mp.prepare();
					}
					catch(IllegalStateException e)
					{
						e.printStackTrace();
					}
					catch(IOException e)
					{
						e.printStackTrace();
					}
					mp.start();//����
					break;
				default:
					break;
			}
		}
	};
	
	
	private OnSeekBarChangeListener seekBarChangeListener = new OnSeekBarChangeListener(){
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
		{
			
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar)
		{
			isChanging = true;
		}
		@Override
		public void onStopTrackingTouch(SeekBar seekBar)
		{
			mp.seekTo(seekBar.getProgress());
			isChanging = false;
		}
	};
	
	
	
	private void  recordMp(final MediaPlayer mps)
	{
		//���Ž���֮�󵯳���ʾ
		mps.setOnCompletionListener(completionListener);
		
		//-----��ʱ����¼���Ž���-----//
		mTimer = new Timer();
		mTimerTask = new TimerTask(){
			@Override
			public void run(){
				if(isChanging == true)
					return;
				if(mps.getVideoHeight()==0)
					skb_audio.setProgress(mps.getCurrentPosition());
				else
					skb_video.setProgress(mps.getCurrentPosition());
			}
		};
		
		mTimer.schedule(mTimerTask, 0, 10);	
	}
	
	
	
	
//	MediaPlayer��
//
//	   �����ʺϲ��Žϴ��ļ��������ļ�Ӧ�ô洢��SD���ϣ�����������Դ�ļ�����д���ÿ��ֻ�ܲ���һ����Ƶ�ļ���
//
//	�����÷����£�
//
//	    1������Դ�ļ��в���
//
//	              MediaPlayer   player  =   new MediaPlayer.create(this,R.raw.test);
//
//	              player.stare();
//
//	    2�����ļ�ϵͳ����
//
//	              MediaPlayer   player  =   new MediaPlayer();
//
//	              String  path   =  "/sdcard/test.mp3";
//
//	               player.setDataSource(path);
//
//	               player.prepare();
//
//	               player.start();
//
//	    3�������粥��
//
//	        (1)ͨ��URI�ķ�ʽ:
//
//	              String path="http://**************.mp3";     //�����һ�������������ַ������
//
//	                Uri  uri  =  Uri.parse(path);
//
//	                MediaPlayer   player  =   new MediaPlayer.create(this,uri);
//
//	                player.start();
//
//	        (2)ͨ����������Դ�ķ�ʽ��
//
//	             MediaPlayer   player  =   new MediaPlayer.create();
//
//	             String path="http://**************.mp3";          //�����һ�������������ַ������
//
//	             player.setDataSource(path);
//
//	             player.prepare();
//
//	             player.start();
//
//	 SoundPool��
//
//	  �����ص���ǵ��ӳٲ��ţ��ʺϲ���ʵʱ��ʵ��ͬʱ���Ŷ������������Ϸ��ը���ı�ը����С��Դ�ļ���������Ƶ�Ƚ��ʺϷŵ���Դ�ļ��� res/raw�ºͳ���һ����APK�ļ���
//
//	  �÷����£�
//
//	        SoundPool soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
//
//	        HashMap<Integer, Integer> soundPoolMap = new HashMap<Integer, Integer>();  
//
//	        soundPoolMap.put(1, soundPool.load(this, R.raw.dingdong1, 1));        
//
//	        soundPoolMap.put(2, soundPool.load(this, R.raw.dingdong2, 2));      
//
//	        public void playSound(int sound, int loop) {
//
//	            AudioManager mgr = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);   
//
//	            float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);   
//
//	            float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);       
//
//	           float volume = streamVolumeCurrent/streamVolumeMax;   
//
//	           soundPool.play(soundPoolMap.get(sound), volume, volume, 1, loop, 1f);
//
//	           //������1��Map��ȡֵ   2����ǰ����     3���������  4�����ȼ�   5���ز�����   6�������ٶ�
//
//	}   
//
//	      this.playSound(1, 0);

//	SoundPoolʹ�÷���
//	�������裺
//	1>����Ŀ��res/rawĿ¼�з�����Ч�ļ���
//	2>�½�SoundPool����Ȼ�����SoundPool.load()������Ч������SoundPool.play()��������ָ����Ч�ļ���
//	public class AudioActivity extends Activity   
//	{  
//	    private SoundPool pool;  
//	    @Override  
//	    public void onCreate(Bundle savedInstanceState)   
//	    {  
//	        super.onCreate(savedInstanceState);  
//	         setContentView(R.layout.main);  
//	        //ָ�������ص������Ƶ����ĿΪ10������Ʒ��Ϊ5  
//	        pool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);  
//	        //������Ƶ���������ڳ��е�id  
//	        final int sourceid = pool.load(this, R.raw.pj, 0);  
//	        Button button = (Button)this.findViewById(R.id.button);  
//	        button.setOnClickListener(new View.OnClickListener()   
//	        {  
//	            public void onClick(View v)   
//	            {                               //������Ƶ���ڶ�������Ϊ����������;����������Ϊ����������;���ĸ�����Ϊ���ȼ������������Ϊѭ��������0��ѭ����-1ѭ��;����������Ϊ���ʣ�����    ���0.5���Ϊ2��1���������ٶ�  
//	                pool.play(sourceid, 1, 1, 0, -1, 1);  
//	            }  
//	        });  
//	    }  
//	}  

	
	
	
	
	
	/*	
	private void mplay(File mfile)
	{
		if(mp !=null && mp.isPlaying()){
			//��Ҫ���������ص�
			mp.stop();
			mp.release();
			mp = null;
		}
		if(mfile !=null && mfile.exists()){
			String path = mfile.getAbsolutePath();
			Log.i(TAG, "path: " + path);
			try{
				//����MediaPlayer
				mp = new MediaPlayer();
				mp.reset();
				skb_audio.setMax(mp.getDuration());//����SeekBar�ĳ���
				//Ϊ���������������ļ�
				mp.setDataSource(path);
//				mp=MediaPlayer.create(MediaTest.this,R.raw.big);//�û���Ӧ���������Դ���resource��Դ
//				mp.setDataSource("/storage/sdcard0/Music/�ְֵĲ�Ь.mp3");//�洢��SD���������ļ�·���µ�ý���ļ�
//				mp.setDataSource("http://www.citynorth.cn/music/confucius.mp3");//���ϵ�ý���ļ�
				mp.prepare();
				mp.start();//����
				mp.seekTo(0);
			}
			catch(IllegalStateException e)
			{
				e.printStackTrace();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			recordMp(mp);
		}
	}
	*/
	
	
	
	
	
//����package com.orgcent.healthtangnb��
//����import java.io.File��
//����import java.util.Arrays��
//����import android.app.Activity��
//����import android.content.Intent��
//����import android.media.MediaPlayer��
//����import android.os.Bundle��
//����import android.os.Environment��
//����import android.view.KeyEvent��
//����import android.view.View��
//����import android.view.ViewGroup��
//����import android.view.View.OnClickListener��
//����import android.widget.AdapterView��
//����import android.widget.Button��
//����import android.widget.ListView��
//����import android.widget.TextView��
//����import android.widget.Toast��
//����import android.widget.AdapterView.OnItemClickListener��
//����import com.ekangcn.healthtangnb.adapter.BaseListAdapter��
//����import com.ekangcn.healthtangnb.util.MediaFile��
//����/**
//����* sdcard�ļ��鿴���������Ƶ�ļ�����
//����*/
//����public class FileExplorer extends Activity implements OnClickListener�� OnItemClickListener {
//����private ListView mFileList��
//����private FileListAdapter mAdapter��
//����private String mFilePath��
//����private TextView mHeaderView��
//����private File mCurrentDir��
//����private MediaPlayer mMediaPlayer��
//����@Override
//����protected void onCreate��Bundle savedInstanceState�� {
//����super.onCreate��savedInstanceState����
//����setContentView��R.layout.file_explorer����
//����Button btn = ��Button�� findViewById��R.id.btnConfirm����
//����btn.setOnClickListener��this����
//����btn = ��Button�� findViewById��R.id.btnCancel����
//����btn.setOnClickListener��this����
//����mFileList = ��ListView�� findViewById��R.id.fileList����
//����mHeaderView = ��TextView��View.inflate��this�� R.layout.file_list_item�� null����
//����mHeaderView.setOnClickListener��this����
//����mFileList.addHeaderView��mHeaderView����
//����mAdapter = new FileListAdapter��this����
//����mFileList.setAdapter��mAdapter����
//����mFileList.setOnItemClickListener��this����
//����// /mnt/sdcard/��Ϊ��Ŀ¼
//����loadFileList��Environment.getExternalStorageDirectory��������
//����}
//����@Override
//����protected void onPause���� {
//����super.onPause������ //activity����ʱ��ֹͣ��������
//����if��null ��= mMediaPlayer�� {
//����mMediaPlayer.release������
//����}
//����}
//����@Override
//����public void onClick��View v�� {
//����switch ��v.getId������ {
//����case R.id.btnConfirm��
//����if��null ��= mMediaPlayer && mMediaPlayer.isPlaying������ {
//����mMediaPlayer.stop������
//����} //��ѡ����ļ�·������Ϊactivity�ĵ�����
//����Intent it = new Intent������
//����it.putExtra��"file"�� mFilePath����
//����setResult��RESULT_OK�� it����
//����finish������
//����break��
//����case R.id.btnCancel��
//����finish������
//����break��
//����case R.id.tvTitle��
//����File parent = null�� //�����������һ�㡱��չʾ��һ���ļ��б���Ϣ
//����final File sdcard = Environment.getExternalStorageDirectory������
//����if��null ��= mCurrentDir && ��parent=mCurrentDir.getParentFile������ ��= null
//����&& ��sdcard ��= null && sdcard.equals��parent������ {
//����loadFileList��parent����
//����}
//����break��
//����}
//����}
//����private void loadFileList��File root�� {
//����if��null ��= root�� {
//����mCurrentDir = root��
//����mHeaderView.setText��"������һ��"����
//����File[] children = root.listFiles������
//����mAdapter.recyle������
//����if��null ��= children�� {
//����mAdapter.add��Arrays.asList��children���� true����
//����} else {
//����}
//����}
//����}
//����class FileListAdapter extends BaseListAdapter<File> {
//����public FileListAdapter��Activity content�� {
//����super��content����
//����}
//����@Override
//����public View getView��int position�� View convertView�� ViewGroup parent�� {
//����TextView tv��
//����if��null == convertView�� {
//����convertView = View.inflate��parent.getContext������ R.layout.file_list_item�� null����
//����}
//����tv = ��TextView�� convertView��
//����//�����ļ�����
//����File item = getItem��position����
//����tv.setText��item.getName��������
//����return convertView��
//����}
//����}
//����@Override
//����public boolean onKeyDown��int keyCode�� KeyEvent event�� {
//����if��keyCode == KeyEvent.KEYCODE_BACK�� {
//����File parent = null�� //�����ؼ���ʵ�ַ�����һ��Ч��
//����final File sdcard = Environment.getExternalStorageDirectory������
//����if��null ��= mCurrentDir && ��parent=mCurrentDir.getParentFile������ ��= null
//����&& ��sdcard ��= null && sdcard.equals��parent������ {
//����loadFileList��parent����
//����return true��
//����}
//����}
//����return super.onKeyDown��keyCode�� event����
//����}
//����@Override
//����public void onItemClick��AdapterView<��> parent�� View view�� int position��
//����long id�� {
//����File item = ��File�� parent.getItemAtPosition��position����
//����if��null ��= item�� {
//����if��item.isDirectory������ {
//����loadFileList��item����
//����} else { //�����ļ������ж��ļ��Ƿ�Ϊ��Ƶ�ļ���MediaFileΪAndroidϵͳ�ڲ�����.��������½��ų�
//����if����MediaFile.isAudioFileType��item.getAbsolutePath�������� {
//����Toast.makeText��this�� "����Ƶ�ļ�����ѡ��"�� Toast.LENGTH_LONG��.show������
//����} else {
//����if��item.getAbsolutePath����.equals��mFilePath���� {
//����return��
//����}
//����mFilePath = item.getAbsolutePath������
//����//��������
//����if��null == mMediaPlayer�� {
//����mMediaPlayer = new MediaPlayer������
//����} else { //�������ڲ��ţ�ֹͣ��������λ����Ϊ��ʼλ�ã���Ȼ���ܲ���.
//����if��mMediaPlayer.isPlaying������ {
//����mMediaPlayer.stop������
//����}
//����mMediaPlayer.seekTo��0����//������λ����Ϊ��ʼλ�ã���Ȼ���ܲ���.
//����}
//����try {//����Ҫ���ŵ���Ƶ�ļ�·��
//����mMediaPlayer.setDataSource��item.getAbsolutePath��������
//����mMediaPlayer.prepare������
//����mMediaPlayer.start������
//����Toast.makeText��this�� "��ѡ�񣬿�ʼ����"�� Toast.LENGTH_LONG��.show������
//����} catch ��Exception e�� {
//����}
//����}
//����}
//����}
//����}
//����}

}