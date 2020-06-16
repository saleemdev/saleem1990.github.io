/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maintenance;

/**
 *
 * @author owner
 */
import servlets.*;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.mtrh.mtportal.sys.ConnectionProperties;
import com.mtrh.mtportal.sys.LeaveFactory;
import com.mtrh.mtportal.sys.PDFPAth;
import com.mtrh.mtportal.sys.QRCodeGenerator;
//import com.mtrh.fleet.dao.ConnectionProperties;
//import com.mtrh.fleet.dao.TestClass;
//import com.mtrh.fleet.dao.VehicleDao;
import java.awt.Desktop;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
//import dbadmin.PDFRenderer;
//import dbadmin.CurrencyFormatter;
//import dbadmin.StoreFunctions;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class WorkOrderSlip implements java.lang.Runnable {

    /**
     * @return the requestid
     */
    public static String getRequestid() {
        return requestid;
    }

    /**
     * @param aRequestid the requestid to set
     */
    public static void setRequestid(String aRequestid) {
        requestid = aRequestid;
    }

    /**
     * @return the requesttype
     */
    public static String getRequesttype() {
        return requesttype;
    }

    /**
     * @param aRequesttype the requesttype to set
     */
    public static void setRequesttype(String aRequesttype) {
        requesttype = aRequesttype;
    }

    /**
     * @return the contactperson
     */
    public static String getContactperson() {
        return contactperson;
    }

    /**
     * @param aContactperson the contactperson to set
     */
    public static void setContactperson(String aContactperson) {
        contactperson = aContactperson;
    }

    /**
     * @return the location_
     */
    public static String getLocation_() {
        return location_;
    }

    /**
     * @param aLocation_ the location_ to set
     */
    public static void setLocation_(String aLocation_) {
        location_ = aLocation_;
    }

    /**
     * @return the description
     */
    public static String getDescription() {
        return description;
    }

    /**
     * @param aDescription the description to set
     */
    public static void setDescription(String aDescription) {
        description = aDescription;
    }

    /**
     * @return the tstamp
     */
    public static String getTstamp() {
        return tstamp;
    }

    /**
     * @param aTstamp the tstamp to set
     */
    public static void setTstamp(String aTstamp) {
        tstamp = aTstamp;
    }

    /**
     * @return the uname
     */
    public static String getUname() {
        return uname;
    }

    /**
     * @param aUname the uname to set
     */
    public static void setUname(String aUname) {
        uname = aUname;
    }

    private String ref_no = null;

    com.mtrh.mtportal.sys.DBObject dbObject;
    java.util.Date beginDate = null;
    java.util.Date endDate = null;
    public static java.sql.Connection connectDB = null;
    public java.lang.String dbUserName = null;
    //org.netbeans.lib.sql.pool.PooledConnectionSource pConnDB = null;
    boolean threadCheck = true;
    java.lang.String Receipt = null;
    java.lang.Thread threadSample;

    com.lowagie.text.Font pFontHeader0 = FontFactory.getFont(FontFactory.HELVETICA, 15, Font.BOLD);
    com.lowagie.text.Font pFontHeader = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);
    com.lowagie.text.Font pFontHeader1 = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL);

    com.lowagie.text.Font pfontData = FontFactory.getFont(FontFactory.COURIER, 9, Font.NORMAL);

    com.lowagie.text.Font pfontData1 = FontFactory.getFont(FontFactory.COURIER, 9, Font.NORMAL);

    //   com.lowagie.text.ParagraphFont pgraph = Paragraph();
    java.lang.Runtime rtThreadSample = java.lang.Runtime.getRuntime();
    java.lang.Process prThread;
    private String filepath;
    public static String transaction;
    public static String reference;
    private String personrqing;
    String begindate, enddate;

    public void RequestPDF(java.sql.Connection connDb, String rqid) {
        //public void IssuedItemsPdf(java.sql.Connection connDb) {

        dbObject = new com.mtrh.mtportal.sys.DBObject();
        //   begindate = date1;
        // enddate = date2;

        connectDB = connDb;
        transaction = "WORK ORDER SLIP";
        reference = rqid;

        serviceRequestFunctions(connectDB, reference);

        generatePdf();
    }

    public static void main(java.lang.String[] args) {
        //		new TransactionsListPdf().TransactionsListPdf();
    }

    public void run() {

        System.out.println("System has entered running mode");

        while (threadCheck) {

            System.out.println("O.K. see how we execute target program");

            this.generatePdf();

            try {

                System.out.println("Right, let's wait for task to complete of fail");

                java.lang.Thread.currentThread().sleep(200);

                System.out.println("It's time for us threads to get back to work after the nap");

            } catch (java.lang.InterruptedException IntExec) {

                System.out.println(IntExec.getMessage());

            }

            threadCheck = false;

            System.out.println("We shall be lucky to get back to start in one piece");

        }

        if (!threadCheck) {

            Thread.currentThread().stop();

        }

    }

    public java.lang.String getDateLable() {

        java.lang.String date_label = null;

        java.lang.String month_now_strs = null;

        java.lang.String date_now_strs = null;

        java.lang.String year_now_strs = null;

        java.lang.String minute_now_strs = null;

        java.lang.String hour_now_strs = null;

        java.lang.Runtime rt = java.lang.Runtime.getRuntime();

        java.util.Calendar calinst = java.util.Calendar.getInstance();

        java.util.Date date_now = calinst.getTime();

        int date_now_str = date_now.getDate();

        int month_now_str = date_now.getMonth();

        int year_now_str = date_now.getYear();

        int hour_now_str = date_now.getHours();

        int minute_now_str = date_now.getMinutes();

        int year_now_abs = year_now_str - 100;

        if (year_now_abs < 10) {

            year_now_strs = "200" + year_now_abs;

        } else {

            year_now_strs = "20" + year_now_abs;

        }

        switch (month_now_str) {

            case 0:
                month_now_strs = "JAN";

                break;

            case 1:
                month_now_strs = "FEB";

                break;

            case 2:
                month_now_strs = "MAR";

                break;

            case 3:
                month_now_strs = "APR";

                break;

            case 4:
                month_now_strs = "MAY";

                break;

            case 5:
                month_now_strs = "JUN";

                break;

            case 6:
                month_now_strs = "JUL";

                break;

            case 7:
                month_now_strs = "AUG";

                break;

            case 8:
                month_now_strs = "SEP";

                break;

            case 9:
                month_now_strs = "OCT";

                break;

            case 10:
                month_now_strs = "NOV";

                break;

            case 11:
                month_now_strs = "DEC";

                break;

            default:
                if (month_now_str < 10) {

                    month_now_strs = "0" + month_now_str;

                } else {

                    month_now_strs = "" + month_now_str;

                }

        }

        if (date_now_str < 10) {

            date_now_strs = "0" + date_now_str;

        } else {

            date_now_strs = "" + date_now_str;

        }

        if (minute_now_str < 10) {

            minute_now_strs = "0" + minute_now_str;

        } else {

            minute_now_strs = "" + minute_now_str;

        }

        if (hour_now_str < 10) {

            hour_now_strs = "0" + hour_now_str;

        } else {

            hour_now_strs = "" + hour_now_str;

        }

        date_label = date_now_strs + month_now_strs + year_now_strs + "@" + hour_now_strs + minute_now_strs;

        return date_label;

    }

    public void fire(String name) {
        PDFPAth.setPath2pdf(name);
        //TestClass.setPath2pdf(name);
        System.err.println("I was fucking fired");

    }

    public void generatePdf() {

        java.lang.Process wait_for_Pdf2Show;

        java.util.Calendar cal = java.util.Calendar.getInstance();

        java.util.Date dateStampPdf = cal.getTime();

        java.lang.String pdfDateStamp = dateStampPdf.toString();
        double osBalance = 0.00;

        try {
            String tDir = System.getProperty("java.io.tmpdir");

            // String filename = tDir+"REP" + this.getDateLable(), extension = "_.pdf";
            String filename = tDir + "REP", extension = "_.pdf";

            filepath = filename + "" + extension;

            java.io.File tempFile = java.io.File.createTempFile(filename, extension);

            filepath = tempFile.getAbsolutePath();

            fire(filepath);
            System.err.println("Path: " + tempFile.getAbsolutePath() + " Name " + filepath);

            System.err.println("Mi Bino es " + PDFPAth.getPath2pdf());

            tempFile.deleteOnExit();

            java.lang.Runtime rt = java.lang.Runtime.getRuntime();

            java.lang.String debitTotal = null;

            java.lang.String creditTotal = null;

            //  com.lowagie.text.Document docPdf = new com.lowagie.text.Document(PageSize.A4.rotate());
            com.lowagie.text.Document docPdf = new com.lowagie.text.Document();

            try {

                try {

                    com.lowagie.text.pdf.PdfWriter.getInstance(docPdf, new java.io.FileOutputStream(tempFile));

                    docPdf.open();

                    String compName = null;
                    String date = null;

                    com.lowagie.text.pdf.PdfPTable table1 = new com.lowagie.text.pdf.PdfPTable(7);

                    int headerwidths1[] = {15, 15, 30, 15, 15, 15, 15};

                    table1.setWidths(headerwidths1);

                    table1.setWidthPercentage((100));

                    Phrase phrase = new Phrase();

                    table1.getDefaultCell().setBorderColor(java.awt.Color.WHITE);

                    try {

                        java.sql.Statement st3 = connectDB.createStatement();
                        java.sql.ResultSet rset3 = st3.executeQuery("SELECT DISTINCT name,box_no||'-'||postal_code||', '||town,main_tel,main_fax,logo, standardization FROM institution_profile");
                        while (rset3.next()) {

                            InputStream is = rset3.getBinaryStream(5);
                            BufferedImage img = ImageIO.read(is);

                            table1.getDefaultCell().setColspan(1);
                            table1.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                            table1.getDefaultCell().setFixedHeight(70);
                            table1.addCell(com.lowagie.text.Image.getInstance(img, null, false));

                            table1.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_CENTER);
                            //    table1.getDefaultCell().setFixedHeight(25);                            //here
                            table1.getDefaultCell().setColspan(5);
                            String addr = "P.O. Box " + rset3.getObject(2).toString() + "\n\nTel: " + rset3.getObject(3).toString();
                            phrase = new Phrase(rset3.getObject(1).toString() + "\n\n\n" + addr, pFontHeader);
                            table1.addCell(phrase);

                            is = rset3.getBinaryStream(6);
                            img = ImageIO.read(is);

                            table1.getDefaultCell().setColspan(1);
                            table1.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
                            table1.getDefaultCell().setFixedHeight(70);
                            table1.addCell(com.lowagie.text.Image.getInstance(img, null, false));
                        }

                    } catch (java.sql.SQLException SqlExec) {

                        SqlExec.printStackTrace();

                    }
                    // table1.addCell(phrase);
                    docPdf.add(table1);
                    PDFPAth.setPath2pdf(filepath);
                    System.err.println("After setting the header " + PDFPAth.getPath2pdf());

                    com.lowagie.text.HeaderFooter footer = new com.lowagie.text.HeaderFooter(new Phrase("MTRH ICT Department - Page: ", pFontHeader), true);// FontFactory.getFont(com.lowagie.text.FontFactory.HELVETICA, 12, Font.BOLDITALIC,java.awt.Color.blue));

                    docPdf.setFooter(footer);

                    //              docPdf.open();
                    try {
//                        com.lowagie.text.pdf.PdfPTable table2 = new com.lowagie.text.pdf.PdfPTable(7);
//
//                        int headerwidths2[] = {15, 15, 30, 15, 15, 15, 15};
//
//                        table2.setWidths(headerwidths2);
//
//                        table2.setWidthPercentage((100));
//                        
//                        tabl
//
//                        docPdf.add(table2);

                        ///////
                        com.lowagie.text.pdf.PdfPTable table = new com.lowagie.text.pdf.PdfPTable(5);

                        int headerwidths[] = {20, 20, 30, 15, 15};

                        table.setWidths(headerwidths);

                        table.setWidthPercentage((100));
                        table.getDefaultCell().setBorderColor(java.awt.Color.white);
                        table.setHeaderRows(2);
                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_CENTER);
                        table.getDefaultCell().setColspan(5);
                        phrase = new Phrase(transaction.toUpperCase(), pFontHeader0);
                        table.addCell(phrase);

                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                        table.getDefaultCell().setColspan(2);
                        phrase = new Phrase("REF NUMBER: " + reference, pfontData1);
                        table.addCell(phrase);

                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                        table.getDefaultCell().setColspan(3);//                      
                        date = com.mtrh.mtportal.sys.ServerTime.serverDate(connectDB);                        //  table.addCell("");
                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
                        phrase = new Phrase("Printed on : " + date, pFontHeader);
                        table.addCell(phrase);

                        table.getDefaultCell().setColspan(1);
                        table.getDefaultCell().setBackgroundColor(java.awt.Color.LIGHT_GRAY);
                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);

                        try {

                            java.sql.Statement st = connectDB.createStatement();

                            java.sql.Statement st2 = connectDB.createStatement();

                        } catch (java.sql.SQLException SqlExec) {

                            SqlExec.printStackTrace();

                        }

                        try {

                            PreparedStatement pstV1 = connectDB.prepareStatement("select * from workorder where refid=? order by tstamp");
                            System.out.println("select * from workorder where refid='" + reference + "' order by tstamp");
                            pstV1.setObject(1, reference);

                            ResultSet rsetV1 = pstV1.executeQuery();

                            Chunk underline = new Chunk("Underline. ");

//                                String lvcat = rsetV1.getObject(4).toString();
//                                String daysr = rsetV1.getObject(5).toString();//int
//                                String appliedon = rsetV1.getObject(6).toString();
//                                String coveringofficer = dbObject.getDBObject(rsetV1.getObject(7).toString(), "-");
//                                String supervisor = dbObject.getDBObject(rsetV1.getObject(8), "-");
//                                String resourcing = dbObject.getDBObject(rsetV1.getObject(9), "-");
//                                String seniormgr = dbObject.getDBObject(rsetV1.getObject(10), "-");
//
//                                String fy = dbObject.getDBObject(rsetV1.getObject(12).toString(), "");
//                                String leavestart = dbObject.getDBObject(rsetV1.getObject(16).toString(), "");
//
//                                String leaveaddr = dbObject.getDBObject(rsetV1.getObject(22).toString(), "");
//
//                                String coveringreal = dbObject.getDBObject(rsetV1.getObject("coveringreal").toString(), "");
//
//                                //Booleans
//                                Boolean covering = rsetV1.getBoolean(17);
//                                Boolean hod = rsetV1.getBoolean(18);
//                                Boolean hresourcing = rsetV1.getBoolean(19);
//                                Boolean snrmgr = rsetV1.getBoolean(20);
                            //applicant details/
                            // new ConnectionProperties().setMetrics(pf, connectDB);
                            String det = "Ticket type: " + getRequesttype() + "\n\n" + "Logged On: " + getTstamp() + "\n\nLocation: " + getLocation_() + "\n\nContact Person: " + getContactperson();

                            ///start QRCode
                            table.getDefaultCell().setBackgroundColor(java.awt.Color.white);
                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                            table.getDefaultCell().setColspan(3);
                            phrase = new Phrase("\n\n\n" + det, pFontHeader);
                            table.addCell(phrase);
/////
                            String url = QRCodeGenerator.QR_URL(det);
                            BufferedImage img2 = QRCodeGenerator.QR_BUFFERED_IMG(det);
                            java.awt.Image scaled = img2.getScaledInstance(100, 100, java.awt.Image.SCALE_DEFAULT);
                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_CENTER);
                            //table.addCell(com.lowagie.text.Image.getInstance(this.resizeImage(url, 50, 50), null, false));
                            table.addCell(com.lowagie.text.Image.getInstance(img2, null, false));
                            System.err.print("I scaled this shit");
//////end

                            //Btw future me or other developer. I want you to know that this QR code took me a month to put together
                            //So you arelucky if it works out of the box
                            //I used Qrgen and Zxing 3.0
///////////////////////////////////////////////////////////////////////
                            table.getDefaultCell().setBackgroundColor(java.awt.Color.LIGHT_GRAY);
                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);

//                                table.getDefaultCell().setColspan(5);                                   //HEADER 1
//                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
//                                phrase = new Phrase("APPLICANT's DETAILS", pFontHeader);
//                                table.addCell(phrase);
//
//                                table.getDefaultCell().setBackgroundColor(java.awt.Color.WHITE);
//                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
//
////                                table.getDefaultCell().setColspan(3);
////                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
////                                phrase = new Phrase("Full Name: " + ConnectionProperties.getUsername(), pfontData);
////                                table.addCell(phrase);
////
////                                table.getDefaultCell().setColspan(2);
////                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
////                                phrase = new Phrase("PF Number: " + pf, pfontData);
////                                table.addCell(phrase);
////
////                                table.getDefaultCell().setColspan(5);
////                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
////                                phrase = new Phrase("", pfontData);
////                                table.addCell(phrase);
////
////                                table.getDefaultCell().setColspan(3);
////                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
////                                phrase = new Phrase("DEPARTMENT: " + ConnectionProperties.getDepartment(), pfontData);
////                                table.addCell(phrase);
////
////                                table.getDefaultCell().setColspan(2);
////                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
////                                phrase = new Phrase("SECTION: " + ConnectionProperties.getSection(), pfontData);
////                                table.addCell(phrase);
                            table.getDefaultCell().setColspan(5);
                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                            phrase = new Phrase("", pfontData);
                            table.addCell(phrase);
                            //
                            table.getDefaultCell().setBackgroundColor(java.awt.Color.LIGHT_GRAY);
                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);

                            table.getDefaultCell().setColspan(5);
                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                            phrase = new Phrase("BRIEF DESCRIPTION OF TICKET", pFontHeader);
                            table.addCell(phrase);

                            table.getDefaultCell().setColspan(5);
                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                            phrase = new Phrase("", pfontData);
                            table.addCell(phrase);
