package comp1110.ass2.gui;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class EndGame {

    public static void display(Stage primaryStage, Image backgroundImage) {
        VBox root = new VBox(20);

        // 使用提供的图片作为背景
        Background background = new Background(new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT));
        root.setBackground(background);

        Scene endScene = new Scene(root, 500, 500);  // 设定适合您的尺寸

        primaryStage.setScene(endScene);
        primaryStage.show();
    }
}


