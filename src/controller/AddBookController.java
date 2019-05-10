package controller;

import entities.Book;
import entities.Publisher;
import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sun.applet.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class AddBookController implements Initializable {

    @FXML private TextField ISBNField;
    @FXML private ComboBox categoryListBox;
    @FXML private TextField titleField;
    @FXML private ComboBox authorListBox;
    @FXML private ComboBox publisherListBox;
    @FXML private TextField yearField;
    @FXML private TextField priceField;
    @FXML private TextField thresholdField;
    @FXML private TextField quantityField;

    public AddBookController(){

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categoryListBox.getItems().add(Book.Category.getCategory(Book.Category.SCIENCE));
        categoryListBox.getItems().add(Book.Category.getCategory(Book.Category.ART));
        categoryListBox.getItems().add(Book.Category.getCategory(Book.Category.RELIGION));
        categoryListBox.getItems().add(Book.Category.getCategory(Book.Category.HISTORY));
        categoryListBox.getItems().add(Book.Category.getCategory(Book.Category.GEOGRAPHY));

        // Add all Authors

        // Add all Publishers
    }

    @FXML
    private void handleAddBook(){

        int isbn = Integer.valueOf(ISBNField.getText());
        int category = Book.Category.getCategory((String) categoryListBox.getValue());
        String title = titleField.getText();
        // TODO get authorID by its name
        int authorID = Integer.valueOf((String) authorListBox.getValue());
        // TODO get publisherID by its name
        int publisherID = Integer.valueOf((String) publisherListBox.getValue());
        int year = Integer.valueOf(yearField.getText());
        int price = Integer.valueOf(priceField.getText());
        int threshold = Integer.valueOf(thresholdField.getText());
        int quantity = Integer.valueOf(quantityField.getText());

        // Validate

        Book newBook = new Book();
        newBook.setIsbn(isbn);
        newBook.setCategory(category);
        newBook.setTitle(title);
        newBook.setAuthorId(authorID);
        newBook.setPublisherId(publisherID);
        newBook.setPublicationYear(year);
        newBook.setSellingPrice(price);
        newBook.setThreshold(threshold);
        newBook.setQuantity(quantity);
        newBook.setAddedBy(MainController.getUserPanel().getUser().getId());

        MainController.getUserPanelAsManager().getStoreManager().addBook(newBook);

        // Success Alert
    }

    @FXML
    private void handleAddAuthor(){

    }

    @FXML
    private void handleAddPublisher(){

    }
}
