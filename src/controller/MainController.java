package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;
import panels.CustomerPanel;
import panels.ManagerPanel;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public static final String ACCOUNT_TAB = "Account";
    public static final String STORE_TAB = "Store";
    public static final String CART_TAB = "Cart";
    public static final String ORDERS_TAB = "Orders";
    public static final String PROMOTE_TAB = "Promote Customers";
    public static final String STATISTICS_TAB = "Statistics";

    @FXML private StackPane paneHolder;
    @FXML private ToggleButton accountTab;
    @FXML private ToggleButton storeTab;
    @FXML private ToggleButton cartTab;
    @FXML private ToggleButton ordersTab;
    @FXML private ToggleButton promoteCustomersTab;
    @FXML private ToggleButton statisticsTab;

    private static CustomerPanel userPanel;

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
        if(ACCOUNT_TAB.equals(tab)){
            PaneNavigator.loadPane(PaneNavigator.ACCOUNT_PANE);
        }else if(STORE_TAB.equals(tab)){
            PaneNavigator.loadPane(PaneNavigator.STORE_PANE);
        }else if(CART_TAB.equals(tab)){
            PaneNavigator.loadPane(PaneNavigator.CART_PANE);
        }else if(ORDERS_TAB.equals(tab)){
            PaneNavigator.loadPane(PaneNavigator.ORDERS_PANE);
        }else if(PROMOTE_TAB.equals(tab)){
            PaneNavigator.loadPane(PaneNavigator.PROMOTE_CUSTOMERS_PANE);
        }else if(STATISTICS_TAB.equals(tab)){
            PaneNavigator.loadPane(PaneNavigator.STATISTICS_PANE);
        }
    }

    public static ManagerPanel getUserPanelAsManager(){
        return (ManagerPanel) userPanel;
    }

    public static CustomerPanel getUserPanel(){
        return userPanel;
    }

    public static void setUserPanel(CustomerPanel panel){
        userPanel = panel;
    }

    public static void setUserPanel(ManagerPanel panel){
        userPanel = panel;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabs.put(PaneNavigator.ACCOUNT_PANE,accountTab);
        tabs.put(PaneNavigator.STORE_PANE,storeTab);
        tabs.put(PaneNavigator.CART_PANE,cartTab);
        tabs.put(PaneNavigator.ORDERS_PANE,ordersTab);
        tabs.put(PaneNavigator.PROMOTE_CUSTOMERS_PANE,promoteCustomersTab);
        tabs.put(PaneNavigator.STATISTICS_PANE,statisticsTab);
    }

}
