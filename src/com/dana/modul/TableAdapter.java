/**
 * ʵ�ֲ���TableCell-->TableRow(TableRowView)-->ListView
 */

package com.dana.modul;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class TableAdapter extends BaseAdapter
{
	//Debug
	private static final String TAG ="TableAdapter";
	
	private Context context;
	private List<tTableRow> table;
	
	public TableAdapter(Context context, List<tTableRow> table)
	{
		this.context = context;
		this.table = table;
	}
	
	@Override
	public int getCount()
	{
		return table.size();
	}
	
	@Override
	public long getItemId(int position)
	{
		return position;
	}
	
	public tTableRow getItem(int position)
	{
		return table.get(position);
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		tTableRow tableRow = table.get(position);
		return new TableRowView(this.context, tableRow);
	}
	
	
	/**
	 * TableRowView ʵ�ֱ���е���ʽ
	 * @author Huaruo.W
	 */
	
	class TableRowView extends LinearLayout
	{
		public TableRowView(Context context, tTableRow tableRow)
		{
			super(context);
			
			this.setOrientation(LinearLayout.HORIZONTAL);
			for(int i = 0; i < tableRow.getSize(); i++)
			{
				//�����Ԫ��ӵ���
				TableCell tableCell = tableRow.getCellValue(i);
				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(tableCell.width,tableCell.height);//���ո�Ԫָ���Ĵ�С���ÿռ�
				layoutParams.setMargins(0,0,1,1);//Ԥ����϶����߿�
				
				if(tableCell.type == TableCell.STRING)
				{
					//�����Ԫ���ı�����
					TextView textCell = new TextView(context);
					textCell.setLines(1);
					textCell.setGravity(Gravity.CENTER);
					textCell.setBackgroundColor(Color.BLACK);
					textCell.setText(String.valueOf(tableCell.value));
					addView(textCell, layoutParams);
				}
				else if(tableCell.type == TableCell.IMAGE)
				{
					//�����Ԫ��ͼ������
					ImageView imgCell = new ImageView(context);
					imgCell.setBackgroundColor(Color.BLACK);
					imgCell.setImageResource((Integer) tableCell.value);
					addView(imgCell, layoutParams);
				}
			}
			setBackgroundColor(Color.WHITE);//���ÿ�϶ʵ�ֱ߿�
		}
	}
	
	/**
	 * TableRowʵ�ֱ�����
	 */
	public static class tTableRow
	{
		private TableCell[] cell;
		public tTableRow(TableCell[] cell)
		{
			this.cell = cell;
		}
		public int getSize()
		{
			return cell.length;
		}
		public TableCell getCellValue(int index)
		{
			if(index >= cell.length)
				return null;
			return cell[index];
		}
	}
	
	
	/**
	 * TableCell ʵ�ֱ��ĸ�Ԫ
	 */
	
	public static class TableCell
	{
		public static final int STRING = 0;
		public static final int IMAGE = 1;
		
		public Object value;
		public int width;
		public int height;
		private int type;
		
		public TableCell(Object  value, int width, int height, int type)
		{
			this.value=value;
			this.width=width;
			this.height=height;
			this.type=type;
		}
	}
}