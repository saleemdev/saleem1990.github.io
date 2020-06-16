/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maintenance;

import com.google.gson.Gson;
import com.mtrh.mtportal.sys.SendSms;
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
@WebServlet(name = "WorkOrders", urlPatterns = {"/WorkOrders"})
public class WorkOrders extends HttpServlet {

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
            out.println("<title>Servlet WorkOrders</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet WorkOrders at " + request.getContextPath() + "</h1>");
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
    private static String UserPhone(String key, Connection conn) {

        String login = "";

        String sql = "";

        // sql = "select login_name from secure_password where  staffid = ?";
        sql = "select phone from secure_password where staffid = ?";

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

    private static String FullnameByID(String key, Connection conn) {

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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

            Object activity = request.getParameter("activity");
            Object refid = request.getParameter("refid");
            Object issue = request.getParameter("issue");

            String sql = "INSERT INTO public.workorder(\n"
                    + "            refid, serviceitem, action_taken, status)\n"
                    + "    VALUES (?, ?, ?, ?);";

            System.err.println(issue + " is the issue\n" + refid + "\nactivity\n" + activity);

            try {
                if (issue.toString().equalsIgnoreCase("-")) {
                    issue = getissuebyid(refid, conn);
                }
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setObject(1, refid);
                pst.setObject(2, issue);
                pst.setObject(3, activity);
                if (issue.toString().equalsIgnoreCase("Ticket Allocation")) {
                    pst.setObject(4, false);
                } else {
                    pst.setObject(4, true);
                }

                if (issue.toString().equalsIgnoreCase("Ticket Allocation")) {
                    String[] user = activity.toString().split("/");

                    String tel = UserPhone(user[1], conn);

                    String fullname = FullnameByID(user[1], conn);
                    
                    String email = UserEmailByID(user[1], conn);

                    new SendSms(tel, "Dear " + fullname + "\n you have been assigned a ticket " + refid + " to consult on a staff issue \nPlease check your email for a e-ticket and accompanying details", refid.toString(), conn);

                    String refno = refid.toString();
                    eTicket pdf = new eTicket();
                    pdf.RequestPDF(conn, refno, email, true);

                }

                pst.executeUpdate();
                result = "Successfuy posted";

            } catch (SQLException ex) {
                result = "Error\n" + ex.getCause();
                ex.printStackTrace();
            }

        }

        out.write(result);

    }
    
    private static String UserEmailByID(String key, Connection conn) {

        String login = "";

        String sql = "";

        // sql = "select login_name from secure_password where  staffid = ?";
        sql = "select email from secure_password where staffid = ?";

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

    private String getissuebyid(Object id, Connection connectDB) {

        String issue = "";
        String sql = "select requesttype from servicerequest where requestid=?";
        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, id);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                issue = rset.getObject(1).toString();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return issue;
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
            result = getAllRequests(conn, staffid);//.replace("[", "").replace("]", "");

            //  }
        }

        out.write(result);

    }

    private String getAllRequests(java.sql.Connection connectDB, String staffid) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent List

        String[] columns = new String[]{"rqid", "rqtype", "contactperson", "location", "description", "tstamp", "uname", "percent"};
        String sql = "select *, workorderstatus(requestid) from servicerequest WHERE completed is FALSE "
                + "AND location_ IN (SELECT location FROM asset_register WHERE responsible_tech ilike ? UNION SELECT station||'/'||section from section_allocation WHERE staffname ilike ? UNION SELECT section FROM section_allocation WHERE staffname ILIKE ?) "
                + "order by tstamp desc ";

        System.err.println(sql + " " + staffid);
        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, "%" + staffid);
            pst.setObject(2, "%" + staffid);
            pst.setObject(3, "%" + staffid);
            //
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

        System.err.println("I am here names " + json);

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
