package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EndGame extends Application {
    public static void main(String[] args) {
        launch(args);
    }



    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: white;");

        // Header
        HBox headerBox = new HBox();
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setPadding(new Insets(20, 0, 20, 0));
        headerBox.setStyle("-fx-background-color: linear-gradient(to right, #FFA500, #FF4500);");
        Label headerLabel = new Label("CONGRATULATIONS!");
        headerLabel.setFont(new Font("Arial", 30));
        headerLabel.setTextFill(Color.WHITE);
        headerBox.getChildren().add(headerLabel);

        // Content
        VBox contentBox = new VBox(10);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPadding(new Insets(20));

        Label winnerLabel = new Label("Gennie");
        winnerLabel.setFont(new Font("Arial", 50));
        winnerLabel.setTextFill(Color.DARKBLUE);

        Label scoreLabel = new Label("SCORE: 000");
        scoreLabel.setFont(new Font("Arial", 20));
        scoreLabel.setTextFill(Color.GRAY);

        contentBox.getChildren().addAll(winnerLabel, scoreLabel);

        for (int i = 2; i <= 4; i++) {
            Text rankText = new Text(i + "   Gennie - 000");
            rankText.setFont(new Font("Arial", 20));
            contentBox.getChildren().add(rankText);
        }

        root.setTop(headerBox);
        root.setCenter(contentBox);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Congratulations Screen");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}







