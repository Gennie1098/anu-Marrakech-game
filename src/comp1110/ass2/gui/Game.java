package comp1110.ass2.gui;

import comp1110.ass2.Assam;
import comp1110.ass2.Marrakech;
import comp1110.ass2.Player;
import comp1110.ass2.Rugs;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.util.Duration;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.util.HexFormat;
import java.util.Random;


public class Game extends Application {
    private Marrakech newGame;
    private final Group root = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;
    private static final int LEFT_PANE_SIZE = 700;
    private static final double TILE_SIZE = 70;
    private static final int BOARD_SIZE = 7;
    private static Font font32 = Font.loadFont("file:assets/JockeyOne-Regular.ttf", 32);

    private SVGPath assamBody = new SVGPath();
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

        Marrakech marrakech = new Marrakech(4);
        final String[] gameString = {marrakech.getGameState()};
        System.out.println(gameString[0]);

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
        StackPane assamInBoard = updateAssamAppearance("Pc00912i");
        board.add(assamInBoard, 3, 3);

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

        final String[] assamString = {"A33N"};

        HBox moveAssamSection = new HBox();
        moveAssamSection.setStyle("-fx-background-color: #FFFCE1");
        moveAssamSection.setPrefHeight(180);
        moveAssamSection.setMaxWidth(Double.MAX_VALUE);

        //"Rotate Assam" functions section, rotate to left or right
        HBox rotateAssam = new HBox(13);

        Button rotateAssamToLeftButton = createButtonImg("assets/rotateToLeft.png","#064B72","#053C5B" );
        rotateAssamToLeftButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String newAssam = Marrakech.rotateAssam(assamString[0], 270);

                assamString[0] = newAssam;
            }
        });
        Button rotateAssamToRightButton = createButtonImg("assets/rotateToRight.png", "#064B72","#053C5B");
        rotateAssamToRightButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String newAssam = Marrakech.rotateAssam(assamString[0], 90);

                assamString[0] = newAssam;
            }
        });

        //A mini version of Assam in Move section, to display real time status of assamInBoard
        //ex: if assamInBoard changes color by player, assamInPlaySection change color too
        StackPane assamInPlaySection = updateAssamAppearance("Pc01209i");

        StackPane assamStatus = new StackPane();
        assamStatus.getChildren().add(assamInPlaySection);
        assamStatus.setStyle("-fx-background-color: #A7A7A7; -fx-background-radius: 7; -fx-border-radius: 7;");
        assamStatus.setMinSize(70, 70);
        assamStatus.setMaxSize(70, 70);
        //TODO: I think it should be one method with the method above for assamInBoard

        rotateAssam.getChildren().addAll(rotateAssamToLeftButton, assamStatus, rotateAssamToRightButton);
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
        Font font18 = Font.loadFont("file:assets/JockeyOne-Regular.ttf", 18);
        amountOfSteps.setFont(font18);
        amountOfSteps.setTextFill(Color.web("064B72"));
        rollDiceButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                    board.getChildren().remove(assamInBoard);

                    int result = Marrakech.rollDie();
                    String newAssam = Marrakech.moveAssam(assamString[0], result);
                    amountOfSteps.setText("ASSAM WILL MOVE \"" + result + "\" STEPS");

                    System.out.println(assamString[0]);
                    assamString[0] = newAssam;

                    Assam Assam = new Assam(newAssam);

                    int assamX = Assam.getX();
                    int assamY = Assam.getY();
                    char orientation = Assam.getOrientation();

                    System.out.println(assamX);
                    System.out.println(assamY);
                    System.out.println(orientation);

                    board.add(assamInBoard, assamX, assamY);
            }
        });

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
        payDirhamsSection.setPrefHeight(90);
        payDirhamsSection.setMaxWidth(Double.MAX_VALUE);
        payDirhamsSection.setAlignment(Pos.CENTER);

        HBox amountOfDirhamsToPay = new HBox(10);

        Label amountOfDirhamsDisplayed = new Label("000");
        amountOfDirhamsDisplayed.setFont(font32);
        amountOfDirhamsDisplayed.setTextFill(Color.web("1F1F1F"));
        //TODO: a method for amountOfDirhamsDisplayed changed by the amount player need to pay
        // Potential problem with game recognizing blank square as a payable option.

        String amountDisplayed = String.valueOf(Marrakech.getPaymentAmount(gameString[0]));
        amountOfDirhamsDisplayed.setText(amountDisplayed);

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
        StackPane rugOneInBoard = createRugHalfOne();
        StackPane rugTwoInBoard = createRugHalfTwo();
        board.add(rugOneInBoard, 3, 2);
        board.add(rugTwoInBoard, 3, 3);

        HBox placeRugSection = new HBox();
        placeRugSection.setStyle("-fx-background-color: #FFFCE1");
        placeRugSection.setPrefHeight(140);
        placeRugSection.setMaxWidth(Double.MAX_VALUE);
        placeRugSection.setPadding(layoutPadding);
        placeRugSection.setAlignment(Pos.CENTER);

        //group of move rug buttons (rotate to the left, rotate to the right, up, down, left, right)
        HBox moveRugs = new HBox(30);

        //group of (rotate to the left, rotate to the right)
        VBox rotateRugButtons = new VBox(15);
        final String[] rugString = {"c033332"};

