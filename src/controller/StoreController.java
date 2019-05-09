package controller;

import entities.Book;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class StoreController implements Initializable {

    @FXML private TextField searchQueryField;
    @FXML private ComboBox attributeListBox;

    private ObservableList<Book> bookData = FXCollections.observableArrayList();

    @FXML private TableView<Book> bookTable = new TableView<>();
    @FXML private TableColumn<Book, Integer> ISBNCol;
    @FXML private TableColumn<Book, String> titleCol;
    @FXML private TableColumn<Book, Integer> authorsCol;
    @FXML private TableColumn<Book, Integer> publisherCol;
    @FXML private TableColumn<Book, Integer> publicationYearCol;
    @FXML private TableColumn<Book, Integer> categoryCol;
    @FXML private TableColumn<Book, Integer> priceCol;

    public StoreController(){

    }

    @FXML
    private void handleSearch() throws Exception {

    }

    @FXML
    private void handleRemoveBook() throws Exception {

    }

    @FXML
    private void handleAddBook() throws Exception {

    }

    @FXML
    private void handleAddToCart() throws Exception {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        attributeListBox.getItems().add("ISBN");
        attributeListBox.getItems().add("Title");
        attributeListBox.getItems().add("Category");
        attributeListBox.getItems().add("Author");
        attributeListBox.getItems().add("Publisher");
        attributeListBox.setValue("Title");

        ISBNCol.setCellValueFactory(cellData ->  new SimpleIntegerProperty(cellData.getValue().getIsbn()).asObject());
        titleCol.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().getTitle()));
        authorsCol.setCellValueFactory(cellData ->  new SimpleIntegerProperty(cellData.getValue().getAuthorId()).asObject());
        publisherCol.setCellValueFactory(cellData ->  new SimpleIntegerProperty(cellData.getValue().getPublisherId()).asObject());
        publicationYearCol.setCellValueFactory(cellData ->  new SimpleIntegerProperty(cellData.getValue().getPublicationYear()).asObject());
        categoryCol.setCellValueFactory(cellData ->  new SimpleIntegerProperty(cellData.getValue().getCategory()).asObject());
        priceCol.setCellValueFactory(cellData ->  new SimpleIntegerProperty(cellData.getValue().getSellingPrice()).asObject());
    }
}
