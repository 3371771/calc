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
    int trigger = 0;

    private int prev;
    private String result;

    private boolean start = true;
    private boolean clik = false;

    private String operator = "";
    private Model model = new Model();


    @FXML
    private void processNum (ActionEvent event) {

        String value = ((Button) event.getSource()).getText();

        if (output.toString().length() > 225) {
            soMany();
        } else
            {
                System.out.println(output.toString().length());
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
                if (!operator.isEmpty()) return;
                operator = value;

                output.setText(output.getText() + " " + operator + " ");
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
    }

    @FXML
    private int processMoney (ActionEvent event) {

        if (output.toString().length() > 225) {
            soMany();
        } else {

            String value = ((Button) event.getSource()).getText();
            output.setText(output.getText() + " " + value);

            if (!clik) {
                switch (value) {
                    case "\u20BD":
                        trigger = 1;
                        clik = true;
                        System.out.println(1);
                        break;
                    case "€":
                        trigger = 2;
                        clik = true;
                        System.out.println(2);
                        break;
                    case "$":
                        trigger = 3;
                        clik = true;
                        System.out.println(3);
                        break;
                    default:
                        break;
                }
            }
        }
            return trigger;
        }


    @FXML
    private void processConvert (ActionEvent event) throws IOException {

        int index = output.getText().indexOf(" ");

        String value3 = (output.getText().substring(0,index));
        System.out.println(output.getText());
        num1 = Double.parseDouble(value3);

        result = (output.getText().substring(output.getText().lastIndexOf(" ")+1));
        prev = trigger;
        System.out.println(num1);
        System.out.println(prev);
        System.out.println(result);

        output.setText(String.valueOf(model.convert(num1,prev,result)));
        operator = "";
        start = true;
    }

    private void soMany () {
        Model.errorBox("Ошибка!", "Слишком много символов. Очистите поле и попробуйте еще раз!");
        output.setText("Очень много символов!!");
    }
}
