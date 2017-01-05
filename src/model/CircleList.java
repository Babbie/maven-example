package model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class that holds all of the circles
 */
public class CircleList {
    private static ArrayList<Circle> arrayList;

    private static CircleList circlelist = new CircleList();

    private CircleList() {
        arrayList = new ArrayList<>();
    }

    public static CircleList getInstance(){
        return circlelist;
    }

    public static ArrayList<Circle> getCircleList() {
        return arrayList;
    }

    //Add circle to the list
    public static void addCircle(Circle circle){arrayList.add(circle);}

    //Remove circle from list
    public static void removeCircle(Circle circle) {
        arrayList.remove(circle);
    }

    //Loop over the circles and call update on each
    public synchronized static void updateCircles(){
        Iterator<Circle> circleListIterator = arrayList.iterator();
        while (circleListIterator.hasNext()) {
            Circle circle = circleListIterator.next();
            circle.update();
            if (circle.isDone()) {
                circleListIterator.remove();
            }
        }
    }

}
