package sample;

class Model {

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

    double convert(double a, String prev, String result) {
        int kurs = 0;
//лучше if
        switch (result){
            case "dol":
                kurs = 60;
            case "evr":
                kurs = 70;
        }
       return a * kurs;

//тут надо забирать данные из парсера и сравнивать что во что хотим кастовать
    }

}
