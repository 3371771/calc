package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.awt.*;
import java.io.IOException;


public class Controller {

    @FXML
    private Text output ;
    private double num1 = 0;
    private double num2 = 0;



    private String prev;
    private String result;

    private boolean start = true;

    private String operator = "";
    private Model model = new Model();



    @FXML
    private void processNum (ActionEvent event) {

        String value = ((Button) event.getSource()).getText();
        output.setText(output.getText() + value);
    }

    @FXML
    private void processOperator (ActionEvent event) {

        int index = output.getText().indexOf(" ");


        String value = ((Button)event.getSource()).getText();
        if (!"=".equals(value)){
            if (!operator.isEmpty()) return;
            operator = value;

         output.setText(output.getText() + " "+ operator + " ");
        } else {
            if (operator.isEmpty()) return;
//режем и парсим первое число
            String value3 = (output.getText().substring(0,index));
            System.out.println(output.getText());
            num1 = Double.parseDouble(value3);
//режем и парсим второе число
            String value2 = (output.getText().substring(output.getText().lastIndexOf(" ")+1));
            num2 = Double.parseDouble(value2);

    //считаем
            output.setText(String.valueOf(model.calculation(num1,num2,operator)));
            operator = "";
            start = true;
        }
    }

    @FXML
    private void clear (ActionEvent event) {
       output.setText("");
        start = false;
    }

    @FXML
        private void processConvert (ActionEvent event) throws IOException {

//        int index = output.getText().indexOf(" ");
//
//        String value3 = (output.getText().substring(0,index));
//        System.out.println(output.getText());
//        num1 = Double.parseDouble(value3);
//
//       result = (output.getText().substring(output.getText().lastIndexOf(" ")+1));prev = "";
//
//        output.setText(String.valueOf(model.convert(num1,prev,result)));
//        operator = "";
//        start = true;
        parser();

    }

    @FXML
    private void processMoney (ActionEvent event) {
        String value = ((Button) event.getSource()).getText();
        output.setText(output.getText() + " " + value);
    }

    private double parser () throws IOException {
        double b;
        double d;
        Document doc = Jsoup.connect("https://cbr.ru/").get();
        Elements tdElements = doc.getElementsByAttributeValue("class", "weak");


        String a = tdElements.get(0).ownText();
        a=a.replace(',','.');
        b = Double.parseDouble(a);

        String c = tdElements.get(1).ownText();
        c=c.replace(',','.');
        d = Double.parseDouble(c);

        System.out.println(b);
        System.out.println(d);

        return 0;
    }
}
