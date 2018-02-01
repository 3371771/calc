package sample;

import javafx.scene.control.Alert;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

class Model {

    private double itog = 0;
    private double dollar;
    private double euro;

    //функция подсчета
    double calculation(double a, double b, String operator) {
        //в зависимости от оператора счтиаем и округляем до 3-х знаков после запятой
        switch (operator){
            case "+":
                return Math.round((a+b) * 1000.0) / 1000.0;
            case "-":
                return Math.round((a-b) * 1000.0) / 1000.0;
            case "*":
                return Math.round((a*b) * 1000.0) / 1000.0;
            case "/":
                //обработка ошибки "на ноль делить нельзя"
                if(b == 0) {errorBox("Ошибка", "На ноль делить нельзя!");}
                else
                return  Math.round((a/b) * 1000.0) / 1000.0;
        }
        return 0;
    }

    //функция конвертирования
    double convert(double a, int thisConv, int toThisConv) throws IOException {

        //сперва получаем актуальных значения курсов валют
        parser();

        //рубль в
        if (thisConv == 1) {
            //доллар
            if (toThisConv == 3) {
                itog = a/dollar;
            }
            //евро
            else if (toThisConv == 2)
            {
                itog = a/euro;
            }
        }
        //евро в
        else if (thisConv == 2) {
            //рубль
            if (toThisConv == 1) {
                itog = a * euro;
            }
            //доллар
            else if (toThisConv == 3) {
                //перевдедем евро в рубли
                double preItog = a*euro;

                //потом рубли в доллары
                itog = preItog/dollar;
            }
        }
        //доллар в
        else if (thisConv == 3) {
            //рубль
            if (toThisConv == 1) {
                itog = a * dollar;
            }
            //евро
            else if (toThisConv == 2) {
                //перевдедем доллары в рубли
                double preItog = a*dollar;

                //потом рубли в евро
                itog = preItog/euro;
            }
        }
        //округляем
        itog = Math.round(itog * 1000.0) / 1000.0;
        return itog;
    }

    //парсер
    private void parser () throws IOException {

        //коннект и парсинг нужного тэга
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

    //функция вывода окна ошибки
    static void errorBox(String infoMessage, String headerMessage)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(infoMessage);

        alert.setHeaderText(headerMessage);
        alert.showAndWait();
    }
}
