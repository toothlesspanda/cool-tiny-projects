package spacegame;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import spacegame.Coord2D;

public abstract class AbstractGameElement {
  private Coord2D position;
  private boolean active;
  private Image image;
  
  protected AbstractGameElement(Coord2D initialPosition, String imageFilename) { 
    active = true;
    position = initialPosition;
    image = new ImageIcon(imageFilename).getImage();
  }
  
  public final void deactivate() {
    active = false;
  }
  
  public final boolean isActive() {
    return active;
  }
  
  public final Coord2D getPosition() {
    return position;
  }
 
  public final void setPosition(Coord2D pos) {
    position = pos;
  }
   
  public void draw(Graphics g) {
    int x = position.x * GameConstants.ICON_SIZE;
    int y = GameConstants.WINDOW_SIZE - (position.y * GameConstants.ICON_SIZE) - GameConstants.ICON_SIZE;
    // System.out.println(getClass() + " " + position + " " + x + " " + y);
    g.drawImage(image, x, y, null);   
  }
  
  public abstract void step();
  
}
