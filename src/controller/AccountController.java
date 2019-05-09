package controller;

import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import panels.CustomerPanel;
import panels.ManagerPanel;
import sun.applet.Main;
import view.App;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountController implements Initializable {

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private TextField confirmPasswordField;
    @FXML private TextField emailField;
    @FXML private TextField phoneNumberField;
    @FXML private TextField shippingAddressField;

    public AccountController(){

    }

    @FXML
    private void handleRestore() throws Exception {
        initialize(null,null);
    }

    @FXML
    private void handleSaveChanges() throws Exception{

        // Validate

        MainController.userPanel.getUser().setFirstName(firstNameField.getText());
        MainController.userPanel.getUser().setLastName(lastNameField.getText());
        MainController.userPanel.getUser().setUsername(usernameField.getText());
        MainController.userPanel.getUser().setPassword(passwordField.getText());
        MainController.userPanel.getUser().setEmail(emailField.getText());
        MainController.userPanel.getUser().setPhone(phoneNumberField.getText());
        MainController.userPanel.getUser().setAddress(shippingAddressField.getText());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        firstNameField.setText(MainController.userPanel.getUser().getFirstName());
        lastNameField.setText(MainController.userPanel.getUser().getLastName());
        usernameField.setText(MainController.userPanel.getUser().getUsername());
        emailField.setText(MainController.userPanel.getUser().getEmail());
        phoneNumberField.setText(MainController.userPanel.getUser().getPhone());
        shippingAddressField.setText(MainController.userPanel.getUser().getAddress());
    }
}
