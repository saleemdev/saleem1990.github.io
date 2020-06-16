/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ihrishandshake;

import com.google.gson.Gson;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

/**
 *
 * @author owner
 */
public class Ihrishandshake {

    /**
     * @param args the command line arguments
     */
    //  public static 
    public static String getLeaveEnd(Connection conn, String date, int days) {
        String finaldate = "";
        try {
            System.err.println("My daye is " + date);
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

            Date yourDate = format1.parse(date);
            Calendar c = Calendar.getInstance();
            c.setTime(yourDate);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

            int i = 0;
            String startdate = format1.format(yourDate).toString();

            while (i < days) {
                dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

                c.add(Calendar.DATE, 1);

                String sqldate = format1.format(c.getTime());
                //if (dayOfWeek != 6 && dayOfWeek != 7 && !sqldate.equalsIgnoreCase("2018-11-09")) {
                if (dayOfWeek != 6 && dayOfWeek != 7 && !amIASpecialDate(sqldate, conn)) {
                    System.err.println(c.getTime() + " " + dayOfWeek + " " + sqldate);

                    i += 1;
                    finaldate = sqldate;
                }

            }

            System.err.println("Finally From " + startdate + " to " + finaldate+" a total of "+days+" days");
//        }
        } catch (Exception ex) {

        }

        return finaldate;
        //String day = String.valueOf(dayOfWeek);
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

    private static Object[] geUnaoploadedLeaves(Connection conn) {
        Object[] data = new String[]{};

        Vector v = new Vector();

        String sql = "SELECT DISTINCT refno FROM hr.leave_application WHERE uploaded IS FALSE AND approved IS TRUE";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                v.add(rset.getObject(1));
            }
            data = v.toArray();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return data;
    }

    private static String getApprovedLeaveApplications(java.sql.Connection connectDB, String rqid) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent List

        // Vector v = new Vector();
        //String[] columns = new String[]{"transport", "snradmin", "ticket", "security"};
        String json = "";

