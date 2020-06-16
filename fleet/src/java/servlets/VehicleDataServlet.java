/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.mtrh.fleet.dao.ConnectionProperties;
import com.mtrh.fleet.dao.VehicleDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author owner
 */
@WebServlet(name = "VehicleDataServlet", urlPatterns = {"/VehicleDataServlet"})
public class VehicleDataServlet extends HttpServlet {

    private boolean status;

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
            out.println("<title>Servlet VehicleDataServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VehicleDataServlet at " + request.getContextPath() + "</h1>");
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
    private void deleteFile(java.sql.Connection connectDB, String fleetid) {

        // String sql = "DELETE FROM fleet.vehicle_register WHERE fleetno = ?";
        String sql = "UPDATE fleet.vehicle_register SET vehicle_status = 'Decommisioned' WHERE fleetno = ?";

        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, fleetid);
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    private void updateFile(String fleetid, Object[] obj, java.sql.Connection connectDB) {
        String sql = "UPDATE fleet.vehicle_register\n"
                + "   SET  regno=?, regdate=?::date, make=?, model=?, \n"
                + "       classification=?, yom=?,  engine_cc=?,  fuel_used=?,\n"
                + "          chassis_no=?, engine_no=?, driver =?, section=?, authority=?, vehicle_status=?\n"
                + " WHERE fleetno=?;";
        // Object[] data2update = new Object[]{regno, date, make, model, yom, enginecc, fueltype, mileage, chassisno, engineno, classification,driver, section, authority};

        System.err.println(sql);

        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, obj[0].toString().toUpperCase());
            pst.setObject(2, obj[1]);
            pst.setObject(3, obj[2]);
            pst.setObject(4, obj[3]);
            pst.setObject(5, obj[10]);
            pst.setObject(6, obj[4]);
            pst.setObject(7, obj[5]);
            pst.setObject(8, obj[6]);
            pst.setObject(9, obj[8]);
            pst.setObject(10, obj[9]);

            pst.setObject(11, obj[11]);
            pst.setObject(12, obj[12]);
            pst.setObject(13, obj[13]);

            pst.setObject(14, obj[14]);

            pst.setObject(15, fleetid);

            pst.executeUpdate();

            //Update Mileage
            VehicleDao vdao = new VehicleDao();

            Double vehmileage = vdao.getVehicleMileage(fleetid, connectDB);
            Double parsedmileage = Double.valueOf(obj[7].toString());

            if (vehmileage != parsedmileage) {
                vdao.setVehicleMileage(connectDB, fleetid, parsedmileage, "FLEET UPDATE", ConnectionProperties.getUsername(),fleetid);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // processRequest(request, response);
        HttpSession session = request.getSession();
        if (session != null) {
            response.setContentType("text/plain");
            String transaction = request.getParameter("transtype");
            String fleetid = request.getParameter("fleetid");
            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");//ConnectionProperties.getConnect2DB();
            if (transaction.equalsIgnoreCase("delete")) {
                deleteFile(conn, fleetid);
                response.getWriter().write("Deleted");
            } else {

                PrintWriter out = response.getWriter();

                String regno = request.getParameter("regno");
                Object date = request.getParameter("date");
                String make = request.getParameter("make");
                String model = request.getParameter("model");
                String yom = request.getParameter("yom");
                String enginecc = request.getParameter("enginecc");
                String fueltype = request.getParameter("fueltype");
                Object mileage = request.getParameter("mileage");
                String chassisno = request.getParameter("chassisno");
                String engineno = request.getParameter("engineno");
                String classification = request.getParameter("classification");

                String driver = request.getParameter("driver");
                String section = request.getParameter("section");
                String authority = request.getParameter("authority");

                String status = request.getParameter("status");

                // String fleetid = request.getParameter("fleetno");
                Object[] data2update = new Object[]{regno, date, make, model, yom, enginecc, fueltype, mileage, chassisno, engineno, classification, driver, section, authority, status};

                updateFile(fleetid, data2update, conn);

                out.write("Updated");

            }
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
        HttpSession session = request.getSession();
        if (session != null) {
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            String responseString = "";
            PrintWriter out = response.getWriter();

            String regno = request.getParameter("regno");
            Object date = request.getParameter("date");
            String make = request.getParameter("make");
            String model = request.getParameter("model");
            String yom = request.getParameter("yom");
            String enginecc = request.getParameter("enginecc");
            String fueltype = request.getParameter("fueltype");
            Object mileage = request.getParameter("mileage");
            String chassisno = request.getParameter("chassisno");
            String engineno = request.getParameter("engineno");
            String classification = request.getParameter("classification");

            // Date date1;
            //  date1 = new Date((long) date);
            Object[] data2Insert = new Object[]{regno, date, make, model, yom, enginecc, fueltype, mileage, chassisno, engineno, classification};
            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");//ConnectionProperties.getConnect2DB();

            //  status = true;
            InsertData(data2Insert, conn);

            if (status = true) {
                responseString = "DATA INSERT SUCCESSFUL";
            } else {
                responseString = "CHECK YOUR ENTRIES PLEASE";
            }
            // System.err.println("SQL Date "+date1);
            System.err.println(regno + "-" + date);

            out.write(responseString);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    private void InsertData(Object[] obj, java.sql.Connection connectDB) {

        String sql = "INSERT INTO fleet.vehicle_register(\n"
                + "            fleetno, regno, model, yom, chassis_no, engine_no,  \n"
                + "            fuel_used, engine_cc, vehicle_status, make, regdate, classification)\n"
                + "    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?::date, ?);";

        String fleetid = randomAlphaNumeric(7);
        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, fleetid);
            pst.setObject(2, obj[0].toString().toUpperCase());
            pst.setObject(3, obj[3]);
            pst.setObject(4, obj[4]);
            pst.setObject(5, obj[8]);
            pst.setObject(6, obj[9]);
            pst.setObject(7, obj[6]);
            pst.setObject(8, obj[5]);
            pst.setObject(9, "Running");
            pst.setObject(10, obj[2]);
            pst.setObject(11, obj[1]);
            pst.setObject(12, obj[10]);
            pst.executeUpdate();

            //Insert Mileage
            VehicleDao vdao = new VehicleDao();

            vdao.setVehicleMileage(connectDB, fleetid, Double.valueOf(obj[7].toString()), "NEW ENTRY", ConnectionProperties.getUsername(),fleetid);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

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

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
