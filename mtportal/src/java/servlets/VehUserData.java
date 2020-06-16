/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
//import com.mtrh.fleet.dao.ConnectionProperties;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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
@WebServlet(name = "VehUserData", urlPatterns = {"/VehUserData"})
public class VehUserData extends HttpServlet {

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
            out.println("<title>Servlet UserData</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserData at " + request.getContextPath() + "</h1>");
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
        // processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private String getAuthorizingOfficer(java.sql.Connection connectDB) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent List

        // Vector v = new Vector();
        String[] columns = new String[]{"managers"};

        String sql = "SELECT DISTINCT fullname||'/'||staffid FROM secure_password WHERE designation ILIKE '%MANAGER%' OR designation ILIKE '%EXECUTIVE%'";
        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            //   pst.setObject(1, fleetno);
            //  pst.setObject(2, fleetno);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {

                HashMap<String, String> child = new HashMap<String, String>();
                for (int i = 0; i < columns.length; i++) {
                    String obj = rset.getObject(i + 1).toString().replace("&", "AND");
                    child.put(columns[i].toString(), obj);
                }

                parentList.add(child);
            }

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }
        String json = new Gson().toJson(parentList);//String JSON object

        //   System.err.println("I am here " + json);
        JSONArray arr = new JSONArray();
        arr.put(json);                         //JSON Array

        return json;

    }

    private String getSections(java.sql.Connection connectDB) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent List

        // Vector v = new Vector();
        String[] columns = new String[]{"sections"};

        String sql = "select department from pb_departments order by 1";
        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            //   pst.setObject(1, fleetno);
            //  pst.setObject(2, fleetno);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {

                HashMap<String, String> child = new HashMap<String, String>();
                for (int i = 0; i < columns.length; i++) {
                    String obj = rset.getObject(i + 1).toString().replace("&", "AND").trim();
                    child.put(columns[i].toString(), obj);
                }

                parentList.add(child);
            }

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }

        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String json = gson.toJson(parentList);//String JSON object

        //   System.err.println("I am here " + json);
        JSONArray arr = new JSONArray();
        arr.put(json);                         //JSON Array

        return json;

    }

    private String getDrivers(java.sql.Connection connectDB) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent List

        // Vector v = new Vector();
        String[] columns = new String[]{"drivers"};

        String sql = "SELECT DISTINCT fullname||'/'||staffid FROM secure_password where department ILIKE '%transport%'";
        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            //   pst.setObject(1, fleetno);
            //  pst.setObject(2, fleetno);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {

                HashMap<String, String> child = new HashMap<String, String>();
                for (int i = 0; i < columns.length; i++) {

                    String obj = rset.getObject(i + 1).toString().replace("&", "AND");
                    child.put(columns[i].toString(), obj);
                }

                parentList.add(child);
            }

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }
        //   String json = new Gson().toJson(parentList);//String JSON object

        Gson gson = new GsonBuilder().disableHtmlEscaping().create();

        String json = gson.toJson(parentList);//String JSON object

        //   System.err.println("I am here " + json);
        JSONArray arr = new JSONArray();
        arr.put(json);                         //JSON Array

        return json;

    }

    private String getAllocatedVehicles(java.sql.Connection connectDB, String driver) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent List

        // Vector v = new Vector();
        String[] columns = new String[]{"vehicle"};

        String sql = "select regno from fleet.vehicle_register where UPPER(driver) ilike ?";
        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, driver.toUpperCase());
            //  pst.setObject(2, fleetno);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {

                HashMap<String, String> child = new HashMap<String, String>();
                for (int i = 0; i < columns.length; i++) {

                    String obj = rset.getObject(i + 1).toString().replace("&", "AND");
                    child.put(columns[i].toString(), obj);
                }

                parentList.add(child);
            }

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }
        //   String json = new Gson().toJson(parentList);//String JSON object

        Gson gson = new GsonBuilder().disableHtmlEscaping().create();

        String json = gson.toJson(parentList);//String JSON object

        //   System.err.println("I am here " + json);
        JSONArray arr = new JSONArray();
        arr.put(json);                         //JSON Array

        return json;

    }

    private String getFreeVehicles(java.sql.Connection connectDB) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent List

        // Vector v = new Vector();
        String[] columns = new String[]{"vehicle"};

        String sql = "select regno from fleet.vehicle_register ORDER BY 1;";
        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {

                HashMap<String, String> child = new HashMap<String, String>();
                for (int i = 0; i < columns.length; i++) {

                    String obj = rset.getObject(i + 1).toString().replace("&", "AND");
                    child.put(columns[i].toString(), obj);
                }

                parentList.add(child);
            }

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }
        //   String json = new Gson().toJson(parentList);//String JSON object

        Gson gson = new GsonBuilder().disableHtmlEscaping().create();

        String json = gson.toJson(parentList);//String JSON object

        //   System.err.println("I am here " + json);
        JSONArray arr = new JSONArray();
        arr.put(json);                         //JSON Array

        return json;

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String transtype = request.getParameter("transtype");
        System.err.println("I want " + transtype);
        //Connection conn = ConnectionProperties.getConnect2DB();
        String result = "";
        HttpSession session = request.getSession();
        if (session != null) {
            Connection conn = (Connection) session.getAttribute("connection");

            if (transtype.equalsIgnoreCase("managers")) {
                result = getAuthorizingOfficer(conn).replace("\u0026", "");;
            }
            if (transtype.equalsIgnoreCase("drivers")) {
                result = getDrivers(conn).replace("\u0026", "");;
            }
            if (transtype.equalsIgnoreCase("sections")) {
                result = getSections(conn).replace("\u0026", "");
            }

            if (transtype.equalsIgnoreCase("alloc")) {
                String driver = request.getParameter("driver");
                result = getAllocatedVehicles(conn, driver).replace("\u0026", "");
            }
            if (transtype.equalsIgnoreCase("free_v")) {
                // String driver = request.getParameter("driver");
                result = getFreeVehicles(conn).replace("\u0026", "");
            }

            System.err.println("I am here " + result);

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
