/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maintenance;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
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
@WebServlet(name = "ManageActivities", urlPatterns = {"/ManageActivities"})
public class ManageActivities extends HttpServlet {

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
            out.println("<title>Servlet ManageActivities</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManageActivities at " + request.getContextPath() + "</h1>");
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
            String staffid = session.getAttribute("staffid").toString();

            //staffid
            //  String loginid = session.getAttribute("username").toString();
            System.err.println("Matching key: " + sessionid);

            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");

            Object activity = request.getAttribute("activity");
            Object refid = request.getAttribute("refid");
            Object issue = request.getAttribute("issue");

            String sql = "INSERT INTO public.workorder(\n"
                    + "            refid, serviceitem, action_taken, status)\n"
                    + "    VALUES (?, ?, ?, ?);";

            try {
                if(issue.toString().equalsIgnoreCase("-")){
                issue =getissuebyid(refid, conn);
                }
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setObject(1, refid);
                pst.setObject(2, issue);
                pst.setObject(3, activity);
                pst.setObject(4, true);
                pst.executeUpdate();
                result ="Successfuy posted";

            } catch (SQLException ex) {
                result = "Error\n"+ex.getCause();
                ex.printStackTrace();
            }

        }

        out.write(result);

    }
    
    private String getissuebyid(Object id, Connection connectDB){
        
        String issue ="";
        String sql ="select requesttype from servicerequest where requestid=?";
          try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, id);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
              issue =  rset.getObject(1).toString();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
          
          return issue;
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

}
