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
@WebServlet(name = "Vacancies", urlPatterns = {"/Vacancies"})
public class Vacancies extends HttpServlet {

    private Object dbServerIp;
    private Object dbPort;
    private Object activeDatabase;
    private String msg;

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
            out.println("<title>Servlet Vacancies</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Vacancies at " + request.getContextPath() + "</h1>");
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
        //   processRequest(request, response);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String result = "";
        
        
        if(session!=null){
            session.invalidate();//Kill any existing session and build a new ons below
        }

        //String refno = request.getParameter("refno");
        //request.getParameter("leaveyear");
//        if (session != null ) {
//            String sessionid = session.getAttribute("sessionid").toString();
//            String staffid = session.getAttribute("staffid").toString();
//
//            //staffid
//            //  String loginid = session.getAttribute("username").toString();
//            System.err.println("Matching key: " + sessionid);
//
//            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");
        //   } else {
        Connection conn = this.connect("postgres", "sequence");
        String key = randomAlphaNumeric(10);
        session = request.getSession();
        session.setMaxInactiveInterval(10 * 60);
        session.setAttribute("username", "");
        session.setAttribute("staffid", "");
        session.setAttribute("sessionid", key);
        session.setAttribute("connection", conn);
        session.setAttribute("validated", "no");
 
        result = getMessages(conn);

        //}
        out.write(result);
    }

    private String getMessages(java.sql.Connection connectDB) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent List

        String[] columns = new String[]{"vacancyid", "category", "designation", "jobgroup", "positions", "postedon", "deadline"};

        String sql = "select vacancyid, category, designation, jobgroup, positions,humanReadableDate(posted_on::varchar), humanReadableDate(deadline::varchar) from vacancies.vacancies where available is true;";

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

    public java.sql.Connection connect(String user, String password) {
        Connection connection = null;

        try {

            java.lang.Class.forName("org.postgresql.Driver");

        } catch (java.lang.ClassNotFoundException cnf) {

            cnf.printStackTrace();

        }
        try {

            if (dbServerIp == null) {
                // dbServerIp = "localhost"jj;
                dbServerIp = servlet.PropertiesClass.getpropValue("dbServerIpAdd").toString();
            }

            if (dbPort == null) {
                dbPort = servlet.PropertiesClass.getpropValue("dbPort").toString();
            }

            if (activeDatabase == null) {
                activeDatabase = servlet.PropertiesClass.getpropValue("activeDatabase").toString();
            }
            //  System.out.println("jdbc:postgresql://" + this.dbServerIp + ":" + dbPort + "/" + activeDatabase + " " + userName + " " + passWord);
            connection = java.sql.DriverManager.getConnection("jdbc:postgresql://" + this.dbServerIp + ":" + dbPort + "/" + activeDatabase, user, password);

            if (connection != null) {
                System.err.println("connected");
            }
        } catch (java.sql.SQLException sqlExec) {

            msg = sqlExec.getMessage().toString();
            System.err.println(System.getProperty("user.dir"));

            sqlExec.printStackTrace();
            //   this.setVisible(true);

        }
        return connection;
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

        response.setContentType("application/json");

        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String result = "";

        //request.getParameter("leaveyear");
        if (session != null) {
            String sessionid = session.getAttribute("sessionid").toString();
            //String staffid = session.getAttribute("staffid").toString();
            String sessionstatus = session.getAttribute("validated").toString();

            Connection conn = (Connection) session.getAttribute("connection");

            result = getMessages(conn);

        }
            

        //}
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
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}
