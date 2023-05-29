package com.example.lab6.data;

import java.io.Serializable;
import java.util.Date;

public class HotelRoom implements Serializable,  Comparable<HotelRoom> {

    private int id;
    private String description;
    private int capacity;
    private double price;
    private boolean isOccupied;
    private Date checkInDate;
    private int lengthOfStay;
    public HotelRoom() {
    }
    public HotelRoom(String description, int capacity, double price, boolean isOccupied) {
        this.id = 0;
        this.description = description;
        this.capacity = capacity;
        this.price = price;
        this.isOccupied = isOccupied;
    }
    public HotelRoom(String description, int capacity, double price, boolean isOccupied,
                     Date checkInDate, int lengthOfStay) {
        this.id = 0;
        this.description = description;
        this.capacity = capacity;
        this.price = price;
        this.isOccupied = isOccupied;
        this.checkInDate = checkInDate;
        this.lengthOfStay = lengthOfStay;
    }

    public HotelRoom(int id, String description, int capacity, double price, boolean isOccupied,
                     Date checkInDate, int lengthOfStay) {
        this.id = id;
        this.description = description;
        this.capacity = capacity;
        this.price = price;
        this.isOccupied = isOccupied;
        this.checkInDate = checkInDate;
        this.lengthOfStay = lengthOfStay;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Boolean Check(boolean isOccupied) {
        return !isOccupied;
    }

    public Boolean Check(int capacity, double price) {
        return this.price <= price && this.capacity == capacity;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getPrice() {
        return price;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public int getLengthOfStay() {
        return lengthOfStay;
    }

    @Override
    public String toString() {
        return  description +
                ", capacity = '" + capacity + '\'' +
                ", price = " + price +
                ", isOccupied = " + isOccupied+
                ", date = " + checkInDate +
                ", getLengthOfStay = " + lengthOfStay;
    }

    @Override
    public int compareTo(HotelRoom other) {

        if (!this.isOccupied() && other.isOccupied()) {
            return -1;
        }

        if (this.isOccupied() && !other.isOccupied()) {
            return 1;
        }

        return 0;
    }
}
