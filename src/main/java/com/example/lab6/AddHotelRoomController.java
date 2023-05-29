package com.example.lab6;

import com.example.lab6.data.HotelRoom;
import com.example.lab6.data.Repository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class AddHotelRoomController {
    private Repository _repository;
    private MainController _mainController;

    public void set_mainController(MainController _mainController) {
        this._mainController = _mainController;
    }

    public void set_repository(Repository _repository) {
        this._repository = _repository;
    }

    @FXML
    TextField description;
    @FXML
    TextField capacity;
    @FXML
    TextField price;
    @FXML
    CheckBox isOccupied;
    @FXML
    TextField checkInDate;
    @FXML
    TextField lengthOfStay;

    public void addHotelRoomToFile(ActionEvent actionEvent) {
        String description_ = description.getText();
        String capacity_ = capacity.getText();
        String price_ = price.getText();
        boolean isOccupiedValue = isOccupied.isSelected();
        String checkInDate_ = checkInDate.getText();
        String lengthOfStay_ = lengthOfStay.getText();

        String pattern = "yyyy-MM-dd";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

        LocalDate localDate;
        Instant instant;
        Date checkInDateValue = null;

        if (!checkInDate_.isEmpty()) {
            localDate = LocalDate.parse(checkInDate_, dateFormatter);
            instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
            checkInDateValue = Date.from(instant);
        }

        int capacityValue = capacity_.isEmpty() ? 0 : Integer.parseInt(capacity_);
        double priceValue = price_.isEmpty() ? 0.0 : Double.parseDouble(price_);
        int lengthOfStayValue = lengthOfStay_.isEmpty() ? 0 : Integer.parseInt(lengthOfStay_);

        HotelRoom newHotelRoom = new HotelRoom(description_, capacityValue,
                priceValue, isOccupiedValue, checkInDateValue, lengthOfStayValue);
        _repository.addHotelRoom(newHotelRoom);

        final Node source = (Node) actionEvent.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        _mainController.updateListsView();
        stage.close();
    }
}
