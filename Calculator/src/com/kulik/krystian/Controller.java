package com.kulik.krystian;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

enum Operation {None, Adding, Subtracting, Multiplying, Dividing}

public class Controller {

    @FXML
    private Button digit0;
    @FXML
    private Button digit1;
    @FXML
    private Button digit2;
    @FXML
    private Button digit3;
    @FXML
    private Button digit4;
    @FXML
    private Button digit5;
    @FXML
    private Button digit6;
    @FXML
    private Button digit7;
    @FXML
    private Button digit8;
    @FXML
    private Button digit9;
    @FXML
    private Button sign;
    @FXML
    private Button point;
    @FXML
    private Button divide;
    @FXML
    private Button multiply;
    @FXML
    private Button subtract;
    @FXML
    private Button add;
    @FXML
    private Button clear;
    @FXML
    private Button backspace;
    @FXML
    private Button equals;

    @FXML
    private Label label;
    @FXML
    private StackPane pane;

    private double number;
    private final double MAX_NUMBER = 9999999999d;
    private double result;
    Operation operation = Operation.None;
    private boolean isEqualing = false;

    private boolean fraction = false;
    private int fractionDigits = 1;

    @FXML
    public void initialize() {

        number = 0;
        result = 0;

        pane.setAlignment(label, Pos.CENTER_RIGHT);

        handleDigit(digit0, 0);
        handleDigit(digit1, 1);
        handleDigit(digit2, 2);
        handleDigit(digit3, 3);
        handleDigit(digit4, 4);
        handleDigit(digit5, 5);
        handleDigit(digit6, 6);
        handleDigit(digit7, 7);
        handleDigit(digit8, 8);
        handleDigit(digit9, 9);

        backspace.setOnAction(e -> {
            if (Math.abs(number) < 10)
                number = 0;
            else
                number /= 10;
            updateLabel(number);


        });

        sign.setOnAction(e -> {
            number *= -1;
            updateLabel(number);

        });

        clear.setOnAction(e -> {
            result = 0;
            number = 0;
            updateLabel(number);
            operation = Operation.None;
            fractionDigits = 1;
            fraction = false;

        });

        point.setOnAction(e -> {

            if (isEqualing) {
                isEqualing = false;
                number = result;
                result = 0;
                operation = Operation.None;
            }


            fraction = true;
            if ((long) number == number)
                label.setText(Long.toString((long) number) + '.');


        });


        setOperation(subtract, Operation.Subtracting);
        setOperation(multiply, Operation.Multiplying);
        setOperation(add, Operation.Adding);
        setOperation(divide, Operation.Dividing);

        equals.setOnAction(e -> {
            if (!(operation == Operation.None)) {
                isEqualing = true;
                execute();
                updateLabel(result);
            }


        });

    }

    private void execute() {
        if (operation == Operation.None) {
            result = number;
        }
        if (operation == Operation.Adding) {
            result = number + result;
        } else if (operation == Operation.Dividing) {
            if (number != 0)
                result = result / number;

        } else if (operation == Operation.Multiplying) {
            result = number * result;
        } else if (operation == Operation.Subtracting) {
            result = result - number;
        }

        if (!isEqualing)
            number = 0;

        fraction = false;
        fractionDigits = 1;

    }

    private void handleDigit(Button digitButton, int digit) {
        digitButton.setOnAction(e -> {
            if (isEqualing) {
                isEqualing = false;
                number = 0;
                operation = Operation.None;
                fraction = false;
            }
            if (fraction) {
                double newNumber = number;
                double decimalPower = Math.pow(10, fractionDigits);
                newNumber += ((double) digit) / (decimalPower);

                for (int i = 0; i < fractionDigits; i++)
                    newNumber *= 10;
                newNumber = Math.round(newNumber);

                for (int i = 0; i < fractionDigits; i++)
                    newNumber /= 10;

                fractionDigits++;
                number = newNumber;
                updateLabel(number);

            } else {
                double newNumber = number;
                newNumber *= 10;
                newNumber += digit;
                if (newNumber <= MAX_NUMBER)
                    number = newNumber;
                updateLabel(number);
            }

        });


    }

    private void setOperation(Button operationButton, Operation o) {

        operationButton.setOnAction(e -> {

            {

                if (isEqualing) {
                    isEqualing = false;
                    number = 0;
                } else {
                    execute();
                    updateLabel(result);
                }

                operation = o;
            }
        });

    }

    private void updateLabel(double number) {

        if ((long) number == number)
            label.setText(Long.toString((long) number));
        else
            label.setText(Double.toString(number));
    }


}
