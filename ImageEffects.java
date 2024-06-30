
/* Jeff Sabol
 * ImageEffects.java - Contains static methods for image effects.
 */
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.util.Random;

public class ImageEffects {
    public static void invertColors(JLabel labelImage) {
        BufferedImage image = getBufferedImage(labelImage);
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int p = image.getRGB(i, j);
                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;
                r = 255 - r;
                g = 255 - g;
                b = 255 - b;
                p = (a << 24) | (r << 16) | (g << 8) | b;
                image.setRGB(i, j, p);
            }
        }
        updateImage(labelImage, image);
    }

    public static void randomizeColors(JLabel labelImage) {
        BufferedImage image = getBufferedImage(labelImage);
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int p = image.getRGB(i, j);
                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;
                r = (int) (Math.random() * 256);
                g = (int) (Math.random() * 256);
                b = (int) (Math.random() * 256);
                p = (a << 24) | (r << 16) | (g << 8) | b;
                if ((int) (Math.random() * 4 % 4) == 0)
                    image.setRGB(i, j, p);
            }
        }
        updateImage(labelImage, image);
    }

    public static void chromaticAberration(JLabel labelImage) {
        BufferedImage image = getBufferedImage(labelImage);
        BufferedImage newImage = new BufferedImage(image.getWidth() + 15, image.getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int p = image.getRGB(i, j);
                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;

                if (i < newImage.getWidth() / 2) {
                    b = Math.max(0, Math.min(255, b + 254));
                    p = (a << 24) | (r << 16) | (g << 8) | b;
                    if (i - 15 >= 0 && i < image.getWidth()) {
                        for (int k = 1; k <= 15; k++)
                            newImage.setRGB(i - k, j, p);
                    }
                } else {
                    r = Math.max(0, Math.min(255, r + 254));
                    p = (a << 24) | (r << 16) | (g << 8) | b;
                    newImage.setRGB(i + 15, j, p);
                }
            }
        }

        BufferedImage combinedImage = new BufferedImage(image.getWidth(), image.getHeight(),
        BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = combinedImage.createGraphics();
        g2d.drawImage(newImage, 0, 0, null);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        
        updateImage(labelImage, combinedImage);
    }

    public static void encodeSteganographicMessage(JFrame frame, JLabel labelImage) {
        // Implement encodeSteganographicMessage effect here
        BufferedImage sourceImage = getBufferedImage(labelImage);

        BufferedImage embeddedImage = null;
        BufferedImage embeddedImage1 = null;
        String mess = JOptionPane.showInputDialog("Enter a message to hide in the image");
        if (mess == null)
          return;

        embeddedImage = sourceImage.getSubimage(0, 0, sourceImage.getWidth(), sourceImage.getHeight());

        SteganoImgProcess sip = new SteganoImgProcess();
        embeddedImage1 = sip.encode(sourceImage, embeddedImage, sourceImage.getWidth(), sourceImage.getHeight(), mess);

        String[] options = { "Yes", "No" };
        int response = JOptionPane.showOptionDialog(null, "Do you want to save the image?", "Save Image",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
            null, options, options[0]);

        // Check the user's response
        if (response == 0) {
          // The user selected "Yes", so save the image
          JFileChooser fileChooser = new JFileChooser();
          fileChooser.showSaveDialog(frame);

          File f = fileChooser.getSelectedFile();
          try {
            if (f.exists())
              f.delete();
            ImageIO.write(embeddedImage, "png".toUpperCase(), f);
          } catch (Exception ex) {
          }
          System.out.println("Saving image...");
        } else {
          // The user selected "No" or closed the dialog

          System.out.println("Not saving image.");
        }

        updateImage(labelImage, embeddedImage1);
    }

    public static void decodeSteganographicMessage(JFrame frame, JLabel labelImage) {
        BufferedImage image = getBufferedImage(labelImage);

        SteganoImgProcess sip = new SteganoImgProcess();
        String vk = sip.decode(image, image.getWidth(), image.getHeight());
        JOptionPane.showMessageDialog(frame, vk);
    }

    public static void pixelScratch(JLabel labelImage) {
        BufferedImage image = getBufferedImage(labelImage);

        // Prompt the user to enter the value for vertical scratches
        String var1Input = JOptionPane.showInputDialog("Enter the amount of vertical scratches:");
        int vert = Integer.parseInt(var1Input);

        // Prompt the user to enter the value for horizontal scratches
        String var2Input = JOptionPane.showInputDialog("Enter the amount of horizontal scratches:");
        int hori = Integer.parseInt(var2Input);

        Random random = new Random();

        // get the dimensions of the image
        int width = image.getWidth();
        int height = image.getHeight();

        for (int x = 0; x < hori; x++) {
          // select a random row
          int row = random.nextInt(height);

          // select a random pixel in the row
          int column = random.nextInt(width);

          // get the pixel value at the selected position
          int pixel = image.getRGB(column, row);

          // generate a random length for the pixel color
          int length = random.nextInt(width - column);

          // set the pixel value for the specified length
          for (int i = column; i < column + length; i++) {
            image.setRGB(i, row, pixel);
          }

        }

        for (int v = 0; v < vert; v++) {
          // select a random column
          int column1 = random.nextInt(width);

          // select a random pixel in the column
          int row1 = random.nextInt(height);

          // get the pixel value at the selected position
          int pixel1 = image.getRGB(column1, row1);

          // generate a random length for the pixel color
          int length1 = random.nextInt(height - row1);

          // set the pixel value for the specified length
          for (int i = row1; i < row1 + length1; i++) {
            image.setRGB(column1, i, pixel1);
          }
        }

        updateImage(labelImage, image);
    }

    public static void gaussianBlur(JLabel labelImage) {
        BufferedImage image = getBufferedImage(labelImage);

        // BLUR
        float[] matrix = {
            1 / 16f, 1 / 8f, 1 / 16f,
            1 / 8f, 1 / 4f, 1 / 8f,
            1 / 16f, 1 / 8f, 1 / 16f
        };

        Kernel kernel = new Kernel(3, 3, matrix);

        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        image = op.filter(image, null);

        updateImage(labelImage, image);
    }

    public static void glitch(JLabel labelImage) {
        BufferedImage image = getBufferedImage(labelImage);

        float[] matrix = {
            -2, -1, 0,
            -1, 1, 1,
            0, 1, 2
        };

        Kernel kernel = new Kernel(3, 3, matrix);

        // Apply the convolution matrix to the image
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        image = op.filter(image, null);
        image = op.filter(image, null);

        updateImage(labelImage, image);
    }


    private static BufferedImage getBufferedImage(JLabel labelImage) {
        Icon icon = labelImage.getIcon();
        BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics gfx = image.createGraphics();
        icon.paintIcon(null, gfx, 0, 0);
        gfx.dispose();
        return image;
    }

    private static void updateImage(JLabel labelImage, BufferedImage image) {
        ImageIcon icon = new ImageIcon(image);
        labelImage.setIcon(icon);
        labelImage.revalidate();
        labelImage.repaint();
    }
}
