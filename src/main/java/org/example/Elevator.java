package org.example;


public abstract class Elevator {
    protected int currentFloor = 0;
    protected boolean goingUp = true;

    public abstract void pressFloorButton(Requestor requestor);

    public abstract void moveToFloor();

    public abstract void startElevator() throws InterruptedException;

}
