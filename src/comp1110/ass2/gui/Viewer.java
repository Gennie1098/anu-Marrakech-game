package comp1110.ass2.gui;

import comp1110.ass2.Assam;
import comp1110.ass2.Board;
import comp1110.ass2.Player;
import javafx.scene.shape.Polygon;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Viewer extends Application {

    private static final int VIEWER_WIDTH = 1200;
    private static final int VIEWER_HEIGHT = 700;
    private final Group root = new Group();
    private final Group controls = new Group();
    private final Group gameLayout = new Group();
    private TextField boardTextField;

    //Checks the length of the game string to find out how many players are in the game
    public static int checkLength(String state) {
        int length = state.length();
        if (length == 168) {
            return 2;
        }
        else if (length == 176) {
            return 3;
        }
        else if (length == 184) {
            return 4;
        }
        else {
            return 0;
        }
    }

    //Creates the board, populates it with the required colours for each rug.
    public static GridPane createBoard(List<Character> colorList) {

        GridPane boardPane = new GridPane();
        int numRows = Board.BOARD_SIZE;
        int numCols = Board.BOARD_SIZE;
        double cellSize = 64;

        //pane.setPadding(new Insets(50));

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                char color = colorList.get(0);
                colorList.remove(0);
                Rectangle r = new Rectangle(cellSize, cellSize);

                switch (color) {
                    case 'r' -> r.setFill(Color.ORANGERED);
                    case 'p' -> r.setFill(Color.MEDIUMPURPLE);
                    case 'c' -> r.setFill(Color.CYAN);
                    case 'y' -> r.setFill(Color.YELLOW);
                    default -> r.setFill(Color.TRANSPARENT);
                }
                r.setStroke(Color.BLACK);
                boardPane.add(r, j, i);
            }
        }

        boardPane.setAlignment(Pos.CENTER);

        return boardPane;
    }

    //Creates the assam figure. Was meant to be an image but couldn't figure out how to make it work :(
    public static Pane assamIcon() {
        Pane assamIcon = new Pane();

        Circle head = new Circle(20, 20, 10);
        head.setFill(Color.BLACK);

        Line body = new Line(20, 30, 20, 50);
        body.setStroke(Color.BLACK);

        Line leftArm = new Line(15, 30, 20, 40);
        leftArm.setStroke(Color.BLACK);
        Line rightArm = new Line(25, 30, 20, 40);
        rightArm.setStroke(Color.BLACK);

        Line leftLeg = new Line(20, 50, 15, 60);
        leftLeg.setStroke(Color.BLACK);
        Line rightLeg = new Line(20, 50, 25, 60);
        rightLeg.setStroke(Color.BLACK);

        assamIcon.getChildren().addAll(head, body, leftArm, rightArm, leftLeg, rightLeg);

        return assamIcon;
    }
    //Creates a triangle pointing in the direction Assam is facing
    public static Pane assamDirection(char orientation) {
        if (orientation == 'N') {
            return createTriangle(20, 10, Color.GREEN, orientation);
        } else if (orientation == 'S') {
            return createTriangle(20, 30, Color.GREEN, orientation);
        } else if (orientation == 'E') {
            return createTriangle(30, 20, Color.GREEN, orientation);
        } else if (orientation == 'W') {
            return createTriangle(10, 20, Color.GREEN, orientation);
        }
        return null;
    }

    //Creation method for the triangle.
    private static Pane createTriangle(double x, double y, Color color, char orientation){
        Pane trianglePane = new Pane();

        Polygon triangle = new Polygon();

        if (orientation == 'N') {
            triangle.getPoints().addAll(
                    x, y - 10,
                    x - 10, y + 10,
                    x + 10, y + 10
            );
        } else if (orientation == 'S') {
            triangle.getPoints().addAll(
                    x, y + 10,
                    x - 10, y - 10,
                    x + 10, y - 10
            );
        } else if (orientation == 'E') {
            triangle.getPoints().addAll(
                    x + 10, y,
                    x - 10, y - 10,
                    x - 10, y + 10
            );
        } else if (orientation == 'W') {
            triangle.getPoints().addAll(
                    x - 10, y,
                    x + 10, y - 10,
                    x + 10, y + 10
            );
        }

        triangle.setFill(color);

        trianglePane.getChildren().add(triangle);

        return trianglePane;
    }

    // Creates a display rectangle that shows all info
    public static GridPane playerInfo(char color, int numberOfDirhams, int numberOfRugs, char inGame) {
        GridPane playerPane = new GridPane();
        playerPane.setPadding(new Insets(20));
        playerPane.setVgap(5);

        int playerNumber = 0;
        if (color == 'c'){
            playerNumber = 1;
        }
        else if (color == ('y')){
            playerNumber = 2;
        }
        else if (color == 'r'){
            playerNumber = 3;
        }
        else if (color == 'p'){
            playerNumber = 4;
        }

        Rectangle playerRectangle = createPlayerRectangle(playerNumber);

        Text playerName = new Text("Player " + playerNumber);
        playerName.setStyle("-fx-font-size: 16px;");
        playerName.setLayoutX(10);
        playerName.setLayoutY(30);

        Text playerRugs = new Text("Rugs Left: " + numberOfRugs);
        playerRugs.setStyle("-fx-font-size: 10px");
        playerRugs.setLayoutX(90);
        playerRugs.setLayoutY(28);


        Text playerDirhams = new Text("Dirhams Left: " + numberOfDirhams);
        playerDirhams.setStyle("-fx-font-size: 10px");
        playerDirhams.setLayoutX(150);
        playerDirhams.setLayoutY(28);

        String status = (inGame == 'i') ? "" : "Player is out!";
        Text playerInGame = new Text(status);
        playerInGame.setStyle("-fx-font-size: 10px");
        playerInGame.setLayoutX(260);
        playerInGame.setLayoutY(28);


        Group playerText = new Group(playerName, playerRugs, playerDirhams, playerInGame);

        Group playerEverything = new Group(playerRectangle, playerText);

        GridPane.setRowIndex(playerEverything, playerNumber);
        playerPane.getChildren().add(playerEverything);
        return playerPane;
    }



    // Creation method for the rectangle, sets the colour and size of the rectangle.
    private static Rectangle createPlayerRectangle(int numPlayer) {
        int currentColor = 0;
        List<Color> playerColors = Arrays.asList(Color.CYAN, Color.YELLOW, Color.ORANGERED, Color.MEDIUMPURPLE);
        Color playerColor = playerColors.get(numPlayer - 1);
        Rectangle rectangle = new Rectangle(350, 50);

        rectangle.setFill(playerColor);

        return rectangle;
    }



    /**
     * Draw a placement in the window, removing any previously drawn placements
     *
     * @param state an array of two strings, representing the current game state
     */
    void displayState(String state) {
        // FIXME Task 5: implement the simple state viewer

        //clears the game layout to prevent overlapping
        gameLayout.getChildren().clear();

        GridPane players = new GridPane();
        List<GridPane> playerGridPanes = new ArrayList<>();

        //counts the number of players
        int numPlayers = checkLength(state);

        //Cycles through to grab the next player string and record all the relevant information
        for (int i = 0; i <= (numPlayers - 1); i++) {
            String playerString = state.substring(0 + (i * 8), 8 + (i * 8));
            Player player = new Player(playerString);
            char color = player.getColor();
            int numberOfDirhams = player.getNumberOfDirhams();
            int numberOfRugs = player.getNumberOfRugs();
            char inGame = playerString.charAt(7);

            GridPane playerInfo = playerInfo(color, numberOfDirhams, numberOfRugs, inGame );
            playerGridPanes.add(playerInfo);
        }
        for (int i = 0; i < playerGridPanes.size(); i++) {
            GridPane playerInfo = playerGridPanes.get(i);
            GridPane.setRowIndex(playerInfo, i);
            players.getChildren().add(playerInfo);
        }

        //Puts the player tiles into the correct spot.
        players.setLayoutX(700);
        players.setLayoutY(50);


        //stackpane for the board, to allow the icons to go within the gridpane
        StackPane allBoard = new StackPane();


        //list for the colors of the rugs, to be cycled through
        List<Character> colorList = new ArrayList<>();

        for (int i = 0; i < 49; i++){
            String rugString = state.substring(8 * numPlayers + 5 + 3 * i, ((8 * numPlayers) + 8 + 3 * i));
            //collects the color of each rug from the substring
            char color = rugString.charAt(0);
            colorList.add(color);
        }

        //Creates the board with the rugs placed in
        GridPane board = createBoard(colorList);


        Pane assamIcon = assamIcon();
        //finds the assam string
        String assamString = state.substring((8 * numPlayers), ((8 * numPlayers) + 4));
        Assam Assam = new Assam(assamString);
        //finds the position of assam on the board
        int assamY = Assam.getY();
        int assamX = Assam.getX();

        //sets the position of assam on the board
        GridPane.setConstraints(assamIcon, assamY - 1, assamX - 1);

        //figures out the direction assam is facing
        char assamOrientation = Assam.getOrientation();
        Pane assamDirection = assamDirection(assamOrientation);

        int directionX = assamX - 1;
        int directionY = assamY - 1;
        // if statements to place the direction triangle on the right square.
        if (assamOrientation == 'N'){
            directionX--;
        }
        if (assamOrientation == 'S'){
            directionX++;
        }
        if (assamOrientation == 'E'){
            directionY++;
        }
        if (assamOrientation == 'W'){
            directionY--;
        }

        //places the triangle on the board
        GridPane.setConstraints(assamDirection, directionY, directionX);

        //populates the board with the assam direction triangle and the figure
        board.getChildren().addAll(assamDirection, assamIcon);

        //puts everything together and puts it in a reasonable spot
        allBoard.getChildren().addAll(board);
        allBoard.setLayoutX(130);
        allBoard.setLayoutY(30);

        gameLayout.getChildren().addAll(allBoard, players);
    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label boardLabel = new Label("Game State:");
        boardTextField = new TextField();
        boardTextField.setPrefWidth(800);
        Button button = new Button("Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                displayState(boardTextField.getText());
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(boardLabel,
                boardTextField, button);
        hb.setSpacing(10);
        hb.setLayoutX(50);
        hb.setLayoutY(VIEWER_HEIGHT - 100);
        controls.getChildren().add(hb);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //contains default game string, no players, nothing except assam on the board.
        displayState("A44Ny00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00\n");

        root.getChildren().add(controls);

        makeControls();

        primaryStage.setTitle("Marrakech Board");

        root.getChildren().add(gameLayout);

        Scene scene = new Scene(root);

        primaryStage.setWidth(VIEWER_WIDTH);
        primaryStage.setHeight(VIEWER_HEIGHT);

        //Disables window resizing
        //primaryStage.setMinWidth(VIEWER_WIDTH);
        //primaryStage.setMaxWidth(VIEWER_WIDTH);
        //primaryStage.setMinHeight(VIEWER_HEIGHT);
        //primaryStage.setMaxHeight(VIEWER_HEIGHT);

        primaryStage.setScene(scene);

        primaryStage.show();
    }
}

/** Test game strings (not properly generated, can end up with weird values)
 * Pc00913iPy00714oA53NBn00n00y08n00n00n00n00r97n00n00n00n00y44n00r01n00n00r78r22n00n00n00c03y86c04r12n00n00n00n00n00n00y66r08c76r24n00n00n00y22y00r20n00n00n00y82r74n00n00
 *Pc01313oPy00915iPr01409iA24WBn00n00n00y87n00n00n00n00r53c69c79c80n00n00r88c82y18c75n00r63n00c37c94y40y23n00n00n00n00n00y79n00y72n00c86r34n00n00c18r47c59r07r92y65n00n00c24n00r53
 *Pc01510iPy01103iPr00411oA36EBy86n00r83n00y62r18y75y63c26n00r71n00n00c24y98y36r57r22n00n00n00n00c82r09c65n00n00y72r98n00n00n00y80r25n00r86n00n00c83y96n00c26n00c71n00n00r44n00c53
 */
