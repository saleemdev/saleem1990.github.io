/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mtrh.fleet.dao.DBConnectionDao;
import com.mtrh.fleet.entity.DashBoardMetricsEntity;
import com.mtrh.fleet.dao.ConnectionProperties;
import com.mtrh.fleet.dao.DashboardMetricsDao;
import static com.mtrh.fleet.dao.DashboardMetricsDao.getAndAndPrepareActivitiesJSON;
import static com.mtrh.fleet.dao.DashboardMetricsDao.getAndSetDashmetrics;
import com.mtrh.fleet.entity.activityBeans;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author owner
 */
@WebServlet(name = "PopulatePage", urlPatterns = {"/PopulatePage"})
public class PopulatePage extends HttpServlet {

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
            out.println("<title>Servlet PopulatePage</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PopulatePage at " + request.getContextPath() + "</h1>");
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
    private Object[] getUserInformation(Object username, java.sql.Connection connectDB) {

        Vector v = new Vector();
        String sql = "select  staffid, fullname, designation, email, phone, login_name from secure_password where staffid = '"+username+"' OR login_name = '"+username+"';";
        
        System.err.println(sql+"\n"+username);
        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
           // pst.setObject(1, username);
            //pst.setObject(2, username);
            //  pst.setObject(2, fleetno);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                v.add(rset.getObject(1));
                v.add(rset.getObject(2));
                v.add(rset.getObject(3));
                v.add(rset.getObject(4));
                v.add(rset.getObject(5));
                v.add(rset.getObject(6));
            }

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }
        
        Object[] result = v.toArray();
        System.err.println(result[0]+"/"+result[1]+"/"+result[2]+"/"+result[3]+"/"+result[4]+"/"+result[5]);
        return result;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // processRequest(request, response);

        HttpSession session = request.getSession();

        if (session != null) {
            response.setContentType("application/json");

            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");//ConnectionProperties.getConnect2DB();
            Object userName = session.getAttribute("username");//ConnectionProperties.getConnect2DB();
            getAndSetDashmetrics(conn);

            Object[] userdet = getUserInformation(userName, conn);
//
//            Object[] prop = new Object[]{ConnectionProperties.getUsername(), ConnectionProperties.getUserlogin(), ConnectionProperties.getPhone(), ConnectionProperties.getEmail(), ConnectionProperties.getDesignation(),
//                DashBoardMetricsEntity.getActive_drivers(), DashBoardMetricsEntity.getVehicles(), DashBoardMetricsEntity.getRequisitions(), DashBoardMetricsEntity.getWorkorders()};
            
             Object[] prop = new Object[]{userdet[1], userdet[0], userdet[4], userdet[5], userdet[2],
                DashBoardMetricsEntity.getActive_drivers(), DashBoardMetricsEntity.getVehicles(), DashBoardMetricsEntity.getRequisitions(), DashBoardMetricsEntity.getWorkorders()};
            // JSONArray arr = new JSONArray();
            JSONObject jsonObj = new JSONObject();
            for (int i = 0; i < prop.length; i++) {
                System.err.println(prop[i]);

            }

            try {
                jsonObj.put("usr", prop[0]);
                jsonObj.put("login", prop[1]);
                jsonObj.put("phone", prop[2]);
                jsonObj.put("email", prop[3]);
                jsonObj.put("designation", prop[4]);
                //dashboardMetrics
                jsonObj.put("drivers", prop[5]);
                jsonObj.put("vehicles", prop[6]);
                jsonObj.put("reqs", prop[7]);
                jsonObj.put("tickets", prop[8]);

            } catch (JSONException ex) {
                Logger.getLogger(PopulatePage.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.err.println(jsonObj.toString());
            response.getWriter().write(jsonObj.toString());

        }
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

        String userid = request.getParameter("user");

        //System.err.println(userid);
        DashboardMetricsDao dao1 = new DashboardMetricsDao();

        dao1.getAndSetDashmetrics(ConnectionProperties.getConnect2DB());
        dao1.getAndAndPrepareActivitiesJSON(ConnectionProperties.getConnect2DB(), userid);

        JSONObject table = new JSONObject();

        JSONArray jarray = new JSONArray();

        //Populate the data now
        //assign the data variables declared above
        table = activityBeans.getDataJSON();
        jarray = activityBeans.getDataJARRAY();

        System.out.println("Json Object: " + table.toString());
        System.out.println("Json Array: " + jarray.toString());

        response.getWriter().write(table.toString());

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
