package com.dana.startapp;

import com.dana.modul.BshElasticView;
import com.dana.modul.BshSOGridView;
import com.dana.modul.BshElasticView.IRefresh;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class BshSOView extends Activity
{
	//Debug
	private static final String TAG = "BshSOView";
	
	BshElasticView bsh_ev;
	BshSOGridView bsh_gv;
	GridAdagper ga = new GridAdagper();
	private Thread mthread = null;
	private boolean threadFlag = true;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.elastic_grid);
		
		bsh_ev = (BshElasticView) findViewById(R.id.bsh_ev);
		//拉动幅度
		bsh_ev.setFactor(2);
		//拉动范围
		bsh_ev.setMaxElastic(0.9f);
		bsh_gv = new BshSOGridView(this);
		bsh_gv.setBackgroundColor(Color.WHITE);
		bsh_gv.setNumColumns(4);
		bsh_gv.setAdapter(ga);
		bsh_ev.setScrollOverable(bsh_gv);
		bsh_ev.irefresh =  new IRefresh()
		{
			@Override
			public  boolean refreshTop()
			{
				if(threadFlag)
				{
					mthread =new Thread(runn);
					mthread.start();
				}
				return false;
			}
			
			@Override
			public boolean refreshBtm()
			{
				if(threadFlag)
				{
					mthread = new Thread(runn);
					mthread.start();
				}
				return false;
			}
		};
		
	}
	
	private Runnable runn = new Runnable()
	{
		@Override
		public void run()
		{
			try
			{
				Log.d(TAG, "refreshing");
				Thread.sleep(3000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			bsh_ev.onRefreshComplete();
		}
	};
	
	class GridAdagper extends BaseAdapter
	{

		@Override
		public int getCount()
		{
			return 100;
		}

		@Override
		public Object getItem(int arg0)
		{
			return null;
		}

		@Override
		public long getItemId(int arg0)
		{
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2)
		{
			if ( null == arg1 )
			{
				arg1 = new ImageView( BshSOView.this );
				arg1.setBackgroundResource( R.drawable.personactivity_takephoto_icon_normal);
			}
			return arg1;
		}

	}
}