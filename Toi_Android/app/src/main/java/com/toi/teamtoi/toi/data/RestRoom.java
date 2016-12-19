package com.toi.teamtoi.toi.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import com.toi.teamtoi.toi.R;

import java.io.Serializable;

public class RestRoom implements Serializable {
    private String rid;
    private String buildingName;
    private String position;
    private String waitingTime;
    private int floor;
    private int maxNumOfPeople;
    private int numOfSpace;
    private int numOfEmptySpace;
    private boolean hasVendingMachine;
    private boolean isPowderRoom;
    private String imagePath;

    public RestRoom(Context context, String rid, String buildingName, String position, String waitingTime, int floor, int maxNumOfPeople, int numOfSpace, int numOfEmptySpace, boolean hasVendingMachine, boolean isPowderRoom, String imagePath) {
        this.rid=rid;
        this.buildingName = buildingName;
        this.position = position;
        this.waitingTime = waitingTime;
        this.floor = floor;
        this.maxNumOfPeople = maxNumOfPeople;
        this.numOfSpace = numOfSpace;
        this.numOfEmptySpace = numOfEmptySpace;
        this.hasVendingMachine = hasVendingMachine;
        this.isPowderRoom = isPowderRoom;
        this.imagePath = imagePath;
    }

    public String getRid(){
        return rid;
    }

    public String getBuildingName() {
        return buildingName;
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
;
    public String getImagePath() {
        return imagePath;
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
