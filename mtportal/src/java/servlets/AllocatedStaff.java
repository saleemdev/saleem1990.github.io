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
@WebServlet(name = "AllocatedStaff", urlPatterns = {"/AllocatedStaff"})
public class AllocatedStaff extends HttpServlet {

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
            out.println("<title>Servlet AllocatedStaff</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AllocatedStaff at " + request.getContextPath() + "</h1>");
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

        String section = request.getParameter("section");
        String station = request.getParameter("station");
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

//            result = getMyColleagues(conn, department).replace("[", "").replace("]", "");
            result = getAllocatedStaff(conn, section, station);
            System.err.println("My colleagues: " + result);

            //  }
        }

        out.write(result);
    }

    private String getAllocatedStaff(java.sql.Connection connectDB, String section, String station) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent List

        String sql = "";
        String[] columns = new String[]{"staffname"};

        sql = "SELECT distinct staffname FROM SECTION_ALLOCATION where upper(section)=? and upper(station) =?";//"select distinct coveringofficer from hr.leaverota where staffid = ? AND rota_fy = ? ";//"select case when staffid IN (select pfno from hr.leave_application) THEN fullname||' on leave' else fullname end as person from secure_password \n"
        // + "where department = ? AND login_name NOT ILIKE current_user;";

        try {

            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, section.toUpperCase());
            pst.setObject(2, station.toUpperCase());

            ResultSet rset = pst.executeQuery();
            while (rset.next()) {

                HashMap<String, String> child = new HashMap<String, String>();
                for (int i = 0; i < columns.length; i++) {
                    child.put(columns[i].toString(), rset.getObject(i + 1).toString());
                    //  child.put(columns[1].toString(), String.valueOf(leaveBalance));
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

            Object staffname = request.getParameter("staffname");
            Object section = request.getParameter("section");
            Object station = request.getParameter("station");
            String transtype = request.getParameter("transtype");

            // "staffname=" + staffname + "&section=" + section + "&transtype=delete&station="+station;
            String sql = "";
            if (transtype.contains("deletion")) {
                sql = "DELETE FROM SECTION_ALLOCATION where UPPER(staffname)=? AND upper(section)=? and upper(station) =?";
            } 
            if (transtype.contains("addition")) {
                sql = "INSERT INTO public.section_allocation(\n"
                        + "            staffname, section, station )\n"
                        + "    VALUES (?, ?, ?);";
            }
            try {

                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setObject(1, staffname.toString().toUpperCase());
                pst.setObject(2, section.toString().toUpperCase());
                pst.setObject(3, station.toString().toUpperCase());

                pst.executeUpdate();
                result = "Successfuy posted";

                com.mtrh.mtportal.sys.LeaveFactory.updateLogs(conn, staffname + "/" + sessionid, "DELETION", "DELETED ALLOCATION OF " + staffname + " FROM " + section + "/" + station);

            } catch (SQLException ex) {
                result = "Error\n" + ex.getCause();
                ex.printStackTrace();
            }

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
