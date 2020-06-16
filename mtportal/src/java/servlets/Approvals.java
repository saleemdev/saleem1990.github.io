/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.mtrh.mtportal.sys.EmailClass;
import com.mtrh.mtportal.sys.LeaveFactory;
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
@WebServlet(name = "Approvals", urlPatterns = {"/Approvals"})
public class Approvals extends HttpServlet {

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
            out.println("<title>Servlet Approvals</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Approvals at " + request.getContextPath() + "</h1>");
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
        //   processRequest(request, response);
        response.setContentType("text/plain");
        String transtype = request.getParameter("transtype");//e.g 1.Confirmation for Covering officer
        String refid = request.getParameter("refid");//eg MTRH/L/..
        String action = request.getParameter("action");//e.g approve

        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String result = "";

        String stage = request.getParameter("stage");
        if (session != null) {
            String sessionid = session.getAttribute("sessionid").toString();
            String staffid = session.getAttribute("staffid").toString();

            System.err.println("Matching key: " + sessionid);

            //  if (key.equalsIgnoreCase(sessionid)) {
            // msg="1";
            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");

            String data = servlets.PropertiesClass.getpropValue("lckey").toString();
            
            String leaveyr = LeaveFactory.getCurrentFYFromLeaveref(conn, refid);

            actOnData(transtype, refid, action, staffid, conn);

            result = "1";

            //  }
        }

