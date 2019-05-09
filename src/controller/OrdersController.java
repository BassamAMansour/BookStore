package controller;

import entities.Book;
import entities.Order;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sun.applet.Main;

import java.net.URL;
import java.sql.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class OrdersController implements Initializable {

    private ObservableList<Order> orderData = FXCollections.observableArrayList();

    @FXML private TableView<Order> orderTable = new TableView<>();
    @FXML private TableColumn<Order, String> bookCol;
    @FXML private TableColumn<Order, Integer> publisherCol;
    @FXML private TableColumn<Order, Integer> orderedQuantityCol;

    @FXML private ComboBox attributeListBox;
    @FXML private TextField queryField;
    @FXML private TextField quantityField;

    public OrdersController(){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // TODO: add findBookbyId()
        bookCol.setCellValueFactory(cellData ->  new SimpleStringProperty(String.valueOf(cellData.getValue().getBookId())));
        publisherCol.setCellValueFactory(cellData ->  new SimpleIntegerProperty(
                cellData.getValue().getOrderedQuantity()).asObject());
        orderedQuantityCol.setCellValueFactory(cellData ->  new SimpleIntegerProperty(
                cellData.getValue().getOrderedQuantity()).asObject());

        TableColumn<Order,Boolean> actionCol = new TableColumn<>("Action");
        actionCol.setSortable(false);
        actionCol.setCellValueFactory((cellData -> new SimpleBooleanProperty(cellData.getValue() != null)));
        actionCol.setCellFactory(cell -> new ButtonCell());
        orderTable.getColumns().add(actionCol);

        // TODO: add getOrders()
        orderData.setAll(new Order());
        orderTable.setItems(orderData);

        attributeListBox.getItems().addAll("ISBN","Title");
    }

    @FXML
    private void handleConfirmAll() throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Are you sure you want to confirm all orders?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // TODO: Confrim All
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }

    //Define the button cell
    private class ButtonCell extends TableCell<Order, Boolean> {
        final Button cellButton = new Button("Confirm Order");
        ButtonCell(){
            cellButton.setOnAction(t -> {
                int selectedIndex = getTableRow().getIndex();
                Order selectedOrder = orderTable.getItems().get(selectedIndex);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setContentText("Are you sure you want to confirm this order?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    MainController.userPanel.getStoreManager().confirmOrder(selectedOrder.getId());
                    //orderTable.getItems().remove(selectedIndex);
                } else {
                    // ... user chose CANCEL or closed the dialog
                }
            });
        }

        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(cellButton);
            }
        }
    }

    @FXML
    private void handlePlaceOrder() {
        // add place order functionality
    }

}
