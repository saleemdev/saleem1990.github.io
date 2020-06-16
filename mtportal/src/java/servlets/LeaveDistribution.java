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
@WebServlet(name = "LeaveDistribution", urlPatterns = {"/LeaveDistribution"})
public class LeaveDistribution extends HttpServlet {

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
            out.println("<title>Servlet LeaveDistribution</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LeaveDistribution at " + request.getContextPath() + "</h1>");
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
        //   processRequest(request, response);
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

            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");

            String leaveyr = getCurrentFY(conn);

            result = getAllDepts(conn);//.replace("[", "").replace("]", "");

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
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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

            String dept = request.getParameter("dept");

            //  if (key.equalsIgnoreCase(sessionid)) {
            // msg="1";
            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");

            String leaveyr = getCurrentFY(conn);

            result = getLeaveBalances(conn, leaveyr, dept).replace("[", "").replace("]", "");

            //  }
        }

        out.write(result);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
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

    private int getCountByMonth(String leaveyr, String month, String dept, Connection conn) {
        int count = 0;
        String sql = "SELECT COUNT(distinct staffid) FROM hr.leaverota WHERE UPPER(month)= ? AND rota_fy=? AND days_plus>0";
        if (dept.length() > 3) {
            sql = sql + " and staffid IN (select staffid from secure_password where upper (department) ilike '" + dept.toUpperCase() + "')";
        }

        try {

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setObject(1, month);
            pst.setObject(2, leaveyr);

            ResultSet rset = pst.executeQuery();
            if (rset.next()) {
                count = rset.getInt(1);
            }

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }

        return count;
    }

    private String getLeaveBalances(java.sql.Connection connectDB, String leaveyr, String department) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent List

        String[] columns = new String[]{"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};

        HashMap<String, String> child = new HashMap<String, String>();
        for (int i = 0; i < columns.length; i++) {

            int leaveCount = getCountByMonth(leaveyr, columns[i].toString().toUpperCase(), department, connectDB);
            System.err.println(columns[i].toString() + ": " + leaveCount);
            child.put(columns[i].toString(), String.valueOf(leaveCount));
        }
        parentList.add(child);
        String json = new Gson().toJson(parentList);//String JSON object

        System.err.println("I am here Days " + json);

        JSONArray arr = new JSONArray();
        arr.put(json);                         //JSON Array

        return json;
    }

    private String getAllDepts(java.sql.Connection connectDB) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent List

        String[] columns = new String[]{"dept"};
        String sql = "select distinct department from secure_password order by 1";

        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                HashMap<String, String> child = new HashMap<String, String>();
                for (int i = 0; i < columns.length; i++) {

                  
                    child.put(columns[i].toString(), String.valueOf(rset.getObject(i+1)));
                }
                parentList.add(child);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        String json = new Gson().toJson(parentList);//String JSON object

        System.err.println("I am here departments " + json);

        JSONArray arr = new JSONArray();
        arr.put(json);                         //JSON Array

        return json;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
