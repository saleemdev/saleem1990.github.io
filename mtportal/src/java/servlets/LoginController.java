/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.mtrh.mtportal.sys.EmailClass;
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
@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

    private Object dbServerIp;
    private Object dbPort;
    private Object activeDatabase;
    private String msg;

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
            out.println("<title>Servlet LoginController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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

            msg = sqlExec.getMessage().toString();
            System.err.println(System.getProperty("user.dir"));

            sqlExec.printStackTrace();
            //   this.setVisible(true);

        }
        return connection;
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
        //    processRequest(request, response);

        HttpSession session = request.getSession();
        //  String username = ConnectionProperties.getUserlogin();
        if (session != null) {
            System.err.println(session.getAttribute("username"));
            session.invalidate();
            session.setAttribute("connection", null);

            return;
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
    private String getAuthLogin(String logintype, String key) {
        java.sql.Connection conn = this.connect("postgres", "sequence");
        String login = "";

        String sql = "";
        if (logintype.equalsIgnoreCase("Staff ID")) {
            sql = "select login_name from secure_password where  staffid = ?";

        } else if (logintype.equalsIgnoreCase("Staff E-mail")) {
            sql = "select login_name from secure_password where email = ?";
        }

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

    private String UserTel(String key) {
        java.sql.Connection conn = this.connect("postgres", "sequence");
        String login = "";

        String sql = "";

        // sql = "select login_name from secure_password where  staffid = ?";
        sql = "select phone from secure_password where login_name = ?";

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
    
    private String getStaffNo(String key) {
        java.sql.Connection conn = this.connect("postgres", "sequence");
        String login = "";

        String sql = "";

        // sql = "select login_name from secure_password where  staffid = ?";
        sql = "select staffid from secure_password where login_name = ?";

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String un = request.getParameter("username");
        String pw = request.getParameter("password");
        String logintype = request.getParameter("logintype");

        String loginid = getAuthLogin(logintype, un);

        if (loginid.equalsIgnoreCase("None")) {
            msg = "There were no matching records to verify the details provided";
        } else {
            java.sql.Connection connectDB = connect(loginid, pw);
            response.setContentType("text/plain");

            if (connectDB != null) {
                String email = "", phone = "";
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(10 * 60);
                // session.setAttribute("sessionID", dbPort);
                // System.err.println("I am here I connected well");

                //Set the Global connection object
                if (logintype.equalsIgnoreCase("Staff E-mail")) {
                    email = un;
                } else {
                    email = UserEmailByID(un);
                }

                
                String staffid = getStaffNo(loginid);
                
                String key = randomAlphaNumeric(5);
                
                phone = UserTel(loginid);
                
                EmailClass em = new com.mtrh.mtportal.sys.EmailClass();
                em.SendPlainEmailAlerts(email, key, "SECURITY KEY["+key+"]", connectDB);
                
                new SendSms(phone, "Your OTP is: "+key);

                System.err.println(key);
                session.setAttribute("username", loginid);
                session.setAttribute("staffid", staffid);
                session.setAttribute("sessionid", key);
                session.setAttribute("connection", connectDB);
//            response.sendRedirect("dashboard.jsp");
                msg = "1";
            } else {
                // response.sendRedirect("404.html");
                // response.sendRedirect("index.jsp");
                msg = "Unable to Login :" + msg;

            }

        }

        System.err.println("My message    " + msg);
        response.getWriter().write(msg);
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

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

}
