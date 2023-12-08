package com.example.languagetranslator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image; // Import the Image class

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/languagetranslator/translator.fxml"));
        Parent root = loader.load();

        // Set the scene
        primaryStage.setScene(new Scene(root));

        // Set the title of the stage (application window)
        primaryStage.setTitle("Language Translator"); // Set your application's title here

        // Set the icon for the stage
        Image icon = new Image(getClass().getResourceAsStream("/com/example/languagetranslator/images/Rabab.png"));
        primaryStage.getIcons().add(icon);

        // Show the stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
