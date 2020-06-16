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
@WebServlet(name = "ApprovalLevel", urlPatterns = {"/ApprovalLevel"})
public class ApprovalLevel extends HttpServlet {

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
            out.println("<title>Servlet ApprovalLevel</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ApprovalLevel at " + request.getContextPath() + "</h1>");
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
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String stage = request.getParameter("stage");
        String result = "";
        if (session != null) {
            String sessionid = session.getAttribute("sessionid").toString();
            String staffid = session.getAttribute("staffid").toString();

            //staffid
            //  String loginid = session.getAttribute("username").toString();
            System.err.println("Matching key: " + sessionid);

            //  if (key.equalsIgnoreCase(sessionid)) {
            // msg="1";
            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");

            result = confirmApprovalLevel(conn, staffid, stage);

            //  }
        }

        out.write(result);

    }

    private String confirmApprovalLevel(Connection conn, String staffid, String stage) {

        String stat = "0";

        String sql = "SELECT CASE when '" + staffid + "' IN (SELECT username\n"
                + "  FROM hr.approval_levels WHERE level ilike '" + stage + "') THEN '0' ELSE '1'  END;";

        System.err.println(sql);
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            // pst.setObject(1, staffid);
            //pst.setObject(2, stage);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                stat = rset.getString(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        System.err.println("Status is " + 0);
        return stat;
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
        //processRequest(request, response);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String result = "";

        String stage = request.getParameter("stage");

        if (session != null) {
            String sessionid = session.getAttribute("sessionid").toString();
            String staffid = session.getAttribute("staffid").toString();

            //staffid
            //  String loginid = session.getAttribute("username").toString();
            System.err.println("Matching key: " + sessionid);

            //  if (key.equalsIgnoreCase(sessionid)) {
            // msg="1";
            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");

            result = getMyTasks(conn, staffid, stage);

            //  }
        }

        out.write(result);

    }

    private String getCoveringOfficerID(String key, Connection conn) {
        //  java.sql.Connection conn = this.connect("postgres", "sequence");
        String staffname = "";

        String sql = "";

        // sql = "select login_name from secure_password where  staffid = ?";
        sql = "select fullname from secure_password where staffid = ?";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setObject(1, key);
            ResultSet rset = pst.executeQuery();
            if (rset.next()) {
                staffname = rset.getString(1);
            } else {
                staffname = "None";
            }

            //   conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return staffname;
    }

    private Boolean isSnrManager(String key, Connection conn) {
        //  java.sql.Connection conn = this.connect("postgres", "sequence");
        String staffname = "";

        String sql = "";

        Boolean isSnrMgr = false;
        // sql = "select login_name from secure_password where  staffid = ?";
        sql = "SELECT CASE WHEN '" + key + "' IN (SELECT pfno FROM snrdirectortbl) THEN true else false end";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            //    pst.setObject(1, key);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                isSnrMgr = rset.getBoolean(1);
            }

            //   conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return isSnrMgr;
    }

    private String getMyTasks(Connection conn, String staffid, String stage) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent List

        System.err.println(staffid);
        // Vector v = new Vector();
        String sql = "";
        String[] columns = new String[]{"refno", "empname", "leavetype", "particulars","dept","section"};

        String officer = getCoveringOfficerID(staffid, conn);
        Boolean ISmGR = isSnrManager(staffid, conn);
        System.err.println(stage);
        if (stage.contains("Covering")) {
//            sql = "select refno,pfno, leavetype||'/'||leavecategory,"
//                    + "'Days Requested: '||''||daysrequested||', Covering Officer: '||''||coveringofficer "
//                    + "from hr.leave_application WHERE coveringofficer ILIKE '%" + staffid + "' AND covering IS null;";
            //  sql = "select refno,pfno, leavetype||'/'||leavecategory, 'Days Requested: '||''||daysrequested||', Covering Officer: '||''||coveringofficer from hr.leave_application WHERE pfno IN (SELECT empno FROM myImediateStaff('"+staffid+"')) AND covering IS null AND pfno!='"+staffid+"';";//Original query
            sql = "select refno,pfno, leavetype||'/'||leavecategory, 'Applicant Name: '||''||getstaffnamebyid(pfno)||','|| ' Days Requested: '||''||daysrequested||', Covering Officer: '||''||coveringreal, getstaffdeptbyid(pfno) as dept, getstaffsectionbyid(pfno) as section from hr.leave_application WHERE pfno IN (SELECT empno FROM myImediateStaff('" + staffid + "')) AND covering IS null AND pfno!='" + staffid + "'; ";
        } else if (stage.contains("Supervisor")) {
            
          //  sql = "select refno,pfno, leavetype||'/'||leavecategory, 'Applicant Name: '||''||getstaffnamebyid(pfno)||','|| ' Days Requested: '||''||daysrequested||', Covering Officer: '||''||coveringofficer from hr.leave_application WHERE covering is true AND hodsupervisor IS null "
            //        + " AND pfno IN (select empno from public.myDeptStaff('" + staffid + "'))";
            //Below is the new thing
              sql = "select refno,pfno, leavetype||'/'||leavecategory, 'Applicant Name: '||''||getstaffnamebyid(pfno)||','|| ' Days Requested: '||''||daysrequested||', Covering Officer: '||''||coveringreal, getstaffdeptbyid(pfno) as dept, getstaffsectionbyid(pfno) as section from hr.leave_application WHERE covering is true AND hodsupervisor IS null "
                    + " AND pfno IN (select empno from public.mydeptstaffcrystal('" + staffid + "'))";
            //mydeptstaffcrystal
//            if (ISmGR) {
//                sql = "select refno,pfno, leavetype||'/'||leavecategory, 'Applicant Name: '||''||getstaffnamebyid(pfno)||','|| ' Days Requested: '||''||daysrequested||', Covering Officer: '||''||coveringofficer from hr.leave_application WHERE covering is true AND hodsupervisor IS null "
//                        + " AND (pfno IN (select empno from public.myDeptStaff('" + staffid + "')) OR pfno IN (select pfno from snrdirectortbl))";
//            }

        } else if (stage.contains("Resourcing")) {
            sql = "select refno,pfno, leavetype||'/'||leavecategory, 'Applicant Name: '||''||getstaffnamebyid(pfno)||','|| ' Days Requested: '||''||daysrequested||', Covering Officer: '||''||coveringreal, getstaffdeptbyid(pfno) as dept, getstaffsectionbyid(pfno) as section from hr.leave_application WHERE  hodsupervisor IS true AND hresourcing is null;";
        } else if (stage.contains("Senior")) {
            sql = "select refno,pfno, leavetype||'/'||leavecategory, 'Applicant Name: '||''||getstaffnamebyid(pfno)||','|| ' Days Requested: '||''||daysrequested||', Covering Officer: '||''||coveringreal, getstaffdeptbyid(pfno) as dept, getstaffsectionbyid(pfno) as section from hr.leave_application WHERE hresourcing IS true AND snrmgr IS null AND pfno IN (SELECT empno FROM myDirectorateStaff('" + staffid + "'));";
        }

        //select refno,pfno, leavetype||'/'||leavecategory, 'Applicant Name: '||''||getstaffnamebyid(pfno)||','|| ' Days Requested: '||''||daysrequested||', Covering Officer: '||''||coveringofficer from hr.leave_application
        System.err.println(sql);

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
//            if (stage.contains("Covering")) {
//                pst.setObject(1, officer);
//            }
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {

                HashMap<String, String> child = new HashMap<String, String>();
                for (int i = 0; i < columns.length; i++) {
                    child.put(columns[i].toString(), rset.getObject(i + 1).toString());
                    // child.put(columns[1].toString(), String.valueOf(leaveBalance));
                }

                parentList.add(child);
            }

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }
        String json = new Gson().toJson(parentList);//String JSON object

        System.err.println("My Approvals are here" + json);

        JSONArray arr = new JSONArray();
        arr.put(json);                         //JSON Array

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
