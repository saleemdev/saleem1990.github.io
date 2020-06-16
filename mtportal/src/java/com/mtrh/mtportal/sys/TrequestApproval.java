/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtrh.mtportal.sys;

//import com.mtrh.fleet.communication.CommunicationList;
//import com.mtrh.fleet.communication.EmailFunctions;
//import com.mtrh.fleet.dao.ConnectionProperties;
import fleet.VehicleDao;
import com.mtrh.mtportal.sys.PDFPAth;
//import com.mtrh.fleet.reports.VehiclesPDF;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
@WebServlet(name = "TrequestApproval", urlPatterns = {"/TrequestApproval"})
public class TrequestApproval extends HttpServlet {

    private String responseText;

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
            out.println("<title>Servlet RequestTransactions</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RequestTransactions at " + request.getContextPath() + "</h1>");
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
    private void updateRQ(String rqid, String transtype, String usr, java.sql.Connection connectDB) {
        String sql = "";

        String sql2 = "INSERT INTO fleet.authorizations(refid,stage )values(?,?)";

        if (transtype.equalsIgnoreCase("APPROVE TRANSPORT")) {
// var request = 'transtype=' + transtype + '&rqid=' + reqid + '&vehreq=' + vehreq+'&vehissued='+vehissued+'&driver='+driver+'&officer='+officer;
            sql = "UPDATE fleet.transportrequestmemo SET transportconfirm = true WHERE rqid = ?";
            responseText = "TRANSPORT APPROVED";

        } else if (transtype.equalsIgnoreCase("AUTHORIZE REQUEST")) {
            sql = "UPDATE fleet.transportrequestmemo SET approved = true WHERE rqid = ?";
            responseText = "REQUEST AUTHORIZED";

            if (rqid.contains("/FR/")) {
                String vehicle = getVehicleByRequestID(rqid, connectDB);
                String cardno = getCardNoByRefNo(rqid, connectDB);
                Object[] fuelinv = getFuelInv(rqid, connectDB);
                
                //requestitem, units,  qty,   qty*units::numeric as total, cash_words((qty*units::numeric)::money)
                Double totalAmt = Double.valueOf(fuelinv[3].toString());

                //requestitem, units,qty,qty*units::numeric as total, cash_words((qty*units::numeric)::money)
                new CardTransactions().setAmount(cardno, "Fuel Request Approval " + rqid, "credit", usr, totalAmt, connectDB);
            }

        } else if (transtype.equalsIgnoreCase("PRINT TICKET")) {
            sql = "UPDATE fleet.transportrequestmemo SET ticket = true, journey_start = now()::timestamp(0) WHERE rqid = ?";
            //PRINT PATH TO PDF

//            com.mtrh.fleet.reports.RequestPDF pdf2 = new com.mtrh.fleet.reports.RequestPDF();
//
            //          pdf2.RequestPDF(ConnectionProperties.getConnect2DB(), rqid, "TICKET");
            // VehiclesPDF pdf = new VehiclesPDF();
            //pdf.VehiclesPDF(ConnectionProperties.getConnect2DB());
            //pdf.generatePdf();
            responseText = PDFPAth.getPath2pdf();

        } else if (transtype.equalsIgnoreCase("END TRIP")) {

            sql = "UPDATE fleet.transportrequestmemo SET  journey_end = now()::timestamp(0) WHERE rqid = ?";
        }

        if (transtype.equalsIgnoreCase("DECLINE TRANSPORT")) {
            sql = "UPDATE fleet.transportrequestmemo SET transportconfirm = false, cancelled=true WHERE rqid = ?";

            responseText = "TRANSPORT REQUEST DECLINED";

        } else if (transtype.equalsIgnoreCase("DECLINE REQUEST")) {
            sql = "UPDATE fleet.transportrequestmemo SET approved = false, cancelled = true WHERE rqid = ?";
            responseText = "REQUEST DECLINED";

        }
        //   System.err.println(transtype + ":, " + sql);

        try {
            PreparedStatement pst = connectDB.prepareCall(sql);
            pst.setObject(1, rqid);
            pst.executeUpdate();
            //INSERT INTO fleet.authorizations(refid,stage,authorized_by )values(?,?,?)
            pst = connectDB.prepareStatement(sql2);
            pst.setObject(1, rqid);
            pst.setObject(2, transtype);
            pst.executeUpdate();
            
            
  //          String requester = 

//            Object[] mail = new Object[]{ConnectionProperties.getEmail()};
//            new CommunicationList().setCommunicationList(rqid, mail, connectDB);
//
//            Object[] addresses = new CommunicationList().getMailingList(rqid, connectDB);
//
//            for (int i = 0; i < addresses.length; i++) {
//                System.out.println(addresses[i]);
//                EmailFunctions emf = new EmailFunctions();
//
//                String fieladdr = System.getProperty("user.dir")
//                        + System.getProperty("file.separator")
//                        + "logo.JPG";
//                if (responseText.endsWith("pdf")) {
//                    fieladdr = responseText;
//                    responseText = "TICKET";
//                }
//
//               // emf.SendEmailAlerts(addresses[i].toString(), "Request " + rqid + " status:" + responseText, fieladdr, rqid, connectDB);
//                
//                emf.SendPlainEmailAlerts(addresses[i].toString(),  "Request " + rqid + " status:" + responseText, rqid, connectDB);
//            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            responseText = "ERROR IN TRANSACTIONS";
        }

    }

