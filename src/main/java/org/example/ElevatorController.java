package org.example;

public class ElevatorController extends Elevator {

    @Override
    public void pressFloorButton (int floor)
    {
        if(floor < 0 || floor > 5) {
            System.out.println("Invalid floor request. Enter floor number b/w 0 and 5.");
            System.out.println();
            return;
        }
        if(floor == currentFloor) {
            System.out.println("You are already on the current floor.");
            System.out.println();
            return;
        }
        floorRequestList.add(floor);
    }


    @Override
    public void moveToFloor()
    {
        if (floorRequestList.isEmpty()) {
            System.out.println("------- No floor requests pending -------");
            System.out.println();
            return;
        }

        Integer nextFloor = goingUp ? floorRequestList.ceiling(currentFloor) : floorRequestList.floor(currentFloor);
        if(nextFloor == null) {
            goingUp = !goingUp;
            nextFloor = goingUp ? floorRequestList.ceiling(currentFloor) : floorRequestList.floor(currentFloor);
        }
        if(nextFloor == null) {
            System.out.println("------- No more pending floor requests -------");
            System.out.println();
            return;
        }

        if(nextFloor > currentFloor) {
            currentFloor++;
        }
        else if (nextFloor < currentFloor) {
            currentFloor--;
        }

        if(floorRequestList.contains(currentFloor)) {
            System.out.println("------- REACHED YOUR REQUESTED FLOOR " + currentFloor + " -------");
            System.out.println();
            floorRequestList.remove(currentFloor);
        }
        else {
            System.out.println("------- CROSSING FLOOR " + currentFloor + " -------");
            System.out.println();
        }
    }


    public void startElevator() throws InterruptedException
    {
        while (!floorRequestList.isEmpty()) {
            moveToFloor();
            Thread.sleep(2000);
        }
    }
}
