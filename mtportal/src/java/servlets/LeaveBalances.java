/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement; 
import java.sql.ResultSet;
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
@WebServlet(name = "LeaveBalances", urlPatterns = {"/LeaveBalances"})
public class LeaveBalances extends HttpServlet {

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
            out.println("<title>Servlet LeaveBalances</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LeaveBalances at " + request.getContextPath() + "</h1>");
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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //  processRequest(request, response);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String result = "";
        if (session != null) {
            String sessionid = session.getAttribute("sessionid").toString();
            String staffid = session.getAttribute("staffid").toString();
            
            String empid=request.getParameter("staffid");
            
            if(empid.length()>2){
                staffid=empid;
            }

            //staffid
            //  String loginid = session.getAttribute("username").toString();
            System.err.println("Matching key: " + sessionid);

            //  if (key.equalsIgnoreCase(sessionid)) {
            // msg="1";
            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");

            result = getLeaveHistory(conn, staffid);

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

    private String getLeaveBalances(java.sql.Connection connectDB, String userref, String leaveyr) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent List

        System.err.println(userref);
        // Vector v = new Vector();
        String sql = "";
        String[] columns = new String[]{"leavetype", "days"};

        sql = "select DISTINCT description, days from hr.leave_types;";

        System.err.println(sql + "-" + userref);
        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            // pst.setObject(1, userref);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {

                //    int myleaveDays = getLeaveDays("", rset.getObject(1).toString(), userref, connectDB);//No of days I have gone for leave
                //  int maxLeaveDays = getLeaveMaximumDays(rset.getObject(1).toString(), connectDB);//No of days maximum for leave
                int leaveBalance = getLeaveDays(leaveyr, rset.getObject(1).toString(), userref, connectDB);;
                HashMap<String, String> child = new HashMap<String, String>();
                // for (int i = 0; i < columns.length; i++) {
                child.put(columns[0].toString(), rset.getObject(1).toString());
                child.put(columns[1].toString(), String.valueOf(leaveBalance));
                //  }

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

    private String getLeaveHistory(java.sql.Connection connectDB, String userref) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent List

        //System.err.println(userref);
        // Vector v = new Vector();
        String sql = "";
        String[] columns = new String[]{"leaveid", "leavetype", "daysreq", "status"};

        sql = "select refno, leavetype, daysrequested::int, \n"
                + "case when approved is true then 'approved'  when cancelled is true then 'rejected' else 'pending' end from hr.leave_application\n"
                + "WHERE pfno = ? order by appliedon desc;";

        System.err.println(sql + "-" + userref);
        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, userref);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {

                HashMap<String, String> child = new HashMap<String, String>();
                for (int i = 0; i < columns.length; i++) {
                    child.put(columns[i].toString(), rset.getObject(i + 1).toString());
                    // child.put(columns[1].toString(), String.valueOf(leaveBalance));
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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

            result = getLeaveBalances(conn, staffid, leaveyr);

            //  }
        }

        out.write(result);

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
