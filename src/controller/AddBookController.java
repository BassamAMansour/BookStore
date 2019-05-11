package controller;

import entities.Author;
import entities.Book;
import entities.Publisher;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Collections;
import java.util.List;
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

        List<Author> authors = MainController.getUserPanelAsManager().getBooksFinder().getAllAuthors();
        for(Author author : authors)
            authorListBox.getItems().add(author.getAuthorName());
        Collections.sort(authorListBox.getItems());
        authorListBox.setValue(authorListBox.getItems().get(0));

        List<Publisher> publishers = MainController.getUserPanelAsManager().getBooksFinder().getAllPublishers();
        for(Publisher publisher : publishers)
            publisherListBox.getItems().add(publisher.getName());
        Collections.sort(publisherListBox.getItems());
        publisherListBox.setValue(publisherListBox.getItems().get(0));
    }

    @FXML
    private void handleAddBook(){

        int isbn = Integer.valueOf(ISBNField.getText());
        int category = Book.Category.getCategory((String) categoryListBox.getValue());
        String title = titleField.getText();
        int authorID = MainController.getUserPanelAsManager().getBooksFinder()
                .getAuthorByName((String) authorListBox.getValue()).getId();
        int publisherID = MainController.getUserPanelAsManager().getBooksFinder()
                .getPublisherByName((String) publisherListBox.getValue()).getId();
        int year = Integer.valueOf(yearField.getText());
        int price = Integer.valueOf(priceField.getText());
        int threshold = Integer.valueOf(thresholdField.getText());
        int quantity = Integer.valueOf(quantityField.getText());

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

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("The book was added successfully!");

        alert.showAndWait();
    }

    @FXML
    private void handleAddAuthor(){

    }

    @FXML
    private void handleAddPublisher(){

    }
}
