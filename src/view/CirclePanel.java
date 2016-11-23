package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * Created by Sebastian on 20-11-2016.
 */
public class CirclePanel extends JPanel implements ActionListener{
    private int x1, y1, x2, y2, s, r;
    private Timer timer;
    private Boolean tStarted = FALSE;

    public CirclePanel() {
        timer = new Timer(100, this);
        drawCircle(0,250,400,400,100,10);
    }

    public void drawCircle(int fromX, int fromY, int toX, int toY, int radius, int speed){
        x1 = fromX; y1 = fromY; x2 = toX; y2 = toY; r = radius; s = speed;

        if(!tStarted){
            timer.start();
            tStarted = TRUE;
        }

        if(fromX!=toX){
            repaint(x1, y1, radius, radius);
            x1+=speed;
            repaint(x1, y1,radius,radius);
        } else {
            timer.stop();
            tStarted = FALSE;
        }

    }

    public void actionPerformed(ActionEvent evt){
        drawCircle(x1, y1, x2, y2, r, s);
    }

    @Override
    public void paintComponent(Graphics g) {
        try {
            super.paintComponent(g);
            BufferedImage beer = ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("dikkestinkbeer.jpg"));
            g.drawImage(beer, 0, 0, null);
            g.setColor(Color.RED);
            g.fillOval(x1,y1,r,r);
            g.setColor(Color.BLACK);
            g.drawOval(x1,y1,r,r);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
