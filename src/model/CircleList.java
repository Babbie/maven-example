package model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Sebastian on 18-12-2016.
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

    public static void addCircle(Circle circle){
        arrayList.add(circle);
    }

    public synchronized static void updateCircles(){
        Iterator<Circle> circleListIterator = arrayList.iterator();
        while (circleListIterator.hasNext()) {
            Circle circle = circleListIterator.next();
            synchronized (circle) {
                circle.update();
            }
        }
    }

    public static void removeCircle(Circle circle) {
        arrayList.remove(circle);
    }
}
