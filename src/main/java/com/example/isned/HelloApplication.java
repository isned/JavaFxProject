package com.example.isned;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("admin");
        stage.setScene(scene);


        Stage Hello = new Stage();
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Hello.fxml"));
        Parent root1 = loader.load();
        Scene s = new Scene(root1);
        Hello.initStyle(StageStyle.UNDECORATED);
        Hello.setScene(s);
        Hello.show();
        //code progressBar
        /*Group root = new Group();
        //Scene scene = new Scene(root, 260, 80);
        stage.setScene(scene);
        Group g = new Group();
        ProgressBar p2 = new ProgressBar();
        g.getChildren().add(p2);
        scene.setRoot(g);
        stage.show();*/


        //public static void main(String[] args) {
        //launch();
        //}

        new Thread(new Runnable() {
            @Override
            public void run() {
                //ici traitement a faire
                try {
                    Thread.sleep(3000);//endormir 2 sec
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Hello.hide();

                        stage.show();

                    }
                });
            }
        }).start();
    }}