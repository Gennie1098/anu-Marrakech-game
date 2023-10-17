package comp1110.ass2.gui;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class EndGame {

    public static void display(Stage primaryStage, Image backgroundImage) {
        VBox root = new VBox(20);

        // Insert the provided picture as the background
        Background background = new Background(new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT));
        root.setBackground(background);

        Scene endScene = new Scene(root, 500, 500);  // Set the size to suit the game

        primaryStage.setScene(endScene);
        primaryStage.show();
    }
}


