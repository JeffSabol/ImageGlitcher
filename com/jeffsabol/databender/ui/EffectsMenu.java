package com.jeffsabol.databender.ui;

import javax.swing.*;

import com.jeffsabol.databender.DataBender;
import com.jeffsabol.databender.effects.ImageEffects;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EffectsMenu {
    private JMenu menu;
    private JFrame frame;
    private JLabel labelImage;

    public EffectsMenu(JFrame frame, JLabel labelImage) {
        this.frame = frame;
        this.labelImage = labelImage;
        menu = new JMenu("Effects");

        addMenuItems();
    }

    private void addMenuItems() {
        JMenuItem invertColors = new JMenuItem("Invert Colors");
        JMenuItem randomColors = new JMenuItem("Randomize Colors");
        JMenuItem chromaticAberration = new JMenuItem("Chromatic Aberration (trans PNG only)");
        JMenuItem steganography = new JMenuItem("Encode Steganographic Message (JPG only)");
        JMenuItem decodeSecretMessage = new JMenuItem("Decode Steganographic Message");
        JMenuItem pixelScratch = new JMenuItem("Pixel Scratch image");
        JMenuItem gBlur = new JMenuItem("Gaussian Blur");
        JMenuItem glitch = new JMenuItem("Glitch");


        invertColors.setIcon(new ImageIcon(DataBender.getPath("com", "jeffsabol", "databender", "ui", "icons", "Inverse.png").toString()));
        randomColors.setIcon(new ImageIcon(DataBender.getPath("com", "jeffsabol", "databender", "ui", "icons", "Random.png").toString()));
        chromaticAberration.setIcon(new ImageIcon(DataBender.getPath("com", "jeffsabol", "databender", "ui", "icons", "CB.png").toString()));
        steganography.setIcon(new ImageIcon(DataBender.getPath("com", "jeffsabol", "databender", "ui", "icons", "Hide.png").toString()));
        decodeSecretMessage.setIcon(new ImageIcon(DataBender.getPath("com", "jeffsabol", "databender", "ui", "icons", "Show.png").toString()));
        pixelScratch.setIcon(new ImageIcon(DataBender.getPath("com", "jeffsabol", "databender", "ui", "icons", "Scratch.png").toString()));
        gBlur.setIcon(new ImageIcon(DataBender.getPath("com", "jeffsabol", "databender", "ui", "icons", "Blur.png").toString()));
        glitch.setIcon(new ImageIcon(DataBender.getPath("com", "jeffsabol", "databender", "ui", "icons", "Glitch.png").toString()));

        invertColors.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageEffects.invertColors(labelImage);
            }
        });

        randomColors.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageEffects.randomizeColors(labelImage);
            }
        });

        chromaticAberration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageEffects.chromaticAberration(labelImage);
            }
        });

        steganography.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageEffects.encodeSteganographicMessage(frame, labelImage);
            }
        });

        decodeSecretMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageEffects.decodeSteganographicMessage(frame, labelImage);
            }
        });

        pixelScratch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageEffects.pixelScratch(labelImage);
            }
        });

        gBlur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageEffects.gaussianBlur(labelImage);
            }
        });

        glitch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageEffects.glitch(labelImage);
            }
        });

        menu.add(invertColors);
        menu.add(randomColors);
        menu.add(chromaticAberration);
        menu.add(steganography);
        menu.add(decodeSecretMessage);
        menu.add(pixelScratch);
        menu.add(gBlur);
        menu.add(glitch);
    }

    public JMenu getMenu() {
        return menu;
    }
}
