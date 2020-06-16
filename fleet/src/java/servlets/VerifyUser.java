/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import com.mtrh.fleet.dao.ConnectionProperties;
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
import org.json.JSONArray;

/**
 *
 * @author owner
 */
@WebServlet(name = "VerifyUser", urlPatterns = {"/VerifyUser"})
public class VerifyUser extends HttpServlet {

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

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private String verifyID(String user, String usertype, Connection connectDB) {
        String returnvalue = "0";
        String sql = "";
        if (usertype.equalsIgnoreCase("TRANSPORT")) {
            sql = "select count(*)  from secure_password where staffid  = '" + user + "'";
        } else {
            sql = "select count(*)  from fsmaster where employee_no  = '" + user + "'";
        }

        System.err.println(sql + "\n" + usertype);
        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            // pst.setObject(1, user.trim());
            // pst.setObject(2, user.trim());
            ResultSet rset = pst.executeQuery();

            while (rset.next()) {

                returnvalue = rset.getObject(1).toString();

                // }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return returnvalue;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // processRequest(request, response);
        PrintWriter out = response.getWriter();
        response.setContentType("text/plain");

        String user = request.getParameter("userid");

        String usertype = request.getParameter("usertype");

        LoginController ls = new LoginController();
        ls.connect("postgres", "sequence");
        Connection conn = ls.connect("postgres", "sequence");

        String isExists = verifyID(user, usertype, conn);

        System.err.println(isExists);
        out.write(isExists);

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
        //  processRequest(request, response);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String userid = request.getParameter("userid");

        Object transtype = request.getParameter("transtype");

        LoginController lc = new LoginController();
        Connection connectDB = lc.connect("postgres", "sequence");

        String json = getUserDetails(connectDB, userid, transtype).replace("[", "").replace("]", "");

        out.write(json);

    }

    private String getUserDetails(java.sql.Connection connectDB, String userref, Object transtype) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent List

        // Vector v = new Vector();
        String sql = "";
        String[] columns = new String[]{"empno", "name", "designation", "email", "phone", "account"};
        if (transtype != null && transtype.toString().equalsIgnoreCase("ALL")) {
            sql = "SELECT employee_no,first_name, case when length(section)<2  then '-' else section end as section,email_address,'','' FROM fsmaster WHERE employee_no= ?";
        } else {
            sql = "select  staffid,fullname,designation, email, phone, login_name from secure_password where staffid = ?;";

        }
        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, userref);
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

        System.err.println("I am here " + json);

        JSONArray arr = new JSONArray();
        arr.put(json);                         //JSON Array

        return json;
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
