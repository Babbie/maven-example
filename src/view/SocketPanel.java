package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.MouseEvent;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;


/**
 * Created by Bab on 26-10-2016.
 */

public class SocketPanel extends JPanel {

    private int panelWidth, panelHeight;
    private int circleX = 0;
    private int circleY = 0;
    private int radius = 20;
    private boolean timerStarted = FALSE;
    Timer timer;

    public SocketPanel() {
        timer = new Timer(100,animator);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if(timerStarted){
                    timer.stop();
                    timerStarted=FALSE;
                } else {
                    timer.start();
                    timerStarted=TRUE;
                }
            }
        });
    }

    public void moveCircle(int X, int Y, int speed, String text){

    }

    ActionListener animator = new ActionListener() {
        public void actionPerformed(ActionEvent evt){
            repaint(circleX, circleY, radius, radius);
            circleX+=10;
            repaint(circleX, circleY, radius, radius);
        }
    };

    @Override
    public void paintComponent(Graphics g) {
        try {
            super.paintComponent(g);
            BufferedImage beer = ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("dikkestinkbeer.jpg"));
            g.drawImage(beer, 0, 0, null);
            if (circleX == panelWidth){
                circleX=0;
            }
            g.setColor(Color.RED);
            g.fillOval(circleX,circleY,radius,radius);
            g.setColor(Color.BLACK);
            g.drawOval(circleX,circleY,radius,radius);
            panelWidth = getWidth();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
