package controller;

import view.App;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;

public class PaneNavigator {

    public static final String MAIN_PANE   = "main.fxml";
    public static final String ACCOUNT_PANE   = "account.fxml";
    public static final String STORE_PANE = "store.fxml";
    public static final String CART_PANE = "cart.fxml";
    public static final String ORDERS_PANE = "orders.fxml";
    public static final String STATISTICS_PANE = "statistics.fxml";
    public static final String PROMOTE_CUSTOMERS_PANE = "promote.fxml";

    private static MainController mainController;

    public static void setMainController(MainController mainController) {
        PaneNavigator.mainController = mainController;
    }

    public static void loadPane(String fxml) {
        try {
            String[] dir = fxml.split("\\.");
            String tabID = dir[dir.length - 2];
            mainController.setCurrentTab(tabID);
            mainController.setPane(FXMLLoader.load( new URL("file:resources/fxml/" + fxml)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}