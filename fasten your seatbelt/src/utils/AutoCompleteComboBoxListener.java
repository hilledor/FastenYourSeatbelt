/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *
 * http://stackoverflow.com/questions/19924852/autocomplete-combobox-in-javafx
 */
package utils;

import java.awt.event.FocusEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import searchers.DefaultSearcher;

/**
 *
 * @author jdor
 */
public class AutoCompleteComboBoxListener<T> implements EventHandler<KeyEvent> {

    private ComboBox comboBox;
    private StringBuilder sb;
    private ObservableList<T> data;
    private boolean moveCaretToPos = false;
    private int caretPos;
    private DefaultSearcher searcher ;
    

    public AutoCompleteComboBoxListener(final ComboBox comboBox, DefaultSearcher searcher) {
        this.comboBox = comboBox;
        this.searcher = searcher;
        sb = new StringBuilder();
        data = comboBox.getItems();

        this.comboBox.setEditable(true);
        this.comboBox.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent t) {
                comboBox.hide();
            }
        });
        
       
        this.comboBox.setOnKeyReleased(AutoCompleteComboBoxListener.this);
       
        
        this.comboBox.focusedProperty().addListener(new ChangeListener<Boolean>() {
 
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (newValue.booleanValue()) {
                        focusGained();
                    } else {
                        focusLost();
                    }
                }

         });
    }

    private void focusGained(){
        System.out.println("Focus Gained.");
    }
 
    private void focusLost(){
        System.out.println("Focus Lost.");
        // Check has Value
        if (this.comboBox.getValue() == null) {
           comboBox.getEditor().setText("");
        }
    }
    
    @Override
    public void handle(KeyEvent event) {

        if(event.getCode() == KeyCode.UP) {
            caretPos = -1;
            moveCaret(comboBox.getEditor().getText().length());
            return;
        } else if(event.getCode() == KeyCode.DOWN) {
            if(!comboBox.isShowing()) {
                comboBox.show();
            }
            caretPos = -1;
            moveCaret(comboBox.getEditor().getText().length());
            return;
        } else if(event.getCode() == KeyCode.BACK_SPACE) {
            moveCaretToPos = true;
            caretPos = comboBox.getEditor().getCaretPosition();
        } else if(event.getCode() == KeyCode.DELETE) {
            moveCaretToPos = true;
            caretPos = comboBox.getEditor().getCaretPosition();
        }

        if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT
                || event.isControlDown() || event.getCode() == KeyCode.HOME
                || event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB) {
            return;
        }

        String searchString = this.comboBox.getEditor().getText();
        System.out.println("searchString "+ searchString);
        ObservableList list = searcher.searchCombo(searchString);
                
        String t = comboBox.getEditor().getText();

        comboBox.setItems(list);
        comboBox.getEditor().setText(t);
        if(!moveCaretToPos) {
            caretPos = -1;
        }
        moveCaret(t.length());
        if(!list.isEmpty()) {
            comboBox.show();
        }
    }

    private void moveCaret(int textLength) {
        if(caretPos == -1) {
            comboBox.getEditor().positionCaret(textLength);
        } else {
            comboBox.getEditor().positionCaret(caretPos);
        }
        moveCaretToPos = false;
    }

     
    
}