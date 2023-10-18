package comp1110.ass2.gui;

import javafx.application.Application;
import comp1110.ass2.gui.Game;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StartGame1 extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 创建一个垂直的布局容器
        VBox firstPane = new VBox(20);
        firstPane.setStyle("-fx-background-color: #FAEBCD;");

        // "WELCOME TO" 文字
        Text welcomeText = new Text("WELCOME TO");
        welcomeText.setFont(Font.font("Arial", 20));

        // "MARRAKECK" 文字
        HBox gameTitleBox1 = new HBox(0); // 创建一个水平布局容器，间距为0
        String[] letters1 = {"M", "A", "R", "R", "A", "K", "E", "C", "K"};
        String[] colors1 = {"#0099CC", "#FFCC00", "#33CC33", "#FF6600", "#9933FF", "#00CCFF", "#FF3399", "#00CC99", "#FF0033"};

        for (int i = 0; i < letters1.length; i++) {
            Text letter = new Text(letters1[i]);
            letter.setFill(Color.web(colors1[i]));
            letter.setFont(Font.font("Arial", 40));
            gameTitleBox1.getChildren().add(letter);
        }

        // "NEW GAME" 按钮
        Button newGameBtn = new Button("NEW GAME");
        newGameBtn.setFont(Font.font("Arial", 20));

        // 设置布局和位置
        firstPane.setAlignment(Pos.CENTER);
        gameTitleBox1.setAlignment(Pos.CENTER);
        firstPane.getChildren().addAll(welcomeText, gameTitleBox1, newGameBtn);

        Scene scene1 = new Scene(firstPane, 700, 500);
        primaryStage.setTitle("Marrakeck Game");
        primaryStage.setScene(scene1);
        primaryStage.show();

        // 设置按钮的文字
        newGameBtn.setText("NEW GAME >");

