package controller;

import entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import panels.CustomerPanel;
import view.App;

public class SignupController {

    @FXML private TextField usernameField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextField passwordField;
    @FXML private TextField confirmPasswordField;
    @FXML private TextField phoneNumberField;
    @FXML private TextField shippingAddressField;

    public SignupController(){

    }

    @FXML
    private void login() throws Exception {
        App.getPrimaryStage().setScene(App.loadLoginScene());
    }

    @FXML
    private void signup() throws Exception{
        String username = usernameField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String phoneNumber = phoneNumberField.getText();
        String shippingAddress = shippingAddressField.getText();

        // Validate data

        User user = new User();
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phoneNumber);
        user.setAddress(shippingAddress);
        user.setPrivilegeType(User.PRIVILEGE_CUSTOMER);

        MainController.userPanel = CustomerPanel.fromNewUser(user);

        App.getPrimaryStage().setScene(App.loadMainScene());
    }
}
