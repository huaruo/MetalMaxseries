package com.dana.modul;

public class Position
{
	private int row;
	private int clmn;
	
	public Position() {
		row=-1;
		clmn=-1;
	}
	
	/**��������*/
	public Position(int row, int clmn)
	{
		this.row=row;
		this.clmn=clmn;
	}
	
	/** ��ȡ������ */
	public  int getRow() {
		return row;
	}
	
	/** ���ú�����  */
	public void setRow(int row) {
		this.row = row;
	}
	
	/** ��ȡ������ */
	public int getClmn() {
		return clmn;
	}
	
	/** ���������� */
	public void setClmn(int clmn) {
		this.clmn = clmn;
	}
	
	/**
	 * ��ȡ��λ��
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