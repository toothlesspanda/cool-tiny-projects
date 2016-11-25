package pco;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;

/**
 * Image file.
 * 
 * @author Eduardo Marques, DI/FCUL, 2013
 */
public final class ImageFile {
  /** Actual image. */
  private final BufferedImage image;
  /** Graphics object associated to image. */
  private final Graphics      graphics;
  /** Output file. */
  private final File          file;

  /**
   * Create an image with a given width and height.
   * 
   * @param filename
   *          File name.
   * @param w
   *          Image width.
   * @param h
   *          Image height.
   * @param originAtCenter
   *          Flag indicating if origin should be center of the image.
   * @param background
   *          Background color for image.
   * @throws IOException
   *           if an I/O error ocurrs
   */
  public ImageFile(String filename, int w, int h, boolean originAtCenter,
      Color background) throws IOException {
    // Create file handle.
    file = new File(filename);

    // Create actual image.
    image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

    // Get graphics handle.
    graphics = image.getGraphics();

    // Fill the image with the background color.
    graphics.setColor(background);
    graphics.fillRect(0, 0, w, h);

    if (originAtCenter) {
      // Set (0,0) as the center of the image.
      graphics.translate(w / 2, h / 2);
      ((Graphics2D) graphics).scale(1, -1);
    }
  }

  /**
   * Get graphics object associated to this image.
   * 
   * @return Graphics object.
   */
  public Graphics getGraphics() {
    return graphics;
  }

  /**
   * Save image to file.
   * 
   * @throws IO
   *           Exception if an error occurs.
   */
  public void save() throws IOException {
    String basename = file.getName();
    String extension = basename.substring(basename.lastIndexOf('.') + 1);
    ImageIO.write(image, extension, file);
  }
}
