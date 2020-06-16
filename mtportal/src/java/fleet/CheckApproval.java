/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fleet;

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
import org.json.JSONObject;

/**
 *
 * @author owner
 */
@WebServlet(name = "CheckApproval", urlPatterns = {"/CheckApproval"})
public class CheckApproval extends HttpServlet {

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
            out.println("<title>Servlet CheckApproval</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CheckApproval at " + request.getContextPath() + "</h1>");
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
        String result = "-";
        HttpSession session = request.getSession();
        if (session != null) {
            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");

            String uname = session.getAttribute("username").toString();
            //---//
            //String  rank = getRank(uname, conn);
            String rank = request.getParameter("level");

            result = getAndAndPrepareActivitiesJSON(conn, rank.toUpperCase());
//--//         
        }
        out.write(result);
    }

    public static String getAndAndPrepareActivitiesJSON(java.sql.Connection connectDB, String rank) {

        String sql = "";
        String json = "";
        if (rank.contains("SENIOR") || rank.contains("EXECUTIVE")) {
            System.err.println("Rank is " + rank);
            sql = "SELECT rqid, rqtype, enteredon::timestamp(0), requestedby, from_,destination,purpose, \n"
                    + "(CASE \n"
                    + "    WHEN approved IS null then 'pending' \n"
                    + "    WHEN approved IS false THEN 'rejected'\n"
                    + "    ELSE 'approved'\n"
                    + "END) \n"
                    + "\n"
                    + "\n"
                    + "FROM fleet.transportrequestmemo \n"
                    + "WHERE enteredon::date > current_date - 300 AND transportconfirm is true AND approved is not TRUE AND cancelled is false  ORDER BY enteredon ASC";;
        } else {
            sql = "SELECT rqid, rqtype, enteredon::timestamp(0), requestedby, from_,destination,purpose, \n"
                    + "(CASE \n"
                    + "    WHEN approved IS null then 'pending' \n"
                    + "    WHEN approved IS false THEN 'rejected'\n"
                    + "    ELSE 'approved'\n"
                    + "END) \n"
                    + "\n"
                    + "\n"
                    + "FROM fleet.transportrequestmemo \n"
                    + "WHERE enteredon::date > current_date - 300  AND transportconfirm is NOT TRUE AND cancelled is false ORDER BY enteredon ASC";;
        }
        System.err.println(sql);
        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            //  System.err.println(sql);
            ResultSet rset = pst.executeQuery();

            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();

            String[] columns = new String[]{"request_id", "request_type", "requestedon", "requestedby", "from", "to", "purpose", "status"};

            ArrayList<HashMap<String, String>> parentList
                    = new ArrayList<HashMap<String, String>>();//Parent List

            while (rset.next()) {

                HashMap<String, String> child = new HashMap<String, String>();
                for (int i = 0; i < columns.length; i++) {
                    child.put(columns[i].toString(), rset.getObject(i + 1).toString());
                }
                parentList.add(child);
            }

            json = new Gson().toJson(parentList);
//            try {
//                jsonObject = new JSONObject(json);
//                System.out.println("After setting here we go "+jsonObject.toString());
//            } catch (JSONException ex) {
//                ex.printStackTrace();
//            }

            //    activityBeans.setJson(json);
            // activityBeans.setDataJSON(jsonObject);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

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
        //  processRequest(request, response);
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        String result = "-";
        String requesttype = request.getParameter("rqtype");
        HttpSession session = request.getSession();
        if (session != null) {
            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");

            String uname = session.getAttribute("username").toString();
            //---//
            if (requesttype.contains("validate")) {
                String level = request.getParameter("level");
                result = validateRank(uname, level, conn);
            } else {
                result = getPermissionStatus(uname, conn);
            }
//--//         }
        }
        out.write(result);

    }

    public static String getPermissionStatus(String uname, Connection conn) {
        String stat = "";
        String sql = "SELECT CASE WHEN '" + uname + "' IN (SELECT uname FROM fleet.approval_level where STATUS is true) THEN 'YES' else 'NO' end ";

        System.err.println(sql);
        try {
            PreparedStatement pst = conn.prepareStatement(sql);

            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                stat = rset.getString(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return stat;

    }

    public static String validateRank(String uname, String level, Connection conn) {
        String rank = "";
        String sql = "SELECT CASE WHEN '" + uname + "' IN (SELECT uname FROM fleet.approval_level WHERE level ilike '" + level + "' AND STATUS is true) THEN 'YES' else 'NO' end ";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);

            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                rank = rset.getString(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rank;

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
