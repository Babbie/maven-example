package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Bab on 26-10-2016.
 */
public class SocketPanel extends JPanel {

    @Override
    public void paintComponent(Graphics g) {
        try {
            BufferedImage beer = ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("dikkestinkbeer.jpg"));
            g.drawImage(beer, 0, 0, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
