import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class FileOpenMenu {
  public static void main(String[] args) {
    // Change theme to match system
    JFrame.setDefaultLookAndFeelDecorated(true);
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      e.printStackTrace();
    }

    JFrame frame = new JFrame("Image Glitcher");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Create a file open menu
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenuItem openMenuItem = new JMenuItem("Open Image");
    JMenuItem helpItem = new JMenuItem("Help");
    menuBar.add(fileMenu);
    menuBar.add(helpItem);
    fileMenu.add(openMenuItem);

    // Add an action listener to the open menu item
    openMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // Show a file open dialog
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(frame);

        // Get the selected file
        File file = fileChooser.getSelectedFile();

        // Open the file if it is not null
        if (file != null) {
          displayJpg(file);
        }
      }
    });

    helpItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // Explain how to use the program
        JOptionPane.showMessageDialog(frame, "In the menu: File > Open Image > select your image\n" +
            "For a glitch effect after uploading your file do\n" +
            "In the menu: Effect > select your desired effect");
      }
    });

    frame.setJMenuBar(menuBar);
    frame.setSize(1000, 700);
    frame.setVisible(true);
  }

  public static void displayJpg(File file) { // TODO: Add proper error handling when incorrect file
                                             // Such as an error popup alerting the user what is wrong
    if (file != null) {
      try {
        BufferedImage image = ImageIO.read(file);
        JFrame frame = new JFrame();
        frame.setSize(image.getWidth(), image.getHeight());
        JLabel label = new JLabel(new ImageIcon(image)); // TODO: Display the image on the same window as main
        frame.add(label);
        frame.setVisible(true);
      } catch (IOException e) {
        System.out.println("Error: unable to open JPG file");
      }
    }
  }

}
