package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;
import panels.CustomerPanel;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML private StackPane paneHolder;
    @FXML private ToggleButton accountTab;
    @FXML private ToggleButton storeTab;
    @FXML private ToggleButton cartTab;
    @FXML private ToggleButton ordersTab;
    @FXML private ToggleButton promoteCustomersTab;
    @FXML private ToggleButton statisticsTab;

    public static CustomerPanel userPanel;

    private HashMap<String,ToggleButton> tabs =  new HashMap<>();

    private ToggleButton currentTab;

    public void setPane(Node node) {
        paneHolder.getChildren().setAll(node);
    }

    public void setCurrentTab(String tabID){
        currentTab = tabs.get(tabID);
        currentTab.setSelected(true);
    }


    public void navigateTab(ActionEvent event) {
        ToggleButton btn = (ToggleButton) event.getSource();
        btn.setSelected(true);
        currentTab = btn;
        String tab = btn.getText();
        System.out.println(tab);
        if(tab.equals("Account")){
            PaneNavigator.loadPane(PaneNavigator.ACCOUNT_PANE);
        }else if(tab.equals("Store")){
            PaneNavigator.loadPane(PaneNavigator.STORE_PANE);
        }else if(tab.equals("Cart")){
            PaneNavigator.loadPane(PaneNavigator.CART_PANE);
        }else if(tab.equals("Orders")){
            PaneNavigator.loadPane(PaneNavigator.ORDERS_PANE);
        }else if(tab.equals("Promote Customers")){
            PaneNavigator.loadPane(PaneNavigator.PROMOTE_CUSTOMERS_PANE);
        }else if(tab.equals("Statistics")){
            PaneNavigator.loadPane(PaneNavigator.STATISTICS_PANE);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabs.put("account",accountTab);
        tabs.put("store",storeTab);
        tabs.put("cart",cartTab);
        tabs.put("orders",ordersTab);
        tabs.put("promote",promoteCustomersTab);
        tabs.put("statistics",statisticsTab);
    }

}
