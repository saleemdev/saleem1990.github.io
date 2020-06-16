/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
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
@WebServlet(name = "Defferment", urlPatterns = {"/Defferment"})
public class Defferment extends HttpServlet {

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
            out.println("<title>Servlet Defferment</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Defferment at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
        //var request = "leaveid=" + lvid + "&staffid=" + staffid + "&leavestart=" + leavestart + "&days=" + days + "&reason=" + reason;

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        String leaveid = request.getParameter("leaveid");
        String staffid = request.getParameter("staffid");
        String leavestart = request.getParameter("leavestart");
        String days = request.getParameter("days");
        String reason = request.getParameter("reason");
        String result = "";
        if (session != null) {

            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");

            try {
                PreparedStatement pst = conn.prepareStatement("INSERT INTO hr.leavedeferrment(leaveid, origdays, deferreddays, origdate, deferreddate,reason)\n"
                        + " SELECT refno,daysrequested,'" + days + "',leavestart, '" + leavestart + "','"+reason+"' FROM hr.leave_application  where refno='" + leaveid + "' ");
                pst.executeUpdate();

                pst = conn.prepareStatement("update hr.leave_application set daysrequested='" + days + "'::int, leavestart='" + leavestart + "' where refno='" + leaveid + "'");
                pst.executeUpdate();
                
                result="OK";
                
            } catch (SQLException ex) {
                Logger.getLogger(Defferment.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
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
