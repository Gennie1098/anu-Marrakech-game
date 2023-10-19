package comp1110.ass2.gui;

import javafx.application.Application;
import comp1110.ass2.gui.Game;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StartGame1 extends Application {
    private static Font font32 = Font.loadFont("file:assets/JockeyOne-Regular.ttf", 32);

    private static Color darkBlue = Color.web("#064B72");
    private static Color yellow = Color.web("#FFA800");
    private static Color green = Color.web("#1F8C86");
    private static Color red = Color.web("#E93119");
    private static Color purple = Color.web("#894FA5");
    private static Color lightYellow = Color.web("#FFE6A9");

    @Override
    public void start(Stage primaryStage) {

        // 创建一个垂直的布局容器
        VBox welcomeScene = new VBox(30);
        welcomeScene.setStyle("-fx-background-color: #FAEBCD;");

        // "WELCOME TO" 文字
        Text welcomeText = new Text("WELCOME TO");
        welcomeText.setFont(font32);

        StackPane bigGameTitle = creatMarrakeckTitle();

        // "NEW GAME" 按钮
        Button newGameBtn = new Button("NEW GAME");
        newGameBtn.setFont(Font.font("Arial", 20));

        // 设置布局和位置
        welcomeScene.setAlignment(Pos.CENTER);
        welcomeScene.getChildren().addAll(welcomeText, bigGameTitle, newGameBtn);

        Scene scene1 = new Scene(welcomeScene, 1200, 700);
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
        StackPane gameTitle1 = creatMarrakeckTitle();
        gameTitle1.setScaleX(0.5);
        gameTitle1.setScaleY(0.5);

        Text question = new Text("HOW MANY PLAYERS YOU HAVE?");
        question.setFont(Font.font("Arial", 30));

        TextField playerInput = new TextField();
        playerInput.setEditable(true); // 设置输入框能否编辑
        playerInput.setPromptText("ENTER A NUMBER FROM 2 TO 4");
        playerInput.setPrefWidth(300); // 设置输入框的推荐宽
        playerInput.setPrefHeight(60); //设置输入框的推荐高

        Button nextButton = new Button("NEXT");
        nextButton.setFont(Font.font("Arial", 20));

        secondPane.getChildren().addAll(gameTitle1, question, playerInput, nextButton);

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
        StackPane gameTitle2 = creatMarrakeckTitle();
        gameTitle2.setScaleX(0.5);
        gameTitle2.setScaleY(0.5);

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
        thirdPane.getChildren().addAll(gameTitle2,Name,player1Input,player2Input,player3Input,player4Input,StartGameButton);

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




    public static void main(String[] args) {
        launch(args);
    }
}