//TODO Fix default case rotation problem - see methods at the bottom of Marrakech class
        Button rotateToLeftButton = createButtonImg("assets/rotateToLeft.png","#E66F51", "#AB513A");
        rotateToLeftButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                board.getChildren().remove(rugTwoInBoard);
                board.getChildren().remove(rugOneInBoard);

                String newString = Marrakech.rotateRugLeft(rugString[0]);
                System.out.println(newString);

                rugString[0] = newString;
                Rugs rugs = new Rugs(newString);

                int halfOneX = rugs.getX2();
                int halfTwoX = rugs.getX1();
                int halfOneY = rugs.getY2();
                int halfTwoY = rugs.getY1();

                board.add(rugOneInBoard, halfOneX, halfOneY);
                board.add(rugTwoInBoard, halfTwoX, halfTwoY);
            }
        });
        Button rotateToRightButton = createButtonImg("assets/rotateToRight.png", "#E66F51", "#AB513A");
        rotateToRightButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                board.getChildren().remove(rugTwoInBoard);
                board.getChildren().remove(rugOneInBoard);

                String newString = Marrakech.rotateRugRight(rugString[0]);
                System.out.println(newString);

                rugString[0] = newString;
                Rugs rugs = new Rugs(newString);

                int halfOneX = rugs.getX2();
                int halfTwoX = rugs.getX1();
                int halfOneY = rugs.getY2();
                int halfTwoY = rugs.getY1();

                board.add(rugOneInBoard, halfOneX, halfOneY);
                board.add(rugTwoInBoard, halfTwoX, halfTwoY);
            }
        });

        rotateRugButtons.getChildren().addAll(rotateToLeftButton, rotateToRightButton);
        rotateRugButtons.setAlignment(Pos.CENTER);

        //group of (up, down, left, right)
        VBox upAndDownButtons = new VBox(15);
        Button moveRugUp = createMoveButton(0.0);
        moveRugUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                board.getChildren().remove(rugTwoInBoard);
                board.getChildren().remove(rugOneInBoard);

                String newString = Marrakech.moveRugUp(rugString[0]);
                System.out.println(newString);

                rugString[0] = newString;
                Rugs rugs = new Rugs(newString);

                int halfOneX = rugs.getX2();
                int halfTwoX = rugs.getX1();
                int halfOneY = rugs.getY2();
                int halfTwoY = rugs.getY1();

                board.add(rugOneInBoard, halfOneX, halfOneY);
                board.add(rugTwoInBoard, halfTwoX, halfTwoY);


            }
        });

        Button moveRugDown = createMoveButton(180.0);
        moveRugDown.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                board.getChildren().remove(rugTwoInBoard);
                board.getChildren().remove(rugOneInBoard);

                String newString = Marrakech.moveRugDown(rugString[0]);
                System.out.println(newString);

                rugString[0] = newString;
                Rugs rugs = new Rugs(newString);

                int halfOneX = rugs.getX2();
                int halfTwoX = rugs.getX1();
                int halfOneY = rugs.getY2();
                int halfTwoY = rugs.getY1();

                board.add(rugOneInBoard, halfOneX, halfOneY);
                board.add(rugTwoInBoard, halfTwoX, halfTwoY);


            }
        });
        upAndDownButtons.getChildren().addAll(moveRugUp,moveRugDown);
        upAndDownButtons.setAlignment(Pos.CENTER);

        Button moveRugLeft = createMoveButton(270.0);
        moveRugLeft.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                board.getChildren().remove(rugTwoInBoard);
                board.getChildren().remove(rugOneInBoard);

                String newString = Marrakech.moveRugLeft(rugString[0]);
                System.out.println(newString);

                rugString[0] = newString;
                Rugs rugs = new Rugs(newString);

                int halfOneX = rugs.getX2();
                int halfTwoX = rugs.getX1();
                int halfOneY = rugs.getY2();
                int halfTwoY = rugs.getY1();

                board.add(rugOneInBoard, halfOneX, halfOneY);
                board.add(rugTwoInBoard, halfTwoX, halfTwoY);
            }
        });

        Button moveRugRight = createMoveButton(90.0);
        moveRugRight.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                board.getChildren().remove(rugTwoInBoard);
                board.getChildren().remove(rugOneInBoard);

                String newString = Marrakech.moveRugRight(rugString[0]);
                System.out.println(newString);

                rugString[0] = newString;
                Rugs rugs = new Rugs(newString);

                int halfOneX = rugs.getX2();
                int halfTwoX = rugs.getX1();
                int halfOneY = rugs.getY2();
                int halfTwoY = rugs.getY1();

                board.add(rugOneInBoard, halfOneX, halfOneY);
                board.add(rugTwoInBoard, halfTwoX, halfTwoY);
            }
        });

        HBox allMoveButtons = new HBox(10);
        allMoveButtons.getChildren().addAll(moveRugLeft, upAndDownButtons, moveRugRight);
        allMoveButtons.setAlignment(Pos.CENTER);

        moveRugs.getChildren().addAll(rotateRugButtons, allMoveButtons);


        Button placeRugButton = createTextButton("PLACE RUG","#9FD395","#7EA976" );

        placeRugButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(gameString[0]);
                String newGameString = Marrakech.makePlacement(gameString[0], rugString[0]);
                gameString[0] = newGameString;
                System.out.println(gameString[0]);
            }
        });


        placeRugSection.getChildren().addAll(moveRugs,createASpacerForLayout(), placeRugButton);





        //TODO: if rug placement is invalid, the button stays gray, and will not allow to place rug
        // if rug placement is valid, the button turns green, and allow to place rug

        /**
         * "Players" section
         * display all player information (color, name, dirhams, rug)
         */
        GridPane playersSection = new GridPane(); //2x2
        playersSection.setPrefHeight(290);
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(50); // 50% of the height
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(50); // 50% of the height
        playersSection.getRowConstraints().addAll(row1, row2);



        //TODO: method to create players by number of player, Name, color, dirhams, rug
        // if it's possible, I prefer the order of player color always be c, y, r, p for beautiful design :)


        String[] playerName = {"Player 1", "Player 2", "Player 3", "Player 4"};
        char[] playerColor = {'c', 'y', 'r', 'p'};
        int numPlayers = 4;

        for (int i = 0; i < (numPlayers + 1) / 2; i++) {
            for (int j = 0; j < 2; j++) {
                int index = i * 2 + j;
                if (index < numPlayers) {
                    VBox player = createPlayerBox(playerName[index], playerColor[index], 30, 15);
                    playersSection.add(player, j, i); // column 0, row 0
                }
            }
        }

        rightPane.getChildren().addAll(moveAssamSection, payDirhamsSection,placeRugSection, playersSection);

        /**
         * Whole game layout
         */
        HBox mainLayout = new HBox(0, leftPane, rightPane);
        root.getChildren().add(mainLayout);

        stage.setScene(scene);
        stage.setTitle("Marrakech Game");
        stage.show();
    }

    private StackPane createAssamDuplication(Color Color){
        Group assam = new Group();
        //Assam's body

        assamBody = new SVGPath();
        assamBody.setContent("M25.4019 24.5C26.5566 22.5 29.4434 22.5 30.5981 24.5L49.6506 57.5C50.8053 59.5 49.362 62 47.0526 62H8.94744C6.63804 62 5.19466 59.5 6.34937 57.5L25.4019 24.5Z");
        assamBody.setFill(Color);
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
        assamHat.setFill(Color);
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

        Font font28 = Font.loadFont("file:assets/JockeyOne-Regular.ttf", 28);
        button.setFont(font28);
        button.setTextFill(Color.web("#FFFCE1"));
        button.setEffect(createDropShadowEffect(shadowColor));
        return button;
    }

    private Button createButtonImg(String imagePath, String buttonColor, String shadowColor) throws FileNotFoundException {
        FileInputStream input= new FileInputStream(imagePath);
        Image image = new Image(input);
        ImageView img =new ImageView(image);

        Button buttonImg = new Button();
        buttonImg.setGraphic(img);
        buttonImg.setPadding(new Insets(0, 10, 0, 10));
        buttonImg.setPrefSize(40, 40);

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

    private StackPane createRugHalfOne() {
        StackPane rugFace = new StackPane();
        Rectangle rug = new Rectangle(70, 70, Color.web("#FFFFFF"));
        rug.setArcHeight(7.0);
        rug.setArcWidth(7.0);
        rugFace.getChildren().add(rug);
        return rugFace;
    }

    private StackPane createRugHalfTwo() {
        StackPane rugFace = new StackPane();
        Rectangle rug = new Rectangle(70, 70, Color.web("#FFFFFF"));
        rug.setArcHeight(7.0);
        rug.setArcWidth(7.0);
        rugFace.getChildren().add(rug);
        return rugFace;
    }


    public StackPane updateAssamAppearance(String playerString) {
        Player player = new Player(playerString);
        // Update color by Player's color
        char color = player.getColor();
        Group assam = new Group();

        Color fillColor;
        if (color == 'c') {
            fillColor = Color.web("#1F8C86");
        } else if (color == 'y') {
            fillColor = Color.web("#FFA800");
        } else if (color == 'p') {
            fillColor = Color.web("#894FA5");
        } else if (color == 'r') {
            fillColor = Color.web("#E93119");
        } else {
            // Handle any other cases if needed
            fillColor = Color.web("#064B72"); // Default color
        }

        return createAssamDuplication(fillColor);
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

    private VBox createPlayerBox (String playerNameInput, char playerColor, int numberOfDirhams, int numberOfRugs) {
        VBox playerBox = new VBox(0);
        playerBox.setAlignment(Pos.CENTER);
        playerBox.setPrefWidth((WINDOW_WIDTH - LEFT_PANE_SIZE)/2);

        Label playerName = new Label(playerNameInput);
        playerName.setFont(font32);
        playerName.setTextFill(Color.WHITE);

        HBox playerAsset = new HBox(10);
        Font font24 = Font.loadFont("file:assets/JockeyOne-Regular.ttf", 24);

        HBox playerDirham = new HBox(0);

        StackPane smallDirhamIcon = createDirhamCoin();
        smallDirhamIcon.setScaleY(0.5);
        smallDirhamIcon.setScaleX(0.5);

        Label dirhams = new Label(Integer.toString(numberOfDirhams));
        dirhams.setFont(font24);
        dirhams.setTextFill(Color.WHITE);
        playerDirham.getChildren().addAll(smallDirhamIcon, dirhams);
        playerDirham.setAlignment(Pos.CENTER_LEFT);

        HBox playerRugs = new HBox(5);
        Label rugs = new Label("RUGS");
        rugs.setFont(font24);
        rugs.setTextFill(Color.WHITE);
        rugs.setPadding(new Insets(0, 5, 0,5));

        Label rugsNumber = new Label(Integer.toString(numberOfRugs));
        rugsNumber.setFont(font24);
        rugsNumber.setTextFill(Color.WHITE);
        playerRugs.getChildren().addAll(rugs, rugsNumber);
        playerRugs.setAlignment(Pos.CENTER_LEFT);

        playerAsset.getChildren().addAll(playerDirham, playerRugs);
        playerAsset.setAlignment(Pos.CENTER);

        playerBox.getChildren().addAll(playerName, playerAsset);

        switch (playerColor) {
            case 'c':
                playerBox.setStyle("-fx-background-color: #1F8C86");
                rugs.setStyle("-fx-background-color: #19706B");
                break;
            case 'y':
                playerBox.setStyle("-fx-background-color: #FFA800");
                rugs.setStyle("-fx-background-color: #A36B00");
                break;
            case 'r':
                playerBox.setStyle("-fx-background-color: #E93119");
                rugs.setStyle("-fx-background-color: #951F10");
                break;
            case 'p':
                playerBox.setStyle("-fx-background-color: #894FA5");
                rugs.setStyle("-fx-background-color: #58326A");
        }
        return playerBox;
    }

    // TODO: Đảm bảo rằng các mặt của xúc xắc được định nghĩa trong phần code của bạn.

    private void rollDiceAnimation(StackPane diceFace) {
        int animationDuration = 300; // Độ dài thời gian cho mỗi mặt xúc xắc (millisecond)
        int totalDuration = 3000; // Tổng thời gian của animation (3 giây)

        Timeline timeline = new Timeline();

        // Tạo hiệu ứng animation cho từng mặt của xúc xắc
        for (int i = 0; i < totalDuration / animationDuration; i++) {
            int finalI = i;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(finalI * animationDuration), e -> {
                int face = finalI % 4 + 1; // Đảm bảo rằng nó sẽ hiển thị mặt từ 1 đến 4
                displayDiceFace(diceFace, face); // Một hàm để hiển thị mặt xúc xắc tương ứng
            });

            timeline.getKeyFrames().add(keyFrame);
        }

        // Sau khi animation hoàn thành, chọn một mặt xúc xắc ngẫu nhiên và hiển thị nó
        timeline.setOnFinished(e -> {
            int randomFace = new Random().nextInt(4) + 1;
            displayDiceFace(diceFace, randomFace);
            // TODO: Xử lý sau khi hoàn thành animation (nếu cần)
        });

        timeline.play();
    }

    private void displayDiceFace(StackPane diceFace, int face) {
        // TODO: Cập nhật giao diện của diceFace để hiển thị mặt `face` của xúc xắc
    }


}

