import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.*;

public class DataBender {
    private JFrame frame;
    private JLabel labelImage;

    public void start() {
        frame = new JFrame("Data Bender");
        frame.setIconImage(new ImageIcon(getPath("Glitch.png").toString()).getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        labelImage = new JLabel();

        setupMenu();
        frame.setVisible(true);
    }

    private void setupMenu() {
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        FileMenu fileMenu = new FileMenu(frame, labelImage);
        EffectsMenu effectsMenu = new EffectsMenu(frame, labelImage);

        menuBar.add(fileMenu.getMenu());
        menuBar.add(effectsMenu.getMenu());
    }

    public static Path getPath(String... parts) {
        return Paths.get("", parts);
    }
}
