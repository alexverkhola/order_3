package sample;


import com.assist.TradeApi;
import data.PrivateData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static TradeApi tradeApi;


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Order v0.0.3");
        primaryStage.setScene(new Scene(root, 520, 550));
        primaryStage.show();


    }


    public static void main(String[] args) {
        String aKey = PrivateData.aKey;
        String aSecret = PrivateData.aSecret;

        //Я считаю что достаточно один раз пройти аутентификацию
        // в начале работы программы, а не вызывать
        //её каждый раз в новом методе Контроллера
        try {
            tradeApi = new TradeApi(aKey, aSecret);
        } catch (Exception e) {
            e.printStackTrace();
        }
        launch(args);
    }
}
