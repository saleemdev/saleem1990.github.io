/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

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
import org.json.JSONArray;

/**
 *
 * @author owner
 */
@WebServlet(name = "LeaveTypes", urlPatterns = {"/LeaveTypes"})
public class LeaveTypes extends HttpServlet {

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
            out.println("<title>Servlet LeaveTypes</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LeaveTypes at " + request.getContextPath() + "</h1>");
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
    private String getLeaveTypes(java.sql.Connection connectDB) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent List

       
        // Vector v = new Vector();
        String sql = "";
        String[] columns = new String[]{"name"};

        sql = "select description from hr.leave_types order by 1";

       
        try {
            // String fy = getCurrentFY(connectDB);

            PreparedStatement pst = connectDB.prepareStatement(sql);
            //pst.setObject(1, staffid);
            //pst.setObject(2, fy);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {

                HashMap<String, String> child = new HashMap<String, String>();
                // for (int i = 0; i < columns.length; i++) {
                child.put(columns[0].toString(), rset.getObject(1).toString());
                //  child.put(columns[1].toString(), String.valueOf(leaveBalance));
                //  }

                parentList.add(child);
            }

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }
        String json = new Gson().toJson(parentList);//String JSON object

        JSONArray arr = new JSONArray();
        arr.put(json);                         //JSON Array

        return json;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        response.setContentType("application/json");

        String department = request.getParameter("department");
        Object transtype = request.getParameter("transtype");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String result = "";
        if (session != null) {
            String sessionid = session.getAttribute("sessionid").toString();
            String staffid = session.getAttribute("staffid").toString();

            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");
       
            result = getLeaveTypes(conn);

            System.err.println("My leavetypes: " + result);

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
