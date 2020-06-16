/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtrh.mtportal.sys;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author owner
 */
public class CardTransactions {

    /**
     * @return the amount
     */
    
    
    //getFleetRInvoice
    
    public Object[] getFleetRInvoice(String refno, java.sql.Connection connectionDB) {

        Object[] toreturn = new Object[]{};
        
        Vector V = new Vector();
        try {
            PreparedStatement pst = connectionDB.prepareStatement("SELECT requestitem, units,qty,qty*units::numeric as total, cash_words((qty*units::numeric)::money) FROM fleet.transport_rq_invoice WHERE requestid =?");
            pst.setObject(1, refno);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                V.add(rset.getObject(1));
                V.add(rset.getObject(2));
                V.add(rset.getObject(3));
                V.add(rset.getObject(4));
                V.add(rset.getObject(5));
                //V.add(rset.getObject(1));
            }
            
            toreturn= V.toArray();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return toreturn;
    }

    
    public String getCardAmount(String cardno, java.sql.Connection connectionDB) {

        try {
            PreparedStatement pst = connectionDB.prepareStatement("SELECT sum(debit-credit) FROM fleet.cardtransactions WHERE cardno =  ? ");
            pst.setObject(1, cardno);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
              
                amount = String.valueOf(rset.getDouble(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return amount;
    }

    public void setAmount(String cardNo, String narration, String transtype, String user, Double amount, java.sql.Connection connectionDB) {
        try {
            PreparedStatement pst = connectionDB.prepareStatement("insert into fleet.cardtransactions values(?,?,?,?,?)");
            pst.setObject(1, cardNo);
            if (transtype.equalsIgnoreCase("debit")) {
                pst.setObject(2, amount);
            } else {
                pst.setObject(2, 0.0);
            }
            if (transtype.equalsIgnoreCase("credit")) {
                pst.setObject(3, amount);

            } else {
                pst.setObject(3, 0.0);

            }
            pst.setObject(4, narration);
            pst.setObject(5, user);
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public String cardtransactions(String cardno, String date1, String date2, java.sql.Connection connectionDB) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent List

        String[] columns = new String[]{"make"};
        try {
            PreparedStatement pst = connectionDB.prepareStatement(" SELECT * FROM fleet.cardtransactions WHERE cardno = ? AND datestamp::date BETWEEN ? AND ? ");
            pst.setObject(1, cardno);
            pst.setObject(2, date1);
            pst.setObject(3, date2);

            ResultSet rset = pst.executeQuery();
            
            while (rset.next()) {
                HashMap<String, String> child = new HashMap<String, String>();
                for (int i = 0; i < columns.length; i++) {
                    child.put("make1", rset.getObject(1).toString().toUpperCase());
                }
                parentList.add(child);
    //            j++;
            }
        }
    
    catch (SQLException ex

    
        ) {
            ex.printStackTrace();
    }
    return json ;
}

String amount;
    String json;

}
