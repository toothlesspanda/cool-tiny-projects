package pco;

public class Coord2D {

  // Fields are intentionally package-protected.
  
  /** X axis coordinate. */
  final int x;
  /** Y axis coordinate. */
  final int y;
  
  public Coord2D(int x, int y) {
    this.x = x; 
    this.y = y;
  }
  
  @Override
  public boolean equals(Object o) {
    if(o instanceof Coord2D){   //sem o if teria de se criar uma excepção
    	Coord2D outro = (Coord2D) o;
    	return x == outro.x && y== outro.y;
    }	  
    return false;
  }
  
  public Coord2D add(Coord2D c) {
	 return new Coord2D(this.x + c.x,this.y + c.y);
  }
}