//HEADER2
                            table.getDefaultCell().setBackgroundColor(java.awt.Color.WHITE);
                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);

                            table.getDefaultCell().setColspan(5);
                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                            phrase = new Phrase(getDescription(), pfontData);
                            table.addCell(phrase);

                            table.getDefaultCell().setColspan(5);
                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                            phrase = new Phrase("", pfontData);
                            table.addCell(phrase);

///////////////////////////////////////////////////////////////////////////////
                            //header 3
                            table.getDefaultCell().setBackgroundColor(java.awt.Color.LIGHT_GRAY);
                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);

                            table.getDefaultCell().setColspan(5);
                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                            phrase = new Phrase("", pfontData);
                            table.addCell(phrase);

                            table.getDefaultCell().setColspan(5);
                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                            phrase = new Phrase("WORK ACTIVITIES PERFORMED", pFontHeader);
                            table.addCell(phrase);

                            table.getDefaultCell().setColspan(5);
                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                            phrase = new Phrase("", pfontData);
                            table.addCell(phrase);
//HEADER2
                            table.getDefaultCell().setBackgroundColor(java.awt.Color.WHITE);
                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);

                            while (rsetV1.next()) {
                                String rqid = rsetV1.getObject(1).toString();
                                String actiontaken = rsetV1.getObject("action_taken").toString();
                                String tstamp = rsetV1.getObject("tstamp").toString();
                                String doneby = rsetV1.getObject("username").toString();
                                //Loop to start here-----------------------------
                                table.getDefaultCell().setColspan(3);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase(actiontaken, pfontData);
                                table.addCell(phrase);

                                table.getDefaultCell().setColspan(2);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase(doneby + "/" + tstamp, pfontData);
                                table.addCell(phrase);

                                table.getDefaultCell().setColspan(5);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase("", pfontData);
                                table.addCell(phrase);

                                table.getDefaultCell().setColspan(5);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase("", pfontData);
                                table.addCell(phrase);

                            }
