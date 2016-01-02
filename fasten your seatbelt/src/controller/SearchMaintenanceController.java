/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.User;
import utils.Utils;

/**
 *
 * @author jandorresteijn
 */
public abstract class SearchMaintenanceController extends  Application implements SearchMaintenanceInterface{

    // Standard FXML
    @FXML
    TableView<User> grid;

 
    @FXML
    TextField searchField;

    
    public void doGridSelect(TableRow row) {}

    
    
    public void defaultSearchMaintenanceInit() {
        grid.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    Node node = ((Node) event.getTarget()).getParent();
                    TableRow row;
                    if (node instanceof TableRow) {
                        row = (TableRow) node;
                    } else {
                        // clicking on text part
                        row = (TableRow) node.getParent();
                    }
                    doGridSelect(row);
                }
            }
        });
        
        newItem(null);
        
    }

    
    public String[] getSearchStrings(){
        if (Utils.isEmpty(searchField.getText())){
            return new String[0];
        }
        String[] retString = searchField.getText().split(" ");
        return retString;
    }
    
    public int[] getSearchInts(){
        String[] strings = searchField.getText().split(" ");
        String newIntString = "";
        for (int i = 0 ; i < strings.length; i++){
            try {
                int isInt = Integer.parseInt(strings[i]);
                newIntString  = Utils.glue(newIntString, ""+isInt, " ");
            } catch (NumberFormatException e) {
            }
        }
        if (Utils.isEmpty(newIntString)){
            return new int[0];
        }
        String[] sInts = newIntString.split(" ");
 
        System.out.println("sInts "+ sInts.length);
        int[] ints = new int[sInts.length];
        for (int i = 0 ; i < sInts.length; i++){
             ints[i] = Integer.parseInt(sInts[i]);
        } 
        return ints;
    }

}
