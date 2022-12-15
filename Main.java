// JeffSabol
// A program to add glitch effects to images

// Known bugs:
// 1. For the same image: open > close > open results in a blank image until you load a different image first

// TODO:
// Add more effects
// Add error handling for incorrect file types
// Give file extension selection option

import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Main {
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

    // Menu bar choices
    menuBar.add(fileMenu);
    menuBar.add(helpItem);
    fileMenu.add(openMenuItem);
    fileMenu.add(clearImageItem);
    fileMenu.add(saveImageItem);
    menuBar.add(effects);
    effects.add(invertColors);
    effects.add(randomColors);

    // Open Image and display on screen
    openMenuItem.addActionListener(new ActionListener() { // TODO: Add proper error handling when incorrect file is
                                                          // selected
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
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

    randomColors.addActionListener(new ActionListener(){
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
            r = (int)(Math.random() * 256);
            g = (int)(Math.random() * 256);
            b = (int)(Math.random() * 256);
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

    frame.setJMenuBar(menuBar);
    frame.setSize(1000, 700);
    frame.setVisible(true);
  }
}