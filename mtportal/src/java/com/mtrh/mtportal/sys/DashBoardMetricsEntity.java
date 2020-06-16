/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtrh.mtportal.sys;

/**
 *
 * @author owner
 */
public class DashBoardMetricsEntity {

 
    

    /**
     * @return the vehicles
     */
    public static String getVehicles() {
        if(vehicles==null){
            vehicles="0";
        }
        return vehicles;
    }
    /**
     * @param aVehicles the vehicles to set
     */
    public static void setVehicles(String aVehicles) {
        vehicles = aVehicles;
    }

    /**
     * @return the requisitions
     */
    public static String getRequisitions() {
        
        if(requisitions==null){
            requisitions="0";
        }
        return requisitions;
    }

    /**
     * @param aRequisitions the requisitions to set
     */
    public static void setRequisitions(String aRequisitions) {
        requisitions = aRequisitions;
    }

    /**
     * @return the workorders
     */
    public static String getWorkorders() {
        if(workorders==null){
            workorders="0";
        }
        return workorders;
    }

    /**
     * @param aWorkorders the workorders to set
     */
    public static void setWorkorders(String aWorkorders) {
        workorders = aWorkorders;
    }
    
    /**
     * @return the active_drivers
     */
    public static String getActive_drivers() {
        if(active_drivers==null){
            active_drivers ="0";
        }
        return active_drivers;
    }

    /**
     * @param aActive_drivers the active_drivers to set
     */
    public static void setActive_drivers(String aActive_drivers) {
        active_drivers = aActive_drivers;
    }

   
    private static String active_drivers;
    private static String vehicles;
    private static String requisitions;
    private static String workorders;

    
    
    
}
