package controller;

import entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import sun.applet.Main;

import java.util.Optional;

public class PromoteController {

    @FXML
    TextField userIdField;

    public PromoteController(){

    }

    @FXML
    private void handlePromote(){
        String userId = userIdField.getText();

        System.out.println("userId: " + userId);

        // Validate

        User user = MainController.getUserPanelAsManager().getUsersManager().getUser(Integer.valueOf(userId));

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("The following user will have managers credentials, Are you sure?");
        alert.setContentText("Full Name: " + user.getFirstName() + " " + user.getLastName() + "\n"
                                + "Email: " + user.getEmail());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            MainController.getUserPanelAsManager().getUsersManager().promoteUser(user, User.PRIVILEGE_MANAGER);
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }

}
