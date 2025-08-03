package org.example;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        ElevatorController elevatorController = new ElevatorController();

        Thread pickfloorThread = new Thread(() -> {
            Random random = new Random();
            while (true) {
                try {
                    int sourceFloor = random.nextInt(6);
                    int destinationFloor;
                    do {
                        destinationFloor = random.nextInt(8);
                    } while (destinationFloor == sourceFloor);

                    Requestor requestor = new Requestor(sourceFloor, destinationFloor);
                    elevatorController.pressFloorButton(requestor);
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        Thread startElevatorThread = new Thread(() -> {
            while (true)
            {
                try {
                    elevatorController.startElevator();
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        pickfloorThread.setDaemon(true);
        startElevatorThread.setDaemon(true);

        pickfloorThread.start();
        startElevatorThread.start();

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }





//        elevator.pressFloorButton(2);
//        elevator.pressFloorButton(5);
//        elevator.pressFloorButton(3);
//        elevator.startElevator();
//
//        elevator.pressFloorButton(5);
//        elevator.pressFloorButton(2);
//        elevator.startElevator();

    }

}