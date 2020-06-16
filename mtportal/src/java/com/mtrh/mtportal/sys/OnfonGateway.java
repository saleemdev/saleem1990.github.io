/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtrh.mtportal.sys;

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
import javax.ws.rs.core.MediaType;
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
import servlets.PropertiesClass;

/**
 *
 * @author owner
 */
public class OnfonGateway {

    public static String _username;
    public static String _apiKey;

    public static String _TOKEN_URL = PropertiesClass.getpropValue("tokenurl").toString();// The URL for Receiving the token e.g "https://onfon.co.ke:7887/oauth2/token";
    public static String _SMS_URL = PropertiesClass.getpropValue("smsurl").toString(); //The URL for sending the sms e.g "https://onfon.co.ke:7887/v1/sendsms/sms";
    public static String _DESTINATION = PropertiesClass.getpropValue("facilitycode").toString();//Facility_ID provided by BULK SMS provider

    public OnfonGateway(String username_, String apiKey_) {
        _username = username_;
        _apiKey = apiKey_;
    }
    //Bulk messages methods

    public String getAuthToken() throws Exception {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(_TOKEN_URL);
        String token = "";
        String username = _username;
        String password = _apiKey;
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
           // httppost.addHeader("Content-Type", "application/json; charset=UTF-8");
//            HashMap<String, String> child = new HashMap<String, String>();
//            // for (int i = 0; i < columns.length; i++) {
//            child.put("grant_type", "password");
//            child.put("username", username);
//            child.put("password", password);
//            child.put("client_id", clientID);
//            child.put("client_secret", clientSecret);
//
//            String json = "[" + new Gson().toJson(child) + "]";
//
//            System.err.println(json);
//
//            StringEntity params = new StringEntity(json.toString());
//            httppost.setEntity(params);
            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

          //  JSONObject json_auth = new JSONObject(EntityUtils.toString(response.getEntity()));
           String answer = org.apache.http.util.EntityUtils.toString(response.getEntity());
           System.err.println(answer);
           JSONObject json_auth= new JSONObject(answer);

            token = json_auth.getString("access_token");

            System.err.println(token);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return token;
    }

    public String sendMessage(String to_, String message_, String token, String id) throws Exception {

        return sendMessageImpro(to_, message_, token, id);//michael3270
        //nixon6511
    }

    public String sendMessageImpro(String to_, String message_, String token, String id) throws Exception {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(_SMS_URL);

        String msisdn = to_;
        String destination = _DESTINATION;
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

            String json = "[" + new Gson().toJson(child) + "]";

            System.err.println(json);

            StringEntity params = new StringEntity(json.toString());
            httppost.setEntity(params);

            //Execute POST
            HttpResponse response = httpclient.execute(httppost);
            String answer = org.apache.http.util.EntityUtils.toString(response.getEntity());
            token = answer;
            System.err.println("My message token is "+token);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return token;
    }
    
   

}
