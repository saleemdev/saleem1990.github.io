/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.mtrh.mtportal.sys.LeaveFactory;
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
@WebServlet(name = "LeaveModification", urlPatterns = {"/LeaveModification"})
public class LeaveModification extends HttpServlet {

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
            out.println("<title>Servlet LeaveModification</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LeaveModification at " + request.getContextPath() + "</h1>");
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
        // processRequest(request, response);
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        String result = "-";
        HttpSession session = request.getSession();
        if (session != null) {
            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");

            //var request = "refid=" + refno+"&days="+days+"&covering="+covering+"&date="+date;
            String staffid = request.getParameter("staffid");

            String refid = request.getParameter("refid");
            String days = request.getParameter("days");
            String covering = request.getParameter("covering");
            String date = request.getParameter("date");
            try {

                PreparedStatement pst = conn.prepareStatement("UPDATE hr.leave_application SET daysrequested = ? , coveringreal = ?, leavestart = ?::date  WHERE refno = ?");
                pst.setInt(1, Integer.valueOf(days));
                pst.setObject(2, covering);
                pst.setObject(3, date);
                pst.setObject(4, refid);
                pst.executeUpdate();
                
                
                pst = conn.prepareStatement("UPDATE hr.leaverota SET days_minus=? WHERE refid =?");
                pst.setObject(1, Integer.valueOf(days));
                pst.setObject(2, refid);
                pst.executeUpdate();

                LeaveFactory.updateLogs(conn, refid, "LEAVE FORM UPDATE", "Updated leave Form attributes for  "+refid);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            //---//

            result = "Done Successfully";

//--//         
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
