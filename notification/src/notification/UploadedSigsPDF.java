/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notification;

/**
 *
 * @author owner
 */
//package com.afrisoftech.hospinventory.mtrhreports;
//import lib.CurrencyFormatter;
//import com.afrisoftech.lib.PDFRenderer;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
//import org.netbeans.lib.sql.pool.PooledConnectionSource;

public class UploadedSigsPDF
        implements Runnable {

    int cnt = 0;
    String branch = null;
    String ministry = null;
    String trimmed_subhead = null;
    String trimmed_subitem = null;
    String trimmed_head = null;
    double used_amt_proc_plan = 0.0D;
    String allocated_amt = null;
    String subhead_quat_amt = null;
    String selectSupp = null;
    String OrderNo = null;
    String beginDate = null;
    String endDate = null;
    String procured_plan_amt = null;
    public static Connection connectDB = null;
    public String dbUserName = null;
//    PooledConnectionSource pConnDB = null;
    boolean threadCheck = true;
    PdfWriter pdfWriter;
    Thread threadSample;
    Font pFontHeader1 = FontFactory.getFont("Helvetica", 16.0F, 1);
    Font pFontHeader11 = FontFactory.getFont("Helvetica", 12.0F, 1);
    Font pFontHeader = FontFactory.getFont("Helvetica", 8.0F, 1);
    Font pFontHeader2 = FontFactory.getFont("Helvetica", 8.0F, 0);
    Font pFontHeader3 = FontFactory.getFont("Helvetica", 13.0F, 1);
    Font pFontHeader5 = FontFactory.getFont("Helvetica", 13.0F, 1);
    Font pFontHeader6 = FontFactory.getFont("Helvetica", 11.0F, 1);
    Font pFontHeader7 = FontFactory.getFont("Helvetica", 8.0F, 0);
    Font pFontHeader9 = FontFactory.getFont("Helvetica", 9.0F, 0);
    Font pFontHeader91 = FontFactory.getFont("Helvetica", 9.0F, 1);
    Runtime rtThreadSample = Runtime.getRuntime();
    Process prThread;
    String financial_yr = null;
    String period = null;
    String votes = null;
    String departmt = null;

    public void UploadedSigsPDF(Connection connDb) {

        connectDB = connDb;

        this.threadSample = new Thread(this, "SampleThread");

        System.out.println("threadSample created");

        this.threadSample.start();

        System.out.println("threadSample fired");
    }

    public static void main(String[] args) {
    }

    public void run() {
        System.out.println("System has entered running mode");
        while (this.threadCheck) {
            System.out.println("O.K. see how we execute target program");

            generatePdf(this.selectSupp);
            try {
                System.out.println("Right, let's wait for task to complete of fail");

                Thread.currentThread();
                Thread.sleep(200L);

                System.out.println("It's time for us threads to get back to work after the nap");
            } catch (InterruptedException IntExec) {
                System.out.println(IntExec.getMessage());
            }
            this.threadCheck = false;

            System.out.println("We shall be lucky to get back to start in one piece");
        }
        if (!this.threadCheck) {
            Thread.currentThread().stop();
        }
    }

    public String getDateLable() {
        String date_label = null;

        String month_now_strs = null;

        String date_now_strs = null;

        String year_now_strs = null;

        String minute_now_strs = null;

        String hour_now_strs = null;

        Runtime rt = Runtime.getRuntime();

        Calendar calinst = Calendar.getInstance();

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
                break;
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

    public void generatePdf(String memNo) {
        Calendar cal = Calendar.getInstance();

        java.util.Date dateStampPdf = cal.getTime();

        String pdfDateStamp = dateStampPdf.toString();
        try {
            File tempFile = File.createTempFile("REP" + getDateLable() + "_", ".pdf");

            tempFile.deleteOnExit();

            Runtime rt = Runtime.getRuntime();

            String debitTotal = null;

            String creditTotal = null;

            Document docPdf = new Document();
            try {
                try {
                    PdfWriter.getInstance(docPdf, new FileOutputStream(tempFile));

                    String compName = null;
                    String date = null;
                    try {
                        Statement st6 = connectDB.createStatement();
                        Statement st4 = connectDB.createStatement();

                        HeaderFooter localHeaderFooter = new HeaderFooter(new Phrase(" - Page:", this.pFontHeader), true);
                    } catch (SQLException SqlExec) {
                        SqlExec.printStackTrace();
                        JOptionPane.showMessageDialog(new JFrame(), SqlExec.getMessage());
                    }
                    docPdf.open();

                    String Username = null;
                    int numColumns = 9;
                    try {
                        Calendar calendar = Calendar.getInstance();

                        long dateNow = calendar.getTimeInMillis();

                        java.sql.Date datenowSql = new java.sql.Date(dateNow);

                        System.out.println(datenowSql.toString());

                        PdfPTable table21 = new PdfPTable(6);

                        int[] headerwidths21 = {15, 15, 30, 15, 15, 15};

                        table21.setWidths(headerwidths21);

                        table21.setWidthPercentage(100.0F);

                        table21.getDefaultCell().setBorder(2);

                        table21.getDefaultCell().setColspan(7);

                        Phrase phrase = new Phrase();

                        table21.getDefaultCell().setColspan(1);
                        try {
                            Image img = Image.getInstance(System.getProperty("user.dir") + "/COMPANY_LOG.jpg");
                            //Image imgWaterMark = Image.getInstance(System.getProperty("company.watermark"));
                            table21.getDefaultCell().setColspan(6);
                            table21.getDefaultCell().setHorizontalAlignment(1);
                            table21.getDefaultCell().setFixedHeight(50.0F);
                            //table21.addCell(Image.getInstance(System.getProperty("user.dir") + "/COMPANY_LOGO.jpg"));
                            table21.getDefaultCell().setHorizontalAlignment(1);
                            table21.getDefaultCell().setFixedHeight(25.0F);
                            Statement st3 = connectDB.createStatement();
                            ResultSet rset3 = st3.executeQuery("SELECT DISTINCT hospital_name FROM pb_hospitalprofile");
                            while (rset3.next()) {
                                this.branch = rset3.getObject(1).toString();
                                table21.getDefaultCell().setColspan(6);

                                table21.getDefaultCell().setHorizontalAlignment(1);
                                phrase = new Phrase(rset3.getObject(1).toString(), this.pFontHeader11);
                                table21.addCell(phrase);
                            }
                            table21.getDefaultCell().setColspan(6);
                            table21.getDefaultCell().setHorizontalAlignment(1);
                            phrase = new Phrase("UPLOADED SIGNATURES", this.pFontHeader11);
                            table21.addCell(phrase);
                        } catch (SQLException SqlExec) {
                            JOptionPane.showMessageDialog(new JFrame(), SqlExec.getMessage());
                        }
                        docPdf.add(table21);
                    } catch (BadElementException BadElExec) {
                        JOptionPane.showMessageDialog(new JFrame(), BadElExec.getMessage());
                    }
                    try {
                        PdfPTable table1 = new PdfPTable(10);
                        table1.getDefaultCell().setPadding(3.0F);

                        int[] headerwidths1 = {15, 10, 10, 10, 10, 10, 10, 10, 10, 12};

                        table1.setWidths(headerwidths1);

                        table1.setWidthPercentage(100.0F);

                        table1.getDefaultCell().setBackgroundColor(Color.WHITE);
                        table1.getDefaultCell().setBorderColor(Color.WHITE);
                        Phrase phrase = new Phrase("", this.pFontHeader);
                        try {

                            table1.getDefaultCell().setColspan(10);
                            table1.getDefaultCell().setHorizontalAlignment(0);
                            phrase = new Phrase(this.financial_yr, this.pFontHeader3);
                            table1.addCell(phrase);

                            Statement st3 = connectDB.createStatement();

                            Statement st2 = connectDB.createStatement();
                            Statement st11 = connectDB.createStatement();
                            Statement st4 = connectDB.createStatement();
                            Statement st5 = connectDB.createStatement();
                            Statement st6 = connectDB.createStatement();
                            int num_rows = 0;

                            table1.getDefaultCell().setColspan(2);
                            table1.getDefaultCell().setHorizontalAlignment(1);
                            phrase = new Phrase("User ID:", this.pFontHeader);
                            table1.addCell(phrase);

                            table1.getDefaultCell().setColspan(6);
                            table1.getDefaultCell().setHorizontalAlignment(1);
                            phrase = new Phrase("Signature:", this.pFontHeader);
                            table1.addCell(phrase);

                            table1.getDefaultCell().setColspan(2);
                            table1.getDefaultCell().setHorizontalAlignment(1);
                            phrase = new Phrase("Time uploaded:", this.pFontHeader);
                            table1.addCell(phrase);

                            table1.getDefaultCell().setColspan(10);
                            table1.getDefaultCell().setHorizontalAlignment(0);
                            phrase = new Phrase("", this.pFontHeader7);
                            table1.addCell(phrase);

                            ResultSet rset41 = st2.executeQuery("SELECT distinct(document_ref_no),document_data, data_capture_time::timestamp(0) FROM funsoft_image_graphics WHERE  document_source = 'DIGITAL_AUTH_SIGNATURE' ORDER BY 1");

                            int m = 1;
                            while (rset41.next()) {
                                num_rows += 1;
                                InputStream is = rset41.getBinaryStream(2);
                                BufferedImage img = ImageIO.read(is);

                                table1.getDefaultCell().setBorderColor(Color.white);
                                table1.getDefaultCell().setColspan(2);
                                table1.getDefaultCell().setHorizontalAlignment(2);
                                phrase = new Phrase(num_rows + "." + rset41.getString("document_ref_no"), this.pFontHeader);
                                table1.addCell(phrase);

                                table1.getDefaultCell().setBorderColor(Color.white);
                                table1.getDefaultCell().setColspan(6);
                                table1.getDefaultCell().setHorizontalAlignment(1);
                                table1.addCell(com.lowagie.text.Image.getInstance(img, null, false));

//0779879
                                table1.getDefaultCell().setColspan(2);
                                table1.getDefaultCell().setHorizontalAlignment(0);
                                phrase = new Phrase(rset41.getObject("data_capture_time").toString(), this.pFontHeader7);
                                table1.addCell(phrase);
                                
                                
                                 table1.getDefaultCell().setColspan(10);
                                table1.getDefaultCell().setHorizontalAlignment(0);
                                phrase = new Phrase("", this.pFontHeader7);
                                table1.addCell(phrase);

                            }

                            table1.getDefaultCell().setColspan(10);
                            table1.getDefaultCell().setHorizontalAlignment(2);
                            phrase = new Phrase("", this.pFontHeader91);
                            table1.addCell(phrase);

                            docPdf.add(table1);
                        } catch (SQLException SqlExec) {
                            SqlExec.printStackTrace();

                            JOptionPane.showMessageDialog(new JFrame(), SqlExec.getMessage());
                        }
                    } catch (BadElementException BadElExec) {
                        JOptionPane.showMessageDialog(new JFrame(), BadElExec.getMessage());
                    }
                } catch (FileNotFoundException fnfExec) {
                    JOptionPane.showMessageDialog(new JFrame(), fnfExec.getMessage());
                }
            } catch (DocumentException lwDocexec) {
                JOptionPane.showMessageDialog(new JFrame(), lwDocexec.getMessage());
            }
            docPdf.close();
//      PDFRenderer.renderPDF(tempFile);
            Desktop.getDesktop().open(tempFile);
        } catch (IOException IOexec) {
            JOptionPane.showMessageDialog(new JFrame(), IOexec.getMessage());
        }
    }
}
