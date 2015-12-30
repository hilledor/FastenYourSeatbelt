/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.User;

/**
 * FXML Controller class
 *
 * @author jandorresteijn
 */
public class UserController extends SearchMaintenanceController implements Initializable {

    // Moet apart naar SearchMaintenanceController
    @FXML
    public Pane searchPane;

    @FXML
    Pane maintenancePane;

    @FXML
    Button searchBut;

    @FXML
    TextField searchField;

    @FXML
    Button newBut;

    @FXML
    Button saveBut;

    @FXML
    Button deleteBut;

    @FXML
    TableView<User> grid;

    @FXML
    TableColumn<User, Integer> idCol;

    @FXML
    TableColumn<User, String> firstnameCol;

    @FXML
    TableColumn<User, String> middlenameCol;

    @FXML
    TableColumn<User, String> lastnameCol;

    @FXML
    TableColumn<User, String> emailCol;

    @FXML
    TableColumn<User, Integer> rolCol;

    @FXML
    TextField idField;

    @FXML
    TextField firstnameField;

    @FXML
    TextField middlenameField;

    @FXML
    TextField lastnameField;

    @FXML
    TextField emailField;

    @FXML
    ComboBox rolField;

    @FXML
    PasswordField passwordField;

    User activeUser;
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            AnchorPane page = (AnchorPane) FXMLLoader.load(MainView.class.getResource("User.fxml"));
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Hille's oplossing");
            primaryStage.show();
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        Application.launch(UserController.class, (java.lang.String[]) null);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idCol.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        firstnameCol.setCellValueFactory(new PropertyValueFactory<User, String>("firstname"));
        middlenameCol.setCellValueFactory(new PropertyValueFactory<User, String>("middlename"));
        lastnameCol.setCellValueFactory(new PropertyValueFactory<User, String>("lastname"));
        emailCol.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        rolCol.setCellValueFactory(new PropertyValueFactory<User, Integer>("rolDisplay"));

        try {
            grid.setItems(getUsers());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }

        saveBut.setOnMousePressed(
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("in save");
                        
                        
                           
                        activeUser.setFirstname(firstnameField.getText());
                        activeUser.setMiddlename(middlenameField.getText());
                        activeUser.setLastname(lastnameField.getText());
                        activeUser.setEmail(emailField.getText());
                        activeUser.setRol((int) rolField.getValue());
                        activeUser.setPassword(passwordField.getText());
                        activeUser.save();
                        /*
                         try {
                
                         selectedUser.setVoorNaam(voornaamTextField.getText());

                         selectedUser.setTussenVoeg(tussenTextField.getText());

                         selectedUser.setAchterNaam(achternaamTextField.getText());

                         selectedUser.setEmail(usernameTextField.getText());

                         selectedUser.setRol("" + rolComboField.getValue());
                         System.out.println("rolComboField.getText() " + rolComboField.getValue());
                         selectedUser.setWachtWoord(passwordTextField.getText());
                         System.out.println("passwordTextField.getText() " + passwordTextField.getText());

                         selectedUser.save();

                         table.setItems(getUsers());
                         setNewUser();

                         } catch (ClassNotFoundException ex) {
                         Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                         } catch (SQLException ex) {
                         Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                         }
                         */
                    }
                });

        defaultSearchMaintenanceInit();
    }

    private ObservableList<User> getUsers() throws ClassNotFoundException, SQLException {

        ObservableList<User> list = FXCollections.observableArrayList();
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/fys2", "root", "root");
        Statement statement = connection.createStatement();

        PreparedStatement pstmt = connection.prepareStatement("select * from user order by lastname");

        System.out.println("sql query " + pstmt.toString());
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            User user = new User(rs.getInt("id"),
                    rs.getString("firstname"),
                    rs.getString("middlename"),
                    rs.getString("lastname"),
                    rs.getString("email"),
                    rs.getInt("rol"),
                    rs.getString("password"));
            list.add(user);
     
        }
        return list;

    }

    public void doGridSelect(TableRow row) {
        User user = (User) row.getItem();
        user.load(); // Extra laden inactive veld is niet in het grid
        fillFields(user);
        // delete.setDisable(false);

    }

    public void newItem(ActionEvent event) {
        User user = new User();
        user.getNew();
        fillFields(user);
    }

    public void fillFields(User user) {
        activeUser = user;
        if (user.getId() < 0) {
            idField.setText("New");
        } else {
            idField.setText(String.valueOf(user.getId()));
        }
        firstnameField.setText(user.getFirstname());
        middlenameField.setText(user.getMiddlename());
        lastnameField.setText(user.getLastname());
        emailField.setText(user.getEmail());
        rolField.setValue(user.getRol());
        passwordField.setText(user.getPassword());

    }

}
