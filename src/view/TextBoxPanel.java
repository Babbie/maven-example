package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Sebastian on 29-12-2016.
 */
public class TextBoxPanel extends JPanel {
    public TextBoxPanel() {
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        JLabel labelLane1 = new JLabel("testAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        labelLane1.setBounds(0,0,400,30);
        labelLane1.paint(g);

    }
}
