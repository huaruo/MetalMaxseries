/**
 * @author Huaruo.W
 * @��չ�� �Ŵ�imageSwitcherͼƬ
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
	private ArrayList<Integer> imgList = new ArrayList<Integer>();//ͼ��ID
	private ArrayList<Object> imgSizes = new ArrayList<Object>();
	int size[] = new int[2];
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imageswitchtest);
		
		//�÷����������ȡ��Դ�е�ͼƬID
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
				//����ͼƬid
				imgList.add(index);
				//����ͼƬ��С
				Bitmap bmImg = BitmapFactory.decodeResource(getResources(), index);
				size[0] = bmImg.getWidth();
				size[1] = bmImg.getHeight();
				imgSizes.add(size);
			}
		}
		
		//����ImageSwitcher�ؼ�
		imgst = (ImageSwitcher) findViewById(R.id.imgsw_switcher);
		imgst.setFactory(this);
		imgst.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
		imgst.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
		imgst.setOnTouchListener(onTouchListener);
		
		//����gallery�ؼ�
		mgallery = (Gallery)findViewById(R.id.imgsw_gallery);
		mgallery.setAdapter(new ImgAdapter(this));
		mgallery.setOnItemSelectedListener(onItemSelectedListener);
		
			
	}
	
	private OnTouchListener  onTouchListener = new OnTouchListener(){
		/*
		 * �� ImageSwitcher�ؼ��ϻ��������л�ͼƬ
		 */
		
		@Override
		public boolean onTouch(View v, MotionEvent event)
		{
			if(event.getAction() == MotionEvent.ACTION_DOWN)
			{
				downX=(int)event.getX();//ȡ�ð���ʱ������
				return true;
			}
			else if(event.getAction() == MotionEvent.ACTION_UP)
			{
				upX=(int) event.getX(); //ȡ�ð���ʱ������
				int index = 0;
				if(upX - downX >100)//�����ϵ��ң�����ǰһ��
				{
					//����ǵ�һ����ص�β��
					if(mgallery.getSelectedItemPosition()==0)
						index = mgallery.getCount()-1;
					else
						index = mgallery.getSelectedItemPosition()-1;
				}
				 else if(downX-upX>100)//�����ϵ��󣬼�����һ��  
					{  
					 //����������ȥ����һ 
					 if(mgallery.getSelectedItemPosition()==(mgallery.getCount()-1)) 
						 index=0;  
					else  
					 index=mgallery.getSelectedItemPosition()+1; 
					}
					  
				//�ı�galleryͼƬ��ѡ���Զ�����ImageSwitcher�� setOnItemSelectedListener
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
	
	//����ImageSwitcher
	@Override
	public View makeView()
	{
		ImageView iv = new ImageView(this);
		iv.setBackgroundColor(0xFF000000);
		iv.setScaleType(ImageView.ScaleType.CENTER);//����
		iv.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));//����ӦͼƬ��С
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
//			 size[0]>80?size[0]:size[0]*8, size[1]>80?size[1]:size[1]*8));//ͼƬ�Ŵ�
			return iv;
		}
	}
	
	/*	//�ж��ļ��Ƿ���ͼƬ��Դ
	try {
        //��SDCARD�¶�ȡһ���ļ�
               FileInputStream inputStream=new FileInputStream(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"gif.gif");
               //File.separator ����� б�� ����ϵͳ��ͬ�Զ� ��Ӧ�� ��\�� ���� ��/����gif.gif ������ļ����� 
                byte[] buffer=new byte[2];
                //�ļ����ʹ���
               String filecode = "";
                //�ļ�����
               String fileType="";
                //ͨ����ȡ������ǰ�����ֽ����ж��ļ�����
               if (inputStream.read(buffer)!=-1) {
                        for (int i = 0; i < buffer.length; i++) {
                                //��ȡÿ���ֽ���0xFF��������������ȡ��λ�������ȡ���������ݲ��ǳ��ָ���
                               //��ת�����ַ���
                               filecode+=Integer.toString((buffer[i]&0xFF));
                        }
                        // ���ַ�����ת����Integer���������ж�
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