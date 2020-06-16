/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtrh.fleet.communication;

import com.mtrh.fleet.dao.ConnectionProperties;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author owner
 */
public class CommunicationList {
    
    
    public static String getUserEmail(){
     return ConnectionProperties.getEmail();
    }

    public void setCommunicationList(String refno, Object[] users, java.sql.Connection connectDB) {

        String sql = "INSERT INTO fleet.mailinglist(\n"
                + "            rqid, username, active)\n"
                + "    VALUES (?, ?, ?);";

        for (int i = 0; i < users.length; i++) {
            try {
                PreparedStatement pst = connectDB.prepareStatement(sql);
                pst.setObject(1, refno);
                pst.setObject(2, users[i]);
                pst.setObject(3, true);
                pst.executeUpdate();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }

    }

    public void removeUserFromCommsList(String refno, Object[] users, java.sql.Connection connectDB) {

//        String sql = "INSERT INTO fleet.mailinglist(\n"
//                + "            rqid, username, active)\n"
//                + "    VALUES (?, ?, ?);";
        String sql = "UPDATE fleet.mailinglist SET active = false WHERE rqid = ? AND username = ? ;";

        for (int i = 0; i < users.length; i++) {
            try {
                PreparedStatement pst = connectDB.prepareStatement(sql);
                pst.setObject(1, refno);
                pst.setObject(2, users[i]);
                //  pst.setObject(3, true);
                pst.executeUpdate();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }

    }
    public void purgeCommunicationList(String refno, java.sql.Connection connectDB) {
        String sql = "UPDATE fleet.mailinglist SET active = false WHERE rqid = ?  ;";

    //    for (int i = 0; i < users.length; i++) {
            try {
                PreparedStatement pst = connectDB.prepareStatement(sql);
                pst.setObject(1, refno);
                pst.executeUpdate();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

     //   }

    }
    
    
   
    
    public Object[] getMailingList(String refid, java.sql.Connection connectDB){
        Object[]mailinglist = new Object[]{};
        
        Vector list = new Vector();
        
        String sql = "SELECT DISTINCT username FROM fleet.mailinglist WHERE rqid = ? AND active = true ";
          try {
                PreparedStatement pst = connectDB.prepareStatement(sql);
                pst.setObject(1, refid);
                ResultSet rset = pst.executeQuery();
                while(rset.next()){
                    list.add(rset.getObject(1));
                }
                mailinglist = list.toArray();
                     
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        return mailinglist;
    }


}
