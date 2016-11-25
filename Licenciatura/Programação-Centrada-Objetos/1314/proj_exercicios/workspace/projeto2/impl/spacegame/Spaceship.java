package spacegame;


public class Spaceship  extends AbstractGameElement implements Controllable {


  private int d;
  /**
   * inicializa o tipo Spaceship
   * @param initialPos
   */
  public Spaceship(Coord2D initialPos) {
    super(initialPos, "spaceship.png");
   
  }

  /*+
   * Movimenta a nave 
   * consoante a tecla que o utilizador introduz´
   * 
   * @see spacegame.AbstractGameElement#step()
   */
  @Override
  public void step() {
    // Get current position
    Coord2D c = getPosition();
    int direct = getDirection();
    
   if((c.x>0 && c.x<24 ) && (c.y>0 && c.y<24)){
	   if(direct==4){
    	c = new Coord2D(c.x,c.y-1);
	   }else if(direct==3){
    	c= new Coord2D(c.x,c.y+1);
    	
	   }else if(direct==2){
		c= new Coord2D(c.x+1,c.y);
	   }else if(direct==1){
		c= new Coord2D(c.x-1,c.y);	
	   }else if(direct==0){
    	c= new Coord2D(c.x,c.y);
	   }
   } 
    
    // Update position
    setPosition(c);    
  }

  /*+
   * (non-Javadoc)
   * @see spacegame.Controllable#setDirection(int)
   */
  @Override
  public void setDirection(int direction) {
	  d=direction;
  }


  /*+
   * (non-Javadoc)
   * @see spacegame.Controllable#getDirection()
   */
  @Override
  public int getDirection() {
	  return d;
  }
}
