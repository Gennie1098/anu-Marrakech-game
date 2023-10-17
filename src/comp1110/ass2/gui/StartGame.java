package comp1110.ass2.gui;

import comp1110.ass2.Marrakech;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StartGame extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Marrakech Game");
        Label playernumberLabel = new Label("Please select the player numbers:");
        // player numbers selection drop-down menu
        ComboBox<String> NumberComboBox = new ComboBox<>();
        NumberComboBox.getItems().addAll("1", "2", "3", "4");
        HBox NumberBox = new HBox(30, playernumberLabel, NumberComboBox);

        // Color selection tag
        Label colorLabel = new Label("Please select your color:");

        //Color selection drop-down menu
        ComboBox<String> colorComboBox = new ComboBox<>();
        colorComboBox.getItems().addAll("y", "c", "g", "b");

        HBox colorBox = new HBox(30, colorLabel, colorComboBox);

        // game Start button
        Button startGameButton = new Button("Start Game");
        startGameButton.setOnAction(e -> {
            String numberSelected = NumberComboBox.getValue();
            String colorSelected = colorComboBox.getValue();
            // Here you can process the numbers entered by the user and the colors selected
        });

        // load the photos
        Image gameImage = new Image("comp1110/ass2/gui/Marrakechpic.jpg");
        ImageView imageView = new ImageView(gameImage);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(400);

        VBox root = new VBox(20);
        root.getChildren().addAll(imageView, colorBox, NumberBox, startGameButton);

        VBox.setVgrow(imageView, Priority.ALWAYS);
        VBox.setVgrow(colorBox, Priority.ALWAYS);
        VBox.setVgrow(startGameButton, Priority.ALWAYS);
        root.setAlignment(javafx.geometry.Pos.CENTER);

        Scene scene = new Scene(root, 500, 500);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}





