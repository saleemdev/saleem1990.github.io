/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.mtrh.fleet.dao.VehicleDao;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author owner
 */
@WebServlet(name = "VehicleMetaData", urlPatterns = {"/VehicleMetaData"})
public class VehicleMetaData extends HttpServlet {

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
            out.println("<title>Servlet VehicleMetaData</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VehicleMetaData at " + request.getContextPath() + "</h1>");
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
        String searchkey = request.getParameter("searchkey");
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        java.sql.Connection conn = new LoginController().connect("postgres", "sequence");

        String val = "0.0";

        if (searchkey.equalsIgnoreCase("mileage")) {
            val = getMileage(searchkey, conn);
        } else {
            val = getLastMaint(searchkey, conn);
        }

        out.print(val);

    }

    private String getMileage(String regno, java.sql.Connection connectDB) {
        String fleetno = new VehicleDao().getFleetIDByRegNo(regno, connectDB);

        return String.valueOf(new VehicleDao().getVehicleMileage(fleetno, connectDB));

    }

    private String getLastMaint(String regno, java.sql.Connection connectDB) {
        return new VehicleDao().DateOfLastMaint(regno, connectDB);
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
