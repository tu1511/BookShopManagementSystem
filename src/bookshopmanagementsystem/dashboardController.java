/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookshopmanagementsystem;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class dashboardController implements Initializable{

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button close;

    @FXML
    private Button minimize;

    @FXML
    private Label username;

    @FXML
    private Button dashboard_btn;

    @FXML
    private Button availableBooks_btn;

    @FXML
    private Button purchase_btn;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Label dashboard_AB;

    @FXML
    private Label dashboard_TI;

    @FXML
    private Label dashboard_TC;

    @FXML
    private AreaChart<?, ?> dashboard_incomeChart;

    @FXML
    private BarChart<?, ?> dashboard_customerChart;

    @FXML
    private AnchorPane availableBooks_form;

    @FXML
    private ImageView availableBooks_imageView;

    @FXML
    private Button availableBooks_importBtn;

    @FXML
    private TextField availableBooks_bookID;

    @FXML
    private TextField availableBooks_bookTitle;

    @FXML
    private TextField availableBooks_author;

    @FXML
    private TextField availableBooks_genre;

    @FXML
    private DatePicker availableBooks_date;

    @FXML
    private TextField availableBooks_price;

    @FXML
    private Button availableBooks_addBtn;

    @FXML
    private Button availableBooks_updateBtn;

    @FXML
    private Button availableBooks_clearBtn;

    @FXML
    private Button availableBooks_deleteBtn;

    @FXML
    private TextField availableBooks_search;

    @FXML
    private TableView<?> availableBooks_tableView;

//    @FXML
//    private TableColumn<bookData, String> availableBooks_col_bookID;
//
//    @FXML
//    private TableColumn<bookData, String> availableBooks_col_bookTItle;
//
//    @FXML
//    private TableColumn<bookData, String> availableBooks_col_author;
//
//    @FXML
//    private TableColumn<bookData, String> availableBooks_col_genre;
//
//    @FXML
//    private TableColumn<bookData, String> availableBooks_col_date;
//
//    @FXML
//    private TableColumn<bookData, String> availableBooks_col_price;

    @FXML
    private AnchorPane purchase_form;

    @FXML
    private ComboBox<?> purchase_bookID;

    @FXML
    private ComboBox<?> purchase_bookTitle;

    @FXML
    private Label purchase_total;

    @FXML
    private Button purchase_addBtn;

    @FXML
    private Label purchase_info_bookID;

    @FXML
    private Label purchase_info_bookTItle;

    @FXML
    private Label purchase_info_author;

    @FXML
    private Label purchase_info_genre;

    @FXML
    private Label purchase_info_date;

    @FXML
    private Button purchase_payBtn;

    @FXML
    private TableView<?> purchase_tableView;

    @FXML
    private Spinner<Integer> purchase_quantity;
    
//    @FXML
//    private TableColumn<customerData, String> purchase_col_bookID;
//
//    @FXML
//    private TableColumn<customerData, String> purchase_col_bookTitle;
//
//    @FXML
//    private TableColumn<customerData, String> purchase_col_author;
//
//    @FXML
//    private TableColumn<customerData, String> purchase_col_genre;
//
//    @FXML
//    private TableColumn<customerData, String> purchase_col_quantity;
//
//    @FXML
//    private TableColumn<customerData, String> purchase_col_price;
     @FXML
    private TableColumn<?, ?> availableBooks_col_author;

    @FXML
    private TableColumn<?, ?> availableBooks_col_bookID;

    @FXML
    private TableColumn<?, ?> availableBooks_col_bookTitle;

    @FXML
    private TableColumn<?, ?> availableBooks_col_date;

    @FXML
    private TableColumn<?, ?> availableBooks_col_genre;

    @FXML
    private TableColumn<?, ?> availableBooks_col_price;


    @FXML
    private TableColumn<?, ?> purchase_col_author;

    @FXML
    private TableColumn<?, ?> purchase_col_bookID;

    @FXML
    private TableColumn<?, ?> purchase_col_bookTitle;

    @FXML
    private TableColumn<?, ?> purchase_col_genre;

    @FXML
    private TableColumn<?, ?> purchase_col_price;

    @FXML
    private TableColumn<?, ?> purchase_col_quantity;
    
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    
    private Image image;
    
    public ObservableList<bookData> availableBooksListData(){
        
        ObservableList<bookData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM book";
        
        connect = database.connectDb();
        
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            bookData bookD;
            
            while(result.next()){
                bookD = new bookData(result.getInt("book_id"), result.getString("title")
                        , result.getString("author"), result.getString("genre")
                        , result.getDate("pub_date"), result.getDouble("price")
                        , result.getString("image"));
                
                listData.add(bookD);
            }
        }catch(Exception e){e.printStackTrace();}
        return listData;
    }
    
    public void displayUsername(){
        String user = getData.username;
        user = user.substring(0, 1).toUpperCase() + user.substring(1);
        username.setText(user);
    }
   
    public void switchForm(ActionEvent event){
        
        if(event.getSource() == dashboard_btn){
            dashboard_form.setVisible(true);
            availableBooks_form.setVisible(false);
            purchase_form.setVisible(false);
            
            dashboard_btn.setStyle("-fx-background-color:linear-gradient(to top right, #72513c, #ab853e);");
            availableBooks_btn.setStyle("-fx-background-color: transparent");
            purchase_btn.setStyle("-fx-background-color: transparent");
            
           
            
        }else if(event.getSource() == availableBooks_btn){
            dashboard_form.setVisible(false);
            availableBooks_form.setVisible(true);
            purchase_form.setVisible(false);
            
            availableBooks_btn.setStyle("-fx-background-color:linear-gradient(to top right, #72513c, #ab853e);");
            dashboard_btn.setStyle("-fx-background-color: transparent");
            purchase_btn.setStyle("-fx-background-color: transparent");
            
//            availableBooksShowListData();
//            availableBooksSeach();
            
        }else if(event.getSource() == purchase_btn){
            dashboard_form.setVisible(false);
            availableBooks_form.setVisible(false);
            purchase_form.setVisible(true);
            
            purchase_btn.setStyle("-fx-background-color:linear-gradient(to top right, #72513c, #ab853e);");
            availableBooks_btn.setStyle("-fx-background-color: transparent");
            dashboard_btn.setStyle("-fx-background-color: transparent");
            
 
            
        }
    }
//    
    private double x = 0;
    private double y = 0;
    public void logout(){
        try{
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();
            
            if(option.get().equals(ButtonType.OK)){
                
                // HIDE YOUR DASHBOARD
                logout.getScene().getWindow().hide();
                // LINK YOUR LOGIN FORM
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) ->{
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) ->{
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) ->{
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();
            }
            
        }catch(Exception e){e.printStackTrace();}
    }
    
    public void close(){
        System.exit(0);
    }
    
    public void minimize(){
        Stage stage = (Stage)main_form.getScene().getWindow();
        stage.setIconified(true);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUsername();
        
//        dashboardAB();
//        dashboardTI();
//        dashboardTC();
//        dashboardIncomeChart();
//        dashboardCustomerChart();
//        
//        // TO SHOW THE DATA ON TABLEVIEW (AVAILABLE BOOKS)
//        availableBooksShowListData();
//        
//        purchaseBookId();
//        purchaseBookTitle();
//        purchaseShowCustomerListData();
//        purchaseDisplayQTY();
//        purchaseDisplayTotal();
        
    }
    
}