// 使用CSS来样式化按钮
        newGameBtn.setStyle("-fx-background-color: #0099CC; " +       // 背景颜色
                "-fx-text-fill: white; " +                // 文字颜色
                "-fx-border-color: #006699; " +           // 边框颜色
                "-fx-border-width: 3px; " +               // 边框宽度
                "-fx-font-family: 'Arial'; " +            // 字体
                "-fx-font-size: 20px; " +                 // 字体大小
                "-fx-padding: 10px 20px;");               // 内部间距






        // 第二个界面
        VBox secondPane = new VBox(50);
        secondPane.setAlignment(Pos.CENTER);
        secondPane.setStyle("-fx-background-color: #FAEBCD;");

        // "MARRAKECK" 文字
        HBox gameTitle2 = new HBox(0); // 创建一个水平布局容器，间距为0
        String[] letters2 = {"M", "A", "R", "R", "A", "K", "E", "C", "K"};
        String[] colors2 = {"#0099CC", "#FFCC00", "#33CC33", "#FF6600", "#9933FF", "#00CCFF", "#FF3399", "#00CC99", "#FF0033"};

        for (int i = 0; i < letters2.length; i++) {
            Text letter = new Text(letters2[i]);
            letter.setFill(Color.web(colors2[i]));
            letter.setFont(Font.font("Arial", 70));
            gameTitle2.getChildren().add(letter);
        }
        gameTitle2.setAlignment(Pos.CENTER);

        Text question = new Text("HOW MANY PLAYERS YOU HAVE?");
        question.setFont(Font.font("Arial", 30));

        TextField playerInput = new TextField();
        playerInput.setEditable(true); // 设置输入框能否编辑
        playerInput.setPromptText("ENTER A NUMBER FROM 2 TO 4");
        playerInput.setPrefWidth(300); // 设置输入框的推荐宽
        playerInput.setPrefHeight(60); //设置输入框的推荐高

        Button nextButton = new Button("NEXT");
        nextButton.setFont(Font.font("Arial", 20));

        secondPane.getChildren().addAll(gameTitle2, question, playerInput, nextButton);

        Scene scene2 = new Scene(secondPane, 700, 500);
        nextButton.setStyle("-fx-background-color: #0099CC; " +       // 背景颜色
                "-fx-text-fill: white; " +                // 文字颜色
                "-fx-border-color: #006699; " +           // 边框颜色
                "-fx-border-width: 3px; " +               // 边框宽度
                "-fx-font-family: 'Arial'; " +            // 字体
                "-fx-font-size: 20px; " +                 // 字体大小
                "-fx-padding: 10px 20px;");               // 内部间距

        // 为开始按钮设置跳转到第二个界面的操作
        newGameBtn.setOnAction(e -> {
            primaryStage.setScene(scene2);
        });




        // 第三个界面

        VBox thirdPane = new VBox(30);
        thirdPane.setAlignment(Pos.CENTER);
        thirdPane.setStyle("-fx-background-color: #FAEBCD;");

        // "MARRAKECK" 文字
        HBox gameTitle3 = new HBox(0); // 创建一个水平布局容器，间距为0
        String[] letters3 = {"M", "A", "R", "R", "A", "K", "E", "C", "K"};
        String[] colors3 = {"#0099CC", "#FFCC00", "#33CC33", "#FF6600", "#9933FF", "#00CCFF", "#FF3399", "#00CC99", "#FF0033"};

        for (int i = 0; i < letters3.length; i++) {
            Text letter = new Text(letters3[i]);
            letter.setFill(Color.web(colors3[i]));
            letter.setFont(Font.font("Arial", 60));
            gameTitle3.getChildren().add(letter);
        }
        gameTitle3.setAlignment(Pos.CENTER);

        Text Name = new Text("PLAYERS　NAME");
        Name.setFont(Font.font("Arial", 30));



        TextField player1Input = new TextField();
        player1Input.setEditable(true); // 设置输入框能否编辑
        player1Input.setPromptText("PLAYER 1");
        player1Input.setPrefWidth(300); // 设置输入框的推荐宽
        player1Input.setPrefHeight(60); //设置输入框的推荐高


        TextField player2Input = new TextField();
        player2Input.setEditable(true); // 设置输入框能否编辑
        player2Input.setPromptText("PLAYER 2");
        player2Input.setPrefWidth(300); // 设置输入框的推荐宽
        player2Input.setPrefHeight(60); //设置输入框的推荐高

        TextField player3Input = new TextField();
        player3Input.setEditable(true); // 设置输入框能否编辑
        player3Input.setPromptText("PLAYER 3");
        player3Input.setPrefWidth(300); // 设置输入框的推荐宽
        player3Input.setPrefHeight(60); //设置输入框的推荐高



        TextField player4Input = new TextField();
        player4Input.setEditable(true); // 设置输入框能否编辑
        player4Input.setPromptText("PLAYER 4");
        player4Input.setPrefWidth(300); // 设置输入框的推荐宽
        player4Input.setPrefHeight(60); //设置输入框的推荐高


        Button StartGameButton = new Button("Start Game");
        StartGameButton.setFont(Font.font("Arial", 30));
        thirdPane.getChildren().addAll(gameTitle3,Name,player1Input,player2Input,player3Input,player4Input,StartGameButton);

        Scene scene3 = new Scene(thirdPane, 700, 500);
        StartGameButton.setStyle("-fx-background-color: #0099CC; " +       // 背景颜色
                "-fx-text-fill: white; " +                // 文字颜色
                "-fx-border-color: #006699; " +           // 边框颜色
                "-fx-border-width: 3px; " +               // 边框宽度
                "-fx-font-family: 'Arial'; " +            // 字体
                "-fx-font-size: 20px; " +                 // 字体大小
                "-fx-padding: 10px 20px;");               // 内部间距


        nextButton.setOnAction(e -> {
            primaryStage.setScene(scene3);
        });


//        StartGameButton.setOnAction(e ->{
//
//            Game scene = new Game();
//            Scene secondScene = Game.start();
//            primaryStage.setScene(secondScene);
//
//        });


    }





    public static void main(String[] args) {
        launch(args);
    }
}





