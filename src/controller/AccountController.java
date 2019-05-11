package controller;

import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
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
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
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

        User user = MainController.getUserPanel().getUser();

        user.setFirstName(firstNameField.getText());
        user.setLastName(lastNameField.getText());
        user.setUsername(usernameField.getText());
        user.setPassword(passwordField.getText());
        user.setEmail(emailField.getText());
        user.setPhone(phoneNumberField.getText());
        user.setAddress(shippingAddressField.getText());

        MainController.getUserPanel().updateUser(user);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Changes are saved successfully!");

        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        firstNameField.setText(MainController.getUserPanel().getUser().getFirstName());
        lastNameField.setText(MainController.getUserPanel().getUser().getLastName());
        usernameField.setText(MainController.getUserPanel().getUser().getUsername());
        passwordField.setText(MainController.getUserPanel().getUser().getPassword());
        confirmPasswordField.setText(MainController.getUserPanel().getUser().getPassword());
        emailField.setText(MainController.getUserPanel().getUser().getEmail());
        phoneNumberField.setText(MainController.getUserPanel().getUser().getPhone());
        shippingAddressField.setText(MainController.getUserPanel().getUser().getAddress());
    }
}
