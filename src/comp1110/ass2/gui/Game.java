package comp1110.ass2.gui;

import comp1110.ass2.Marrakech;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;
import javafx.geometry.Insets;


import java.awt.*;


public class Game extends Application {
    private Marrakech newGame;

    private final Group root = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;

    private static final int LEFT_PANE_SIZE = 700;

    private static final double TILE_SIZE = 70;

    private static final int BOARD_SIZE = 7;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // FIXME Task 7 and 15

        /** Authority: Gennie Nguyen
         * Create Game GUI, all
         *
         */

        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);


        /**
         * create left pane for game board, size 700x700
         * the game state (game string) will be displayed only in this area, including board, Assam, and any rug placed on board
         */

        //board grid with tiles in centre
        GridPane board = new GridPane();
        board.setHgap(0.5);  // Add horizontal gap
        board.setVgap(0.5);  // Add vertical gap

        //Centre board
        // Board should not grow beyond its preferred size.
        board.setMaxSize(BOARD_SIZE * TILE_SIZE, BOARD_SIZE * TILE_SIZE);

        //tiles
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                //because in design, tile stroke is "outside" style, this is for outside border effect.
                Rectangle tile = new Rectangle(TILE_SIZE + 1, TILE_SIZE + 1);

                //tile border
                tile.setStroke(Color.web("#FFFCE1"));
                tile.setStrokeWidth(1.0);
                tile.setArcHeight(5.0);
                tile.setArcWidth(5.0);

                //tile color
                if ((i + j) % 2 == 0) {
                    tile.setFill(Color.web("#F3A261"));
                } else {
                    tile.setFill(Color.web("#FFE6A9"));
                }

                board.add(tile, i, j);
            }
        }

        //Assam on board
        Group assam = new Group();

        //Assam's body
        SVGPath assamBody = new SVGPath();
        assamBody.setContent("M25.4019 24.5C26.5566 22.5 29.4434 22.5 30.5981 24.5L49.6506 57.5C50.8053 59.5 49.362 62 47.0526 62H8.94744C6.63804 62 5.19466 59.5 6.34937 57.5L25.4019 24.5Z");
        assamBody.setFill(Color.web("#064B72"));
        assam.getChildren().add(assamBody);

        //Assam's buttons on body
        int[] assamButtonCentersY = {55, 46, 37};
        double assamButtonRadius = 2;
        Color assamButtonColor = Color.web("#FFFCE1");

        for (int centerY : assamButtonCentersY) {
            Circle assamButton = new Circle(28, centerY, assamButtonRadius, assamButtonColor);
            assam.getChildren().add(assamButton);
        }

        //Assam's face
        int assamFaceCentersY = 17;
        double assamFaceRadius = 13;
        Color assamFaceColor = Color.web("#FFFCE1");

        Circle assamFace = new Circle(28, assamFaceCentersY, assamFaceRadius, assamFaceColor);
        assam.getChildren().add(assamFace);

        //Assam's hat
        SVGPath assamHat = new SVGPath();
        assamHat.setContent("M45 17H28H11C11 7.61116 18.6112 0 28 0C37.3888 0 45 7.61116 45 17Z");
        assamHat.setFill(Color.web("#064B72"));
        assam.getChildren().add(assamHat);

        //Assam's position
        StackPane tileWithAssam = new StackPane();
        tileWithAssam.getChildren().add(assam);
        board.add(tileWithAssam, 3, 3);


        //layer with circles around game board
        StackPane boardWithCircles = new StackPane(board);
        String imageUrl = "file:assets/boardWithCircles.png";
        boardWithCircles.setStyle("-fx-background-image: url('" + imageUrl + "'); -fx-background-position: center center; -fx-background-repeat: stretch;");


        StackPane leftPane = new StackPane(boardWithCircles);
        leftPane.setPrefSize(LEFT_PANE_SIZE, LEFT_PANE_SIZE);
        leftPane.setStyle("-fx-background-color: #E66F51");



        /**
         * create right pane for all game play functions and player information, size 500x700
         * game functions include: rotate Assam, roll dice to move Assam, pay Dirhams if needed, place rug
         * player information include: color, name, amount of Dirhams, amount of Rug, status (by color)
         */

        VBox rightPane = new VBox();
        rightPane.setPrefWidth(WINDOW_WIDTH - LEFT_PANE_SIZE);


        //"Move Assam" functions section, rotate Assam and roll dice
        HBox moveAssamSection = new HBox();
        moveAssamSection.setStyle("-fx-background-color: #FFFCE1");
        moveAssamSection.setPrefHeight(198);
        moveAssamSection.setMaxWidth(Double.MAX_VALUE);

        DropShadow blueDropShadow = new DropShadow();
        blueDropShadow.setOffsetY(7.0);
        blueDropShadow.setColor(Color.web("#053C5B"));
        blueDropShadow.setRadius(0.0);

        Insets buttonPadding = new Insets(0, 10, 0, 10);

        //"Rotate Assam" functions section, rotate to N, E, S, W
        StackPane rotateAssam = new StackPane();

        Button rotateAssamToNButton = new Button("N");
        rotateAssamToNButton.setPadding(buttonPadding);
        rotateAssamToNButton.setStyle("-fx-background-color: #064B72");
        rotateAssamToNButton.setFont(new Font("Jockey One", 28));
        rotateAssamToNButton.setTextFill(Color.web("#FFFCE1"));
        rotateAssamToNButton.setEffect(blueDropShadow);

        Button rotateAssamToEButton = new Button("E");
        rotateAssamToEButton.setPadding(buttonPadding);
        rotateAssamToEButton.setStyle("-fx-background-color: #064B72");
        rotateAssamToEButton.setFont(new Font("Jockey One", 28));
        rotateAssamToEButton.setTextFill(Color.web("#FFFCE1"));
        rotateAssamToEButton.setEffect(blueDropShadow);

        Button rotateAssamToSButton = new Button("S");
        rotateAssamToSButton.setPadding(buttonPadding);
        rotateAssamToSButton.setStyle("-fx-background-color: #064B72");
        rotateAssamToSButton.setFont(new Font("Jockey One", 28));
        rotateAssamToSButton.setTextFill(Color.web("#FFFCE1"));
        rotateAssamToSButton.setEffect(blueDropShadow);

        Button rotateAssamToWButton = new Button("W");
        rotateAssamToWButton.setPadding(buttonPadding);
        rotateAssamToWButton.setStyle("-fx-background-color: #064B72");
        rotateAssamToWButton.setFont(new Font("Jockey One", 28));
        rotateAssamToWButton.setTextFill(Color.web("#FFFCE1"));
        rotateAssamToWButton.setEffect(blueDropShadow);

        StackPane assamStatus = new StackPane();
        assamStatus.getChildren().add(assam);
        assamStatus.setStyle("-fx-background-color: #A7A7A7");
        assamStatus.setPrefSize(50, 50);

        rotateAssam.getChildren().addAll(rotateAssamToNButton, rotateAssamToEButton, rotateAssamToSButton, rotateAssamToWButton, assamStatus);

        //"Roll dice" functions section
        VBox rollDice = new VBox();

        Button rollDiceButton = new Button("Roll dice");
        rollDiceButton.setPadding(buttonPadding);
        rollDiceButton.setStyle("-fx-background-color: #064B72");
        rollDiceButton.setFont(new Font("Jockey One", 28));
        rollDiceButton.setTextFill(Color.web("#FFFCE1"));
        rollDiceButton.setEffect(blueDropShadow);

        rollDice.getChildren().addAll(rollDiceButton);


        moveAssamSection.getChildren().addAll(rotateAssam, rollDice);

        //"Pay Dirhams" functions section
        HBox payDirhamsSection = new HBox();
        payDirhamsSection.setStyle("-fx-background-color: #FFE6A9");
        payDirhamsSection.setPrefHeight(80);
        payDirhamsSection.setMaxWidth(Double.MAX_VALUE);

        DropShadow yellowDropShadow = new DropShadow();
        yellowDropShadow.setOffsetY(7.0);
        yellowDropShadow.setColor(Color.web("#EBB700"));
        yellowDropShadow.setRadius(0.0);

        Button payDirhamsButton = new Button("Pay Dirhams");
        payDirhamsButton.setPadding(buttonPadding);
        payDirhamsButton.setStyle("-fx-background-color: #FFC700");
        payDirhamsButton.setFont(new Font("Jockey One", 28));
        payDirhamsButton.setTextFill(Color.web("#FFFCE1"));
        payDirhamsButton.setEffect(yellowDropShadow);

        payDirhamsSection.getChildren().addAll(payDirhamsButton);


        //"Place rug" functions section
        VBox placeRugSection = new VBox();
        placeRugSection.setStyle("-fx-background-color: #FFFCE1");
        placeRugSection.setPrefHeight(134);
        placeRugSection.setMaxWidth(Double.MAX_VALUE);

        Button rugButton = new Button("Place Rug");
        placeRugSection.getChildren().add(rugButton);


        //Players section
        GridPane playersSection = new GridPane();
        playersSection.setMaxWidth(Double.MAX_VALUE);

        // Tạo một style cho minh hoạ
        String style = "-fx-border-color: black; -fx-background-color: #D3D3D3; -fx-padding: 10;";

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                VBox playerBox = new VBox(); // khoảng cách giữa các phần tử trong VBox là 10
                playerBox.setStyle(style); // set style cho VBox

                Label playerName = new Label("Player " + (i*2 + j + 1));
                Label playerInfo = new Label("Info..."); // Thay "Info..." bằng thông tin thực tế của người chơi

                playerBox.getChildren().addAll();

                playersSection.add(playerBox, i, j); // Thêm hình chữ nhật vào GridPane ở vị trí (i, j)
            }
        }


        rightPane.getChildren().addAll(moveAssamSection, payDirhamsSection,placeRugSection, playersSection);


        /**
         * Combine left and right panes
         *
         */
        HBox mainLayout = new HBox(0, leftPane, rightPane);
        root.getChildren().add(mainLayout);



        stage.setScene(scene);
        stage.setTitle("Marrakech Game");
        stage.show();

    }

    // TODO: constructor game with arg String gameString;



}