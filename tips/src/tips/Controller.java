package tips;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;

public class Controller {


    private static boolean isPressedButton = false;
    private static double output_tip = 0;
    private static double output_result = 0;
    private static double output_result_in_rubles = 0;
    @FXML
    private Label cheque;

    @FXML
    private TextField inputBill;

    @FXML
    private Label error;

    @FXML
    private ComboBox<?> comboBox;

    @FXML
    private Label tip;

    @FXML
    private ToggleGroup group;

    @FXML
    private RadioButton noTip;

    @FXML
    private RadioButton fivePercent;

    @FXML
    private RadioButton tenPercent;

    @FXML
    private Button deem;

    @FXML
    private Button clear;

    @FXML
    private CheckBox inRubles;

    @FXML
    private CheckBox round;

    @FXML
    private Label resultInRubles;

    @FXML
    private TextField outputTip;

    @FXML
    private TextField outputBillInRubles;

    @FXML
    private TextField outputResult;

    @FXML
    private Label InRublesCheque;

    @FXML
    private TextField outputResultInRubles;

    @FXML
    private Label tipInRubles;

    @FXML
    private TextField outputTipInRubles;

    @FXML
    void clickOnClear(ActionEvent event) {
        tipInRubles.setVisible(false);
        InRublesCheque.setVisible(false);
        resultInRubles.setVisible(false);
        outputBillInRubles.setVisible(false);
        outputResultInRubles.setVisible(false);
        outputTipInRubles.setVisible(false);
        inRubles.setSelected(false);
        round.setSelected(false);
        inputBill.clear();
        outputTip.clear();
        outputResult.clear();
        error.setVisible(false);
    }

    @FXML
    void clickOnDeem(ActionEvent event) {
        if (isValidation() == false || parses(inputBill) < 0) {
            outputError();
            return;
        } else {
            isPressedButton = true;

            radioButtonChanged();
            clickOnInRubles(event);
            clickOnRound(event);
            error.setVisible(false);
        }
    }

    @FXML
    void clickOnFivePercent(ActionEvent event) {

    }

    @FXML
    void clickOnInRubles(ActionEvent event) {

        if (comboBox.getValue().equals("Рубли")) return;

        if (!isPressedButton) return;

        if (inRubles.isSelected()) {


            if (!comboBox.getValue().equals("Рубли")) {
                tipInRubles.setVisible(true);
                InRublesCheque.setVisible(true);
                resultInRubles.setVisible(true);
                outputBillInRubles.setVisible(true);
                outputResultInRubles.setVisible(true);
                outputTipInRubles.setVisible(true);

                double billInRubles = parses(inputBill);
                double tipInRubles = output_tip;

                double result_In_Rubles;

                if (comboBox.getValue().equals("Евро")) {
                    billInRubles = billInRubles * 76.91;
                    tipInRubles = tipInRubles * 76.91;
                }
                if (comboBox.getValue().equals("Доллары")) {
                    billInRubles = billInRubles * 68.85;
                    tipInRubles = tipInRubles * 68.85;

                }
                billInRubles = roundingNumber(billInRubles);
                outputBillInRubles.setText(String.valueOf(billInRubles));

                outputFormatted(outputBillInRubles);
                outputTipInRubles.setText(String.valueOf(tipInRubles));
                outputFormatted(outputTipInRubles);
                result_In_Rubles = billInRubles + tipInRubles;
                result_In_Rubles = roundingNumber(result_In_Rubles);
                outputResultInRubles.setText(String.valueOf(result_In_Rubles));
                output_result_in_rubles = parses(outputResultInRubles);
                outputFormatted(outputResultInRubles);

            }
        } else {
            tipInRubles.setVisible(false);
            InRublesCheque.setVisible(false);
            resultInRubles.setVisible(false);
            outputBillInRubles.setVisible(false);
            outputResultInRubles.setVisible(false);
            outputTipInRubles.setVisible(false);
        }
    }

    @FXML
    void clickOnNoTip(ActionEvent event) {

    }

    @FXML
    void clickOnRound(ActionEvent event) {

        if (!isPressedButton) return;

        double forPayment = output_result;

        if (round.isSelected()) {

            if (group.getSelectedToggle().equals(noTip)) {
                forPayment = Math.ceil(forPayment);

            }

            if (!group.getSelectedToggle().equals(noTip)) {
                forPayment = Math.round(forPayment);

            }
        }
        outputResult.setText(String.valueOf(forPayment));
        outputFormatted(outputResult);
        roundingUP();

    }

    @FXML
    void clickOnTenPersent(ActionEvent event) {

    }

    private void radioButtonChanged() {

        if (isValidation() == true) {
            double tipsInMoney = parses(inputBill);
            if (group.getSelectedToggle().equals(noTip)) {
                tipsInMoney = 0;
            }

            if (group.getSelectedToggle().equals(fivePercent)) {
                tipsInMoney = tipsInMoney / 100 * 5;
            }

            if (group.getSelectedToggle().equals(tenPercent)) {
                tipsInMoney = tipsInMoney / 100 * 10;

            }
            tipsInMoney = roundingNumber(tipsInMoney);
            outputTip.setText(String.valueOf(tipsInMoney));
            output_tip = parses(outputTip);
            outputFormatted(outputTip);
            double forPayment = tipsInMoney + parses(inputBill);
            forPayment = roundingNumber(forPayment);

            outputResult.setText(String.valueOf(forPayment));
            output_result = parses(outputResult);
            outputFormatted(outputResult);
        }
    }

    double parses(TextField textField) {
        return Double.valueOf(textField.getText());
    }

    boolean isValidation() {
        try {
            parses(inputBill);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void outputError() {
        error.setVisible(true);
        error.setText("Некорректный ввод");

    }

    private double roundingNumber(Double number) {

        return new BigDecimal(number).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    private void roundingUP() {
        double forPaymentInRubles = output_result_in_rubles;
        if (!inRubles.isSelected()) return;
        if (round.isSelected() == true) {
            if (group.getSelectedToggle().equals(noTip)) {
                forPaymentInRubles = Math.ceil(forPaymentInRubles);
            } else {
                forPaymentInRubles = Math.round(forPaymentInRubles);
            }

        }
        outputResultInRubles.setText(String.valueOf(forPaymentInRubles));
        outputFormatted(outputResultInRubles);

    }

    private void outputFormatted(TextField textField) {
        DecimalFormat formatTwo = new DecimalFormat("#0.00");
        double number1 = Double.valueOf(textField.getText());

        String str1 = formatTwo.format(number1);
        textField.setText(str1);
    }


}

