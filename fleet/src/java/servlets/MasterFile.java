/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import com.mtrh.fleet.dao.ConnectionProperties;
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
import org.json.JSONArray;

/**
 *
 * @author owner
 */
@WebServlet(name = "MasterFile", urlPatterns = {"/MasterFile"})
public class MasterFile extends HttpServlet {

    public static String msg;

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
            out.println("<title>Servlet MasterFile</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MasterFile at " + request.getContextPath() + "</h1>");
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
        String userid = request.getParameter("userid");

        Connection conn = ConnectionProperties.getConnect2DB();

        String json = getMasterRecordsByID(conn, userid).replace("[", "").replace("]", "");

        out.write(json);
    }

    private String getMasterRecordsByID(Connection connectDB, String userref) {
        String sql = "select employee_no, first_name, department, section, email_address, case when ? IN (select staffid FROM secure_password) then 'active' \n"
                + "else 'inactive' end as emp_stat\n"
                + "from fsmaster where employee_no =?";
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent List

        // Vector v = new Vector();
        String[] columns = new String[]{"empno", "name", "department", "section", "email", "status"};

        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, userref);
            pst.setObject(2, userref);
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

        System.err.println("I am here " + json);

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
        //processRequest(request, response);
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        String transtype = request.getParameter("transtype"), login = request.getParameter("login"), email = request.getParameter("email");

        if (transtype.equalsIgnoreCase("chpwd") ) { //Offline change of password
            
            String password = request.getParameter("password");
            Connection conn = new LoginController().connect("postgres", "sequence");

            changePassword(conn, login, password, email);

        } else {
            String empno = request.getParameter("empno"),
                    telno = request.getParameter("telno"), department = request.getParameter("department"), section = request.getParameter("section"),
                    designation = request.getParameter("designation"), fullname = request.getParameter("fullname");

            Object[] obj = new Object[]{empno, login, email, telno, department, section, designation, transtype, fullname};

            Connection conn = ConnectionProperties.getConnect2DB();

            //     
            saveOrUpdateData(obj, conn);
        }

        out.write(msg);
    }
    
    public static void changePassword(Connection connectDB, String login,String password, String email){
        String  sql2 = "ALTER USER " + login + " with password '" + password + "' ";
        
        try {
            PreparedStatement ps = connectDB.prepareStatement(sql2);
            ps.executeUpdate();
            msg ="SUCCESS";
            com.mtrh.fleet.communication.EmailFunctions em = new com.mtrh.fleet.communication.EmailFunctions();

            String text = "User Account Updated Successfully:\n Username: " + login+ "  Password: " + password + "\n";

            em.SendPlainEmailAlerts(email, text, login, connectDB);
            
        } catch (SQLException ex) {
            msg =ex.getMessage();
            ex.printStackTrace();
        }
    }

    public static void saveOrUpdateData(Object[] obj, Connection connectDB) {

        String transtype = obj[7].toString();
        String password = "";
        String sql1 = "", sql2 = "", sql3 = "UPDATE fsmaster SET department =? ,section =?, email_address= ? WHERE employee_no = ?";
        password = randomAlphaNumeric(7);
        if (transtype.contains("Create")) {
            sql1 = "INSERT INTO secure_password (login_name, fullname, staffid, email, phone, designation, department, cgn) \n"
                    + "	VALUES (?,?,?,?,?,?,?,?)";
            sql2 = "CREATE USER " + obj[1] + " with password '" + password + "' superuser login";

            if (obj[6].toString().equalsIgnoreCase("GENERAL USER")) {
                sql2 = "CREATE USER " + obj[1] + " with password '" + password + "' superuser nologin";
            }

            //   Object[] obj = new Object[]{empno, login, email, telno, department, section, designation, transtype, fullname}; 
            try {
                connectDB.setAutoCommit(false);
                PreparedStatement ps = connectDB.prepareStatement(sql1);
                ps.setObject(1, obj[1].toString());
                ps.setObject(2, obj[8].toString());
                ps.setObject(3, obj[0].toString());
                ps.setObject(4, obj[2].toString());
                ps.setObject(5, obj[3].toString());
                ps.setObject(6, obj[6].toString());
                ps.setObject(7, obj[4].toString());
                ps.setObject(8, obj[2].toString());
                ps.executeUpdate();

                ps = connectDB.prepareStatement(sql2);
                ps.executeUpdate();

                ps = connectDB.prepareStatement(sql3);
                ps.setObject(1, obj[6].toString());
                ps.setObject(2, obj[5].toString());
                ps.setObject(3, obj[2].toString());
                ps.setObject(4, obj[0].toString());
                ps.executeUpdate();
                connectDB.commit();

                msg = "SUCCESS";

            } catch (SQLException ex) {
                msg = ex.getMessage();
                try {
                    connectDB.rollback();
                } catch (SQLException ex1) {
                    ex1.printStackTrace();
                }

                ex.printStackTrace();

            }

        } else {
            sql1 = "UPDATE secure_password SET  email=? , phone = ?, designation =?, department =? WHERE staffid = ? ";

//   Object[] obj = new Object[]{empno, login, email, telno, department, section, designation, transtype, fullname}; 
            try {
                connectDB.setAutoCommit(false);
                PreparedStatement ps = connectDB.prepareStatement(sql1);

                ps.setObject(1, obj[2]);
                ps.setObject(2, obj[3]);
                ps.setObject(3, obj[6]);
                ps.setObject(4, obj[4]);
                ps.setObject(5, obj[0]);
                ps.executeUpdate();

                if (obj[6].toString().equalsIgnoreCase("GENERAL USER")) {
                    sql2 = "ALTER USER " + obj[1] + " WITH NOLOGIN";
                    ps = connectDB.prepareStatement(sql2);
                    ps.executeUpdate();
                }

                ps = connectDB.prepareStatement(sql3);
                ps.setObject(1, obj[6].toString());
                ps.setObject(2, obj[5].toString());
                ps.setObject(3, obj[2].toString());
                ps.setObject(4, obj[0].toString());
                ps.executeUpdate();
                connectDB.commit();

            } catch (SQLException ex) {
                msg = ex.getMessage();
                try {
                    connectDB.rollback();
                } catch (SQLException ex1) {
                    ex1.printStackTrace();
                }

                ex.printStackTrace();

            }
        }
        //   Object[] obj = new Object[]{empno, login, email, telno, department, section, designation, transtype, fullname}; 
        if (transtype != "GENERAL USER") {
            com.mtrh.fleet.communication.EmailFunctions em = new com.mtrh.fleet.communication.EmailFunctions();

            String text = "User Account Updated Successfully:\nDepartment: " + obj[4] + "\nUsername: " + obj[1] + "  Password: " + password + "\n";

            em.SendPlainEmailAlerts(obj[2].toString(), text, obj[0].toString(), connectDB);
        }
        System.err.println(sql1
                + "" + sql2 + "" + sql3);

    }

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
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
