/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

/**
 *
 * @author owner
 */
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.mtrh.mtportal.sys.DBObject;
//import ConnectionProperties;
//import TestClass;
//import VehicleDao;
import com.mtrh.mtportal.sys.PDFPAth;
import com.mtrh.mtportal.sys.QRCodeGenerator;
import com.mtrh.mtportal.sys.ServerTime;
import com.mtrh.mtportal.sys.VehicleDao;
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

public class FuelRqPDF implements java.lang.Runnable {

    private String ref_no = null;

    DBObject dbObject;
    java.util.Date beginDate = null;
    java.util.Date endDate = null;
    public static java.sql.Connection connectDB = null;
    public java.lang.String dbUserName = null;
    //org.netbeans.lib.sql.pool.PooledConnectionSource pConnDB = null;
    boolean threadCheck = true;
    java.lang.String Receipt = null;
    java.lang.Thread threadSample;
    com.lowagie.text.Font pFontHeader = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);
    com.lowagie.text.Font pFontHeader1 = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL);

    com.lowagie.text.Font pfontData = FontFactory.getFont(FontFactory.COURIER, 9, Font.NORMAL);

    com.lowagie.text.Font pfontData1 = FontFactory.getFont(FontFactory.COURIER, 9, Font.NORMAL);

    //   com.lowagie.text.ParagraphFont pgraph = Paragraph();
    java.lang.Runtime rtThreadSample = java.lang.Runtime.getRuntime();
    java.lang.Process prThread;
    private String filepath;
    public static String transaction;
    public static String request;
    private String personrqing;

    public void RequestPDF(java.sql.Connection connDb, String rqid, String transtype) {
        //public void IssuedItemsPdf(java.sql.Connection connDb) {

        dbObject = new DBObject();

        connectDB = connDb;
        transaction = transtype;
        request = rqid;

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
        //PDFPAth.setPath2pdf(name);
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

                    table1.getDefaultCell().setBorderColor(java.awt.Color.blue);

                    try {

                        java.sql.Statement st3 = connectDB.createStatement();
                        java.sql.ResultSet rset3 = st3.executeQuery("SELECT DISTINCT name,box_no||'-'||postal_code||', '||town,main_tel,main_fax,logo FROM institution_profile");
                        while (rset3.next()) {

                            InputStream is = rset3.getBinaryStream(5);
                            BufferedImage img = ImageIO.read(is);

                            table1.getDefaultCell().setColspan(2);
                            //  table1.getDefaultCell().setColspan(10);
                            table1.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_CENTER);
                            table1.getDefaultCell().setFixedHeight(70);
                            table1.addCell(com.lowagie.text.Image.getInstance(img, null, false));
                            table1.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_CENTER);

                            table1.getDefaultCell().setFixedHeight(25);                            //here
                            table1.getDefaultCell().setColspan(6);
                            table1.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_CENTER);
                            String addr = "P.O. Box " + rset3.getObject(2).toString() + "\nTel: " + rset3.getObject(2).toString() + "\nFax " + rset3.getObject(3).toString();
                            phrase = new Phrase(rset3.getObject(1).toString() + "\n\n\n" + addr, pFontHeader);
                            table1.addCell(phrase);
                        }

                    } catch (java.sql.SQLException SqlExec) {

                        SqlExec.printStackTrace();

                    }
                    table1.addCell(phrase);
                    docPdf.add(table1);
                    PDFPAth.setPath2pdf(filepath);
                    System.err.println("After setting the header " + PDFPAth.getPath2pdf());

                    com.lowagie.text.HeaderFooter footer = new com.lowagie.text.HeaderFooter(new Phrase("MTRH Transport Department - Page: ", pFontHeader), true);// FontFactory.getFont(com.lowagie.text.FontFactory.HELVETICA, 12, Font.BOLDITALIC,java.awt.Color.blue));

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
                        com.lowagie.text.pdf.PdfPTable table = new com.lowagie.text.pdf.PdfPTable(4);

                        int headerwidths[] = {25, 37, 25, 37};

                        table.setWidths(headerwidths);

                        table.setWidthPercentage((100));
                        table.getDefaultCell().setBorderColor(java.awt.Color.white);

                        table.setHeaderRows(2);
                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_CENTER);
                        table.getDefaultCell().setColspan(4);
                        phrase = new Phrase(transaction.toUpperCase(), pFontHeader);
                        table.addCell(phrase);
                        // table.getDefaultCell().setBorder(Rectangle.BOTTOM);
                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                        table.getDefaultCell().setColspan(2);

