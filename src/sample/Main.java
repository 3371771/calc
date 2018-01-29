package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Калькулятор");
        primaryStage.getIcons().add(new Image("/res/icon.jpg"));
        primaryStage.setScene(new Scene(root, 300, 400));
        primaryStage.setResizable(false);
        primaryStage.show();

        root.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.F1))
                System.out.println("f1");
            Manual.display();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
