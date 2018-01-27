package sample;


import com.assist.CancelOrder;
import com.assist.Trade;
import com.assist.TradeApi;
import data.PrivateData;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML private ComboBox choice_pair_buy;
    @FXML private TextField amount_buy;
    @FXML private TextField my_rate_buy;

    @FXML private ComboBox choice_pair_sell;
    @FXML private TextField amount_sell;
    @FXML private TextField my_rate_sell;

    //Для хранения id идентификаторов последних ордеров.
    private String id_last_order_buy;
    private String id_last_order_sell;

    // Формирует ордер покупки монеты
    // по цене указаной в поле my_rate_buy, и кол-ву в my_amount_buy
    public void buyAtMyPrice(){

        String pair = choice_pair_buy.getValue().toString();
        String amount = amount_buy.getText();
        String rate = my_rate_buy.getText();

        try {

            //buy - true, sell - false
            Trade result = Main.tradeApi.extendedTrade(pair, true, rate, amount);

            //Если ордер создан уведомляю
            if(result.isSuccess()){
                id_last_order_buy = result.getOrder_id();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Всё прошло хорошо!!");
                alert.setHeaderText(null);
                alert.setContentText("    Ордер создан");

                alert.showAndWait();

            } else {

                Alert alert = new Alert(Alert.AlertType.WARNING);

                alert.setTitle("Что то не так");
                alert.setHeaderText(null);
                alert.setContentText("   Ордер не создан \r\n" + result.toString().substring(13));


                alert.showAndWait();
            }

        }catch (Exception e){

            Alert alert = new Alert(Alert.AlertType.WARNING);

            alert.setTitle("Что то не так");
            alert.setHeaderText(null);
            alert.setContentText("   Ордер не создан - ошибка клиента\n" + e.getMessage());

            alert.showAndWait();
        }
    }

    public void sellAtMyPrice(){

        String pair = choice_pair_sell.getValue().toString();
        String amount = amount_sell.getText();
        String rate = my_rate_sell.getText();

        try {

            //buy - true, sell - false
            Trade result = Main.tradeApi.extendedTrade(pair, false, rate, amount);

            //Если ордер создан уведомляю
            if(result.isSuccess()){
                id_last_order_sell = result.getOrder_id();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Всё прошло хорошо!!");
                alert.setHeaderText(null);
                alert.setContentText("    Ордер создан");

                alert.showAndWait();

            } else {

                Alert alert = new Alert(Alert.AlertType.WARNING);

                alert.setTitle("Что то не так");
                alert.setHeaderText(null);
                alert.setContentText("   Ордер не создан \r\n" + result.toString().substring(13));


                alert.showAndWait();
            }

        }catch (Exception e){

            Alert alert = new Alert(Alert.AlertType.WARNING);

            alert.setTitle("Что то не так");
            alert.setHeaderText(null);
            alert.setContentText("   Ордер не создан - ошибка клиента\n" + e.getMessage());
            alert.setWidth(400);

            alert.showAndWait();
        }
    }

    public void cancelLastBuyOrder(){
        //Если уже имеется id крайнего ордера, закрою его
        if(id_last_order_buy != null) {
            try {
                List<String> stringList = new ArrayList<>();
                stringList.add(id_last_order_buy);

                Main.tradeApi.cancelFewOrders(stringList);

                //Сбрасываю id крайнего ордера в памяти программы на null,
                //что бы не путатся
                id_last_order_buy = null;

                //Традиционное окно о удачном закрытии ордера

                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Всё прошло хорошо!!");
                alert.setHeaderText(null);
                alert.setContentText("    Ордер закрыт");

                alert.showAndWait();


            } catch (Exception e) {
                e.printStackTrace();

                Alert alert = new Alert(Alert.AlertType.WARNING);

                alert.setTitle("Что то не так");
                alert.setHeaderText(null);
                alert.setContentText("   Ордер не закрыт - ошибка клиента\n" + e.getMessage());

                alert.showAndWait();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Что то не так");
            alert.setHeaderText(null);
            alert.setContentText("В памяти программы нет открытых ордеров");

            alert.showAndWait();
        }
    }

    public void cancelLastSellOrder(){
        //Если уже имеется id крайнего ордера, закрою его
        if(id_last_order_sell != null) {
            try {
                List<String> stringList = new ArrayList<>();
                stringList.add(id_last_order_sell);

                Main.tradeApi.cancelFewOrders(stringList);

                //Сбрасываю id крайнего ордера в памяти программы на null,
                //что бы не путатся
                id_last_order_sell = null;

                //Традиционное окно о удачном закрытии ордера

                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Всё прошло хорошо!!");
                alert.setHeaderText(null);
                alert.setContentText("    Ордер закрыт");

                alert.showAndWait();


            } catch (Exception e) {
                e.printStackTrace();

                Alert alert = new Alert(Alert.AlertType.WARNING);

                alert.setTitle("Что то не так");
                alert.setHeaderText(null);
                alert.setContentText("   Ордер не закрыт - ошибка клиента\n" + e.getMessage());

                alert.showAndWait();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Что то не так");
            alert.setHeaderText(null);
            alert.setContentText("В памяти программы нет открытых ордеров");

            alert.showAndWait();
        }

    }

    // Устанавливает в поле "Продажа -> количество" все монеты имеющиеся на балансе
    public void setFullAmountSell(){
        String pair = choice_pair_sell.getValue().toString();

        //Обрезаю всё после первых трёх символов
        pair = pair.substring(0, 3);

        Main.tradeApi.getInfo.runMethod();

        String balance = Main.tradeApi.getInfo.getBalance(pair);

        System.out.println(pair + balance);

        amount_sell.setText(balance);
    }

}
