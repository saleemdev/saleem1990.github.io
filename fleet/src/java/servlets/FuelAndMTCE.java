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
@WebServlet(name = "FuelAndMTCE", urlPatterns = {"/FuelAndMTCE"})
public class FuelAndMTCE extends HttpServlet {

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
            out.println("<title>Servlet FuelAndMTCE</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FuelAndMTCE at " + request.getContextPath() + "</h1>");
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
    private static String getInvInfo(java.sql.Connection connectDB, String rqid) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent List

        // Vector v = new Vector();
        String[] columns = new String[]{"vehicle", "servicetype", "refno", "planned", "status", "action"};

        String sql = "select vehicle_requested,rqtype, rqid, planned_date, case when completed is true then 'completed' else 'pending' end,\n"
                + "case when completed is true then 'completed' else 'pending' end\n"
                + "  from fleet.transportrequestmemo where vehicle_requested not ilike 'undefined' AND rqid NOT ilike 'MTRH/TR/%' and rqid ilike 'MTRH%'";
        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            //    pst.setObject(1, rqid);
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

        System.err.println("My Fuel Data " + json);

        JSONArray arr = new JSONArray();
        arr.put(json);                         //JSON Array

        return json;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String json = "";
        if (session != null) {
            String rqid = request.getParameter("rqid");
            String transtype = request.getParameter("transtype");

            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");//ConnectionProperties.getConnect2DB();

            json = getInvInfo(conn, rqid);

            System.err.println(json);

        }
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
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //  processRequest(request, response);

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String reply = "";

        //String rqid = request.getParameter("rqid");
        if (session != null) {
            String rqid = request.getParameter("rqid");
            String transtype = request.getParameter("transtype");

            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");//ConnectionProperties.getConnect2DB();

            String sql = "UPDATE fleet.transportrequestmemo SET completed = true WHERE rqid = ?";
            System.err.println( "UPDATE fleet.transportrequestmemo SET completed = true WHERE rqid = '"+rqid+"'");
            try {
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setObject(1, rqid);
                pst.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            reply = "Success";
        }
        out.write(reply);
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
