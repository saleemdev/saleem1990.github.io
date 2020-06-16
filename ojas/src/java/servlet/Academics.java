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
@WebServlet(name = "Academics", urlPatterns = {"/Academics"})
public class Academics extends HttpServlet {

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
            out.println("<title>Servlet Academics</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Academics at " + request.getContextPath() + "</h1>");
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
        response.setContentType("text/plain");

        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String result = "";

        //request.getParameter("leaveyear");
        if (session != null) {
            String sessionid = session.getAttribute("sessionid").toString();
            //String staffid = session.getAttribute("staffid").toString();
            String applicantref = session.getAttribute("staffid").toString();

            Connection conn = (Connection) session.getAttribute("connection");

            String uniqueid = request.getParameter("uniqueid");

            try {
                String sql = "DELETE FROM vacancies.applicant_qualifications WHERE uniqueid=?";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setObject(1, uniqueid);

                pst.executeUpdate();
                result = "Data successfully deleted";
            } catch (SQLException ex) {
                result = "FAILED " + ex.getMessage();
                ex.printStackTrace();
            }
        }

        //}
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
        // processRequest(request, response);
        response.setContentType("text/plain");

        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String result = "";

        //request.getParameter("leaveyear");
        if (session != null) {
            String sessionid = session.getAttribute("sessionid").toString();
            //String staffid = session.getAttribute("staffid").toString();
            String applicantref = session.getAttribute("staffid").toString();

            Connection conn = (Connection) session.getAttribute("connection");

            String qualification = request.getParameter("qualification");

            String year = request.getParameter("year");

            String gradepos = request.getParameter("gradepos");

            String refno = request.getParameter("reference");

            String uniqueid = applicantref + "-" + randomAlphaNumeric(5);

            try {
                String sql = "INSERT INTO vacancies.applicant_qualifications(\n"
                        + "            applicantid, qualification, year_, gradepos, referenceid, uniqueid)\n"
                        + "    VALUES (?, ?, ?, ?, ?,?);";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setObject(1, applicantref);
                pst.setObject(2, qualification);
                pst.setObject(3, year);
                pst.setObject(4, gradepos);
                pst.setObject(5, refno);
                pst.setObject(6, uniqueid);
                pst.executeUpdate();
                result = "Data successfully posted";
            } catch (SQLException ex) {
                result = "FAILED " + ex.getMessage();
                ex.printStackTrace();
            }
        }

        //}
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
