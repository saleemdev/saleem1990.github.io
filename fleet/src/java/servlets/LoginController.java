/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.mtrh.fleet.dao.DBConnectionDao;
import com.mtrh.fleet.dao.ConnectionProperties;
import com.mtrh.fleet.dao.DashboardMetricsDao;
import static com.mtrh.fleet.dao.DashboardMetricsDao.getAndAndPrepareActivitiesJSON;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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

    private static Object dbServerIp;
    private static Object dbPort;
    private static Object activeDatabase;
    private String msg = "";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet LoginController</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet LoginController at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
//    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
//    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
      //  String username = ConnectionProperties.getUserlogin();
        if (session != null ) {
            System.err.println(session.getAttribute("username"));
            session.invalidate();
           // Connection conn = ConnectionProperties.getConnect2DB();
//            try {
//                conn.close();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
           // ConnectionProperties.setConnect2DB(null);
            //session.removeAttribute("username");
            //session.removeAttribute("connection");
         
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
                dbServerIp = com.mtrh.fleet.sys.PropertiesFunctions.getpropValue("dbServerIpAdd").toString();
            }

            if (dbPort == null) {
                dbPort = com.mtrh.fleet.sys.PropertiesFunctions.getpropValue("dbPort").toString();
            }

            if (activeDatabase == null) {
                activeDatabase = com.mtrh.fleet.sys.PropertiesFunctions.getpropValue("activeDatabase").toString();
            }
            //  System.out.println("jdbc:postgresql://" + this.dbServerIp + ":" + dbPort + "/" + activeDatabase + " " + userName + " " + passWord);
            connection = java.sql.DriverManager.getConnection("jdbc:postgresql://" + this.dbServerIp + ":" + dbPort + "/" + activeDatabase, user, password);

            if (connection != null) {
                System.err.println("connected");
            }
        } catch (java.sql.SQLException sqlExec) {

            msg = sqlExec.getMessage().toString();
            System.err.println(System.getProperty("user.dir"));

            //     Accurate = false;
            //javax.swing.JOptionPane.showMessageDialog(this, "ERROR : Logon denied due to incorrect username & password,\n network disconnection or dataserver not running!\n\nERROR DETAILS : \n[" + sqlExec.getMessage() + "]");
            sqlExec.printStackTrace();
            //   this.setVisible(true);

        }
        return connection;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //  processRequest(request, response);

        String un = request.getParameter("username");
        String pw = request.getParameter("password");

       // java.sql.Connection connectDB = this.connect(un, pw);

        java.sql.Connection connectDB = new ConnectionProperties().connect(un, pw);
        response.setContentType("text/plain");

        if (connectDB != null) {
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(10 * 60);
            // session.setAttribute("sessionID", dbPort);
            // System.err.println("I am here I connected well");

            //Set the Global connection object
            ConnectionProperties.setConnect2DB(connectDB);

            ConnectionProperties.setUserlogin(un);
            //Get the Connection attributes using the Dao and set them in the Connection property bean

            ///There is another controller servlet that is responsible for getting the Dashboard and Login Parameters
            DBConnectionDao dao = new DBConnectionDao();
            dao.getConnectionAttributes(connectDB, un);
            //setdashboard metrics
            DashboardMetricsDao dao1 = new DashboardMetricsDao();

            dao1.getAndSetDashmetrics(connectDB);
            dao1.getAndAndPrepareActivitiesJSON(ConnectionProperties.getConnect2DB(), un);

            session.setAttribute("username", un);
            session.setAttribute("connection", connectDB);
//            response.sendRedirect("dashboard.jsp");
            msg = "1";
        } else {
            // response.sendRedirect("404.html");
            // response.sendRedirect("index.jsp");
            msg = "Unable to Login :" + msg;

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

}
