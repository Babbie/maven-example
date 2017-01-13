package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Class that holds all of the circles
 */
public class CircleList {
    private static ArrayList<Circle> arrayList;
    private final static ReadWriteLock lock = new ReentrantReadWriteLock(true);

    private static CircleList circleList = new CircleList();

    private CircleList() {
        arrayList = new ArrayList<>();
    }

    public static CircleList getInstance(){
        return circleList;
    }

    public static ArrayList<Circle> getCircleList() {
        return arrayList;
    }

    //Add circle to the list
    public static void addCircle(Circle circle){
        Lock writeLock = lock.writeLock();
        writeLock.lock();
        try {
            arrayList.add(circle);
        } finally {
            writeLock.unlock();
        }
    }

    //Remove circle from list
    public static void removeCircle(Circle circle) {
        Lock writeLock = lock.writeLock();
        writeLock.lock();
        try {
            arrayList.remove(circle);
        } finally {
            writeLock.unlock();
        }
    }

    //Loop over the circles and call update on each
    public static void updateCircles(){
        Lock readLock = lock.readLock();
        readLock.lock();
        ArrayList<Circle> toRemove = new ArrayList<>();
        try {
            for (Circle circle : arrayList) {
                circle.update();
                if (circle.isDone()) {
                    toRemove.add(circle);
                }
            }
        } finally {
            readLock.unlock();
        }
        for (Circle circle : toRemove) {
            removeCircle(circle);
        }
    }

}
