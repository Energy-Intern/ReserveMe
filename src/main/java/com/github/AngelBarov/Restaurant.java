package com.github.AngelBarov;

import java.util.*;

public class Restaurant {
    private final UUID uuid;
    private HashMap<String , Integer> menu;
    private final int places;
    private final boolean lunchMenu;
    private final boolean outside;
    private final boolean number;
    private final double longtitude;
    private final double latitude;
    private final String name;
    private final String telephoneNumber;
    private final String address;

    public Restaurant(String name, String address, String telephoneNumber, int places, boolean outside, boolean lunchMenu, double longtitude, double latitude, UUID uuid){
        //Restaurant details
        this.name=name;
        if(telephoneNumber!=null) {
            this.number = true;
        }else{
            this.number=false;
        }
        this.telephoneNumber=telephoneNumber;

        //Restaurant seating availability
        this.places=places;
        this.outside=outside;
        this.lunchMenu=lunchMenu;

        //Restaurant address
        this.longtitude=longtitude;
        this.latitude=latitude;
        this.address=address;

        //Restaurant UUID
        if (uuid!=null){
            this.uuid=uuid;
        } else {
            this.uuid = UUID.randomUUID();
        }
    }

    public String getName() {
        return name;
    }

    public int getPlaces() {
        return places;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public String getAddress() {
        return address;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public boolean isLunchMenu() {
        return lunchMenu;
    }

    public boolean isNumber() {
        return number;
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean isOutside() {
        return outside;
    }

    public void setMenu(HashMap<String, Integer> menu) {
        this.menu = menu;
    }

}
