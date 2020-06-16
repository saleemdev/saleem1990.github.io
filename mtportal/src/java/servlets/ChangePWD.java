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
@WebServlet(name = "ChangePWD", urlPatterns = {"/ChangePWD"})
public class ChangePWD extends HttpServlet {

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
            out.println("<title>Servlet ChangePWD</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ChangePWD at " + request.getContextPath() + "</h1>");
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
    private String UserTel(String key, Connection conn) {
//        java.sql.Connection conn = this.connect("postgres", "sequence");
        String login = "";

        String sql = "";

        // sql = "select login_name from secure_password where  staffid = ?";
        sql = "select phone from secure_password where login_name = ?";

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

            //  conn.close();
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        response.setContentType("text/plain");
        Connection conn = new LogginController().connect("postgres", "sequence");
        String staffid = request.getParameter("staffid");
        String loginid = getLoginid(conn, staffid);
        PrintWriter out = response.getWriter();
        String pwd = randomAlphaNumeric(5);

        if (!loginid.equalsIgnoreCase("None")) {

            String phone = UserTel(loginid, conn);
            try {
                PreparedStatement pst = conn.prepareStatement("ALTER USER " + loginid + " WITH password '" + pwd + "' ");
                pst.executeUpdate();

                pst = conn.prepareStatement("UPDATE secure_password SET tmplogin=true WHERE staffid ='" + staffid + "'");
                pst.executeUpdate();
                String email = UserEmailByID(staffid);
                if (tellme(conn)) {
                    EmailClass em = new com.mtrh.mtportal.sys.EmailClass();
                    em.SendPlainEmailAlertsV2(email,  "Your temporary login password is " + pwd + "\nPlease change it after login","MTRH PORTAL password reset instructions", conn);
                } else {
                    new SendSms(phone, "Your temporary login password is " + pwd + "\nPlease change it after login", staffid, conn);
                }
                out.write("0");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            out.write("1");
        }
    }

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

        //request.getParameter("leaveyear");
        if (session != null) {
            String sessionid = session.getAttribute("sessionid").toString();
            String loginid = session.getAttribute("username").toString();
            //     String loginid = session.getAttribute("staffid").toString();
            String staffid = session.getAttribute("staffid").toString();

            String pwd = request.getParameter("pwd");

            System.err.println("Matching key: " + sessionid);

            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");

            try {
                PreparedStatement pst = conn.prepareStatement("ALTER USER " + loginid + " WITH password '" + pwd + "' ");
                System.err.println("ALTER USER " + loginid + " WITH password '" + pwd + "' ");
                pst.executeUpdate();
                pst = conn.prepareStatement("UPDATE secure_password SET tmplogin=false WHERE staffid='" + staffid + "' ");
                pst.executeUpdate();

                //  }
            } catch (SQLException ex) {
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

    private static final String ALPHA_NUMERIC_STRING = "0123456789";

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}
