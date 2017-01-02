package model;

/**
 * Created by Bab on 29-12-2016.
 */
public enum Lane {
    First(110), Second(250), Third(390);

    public int yPos;

    Lane(int yPos) {
        this.yPos = yPos;
    }
}
