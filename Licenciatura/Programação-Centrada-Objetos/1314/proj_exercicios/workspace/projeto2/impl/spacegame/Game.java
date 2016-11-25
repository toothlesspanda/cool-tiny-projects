package spacegame;

import java.util.Scanner;


public class Game extends AbstractGame {

  private Star[] starsG;
  private Alien[] aliensG;
 //private Bomb[] bombsG;
  private int num_stars;
  private int num_aliens;
 // private int num_bombs;
  private Spaceship spaceship;
  /**
   * leitura do ficheiro dado
   * @param s
   */
  public Game(Scanner s) {
  
	
	int x=0,y=0; /*spaceship*/
	int num_star=0; /*numero estrelas*/
	int xS=0,yS=0,stepS=0;/*posicao e numero de passos da estrela*/
	char posS; /*posicao V ou H da estrela*/
	int num_alien=0;
	int xA=0, yA=0, stepX=0, stepY=0;
	Star star;
	Alien alien;

    
    x=s.nextInt();
    y=s.nextInt();
    spaceship = new Spaceship(new Coord2D(x,y));
    num_star=s.nextInt();
    starsG=new Star[num_star];
    
    for(int i=0; i < num_star;i++){
    	xS=s.nextInt();
    	yS= s.nextInt();
    	posS= s.next().charAt(0);
    	stepS= s.nextInt();
    	star=new Star(new Coord2D(xS,yS),posS,stepS);
    	starsG[i]=star;
    }
   
    num_stars=num_star;
    
    num_alien=s.nextInt();
    aliensG= new Alien[num_alien];
    for(int k=0; k<num_alien;k++){
    	xA=s.nextInt();
    	yA=s.nextInt();
    	stepX=s.nextInt();
    	stepY=s.nextInt();
    	alien=new Alien(new Coord2D(xA,yA),stepX,stepY);
    	aliensG[k] =alien;
    }
   
    num_aliens=num_alien;
    //num_bombs=5;
    //bombsG= new Bomb[num_bombs];
    
   
    
  }
  /**
   * @return a nave
   */
  @Override
  public Spaceship getSpaceShip() {
    return spaceship;
  }
 /**
  * @return vector de estrelas
  */
  @Override
  public Star[] getStars() { 
    return starsG;
  }

  /**
   * @return vector de Aliens
   */
  @Override
  public Alien[] getAliens() {
    return aliensG;
  }

 /**
  * numero de aliens activos
  */
  public int activeAliens(){
	  int activos=0;
	  for(int i=0; i<num_aliens ;i++){
		  activos++;
	  }
	  return activos;
  }
  
  /**
   * numero de estrelas activas
   */
  @Override
  public int activeStars() {
	int activas=0;
	for(int i =0; i <num_stars;i++){
		if(starsG[i].isActive()){
			activas++;
		}
	}
	
    return activas;
  }
  

  /*public Bomb[] getBombs(){
	  return bombsG;
  }*/
  
  /*public int activeBombs(){
	  int activas=0;
		for(int i =0; i <num_bombs;i++){
			if(bombsG[i].isActive()){
				activas++;
			}
		}
		
	    return activas;
  }*/
  /**
   * O jogo acaba se as estrelas ficam a zero
   * Ou se a spaceship choca com um alien
   */
  @Override
  public boolean gameOver() {
	  
    if(activeStars()==0 || !spaceship.isActive()){
    	return true;
    }
    return false;
  }

  @Override
  public String getGameStatus() {
	  String s;	 
	  Coord2D space = spaceship.getPosition();
	  if(!spaceship.isActive()){
		  s= space+" Aliens= "+activeAliens()+" Stars= "+activeStars()+ "GAME OVER ";
	  }
	  if(activeStars()==0){
		 s=space+" Aliens= "+activeAliens()+" Stars= "+activeStars()+" YOU WIN ";
	  }else{
		 s=space+" Aliens= "+activeAliens()+" Stars= "+activeStars();
		}
    return s;
  }
}
