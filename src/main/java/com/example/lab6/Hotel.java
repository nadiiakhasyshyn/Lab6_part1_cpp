package com.example.lab6;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Hotel extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                Hotel.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hotel rooms");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:/Lab6/db/hotelRooms", "123", "123")){
            //System.out.println("connection.isValid(0) = " + connection.isValid(0));
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        launch();
    }
}
