package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.util.Duration;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
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

    private static Color darkBlue = Color.web("#064B72");
    private static Color yellow = Color.web("#FFA800");
    private static Color green = Color.web("#1F8C86");
    private static Color red = Color.web("#E93119");
    private static Color purple = Color.web("#894FA5");
    private static Color lightYellow = Color.web("#FFE6A9");

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
        final String[] gameString = {
                marrakech.getGameState()
        };
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
        //TODO make it so that the rotate buttons can only rotate at most 90* each direction
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

                assamString[0] = newAssam;

                Assam Assam = new Assam(newAssam);

                int assamX = Assam.getX();
                int assamY = Assam.getY();
                char orientation = Assam.getOrientation();

                board.add(assamInBoard, assamX, assamY);
            }
        });

        rollDice.getChildren().addAll(rollDiceButton, dice, amountOfSteps);
        rollDice.setAlignment(Pos.CENTER_RIGHT);

        moveAssamSection.setPadding(layoutPadding);
        moveAssamSection.getChildren().addAll(rotateAssam, createASpacerForLayoutHBox(), rollDice);

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
        payDirhamsSection.getChildren().addAll(amountOfDirhamsToPay, createASpacerForLayoutHBox(), payDirhamsButton);

        /**
         * "Place rug" functions section
         * buttons to change rug location (rotate and move)
         * "place rug" button
         * notice if the rug placement is invalid
         */
        final String[] rugString = {"c033332"};

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


