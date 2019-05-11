package controller;

import entities.Book;
import entities.Order;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class OrdersController implements Initializable {

    private ObservableList<Order> orderData = FXCollections.observableArrayList();

    @FXML private TableView<Order> orderTable = new TableView<>();
    @FXML private TableColumn<Order, String> bookCol;
    @FXML private TableColumn<Order, String> publisherCol;
    @FXML private TableColumn<Order, Integer> orderedQuantityCol;

    @FXML private ComboBox attributeListBox;
    @FXML private TextField queryField;
    @FXML private TextField quantityField;

    public OrdersController(){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        bookCol.setCellValueFactory(cellData ->  new SimpleStringProperty(MainController.getUserPanelAsManager().getBooksFinder().
                findBookByISBN(cellData.getValue().getBookId()).getTitle()));
        publisherCol.setCellValueFactory(cellData ->  new SimpleStringProperty(MainController.getUserPanelAsManager().getBooksFinder()
                .getPublisherById(MainController.getUserPanelAsManager().getBooksFinder()
                        .findBookByISBN(cellData.getValue().getBookId()).getPublisherId()).getName()));
        orderedQuantityCol.setCellValueFactory(cellData ->  new SimpleIntegerProperty(
                cellData.getValue().getOrderedQuantity()).asObject());

        TableColumn<Order,Boolean> actionCol = new TableColumn<>("Action");
        actionCol.setSortable(false);
        actionCol.setCellValueFactory((cellData -> new SimpleBooleanProperty(cellData.getValue() != null)));
        actionCol.setCellFactory(cell -> new ButtonCell());
        orderTable.getColumns().add(actionCol);

        updateOrderTable();

        attributeListBox.getItems().addAll(StoreController.ISBN_ATTRIBUTE,StoreController.TITLE_ATTRIBUTE);
        attributeListBox.setValue(attributeListBox.getItems().get(0));
    }

    @FXML
    private void handleConfirmAll() throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Are you sure you want to confirm all orders?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            for(Order order: orderData){
                MainController.getUserPanelAsManager().getStoreManager().confirmOrder(order.getId());
            }
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
                    MainController.getUserPanelAsManager().getStoreManager().confirmOrder(selectedOrder.getId());
                    orderTable.getItems().remove(selectedIndex);
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
        String attribute = (String) attributeListBox.getValue();
        String query = queryField.getText();
        int quantity = Integer.valueOf(quantityField.getText());
        Book book = new Book();
        if(StoreController.ISBN_ATTRIBUTE.equals(attribute)){
            book = MainController.getUserPanelAsManager().getBooksFinder().findBookByISBN(Integer.valueOf(query));
        }else if(StoreController.TITLE_ATTRIBUTE.equals(attribute)){
            book = MainController.getUserPanelAsManager().getBooksFinder().findBookByTitle(query);
        }
        Order order = new Order();
        order.setBookId(book.getIsbn());
        order.setOrderedQuantity(quantity);
        MainController.getUserPanelAsManager().getStoreManager().addOrder(order);
        System.out.println("Success");
        updateOrderTable();
    }

    private void updateOrderTable(){
        orderData.setAll(MainController.getUserPanelAsManager().getStoreManager().getAllOrders());
        orderTable.setItems(orderData);
    }

}
