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
@WebServlet(name = "LeaveRota", urlPatterns = {"/LeaveRota"})
public class LeaveRota extends HttpServlet {

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
            out.println("<title>Servlet LeaveRota</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LeaveRota at " + request.getContextPath() + "</h1>");
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
    public Boolean leaveISValid(String month, String covering, Connection conn) {
        Boolean leaveisvalid = false;

        String[] alldet = covering.split("/");

        String staffid = alldet[1];
        String sql = "select case when '" + month.toUpperCase() + "' in (SELECT UPPER(month) from hr.leaverota where staffid='" + staffid + "' AND days_plus>0) then true else false end --from hr.leaverota ";
        System.err.println(sql);
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            // pst.setObject(1, month.toUpperCase());
            //pst.setObject(2, staffid);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                leaveisvalid = rset.getBoolean(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return leaveisvalid;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // processRequest(request, response);
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String result = "";
        //request.getParameter("leaveyear");
        if (session != null) {
            String sessionid = session.getAttribute("sessionid").toString();
            String staffid = session.getAttribute("staffid").toString();
            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");
            //var request = "oid=" + oid + "&lvtype=" + lvtype + "&days=" + days + "&cvof=" + cvof + "&month=" + month;

            String oid = request.getParameter("oid");
            String lvtype = request.getParameter("lvtype").toUpperCase();
            int days = Integer.valueOf(request.getParameter("days"));
            String cvof = request.getParameter("cvof");
            String month = request.getParameter("month").toUpperCase();
            String pf = request.getParameter("staffid");

            String fy = getCurrentFY(conn);
            String transaction ="";
            if (!leaveISValid(month, cvof, conn)) {
                String sql = "UPDATE  hr.leaverota SET days_plus = ?, days_minus = ? , coveringofficer = ? , month = ?  WHERE oid::varchar = ?::varchar;";
                  String sql2 = "SELECT  'Updated '||refid||'/ '||staffid||' /'||leavetype||' /'||days_plus||' / '||coveringofficer||' / '||month  FROM  hr.leaverota WHERE oid::varchar = ?::varchar;";
                try {
                    conn.setAutoCommit(false);
                    /////
                     PreparedStatement pst2 = conn.prepareStatement(sql2); //Let us get the data first before executing update statement;
                    pst2.setObject(1, oid);
                    ResultSet rset = pst2.executeQuery();
                    while (rset.next()) {
                        transaction = rset.getString(1);
                    }
                   ///////////// 
                    
                    PreparedStatement pst = conn.prepareStatement(sql);
                    pst.setObject(1, days);
                    pst.setObject(2, 0);
                    pst.setObject(3, cvof);
                    pst.setObject(4, month);
                    pst.setObject(5, oid);
                    
                    LeaveFactory.updateLogs(conn, transaction+" into "+days+" "+cvof+" "+month, transaction, "updated" );

                    pst.executeUpdate();
                    conn.setAutoCommit(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    try {
                        conn.rollback();
                    } catch (SQLException ex1) {
                        ex1.printStackTrace();
                    }
                }

                result = "OK";
            } else {
                result = "NOT OK";
            }
            //  }
        }

        out.write(result);
    }

    private static String getCurrentFY(Connection conn) {
        String fy = "";
        try {
            String sql = "select yrid from hr.activeyear where status = true";
            PreparedStatement pst = conn.prepareStatement(sql);

            ResultSet rset = pst.executeQuery();
            if (rset.next()) {
                fy = rset.getObject(1).toString();
            }

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }

        return fy;
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
