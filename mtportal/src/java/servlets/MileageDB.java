/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author owner
 */
@WebServlet(name = "MileageDB", urlPatterns = {"/MileageDB"})
public class MileageDB extends HttpServlet {

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
            out.println("<title>Servlet MileageDB</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MileageDB at " + request.getContextPath() + "</h1>");
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

        PrintWriter out = response.getWriter();
        response.setContentType("text/plain");

        HttpSession session = request.getSession();

        String isExists = "0";

        String result = "-";
        if (session != null) {
            //java.sql.Connection conn = ConnectionProperties.getConnect2DB();
            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");

            String vehreg = request.getParameter("regno");

            String rqtype = request.getParameter("rqtype");

            String sql = "SELECT rqid, vehicle_requested, rqid||'-'||humanreadabledate((enteredon::date)::varchar) FROM fleet.transportrequestmemo WHERE vehicle_requested = ? and rqid IN (Select refid from fleet.authorizations WHERE stage = 'AUTHORIZE REQUEST')  ORDER BY enteredon desc limit 1;";

            try {
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setObject(1, vehreg);
                ResultSet rset = pst.executeQuery();

                if (rset.next()) {
                    result = rset.getObject(3).toString();
                }

            } catch (SQLException ex) {
                Logger.getLogger(MileageDB.class.getName()).log(Level.SEVERE, null, ex);
            }

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
        //processRequest(request, response);
        PrintWriter out = response.getWriter();
        response.setContentType("text/plain");

        HttpSession session = request.getSession();

       // String isExists = "0";

        String result = "-";
        if (session != null) {
            //java.sql.Connection conn = ConnectionProperties.getConnect2DB();
            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");

            String reference = request.getParameter("refno");

           // String rqtype = request.getParameter("rqtype");

            String sql = "SELECT current_mileage FROM fleet.vehicle_mileage WHERE refno =? limit 1;";

            try {
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setObject(1, reference);
                ResultSet rset = pst.executeQuery();

                if (rset.next()) {
                    result = rset.getObject(1).toString();
                }

            } catch (SQLException ex) {
                result = ex.getMessage();
                ex.printStackTrace();
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
