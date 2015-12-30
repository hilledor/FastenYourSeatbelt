/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Utils;

/**
 *
 * @author jandorresteijn
 */
public abstract class DataEntity implements Tabel {

    public enum DataType {

        ID,
        INT,
        STRING,
        DATETIME,
    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface Column {

        DataType dataType();
    }

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/fys2", "root", "root");
            return conn;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataEntity.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DataEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void delete(int id) throws IllegalAccessException {
        Field idField = getIdField();
        idField.set(this, id);
        delete();
    }

    public void delete() {
        try {
            Connection conn = getConnection();
            Statement statement = conn.createStatement();
            Field idField = getIdField();
            PreparedStatement pstmt = conn.prepareStatement("delete from " + getTable()
                    + " where " + idField.getName() + " = " + idField.getLong(this));
        } catch (SQLException ex) {
            Logger.getLogger(DataEntity.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(DataEntity.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DataEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void load(int id) throws IllegalAccessException {
        Field idField = getIdField();
        idField.set(this, id);
        load();
    }

    public void load() {
        try {
            Connection conn = getConnection();
            Field idField = getIdField();
            PreparedStatement pstmt = conn.prepareStatement("select * from " + getTable()
                    + " where " + idField.getName() + " = " + idField.getLong(this));
            System.out.println("sql query " + pstmt.toString());
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()){
                getNew();
            }
            // Laad velden via annotatie
            Class cls = getClass();
            for (Field field : cls.getDeclaredFields()) {
                for (Annotation anno : field.getAnnotations()) {
                    if (anno instanceof Column) {
                        Column col = (Column) anno;
                        DataType dt = col.dataType();
                        switch (dt) {
                            case ID:
                                field.set(this, rs.getInt(field.getName()));
                                break;
                            case STRING:
                                field.set(this, rs.getString(field.getName()));
                                break;
                            case INT:
                                field.set(this, rs.getInt(field.getName()));
                                break;
                            case DATETIME:
                                java.sql.Date myDate = rs.getDate(field.getName());
                                Calendar cal = Calendar.getInstance();
                                field.set(this, myDate.getTime());
                                break;
                        }
                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataEntity.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(DataEntity.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DataEntity.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void getNew() {
        Class cls = getClass();
        for (Field field : cls.getDeclaredFields()) {
            for (Annotation anno : field.getAnnotations()) {
                if (anno instanceof Column) {
                    try {
                        Column col = (Column) anno;
                        DataType dt = col.dataType();
                        switch (dt) {
                            case ID:
                                field.set(this, -1);
                                break;
                            case STRING:
                                field.set(this, "");
                                break;
                            case INT:
                                field.set(this, 0);
                                break;
                            case DATETIME:
                                field.set(this, Calendar.getInstance());
                                break;
                        }
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(DataEntity.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(DataEntity.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

    }

    public Field getIdField() {
        Class cls = getClass();
        for (Field field : cls.getDeclaredFields()) {
            for (Annotation anno : field.getAnnotations()) {
                if (anno instanceof Column) {
                    Column col = (Column) anno;
                    if (col.dataType().equals(DataType.ID)) {
                        return field;
                    }
                }
            }
        }
        // throw RuntimeException("No ID Field");
        return null;
    }
    
    
    public void save(){
        try {
            
            Connection conn = getConnection();
            Statement statement = conn.createStatement();
            Field idField = getIdField();
            PreparedStatement pstmt ;
  
            String updateString = "";  // Make update String
            String insertField = "";  // Make insert fields String
            String insertQuestionMark = "";  // Make insert Questionmark String
           
            
            // Laad velden via annotatie
            // Eerst veld specifieke 
            Class cls = getClass();
            for (Field field : cls.getDeclaredFields()) {
                for (Annotation anno : field.getAnnotations()) {
                    if (anno instanceof Column) {
                         Column col = (Column) anno;
                        DataType dt = col.dataType();
                        switch (dt) {
                            case ID:
                               
                                break;
                            default:
                                updateString = Utils.glue(updateString , field.getName()+" = ? "," , ");
                                insertField  = Utils.glue(insertField , field.getName()," , ");
                                insertQuestionMark= Utils.glue(insertQuestionMark , " ? ",",");
                        }
                    }
                }
            }

            int key = idField.getInt(this);
            if (key == -1){         // Insert
                pstmt = conn.prepareStatement("insert into " + getTable()
                    + " ( " + insertField + " ) values ( " + insertQuestionMark +" )");
            } else {
                pstmt = conn.prepareStatement("update " + getTable()
                    +" set " + updateString +" where " + idField.getName() + " = " + idField.getLong(this));
            }
       
            System.out.println("sql query 1 " + pstmt.toString());
            // Laad velden via annotatie
            // Eerst veld specifieke 
            int counter = 0;
            
            for (Field field : cls.getDeclaredFields()) {
                for (Annotation anno : field.getAnnotations()) {
                    if (anno instanceof Column) {
                        counter += 1;
                        Column col = (Column) anno;
                        DataType dt = col.dataType();
                        switch (dt) {
                            case ID:
                                counter -= 1;   // Id doet niet mee wordt niet geupdate
                                break;
                            case STRING:
                                pstmt.setString(counter, field.get(this).toString());
                                break;
                            case INT:
                                pstmt.setInt(counter, field.getInt(this));
                                break;
                            case DATETIME:
                                Calendar cal = (Calendar) field.get(this);
                                
                                pstmt.setLong(counter, cal.getTimeInMillis());
                                break;
                        }
                    }
                }
            }
            System.out.println("sql query " + pstmt.toString());
            pstmt.executeUpdate();
    

            
        } catch (SQLException ex) {
            Logger.getLogger(DataEntity.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(DataEntity.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DataEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
}
