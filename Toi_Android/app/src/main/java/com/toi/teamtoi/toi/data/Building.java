package com.toi.teamtoi.toi.data;

public class Building {
    private String name;
    private int startFloor;
    private int endFloor;

    public Building(String name, int startFloor, int endFloor) {
        this.name = name;
        this.startFloor = startFloor;
        this.endFloor = endFloor;
    }

    public int getStartFloor() {
        return startFloor;
    }

    public int getEndFloor() {
        return endFloor;
    }

    public String getName() {
        return name;
    }
}
