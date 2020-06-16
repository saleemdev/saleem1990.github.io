/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
@WebServlet(name = "VerifyUser", urlPatterns = {"/VerifyUser"})
public class VerifyUser extends HttpServlet {

    private String msg;

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
            out.println("<title>Servlet VerifyUser</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VerifyUser at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private String getUserDetails(java.sql.Connection connectDB, String userref, Object transtype) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent List

        // Vector v = new Vector();
        String sql = "";
        String[] columns = new String[]{"empno", "name", "designation", "email", "phone", "account", "department", "fslogin", "tlogin","section"};
        //kkkk-------
        if (transtype != null && transtype.toString().equalsIgnoreCase("ALL")) {
            sql = "SELECT employee_no,first_name, case when length(section)<2  then '-' else section end as section,email_address,'','','','' FROM fsmaster WHERE employee_no= ?";
        } else {
            sql = "SELECT staffid,fullname,designation, email, phone, login_name, department, CASE WHEN first_time_login is FALSE OR first_time_login IS NULL THEN 'fs' ELSE 'nfs' END, CASE WHEN tmplogin IS false THEN 'ntl' ELSE 'tl' END, section from secure_password where login_name = ? or staffid=?;";

        }
        System.err.println(sql + " is this " + userref);
        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, userref);
            pst.setObject(2, userref);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {

                HashMap<String, String> child = new HashMap<String, String>();
                for (int i = 0; i < columns.length; i++) {
                    child.put(columns[i].toString(), rset.getObject(i + 1).toString());
                }

                parentList.add(child);
            }

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }
        String json = new Gson().toJson(parentList);//String JSON object
        
        JSONArray arr = new JSONArray();
        arr.put(json);                         //JSON Array

        return json;
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

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String staffid = request.getParameter("staffid");

        HttpSession session = request.getSession();
        String result = "";
        if (session != null) {
            String sessionid = session.getAttribute("sessionid").toString();
            String loginid = session.getAttribute("username").toString();
            // System.err.println("Matching key: " + sessionid);

            //  if (key.equalsIgnoreCase(sessionid)) {
            // msg="1";
            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");
            if (staffid.length() > 2) {
                result = getUserDetails(conn, staffid, "-").replace("[", "").replace("]", "");
            } else {
                result = getUserDetails(conn, loginid, "-").replace("[", "").replace("]", "");
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
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //   processRequest(request, response);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String key = request.getParameter("sessionkey");
        System.err.println("Parameter: " + key);
        HttpSession session = request.getSession();
        String result = "";
        if (session != null) {
            String sessionid = session.getAttribute("sessionid").toString();
            String loginid = session.getAttribute("username").toString();
            System.err.println("Matching key: " + sessionid);

            if (key.equalsIgnoreCase(sessionid)) {
                // msg="1";
                session.setAttribute("validated", "yes");
                java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");

                result = getUserDetails(conn, loginid, "-").replace("[", "").replace("]", "");

            }
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
