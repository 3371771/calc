package sample;

import javafx.scene.control.Alert;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

;

class Model {

    private double itog = 0;
    private double dollar;
    private double euro;

    double calculation(double a, double b, String operator) {

        switch (operator){
            case "+":
                return a+b;
            case "-":
                return a-b;
            case "*":
                return a*b;
            case "/":
                if(b == 0) {errorBox("Ошибка", "На ноль делить нельзя!");}
                else
                return a/b;
        }

        System.out.println("Неизвестный оператор" + operator);
        return 0;
    }

    double convert(double a, int prev, String result) throws IOException {

        parser();

        //рубль в
        if (prev == 1) {
            //доллар
            if (result.equals("$")) {
                itog = a/dollar;
            }
            //евро
            else if (result.equals("€"))
            {
                parser();
                itog = a/euro;
            }
        }
        //евро в
        else if (prev == 2) {
            //рубль
            if (result.equals("\u20BD")) {
                itog = a * euro;
            }
            //доллар
            else if (result.equals("$")) {
                //перевдедем евро в рубли
                double preItog = a*euro;

                //потом рубли в доллары
                itog = preItog/dollar;
            }
        }
        //доллар в
        else if (prev == 3) {
            //рубль
            if (result.equals("\u20BD")) {
                itog = a * dollar;
            }
            //евро
            else if (result.equals("€")) {
                //перевдедем доллары в рубли
                double preItog = a*dollar;

                //потом рубли в евро
                itog = preItog/euro;
            }
        }
        return itog;
    }

    private void parser () throws IOException {

        //клннект и парсинг нужного тэга
        Document doc = Jsoup.connect("https://cbr.ru/").get();
        Elements tdElements = doc.getElementsByAttributeValue("class", "weak");

        //парсим курс доллара
        String dollarStr = tdElements.get(0).ownText();
        dollarStr = dollarStr.replace(',','.');
        dollar = Double.parseDouble(dollarStr);

        //парсим курс евро
        String euroStr = tdElements.get(1).ownText();
        euroStr = euroStr.replace(',','.');
        euro = Double.parseDouble(euroStr);

    }

    static void errorBox(String infoMessage, String headerMessage)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(infoMessage);

        alert.setHeaderText(headerMessage);
        alert.showAndWait();
    }
}
