package com.toi.teamtoi.toi.data;

public class RestRoom {
    private String position;
    private String waitingTime;
    private String status;
    private int floor;
    private int maxNumOfPeople;
    private int numOfSpace;
    private int numOfEmptySpace;
    private boolean hasVendingMachine;
    private boolean isPowderRoom;

    public RestRoom(String position, String waitingTime, String status, int floor, int maxNumOfPeople, int numOfSpace, int numOfEmptySpace, boolean hasVendingMachine, boolean isPowderRoom) {
        this.position = position;
        this.waitingTime = waitingTime;
        this.status = status;
        this.floor = floor;
        this.maxNumOfPeople = maxNumOfPeople;
        this.numOfSpace = numOfSpace;
        this.numOfEmptySpace = numOfEmptySpace;
        this.hasVendingMachine = hasVendingMachine;
        this.isPowderRoom = isPowderRoom;
    }

    public String getPosition() {
        return position;
    }

    public String getWaitingTime() {
        return waitingTime;
    }

    public String getStatus() {
        return status;
    }

    public int getFloor() {
        return floor;
    }

    public boolean isHasVendingMachine() {
        return hasVendingMachine;
    }

    public boolean isPowderRoom() {
        return isPowderRoom;
    }

    public int getMaxNumOfPeople() {
        return maxNumOfPeople;
    }

    public int getNumOfSpace() {
        return numOfSpace;
    }

    public int getNumOfEmptySpace() {
        return  numOfEmptySpace;
    }
}
