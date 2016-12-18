package model;

import java.util.Timer;

/**
 * Created by Sebastian on 24-11-2016.
 */
public class Circle {
    private int x;
    private int y;
    private int radius;
    private int speed;
    private int goalX;
    private int goalY;
    private String text;
    private boolean hasArrived = false;

    public Circle(int x, int y, int goalX, int goalY, int radius, int speed, String text) {
        this.x = x;
        this.y = y;
        this.goalX = goalX;
        this.goalY = goalY;
        this.radius = radius;
        this.speed = speed;
        this.text = text;
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
        return y;
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

    public int getCenterY(){return y + radius/2;}

    public String getText() {return text;}

    public void setText(String text) {this.text = text;}

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
    }
}
