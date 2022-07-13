package ext.ket.shared.util;

import java.util.ArrayList;

import ext.ket.shared.log.Kogger;

public class TimerUtil {
	long start;
	long end;
	long total;
	String title;
	String desc;
	ArrayList<String> history;
	
	public TimerUtil(String title){
		this.start = 0;
		this.end = 0;
		this.total = 0;
		this.title = title;
		this.history = new ArrayList<String>();
	}
	
	public void setStartPoint(String desc){
		this.desc = desc;
		this.start = System.currentTimeMillis();
	}
	
	public void setEndPoint(){
		this.end =  System.currentTimeMillis();
		 
		String str = desc + " : " + (end - start) + " milliseconds";
		history.add(str);
		
		this.total += end - start;
		this.start = this.end;
	}
	
	public void display(){
		System.out.println("=========================================");
		System.out.println( this.title);
		System.out.println( "=========================================");
		
		for(String str : this.history){
			Kogger.debug(getClass(), str);
		}
		System.out.println( "=========================================");
		System.out.println( "전체 : " + total + " milliseconds");
		System.out.println( "=========================================");
		
	}
}
