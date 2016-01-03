/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static java.lang.Math.log;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.DataEntity;
import model.Log;
import model.User;
import model.User.Rol;
import utils.Utils;

/**
 * FXML Controller class
 *
 * @author jandorresteijn
 */
public class LogController extends SearchMaintenanceController implements Initializable {

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
    TableView<Log> grid;

    @FXML
    TableColumn<Log, Integer> idCol;

    @FXML
    TableColumn<Log, Calendar> logdateCol;

    @FXML
    TableColumn<Log, String> userDisplayCol;

    @FXML
    TableColumn<Log, String> updatetypeCol;

    @FXML
    TableColumn<Log, String> memoCol;

  
    @FXML
    TextField idField;

    @FXML
    TextField userDisplayField;

    @FXML
    TextArea memoField;

    Log activeLog;

    @FXML
    ComboBox tablenameSearch;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            AnchorPane page = (AnchorPane) FXMLLoader.load(MainView.class.getResource("Log.fxml"));
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Hille's oplossing");
            primaryStage.show();
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        Application.launch(LogController.class, (java.lang.String[]) null);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
      //  idCol.setCellValueFactory(new PropertyValueFactory<Log, Integer>("id"));
        logdateCol.setCellValueFactory(new PropertyValueFactory<Log, Calendar>("logdateDisplay"));
        userDisplayCol.setCellValueFactory(new PropertyValueFactory<Log, String>("userDisplay"));
        updatetypeCol.setCellValueFactory(new PropertyValueFactory<Log, String>("updatetypeDisplay"));
        memoCol.setCellValueFactory(new PropertyValueFactory<Log, String>("memo"));
        
        // We do not use sabe in log
        saveBut.setOnMousePressed(
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("in save");
                        /*
                        activeUser.setFirstname(firstnameField.getText());
                        activeUser.setMiddlename(middlenameField.getText());
                        activeUser.setLastname(lastnameField.getText());
                        activeUser.setEmail(emailField.getText());
                        activeUser.setRol(Rol.getRol(rolField.getValue().toString()).getId());
                        activeUser.setPassword(passwordField.getText());
                        activeUser.save();
                        newItem(null);
                        
                        fillGrid(null);
                        */
                    }
                }
        );

        defaultSearchMaintenanceInit();
    }

    private ObservableList<Log> getLogs() throws ClassNotFoundException, SQLException {

        ObservableList<Log> list = FXCollections.observableArrayList();
        Connection conn = DataEntity.getConnection();
    
        // Definer paramsList
        List<Object> params = new ArrayList<Object>();
       
        // Maak SQL string
        String sqlString = "SELECT * FROM log ";
        sqlString += getWhereClause(params);
        sqlString += " ORDER BY logdate ";
        System.out.println("sqlString = "+ sqlString);
        
        PreparedStatement pstmt = conn.prepareStatement(sqlString);
        DataEntity.addParamsToStatement(pstmt, params);
        
        System.out.println("sql query " + pstmt.toString());
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Calendar cal  = new GregorianCalendar();
            cal.setTime(rs.getTimestamp("logdate"));
            Log log = new Log(rs.getInt("id"),
                    rs.getInt("user_id"),
                    cal,
                    rs.getString("tablename"),
                    rs.getInt("tableid"),
                    rs.getInt("updatetype"),
                    rs.getString("memo"));
            list.add(log);

        }
        return list;

    }

    public void doGridSelect(TableRow row) {
        // Do nothing

    }

    public void newItem(ActionEvent event) {
        Log log = new Log();
        log.getNew();
        fillFields(log);
        
        // Deactivate buttons
        deleteBut.setDisable(true);
        logBut.setDisable(true);
    }
    
    public void delete(ActionEvent event) {
        activeLog.delete();
        // Set new user
        newItem(event);
        // Refresh grid
        fillGrid(event);
    }

    public void fillFields(Log log) {
        /*
        User activeLog = user;
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
        */
    }

    public String getWhereClause( List<Object> params) {
        String whereString = "";
        
        // Check Rol
        String tableName = tablenameSearch.getValue().toString();
        if (! Utils.isEmpty(tableName)) {
            whereString += " tablename = ? " ;
            params.add(tableName);
        }
        
        String whereLikes = "";
         // Check ook ints
        int[] searchInts = getSearchInts();
        for (int i = 0 ; i <  searchInts.length ; i++){
             whereLikes = "tableid = ?" ;
             params.add(searchInts[i]);
        }
        
        whereString = Utils.glue(whereString, whereLikes, " AND ");
        
        if (!Utils.isEmpty(whereString)) {
            whereString = " WHERE " +whereString;
        }
        return whereString;
    }

    public void fillGrid(ActionEvent event) {
        try {
            grid.setItems(getLogs());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LogController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LogController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void searchTableNameAndId(String table, int id) {
        try {
            tablenameSearch.setValue(table);
            searchField.setText(""+id);
            grid.setItems(getLogs());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LogController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LogController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
