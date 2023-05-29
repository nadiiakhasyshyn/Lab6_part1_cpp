package com.example.lab6.data;
import java.util.List;
public interface Repository {
    List<HotelRoom> getAll();
    HotelRoom getById(int id);
    List<HotelRoom> getAllByFree(Boolean isOccupied);
    boolean addHotelRoom(HotelRoom hotelRoom);
    boolean updateHotelRoom(int id, HotelRoom hotelRoom);
    boolean deleteHotelRoom(int id);

    List<HotelRoom> getAllByCapacityAndPrice(int capacity, double maxPrice);
}