package controller;

import entities.Book;
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

public class CartController implements Initializable {

    private ObservableList<Book> cartData = FXCollections.observableArrayList();

    @FXML private TableView<Book> cartTable = new TableView<>();
    @FXML private TableColumn<Book, String> bookCol;
    @FXML private TableColumn<Book, Integer> priceCol;
    @FXML private TableColumn<Book, Integer> quantityCol;

    @FXML private Label totalPriceLabel;

    public CartController(){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        bookCol.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().getTitle()));
        priceCol.setCellValueFactory(cellData ->  new SimpleIntegerProperty(cellData.getValue().getSellingPrice()).asObject());
        quantityCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(MainController.getUserPanel().getSalesManager()
                .getCart().getBookQuantity(cellData.getValue().getIsbn())).asObject());

        TableColumn<Book,Boolean> actionCol = new TableColumn<>("Action");
        actionCol.setSortable(false);
        actionCol.setCellValueFactory((cellData -> new SimpleBooleanProperty(cellData.getValue() != null)));
        actionCol.setCellFactory(cell -> new ButtonCell());
        cartTable.getColumns().add(actionCol);

        cartData.setAll(MainController.getUserPanel().getSalesManager().getCart().getBooks());
        cartTable.setItems(cartData);

        updateTotalPriceLabel();
    }

    @FXML
    private void handleCheckout() throws Exception {
        TextInputDialog dialog = createInputCreditCardDialog();
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()){
            int creditCard = Integer.valueOf(result.get());
            MainController.getUserPanel().getSalesManager().confirmSale(String.valueOf(creditCard), new Date(2020,6,30));
            // TODO: clear all items in cart
        }
    }


    //Define the button cell
    private class ButtonCell extends TableCell<Book, Boolean> {
        final Button cellButton = new Button("Remove from Cart");
        ButtonCell(){
            cellButton.setOnAction(t -> {
                int selectedIndex = getTableRow().getIndex();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setContentText("Are you sure you want to remove this item from the cart?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    cartTable.getItems().remove(selectedIndex);
                    updateTotalPriceLabel();
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

    private void updateTotalPriceLabel(){
        totalPriceLabel.setText(String.valueOf(MainController.getUserPanel().getSalesManager().getCart().getTotalPrice()));
    }

    private boolean isInvalidCreditCard(String quantity){
        boolean numeric = true;
        try {
            int num = Integer.parseInt(quantity);
        } catch (NumberFormatException e) {
            numeric = false;
        }
        return (quantity==null || !numeric);
    }

    private TextInputDialog createInputCreditCardDialog(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Checkout");
        dialog.setHeaderText(null);
        dialog.setContentText("Please enter your credit card number:");

        Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        TextField inputField = dialog.getEditor();

        BooleanBinding isInvalid = Bindings.createBooleanBinding(() -> isInvalidCreditCard(inputField.getText()), inputField.textProperty());
        okButton.disableProperty().bind(isInvalid);
        return dialog;
    }

}
