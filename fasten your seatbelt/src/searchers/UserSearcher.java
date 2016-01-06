/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.DataEntity;
import model.User;
import utils.Utils;

/**
 *
 * @author jdor
 */
public class UserSearcher extends DefaultSearcher implements searcher {
    
    
    public  ObservableList<User> searchCombo(String searchText){
        this.searchText = searchText;
        ObservableList<User> list = FXCollections.observableArrayList();
        try {
             Connection conn = DataEntity.getConnection();
            
            // Definer paramsList
            List<Object> params = new ArrayList<Object>();
            
            // Maak SQL string
            String sqlString = "SELECT * FROM user ";
            sqlString += getWhereClause(params, searchText);
            sqlString += " ORDER BY lastname ";
            System.out.println("sqlString = "+ sqlString);
            
            PreparedStatement pstmt = conn.prepareStatement(sqlString);
            DataEntity.addParamsToStatement(pstmt, params);
            
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
        } catch (SQLException ex) {
            Logger.getLogger(UserSearcher.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
       
    }
    
    public String getWhereClause( List<Object> params, String searchText) {
        this.searchText = searchText;
        String whereString = "";
        
        /*
        // Check Rol
        User.Rol sRol = User.Rol.getRol("" + rolSearch.getValue());
        if (sRol != null) {
            whereString += " rol = ? " ;
            params.add(sRol.getId());
        }
        */
        
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

    
    
    
 
}
