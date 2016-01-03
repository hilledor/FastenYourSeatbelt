/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.User;

/**
 * FXML Controller class
 *
 * @author jandorresteijn
 */
public class MainController implements Initializable {

    @FXML
    Button users;

    @FXML
    Button clients;

    @FXML
    Pane mainPain;

    @FXML
    SplitPane splitPane;

    public void onUsers(ActionEvent event) {
        try {
            AnchorPane userScr = (AnchorPane) FXMLLoader.load(LogController.class.getResource("User.fxml"));
            //Opruimnen oude objecten weet niet of dit de manier is
            mainPain.getChildren().removeAll(mainPain.getChildren());
            mainPain.getChildren().clear();
            mainPain.getChildren().setAll(userScr);
            FouteStatic.primaryStage.setTitle("FYS Users");
            AnchorPane.setTopAnchor(userScr, 0.0);
            AnchorPane.setLeftAnchor(userScr, 0.0);
            AnchorPane.setRightAnchor(userScr, 0.0);
            AnchorPane.setBottomAnchor(userScr, 0.0);

        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        

    public void onClients(ActionEvent event) {
        try {
            AnchorPane clientScr = (AnchorPane) FXMLLoader.load(LogController.class.getResource("Client.fxml"));
            //Opruimnen oude objecten weet niet of dit de manier is
            mainPain.getChildren().removeAll(mainPain.getChildren());
            mainPain.getChildren().clear();
            mainPain.getChildren().setAll(clientScr);
            FouteStatic.primaryStage.setTitle("FYS Clients");
            AnchorPane.setTopAnchor(clientScr, 0.0);
            AnchorPane.setLeftAnchor(clientScr, 0.0);
            AnchorPane.setRightAnchor(clientScr, 0.0);
            AnchorPane.setBottomAnchor(clientScr, 0.0);

        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}


