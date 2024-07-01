package com.jeffsabol.databender.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import com.jeffsabol.databender.DataBender;

public class FileMenu {
    private JMenu menu;
    private JFrame frame;
    private JLabel labelImage;

    public FileMenu(JFrame frame, JLabel labelImage) {
        this.frame = frame;
        this.labelImage = labelImage;
        menu = new JMenu("File");

        addMenuItems();
    }

    private void addMenuItems() {
        JMenuItem openMenuItem = new JMenuItem("Open Image");
        JMenuItem clearImageItem = new JMenuItem("Clear Image");
        JMenuItem saveImageItem = new JMenuItem("Save Image");

        openMenuItem.setIcon(new ImageIcon(DataBender.getPath("com", "jeffsabol", "databender", "ui", "icons", "Open.png").toString())); // from https://www.flaticon.com/free-icon/open-folder-with-document_32743
        clearImageItem.setIcon(new ImageIcon(DataBender.getPath("com", "jeffsabol", "databender", "ui", "icons", "Wipe.png").toString())); // from http://www.onlinewebfonts.com/icon
        saveImageItem.setIcon(new ImageIcon(DataBender.getPath("com", "jeffsabol", "databender", "ui", "icons", "Save.png").toString()));

        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openImage();
            }
        });

        clearImageItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearImage();
            }
        });

        saveImageItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveImage();
            }
        });

        menu.add(openMenuItem);
        menu.add(clearImageItem);
        menu.add(saveImageItem);
    }

    private void openImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(frame);
        File file = fileChooser.getSelectedFile();
        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(file));
            labelImage.setIcon(icon);
            frame.add(labelImage);
            frame.setSize(icon.getIconWidth(), icon.getIconHeight());
            frame.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearImage() {
        frame.getContentPane().removeAll();
        frame.repaint();
    }

    private void saveImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showSaveDialog(frame);
        File file = fileChooser.getSelectedFile();
        try {
            Icon icon = labelImage.getIcon();
            BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics gfx = image.createGraphics();
            icon.paintIcon(null, gfx, 0, 0);
            gfx.dispose();
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JMenu getMenu() {
        return menu;
    }
}