//TODO Fix default case rotation problem - see methods at the bottom of Marrakech class
        Button rotateToLeftButton = createButtonImg("assets/rotateToLeft.png","#E66F51", "#AB513A");
        rotateToLeftButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                board.getChildren().remove(rugTwoInBoard);
                board.getChildren().remove(rugOneInBoard);

                String newString = Marrakech.rotateRugLeft(rugString[0]);

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

        //TODO implement a next turn/updated visual state when place rug button is pressed (it's the last part of a turn)
        Button placeRugButton = createTextButton("PLACE RUG","#9FD395","#7EA976" );

        placeRugButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Marrakech marrakech = new Marrakech();
                System.out.println(gameString[0]);
                String newGameString = Marrakech.makePlacement(gameString[0], rugString[0]);
                gameString[0] = newGameString;
                System.out.println(gameString[0]);
                marrakech.setGameInfo(gameString[0]);
            }
        });

        placeRugSection.getChildren().addAll(moveRugs,createASpacerForLayoutHBox(), placeRugButton);

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
        //Welcome scene with "NEW GAME" BUTTON
        VBox welcomeScene = new VBox(30);
        welcomeScene.setStyle("-fx-background-color: #FAEBCD;");

        Text welcomeText = new Text("WELCOME TO");
        welcomeText.setFont(font32);
        StackPane bigGameTitle = creatMarrakeckTitle();

        Button newGameBtn = createTextButton("NEW GAME >", "#064B72", "#053C5B");

        welcomeScene.setAlignment(Pos.CENTER);
        welcomeScene.getChildren().addAll(welcomeText, bigGameTitle, newGameBtn);

        Scene scene1 = new Scene(welcomeScene, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setTitle("Marrakeck Game");
        stage.setScene(scene1);
        stage.show();


        // "Game Prepare 1" scence with fill number of players
        VBox gamePrepare1 = new VBox(20);
        gamePrepare1.setPadding(new Insets(80, 0, 50, 0));
        gamePrepare1.setAlignment(Pos.CENTER);
        gamePrepare1.setStyle("-fx-background-color: #FAEBCD;");

        StackPane gameTitle1 = creatMarrakeckTitle();
        gameTitle1.setScaleX(0.5);
        gameTitle1.setScaleY(0.5);

        Text numberOfPlayers = new Text("HOW MANY PLAYERS YOU HAVE?");
        numberOfPlayers.setFont(font32);

        TextField playerInput = createTextField("ENTER A NUMBER FROM 2 TO 4", "\\d*");

        Button nextButton = createTextButton("NEXT", "#064B72", "#053C5B");

        gamePrepare1.getChildren().addAll(gameTitle1, createASpacerForLayoutVBox(), numberOfPlayers, playerInput, nextButton);

        Scene scene2 = new Scene(gamePrepare1, WINDOW_WIDTH, WINDOW_HEIGHT);

        //click on "NEW GAME" button, go to "Game Prepare 1" scence
        newGameBtn.setOnAction(e -> {
            stage.setScene(scene2);
        });

        // "Game Prepare 2" scence with fill players name
        VBox gamePrepare2 = new VBox(10);
        gamePrepare2.setPadding(new Insets(80, 0, 50, 0));
        gamePrepare2.setAlignment(Pos.CENTER);
        gamePrepare2.setStyle("-fx-background-color: #FAEBCD;");

        StackPane gameTitle2 = creatMarrakeckTitle();
        gameTitle2.setScaleX(0.5);
        gameTitle2.setScaleY(0.5);

        Text Name = new Text("PLAYERS　NAME");
        Name.setFont(font32);

        TextField player1Input = createTextField("PLAYER 1", "\\s");
        TextField player2Input =  createTextField("PLAYER 2", "\\s");
        TextField player3Input =  createTextField("PLAYER 3", "\\s");
        TextField player4Input =  createTextField("PLAYER 3", "\\s");

        Button startGameButton = createTextButton("START GAME", "#064B72", "#053C5B");
        gamePrepare2.getChildren().addAll(gameTitle2,createASpacerForLayoutVBox(), Name,player1Input,player2Input,player3Input,player4Input,startGameButton);

        Scene scene3 = new Scene(gamePrepare2, WINDOW_WIDTH, WINDOW_HEIGHT);

        nextButton.setOnAction(e -> {
            stage.setScene(scene3);
        });

        startGameButton.setOnAction(e -> {
            stage.setScene(scene);
        });

        HBox mainLayout = new HBox(leftPane, rightPane);
        root.getChildren().add(mainLayout);

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

    private Region createASpacerForLayoutHBox () {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    private Region createASpacerForLayoutVBox () {
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
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

//        // Tạo hiệu ứng animation cho từng mặt của xúc xắc
//        for (int i = 0; i < totalDuration / animationDuration; i++) {
//            int finalI = i;
//            KeyFrame keyFrame = new KeyFrame(Duration.millis(finalI * animationDuration), e -> {
//                int face = finalI % 4 + 1; // Đảm bảo rằng nó sẽ hiển thị mặt từ 1 đến 4
//                displayDiceFace(diceFace, face); // Một hàm để hiển thị mặt xúc xắc tương ứng
//            });
//
//            timeline.getKeyFrames().add(keyFrame);
//        }
//
//        // Sau khi animation hoàn thành, chọn một mặt xúc xắc ngẫu nhiên và hiển thị nó
//        timeline.setOnFinished(e -> {
//            int randomFace = new Random().nextInt(4) + 1;
//            displayDiceFace(diceFace, randomFace);
//            // TODO: Xử lý sau khi hoàn thành animation (nếu cần)
//        });

        timeline.play();
    }

    private StackPane creatMarrakeckTitle () {
        StackPane marrakeckTilte = new StackPane();

        Group mainText = new Group();

        SVGPath path1 = new SVGPath();
        path1.setContent("M789.781 168.364V25.5096H817.739V85.7124H818.76L844.269 25.5096H873.044L843.249 93.6714L874.473 168.364H843.453L819.168 105.916H817.739V168.364H789.781Z");
        path1.setFill(darkBlue);
        SVGPath path2 = new SVGPath();
        path2.setContent("M743.863 144.691H779.781V168.364H727.945C718.966 159.929 712.163 149.249 707.537 136.324C702.912 123.399 700.599 110.134 700.599 96.5285C700.599 82.7873 703.184 69.5903 708.354 56.9376C713.524 44.2848 721.211 33.8088 731.414 25.5096H779.781V49.9989H743.863C740.19 54.2165 736.993 61.0191 734.271 70.4066C731.687 79.7942 730.394 88.7736 730.394 97.3448C730.394 105.916 731.687 114.895 734.271 124.283C736.993 133.671 740.19 140.473 743.863 144.691Z");
        path2.setFill(green);
        SVGPath path3 = new SVGPath();
        path3.setContent("M616.519 142.854V0H689.782V24.4893H644.477V58.5702H684.885V82.2431H644.477V119.181H690.599V142.854H616.519Z");
        path3.setFill(yellow);
        SVGPath path4 = new SVGPath();
        path4.setContent("M521.827 168.364V25.5096H549.785V85.7124H550.806L576.315 25.5096H605.09L575.295 93.6714L606.519 168.364H575.499L551.214 105.916H549.785V168.364H521.827Z");
        path4.setFill(yellow);
        SVGPath path5 = new SVGPath();
        path5.setContent("M484.48 214.6L480.195 191.947H447.951L443.665 214.6H415.91L443.461 71.7459H485.705L511.827 214.6H484.48ZM463.664 98.276L455.705 145.826L451.216 170.111H476.725L472.032 145.826L464.073 98.276H463.664Z");
        path5.setFill(purple);
        SVGPath path6 = new SVGPath();
        path6.setContent("M320.606 25.5096H374.891C383.462 28.3667 390.741 33.6727 396.727 41.4277C402.849 49.0466 405.91 58.4341 405.91 69.5903C405.91 87.8212 398.7 101.222 384.278 109.794L404.89 168.364H376.319L358.973 116.324H348.156V168.364H320.606V25.5096ZM348.156 49.9989V93.4674H365.707C368.02 92.9232 370.537 90.6103 373.258 86.5288C376.115 82.3112 377.544 77.4133 377.544 71.8352C377.544 66.2571 376.319 61.3592 373.87 57.1416C371.557 52.924 369.244 50.5431 366.932 49.9989H348.156Z");
        path6.setFill(red);
        SVGPath path7 = new SVGPath();
        path7.setContent("M225.302 25.5096H279.586C288.158 28.3667 295.436 33.6727 301.423 41.4277C307.545 49.0466 310.606 58.4341 310.606 69.5903C310.606 87.8212 303.395 101.222 288.974 109.794L309.586 168.364H281.015L263.668 116.324H252.852V168.364H225.302V25.5096ZM252.852 49.9989V93.4674H270.403C272.716 92.9232 275.233 90.6103 277.954 86.5288C280.811 82.3112 282.239 77.4133 282.239 71.8352C282.239 66.2571 281.015 61.3592 278.566 57.1416C276.253 52.924 273.94 50.5431 271.627 49.9989H252.852Z");
        path7.setFill(darkBlue);
        SVGPath path8 = new SVGPath();
        path8.setContent("M187.955 142.854L183.67 120.202H151.426L147.14 142.854H119.385L146.936 0H189.18L215.302 142.854H187.955ZM167.139 26.53L159.18 74.0801L154.691 98.3653H180.2L175.507 74.0801L167.548 26.53H167.139Z");
        path8.setFill(purple);
        SVGPath path9 = new SVGPath();
        path9.setContent("M55.1009 103.059L65.917 58.3661L74.6923 25.5097H100.202L109.385 168.364H82.6513L78.7738 90.4062H77.7534L63.0599 147.956H46.3255L31.632 90.4062H30.6116L26.7341 168.364H0L9.18348 25.5097H34.6931L43.4685 58.3661L54.2846 103.059H55.1009Z");
        path9.setFill(green);

        mainText.getChildren().addAll(path1, path2, path3, path4, path5, path6, path7, path8, path9);

        Group textEffect = new Group();

        SVGPath path1e = new SVGPath();
        path1e.setContent("M800.781 178.364V35.5096H828.739V95.7124H829.76L855.269 35.5096H884.044L854.249 103.671L885.473 178.364H854.453L830.168 115.916H828.739V178.364H800.781Z");
        path1e.setFill(lightYellow);
        path1e.setStroke(lightYellow);
        path1e.setStrokeWidth(20);

        SVGPath path2e = new SVGPath();
        path2e.setContent("M754.863 154.691H790.781V178.364H738.945C729.966 169.929 723.163 159.249 718.537 146.324C713.912 133.399 711.599 120.134 711.599 106.529C711.599 92.7873 714.184 79.5903 719.354 66.9376C724.524 54.2848 732.211 43.8088 742.414 35.5096H790.781V59.9989H754.863C751.19 64.2165 747.993 71.0191 745.271 80.4066C742.687 89.7942 741.394 98.7736 741.394 107.345C741.394 115.916 742.687 124.895 745.271 134.283C747.993 143.671 751.19 150.473 754.863 154.691Z");
        path2e.setFill(lightYellow);
        path2e.setStroke(lightYellow);
        path2e.setStrokeWidth(20);

        SVGPath path3e = new SVGPath();
        path3e.setContent("M627.519 152.854V10H700.782V34.4893H655.477V68.5702H695.885V92.2431H655.477V129.181H701.599V152.854H627.519Z");
        path3e.setFill(lightYellow);
        path3e.setStroke(lightYellow);
        path3e.setStrokeWidth(20);

        SVGPath path4e = new SVGPath();
        path4e.setContent("M532.827 178.364V35.5096H560.785V95.7124H561.806L587.315 35.5096H616.09L586.295 103.671L617.519 178.364H586.499L562.214 115.916H560.785V178.364H532.827Z");
        path4e.setFill(lightYellow);
        path4e.setStroke(lightYellow);
        path4e.setStrokeWidth(20);

        SVGPath path5e = new SVGPath();
        path5e.setContent("M495.48 224.6L491.195 201.947H458.951L454.665 224.6H426.91L454.461 81.7459H496.705L522.827 224.6H495.48ZM474.664 108.276L466.705 155.826L462.216 180.111H487.725L483.032 155.826L475.073 108.276H474.664Z");
        path5e.setFill(lightYellow);
        path5e.setStroke(lightYellow);
        path5e.setStrokeWidth(20);

        SVGPath path6e = new SVGPath();
        path6e.setContent("M331.606 35.5096H385.891C394.462 38.3667 401.741 43.6727 407.727 51.4277C413.849 59.0466 416.91 68.4341 416.91 79.5903C416.91 97.8212 409.7 111.222 395.278 119.794L415.89 178.364H387.319L369.973 126.324H359.156V178.364H331.606V35.5096ZM359.156 59.9989V103.467H376.707C379.02 102.923 381.537 100.61 384.258 96.5288C387.115 92.3112 388.544 87.4133 388.544 81.8352C388.544 76.2571 387.319 71.3592 384.87 67.1416C382.557 62.924 380.244 60.5431 377.932 59.9989H359.156Z");
        path6e.setFill(lightYellow);
        path6e.setStroke(lightYellow);
        path6e.setStrokeWidth(20);

        SVGPath path7e = new SVGPath();
        path7e.setContent("M236.302 35.5096H290.586C299.158 38.3667 306.436 43.6727 312.423 51.4277C318.545 59.0466 321.606 68.4341 321.606 79.5903C321.606 97.8212 314.395 111.222 299.974 119.794L320.586 178.364H292.015L274.668 126.324H263.852V178.364H236.302V35.5096ZM263.852 59.9989V103.467H281.403C283.716 102.923 286.233 100.61 288.954 96.5288C291.811 92.3112 293.239 87.4133 293.239 81.8352C293.239 76.2571 292.015 71.3592 289.566 67.1416C287.253 62.924 284.94 60.5431 282.627 59.9989H263.852Z");
        path7e.setFill(lightYellow);
        path7e.setStroke(lightYellow);
        path7e.setStrokeWidth(20);

        SVGPath path8e = new SVGPath();
        path8e.setContent("M198.955 152.854L194.67 130.202H162.426L158.14 152.854H130.385L157.936 10H200.18L226.302 152.854H198.955ZM178.139 36.53L170.18 84.0801L165.691 108.365H191.2L186.507 84.0801L178.548 36.53H178.139Z");
        path8e.setFill(lightYellow);
        path8e.setStroke(lightYellow);
        path8e.setStrokeWidth(20);

        SVGPath path9e = new SVGPath();
        path9e.setContent("M66.1009 113.059L76.917 68.3661L85.6923 35.5097H111.202L120.385 178.364H93.6513L89.7738 100.406H88.7534L74.0599 157.956H57.3255L42.632 100.406H41.6116L37.7341 178.364H11L20.1835 35.5097H45.6931L54.4685 68.3661L65.2846 113.059H66.1009Z");
        path9e.setFill(lightYellow);
        path9e.setStroke(lightYellow);
        path9e.setStrokeWidth(20);

        textEffect.getChildren().addAll(path1e, path2e, path3e, path4e, path5e, path6e, path7e, path8e, path9e);

        marrakeckTilte.getChildren().addAll(textEffect, mainText);
        marrakeckTilte.setAlignment(Pos.CENTER);

        return marrakeckTilte;
    }

    private TextField createTextField (String promptText, String dataType) {
        TextField fillInfo = new TextField();
        fillInfo.setEditable(true);
        fillInfo.setPromptText(promptText);
        fillInfo.setMaxSize(360, 60);
        fillInfo.setMinSize(360, 60);
        fillInfo.setStyle("-fx-border-color: #D9D9D9; -fx-background-color: #D9D9D9; fx-prompt-text-fill: #9A9999;");

        fillInfo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(dataType)) { //only allow the data type set
                fillInfo.setText(newValue.replaceAll(dataType, "")); //replace all the incorrect data type by blank
            }
        });

        return fillInfo;
    }

}

