/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.DataEntity;
import model.User;
import utils.Utils;

/**
 * FXML Controller class
 *
 * @author jandorresteijn
 */
public class LoginController implements Initializable {

    
    
    
    @FXML
    Button login;
            
    
    @FXML
    TextField email;
    
    @FXML
    Label hint;
    
    
    @FXML
    PasswordField password;
    
    
    @FXML
    Pane loginPane;
    
  
    
    
    
    public void onLogin(ActionEvent event){
        try {
            User user = searchUser(email.getText(), password.getText());
            if (user.isNew()){
                Utils.alert("Login","Email or Password incorrect!");
                return;        
            } else if (email.getText().equals("hille@base.nl") && password.getText().equals("admin")){
                Utils.alert("Login","Hint text disappears when you change password from user 'hille@base.nl'");
            }
            System.out.println("in login");
            FXMLLoader loader = new FXMLLoader();
            AnchorPane page = (AnchorPane) loader.load(MainView.class.getResource("Main.fxml"));
            LoginController lc = loader.getController();
            
            Scene scene = new Scene(page);
            FouteStatic.primaryStage.close();
            FouteStatic.primaryStage.setScene(scene);
            FouteStatic.primaryStage.show();
            // 
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
               
    }
  
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    
    public User searchUser(String email, String passWord){
        User user = null;
        try {
            // Check table user via select
            Connection connection = DataEntity.getConnection();
            PreparedStatement pstmt = connection.prepareStatement("select * from user  where email=? and password = ? limit 1");
            pstmt.setString(1, email);
            pstmt.setString(2, passWord);
            
            ResultSet rs = pstmt.executeQuery();
            user = new User(rs);
        } catch (SQLException ex) {
            Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }
    
}
