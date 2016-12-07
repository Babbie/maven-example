package view;

import model.Circle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        addCircle(0,300,200,400,100,4, "Hoi");
        addCircle(100,100,0,0,100,-4, "Halloo");
    }

    //TODO: implement a method that draws a circle in the specified lane and with the specified text & direction, returning only when done
    public void sendCircle(int lane, String text, boolean outward) {

    }

    //TODO: implement a method that sets the status for a lane to the specified text
    public void setStatus(int lane, String text) {

    }

    public void updateCircle() {
        Iterator<Circle> circleListIterator = circleList.iterator();
        while (circleListIterator.hasNext()) {
            Circle circle = circleListIterator.next();
            if (circle.getSpeed() >= 0) {
                if (circle.getX() + circle.getSpeed() >= circle.getGoalX()) {
                    circleListIterator.remove();
                } else {
                    circle.setX(circle.getX() + circle.getSpeed());
                }
            } else {
                if (circle.getX() + circle.getSpeed() <= circle.getGoalX()) {
                    circleListIterator.remove();
                } else {
                    circle.setX(circle.getX() + circle.getSpeed());
                }
            }
            repaint();
        }
    }

    public void addCircle(int startX, int startY, int toX, int toY, int radius, int speed, String text){
        Circle circle = new Circle(startX, startY, toX, toY,radius,speed, text);
        circleList.add(circle);
    }

    public static void drawCenteredText(Graphics g, int x, int y, float size, String text) {
        // Create a new font with the desired size
        Font newFont = g.getFont().deriveFont(size);
        g.setFont(newFont);
        // Find the size of string s in font f in the current Graphics context g.
        FontMetrics fm = g.getFontMetrics();
        java.awt.geom.Rectangle2D rect = fm.getStringBounds(text, g);

        int textHeight = (int) (rect.getHeight());
        int textWidth = (int) (rect.getWidth());

        // Find the top left and right corner
        int cornerX = x - (textWidth / 2);
        int cornerY = y - (textHeight / 2) + fm.getAscent();

        g.drawString(text, cornerX, cornerY);  // Draw the string.
    }

    public void actionPerformed(ActionEvent evt){
        updateCircle();
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
