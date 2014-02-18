package com.dana.startapp;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Service;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

import com.dana.modul.Position;

public class LongClick extends Activity {
	//Debug
	private static final String TAG = "DragTest";
	
	private Button myButton, myButton1;
	
	private  int containerWidth, containerHeight;
	
	private Map<Position,View> dragViewsMap;
	private List<Position> positions;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.longclick);
		//��ȡ��Ļ�ֱ���
		DisplayMetrics dm = this.getResources().getDisplayMetrics();
		this.containerWidth = dm.widthPixels;
		this.containerHeight = dm.heightPixels-70;
		
		Log.i(TAG, "this.containerWidth = " + this.containerWidth);
		Log.i(TAG, "this.containerHeight = " + this.containerHeight);
		
		
		myButton = (Button) findViewById (R.id.long_button);
		myButton1 = (Button) findViewById (R.id.long_button1);
		myButton.setOnLongClickListener(listener);
		myButton1.setOnLongClickListener(listener);		
	}
	
	private OnLongClickListener listener = new OnLongClickListener() {
		public boolean onLongClick(View v)
		{
			switch(v.getId())
			{
			case R.id.long_button:
			case R.id.long_button1:
				/**�𶯷���*/
				Vibrator vib = (Vibrator) LongClick.this.getSystemService(Service.VIBRATOR_SERVICE);
				long[] pattern = {100,150};
				//����������һ�����Զ�����ģʽ��  
				//���������ֵĺ��������Ǿ�ֹ��ʱ������ʱ������ֹʱ������ʱ��������ʱ���ĵ�λ�Ǻ���  
				//�ڶ����ǡ��Ƿ񷴸��𶯡�,-1 ���ظ���  
				//�ڶ�����������С��pattern�ĳ��ȣ���Ȼ����ArrayIndexOutOfBoundsException 
				vib.vibrate(pattern, 1);
				myButton.setOnTouchListener(touchListener);
				myButton1.setOnTouchListener(touchListener);
				break;
			default:
				break;
			}
			return false;
		}
	};

	private OnTouchListener touchListener = new OnTouchListener() {
		int lastX, lastY;
		Position originPos = Position.getEmptyPosition();
		
		public boolean onTouch(View v, MotionEvent event) {
			//TODO Auto-generated method stub
			int action = event.getAction();
			Log.i(TAG, "Touch: " + action);
			
			switch(action){
			case MotionEvent.ACTION_DOWN:
				Log.v(TAG, "MotionEvent.ACTION_DOWN");
				lastX = (int) event.getRawX();
				lastY = (int) event.getRawX();
				Log.v(TAG, "lastX = " + lastX + ", lastY = " + lastY);
				break;
				/**
				 * layout(l,t,r,b) l Left position, relative to parent t Top
				 * position, relative to parent r Right position, relative
				 * to parent b Bottom position, relative to parent
				 */
			case MotionEvent.ACTION_MOVE:
				Log.v(TAG, "MotionEvent.ACTION_MOVE");
				int dx = (int) event.getRawX() - lastX;
				int dy = (int) event.getRawY() - lastY;
				Log.v(TAG, "dx = " + dx + ", dy = " + dy);
				
				int left = v.getLeft() + dx;
				int top = v.getTop() + dy;
				int right = v.getRight() + dx;
				int bottom = v.getBottom() + dy;
				
				if (left < 0) {
					left = 0;
					right = left+v.getWidth();
				}
				if(right > containerWidth){
					right = containerWidth;
					left = right - v.getWidth();
				}
				if(top < 0){
					top = 0;
					bottom = top +v.getHeight();
				}
				if (bottom > containerHeight) {
					bottom = containerHeight;
					top = bottom - v.getHeight();
				}
				v.layout(left, top, right, bottom);
				
				Log.v(TAG, "position: " + left + ", " + top + ", " + right + "," + bottom);
				
				lastX = (int) event.getRawX();
				lastY = (int) event.getRawY();
				break;
			case MotionEvent.ACTION_UP:
				break;
			}
			return false;
		}
	};
	
	/**���ݵ��λ�û�ȡ����Ŀؼ�����*/
/*	private Position getTouchPos( int tx, int ty)
	{		 return new Position(tx, ty);
	}
*/	
	/**
	 * �ؼ���λ
	 * */
/*	private void move(View view,Position fromPos,Position toPos)
	{
		Log.v(TAG, "move().....topos : "+toPos.toString());
		toPos=this.mappingPosition(toPos);
		
		//�жϻ�λ˳�������ϻ�λ�������»�λ
		int from_index=-1;
		int to_index=-1;
		for(int i=0;i<this.positions.size();i++)
		{
			Position pos = this.positions.get(i);
			if(pos.getRow()==fromPos.getRow()&&pos.getClmn()==fromPos.getClmn())
			{
				from_index=i;
			}
			
			if(pos.getRow()==toPos.getRow()&&pos.getClmn()==toPos.getClmn())
			{
				to_index=i;
			}
		}
		if(to_index<from_index) //���Ͻ���
		{
			for(int i=from_index-1;i>=to_index;i--)
			{
				View moving_view = Main.this.dragViewsMap.get(this.positions.get(i));
				Main.this.locateView(moving_view, this.positions.get(i+1));
				Main.this.dragViewsMap.put(this.positions.get(i+1), moving_view);
			}
			//���϶��Ŀؼ��ŵ�Ŀ������λ��
			this.locateView(view, toPos);
			this.dragViewsMap.put(toPos, view);
		}
		else   //���½���
		{
			for(int i=from_index+1;i<=to_index;i++)
			{
				View moving_view = this.dragViewsMap.get(this.positions.get(i));
				this.locateView(moving_view, this.positions.get(i-1));
				this.dragViewsMap.put(this.positions.get(i-1), moving_view);
			}
			//���϶��Ŀؼ��ŵ�Ŀ������λ��
			this.locateView(view, toPos);
			this.dragViewsMap.put(toPos, view);
		}
	}
	
	private Position mappingPosition(Position position)
	{
		for(Position pos : this.positions)
		{
			if(pos.getRow()==position.getRow()&&pos.getClmn()==position.getClmn())
			{
				return pos;
			}
		}
		return null;
	}
*/	
	/**��λָ���Ŀؼ�*/
/*	private void locateView(View view,Position pos)
	{
		Log.v(TAG, "locatView()..."+pos.toString());
		
		boolean isInit=false;
		RelativeLayout.LayoutParams layout_params=null;
		if(view==null)
		{
			isInit=true;
			view = this.dragViewsMap.get(pos);
			layout_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		}
		else
		{
			layout_params = (LayoutParams) view.getLayoutParams();
		}
		
		int left_margin=pos.getClmn()*this.containerWidth;
		int top_margin =pos.getRow()*this.containerHeight;
		
		Log.d(TAG, "left_margin = "+left_margin);
		Log.d(TAG, "top_margin = "+top_margin);
		
		layout_params.setMargins(left_margin, top_margin, 0, 0);
		view.setLayoutParams(layout_params);
	}
	*/
}
