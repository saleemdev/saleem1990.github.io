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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static servlets.LeaveApplication.randomAlphaNumeric;

/**
 *
 * @author owner
 */
@WebServlet(name = "FirstTimeLogin", urlPatterns = {"/FirstTimeLogin"})
public class FirstTimeLogin extends HttpServlet {

    private String result;
    private Object dbServerIp;
    private Object dbPort;
    private Object activeDatabase;

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
            out.println("<title>Servlet FirstTimeLogin</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FirstTimeLogin at " + request.getContextPath() + "</h1>");
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
        //  processRequest(request, response);
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

            String sql = "UPDATE secure_password SET phone =? , email = ?, first_time_login = true WHERE staffid =?;";
            String refno = "MTRH/HR/C/" + randomAlphaNumeric(5);

            String email = request.getParameter("email");
            String phone = request.getParameter("phone");

            //String coveringOfficerPF = getPFByName
            try {
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setObject(1, phone);
                pst.setObject(2, email);
                pst.setObject(3, staffid);
                pst.executeUpdate();

                new ConnectionProperties().setMetrics(staffid, conn);
                String fullname = ConnectionProperties.getUsername();
                String emailid = UserEmailByID(staffid);
                if (tellme(conn)) {
                    EmailClass em = new com.mtrh.mtportal.sys.EmailClass();
                    //em.SendPlainEmailAlertsV2(email, "MTRH PORTAL password reset instructions", "Your temporary login password is " + pwd + "\nPlease change it after login", conn);
                    em.SendPlainEmailAlertsV2(emailid, "Thankyou, " + fullname + " for reading and accepting our Commmunications policy\nYour One Time Pin(OTP) is " + sessionid,"Communication Policy Message",  conn);
                } else {
                    new SendSms(phone, "Thankyou, " + fullname + " for reading and accepting our Commmunications policy\nYour One Time Pin(OTP) is " + sessionid, sessionid, conn);
                    //    new EmailClass().SendPlainEmailAlerts(email, "Thankyou, "+fullname+" for reading and accepting our Commmunications policy\nYour OTP is "+sessionid, "", conn);
                }
                result = refno;

            } catch (SQLException ex) {
                result = "Transaction Failed";
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
    
    private Boolean tellme(Connection conn) {

        Boolean login = Boolean.FALSE;

        String sql = "";

        // sql = "select login_name from secure_password where  staffid = ?";
        sql = "select status from alerts_setting where alert_type = 'sendmailotp'";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);

            ResultSet rset = pst.executeQuery();
            if (rset.next()) {
                login = rset.getBoolean(1);
            } else {
                login = Boolean.FALSE;

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return login;
    }

    public String getLoginid(Connection conn, String staffid) {
        String sql = "select login_name from secure_password where staffid = ?";
        String login = "";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setObject(1, staffid);
            ResultSet rset = pst.executeQuery();
            if (rset.next()) {
                login = rset.getString(1);
            } else {
                login = "None";
            }

            //conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return login;
    }
    public java.sql.Connection connect(String user, String password) {
        Connection connection = null;

        try {

            java.lang.Class.forName("org.postgresql.Driver");

        } catch (java.lang.ClassNotFoundException cnf) {

            cnf.printStackTrace();

        }
        try {

            if (dbServerIp == null) {
                // dbServerIp = "localhost"jj;
                dbServerIp = servlets.PropertiesClass.getpropValue("dbServerIpAdd").toString();
            }

            if (dbPort == null) {
                dbPort = servlets.PropertiesClass.getpropValue("dbPort").toString();
            }

            if (activeDatabase == null) {
                activeDatabase = servlets.PropertiesClass.getpropValue("activeDatabase").toString();
            }
            //  System.out.println("jdbc:postgresql://" + this.dbServerIp + ":" + dbPort + "/" + activeDatabase + " " + userName + " " + passWord);
            connection = java.sql.DriverManager.getConnection("jdbc:postgresql://" + this.dbServerIp + ":" + dbPort + "/" + activeDatabase, user, password);

            if (connection != null) {
                System.err.println("connected");
            }
        } catch (java.sql.SQLException sqlExec) {

            //      msg = sqlExec.getMessage().toString();
            System.err.println(System.getProperty("user.dir"));

            sqlExec.printStackTrace();
            //   this.setVisible(true);

        }
        return connection;
    }
    private String UserEmailByID(String key) {
        java.sql.Connection conn = this.connect("postgres", "sequence");
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

            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return login;
    }

}
