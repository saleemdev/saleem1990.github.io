/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.mtrh.fleet.dao.ConnectionProperties;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author owner
 */
@WebServlet(name = "PDFActions", urlPatterns = {"/PDFActions"})
public class PDFActions extends HttpServlet {

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
            out.println("<title>Servlet PDFActions</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PDFActions at " + request.getContextPath() + "</h1>");
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
        //processRequest(request, response);

        //response.setContentType("application/pdf");
        response.setContentType("application/pdf;charset=UTF-8");

        String pdftype = request.getParameter("pdfname");
        String refno = request.getParameter("refno");

        System.err.println(pdftype);

        switch (pdftype) {
            case "ALLVEHICLES":
                com.mtrh.fleet.reports.VehiclesPDF pdf = new com.mtrh.fleet.reports.VehiclesPDF();

                pdf.VehiclesPDF(ConnectionProperties.getConnect2DB());
                break;
            case "TICKET":
                com.mtrh.fleet.reports.RequestPDF pdf1 = new com.mtrh.fleet.reports.RequestPDF();

                pdf1.RequestPDF(ConnectionProperties.getConnect2DB(), refno, pdftype);
                break;

            case "REQUEST FOR TRANSPORT":
                LoginController ls = new LoginController();
                ConnectionProperties.setConnect2DB(ls.connect("postgres", "sequence"));

                com.mtrh.fleet.reports.RequestPDF pdf2 = new com.mtrh.fleet.reports.RequestPDF();

                pdf2.RequestPDF(ConnectionProperties.getConnect2DB(), refno, pdftype);
                break;

            case "REQUEST FOR MAINTENANCE":
                LoginController ls1 = new LoginController();
                ConnectionProperties.setConnect2DB(ls1.connect("postgres", "sequence"));

                com.mtrh.fleet.reports.RequestPDF pdf3 = new com.mtrh.fleet.reports.RequestPDF();

                pdf3.RequestPDF(ConnectionProperties.getConnect2DB(), refno, pdftype);
                break;

            case "ALLTICKETS":

                String date1 = request.getParameter("date1");
                String date2 = request.getParameter("date2");
                HttpSession session = request.getSession();
                java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");

                com.mtrh.fleet.reports.AllTicketsPDF allt = new com.mtrh.fleet.reports.AllTicketsPDF();
                allt.RequestPDF(conn, refno, date1, date2);

                break;
            case "ALLUSERS":
                session = request.getSession();
                conn = (java.sql.Connection) session.getAttribute("connection");

                com.mtrh.fleet.reports.UsersPDF allu = new com.mtrh.fleet.reports.UsersPDF();
                allu.RequestPDF(conn);
                break;

            case "ALLMTCE":
                session = request.getSession();
                conn = (java.sql.Connection) session.getAttribute("connection");

                
                String reqid = request.getParameter("reqid");

                com.mtrh.fleet.reports.AllMaintenanceTickets allm = new com.mtrh.fleet.reports.AllMaintenanceTickets();
                allm.RequestPDF(conn, refno,reqid);
                break;
        }

        String filepath = com.mtrh.fleet.reports.PDFPAth.getPath2pdf();

        File pdfFile = new File(filepath);

        response.addHeader("Content-Disposition", "inline; filename=" + filepath);
        ServletOutputStream out = response.getOutputStream();//This is optional

        System.err.println("At the servlet level This is the path: " + filepath);

        FileInputStream fileInputStream = new FileInputStream(pdfFile);
        OutputStream responseOutputStream = response.getOutputStream();
        int bytes;
        while ((bytes = fileInputStream.read()) != -1) {
            responseOutputStream.write(bytes);
        }

        // response.sendRedirect(filepath);
        //   response.getWriter().write(filepath);
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
