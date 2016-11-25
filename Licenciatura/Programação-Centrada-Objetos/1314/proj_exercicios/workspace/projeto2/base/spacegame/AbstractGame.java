package spacegame;

import java.awt.Graphics;

import spacegame.Alien;
import spacegame.Coord2D;
import spacegame.Spaceship;
import spacegame.Star;

/**
 * Base class for game implementation.
 */
public abstract class AbstractGame {

	protected AbstractGame() {

	}


	public abstract Spaceship getSpaceShip();

	public abstract Star[] getStars();

	public abstract Alien[] getAliens();

	//public abstract Bomb[] getBombs();

	public abstract int activeStars(); 
	
	public abstract int activeAliens();

	//public abstract int activeBombs();

	public abstract boolean gameOver();


	public abstract String getGameStatus();

	/**
	 * Game step.
	 * This updates the position of all game elements 
	 * and detects collisions between the spaceship and other elements.
	 */
	public void step() {
		Spaceship spaceship = getSpaceShip();
		Coord2D sPos = spaceship.getPosition();
		spaceship.step();
		Coord2D ePos = spaceship.getPosition();



		for (Star star : getStars()) {
			if (star.isActive()) {
				star.step();
				if (star.getPosition().equals(sPos) || star.getPosition().equals(ePos) ) {
					star.deactivate();
				}
			}
		}
		for (Alien b : getAliens()) {
			if (b.isActive()) {
				b.step();
				if (b.getPosition().equals(sPos) || b.getPosition().equals(ePos)) {
					spaceship.deactivate();
				}
				/*for(Bomb bo : getBombs()){
					if(bo.isActive()){
						if(bo.getPosition().equals(b.getPosition())){
							b.deactivate();
							bo.deactivate();
						}
					}
				}*/
			}
		}

	}
	/**
	 * Draw the game elements.
	 * @param g Graphics object.
	 */
	public void draw(Graphics g) {
		for (Star star : getStars()) {
			if (star.isActive()) {
				star.draw(g);
			}
		}
		for (Alien b : getAliens()) {
			if (b.isActive()) {
				b.draw(g);
			}
		}
		Spaceship spaceship = getSpaceShip();
		if (spaceship.isActive()) {
			spaceship.draw(g);
		}
		
		/*for(Bomb b : getBombs()){
			b.draw(g);
		}*/
	}
}
