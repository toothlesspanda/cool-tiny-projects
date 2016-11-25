package spacegame;

/**
 * Controllable interface implemented by the Spaceship class. 
 */
public interface Controllable {
  /** Stop. */
  int STOP = 0;
  /** Left direction. */
  int LEFT = 1;
  /** Right direction. */
  int RIGHT = 2;
  /** Up direction. */
  int UP = 3;
  /** Down direction */
  int DOWN = 4;
  
  int BOMB = 5;
  /**
   * Set direction.
   * @param direction new direction.
   */
  void setDirection(int direction);
  
  /**
   * Get current direction.
   * @return current direction.
   */
  int getDirection();
}
