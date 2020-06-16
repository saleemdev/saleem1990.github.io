/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.mtrh.mtportal.sys.ConnectionProperties;
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
@WebServlet(name = "Appraisal", urlPatterns = {"/Appraisal"})
public class Appraisal extends HttpServlet {

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
            out.println("<title>Servlet Appraisal</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Appraisal at " + request.getContextPath() + "</h1>");
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
    private String FullNameByID(String key, Connection conn) {

        String login = "";

        String sql = "";

        // sql = "select login_name from secure_password where  staffid = ?";
        sql = "select fullname from secure_password where staffid = ?";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setObject(1, key);
            ResultSet rset = pst.executeQuery();
            if (rset.next()) {
                login = rset.getString(1);
            } else {
                login = "None";
            }

            //  conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return login;
    }

    private String UserEmailByID(String key, Connection conn) {

        String login = "";

        String sql = "";

        // sql = "select login_name from secure_password where  staffid = ?";
        sql = "select email from secure_password where staffid = ?";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setObject(1, key);
            ResultSet rset = pst.executeQuery();
            if (rset.next()) {
                login = rset.getString(1);
            } else {
                login = "None";
            }

            //  conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return login;
    }

    private String UserPhone(String key, Connection conn) {

        String login = "";

        String sql = "";

        // sql = "select login_name from secure_password where  staffid = ?";
        sql = "select phone from secure_password where staffid = ?";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setObject(1, key);
            ResultSet rset = pst.executeQuery();
            if (rset.next()) {
                login = rset.getString(1);
            } else {
                login = "None";
            }

            //  conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return login;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //     processRequest(request, response);
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String result = "";

        String pfno = request.getParameter("staffid");
        String date1 = request.getParameter("date1");
        String date2 = request.getParameter("date2");
        String message = request.getParameter("message");

        String appraisaltype = request.getParameter("appraisaltype");
        String refid = pfno + "" + randomAlphaNumeric(3);

        String msg = "";

        if (session != null) {
            String sessionid = session.getAttribute("sessionid").toString();
            String staffid = session.getAttribute("staffid").toString();

            //staffid
            //  String loginid = session.getAttribute("username").toString();
            System.err.println("Matching key: " + sessionid);

            //  if (key.equalsIgnoreCase(sessionid)) {
            // msg="1";
            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");

            new ConnectionProperties().setMetrics(pfno, conn);

            String email = UserEmailByID(pfno, conn);

            String phone = UserPhone(pfno, conn);

            String name = FullNameByID(staffid, conn);

            String recipientName = FullNameByID(pfno, conn);

            if (!appraisalInProgress(conn, appraisaltype, pfno)) {
                try {
                    PreparedStatement pst = conn.prepareStatement("INSERT INTO hr.staff_appraisal(refno, staffid, periodstart, periodend, type_)"
                            + " values(?,?,?::date,?::date,?)");
                    pst.setObject(1, refid);
                    pst.setObject(2, pfno);
                    pst.setObject(3, date1);
                    pst.setObject(4, date2);
                    pst.setObject(5, appraisaltype);
                    pst.executeUpdate();
                    msg = recipientName;
                    
                    sendMsg(conn, refid, message, name);
                    //  new EmailClass().SendPlainEmailAlerts(email, name+" says:\n"+message, refid, conn);
                    // new SendSms(phone, "RefID " + refid + "\n" + name + " says " + message, refid, conn);
                    //msg = "Message Sent Successfully to " + recipientName;
                    //  }
                } catch (SQLException ex) {
                    msg = "Failed\n " + ex.getCause();
                    ex.printStackTrace();
                    out.write("Failed\n " + ex.getCause());

                }
            } else {
                msg = "FAILED : An existing " + appraisaltype + " for selected staff is in progress";
             //   out.write(msg);
            }
        }

        out.write(msg);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    private static Boolean appraisalInProgress(Connection conn, String type, String pfno) {
        Boolean stat = false;

        try {
            // String leaveyr = getCurrentFY(conn);
            PreparedStatement pst = conn.prepareStatement("select case when upper('" + type + "') IN (select upper(type_) from hr.staff_appraisal where staffid ='" + pfno + "' and hrapproval is false) then true else false end");
            System.err.println("select case when upper('" + type + "') IN (select upper(type_) from hr.staff_appraisal where staffid ='" + pfno + "' and hrapproval is false) then true else false end");
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                stat = rset.getBoolean(1);
            }
            //pst.executeUpdate();

            //  }
        } catch (SQLException ex) {
            ex.printStackTrace();
//                result = "FAIL" + ex.getMessage();
        }
        return stat;
    }

    private static void sendMsg(Connection conn, String refno, String message, String name) {
        try {
            // String leaveyr = getCurrentFY(conn);
            PreparedStatement pst = conn.prepareStatement("INSERT into hr.notifications(refno,message,username) values('" + refno + "',?, ?||'@'||now()::timestamp(0))");
            pst.setObject(1, message);
            pst.setObject(2, name);
            pst.executeUpdate();

            //  }
        } catch (SQLException ex) {
            ex.printStackTrace();
//                result = "FAIL" + ex.getMessage();
        }

    }
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
