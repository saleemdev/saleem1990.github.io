/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtrh.mtportal.sys;

import com.google.gson.Gson;
import com.mtrh.mtportal.sys.DashBoardMetricsEntity;
import com.mtrh.mtportal.sys.activityBeans;
//import com.mtrh.mtportal.sys.CurrencyFormatter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author owner
 */
public class DashboardMetricsDao {

    public DashboardMetricsDao() {
    }

    public static void getAndSetDashmetrics(java.sql.Connection connectDB) {
        String sqldrv = "select count(*) from secure_password where designation ILIKE 'driver'",
                sqlveh = "select count (fleetno) from fleet.vehicle_register ",
                sqlreq = "select count(distinct(rqid)) from fleet.transportrequestmemo where approved is null AND enteredon::date > current_date -8 ",
                sqltickets = "select count(distinct(rqid)) from fleet.transportrequestmemo where ticket is true AND enteredon::date > current_date -8";

        System.out.println(sqldrv + "\n" + sqlveh + "\n" + "\n" + sqlreq + "\n" + "\n" + sqltickets);
        try {
            PreparedStatement pst = connectDB.prepareStatement(sqldrv);
            ResultSet rset = pst.executeQuery();

            PreparedStatement pst1 = connectDB.prepareStatement(sqlveh);
            ResultSet rset1 = pst1.executeQuery();

            PreparedStatement pst2 = connectDB.prepareStatement(sqlreq);
            ResultSet rset2 = pst2.executeQuery();

            PreparedStatement pst3 = connectDB.prepareStatement(sqltickets);
            ResultSet rset3 = pst3.executeQuery();

            DashBoardMetricsEntity dao = new DashBoardMetricsEntity();

            Integer vehicles = 0, drivers = 0, requisitions = 0, orders = 0;

            while (rset.next()) {

                dao.setActive_drivers(String.valueOf(rset.getInt(1)));
                System.out.println("Drivers: " + rset.getInt(1));

            }

            while (rset1.next()) {
                dao.setVehicles(String.valueOf(rset1.getInt(1)));
                System.out.println("Vehicles: " + rset1.getInt(1));

            }

            while (rset2.next()) {
                dao.setRequisitions(String.valueOf(rset2.getInt(1)));
                System.out.println("Requisitions: " + rset2.getInt(1));

            }
            while (rset3.next()) {
                dao.setWorkorders(String.valueOf(rset3.getInt(1)));
                System.out.println("Tickets: " + rset3.getInt(1));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

//    INSERT INTO fleet.activityactions(
//            id, action, username, timestamp_)
//    VALUES (?, ?, ?, ?);
    public static void getAndAndPrepareActivitiesJSON(java.sql.Connection connectDB, String rank) {

        String sql ="";
        
        
        sql = "SELECT rqid, rqtype, enteredon::timestamp(0), requestedby, \n"
                + "(CASE \n"
                + "    WHEN approved IS null then 'pending' \n"
                + "    WHEN approved IS false THEN 'rejected'\n"
                + "    ELSE 'approved'\n"
                + "END) \n"
                + "\n"
                + "\n"
                + "FROM fleet.transportrequestmemo \n"
                + "WHERE enteredon::date > current_date - 300  ORDER BY 5 desc";;
        
//        if(rank.contains("SENIOR") || rank.contains("EXECUTIVE")){
//        
//         sql = "SELECT rqid, rqtype, enteredon::timestamp(0), requestedby, \n"
//                + "(CASE \n"
//                + "    WHEN approved IS null then 'pending' \n"
//                + "    WHEN approved IS false THEN 'rejected'\n"
//                + "    ELSE 'approved'\n"
//                + "END) \n"
//                + "\n"
//                + "\n"
//                + "FROM fleet.transportrequestmemo \n"
//                + "WHERE enteredon::date > current_date - 8 AND transportconfirm is true AND (approved is null or approved is false or approved is true)  ORDER BY 5 desc";;
//        } else{
//             sql = "SELECT rqid, rqtype, enteredon::timestamp(0), requestedby, \n"
//                + "(CASE \n"
//                + "    WHEN approved IS null then 'pending' \n"
//                + "    WHEN approved IS false THEN 'rejected'\n"
//                + "    ELSE 'approved'\n"
//                + "END) \n"
//                + "\n"
//                + "\n"
//                + "FROM fleet.transportrequestmemo \n"
//                + "WHERE enteredon::date > current_date - 8   ORDER BY 5 desc";;
//        }
     //    System.err.println(sql);
        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
          //  System.err.println(sql);
            ResultSet rset = pst.executeQuery();

            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();

            String[] columns = new String[]{"request_id", "request_type", "requestedon", "requestedby", "status"};

            ArrayList<HashMap<String, String>> parentList
                    = new ArrayList<HashMap<String, String>>();//Parent List

            while (rset.next()) {

                HashMap<String, String> child = new HashMap<String, String>();
                for (int i = 0; i < columns.length; i++) {
                    child.put(columns[i].toString(), rset.getObject(i + 1).toString());
                }
                parentList.add(child);
            }

            String json = new Gson().toJson(parentList);


            activityBeans.setJson(json);


        } catch (Exception ex) {
           ex.printStackTrace();
        }

    }
}
