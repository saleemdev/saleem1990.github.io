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
@WebServlet(name = "ApplicantCreds", urlPatterns = {"/ApplicantCreds"})
public class ApplicantCreds extends HttpServlet {

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
            out.println("<title>Servlet ApplicantCreds</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ApplicantCreds at " + request.getContextPath() + "</h1>");
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
        // processRequest(request, response);
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

            String idtype = request.getParameter("idtype");
            String idno = request.getParameter("idno");
            String fname = request.getParameter("fname");
            String lname = request.getParameter("lname");
            String email = request.getParameter("email");
            String pw = randomAlphaNumeric(8);

            String sql = "INSERT INTO vacancies.applicant_bio(\n"
                    + "            idtype, idno,  fname,  lname,  email, \n"
                    + "             dob, password)\n"
                    + "    VALUES (?, ?, ?, ?, ?, current_date, md5(?));";

            try {
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setObject(1, idtype);
                pst.setObject(2, idno);
                pst.setObject(3, fname);
                pst.setObject(4, lname);
                pst.setObject(5, email);
                pst.setObject(6, pw);

                pst.executeUpdate();

                result = "User account created successfully. We have sent your login credentials to your email at " + email;

                EmailFunctions em = new EmailFunctions();

                String content = "<p>Dear <strong>" + lname + "</strong>, MTRH appreciates your interest to grow your career with us.</p>"
                        + "<p>We invite you to follow the application process tips outlined <a href='#'>here</a> to analyze the best possibilities.</p><p><h4>Please find your login credentials below</h5></p><p>Login ID: "+idno+"</p><p>Password: "+pw+"</p> ";
                em.SendPlainEmailAlerts(email, content, idno, conn);
                
              //  em.SendMailUsingSMPT(email, content, idno, conn);
                //pst.setObject(1, idtype );
                // "idtype="+idtype+"&idno="+loginid+"&fname="+fnameid.toString().
                //toUpperCase()+"&lname="+lnameid.toString().toUpperCase()+"&email="+email.toString().toLowerCase()
            } catch (SQLException ex) {
                result = "Error: " + ex.getMessage();
                try {
                    conn.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(ApplicantCreds.class.getName()).log(Level.SEVERE, null, ex1);
                }
                ex.printStackTrace();

            }

        }

        out.write(result);

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
        processRequest(request, response);
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

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

}
