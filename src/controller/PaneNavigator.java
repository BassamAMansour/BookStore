package controller;

import view.App;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;

public class PaneNavigator {

    public static final String MAIN_PANE   = "main";
    public static final String ACCOUNT_PANE   = "account";
    public static final String STORE_PANE = "store";
    public static final String CART_PANE = "cart";
    public static final String ORDERS_PANE = "orders";
    public static final String STATISTICS_PANE = "statistics";
    public static final String PROMOTE_CUSTOMERS_PANE = "promote";

    private static MainController mainController;

    public static void setMainController(MainController mainController) {
        PaneNavigator.mainController = mainController;
    }

    public static void loadPane(String fxml) {
        try {
            mainController.setCurrentTab(fxml);
            mainController.setPane(FXMLLoader.load( new URL("file:resources/fxml/" + fxml + ".fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}