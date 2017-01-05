package model;

import main.Lane;

import java.util.Observable;

/**
 * Class representing a circle
 */
public class Circle extends Observable {
    private int x;
    private Lane lane;
    private int radius;
    private int speed;
    private int goalX;
    private String text;
    private boolean hasArrived = false;
    private boolean toDelete = false;
    private int standStillTicks = 0;

    //Create a circle with the appropriate direction, lane, speed, goal and text
    public Circle(boolean outgoing, boolean client, Lane lane, String text) {
        this.radius = 140;
        this.lane = lane;
        this.text = text;

        if (client) {
            if (outgoing) {
                this.x = 450;
                this.goalX = -radius*2;
                this.speed = -10;
            } else {
                this.x = -2*radius-100;
                this.goalX = 450-radius*2;
                this.speed = 10;
            }
        } else {
            if (outgoing) {
                this.x = 0;
                this.goalX = 400+radius*2;
                this.speed = 10;
            } else {
                this.x = 600;
                this.goalX = 0;
                this.speed = -10;
            }
        }

        CircleList.addCircle(this);
    }

    /**
     * Used to calculate the width of the oval. About 8 characters fit in an oval of width of 140.
     * For each multiple of 8 the width will be increased
     */
    public int getOvalWidth(){
        int width = (int) Math.ceil(radius * (text.length()/8f));
        return width > radius ? width : radius ;
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

    public String getText() {return text;}

    public void setText(String text) {this.text = text;}

    public void delete() {
        toDelete = true;
    }

    public boolean isDone() {
        return toDelete;
    }

    public boolean isStandingStill() {
        return standStillTicks > 0;
    }

    public void standStill(int ticks) {
        standStillTicks += ticks;
    }

    //Update the position of the circle by adding its speed to its current XPos
    public void update() {
        if (standStillTicks > 0) {
            standStillTicks--;
            setChanged();
            notifyObservers();
            return;
        }
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
}
