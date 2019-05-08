package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import panels.CustomerPanel;
import view.App;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private TextField passwordField;

    public LoginController(){

    }

    @FXML
    private void login() throws Exception {
        String userbame = usernameField.getText();
        String password = passwordField.getText();
        //MainController.userPanel = CustomerPanel.fromCredentials(username, password);
        App.getPrimaryStage().setScene(App.loadMainScene());
    }

    @FXML
    private void signup() throws Exception{
        App.getPrimaryStage().setScene(App.loadSignupScene());
    }
}
