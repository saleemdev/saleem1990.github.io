/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.mtrh.mtportal.sys.LeaveFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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
@WebServlet(name = "CheckForBiddenMonth", urlPatterns = {"/CheckForBiddenMonth"})
public class CheckForBiddenMonth extends HttpServlet {

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
            out.println("<title>Servlet CheckForBiddenMonth</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CheckForBiddenMonth at " + request.getContextPath() + "</h1>");
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
        response.setContentType("text/plain;charset=UTF-8");

        String msg = "NO";

        String refno = request.getParameter("rqid");

        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        String result = "";
        String staffid = "";
        if (session != null) {
            String sessionid = session.getAttribute("sessionid").toString();
            String staffno = session.getAttribute("staffid").toString();

            //session.getAttribute("staffid").toString();
            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");

            String leavestart = LeaveFactory.getLeaveStart(refno, conn);
            String leavend = "";
            String leavetype = "";
            int days = 0;

            try {
                PreparedStatement pst = conn.prepareStatement("SELECT daysrequested, leavetype FROM hr.leave_application WHERE refno =?");

                pst.setObject(1, refno);
                ResultSet rset = pst.executeQuery();
                while (rset.next()) {
                    days = rset.getInt(1);
                    leavetype = rset.getString(2);
                }

                leavend = LeaveFactory.getLeaveEnd(conn, leavestart, days, leavetype);

                String monthend = leavend.split("-")[1];
                String monthstart = leavestart.split("-")[1];

                msg = monthstart + "-" + monthend;

                if (isMonthForbidden(monthend, leavetype, conn).toString().equalsIgnoreCase("YES") || isMonthForbidden(monthstart, leavetype, conn).toString().equalsIgnoreCase("YES")) {
                    msg = msg + "YES";
                } else {
                    msg = msg + "NO";

                }

            } catch (SQLException ex) {
                Logger.getLogger(CheckForBiddenMonth.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        out.write(msg);

    }

    public static String isMonthForbidden(String monthid, String leavetype, Connection conn) {
        String stat = "NO";
        try {
            PreparedStatement pst = conn.prepareStatement("SELECT CASE WHEN forbiddenmonth ilike '%" + monthid + "%'  THEN 'YES' ELSE 'NO' END AS stat from hr.leave_types WHERE upper(description) ='" + leavetype.toUpperCase() + "'");
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                stat = rset.getObject(1).toString();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CheckForBiddenMonth.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stat;
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
