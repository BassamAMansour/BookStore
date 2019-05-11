package controller;

import entities.User;
import panels.CustomerPanel;
import panels.ManagerPanel;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import view.App;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private TextField passwordField;

    public LoginController(){
        try {
            MainController.setUserPanel(ManagerPanel.fromCredentials("admin","admin"));
        }catch (Exception e){
            System.out.println("Admin manger is not found in the database!");
        }
    }

    @FXML
    private void login() throws Exception {
        String username = usernameField.getText();
        String password = passwordField.getText();

        CustomerPanel customerPanel = CustomerPanel.fromCredentials(username,password);
        if(customerPanel.getUser().getPrivilegeType()==User.PRIVILEGE_MANAGER){
            MainController.setUserPanel(ManagerPanel.fromManager(customerPanel.getUser()));
        }else{
            MainController.setUserPanel(customerPanel);
        }

        App.getPrimaryStage().setScene(App.loadMainScene());
    }

    @FXML
    private void signup() throws Exception{
        App.getPrimaryStage().setScene(App.loadSignupScene());
    }
}
