package view;

import model.Circle;
import model.CircleList;
import model.Lane;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;


/**
 * Created by Sebastian on 20-11-2016.
 */
public abstract class CirclePanel extends JPanel implements ActionListener {
    protected String bgName;

    public CirclePanel() {
        Timer timer = new Timer(16, this);
        timer.start();
        new Circle(true, Lane.First, "HOI");
    }

    public static void drawCenteredText(Graphics g, int x, int y, float size, String text) {
        // Create a new font with the desired size
        Font newFont = g.getFont().deriveFont(size);
        g.setFont(newFont);
        // Find the size of string s in font f in the current Graphics context g.
        FontMetrics fm = g.getFontMetrics();
        Rectangle2D rect = fm.getStringBounds(text, g);

        int textHeight = (int) (rect.getHeight());
        int textWidth = (int) (rect.getWidth());

        // Find the top left and right corner
        int cornerX = x - (textWidth / 2);
        int cornerY = y - (textHeight / 2) + fm.getAscent();

        g.drawString(text, cornerX, cornerY);  // Draw the string.
    }

    @Override
    public void paintComponent(Graphics g) {
        try {
            super.paintComponent(g);
            CircleList.updateCircles();
            BufferedImage bg = ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream(bgName));
            g.drawImage(bg, 0, 0, null);
            for (Circle circle : CircleList.getCircleList()) {
                g.setColor(Color.RED);
                g.fillOval((circle.getX() + (circle.getRadius()/2)), (circle.getY() + (circle.getRadius()/2)), circle.getRadius(), circle.getRadius());
                g.setColor(Color.BLACK);
                g.drawOval((circle.getX() + (circle.getRadius()/2)), (circle.getY() + (circle.getRadius()/2)), circle.getRadius(), circle.getRadius());
                drawCenteredText(g, circle.getCenterX()+(circle.getRadius()/2), circle.getCenterY()+(circle.getRadius()/2), 30f, circle.getText());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
