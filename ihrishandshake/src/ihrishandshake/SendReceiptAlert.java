/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ihrishandshake;

import static ihrishandshake.OnfonGateway.getAuthToken;
import static ihrishandshake.OnfonGateway.sendMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import lib.SendSms;

/**
 *
 * @author owner
 */
public class SendReceiptAlert {

    private static Object dbServerIp;
    private static Object dbPort;
    private static Object activeDatabase;

    public SendReceiptAlert() {

    }

    public static java.sql.Connection custom_connect(String user, String password, String url, String port, String db) {
        Connection connection = null;

        try {

            java.lang.Class.forName("org.postgresql.Driver");

        } catch (java.lang.ClassNotFoundException cnf) {

            cnf.printStackTrace();

        }

        try {

            // if (dbServerIp == null) {
            // dbServerIp = "localhost"jj;
            dbServerIp = url;
            //}

            //   if (dbPort == null) {
            dbPort = port;
            // }

            //if (activeDatabase == null) {
            activeDatabase = db;
            //}
            //  System.out.println("jdbc:postgresql://" + this.dbServerIp + ":" + dbPort + "/" + activeDatabase + " " + userName + " " + passWord);
            connection = java.sql.DriverManager.getConnection("jdbc:postgresql://" + dbServerIp + ":" + dbPort + "/" + activeDatabase, user, password);
            System.err.print("jdbc:postgresql://" + dbServerIp + ":" + dbPort + "/" + activeDatabase);
//            if (connection != null) {
//                JOptionPane.showMessageDialog(new java.awt.Frame(), "connected");
//            }
//            else{
//                  JOptionPane.showMessageDialog(new java.awt.Frame(), "Not connected");
            System.err.println(System.getProperty("user.dir") + System.getProperty("file.separator") + "logo.jpg");
//            }
        } catch (java.sql.SQLException sqlExec) {

            //     msg = sqlExec.getMessage().toString();
            System.err.println(System.getProperty("user.dir"));

            //     Accurate = false;
            //javax.swing.JOptionPane.showMessageDialog(this, "ERROR : Logon denied due to incorrect username & password,\n network disconnection or dataserver not running!\n\nERROR DETAILS : \n[" + sqlExec.getMessage() + "]");
            sqlExec.printStackTrace();
            //   this.setVisible(true);

        }
        return connection;
    }

    public static java.sql.Connection connect(String user, String password) {
        Connection connection = null;

        try {

            java.lang.Class.forName("org.postgresql.Driver");

        } catch (java.lang.ClassNotFoundException cnf) {

            cnf.printStackTrace();

        }
        try {

            if (dbServerIp == null) {
                // dbServerIp = "localhost"jj;
                dbServerIp = PropertiesFunctions.getpropValue("dbServerIpAdd").toString();
            }

            if (dbPort == null) {
                dbPort = PropertiesFunctions.getpropValue("dbPort").toString();
            }

            if (activeDatabase == null) {
                activeDatabase = PropertiesFunctions.getpropValue("activeDatabase").toString();
            }
            //  System.out.println("jdbc:postgresql://" + this.dbServerIp + ":" + dbPort + "/" + activeDatabase + " " + userName + " " + passWord);
            connection = java.sql.DriverManager.getConnection("jdbc:postgresql://" + SendReceiptAlert.dbServerIp + ":" + dbPort + "/" + activeDatabase, user, password);

            if (connection != null) {
                System.err.println("connected");
            }
        } catch (java.sql.SQLException sqlExec) {

            System.err.println(System.getProperty("user.dir"));

            sqlExec.printStackTrace();
            //   this.setVisible(true);

        }
        return connection;
    }
    //select transaction_no, case when cash_point not ilike '%cashpoint' then upper(cash_point) ||' Cashpoint' else upper(cash_point) end as cashpoint 
    //from ac_cash_collection where  payment_mode ilike 'm%pesa';
    //273469.00
    //51 821 5400.75

