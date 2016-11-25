package spacegame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.SwingUtilities;

import spacegame.Game;

public class Program {
  /**
   * Main program.
   * @param args The game data file must be supplied.
   */
  public static void main(String[] args) {
    if (args.length != 1) {
      System.err.println("Game data file must be given.");
      return;
    }
    Scanner scanner = null;
    Game game = null;
    try {
      scanner = new Scanner(new File(args[0]));
      game = new Game(scanner);
    } 
    catch (FileNotFoundException e) {
      System.err.println(args[0] + ": file not found");
      return;
    } 
    finally {
      if (scanner != null)
        scanner.close();
    }
    GUI gui = new GUI("Space game", game);
    SwingUtilities.invokeLater(gui);
  }  
}
