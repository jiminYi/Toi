package com.toi.teamtoi.toi.data;

import java.util.List;

public class Campus {
    private String name;
    private List<Building> buildings;

    public Campus(String name, List<Building> buildings) {
        this.name = name;
        this.buildings = buildings;
    }

    public String getName() {
        return name;
    }

    public List<Building> getBuildings() {
        return buildings;
    }
}
