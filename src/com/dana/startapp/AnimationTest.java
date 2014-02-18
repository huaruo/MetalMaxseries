package com.dana.startapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

public class AnimationTest extends Activity
{
	//Debug
	private static final String TAG ="AnimationTest";
	
	private Button btnXmlAlpha, btnXmlScale, btnXmlTranslate, btnXmlRotate;
	private Button btnJavaAlpha, btnJavaScale, btnJavaTranslate, btnJavaRotate;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.animationtest);
		
		btnXmlAlpha = (Button) findViewById(R.id.animation_xmlalpha);
		btnXmlScale = (Button) findViewById(R.id.animation_xmlscale);
		btnXmlTranslate = (Button) findViewById(R.id.animation_xmltranslate);
		btnXmlRotate = (Button) findViewById(R.id.animation_xmlrotate);
		
		btnJavaAlpha = (Button) findViewById(R.id.animation_javaalpha);
		btnJavaScale = (Button) findViewById(R.id.animation_javascale);
		btnJavaTranslate = (Button) findViewById(R.id.animation_javatranslate);
		btnJavaRotate = (Button) findViewById(R.id.animation_javarotate);
		
		btnXmlAlpha.setOnClickListener(listener);
		btnXmlScale.setOnClickListener(listener);
		btnXmlTranslate.setOnClickListener(listener);
		btnXmlRotate.setOnClickListener(listener);
		
		btnJavaAlpha.setOnClickListener(listener);
		btnJavaScale.setOnClickListener(listener);
		btnJavaTranslate.setOnClickListener(listener);
		btnJavaRotate.setOnClickListener(listener);
		
	}
	
	private OnClickListener listener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			Animation mAnimation;
			AnimationSet animationSet;
			AlphaAnimation alphaAnimation;
			ScaleAnimation scaleAnimation;
			TranslateAnimation translateAnimation;
			RotateAnimation rotateAnimation;
			switch(v.getId())
			{
				case R.id.animation_xmlalpha:
					mAnimation = AnimationUtils.loadAnimation(AnimationTest.this, R.anim.alpha);
					btnXmlAlpha.startAnimation(mAnimation);
					break;
				case R.id.animation_xmlscale:
					mAnimation = AnimationUtils.loadAnimation(AnimationTest.this,R.anim.scale);
					btnXmlScale.startAnimation(mAnimation);
					break;
				case R.id.animation_xmltranslate:
					mAnimation = AnimationUtils.loadAnimation(AnimationTest.this, R.anim.translate);
					mAnimation.setRepeatCount(1);
					mAnimation.setRepeatMode(2);
					mAnimation.setAnimationListener(animationListener);
					
					btnXmlTranslate.startAnimation(mAnimation);
					break;
				case R.id.animation_xmlrotate:
					mAnimation = AnimationUtils.loadAnimation(AnimationTest.this, R.anim.rotate);
					btnXmlRotate.startAnimation(mAnimation);
					break;
				case R.id.animation_javaalpha:
					animationSet = new AnimationSet(true);//创建一个AnimationSet对象
					alphaAnimation = new AlphaAnimation(1,0);//创建一个AlphaAnimation对象
					alphaAnimation.setDuration(1000);//设置动画执行时间（单位:毫秒）
					animationSet.addAnimation(alphaAnimation);//将AlphaAnimation对象添加到AnimationSet当中
					btnJavaAlpha.setAnimation(animationSet);//使用ImageView的startAnimation方法开始执行动画
					break;
				case R.id.animation_javascale:
					animationSet = new AnimationSet(true);
					/**
					 * 围绕一个点伸缩
					 */
					scaleAnimation = new ScaleAnimation(1,0.3f, 1, 0.3f,
							Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
					animationSet.addAnimation(scaleAnimation);
					animationSet.setStartOffset(100);
					animationSet.setFillAfter(false);
					animationSet.setFillBefore(true);
					animationSet.setDuration(2000);
					btnJavaScale.startAnimation(animationSet);
					Log.i(TAG, "anim");
					break;
				case R.id.animation_javatranslate:
					animationSet = new AnimationSet(true);
					/**
					 * x和y轴的起始和结束位置
					 */
					translateAnimation = new TranslateAnimation(
							Animation.RELATIVE_TO_SELF,0f,
							Animation.RELATIVE_TO_SELF,0.5f,
							Animation.RELATIVE_TO_SELF,0f,
							Animation.RELATIVE_TO_SELF,1.0f
							);
					translateAnimation.setDuration(1000);
					animationSet.addAnimation(translateAnimation);
					btnJavaTranslate.startAnimation(animationSet);
					break;
				case R.id.animation_javarotate:
					animationSet = new AnimationSet(true);
					/**
					 * 前两个参数定义旋转的起始和结束 的度数,后两个参数定义圆心的位置
					 */
					rotateAnimation = new RotateAnimation(0, 360,
							Animation.RELATIVE_TO_PARENT, 1f,
							Animation.RELATIVE_TO_PARENT,0f);
					rotateAnimation.setDuration(5000);
					animationSet.addAnimation(rotateAnimation);
					btnJavaRotate.startAnimation(animationSet);
					break;
				default:
					break;
					
			}
		}
	};
	
	private AnimationListener animationListener = new AnimationListener()
	{
		@Override
		public void onAnimationEnd(Animation animation)
		{	
		}
		@Override
		public void  onAnimationRepeat(Animation animation)
		{
		}
		@Override
		public void onAnimationStart(Animation animation)
		{
		}
	};
}