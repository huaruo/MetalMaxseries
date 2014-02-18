/**
 * @author Huaruo.W
 * @扩展： 放大imageSwitcher图片
 */

package com.dana.startapp;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher.ViewFactory;

public class ImgSwitcherTest extends Activity implements ViewFactory
{
	private ImageSwitcher imgst;
	private Gallery mgallery;
	private int downX, upX;
	private ArrayList<Integer> imgList = new ArrayList<Integer>();//图像ID
	private ArrayList<Object> imgSizes = new ArrayList<Object>();
	int size[] = new int[2];
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imageswitchtest);
		
		//用反射机制来获取资源中的图片ID
		Field[] fields = R.drawable.class.getDeclaredFields();
		for(Field field : fields)
		{
			if("image".equals(field.getName()) || "password".equals(field.getName()))
			{
				int index = 0;
				try {
					index = field.getInt(R.drawable.class);
				}
				catch (IllegalArgumentException e)
				{
					e.printStackTrace();
				}
				catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
				//保存图片id
				imgList.add(index);
				//保存图片大小
				Bitmap bmImg = BitmapFactory.decodeResource(getResources(), index);
				size[0] = bmImg.getWidth();
				size[1] = bmImg.getHeight();
				imgSizes.add(size);
			}
		}
		
		//设置ImageSwitcher控件
		imgst = (ImageSwitcher) findViewById(R.id.imgsw_switcher);
		imgst.setFactory(this);
		imgst.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
		imgst.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
		imgst.setOnTouchListener(onTouchListener);
		
		//设置gallery控件
		mgallery = (Gallery)findViewById(R.id.imgsw_gallery);
		mgallery.setAdapter(new ImgAdapter(this));
		mgallery.setOnItemSelectedListener(onItemSelectedListener);
		
			
	}
	
	private OnTouchListener  onTouchListener = new OnTouchListener(){
		/*
		 * 在 ImageSwitcher控件上滑动可以切换图片
		 */
		
		@Override
		public boolean onTouch(View v, MotionEvent event)
		{
			if(event.getAction() == MotionEvent.ACTION_DOWN)
			{
				downX=(int)event.getX();//取得按下时的坐标
				return true;
			}
			else if(event.getAction() == MotionEvent.ACTION_UP)
			{
				upX=(int) event.getX(); //取得按下时的坐标
				int index = 0;
				if(upX - downX >100)//从左拖到右，即看前一张
				{
					//如果是第一，则回到尾部
					if(mgallery.getSelectedItemPosition()==0)
						index = mgallery.getCount()-1;
					else
						index = mgallery.getSelectedItemPosition()-1;
				}
				 else if(downX-upX>100)//从右拖到左，即看后一张  
					{  
					 //如果是最后，则去到第一 
					 if(mgallery.getSelectedItemPosition()==(mgallery.getCount()-1)) 
						 index=0;  
					else  
					 index=mgallery.getSelectedItemPosition()+1; 
					}
					  
				//改变gallery图片所选，自动触发ImageSwitcher的 setOnItemSelectedListener
				mgallery.setSelection(index,true);
				return true;
			}
			return false;
		}
	};

	private OnItemSelectedListener onItemSelectedListener = new OnItemSelectedListener()
	{
		@Override
		public void onItemSelected(AdapterView<?> arg0, View v, int position, long arg3)
		{
			imgst.setImageResource(imgList.get(position));
			ImgSwitcherTest.this.setTitle(String.valueOf(position));
		}
		@Override
		public void onNothingSelected(AdapterView<?> arg0)
		{
			
		}
	};
	
	//设置ImageSwitcher
	@Override
	public View makeView()
	{
		ImageView iv = new ImageView(this);
		iv.setBackgroundColor(0xFF000000);
		iv.setScaleType(ImageView.ScaleType.CENTER);//居中
		iv.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));//自适应图片大小
		return iv;
	}
	
	public class ImgAdapter extends BaseAdapter{
		private Context mContext;
		
		
		public ImgAdapter(Context c)
		{
			mContext = c;
		}
		public int getCount()
		{
			return imgList.size();
		}
		public Object getItem(int position)
		{
			return position;
		}
		public long getItemId(int position)
		{
			return position;
		}
		
		public View getView(int position, View convertView, ViewGroup parent)
		{
			ImageView iv = new ImageView(mContext);
			iv.setImageResource(imgList.get(position));
			iv.setAdjustViewBounds(true);
			iv.setLayoutParams(new Gallery.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//			iv.setLayoutParams(new Gallery.LayoutParams(
//			 size[0]>80?size[0]:size[0]*8, size[1]>80?size[1]:size[1]*8));//图片放大
			return iv;
		}
	}
	
	/*	//判断文件是否是图片资源
	try {
        //从SDCARD下读取一个文件
               FileInputStream inputStream=new FileInputStream(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"gif.gif");
               //File.separator 这个是 斜线 根据系统不同自动 适应的 “\” 或者 “/”。gif.gif 这个是文件名字 
                byte[] buffer=new byte[2];
                //文件类型代码
               String filecode = "";
                //文件类型
               String fileType="";
                //通过读取出来的前两个字节来判断文件类型
               if (inputStream.read(buffer)!=-1) {
                        for (int i = 0; i < buffer.length; i++) {
                                //获取每个字节与0xFF进行与运算来获取高位，这个读取出来的数据不是出现负数
                               //并转换成字符串
                               filecode+=Integer.toString((buffer[i]&0xFF));
                        }
                        // 把字符串再转换成Integer进行类型判断
                       switch (Integer.parseInt(filecode)) {
                                case 7790:  
                                   fileType = "exe";  
                                   break;  
                              case 7784:  
                                   fileType =  "midi";  
                                 break;  
                               case 8297:  
                                   fileType = "rar";  
                                    break;          
                                case 8075:  
                                   fileType = "zip";  
                                     break;  
                               case 255216:  
                                   fileType = "jpg";  
                                  break;  
                                case 7173:  
                                    fileType = "gif";  
                                    break;  
                               case 6677:  
                                   fileType = "bmp";  
                                     break;  
                                 case 13780:  
                                  fileType = "png";  
                                     break;  
                                default:  
                                     fileType = "unknown type: "+filecode;  
                            }  

                        }
                        System.out.println(fileType);
                }
         catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
}
*/

}