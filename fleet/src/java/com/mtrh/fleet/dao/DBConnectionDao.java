/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtrh.fleet.dao;

import com.mtrh.fleet.dao.ConnectionProperties;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author owner
 */
public class DBConnectionDao {

    public DBConnectionDao() {
    }

    public void getConnectionAttributes(java.sql.Connection connect2DB, Object user) {

        try {
            PreparedStatement pst = connect2DB.prepareStatement("select login_name,fullname, email,phone,logo, designation from secure_password where login_name = ?;");
           pst.setObject(1, user);
            ResultSet rset = pst.executeQuery();
            while(rset.next()){
                ConnectionProperties.setUserlogin(rset.getObject(1).toString());
                ConnectionProperties.setUsername(rset.getObject(2).toString());
                ConnectionProperties.setEmail(rset.getObject(3).toString());
                ConnectionProperties.setPhone(rset.getObject(4).toString());
                
                if(rset.getBinaryStream(5)!=null){
                ConnectionProperties.setIs(rset.getBinaryStream(5));
                    try {
                        ConnectionProperties.setImg(ImageIO.read(rset.getBinaryStream(5)));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                ConnectionProperties.setDesignation(rset.getObject(6).toString());
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnectionDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
