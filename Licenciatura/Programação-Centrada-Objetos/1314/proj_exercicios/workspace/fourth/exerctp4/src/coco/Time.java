package coco;
import pco.Date;

public class Time {
	public static void main(String[] args){
		Date d1= new Date(17,10,2013);
		Date d2= new Date(17,10,2013);
		Date d3= new Date(18,10,2013);
		String s= "abc";
		//System.out.println(d.getDay()+"/"+ d.getMonth()+"/"+d.getYear());
		
		if(d1.equals(d2)){
			System.out.println("ok");
		}
		if(d1.equals(d3)==false){
			System.out.println("nop");
		}
		if(d1.equals(s)==false){
			System.out.println("nop");
		}
	}

}
