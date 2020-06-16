/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import com.mtrh.mtportal.sys.ConnectionProperties;
import com.mtrh.mtportal.sys.DBObject;
//import com.mtrh.fleet.communication.CommunicationList;
//import com.mtrh.fleet.communication.EmailFunctions;
//import com.mtrh.fleet.dao.ConnectionProperties;
//import com.mtrh.fleet.dao.DBObject;
import com.mtrh.mtportal.sys.VehicleDao;
import com.sun.imageio.plugins.jpeg.JPEG;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "RequestDataServlet", urlPatterns = {"/RequestDataServlet"})
public class TransportRequest extends HttpServlet {

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
            out.println("<title>Servlet RequestDataServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RequestDataServlet at " + request.getContextPath() + "</h1>");
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
    private String getApprovalLevels(java.sql.Connection connectDB, String rqid) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent List

        // Vector v = new Vector();
        String[] columns = new String[]{"transport", "snradmin", "ticket", "security"};

        String sql = "SELECT  CASE WHEN transportconfirm IS NULL THEN 'pending' WHEN transportconfirm is false then 'rejected' else 'approved' end, \n"
                + "        CASE WHEN approved IS NULL THEN 'pending' WHEN approved is false then 'rejected' else 'approved' end, \n"
                + "        CASE WHEN ticket IS NULL THEN 'pending' WHEN ticket is false then 'rejected' else 'approved' end, \n"
                + "        CASE WHEN securityrelease IS NULL THEN 'pending' WHEN securityrelease is false then 'rejected' else 'approved' end\n"
                + "  FROM fleet.transportrequestmemo WHERE rqid = ?;";
        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, rqid);
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //  processRequest(request, response);

        // response.setContentType("text/plain;charset=UTF-8");
        HttpSession session = request.getSession();
        if (session != null) {
            String rqid = request.getParameter("rqid");
            String transtype = request.getParameter("transtype");

            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");//ConnectionProperties.getConnect2DB();

            if (transtype.equalsIgnoreCase("approvstage")) {

                response.setContentType("application/json");
                PrintWriter out = response.getWriter();

                String json = getApprovalLevels(conn, rqid).replace("[", "").replace("]", "");

                out.write(json);
            } else if (transtype.equalsIgnoreCase("reqinfo")) {
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();

                String json = getReqInfo(conn, rqid).replace("[", "").replace("]", "");;

                System.err.println(json);
                out.write(json);

            } else if (transtype.equalsIgnoreCase("invoice")) {
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();

                String json = getInvInfo(conn, rqid);

                System.err.println(json);
                out.write(json);

            }
        }
    }

    private static String getInvInfo(java.sql.Connection connectDB, String rqid) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent List

        // Vector v = new Vector();
        String[] columns = new String[]{"particulars", "unitcost", "total"};

        String sql = "select units||' * '||requestitem, qty::varchar, sum(debit-credit)::varchar from fleet.transport_rq_invoice WHERE requestid = ?  group by 1,2 Order by 1";
        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, rqid);
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

        System.err.println("Invoice Details " + json);

        JSONArray arr = new JSONArray();
        arr.put(json);                         //JSON Array

        return json;
    }

    private static String getReqInfo(java.sql.Connection connectDB, String rqid) {
        ArrayList<HashMap<String, String>> parentList
                = new ArrayList<HashMap<String, String>>();//Parent List

        // Vector v = new Vector();
        String[] columns = new String[]{"organization", "department", "from", "destination", "vehiclereq", "pax", "tel", "email", "purpose", "driver", "vehicleissued"};

        String sql = "SELECT organization, CASE when length(department)<2 then 'unspecified' else department end, from_, \n"
                + "CASE when length(destination)<3 then 'unspecified' else destination end,\n"
                + "CASE when length(vehicle_requested)<3 then 'unspecified' else vehicle_requested end, \n"
                + "pax_no, telno,email, \n"
                + "CASE when length(purpose||''||remarks)<2 then 'unspecified' else remarks||' '||purpose end, driver, vehicle_allocated  from fleet.transportrequestmemo WHERE rqid =?";
        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, rqid);
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

        response.setContentType("text/plain;charset=UTF-8");

        msg = "";
        String transtype = request.getParameter("transtype");

        System.err.println("Transtype: Transss " + transtype);

        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        String result = "";
        String staffid = "";
        if (session != null) {
            String sessionid = session.getAttribute("sessionid").toString();
            String staffno = session.getAttribute("staffid").toString();

            //session.getAttribute("staffid").toString();
            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");

            Object[] metrics = ConnectionProperties.staffMetrics(staffno, conn);

            String fullname = "";//1
            String idno = "";//2
            String email = "";//3
            String telno = "";//4 
            String department = "";

            for (int i = 0; i < metrics.length; i++) {
                fullname = metrics[0].toString();
                idno = staffno;
                email = metrics[4].toString();
                telno = metrics[3].toString();
                department = metrics[6].toString();

            }

            if (transtype.equalsIgnoreCase("insert")) {

                //Up to here catered for
                String rqtype = request.getParameter("rqtype");//5

                DBObject dbobj = new DBObject();
                String orgname = dbobj.getDBObject(request.getParameter("organization"), "");//6
                String dept = department;//dbobj.getDBObject(request.getParameter("dept"), "");//7
                String from = dbobj.getDBObject(request.getParameter("from"), "");//8
                String destination = dbobj.getDBObject(request.getParameter("destination"), "");//9
                // Object distance = dbobj.getDBObject(request.getParameter("distance"), String.valueOf(0.0));//10
                //Object pax = dbobj.getDBObject(request.getParameter("pax"), String.valueOf(0.0));//11 
                String vehrequested = dbobj.getDBObject(request.getParameter("vehrequested"), "");//12
                String purpose = dbobj.getDBObject(request.getParameter("purpose"), "");//13
                String remarks = dbobj.getDBObject(request.getParameter("purpose"), "");//14

                String departuredate = dbobj.getDBObject(request.getParameter("depdate"), "");//
                String departuretime = dbobj.getDBObject(request.getParameter("deptime"), "");//

                //  String driver = dbobj.getDBObject(request.getParameter("driver"), "");//14
                //String vehicle = dbobj.getDBObject(request.getParameter("vehicle"), "");//14
                //String enteredby = request.getParameter("enteredby");//14 
                Object[] items = new Object[]{fullname, idno, email, telno, rqtype, orgname, dept, from, destination, vehrequested, purpose, remarks, departuredate, departuretime};

                String refno = "MTRH/TR/" + randomAlphaNumeric(7);

                System.err.println(refno);

                insertData(conn, items, refno);
//                System.err.println(driver + "/" + vehicle);
//
//                if (driver.length() > 2 && vehicle.length() > 2) {
//
//                    VehicleDao vdao = new VehicleDao();
//                    vdao.DeployVehicle(conn, vehicle, driver, "", "", refno, "-");
//
//                }

                System.out.println("i passed here");

                response.getWriter().write(refno);
//                if (email.length() > 3) {
//
//                    com.mtrh.fleet.communication.EmailFunctions emf = new EmailFunctions();
//
//                    String fieladdr = System.getProperty("user.dir")
//                            + System.getProperty("file.separator")
//                            + "logo.JPG";
//
//                    emf.SendEmailAlerts(email, "Your request " + refno + " is pending approval", fieladdr, refno, conn);
//                }

            } // }
            if (transtype.equalsIgnoreCase("insertinvoice")) { //dont need this

                System.err.println(transtype + " Here");
                String rqid = request.getParameter("rqid");
                String item = request.getParameter("item");
                String qty = (request.getParameter("qty"));
                String cost = (request.getParameter("cost"));

                System.err.println(rqid + "-" + item + "-" + qty + "-" + cost);

                Object[] items = new Object[]{rqid, item, qty, cost};
                insertInvoice(conn, items);

                response.getWriter().write("Data Inserted Successfully");
            }

            if (transtype.equalsIgnoreCase("fuel")) {

                String refno = "MTRH/FR/" + randomAlphaNumeric(7);

                String cardno = request.getParameter("cardno");
                String driverref = staffno;//request.getParameter("driverref");
                String vehreg = request.getParameter("vehreg");
                String quantity = request.getParameter("quantity");
                String fueltype = request.getParameter("fueltype");
                String amount = request.getParameter("amount");
                 String comments = request.getParameter("comments");

                String mileage = request.getParameter("mileage");

                Object[] items = new Object[]{refno, cardno, driverref, vehreg, quantity, fueltype, amount,comments, mileage};

                insertFuelReq(items, conn);

                response.getWriter().write(refno);

            }

            if (transtype.equalsIgnoreCase("mtce")) {

                //var data = 'regno=' + regno + '&requestedby=' + requestedby + '&purpose=' + purpose + '&mileage=' + mileage + '&transtype=' + transtype;
                String refno = "MTRH/MTR/" + randomAlphaNumeric(7);

                //String cardno = request.getParameter("cardno");
                String driverref = request.getParameter("requestedby");
                String vehreg = request.getParameter("regno");
                String mileage = request.getParameter("mileage");
                //String fueltype = request.getParameter("fueltype");
                //String amount = request.getParameter("amount");
                String comments = request.getParameter("purpose");

                Object[] items = new Object[]{refno, driverref, vehreg, comments, mileage};

                insertMtceReq(items, conn);

                response.getWriter().write(refno);

            }
        }

    }

    private void insertMtceReq(Object[] obj, java.sql.Connection connectDB) {
        String refno = obj[0].toString();
        String driverref = obj[1].toString();
        String vehreg = obj[2].toString();
        String comments = obj[3].toString();

        Double mileage = Double.valueOf(obj[4].toString());

        String sql = "INSERT INTO fleet.transportrequestmemo(\n"
                + "            rqid, rqtype, requestedby, idno, email, telno, organization, \n"
                + "            department, purpose, remarks,  enteredby,vehicle_requested)\n"
                + "    VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try {

            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, refno);
            pst.setObject(2, "MAINTENANCE REQUEST");
            pst.setObject(3, driverref);
            pst.setObject(4, "");
            pst.setObject(5, "");
            pst.setObject(6, "");
            pst.setObject(7, "MTRH");
            pst.setObject(8, "TRANSPORT DEPARTMENT");
            pst.setObject(9, comments);
            pst.setObject(10, comments);
            pst.setObject(11, driverref);
            pst.setObject(12, vehreg);

            //-------------
            pst.executeUpdate();

            VehicleDao vdao = new VehicleDao();

            vdao.setVehicleMileage(connectDB, vdao.getFleetIDByRegNo(vehreg, connectDB), mileage, comments, driverref, refno);

        } catch (SQLException ex) {
            msg = "Error: " + ex.getMessage();
            try {
                connectDB.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(TransportRequest.class.getName()).log(Level.SEVERE, null, ex1);
            }
            ex.printStackTrace();
        }

    }

    private void insertFuelReq(Object[] obj, java.sql.Connection connectDB) {

        String refno = obj[0].toString();
        String cardno = obj[1].toString();
        String driverref = obj[2].toString();

        String veh = obj[3].toString();
        //{Let us get the rest of the driver details}
//        com.mtrh.fleet.sys.UserProfile upf = new com.mtrh.fleet.sys.UserProfile();
        Object[] details = ConnectionProperties.staffMetrics(driverref, connectDB);
        //  upf.UserNameFactory(driverref, connectDB);
        String email = details[4].toString();
        String staffid = details[1].toString();
        String designation = details[2].toString();
        String fullname = details[0].toString();
        String username = details[7].toString();
        String phone = details[3].toString();
        //{Done for now...}
        Double quantity = Double.valueOf(obj[4].toString());
        Double amount = Double.valueOf(obj[6].toString());
        Double debit = quantity * amount;
        String comments = obj[7].toString();

        Double mileage = Double.valueOf(obj[8].toString());
        String fueltype = obj[5].toString();

        String sql = "INSERT INTO fleet.transportrequestmemo(\n"
                + "            rqid, rqtype, requestedby, idno, email, telno, organization, \n"
                + "            department, purpose, remarks,  enteredby, vehicle_requested)\n"
                + "    VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?);";

        try {

            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, refno);
            pst.setObject(2, "FUEL REQUEST");
            pst.setObject(3, fullname);
            pst.setObject(4, staffid);
            pst.setObject(5, email);
            pst.setObject(6, phone);
            pst.setObject(7, "MTRH");
            pst.setObject(8, "TRANSPORT DEPARTMENT");
            pst.setObject(9, comments);
            pst.setObject(10, comments);
            pst.setObject(11, username);
            pst.setObject(12, veh);

            //-------------
            pst.executeUpdate();

            msg = refno.toString();

            sql = "INSERT INTO fleet.transport_rq_invoice(requestid, requestitem, units, qty, debit) VALUES (?, ?, ?, ?, ?);";

            // connectDB.setAutoCommit(false);
            pst = connectDB.prepareStatement(sql);
            pst.setObject(1, refno);
            pst.setObject(2, fueltype + "/" + cardno);
            pst.setObject(3, quantity);//this column is wrngly labelled at the backend
            pst.setObject(4, amount);
            pst.setObject(5, quantity * amount);

            pst.executeUpdate();
//            EmailFunctions emf = new EmailFunctions();

            String fleetid = new fleet.VehicleDao().getFleetIDByRegNo(veh, connectDB);
            
            new fleet.VehicleDao().setVehicleMileage(connectDB, fleetid, Double.valueOf(mileage), "FUEL REQUEST", username, refno);

            //   connectDB.commit();
            msg = "REQUEST POSTED SUCCESSFULLY!";
        } catch (SQLException ex) {
            msg = "Error: " + ex.getMessage();
            try {
                connectDB.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(TransportRequest.class.getName()).log(Level.SEVERE, null, ex1);
            }
            ex.printStackTrace();
        }
    }

    private void insertInvoice(java.sql.Connection connectDB, Object[] obj) {
        String sql = "INSERT INTO fleet.transport_rq_invoice(requestid, requestitem, units, qty, debit) VALUES (?, ?, ?, ?, ?);";

        try {
            // connectDB.setAutoCommit(false);
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, obj[0]);
            pst.setObject(2, obj[1]);
            pst.setObject(3, Double.valueOf(obj[2].toString()));
            pst.setObject(4, Double.valueOf(obj[3].toString()));
            pst.setObject(5, Double.valueOf(obj[2].toString()) * Double.valueOf(obj[3].toString()));

            pst.executeUpdate();
            //   connectDB.commit();
            msg = "REQUEST POSTED SUCCESSFULLY!";
        } catch (SQLException ex) {
            msg = "Error: " + ex.getMessage();
            try {
                connectDB.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(TransportRequest.class.getName()).log(Level.SEVERE, null, ex1);
            }
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

    private void insertData(java.sql.Connection connectDB, Object[] obj, String refno) {
        String sql = "INSERT INTO fleet.transportrequestmemo(\n"
                + "            rqid, rqtype, requestedby, idno, email, telno, organization, \n"
                + "            department, from_, destination,  vehicle_requested, \n"
                + "            purpose, remarks, enteredby, planned_date, planned_time)\n"
                + "    VALUES (?, ?, ?, ?, ?, ?, ?, \n"
                + "            ?, ?, ?, \n"
                + "            ?, ?, ?, ?, ?::date,?);";

        try {

            if (obj[9].equals("")) {
                obj[9] = 0.0;
            }
            if (obj[10].equals("")) {
                obj[10] = 0.0;
            }

            //{fullname, idno, email, telno, rqtype, orgname, dept, from, destination,  vehrequested, purpose, remarks, departuredate, departuretime};
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, refno);
            pst.setObject(2, obj[4].toString().toUpperCase());
            pst.setObject(3, obj[0]);
            pst.setObject(4, obj[1]);
            pst.setObject(5, obj[2]);
            pst.setObject(6, obj[3]);
            pst.setObject(7, obj[5]);
            pst.setObject(8, obj[6]);
            pst.setObject(9, obj[7]);
            pst.setObject(10, obj[8]);
            pst.setObject(11, obj[9]);
            pst.setObject(12, obj[10]);
            //-------------
            pst.setObject(13, obj[10]);

            pst.setObject(14, obj[3]);

            pst.setObject(15, obj[12]);
            pst.setObject(16, obj[13]);

            pst.executeUpdate();

            //Object[] mailing = new Object[]{obj[2].toString()};
            msg = refno.toString();
        } catch (SQLException ex) {
            msg = "Error: " + ex.getMessage();
            try {
                connectDB.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(TransportRequest.class.getName()).log(Level.SEVERE, null, ex1);
            }
            ex.printStackTrace();
        }

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
