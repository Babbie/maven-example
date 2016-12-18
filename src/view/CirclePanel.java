package view;

import model.Circle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * Created by Sebastian on 20-11-2016.
 */
public class CirclePanel extends JPanel implements ActionListener{
    private Timer timer;
    private ArrayList<Circle> circleList;

    public CirclePanel() {
        circleList = new ArrayList<>();
        timer = new Timer(16, this);
        timer.start();
        Circle circle = new Circle(100, 100, 0, 0,100,-4, "Halloo");
        addCircle(circle);
    }

    public void addCircle(Circle circle){
        circleList.add(circle);
    }

    public void actionPerformed(ActionEvent evt){
        updateCircle();
    }

    public void updateCircle() {
        Iterator<Circle> circleListIterator = circleList.iterator();
        while (circleListIterator.hasNext()) {

            repaint();
        }
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
            BufferedImage beer = ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("dikkestinkbeer.jpg"));
            g.drawImage(beer, 0, 0, null);
            for (Circle circle: circleList){
                g.setColor(Color.RED);
                g.fillOval(circle.getX(),circle.getY(),circle.getRadius(),circle.getRadius());
                g.setColor(Color.BLACK);
                g.drawOval(circle.getX(),circle.getY(),circle.getRadius(),circle.getRadius());
                drawCenteredText(g, circle.getCenterX(), circle.getCenterY(), 30f, circle.getText());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
