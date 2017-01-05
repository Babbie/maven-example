package main;

/**
 * Enum which defines the places of the 3 lanes
 */
public enum Lane {
    First(110), Second(250), Third(390);

    public int yPos;

    Lane(int yPos) {
        this.yPos = yPos;
    }
}
