/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtrh.mtportal.sys;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import servlets.PropertiesClass;

/**
 *
 * @author owner
 */
public class ConnectionProperties {

    /**
     * @return the department
     */
    public static String getDepartment() {
        return department;
    }

    /**
     * @param aDepartment the department to set
     */
    public static void setDepartment(String aDepartment) {
        department = aDepartment;
    }

    /**
     * @return the section
     */
    public static String getSection() {
        return section;
    }

    /**
     * @param aSection the section to set
     */
    public static void setSection(String aSection) {
        section = aSection;
    }

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
                dbServerIp = PropertiesClass.getpropValue("dbServerIpAdd").toString();
            }

            if (dbPort == null) {
                dbPort = PropertiesClass.getpropValue("dbPort").toString();
            }

            if (activeDatabase == null) {
                activeDatabase = PropertiesClass.getpropValue("activeDatabase").toString();
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
    public void setMetrics(String pfno, Connection connectDB) {

//        Connection connectDB = connect("postgres", "sequence");
        try {
            PreparedStatement pst = connectDB.prepareStatement("SELECT * FROM secure_password WHERE staffid=?");
            pst.setObject(1, pfno);
            ResultSet rset = pst.executeQuery();

            while (rset.next()) {
                setUsername(rset.getString("fullname"));
                setUserlogin(rset.getString("login_name"));
                setDesignation(rset.getString("designation"));
                setEmail(rset.getString("email"));
                setPhone(rset.getString("phone"));
                setSection(rset.getString("section"));
                setDepartment(rset.getString("department"));

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static Object[] staffMetrics(String pfno, Connection connectDB) {
        /*
        fullname,login_name,designation,phone,email,section,department,stafid
        */
        Object[] metrics = new Object[]{};
        
        Vector v = new Vector();
//        Connection connectDB = connect("postgres", "sequence");
        try {
            PreparedStatement pst = connectDB.prepareStatement("SELECT * FROM secure_password WHERE staffid=?");
            pst.setObject(1, pfno);
            ResultSet rset = pst.executeQuery();

            while (rset.next()) {
                v.add(rset.getString("fullname"));
                v.add(rset.getString("login_name"));
                v.add(rset.getString("designation"));
                v.add(rset.getString("phone"));
                v.add(rset.getString("email"));
                v.add(rset.getString("section"));
                v.add(rset.getString("department"));
                v.add(rset.getString("staffid"));

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        
        return v.toArray();
    }

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
    private static String department;
    private static String section;
    private static String email;
    private static String phone;
    private static BufferedImage img;
    private static InputStream is;
    static Connection connect2DB;

}
