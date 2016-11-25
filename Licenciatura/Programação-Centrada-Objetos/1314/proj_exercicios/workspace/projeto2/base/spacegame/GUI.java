package spacegame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import spacegame.Game;
import spacegame.Spaceship;



@SuppressWarnings("serial")
public class GUI extends JFrame implements KeyListener, Runnable {

  /**
   * Game instance.
   */
  private final Game game;
  //private int contador;
  /**
   * Font for game status.
   */
  private static Font ARIAL_20 = new Font("Arial", Font.PLAIN, 20);
  
  /**
   * Constructor.
   * @param name Name of the application.
   */
  public GUI(String name, final Game game) {
    // Call superclass constructor.
    super(name);
    this.game = game;
    final Image background = new ImageIcon("background.jpg").getImage();

    JPanel panel = new JPanel() {
      @Override
      public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(background, 0, 0, null);
        game.draw(graphics);  
        char status[] = game.getGameStatus().toCharArray();
        graphics.setFont(ARIAL_20);
        graphics.drawChars(status, 0, status.length, 0, GameConstants.WINDOW_SIZE + 20);
      }
    };
    panel.setSize(GameConstants.WINDOW_SIZE, GameConstants.WINDOW_SIZE);
    add(panel, BorderLayout.CENTER);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(new Dimension(GameConstants.WINDOW_SIZE, GameConstants.WINDOW_SIZE + 50));
    setResizable(false);
    addKeyListener(this);

  }

  /**
   * Implementation of Runnable interface.
   */
  @Override
  public final void run() {
    // Make the frame visible (and that's it).
    setVisible(true);
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        try { 
          Thread.sleep(GameConstants.REFRESH_RATE); 
        } 
        catch(InterruptedException e) { 

        }
        if (!game.gameOver()) {
          game.step();
        }
        repaint(); 
        Toolkit.getDefaultToolkit().sync();
        SwingUtilities.invokeLater(this);
      }

    });
  }

  /**
   * Handler for key presses.
   * The method sends direction commands to the spaceship, while the game is not over.
   * @param e key event.
   */
  @Override
  public void keyPressed(KeyEvent e) {
    if(game.gameOver() == false) {
      int direction;
      //Bomb[] bomb=game.getBombs();
      
      switch (e.getKeyCode()) {
        case ' ':
          direction = Controllable.STOP;
          break;
        case KeyEvent.VK_LEFT:
          direction = Controllable.LEFT;
          break;
        case KeyEvent.VK_RIGHT:
          direction = Controllable.RIGHT;
          break;
        case KeyEvent.VK_UP:
          direction = Controllable.UP;
          break;
        case KeyEvent.VK_DOWN:
          direction = Controllable.DOWN;
          break;
        /*case KeyEvent.VK_Z:
        		bomb[contador]= new Bomb(game.getSpaceShip().getPosition());
        		contador++;
        	direction=Controllable.BOMB;
          break;*/
        default:
          direction = -1; 
          break;
      }
      if (direction != -1) {
        Spaceship s = game.getSpaceShip();
        s.setDirection(direction);
      }
    }
  }

  /**
   * Handler for key releases. 
   * Nothing is done by this method.
   * @param e key event.
   */
  @Override
  public final void keyReleased(KeyEvent e) {
    // DO NOTHING
  }
  
  /**
   * Handler for typed keys. 
   * Nothing is done by this method.
   * @param e key event.
   */
  @Override
  public final void keyTyped(KeyEvent e) { 
    // DO NOTHING
  }
}