/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sms;

/**
 *
 * @author Wilson Gitau
 */
import org.json.*;
import java.sql.Connection;

public class SendSms {

    public static Connection connectDB;
    public static String username;

    public SendSms(String recepients, String message, String refno) {
        String username = "mtrh";
        String apiKey = "3eldoret";

        OnfonGateway gateway = new OnfonGateway(username, apiKey);
        String randomAlphanumeric = randomAlphaNumeric(10);
        try {
            String oauth2code = gateway.getAuthToken();
            System.err.println("My length is " + oauth2code.length());
            if (oauth2code.length() > 4) {
                String json = gateway.sendMessageImpro(recepients, message, oauth2code, randomAlphanumeric);

              


            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
