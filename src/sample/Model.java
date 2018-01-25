package sample;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

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
                if(b == 0) return 0;
                //toDo вывод окна На ноль делить нельзя
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
//        else if (prev == 2) {
//            //рубль
//            if (result.equals("\u20BD")) {
//
//                itog = a * result1;
//            }
//            //доллар
//            else if (result.equals("$")) {
//                parser();
//                itog = a * result1;
//            }
//        }
//        //доллар в
//        else if (prev == 3) {
//            //рубль
//            if (result.equals("\u20BD")) {
//                parser();
//                itog = a * result1;
//            }
//            //евро
//            else if (result.equals("€")) {
//                parser();
//                itog = a * result1;
//            }
//        }



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
}
