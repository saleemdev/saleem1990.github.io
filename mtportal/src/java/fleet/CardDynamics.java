/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fleet;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
@WebServlet(name = "CardDynamics", urlPatterns = {"/CardDynamics"})
public class CardDynamics extends HttpServlet {

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
            out.println("<title>Servlet CardDynamics</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CardDynamics at " + request.getContextPath() + "</h1>");
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
        PrintWriter out = response.getWriter();
        response.setContentType("text/plain");

        //       PrintWriter out = response.getWriter();
        //    response.setContentType("text/plain");
        HttpSession session = request.getSession();

        String transtype = request.getParameter("transtype");
        String cardno = request.getParameter("cardno");
        String regno = request.getParameter("regno");

        //   String isExists = "0";
        String result = "";
        String logtrans = "";

        if (session != null) {

            String sql = "";
            if (transtype.contains("allocate")) {
                sql = "INSERT INTO fleet.vehicle_card_allocation(\n"
                        + "            regno, cardno)\n"
                        + "    VALUES (?, ?);";
                logtrans = "ALLOCATE CARD " + cardno + " to VEHICLE " + regno;

            } else {
                sql = "DELETE FROM fleet.vehicle_card_allocation\n"
                        + " WHERE regno =?  AND cardno=?;";
                logtrans = "DELETED ALLOCATION FOR CARD " + cardno + " to VEHICLE " + regno;
            }

            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");
            try {
                PreparedStatement pst = conn.prepareStatement(sql); //}

                pst.setObject(1, regno);
                pst.setObject(2, cardno);
                pst.executeUpdate();

                com.mtrh.mtportal.sys.LeaveFactory.updateLogs(conn, cardno + "/" + regno, transtype, logtrans);

                result = "TRANSACTION COMPLETED SUCCESSFULLY";
            } catch (SQLException ex) {
                result = ex.getMessage();
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
        //processRequest(request, response);
        //CardDynamics
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");

        //       PrintWriter out = response.getWriter();
        //    response.setContentType("text/plain");
        HttpSession session = request.getSession();

        String isExists = "0";

        if (session != null) {
            //java.sql.Connection conn = ConnectionProperties.getConnect2DB();
            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");
//            String cardno = request.getParameter("cardno");
//            String transtype = request.getParameter("transtype");

            // if (transtype.equalsIgnoreCase("CARD VEHICLES")) {
            String json = getVehiclesByCardNo(conn);

            out.write(json);
            //} 
        }
    }

    private String getCardamt(String cardno, Connection connectDB) {

        com.mtrh.mtportal.sys.CardTransactions card = new com.mtrh.mtportal.sys.CardTransactions();

        return card.getCardAmount(cardno, connectDB);
    }

    private String getVehiclesByCardNo(java.sql.Connection connectDB) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();

        String[] columns = new String[]{"cardno", "amount"};

        String sql = "SELECT DISTINCT upper(cardno) FROM fleet.vehicle_card_allocation";

        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);

            ResultSet rset = pst.executeQuery();

            int j = 0;
            while (rset.next()) {
                String cardno = rset.getObject(1).toString();

                HashMap<String, String> child = new HashMap<String, String>();
                //  for (int i = 0; i < columns.length; i++) {
                child.put(columns[0], cardno);

                child.put(columns[1], getCardamt(cardno, connectDB));
                //}
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
