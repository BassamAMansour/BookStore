package controller;

import entities.Book;
import entities.Sale;
import entities.User;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Date;
import java.time.Instant;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {

    public static final long SECONDS_OF_MONTH = 30*24*60*60;

    public StatisticsController(){

    }

    public static final String TOTAL_SALES_REPORT = "The total sales for books in the previous month";
    public static final String TOP_5_CUSTOMERS_REPORT = "The top 5 purchasers for the last three months";
    public static final String TOP_10_BOOKS_REPORT = " The top 10 selling books for the last three months";


    private ObservableList<Sale> saleData = FXCollections.observableArrayList();
    private ObservableList<User> userData = FXCollections.observableArrayList();
    private ObservableList<Book> bookData = FXCollections.observableArrayList();

    @FXML private TableView<Sale> saleTable = new TableView<>();
    @FXML private TableColumn<Sale, String> saleDateCol;
    @FXML private TableColumn<Sale, String> saleUserCol;
    @FXML private TableColumn<Sale, String> saleBookCol;
    @FXML private TableColumn<Sale, Integer> saleQuantityCol;

    @FXML private TableView<User> userTable = new TableView<>();
    @FXML private TableColumn<User, String> userUsernameCol;
    @FXML private TableColumn<User, String> userFirstNameCol;
    @FXML private TableColumn<User, String> userLastNameCol;
    @FXML private TableColumn<User, String> userEmailCol;

    @FXML private TableView<Book> bookTable = new TableView<>();
    @FXML private TableColumn<Book, Integer> bookISBNCol;
    @FXML private TableColumn<Book, String> bookTitleCol;
    @FXML private TableColumn<Book, String> bookAuthorCol;
    @FXML private TableColumn<Book, String> bookPublisherCol;

    @FXML private ComboBox reportListBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reportListBox.getItems().addAll(TOTAL_SALES_REPORT, TOP_5_CUSTOMERS_REPORT, TOP_10_BOOKS_REPORT);
        reportListBox.setValue(TOTAL_SALES_REPORT);

        saleData.setAll(MainController.getUserPanelAsManager().getReportsManager()
                .getSalesAfterDate(getDatefromMonth()));
        userData.setAll(MainController.getUserPanelAsManager().getReportsManager()
                .getTopPurchasersAfterDate(getDateFromThreeMonths(),5));
        bookData.setAll(MainController.getUserPanelAsManager().getReportsManager()
                .getTopSellingBooksAfterDate(getDateFromThreeMonths(),10));

        initializeSaleTable();
        initializeUserTable();
        initializeBookTable();

        saleTable.setItems(saleData);
        System.out.println("saleData size:" + saleData.size());
        userTable.setItems(userData);
        System.out.println("userData size:" + userData.size());
        bookTable.setItems(bookData);
        System.out.println("bookData size:" + bookData.size());

        handleViewReport();
    }

    @FXML
    private void handleViewReport(){
        String report = (String) reportListBox.getValue();
        if(TOTAL_SALES_REPORT.equals(report)){
            saleTable.setVisible(true);
            userTable.setVisible(false);
            bookTable.setVisible(false);
        }else if(TOP_5_CUSTOMERS_REPORT.equals(report)){
            saleTable.setVisible(false);
            userTable.setVisible(true);
            bookTable.setVisible(false);
        }else if(TOP_10_BOOKS_REPORT.equals(report)){
            saleTable.setVisible(false);
            userTable.setVisible(false);
            bookTable.setVisible(true);
        }
        System.out.println("View is pressed");
    }

    private void initializeSaleTable(){
        saleDateCol.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().getSaleDate().toString()));
        saleUserCol.setCellValueFactory(cellData -> new SimpleStringProperty(MainController.getUserPanelAsManager().getUsersManager()
                .getUser(cellData.getValue().getUserId()).getUsername()));
        // TODO: findBookById
        saleBookCol.setCellValueFactory(cellData -> new SimpleStringProperty(MainController.getUserPanelAsManager().getBooksFinder().
                findBookByISBN(cellData.getValue().getBookId()).getTitle()));
        saleQuantityCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getSoldQuantity()).asObject());
    }

    private void initializeUserTable(){
        userUsernameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        userFirstNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
        userLastNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
        userEmailCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
    }

    private void initializeBookTable(){
        bookISBNCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIsbn()).asObject());
        bookTitleCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        bookAuthorCol.setCellValueFactory(cellData -> new SimpleStringProperty(MainController.getUserPanelAsManager().getBooksFinder()
                .getAuthorById(cellData.getValue().getAuthorId()).getAuthorName()));
        bookPublisherCol.setCellValueFactory(cellData -> new SimpleStringProperty(MainController.getUserPanelAsManager().getBooksFinder()
                .getPublisherById(cellData.getValue().getAuthorId()).getName()));
    }

    private Date getDatefromMonth(){
        long secondsFromEpoch = Instant.now().getEpochSecond();
        return new Date((secondsFromEpoch-SECONDS_OF_MONTH)*1000L);
    }

    private Date getDateFromThreeMonths(){
        long secondsFromEpoch = Instant.now().getEpochSecond();
        return new Date((secondsFromEpoch-(3*SECONDS_OF_MONTH))*1000L);
    }
}
