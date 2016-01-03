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
import java.util.ArrayList;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Client;
import model.DataEntity;
import model.User;
import model.User.Rol;
import utils.Utils;

/**
 * FXML Controller class
 *
 * @author jandorresteijn
 */
public class ClientController extends SearchMaintenanceController implements Initializable {

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
    TableView<Client> grid;

    @FXML
    TableColumn<Client, Integer> idCol;

    @FXML
    TableColumn<Client, String> firstnameCol;

    @FXML
    TableColumn<Client, String> middlenameCol;

    @FXML
    TableColumn<Client, String> lastnameCol;

    @FXML
    TableColumn<Client, String> emailCol;

    @FXML
    TableColumn<Client, String> phonenumberCol;
    
    @FXML
    TableColumn<Client, String> streetCol;
    
    @FXML
    TableColumn<Client, String> streetnumberCol;
    
    @FXML
    TableColumn<Client, String> zipcodeCol;
    
    @FXML
    TableColumn<Client, String> cityCol;
    
    @FXML
    TableColumn<Client, String> countryCol;
    
    

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
    TextField phonenumberField;

    @FXML
    TextField streetField;
    
    @FXML
    TextField streetnumberField;
    
    @FXML
    TextField zipcodeField;
    
    @FXML
    TextField cityField;
    
    @FXML
    TextField countryField;

    Client activeClient;

    @FXML
    ComboBox rolSearch;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            AnchorPane page = (AnchorPane) FXMLLoader.load(MainView.class.getResource("Client.fxml"));
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Hille's oplossing");
            primaryStage.show();
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        Application.launch(ClientController.class, (java.lang.String[]) null);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //idCol.setCellValueFactory(new PropertyValueFactory<Client, Integer>("id"));
        firstnameCol.setCellValueFactory(new PropertyValueFactory<Client, String>("firstname"));
        middlenameCol.setCellValueFactory(new PropertyValueFactory<Client, String>("middlename"));
        lastnameCol.setCellValueFactory(new PropertyValueFactory<Client, String>("lastname"));
        emailCol.setCellValueFactory(new PropertyValueFactory<Client, String>("email"));
        phonenumberCol.setCellValueFactory(new PropertyValueFactory<Client, String>("phonenumber"));
        streetCol.setCellValueFactory(new PropertyValueFactory<Client, String>("phonenumber"));
        streetnumberCol.setCellValueFactory(new PropertyValueFactory<Client, String>("phonenumber"));
        zipcodeCol.setCellValueFactory(new PropertyValueFactory<Client, String>("phonenumber"));
        cityCol.setCellValueFactory(new PropertyValueFactory<Client, String>("phonenumber"));
        countryCol.setCellValueFactory(new PropertyValueFactory<Client, String>("phonenumber"));


        try {
            grid.setItems(getClients());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }

        saveBut.setOnMousePressed(
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("in save");
                        activeClient.setFirstname(firstnameField.getText());
                        activeClient.setMiddlename(middlenameField.getText());
                        activeClient.setLastname(lastnameField.getText());
                        activeClient.setEmail(emailField.getText());
                        activeClient.setPhonenumber(phonenumberField.getText());
                        activeClient.setStreet(streetField.getText());
                        activeClient.setStreetnumber(streetnumberField.getText());
                        activeClient.setZipcode(zipcodeField.getText());
                        activeClient.setCity(cityField.getText());
                        activeClient.setCountry(countryField.getText());

                        activeClient.save();
                        newItem(null);
                        fillGrid(null);
                    }
                }
        );

        defaultSearchMaintenanceInit();
    }

    private ObservableList<Client> getClients() throws ClassNotFoundException, SQLException {

        ObservableList<Client> list = FXCollections.observableArrayList();
        Connection conn = DataEntity.getConnection();
    
        // Definer paramsList
        List<Object> params = new ArrayList<Object>();
       
        // Maak SQL string
        String sqlString = "SELECT * FROM Client ";
        sqlString += getWhereClause(params);
        sqlString += " ORDER BY lastname ";
        System.out.println("sqlString = "+ sqlString);
        
        PreparedStatement pstmt = conn.prepareStatement(sqlString);
        DataEntity.addParamsToStatement(pstmt, params);
        
        System.out.println("sql query " + pstmt.toString());
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Client client = new Client(rs.getInt("id"),
                    rs.getString("firstname"),
                    rs.getString("middlename"),
                    rs.getString("lastname"),
                    rs.getString("email"),
                    rs.getString("phonenumber"),
                    rs.getString("street"),
                    rs.getString("streetnumber"),
                    rs.getString("zipcode"),
                    rs.getString("city"),
                    rs.getString("country"));
            list.add(client);

        }
        return list;

    }

    public void doGridSelect(TableRow row) {
        Client client = (Client) row.getItem();
        client.load(); // Extra laden inactive veld is niet in het grid
        fillFields(client);
        // delete.setDisable(false);

    }

    public void newItem(ActionEvent event) {
        Client client = new Client();
        client.getNew();
        fillFields(client);
        
        // Deactivate buttons
        deleteBut.setDisable(true);
        logBut.setDisable(true);
    }
    
    public void delete(ActionEvent event) {
        activeClient.delete();
        // Set new client
        newItem(event);
        // Refresh grid
        fillGrid(event);
    }

    public void fillFields(Client client) {
        activeClient = client;
        if (client.getId() < 0) {
            idField.setText("New");
        } else {
            idField.setText(String.valueOf(client.getId()));
            // Activate buttons
            deleteBut.setDisable(false);
            logBut.setDisable(false);
 
        }
        firstnameField.setText(client.getFirstname());
        middlenameField.setText(client.getMiddlename());
        lastnameField.setText(client.getLastname());
        emailField.setText(client.getEmail());
        phonenumberField.setText(client.getPhonenumber());
        streetField.setText(client.getStreet());

    }

    public String getWhereClause( List<Object> params) {
        String whereString = "";
        
        
        // Beetje fulltext
        String[] searchStrs = getSearchStrings();
        String whereLikes = "";
        for (int i = 0 ; i <  searchStrs.length ; i++){
            whereLikes = Utils.glue(whereLikes, "firstname like ?" , " OR ");
            params.add("%"+searchStrs[i]+"%");
            whereLikes = Utils.glue(whereLikes, "lastname like ?" , " OR ");
            params.add("%"+searchStrs[i]+"%");
            whereLikes = Utils.glue(whereLikes, "email like ?" , " OR ");
            params.add("%"+searchStrs[i]+"%");
        }
       
        
        // Check ook ints
        int[] searchInts = getSearchInts();
        for (int i = 0 ; i <  searchInts.length ; i++){
             whereLikes = Utils.glue(whereLikes, "id like ?" , " OR ");
             params.add(searchInts[i]);
        }
        
        if (!Utils.isEmpty(whereLikes)){
            whereLikes = " ( "+ whereLikes+" ) ";          
        }
        whereString = Utils.glue(whereString, whereLikes, " AND ");
        
        if (!Utils.isEmpty(whereString)) {
            whereString = " WHERE " +whereString;
        }
        return whereString;
    }

    public void fillGrid(ActionEvent event) {
        try {
            grid.setItems(getClients());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void showLog(ActionEvent event) {
        showLog(activeClient.getTable(), activeClient.getId());
    }

 
    
    
}
