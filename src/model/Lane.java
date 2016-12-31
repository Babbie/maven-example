package model;

/**
 * Created by Bab on 29-12-2016.
 */
public enum Lane {
    First(150), Second(300), Third(450);

    public int yPos;

    Lane(int yPos) {
        this.yPos = yPos;
    }
}
