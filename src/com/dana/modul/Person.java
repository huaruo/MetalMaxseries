package com.dana.modul;

import java.util.Comparator;

public class Person
{

	String name;
	int age;
	
	public Person(String name, int age){
		this.name = name;
		this.age = age;
	}
	
	public int getAge(){
		return age;
	}
	
	public void setAge(int age) {
		this.age =age;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	
	// ʵ��Comparator�ӿ�,Ҳ���Ƕ����������,�㼸�����Զ����κι���
	public static class Mycomparator implements Comparator<Person>
	{
		public int compare(Person o1, Person o2) {
			Person p1 = o1;
			Person p2 = o2;
			if(p1.age < p2.age)
				return -1;
			if(p1.age == p2.age)
				return 0;
			else
				return 1;
		}
	}
}