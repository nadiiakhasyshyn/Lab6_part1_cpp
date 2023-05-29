package com.example.lab6.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseRepository implements Repository {

    private DataBaseConnector dataBaseConnector;

    public DataBaseRepository(DataBaseConnector dataBaseConnector) {

        this.dataBaseConnector = dataBaseConnector;
        try (Connection conn = dataBaseConnector.getConnection()) {
            String tableCreateStr =
                    "CREATE TABLE IF NOT EXISTS HotelRooms\n" +
                            "(id INT NOT NULL AUTO_INCREMENT, Description VARCHAR(50)," +
                            "Capacity INT, Price DOUBLE, isOccupied BIT ,checkInDate DATE, lengthOfStay INT,  PRIMARY KEY (id));";

            Statement createTable = conn.createStatement();
            createTable.execute(tableCreateStr);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<HotelRoom> getAll() {
        List<HotelRoom> hotelRooms = new ArrayList<>();

        try (Connection connection = dataBaseConnector.getConnection()) {

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from HotelRooms");
            while (rs.next()) {
                hotelRooms.add(new HotelRoom(rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getDouble(4),
                        rs.getBoolean(5),
                        rs.getDate(6),
                        rs.getInt(7)));

            }
            rs.close();
        } catch (SQLException exception) {
            System.out.println("Не відбулося підключення до БД");
            exception.printStackTrace();
        }
        return hotelRooms;
    }

    @Override
    public HotelRoom getById(int id) {
        HotelRoom hotelRoom = null;
        try (Connection connection = dataBaseConnector.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select * from HotelRooms where id = ?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                hotelRoom = new HotelRoom(rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getDouble(4),
                        rs.getBoolean(5),
                        rs.getDate(6),
                        rs.getInt(7));
            }
            rs.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            return hotelRoom;
        }
    }

    public List<HotelRoom> getAllByFree(Boolean isOccupied) {
        List<HotelRoom> hotelRooms = new ArrayList<>();
        try (Connection connection = dataBaseConnector.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(
                            "select * from HotelRooms where isOccupied = ?"
                    );
            statement.setBoolean(1, isOccupied);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                hotelRooms.add(new HotelRoom(rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getDouble(4),
                        rs.getBoolean(5),
                        rs.getDate(6),
                        rs.getInt(7)));
            }
            rs.close();
        } catch (SQLException exception) {
            System.out.println("Не відбулося підключення до БД");
            exception.printStackTrace();
        }
        return hotelRooms;
    }

    @Override
    public boolean addHotelRoom(HotelRoom hotelRoom) {
        int updCount = 0;
        try (Connection conn = dataBaseConnector.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO HotelRooms (Description, Capacity, Price, IsOccupied, CheckInDate, LengthOfStay) VALUES (?,?,?,?,?,?)");
            preparedStatement.setString(1, hotelRoom.getDescription());
            preparedStatement.setInt(2, hotelRoom.getCapacity());
            preparedStatement.setDouble(3, hotelRoom.getPrice());
            preparedStatement.setBoolean(4, hotelRoom.isOccupied());
            if (hotelRoom.getCheckInDate() != null) {
                preparedStatement.setDate(5, new java.sql.Date(hotelRoom.getCheckInDate().getTime()));
            } else {
                preparedStatement.setNull(5, java.sql.Types.DATE);
            }
            preparedStatement.setInt(6, hotelRoom.getLengthOfStay());
            updCount = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return updCount > 0;
    }


    @Override
    public boolean updateHotelRoom(int id, HotelRoom hotelRoom) {
        int updCount = 0;
        try (Connection conn = dataBaseConnector.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "UPDATE HotelRooms " +
                            "SET Description = ?, Capacity = ?," +
                            "Price = ?, IsOccupied = ?, CheckInDate = ?, LengthOfStay = ? " +
                            "WHERE id = ?");
            preparedStatement.setString(1, hotelRoom.getDescription());
            preparedStatement.setInt(2, hotelRoom.getCapacity());
            preparedStatement.setDouble(3, hotelRoom.getPrice());
            preparedStatement.setBoolean(4, hotelRoom.isOccupied());
            preparedStatement.setDate(5, new java.sql.Date(hotelRoom.getCheckInDate().getTime()));
            preparedStatement.setInt(6, hotelRoom.getLengthOfStay());
            preparedStatement.setInt(7, id);
            updCount = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return updCount > 0;
    }

    @Override
    public boolean deleteHotelRoom(int id) {
        int updCount = 0;
        try (Connection conn = dataBaseConnector.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "DELETE FROM HotelRooms WHERE id = ?");
            preparedStatement.setInt(1, id);
            updCount = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return updCount > 0;
    }

    public List<HotelRoom> getAllByCapacityAndPrice(int capacity, double maxPrice) {
        List<HotelRoom> hotelRooms = new ArrayList<>();
        try (Connection connection = dataBaseConnector.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(
                            "SELECT * FROM HotelRooms WHERE Capacity >= ? AND Price <= ?"
                    );
            statement.setInt(1, capacity);
            statement.setDouble(2, maxPrice);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                hotelRooms.add(new HotelRoom(rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getDouble(4),
                        rs.getBoolean(5),
                        rs.getDate(6),
                        rs.getInt(7)));
            }
            rs.close();
        } catch (SQLException exception) {
            System.out.println("Не відбулося підключення до БД");
            exception.printStackTrace();
        }
        return hotelRooms;
    }


}
