package sample;

import com.assist.Trade;
import com.assist.TradeApi;
import data.PrivateData;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.List;

// Класс включает в себя разные методы с TradeAPI,
// добавляет к ним новые методы,
// и самое главное - выбрасывает окно уведомлений
// после операций с ордерами
public class VisualTrade {

    // Купить монету
    public void buy(String pair, String amount, String rate){

        try {

            //buy - true, sell - false
            Trade result = Main.tradeApi.extendedTrade(pair, true, rate, amount);

            //Если ордер создан уведомляю
            if(result.isSuccess()){
                PrivateData.id_last_order_buy = result.getOrder_id();

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

    //Продать монету
    public void sell(String pair, String amount, String rate){

        try {

            //buy - true, sell - false
            Trade result = Main.tradeApi.extendedTrade(pair, false, rate, amount);

            //Если ордер создан уведомляю
            if(result.isSuccess()){
                PrivateData.id_last_order_sell = result.getOrder_id();

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

    // Закрыть один ордер
    public void cancelOrder(String id){
        if(id != null) {
            try {
                List<String> stringList = new ArrayList<>();
                stringList.add(id);

                Main.tradeApi.cancelFewOrders(stringList);


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

    // Получить цену последней сделки на сайте
    public String lastTrade(String pair){

        String price = null;

        Main.tradeApi.ticker.addPair("ppc_usd");

        Main.tradeApi.ticker.runMethod();

        while (Main.tradeApi.ticker.hasNextPair()) {

            Main.tradeApi.ticker.switchNextPair();

            price = Main.tradeApi.ticker.getCurrentLast();

        }

        Main.tradeApi.ticker.resetParams();

        return price;
    }

    // Получить цену наилучшего предложеня продажи для покупки
    public String bestPriceSell(String pair){

        String rate = null;

        Main.tradeApi.ticker.addPair(pair);
        Main.tradeApi.ticker.setLimit(1);
        Main.tradeApi.ticker.runMethod();

        while (Main.tradeApi.ticker.hasNextPair()) {

            Main.tradeApi.ticker.switchNextPair();
            rate = Main.tradeApi.ticker.getCurrentBuy();

        }

        Main.tradeApi.ticker.resetParams();
        return rate;
    }

    // Получить цену наилучшего предложеня покупки
    public String bestPriceBuy(String pair){

        String rate = null;

        Main.tradeApi.ticker.addPair(pair);
        Main.tradeApi.ticker.setLimit(1);
        Main.tradeApi.ticker.runMethod();

        while (Main.tradeApi.ticker.hasNextPair()) {

            Main.tradeApi.ticker.switchNextPair();
            rate = Main.tradeApi.ticker.getCurrentSell();

        }

        Main.tradeApi.ticker.resetParams();
        return rate;
    }

    public String getBalance(String pair){
        String balance;

        Main.tradeApi.getInfo.runMethod();
        balance = Main.tradeApi.getInfo.getBalance(pair);
        Main.tradeApi.getInfo.resetParams();

        return balance;

    }
}