//                        Phrase phrase = new Phrase("Transfered Items", pFontHeader);
                        date = ServerTime.serverDate(connectDB);

                        //  table.addCell("");
                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
                        table.getDefaultCell().setColspan(5);
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
                        table.getDefaultCell().setBackgroundColor(java.awt.Color.white);
                        //  table.getDefaultCell().setBorder(Rectangle.BOX);
                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                        table.getDefaultCell().setColspan(3);
                        phrase = new Phrase("REF NUMBER: " + request, pFontHeader);
                        table.addCell(phrase);
/////
                        String url = QRCodeGenerator.QR_URL(request);
                        // table.getDefaultCell().setColspan(2);
                        BufferedImage img2 = QRCodeGenerator.QR_BUFFERED_IMG(request);
                        java.awt.Image scaled = img2.getScaledInstance(100, 100, java.awt.Image.SCALE_DEFAULT);
                        //   table.getDefaultCell().setBorder(Rectangle.BOX);
                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_CENTER);
                        table.addCell(com.lowagie.text.Image.getInstance(this.resizeImage(url, 100, 100), null, false));
                        System.err.print("I scaled this shit");
//////

                        //Btw future me or other developer. I want you to know that this QR code took me a month to put together
                        //So you arelucky if it works out of the box
                        //I used Qrgen and Zxing 3.0
///////////////////////////////////////////////////////////////////////
                        table.getDefaultCell().setBackgroundColor(java.awt.Color.gray);
                        table.getDefaultCell().setBorder(Rectangle.BOX);
                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                        table.getDefaultCell().setColspan(4);
                        phrase = new Phrase("REQUEST DETAILS", pFontHeader);
                        table.addCell(phrase);

                        try {

                            PreparedStatement pstV1 = connectDB.prepareStatement("SELECT * FROM fleet.transportrequestmemo WHERE rqid = ? ");
                            pstV1.setObject(1, request);
                            ResultSet rsetV1 = pstV1.executeQuery();

                            table.getDefaultCell().setBackgroundColor(java.awt.Color.WHITE);
                            table.getDefaultCell().setBorderColor(java.awt.Color.blue);

                            while (rsetV1.next()) {

                                String rqtype = rsetV1.getObject(2).toString();
                                String requestedby = rsetV1.getObject(3).toString();
                                String idno = rsetV1.getObject(4).toString();
                                String email = rsetV1.getObject(5).toString();
                                String telno = rsetV1.getObject(6).toString();
                                String organization = rsetV1.getObject(7).toString();
                                String department = rsetV1.getObject(8).toString();

                                String from = rsetV1.getObject(9).toString();
                                String destination = rsetV1.getObject(10).toString();
                                String distance = rsetV1.getObject(11).toString();
                                String paxno = rsetV1.getObject(12).toString();
                                String vehreq = rsetV1.getObject(13).toString();

                                String purpose = rsetV1.getObject(14).toString();
                                String remarks = rsetV1.getObject(15).toString();
                                String enteredby = rsetV1.getObject(17).toString();
                                String enteredon = rsetV1.getObject(18).toString();

                                String vehissued = rsetV1.getObject(26).toString();
                                String driver = rsetV1.getObject(27).toString();
                                // String vehreq = rsetV1.getObject(12).toString();
                                //-----
                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);
                                table.getDefaultCell().setColspan(4);
                                table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase("", pFontHeader1);
                                table.addCell(phrase);

                                ////
                                table.getDefaultCell().setColspan(1);
                                // table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
                                phrase = new Phrase("VEHICLE REGISTRATION: ", pFontHeader1);
                                table.addCell(phrase);
///////////////////
                                table.getDefaultCell().setBackgroundColor(java.awt.Color.LIGHT_GRAY);
                                table.getDefaultCell().setColspan(1);
                                //table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase(vehreq, pfontData);
                                table.addCell(phrase);
///////////////////////////////////////
                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);
                                table.getDefaultCell().setColspan(1);
                                table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
                                phrase = new Phrase("REQUESTED BY: ", pFontHeader1);
                                table.addCell(phrase);