        out.write(result);

    }

    private String getFullnameByStaffID(String key, Connection conn) {

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

            //   conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return login;
    }

    private String UserTel(String key, Connection conn) {
        // java.sql.Connection conn = this.connect("postgres", "sequence");
        String login = "";

        String sql = "";

        // sql = "select login_name from secure_password where  staffid = ?";
        sql = "select phone from secure_password where staffid = ? limit 1";

        //  System.err.println(sql+"/"+key);
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setObject(1, key);
            ResultSet rset = pst.executeQuery();
            if (rset.next()) {
                login = rset.getString(1);
            } else {
                login = "None";
            }

            //   conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return login;
    }

    private void actOnData(String trans, String refid, String action, String staffid, Connection conn) {

        String fullname = getFullnameByStaffID(staffid, conn);
        String entry = fullname + "/" + staffid;
        String sql = "";
        String email = recepientStaffIDByLeaveID(refid, conn);
        System.err.println(trans + " " + action);
        String applicantPF = recepientStaffIDByLeaveID2(refid, conn);
        String phone = UserTel(applicantPF, conn);

        String leaveinquestion = applicationtypeByLeaveID(refid, conn);

        String leavestart =  applicationStart(refid, conn);

        switch (trans) {
            case "1.Confirmation of Covering officer":
                if (action.equalsIgnoreCase("approve")) {
                    sql = "UPDATE hr.leave_application SET covering = true, coveringofficer ='" + entry + "' WHERE refno ='" + refid + "' ";
                } else {
                    sql = "UPDATE hr.leave_application SET cancelled=true, covering = false, coveringofficer ='" + entry + "' WHERE refno ='" + refid + "' ";

                }
                break;
            case "2.HOD/Immediate Supervisor":
                if (action.equalsIgnoreCase("approve")) {
                    sql = "UPDATE hr.leave_application SET hodsupervisor =true, supervisor ='" + entry + "' WHERE refno ='" + refid + "' ";
                } else {
                    sql = "UPDATE hr.leave_application SET cancelled=true, hodsupervisor =false, supervisor ='" + entry + "' WHERE refno ='" + refid + "' ";

//                    new SendSms(phone, "Leave ID " + refid + " has been rejected at \nPlease login to the MTP to print the same", refid, conn);
                }
                break;
            case "3.Resourcing Approval":
                if (action.equalsIgnoreCase("approve")) {
                    sql = "UPDATE hr.leave_application SET hresourcing = true, resourcing ='" + entry + "' WHERE refno ='" + refid + "' ";

                } else {
                    sql = "UPDATE hr.leave_application SET cancelled=true, hresourcing = false, resourcing ='" + entry + "' WHERE refno ='" + refid + "' ";
                }
                break;
            case "4.Senior Management Approval":

                if (action.equalsIgnoreCase("approve")) {
                    sql = "UPDATE hr.leave_application SET snrmgr = true, seniormanager ='" + entry + "', daysapproved=daysrequested, approved = true WHERE refno ='" + refid + "' ";

                    int daysapproved = applicationDays(refid, conn);

                    String leavend =  LeaveFactory.getLeaveEnd(conn, leavestart, daysapproved, leaveinquestion.toUpperCase());

                    String resume = LeaveFactory.getFormattedDate(conn, LeaveFactory.getResumptionDate(conn, leavend));

                    new SendSms(phone, "Your " + leaveinquestion + " leave application, ID: " + refid + " has been approved to start on " + LeaveFactory.getFormattedDate(conn,leavestart) + " to " + LeaveFactory.getFormattedDate(conn,leavend) + "(" + daysapproved + " days).\nYou're required to resume on " + resume, refid, conn);

                    if (tellme(conn)) {

                        new SendSms("0722810063",  leaveinquestion + " leave application, ID: " + refid + " was approved to start on " + LeaveFactory.getFormattedDate(conn,leavestart) + " to " + LeaveFactory.getFormattedDate(conn,leavend) + "(" + daysapproved + " days).\nResumption " + resume, refid, conn);

                    }

                } else {
                    sql = "UPDATE hr.leave_application SET cancelled=true, snrmgr = false, seniormanager ='" + entry + "' WHERE refno ='" + refid + "' ";

                    new SendSms(phone, "Your " + leaveinquestion + " leave application ID: " + refid + " has been declined\nPlease login to your MTP dashboard for more details", refid, conn);

                }
                break;

        }
        System.err.println(sql);
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.executeUpdate();

            //I wrote this code thinking of Sarah and the Pug. She is a really nice friend and really cool
            //And really the smartest person I've met so far
            PreparedStatement pst1 = conn.prepareCall("INSERT INTO leavelogs(refid, transtype, action) SELECT '" + refid + "', '" + trans + "','" + action + "' ");
            pst1.executeUpdate();

            //Update Dependent Leave By Leave Type Upon Final Approval
            if (trans.equalsIgnoreCase("4.Senior Management Approval") && action.equalsIgnoreCase("approve")) {
                String leavetype = applicationtypeByLeaveID(refid, conn);

                String depends_on = LeaveFactory.getDependsOn(leavetype, conn);

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private Boolean tellme(Connection conn) {

        String sql = "";
        Boolean stat = Boolean.FALSE;
        // sql = "select login_name from secure_password where  staffid = ?";
        sql = "select status from alerts_setting where alert_type = 'leaveapproval' and status is true";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);

            ResultSet rset = pst.executeQuery();
            if (rset.next()) {
                stat = Boolean.TRUE;
            } else {
                stat = Boolean.FALSE;
            }

            //  conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return stat;
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

    private String recepientStaffIDByLeaveID(String refid, Connection connectDB) {
        String email = "";

        String sql = "select pfno from hr.leave_application where refno=?";
        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, refid);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                email = UserEmailByID(rset.getObject(1).toString(), connectDB);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return email;
    }

    private String recepientStaffIDByLeaveID2(String refid, Connection connectDB) {
        String staffid = "";

        String sql = "select pfno from hr.leave_application where refno=?";
        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, refid);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                staffid = rset.getObject(1).toString();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return staffid;
    }

    private String leavetypeByID(String refid, Connection connectDB) {
        String leavetype = "";

        String sql = "select leavetype from hr.leave_application where refno=?";
        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, refid);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                leavetype = rset.getObject(1).toString();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return leavetype;
    }

    private String applicationtypeByLeaveID(String refid, Connection connectDB) {
        String leavetype = "";

        String sql = "select leavetype from hr.leave_application where refno=?";
        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, refid);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                leavetype = rset.getObject(1).toString();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return leavetype;
    }

    private String applicationStart(String refid, Connection connectDB) {
        String leavetype = "";

        String sql = "select leavestart from hr.leave_application where refno=?";
        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, refid);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                leavetype = rset.getObject(1).toString();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return leavetype;
    }

    private int applicationDays(String refid, Connection connectDB) {
        int leavetype = 0;

        String sql = "select daysrequested from hr.leave_application where refno=?";
        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, refid);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                leavetype = rset.getInt(1);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return leavetype;
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
