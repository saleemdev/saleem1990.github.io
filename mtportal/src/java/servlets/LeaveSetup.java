/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import com.mtrh.mtportal.sys.LeaveFactory;
import com.mtrh.mtportal.sys.SendSms;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;

/**
 *
 * @author owner
 */
@WebServlet(name = "LeaveSetup", urlPatterns = {"/LeaveSetup"})
public class LeaveSetup extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LeaveSetup</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LeaveSetup at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public Boolean leaveISValid(String month, String covering, Connection conn) {
        Boolean leaveisvalid = false;

        String[] alldet = covering.split("/");

        String staffid = alldet[1];
        String sql = "select case when '" + month.toUpperCase() + "' in (SELECT UPPER(month) from hr.leaverota where staffid='" + staffid + "' AND days_plus>0) then true else false end --from hr.leaverota ";
        System.err.println(sql);
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            // pst.setObject(1, month.toUpperCase());
            //pst.setObject(2, staffid);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                leaveisvalid = rset.getBoolean(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LeaveSetup.class.getName()).log(Level.SEVERE, null, ex);
        }
        return leaveisvalid;
    }
    
    
    public Boolean leaveISValid2(String info, String leavetype, Connection conn) {
        Boolean leaveisvalid = false;

    //    String[] alldet = info.split("/");

        String staffid = info;
        String sql = "select case when '" + staffid.toUpperCase() + "' in (SELECT UPPER(pfno) from hr.leave_application where pfno='" + staffid.toUpperCase() + "' AND upper(leavetype)='"+leavetype.toUpperCase()+"' AND (approved IS true OR cancelled is true)) then true else false end --from hr.leaverota ";
        System.err.println(sql);
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            // pst.setObject(1, month.toUpperCase());
            //pst.setObject(2, staffid);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                leaveisvalid = rset.getBoolean(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LeaveSetup.class.getName()).log(Level.SEVERE, null, ex);
        }
        return leaveisvalid;
    }
    
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //   processRequest(request, response);

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String result = "";
        //request.getParameter("leaveyear");
        if (session != null) {
            String sessionid = session.getAttribute("sessionid").toString();
            String staffid = session.getAttribute("staffid").toString();

            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");

            // "PF No: " + pf + "\nName:" + $('#nigganame').text() + "\nLeavetype: " + leavetype + "\nDays: " + days + "\nCovering Officer: " + covering + "\nfor the month of " + month,
//"pfno=" + pf + "&leaveyr=" + leaveyr + "&leavetype=" + leavetype + "&days=" + days + "&covering=" + covering + "&month=" + month;
            String transno = request.getParameter("transno");
            if (transno.length() > 2) {
                String sql = "DELETE FROM  hr.leaverota WHERE oid::varchar = ?::varchar;";
                String sql2 = "SELECT  'Deleted '||refid||'/ '||staffid||' /'||leavetype||' /'||days_plus||' / '||coveringofficer||' / '||month  FROM  hr.leaverota WHERE oid::varchar = ?::varchar;";
                String transaction = "";
                try {
                    conn.setAutoCommit(false);

                    PreparedStatement pst = conn.prepareStatement(sql);
                    /////////////////----
                    PreparedStatement pst2 = conn.prepareStatement(sql2); //Let us get the data first before executing delete statement;
                    pst2.setObject(1, transno);
                    ResultSet rset = pst2.executeQuery();
                    while (rset.next()) {
                        transaction = rset.getString(1);
                    }

                    //////////////////////---
                    pst.setObject(1, transno);
                    pst.executeUpdate();
                    //"INSERT INTO leavelogs(refid, transtype, action) SELECT '" + refid + "', '" + trans + "','" + action + "' "
                    LeaveFactory.updateLogs(conn, transaction, transaction, "Deleted");
                    conn.setAutoCommit(true);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    try {
                        conn.rollback();
                    } catch (SQLException ex1) {
                        ex1.printStackTrace();
                    }
                }
            } else {

                String pf = request.getParameter("pfno");
                String leaveyr = request.getParameter("leaveyr");
                String leavetype = request.getParameter("leavetype").toUpperCase();
                String days = request.getParameter("days");
                String covering = request.getParameter("covering");
                String month = request.getParameter("month").toUpperCase();
                String permtype = LeaveFactory.getPermissionType(leavetype, conn);

                if (!leaveISValid(month, covering, conn)) {
                    String sql = "INSERT INTO hr.leaverota( rota_fy, refid, staffid, leavetype, days_plus, days_minus,  coveringofficer, month)VALUES (?, ?, ?, ?, ?::int, ?::int, ?,?);";
                    try {
                        conn.setAutoCommit(false);
                        PreparedStatement pst = conn.prepareStatement(sql);
                        pst.setObject(1, leaveyr);
                        pst.setObject(2, leaveyr);
                        pst.setObject(3, pf);
                        pst.setObject(4, leavetype);
                        pst.setObject(5, Integer.valueOf(days));
                        pst.setObject(6, Integer.valueOf(0));
                        pst.setObject(7, covering);
                        pst.setObject(8, month);
                        pst.executeUpdate();
                        conn.setAutoCommit(true);
                        result = "OK";

                        if (permtype.contains("GRANTED")) {

                            String date = request.getParameter("date");
                            //INSERT LEAVE automatically
                            sql = "INSERT INTO hr.leave_application(\n"
                                    + "            refno, pfno, leavetype, daysrequested, leavestart, \n"
                                    + "            coveringofficer, \n"
                                    + "            fy, leaveaddress, comments,coveringreal)\n"
                                    + "    VALUES (?, ?, ?, ?, ?::date, ?,?,?,?,?);";//SELECT to_char(to_date('23-11-2018','dd-mm-yyyy'), 'yyyy-mm-dd');

                            String refno = "MTRH/HR/L/" + pf + "" + randomAlphaNumeric(5);

                            pst = conn.prepareStatement(sql);
                            pst.setObject(1, refno);
                            pst.setObject(2, pf);
                            pst.setObject(3, leavetype);
                            pst.setInt(4, Integer.valueOf(days));
                            pst.setObject(5, date);
                            pst.setObject(6, covering);
                            pst.setObject(7, "");
                            pst.setObject(8, "-");
                            pst.setObject(9, "");
                            pst.setObject(10, covering);
                            pst.executeUpdate();

                            //Alert staff;
                            String applicantphone = UserPhone(pf, conn);

                            String leavend = LeaveFactory.getLeaveEnd(conn, date.replace("/", "-"), Integer.valueOf(days), leavetype);
                            String staffname = FullnameByID(pf, conn);

                            new SendSms(applicantphone.toString(), "Dear " + staffname + ",\n your " + leavetype + " has been approved for " + days + "days.  Ref: " + refno + "starting from " + LeaveFactory.getFormattedDate(conn, date) + " to " + LeaveFactory.getFormattedDate(conn, leavend) + ".\n Please make necessary handover arrangements in your section before proceeding.", refno, conn);

                            System.err.println(applicantphone.toString() + "Dear " + staffname + ",\n your " + leavetype + " has been approved for " + days + "days.  Ref: " + refno + "starting from " + LeaveFactory.getFormattedDate(conn, date) + " to " + LeaveFactory.getFormattedDate(conn, leavend) + ".\n Please make necessary handover arrangements in your section before proceeding.");
                            //Alert
                            //String[] officerdetails = covering.toString().split("/");

                            // String staffID = officerdetails[1];
                            //    addRights("1.Confirmation of Covering officer", staffID, conn);
                            //     new SendSms(officerphone.toString(), "Hello,\n" + fullname + " has sent a request for  " + leavetype + "  Ref: " + refno + " starting " + appldate + "\n and you are required to cover their duties during the period they will be away", refno, conn);
                            //  result = refno;
                            //ALERT STAFF 
                            LeaveFactory.updateLogs(conn, leaveyr, leavetype + " Entry for " + pf + " days" + days + " /" + month, "Entry recorded");

                        }
                        // conn.commit();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        try {
                            conn.rollback();
                        } catch (SQLException ex1) {
                            ex1.printStackTrace();
                        }
                    }

                } else {
                    result = "NOT OK";
                }
            }

            //  }
        }

        out.write(result);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static String FullnameByID(String key, Connection conn) {

        String login = "";

        String sql = "";

        // sql = "select login_name from secure_password where  staffid = ?";
        sql = "select fullname from secure_password where staffid = ?";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setObject(1, key);
            ResultSet rset = pst.executeQuery();
            if (rset.next()) {
                login = rset.getString(1);
            } else {
                login = "None";
            }

            //  conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return login;
    }

    private static String UserPhone(String key, Connection conn) {

        String login = "";

        String sql = "";

        // sql = "select login_name from secure_password where  staffid = ?";
        sql = "select phone from secure_password where staffid = ?";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setObject(1, key);
            ResultSet rset = pst.executeQuery();
            if (rset.next()) {
                login = rset.getString(1);
            } else {
                login = "None";
            }

            //  conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return login;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //    processRequest(request, response);
        // processRequest(request, response);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String result = "";
        //request.getParameter("leaveyear");
        if (session != null) {
            String sessionid = session.getAttribute("sessionid").toString();
            String staffid = session.getAttribute("staffid").toString();

            //staffid
            //  String loginid = session.getAttribute("username").toString();
            System.err.println("Matching key: " + sessionid);

            //  if (key.equalsIgnoreCase(sessionid)) {
            // msg="1";
            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");

            String leaveyr = getCurrentFY(conn);

            staffid = request.getParameter("staffid");

            result = getLeaveBalances(conn, staffid, leaveyr);

            //  }
        }

        out.write(result);

    }

    private int getLeaveDays(String fy, String leavetype, String pfno, Connection connectDB) {
        int days = 0;

        String sq = "SELECT rota_fy, refid, staffid, leavetype, days_plus, days_minus, leavestart, \n"
                + "       coveringofficer\n"
                + "  FROM hr.leaverota;";
        String sql = "SELECT sum(days_plus-days_minus) from hr.leaverota where rota_fy =? and leavetype ilike ? and staffid = ?";
        //"SELECT sum(daysapproved-dayscompensated) from hr.leave_application where fy =? and leavetype ilike ? and pfno = ? AND approved is true";

        System.err.println("SELECT sum(days_plus-days_minus) from hr.leaverota where rota_fy ='" + fy + "' and leavetype ilike '" + leavetype + "' and staffid = '" + pfno + "'");
        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, fy);
            pst.setObject(2, leavetype);
            pst.setObject(3, pfno);
            // pst.setObject(4, fy);

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

    private String getLeaveBalances(java.sql.Connection connectDB, String userref, String leaveyr) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent List

        System.err.println(userref);
        // Vector v = new Vector();
        String sql = "";
        //  String[] columns = new String[]{"leaveyr","leavetype", "days","personcovering", "month"};

        sql = "select rota_fy,leavetype,days_plus,coveringofficer,month, hr.leaverota.oid, secure_password.designation||','||secure_password.department as designation, secure_password.section as section  from hr.leaverota, secure_password \n"
                + "where days_plus >0 and hr.leaverota.staffid = ? and secure_password.staffid=? and rota_fy = ?;";//"select rota_fy,leavetype,days_plus,coveringofficer,month, oid from hr.leaverota where days_plus >0 and staffid = ? and rota_fy = ?;";

        System.err.println(sql + "-" + userref);
        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, userref);
            pst.setObject(2, userref);
            pst.setObject(3, leaveyr);
            ResultSet rset = pst.executeQuery();
            ResultSetMetaData metaData = rset.getMetaData();

            int columns = metaData.getColumnCount();
            while (rset.next()) {

                HashMap<String, String> child = new HashMap<String, String>();
                for (int i = 0; i < columns; i++) {

                    child.put(metaData.getColumnName(i + 1), rset.getObject(i + 1).toString());

                }

                parentList.add(child);
            }

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }
        String json = new Gson().toJson(parentList);//String JSON object

        System.err.println("I am here Days " + json);

        JSONArray arr = new JSONArray();
        arr.put(json);                         //JSON Array

        return json;
    }

    private static String getCurrentFY(Connection conn) {
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

    private static final String ALPHA_NUMERIC_STRING = "ABCDE0123456789";

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
