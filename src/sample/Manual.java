package sample;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Manual {

    static void display() {

        Stage windowAbout = new Stage();
        //добавим иконку и названеи окна
        windowAbout.setTitle("Мануал");
        windowAbout.getIcons().add(new Image("/res/icon.jpg"));
        windowAbout.setHeight(600);
        windowAbout.setWidth(700);
        windowAbout.setX(10);
        windowAbout.setY(10);

        WebView webView = new WebView();
        WebEngine engine = webView.getEngine();
        engine.load(Manual.class.getClassLoader().getResource("res/HTML/index.html").toExternalForm());
        Scene wV = new Scene(webView);
        windowAbout.setScene(wV);
        windowAbout.show();
    }
}