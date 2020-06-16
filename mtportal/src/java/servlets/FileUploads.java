/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.ws.rs.Consumes;
import org.json.JSONArray;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.*;

/**
 *
 * @author owner
 */
@WebServlet(name = "FileUploads", urlPatterns = {"/FileUploads"})
@MultipartConfig
public class FileUploads extends HttpServlet {

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
            out.println("<title>Servlet FileUploads</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FileUploads at " + request.getContextPath() + "</h1>");
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
        //select pkref, description from docs where refno ='3963JFR'

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String result = "";

        String refno = request.getParameter("refno");

        //request.getParameter("leaveyear");
        if (session != null) {
            String sessionid = session.getAttribute("sessionid").toString();
            String staffid = session.getAttribute("staffid").toString();

            //staffid
            //  String loginid = session.getAttribute("username").toString();
            System.err.println("Matching key: " + sessionid);

            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");

            // String leaveyr = getCurrentFY(conn);
            result = getUploads(conn, refno);//.replace("[", "").replace("]", "");

            //  }
        }

        out.write(result);

    }

    private String getUploads(java.sql.Connection connectDB, String refno) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent List

        String[] columns = new String[]{"refno", "description"};

        String sql = "select pkref, description from docs WHERE refno='" + refno + "' order by 1;";

        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                HashMap<String, String> child = new HashMap<String, String>();

                for (int i = 0; i < columns.length; i++) {

                    child.put(columns[i].toString(), String.valueOf(rset.getObject(i + 1)));
                }

                parentList.add(child);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        String json = new Gson().toJson(parentList);//String JSON object

        System.err.println("I am here files " + json);

        JSONArray arr = new JSONArray();
        arr.put(json);                         //JSON Array

        return json;
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
    @Consumes("multipart/form-data")

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
            String staffid = session.getAttribute("staffid").toString();

            //staffid
            //  String loginid = session.getAttribute("username").toString();
            System.err.println("Matching key: " + sessionid);
 
            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");

            String refno = request.getParameter("refno");
            String desc = request.getParameter("filedesc");
            String pkref = refno + "" + randomAlphaNumeric(5);
            Part filepart = request.getPart("file");
            
            InputStream pdfFileBytes = null;

            pdfFileBytes = filepart.getInputStream();
            
            final byte[] bytes = new byte[pdfFileBytes.available()];

            pdfFileBytes.read(bytes); //Option 2 use bytes

            PreparedStatement pst;
            try {
                pst = conn.prepareStatement("INSERT INTO docs (refno,description,uploaded_file,pkref) values (?,?,?,?)");
                pst.setObject(1, refno);
                pst.setObject(2, desc);
                pst.setBytes(3, bytes);
                pst.setObject(4, pkref);

                pst.executeUpdate();

                result = "Data Saved Successfully. Click back on the browser to access your data "+refno;
                
                
                
                
            } catch (SQLException ex) {
                result = "FAILED\n" + ex.getMessage();
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
