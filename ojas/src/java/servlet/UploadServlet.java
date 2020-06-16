/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author owner
 */
@WebServlet(name = "UploadServlet", urlPatterns = {"/UploadServlet"})
public class UploadServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private boolean isMultipart;
    private String filePath;
    private int maxFileSize = 50 * 1024;
    private int maxMemSize = 4 * 1024;
    private File file;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UploadServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UploadServlet at " + request.getContextPath() + "</h1>");
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
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String result = "";

        //request.getParameter("leaveyear");
        if (session != null) {
            String sessionid = session.getAttribute("sessionid").toString();
            //String staffid = session.getAttribute("staffid").toString();
            String applicantref = session.getAttribute("staffid").toString();

            Connection conn = (Connection) session.getAttribute("connection");

            DiskFileItemFactory factory = new DiskFileItemFactory();

            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);

            // maximum file size to be uploaded.
            upload.setSizeMax(maxFileSize);

            final String UPLOAD_DIRECTORY = "C:\\Users\\owner\\Desktop\\FMS";
            
            System.out.println("I am here proceeding to test..");
            if (ServletFileUpload.isMultipartContent(request)) {
                System.err.println(UPLOAD_DIRECTORY);
                try {
                    List multiparts = new ServletFileUpload(
                            new DiskFileItemFactory()).parseRequest(request);
                    // Parse the request to get file items.
                    List fileItems = upload.parseRequest(request);

                    // Process the uploaded file items
                    Iterator i = fileItems.iterator();
                    //   for (FileItem item : multiparts) {
                    while (i.hasNext()) {
                        FileItem item = (FileItem) i.next();
                        if (!item.isFormField()) {
                            File fileSaveDir = new File(UPLOAD_DIRECTORY);
                            if (!fileSaveDir.exists()) {
                                fileSaveDir.mkdir();
                            }
                            String name = new File(item.getName()).getName();
                            item.write(new File(UPLOAD_DIRECTORY + File.separator + name));
                        }
                    }
                    //  }
                } catch (Exception e) {
                    // exception handling
                }

                // PrintWriter out = response.getWriter();
                out.print("{\"status\":1}");
            }

        }

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
        //  processRequest(request, response);
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String result = "";

        //request.getParameter("leaveyear");
        if (session != null) {
            String sessionid = session.getAttribute("sessionid").toString();
            //String staffid = session.getAttribute("staffid").toString();
            String applicantref = session.getAttribute("staffid").toString();

            Connection conn = (Connection) session.getAttribute("connection");

            isMultipart = ServletFileUpload.isMultipartContent(request);
            response.setContentType("text/plain");

            if (!isMultipart) {
//                out.println("<html>");
//                out.println("<head>");
//                out.println("<title>Servlet upload</title>");
//                out.println("</head>");
//                out.println("<body>");
//                out.println("<p>No file uploaded</p>");
//                out.println("</body>");
//                out.println("</html>");
//                return;
                result = "Invalid file format";
            } else {

                DiskFileItemFactory factory = new DiskFileItemFactory();

                // maximum size that will be stored in memory
                factory.setSizeThreshold(maxMemSize);

                // Location to save data that is larger than maxMemSize.
                factory.setRepository(new File("/tmp/"));

                // Create a new file upload handler
                ServletFileUpload upload = new ServletFileUpload(factory);

                // maximum file size to be uploaded.
                upload.setSizeMax(maxFileSize);

                try {
                    // Parse the request to get file items.
                    List fileItems = upload.parseRequest(request);

                    // Process the uploaded file items
                    Iterator i = fileItems.iterator();

//                out.println("<html>");
//                out.println("<head>");
//                out.println("<title>Servlet upload</title>");
//                out.println("</head>");
//                out.println("<body>");
                    while (i.hasNext()) {
                        FileItem fi = (FileItem) i.next();
                        if (!fi.isFormField()) {
                            // Get the uploaded file parameters
                            String fieldName = fi.getFieldName();
                            String fileName = fi.getName();
                            String contentType = fi.getContentType();
                            boolean isInMemory = fi.isInMemory();
                            long sizeInBytes = fi.getSize();

                            // Write the file
                            if (fileName.lastIndexOf("\\") >= 0) {
                                file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\")));
                            } else {
                                file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\") + 1));
                            }
                            fi.write(file);
                            //  out.println("Uploaded Filename: " + fileName + "<br>");
                            //      updateRecords(conn,applicantref,docref, file);

                        }
                    }
//                out.println("</body>");
//                out.println("</html>");
                    result = "Successfully updated Records ";
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
            //  result = get(conn);
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

}
