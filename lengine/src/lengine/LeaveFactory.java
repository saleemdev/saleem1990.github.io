/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lengine;

/**
 *
 * @author owner
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author owner
 */
public class LeaveFactory {

//    public LeaveFactory() {
//
//    }
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

    public static int getLeaveEntitleMent(String pfno, String leavetype, java.sql.Connection connectDB) {
        int days = 0;
        String sql = "select sum(days_plus) from hr.leaverota WHERE staffid =?  AND rota_fy = ? AND upper(leavetype) ilike upper(?) AND days_plus > 0 ";

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
        String sql = "select sum(days_plus-days_minus) from hr.leaverota WHERE staffid =?  AND rota_fy = ? AND upper(leavetype) ilike upper(?) ";

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

    public static int getLeaveAccumulatedDaysByType(String pfno, String leavetype, java.sql.Connection connectDB) {
        int days = 0;

        String sql = "select sum(days_minus) from hr.leaverota WHERE staffid =? AND rota_fy = ? AND upper(leavetype) ilike upper(?) ";

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
    public static String getLeaveEnd(Connection conn, String date, int days, String leavetype) {
        String finaldate = "";
        String startdate = "";
        System.err.println(date + " to start");
        if (days == 1) {
            finaldate = date;
        } else {
            try {

                if (leavetype.toUpperCase().contains("MATERNITY") || leavetype.toUpperCase().contains("TERMINAL") || leavetype.toUpperCase().contains("CHILD")) {
                    System.err.println("Will not skip");
                    //
                    days-=1;
                    String sql = "SELECT '" + date + "'::date + " + days + ";";
                    PreparedStatement pst = conn.prepareStatement(sql);
                    ResultSet rset = pst.executeQuery();
                    while (rset.next()) {
                        finaldate = rset.getObject(1).toString();
                    }

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
                            finaldate = sqldate;
                            System.err.println(finaldate);
                        } else {
                            System.err.println("[" + sqldate + " is a holiday/weekend]");
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
            //        System.err.println("My date is " + date);
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
                    //              System.err.println(c.getTime() + " " + dayOfWeek + " " + sqldate);
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
            //    System.err.println("Finally From " + startdate + " to " + finaldate);
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
}

