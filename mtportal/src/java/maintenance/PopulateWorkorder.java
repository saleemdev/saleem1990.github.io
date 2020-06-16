/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maintenance;

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
@WebServlet(name = "PopulateWorkorder", urlPatterns = {"/PopulateWorkorder"})
public class PopulateWorkorder extends HttpServlet {

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
            out.println("<title>Servlet PopulateWorkorder</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PopulateWorkorder at " + request.getContextPath() + "</h1>");
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

        response.setContentType("application/json");
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

          

            // String leaveyr = getCurrentFY(conn);
            result = getMyWorkload(conn);//.replace("[", "").replace("]", "");

            //  }
        }

        out.write(result);

//        select distinct refid,servicerequest.location_,servicerequest.requesttype from public.workorder, 
//servicerequest where username ='admin' AND servicerequest.requestid = workorder.refid;
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
        response.setContentType("application/json");
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

            String refid = request.getParameter("refid");

            // String leaveyr = getCurrentFY(conn);
            result = getWorkOrdersActivities(conn, refid);//.replace("[", "").replace("]", "");

            //  }
        }

        out.write(result);
    }

    private String getWorkOrdersActivities(java.sql.Connection connectDB, String refid) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent Listr

        String[] columns = new String[]{"action"};
        //  String sql = "select distinct staffid , fullname, designation, department, section from secure_password order by 2";

        String sql = "SELECT DISTINCT action_taken, tstamp from public.workorder where refid =? and username = current_user ORDER BY tstamp";

        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, refid);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                HashMap<String, String> child = new HashMap<String, String>();
                for (int i = 0; i < columns.length; i++) {//I will not loop because I want to statically place variables from differend resultsets

                    child.put(columns[i].toString(), String.valueOf(rset.getObject(i + 1)));

                }
                parentList.add(child);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        String json = new Gson().toJson(parentList);//String JSON object

        JSONArray arr = new JSONArray();
        arr.put(json);                         //JSON Array

        System.err.println("activities: " + json);
        return json;
    }

    private String getMyWorkload(java.sql.Connection connectDB) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent Listr

        String[] columns = new String[]{"refid","location","requesttype"};
        //  String sql = "select distinct staffid , fullname, designation, department, section from secure_password order by 2";

        String sql = "select distinct refid,servicerequest.location_,servicerequest.requesttype from public.workorder, \n"
                + "servicerequest where username =current_user AND servicerequest.requestid = workorder.refid;";

        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            //pst.setObject(1, refid);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                HashMap<String, String> child = new HashMap<String, String>();
                for (int i = 0; i < columns.length; i++) {//I will not loop because I want to statically place variables from differend resultsets

                    child.put(columns[i].toString(), String.valueOf(rset.getObject(i + 1)));

                }
                parentList.add(child);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        String json = new Gson().toJson(parentList);//String JSON object

        JSONArray arr = new JSONArray();
        arr.put(json);                         //JSON Array

        System.err.println("activities: " + json);
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
