package view;

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

    public Circle(int x, int y, int goalX, int goalY, int radius, int speed) {
        this.x = x;
        this.y = y;
        this.goalX = goalX;
        this.goalY = goalY;
        this.radius = radius;
        this.speed = speed;
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

    public void setY(int y) {
        this.y = y;
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

    public int getGoalY() {
        return goalY;
    }
}