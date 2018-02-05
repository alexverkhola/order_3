package sample;



import data.PrivateData;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;


public class Controller {

    @FXML private ComboBox choice_pair_buy;
    @FXML private TextField amount_buy;
    @FXML private TextField my_rate_buy;

    @FXML private ComboBox choice_pair_sell;
    @FXML private TextField amount_sell;
    @FXML private TextField my_rate_sell;

    @FXML private ComboBox choice_percent;


    // Формирует ордер покупки монеты
    // по цене указаной в поле my_rate_buy, и кол-ву в my_amount_buy
    public void buyAtMyPrice(){

        String pair = choice_pair_buy.getValue().toString();
        String amount = amount_buy.getText();
        String rate = my_rate_buy.getText();

        VisualTrade visualTrade = new VisualTrade();
        visualTrade.buy(pair, amount, rate);


    }

    public void sellAtMyPrice(){

        String pair = choice_pair_sell.getValue().toString();
        String amount = amount_sell.getText();
        String rate = my_rate_sell.getText();

        VisualTrade visualTrade = new VisualTrade();
        visualTrade.sell(pair, amount, rate);

    }

    //Закрывает последний ордер покупки
    public void cancelLastBuyOrder(){


            VisualTrade visualTrade = new VisualTrade();
            visualTrade.cancelOrder(PrivateData.id_last_order_buy);

            //Сбрасываю id крайнего ордера в памяти программы на null,
            //что бы не путатся
            PrivateData.id_last_order_buy = null;
    }

    // Закрывает последний ордер продажи
    public void cancelLastSellOrder(){

        VisualTrade visualTrade = new VisualTrade();
        visualTrade.cancelOrder(PrivateData.id_last_order_sell);

        //Сбрасываю id крайнего ордера в памяти программы на null,
        //что бы не путатся
        PrivateData.id_last_order_sell = null;
    }

    //Установить в поле "Покупка => кол-во" кол-во монет на которые хватит ресурсов на
    //балансе
    public void setFullAmountBuy(){
        String pair = choice_pair_buy.getValue().toString();
        String balance;

        //Обрезаю все символы, оставляю только знак второй пары
        pair = pair.substring(4);

        VisualTrade visualTrade = new VisualTrade();
        balance = visualTrade.getBalance(pair);

        double value = Double.parseDouble(balance);
        double rate = Double.parseDouble(my_rate_buy.getText());

        double amount = value / rate;

        amount_buy.setText(Double.toString(amount));

    }

    // Устанавливает в поле "Продажа -> количество" все монеты имеющиеся на балансе
    public void setFullAmountSell(){
        String pair = choice_pair_sell.getValue().toString();
        String balance;

        //Обрезаю всё после первых трёх символов
        pair = pair.substring(0, 3);

        VisualTrade visualTrade = new VisualTrade();
        balance = visualTrade.getBalance(pair);

        amount_sell.setText(balance);
    }

    // установить цену на покупку по цене
    // самого дешёвого ордера продажи
    public void buyAtBestOrder(){
        String pair = choice_pair_buy.getValue().toString();
        String rate;

        VisualTrade visualTrade = new VisualTrade();
        rate = visualTrade.bestPriceSell(pair);

        my_rate_buy.setText(rate);

    }

    //Установить цену на покупку по текущей цене на графике -
    // по цене последней сделки
    public void buyAtLastTrade(){

        String pair = choice_pair_buy.getValue().toString();
        String lastPrice;

        VisualTrade visualTrade = new VisualTrade();
        lastPrice = visualTrade.lastTrade(pair);

        my_rate_buy.setText(lastPrice);
    }

    //Установить цену продажи по текущей цене на графике -
    // по цене последней сделки
    public void sellAtLastTrade(){

        String pair = choice_pair_sell.getValue().toString();
        String lastPrice;

        VisualTrade visualTrade = new VisualTrade();
        lastPrice = visualTrade.lastTrade(pair);

        my_rate_sell.setText(lastPrice);
    }

    //Выводит наибольшую цену покупки ( из доступных ордеров на покупку )
    public void sellAtBestOrder(){
        String pair = choice_pair_sell.getValue().toString();
        VisualTrade visualTrade = new VisualTrade();
        String rate = visualTrade.bestPriceBuy(pair);

        my_rate_sell.setText(rate);
    }

    // Устанавливает цену продажи монеты по цене крайнего ордера моей покупки + проценты
    public void setMyPriceWithPercent(){

        //Получаю процентную надбавку
        double percent = Double.parseDouble(choice_percent.getValue().toString());

        //Получаю цену из поля цены покупки
        double previous_price =Double.parseDouble(my_rate_buy.getText());

        double price = previous_price + (previous_price / 100 * percent);

        my_rate_sell.setText(Double.toString(price));
    }

}
