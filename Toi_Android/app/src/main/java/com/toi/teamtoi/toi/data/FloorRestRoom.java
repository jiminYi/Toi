package com.toi.teamtoi.toi.data;

import java.util.List;

public class FloorRestRoom {
    private String floor;
    private List<RestRoom> restRoomList;

    public FloorRestRoom(String floor, List<RestRoom> restRoomList) {
        this.floor = floor;
        this.restRoomList = restRoomList;
    }

    public String getFloor() {
        return floor;
    }

    public List<RestRoom> getRestRoomList() {
        return restRoomList;
    }
}
