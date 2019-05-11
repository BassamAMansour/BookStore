package controller;

import entities.Book;
import entities.User;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import sun.applet.Main;

import java.net.URL;
import java.sql.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class StoreController implements Initializable {

    public static final String ISBN_ATTRIBUTE = "ISBN";
    public static final String TITLE_ATTRIBUTE = "Title";
    public static final String CATEGORY_ATTRIBUTE = "Category";
    public static final String AUTHOR_ATTRIBUTE = "Author Name";
    public static final String PUBLISHER_ATTRIBUTE = "Publisher Name";

    @FXML private TextField searchQueryField;
    @FXML private ComboBox attributeListBox;

    private ObservableList<Book> bookData = FXCollections.observableArrayList();

    @FXML private TableView<Book> bookTable = new TableView<>();
    @FXML private TableColumn<Book, String> ISBNCol;
    @FXML private TableColumn<Book, String> titleCol;
    @FXML private TableColumn<Book, String> authorCol;
    @FXML private TableColumn<Book, String> publisherCol;
    @FXML private TableColumn<Book, String> publicationYearCol;
    @FXML private TableColumn<Book, String> categoryCol;
    @FXML private TableColumn<Book, String> priceCol;

    public StoreController(){

    }

    @FXML
    private void handleSearch() throws Exception {
        bookTable.getItems().clear();

        String attribute = (String) attributeListBox.getValue();
        String query = searchQueryField.getText();

        try{
            if(ISBN_ATTRIBUTE.equals(attribute)){
                bookData.setAll(MainController.getUserPanel().getBooksFinder().findBookByISBN(Integer.valueOf(query)));
            }else if(TITLE_ATTRIBUTE.equals(attribute)){
                bookData.setAll(MainController.getUserPanel().getBooksFinder().findBookByTitle(query));
            }else if(CATEGORY_ATTRIBUTE.equals(attribute)){
                bookData.setAll(MainController.getUserPanel().getBooksFinder().findBooksByCategory(Book.Category.getCategory(query)));
            }else if(AUTHOR_ATTRIBUTE.equals(attribute)){
                bookData.setAll(MainController.getUserPanel().getBooksFinder().findBooksByAuthor(query));
            }else if(PUBLISHER_ATTRIBUTE.equals(attribute)){
                bookData.setAll(MainController.getUserPanel().getBooksFinder().findBooksByPublisher(query));
            }
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Ooops, no result is found!");
            alert.showAndWait();
        }



        bookTable.setItems(bookData);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initializeAttributeListBox();

        ISBNCol.setCellValueFactory(cellData ->  new SimpleStringProperty(String.valueOf(cellData.getValue().getIsbn())));
        titleCol.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().getTitle()));
        authorCol.setCellValueFactory(cellData ->  new SimpleStringProperty(MainController.getUserPanel().getBooksFinder()
                .getAuthorById(cellData.getValue().getAuthorId()).getAuthorName()));
        publisherCol.setCellValueFactory(cellData ->  new SimpleStringProperty(MainController.getUserPanel().getBooksFinder()
                .getPublisherById(cellData.getValue().getPublisherId()).getName()));
        publicationYearCol.setCellValueFactory(cellData ->  new SimpleStringProperty(String.valueOf(cellData.getValue().getPublicationYear())));
        categoryCol.setCellValueFactory(cellData ->  new SimpleStringProperty(Book.Category.getCategory(cellData.getValue().getCategory())));
        priceCol.setCellValueFactory(cellData ->  new SimpleStringProperty(String.valueOf(cellData.getValue().getSellingPrice())));

        TableColumn<Book,Boolean> actionCol = new TableColumn<>("Action");
        actionCol.setSortable(false);
        actionCol.setCellValueFactory((cellData -> new SimpleBooleanProperty(cellData.getValue() != null)));
        actionCol.setCellFactory(cell -> new ButtonCell());
        bookTable.getColumns().add(actionCol);

        if(MainController.getUserPanel().getUser().getPrivilegeType()== User.PRIVILEGE_MANAGER){
            TableColumn<Book,Integer> quantityCol = new TableColumn<>("Available Quantity");
            quantityCol.setCellValueFactory(cellData ->  new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
            bookTable.getColumns().add(quantityCol);
        }

        bookTable.setItems(bookData);

        if(MainController.getUserPanel().getUser().getPrivilegeType()==User.PRIVILEGE_MANAGER)
            addEditBookFunctionality();
    }

    //Define the button cell
    private class ButtonCell extends TableCell<Book, Boolean> {
        final Button cellButton = new Button("Add to Cart");
        ButtonCell(){
            cellButton.setOnAction(t -> {
                int selectedIndex = getTableRow().getIndex();
                Book selectedBook = bookTable.getItems().get(selectedIndex);

                TextInputDialog dialog = createInputQuantityDialog();
                Optional<String> result = dialog.showAndWait();

                if (result.isPresent()){
                    int quantity = Integer.valueOf(result.get());
                    MainController.getUserPanel().getSalesManager().getCart().addBook(selectedBook,quantity);
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

    private boolean isInvalidQuantity(String quantity){
        boolean numeric = true;
        try {
            int num = Integer.parseInt(quantity);
        } catch (NumberFormatException e) {
            numeric = false;
        }
        return (quantity==null || !numeric);
    }

    private TextInputDialog createInputQuantityDialog(){
        TextInputDialog dialog = new TextInputDialog("1");
        dialog.setTitle("Add Book to the cart");
        dialog.setHeaderText(null);
        dialog.setContentText("Please enter the required quantity:");

        Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        TextField inputField = dialog.getEditor();

        BooleanBinding isInvalid = Bindings.createBooleanBinding(() -> isInvalidQuantity(inputField.getText()), inputField.textProperty());
        okButton.disableProperty().bind(isInvalid);
        return dialog;
    }

    private void initializeAttributeListBox(){
        attributeListBox.getItems().addAll(ISBN_ATTRIBUTE,TITLE_ATTRIBUTE,CATEGORY_ATTRIBUTE,
                AUTHOR_ATTRIBUTE,PUBLISHER_ATTRIBUTE);
        attributeListBox.setValue(attributeListBox.getItems().get(0));
    }

    private void addEditBookFunctionality(){

        authorCol.setCellFactory(TextFieldTableCell.forTableColumn());
        authorCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Book, String> t) -> {
                    Book book = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    book.setAuthorId(MainController.getUserPanelAsManager().getBooksFinder()
                            .getAuthorById(Integer.valueOf(t.getNewValue())).getId());
                    MainController.getUserPanelAsManager().getStoreManager().updateBook(book);
                });

        publisherCol.setCellFactory(TextFieldTableCell.forTableColumn());
        publisherCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Book, String> t) -> {
                    Book book = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    book.setPublisherId(MainController.getUserPanelAsManager().getBooksFinder()
                            .getPublisherById(Integer.valueOf(t.getNewValue())).getId());
                    MainController.getUserPanelAsManager().getStoreManager().updateBook(book);
                });

        publicationYearCol.setCellFactory(TextFieldTableCell.forTableColumn());
        publicationYearCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Book, String> t) -> {
                    Book book = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    book.setPublicationYear(Integer.valueOf(t.getNewValue()));
                    MainController.getUserPanelAsManager().getStoreManager().updateBook(book);
                });

        priceCol.setCellFactory(TextFieldTableCell.forTableColumn());
        priceCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Book, String> t) -> {
                    Book book = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    book.setSellingPrice(Integer.valueOf(t.getNewValue()));
                    MainController.getUserPanelAsManager().getStoreManager().updateBook(book);
                });

        categoryCol.setCellFactory(TextFieldTableCell.forTableColumn());
        categoryCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Book, String> t) -> {
                    Book book = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    book.setCategory(Book.Category.getCategory(t.getNewValue()));
                    MainController.getUserPanelAsManager().getStoreManager().updateBook(book);
                });

    }
}
