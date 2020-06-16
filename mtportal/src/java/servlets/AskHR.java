/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

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
import org.json.JSONArray;

/**
 *
 * @author owner
 */
@WebServlet(name = "AskHR", urlPatterns = {"/AskHR"})
public class AskHR extends HttpServlet {

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
            out.println("<title>Servlet AskHR</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AskHR at " + request.getContextPath() + "</h1>");
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
            result = getMessages(conn, refno);//.replace("[", "").replace("]", "");

            //  }
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
    private String getMessages(java.sql.Connection connectDB, String refno) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent List

        String[] columns = new String[]{"message", "meta"};

        String sql = "select message,username from hr.notifications where refno='" + refno + "' order by timestamp_;";

        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                HashMap<String, String> child = new HashMap<String, String>();
                //  for (int i = 0; i < columns.length; i++) {//I will not loop because I want to statically place variables from differend resultsets

                for (int i = 0; i < columns.length; i++) {

                    child.put(columns[i].toString(), String.valueOf(rset.getObject(i + 1)));
                }

                // child.put(columns[3].toString(), String.valueOf(getUnappraisedForms(connectDB, staffid)));
                //     }
                parentList.add(child);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        String json = new Gson().toJson(parentList);//String JSON object

        System.err.println("I am here names " + json);

        JSONArray arr = new JSONArray();
        arr.put(json);                         //JSON Array

        return json;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
            String message = request.getParameter("message");
            String refno = request.getParameter("refno");
            String username = request.getParameter("username");

            String pfinq = request.getParameter("pfno");

            String transtype = request.getParameter("transtype");
            try {
                // String leaveyr = getCurrentFY(conn);
                PreparedStatement pst = conn.prepareStatement("INSERT into hr.notifications(refno,message,username, status) values('" + refno + "',?, ?, 'R')");
                pst.setObject(1, message);
                pst.setObject(2, username);
                // pst.setObject(3, transtype);
                pst.executeUpdate();

                String chatidstat = chatIDstatus(refno, conn);//Does everything
                
                
                //  }
            } catch (SQLException ex) {
                ex.printStackTrace();
                result = "FAIL" + ex.getMessage();
            }
        }

        out.write(result);
    }

    private static void persistChat(String refno, String stat, Connection conn) {

        String sql = "UPDATE hr.notifications SET status = '" + stat + "' WHERE refno ='" + refno + "'";
        System.err.println(sql);
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.executeUpdate();

            //return stat;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static String chatIDstatus(String refno, Connection conn) {
        String stat = "S";
        String sql = "SELECT count(refno) FROM hr.notifications WHERE refno ='" + refno + "'";
        System.err.println(sql);

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();
            if (rset.next()) {
                int count = rset.getInt(1);

                //  if (count > 0) { //if chatid exists
                if (count == 1) {
                    stat = "S";
                    System.err.println("Updated because entry was one");
                    persistChat(refno, stat, conn);
                } else {
                    stat = "R";
                }
                // }

            }

            //return stat;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return stat;
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
