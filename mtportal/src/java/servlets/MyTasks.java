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
@WebServlet(name = "MyTasks", urlPatterns = {"/MyTasks"})
public class MyTasks extends HttpServlet {

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
            out.println("<title>Servlet MyTasks</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MyTasks at " + request.getContextPath() + "</h1>");
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
    private String FullNameByID(String key, Connection conn) {

        String login = "";

        String sql = "";

        // sql = "select login_name from secure_password where  staffid = ?";
        sql = "select fullname from secure_password where staffid = ?";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setObject(1, key);
            ResultSet rset = pst.executeQuery();
            if (rset.next()) {
                login = rset.getString(1);
            } else {
                login = "None";
            }

            //  conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return login;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // processRequest(request, response);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String result = "";

        //String stage = request.getParameter("stage");
        if (session != null) {
            String sessionid = session.getAttribute("sessionid").toString();
            String staffid = session.getAttribute("staffid").toString();

            //staffid
            //  String loginid = session.getAttribute("username").toString();
            System.err.println("Matching key: " + sessionid);

            //  if (key.equalsIgnoreCase(sessionid)) {
            // msg="1";
            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");

            String fullname = FullNameByID(staffid, conn).trim().toUpperCase();

            result = getMyTasks(conn, fullname);

            //  }
        }

        out.write(result);

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    private String getMyTasks(Connection conn, String fullname) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent List

        System.err.println(fullname);
        // Vector v = new Vector();
        String sql = "";
        String[] columns = new String[]{"refno", "level", "applicant", "leavetype", "action"};
//refno, level, applicant,leavetype,action
        sql = " select refno, 'IMMEDIATE SUPERVISOR', secure_password.fullname||'-'||pfno, leavetype,case when covering is true then 'APPROVED' WHEN covering is false then 'DECLINED' END AS action_taken \n"
                + " from hr.leave_application, secure_password where secure_password.staffid = hr.leave_application.pfno\n"
                + " and upper(coveringofficer) ilike ?\n"
                + "\n"
                + "UNION\n"
                + "\n"
                + " select  refno,'DEPARTMENT HEAD', secure_password.fullname||'-'||pfno, leavetype,case when hodsupervisor is true then 'APPROVED' WHEN hodsupervisor is false then 'DECLINED' END AS action_taken \n"
                + " from hr.leave_application, secure_password where secure_password.staffid = hr.leave_application.pfno\n"
                + " and upper(supervisor) ilike ?\n"
                + "\n"
                + " UNION\n"
                + "\n"
                + "\n"
                + " select  refno,'RESOURCING', secure_password.fullname||'-'||pfno, leavetype,case when hresourcing is true then 'APPROVED' WHEN hresourcing is false then 'DECLINED' END AS action_taken \n"
                + " from hr.leave_application, secure_password where secure_password.staffid = hr.leave_application.pfno\n"
                + " and upper(resourcing) ilike ? \n"
                + "\n"
                + "UNION\n"
                + " \n"
                + " SELECT  refno,'SENIOR MANAGEMENT', secure_password.fullname||'-'||pfno, leavetype,case when snrmgr is true then 'APPROVED' WHEN snrmgr is false then 'DECLINED' END AS action_taken \n"
                + " from hr.leave_application, secure_password where secure_password.staffid = hr.leave_application.pfno\n"
                + " and upper(seniormanager) ilike ? order by 2";

        //select refno,pfno, leavetype||'/'||leavecategory, 'Applicant Name: '||''||getstaffnamebyid(pfno)||','|| ' Days Requested: '||''||daysrequested||', Covering Officer: '||''||coveringofficer from hr.leave_application
        System.err.println(" select refno, 'IMMEDIATE SUPERVISOR', secure_password.fullname||'-'||pfno, leavetype,case when covering is true then 'APPROVED' WHEN covering is false then 'DECLINED' END AS action_taken \n"
                + " from hr.leave_application, secure_password where secure_password.staffid = hr.leave_application.pfno\n"
                + " and upper(coveringofficer) ilike '" + fullname + "%'\n"
                + "\n"
                + "UNION\n"
                + "\n"
                + " select  refno,'DEPARTMENT HEAD', secure_password.fullname||'-'||pfno, leavetype,case when hodsupervisor is true then 'APPROVED' WHEN hodsupervisor is false then 'DECLINED' END AS action_taken \n"
                + " from hr.leave_application, secure_password where secure_password.staffid = hr.leave_application.pfno\n"
                + " and upper(supervisor) ilike '" + fullname + "%'\n"
                + "\n"
                + " UNION\n"
                + "\n"
                + "\n"
                + " select  refno,'RESOURCING', secure_password.fullname||'-'||pfno, leavetype,case when hresourcing is true then 'APPROVED' WHEN hresourcing is false then 'DECLINED' END AS action_taken \n"
                + " from hr.leave_application, secure_password where secure_password.staffid = hr.leave_application.pfno\n"
                + " and upper(resourcing) ilike '" + fullname + "%' \n"
                + "\n"
                + "UNION\n"
                + " \n"
                + " SELECT  refno,'SENIOR MANAGEMENT', secure_password.fullname||'-'||pfno, leavetype,case when snrmgr is true then 'APPROVED' WHEN snrmgr is false then 'DECLINED' END AS action_taken \n"
                + " from hr.leave_application, secure_password where secure_password.staffid = hr.leave_application.pfno\n"
                + " and upper(seniormanager) ilike '" + fullname + "%' order by 2");

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
//            if (stage.contains("Covering")) {
            pst.setObject(1, fullname+"%");
            pst.setObject(2, fullname+"%");
            pst.setObject(3, fullname+"%");
            pst.setObject(4, fullname+"%");
//            }
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                System.err.println(rset.getObject(1).toString());;
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
