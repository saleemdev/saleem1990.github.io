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
public class VehicleBeans {

    /**
     * @return the vehiclesJson
     */
    public JSONObject getVehiclesJson() {
        return vehiclesJson;
    }

    /**
     * @param vehiclesJson the vehiclesJson to set
     */
    public void setVehiclesJson(JSONObject vehiclesJson) {
        this.vehiclesJson = vehiclesJson;
    }

    /**
     * @return the vehicleArr
     */
    public JSONArray getVehicleArr() {
        return vehicleArr;
    }

    /**
     * @param vehicleArr the vehicleArr to set
     */
    public void setVehicleArr(JSONArray vehicleArr) {
        this.vehicleArr = vehicleArr;
    }

    /**
     * @return the vehiclesJson
     */
    public String getVehiclesStringJson() {
        return vehiclesStringJson;
    }

    /**
     * @param vehiclesJson the vehiclesJson to set
     */
    public void setVehiclesStringJson(String vehiclesJson) {
        this.vehiclesStringJson = vehiclesJson;
    }

    /**
     * @return the vehicleAttibutes
     */
    public Object[] getVehicleAttibutes() {
        return vehicleAttibutes;
    }

    /**
     * @param vehicleAttibutes the vehicleAttibutes to set
     */
    public void setVehicleAttibutes(Object[] vehicleAttibutes) {
        this.vehicleAttibutes = vehicleAttibutes;
    }

    public VehicleBeans() {
    }

    private String vehiclesStringJson;
    private JSONObject vehiclesJson;

    private JSONArray vehicleArr;
    private Object[] vehicleAttibutes;

}