//Loop to end here
                            table.getDefaultCell().setColspan(5);
                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                            phrase = new Phrase("", pfontData);
                            table.addCell(phrase);

//                                table.getDefaultCell().setColspan(5);
//                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
//                                phrase = new Phrase("Remarks: ", pfontData);
//                                table.addCell(phrase);
/////////////////////////////////////////////////////////////////
                            table.getDefaultCell().setColspan(5);
                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                            phrase = new Phrase("", pfontData);
                            table.addCell(phrase);
/////////////////////////////////////////////////////////////////////////////// hod supervisor
                            //header 3

                            table.getDefaultCell().setBackgroundColor(java.awt.Color.LIGHT_GRAY);
                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);

                            table.getDefaultCell().setColspan(5);
                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                            phrase = new Phrase("", pfontData);
                            table.addCell(phrase);

///////////////////////////////////////////////////////////////// 
/////////////////////////////////////////////////////////////////////////////// hod supervisor
                            //header 3
//                                if (snrmgr == Boolean.TRUE) {
//                                    table.getDefaultCell().setColspan(2);
//                                    table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
//                                    phrase = new Phrase("APPROVAL STATUS: " + String.valueOf("APPROVED"), pfontData);
//                                    table.addCell(phrase);
//                                } else {
//                                    if (snrmgr == Boolean.FALSE) {
//                                        table.getDefaultCell().setColspan(2);
//                                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
//                                        phrase = new Phrase("APPROVAL STATUS: " + String.valueOf("DECLINED"), pfontData);//CHANGED 01/02/2019
//                                        table.addCell(phrase);
//                                    } else {
//                                        table.getDefaultCell().setColspan(2);
//                                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
//                                        phrase = new Phrase("APPROVAL STATUS: " + String.valueOf("PENDING"), pfontData);
//                                        table.addCell(phrase);
//                                    }
//                                }
//                                table.getDefaultCell().setColspan(5);
//                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
//                                phrase = new Phrase("Remarks: ", pfontData);
//                                table.addCell(phrase);
/////////////////////////////////////////////////////////////////
                            table.getDefaultCell().setColspan(5);
                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                            phrase = new Phrase("", pfontData);
                            table.addCell(phrase);

                            table.getDefaultCell().setBorderColor(java.awt.Color.BLACK);

                            table.getDefaultCell().setBorder(Rectangle.BOTTOM | Rectangle.TOP);

                            table.getDefaultCell().setColspan(2);

                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);

                            table.getDefaultCell().setColspan(2);

                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_RIGHT);

                            table.getDefaultCell().setColspan(8);
                            table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                            phrase = new Phrase("     ", pFontHeader);
                            table.addCell(phrase);

                            String usr = "";

