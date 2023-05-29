package com.example.lab6;

import com.example.lab6.data.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    ListView<HotelRoom> listHotelRooms;
    @FXML
    ComboBox<String> freeRoomsCombo;
    @FXML
    TextField capacityTextField;
    @FXML
    TextField maxPriceTextField;

    private Repository repository;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        repository = new DataBaseRepository(new DataBaseConnector("hotelRoomsDB"));
        updateListsView();
        freeRoomsCombo.setOnAction(this::filterByFree);
        capacityTextField.setOnAction(this::filterByCapacityAndPrice);
        maxPriceTextField.setOnAction(this::filterByCapacityAndPrice);
    }

    public void updateListsView() {
        List<HotelRoom> hotelRooms = repository.getAll();
        ObservableList<HotelRoom> hotelRoomsList = FXCollections.observableList(hotelRooms);
        listHotelRooms.setItems(hotelRoomsList);

        List<String> freeRooms = new ArrayList<>();
        freeRooms.addAll(hotelRooms.stream()
                .map(hotelRoom -> hotelRoom.isOccupied() ? "зайнято" : "вільно")
                .distinct()
                .toList());
        freeRooms.add("all");

        ObservableList<String> descriptionList = FXCollections.observableList(freeRooms);
        freeRoomsCombo.setItems(descriptionList);
        freeRoomsCombo.getSelectionModel().select(freeRooms.size() - 1);
    }

    @FXML
    public void deleteHotelRoom(ActionEvent actionEvent) {
        HotelRoom toDelete = listHotelRooms.getSelectionModel().getSelectedItem();
        repository.deleteHotelRoom(toDelete.getId());
        updateListsView();
    }

    @FXML
    public void addNewHotelRoom(ActionEvent actionEvent) {
        Stage newWindow = new Stage();
        FXMLLoader loader = new FXMLLoader(Hotel.class.getResource("add-hotel-room-view.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        newWindow.setTitle("Додати номер");
        newWindow.setScene(new Scene(root, 250, 220));
        AddHotelRoomController secondController = loader.getController();
        secondController.set_repository(repository);
        secondController.set_mainController(this);
        newWindow.show();
    }

    public void filterByFree(ActionEvent actionEvent) {
        String isOccupied = freeRoomsCombo.getSelectionModel().getSelectedItem();
        List<HotelRoom> hotelRooms;
        if ("all".equals(isOccupied)) {
            hotelRooms = repository.getAll();
            Collections.sort(hotelRooms);
        } else {
            boolean isOccupiedValue = "зайнято".equals(isOccupied);
            hotelRooms = repository.getAllByFree(isOccupiedValue);
        }
        ObservableList<HotelRoom> hotelRoomsList = FXCollections.observableArrayList(hotelRooms);
        listHotelRooms.setItems(hotelRoomsList);
        applyFilters();
    }

    public void filterByCapacityAndPrice(ActionEvent actionEvent) {
        String capacityText = capacityTextField.getText();
        String maxPriceText = maxPriceTextField.getText();

        if (capacityText.isEmpty() && maxPriceText.isEmpty()) {
            // Якщо обидва поля порожні, забираємо всі фільтри
            listHotelRooms.setItems(FXCollections.observableList(repository.getAll()));
            return;
        }

        int capacity = capacityText.isEmpty() ? 0 : Integer.parseInt(capacityText);
        double maxPrice = maxPriceText.isEmpty() ? Double.MAX_VALUE : Double.parseDouble(maxPriceText);

        List<HotelRoom> filteredRooms = repository.getAllByCapacityAndPrice(capacity, maxPrice);
        ObservableList<HotelRoom> hotelRoomsList = FXCollections.observableArrayList(filteredRooms);
        listHotelRooms.setItems(hotelRoomsList);
        applyFilters();
    }




    public void applyFilters() {
        String isOccupied = freeRoomsCombo.getSelectionModel().getSelectedItem();
        String capacityText = capacityTextField.getText();
        String maxPriceText = maxPriceTextField.getText();

        boolean filterByOccupancy = !"all".equals(isOccupied);
        boolean filterByCapacity = !capacityText.isBlank();
        boolean filterByPrice = !maxPriceText.isEmpty();

        ObservableList<HotelRoom> currentHotelRoomsList = listHotelRooms.getItems();
        List<HotelRoom> filteredRooms = new ArrayList<>(currentHotelRoomsList);

        if (filterByOccupancy) {
            boolean isOccupiedValue = "зайнято".equals(isOccupied);
            filteredRooms.removeIf(hotelRoom -> hotelRoom.isOccupied() != isOccupiedValue);
        }

        if (filterByCapacity) {
            int capacity = Integer.parseInt(capacityText);
            filteredRooms.removeIf(hotelRoom -> hotelRoom.getCapacity() != capacity);
        }

        if (filterByPrice) {
            double maxPrice = Double.parseDouble(maxPriceText);
            filteredRooms.removeIf(hotelRoom -> hotelRoom.getPrice() > maxPrice);
        }

        Collections.sort(filteredRooms);

        ObservableList<HotelRoom> filteredHotelRoomsList = FXCollections.observableArrayList(filteredRooms);
        listHotelRooms.setItems(filteredHotelRoomsList);
    }

    public void editHotelRoom(ActionEvent actionEvent) {
        // TODO
        // редагування вибраного зі списку елемента
        // пропонується розробити самостійно
        // за прикладом створення вікна для додавання
        // нового елемента в список
    }
}
