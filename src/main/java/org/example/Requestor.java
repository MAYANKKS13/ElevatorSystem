package org.example;

public class Requestor {

    private final int source;
    private final int destination;

    public Requestor(int source, int destination)
    {
        if (source < 0 || source > 5 || destination < 0 || destination > 5) {
            System.out.println("Invalid floor request. Enter floor number b/w 0 and 5.");
            System.out.println();
        }
        if (source == destination) {
            System.out.println("You are already on the current floor.");
            System.out.println();
        }

        this.source = source;
        this.destination = destination;
    }

    public int getSourceFloor() {
        return source;
    }
    public int getDestinationFloor() {
        return destination;
    }

}
