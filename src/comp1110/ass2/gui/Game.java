package comp1110.ass2.gui;

import comp1110.ass2.Marrakech;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.geometry.Insets;

import java.io.FileInputStream;

import java.awt.*;


public class Game extends Application {
    private Marrakech newGame;
    private final Group root = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;
    private static final int LEFT_PANE_SIZE = 700;
    private static final double TILE_SIZE = 70;
    private static final int BOARD_SIZE = 7;

    private static  Font font = Font.loadFont("file:assets/JockeyOne-Regular.ttf", 28); //"Jockey One" font from Google Font
    private Insets layoutPadding = new Insets(0, 40, 0, 40);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // FIXME Task 7 and 15

        /** @Authority: Gennie Nguyen
         * Create Game GUI
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

        //Centre board (Board should not grow beyond its preferred size).
        board.setMaxSize(BOARD_SIZE * TILE_SIZE, BOARD_SIZE * TILE_SIZE);

        //tiles
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                //because in design, tile stroke is "outside" style, this is for outside border effect.
                Rectangle tile = new Rectangle(TILE_SIZE + 1, TILE_SIZE + 1);

                //tile border
                tile.setStroke(Color.web("#FFFCE1"));
                tile.setStrokeWidth(1.0);
                tile.setArcHeight(7.0);
                tile.setArcWidth(7.0);

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
        StackPane assamInBoard = createAssamDuplication();
        board.add(assamInBoard, 3, 3);
        //TODO: method to change Assam's hat and body color by Player's color
        //TODO: method to rotate Assam by Player's choice (by hitting one of 4 N, E, S, W buttons), example below
//        public void updateAssamAppearance(Player player, Orientation orientation) {
//            // Update color by Player's color
//            switch (player) {
//                case RED:
//                    assamInBoard.setFill(Color.RED);
//                    break;
//                case BLUE:
//                    assamInBoard.setFill(Color.BLUE);
//                    break;
//                // ... continue
//            }
//
//            // Update orientation by Player's choice
//            switch (direction) {
//                case EAST:
//                    piecePath.setRotate(90);
//                    break;
//                case SOUTH:
//                    piecePath.setRotate(180);
//                    break;
//                case WEST:
//                    piecePath.setRotate(270);
//                    break;
//                case NORTH:
//                    piecePath.setRotate(0);
//                    break;
//            }
//        }

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

        /**
         * "Move Assam" functions section, rotate Assam and roll dice
         */
        HBox moveAssamSection = new HBox();
        moveAssamSection.setStyle("-fx-background-color: #FFFCE1");
        moveAssamSection.setPrefHeight(198);
        moveAssamSection.setMaxWidth(Double.MAX_VALUE);

        //"Rotate Assam" functions section, rotate to N, E, S, W
        VBox innerGroupButtons = new VBox(15);
        HBox rotateAssam = new HBox(13);

        Button rotateAssamToNButton = createTextButton("N","#064B72","#053C5B" );
        rotateAssamToNButton.setPrefSize(45, 39);
        Button rotateAssamToEButton = createTextButton("E","#064B72","#053C5B" );
        rotateAssamToEButton.setPrefSize(45, 39);
        Button rotateAssamToSButton = createTextButton("S","#064B72","#053C5B" );
        rotateAssamToSButton.setPrefSize(45, 39);
        Button rotateAssamToWButton = createTextButton("W","#064B72","#053C5B" );
        rotateAssamToWButton.setPrefSize(45, 39);

        //A mini version of Assam in Move section, to display real time status of assamInBoard
        //ex: if assamInBoard changes color by player, assamInPlaySection change color too
        StackPane assamInPlaySection = createAssamDuplication();
        assamInPlaySection.setScaleX(0.6);
        assamInPlaySection.setScaleY(0.6);

        StackPane assamStatus = new StackPane();
        assamStatus.getChildren().add(assamInPlaySection);
        assamStatus.setStyle("-fx-background-color: #A7A7A7; -fx-background-radius: 7; -fx-border-radius: 7;");
        assamStatus.setMinSize(50, 50);
        assamStatus.setMaxSize(50, 50);
        //TODO: I think it should be one method with the method above for assamInBoard

        innerGroupButtons.getChildren().addAll(rotateAssamToNButton, assamStatus, rotateAssamToSButton);
        innerGroupButtons.setAlignment(Pos.CENTER);
        rotateAssam.getChildren().addAll(rotateAssamToWButton, innerGroupButtons, rotateAssamToEButton);
        rotateAssam.setAlignment(Pos.CENTER);

        //"Roll dice" functions section
        VBox rollDice = new VBox(15);
        rollDice.setAlignment(Pos.CENTER_RIGHT);

        Button rollDiceButton = createTextButton("ROLL DICE","#064B72","#053C5B" );
        //TODO: when hit rollDiceButton, 2 things happen (an animation run through all dice faces then display the one = dice number,
        // then (below) the number of steps change
        // then Assam auto moves on board
        HBox dice = new HBox(10);
        StackPane dice1 = createDiceFace(1);
        StackPane dice2 = createDiceFace(2);
        StackPane dice3 = createDiceFace(3);
        StackPane dice4 = createDiceFace(4);
        dice.getChildren().addAll(dice1, dice2, dice3, dice4);

