package org.example;

import java.util.TreeSet;

public abstract class Elevator {
    protected int currentFloor = 0;
    protected boolean goingUp = true;
    protected TreeSet<Integer> floorRequestList = new TreeSet<>();

    public abstract void pressFloorButton(int floor);

    public abstract void moveToFloor();

}
