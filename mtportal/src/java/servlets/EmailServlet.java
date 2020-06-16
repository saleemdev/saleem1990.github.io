/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import static com.mtrh.mtportal.sys.EmailClass.SendMailUsingSMPT;
import com.mtrh.mtportal.sys.SendSms;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
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
@WebServlet(name = "EmailServlet", urlPatterns = {"/EmailServlet"})
public class EmailServlet extends HttpServlet {

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
            out.println("<title>Servlet EmailServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EmailServlet at " + request.getContextPath() + "</h1>");
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
        //  processRequest(request, response);
        //var request = "refid=" + refno + "&staffid=" + items + "&cc=" + cc + "&bcc=" + bcc + "&message=" + message;
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String result = "";

        String pfno = request.getParameter("staffid");
        String ccpf = request.getParameter("cc");
        String bccpf = request.getParameter("bcc");
        String message = request.getParameter("message");
        String refid = request.getParameter("refid");

        String msg = "";
        String[] tomails = new String[]{};

        if (session != null) {
            String sessionid = session.getAttribute("sessionid").toString();
            String staffid = session.getAttribute("staffid").toString();
            System.err.println("Matching key: " + sessionid);

            java.sql.Connection conn = (java.sql.Connection) session.getAttribute("connection");

            // //
            try {
                
                InternetAddress[] myBccList, myCcList;
                //BCC list
                if (bccpf.length() > 3) { //If the BCC list is empty parse an empty string
                     myBccList = InternetAddress.parse(getConcatEmails(bccpf, conn));
                    System.err.println("BCC List" + bccpf);
                } else {
                    myBccList = InternetAddress.parse("");
                    System.err.println("BCC List is empty");
                }
                //CC List
                if (ccpf.length() > 3) { //If the CC list is empty, an error comes up
                     myCcList = InternetAddress.parse(getConcatEmails(ccpf, conn));
                    System.err.println("CC List" + bccpf);
                } else {
                     myCcList = InternetAddress.parse("");
                    System.err.println("CC List is empty");
                }
                InternetAddress[] to = InternetAddress.parse(getConcatEmails(pfno, conn));
                System.err.println("TO List" + bccpf);

                SendMailUsingSMPT(to, message, refid, conn, myCcList, myBccList);
            } catch (AddressException ex) {
                ex.printStackTrace();
            }
            //  //

            String email = UserEmailByID(pfno, conn);

            String phone = UserPhone(pfno, conn);

            String name = FullNameByID(staffid, conn);

            String recipientName = FullNameByID(pfno, conn);

            //  new EmailClass().SendPlainEmailAlerts(email, name+" says:\n"+message, refid, conn);
            // new SendSms(phone, "RefID " + refid + "\n" + name + " says " + message, refid, conn);
            msg = "Message Sent Successfully to " + recipientName;
            //  }
        }

        out.write(msg);

    }

    public Boolean isAGroupID(String str, Connection conn) {
        Boolean stat = false;
        String sql = "select case when upper('" + str + "') in (select distinct UPPER(groupname) from records.commsgroup) THEN true else false end";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);

            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                stat = rset.getBoolean(1);
            }

            //  conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return stat;
    }

    public Object[] getGroupMembers(String str, Connection conn) {
        Object[] stat = new String[]{};
        Vector v = new Vector();
        String sql = "select distinct memberpf from records.commsgroup where UPPER(groupname) =upper('" + str + "') order by 1";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);

            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                v.add(rset.getString(1));
            }
            stat = v.toArray();

            //  conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return stat;
    }

    private String FullNameByID(String key, Connection conn) {

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

    private String UserEmailByID(String key, Connection conn) {

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

    private String UserPhone(String key, Connection conn) {

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

    public String getConcatEmails(String recepients, Connection conn) {
        System.err.println("My recepients: " + recepients);
        String concat = "";
        String[] emails = new String[]{};
        Vector<String> emailsv = new Vector<String>();

        String[] individualpfs = recepients.split(",");//COnvert the string into an array for looping
        for (int i = 0; i < individualpfs.length; i++) {
            String pf = individualpfs[i];

            if (isAGroupID(pf, conn)) {//If string is a group id, find the array of members, loop through group members sending messages

                Object[] members = getGroupMembers(pf, conn);
                for (int j = 0; j < members.length; j++) {

                    recepients = members[j].toString();

                    String email = UserEmailByID(recepients, conn);

                    String phone = UserPhone(recepients, conn);

                    String recipientName = FullNameByID(recepients, conn);

                    emailsv.add(email);
                }

            } else {//We can be having JOHN/8976
                //Start business
                recepients = pf.split("/")[1];
                System.err.println("Starting with PF Number: " + recepients);
                String email = UserEmailByID(recepients, conn);

                String phone = UserPhone(recepients, conn);

                // String name = FullNameByID(staffid, conn);
                String recipientName = FullNameByID(recepients, conn);

                emailsv.add(email);

                System.err.println("Added " + email);

                //   new SendSms(phone, "RefID " + refid + "\nRegistry Alert:\n" + message, refid, conn);
                //End business...
            }

        }

        emails = emailsv.toArray(new String[emailsv.size()]);//emailsv.toArray();

        concat = returnCOncatArr(emails);
        System.err.println("My emails: " + concat);
        //  System.err.println(concat);
        return concat;

    }

    private String returnCOncatArr(String[] array) {
        String concat = "";

        String delimiter = ",";
        concat = String.join(delimiter, array);
        System.err.println(concat);

        return concat;
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
