package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
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
import java.util.*;


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
    private StackPane dice1, dice2, dice3, dice4;

    private GridPane board;
    private StackPane rugOneInBoard;
    private StackPane rugTwoInBoard;
    private Label amountOfDirhamsDisplayed;
    private Label amountOfSteps;
    private StackPane assamStatus;
    private StackPane assamInBoard;
    private StackPane assamInPlaySection;
    private GridPane playersSection;
    private HBox moveAssamSection;
    private VBox rightPane;
    private StackPane leftPane;
    private Button rotateAssamToLeftButton;
    private Button rotateAssamToRightButton;
    private Button rotateToLeftButton;
    private Button rotateToRightButton;
    private Button moveRugUp;
    private Button moveRugDown;
    private Button moveRugLeft;
    private Button moveRugRight;
    private Button placeRugButton;
    private Button rollDiceButton;
    private Button payDirhamsButton;
    private String amountOwed;
    private int gameTurn = 1;
    private TextField player1Input;
    private TextField player2Input;
    private TextField player3Input;
    private TextField player4Input;
    private TextField numPlayerInput;

    private TextField field;
    private String player1Name;
    private String player2Name;
    private String player3Name;
    private String player4Name;
    private Scene gameScene;
    private int numOfPlayers;
    private int cyanScore;
    private int yellowScore;
    private int redScore;
    private int purpleScore;

    private final String[] gameString = {""};
    private final String[] assamString = {""};
    private final String[] rugString = {""};
    private final String[] playerString = {""};
    private Stage stage;
    private Label congratsText1;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // FIXME Task 7 and 15
        this.stage = stage;


        /**@Authority: Gennie Nguyen (GUI), tong yuan xiong (function)
         * Staring game: Welcome scene,input number of players scene, input Player's name scene
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

        numPlayerInput = createTextField("ENTER A NUMBER FROM 2 TO 4", "\\d*");


        Button nextButton = createTextButton("NEXT", "#064B72", "#053C5B");

        gamePrepare1.getChildren().addAll(gameTitle1, createASpacerForLayoutVBox(), numberOfPlayers, numPlayerInput, nextButton);

        Scene scene2 = new Scene(gamePrepare1, WINDOW_WIDTH, WINDOW_HEIGHT);

        // "Game Prepare 2" scence with fill players name
        VBox gamePrepare2 = new VBox(10);
        gamePrepare2.setPadding(new Insets(80, 0, 50, 0));
        gamePrepare2.setAlignment(Pos.CENTER);
        gamePrepare2.setStyle("-fx-background-color: #FAEBCD;");

        StackPane gameTitle2 = creatMarrakeckTitle();
        gameTitle2.setScaleX(0.5);
        gameTitle2.setScaleY(0.5);

        Text inputName = new Text("PLAYERS　NAME");
        inputName.setFont(font32);

        numPlayerInput.setText("4");
        numPlayerInput.textProperty().addListener(new ChangeListener<String>() {
              @Override
              public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                  if("1".equals(newValue)){
                      numPlayerInput.setText("2");
                  }

                  if("2".equals(newValue) || "3".equals(newValue)){
                      numPlayerInput.setText(newValue);
                  }

              }

          }
        );

        //TODO: (from Gennie to Terry) I fixed your code a bit here

        VBox playerNameInput = new VBox(5);
        playerNameInput.setAlignment(Pos.CENTER);
        String numberofplayers = numPlayerInput.getText();
        int num = Integer.parseInt(numberofplayers);
        List<TextField> playerFields = createPlayerNameFields(num);
        for (int i = 0; i <  playerFields.size(); i++) {
            field = playerFields.get(i);
            playerNameInput.getChildren().add(field);
//            gamePrepare2.getChildren().add(field);
        }

        Button startGameButton = createTextButton("START GAME", "#064B72", "#053C5B");
        gamePrepare2.getChildren().addAll(gameTitle2, createASpacerForLayoutVBox(), inputName, playerNameInput, startGameButton);

        Scene scene3 = new Scene(gamePrepare2, WINDOW_WIDTH, WINDOW_HEIGHT);

        //click on "NEW GAME" button, go to "Game Prepare 1" scence
        newGameBtn.setOnAction(e -> {
            stage.setScene(scene2);
        });

        //click on "NEXT" button, go to "Game Prepare 2" scence
        nextButton.setOnAction(e -> {
            numOfPlayers = Integer.parseInt(numPlayerInput.getText());
            stage.setScene(scene3);

            Marrakech marrakech = new Marrakech(numOfPlayers);
            gameString[0] = marrakech.getGameState();
            assamString[0] = gameString[0].substring(32,36); //assam string from game string
            rugString[0] = "c013332"; //base rug string
            playerString[0] = "Pc03015i";
        });

        //click on "START GAME" button, go to "main layout" Game scence
        startGameButton.setOnAction(e -> {
//            player1Name = playerFields.get(0).getText();
//            player2Name = playerFields.get(1).getText();
//            player3Name = playerFields.get(2).getText();
//            player4Name = playerFields.get(3).getText();
            if(playerFields.size()==1&&playerFields.size()==0){
                try {
                    throw new Exception("please enter the right number!");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
            if (playerFields.size()==2){
                player1Name = playerFields.get(0).getText();
                player2Name = playerFields.get(1).getText();
            } else if (playerFields.size()==3) {
                player1Name = playerFields.get(0).getText();
                player2Name = playerFields.get(1).getText();
                player3Name = playerFields.get(2).getText();
            } else if (playerFields.size()==4) {
                player1Name = playerFields.get(0).getText();
                player2Name = playerFields.get(1).getText();
                player3Name = playerFields.get(2).getText();
                player4Name = playerFields.get(3).getText();
            }

            if(player1Name==""||player2Name==""||player3Name==""||player4Name==""){
                try {
                    throw new Exception("please enter the name!");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }

            try {
                gameScene = createGameScene();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }

            stage.setScene(gameScene);
        });
    }

    /**
     * create the playerNameFields to fix the imput text
     *
     */

    private List<TextField> createPlayerNameFields(int number) {
        List<TextField> playerFields = new ArrayList<>();

        for (int i = 1; i <= number; i++) {
            TextField playerInput = createTextField("PLAYER " + i, "\\s");
            playerFields.add(playerInput);
        }

        return playerFields;
    }


    /** @Authority: Gennie Nguyen, Morris
     * Create main Game GUI
     */
    private Scene createGameScene() throws FileNotFoundException {
        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);
        /**
         * create left pane for game board, size 700x700
         * the game state (game string) will be displayed only in this area, including board, Assam, and any rug placed on board
         */
        leftPane = populateRugBoard(gameString[0]);
        /**
         * create right pane for all game play functions and player information, size 500x700
         * game functions include: rotate Assam, roll dice to move Assam, pay Dirhams if needed, place rug
         * player information include: color, name, amount of Dirhams, amount of Rug, status (by color)
         */
        rightPane = new VBox();
        rightPane.setPrefWidth(WINDOW_WIDTH - LEFT_PANE_SIZE);

        /**
         * Right Pane populating section
         */
        HBox moveAssamSection = createMoveAssamSection();
        HBox payDirhamsSection = createPayDirhamsSection("000");
        HBox placeRugSection = createPlaceRugSection(gameString[0], rugString[0]);
        GridPane playersSection = createPlayerSection();
        rightPane.getChildren().addAll(moveAssamSection, payDirhamsSection, placeRugSection, playersSection);

        setButtonDisable(payDirhamsButton, true);
        setButtonDisable(placeRugButton, true);
        setButtonDisable(rotateToRightButton, true);
        setButtonDisable(rotateToLeftButton, true);
        setButtonDisable(moveRugUp, true);
        setButtonDisable(moveRugDown, true);
        setButtonDisable(moveRugLeft, true);
        setButtonDisable(moveRugRight, true);

        //All buttons
        payDirhamsButton.setOnAction(event -> {
            try {
                handleDirhamPayment();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        rotateAssamToLeftButton.setOnAction(event -> handleAssamRotation(true));
        rotateAssamToRightButton.setOnAction(event -> handleAssamRotation(false));
        rollDiceButton.setOnAction(event -> handleAssamMovement());
        rotateToLeftButton.setOnAction(event -> handleRugRotation(true));
        rotateToRightButton.setOnAction(event -> handleRugRotation(false));
        moveRugUp.setOnAction(event -> handleRugMovement("up"));
        moveRugDown.setOnAction(event -> handleRugMovement("down"));
        moveRugLeft.setOnAction(event -> handleRugMovement("left"));
        moveRugRight.setOnAction(event -> handleRugMovement("right"));
        placeRugButton.setOnAction(event -> {
            try {
                handleRugPlacement();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        HBox mainLayout = new HBox(leftPane, rightPane);
        root.getChildren().add(mainLayout);

        return scene;
    }

    /**
     * @Authority: Gennie Nguyen (GUI), Morris (orientation update)
     * @param Color a String represents color of Assam's hat and Body, set by Player's color, to show Player's turn
     * @param Orientation a Char represents Assam's orientation
     * @return a StackPane of Assam appearance and state
     */
    private StackPane createAssamDuplication(Color Color, char Orientation){
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

        Rotate rotation = new Rotate();

        rotation.setPivotX(28);
        rotation.setPivotY(28);

        switch (Orientation) {
            case 'N':
                rotation.setAngle(180); // Set the desired rotation angle for 'N'
                break;
            case 'E':
                rotation.setAngle(270); // Set the desired rotation angle for 'E'
                break;
            case 'S':
                rotation.setAngle(0); // Set the desired rotation angle for 'S'
                break;
            case 'W':
                rotation.setAngle(90); // Set the desired rotation angle for 'W'
                break;
        }

        assam.getTransforms().add(rotation);

        //Assam's position
        StackPane tileWithAssam = new StackPane();
        tileWithAssam.getChildren().add(assam);

        return tileWithAssam;
    }

    /** @Authority: Gennie Nguyen
     * @return a StackPane of DirHam Icon for display
     */
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

    /**
     * @Authority: Gennie Nguyen
     * @param shadowColor a String represents color drop shadow
     * @return a DropShadow effect
     */
    private DropShadow createDropShadowEffect(String shadowColor) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetY(7.0);
        dropShadow.setColor(Color.web(shadowColor));
        dropShadow.setRadius(0.0);
        return dropShadow;
    }

    private static class ButtonStyle {
        private final String style;
        private final Effect effect;

        public ButtonStyle(String style, Effect effect) {
            this.style = style;
            this.effect = effect;
        }
    }

    /**
     * @Authority: Gennie Nguyen
     * set style if button is disable, and return to the old style when button is enable
     * @param button
     * @param isDisable
     */
    private void setButtonDisable (Button button, Boolean isDisable) {
        button.setDisable(isDisable);
        if (isDisable) {
            // Only store the current style and effect if it hasn't been stored before
            if (button.getUserData() == null || !(button.getUserData() instanceof ButtonStyle)) {
                ButtonStyle originalStyle = new ButtonStyle(button.getStyle(), button.getEffect());
                button.setUserData(originalStyle);
            }

            button.setStyle("-fx-background-color: #9D9D9D; -fx-opacity: 1;");
            button.setEffect(createDropShadowEffect("6A6A6A"));
        } else {
            // Restore the original style and effect
            if (button.getUserData() != null && button.getUserData() instanceof ButtonStyle) {
                ButtonStyle storedStyle = (ButtonStyle) button.getUserData();
                button.setStyle(storedStyle.style);
                button.setEffect(storedStyle.effect);
            }
        }
    }

    /**
     * @Authority: Gennie Nguyen
     * create button with text
     *
     */
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

    /**
     * @Authority: Gennie Nguyen
     * create button with image
     *
     */
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

    /**
     * @Authority: Gennie Nguyen
     * create button with shape, specifically for move rug buttons
     *
     */
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

    /**
     * @Authority: Gennie Nguyen
     * create a spacer to align layout in HBox
     *
     */
    private Region createASpacerForLayoutHBox () {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    /**
     * @Authority: Gennie Nguyen
     * create a spacer to align layout in VBox
     *
     */
    private Region createASpacerForLayoutVBox () {
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    /**
     * @Authority: Gennie Nguyen
     * create a triangle, specifically for shape in button (move rug buttons)
     *
     */
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
    private StackPane createRugHalfOne(String rugString) {
        char color = rugString.charAt(0);
        StackPane rugFace = new StackPane();
        Rectangle rug = new Rectangle(70, 70, Color.web("#FFFFFF"));
        rug.setArcHeight(7.0);
        rug.setArcWidth(7.0);

        if (color == 'r') {
            rug.setFill(red);
        }
        if (color == 'y') {
            rug.setFill(yellow);
        }
        if (color == 'p') {
            rug.setFill(purple);
        }
        if (color == 'c') {
            rug.setFill(green);
        }

        rugFace.getChildren().add(rug);
        return rugFace;
    }
    private StackPane createRugHalfTwo(String rugString) {
        char color = rugString.charAt(0);
        StackPane rugFace = new StackPane();
        Rectangle rug = new Rectangle(70, 70, Color.web("#FFFFFF"));
        rug.setArcHeight(7.0);
        rug.setArcWidth(7.0);

        if (color == 'r') {
            rug.setFill(red);
        }
        if (color == 'y') {
            rug.setFill(yellow);
        }
        if (color == 'p') {
            rug.setFill(purple);
        }
        if (color == 'c') {
            rug.setFill(green);
        }
        rugFace.getChildren().add(rug);
        return rugFace;
    }

    public StackPane updateAssamAppearance(String playerString, String assamString) {
        Player player = new Player(playerString);
        // Update color by Player's color
        char color = player.getColor();
        Assam assam = new Assam(assamString);
        char orientation = assam.getOrientation();

        Color fillColor;
        if (color == 'c') {
            fillColor = Color.web("#19706B");
        } else if (color == 'y') {
            fillColor = Color.web("#A36B00");
        } else if (color == 'p') {
            fillColor = Color.web("#58326A");
        } else if (color == 'r') {
            fillColor = Color.web("#951F10");
        } else {
            // Handle any other cases if needed
            fillColor = Color.web("#064B72"); // Default color
        }

        return createAssamDuplication(fillColor, orientation);
    }

    /**
     * @Authority: Gennie Nguyen
     * create dice faces with dice number 1-4
     *
     */
    private StackPane createDiceFace (int number) {
        StackPane diceFace = new StackPane();
        Rectangle square = new Rectangle(39, 39, Color.web("#FFE6A9"));
        square.setArcWidth(7.0);
        square.setArcHeight(7.0);
        diceFace.getChildren().add(square);
        diceFace.setUserData(square);

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

    /**
     * @Authority: Gennie Nguyen
     * create game title "MARRAKECK" for UI use
     *
     */
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

    /**
     * @Authority: Gennie Nguyen
     * create a fill box for information input
     *
     */
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
    private StackPane populateRugBoard(String gameString) {
        String boardString = gameString.substring(37, 184);
        String assamString = gameString.substring(32, 36);

        List<Character> colorList = new ArrayList<>();
        for (int i = 0; i < boardString.length(); i += 3) {
            char color = boardString.charAt(i);
            colorList.add(color);
        }

        //board grid with tiles in centre
        board = new GridPane();
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
                char color = colorList.get(0);
                colorList.remove(0);

                switch (color) {
                    case 'r' -> tile.setFill(red);
                    case 'p' -> tile.setFill(purple);
                    case 'c' -> tile.setFill(green);
                    case 'y' -> tile.setFill(yellow);
                    default -> {
                        if ((i + j) % 2 == 0) {
                            tile.setFill(Color.web("#F3A261"));
                        } else {
                            tile.setFill(Color.web("#FFE6A9"));
                        }
                    }
                }
                board.add(tile, i, j);
            }
        }
        //Assam on board
        assamInBoard = updateAssamAppearance(playerString[0], assamString);
        Assam assam = new Assam(assamString);


        board.add(assamInBoard, assam.getX(), assam.getY());
        //A mini version of Assam in Move section, to display real time status of assamInBoard
        //ex: if assamInBoard changes color by player, assamInPlaySection change color too
        StackPane assamInPlaySection = updateAssamAppearance(playerString[0], assamString);

        assamStatus = new StackPane();
        assamStatus.getChildren().add(assamInPlaySection);
        assamStatus.setStyle("-fx-background-color: #A7A7A7; -fx-background-radius: 7; -fx-border-radius: 7;");
        assamStatus.setMinSize(70, 70);
        assamStatus.setMaxSize(70, 70);

        //layer with circles around game board
        StackPane boardWithCircles = new StackPane(board);
        String imageUrl = "file:assets/boardWithCircles.png";
        boardWithCircles.setStyle("-fx-background-image: url('" + imageUrl + "'); -fx-background-position: center center; -fx-background-repeat: stretch;");

        leftPane = new StackPane(boardWithCircles);
        leftPane.setPrefSize(LEFT_PANE_SIZE, LEFT_PANE_SIZE);
        leftPane.setStyle("-fx-background-color: #E66F51");

        return leftPane;
    }

    /**
     * @Authority: Gennie Nguyen
     * create "pay Dirhams" section, with number of dirhams needed to be paid displayed, and "pay dirhams" button
     *
     */
    private HBox createPayDirhamsSection(String amountDirhams) {
        HBox payDirhamsSection = new HBox();
        payDirhamsSection.setStyle("-fx-background-color: #FFE6A9");
        payDirhamsSection.setPrefHeight(90);
        payDirhamsSection.setMaxWidth(Double.MAX_VALUE);
        payDirhamsSection.setAlignment(Pos.CENTER);

        HBox amountOfDirhamsToPay = new HBox(10);

        amountOfDirhamsDisplayed = new Label();
        amountOfDirhamsDisplayed.setText(amountDirhams);
        amountOfDirhamsDisplayed.setFont(font32);
        amountOfDirhamsDisplayed.setTextFill(Color.web("1F1F1F"));

        amountOfDirhamsToPay.getChildren().addAll(createDirhamCoin(), amountOfDirhamsDisplayed);
        amountOfDirhamsToPay.setAlignment(Pos.CENTER_LEFT);

        payDirhamsButton = createTextButton("PAY DIRHAMS","#FFC700","#EBB700");

        payDirhamsSection.setPadding(layoutPadding);
        payDirhamsSection.getChildren().addAll(amountOfDirhamsToPay, createASpacerForLayoutHBox(), payDirhamsButton);

        return payDirhamsSection;
    }
    private String updateDirhams(String gameString) {
        amountOwed = String.valueOf(Marrakech.getPaymentAmount(gameString));
        amountOfDirhamsDisplayed.setText(amountOwed);
        if (amountOwed == null){
            amountOwed = "000";
        }
        return amountOwed;
    }

    /**
     * @Authority: Gennie Nguyen
     * create one box for each player, have player name, color, number of rugs, number of dirhams
     *
     */
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

    /**
     * @Authority: Gennie Nguyen
     * create "Players" Section, all players
     *
     */
    private GridPane createPlayerSection() {
        Marrakech marrakech = new Marrakech();
        marrakech.setGameInfo(gameString[0]);

        GridPane playersSection = new GridPane(); //2x2
        playersSection.setPrefHeight(290);
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(50); // 50% of the height
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(50); // 50% of the height
        playersSection.getRowConstraints().addAll(row1, row2);


        String[] playerName = {player1Name, player2Name, player3Name, player4Name};
        char[] playerColor = {'c', 'y', 'r', 'p'};
        int numPlayers = numOfPlayers;

        for (int i = 1; i <= numPlayers; i++) {
            int index = i - 1;
            Player[] players = Marrakech.players;
            int numDirhams = players[index].getNumberOfDirhams();
            int numRugs = players[index].getNumberOfRugs();
            VBox player = createPlayerBox(playerName[index], playerColor[index], numDirhams, numRugs);
            playersSection.add(player, (i - 1) % 2, (i - 1) / 2); // Column and Row logic adjusted
        }
        return playersSection;
    }

    /** @Authority: Gennie Nguyen
     * "Place rug" functions section
     * buttons to change rug location (rotate and move)
     * "place rug" button
     * notice if the rug placement is invalid
     */
    private HBox createPlaceRugSection(String gameString, String rugString) throws FileNotFoundException {

        HBox placeRugSection = new HBox();
        placeRugSection.setStyle("-fx-background-color: #FFFCE1");
        placeRugSection.setPrefHeight(140);
        placeRugSection.setMaxWidth(Double.MAX_VALUE);
        placeRugSection.setPadding(layoutPadding);
        placeRugSection.setAlignment(Pos.CENTER);
        placeRugButton = createTextButton("PLACE RUG","#9FD395","#7EA976" );

        //group of (rotate to the left, rotate to the right)
        VBox rotateRugButtons = new VBox(15);
        rotateToLeftButton = createButtonImg("assets/rotateToLeft.png","#E66F51", "#AB513A");
        rotateToRightButton = createButtonImg("assets/rotateToRight.png", "#E66F51", "#AB513A");

        rotateRugButtons.getChildren().addAll(rotateToLeftButton, rotateToRightButton);
        rotateRugButtons.setAlignment(Pos.CENTER);

        //group of move rug buttons (rotate to the left, rotate to the right, up, down, left, right)
        HBox moveRugs = new HBox(30);
        //group of (up, down, left, right)
        VBox upAndDownButtons = new VBox(15);

        moveRugUp = createMoveButton(0.0);
        moveRugDown = createMoveButton(180.0);

        upAndDownButtons.getChildren().addAll(moveRugUp,moveRugDown);
        upAndDownButtons.setAlignment(Pos.CENTER);

        moveRugLeft = createMoveButton(270.0);
        moveRugRight = createMoveButton(90.0);

        HBox allMoveButtons = new HBox(10);

        allMoveButtons.getChildren().addAll(moveRugLeft, upAndDownButtons, moveRugRight);
        allMoveButtons.setAlignment(Pos.CENTER);

        moveRugs.getChildren().addAll(rotateRugButtons, allMoveButtons);

        placeRugSection.getChildren().addAll(moveRugs,createASpacerForLayoutHBox(), placeRugButton);

        return placeRugSection;
    }

    /**@Authority: Gennie Nguyen
     * "Move Assam" functions section, rotate Assam and roll dice
     */
    private HBox createMoveAssamSection() throws FileNotFoundException {

        rollDiceButton = createTextButton("ROLL DICE","#064B72","#053C5B" );
        //TODO: when hit rollDiceButton, 2 things happen (an animation run through all dice faces then display the one = dice number,

        HBox moveAssamSection = new HBox();
        moveAssamSection.setStyle("-fx-background-color: #FFFCE1");
        moveAssamSection.setPrefHeight(180);
        moveAssamSection.setMaxWidth(Double.MAX_VALUE);

        //"Rotate Assam" functions section, rotate to left or right
        HBox rotateAssam = new HBox(13);
        //TODO make it so that the rotate buttons can only rotate at most 90* each direction
        rotateAssamToLeftButton = createButtonImg("assets/rotateToLeft.png","#064B72","#053C5B" );
        rotateAssamToRightButton = createButtonImg("assets/rotateToRight.png", "#064B72","#053C5B");


        rotateAssam.getChildren().addAll(rotateAssamToLeftButton, assamStatus, rotateAssamToRightButton);
        rotateAssam.setAlignment(Pos.CENTER);

        //"Roll dice" functions section
        VBox rollDice = new VBox(15);
        rollDice.setAlignment(Pos.CENTER_RIGHT);

        HBox dice = new HBox(10);
        dice1 = createDiceFace(1);
        dice2 = createDiceFace(2);
        dice3 = createDiceFace(3);
        dice4 = createDiceFace(4);
        dice.getChildren().addAll(dice1, dice2, dice3, dice4);

        amountOfSteps = new Label("ASSAM WILL MOVE \"X\" STEPS");
        Font font18 = Font.loadFont("file:assets/JockeyOne-Regular.ttf", 18);
        amountOfSteps.setFont(font18);
        amountOfSteps.setTextFill(Color.web("064B72"));

        rollDice.getChildren().addAll(rollDiceButton, dice, amountOfSteps);
        rollDice.setAlignment(Pos.CENTER_RIGHT);

        moveAssamSection.setPadding(layoutPadding);
        moveAssamSection.getChildren().addAll(rotateAssam, createASpacerForLayoutHBox(), rollDice);

        return moveAssamSection;
    }

    private void handleRugMovement(String direction) {
        board.getChildren().remove(rugTwoInBoard);
        board.getChildren().remove(rugOneInBoard);

        String newString = Marrakech.moveRug(rugString[0], direction);

        rugString[0] = newString;
        Rugs rugs = new Rugs(rugString[0]);

        int halfOneX = rugs.getX2();
        int halfTwoX = rugs.getX1();
        int halfOneY = rugs.getY2();
        int halfTwoY = rugs.getY1();

        board.add(rugOneInBoard, halfOneX, halfOneY);
        board.add(rugTwoInBoard, halfTwoX, halfTwoY);

        if (!Marrakech.isRugValid(gameString[0], rugString[0]) || !Marrakech.isPlacementValid(gameString[0], rugString[0])) {
            setButtonDisable(placeRugButton, true);
        }

        if (Marrakech.isRugValid(gameString[0], rugString[0]) && Marrakech.isPlacementValid(gameString[0], rugString[0])) {
            setButtonDisable(placeRugButton, false);
        }
    }
    private void handleRugRotation(Boolean rotateLeft) {
        board.getChildren().remove(rugTwoInBoard);
        board.getChildren().remove(rugOneInBoard);

        String newString;
        if (rotateLeft) {
            newString = Marrakech.rotateRug(rugString[0], true);
        }
        else {
            newString = Marrakech.rotateRug(rugString[0], false);
        }

        rugString[0] = newString;
        Rugs rugs = new Rugs(rugString[0]);

        int halfOneX = rugs.getX2();
        int halfTwoX = rugs.getX1();
        int halfOneY = rugs.getY2();
        int halfTwoY = rugs.getY1();

        board.add(rugOneInBoard, halfOneX, halfOneY);
        board.add(rugTwoInBoard, halfTwoX, halfTwoY);

        if (!Marrakech.isRugValid(gameString[0], rugString[0]) || !Marrakech.isPlacementValid(gameString[0], rugString[0])) {
            setButtonDisable(placeRugButton, true);
        }
        if (Marrakech.isRugValid(gameString[0], rugString[0]) && Marrakech.isPlacementValid(gameString[0], rugString[0])) {
            setButtonDisable(placeRugButton, false);
        }
    }

    private void handleAssamRotation(boolean rotateLeft) {
        setButtonDisable(rotateAssamToLeftButton, true);
        setButtonDisable(rotateAssamToRightButton, true);

        String newAssam;
        if (rotateLeft){
            newAssam = Marrakech.rotateAssam(assamString[0], 270);
        }
        else {
            newAssam = Marrakech.rotateAssam(assamString[0], 90);
        }

        assamString[0] = newAssam;

        gameString[0] = gameString[0].substring(0, 32) + assamString[0] + gameString[0].substring(36, 184);

        assamStatus.getChildren().clear();
        StackPane newAssamStatus = updateAssamAppearance(playerString[0], assamString[0]);

        newAssamStatus.setStyle("-fx-background-color: #A7A7A7; -fx-background-radius: 7; -fx-border-radius: 7;");
        newAssamStatus.setMinSize(70, 70);
        newAssamStatus.setMaxSize(70, 70);

        assamStatus.getChildren().add(newAssamStatus);

        assamInBoard.getChildren().clear();
        StackPane newAssamBoard = updateAssamAppearance(playerString[0], assamString[0]);
        assamInBoard.getChildren().add(newAssamBoard);
    }

    private void handleAssamMovement() {
        setButtonDisable(rotateAssamToLeftButton, true);
        setButtonDisable(rotateAssamToRightButton, true);
        setButtonDisable(rollDiceButton, true);
        setButtonDisable(payDirhamsButton, false);

        // Create dice rolling animation
        animateDiceRolling(() -> {

        int result = Marrakech.rollDie();
        String newAssam = Marrakech.moveAssam(assamString[0], result);
        amountOfSteps.setText("ASSAM WILL MOVE \"" + result + "\" STEPS");

        assamString[0] = newAssam;

        gameString[0] = gameString[0].substring(0, 32) + assamString[0] + gameString[0].substring(36, 184);

        Assam Assam = new Assam(newAssam);

        int assamX = Assam.getX();
        int assamY = Assam.getY();

        board.getChildren().remove(assamInBoard);
        assamInBoard.getChildren().clear();
        StackPane newAssamInBoard = updateAssamAppearance(playerString[0], assamString[0]);
        assamInBoard.getChildren().add(newAssamInBoard);
        board.add(assamInBoard, assamX, assamY);

        assamStatus.getChildren().clear();
        StackPane newAssamStatus = updateAssamAppearance(playerString[0], assamString[0]);

        newAssamStatus.setStyle("-fx-background-color: #A7A7A7; -fx-background-radius: 7; -fx-border-radius: 7;");
        newAssamStatus.setMinSize(70, 70);
        newAssamStatus.setMaxSize(70, 70);

        assamStatus.getChildren().add(newAssamStatus);

        updateDirhams(gameString[0]);

            if ((amountOwed == null) || (amountOwed.equals("0"))) {
                setButtonDisable(rotateAssamToLeftButton, true);
                setButtonDisable(rotateAssamToRightButton, true);
                setButtonDisable(rollDiceButton, true);
                setButtonDisable(rotateToRightButton, false);
                setButtonDisable(rotateToLeftButton, false);
                setButtonDisable(moveRugUp, false);
                setButtonDisable(moveRugDown, false);
                setButtonDisable(moveRugLeft, false);
                setButtonDisable(moveRugRight, false);
                setButtonDisable(payDirhamsButton, true);
                if (Marrakech.isRugValid(gameString[0], rugString[0]) && Marrakech.isPlacementValid(gameString[0], rugString[0]) && placeRugButton.isDisabled()) {
                    setButtonDisable(placeRugButton, false);
                }

                if ((!Marrakech.isRugValid(gameString[0], rugString[0]) || !Marrakech.isPlacementValid(gameString[0], rugString[0])) && placeRugButton.isDisabled()) {
                    //Do nothing
                }
                rugOneInBoard = createRugHalfOne(rugString[0]);
                rugTwoInBoard = createRugHalfTwo(rugString[0]);
                board.add(rugOneInBoard, 3, 3);
                board.add(rugTwoInBoard, 3, 2);
            }
        });
    }

    /**@Authority: Gennie
     * Animation run through dice faces for roll dice effect
     */
    private void animateDiceRolling(Runnable onFinish) {
        Timeline timeline = new Timeline();
        Duration frameGap = Duration.millis(200); // Adjust as per the required speed
        Duration currentDuration = Duration.ZERO;

        StackPane[] dices = new StackPane[]{dice1, dice2, dice3, dice4};

        for (int i = 0; i < dices.length; i++) {
            final int index = i;

            // Turn on the current dice by setting its fill to original color
            KeyFrame keyFrameOn = new KeyFrame(currentDuration = currentDuration.add(frameGap), e -> {
                Rectangle square = (Rectangle) dices[index].getUserData();
                square.setFill(Color.web("#FFE6A9"));
            });
            timeline.getKeyFrames().add(keyFrameOn);

            // Turn off the current dice by setting its fill to white
            KeyFrame keyFrameOff = new KeyFrame(currentDuration = currentDuration.add(frameGap), e -> {
                Rectangle square = (Rectangle) dices[index].getUserData();
                square.setFill(Color.WHITE);
                dices[index].setEffect(createDropShadowEffect("#E0E0E0"));
            });
            timeline.getKeyFrames().add(keyFrameOff);

            // Turn on again
            KeyFrame keyFrameReset = new KeyFrame(currentDuration = currentDuration.add(frameGap), e -> {
                Rectangle square = (Rectangle) dices[index].getUserData();
                square.setFill(Color.web("#FFE6A9"));
                dices[index].setEffect(createDropShadowEffect("#E8C777"));
            });
            timeline.getKeyFrames().add(keyFrameReset);
        }

        timeline.setOnFinished(e -> onFinish.run());
        timeline.setCycleCount(1);
        timeline.play();
    }


    private void handleRugPlacement() throws FileNotFoundException {
        Marrakech marrakech = new Marrakech();
        String newGameString = marrakech.makePlacement(gameString[0], rugString[0]);
        gameString[0] = newGameString;

        board.getChildren().clear();
        board.getChildren().addAll(populateRugBoard(gameString[0]));

        newGameTurn();
    }

    private void handleDirhamPayment() throws FileNotFoundException {
        int amountDirhamsToPay = Integer.parseInt(amountOwed);
        if (amountDirhamsToPay != 0) {
            String boardString = gameString[0].substring(37, 184);

            Assam assam = new Assam(assamString[0]);
            Board newBoard = new Board(boardString);
            String abbBoardString = newBoard.getTile(assam.getX(), assam.getY());

            Player playerA = new Player(playerString[0]);
            int numDirhamSub = playerA.subDirhams(amountDirhamsToPay);
            playerA.setNumberOfDirhams(numDirhamSub);

            playerString[0] = playerA.getPlayerState();

            String playerBString = "";
            char firstChar = abbBoardString.charAt(0);
            switch (firstChar) {
                case 'c':
                    playerBString = gameString[0].substring(0, 8);
                    break;
                case 'y':
                    playerBString = gameString[0].substring(8, 16);
                    break;
                case 'r':
                    playerBString = gameString[0].substring(16, 24);
                    break;
                case 'p':
                    playerBString = gameString[0].substring(24, 32);
                    break;
            }
            Player playerB = new Player(playerBString);
            if (firstChar != playerA.getColor()){
                int numDirhamsAdd = playerB.addDirhams(amountDirhamsToPay);
                playerB.setNumberOfDirhams(numDirhamsAdd);
            }

            rightPane = new VBox();
            rightPane.setPrefWidth(WINDOW_WIDTH - LEFT_PANE_SIZE);

            /**
             * Right Pane populating section
             */
            moveAssamSection = createMoveAssamSection();
            HBox payDirhamsSection = createPayDirhamsSection("000");
            HBox placeRugSection = createPlaceRugSection(gameString[0], rugString[0]);
            updateGameStringPlayerString(playerA.getPlayerState(), playerB.getPlayerState(), playerBString);
            GridPane updatedPlayersSection = createPlayerSection();
            rightPane.getChildren().addAll(moveAssamSection, payDirhamsSection, placeRugSection, updatedPlayersSection);
            HBox mainLayout = new HBox(leftPane, rightPane);
            root.getChildren().add(mainLayout);

            //All buttons
            payDirhamsButton.setOnAction(event -> {
                try {
                    handleDirhamPayment();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
            rotateAssamToLeftButton.setOnAction(event -> handleAssamRotation(true));
            rotateAssamToRightButton.setOnAction(event -> handleAssamRotation(false));
            rollDiceButton.setOnAction(event -> handleAssamMovement());
            rotateToLeftButton.setOnAction(event -> handleRugRotation(true));
            rotateToRightButton.setOnAction(event -> handleRugRotation(false));
            moveRugUp.setOnAction(event -> handleRugMovement("up"));
            moveRugDown.setOnAction(event -> handleRugMovement("down"));
            moveRugLeft.setOnAction(event -> handleRugMovement("left"));
            moveRugRight.setOnAction(event -> handleRugMovement("right"));
            placeRugButton.setOnAction(event -> {
                try {
                    handleRugPlacement();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        setButtonDisable(rotateAssamToLeftButton,true);
        setButtonDisable(rotateAssamToRightButton,true);
        setButtonDisable(rollDiceButton,true);
        setButtonDisable(rotateToRightButton,false);
        setButtonDisable(rotateToLeftButton,false);
        setButtonDisable(moveRugUp,false);
        setButtonDisable(moveRugDown,false);
        setButtonDisable(moveRugLeft,false);
        setButtonDisable(moveRugRight,false);
        setButtonDisable(payDirhamsButton,true);


        rugOneInBoard = createRugHalfOne(rugString[0]);
        rugTwoInBoard = createRugHalfTwo(rugString[0]);
        board.add(rugOneInBoard, 3, 3);
        board.add(rugTwoInBoard, 3, 2);

    }
    private void newGameTurn() throws FileNotFoundException {
        Marrakech.resetRotationState();

        gameTurn += 1;
        int currentIndex = 0;
        switch (numOfPlayers) {
            case 2:
                currentIndex = (gameTurn - 1 + 2) % 2;
                break;
            case 3:
                currentIndex = (gameTurn - 1 + 3) % 3;
                break;
            case 4:
                currentIndex = (gameTurn - 1 + 4) % 4;
                break;
        }


        Marrakech marrakech = new Marrakech();
        marrakech.setGameInfo(gameString[0]);
        gameString[0] = marrakech.getGameState();
        assamString[0] = gameString[0].substring(32,36); //assam string from game string
        String formattedGameTurn = String.format("%02d", gameTurn);
        playerString[0] = gameString[0].substring(8 * (currentIndex), 8 * (currentIndex + 1));
        rugString[0] = playerString[0].substring(1, 2) + formattedGameTurn + "3332";


        StackPane leftPane = populateRugBoard(gameString[0]);

        rightPane = new VBox();
        rightPane.setPrefWidth(WINDOW_WIDTH - LEFT_PANE_SIZE);

        /**
         * Right Pane populating section
         */
        HBox moveAssamSection = createMoveAssamSection();
        HBox payDirhamsSection = createPayDirhamsSection("000");
        HBox placeRugSection = createPlaceRugSection(gameString[0], rugString[0]);
        playersSection = createPlayerSection();
        rightPane.getChildren().addAll(moveAssamSection, payDirhamsSection, placeRugSection, playersSection);
        HBox mainLayout = new HBox(leftPane, rightPane);
        root.getChildren().add(mainLayout);

        setButtonDisable(payDirhamsButton, true);
        setButtonDisable(moveRugUp, true);
        setButtonDisable(moveRugDown, true);
        setButtonDisable(moveRugLeft, true);
        setButtonDisable(moveRugRight, true);
        setButtonDisable(rotateToLeftButton, true);
        setButtonDisable(rotateToRightButton, true);
        setButtonDisable(placeRugButton, true);
        setButtonDisable(rotateAssamToLeftButton, false);
        setButtonDisable(rotateAssamToRightButton, false);
        setButtonDisable(rollDiceButton, false);

        //All buttons
        payDirhamsButton.setOnAction(event -> {
            try {
                handleDirhamPayment();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        rotateAssamToLeftButton.setOnAction(event -> handleAssamRotation(true));
        rotateAssamToRightButton.setOnAction(event -> handleAssamRotation(false));
        rollDiceButton.setOnAction(event -> handleAssamMovement());
        rotateToLeftButton.setOnAction(event -> handleRugRotation(true));
        rotateToRightButton.setOnAction(event -> handleRugRotation(false));
        moveRugUp.setOnAction(event -> handleRugMovement("up"));
        moveRugDown.setOnAction(event -> handleRugMovement("down"));
        moveRugLeft.setOnAction(event -> handleRugMovement("left"));
        moveRugRight.setOnAction(event -> handleRugMovement("right"));
        placeRugButton.setOnAction(event -> {
            try {
                handleRugPlacement();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        checkWinner(stage);
    }

    /** @Authority: Gennie Nguyen
     * create a banner for Winner
     * @param winnerName
     * @param winnerChar
     * @param playerScore
     * @return
     */
    private VBox createWinnerBanner (String winnerName, Character winnerChar, String playerScore) {
        VBox winnerDisplay = new VBox(20);

        StackPane winnerBanner = new StackPane();

        Text winnerNameDisplayed = new Text(winnerName);
        winnerNameDisplayed.setFill(Color.WHITE);
        Font font64 = Font.loadFont("file:assets/JockeyOne-Regular.ttf", 64);
        winnerNameDisplayed.setFont(font64);

        Rectangle background = new Rectangle(474, 90);
        String bannerBackgroundColor = "";
        String bannerBorderColor = "";
        String shadowColor = "";

        switch (winnerChar) {
            case 'c':
                bannerBackgroundColor = "#1F8C86";
                bannerBorderColor = "#00FFF1";
                shadowColor = "#19706B";
                break;
            case 'y':
                bannerBackgroundColor = "#FFA800";
                bannerBorderColor = "#FFE588";
                shadowColor = "#CC8600";
                break;
            case 'r':
                bannerBackgroundColor = "#E93119";
                bannerBorderColor = "#FF6551";
                shadowColor = "#BA2714";
                break;
            case 'p':
                bannerBackgroundColor = "#894FA5";
                bannerBorderColor = "#E09DFF";
                shadowColor = "#6E3F84";
                break;
            default:
                bannerBackgroundColor = "#064B72";
                bannerBorderColor = "#0091E3";
                shadowColor = "#053C5B";
        }

        background.setArcHeight(10);
        background.setArcWidth(10);
        background.setStroke(Color.web(bannerBorderColor));
        background.setStrokeWidth(5);
        background.setEffect(createDropShadowEffect(shadowColor));
        background.setFill(Color.web(bannerBackgroundColor));

        StackPane mainContent = new StackPane(background, winnerNameDisplayed);
        mainContent.setPrefSize(480, 120);

        SVGPath svg1 = new SVGPath();
        svg1.setContent("M6.92401 15.1756C2.37225 10.4018 5.756 2.5 12.352 2.5H74.5C78.6421 2.5 82 5.85787 82 10V80C82 84.1421 78.6421 87.5 74.5 87.5H10.9936C4.55523 87.5 1.10945 79.9215 5.34182 75.0697L25.8881 51.5167C30.1342 46.6492 29.9725 39.3484 25.5152 34.6736L6.92401 15.1756Z");
        svg1.setFill(Color.web(bannerBackgroundColor));
        svg1.setStroke(Color.web(bannerBorderColor));
        svg1.setStrokeWidth(5);
        svg1.setEffect(createDropShadowEffect(shadowColor));

        SVGPath svg2 = new SVGPath();
        svg2.setContent("M77.576 15.1756C82.1278 10.4018 78.744 2.5 72.148 2.5H10C5.85787 2.5 2.5 5.85787 2.5 10V80C2.5 84.1421 5.85786 87.5 10 87.5H73.5064C79.9448 87.5 83.3906 79.9215 79.1582 75.0697L58.6119 51.5167C54.3658 46.6492 54.5275 39.3484 58.9848 34.6736L77.576 15.1756Z");
        svg2.setFill(Color.web(bannerBackgroundColor));
        svg2.setStroke(Color.web(bannerBorderColor));
        svg2.setStrokeWidth(5);
        svg2.setEffect(createDropShadowEffect(shadowColor));

        HBox bannerSides = new HBox(svg1, createASpacerForLayoutHBox(), svg2);
        bannerSides.setMaxSize(590, 120);

        String imageUrl = "file:assets/winnerTrophy.png";
        Pane trophy1 = new Pane();
        trophy1.setPrefSize(80, 100);
        trophy1.setStyle("-fx-background-image: url('" + imageUrl + "'); -fx-background-position: center center; -fx-background-repeat: stretch;");
        Pane trophy2 = new Pane();
        trophy2.setStyle("-fx-background-image: url('" + imageUrl + "'); -fx-background-position: center center; -fx-background-repeat: stretch;");
        trophy2.setPrefSize(80, 100);

        HBox trophies = new HBox(trophy1, createASpacerForLayoutHBox(), trophy2);
        trophies.setMaxSize(510, 120);

        winnerBanner.setPrefSize(590, 120);
        winnerBanner.getChildren().addAll(bannerSides, mainContent, trophies);
        trophies.setAlignment(Pos.TOP_CENTER);
        mainContent.setAlignment(Pos.CENTER);
        bannerSides.setAlignment(Pos.BOTTOM_CENTER);

        Label winnerScore = new Label("SCORE: " + playerScore);
        winnerScore.setTextFill(Color.BLACK);
        winnerScore.setFont(font32);

        winnerDisplay.getChildren().addAll(winnerBanner, winnerScore);
        winnerDisplay.setAlignment(Pos.CENTER);

        return winnerDisplay;
    }
    private void checkWinner(Stage stage) {
        if (Marrakech.isGameOver(gameString[0])) {
            char winnerChar = Marrakech.getWinner(gameString[0]);
            String winner = "";
            switch (winnerChar) {
                case 'c':
                    winner = player1Name;
                    break;
                case 'y':
                    winner = player2Name;
                    break;
                case 'r':
                    winner = player3Name;
                    break;
                case 'p':
                    winner = player4Name;
                    break;
                case 't':
                    winner = "It's A Draw!"; // draw case
            }

            List<String> rankings = getRankingsScore();


            BorderPane root = new BorderPane();
            root.setStyle("-fx-background-color: white;");


            Font font48 = Font.loadFont("file:assets/JockeyOne-Regular.ttf", 48);
            Label congratsText2 = new Label();
            Text labelText = new Text(winner);
            labelText.setStroke(lightYellow);
            labelText.setStrokeWidth(12.0);
            congratsText2.setGraphic(labelText);
            congratsText2.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            labelText.setFont(font48);
            labelText.setFill(red);
            if (winnerChar == 't') {
                congratsText1 = new Label(winner);
            }
            else {
                congratsText1 = new Label("CONGRATULATIONS!");
            }
            congratsText1.setFont(font48);
            congratsText1.setTextFill(red);
            StackPane headerText = new StackPane(congratsText2, congratsText1);


            //Winner Banner
            VBox winnerBanner = createWinnerBanner(winner, winnerChar, rankings.get(0).substring(1, 3));

            // Content
            VBox mainContent = new VBox(20);
            mainContent.getChildren().addAll(headerText, winnerBanner);
            mainContent.setAlignment(Pos.CENTER);

            for (int i = 1; i < numOfPlayers; i++) {
                String playerName = "";
                String playerChar = rankings.get(i).substring(0, 1);
                switch (playerChar) {
                    case "c":
                        playerName = player1Name;
                        break;
                    case "y":
                        playerName = player2Name;
                        break;
                    case "r":
                        playerName = player3Name;
                        break;
                    case "p":
                        playerName = player4Name;
                        break;
                }
                Text rankText = new Text((i + 1) + ". " + playerName +  " - " + rankings.get(i).substring(1,3));
                Font font24 = Font.loadFont("file:assets/JockeyOne-Regular.ttf", 24);
                rankText.setFont(font24);
                mainContent.getChildren().add(rankText);
            }

            root.setCenter(mainContent);
            String imageUrl = "file:assets/winnerBackground.png";
            root.setStyle("-fx-background-image: url('" + imageUrl + "'); -fx-background-position: center center; -fx-background-repeat: stretch;");

            Scene scene = new Scene(root, 1200, 700);
            stage.setTitle("Congratulations!");
            stage.setScene(scene);
            stage.show();
        }
    }
    private void updateGameStringPlayerString(String playerA, String playerB, String originalPlayerB) {
        Player playerAColor = new Player(playerA);
        char colorA = playerAColor.getColor();

        switch (colorA) {
            case 'c':
                gameString[0] = playerString[0] + gameString[0].substring(8);
                break;
            case 'y':
                gameString[0] = gameString[0].substring(0, 8) + playerString[0] + gameString[0].substring(16);
                break;
            case 'r':
                gameString[0] = gameString[0].substring(0, 16) + playerString[0] + gameString[0].substring(24);
                break;
            case 'p':
                gameString[0] = gameString[0].substring(0, 24) + playerString[0] + gameString[0].substring(32);
                break;
        }
        Player playerBColor = new Player(originalPlayerB);
        char colorB = playerBColor.getColor();

        switch (colorB) {
            case 'c':
                gameString[0] = playerB + gameString[0].substring(8);
                break;
            case 'y':
                gameString[0] = gameString[0].substring(0, 8) + playerB + gameString[0].substring(16);
                break;
            case 'r':
                gameString[0] = gameString[0].substring(0, 16) + playerB + gameString[0].substring(24);
                break;
            case 'p':
                gameString[0] = gameString[0].substring(0, 24) + playerB+ gameString[0].substring(32);
                break;
        }
    }
    private List<String> getRankingsScore() {
        String player1 = gameString[0].substring(0, 8);
        String player2 = gameString[0].substring(8, 16);
        String player3 = gameString[0].substring(16, 24);
        String player4 = gameString[0].substring(24, 32);
        String[] players = new String[4];
        players[0] = player1;
        players[1] = player2;
        players[2] = player3;
        players[3] = player4;
        String boardString = gameString[0].substring(32, 184);

        // Initialize variables to keep track of player scores
        int cyanScore = 0;
        int yellowScore = 0;
        int redScore = 0;
        int purpleScore = 0;

        // Calculate player scores based on their dirhams
        // Player strings are in the format: P<color><dirhams><remaining rugs>i
        for (int i = 0; i < 4; i++) {
            String playerString = players[i];
            char color = playerString.charAt(1);
            int dirhams = Integer.parseInt(playerString.substring(2, 5));

            switch (color) {
                case 'c':
                    cyanScore = dirhams;
                    break;
                case 'y':
                    yellowScore = dirhams;
                    break;
                case 'r':
                    redScore = dirhams;
                    break;
                case 'p':
                    purpleScore = dirhams;
                    break;
            }
        }

        // Calculate player scores based on the board information
        // Board string is in the format: "n00<board_data>"
        // Each character in <board_data> represents a square on the board
        for (char square : boardString.toCharArray()) {
            switch (square) {
                case 'c':
                    cyanScore++;
                    break;
                case 'y':
                    yellowScore++;
                    break;
                case 'r':
                    redScore++;
                    break;
                case 'p':
                    purpleScore++;
                    break;
            }
        }
        int[] scores = {cyanScore, yellowScore, redScore, purpleScore};
        String[] playerColors = {"c", "y", "r", "p"};

        for (int i = 0; i < 4; i++) {
            for (int j = i + 1; j < 4; j++) {
                if (scores[i] < scores[j]) {
                    // Swap scores
                    int tempScore = scores[i];
                    scores[i] = scores[j];
                    scores[j] = tempScore;

                    // Swap players
                    String tempColor = playerColors[i];
                    playerColors[i] = playerColors[j];
                    playerColors[j] = tempColor;
                }
            }
        }
        List<String> rankedPlayers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            rankedPlayers.add(playerColors[i] + scores[i]);
        }
        return rankedPlayers;
}
}

