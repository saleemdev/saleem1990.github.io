/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtrh.fleet.dao;
import org.json.*;
/**
 *
 * @author owner
 */
public class JSONObjects {

    /**
     * @return the userDetails
     */
    public JSONObject getUserDetails() {
        return userDetails;
    }

    /**
     * @param userDetails the userDetails to set
     */
    public void setUserDetails(JSONObject userDetails) {
        this.userDetails = userDetails;
    }

    /**
     * @return the vehicleDetails
     */
    public JSONObject getVehicleDetails() {
        return vehicleDetails;
    }

    /**
     * @param vehicleDetails the vehicleDetails to set
     */
    public void setVehicleDetails(JSONObject vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }

    /**
     * @return the requestdetails
     */
    public JSONObject getRequestdetails() {
        return requestdetails;
    }

    /**
     * @param requestdetails the requestdetails to set
     */
    public void setRequestdetails(JSONObject requestdetails) {
        this.requestdetails = requestdetails;
    }

    /**
     * @return the driverdetails
     */
    public JSONObject getDriverdetails() {
        return driverdetails;
    }

    /**
     * @param driverdetails the driverdetails to set
     */
    public void setDriverdetails(JSONObject driverdetails) {
        this.driverdetails = driverdetails;
    }
    public JSONObjects(){}
    
    private JSONObject userDetails;
    private JSONObject vehicleDetails;
    private JSONObject requestdetails;
    private JSONObject driverdetails;
    
}
