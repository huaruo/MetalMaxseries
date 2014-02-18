package com.dana.modul;

import java.util.Comparator;

public class PinYinContent {

	private String letter;
	private String name;
	
		
	
	public PinYinContent(String letter, String name) {
		super();
		this.letter = letter;
		this.name = name;
	}
	public String getLetter() {
		return letter;
	}
	public void setLetter(String letter) {
		this.letter = letter;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public static class PinyinComparator implements Comparator<PinYinContent> {

		public int compare(PinYinContent o1, PinYinContent o2) {
			if (o1.getLetter().equals("@")
					|| o2.getLetter().equals("#")) {
				return -1;
			} else if (o1.getLetter().equals("#")
					|| o2.getLetter().equals("@")) {
				return 1;
			} else {
				return o1.getLetter().compareTo(o2.getLetter());
			}
		}
	}
}
