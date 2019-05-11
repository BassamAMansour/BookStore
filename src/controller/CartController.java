package controller;

import entities.Book;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.ResourceBundle;

public class CartController implements Initializable {

    private ObservableList<Book> cartData = FXCollections.observableArrayList();

    @FXML private TableView<Book> cartTable = new TableView<>();
    @FXML private TableColumn<Book, String> bookCol;
    @FXML private TableColumn<Book, Integer> priceCol;
    @FXML private TableColumn<Book, Integer> quantityCol;
    private TableColumn<Book,Boolean> actionCol = new TableColumn<>("Action");

    @FXML private Label totalPriceLabel;


    public CartController(){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        bookCol.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().getTitle()));
        priceCol.setCellValueFactory(cellData ->  new SimpleIntegerProperty(cellData.getValue().getSellingPrice()).asObject());
        quantityCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(MainController.getUserPanel().getSalesManager()
                .getCart().getBookQuantity(cellData.getValue().getIsbn())).asObject());


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
        Dialog<Pair<String, String>> dialog = createInputCreditCardDialog();
        Optional<Pair<String, String>> result = dialog.showAndWait();

        if (result.isPresent()){
             if( isValid(result)){
                 String cardNum = result.get().getKey();
                 String expiryDate = result.get().getValue();
                 SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
                 java.util.Date date = sdf1.parse(expiryDate);
                 java.sql.Date sqlExpiryDate = new java.sql.Date(date.getTime());
                 MainController.getUserPanel().getSalesManager().confirmSale(cardNum, sqlExpiryDate);
                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
                 alert.setTitle("Information");
                 alert.setHeaderText(null);
                 alert.setContentText("Checkout is done successfully!");

                 alert.showAndWait();
                 cartTable.getItems().clear();
                 cartTable.refresh();
             }else{
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Error");
                 alert.setHeaderText(null);
                 alert.setContentText("Ooops, the data entered is not valid!");
                 alert.showAndWait();
             }

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
                    Book book = cartTable.getItems().get(selectedIndex);
                    MainController.getUserPanel().getSalesManager().getCart().removeBook(book);
                    cartTable.getItems().remove(selectedIndex);
                    updateTotalPriceLabel();
                    cartTable.refresh();
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

    private Dialog<Pair<String, String>> createInputCreditCardDialog(){

        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Checkout");
        dialog.setHeaderText(null);

        // Set the button types.
        ButtonType checkoutButtonType = new ButtonType("Checkout!", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(checkoutButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField cardNumberField = new TextField();
        cardNumberField.setPromptText("16-digit card number");
        TextField expiryDateField = new TextField();
        expiryDateField.setPromptText("dd-MM-yyyy");

        grid.add(new Label("Card Number:"), 0, 0);
        grid.add(cardNumberField, 1, 0);
        grid.add(new Label("Expiry Date:"), 0, 1);
        grid.add(expiryDateField, 1, 1);

        Node checkoutButton = dialog.getDialogPane().lookupButton(checkoutButtonType);
        checkoutButton.setDisable(true);

        cardNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            checkoutButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == checkoutButtonType) {
                return new Pair<>(cardNumberField.getText(), expiryDateField.getText());
            }
            return null;
        });

        return dialog;
    }

    private boolean isValid(Optional<Pair<String, String>> input){

        boolean cardNumValid = true;
        boolean expiryDateVaild = true;

        String cardNum = input.get().getKey();
        if(cardNum.length()!=16)    cardNumValid = false;
        try {
            long num = Long.parseUnsignedLong(cardNum);
        } catch (NumberFormatException e) {
            cardNumValid = false;
        }

        String expiryDate = input.get().getValue();
        String[] fields = expiryDate.split("-");
        if(fields.length != 3)  expiryDateVaild = false;
        try {
            if(!(Integer.valueOf(fields[0]) >= 1 && Integer.valueOf(fields[0]) <= 31))
                expiryDateVaild = false;
            if(!(Integer.valueOf(fields[1]) >= 1 && Integer.valueOf(fields[1]) <= 12))
                expiryDateVaild = false;
            if(!(Integer.valueOf(fields[2]) >= 2019 && Integer.valueOf(fields[1]) <= 2100))
                expiryDateVaild = false;
        } catch (NumberFormatException e) {
            expiryDateVaild = false;
        }

        System.out.println(cardNumValid);
        System.out.println(expiryDateVaild);
        return cardNumValid && expiryDateVaild;
    }

}
