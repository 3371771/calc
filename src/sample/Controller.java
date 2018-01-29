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

    private boolean clik = false;
    private boolean start = false;

    private String operator = "";
    private Model model = new Model();

    @FXML
    private void processNum (ActionEvent event) {
        String value = ((Button) event.getSource()).getText();

        if (output.toString().length() > 225) {
            soMany();
        } else
            {
                output.setText(output.getText() + value);
            }
    }

    @FXML
    private void processOperator (ActionEvent event) {
        if (output.toString().length() > 225) {
            soMany();
        } else {
            int index = output.getText().indexOf(" ");

            String value = ((Button) event.getSource()).getText();
            if (!"=".equals(value)) {
                System.out.println("Нажато не равно");
                if (!operator.isEmpty()) return;
                else {
                operator = value;
                output.setText(output.getText() + " " + operator + " ");
                }

            } else {
                if (operator.isEmpty()) return;
//режем и парсим первое число
                String value3 = (output.getText().substring(0, index));
                System.out.println(output.getText());
                num1 = Double.parseDouble(value3);
//режем и парсим второе число
                String value2 = (output.getText().substring(output.getText().lastIndexOf(" ") + 1));
                num2 = Double.parseDouble(value2);

                //считаем
                output.setText(String.valueOf(model.calculation(num1, num2, operator)));
                operator = "";
                start = true;
            }
        }
    }

    @FXML
    private void clear (ActionEvent event) {
       output.setText("");
       start = false;
       clik = false;
    }

    @FXML
    private int processMoney (ActionEvent event) {
        if (output.toString().length() > 225) {
            soMany();
        } else {
            String value = ((Button) event.getSource()).getText();
            output.setText(output.getText() + " " + value);

            if (!clik) {
                output.setText(output.getText() + "->");
                //проверяем было ли ранее что-то ввидено
                //проверяем что будем конвертировать и присваеваем значение переменной trigger
                //переводим указатель нажатия clik = true
                switch (value) {
                    case "\u20BD":
                        trigger = 1;
                        clik = true;
                        break;
                    case "€":
                        trigger = 2;
                        clik = true;
                        break;
                    case "$":
                        trigger = 3;
                        clik = true;
                        break;
                    default:
                        break;
                }
            }
        }
        //получаем что хотим конвертировать
            return trigger;
        }

    @FXML
    private void processConvert (ActionEvent event) throws IOException {
        int index = output.getText().indexOf(" ");

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

        output.setText(String.valueOf(model.convert(num1, thisConv,toThisConv)));
        operator = "";
        start = true;
    }

    private void soMany () {
        Model.errorBox("Ошибка!", "Слишком много символов. Очистите поле и попробуйте еще раз!");
        output.setText("Очень много символов!!");
    }
}
