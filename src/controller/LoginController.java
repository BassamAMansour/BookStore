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

        CustomerPanel customerPanel = CustomerPanel.fromCredentials(username,password);
        if(customerPanel.getUser().getPrivilegeType()== User.PRIVILEGE_MANAGER){
            MainController.userPanel = ManagerPanel.fromManager(customerPanel.getUser());
        }else{
            MainController.userPanel = (ManagerPanel) customerPanel;
        }

        App.getPrimaryStage().setScene(App.loadMainScene());
    }

    @FXML
    private void signup() throws Exception{
        App.getPrimaryStage().setScene(App.loadSignupScene());
    }
}
