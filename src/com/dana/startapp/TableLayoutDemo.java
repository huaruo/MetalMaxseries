package com.dana.startapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TableLayoutDemo  extends Activity{
	//Debug
	private static final String TAG = "TabDemo";
	
	private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
	private final int FP = ViewGroup.LayoutParams.MATCH_PARENT;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mtablelayout);
		
		TableLayout mtab = (TableLayout) findViewById(R.id.mtablel_TL);
		//ȫ�����Զ����հ״�
		mtab.setStretchAllColumns(true);
		//����ʮ�а��еı��
		for(int row=0; row<10; row++)
		{
			TableRow tableRow = new TableRow(this);
			for(int col=0; col<8; col++)
			{
				TextView tv = new TextView(this);
				tv.setText("(" + col + "," + row + ")");
				tableRow.addView(tv);
			}
			
			//�½���TableRow��ӵ�TableLayout
			mtab.addView(tableRow, new TableLayout.LayoutParams(FP,WC));
		}
	}
	
}