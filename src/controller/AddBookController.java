package controller;

import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddBookController implements Initializable {

    @FXML private TextField ISBNField;
    @FXML private ComboBox categoryListBox;
    @FXML private TextField titleField;
    @FXML private TextField authorField;
    @FXML private ComboBox publisherListBox;
    @FXML private TextField yearField;
    @FXML private TextField priceField;
    @FXML private TextField thresholdField;
    @FXML private TextField quantityField;

    public AddBookController(){

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
