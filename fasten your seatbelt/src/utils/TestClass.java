/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DataEntity;
import model.User;

/**
 *
 * @author jandorresteijn
 */
public class TestClass {

    public static void main(String[] args) {
        TestClass tc = new TestClass();
        tc.doTest1();
    }

    private void doTest1() {
        try {
            Connection conn = DataEntity.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select * from user  where email=? and password = ? limit 1");
            List<Object> params = new ArrayList<Object>();
            params.add( "hille@base.nl" );
            params.add( "admin" );
            DataEntity.addParamsToStatement(pstmt, params);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                System.out.println("Found");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TestClass.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
