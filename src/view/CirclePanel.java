package view;

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
        addCircle(0,0,200,400,100,4);
        addCircle(100,100,0,0,100,-4);
    }

    public void updateCircle(){
        Iterator<Circle> circleIterator = circleList.iterator();
        for (Circle circle: circleList){
            if(circle.getX() < circle.getGoalX()&&circle.getSpeed()>0||circle.getX() > circle.getGoalX()&&circle.getSpeed()<0){
                circle.setX(circle.getX()+circle.getSpeed());
                repaint();
            }
        }
    }

    public void addCircle(int startX, int startY, int toX, int toY, int radius, int speed){
        Circle circle = new Circle(startX, startY, toX, toY,radius,speed);
        circleList.add(circle);
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
