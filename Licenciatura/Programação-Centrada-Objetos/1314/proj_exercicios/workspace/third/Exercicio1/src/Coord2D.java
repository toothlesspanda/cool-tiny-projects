import java.util.Scanner;

public class Coord2D {
	
	 double x;
	 double y;
	
	public Coord2D(double x0, double y0){
		 x=x0;
		 y=y0;
		
	}

	public double getX(){   	
		return x;
	} 

	public double getY(){
		return y;
	}
	public  String toString() {
	
		return "("+x+","+y+")";
      
    }
	public void add(Coord2D other){
		x= x + other.x;
		y= y + other.y;
	}
	
	public void mul(double a){
		x=x*a;
		y=y*a;
	}
	
	public double distance(Coord2D other){
		return Math.sqrt(Math.pow(x-other.x,2) + Math.pow(y-other.y,2));
		
	}

	public static final Coord2D ORIGIN = new Coord2D(0,0);
	
	
	public static Coord2D middle(Coord2D[] v){
		Coord2D m = new Coord2D(0,0);
		for(int i=0; i < v.length;i++){
			m.add(v[i]);
		}
		m.mul(1.0/v.length);
		
		return m;
	}
	
	public static void main(String[] args) {
		Coord2D c1= new Coord2D(1,1);
		Coord2D c2= new Coord2D(2,2);
		c1.add(c2);
		c2.mul(3.0);
		Coord2D[] v= {c1,c2};
		
		Coord2D m = Coord2D.middle(v);
		
		System.out.println(c1);
		System.out.println(c2);
		System.out.println(m);
		System.out.println("--------------");
		System.out.println(c1.getX()+ " "+ c1.getY());
		System.out.println(c2.getX()+ " " + c2.getY());
		System.out.println("--------------");
		System.out.println(c1.toString());
		System.out.println(c2.toString());
		System.out.println(m.toString());

	}

}
