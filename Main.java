// JeffSabol
// A program to add glitch effects to images

// Known bugs:
// 1. For the same image: open > close > open results in a blank image until you load a different image first
// 2. Cannot save steganographic images via the save button (low quality work around was added to open a save dialog after the message is hidden)
// 3. Steganography only works with .jpg

// TODO:
// Fix or remove the help menu. (It's damn near useless anyways)
// Allow user to specify randomize pixel color percentage
// Apply more OOP principles
//    move all the ActionListeners to their own respective classes
//    move menubar to it's own class
// test to see if steganography works with other file types
// sort effects by file type?
// Add more effects
//    Convert image to ascii art
//        (allows user to save as txt file)
//    Fractal generator
//        (takes image input as a seed and will always generate the same fractal per image)
// Give file extension selection option (tried not sure how) instead of user manually entering them
// Add additional option to let random pixel color to ignore white pixels (maybe different shades of white could screw this up)
// Make chromatic aberration work with other file types besides transparent PNGs
//    (if direction = x move picture left by x, etc)
//    Let the user select colors for chromatic aberration

import java.awt.event.*;
import java.io.*;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class Main extends JFrame {

  public static void main(String[] args) {
    final JLabel labelImage = new JLabel();
    // Change theme to match system
    JFrame.setDefaultLookAndFeelDecorated(true);
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      e.printStackTrace();
    }

    JFrame frame = new JFrame("Data Bender");
    frame.setIconImage(new ImageIcon("Glitch.png").getImage());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Create a file open menu
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenuItem openMenuItem = new JMenuItem("Open Image");
    JMenuItem clearImageItem = new JMenuItem("Clear Image");
    JMenuItem saveImageItem = new JMenuItem("Save Image");
    JMenu helpItem = new JMenu("Help");
    JMenu effects = new JMenu("Effects");
    JMenuItem invertColors = new JMenuItem("Invert Colors");
    JMenuItem randomColors = new JMenuItem("Randomize Colors");
    JMenuItem chromaticAberration = new JMenuItem("Chromatic Aberration (trans PNG only) ");
    JMenuItem steganography = new JMenuItem("Encode Steganographic Message (JPG only)");
    JMenuItem decodeSecretMessage = new JMenuItem("Decode Steganographic Message");
    JMenuItem pixelScratch = new JMenuItem("Pixel Scratch image");

    // Menu bar choices
    menuBar.add(fileMenu);
    fileMenu.add(openMenuItem);
    fileMenu.add(clearImageItem);
    fileMenu.add(saveImageItem);
    menuBar.add(effects);
    effects.add(invertColors);
    effects.add(randomColors);
    effects.add(chromaticAberration);
    effects.add(steganography);
    effects.add(decodeSecretMessage);
    effects.add(pixelScratch);
    menuBar.add(helpItem);

    // Add icons
    openMenuItem.setIcon(new ImageIcon("./IconImages/Open.png")); // from
                                                                  // https://www.flaticon.com/free-icon/open-folder-with-document_32743
                                                                  // (website told me to add attribution)
    saveImageItem.setIcon(new ImageIcon("./IconImages/Save.png"));
    clearImageItem.setIcon(new ImageIcon("./IconImages/Wipe.png")); // from http://www.onlinewebfonts.com/icon (website
                                                                    // told me to add attribution)
    invertColors.setIcon(new ImageIcon("./IconImages/Inverse.png"));
    randomColors.setIcon(new ImageIcon("./IconImages/Random.png"));
    chromaticAberration.setIcon(new ImageIcon("./IconImages/CB.png"));
    steganography.setIcon(new ImageIcon("./IconImages/Hide.png"));
    decodeSecretMessage.setIcon(new ImageIcon("./IconImages/Show.png"));
    pixelScratch.setIcon(new ImageIcon("./IconImages/Scratch.png"));

    // Open Image and display on screen
    openMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("jfif", "jpeg", "png", "jpg", "gif",
            "bmp", "JPG", "JPEG", "PNG", "GIF", "BMP");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(imageFilter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.showOpenDialog(frame);
        File file = fileChooser.getSelectedFile();
        try {
          ImageIcon icon = new ImageIcon(ImageIO.read(file));
          labelImage.setIcon(icon);
          frame.add(labelImage);
          frame.setSize(icon.getIconWidth(), icon.getIconHeight());
          frame.setVisible(true);
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    });

    // Clear Image from screen (I don't have any better way yet)
    clearImageItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        frame.getContentPane().removeAll();
        frame.repaint();
      }
    });

    // Save the image on screen
    saveImageItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showSaveDialog(frame);

        File file = fileChooser.getSelectedFile();
        try {
          Icon icon = labelImage.getIcon();
          BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(),
              BufferedImage.TYPE_INT_ARGB);
          Graphics gfx = image.createGraphics();
          icon.paintIcon(null, gfx, 0, 0);
          gfx.dispose();
          ImageIO.write(image, "png", file);
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    });

    // open basic help instructions
    helpItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(frame, "In the menu: File > Open Image > select your image\n" +
            "For a glitch effect after uploading your file do\n" +
            "In the menu: Effect > select your desired effect");
      }
    });

    invertColors.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Icon icon = labelImage.getIcon();
        BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics gfx = image.createGraphics();
        icon.paintIcon(null, gfx, 0, 0);
        gfx.dispose();
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
        // erase old picture
        frame.getContentPane().removeAll();
        frame.repaint();

        // need to convert to ImageIcon now
        ImageIcon ic = new ImageIcon(image);
        labelImage.setIcon(ic);
        frame.add(labelImage);
        frame.setVisible(true);
      }
    });

    randomColors.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Icon icon = labelImage.getIcon();
        BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics gfx = image.createGraphics();
        icon.paintIcon(null, gfx, 0, 0);
        gfx.dispose();
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
        // erase old picture
        frame.getContentPane().removeAll();
        frame.repaint();

        // need to convert to ImageIcon now
        ImageIcon ic = new ImageIcon(image);
        labelImage.setIcon(ic);
        frame.add(labelImage);
        frame.setVisible(true);
      }
    });

    chromaticAberration.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Icon icon = labelImage.getIcon();
        BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics gfx = image.createGraphics();
        icon.paintIcon(null, gfx, 0, 0);
        gfx.dispose();

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

        // combined the images
        BufferedImage combinedImage = new BufferedImage(image.getWidth(), image.getHeight(),
            BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = combinedImage.createGraphics();
        g2d.drawImage(newImage, 0, 0, null);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        // erase old picture
        frame.getContentPane().removeAll();
        frame.repaint();

        // need to convert to ImageIcon now
        ImageIcon ic = new ImageIcon(combinedImage);
        labelImage.setIcon(ic);
        frame.add(labelImage);
        frame.setVisible(true);
      }
    });

    // Use steganography to hide a message in the image via the LSB
    // ENCODING AND DECODING CREDIT TO https://github.com/vishal-kataria/ <3 thank
    // you so much
    // works with JPG, GIF
    steganography.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Icon icon = labelImage.getIcon();
        BufferedImage sourceImage = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(),
            BufferedImage.TYPE_INT_ARGB);
        Graphics gfx = sourceImage.createGraphics();
        icon.paintIcon(null, gfx, 0, 0);
        gfx.dispose();

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

        // erase old picture
        frame.getContentPane().removeAll();
        frame.repaint();

        // need to convert to ImageIcon now
        ImageIcon ic = new ImageIcon(embeddedImage1);
        labelImage.setIcon(ic);
        frame.add(labelImage);
        frame.setVisible(true);

      }
    });

    // Decodes the steganographic message hidden in the image
    decodeSecretMessage.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Icon icon = labelImage.getIcon();
        BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics gfx = image.createGraphics();
        icon.paintIcon(null, gfx, 0, 0);
        gfx.dispose();

        SteganoImgProcess sip = new SteganoImgProcess();
        String vk = sip.decode(image, image.getWidth(), image.getHeight());
        JOptionPane.showMessageDialog(frame, vk);

        // erase old picture
        frame.getContentPane().removeAll();
        frame.repaint();

        // need to convert to ImageIcon now
        ImageIcon ic = new ImageIcon(image);
        labelImage.setIcon(ic);
        frame.add(labelImage);
        frame.setVisible(true);
      }
    });

    // Applies pixel scratching to the image
    pixelScratch.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Icon icon = labelImage.getIcon();
        BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics gfx = image.createGraphics();
        icon.paintIcon(null, gfx, 0, 0);
        gfx.dispose();

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

        }

        // erase old picture
        frame.getContentPane().removeAll();
        frame.repaint();

        // need to convert to ImageIcon now
        ImageIcon ic = new ImageIcon(image);
        labelImage.setIcon(ic);
        frame.add(labelImage);
        frame.setVisible(true);
      }
    });

    frame.setJMenuBar(menuBar);
    frame.setSize(1000, 700);
    frame.setVisible(true);
  }

}