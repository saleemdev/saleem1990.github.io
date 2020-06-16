/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fleet;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
@WebServlet(name = "CardTransactions", urlPatterns = {"/CardTransactions"})
public class CardTransactions extends HttpServlet {

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
            out.println("<title>Servlet CardTransactions</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CardTransactions at " + request.getContextPath() + "</h1>");
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
        //processRequest(request, response);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");

        //       PrintWriter out = response.getWriter();
        //    response.setContentType("text/plain");
        HttpSession session = request.getSession();

        String isExists = "0";
        String cardno = request.getParameter("cardno");

        if (session != null) {
            //java.sql.Connection conn = ConnectionProperties.getConnect2DB();
            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");
//            String cardno = request.getParameter("cardno");
//            String transtype = request.getParameter("transtype");

            // if (transtype.equalsIgnoreCase("CARD VEHICLES")) {
            String json = getVehiclesByCardNo(conn, cardno);

            out.write(json);
            //} 
        }
    }

    private String getVehiclesByCardNo(java.sql.Connection connectDB, String cardno) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();

        String[] columns = new String[]{"cardno", "debit", "credit", "narration", "enteredby", "enteredon"};

        String sql = "select cardno, debit, credit, narration,getstaffnamebylogin(authorizedby), datestamp::timestamp(0)  FROM fleet.cardtransactions\n"
                + "where cardno = ? order by datestamp desc";

        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, cardno);

            ResultSet rset = pst.executeQuery();

            int j = 0;
            while (rset.next()) {

                HashMap<String, String> child = new HashMap<String, String>();
                for (int i = 0; i < columns.length; i++) {
                    child.put(columns[i], rset.getObject(i + 1).toString());
                }
                parentList.add(child);
                j++;
            }
        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }

        String json = new Gson().toJson(parentList);//String JSON object

        System.err.println("I am here " + json);

        return json;

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
