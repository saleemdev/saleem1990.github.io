/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.mtrh.mtportal.sys.ConnectionProperties;
import com.mtrh.mtportal.sys.EmailClass;
import com.mtrh.mtportal.sys.SendSms;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
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
@WebServlet(name = "MessagingServlet", urlPatterns = {"/MessagingServlet"})
public class MessagingServlet extends HttpServlet {

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
            out.println("<title>Servlet MessagingServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MessagingServlet at " + request.getContextPath() + "</h1>");
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
    public Boolean isAGroupID(String str, Connection conn) {
        Boolean stat = false;
        String sql = "select case when upper('" + str + "') in (select distinct UPPER(groupname) from records.commsgroup) THEN true else false end";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);

            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                stat = rset.getBoolean(1);
            }

            //  conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return stat;
    }

    public Object[] getGroupMembers(String str, Connection conn) {
        Object[] stat = new String[]{};
        Vector v = new Vector();
        String sql = "select distinct memberpf from records.commsgroup where UPPER(groupname) =upper('" + str + "') order by 1";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);

            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                v.add(rset.getString(1));
            }
            stat = v.toArray();

            //  conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return stat;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // processRequest(request, response);
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String result = "";

        String pfno = request.getParameter("staffid");
        String message = request.getParameter("message");
        String refid = request.getParameter("refid");

        String msg = "";

        if (session != null) {
            String sessionid = session.getAttribute("sessionid").toString();
            String staffid = session.getAttribute("staffid").toString();

            System.err.println("Matching key: " + sessionid);
            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");

            String[] individualpfs = pfno.split(",");//COnvert the string into an array for looping
            for (int i = 0; i < individualpfs.length; i++) {
                String pf = individualpfs[i];

                if (isAGroupID(pf, conn)) {//If string is a group id, find the array of members, loop through group members sending messages

                    Object[] members = getGroupMembers(pf, conn);
                    for (int j = 0; j < members.length; j++) {

                        pfno = members[j].toString();
                        String email = UserEmailByID(pfno, conn);

                        String phone = UserPhone(pfno, conn);

                        String name = FullNameByID(staffid, conn);

                        String recipientName = FullNameByID(pfno, conn);

                        new SendSms(phone, "RefID " + refid + "\nRegistry Alert:\n" + message, refid, conn);
                    }

                } else {//We can be having JOHN/8976
                    //Start business
                    pfno = pf.split("/")[1];
                    String email = UserEmailByID(pfno, conn);

                    String phone = UserPhone(pfno, conn);

                    String name = FullNameByID(staffid, conn);

                    String recipientName = FullNameByID(pfno, conn);
                    
                    

                    new SendSms(phone, "RefID " + refid + "\nRegistry Alert:\n" + message, refid, conn);
                    //End business...

                }

            }
        }

        out.write(msg);
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
        // processRequest(request, response);
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String result = "";

        String pfno = request.getParameter("staffid");
        String message = request.getParameter("message");
        String refid = request.getParameter("refid");

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

            //  new EmailClass().SendPlainEmailAlerts(email, name+" says:\n"+message, refid, conn);
            new SendSms(phone, "RefID " + refid + "\n" + name + " says " + message, refid, conn);

            msg = "Message Sent Successfully to " + recipientName;
            //  }
        }

        out.write(msg);
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
