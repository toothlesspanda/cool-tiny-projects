package spacegame;

public class Alien extends AbstractGameElement {

  private int stepsX;
  private int stepsY;
  private Coord2D ini;
  
  /**
   * 
   * inicializa o tipo Alien
   * @param initialPos
   * @param stepX
   * @param stepY
   * 
   */
  public Alien(Coord2D initialPos,int stepX, int stepY) {
    super(initialPos, "alien.png");
    stepsX=stepX;
    stepsY=stepY;
    ini = new Coord2D(initialPos.x,initialPos.y);
  }
  

  /*+
   * Movimento do alien num sentido rectangular
   * @see spacegame.AbstractGameElement#step()
   */
  @Override
  public void step() {
    // Get current position
    Coord2D c = getPosition();
    Coord2D add = new Coord2D(ini.x+stepsX,ini.y+stepsY); //vector auxiliar de comparacao a atualizacao da posicao do alien
    
    if(c.x<add.x && c.y==ini.y){
    		c= new Coord2D(c.x+1,c.y);
    		setPosition(c);

    }else
    if(add.x==c.x && c.y<add.y){

    	  	c= new Coord2D(c.x,c.y+1);
    	  	setPosition(c);
    	
    }else
    if( c.y==add.y && c.x>ini.x ) {

    		 c= new Coord2D(c.x-1,c.y);
    		 setPosition(c);
    	
    }else
    if(ini.x==c.x && c.y>ini.y){

   		 c= new Coord2D(c.x,c.y-1);
   		setPosition(c);
    	
    }
    
        
  }
}
