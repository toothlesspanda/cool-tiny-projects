package pco;

public class Date extends java.lang.Object {
	private int day;
	private int month;
	private int year;
	
	public Date(int d, int m, int y){
		day=d;
		month=m;
		year=y;
	}

	public int getDay(){
		return day;
	}
	public int getMonth(){
		return month;
	}
	
	public int getYear(){
		return year;
	}
	
	public String toString(){
		return day+"-"+month+ "-"+year;

	}
	
	public boolean equals(Object other){
		boolean eq = false;
		if (other instanceof Date){
			Date d = (Date) other;
			eq = (day == d.day) && (month==d.month) && (year==d.year);
 		}
		return eq;
	}
	
	public static void main(String[] args){
		Date d= new Date(17,10,2013);

		System.out.println(d.getDay()+"/"+ d.getMonth()+"/"+d.getYear());
		System.out.println(d.toString());
	}

}
