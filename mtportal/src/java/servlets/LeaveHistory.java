/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import com.mtrh.mtportal.sys.DBObject;
import com.mtrh.mtportal.sys.LeaveFactory;
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
@WebServlet(name = "LeaveHistory", urlPatterns = {"/LeaveHistory"})
public class LeaveHistory extends HttpServlet {

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
            out.println("<title>Servlet LeaveHistory</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LeaveHistory at " + request.getContextPath() + "</h1>");
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
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //   processRequest(request, response);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String refno = request.getParameter("refno");
        String leavetype = request.getParameter("lvtype");
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

            result = getLeaveComputation(conn, refno, leavetype);

            //  }
        }

        out.write(result);

    }

    private String getLeaveComputation(Connection conn, String refno, String leavetype) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent List

        System.err.println(refno);
        // Vector v = new Vector();
        String sql = "";
        String[] columns = new String[]{"parameter", "answer"};

        sql = "select * from hr.leave_application WHERE refno=?; ";

        //select refno,pfno, leavetype||'/'||leavecategory, 'Applicant Name: '||''||getstaffnamebyid(pfno)||','|| ' Days Requested: '||''||daysrequested||', Covering Officer: '||''||coveringofficer from hr.leave_application
        System.err.println(sql);

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setObject(1, refno);
//            if (stage.contains("Covering")) {
//                pst.setObject(1, officer);
//            }
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                HashMap<String, String> child = new HashMap<String, String>();

                String pf = rset.getString("pfno");

                String daysreq = rset.getString("daysrequested");
                String leavestart =rset.getString("leavestart").replace("/", "-");

                int leaveEntitlement = LeaveFactory.getLeaveEntitleMent(pf, leavetype,refno, conn);
                int leaveAccumulated = LeaveFactory.getLeaveAccumulatedDaysByType(pf, leavetype,refno, conn);

                //  int leaveRequested = LeaveFactory.getLeaveEntitleMent(pf, lvtype, connectDB);
                int leaveGranted = LeaveFactory.getLeaveDaysGranted(pf, refno, leavetype, conn);

                int accumulatedBeforeGrant = leaveAccumulated - leaveGranted;

                int leavebalbefore = leaveEntitlement - accumulatedBeforeGrant;

                int leaveBalAfter = leaveEntitlement - leaveAccumulated;
                

                String leaveEnd = new DBObject().getDBObject(LeaveFactory.getLeaveEnd(conn, leavestart, leaveGranted, leavetype), "") ;

                String resumption =  new DBObject().getDBObject(LeaveFactory.getResumptionDate(conn, leaveEnd),"");
                
                
                // for (int i = 0; i < columns.length; i++) {
               

                child.put(columns[0], "LEAVE TYPE");
                child.put(columns[1], leavetype); //First row
                parentList.add(child);

                child.put(columns[0], "ACCUMULATED DAYS(with permission)");
                child.put(columns[1], String.valueOf(leaveAccumulated)); //Second row
                parentList.add(child);

                child.put(columns[0], "DAYS REQUESTED");
                child.put(columns[1], daysreq); //Third row
                parentList.add(child);

                child.put(columns[0], "BALANCE BEFORE COMPUTATION");
                child.put(columns[1], String.valueOf(leavebalbefore)); //Fourth row
                parentList.add(child);

                child.put(columns[0], "DAYS GRANTED");
                child.put(columns[1], String.valueOf(leaveGranted)); //Fifth row
                parentList.add(child);

                child.put(columns[0], "BALANCE AFTER COMPUTATION");
                child.put(columns[1], String.valueOf(leaveBalAfter)); //Sixth row
                parentList.add(child);

                child.put(columns[0], "START DATE");
                child.put(columns[1], LeaveFactory.getFormattedDate(conn, leavestart)); //Seventh row
                parentList.add(child);

                child.put(columns[0], "END DATE");
                child.put(columns[1], LeaveFactory.getFormattedDate(conn, leaveEnd)); //Eighth row
                parentList.add(child);
                
                child.put(columns[0], "RESUMING DATE");
                child.put(columns[1], LeaveFactory.getFormattedDate(conn, resumption)); //Ninth row
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
