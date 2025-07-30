package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        ElevatorController elevator = new ElevatorController();

        elevator.pressFloorButton(2);
        elevator.pressFloorButton(5);
        elevator.pressFloorButton(3);
        elevator.startElevator();

        elevator.pressFloorButton(5);
        elevator.pressFloorButton(2);
        elevator.startElevator();


    }
}