package pco;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;

public class BlueLines {

  public static void main(String[] args) {

    try {
      String filename = "BlueLines.png";
      ImageFile image = new ImageFile(filename, 150, 150, true, Color.WHITE);
      Graphics g = image.getGraphics();

      for (int i = 1; i <= 14; i++ ) {
        int d = i * 5;
        Coord2D[] points = {
            new Coord2D(-d, -d),
            new Coord2D(-d,  d),
            new Coord2D( d,  d),
            new Coord2D( d, -d),

        };
        Line line = new Line(Color.BLUE, points);
        line.draw(g);
      }

      image.save();
      System.out.println("See " + filename + " for results");
    } catch(IOException e) {
      System.err.println("I/O exception :(");
      e.printStackTrace();
    }
  }
}
