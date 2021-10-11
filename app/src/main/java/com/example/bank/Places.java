package com.example.bank;

import org.json.JSONObject;

public class Places {
    private String address, type, coordinates;
    private Time time;
    private int id;

    public Places(int id, String address, String type, String coordinates, Time time) {
        this.id = id;
        this.address = address;
        this.type = type;
        this.coordinates = coordinates;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