        String sql = "select refno, pfno,leavetype, daysapproved, coveringofficer, leavestart, '' as leavend , '' as resumption from hr.leave_application WHERE refno = ? ;";
        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, rqid);
            ResultSet rset = pst.executeQuery();
            ResultSetMetaData metaData = rset.getMetaData();

            int columns = metaData.getColumnCount();

            String leavend = "", resumptiondate = "";
            
            

            while (rset.next()) {

                int daysapproved = rset.getInt(4);
                String leavestart = rset.getObject(6).toString();

                leavend = getLeaveEnd(connectDB, leavestart,daysapproved);

                resumptiondate = getResumptionDate(connectDB, leavend);

                System.err.println(leavestart + " is the leavestart, and we are counting "+daysapproved);

                HashMap<String, String> child = new HashMap<String, String>();
                for (int i = 0; i < columns; i++) {
                    if (i == 6) {
                        child.put(metaData.getColumnName(i + 1), leavend);
                        // System.err.println(metaData.getColumnName(i + 1)+":"+getLeaveEnd(connectDB, leavestart));
                    } else if (i == 7) {
                        child.put(metaData.getColumnName(i + 1), resumptiondate);
                    } else {
                        System.err.println(i);
                        child.put(metaData.getColumnName(i + 1), rset.getObject(i + 1).toString());
                    }
//                    System.err.println(metaData.getColumnName(i + 1) + ":" + rset.getObject(i + 1).toString());

                }
                System.err.println("End..");

                parentList.add(child);
            }

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }
        json = new Gson().toJson(parentList);//String JSON object

        JSONArray arr = new JSONArray();
        arr.put(json);                         //JSON Array

        return json;
    }

    private static void markAsUploaded(String leaveID, Connection connectDB) {
        try {
            connectDB.setAutoCommit(false);
            String sql = "UPDATE  hr.leave_application SET UPLOADED = TRUE WHERE refno = ?";
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, leaveID);
            pst.executeUpdate();
            connectDB.setAutoCommit(true);
            connectDB.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                connectDB.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
        }

    }
    private static Object dbServerIp;
    private static Object dbPort;
    private static Object activeDatabase;

    public static java.sql.Connection connect(String user, String password) {
        Connection connection = null;

        try {

            java.lang.Class.forName("org.postgresql.Driver");

        } catch (java.lang.ClassNotFoundException cnf) {

            cnf.printStackTrace();

        }

        try {

            if (dbServerIp == null) {
                // dbServerIp = "localhost"jj;
                dbServerIp = PropertiesFunctions.getpropValue("dbServerIpAdd").toString();
            }

            if (dbPort == null) {
                dbPort = PropertiesFunctions.getpropValue("dbPort").toString();
            }

            if (activeDatabase == null) {
                activeDatabase = PropertiesFunctions.getpropValue("activeDatabase").toString();
            }
            //  System.out.println("jdbc:postgresql://" + this.dbServerIp + ":" + dbPort + "/" + activeDatabase + " " + userName + " " + passWord);
            connection = java.sql.DriverManager.getConnection("jdbc:postgresql://" + dbServerIp + ":" + dbPort + "/" + activeDatabase, user, password);

            if (connection != null) {
                System.err.println("connected");
            }
        } catch (java.sql.SQLException sqlExec) {

            //  msg = sqlExec.getMessage().toString();
            System.err.println(System.getProperty("user.dir"));

            //     Accurate = false;
            //javax.swing.JOptionPane.showMessageDialog(this, "ERROR : Logon denied due to incorrect username & password,\n network disconnection or dataserver not running!\n\nERROR DETAILS : \n[" + sqlExec.getMessage() + "]");
            sqlExec.printStackTrace();
            //   this.setVisible(true);

        }
        return connection;
    }

   

    public static void main(String[] args) {

        Connection conn = connect("postgres", "sequence");
        Object[] notuploadedIDS = geUnaoploadedLeaves(conn);
        if (notuploadedIDS.length > 0) {
            for (int j = 0; j < notuploadedIDS.length; j++) {
                String json = getApprovedLeaveApplications(conn, notuploadedIDS[j].toString()).replace("[", "").replace("]", "");
                System.err.println("I am here " + json);
                //     String json = "{\"message\":\"This is a message\"}";
                //     System.err.println(getLeaveEnd(conn, "2018-10-10"));

                HttpClient httpClient = new DefaultHttpClient();

                try {
                    //  HttpPost request = new HttpPost("http://41.89.183.14/moh/leave.php");

                    String ip = PropertiesFunctions.getpropValue("ihrisip").toString();
                    System.err.println(ip);
                    HttpPost request = new HttpPost(ip);//http://192.168.130.146/
                    StringEntity params = new StringEntity(json);
                    //request.addHeader("content-type", "application/x-www-form-urlencoded");
                    request.addHeader("content-type", "application/json; charset=UTF-8");
                    //  request.addHeader("content-type");
                    request.setEntity(params);
                    HttpResponse response = httpClient.execute(request);

                    // handle response here...
                    Date date = new Date();
                    long time = date.getTime();
                    Timestamp ts = new Timestamp(time);
                    //   System.out.println("Current Time Stamp: " + ts);
                    System.out.println(ts + ": My Response is " + org.apache.http.util.EntityUtils.toString(response.getEntity()));

                    //  System.out.println(response.getEntity());
                    org.apache.http.util.EntityUtils.consume(response.getEntity());

                    String answer = org.apache.http.util.EntityUtils.toString(response.getEntity());

                    if (answer.equalsIgnoreCase("1")) {
                        markAsUploaded(notuploadedIDS[j].toString(), conn);
                    }
                } catch (Exception ex) {
                    // handle exception here
                    ex.printStackTrace();
                } finally {
                    httpClient.getConnectionManager().shutdown();
                }
            }
        }
    }

}
