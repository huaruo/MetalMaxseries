package com.dana.modul;

public class Position
{
	private int row;
	private int clmn;
	
	public Position() {
		row=-1;
		clmn=-1;
	}
	
	/**设置坐标*/
	public Position(int row, int clmn)
	{
		this.row=row;
		this.clmn=clmn;
	}
	
	/** 获取横坐标 */
	public  int getRow() {
		return row;
	}
	
	/** 设置横坐标  */
	public void setRow(int row) {
		this.row = row;
	}
	
	/** 获取纵坐标 */
	public int getClmn() {
		return clmn;
	}
	
	/** 设置纵坐标 */
	public void setClmn(int clmn) {
		this.clmn = clmn;
	}
	
	/**
	 * 获取空位置
	 */
	public static Position getEmptyPosition()
	{
		return new Position(-1, -1);
	}
	
	@Override
	public String toString() {
		//TODO Auto-generated method stub
		return "position -> (" + this.row + " , " + this.clmn+ ")";
	}

}