////////////////////
                                table.getDefaultCell().setBackgroundColor(java.awt.Color.LIGHT_GRAY);
                                table.getDefaultCell().setColspan(1);
                                //table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase(requestedby, pfontData);
                                table.addCell(phrase);

                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);

                                ///////////////////////////////////////
                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);
                                table.getDefaultCell().setColspan(1);
                                table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
                                phrase = new Phrase("FUEL TYPE: ", pFontHeader1);
                                table.addCell(phrase);

                                Object[] vdet = new VehicleDao().vehicleDetails(connectDB, vehreq);

                                String fuel = new DBObject().getDBObject(vdet[7], "not specified");

////////////////////
                                if (department.length() < 2) {
                                    department = "unspecified";
                                }
                                table.getDefaultCell().setBackgroundColor(java.awt.Color.LIGHT_GRAY);
                                table.getDefaultCell().setColspan(1);
                                //table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase(fuel, pfontData);
                                table.addCell(phrase);

                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);
                                table.getDefaultCell().setColspan(1);
                                table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
                                phrase = new Phrase("POSTING TIME: ", pFontHeader1);
                                table.addCell(phrase);

////////////////////
                                table.getDefaultCell().setBackgroundColor(java.awt.Color.LIGHT_GRAY);
                                table.getDefaultCell().setColspan(1);
                                //table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase(enteredon, pfontData);
                                table.addCell(phrase);

                                personrqing = requestedby;

                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);
                                table.getDefaultCell().setColspan(1);
                                table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
                                phrase = new Phrase("MILEAGE ON REQUEST: ", pFontHeader1);
                                table.addCell(phrase);

                                table.getDefaultCell().setBackgroundColor(java.awt.Color.LIGHT_GRAY);
                                table.getDefaultCell().setColspan(3);
                                //table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase(vehicleMileage(request, connectDB), pfontData);
                                table.addCell(phrase);

                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);
                                table.getDefaultCell().setColspan(4);
                                table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase("\n", pFontHeader1);
                                table.addCell(phrase);

                                table.getDefaultCell().setBackgroundColor(java.awt.Color.LIGHT_GRAY);
                                table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setColspan(4);
                                phrase = new Phrase("CONTACT INFORMATION", pFontHeader);
                                table.addCell(phrase);
                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);

                                table.getDefaultCell().setColspan(1);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
                                phrase = new Phrase("PF/ID NO: ", pFontHeader1);
                                table.addCell(phrase);

                                table.getDefaultCell().setBackgroundColor(java.awt.Color.LIGHT_GRAY);
                                table.getDefaultCell().setColspan(1);
                                //table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase(enteredby, pfontData);
                                table.addCell(phrase);

                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);
                                table.getDefaultCell().setColspan(1);
                                table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
                                phrase = new Phrase("E-MAIL ADDRESS: ", pFontHeader1);
                                table.addCell(phrase);

                                table.getDefaultCell().setBackgroundColor(java.awt.Color.LIGHT_GRAY);
                                table.getDefaultCell().setColspan(1);
                                //table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase(email, pfontData);
                                table.addCell(phrase);
                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);

                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);
                                table.getDefaultCell().setColspan(1);
                                table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
                                phrase = new Phrase("TEL NO: ", pFontHeader1);
                                table.addCell(phrase);

                                table.getDefaultCell().setBackgroundColor(java.awt.Color.LIGHT_GRAY);
                                table.getDefaultCell().setColspan(3);
                                //table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase(telno, pfontData);
                                table.addCell(phrase);
                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);

                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);
                                table.getDefaultCell().setColspan(4);
                                table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase("\n", pFontHeader1);
                                table.addCell(phrase);

                                table.getDefaultCell().setBackgroundColor(java.awt.Color.LIGHT_GRAY);
                                table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setColspan(4);
                                phrase = new Phrase("FUEL REQUEST DETAILS", pFontHeader);
                                table.addCell(phrase);
                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);

                                Object[] fuelinv = getFuelInv(request, connectDB);
                                String[] concat = fuelinv[0].toString().split("/");
                                String cardno = concat[1];
                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);
                                table.getDefaultCell().setColspan(1);
                                table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
                                phrase = new Phrase("CARD NUMBER: ", pFontHeader1);
                                table.addCell(phrase);

                                table.getDefaultCell().setBackgroundColor(java.awt.Color.LIGHT_GRAY);
                                table.getDefaultCell().setColspan(1);
                                //table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase(cardno, pfontData);
                                table.addCell(phrase);
                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);

                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);
                                table.getDefaultCell().setColspan(1);
                                table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
                                phrase = new Phrase("CARD BALANCE: ", pFontHeader1);
                                table.addCell(phrase);

                                table.getDefaultCell().setBackgroundColor(java.awt.Color.LIGHT_GRAY);
                                table.getDefaultCell().setColspan(1);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase(getCardamt(cardno, connectDB), pfontData);
                                table.addCell(phrase);
                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);

                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);
                                table.getDefaultCell().setColspan(1);
                                table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
                                phrase = new Phrase("AMOUNT REQUESTED(LITRES): ", pFontHeader1);
                                table.addCell(phrase);

                                table.getDefaultCell().setBackgroundColor(java.awt.Color.LIGHT_GRAY);
                                table.getDefaultCell().setColspan(1);
                                //table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase(String.valueOf(fuelinv[1]), pfontData);
                                table.addCell(phrase);
                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);

                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);
                                table.getDefaultCell().setColspan(1);
                                table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
                                phrase = new Phrase("PRICE PER LITRE(Kes): ", pFontHeader1);
                                table.addCell(phrase);

                                table.getDefaultCell().setBackgroundColor(java.awt.Color.LIGHT_GRAY);
                                table.getDefaultCell().setColspan(1);
                                //table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase(String.valueOf(fuelinv[2]), pfontData);
                                table.addCell(phrase);
                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);

                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);
                                table.getDefaultCell().setColspan(1);
                                table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
                                phrase = new Phrase("TOTAL ORDER AMOUNT: ", pFontHeader1);
                                table.addCell(phrase);

                                table.getDefaultCell().setBackgroundColor(java.awt.Color.LIGHT_GRAY);
                                table.getDefaultCell().setColspan(3);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase(String.valueOf(fuelinv[3]) + " / " + String.valueOf(fuelinv[4]).replace("dollars", "kes"), pfontData);
                                table.addCell(phrase);
                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);
                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);

                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);
                                table.getDefaultCell().setColspan(4);
                                table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase("\n", pFontHeader1);
                                table.addCell(phrase);

                                table.getDefaultCell().setBackgroundColor(java.awt.Color.LIGHT_GRAY);
                                table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setColspan(4);
                                phrase = new Phrase("FLEET FUEL LOG", pFontHeader);
                                table.addCell(phrase);
                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);

                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);

                                table.getDefaultCell().setColspan(1);
                                table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
                                phrase = new Phrase("DATE: ", pFontHeader1);
                                table.addCell(phrase);

                                table.getDefaultCell().setColspan(1);
                                //table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase("REQUEST ID", pFontHeader1);
                                table.addCell(phrase);

                                //  table.getDefaultCell().setBackgroundColor(java.awt.Color.LIGHT_GRAY);
                                table.getDefaultCell().setColspan(1);
                                //table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase("LITRES REQUESTED", pFontHeader1);
                                table.addCell(phrase);

                                //table.getDefaultCell().setBackgroundColor(java.awt.Color.LIGHT_GRAY);
                                table.getDefaultCell().setColspan(1);
                                //table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase("MILEAGE", pFontHeader1);
                                table.addCell(phrase);

                                table.getDefaultCell().setBackgroundColor(java.awt.Color.LIGHT_GRAY);

                                PreparedStatement pst = connectDB.prepareStatement("SELECT humanreadabledate((enteredon::date)::text), rqid FROM fleet.transportrequestmemo WHERE rqtype ILIKE 'FUEL%' AND vehicle_requested =? and approved is true order by enteredon desc");
                                pst.setObject(1, vehreq);
                                ResultSet rset = pst.executeQuery();
                                int j = 1;
                                while (rset.next()) {
                                    String rqid = rset.getObject(2).toString();
                                    
                                    Object[] fuelin = getFuelInv(rqid, connectDB);
                                    
                                    Object fuelamt = new DBObject().getDBObject(fuelin[1], "-");
                                    Object mileage = new DBObject().getDBObject(vehicleMileage(rqid, connectDB), "-");
                                    
                                    
                                    table.getDefaultCell().setColspan(1);
                                    table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                    phrase = new Phrase(j+"."+rset.getObject(1).toString(), pfontData);
                                    table.addCell(phrase);
                                    
                                    table.getDefaultCell().setColspan(1);
                                    table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                    phrase = new Phrase(rqid, pfontData);
                                    table.addCell(phrase);
                                    
                                    table.getDefaultCell().setColspan(1);
                                    table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                    phrase = new Phrase(String.valueOf(fuelamt), pfontData);
                                    table.addCell(phrase);
                                    
                                    
                                    
                                    table.getDefaultCell().setColspan(1);
                                    table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                    phrase = new Phrase(String.valueOf(mileage), pfontData);
                                    table.addCell(phrase);
                                    
                                    j+=1;

                                }
