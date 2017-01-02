package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

/**
 * Created by Sebastian on 24-11-2016.
 */
public class Circle extends Observable implements ActionListener {
    private int x;
    private Lane lane;
    private int radius;
    private int speed;
    private int goalX;
    private String text;
    private boolean hasArrived = false;

    public Circle(boolean outgoing, Lane lane, String text) {
        if (outgoing) {
            this.x = 100;
            this.goalX = 500;
            this.speed = 10;
        } else {
            this.x = 500;
            this.goalX = 100;
            this.speed = -10;
        }
        this.radius = 50;
        this.lane = lane;
        this.text = text;
        CircleList.addCircle(this);
    }

    public boolean hasArrived() {
        return hasArrived;
    }

    public void setHasArrived() {
        this.hasArrived = true;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return lane.yPos;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getGoalX() {
        return goalX;
    }

    public int getCenterX(){return x + radius/2;}

    public int getCenterY(){return lane.yPos + radius/2;}

    public String getText() {return text;}

    public void setText(String text) {this.text = text;}

    public void delete() {
        CircleList.removeCircle(this);
    }

    public void update() {
        if (getSpeed() >= 0) {
            if (getX() + getSpeed() >= getGoalX()) {
                setHasArrived();
            } else {
                setX(getX() + getSpeed());
            }
        } else {
            if (getX() + getSpeed() <= getGoalX()) {
                setHasArrived();
            } else {
                setX(getX() + getSpeed());
            }
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        update();
    }
}
