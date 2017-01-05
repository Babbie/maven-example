package view;

import model.Circle;
import model.CircleList;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;


/**
 * Abstract class which takes care of drawing the circles
 */
public abstract class CirclePanel extends JPanel implements ActionListener {
    protected String bgName;

    //A timer which triggers repaint
    public CirclePanel() {
        Timer timer = new Timer(16, this);
        timer.start();
    }

    //Function which draws the text in the center of the circle
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

    /**
     * Method called by repaint which takes care of the actual painting.
     */
    @Override
    public void paintComponent(Graphics g) {
        try {
            super.paintComponent(g);
            //Call updateCircles() which updates the coordinates of all circles
            CircleList.updateCircles();
            //Draw background image
            BufferedImage bg = ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream(bgName));
            g.drawImage(bg, 0, 0, null);
            //Loop over all circles and draw them. The width of the circle depends on the length of the text it holds
            for (Circle circle : CircleList.getCircleList()) {
                g.setColor(Color.RED);
                g.fillOval((circle.getX() - (circle.getOvalWidth()/2)), (circle.getY() - (circle.getRadius()/2)), circle.getOvalWidth(), circle.getRadius());
                g.setColor(Color.BLACK);
                g.drawOval((circle.getX() - (circle.getOvalWidth()/2)), (circle.getY() - (circle.getRadius()/2)), circle.getOvalWidth(), circle.getRadius());
                drawCenteredText(g, circle.getX(), circle.getY(), 30f, circle.getText());
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