        Label amountOfSteps = new Label("ASSAM WILL MOVE \"4\" STEPS");
        Font fontSmall = Font.loadFont("file:assets/JockeyOne-Regular.ttf", 18);
        amountOfSteps.setFont(fontSmall);
        amountOfSteps.setTextFill(Color.web("064B72"));
//      TODO: insert roll dice method to return a number
//      int result = rollDiceFunction();
//      amountOfSteps.setText("ASSAM WILL MOVE \"" + result + "\" STEPS");

        rollDice.getChildren().addAll(rollDiceButton, dice, amountOfSteps);
        rollDice.setAlignment(Pos.CENTER_RIGHT);

        moveAssamSection.setPadding(layoutPadding);
        moveAssamSection.getChildren().addAll(rotateAssam, createASpacerForLayout(), rollDice);

        /**
         * "Pay Dirhams" functions section
         * display the amount of dirhams needed to be pay, and pay button
         */
        HBox payDirhamsSection = new HBox();
        payDirhamsSection.setStyle("-fx-background-color: #FFE6A9");
        payDirhamsSection.setPrefHeight(80);
        payDirhamsSection.setMaxWidth(Double.MAX_VALUE);
        payDirhamsSection.setAlignment(Pos.CENTER);

        HBox amountOfDirhamsToPay = new HBox(10);

        Label amountOfDirhamsDisplayed = new Label("000");
        Font fontLarge = Font.loadFont("file:assets/JockeyOne-Regular.ttf", 32);
        amountOfDirhamsDisplayed.setFont(fontLarge);
        amountOfDirhamsDisplayed.setTextFill(Color.web("1F1F1F"));
        //TODO: a method for amountOfDirhamsDisplayed changed by the amount player need to pay

        amountOfDirhamsToPay.getChildren().addAll(createDirhamCoin(), amountOfDirhamsDisplayed);
        amountOfDirhamsToPay.setAlignment(Pos.CENTER_LEFT);

        Button payDirhamsButton = createTextButton("PAY DIRHAMS","#FFC700","#EBB700");
        //TODO: when hit payDirhamsButton, money from play A change to player B

        payDirhamsSection.setPadding(layoutPadding);
        payDirhamsSection.getChildren().addAll(amountOfDirhamsToPay, createASpacerForLayout(), payDirhamsButton);

        /**
         * "Place rug" functions section
         * buttons to change rug location (rotate and move)
         * "place rug" button
         * notice if the rug placement is invalid
         */
        HBox placeRugSection = new HBox();
        placeRugSection.setStyle("-fx-background-color: #FFFCE1");
        placeRugSection.setPrefHeight(134);
        placeRugSection.setMaxWidth(Double.MAX_VALUE);
        placeRugSection.setPadding(layoutPadding);
        placeRugSection.setAlignment(Pos.CENTER);

        //group of move rug buttons (rotate to the left, rotate to the right, up, down, left, right)
        HBox moveRugs = new HBox(30);

        //group of (rotate to the left, rotate to the right)
        VBox rotateRugButtons = new VBox(15);

        Button rotateToLeftButton = createGraphicButtonImg("file:assets/rotateToLeft.png","#E66F51", "#AB513A");
        Button rotateToRightButton = createGraphicButtonImg("file:assets/rotateToRight.png", "#E66F51", "#AB513A");

        rotateRugButtons.getChildren().addAll(rotateToLeftButton, rotateToRightButton);
        rotateRugButtons.setAlignment(Pos.CENTER);

        //group of (up, down, left, right)
        VBox upAndDownButtons = new VBox(15);
        //TODO: action when hit one button, the rug move 1 tile towards that direction
        Button moveRugUp = createMoveButton(0.0);
        Button moveRugDown = createMoveButton(180.0);

        upAndDownButtons.getChildren().addAll(moveRugUp,moveRugDown);
        upAndDownButtons.setAlignment(Pos.CENTER);

        Button moveRugLeft = createMoveButton(270.0);
        Button moveRugRight = createMoveButton(90.0);

        HBox allMoveButtons = new HBox(10);
        allMoveButtons.getChildren().addAll(moveRugLeft, upAndDownButtons, moveRugRight);
        allMoveButtons.setAlignment(Pos.CENTER);

        moveRugs.getChildren().addAll(rotateRugButtons, allMoveButtons);

        Button placeRugButton = createTextButton("PLACE RUG","#9FD395","#7EA976" );
        //TODO: if rug placement is invalid, the button stays gray, and will not allow to place rug
        // if rug placement is valid, the button turns green, and allow to place rug
        placeRugSection.getChildren().addAll(moveRugs,createASpacerForLayout(), placeRugButton);


        /**
         * "Players" section
         * display all player information (color, name, dirhams, rug)
         */
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
         * Whole game layout
         *
         */
        HBox mainLayout = new HBox(0, leftPane, rightPane);
        root.getChildren().add(mainLayout);

