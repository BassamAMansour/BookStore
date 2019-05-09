package controller;

import entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import panels.CustomerPanel;
import panels.ManagerPanel;
import view.App;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private TextField passwordField;

    public LoginController(){

    }

    @FXML
    private void login() throws Exception {
        String username = usernameField.getText();
        String password = passwordField.getText();
        CustomerPanel userPanel = CustomerPanel.fromCredentials(username,password);
        if(userPanel.getUser().getPrivilegeType()== User.PRIVILEGE_MANAGER){
            userPanel = ManagerPanel.fromManager(userPanel.getUser());
        }
        MainController.userPanel = userPanel;
        App.getPrimaryStage().setScene(App.loadMainScene());
    }

    @FXML
    private void signup() throws Exception{
        App.getPrimaryStage().setScene(App.loadSignupScene());
    }
}
