/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

//import com.mtrh.fleet.dao.ConnectionProperties;
import com.itextpdf.text.pdf.BadPdfFormatException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.lowagie.text.DocumentException;
import com.mtrh.mtportal.sys.PDFPAth;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import maintenance.eTicket;

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

        HttpSession session = request.getSession();
        java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");

        switch (pdftype) {
            case "LEAVE_APPLICATION":
                LeaveApplicationPDF allt = new LeaveApplicationPDF();
                allt.RequestPDF(conn, refno);
                break;
            case "ENTITLEMENTS":
                LeaveEntitlements len = new LeaveEntitlements();
                len.LeaveEntitlements(conn, refno);
                break;

            case "ENTITLEMENTS_DEPT":
                String dept = request.getParameter("dept");
                LeaveEntitlementsByDept len2 = new LeaveEntitlementsByDept();
                len2.LeaveEntitlementsByDept(conn, dept);
                break;
            case "STAFFONLEAVEREPORT":
                StaffOnLeave slv = new StaffOnLeave();
                slv.RequestPDF(conn);
                break;
            case "PENDINGLEAVES":
                ActiveApplications alv = new ActiveApplications();
                alv.RequestPDF(conn);
                break;
            //PRINTUPCOMING
            case "PRINTUPCOMING":
                String month = request.getParameter("refno");
                UpcomingLeaves upc = new UpcomingLeaves();
                upc.RequestPDF(conn, month);
                break;

            case "REJECTEDAPPLICATIONS":
                // String month = request.getParameter("refno");
                RejectedApplications rjc = new RejectedApplications();
                rjc.RequestPDF(conn);
                break;

            case "MYAPPROVALS":

                break;
            case "REQUEST FOR TRANSPORT":
                TRequestPDF pdf2 = new TRequestPDF();

                pdf2.TRequestPDF(conn, refno, pdftype);

                break;

            case "REQUEST FOR MAINTENANCE":
                RequestPDF pdf3 = new RequestPDF();

                pdf3.RequestPDF(conn, refno, pdftype);

                break;

            case "REQUEST FOR FUEL":
                FuelRqPDF pdf4 = new FuelRqPDF();

                pdf4.RequestPDF(conn, refno, pdftype);

                break;

            case "BLOBUPLOAD":
                String uniqueid = request.getParameter("refno");

                 {
                    try {
                        getStoredPDF(conn, uniqueid);
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (DocumentException ex) {
                        ex.printStackTrace();
                    }
                }

                break;
            case "APPRAISALPDF":
                AppraisalPDF alltPR = new AppraisalPDF();
                alltPR.RequestPDF(conn, refno);
                break;

            case "WORKORDERPDF":
                maintenance.WorkOrderSlip alltPR2 = new maintenance.WorkOrderSlip();
                alltPR2.RequestPDF(conn, refno);
                break;

            case "ETICKET":
                eTicket pdf = new eTicket();
                pdf.RequestPDF(conn, refno, null, false);

                break;
        }
        String filepath = com.mtrh.mtportal.sys.PDFPAth.getPath2pdf();

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
    public void getStoredPDF(Connection connDB, String documentRefNumber)
            throws FileNotFoundException, com.lowagie.text.DocumentException {
        File tempFile = null;
        File tempFile2 = null;
        String filename2b = documentRefNumber + "" + randomAlphaNumeric(5);
        try {
            tempFile2 = File.createTempFile("REP" + filename2b + "_", ".pdf");

            com.itextpdf.text.Document document = new com.itextpdf.text.Document();

            PdfCopy copy = null;
            try {
                copy = new PdfCopy(document, new FileOutputStream(tempFile2));
            } catch (com.itextpdf.text.DocumentException ex) {
                ex.printStackTrace();
            }
            document.open();

            PdfReader reader = null;
            try {
                int bytePos = 0;
                PreparedStatement pstmtR = connDB.prepareStatement("select uploaded_file from docs where pkref='" + documentRefNumber + "'");
                //  pstmtR.setString(1, documentRefNumber);
                ResultSet rs = pstmtR.executeQuery();
                while (rs.next()) {
                    tempFile = File.createTempFile("REP" + filename2b + "_", ".pdf");
                    OutputStream fileIS = null;
                    tempFile.deleteOnExit();

                    fileIS = new FileOutputStream(tempFile);
                    ByteArrayOutputStream byteaStream = new ByteArrayOutputStream();
                    byte[] imgBytes = rs.getBytes(1);

                    byteaStream.write(imgBytes);

                    fileIS.write(imgBytes);

                    fileIS.close();
                    System.out.println(tempFile.getPath());

                    reader = new PdfReader(tempFile.getPath());

                    int n = reader.getNumberOfPages();
                    for (int page = 0; page < n;) {
                        try {
                            copy.addPage(copy.getImportedPage(reader, ++page));
                        } catch (BadPdfFormatException ex) {
                            ex.printStackTrace();
                        }
                    }
                    copy.freeReader(reader);
                    reader.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            document.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        PDFPAth.setPath2pdf(tempFile2.getAbsolutePath());
        //  return tempFile2;
    }

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
