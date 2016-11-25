package pco;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;

public class BlueAndRedLines {

  public static void main(String[] args) {

    try {
      String filename = "BlueAndRedLines.png";
      ImageFile image = new ImageFile(filename, 300, 150, true, Color.WHITE);
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
        line.move(new Coord2D(-75, 0));
        line.draw(g);
        line.setColor(Color.RED);
        line.move(new Coord2D(150, 0));
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
