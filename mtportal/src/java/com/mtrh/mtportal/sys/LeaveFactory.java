/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtrh.mtportal.sys;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Image;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author owner
 */
public class LeaveFactory {

    private static String dbServerIp;
    private static String dbPort;
    private static String activeDatabase;

//    public LeaveFactory() {
//
//    }
    public static String getLeaveStart(String refno, java.sql.Connection connectDB) {
        String startdate = "";

        String sql = "select leavestart from hr.leave_application WHERE refno = ? ";

        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, refno);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                startdate = rset.getObject(1).toString();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return startdate;
    }

    public static String getDependsOn(String leavetype, Connection conn) {
        String stat = "";
        String sql = "SELECT deductedfrom FROM hr.leave_types WHERE UPPER(description) ILIKE ?";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setObject(1, leavetype);

            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                stat = rset.getString(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return stat;

    }

    public static String getFormattedDate(Connection conn, String date) {
        String dateF = "";

        String sql = "select humanreadabledate('" + date + "');";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {

                dateF = rset.getString(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return dateF;
    }

    private static String SpecialDay(String date2check, Connection conn) {
        String stat = "";
        try {
            PreparedStatement pst = conn.prepareStatement("SELECT DISTINCT upper(holiday_name) FROM hr.holidays WHERE date::date = ?::date");
            pst.setObject(1, date2check);
            ResultSet rset = pst.executeQuery();

            if (rset.next()) {
                stat = rset.getString(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return stat;
    }

    public static String getPermissionType(String leavetype, Connection conn) {
        String stat = "";
        String sql = "SELECT permission_type FROM hr.leave_types WHERE UPPER(description) ILIKE ?";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setObject(1, leavetype);

            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                stat = rset.getString(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return stat;

    }

    public static int confirmLeaveMonth(String pfno, String leavetype, String month, Connection connectDB) {
        int days = 0;
        //  String sql = "select month from hr.leaverota WHERE staffid =?  AND rota_fy = ? AND upper(leavetype) ilike upper(?) AND days_plus > 0 ";

        String sql = "select case when upper(?) in (select upper(month) from hr.leaverota WHERE staffid =?  AND rota_fy = ? AND upper(leavetype) ilike upper(?) AND days_plus > 0) then 1 else 0 END";

        System.err.println("select case when '" + month.toUpperCase() + "' in (select month from hr.leaverota WHERE staffid ='" + pfno + "'  AND rota_fy = '" + getCurrentFY(connectDB) + "' AND upper(leavetype) ilike upper('" + leavetype + "') AND days_plus > 0) then 1 else 0 END");
        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, month.toUpperCase());
            pst.setObject(2, pfno);
            pst.setObject(3, getCurrentFY(connectDB));
            pst.setObject(4, leavetype);

            ResultSet rset = pst.executeQuery();
            if (rset.next()) {
                days = rset.getInt(1);
            } else {
                days = 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return days;
    }

    public static int getLeaveEntitleMent(String pfno, String leavetype, String leaveid, java.sql.Connection connectDB) {
        int days = 0;
        String sql = "select sum(days_plus) from hr.leaverota WHERE staffid =?  AND rota_fy = ? AND upper(leavetype) ilike upper(?) AND days_plus > 0 ";

        String fy = getCurrentFYFromLeaveref(connectDB, leaveid);
        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, pfno);
            pst.setObject(2, fy);
            pst.setObject(3, leavetype);

            ResultSet rset = pst.executeQuery();
            if (rset.next()) {
                days = rset.getInt(1);
            } else {
                days = 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return days;
    }

    public static int getLeaveBalanceAll(String pfno, java.sql.Connection connectDB) {
        int days = 0;

        String sql = "select sum(days_plus-days_minus) from hr.leaverota WHERE staffid =? AND rota_fy = ? ";

        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, pfno);
            // pst.setObject(2, leaveref);
            pst.setObject(2, getCurrentFY(connectDB));

            ResultSet rset = pst.executeQuery();
            if (rset.next()) {
                days = rset.getInt(1);
            } else {
                days = 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return days;
    }

    public static int getLeaveBalanceByType(String pfno, String leavetype, java.sql.Connection connectDB) {
        int days = 0;
        String sql = "select sum(days_plus-days_minus) from hr.leaverota WHERE staffid =?  AND rota_fy = ? AND upper(leavetype) = upper(?) ";

        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, pfno);
            pst.setObject(2, getCurrentFY(connectDB));
            pst.setObject(3, leavetype);

            ResultSet rset = pst.executeQuery();
            if (rset.next()) {
                days = rset.getInt(1);
            } else {
                days = 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return days;
    }

    public static int getLeaveAccumulatedDaysByType(String pfno, String leavetype, String leaveid, java.sql.Connection connectDB) {
        int days = 0;

        String fy = getCurrentFY(connectDB);
        fy = getCurrentFYFromLeaveref(connectDB, leaveid);
        String sql = "select sum(days_minus) from hr.leaverota WHERE staffid =? AND rota_fy = ? AND upper(leavetype) ilike upper(?) ";

        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, pfno);
            //  pst.setObject(2, getCurrentFY(connectDB));
            pst.setObject(2, fy);
            pst.setObject(3, leavetype);

            ResultSet rset = pst.executeQuery();
            if (rset.next()) {
                days = rset.getInt(1);
            } else {
                days = 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return days;
    }

    public static int getLeaveDaysGranted(String pfno, String leaveref, String leavetype, java.sql.Connection connectDB) {
        int days = 0;

        String sql = "select sum(days_minus) from hr.leaverota WHERE staffid =? AND refid = ? and UPPER(leavetype) = upper(?) ";

        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, pfno);
            pst.setObject(2, leaveref);
            pst.setObject(3, leavetype);

            ResultSet rset = pst.executeQuery();
            if (rset.next()) {
                days = rset.getInt(1);
            } else {
                days = 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return days;
    }

    private int getLeaveMaximumDays(String leavetype, Connection connectDB) {
        int days = 0;

        String sql = "select days from hr.leave_types where description ilike ?";

        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, leavetype);

            ResultSet rset = pst.executeQuery();
            if (rset.next()) {
                days = rset.getInt(1);
            } else {
                days = 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return days;
    }

    //  public static 
//    public static String getLeaveEnd(Connection conn, String date, int days, String leavetype) {
//        String finaldate = "";
//        String startdate = "";
//        System.err.println("Getting leavend for " + date + ", " + days + ", " + leavetype);
//        try {
//            if (leavetype.toUpperCase().contains("MATERNITY") || leavetype.toUpperCase().contains("TERMINAL") || leavetype.toUpperCase().contains("CHILD")) {
//
//                //
//                String sql = "SELECT '" + date + "'::date + " + days + ";";
//                PreparedStatement pst = conn.prepareStatement(sql);
//                ResultSet rset = pst.executeQuery();
//                while (rset.next()) {
//                    finaldate = rset.getObject(1).toString();
//                }
//                //
//            } else {
//
//                System.err.println("My day is " + date);
//                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
//
//                Date yourDate = format1.parse(date);
//                Calendar c = Calendar.getInstance();
//                c.setTime(yourDate);
//                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
//
//                int i = 0;
//                startdate = format1.format(yourDate).toString();
//
//                while (i < days - 1) {
//                    dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
//
//                    c.add(Calendar.DATE, 1);
//
//                    String sqldate = format1.format(c.getTime());
//
//                    if (dayOfWeek != 6 && dayOfWeek != 7 && !amIASpecialDate(sqldate, conn)) {
//                        System.err.println(c.getTime() + " " + dayOfWeek + " " + sqldate);
//
//                        i += 1;
//                        finaldate = sqldate;
//                    }
//
//                }
//
//            }
////        for(int i=0;i<30;i++){
////             c.add(Calendar.DATE, 1);
//            System.err.println("(Flipped)Finally From " + startdate + " to " + finaldate + " a total of " + days + " days");
////        }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        return finaldate;
//        //String day = String.valueOf(dayOfWeek);
//    }
//    public static String getLeaveEnd(Connection conn, String date, int days, String leavetype) { //version 2
//        String finaldate = "";
//        String startdate = "";
//
//        if (days == 1) {
//            finaldate = date;
//        } else {
//            try {
//
//                if (leavetype.toUpperCase().contains("MATERNITY") || leavetype.toUpperCase().contains("TERMINAL") || leavetype.toUpperCase().contains("CHILD")) {
//                    System.err.println("Will not skip");
//                    //
//                    String sql = "SELECT '" + date + "'::date + " + days + ";";
//                    PreparedStatement pst = conn.prepareStatement(sql);
//                    ResultSet rset = pst.executeQuery();
//                    while (rset.next()) {
//                        finaldate = rset.getObject(1).toString();
//                    }
//
//                } else {
//                  //  System.err.println("Will definitely skip");
//                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
//
//                    Date yourDate = format1.parse(date);
//                    Calendar c = Calendar.getInstance();
//                    c.setTime(yourDate);
//                    int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
//
//                    int i = 0;
//                    startdate = format1.format(yourDate).toString();
//
//                    while (i < days - 1) {
//                        dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
//
//                        c.add(Calendar.DATE, 1);
//
//                        String sqldate = format1.format(c.getTime());
//
//                        if (dayOfWeek != 6 && dayOfWeek != 7 && !amIASpecialDate(sqldate, conn)) {
//                            //   System.err.println(c.getTime() + " " + dayOfWeek + " " + sqldate);
//
//                            i += 1;
//                            finaldate = sqldate;
//                        }
//
//                    }
//
//                }
////        for(int i=0;i<30;i++){
////             c.add(Calendar.DATE, 1);
//                //System.err.println("(Flipped)Finally From " + startdate + " to " + finaldate + " a total of " + days + " days");
////        }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//        return finaldate;
//        //String day = String.valueOf(dayOfWeek); //String day = String.valueOf(dayOfWeek);
//    }
    public static String getLeaveEnd(Connection conn, String date, int days, String leavetype) {
        String finaldate = "";
        String startdate = "";
        Object[] mydays = new Object[]{};

        Vector v = new Vector();
        System.err.println("Day 1 =" + date);
        v.add("Day 1 =" + date);
        if (days == 1) {
            finaldate = date;
            v.add(date + " to end");
        } else {
            try {

                if (leavetype.toUpperCase().contains("MATERNITY") || leavetype.toUpperCase().contains("TERMINAL") || leavetype.toUpperCase().contains("CHILD")) {
                    System.err.println("Will not skip");
                    v.add("Will not skip days");
                    //
                    //days -= 1;
                    int j = 0;
                    for (int i = 0; i < days; i++) {
                        String sql = "SELECT '" + date + "'::date + " + i + ";";
                        PreparedStatement pst = conn.prepareStatement(sql);
                        ResultSet rset = pst.executeQuery();
                        while (rset.next()) {
                            finaldate = rset.getObject(1).toString();
                        }
                        j += 1;
                        System.err.println("Day " + j + "=" + finaldate);
                        v.add("Day " + j + "=" + finaldate);
                    }

                    //  v.add(finaldate+" to end")
                    System.err.println(finaldate);
                } else {
                    System.err.println("Will definitely skip");
                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

                    Date yourDate = format1.parse(date);
                    Calendar c = Calendar.getInstance();
                    c.setTime(yourDate);
                    int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

                    int i = 0;
                    startdate = format1.format(yourDate).toString();

                    while (i < days - 1) {
                        dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

                        c.add(Calendar.DATE, 1);

                        String sqldate = format1.format(c.getTime());

                        if (dayOfWeek != 6 && dayOfWeek != 7 && !amIASpecialDate(sqldate, conn)) {
                            //   System.err.println(c.getTime() + " " + dayOfWeek + " " + sqldate);

                            i += 1;

                            int j = i + 1;
                            finaldate = sqldate;
                            System.err.println(finaldate);
                            v.add("Day " + j + " " + finaldate);
                        } else {

                            v.add("[" + sqldate + " is a holiday/weekend]" + SpecialDay(sqldate, conn));
                            System.err.println("[" + sqldate + " is a holiday/weekend]" + SpecialDay(sqldate, conn));
                            //finaldate=finaldate+" is a Holiday or weekend";
                        }

                    }

                }
//        for(int i=0;i<30;i++){
//             c.add(Calendar.DATE, 1);
                //System.err.println("(Flipped)Finally From " + startdate + " to " + finaldate + " a total of " + days + " days");
//        }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        mydays = v.toArray();

        //   updateMydays(mydays);
        return finaldate;
        //String day = String.valueOf(dayOfWeek); //String day = String.valueOf(dayOfWeek);
    }

    public static void updateLogs(Connection conn, String refid, String trans, String action) {
        try {

            //I wrote this code thinking of Sarah and the Pug. She is a really nice friend and really cool
            //And really the smartest person I've met so far
            PreparedStatement pst1 = conn.prepareCall("INSERT INTO leavelogs(refid, transtype, action) SELECT '" + refid + "', '" + trans + "','" + action + "' ");
            pst1.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    private static Boolean weekDay(int dayID) {
        Boolean isWeekDay = false;
        int[] daysIwant = new int[]{1, 2, 3, 4, 5};

        for (int i = 0; i < daysIwant.length; i++) {
            if (dayID == i) {
                isWeekDay = true;
            }

        }

        return isWeekDay;
    }

    public static String getResumptionDate(Connection conn, String date) {
        String finaldate = "";
        try {
            System.err.println("My date is " + date);
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

            Date yourDate = format1.parse(date);
            Calendar c = Calendar.getInstance();
            c.setTime(yourDate);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

            int i = 0;
            String startdate = format1.format(yourDate).toString();

            while (i < 8) {

                dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

                c.add(Calendar.DATE, 1);

                String sqldate = format1.format(c.getTime());
                //if (dayOfWeek != 6 && dayOfWeek != 7 && !sqldate.equalsIgnoreCase("2018-11-09")) {
                if (dayOfWeek != 6 && dayOfWeek != 7 && !amIASpecialDate(sqldate, conn)) {
                    System.err.println(c.getTime() + " " + dayOfWeek + " " + sqldate);
                    if (!weekDay(i)) {
                        i += 1;
                        finaldate = sqldate;

                    } else {
                        i = 9;
                        finaldate = sqldate;
                    }
                }

            }

//        for(int i=0;i<30;i++){
//             c.add(Calendar.DATE, 1);
            System.err.println("Finally From " + startdate + " to " + finaldate);
//        }
        } catch (Exception ex) {

        }

        return finaldate;
        //String day = String.valueOf(dayOfWeek);
    }

    private static Boolean amIASpecialDate(String date2check, Connection conn) {
        Boolean stat = false;
        Object[] specialDates = getSpecialDayes(conn);//new String[]{"2018-11-07","2018-10-05"};
        for (int i = 0; i < specialDates.length; i++) {
            if (date2check.equalsIgnoreCase(specialDates[i].toString())) {
                stat = true;
            }
        }
        return stat;
    }

    private static Object[] getSpecialDayes(Connection connectDB) {
        Object[] days = new String[]{};
        Vector v = new Vector();

        try {
            PreparedStatement pst = connectDB.prepareStatement("SELECT DISTINCT date FROM hr.holidays");

            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                v.add(rset.getObject(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        days = v.toArray();

        return days;
    }

    public static String getCurrentFY(Connection conn) {
        String fy = "";
        try {
            String sql = "select yrid from hr.activeyear where status = true";
            PreparedStatement pst = conn.prepareStatement(sql);

            ResultSet rset = pst.executeQuery();
            if (rset.next()) {
                fy = rset.getObject(1).toString();
            }

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }

        return fy;
    }

    public static String getLeavedate(Connection conn, String refno) {
        String date = "";
        try {
            String sql = "select appliedon::date from hr.leave_application where refno = ? ";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setObject(1, refno);

            ResultSet rset = pst.executeQuery();
            if (rset.next()) {
                date = rset.getObject(1).toString();
            }

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }

        return date;
    }

    public static String getCurrentFYFromLeaveref(Connection conn, String ref) {
        String fy = "";

        String dateapplied = getLeavedate(conn, ref);
        try {
            String sql = "select yrid from hr.activeyear where ?::date between starts and ends;";
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setObject(1, dateapplied);

            ResultSet rset = pst.executeQuery();
            if (rset.next()) {
                fy = rset.getObject(1).toString();
            }

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }

        return fy;
    }

    public static String getActionTimestamp(Connection conn, String approvalLevel) {
        String timestamp = "";
        try {
            String sql = "select yrid from hr.activeyear where status = true";
            PreparedStatement pst = conn.prepareStatement(sql);

            ResultSet rset = pst.executeQuery();
            if (rset.next()) {
                timestamp = rset.getObject(1).toString();
            }

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }

        return timestamp;
    }

    public static String getFunsoftUnameByPF(Connection connectDB, String pfno) {
        String uname = "-";
        try {
            PreparedStatement pstmt = connectDB.prepareStatement("SELECT u_name FROM uname_mapping WHERE staffid= ? ");

            pstmt.setString(1, pfno);

            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                uname = rset.getString(1);
            } else {
                uname = "-";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return uname;
    }

    public static Image getSignature(Connection connectDB, String identifier) {
        Image signatureImage = null;
        try {
            PreparedStatement pstmtImages = connectDB.prepareStatement("SELECT data_capture_time, document_data FROM funsoft_image_graphics WHERE document_ref_no = ? AND document_source = 'DIGITAL_AUTH_SIGNATURE' ORDER BY 1");

            pstmtImages.setString(1, identifier);

            ResultSet rsetImages = pstmtImages.executeQuery();
            if (rsetImages.next()) {
                ByteArrayOutputStream bout = getImageBytea(connectDB, identifier, rsetImages.getTimestamp(1));
                try {
                    signatureImage = Image.getInstance(bout.toByteArray());
                } catch (BadElementException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
//    if (signatureImage == null) {
//      JOptionPane.showMessageDialog(null, "Please make sure that you have captured all the digital signatures and all approvals have been done.");
//    }
        return signatureImage;
    }

    public static ByteArrayOutputStream getImageBytea(Connection connDB, String documentRefNumber, Timestamp date) {
        File tempFile = null;
        Image image = null;
        // connectDB = connDB;
        ByteArrayOutputStream byteaStream = null;
        try {
            PreparedStatement pstmtR = connDB.prepareStatement("SELECT DISTINCT document_data, data_capture_time FROM funsoft_image_graphics  WHERE document_ref_no = ? AND data_capture_time = ? ORDER BY data_capture_time DESC LIMIT 1");
            pstmtR.setString(1, documentRefNumber);
            pstmtR.setTimestamp(2, date);
            ResultSet rs = pstmtR.executeQuery();
            while (rs.next()) {
                byte[] imgBytes = rs.getBytes(1);
                byteaStream = new ByteArrayOutputStream();
                try {
                    byteaStream.write(imgBytes);
                } catch (IOException ex) {
                    ex.printStackTrace();

                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return byteaStream;
    }

    public static java.sql.Connection custom_connect(String user, String password, String url, String port, String db) {
        Connection connection = null;

        try {

            java.lang.Class.forName("org.postgresql.Driver");

        } catch (java.lang.ClassNotFoundException cnf) {

            cnf.printStackTrace();

        }

        try {

            // if (dbServerIp == null) {
            // dbServerIp = "localhost"jj;
            dbServerIp = url;
            //}

            //   if (dbPort == null) {
            dbPort = port;
            // }

            //if (activeDatabase == null) {
            activeDatabase = db;
            //}
            //  System.out.println("jdbc:postgresql://" + this.dbServerIp + ":" + dbPort + "/" + activeDatabase + " " + userName + " " + passWord);
            connection = java.sql.DriverManager.getConnection("jdbc:postgresql://" + dbServerIp + ":" + dbPort + "/" + activeDatabase, user, password);
            System.err.print("jdbc:postgresql://" + dbServerIp + ":" + dbPort + "/" + activeDatabase);
//            if (connection != null) {
//                JOptionPane.showMessageDialog(new java.awt.Frame(), "connected");
//            }
//            else{
//                  JOptionPane.showMessageDialog(new java.awt.Frame(), "Not connected");
            System.err.println(System.getProperty("user.dir") + System.getProperty("file.separator") + "logo.jpg");
//            }
        } catch (java.sql.SQLException sqlExec) {

            //     msg = sqlExec.getMessage().toString();
            System.err.println(System.getProperty("user.dir"));

            //     Accurate = false;
            //javax.swing.JOptionPane.showMessageDialog(this, "ERROR : Logon denied due to incorrect username & password,\n network disconnection or dataserver not running!\n\nERROR DETAILS : \n[" + sqlExec.getMessage() + "]");
            sqlExec.printStackTrace();
            //   this.setVisible(true);

        }
        return connection;
    }
}
