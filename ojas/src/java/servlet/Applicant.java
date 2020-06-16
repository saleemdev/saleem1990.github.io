/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
//import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.ws.rs.Consumes;
import org.json.JSONArray;

/**
 *
 * @author owner
 */
@WebServlet(name = "Applicant", urlPatterns = {"/Applicant"})
@MultipartConfig
public class Applicant extends HttpServlet {

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
            out.println("<title>Servlet Applicant</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Applicant at " + request.getContextPath() + "</h1>");
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
    @Consumes("multipart/form-data")
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // processRequest(request, response);
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
            String transtype = request.getParameter("transtype");

            if (transtype.equalsIgnoreCase("elb")) {
                String jobref = request.getParameter("jobref");

                String firstname = request.getParameter("firstnameid");
                String secondname = request.getParameter("secondnameid");
                String lastname = request.getParameter("lastnameid");
                String email = request.getParameter("emailaddressid");
                String mobile = request.getParameter("mobilenoid");
                String pin = request.getParameter("pinnoid");

                System.err.println("My pin is.." + pin);
                //String pkref = applicantref + "-" + randomAlphaNumeric(5);
//            Part filepart = request.getPart("passport");
//
//            InputStream imgfilebytes = null;
//
//            imgfilebytes = filepart.getInputStream();
//
//            final byte[] bytes = new byte[imgfilebytes.available()];
//
//            imgfilebytes.read(bytes); //Option 2 use bytes

                PreparedStatement pst;
                try {
                    pst = conn.prepareStatement("UPDATE vacancies.applicant_bio\n"
                            + "   SET  fname=?, mname=?, lname=?, maintel=?, \n"
                            + "       email=?, pin=? \n"
                            + " WHERE idno= ?\n"
                            + ";");
                    pst.setObject(1, firstname);
                    pst.setObject(2, secondname);
                    pst.setObject(3, lastname);
                    pst.setObject(4, mobile);
                    pst.setObject(5, email);
                    pst.setObject(6, pin);
                    // pst.setBytes(7, bytes);
                    pst.setObject(7, applicantref);

                    pst.executeUpdate();

                    result = "Data Saved Successfully. Click back on the browser to access your data";

                } catch (SQLException ex) {
                    result = "FAILED\n" + ex.getMessage();
                    ex.printStackTrace();

                }
            }
            
            else {
                //String jobref = request.getParameter("jobref");

                String dob = request.getParameter("dob");
                String gender = request.getParameter("gender");
                String postal = request.getParameter("postal");
                String nhif = request.getParameter("nhif");
                String nssf = request.getParameter("nssf");
                String homecounty = request.getParameter("homecounty");
                String ethnicity = request.getParameter("ethnicity");
                String subgroup = request.getParameter("subgroup");

                System.err.println("My homecounty is.." + homecounty);
                //String pkref = applicantref + "-" + randomAlphaNumeric(5);
//            Part filepart = request.getPart("passport");
//
//            InputStream imgfilebytes = null;
//
//            imgfilebytes = filepart.getInputStream();
//
//            final byte[] bytes = new byte[imgfilebytes.available()];
//
//            imgfilebytes.read(bytes); //Option 2 use bytes

                PreparedStatement pst;
                try {
                    pst = conn.prepareStatement("UPDATE vacancies.applicant_bio\n"
                            + "   SET  dob=?::date, gender=?, postaladdr=?, nhifno=?, \n"
                            + "       nssfno=?, homecounty=?, ethnicity=?, subgroup=? \n"
                            + " WHERE idno= ?\n"
                            + ";");
                    pst.setObject(1, dob);
                    pst.setObject(2, gender);
                    pst.setObject(3, postal);
                    pst.setObject(4, nhif);
                    pst.setObject(5, nssf);
                    pst.setObject(6, homecounty);
                    // pst.setBytes(7, bytes);
                    pst.setObject(7, ethnicity);
                    pst.setObject(8, subgroup);
                    
                    pst.setObject(9, applicantref);

                    pst.executeUpdate();

                    result = "Data Saved Successfully. Click back on the browser to access your data";

                } catch (SQLException ex) {
                    result = "FAILED\n" + ex.getMessage();
                    ex.printStackTrace();

                }
            }
        }
        out.println(result);
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
//        $('#idtypeappl').val(data.idtype);
//            $('#idnoappl').val(data.idno);
//            $('#fnameappl').val(data.fname);
//            $('#snameappl').val(data.sname);
//            $('#lnameappl').val(data.lname);
//            $('#emailapplid').val(data.email);
//            $('#appldate').val(data.dob);
//            $('#genderapplid').val(data.gender);
//            $('#postaladdrappl').val(data.postal);
//            $('#nhifapplid').val(data.nhif);
//            $('#nssfapplid').val(data.nssf);
//            $('#homecountyapplid').val(data.homecounty);
//            $('#ethnicityapplid').val(data.ethnicity);
//            $('#subgroupapplid').val(data.subgroup);
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

            String requesttype = request.getParameter("rqtype");
            if (requesttype.equalsIgnoreCase("bio")) {
                result = getBio(conn, applicantref);
            }
            if (requesttype.equalsIgnoreCase("aca")) {
                result = getAcademics(conn, applicantref);
            }

            if (requesttype.equalsIgnoreCase("upl")) {
                result = getUploads(conn, applicantref);
            }

            //  result = get(conn);
        }

        //}
        out.write(result);

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    private String getUploads(java.sql.Connection connectDB, String refno) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent List

        String[] columns = new String[]{"fileref", "description", "remarks"};

        String sql = "select fileref,description,remarks \n"
                + " from vacancies.applicantdocs where octet_length(doc) >0 and applicantid=?";

        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, refno);
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

        System.err.println("I am here files " + json);

        JSONArray arr = new JSONArray();
        arr.put(json);                         //JSON Array

        return json;
    }

    private String getAcademics(java.sql.Connection connectDB, String applicantref) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent List

        String[] columns = new String[]{"uniqueid","qualification", "year", "gradepos", "reference"};
//spplicantid,qualification, year, gradepos,referenceid
        String sql = "select uniqueid, qualification,year_,gradepos,referenceid from vacancies.applicant_qualifications  where applicantid='" + applicantref + "'";

        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                HashMap<String, String> child = new HashMap<String, String>();

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

    private String getBio(java.sql.Connection connectDB, String applicantref) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent List

        String[] columns = new String[]{"idtype", "idno", "fname", "sname", "lname", "email", "mobileno", "pin", "dob", "gender", "postal", "nhif", "nssf", "homecounty", "ethnicity", "subgroup"};

        String sql = "select idtype, idno, fname,mname,lname,email,maintel,pin,dob,gender,postaladdr,nhifno,nssfno,homecounty,ethnicity,subgroup from vacancies.applicant_bio where idno='" + applicantref + "'";

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
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