//                            if (ConnectionProperties.getUsername() == null) {
//                                usr = personrqing;
//                            } else {
//                                usr = ConnectionProperties.getUsername().toUpperCase();
//                            }
                            usr = getLoggedInUser(connectDB);

                            phrase = new Phrase("Printed By: " + usr, pFontHeader);
                            table.addCell(phrase);
                            docPdf.add(table);

                            docPdf.newPage();

                        } catch (java.sql.SQLException SqlExec) {
                            SqlExec.printStackTrace();
                            // javax.swing.JOptionPane.showMessageDialog(new javax.swing.JFrame(), SqlExec.getMessage());

                        }

                    } catch (com.lowagie.text.BadElementException BadElExec) {

                        javax.swing.JOptionPane.showMessageDialog(new javax.swing.JFrame(), BadElExec.getMessage());

                    }

                } catch (java.io.FileNotFoundException fnfExec) {

                    javax.swing.JOptionPane.showMessageDialog(new javax.swing.JFrame(), fnfExec.getMessage());

                }
            } catch (com.lowagie.text.DocumentException lwDocexec) {

                javax.swing.JOptionPane.showMessageDialog(new javax.swing.JFrame(), lwDocexec.getMessage());

            }

            docPdf.close();

            PDFPAth.setPath2pdf(tempFile.getAbsolutePath());
            System.err.println("Finally " + tempFile.getAbsolutePath());
            //Desktop.getDesktop().open(tempFile);

