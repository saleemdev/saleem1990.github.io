/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ihrishandshake;

import com.google.gson.Gson;
import com.google.gson.JsonSerializer;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author owner
 */
public class OnfonGateway {

    private final String _username;
    private final String _apiKey;

    private String ofnfontokenIP = "https://onfon.co.ke:7887/oauth2/token";
    private String onfonSendSMSIP = "https://onfon.co.ke:7887/v1/sendsms/sms";
    public String destination = "MTRH";

    public OnfonGateway(String username_, String apiKey_) {
        _username = username_;
        _apiKey = apiKey_;
    }
    //Bulk messages methods

    public static String getAuthToken() throws Exception { 

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("https://onfon.co.ke:7887/oauth2/token");
        String token = "";
        String username = "mtrh";
        String password = "3eldoret";
        String clientID = "27";
        String clientSecret = "75za6W8wqZCG7pvy";

        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("grant_type", "password"));
            nameValuePairs.add(new BasicNameValuePair("username", username));
            nameValuePairs.add(new BasicNameValuePair("password", password));
            nameValuePairs.add(new BasicNameValuePair("client_id", clientID));
            nameValuePairs.add(new BasicNameValuePair("client_secret", clientSecret));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            System.err.println(new UrlEncodedFormEntity(nameValuePairs));
            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
//3eldoret
//0766519

//2030
            JSONObject json_auth = new JSONObject(EntityUtils.toString(response.getEntity()));
            token = json_auth.getString("access_token");

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return token;
    }

    public static String sendMessage(String to_, String message_, String token, String id) throws Exception {

        return sendMessageImpro(to_, message_, token, id);//michael3270
        //nixon6511
    }

    public static String sendMessageImpro(String to_, String message_, String token, String id) throws Exception {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("https://onfon.co.ke:7887/v1/sendsms/sms");

        String msisdn = to_;
        String destination = "MTRH";
        String message = message_;
        String smsid = id;
        // System.err.println(token);
        httppost.addHeader("Content-Type", "application/json; charset=UTF-8");
        //     httppost.addHeader("Content-Type", "application/json");
        httppost.addHeader("Authorization", "Bearer " + token);
        try {
            HashMap<String, String> child = new HashMap<String, String>();
            // for (int i = 0; i < columns.length; i++) {
            child.put("msisdn", msisdn);
            child.put("destination", destination);
            child.put("sms_id", smsid);
            child.put("message", message);
            
            String json = "["+new Gson().toJson(child)+"]";
            
            System.err.println(json);
            
            StringEntity params = new StringEntity(json.toString());
            httppost.setEntity(params);
            //   child.put("", String.valueOf(leaveBalance));
            //---------------------------------------------
            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            // JSONObject json_auth = new JSONObject(EntityUtils.toString(response.getEntity()));
            //token = json_auth.toString();//.getString("access_token");

            //    org.apache.http.util.EntityUtils.consume(response.getEntity());
            String answer = org.apache.http.util.EntityUtils.toString(response.getEntity());
            token = answer;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return token;
    }
    
    
    public static String[] getBookingList(){
       String sql ="select DISTINCT (patient_no), p_name, clinic,telno,trim(to_char(appointment_date, 'Day DD Month,YYYY')), doctor_req from pb_bookings where telno ilike '07%' and length(telno)=10 and appointment_date::date = current_date + 1";
        
        return null;
    }

    //2705
    public static void main(String[] args) {
        try {

            String token = getAuthToken();

            System.err.println(token);

            String results = sendMessage("0722810063", "First message using MTRH API", token,"3736");

            System.err.println(results);

        } catch (Exception ex) {
            System.out.println("My Exception:");
            ex.printStackTrace();
        }
    }

}