    public static void markIDAsChecked(String id, Connection conn) {
        String sql = "insert into sms.alertedtxs values(?)";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setObject(1, id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static Object getAlertDetailsByTx_id(String reqid, Connection conn) {
        Object data = new Object();
      //  Vector v = new Vector();
        String sql = "select telephone_number||'/'||mobile_tx_id||'/'||sum(paid_amount) from mobile_payments WHERE checkout_request_id = ? AND mobilepay_alert is TRUE GROUP BY mobile_tx_id, telephone_number;";

        //select telephone_number||'/'||mobile_tx_id||'/'||sum(paid_amount) from mobile_payments WHERE checkout_request_id = 'ws_CO_DMZ_267755382_14032019110131024' AND mobilepay_alert is TRUE GROUP BY mobile_tx_id, telephone_number;
        //  System.err.println("select telephone_number||'/'||mobile_tx_id||'/'||sum(paid_amount) from mobile_payments WHERE checkout_request_id = '" + reqid + "' AND mobilepay_alert is TRUE GROUP BY mobile_tx_id, telephone_number;");
        //select telephone_number||'/'||mobile_tx_id||'/'||sum(paid_amount) from mobile_payments WHERE checkout_request_id = 'ws_CO_DMZ_122313193_01112018145404838' AND mobilepay_alert is TRUE GROUP BY mobile_tx_id, telephone_number;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setObject(1, reqid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                //  v.add(rs.getObject(1));

                data = rs.getObject(1);

                // System.err.println(rs.getObject(1));
            }else{
                
            data ="M";
        }
            // data = v.toArray();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return data;
    }

    public static Object getAlertDetailsByTx_id_pull(String reqid, Connection conn) {
        Object data = new Object();
        Vector v = new Vector();
        String sql = "select account_no||'/'||journal_no||'/'||sum(debit) from ac_cash_collection WHERE checkout_request_id = ? GROUP BY account_no, journal_no;";

        //select telephone_number||'/'||mobile_tx_id||'/'||sum(paid_amount) from mobile_payments WHERE checkout_request_id = 'ws_CO_DMZ_267755382_14032019110131024' AND mobilepay_alert is TRUE GROUP BY mobile_tx_id, telephone_number;
        //  System.err.println("select telephone_number||'/'||mobile_tx_id||'/'||sum(paid_amount) from mobile_payments WHERE checkout_request_id = '" + reqid + "' AND mobilepay_alert is TRUE GROUP BY mobile_tx_id, telephone_number;");
        //select telephone_number||'/'||mobile_tx_id||'/'||sum(paid_amount) from mobile_payments WHERE checkout_request_id = 'ws_CO_DMZ_122313193_01112018145404838' AND mobilepay_alert is TRUE GROUP BY mobile_tx_id, telephone_number;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setObject(1, reqid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                //  v.add(rs.getObject(1));

                data = rs.getObject(1);

                // System.err.println(rs.getObject(1));
            }
            // data = v.toArray();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return data;
    }

    public static String getINPatientNameByNo(Connection conn, String clientID) {
        String name = "";
        String sql = "select patient_name FROM hp_admission WHERE patient_no = ? LIMIT 1;";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setObject(1, clientID);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                name = rs.getObject(1).toString();

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return name;
    }

    public static String getPatientNameByNo(Connection conn, String clientID) {
        String name = "";
        String sql = "select first_name ||' '||second_name FROM hp_patient_register WHERE patient_no = ? LIMIT 1;";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setObject(1, clientID);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                name = rs.getObject(1).toString().trim();

                if (name.length() < 1) {
                    name = getINPatientNameByNo(conn, clientID);
                }

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return name;
    }

    public static Object[] getUnNotifiedTransactions(Connection conn) {
        //select first_name||' '||second_name from hp_patient_register where patient_no='0187718';
        Object[] txs = new Object[]{};
        Vector v = new Vector();
        String sql = "select DISTINCT checkout_request_id||'/'||patient_no from ac_cash_collection where payment_mode ilike 'm%pesa' and length(checkout_request_id)>5 and checkout_request_id NOT IN (SELECT coutid FROM sms.alertedTxs);";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                v.add(rs.getObject(1));

            }
            txs = v.toArray();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return txs;
    }

    //http://www.fixedbyvonnie.com/2014/11/virtualbox-showing-32-bit-guest-versions-64-bit-host-os/
    public static void main(String[] args) {
        try {

            Connection conn = custom_connect("salim7915", "info27613716", "172.16.106.1", "5432", "funsoft");

            Connection bespoke = custom_connect("postgres", "sequence", "172.16.103.200", "5433", "bespoke");

            // String token = getAuthToken();
            //String results = sendMessage("0722810063", "Testing...", token, randomAlphaNumeric(10));
            //   new SendSms("0722810063", "Testing stuff", "848899409", conn);
            //   System.err.println(token + " and length " + token.length());
            //if (token.length() > 5) {
            Object[] txs = getUnNotifiedTransactions(conn);
            for (int i = 0; i < txs.length; i++) {
                String det[] = txs[i].toString().split("/");
                String checkoutID = det[0];
                String clientid = det[1];

                String name = getPatientNameByNo(conn, clientid);
                String moneyString = getAlertDetailsByTx_id(checkoutID, conn).toString();
                
                System.err.println(moneyString.toString().length()+" is the length");
                if (moneyString.equals(null) || moneyString.isEmpty() || moneyString.length() < 2) {
                    moneyString = getAlertDetailsByTx_id_pull(checkoutID, conn).toString();
                }

                System.err.println("Moneystuff for: " + checkoutID + ": " + moneyString);
                Object[] moneyStuff = moneyString.split("/");
                //
                //  System.err.println("Money stuff length" + moneyStuff.length);

                if (moneyStuff.length > 2) {

                    //   System.err.println("Checkout ID inserted " + checkoutID);
                    String tel = moneyStuff[0].toString();
                    Double amount = Double.valueOf(moneyStuff[2].toString());
                    String msg = "Your payment of KES " + CurrencyFormatter.getFormattedDouble(amount) + " to MTRH for Hospital IP/OP Number:" + clientid + " has COMPLETED.\nMPESA Transaction Number: " + moneyStuff[1] + "\nwww.mtrh.go.ke";
                    System.err.println("Sending Msg to: " + tel + ":\n\n" + msg + "\n");

                    //String results = sendMessage("0722810063", msg, token, randomAlphaNumeric(10)+""+tel);
                    new SendSms(tel, msg, randomAlphaNumeric(i), bespoke);
                    markIDAsChecked(checkoutID, conn);
                    //System.err.println(results);
                }
            }
            //  }
        } catch (Exception ex) {
            System.out.println("My Exception:");
            ex.printStackTrace();
        }
    }

    private static final String ALPHA_NUMERIC_STRING = "0123456789";

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

}
