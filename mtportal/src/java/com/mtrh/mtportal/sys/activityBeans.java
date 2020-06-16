/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtrh.mtportal.sys;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author owner
 */
public class activityBeans {

    /**
     * @return the json
     */
    public static String getJson() {
        return json;
    }

    /**
     * @param aJson the json to set
     */
    public static void setJson(String aJson) {
        json = aJson;
    }

    /**
     * @return the dataJSON
     */
    public static JSONObject getDataJSON() {
       System.err.println("This is what I got: "+dataJSON.toString());
        return dataJSON;
    }

    /**
     * @param aDataJSON the dataJSON to set
     */
    public static void setDataJSON(JSONObject aDataJSON) {
        dataJSON = aDataJSON;
    }

    /**
     * @return the dataJARRAY
     */
    public static JSONArray getDataJARRAY() {
      //  System.out.println(dataJARRAY.toString());
        return dataJARRAY;
    }

    /**
     * @param aDataJARRAY the dataJARRAY to set
     */
    public static void setDataJARRAY(JSONArray aDataJARRAY) {
        dataJARRAY = aDataJARRAY;
    }

    public activityBeans() {
    }
  private static  JSONObject dataJSON;
  private static  JSONArray dataJARRAY;
  private static String json;
    
}
