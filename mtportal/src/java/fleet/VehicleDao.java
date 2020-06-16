/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fleet;

import com.google.gson.Gson;
//import com.mtrh.fleet.entity.VehicleBeans;
//import com.mtrh.fleet.reports.VehiclesPDF;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author owner
 */
public class VehicleDao {

    public VehicleDao() {

    }

    public ArrayList<HashMap<String, String>> getVehicleDetails(java.sql.Connection connectDB) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();

        String[] columns = new String[]{"fleetno", "regno", "classification", "make", "model", "regdate"};

        String sql = "SELECT fleetno, regno, classification, make, model, regdate::varchar FROM fleet.vehicle_register";
        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {

                HashMap<String, String> child = new HashMap<String, String>();
                for (int i = 0; i < columns.length; i++) {
                    child.put(columns[i].toString(), rset.getObject(i + 1).toString());
                }
                parentList.add(child);
            }

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }
        String json = new Gson().toJson(parentList);//String JSON object

        json = json.replace("[", "").replace("]", "");

        JSONArray arr = new JSONArray();
        arr.put(json);                         //JSON Array
        //jsobj = new JSONObject();
//        VehicleBeans v = new VehicleBeans();
  //      v.setVehiclesStringJson(json);//setStringJSOn
    //    v.setVehicleArr(arr);//setArray
        try {
            JSONObject jsobj = new JSONObject(json); //jsObject
         //   v.setVehiclesJson(jsobj);//setJSONObject
            System.out.println("At the DAO:\nJSON Object " + jsobj + "\nJSON String " + json + "\nJSOn Arr " + arr);

        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return parentList;
    }

    public void DeployVehicle(java.sql.Connection connectDB, Object vehicle, Object driver, Object officer, Object section, String request, String transtype) {
        String sql = "", sql2 = "";

        sql = "UPDATE fleet.vehicle_register SET vehicle_status ='Deployed', driver = ?, authority = ?, section =''  WHERE regno = ? ;";


        sql2 = "UPDATE fleet.transportrequestmemo SET driver = ?, vehicle_allocated =? WHERE rqid = ? ;";

        System.err.println("UPDATE fleet.vehicle_register SET vehicle_status ='Deployed', driver = '" + driver + "', authority = '" + officer + "', section =''  WHERE regno = '" + vehicle + "'");

        try {
            PreparedStatement pst;
            if (transtype.equalsIgnoreCase("AUTHORIZE REQUEST")) {
                pst = connectDB.prepareStatement(sql);               
                pst.setObject(1, driver);
                pst.setObject(2, officer);
                pst.setObject(3, vehicle);                
                pst.executeUpdate();
            }

            pst = connectDB.prepareStatement(sql2);
            pst.setObject(1, driver);
            pst.setObject(2, vehicle);
            pst.setObject(3, request);

            pst.executeUpdate();
        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }
        
        
        System.err.println("Deployed "+vehicle);
    }

    public ArrayList< String> getVehArrayList(java.sql.Connection connectDB) {
        ArrayList< String> parentList
                = new ArrayList< String>();

        Vector v = new Vector();

        String[] columns = new String[]{"fleetno", "regno", "classification", "make", "model", "regdate"};

        String sql = "SELECT fleetno, regno, classification, make, model, regdate::varchar FROM fleet.vehicle_register";
        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {

                ArrayList< String> child = new ArrayList<String>();
                for (int i = 0; i < columns.length; i++) {
                    //  child.put( rset.getObject(i + 1).toString());
                    child.add(rset.getObject(i + 1).toString());
                }
                parentList = child;
            }

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }
        String json = new Gson().toJson(parentList);//String JSON object

        System.err.println("Before setting " + json);

        //  json = json.replace("[", "{").replace("]", "}");
        JSONArray arr = new JSONArray();
        arr.put(json);                         //JSON Array
        //jsobj = new JSONObject();
//        VehicleBeans vroom = new VehicleBeans();
  //      vroom.setVehiclesStringJson(json);//setStringJSOn
    //    vroom.setVehicleArr(arr);//setArray

        return parentList;
    }

    public Object[] vehicleDetails(java.sql.Connection connectDB, String refno) {
        Object[] obj = new Object[]{};
        //  String[] columns = new String[]{"fleetno", "regno", "model", "yom", "chassisno", "engineno", "classification", "fuel", "status", "engine_cc"};

        String sql = "SELECT fleetno, regno, model, yom, chassis_no, engine_no, classification, \n"
                + "       fuel_used, engine_cc, vehicle_status,make\n"
                + "  FROM fleet.vehicle_register WHERE fleetno ='" + refno + "' OR regno='" + refno + "';";
        Vector v = new Vector();

        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();

            int column_length = rset.getMetaData().getColumnCount();
            while (rset.next()) {
                for (int i = 1; i <= column_length; i++) {
                    v.add(rset.getObject(i).toString());
                }

                obj = v.toArray();
            }
        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }
        String json = new Gson().toJson(obj);

        return obj;
    }

    public Double getVehicleMileage(String fleetid, java.sql.Connection connectDB) {
        Double mileage = 0.0;

        String sql = "SELECT current_mileage FROM fleet.vehicle_mileage WHERE fleet_no = ? AND status = true";

        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);

            pst.setObject(1, fleetid);
            ResultSet rset = pst.executeQuery();

            while (rset.next()) {
                mileage = rset.getDouble(1);
            }
        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }

        return mileage;
    }

    public void setVehicleMileage(java.sql.Connection connectDB, String fleetid, Double mileage, String comment, String enteredby, String refno) {
        String sql = "UPDATE fleet.vehicle_mileage SET status = false WHERE fleet_no = ?";

        String sql2 = "INSERT INTO fleet.vehicle_mileage(\n"
                + "            fleet_no, current_mileage, description, enteredby, refno)\n"
                + "    VALUES (?, ?, ?, ?, ?);";
        try {

            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, fleetid);
            pst.executeUpdate();

            // pst = connectDB.prepareStatement(sql)
            pst = connectDB.prepareStatement(sql2);
            pst.setObject(1, fleetid);
            pst.setObject(2, mileage);
            pst.setObject(3, comment);
            pst.setObject(4, enteredby);
            pst.setObject(5, refno);
            pst.executeUpdate();

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }
    }

    public String getFleetIDByRegNo(String regno, java.sql.Connection connectDB) {

        String fleetno = "-";
        String sql = "SELECT fleetno FROM fleet.vehicle_register WHERE regno = ?";

        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, regno);
            ResultSet rset = pst.executeQuery();
            if (rset.next()) {
                fleetno = rset.getObject(1).toString();
            }

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }

        return fleetno;
    }

    public String DateOfLastMaint(String regno, Connection connectDB) {
        String sql = "select date::date from fleet.vehicle_service_log where regno ='" + regno + "' and scheduled = false order by 1 desc";
        String date = "-";
        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, regno);
            ResultSet rset = pst.executeQuery();
            if (rset.next()) {
                date = rset.getObject(1).toString();
            }

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }
        return date;
    }
}
