package com.toi.teamtoi.toi.data;

public class RestRoom {
    private String position;
    private String waitingTime;
    private int floor;
    private int maxNumOfPeople;
    private int numOfSpace;
    private int numOfEmptySpace;
    private boolean hasVendingMachine;
    private boolean isPowderRoom;

    public RestRoom(String position, String waitingTime, int floor, int maxNumOfPeople, int numOfSpace, int numOfEmptySpace, boolean hasVendingMachine, boolean isPowderRoom) {
        this.position = position;
        this.waitingTime = waitingTime;
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

    @Override
    public String toString() {
        return "RestRoom{" +
                "floor=" + floor +
                ", position='" + position + '\'' +
                ", waitingTime='" + waitingTime + '\'' +
                ", maxNumOfPeople=" + maxNumOfPeople +
                ", numOfSpace=" + numOfSpace +
                ", numOfEmptySpace=" + numOfEmptySpace +
                ", hasVendingMachine=" + hasVendingMachine +
                ", isPowderRoom=" + isPowderRoom +
                '}';
    }
}
