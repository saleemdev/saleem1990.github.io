/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtrh.fleet.dao;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.sql.Connection;
import javax.servlet.http.HttpSession;

/**
 *
 * @author owner
 */
public class ConnectionProperties {

    private String msg;
    private Object dbServerIp;
    private Object dbPort;
    private Object activeDatabase;

   
    
    
    
    public java.sql.Connection connect(String user, String password) {
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
            connect2DB = java.sql.DriverManager.getConnection("jdbc:postgresql://" + this.dbServerIp + ":" + dbPort + "/" + activeDatabase, user, password);

            if (connection != null) {
                System.err.println("connected");
            }
        } catch (java.sql.SQLException sqlExec) {

            msg = sqlExec.getMessage().toString();
            System.err.println(System.getProperty("user.dir"));

            //     Accurate = false;
            //javax.swing.JOptionPane.showMessageDialog(this, "ERROR : Logon denied due to incorrect username & password,\n network disconnection or dataserver not running!\n\nERROR DETAILS : \n[" + sqlExec.getMessage() + "]");
            sqlExec.printStackTrace();
            //   this.setVisible(true);

        }
        return connect2DB;
    }
    /**
     * @return the is
     */
    public static InputStream getIs() {
        return is;
    }

    /**
     * @param aIs the is to set
     */
    public static void setIs(InputStream aIs) {
        is = aIs;
    }

    /**
     * @return the email
     */
    public static String getEmail() {
        
        
        
        return email;
    }

    /**
     * @param aEmail the email to set
     */
    public static void setEmail(String aEmail) {
        email = aEmail;
    }

    /**
     * @return the phone
     */
    public static String getPhone() {
        return phone;
    }

    /**
     * @param aPhone the phone to set
     */
    public static void setPhone(String aPhone) {
        phone = aPhone;
    }

    /**
     * @return the img
     */
    public static BufferedImage getImg() {
        return img;
    }

    /**
     * @param aImg the img to set
     */
    public static void setImg(BufferedImage aImg) {
        img = aImg;
    }

    /**
     * @return the designation
     */
    public static String getDesignation() {
        return designation;
    }

    /**
     * @param aDesignation the designation to set
     */
    public static void setDesignation(String aDesignation) {
        designation = aDesignation;
    }

    /**
     * @return the userlogin
     */
    public static String getUserlogin() {
        return userlogin;
    }

    /**
     * @param aUserlogin the userlogin to set
     */
    public static void setUserlogin(String aUserlogin) {
        userlogin = aUserlogin;
    }

    /**
     * @return the username
     */
    public static String getUsername() {
        return username;
    }

    /**
     * @param aUsername the username to set
     */
    public static void setUsername(String aUsername) {
        username = aUsername;
    }

    /**
     * @return the connect2DB
     */
    public static Connection getConnect2DB() {
      //   HttpSession session = request.getSession();
        
        
        return connect2DB;
    }

    /**
     * @param aConnect2DB the connect2DB to set
     */
    public static void setConnect2DB(Connection aConnect2DB) {
        connect2DB = aConnect2DB;
    }
    private static String userlogin;
    private static String username;
    private static String designation;
    private static String email;
    private static String phone;
    private static BufferedImage img;
    private static InputStream is;
     static Connection connect2DB;

}
