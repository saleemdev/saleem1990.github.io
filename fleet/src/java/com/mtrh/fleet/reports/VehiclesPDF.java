/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtrh.fleet.reports;

/**
 *
 * @author owner
 */
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.mtrh.fleet.dao.ConnectionProperties;
import com.mtrh.fleet.dao.TestClass;
import com.mtrh.fleet.dao.VehicleDao;
import java.awt.Desktop;
//import dbadmin.PDFRenderer;
//import dbadmin.CurrencyFormatter;
//import dbadmin.StoreFunctions;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
//import static reports.MtrhSthirteenPdf.connectDB;

public class VehiclesPDF implements java.lang.Runnable {

    private String ref_no = null;

    com.mtrh.fleet.dao.DBObject dbObject;
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
    //   com.lowagie.text.ParagraphFont pgraph = Paragraph();
    java.lang.Runtime rtThreadSample = java.lang.Runtime.getRuntime();
    java.lang.Process prThread;
    private String filepath;

    public void VehiclesPDF(java.sql.Connection connDb) {
        //public void IssuedItemsPdf(java.sql.Connection connDb) {

        dbObject = new com.mtrh.fleet.dao.DBObject();

        connectDB = connDb;
        //  beginDate = begindate;
        //  endDate = endate;

//        threadSample = new java.lang.Thread(this, "SampleThread");
//
//        System.out.println("threadSample created");
//
//        threadSample.start();
//
//        System.out.println("threadSample fired");
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
        TestClass.setPath2pdf(name);
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
            String filename = tDir+"REP", extension = "_.pdf";

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

                    //   table1.getDefaultCell().setBorder(Rectangle.BOTTOM);
                    // table1.getDefaultCell().setColspan(7);
                    Phrase phrase = new Phrase();

                    table1.getDefaultCell().setBorderColor(java.awt.Color.blue);

                    try {

                        java.sql.Statement st3 = connectDB.createStatement();
                        java.sql.ResultSet rset3 = st3.executeQuery("SELECT DISTINCT name,box_no,main_tel,main_fax,logo FROM institution_profile");
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
                            String addr = "P.O. Box " + rset3.getObject(2).toString() + " - Tel: " + rset3.getObject(3).toString() + " - Fax " + rset3.getObject(3).toString();
                            phrase = new Phrase(rset3.getObject(1).toString() + "\n\n\n" + addr, pFontHeader);
                            table1.addCell(phrase);

                            //      phrase = new Phrase("P.O. Box " + rset3.getObject(2).toString() + " - Tel: " + rset3.getObject(3).toString() + " - Fax " + rset3.getObject(3).toString(), pFontHeader1);
                            //  table1.addCell(phrase);
                        }

                    } catch (java.sql.SQLException SqlExec) {

                        SqlExec.printStackTrace();

                        javax.swing.JOptionPane.showMessageDialog(new javax.swing.JFrame(), SqlExec.getMessage());

                    }
                    table1.addCell(phrase);
                    docPdf.add(table1);
                    PDFPAth.setPath2pdf(filepath);
                    System.err.println("After settig the header " + PDFPAth.getPath2pdf());

                    com.lowagie.text.HeaderFooter footer = new com.lowagie.text.HeaderFooter(new Phrase("Fleet Items - Page: ", pFontHeader), true);// FontFactory.getFont(com.lowagie.text.FontFactory.HELVETICA, 12, Font.BOLDITALIC,java.awt.Color.blue));

                    docPdf.setFooter(footer);

                    //              docPdf.open();
                    try {

                        com.lowagie.text.pdf.PdfPTable table = new com.lowagie.text.pdf.PdfPTable(4);

                        int headerwidths[] = {25, 25, 50, 20};

                        table.setWidths(headerwidths);

                        table.setWidthPercentage((100));

                        table.setHeaderRows(2);
                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_CENTER);
                        table.getDefaultCell().setColspan(4);
                        phrase = new Phrase("FLEET REGISTER", pFontHeader);
                        table.addCell(phrase);
                        table.getDefaultCell().setBorder(Rectangle.BOTTOM);
                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                        table.getDefaultCell().setColspan(2);

//                        Phrase phrase = new Phrase("Transfered Items", pFontHeader);
                        date = com.mtrh.fleet.dao.ServerTime.serverDate(connectDB);

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