//                                table.getDefaultCell().setBackgroundColor(java.awt.Color.LIGHT_GRAY);
//                                table.getDefaultCell().setColspan(3);
//                                //table.getDefaultCell().setBorder(Rectangle.BOX);
//                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
//                                phrase = new Phrase(vehreq, pfontData);
//                                table.addCell(phrase);

                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);

                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);

                                table.getDefaultCell().setColspan(4);
                                //table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase("\n", pfontData);
                                table.addCell(phrase);
                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);

                                table.getDefaultCell().setBackgroundColor(java.awt.Color.LIGHT_GRAY);
                                table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                table.getDefaultCell().setColspan(4);
                                phrase = new Phrase("APPROVAL(FOR OFFICIAL USE ONLY)", pFontHeader);
                                table.addCell(phrase);
                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);

                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);
                                table.getDefaultCell().setColspan(2);
                                table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_CENTER);
                                phrase = new Phrase("TRANSPORT MANAGER: ", pFontHeader1);
                                table.addCell(phrase);

                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);
                                table.getDefaultCell().setColspan(2);
                                table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_CENTER);
                                phrase = new Phrase("SNR MANAGER APPROVAL: ", pFontHeader1);
                                table.addCell(phrase);

                                table.getDefaultCell().setBackgroundColor(java.awt.Color.LIGHT_GRAY);
                                table.getDefaultCell().setColspan(2);
                                /*
                                //table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase("SIGNATURE\n\n\n", pfontData);
                                table.addCell(phrase);
                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);

                                table.getDefaultCell().setBackgroundColor(java.awt.Color.LIGHT_GRAY);

                               // table.getDefaultCell().setColspan(2);
                                //table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase("SIGNATURE\n\n\n", pfontData);
                                table.addCell(phrase);
                                BufferedImage bimg = DigitalSignatureFactory.getSignature("7915", connectDB);
                                //resizeBufferedImage
                                table.addCell(com.lowagie.text.Image.getInstance(resizeBufferedImage(bimg, 100, 100), null, false));
                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);

//                                ///Actual Signatures
//                              //  table.getDefaultCell().setColspan(2);
//                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
//                                table.addCell(com.lowagie.text.Image.getInstance(DigitalSignatureFactory.getSignature("7915", connectDB), null, false));
//                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);
//
//                                table.getDefaultCell().setColspan(2);
//                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
//                                phrase = new Phrase("SIGNATURE\n\n\n", pfontData);
//                                table.addCell(phrase);
//                                table.getDefaultCell().setBackgroundColor(java.awt.Color.white);
                                 */
                            }

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

                            Object issuedTo = null;
                            Object receivedBy = null;

                            String usr = "";

