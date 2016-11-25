package pco;

import java.awt.Graphics;
import java.awt.Color;

public class Line implements Colored, Movable, Drawable {
	private Color color;
	private Coord2D[] points;

	public Line(Color color, Coord2D[] points) {
		if( points.length<2){
			throw new InvalidLineException("Invalid number of points!");
		}
		this.color = color;
		this.points = points;
	}
	@Override
	public Color getColor(){
		return color;
	}
	
	@Override
	public void setColor(Color c){
		color = c;
	}
	
	@Override
	public void move( Coord2D coord){
		for(int i=0; i< points.length ;i++){
			points[i] = points[i].add(coord);

		}	
	}
	
	@Override
	public void draw(Graphics g){
		g.setColor(new Color(40,100,10));
		int[] xPoints= new int[points.length];  //vector preenchido com zeros
		int[] yPoints=new int[points.length];
		int nPoints=points.length;
		for(int i=0; i < nPoints; i++){
			xPoints[i] = points[i].x;
			yPoints[i] = points[i].y;
		}
		
		g.drawPolyline(xPoints,yPoints,nPoints);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Line) { // sem o if teria de se criar uma excepção
			Line outro = (Line) o;
			if (color.equals(outro.color)==false) { // aplica-se a funcao equals
				return false;
			}
			if (points.length != outro.points.length) {
				return false;
			}

			for (int i = 0; i < points.length; i++) {
				if (points[i].equals(outro.points[i]) == false) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		Coord2D a = new Coord2D(0, 0);
		Coord2D b = new Coord2D(0, 1);
		Coord2D c = new Coord2D(1, 0);
		Coord2D d = new Coord2D(1, 1);

		try{
		Line l = new Line(Color.black, new Coord2D[]{a});
		} catch(InvalidLineException e){
			System.out.println("excepção!");
			e.printStackTrace();
		}
		Line l1 = new Line(Color.BLUE, new Coord2D[] { a, b, c }); // new
																	// Color(0,0,225)
																	// inves de
																	// Color.Blue
																	// (mesma
																	// cor, mas
																	// objectos
																	// diferentes);
		Line l2 = new Line(Color.RED, new Coord2D[] { a, b, c });
		Line l3 = new Line(Color.BLUE, new Coord2D[] { b, c, d });
		Line l4 = new Line(Color.BLUE, new Coord2D[] { a, b, c });
		Line l5 = new Line(Color.BLUE, new Coord2D[] { a, b, c, d });

		System.out.println(l1.equals(l2));
		System.out.println(l1.equals(l3));
		System.out.println(l1.equals(l4));
		System.out.println(l1.equals(l5));
		String s= "ABC";
		System.out.println(l1.equals(s));

	}
}
