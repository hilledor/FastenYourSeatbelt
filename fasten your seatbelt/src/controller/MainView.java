package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author jandorresteijn
 */
public class MainView extends Application {

    public static void main(String[] args) {
        Application.launch(MainView.class, (java.lang.String[]) null);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        fillDataBaseIfEmpty();

        try {
            AnchorPane page = (AnchorPane) FXMLLoader.load(MainView.class.getResource("Main.fxml"));
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Hille's oplossing");
            primaryStage.show();
        } catch (Exception e) {
        }

    }
    

    private boolean fillDataBaseIfEmpty() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/fys2", "root", "root");

        // Check table user via select
        PreparedStatement pstmt = connection.prepareStatement("select * from user limit 1");
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            System.out.println("Er zijn al gegevens");
            return true;
        }
        System.out.println("Nog geen data vul users");

         // if not records insert 3 users
        pstmt = connection.prepareStatement("INSERT into user (firstname, middlename, lastname, email, rol, password, inactive) values (?,?,?,?,?,?,?)");
        pstmt.setString(1,"admin");
        pstmt.setString(2,"van");
        pstmt.setString(3,"Dorresteijn");
        pstmt.setString(4,"jan@base.nl");
        pstmt.setInt(5,1);
        pstmt.setString(6, "admin");
        pstmt.setInt(7,0);
        System.out.println("sql : "+pstmt.toString());
        pstmt.executeUpdate();
        
        return false;
    }
}
