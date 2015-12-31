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
import model.DataEntity;
import model.User;
import model.User.Rol;
import utils.Utils;

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
    Button logBut;

    
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
    TableColumn<User, String> rolCol;

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

    @FXML
    ComboBox rolSearch;

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
        rolCol.setCellValueFactory(new PropertyValueFactory<User, String>("rolDisplay"));

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
                        activeUser.setRol(Rol.getRol(rolField.getValue().toString()).getId());
                        activeUser.setPassword(passwordField.getText());
                        activeUser.save();
                        newItem(null);
                        fillGrid(null);
                    }
                }
        );

        defaultSearchMaintenanceInit();
    }

    private ObservableList<User> getUsers() throws ClassNotFoundException, SQLException {

        ObservableList<User> list = FXCollections.observableArrayList();
        Connection connection = DataEntity.getConnection();
        Statement statement = connection.createStatement();

        // Maak SQL string
        String sqlString = "select * from user ";
        sqlString += getWhereClause();
        sqlString += " order by lastname ";

        PreparedStatement pstmt = connection.prepareStatement(sqlString);

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
        
        // Deactivate buttons
        deleteBut.setDisable(true);
        logBut.setDisable(true);
    }
    
    public void delete(ActionEvent event) {
        activeUser.delete();
        // Set new user
        newItem(event);
        // Refresh grid
        fillGrid(event);
    }

    public void fillFields(User user) {
        activeUser = user;
        if (user.getId() < 0) {
            idField.setText("New");
        } else {
            idField.setText(String.valueOf(user.getId()));
            // Activate buttons
            deleteBut.setDisable(false);
            logBut.setDisable(false);
 
        }
        firstnameField.setText(user.getFirstname());
        middlenameField.setText(user.getMiddlename());
        lastnameField.setText(user.getLastname());
        emailField.setText(user.getEmail());
        Rol rol = Rol.getRol(user.getRol());
        rolField.setValue(rol.getDescription());
        passwordField.setText(user.getPassword());

    }

    public String getWhereClause() {
        String whereString = "";
        // Check Rol
        Rol sRol = Rol.getRol("" + rolSearch.getValue());
        if (sRol != null) {
            whereString += " rol = " + sRol.getId();
        }
        
        if (!Utils.isEmpty(whereString)) {
            whereString = " Where " +whereString;
        }
        return whereString;
    }

    public void fillGrid(ActionEvent event) {
        try {
            grid.setItems(getUsers());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
