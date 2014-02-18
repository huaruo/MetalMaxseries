package com.dana.modul;

import com.dana.modul.BshElasticView.IScrollOverable;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class BshSOGridView extends GridView implements
		IScrollOverable
{
	public BshSOGridView(Context context, AttributeSet attrs,
			int defStyle)
	{
		super( context, attrs, defStyle );
	}

	public BshSOGridView(Context context, AttributeSet attrs)
	{
		super( context, attrs );
	}

	public BshSOGridView(Context context)
	{
		super( context );
	}

	@Override
	public boolean isScrollOnTop()
	{
		return 0 == getFirstVisiblePosition() ? true : false;
	}

	@Override
	public boolean isScrollOnBtm()
	{
		return (getCount() - 1) == getLastVisiblePosition() ? true : false;
	}
}
