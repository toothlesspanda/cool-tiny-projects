package spacegame;

public class Coord2D {

  public final int x;
  public final int y;

  public Coord2D(int x, int y) {
    this.x = x;
    this.y = y;
  }

  
  @Override
  public boolean equals(Object other) {  
    if(other instanceof Coord2D){
    	Coord2D coord = (Coord2D)other;
    	return x == coord.x && y == coord.y;
    }
    return false;
  }

  @Override
  public String toString() {
    return "("+x+","+y+")" ;
  }
  
  // TODO define other methods you find necessary here
}
