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
import org.json.JSONArray;

/**
 *
 * @author owner
 */
@WebServlet(name = "MaintenanceRequests", urlPatterns = {"/MaintenanceRequests"})
public class MaintenanceRequests extends HttpServlet {

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
            out.println("<title>Servlet MaintenanceRequests</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MaintenanceRequests at " + request.getContextPath() + "</h1>");
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
    public static String getVehicleSearchResults(java.sql.Connection connectDB, String searchkey) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent List

        String[] columns = new String[]{"make"};

        String sql = "select regno from fleet.vehicle_register WHERE regno ILIKE '%"+searchkey+"%' OR fleetno ILIKE '%"+searchkey+"%' ORDER BY 1;";

        System.err.println(sql);
        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);

            ResultSet rset = pst.executeQuery();

            int j = 0;
            while (rset.next()) {

                HashMap<String, String> child = new HashMap<String, String>();
                for (int i = 0; i < columns.length; i++) {
                    child.put("value", rset.getObject(1).toString().toUpperCase());
                }
                parentList.add(child);
                j++;
            }

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }

        String json = new Gson().toJson(parentList);//String JSON object

        System.err.println("I am here " + json);

        return json;

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       

        response.setContentType("application/json");
        String searchkey = request.getParameter("vehreg");
        
        PrintWriter out = response.getWriter();

        java.sql.Connection conn = new LoginController().connect("postgres", "sequence");

        String json = getVehicleSearchResults(conn, searchkey);

        System.err.println("Godammit yo: " + json); 

        out.write(json);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public static String getMaintenanceTypes(java.sql.Connection connectDB) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent List

        String[] columns = new String[]{"make"};

        String sql = "SELECT distinct upper(maintenancetypes)\n"
                + "  FROM fleet.maintenancelist ORDER BY 1;";

        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);

            ResultSet rset = pst.executeQuery();

            int j = 0;
            while (rset.next()) {

                HashMap<String, String> child = new HashMap<String, String>();
                for (int i = 0; i < columns.length; i++) {
                    child.put("value", rset.getObject(1).toString().toUpperCase());
                }
                parentList.add(child);
                j++;
            }

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }

        String json = new Gson().toJson(parentList);//String JSON object

        System.err.println("I am here " + json);

        return json;

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // processRequest(request, response);
        response.setContentType("application/json");
        
        PrintWriter out = response.getWriter();

        java.sql.Connection conn = new LoginController().connect("postgres", "sequence");

        String json = getMaintenanceTypes(conn);

        System.err.println("Godammit: " + json);

        out.write(json);

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
