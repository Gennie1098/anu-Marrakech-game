package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EndGame extends Application {

    private static Color darkBlue = Color.web("#064B72");
    private static Color yellow = Color.web("#FFA800");
    private static Color green = Color.web("#1F8C86");
    private static Color red = Color.web("#E93119");
    private static Color purple = Color.web("#894FA5");
    private static Color lightYellow = Color.web("#FFE6A9");
    private static Font font32 = Font.loadFont("file:assets/JockeyOne-Regular.ttf", 32);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        String imageUrl = "file:assets/winnerBackground.png";
        root.setStyle("-fx-background-image: url('" + imageUrl + "'); -fx-background-position: center center; -fx-background-repeat: stretch;");

        VBox mainContent = new VBox(20);

        //Header "CONGRATULATIONS!"
        Font font48 = Font.loadFont("file:assets/JockeyOne-Regular.ttf", 48);
        Label congratsText2 = new Label();
        Text labelText = new Text("CONGRATULATIONS!");
        labelText.setStroke(lightYellow);
        labelText.setStrokeWidth(12.0);
        congratsText2.setGraphic(labelText);
        congratsText2.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        labelText.setFont(font48);
        labelText.setFill(red);
        Label congratsText1 = new Label("CONGRATULATIONS!");
        congratsText1.setFont(font48);
        congratsText1.setTextFill(red);
        StackPane headerText = new StackPane(congratsText2, congratsText1);

        //Winner banner
        VBox winnerBanner = createWinnerBanner("Gennie", "Pp12000i", "200");


        mainContent.getChildren().addAll(headerText, winnerBanner);
        mainContent.setAlignment(Pos.CENTER);



        root.setCenter(mainContent);

        Scene scene = new Scene(root, 1200, 700);
        primaryStage.setTitle("Congratulations Screen");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private DropShadow createDropShadowEffect(String shadowColor) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetY(7.0);
        dropShadow.setColor(Color.web(shadowColor));
        dropShadow.setRadius(0.0);
        return dropShadow;
    }
    private Region createASpacerForLayoutHBox () {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    private VBox createWinnerBanner (String winnerName, String playerString, String playerScore) {
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


        Character bannerColor = playerString.charAt(1);
        switch (bannerColor) {
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

    private VBox showOtherPlayerResults () {
        VBox otherPlayerResults = new VBox(10);

//        for (int i = 0; i < numberOfPlayer; i ++) {
//
//        }

        return otherPlayerResults;
    }
}







