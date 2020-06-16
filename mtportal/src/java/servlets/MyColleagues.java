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
@WebServlet(name = "MyColleagues", urlPatterns = {"/MyColleagues"})
public class MyColleagues extends HttpServlet {

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
            out.println("<title>Servlet MyColleagues</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MyColleagues at " + request.getContextPath() + "</h1>");
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
    private String getUserDetails(java.sql.Connection connectDB, String staffid, Object transtype) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent List

        // Vector v = new Vector();
        String sql = "";
        String[] columns = new String[]{"empno", "name", "designation", "email", "phone", "account", "department"};

        sql = "select  staffid,fullname,designation, email, phone, login_name, department from secure_password where upper(staffid) ilike upper(?);";

        // System.err.println(sql + " is this " + userref);
        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, staffid);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {

                HashMap<String, String> child = new HashMap<String, String>();
                for (int i = 0; i < columns.length; i++) {
                    child.put(columns[i].toString(), rset.getObject(i + 1).toString());
                }

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //  processRequest(request, response);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        String result = "";
        String staffid = "";
        if (session != null) {
            String sessionid = session.getAttribute("sessionid").toString();
            String covering = request.getParameter("name");//session.getAttribute("username").toString();
            String[] seperatedDetails = covering.split("/");

            //session.getAttribute("staffid").toString();
            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");

            if (covering.length() < 2) {
                staffid = request.getParameter("staffid");
                result = getUserDetails(conn, staffid, "-").replace("[", "").replace("]", "");
            } else {
                staffid = seperatedDetails[1];
                result = getUserDetails(conn, staffid, "-").replace("[", "").replace("]", "");
            }
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
    private String getMyColleagues(java.sql.Connection connectDB, String staffid) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent List

        System.err.println(staffid);
        // Vector v = new Vector();
        String sql = "";
        String[] columns = new String[]{"name"};

        sql = "select distinct coveringofficer from hr.leaverota where staffid = ? AND rota_fy = ? ";//"select case when staffid IN (select pfno from hr.leave_application) THEN fullname||' on leave' else fullname end as person from secure_password \n"
        // + "where department = ? AND login_name NOT ILIKE current_user;";

        System.err.println(sql + "--------------" + staffid);
        try {
            String fy = getCurrentFY(connectDB);

            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, staffid);
            pst.setObject(2, fy);
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

    private String getAllMyColleagues(java.sql.Connection connectDB, String staffid) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent List

        System.err.println(staffid);
        // Vector v = new Vector();
        String sql = "";
        String[] columns = new String[]{"name"};

        sql = "select empname||'/'||empno from mydeptstaff(?)";//"select distinct coveringofficer from hr.leaverota where staffid = ? AND rota_fy = ? ";//"select case when staffid IN (select pfno from hr.leave_application) THEN fullname||' on leave' else fullname end as person from secure_password \n"
        // + "where department = ? AND login_name NOT ILIKE current_user;";

        try {
            String fy = getCurrentFY(connectDB);

            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, staffid);
            // pst.setObject(2, fy);
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

    private static String getCurrentFY(Connection conn) {
        String fy = "";
        try {
            String sql = "select yrid from hr.activeyear where status = true";
            PreparedStatement pst = conn.prepareStatement(sql);

            ResultSet rset = pst.executeQuery();
            if (rset.next()) {
                fy = rset.getObject(1).toString();
            }

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }

        return fy;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");

        String department = request.getParameter("department");
        Object transtype = request.getParameter("transtype");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String result = "";
        if (session != null) {
            String sessionid = session.getAttribute("sessionid").toString();
            String staffid = session.getAttribute("staffid").toString();

            //staffid
            //  String loginid = session.getAttribute("username").toString();
            //  if (key.equalsIgnoreCase(sessionid)) {
            // msg="1";
            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");

            System.err.println("Department length " + department.length() + " Department " + department);
//            result = getMyColleagues(conn, department).replace("[", "").replace("]", "");
            if (department.length() < 2) {
                staffid = request.getParameter("staffid");
                result = getAllMyColleagues(conn, staffid);
            } else {
                result = getMyColleagues(conn, staffid);

            }
            System.err.println("My colleagues: " + result);

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