//                            if (ConnectionProperties.getUsername() == null) {
//                                usr = personrqing;
//                            } else {
//                                usr = ConnectionProperties.getUsername().toUpperCase();
//                            }
                            phrase = new Phrase("Printed By: " + usr, pFontHeader);
                            table.addCell(phrase);

                            docPdf.add(table);

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

    //SELECT * FROM fleet.transport_rq_invoice 
    private String vehicleMileage(String reference, Connection connectDB) {

        //String reference = request.getParameter("refno");
        // String rqtype = request.getParameter("rqtype");
        String result = "";

        String sql = "SELECT current_mileage FROM fleet.vehicle_mileage WHERE refno =? limit 1;";

        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            pst.setObject(1, reference);
            ResultSet rset = pst.executeQuery();

            if (rset.next()) {
                result = rset.getObject(1).toString();
            }

        } catch (SQLException ex) {
            result = ex.getMessage();
            ex.printStackTrace();
        }

        return result;
    }

    
    
    private Object[] getFuelInv(String refno, Connection connectDB) {

        com.mtrh.mtportal.sys.CardTransactions card = new com.mtrh.mtportal.sys.CardTransactions();

        return card.getFleetRInvoice(refno, connectDB);
    }

    private String getCardamt(String cardno, Connection connectDB) {
 
        com.mtrh.mtportal.sys.CardTransactions card = new com.mtrh.mtportal.sys.CardTransactions();

        return card.getCardAmount(cardno, connectDB);
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
//   

    public void Trebuchet() {
        //BaseFont base = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.WINANSI);
//Font font = new Font(base, 11f, Font.BOLD);

    }
}
