/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtrh.fleet.sys;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author owner
 */
public class UserProfile {

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the fullname
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * @param fullname the fullname to set
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * @return the refid
     */
    public String getRefid() {
        return refid;
    }

    /**
     * @param refid the refid to set
     */
    public void setRefid(String refid) {
        this.refid = refid;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the designation
     */
    public String getDesignation() {
        return designation;
    }

    /**
     * @param designation the designation to set
     */
    public void setDesignation(String designation) {
        this.designation = designation;
    }
    private String username;
    private String fullname;
    private String refid;
    private String email;
    private String phone;
    private String designation;

    public String UserNameFactory(String staffid, Connection connectDB) {
        String returnvalue = "0";
        String sql = "select *  from secure_password where staffid  = '" + staffid + "'";
        System.err.println(sql);
        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();
            
            if (rset.next()) {                
                this.setFullname(rset.getObject("fullname").toString());
                this.setRefid(rset.getObject("staffid").toString());
                this.setEmail(rset.getObject("email").toString());
                this.setPhone(rset.getObject("phone").toString());
                this.setDesignation(rset.getObject("designation").toString());
                this.setUsername(rset.getObject("login_name").toString());
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return returnvalue;
    }
    
}
