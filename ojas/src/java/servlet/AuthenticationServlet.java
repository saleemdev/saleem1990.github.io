/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

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
@WebServlet(name = "AuthenticationServlet", urlPatterns = {"/AuthenticationServlet"})
public class AuthenticationServlet extends HttpServlet {

    /**
     * @return the idtype
     */
    public static Object getIdtype() {
        return idtype;
    }

    /**
     * @param aIdtype the idtype to set
     */
    public static void setIdtype(Object aIdtype) {
        idtype = aIdtype;
    }

    /**
     * @return the idno
     */
    public static Object getIdno() {
        return idno;
    }

    /**
     * @param aIdno the idno to set
     */
    public static void setIdno(Object aIdno) {
        idno = aIdno;
    }

    /**
     * @return the initials
     */
    public static Object getInitials() {
        return initials;
    }

    /**
     * @param aInitials the initials to set
     */
    public static void setInitials(Object aInitials) {
        initials = aInitials;
    }

    /**
     * @return the fname
     */
    public static Object getFname() {
        return fname;
    }

    /**
     * @param aFname the fname to set
     */
    public static void setFname(Object aFname) {
        fname = aFname;
    }

    /**
     * @return the mname
     */
    public static Object getMname() {
        return mname;
    }

    /**
     * @param aMname the mname to set
     */
    public static void setMname(Object aMname) {
        mname = aMname;
    }

    /**
     * @return the lname
     */
    public static Object getLname() {
        return lname;
    }

    /**
     * @param aLname the lname to set
     */
    public static void setLname(Object aLname) {
        lname = aLname;
    }

    /**
     * @return the maintel
     */
    public static Object getMaintel() {
        return maintel;
    }

    /**
     * @param aMaintel the maintel to set
     */
    public static void setMaintel(Object aMaintel) {
        maintel = aMaintel;
    }

    /**
     * @return the email
     */
    public static Object getEmail() {
        return email;
    }

    /**
     * @param aEmail the email to set
     */
    public static void setEmail(Object aEmail) {
        email = aEmail;
    }

    /**
     * @return the postaladdr
     */
    public static Object getPostaladdr() {
        return postaladdr;
    }

    /**
     * @param aPostaladdr the postaladdr to set
     */
    public static void setPostaladdr(Object aPostaladdr) {
        postaladdr = aPostaladdr;
    }

    /**
     * @return the dob
     */
    public static Object getDob() {
        return dob;
    }

    /**
     * @param aDob the dob to set
     */
    public static void setDob(Object aDob) {
        dob = aDob;
    }

    /**
     * @return the gender
     */
    public static Object getGender() {
        return gender;
    }

    /**
     * @param aGender the gender to set
     */
    public static void setGender(Object aGender) {
        gender = aGender;
    }

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
            out.println("<title>Servlet AuthenticationServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AuthenticationServlet at " + request.getContextPath() + "</h1>");
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
        //  processRequest(request, response);

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String result = "";

        //request.getParameter("leaveyear");
        if (session != null) {
            String sessionid = session.getAttribute("sessionid").toString();
            //String staffid = session.getAttribute("staffid").toString();
            String sessionstatus = session.getAttribute("validated").toString();

            Connection conn = (Connection) session.getAttribute("connection");

            String uid = request.getParameter("uid");
 
            String pwd = request.getParameter("pwd"); 

            if (registrationMatchesRecords(conn, uid, pwd).equalsIgnoreCase("yes")) {
                //Reset Session Attributes;
                result = "matches";

                prepareData(conn, uid);

                String ssessionid =randomAlphaNumeric(20);
                session.setAttribute("sessionid", sessionid); 
                session.setAttribute("validated", "yes");
                session.setMaxInactiveInterval(10 * 60);
                session.setAttribute("username", getLname() + ", " + getFname());
                session.setAttribute("staffid", uid);

            } else {
                result = "noluck";
            }

        }

        out.write(result);
    }

    private static void prepareData(Connection conn, String uid) {

        try {
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM vacancies.applicant_bio WHERE idno='" + uid + "'");
            ResultSet rset = pst.executeQuery();

            while (rset.next()) {
                setIdno(uid);
                setFname(rset.getObject("fname"));
                setMname(rset.getObject("mname"));
                setLname(rset.getObject("lname"));
                setMaintel(rset.getObject("maintel"));

                setEmail(rset.getObject("email"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AuthenticationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static String registrationMatchesRecords(Connection conn, String uid, String pwd) {

        String stat = "no";
        String sql = "SELECT CASE WHEN md5('" + pwd + "') IN  ( select password from vacancies.applicant_bio   where idno='" + uid + "') then 'yes' else 'no' end";
        System.err.println(sql);
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();

            while (rset.next()) {
                stat = rset.getString(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return stat;

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
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String result = "";

        //request.getParameter("leaveyear");
        if (session != null) {
            String sessionid = session.getAttribute("sessionid").toString();
            //String staffid = session.getAttribute("staffid").toString();
            String sessionstatus = session.getAttribute("validated").toString();

            Connection conn = (Connection) session.getAttribute("connection");

            result = session.getAttribute("username").toString();

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

    private static Object idtype;

    private static Object idno;

    private static Object initials;

    private static Object fname;

    private static Object mname;

    private static Object lname;

    private static Object maintel;

    private static Object email;

    private static Object postaladdr;

    private static Object dob;

    private static Object gender;

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