        stage.setScene(scene);
        stage.setTitle("Marrakech Game");
        stage.show();

    }

    private StackPane createAssamDuplication (){
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

        return tileWithAssam;
    }

    private StackPane createDirhamCoin() {
        StackPane dirhamIcon = new StackPane();

        Circle coinFace = new Circle(22.5, Color.web("#FFC700"));
        coinFace.setStroke(Color.web("#EBB700"));
        coinFace.setStrokeWidth(5.0);

        SVGPath innerDollarShape = new SVGPath();
        innerDollarShape.setContent("M27.296 14.752H30.464V18.592H24.256L24.16 18.784L29.344 26.976C30.3893 28.6613 30.912 30.0907 30.912 31.264C30.912 33.4187 30.1973 34.9973 28.768 36H27.264V38.752H23.872V36H20.288V32.288H27.072L27.168 32.096L21.248 22.912C20.416 21.5893 20 20.3733 20 19.264C20 17.0453 20.928 15.5413 22.784 14.752H23.904V12H27.296V14.752Z");
        innerDollarShape.setFill(Color.web("#C19700"));

        dirhamIcon.getChildren().addAll(coinFace, innerDollarShape);

        return dirhamIcon;
    }

    private DropShadow createDropShadowEffect(String shadowColor) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetY(7.0);
        dropShadow.setColor(Color.web(shadowColor));
        dropShadow.setRadius(0.0);
        return dropShadow;
    }


    private Button createTextButton(String buttonLabel, String buttonColor, String shadowColor) {
        Button button = new Button(buttonLabel);
        button.setPadding(new Insets(0, 10, 0, 10));

        String colorStyle = String.format("-fx-background-color: %s", buttonColor);
        button.setStyle(colorStyle + "; " + "-fx-background-radius: 7; -fx-border-radius: 7;");

        button.setFont(font);
        button.setTextFill(Color.web("#FFFCE1"));
        button.setEffect(createDropShadowEffect(shadowColor));
        return button;
    }

    private Button createGraphicButtonImg(String imagePath, String buttonColor, String shadowColor) {
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        ImageView imageView = new ImageView(image);

        Button buttonImg = new Button();
        buttonImg.setGraphic(imageView);
        buttonImg.setPadding(new Insets(0, 10, 0, 10));

        String colorStyle = String.format("-fx-background-color: %s", buttonColor);
        buttonImg.setStyle(colorStyle + "; " + "-fx-background-radius: 7; -fx-border-radius: 7;");
        buttonImg.setEffect(createDropShadowEffect(shadowColor));
        return buttonImg;
    }

    private Button createMoveButton(Double angle) {
        Button buttonShape = new Button();

        Triangle triangle = new Triangle(19.5, 19.5, 20);
        triangle.setFill(Color.web("FFFCE1"));
        triangle.getTransforms().add(new Rotate(angle, 19.5, 19.5));

        buttonShape.setGraphic(triangle);
        buttonShape.setPadding(new Insets(0, 10, 0, 10));
        buttonShape.setPrefSize(40,40);

        String colorStyle = String.format("-fx-background-color: #E66F51");
        buttonShape.setStyle(colorStyle + "; " + "-fx-background-radius: 7; -fx-border-radius: 7;");
        buttonShape.setEffect(createDropShadowEffect("#AB513A"));
        return buttonShape;
    }

    private Region createASpacerForLayout () {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    class Triangle extends Polygon {
        Triangle(double x, double y, double side) {
            double height = side * Math.sqrt(3) / 2; //equilateral triangle

            // Define the points of the upright triangle
            getPoints().addAll(
                    x, y - height / 2,
                    x - side / 2, y + height / 2,
                    x + side / 2, y + height / 2
            );
        }
    }

    private StackPane createDiceFace (int number) {
        StackPane diceFace = new StackPane();
        Rectangle square = new Rectangle(39, 39, Color.web("#FFE6A9"));
        square.setArcWidth(7.0);
        square.setArcHeight(7.0);
        diceFace.getChildren().add(square);

        if (number > 0) {
            Circle center = new Circle(5, Color.web("#064B72"));
            if (number == 1 || number == 3) {
                diceFace.getChildren().add(center);
            }

            Circle topLeft = new Circle(5, Color.web("#064B72"));
            topLeft.setTranslateX(-9);
            topLeft.setTranslateY(-9);

            Circle bottomRight = new Circle(5, Color.web("#064B72"));
            bottomRight.setTranslateX(9);
            bottomRight.setTranslateY(9);

            if (number == 2 || number == 3 || number == 4) {
                diceFace.getChildren().addAll(topLeft, bottomRight);
            }

            Circle topRight = new Circle(5, Color.web("#064B72"));
            topRight.setTranslateX(9);
            topRight.setTranslateY(-9);

            Circle bottomLeft = new Circle(5, Color.web("#064B72"));
            bottomLeft.setTranslateX(-9);
            bottomLeft.setTranslateY(9);

            if (number == 4) {
                diceFace.getChildren().addAll(topRight, bottomLeft);
            }
        }

        diceFace.setEffect(createDropShadowEffect("#E8C777"));
        return diceFace;
    }


}

