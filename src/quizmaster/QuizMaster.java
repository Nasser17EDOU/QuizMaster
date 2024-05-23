/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quizmaster;

/*import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;*/
//import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
//import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
//import javafx.scene.control.RadioButton;
//import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
//import sun.audio.AudioPlayer;
//import sun.audio.AudioStream;

/**
 *
 * @author Tony Ndoss
 */
public class QuizMaster extends Application {
    
    ResultSet resultObj = null;
    public static Pane bigRoot = new Pane();
    public static int scoreValue = 0;
    public static boolean isGreater;
    public static ArrayList<String> questionsAnswered = new ArrayList<String>();
    
    @Override
    
    public void start(Stage stage){
        
        stage.getIcons().add(new Image(getClass().getResourceAsStream("images/Icon.png")));
        stage.setTitle("Quiz Master");
        stage.setResizable(false);
        stage.centerOnScreen(); //end of stage parameters
        
        
        Label quiz = new Label("uiz");
        quiz.setId("quiz");
        quiz.setLayoutX(540);
        quiz.setLayoutY(425);
        
        Label master = new Label("aster");
        master.setId("master");
        master.setLayoutX(727);
        master.setLayoutY(390);
        
        Pane root = new Pane();
        root.setId("pane");
        root.setPrefSize(972, 780);
        root.setOpacity(0);
        
        root.getChildren().addAll(quiz, master);
        Timeline bootingAnim = new Timeline();
        KeyValue bootingAnimkv1 = new KeyValue(root.opacityProperty(), 0, Interpolator.EASE_IN);
        KeyFrame bootingAnimkf1 = new KeyFrame(Duration.seconds(1.5), bootingAnimkv1);
        KeyValue bootingAnimkv2 = new KeyValue(root.opacityProperty(), 1, Interpolator.EASE_OUT);
        KeyFrame bootingAnimkf2 = new KeyFrame(Duration.seconds(3.5), bootingAnimkv2);
        bootingAnim.getKeyFrames().addAll(bootingAnimkf1, bootingAnimkf2);
        bootingAnim.setOnFinished((ActionEvent event) -> {
        /*    
        try {
            AudioPlayer.player.start(audio());
        } catch (IOException ex) {
            Logger.getLogger(QuizMaster.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        });
        bootingAnim.play();
        //PauseTransition delay = new PauseTransition(Duration.seconds(5));
        Timeline menuSceneTransiDelay = new Timeline();
        //line2.setDelay(Duration.seconds(5));
        KeyFrame menuSceneTransiDelayKf = new KeyFrame(Duration.seconds(5));
        menuSceneTransiDelay.getKeyFrames().add(menuSceneTransiDelayKf);
        menuSceneTransiDelay.play();
        menuSceneTransiDelay.setOnFinished(event -> menuScenefunc(stage));
        
        //bigRoot.setOpacity(0.4);
        bigRoot.getChildren().add(root);
        
        Scene scene = new Scene(bigRoot, 960, 768);
        //bigRoot.getChildren().add(root);
        //System.out.println(root.getOpacity());
        
        scene.getStylesheets().add(getClass().getResource("StyleSheet.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
        
        //end of first scene descriptions
        
    }
    
    /***************************************************************************
     * *************************************************************************
     * *************************************************************************
     * @param args
     */
    
    public static void main(String[] args) {
        launch(args);
    }
    
    /***************************************************************************
     * *************************************************************************
     * *************************************************************************
     * @param primaryStage
     */
    
    public void menuScenefunc(Stage primaryStage){
        
        Button begin = new Button("Start game");
        begin.setId("begin");
        begin.setLayoutY(95);
        begin.setLayoutX(382.5);
        begin.setOpacity(0);
        begin.setOnAction((ActionEvent event) -> {
            startGameMessageFunc(primaryStage);
        });
        
        /*Button options = new Button("Options");
        options.setId("options");
        options.setLayoutY(224);
        options.setLayoutX(382.5);
        options.setOpacity(0);
        options.setOnAction((ActionEvent event) -> {
            optionFunc(primaryStage);
            //System.out.println("Hello World!");
        });*/
        
        Button instructions = new Button("Instructions");
        instructions.setId("instructions");
        instructions.setLayoutY(265);
        instructions.setLayoutX(382.5);
        instructions.setOpacity(0);
        instructions.setOnAction((ActionEvent event) -> {
            //System.out.println("Hello World!");
            instructionsFunc(primaryStage);
        });
        
        Button bestScore = new Button("Best score");
        bestScore.setId("bestScore");
        bestScore.setLayoutY(435);
        bestScore.setLayoutX(382.5);
        bestScore.setOpacity(0);
        bestScore.setOnAction((ActionEvent event) -> {
            try {
                //System.out.println("Hello World!");
                bestScoreFunc(primaryStage);
            } 
            catch (SQLException ex) {
                Logger.getLogger(QuizMaster.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        Button about = new Button("About");
        about.setId("about");
        //about.setMaxHeight(100);
        //about.setPrefHeight(40);
        about.setOpacity(1);
        about.setLayoutY(605);
        about.setLayoutX(382.5);
        about.setOpacity(0);
        about.setOnAction((ActionEvent event) -> {
            //System.out.println("Hello World!");
            aboutFunc(primaryStage);
        });
        
        Button exit = new Button("Exit");
        exit.setId("exit");
        exit.setLayoutY(737.5);
        exit.setOnAction((ActionEvent event) -> {
            primaryStage.close();
            /*try {
                AudioPlayer.player.stop(audio());
            } 
            catch (IOException ex) {
                Logger.getLogger(QuizMaster.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        });
        
        Rectangle rectangle = new Rectangle();
        rectangle.setId("rectangle");
        rectangle.setFill(Color.BLUEVIOLET);
        rectangle.setHeight(0);
        rectangle.setWidth(505);
        rectangle.setLayoutX(230);
        rectangle.setLayoutY(40);
        rectangle.setOpacity(0.8);
        rectangle.setVisible(true);
        
        Pane root1 = new Pane();
        root1.setPrefSize(972, 780);
        root1.setTranslateY(768);
        
        Pane smallRoot1 = new Pane();
        smallRoot1.setId("pane1");
        smallRoot1.setPrefSize(972, 780);
        
        smallRoot1.getChildren().addAll(rectangle, begin, instructions, bestScore, /*options,*/ about, exit);
        //System.out.println(Thread.currentThread());
        root1.getChildren().add(smallRoot1);
        Timeline root1Transi = new Timeline();
        KeyValue root1TransiKv = new KeyValue(root1.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame root1TransiKf = new KeyFrame(Duration.seconds(1), root1TransiKv);
        
        KeyValue rectangleTransiKv = new KeyValue(rectangle.heightProperty(),0);
        KeyFrame rectangleTransiKf = new KeyFrame(Duration.seconds(1), rectangleTransiKv);
        KeyValue rectangleTransiKv1 = new KeyValue(rectangle.heightProperty(), 680);
        KeyFrame rectangleTransiKf1 = new KeyFrame(Duration.seconds(2), rectangleTransiKv1);
        
        KeyValue beginKv = new KeyValue(begin.opacityProperty(), 0, Interpolator.EASE_OUT);
        //KeyValue optionsKv = new KeyValue(options.opacityProperty(), 0, Interpolator.EASE_OUT);
        KeyValue instructionsKv = new KeyValue(instructions.opacityProperty(), 0, Interpolator.EASE_OUT);
        KeyValue bestScoreKv = new KeyValue(bestScore.opacityProperty(), 0, Interpolator.EASE_OUT);
        KeyValue aboutKv = new KeyValue(about.opacityProperty(), 0, Interpolator.EASE_OUT);
        KeyFrame controlsKf = new KeyFrame(Duration.seconds(2), beginKv, /*optionsKv,*/ instructionsKv, bestScoreKv, aboutKv);
        KeyValue beginKv1 = new KeyValue(begin.opacityProperty(), 1, Interpolator.EASE_OUT);
        //KeyValue optionsKv1 = new KeyValue(options.opacityProperty(), 1, Interpolator.EASE_OUT);
        KeyValue instructionsKv1 = new KeyValue(instructions.opacityProperty(), 1, Interpolator.EASE_OUT);
        KeyValue bestScoreKv1 = new KeyValue(bestScore.opacityProperty(), 1, Interpolator.EASE_OUT);
        KeyValue aboutKv1 = new KeyValue(about.opacityProperty(), 1, Interpolator.EASE_OUT);
        KeyFrame controlsKf1 = new KeyFrame(Duration.seconds(3), beginKv1, /*optionsKv1,*/ instructionsKv1, bestScoreKv1, aboutKv1);
        
        root1Transi.getKeyFrames().addAll(root1TransiKf, rectangleTransiKf, rectangleTransiKf1, controlsKf, controlsKf1);
        root1Transi.setOnFinished(event ->  bigRoot.getChildren().remove(0));
        root1Transi.play();
        
        Scene scene1;
        scene1 = bigRoot.getScene();
        
        bigRoot.getChildren().add(root1);
        
        scene1.getStylesheets().add(getClass().getResource("StyleSheet.css").toExternalForm());
        primaryStage.setScene(scene1);
        primaryStage.show();
    }
    
    /***************************************************************************
     * *************************************************************************
     * *************************************************************************
     * @param stage
     */
    
    public void startGameMessageFunc(Stage stage){
        Label startMess = new Label("Start in 3 seconds");
        startMess.setWrapText(true);
        startMess.setAlignment(Pos.CENTER);
        startMess.setId("startMess");
        startMess.setPrefSize(600, 400);
        startMess.setLayoutX(180);
        startMess.setLayoutY(184);
        startMess.setOpacity(0);
        
        Pane root2 = new Pane();
        root2.setId("pane2");
        root2.setPrefSize(972, 780);
        root2.getChildren().add(startMess);
        
        Timeline startMessTransi = new Timeline();
        KeyValue startMessKv = new KeyValue(startMess.opacityProperty(), 0.9);
        KeyFrame startMessKf = new KeyFrame(Duration.seconds(1), startMessKv);
        
        KeyValue startMessKv1 = new KeyValue(startMess.textProperty(), "Start in 2 seconds");
        KeyFrame startMessKf1 = new KeyFrame(Duration.seconds(2), startMessKv1);
        KeyValue startMessKv2 = new KeyValue(startMess.textProperty(), "Start in 1 second");
        KeyFrame startMessKf2 = new KeyFrame(Duration.seconds(3), startMessKv2);
        KeyValue startMessKv3 = new KeyValue(startMess.textProperty(), "Start in 0 seconds");
        KeyFrame startMessKf3 = new KeyFrame(Duration.seconds(4), startMessKv3);
        
        startMessTransi.getKeyFrames().addAll(startMessKf, startMessKf1, startMessKf2, startMessKf3);
        startMessTransi.setOnFinished((ActionEvent event) -> {
            try {
                startGameFunc(stage);
                
            } catch (SQLException ex) {
                Logger.getLogger(QuizMaster.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        startMessTransi.play();
        
        Scene scene2;
        scene2 = bigRoot.getScene();
        bigRoot.getChildren().clear();
        bigRoot.getChildren().add(root2);
        scene2.getStylesheets().add(getClass().getResource("StyleSheet.css").toExternalForm());
        //Stage stage2 = new Stage();
        stage.setScene(scene2);
        stage.show();
    }
    
    /***************************************************************************
     * *************************************************************************
     * *************************************************************************
     * @param stage
     * @throws java.sql.SQLException
     */
    
    public void startGameFunc(Stage stage) throws SQLException{
        Integer idSelected = null;
        String questionSelected = "", actuAnswer = "";
        boolean isAnsweredAlready = true;
        
        ArrayList<String> propositionsList = new ArrayList<>();
        
        ArrayList<String> dbTableList = new ArrayList<>();
        dbTableList.add("CHEMICALQUESTION");
        dbTableList.add("GEOGRAPHICALQUESTION");
        dbTableList.add("HISTORICALQUESTION");
        dbTableList.add("MATHEMATICALQUESTION");
        
        Random random = new Random();
        
        while(isAnsweredAlready){
            Collections.shuffle(dbTableList);
            idSelected = random.nextInt(5) + 1;
            resultObj = connectionToSQL().executeQuery("Select * From " + dbTableList.get(0) + " where ID = " + idSelected.toString());
            while(resultObj.next()){
                questionSelected = resultObj.getString(2);
            }
            
            int n = 0;
            
            for(String str:questionsAnswered){
                if(questionSelected.equals(str)){
                   n++; 
                   System.out.println("Selected");
                }
                else{}
            }
            
            if(n == 0){
                isAnsweredAlready = false;
            }
            else{}
        }
        
        resultObj = connectionToSQL().executeQuery("Select * From " + dbTableList.get(0) + " where ID = " + idSelected.toString());
        
        while(resultObj.next()){
            propositionsList.add(resultObj.getString(3));
            propositionsList.add(resultObj.getString(4));
            propositionsList.add(resultObj.getString(5));
            propositionsList.add(resultObj.getString(6));
            
            actuAnswer = resultObj.getString(3);
        }
        
        final String actuAnswerFinal = actuAnswer, questionSelectedFinal = questionSelected;
        
        Collections.shuffle(propositionsList);
        
        
        Label question = new Label(questionSelected);
        question.setWrapText(true);
        question.setAlignment(Pos.TOP_LEFT);
        question.setId("question");
        question.setPrefSize(400, 200);
        question.setLayoutX(280);
        question.setLayoutY(59);
        
        Label scoreMess = new Label("Actual Score:");
        scoreMess.setId("scoreMess");
        scoreMess.setAlignment(Pos.CENTER);
        scoreMess.setLayoutX(130);
        scoreMess.setLayoutY(609);
        
        Label actualScore = new Label(String.valueOf(scoreValue));
        actualScore.setId("actualScore");
        actualScore.setAlignment(Pos.CENTER);
        actualScore.setLayoutX(130);
        actualScore.setLayoutY(659);
        
        Button proposition1 = new Button(propositionsList.get(0));
        proposition1.setId("proposition1");
        proposition1.setLayoutX(155);
        proposition1.setLayoutY(316.5);
        proposition1.setOnAction((ActionEvent event) -> {
            ifAnswerTrue(stage, proposition1, actuAnswerFinal, questionSelectedFinal);
        });
        
        Button proposition2 = new Button(propositionsList.get(1));
        proposition2.setId("proposition2");
        proposition2.setLayoutX(505);
        proposition2.setLayoutY(316.5);
        proposition2.setOnAction((ActionEvent event) -> {
            ifAnswerTrue(stage, proposition2, actuAnswerFinal, questionSelectedFinal);
        });
        
        Button proposition3 = new Button(propositionsList.get(2));
        proposition3.setId("proposition3");
        proposition3.setLayoutX(155);
        proposition3.setLayoutY(431.5);
        proposition3.setOnAction((ActionEvent event) -> {
            ifAnswerTrue(stage, proposition3, actuAnswerFinal, questionSelectedFinal);
        });
        
        Button proposition4 = new Button(propositionsList.get(3));
        proposition4.setId("proposition4");
        proposition4.setLayoutX(505);
        proposition4.setLayoutY(431.5);
        proposition4.setOnAction((ActionEvent event) -> {
            ifAnswerTrue(stage, proposition4, actuAnswerFinal, questionSelectedFinal);
        });
        
        Button quitGame = new Button("Quit current game");
        quitGame.setId("quitGame");
        quitGame.setLayoutY(737.5);
        quitGame.setWrapText(true);
        quitGame.setOnAction((ActionEvent event) -> {
            try {
                    int bestScore = 0;
                    resultObj = connectionToSQL().executeQuery("Select * From BESTSCORE where ID = 1");
                    while(resultObj.next()){
                        bestScore = resultObj.getInt(3);
                    }
                    if(scoreValue > bestScore){
                        connectionToSQL().executeUpdate("UPDATE BESTSCORE SET SCORE = " + scoreValue + " WHERE ID = 1");
                    }
                    else{}
                    scoreValue = 0;
                    questionsAnswered.clear();
                    menuScenefunc(stage);
                    } 
                    catch (SQLException ex) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                    }
        });
        
        
        Rectangle rectangle2 = new Rectangle();
        //rectangle2.setId("rectangle2");
        rectangle2.setFill(Color.CADETBLUE);
        rectangle2.setHeight(650);
        rectangle2.setWidth(700);
        rectangle2.setLayoutX(130);
        rectangle2.setLayoutY(59);
        rectangle2.setOpacity(0.85);
        rectangle2.setVisible(true);
        
        
        Pane smallRoot2 = new Pane();
        smallRoot2.setId("smallRoot2");
        smallRoot2.setPrefSize(972, 780);
        smallRoot2.getChildren().addAll(rectangle2, question, proposition1, proposition2, proposition3, proposition4, scoreMess, actualScore,quitGame);
        
        Scene scene3;
        scene3 = bigRoot.getScene();
        bigRoot.getChildren().clear();
        bigRoot.getChildren().add(smallRoot2);
        scene3.getStylesheets().add(getClass().getResource("StyleSheet.css").toExternalForm());
        //Stage stage2 = new Stage();
        stage.setScene(scene3);
        stage.show();
    }
    
    /***************************************************************************
     * *************************************************************************
     * *************************************************************************
     * @param stage
     * @param proposition
     * @param answer
     * @param question
     */
    
    public void ifAnswerTrue(Stage stage, Button proposition, String answer, String question){
        isGreater = false;
        if(proposition.getText().equals(answer)){
                proposition.setStyle("-fx-background-color: green;");
                Timeline chooseAnswerTimeline = new Timeline();
                KeyValue propositionKv = new KeyValue(proposition.styleProperty(),"-fx-background-color: none;"); 
                KeyFrame propositionKf = new KeyFrame(Duration.seconds(0.25), propositionKv);
                KeyValue propositionKv1 = new KeyValue(proposition.styleProperty(),"-fx-background-color: green;"); 
                KeyFrame propositionKf1 = new KeyFrame(Duration.seconds(0.5), propositionKv1);
                KeyValue propositionKv2 = new KeyValue(proposition.styleProperty(),"-fx-background-color: green;"); 
                KeyFrame propositionKf2 = new KeyFrame(Duration.seconds(2), propositionKv2);
                chooseAnswerTimeline.getKeyFrames().addAll(propositionKf, propositionKf1, propositionKf2);
                chooseAnswerTimeline.setOnFinished((ActionEvent event1) -> {
                    if(scoreValue == 19){
                        try {
                            connectionToSQL().executeUpdate("UPDATE BESTSCORE SET SCORE = 20 WHERE ID = 1");
                        } 
                        catch (SQLException ex) {
                            Logger.getLogger(QuizMaster.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        scoreValue = 0;
                        questionsAnswered.clear();
                        menuScenefunc(stage);
                    }
                    else{
                        scoreValue++;
                        questionsAnswered.add(question);
                    
                        try {
                            startGameFunc(stage);
                        } 
                        catch (SQLException ex) {
                            Logger.getLogger(QuizMaster.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                });
                chooseAnswerTimeline.play();
            } 
            else{
                proposition.setStyle("-fx-background-color: red;");
                Timeline chooseAnswerTimeline = new Timeline();
                KeyValue propositionKv = new KeyValue(proposition.styleProperty(),"-fx-background-color: none;"); 
                KeyFrame propositionKf = new KeyFrame(Duration.seconds(0.25), propositionKv);
                KeyValue propositionKv1 = new KeyValue(proposition.styleProperty(),"-fx-background-color: red;"); 
                KeyFrame propositionKf1 = new KeyFrame(Duration.seconds(0.5), propositionKv1);
                KeyValue propositionKv2 = new KeyValue(proposition.styleProperty(),"-fx-background-color: red;"); 
                KeyFrame propositionKf2 = new KeyFrame(Duration.seconds(2), propositionKv2);
                chooseAnswerTimeline.getKeyFrames().addAll(propositionKf, propositionKf1, propositionKf2);
                chooseAnswerTimeline.setOnFinished((ActionEvent event1) -> {
                    try {
                        int bestScore = 0;
                        resultObj = connectionToSQL().executeQuery("Select * From BESTSCORE where ID = 1");
                        while(resultObj.next()){
                            bestScore = resultObj.getInt(3);
                        }
                        if(scoreValue > bestScore){
                            connectionToSQL().executeUpdate("UPDATE BESTSCORE SET SCORE = " + scoreValue + " WHERE ID = 1");
                        }
                        else{}
                        
                        scoreValue = 0;
                        questionsAnswered.clear();
                        menuScenefunc(stage);
                    } 
                    catch (SQLException ex) {
                        Logger.getLogger(QuizMaster.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                });
                chooseAnswerTimeline.play();
                
            }
    }
    
    /***************************************************************************
     * *************************************************************************
     * *************************************************************************
     * @param stage
     */
    
    /*public void optionFunc(Stage stage){
        
        Label reinitialize = new Label("Reinitialize best score:");
        reinitialize.setWrapText(true);
        //label1.setAlignment(Pos.CENTER);
        
        Button reinitializeBtn = new Button("Reinitialize");
        reinitializeBtn.setOnAction((ActionEvent event) -> {
            try {
                connectionToSQL().executeUpdate("UPDATE BESTSCORE SET SCORE = 0 WHERE ID = 1");
            } catch (SQLException ex) {
                Logger.getLogger(QuizMaster.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
       
        
        VBox vbox = new VBox();
        vbox.setId("vbox");
        vbox.setPrefSize(600, 400);
        vbox.setLayoutX(180);
        vbox.setLayoutY(184);
        vbox.setOpacity(0.9);
        vbox.getChildren().addAll(reinitialize, reinitializeBtn);
        
        Button back = new Button("Back");
        back.setId("back");
        back.setLayoutY(737.5);
        back.setWrapText(true);
        back.setOnAction((ActionEvent event) -> {
            menuScenefunc(stage);
        });
        
        Pane root3 = new Pane();
        root3.setId("pane3");
        root3.setPrefSize(972, 780);
        //smallRoot2.setOpacity(0.4);
        root3.getChildren().addAll(vbox, back);
        
        Scene scene2;
        scene2 = bigRoot.getScene();
        bigRoot.getChildren().clear();
        bigRoot.getChildren().add(root3);
        scene2.getStylesheets().add(getClass().getResource("StyleSheet.css").toExternalForm());
        //Stage stage2 = new Stage();
        stage.setScene(scene2);
        stage.show();
    }*/
    
    /***************************************************************************
     * *************************************************************************
     * *************************************************************************
     * @param stage
     */
    
    public void instructionsFunc(Stage stage){
        
        Label instructions = new Label("Read the questions and choose the correct answer\n\n"
                + "Get \"1\"(one) point for every correct answer\n\n"
                + "The highest score possible, for now, is \"20\"(twenty)");
        instructions.setWrapText(true);
        instructions.setAlignment(Pos.CENTER);
        instructions.setId("instructions1");
        instructions.setPrefSize(600, 400);
        instructions.setLayoutX(180);
        instructions.setLayoutY(184);
        instructions.setOpacity(0.9);
        
        
        
        Button back = new Button("Back");
        back.setId("back");
        back.setLayoutY(737.5);
        back.setWrapText(true);
        back.setOnAction((ActionEvent event) -> {
            menuScenefunc(stage);
        });
        
        Pane root3 = new Pane();
        root3.setId("pane3");
        root3.setPrefSize(972, 780);
        //smallRoot2.setOpacity(0.4);
        root3.getChildren().addAll(instructions, back);
        
        Scene scene2;
        scene2 = bigRoot.getScene();
        bigRoot.getChildren().clear();
        bigRoot.getChildren().add(root3);
        scene2.getStylesheets().add(getClass().getResource("StyleSheet.css").toExternalForm());
        //Stage stage2 = new Stage();
        stage.setScene(scene2);
        stage.show();
    }
    
    /***************************************************************************
     * *************************************************************************
     * *************************************************************************
     * @param stage
     * @throws java.sql.SQLException
     */
    
    public void bestScoreFunc(Stage stage) throws SQLException{
        
        //connectionToSQL().executeUpdate("UPDATE BESTSCORE SET SCORE = 0 WHERE ID = 1");
        int bestScore = 0;
        
        Label reinitialize = new Label("Reinitialize best score:");
        reinitialize.setTextFill(Color.WHITE);
        reinitialize.setWrapText(true);
        //label1.setAlignment(Pos.CENTER);
        
        Button reinitializeBtn = new Button("Reinitialize");
        reinitializeBtn.setOnAction((ActionEvent event) -> {
            try {
                connectionToSQL().executeUpdate("UPDATE BESTSCORE SET SCORE = 0 WHERE ID = 1");
            } catch (SQLException ex) {
                Logger.getLogger(QuizMaster.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        resultObj = connectionToSQL().executeQuery("Select * From BESTSCORE where ID = 1");
        
        while(resultObj.next()){
            bestScore = resultObj.getInt(3);
        }
        
        Label bestScoreLabel = new Label("Best Score:\n" + bestScore);
        bestScoreLabel.setWrapText(true);
        bestScoreLabel.setAlignment(Pos.CENTER);
        bestScoreLabel.setId("instructions1");
        bestScoreLabel.setPrefSize(600, 400);
        bestScoreLabel.setLayoutX(180);
        bestScoreLabel.setLayoutY(184);
        bestScoreLabel.setOpacity(0.9);
        
        VBox vbox = new VBox();
        vbox.setId("vbox");
        vbox.setPrefSize(600, 400);
        vbox.setLayoutX(180);
        vbox.setLayoutY(184);
        vbox.setOpacity(0.9);
        vbox.getChildren().addAll(bestScoreLabel, reinitialize, reinitializeBtn);
        
        
        
        Button back = new Button("Back");
        back.setId("back");
        back.setLayoutY(737.5);
        back.setWrapText(true);
        back.setOnAction((ActionEvent event) -> {
            menuScenefunc(stage);
        });
        
        Pane root3 = new Pane();
        root3.setId("pane3");
        root3.setPrefSize(972, 780);
        //smallRoot2.setOpacity(0.4);
        root3.getChildren().addAll(vbox, back);
        
        Scene scene2;
        scene2 = bigRoot.getScene();
        bigRoot.getChildren().clear();
        bigRoot.getChildren().add(root3);
        scene2.getStylesheets().add(getClass().getResource("StyleSheet.css").toExternalForm());
        //Stage stage2 = new Stage();
        stage.setScene(scene2);
        stage.show();
    }
    
    /***************************************************************************
     * *************************************************************************
     * *************************************************************************
     * @param stage
     */
    
    public void aboutFunc(Stage stage){
        Label aboutLabel = new Label("Quiz Master is puzzle game developed for an end of semester project by EDOU BIYO'O Nasser Danys,"
                + " level200 student of Catholic University College of Ghana.\n"
                + "We are reachable for any criticism at \"edoubiyoonasser@gmail.com\"");
        aboutLabel.setWrapText(true);
        aboutLabel.setAlignment(Pos.CENTER);
        aboutLabel.setId("instructions1");
        aboutLabel.setPrefSize(600, 400);
        aboutLabel.setLayoutX(180);
        aboutLabel.setLayoutY(184);
        aboutLabel.setOpacity(0.9);
        
        
        
        Button back = new Button("Back");
        back.setId("back");
        back.setLayoutY(737.5);
        back.setWrapText(true);
        back.setOnAction((ActionEvent event) -> {
            menuScenefunc(stage);
        });
        
        Pane root3 = new Pane();
        root3.setId("pane3");
        root3.setPrefSize(972, 780);
        //smallRoot2.setOpacity(0.4);
        root3.getChildren().addAll(aboutLabel, back);
        
        Scene scene2;
        scene2 = bigRoot.getScene();
        bigRoot.getChildren().clear();
        bigRoot.getChildren().add(root3);
        scene2.getStylesheets().add(getClass().getResource("StyleSheet.css").toExternalForm());
        //Stage stage2 = new Stage();
        stage.setScene(scene2);
        stage.show();
    }
    
    /***************************************************************************
     * *************************************************************************
     * *************************************************************************
     * @return 
     * @throws java.lang.InterruptedException 
     */
    
    /*public String inputNameDialog() throws InterruptedException{
        String name = null;
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Name required");
        dialog.setHeaderText("New best score!!!");
        dialog.setContentText("Enter your name:");
        dialog.show();
        
        while("".equals(dialog.getEditor().getText())){
            dialog.wait();
        }
        return dialog.getEditor().getText();
    }*/
    
    /***************************************************************************
     * *************************************************************************
     * *************************************************************************
     * @return 
     * @throws java.sql.SQLException 
     */
    
    public Statement connectionToSQL() throws SQLException{
        //Connection connectionObj = null;
        //Statement statementObj = null;
        
        //connectionObj = DriverManager.getConnection("jdbc:sqlite:./database/databasefile.db");
        return DriverManager.getConnection("jdbc:sqlite:./database/databasefile.db").createStatement();
    }
    
    /***************************************************************************
     * *************************************************************************
     * *************************************************************************
     * @return 
     * @throws java.io.FileNotFoundException 
     */
    
    /*public AudioStream audio() throws FileNotFoundException, IOException{
        InputStream music = new FileInputStream(new File("C:\\Users\\Tony Ndoss\\Documents\\NetBeansProjects\\QuizMaster\\src\\quizmaster\\sounds\\track.wav"));
        AudioStream audios = new AudioStream(music);
        return audios;
        
    }*/
}
