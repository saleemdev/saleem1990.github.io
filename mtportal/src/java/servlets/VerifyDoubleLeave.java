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
@WebServlet(name = "VerifyDoubleLeave", urlPatterns = {"/VerifyDoubleLeave"})
public class VerifyDoubleLeave extends HttpServlet {

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
            out.println("<title>Servlet VerifyDoubleLeave</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VerifyDoubleLeave at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        String result = "";

        String leavetype = request.getParameter("lvtype");
        String pfno = request.getParameter("staffid");

        //String stage = request.getParameter("stage");
        if (session != null) {
            String sessionid = session.getAttribute("sessionid").toString();
            String staffid = session.getAttribute("staffid").toString();

            
            System.err.println("Matching key: " + sessionid);

          
            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");

            String fy = LeaveFactory.getCurrentFY(conn);


            result = getStat(conn, pfno, fy, leavetype);

            //  }
        }

        out.write(result);

    }

    private static String getStat(Connection conn, String pfno, String fy, String leavetype) {
        String status = "";
        String sql = "SELECT case when cancelled is true OR approved is true then 'actioned' else 'not actioned' end "
                + "from hr.leave_application where pfno=?\n"
                + "and fy =? and UPPER(leavetype) = upper(?) order by 1 desc limit 1";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setObject(1, pfno);
            pst.setObject(2, fy);
            pst.setObject(3, leavetype.toUpperCase());
            ResultSet rset = pst.executeQuery();

            while (rset.next()) {
                status = rset.getObject(1).toString();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return status;
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