    private Object[] getFuelInv(String refno, Connection connectDB) {

        com.mtrh.mtportal.sys.CardTransactions card = new com.mtrh.mtportal.sys.CardTransactions();

        return card.getFleetRInvoice(refno, connectDB);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // processRequest(request, response);
        HttpSession session = request.getSession();
        if (session != null) {
            String requestid = request.getParameter("rqid");
            String transtype = request.getParameter("transtype");
            // var request = 'transtype=' + transtype + '&rqid=' + reqid + '&vehreq=' + vehreq+'&vehissued='+vehissued+'&driver='+driver+'&officer='+officer;
            Object vehicle = request.getParameter("vehissued");
            Object driver = request.getParameter("driver");
            Object officer = request.getParameter("officer");

            Object section = request.getParameter("officer");

            String staffid = session.getAttribute("staffid").toString();

            responseText = "";

            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");//ConnectionProperties.getConnect2DB();

            Object[] staffdet = ConnectionProperties.staffMetrics(staffid, conn);

            updateRQ(requestid, transtype, staffdet[1].toString(), conn);

            if (requestid.contains("/TR/") && transtype.equalsIgnoreCase("APPROVE TRANSPORT")) {//DEPLOY VEHICLE
                VehicleDao vdao = new VehicleDao();//
                vdao.DeployVehicle(conn, vehicle, driver, officer, section, requestid, transtype);
                
                String driverpf = driver.toString().split("/")[1];
                
                String drivername = driver.toString().split("/")[0];
                
                String phone = UserPhone(driverpf, conn);
                
                
                new SendSms(phone, "Dear "+drivername+"\n you have been assigned a trip "+requestid+" on fleet "+vehicle+"\nPlease check your email for a e-ticket and fleet manifest", requestid, conn);
            }
            response.setContentType("text/plain");
            PrintWriter out = response.getWriter();

            out.write(responseText);

        }
    }
    
    private String getCardNoByRefNo(String regno, Connection connectDB) {
        String returnvalue = "0";
        String sql = "SELECT requestitem FROM fleet.transport_rq_invoice WHERE upper(requestid) = ? limit 1;";

        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, regno.trim().toUpperCase());
            ResultSet rset = pst.executeQuery();

            while (rset.next()) {

                returnvalue = String.valueOf(rset.getObject(1).toString().split("/")[1]);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return returnvalue;
    }

    private String getCardNoByVehreg(String vehreg, Connection connectDB) {
        String returnvalue = "0";
        String sql = "SELECT cardno FROM fleet.vehicle_register WHERE upper(regno) = ? ";

        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, vehreg.trim().toUpperCase());
            ResultSet rset = pst.executeQuery();

            while (rset.next()) {

                returnvalue = String.valueOf(rset.getObject(1));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return returnvalue;
    }
    
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

    private String getVehicleByRequestID(String rqid, Connection connectDB) {
        String returnvalue = "0";
        String sql = "SELECT vehicle_requested FROM fleet.transportrequestmemo WHERE rqid = ?";

        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, rqid.trim().toUpperCase());
            ResultSet rset = pst.executeQuery();

            while (rset.next()) {

                returnvalue = String.valueOf(rset.getObject(1));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return returnvalue;
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

        String rcptno = request.getParameter("receiptno");
        if (exists(rcptno)) {

            out.write("Verified");
        } else {
            out.write("UnVerified");
        }

    }

    private Boolean exists(String rcpt) {
        String[] rcpts = new String[]{"64", "45", "27"};//an array of receipts
        Boolean isHere = false;
        for (int i = 0; i < rcpts.length; i++) {
            System.err.println(rcpt + " vs " + rcpts[i]);
            if (rcpt.equalsIgnoreCase(rcpts[i])) {
                isHere = true;
            }
        }

        return isHere;
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
