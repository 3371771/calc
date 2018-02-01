package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.IOException;

public class Controller {

    @FXML
    private Text output ;
    private double num1 = 0;
    private double num2 = 0;
    private int trigger = 0;

    private boolean click = false;

    private String operator = "";
    private Model model = new Model();

    @FXML
    //обработка нажатия на кнопки-цифры
    private void processNum (ActionEvent event) {
        //считываем значение с нажатой кнопки
        String value = ((Button) event.getSource()).getText();

        //проверяем не привышен ли лимит символов в окне
        if (output.toString().length() > 225) {
            //если да обрабатываем, как ошибку
            soMany();
        } else
            {
                output.setText(output.getText() + value);
            }
    }

    @FXML
    //обработка нажатия на кнопки-операторы вычисленйи
    private void processOperator (ActionEvent event) {
        if (output.toString().length() > 225) {
            soMany();
        } else {
            //задаем переменную для последующей вырезки необходимой части текста
            int index = output.getText().indexOf(" ");

            //считываем значение с нажатой кнопки
            String value = ((Button) event.getSource()).getText();
            //проверяем какой из операторов был введен
            if (!"=".equals(value)) {
                if (!operator.isEmpty()) return;
                else {
                operator = value;
                //выводим все данне на дисплей
                output.setText(output.getText() + " " + operator + " ");
                }
            } else {
                if (operator.isEmpty()) return;

                //режем и парсим первое число
                String value3 = (output.getText().substring(0, index));
                num1 = Double.parseDouble(value3);

                //режем и парсим второе число
                String value2 = (output.getText().substring(output.getText().lastIndexOf(" ") + 1));
                num2 = Double.parseDouble(value2);

                //считаем и выводим результат
                output.setText(String.valueOf(model.calculation(num1, num2, operator)));
                operator = "";
            }
        }
    }

    @FXML
    //функция для очистки дисплея
    private void clear (ActionEvent event) {
       output.setText("");
       click = false;
    }

    @FXML
    //обработка нажатия на кнопки-валюты
    private int processMoney (ActionEvent event) {
        if (output.toString().length() > 225) {
            soMany();
        } else {
            String value = ((Button) event.getSource()).getText();
            output.setText(output.getText() + " " + value);

            if (!click) {
                output.setText(output.getText() + "->");
                //проверяем было ли ранее что-то ввидено
                //проверяем что будем конвертировать и присваеваем значение переменной trigger
                // (не считываем на прямую, т.к.на разных ПК разные кодировки и работает не всегда корректно
                //переводим указатель нажатия clik = true
                switch (value) {
                    case "\u20BD":
                        trigger = 1;
                        click = true;
                        break;
                    case "€":
                        trigger = 2;
                        click = true;
                        break;
                    case "$":
                        trigger = 3;
                        click = true;
                        break;
                    default:
                        break;
                }
            }
        }
        //получаем значение переменной для того, что хотим конвертировать
            return trigger;
        }

    @FXML
    //обработка нажатия на кнопку conv
    private void processConvert (ActionEvent event) throws IOException {
        int index = output.getText().indexOf(" ");

        //счиывваем количестов конвертируемой валюты
        String valueNumConv = (output.getText().substring(0,index));
        num1 = Double.parseDouble(valueNumConv);

        // вырезаем то во что будем конвертировать
        String toThisConvString = (output.getText().substring(output.getText().lastIndexOf(" ") + 1));
        int toThisConv = 0;
        switch (toThisConvString) {
            //если переводим в рубли
            case "\u20BD":
                toThisConv = 1;
                break;
            //если переводим в евро
            case "€":
                toThisConv = 2;
                break;
            //если переводим в доллары
            case "$":
                toThisConv = 3;
                break;
            default: break;
        }
        int thisConv = trigger;

        //конвертируем
        output.setText(String.valueOf(model.convert(num1, thisConv,toThisConv)));
        operator = "";
    }

    //функция вывода ошибки при превышении лимита ввода
    private void soMany () {
        Model.errorBox("Ошибка!", "Слишком много символов. Очистите поле и попробуйте еще раз!");
        output.setText("Очень много символов!!");
    }
}
