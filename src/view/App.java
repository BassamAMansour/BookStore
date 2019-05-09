package view;

import controller.LoginController;
import controller.MainController;
import controller.PaneNavigator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class App extends Application {

    private static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setTitle("Book Store");
        //primaryStage.setResizable(false);
        //primaryStage.getIcons().add(new Image("file:resources/images/logo.png"));
        primaryStage.setScene(loadLoginScene());
        //primaryStage.setScene(createScene(loadMainPane()));
        primaryStage.show();

    }


    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static Scene loadLoginScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(new URL("file:resources/fxml/login.fxml"));
        AnchorPane pane = loader.load();
        return new Scene(pane);
    }

    public static Scene loadSignupScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(new URL("file:resources/fxml/signup.fxml"));
        GridPane pane = loader.load();
        return new Scene(pane);
    }

    public static Scene loadMainScene() throws Exception {
        Pane mainPane = loadMainPane();
        Scene scene = new Scene(mainPane);
        File f = new File("resources/style/tab.css");
        scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
        return scene;
    }


    private static Pane loadMainPane() throws IOException {
        FXMLLoader loader = new FXMLLoader(new URL("file:resources/fxml/" + PaneNavigator.MAIN_PANE + ".fxml"));
        Pane mainPane = loader.load();
        MainController mainController = loader.getController();
        PaneNavigator.setMainController(mainController);
        PaneNavigator.loadPane(PaneNavigator.STORE_PANE);
        return mainPane;
    }
}
