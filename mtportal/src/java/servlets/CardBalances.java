/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.mtrh.mtportal.sys.CardTransactions;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "CardBalances", urlPatterns = {"/CardBalances"})
public class CardBalances extends HttpServlet {

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
            out.println("<title>Servlet CardBalances</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CardBalances at " + request.getContextPath() + "</h1>");
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
     //   processRequest(request, response);
     
     response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

       
        String result = "";
        if (session != null) {

            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");
            
            String staffid = session.getAttribute("staffid").toString();
            String cardno = request.getParameter("cardno");
            Double oldamt = Double.valueOf(request.getParameter("oldamt"));
            Double newamt = Double.valueOf(request.getParameter("newamt"));

            // public void setAmount(String cardNo, String narration, String transtype, String user, Double amount, java.sql.Connection connectionDB) {
            new CardTransactions().setAmount(cardno, "Card Difference", "credit", staffid, oldamt, conn);
            
            new CardTransactions().setAmount(cardno, "Card Reconciliation", "debit", staffid, newamt, conn);
            
            

        }
        
        out.write("TRANSACTION COMPLETED SUCCESSFULLY");
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
