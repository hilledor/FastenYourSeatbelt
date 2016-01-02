package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.DataEntity;
import model.User;
import utils.Utils;

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
        // fill database if applicable
        fillDataBaseIfEmpty();

        FXMLLoader loader = new FXMLLoader(MainView.class.getResource("Login.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
     
        Scene scene = new Scene(page);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hille's oplossing");

        // 
        FouteStatic.primaryStage = primaryStage;

        primaryStage.show();
        
        LoginController lc = loader.getController();


        // Check Admin user
        System.out.println("Check Admin password ");
        if (lc == null){
            System.out.println("lc ============= nul");
        }
        User user = lc.searchUser("hille@base.nl", "admin");
        if (user.isNew()) {
            System.out.println("Admin password aangepast");
            // Hide hint
            lc.hint.setVisible(false);
        } else {
            lc.email.setText("hille@base.nl");
            lc.password.setText("admin");
            System.out.println("passw. " + user.getPassword());
        }

    }

    private boolean fillDataBaseIfEmpty() throws ClassNotFoundException, SQLException {
        System.out.println("fillDataBaseIfEmptyfillDataBaseIfEmpty ");
        Connection connection = DataEntity.getConnection();

        // Check table user via select
        PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM user LIMIT 1");
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            System.out.println("Er zijn al gegevens");
            return true;
        }
        System.out.println("Nog geen data vul users");

        // if not records insert 3 users
        pstmt = connection.prepareStatement("INSERT into user (firstname, middlename, lastname, email, rol, password, inactive) values (?,?,?,?,?,?,?)");
        pstmt.setString(1, "admin");
        pstmt.setString(2, "van");
        pstmt.setString(3, "Dorresteijn");
        pstmt.setString(4, "hille@base.nl");
        pstmt.setInt(5, 1);
        pstmt.setString(6, "admin");
        pstmt.setInt(7, 0);
        System.out.println("sql : " + pstmt.toString());
        pstmt.executeUpdate();

        //Kan ook via User
        User user = new User(-1, "Piet", "", "Puk", "piet@puk.nl", 3, "Pietje");
        user.save();
        
        return false;
    }

}
