/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtrh.mtportal.sys;

/**
 *
 * @author Wilson Gitau
 */
import org.json.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SendSms {

    public static Connection connectDB;
    public static String username;

    public SendSms(String recepients, String message, String refno, Connection connectDB) {
        String username = "mtrh";
        String apiKey = "3eldoret";

        OnfonGateway gateway = new OnfonGateway(username, apiKey);
        String randomAlphanumeric = randomAlphaNumeric(10);
        try {
            String oauth2code = gateway.getAuthToken();
            System.err.println("My length is " + oauth2code.length());
            if (oauth2code.length() > 4) {
                String json = gateway.sendMessageImpro(recepients, message, oauth2code, randomAlphanumeric);

                //JSONObject json_auth= new JSONObject(json);
                //--------------
                // connectDB.setAutoCommit(false);
                if (json.contains("Empty packet")) {
                    new SendSms(recepients, "MTRH ALERT:\n" + message);
                } else {
                    System.err.println("Inserting messages now");
                    java.sql.PreparedStatement pstmt = connectDB.prepareStatement("insert into sms  values(?,?,?,?, current_timestamp, current_user, ?)");
                    pstmt.setObject(1, refno);
                    pstmt.setObject(2, "Success");
                    pstmt.setObject(3, recepients);
                    pstmt.setObject(4, message);
                    pstmt.setObject(5, json);

                    pstmt.executeUpdate();
                }

            }
        } catch (Exception ex) {
            //new SendSms(recepients, "MTRH ALERT:\n" + message);

            ex.printStackTrace();

            String email = UserEmailByPhone(recepients, connectDB);
            EmailClass em = new com.mtrh.mtportal.sys.EmailClass();
            em.SendPlainEmailAlerts(email, message, "One Time Pin[" + refno + "]", connectDB);

        }
    }
    
     private String UserEmailByPhone(String key, Connection conn) {
        
        String login = "";

        String sql = "";

        // sql = "select login_name from secure_password where  staffid = ?";
        sql = "select email from secure_password where phone = ?";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setObject(1, key);
            ResultSet rset = pst.executeQuery();
            if (rset.next()) {
                login = rset.getString(1);
            } else {
                login = "None";
            }

          //  conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return login;
    }

    public SendSms(String recipients, String message) {
        String answer = null;
        // Specify your login credentials
        String username = "salim1990";
        String apiKey = "3596a39ba0ea70acf5cade0dbb1b676d2c5888a5ba00d6b0a0e05962dc4d415e";

        // Create a new instance of our awesome gateway class
        SmsGateway gateway = new SmsGateway(username, apiKey);

        // Thats it, hit send and we'll take care of the rest. Any errors will
        // be captured in the Exception class below
        try {
            JSONArray results = gateway.sendMessage(recipients, message);

            for (int i = 0; i < results.length(); ++i) {
                JSONObject result = results.getJSONObject(i);

                //{messageID:97438753,status: true, number:07000,message:"hjdfjdfj"}
                try {

                    connectDB.setAutoCommit(false);
                    java.sql.PreparedStatement pstmt = connectDB.prepareStatement("insert into sms (messageId,message_status,number,message) values(?,?,?,?)");
                    pstmt.setObject(1, result.getString("messageId"));
                    pstmt.setObject(2, result.getString("status"));
                    pstmt.setObject(3, result.getString("number"));
                    pstmt.setObject(4, result.getString("message"));
                    pstmt.executeUpdate();
                    connectDB.commit();
                    connectDB.setAutoCommit(true);

                } catch (Exception sq) {
                    sq.printStackTrace();

                    try {
                        connectDB.rollback();
                    } catch (java.sql.SQLException sql) {
                        sql.printStackTrace();
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Encountered an error while sending " + e.getMessage());
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