//            PDFRenderer.renderPDF(tempFile);
        } catch (java.io.IOException IOexec) {
            IOexec.printStackTrace();
            // javax.swing.JOptionPane.showMessageDialog(new javax.swing.JFrame(), IOexec.getMessage());

        }

    }

    private String getLoggedInUser(java.sql.Connection conn) {

        String fullname = "";
        String sql = "SELECT fullname FROM secure_password WHERE login_name = current_user";

        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                fullname = rset.getObject(1).toString();
            }
        } catch (SQLException ex) {
            Logger.getLogger(WorkOrderSlip.class.getName()).log(Level.SEVERE, null, ex);
        }

        return fullname;

    }

    public BufferedImage resizeBufferedImage(BufferedImage bsrc, int w, int h) {

        BufferedImage bdest = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bdest.createGraphics();
        AffineTransform at = AffineTransform.getScaleInstance((double) w / bsrc.getWidth(),
                (double) h / bsrc.getHeight());
        g.drawRenderedImage(bsrc, at);
        return bdest;

    }

    public BufferedImage resizeImage(String filePath, int w, int h) {

        String data = filePath;
        BufferedImage bsrc, bdest;
        ImageIcon theIcon;

//scale the image
        try {

            bsrc = ImageIO.read(new File(data));

            bdest = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bdest.createGraphics();
            AffineTransform at = AffineTransform.getScaleInstance((double) w / bsrc.getWidth(),
                    (double) h / bsrc.getHeight());
            g.drawRenderedImage(bsrc, at);

            //add the scaled image
            theIcon = new ImageIcon(bdest);
            // return theIcon;
            return bdest;
        } catch (Exception e) {
            System.out.println("This image can not be resized. Please check the path and type of file.");
            return null;
        }

    }

    public static String approvalStatus(String level, String refid, Connection conn) {
        String stat = "";

        String sql = "select case when " + level + " is false then 'DECLINED'  when " + level + " is true THEN 'APPROVED' else 'PENDING' end as status\n"
                + "FROM hr.leave_application\n"
                + "where refno ='" + refid + "'";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                stat = rset.getString(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return stat;
    }
//   

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public void Trebuchet() {
        //BaseFont base = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.WINANSI);
//Font font = new Font(base, 11f, Font.BOLD);

    }

    public static void serviceRequestFunctions(Connection conn, String refid) {
        try {
            PreparedStatement pst = connectDB.prepareStatement("select * from servicerequest where requestid = ?");
            pst.setObject(1, refid);
            ResultSet rset = pst.executeQuery();

            while (rset.next()) {

                setRequestid(rset.getObject(1).toString());
                setRequesttype(rset.getObject(2).toString());
                setContactperson(rset.getObject(3).toString());
                setLocation_(rset.getObject(4).toString());
                setDescription(rset.getObject(5).toString());
                setTstamp(rset.getObject(6).toString());
                setUname(rset.getObject(7).toString());
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    private static String requestid;
    private static String requesttype;
    private static String contactperson;
    private static String location_;
    private static String description;
    private static String tstamp;
    private static String uname;
}
