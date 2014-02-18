/**
 * @author Huaruo.W
 * @Issue: 1.从sd卡读取音频文件,音频文件剥离，2.播放视频。3. 浏览sd卡文件;
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
	
	private boolean isChanging = false;//互斥变量，防止定时器与SeekBar拖动时进度冲突 
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mediaplayertest);
		
		//-----Media控件设置-----//
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
			Toast.makeText(MediaTest.this, "结束", 1000).show();
			mp.release();
		}
	};
	
	
	/*
	 * 按键事件处理
	 */
	
	private OnClickListener listener = new OnClickListener(){
		@Override
		public void onClick(View v)
		{
			switch(v.getId())
			{
				case R.id.media_btn1:
					mp.reset();//恢复到未初始化的状态
					//读取音频
					try{
						//准备
//						//mp=MediaPlayer.create(MediaTest.this,R.raw.big);//用户在应用中事先自带的resource资源
//						//skb_audio.setMax(mp.getDuration());//设置SeekBar的长度
//						//mp.prepare();
						mp.setDataSource("/storage/sdcard0/Music/张可儿  - 雨巷.mp3");//存储在SD卡或其他文件路径下的媒体文件,
//						mp.setDataSource("http://www.citynorth.cn/music/confucius.mp3");//网上的媒体文件
						/**
						 * 1）  当一个MediaPlayer对象被新建或调用reset()方法之后，它处于空闲状态，在调用release方法之后，才会处于结束状态。
						 * 2）  一个新建的MediaPlayer对象在调用getCurrenProgress()、getDuration、getVideoHeight()、getVideoWith()、setAudioStreamType(int)、setLooping(boolean)、setVolume(float,float)、pause()、start()、stop()、seekTo()、prepare()、prepareAsync()方法时，不会触发OnErrorListenerError()事件，但是MediaPlayer对象如果调用了reset()方法后，再使用这些方法则会触发OnErrorListenerError（）事件。
						 * 所以，当你调用了reset()方法后，又调用getDuration()时，就会报异常。
						 */
						mp.prepare();
						skb_audio.setMax(mp.getDuration());//设置SeekBar的长度
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
					mp.start();//播放
					recordMp(mp);
					break;
				case R.id.media_btn2:
				case R.id.media_btn4:
					mp.stop();
					break;
				case R.id.media_btn3:
					mp.reset(); //恢复到未初始化的状态
					skb_video.setMax(mp.getDuration());//设置SeekBar的长度
					mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
					mp.setDisplay(surfaceHolder);//设置屏幕
					try{
//						mp=MediaPlayer.create(MediaTest.this, R.raw.test);//读取视频
						mp.setDataSource("/storage/sdcard0/Movies/郑源vs郑丽品-寒江雪(演).mpg");
						//准备
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
					mp.start();//播放
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
		//播放结束之后弹出提示
		mps.setOnCompletionListener(completionListener);
		
		//-----定时器记录播放进度-----//
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
	
	
	
	
//	MediaPlayer：
//
//	   此类适合播放较大文件，此类文件应该存储在SD卡上，而不是在资源文件里，还有此类每次只能播放一个音频文件。
//
//	此类用法如下：
//
//	    1、从资源文件中播放
//
//	              MediaPlayer   player  =   new MediaPlayer.create(this,R.raw.test);
//
//	              player.stare();
//
//	    2、从文件系统播放
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
//	    3、从网络播放
//
//	        (1)通过URI的方式:
//
//	              String path="http://**************.mp3";     //这里给一个歌曲的网络地址就行了
//
//	                Uri  uri  =  Uri.parse(path);
//
//	                MediaPlayer   player  =   new MediaPlayer.create(this,uri);
//
//	                player.start();
//
//	        (2)通过设置数据源的方式：
//
//	             MediaPlayer   player  =   new MediaPlayer.create();
//
//	             String path="http://**************.mp3";          //这里给一个歌曲的网络地址就行了
//
//	             player.setDataSource(path);
//
//	             player.prepare();
//
//	             player.start();
//
//	 SoundPool：
//
//	  此类特点就是低延迟播放，适合播放实时音实现同时播放多个声音，如游戏中炸弹的爆炸音等小资源文件，此类音频比较适合放到资源文件夹 res/raw下和程序一起打成APK文件。
//
//	  用法如下：
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
//	           //参数：1、Map中取值   2、当前音量     3、最大音量  4、优先级   5、重播次数   6、播放速度
//
//	}   
//
//	      this.playSound(1, 0);

//	SoundPool使用方法
//	开发步骤：
//	1>往项目的res/raw目录中放入音效文件。
//	2>新建SoundPool对象，然后调用SoundPool.load()加载音效，调用SoundPool.play()方法播放指定音效文件。
//	public class AudioActivity extends Activity   
//	{  
//	    private SoundPool pool;  
//	    @Override  
//	    public void onCreate(Bundle savedInstanceState)   
//	    {  
//	        super.onCreate(savedInstanceState);  
//	         setContentView(R.layout.main);  
//	        //指定声音池的最大音频流数目为10，声音品质为5  
//	        pool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);  
//	        //载入音频流，返回在池中的id  
//	        final int sourceid = pool.load(this, R.raw.pj, 0);  
//	        Button button = (Button)this.findViewById(R.id.button);  
//	        button.setOnClickListener(new View.OnClickListener()   
//	        {  
//	            public void onClick(View v)   
//	            {                               //播放音频，第二个参数为左声道音量;第三个参数为右声道音量;第四个参数为优先级；第五个参数为循环次数，0不循环，-1循环;第六个参数为速率，速率    最低0.5最高为2，1代表正常速度  
//	                pool.play(sourceid, 1, 1, 0, -1, 1);  
//	            }  
//	        });  
//	    }  
//	}  

	
	
	
	
	
	/*	
	private void mplay(File mfile)
	{
		if(mp !=null && mp.isPlaying()){
			//主要避免声音重叠
			mp.stop();
			mp.release();
			mp = null;
		}
		if(mfile !=null && mfile.exists()){
			String path = mfile.getAbsolutePath();
			Log.i(TAG, "path: " + path);
			try{
				//重置MediaPlayer
				mp = new MediaPlayer();
				mp.reset();
				skb_audio.setMax(mp.getDuration());//设置SeekBar的长度
				//为播放器设置数据文件
				mp.setDataSource(path);
//				mp=MediaPlayer.create(MediaTest.this,R.raw.big);//用户在应用中事先自带的resource资源
//				mp.setDataSource("/storage/sdcard0/Music/爸爸的草鞋.mp3");//存储在SD卡或其他文件路径下的媒体文件
//				mp.setDataSource("http://www.citynorth.cn/music/confucius.mp3");//网上的媒体文件
				mp.prepare();
				mp.start();//播放
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
	
	
	
	
	
//　　package com.orgcent.healthtangnb；
//　　import java.io.File；
//　　import java.util.Arrays；
//　　import android.app.Activity；
//　　import android.content.Intent；
//　　import android.media.MediaPlayer；
//　　import android.os.Bundle；
//　　import android.os.Environment；
//　　import android.view.KeyEvent；
//　　import android.view.View；
//　　import android.view.ViewGroup；
//　　import android.view.View.OnClickListener；
//　　import android.widget.AdapterView；
//　　import android.widget.Button；
//　　import android.widget.ListView；
//　　import android.widget.TextView；
//　　import android.widget.Toast；
//　　import android.widget.AdapterView.OnItemClickListener；
//　　import com.ekangcn.healthtangnb.adapter.BaseListAdapter；
//　　import com.ekangcn.healthtangnb.util.MediaFile；
//　　/**
//　　* sdcard文件查看器，点击音频文件播放
//　　*/
//　　public class FileExplorer extends Activity implements OnClickListener， OnItemClickListener {
//　　private ListView mFileList；
//　　private FileListAdapter mAdapter；
//　　private String mFilePath；
//　　private TextView mHeaderView；
//　　private File mCurrentDir；
//　　private MediaPlayer mMediaPlayer；
//　　@Override
//　　protected void onCreate（Bundle savedInstanceState） {
//　　super.onCreate（savedInstanceState）；
//　　setContentView（R.layout.file_explorer）；
//　　Button btn = （Button） findViewById（R.id.btnConfirm）；
//　　btn.setOnClickListener（this）；
//　　btn = （Button） findViewById（R.id.btnCancel）；
//　　btn.setOnClickListener（this）；
//　　mFileList = （ListView） findViewById（R.id.fileList）；
//　　mHeaderView = （TextView）View.inflate（this， R.layout.file_list_item， null）；
//　　mHeaderView.setOnClickListener（this）；
//　　mFileList.addHeaderView（mHeaderView）；
//　　mAdapter = new FileListAdapter（this）；
//　　mFileList.setAdapter（mAdapter）；
//　　mFileList.setOnItemClickListener（this）；
//　　// /mnt/sdcard/作为根目录
//　　loadFileList（Environment.getExternalStorageDirectory（））；
//　　}
//　　@Override
//　　protected void onPause（） {
//　　super.onPause（）； //activity挂起时，停止播放音乐
//　　if（null ！= mMediaPlayer） {
//　　mMediaPlayer.release（）；
//　　}
//　　}
//　　@Override
//　　public void onClick（View v） {
//　　switch （v.getId（）） {
//　　case R.id.btnConfirm：
//　　if（null ！= mMediaPlayer && mMediaPlayer.isPlaying（）） {
//　　mMediaPlayer.stop（）；
//　　} //将选择的文件路径返回为activity的调用者
//　　Intent it = new Intent（）；
//　　it.putExtra（"file"， mFilePath）；
//　　setResult（RESULT_OK， it）；
//　　finish（）；
//　　break；
//　　case R.id.btnCancel：
//　　finish（）；
//　　break；
//　　case R.id.tvTitle：
//　　File parent = null； //点击“返回上一层”后，展示上一级文件列表信息
//　　final File sdcard = Environment.getExternalStorageDirectory（）；
//　　if（null ！= mCurrentDir && （parent=mCurrentDir.getParentFile（）） ！= null
//　　&& （sdcard ！= null && sdcard.equals（parent））） {
//　　loadFileList（parent）；
//　　}
//　　break；
//　　}
//　　}
//　　private void loadFileList（File root） {
//　　if（null ！= root） {
//　　mCurrentDir = root；
//　　mHeaderView.setText（"返回上一级"）；
//　　File[] children = root.listFiles（）；
//　　mAdapter.recyle（）；
//　　if（null ！= children） {
//　　mAdapter.add（Arrays.asList（children）， true）；
//　　} else {
//　　}
//　　}
//　　}
//　　class FileListAdapter extends BaseListAdapter<File> {
//　　public FileListAdapter（Activity content） {
//　　super（content）；
//　　}
//　　@Override
//　　public View getView（int position， View convertView， ViewGroup parent） {
//　　TextView tv；
//　　if（null == convertView） {
//　　convertView = View.inflate（parent.getContext（）， R.layout.file_list_item， null）；
//　　}
//　　tv = （TextView） convertView；
//　　//设置文件名称
//　　File item = getItem（position）；
//　　tv.setText（item.getName（））；
//　　return convertView；
//　　}
//　　}
//　　@Override
//　　public boolean onKeyDown（int keyCode， KeyEvent event） {
//　　if（keyCode == KeyEvent.KEYCODE_BACK） {
//　　File parent = null； //按返回键，实现返回上一级效果
//　　final File sdcard = Environment.getExternalStorageDirectory（）；
//　　if（null ！= mCurrentDir && （parent=mCurrentDir.getParentFile（）） ！= null
//　　&& （sdcard ！= null && sdcard.equals（parent））） {
//　　loadFileList（parent）；
//　　return true；
//　　}
//　　}
//　　return super.onKeyDown（keyCode， event）；
//　　}
//　　@Override
//　　public void onItemClick（AdapterView<？> parent， View view， int position，
//　　long id） {
//　　File item = （File） parent.getItemAtPosition（position）；
//　　if（null ！= item） {
//　　if（item.isDirectory（）） {
//　　loadFileList（item）；
//　　} else { //根据文件名称判断文件是否为音频文件，MediaFile为Android系统内部代码.后面的文章将放出
//　　if（！MediaFile.isAudioFileType（item.getAbsolutePath（））） {
//　　Toast.makeText（this， "非音频文件不能选择"， Toast.LENGTH_LONG）.show（）；
//　　} else {
//　　if（item.getAbsolutePath（）.equals（mFilePath）） {
//　　return；
//　　}
//　　mFilePath = item.getAbsolutePath（）；
//　　//播放音乐
//　　if（null == mMediaPlayer） {
//　　mMediaPlayer = new MediaPlayer（）；
//　　} else { //若在正在播放，停止并将播放位置置为开始位置，不然不能播放.
//　　if（mMediaPlayer.isPlaying（）） {
//　　mMediaPlayer.stop（）；
//　　}
//　　mMediaPlayer.seekTo（0）；//将播放位置置为开始位置，不然不能播放.
//　　}
//　　try {//设置要播放的音频文件路径
//　　mMediaPlayer.setDataSource（item.getAbsolutePath（））；
//　　mMediaPlayer.prepare（）；
//　　mMediaPlayer.start（）；
//　　Toast.makeText（this， "已选择，开始播放"， Toast.LENGTH_LONG）.show（）；
//　　} catch （Exception e） {
//　　}
//　　}
//　　}
//　　}
//　　}
//　　}

}