                            //javax.swing.JOptionPane.showMessageDialog(new javax.swing.JFrame(), SqlExec.getMessage());
                        }
                        table.getDefaultCell().setBorder(Rectangle.BOX);
                        table.getDefaultCell().setColspan(1);
                        phrase = new Phrase("Fleet No", pFontHeader);
                        table.addCell(phrase);
                        phrase = new Phrase("Registration No", pFontHeader);
                        table.addCell(phrase);

                        table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_RIGHT);

                        phrase = new Phrase("Model/Make Details", pFontHeader);
                        table.addCell(phrase);

                        // phrase = new Phrase("Price",pFontHeader);
                        //table.addCell(phrase);
                        phrase = new Phrase("Vehicle Status", pFontHeader);
                        table.addCell(phrase);

                        try {

                            PreparedStatement pstV1 = connectDB.prepareStatement("SELECT DISTINCT vehicle_status FROM fleet.vehicle_register ORDER BY 1");
                            ResultSet rsetV1 = pstV1.executeQuery();

                            table.getDefaultCell().setBackgroundColor(java.awt.Color.WHITE);
                            table.getDefaultCell().setBorderColor(java.awt.Color.blue);

                            VehicleDao vdao = new VehicleDao();
                            while (rsetV1.next()) {

                                String stat = rsetV1.getString(1);

                                table.getDefaultCell().setColspan(4);
                                table.getDefaultCell().setBorder(Rectangle.BOX);
                                table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                phrase = new Phrase("Status: " + stat, pFontHeader1);
                                table.addCell(phrase);

                                PreparedStatement pstV = connectDB.prepareStatement("SELECT fleetno, regno FROM fleet.vehicle_register WHERE vehicle_status =? ORDER BY 2");
                                pstV.setObject(1, stat);
                                ResultSet rsetV = pstV.executeQuery();
                                while (rsetV.next()) {
                                    String fleetno = rsetV.getString(1), regno = rsetV.getString(2);

                                    table.getDefaultCell().setColspan(1);
                                    table.getDefaultCell().setBorder(Rectangle.BOX);
                                    table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                    phrase = new Phrase(fleetno, pFontHeader1);
                                    table.addCell(phrase);

                                    table.getDefaultCell().setBorder(Rectangle.BOX);
                                    table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                    phrase = new Phrase(regno, pFontHeader1);
                                    table.addCell(phrase);

                                    Object[] vdetails = vdao.vehicleDetails(connectDB, fleetno);

                                    String manufacturingDetails = "Make: " + vdetails[10].toString() + " " + vdetails[2].toString() + "\nYOM: " + vdetails[3].toString() + "\nFuel Used: " + vdetails[7].toString() + "\nEngine CC: " + vdetails[8].toString() + "\nEngine No:" + vdetails[5].toString() + "\nCHassis No:" + vdetails[4].toString();
///presentation
                                    table.getDefaultCell().setBorder(Rectangle.BOX);
                                    table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                    phrase = new Phrase(manufacturingDetails, pFontHeader1);
                                    table.addCell(phrase);

                                    String status = "Condition: " + vdetails[9].toString() + "\nCurrent Mileage: (Km)";
                                    table.getDefaultCell().setBorder(Rectangle.BOX);
                                    table.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_LEFT);
                                    phrase = new Phrase(status, pFontHeader1);
                                    table.addCell(phrase);
                                }
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

                            phrase = new Phrase("Printed By: "+ConnectionProperties.getUsername().toUpperCase(), pFontHeader);
                            table.addCell(phrase);

                            docPdf.add(table);

                        } catch (java.sql.SQLException SqlExec) {
                            SqlExec.printStackTrace();
                            javax.swing.JOptionPane.showMessageDialog(new javax.swing.JFrame(), SqlExec.getMessage());

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

            javax.swing.JOptionPane.showMessageDialog(new javax.swing.JFrame(), IOexec.getMessage());

        }

    }
//   
}
