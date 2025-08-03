package org.example;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

public class ElevatorController extends Elevator {

    protected ConcurrentSkipListSet<Integer> floorRequestList = new ConcurrentSkipListSet<>();
    private final ConcurrentSkipListSet<Integer> oppDirFloorRequestList = new ConcurrentSkipListSet<>();
    private final Map<Integer, List<Requestor>> waitingMap = new HashMap<>();

    public ElevatorController() {
        for (int i = 0; i <= 5; i++) {
            waitingMap.put(i, new ArrayList<>());
        }
    }

    @Override
    public synchronized void pressFloorButton (Requestor requestor)
    {
        int sourceFloor = requestor.getSourceFloor();
        waitingMap.get(sourceFloor).add(requestor);

        if(sourceFloor == currentFloor) {
            floorRequestList.add(requestor.getDestinationFloor());
            System.out.printf("------{PICKUP} Passenger already at floor %d → Going to floor %d%n",
                    sourceFloor, requestor.getDestinationFloor());
        }
        else {
            if((goingUp && sourceFloor > currentFloor) || (!goingUp && sourceFloor < currentFloor)) {
                floorRequestList.add(sourceFloor);
            }
            else {
                oppDirFloorRequestList.add(sourceFloor);
            }
            System.out.printf("------{REQUEST} Pickup requested at floor %d → Destination: %d%n",
                    sourceFloor, requestor.getDestinationFloor());
        }
        
    }


    @Override
    public synchronized void moveToFloor()
    {
        if (floorRequestList.isEmpty()) {
            return;
        }

        Integer nextFloor = goingUp ? floorRequestList.ceiling(currentFloor) : floorRequestList.floor(currentFloor);
        if(nextFloor == null) {
            goingUp = !goingUp;
            floorRequestList.addAll(oppDirFloorRequestList);
            oppDirFloorRequestList.clear();
            nextFloor = goingUp ? floorRequestList.ceiling(currentFloor) : floorRequestList.floor(currentFloor);
        }
        if(nextFloor == null) {
            return;
        }

        if(nextFloor > currentFloor) {
            currentFloor++;
        }
        else if (nextFloor < currentFloor) {
            currentFloor--;
        }

        if(floorRequestList.contains(currentFloor)) {
            System.out.printf("------{STOP} Elevator stopped at floor %d%n", currentFloor);
            System.out.println();
            floorRequestList.remove(currentFloor);

            List<Requestor>  waitingRequestorslist = waitingMap.get(currentFloor);
            if (waitingRequestorslist != null && !waitingRequestorslist.isEmpty()) {
                for (Requestor waitingRequestor : waitingRequestorslist) {
                    System.out.printf("------{BOARD} Passenger boards at floor %d → Destination: %d%n",
                            currentFloor, waitingRequestor.getDestinationFloor());
                    floorRequestList.add(waitingRequestor.getDestinationFloor());
                }
                waitingRequestorslist.clear();
            }
            System.out.printf("------{EXIT} Passenger(s) getting off at floor %d%n", currentFloor);
        }
        else {
            System.out.printf("------{PASS} Elevator crossing floor %d%n", currentFloor);
            System.out.println();
        }
    }

    @Override
    public void startElevator() throws InterruptedException
    {
        while (true) {
            synchronized (this) {
                if (floorRequestList.isEmpty() && !oppDirFloorRequestList.isEmpty()) {
                    goingUp = !goingUp;
                    floorRequestList.addAll(oppDirFloorRequestList);
                    oppDirFloorRequestList.clear();
                }
            }

            while (!floorRequestList.isEmpty()) {
                moveToFloor();
                Thread.sleep(2000);
            }
        }
    }
}
