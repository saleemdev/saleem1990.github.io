/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtrh.fleet.dao;

import com.google.gson.Gson;
import com.mtrh.fleet.reports.PDFPAth;
import com.mtrh.fleet.reports.VehiclesPDF;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;

/**
 *
 * @author owner
 */
public class TestClass {

    /**
     * @return the path2pdf
     */
    public static String getPath2pdf() {
        return path2pdf;
    }

    /**
     * @param aPath2pdf the path2pdf to set
     */
    public static void setPath2pdf(String aPath2pdf) {
        path2pdf = aPath2pdf;
    }

    private static String path2pdf;
    private Object dbServerIp;
    private Object dbPort;
    private Object activeDatabase;

    public java.sql.Connection getConnection(String user, String password) {
        Connection connection = null;

        try {

            java.lang.Class.forName("org.postgresql.Driver");

        } catch (java.lang.ClassNotFoundException cnf) {

            cnf.printStackTrace();

        }

        try {

            if (dbServerIp == null) {
                // dbServerIp = "localhost"jj;
                dbServerIp = com.mtrh.fleet.sys.PropertiesFunctions.getpropValue("dbServerIpAdd").toString();
            }

            if (dbPort == null) {
                dbPort = com.mtrh.fleet.sys.PropertiesFunctions.getpropValue("dbPort").toString();
            }

            if (activeDatabase == null) {
                activeDatabase = com.mtrh.fleet.sys.PropertiesFunctions.getpropValue("activeDatabase").toString();
            }
            //  System.out.println("jdbc:postgresql://" + this.dbServerIp + ":" + dbPort + "/" + activeDatabase + " " + userName + " " + passWord);
            connection = java.sql.DriverManager.getConnection("jdbc:postgresql://" + this.dbServerIp + ":" + dbPort + "/" + activeDatabase, user, password);

        } catch (java.sql.SQLException sqlExec) {

//            msg = sqlExec.getMessage().toString();
            System.err.println(System.getProperty("user.dir"));

            //     Accurate = false;
            //javax.swing.JOptionPane.showMessageDialog(this, "ERROR : Logon denied due to incorrect username & password,\n network disconnection or dataserver not running!\n\nERROR DETAILS : \n[" + sqlExec.getMessage() + "]");
            sqlExec.printStackTrace();
            //   this.setVisible(true);

        }
        return connection;
    }

    public static void work(java.sql.Connection connectDB, String user) {
//        VehiclesPDF pdf = new VehiclesPDF();
//
//        pdf.VehiclesPDF(connectDB);
//        
//        System.err.println("File path"+PDFPAth.getPath2pdf());
//        System.err.println("Local File path"+getPath2pdf());
//        
//        File file = new File(getPath2pdf());
//        try {
//            Desktop.getDesktop().open(file);
//        } catch (IOException ex) {
//            Logger.getLogger(TestClass.class.getName()).log(Level.SEVERE, null, ex);
//        }

System.err.println("FInal String is "+randomAlphaNumeric(5));
    }
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        java.sql.Connection conn = new TestClass().getConnection("admin", "12345");

        work(conn, "admin");

    }

}
