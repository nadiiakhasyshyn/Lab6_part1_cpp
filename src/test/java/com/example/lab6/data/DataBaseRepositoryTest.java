package com.example.lab6.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class DataBaseRepositoryTest {
    @Test
    void addCar() {
        DataBaseRepository repository = new DataBaseRepository(
                new DataBaseConnector("test2"));
        repository.addHotelRoom(new HotelRoom("test brand",1,
                200, false,null,0));
        repository.addHotelRoom(new HotelRoom("test brand", 2 ,
                200, false,null,0));
        assertNotNull(repository.getById(1));
        java.util.List<HotelRoom> cars = repository.getAll();
        assertTrue(cars.size()>0);
    }
    @Test
    void getByBrand() {
        DataBaseRepository repository = new DataBaseRepository(
                new DataBaseConnector("test3"));
        repository.addHotelRoom(new HotelRoom("test brand",1,
                200, false,null,0));
        repository.addHotelRoom(new HotelRoom("test brand", 2 ,
                200, false,null,0));
        java.util.List<HotelRoom> hotelRooms =
                repository.getAllByFree(false);
        assertNotNull(repository.getById(1));
        assertTrue(hotelRooms.size()>1);
    }
}