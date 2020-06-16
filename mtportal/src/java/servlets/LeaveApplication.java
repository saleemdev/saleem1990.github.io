/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.mtrh.mtportal.sys.EmailClass;
import com.mtrh.mtportal.sys.OnfonGateway;
import com.mtrh.mtportal.sys.SendSms;
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
@WebServlet(name = "LeaveApplication", urlPatterns = {"/LeaveApplication"})
public class LeaveApplication extends HttpServlet {

    private String result;

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
            out.println("<title>Servlet LeaveApplication</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LeaveApplication at " + request.getContextPath() + "</h1>");
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
    public static String sqlDate(String date, Connection conn) {
        String sqldate = "";
        String sql = "SELECT to_char(to_date('" + date + "','dd-mm-yyyy'), 'yyyy-mm-dd');";

        System.err.println(sql);
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                sqldate = rset.getObject(1).toString();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.err.println("I am returning "+sqldate);
        return sqldate;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // = "transtype=" + transtype + "&pfno=" + pfno + "&leavetype=" + leavetype + "&daysapplied=" + daysapplied
        //    + "&appldate=" + appldate + "&officer=" + officer + "&officeremail=" + officeremail +
        //"&officerphone=" + officerphone + "&comments=" + comments;

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        result = "";
        if (session != null) {
            String sessionid = session.getAttribute("sessionid").toString();
            String staffid = session.getAttribute("staffid").toString();

            //staffid
            //  String loginid = session.getAttribute("username").toString();
            System.err.println("Matching key: " + sessionid);

            //  if (key.equalsIgnoreCase(sessionid)) {
            // msg="1";
            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");

            //  }
            Object transtype = request.getParameter("transtype");

            Object pfno = request.getParameter("pfno");
            Object leavetype = request.getParameter("leavetype");
            String daysapplied = request.getParameter("daysapplied").trim();
            String appldate = request.getParameter("appldate");
            Object officer = request.getParameter("officer");
            Object officeremail = request.getParameter("officeremail");
            Object officerphone = request.getParameter("officerphone");
            Object comments = request.getParameter("comments");

            Object email = request.getParameter("myMail");
            Object fullname = request.getParameter("fullname");

            Object leavecontact = request.getParameter("leaveaddress");

           // String sqldate = sqlDate(appldate, conn);

            String sql = "INSERT INTO hr.leave_application(\n"
                    + "            refno, pfno, leavetype, daysrequested, leavestart, \n"
                    + "            coveringofficer, \n"
                    + "            fy, leaveaddress, comments,coveringreal)\n"
                    + "    VALUES (?, ?, ?, ?, ?::date, ?,?,?,?,?);";//SELECT to_char(to_date('23-11-2018','dd-mm-yyyy'), 'yyyy-mm-dd');

            String refno = "MTRH/HR/L/" + pfno+""+randomAlphaNumeric(5);

            System.err.println(sql);

            
            //String coveringOfficerPF = getPFByName
            try {
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setObject(1, refno);
                pst.setObject(2, pfno);
                pst.setObject(3, leavetype);
                pst.setInt(4, Integer.valueOf(daysapplied));
                pst.setObject(5, appldate);
                pst.setObject(6, officer);
                pst.setObject(7, "");
                pst.setObject(8, leavecontact);
                pst.setObject(9, comments);
                pst.setObject(10, officer);
                pst.executeUpdate();

                String[] officerdetails = officer.toString().split("/");

                String staffID = officerdetails[1];
                //    addRights("1.Confirmation of Covering officer", staffID, conn);

                new SendSms(officerphone.toString(), "Hello,\n"+fullname + " has sent a request for  "+leavetype+"  Ref: " + refno + " starting "+appldate+"\n and you are required to cover their duties during the period they will be away", refno, conn);

                result = refno;

            } catch (SQLException ex) {
                result = "Transaction Failed\n" + ex.getMessage() + "\n" + appldate;
                ex.printStackTrace();
            }

        }

        out.write(result);

    }

    private void addRights(String right, String pf, Connection connectDB) {
        try {

            String sql = "INSERT INTO hr.approval_levels(\n"
                    + "            username, level)\n"
                    + "    VALUES (?, ?);";
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, pf);
            pst.setObject(2, right);

            pst.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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

    private static final String ALPHA_NUMERIC_STRING = "ABCDE0123456789";

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

}
