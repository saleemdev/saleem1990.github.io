/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "AppraisalApproval", urlPatterns = {"/AppraisalApproval"})
public class AppraisalApproval extends HttpServlet {

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
            out.println("<title>Servlet AppraisalApproval</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AppraisalApproval at " + request.getContextPath() + "</h1>");
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
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String result = "";

        //request.getParameter("leaveyear");
        if (session != null) {
            String sessionid = session.getAttribute("sessionid").toString();
            String staffid = session.getAttribute("staffid").toString();

            //staffid
            //  String loginid = session.getAttribute("username").toString();
            System.err.println("Matching key: " + sessionid);

            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");

            // String leaveyr = getCurrentFY(conn);
            PreparedStatement pst = null;
            String sql = "";
            try {
                String level = request.getParameter("level");
                String refno = request.getParameter("refno");

                if (level.contains("SUPERVISOR")) {
                    sql = "update hr.staff_appraisal SET supervisor=getstaffnamebyid('" + staffid + "'), supervisorapproval=true, supervisortime=now() WHERE refno = '" + refno + "'";
                    pst = conn.prepareStatement(sql);
                    pst.executeUpdate();
                    
                    String questionid = request.getParameter("questionid");
                    String question = request.getParameter("question");
                    String answer = request.getParameter("answer");
                    int score = Integer.valueOf(request.getParameter("score"));

                    sql = "INSERT INTO hr.appraisal_scores(\n"
                            + "            refno, questionid, question, answer, score)\n"
                            + "    VALUES ('"+refno+"', '"+questionid+"', ?, ?, "+score+");";
                    pst = conn.prepareStatement(sql);
                    
                    pst.setObject(1, question);
                    pst.setObject(2, answer);
                    pst.executeUpdate();

                }
                if (level.contains("TEAM")) {

                }

                if (level.contains("HR")) {

                }
            } catch (Exception ex) {
                try {
                    conn.rollback();
                } catch (SQLException ex1) {
                    ex1.printStackTrace();
                }
                result = "FAIL"+ex.getMessage();
                ex.printStackTrace();
            }

            //  }
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

}
