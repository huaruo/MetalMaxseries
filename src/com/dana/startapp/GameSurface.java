/**
 * ������Canvas�ϴ���ͼ��surface����Canvas������view��surface�ϵĻ�ͼ������
 */

package com.dana.startapp;

import com.dana.modul.MySurfaceView;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

public class GameSurface extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//����ȫ��
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//��ʾ�Զ����SurfaceView��ͼ
		setContentView(new MySurfaceView(this));
	}
}