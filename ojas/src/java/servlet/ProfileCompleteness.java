/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

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
@WebServlet(name = "ProfileCompleteness", urlPatterns = {"/ProfileCompleteness"})
public class ProfileCompleteness extends HttpServlet {

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
            out.println("<title>Servlet ProfileCompleteness</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProfileCompleteness at " + request.getContextPath() + "</h1>");
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
        // processRequest(request, response);
        response.setContentType("application/json");

        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String result = "";

        //request.getParameter("leaveyear");
        if (session != null) {
            String sessionid = session.getAttribute("sessionid").toString();
            //String staffid = session.getAttribute("staffid").toString();
            String applicantref = session.getAttribute("staffid").toString();

            Connection conn = (Connection) session.getAttribute("connection");

            // String jobref = request.getParameter("jobref");
            result = getCompleteness(conn, applicantref);

            //  result = get(conn);
        }

        //}
        out.write(result);

    }

    private static String bioCompleteness(Connection conn, String applicantref) {
        String sql = "select case when length(fname)>1 then 1 else 0 end as fname,\n"
                + "case when length(mname)>1 then 1 else 0 end as mname,\n"
                + "case when length(lname)>1 then 1 else 0 end as lname,\n"
                + "case when length(maintel)>5 then 1 else 0 end as maintel,\n"
                + "case when length(email)>1 then 1 else 0 end as email,\n"
                + "case when length(pin)>4 then 1 else 0 end as pin,\n"
                + "case when length(dob::text)>3 then 1 else 0 end as dob,\n"
                + "case when length(gender)>3 then 1 else 0 end as gender,\n"
                + "case when length(homecounty)> 3 then 1 else 0 end as homecounty\n"
                + "\n"
                + "\n"
                + "\n"
                + "--select *\n"
                + "from vacancies.applicant_bio where \n"
                + "idno=? limit 1 ;";
        String result = "0.0";
        Double total = 0.0;
        Double percent = 0.0;
        int size = 0;
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setObject(1, applicantref);
            ResultSet rset = pst.executeQuery();

            // if(rset!=null){
            if (rset.next()) {
                size = rset.getMetaData().getColumnCount();

                System.err.println(size);
                for (int i = 1; i <= size; i++) {
                    //  System.err.println(rset.getString(i));
                    total += Double.valueOf(rset.getString(i));
                }
                //   Double total = Double.valueOf(rset.getString(1)) + Double.valueOf(rset.getString(2)) + Double.valueOf(rset.getString(3)) + Double.valueOf(rset.getString(4)) + Double.valueOf(rset.getString(5))
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //  }
        percent = total / size;
        result = String.valueOf(round(percent, 2) * 100);

        return result;
    }

    private static String acaDemicCompleteness(Connection conn, String applicantref) {
        String sql = "select case when ? in (select applicantid from vacancies.applicant_qualifications)  then 1 else 0 end ;";
        String result = "0.0";
        Double total = 0.0;
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setObject(1, applicantref);
            ResultSet rset = pst.executeQuery();

            // if(rset!=null){
            if (rset.next()) {
                total = Double.valueOf(rset.getString(1));
                //   Double total = Double.valueOf(rset.getString(1)) + Double.valueOf(rset.getString(2)) + Double.valueOf(rset.getString(3)) + Double.valueOf(rset.getString(4)) + Double.valueOf(rset.getString(5))
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //  }

        if (total >= 1) {
            result = "100.0";
        }

        return result;
    }

    private static String attachmentCompleteness(Connection conn, String applicantref) {
        String sql = "select count(applicantid) from vacancies.applicantdocs where applicantid =? and octet_length(doc) >0";
        String result = "0.0";
        Double total = 0.0;
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setObject(1, applicantref);
            ResultSet rset = pst.executeQuery();

            // if(rset!=null){
            if (rset.next()) {
                total = Double.valueOf(rset.getString(1));

                //   Double total = Double.valueOf(rset.getString(1)) + Double.valueOf(rset.getString(2)) + Double.valueOf(rset.getString(3)) + Double.valueOf(rset.getString(4)) + Double.valueOf(rset.getString(5))
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //  }
        if (total >= 1) {
            result = "100.0";
        }

        return result;
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    private String getCompleteness(java.sql.Connection connectDB, String refno) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent List

        String[] columns = new String[]{"bio", "academics", "uploads"};

        HashMap<String, String> child = new HashMap<String, String>();

        //for (int i = 0; i < columns.length; i++) {
        child.put(columns[0].toString(), String.valueOf(bioCompleteness(connectDB, refno)));
        child.put(columns[1].toString(), String.valueOf(acaDemicCompleteness(connectDB, refno)));
        child.put(columns[2].toString(), String.valueOf(attachmentCompleteness(connectDB, refno)));
        // }

        parentList.add(child);

        String json = new Gson().toJson(parentList);//String JSON object

        System.err.println("Completeness index " + json);

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
        //  processRequest(request, response);
        response.setContentType("text/plain");

        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String result = "";

        //request.getParameter("leaveyear");
        if (session != null) {
            String sessionid = session.getAttribute("sessionid").toString();
            //String staffid = session.getAttribute("staffid").toString();
            String applicantref = session.getAttribute("staffid").toString();
            Connection conn = (Connection) session.getAttribute("connection");

            String jobref = request.getParameter("jobref");
            String disabilitystat = request.getParameter("disabilitystat");
            String offencestat = request.getParameter("offencestat");
            String dismissalstat = request.getParameter("dismissalstat");
            String disabilitytext = request.getParameter("disabilitytext");
            String offencetext = request.getParameter("offencetext");
            String dismissaltext = request.getParameter("dismissaltext");

            System.err.println("My dismissaltext is.." + dismissaltext);

            Boolean disability = Boolean.FALSE, offence = Boolean.FALSE, dismissed = Boolean.FALSE;

            if (disabilitystat.equalsIgnoreCase("yes")) {
                disability = Boolean.TRUE;
            }

            if (offencestat.equalsIgnoreCase("yes")) {
                offence = Boolean.TRUE;
            }

            if (dismissalstat.equalsIgnoreCase("yes")) {
                dismissed = Boolean.TRUE;
            }

            PreparedStatement pst;
            try {
                pst = conn.prepareStatement("INSERT INTO vacancies.applications(\n"
                        + "            jobref, applicantid,  disabilitystatus, disabilitydesc, \n"
                        + "            dismissalstatus, dismissaldesc, crimminalstatus, crimminaldesc)\n"
                        + "    VALUES (?, ?, ?, ?, ?, \n"
                        + "            ?, ?, ?);");
                pst.setObject(1, jobref);
                pst.setObject(2, applicantref);
                pst.setBoolean(3, disability);
                pst.setObject(4, disabilitytext);
                pst.setBoolean(5, dismissed);
                pst.setObject(6, dismissaltext);
                pst.setBoolean(7, offence);
                pst.setObject(8, offencetext);

                pst.executeUpdate();

                result = "Data Saved Successfully. Click back on the browser to access your data";

            } catch (SQLException ex) {
                result = "FAILED\n" + ex.getMessage();
                ex.printStackTrace();

            }

        }
        out.println(result);
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
