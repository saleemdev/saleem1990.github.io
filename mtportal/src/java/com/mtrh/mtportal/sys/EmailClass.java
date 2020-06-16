/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtrh.mtportal.sys;

/**
 *
 * @author owner
 */
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import servlets.PropertiesClass;

//import com.connectpool.DatabaseConnection;
public class EmailClass {

    private Object dbServerIp;
    private Object dbPort;
    private Object activeDatabase;

//   java.sql.Connection con = DatabaseConnection.getConnection();
    public String getInstitutionEmail(java.sql.Connection con) {

        String email = "";
        try {
            java.sql.PreparedStatement pstmtUser = con.prepareStatement("SELECT email FROM institution_profile  ");

            java.sql.ResultSet rsetUser = pstmtUser.executeQuery();

            while (rsetUser.next()) {

                email = rsetUser.getString(1).toLowerCase();

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            //javax.swing.JOptionPane.showMessageDialog(new java.awt.Frame(), ex.getMessage());

        }
        return email;
    }

    ///
    public String getUserEmail() {

//        String email = "";
//        try {
//            java.sql.PreparedStatement pstmtUser = con.prepareStatement("SELECT email_name from email_table");
//
//            java.sql.ResultSet rsetUser = pstmtUser.executeQuery();
//
//            while (rsetUser.next()) {
//
//                email = rsetUser.getString(1).toLowerCase();
//
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            //javax.swing.JOptionPane.showMessageDialog(new java.awt.Frame(), ex.getMessage());
//
//        }
//        return email;
        return "";
    }
    
    public static void SendAttachmentEmailAlerts(String to, String text, String refno, Connection conn, String fileaddr) {
        //  String from = this.getInstitutionEmail(conn);
        String from = "resourcing@mtrh.go.ke";
        System.out.println("MailFrom " + from + " TO " + to);

        System.out.println(from.indexOf("@", 0));;

        System.err.println("Sending email now..");
        final String username = "resourcing";//change accordingly
        final String password = "resourcing2017";//change accordingly
        // Assuming you are sending email through relay.jangosmtp.net
        String host = hostIP();
        //   String host = "www.mtrh.info";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "25");
        //props.put("mail.smtp.socketFactory.port", "465");
        //props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
        //props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", password);

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject("ALERT: " + refno);

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
           // messageBodyPart.setText(text + "\n\n\n\n\n\n Powered by MTRH ICT");
            messageBodyPart.setContent(text+"\n\n\n\n\n\n Powered by MTRH ICT", "text/html");

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            String filename = fileaddr;
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            // Send message
            //message.setHeader("", username);
            Transport transport = session.getTransport("smtp");
            transport.send(message);

            System.out.println("Sent message successfully....");
         //   setEmailSentStatus("Success");

        } catch (MessagingException e) {
            //throw new RuntimeException(e);
           // setEmailSentStatus("Failure");
            e.printStackTrace();
        }

    }


    public static void SendPlainEmailAlerts2(String to, String text, String refno, Connection conn) {
        //  String from = this.getInstitutionEmail(conn);
        String from = "resourcing@mtrh.go.ke";
        System.out.println("MailFrom " + from + " TO " + to);

        System.out.println(from.indexOf("@", 0));;

        System.err.println("Sending email now..");
        final String username = "resourcing";//change accordingly
        final String password = "resourcing2017";//change accordingly
        // Assuming you are sending email through relay.jangosmtp.net
        String host = hostIP();
        //   String host = "www.mtrh.info";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "25");
        //props.put("mail.smtp.socketFactory.port", "465");
        //props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
        //props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", password);

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject("ALERT: " + refno);

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
           // messageBodyPart.setText(text + "\n\n\n\n\n\n Powered by MTRH ICT");
            messageBodyPart.setContent(text+"\n\n\n\n\n\n Powered by MTRH ICT", "text/html");

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            // String filename = fileaddr;
            //DataSource source = new FileDataSource(filename);
            //messageBodyPart.setDataHandler(new DataHandler(source));
            //messageBodyPart.setFileName(filename);
            //multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            // Send message
            //message.setHeader("", username);
            Transport transport = session.getTransport("smtp");
            transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
        }

    }
   

    //
    public String getUsePassword() {

//        String pass_word = "";
//        try {
//            java.sql.PreparedStatement pstmtUser = con.prepareStatement("SELECT pass_word from email_table");
//
//            java.sql.ResultSet rsetUser = pstmtUser.executeQuery();
//
//            while (rsetUser.next()) {
//
//                pass_word = rsetUser.getString(1).toLowerCase();
//
//            }
//        } catch (SQLException ex)
//        {
//            ex.printStackTrace();
//            //javax.swing.JOptionPane.showMessageDialog(new java.awt.Frame(), ex.getMessage());
//
//        }
        return "";
    }

    ///
    // public void SendOrderEmail(String to, String text, String fileaddr, String orderno, Connection conn) 
    public void SendOrderEmail(String to, String text) {
// Recipient's email ID needs to be mentioned.
        //  String to = "ruthmax341@gmail.com";

        // Sender's email ID needs to be mentioned
        //   String from = this.hostMail();
        // String from ="elvistarus@mtrh.go.ke";
        final String from = "registry_alerts@mtrh.go.ke";//this.getUserEmail();
        final String username = "registry_alerts";
        final String pass_word = "registry2019";//this.getUsePassword();

        System.out.println("MailFrom " + from + " TO " + to);
        System.out.println(from.indexOf("@", 0));;
        //final String username = "bizpokalerts";//change accordingly
        // final String password = "80885147";//change accordingly

        // final String frommail ="elvistarus@mtrh.go.ke";
        // Assuming you are sending email through relay.jangosmtp.net
        String host = this.hostIP();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        //props.put("mail.smtp.port", "587");
        props.put("mail.smtp.port", "25");
        //props.put("mail.smtp.socketFactory.port", "465");
        //props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
        //props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass_word);

        // props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.ssl.trust", host);

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, pass_word);
            }
        });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject("New Task ");

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setText(text + "\n\n\n\n\n\n MTRH ");

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            /*
            messageBodyPart = new MimeBodyPart();
            String filename = fileaddr;
           DataSource source = new FileDataSource(filename);
           messageBodyPart.setDataHandler(new DataHandler(source));
            
           messageBodyPart.setFileName(filename);
           messageBodyPart.setFileName("REF_NO.pdf");
            multipart.addBodyPart(messageBodyPart);
             */
            // Send the complete message parts
            message.setContent(multipart);
            // message.addRecipient(Message.RecipientType.CC, adrs);

            // Send message
            Transport transport = session.getTransport("smtp");
            transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
    }

    public void SendEmailAlerts(String to, String text, String fileaddr, String refno, Connection conn) {

        //  String from = this.getInstitutionEmail(conn);
        String from = "registry_alerts@mtrh.go.ke";
        System.out.println("MailFrom " + from + " TO " + to);

        System.out.println(from.indexOf("@", 0));;

        final String username = "registry_alerts";//change accordingly
        final String password = "registry2019";//change accordingly

        // Assuming you are sending email through relay.jangosmtp.net
        String host = this.hostIP();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "25");
        //props.put("mail.smtp.socketFactory.port", "465");
        //props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
        //props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.ssl.trust", host);

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject("Alert Info REF: " + refno);

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setText(text + "\n\n\n\n\n\n Powered by MTRH ICT");

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            String filename = fileaddr;
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            // Send message
            //message.setHeader("", username);
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
        }

    }

    private static boolean netIsAvailable() {
        try {
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }

    public void SendPlainEmailAlerts(String to, String text, String refno, Connection conn) {

        String from = "registry_alerts@mtrh.go.ke";

        // persistNotification(refno, text, from, to, conn);
        //  String from = this.getInstitutionEmail(conn);
       // if (netIsAvailable()) {

            System.out.println("MailFrom " + from + " TO " + to);

            System.out.println(from.indexOf("@", 0));;

            System.err.println("Sending email right now..");
            final String username = "registry_alerts";//change accordingly
            final String password = "registry2019";//change accordingly
            // Assuming you are sending email through relay.jangosmtp.net
            String host = this.hostIP();
            //   String host = "www.mtrh.info";
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "25");
            props.put("mail.smtp.ssl.trust", host);
            //props.put("mail.smtp.port", "587");
            //props.put("mail.smtp.socketFactory.port", "465");
            //props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
            //props.put("mail.smtp.socketFactory.fallback", "false");
            props.put("mail.smtp.user", from);
            props.put("mail.smtp.password", password);

            // Get the Session object.
            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            try {
                // Create a default MimeMessage object.
                Message message = new MimeMessage(session);

                // Set From: header field of the header.
                message.setFrom(new InternetAddress(from));

                // Set To: header field of the header.
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(to));

                // Set Subject: header field
                message.setSubject(refno);

                // Create the message part
                BodyPart messageBodyPart = new MimeBodyPart();

                // Now set the actual message
                messageBodyPart.setText(text + "\n\n\n\n\n\n Powered by MTRH ICT");

                // Create a multipar message
                Multipart multipart = new MimeMultipart();

                // Set text message part
                multipart.addBodyPart(messageBodyPart);

                // Part two is attachment
                messageBodyPart = new MimeBodyPart();
                // String filename = fileaddr;
                //DataSource source = new FileDataSource(filename);
                //messageBodyPart.setDataHandler(new DataHandler(source));
                //messageBodyPart.setFileName(filename);
                //multipart.addBodyPart(messageBodyPart);

                // Send the complete message parts
                message.setContent(multipart);

                // Send message
                //message.setHeader("", username);
                Transport transport = session.getTransport("smtp");
                transport.send(message);

                System.out.println("Sent message successfully....");

                //  AppendPersistenceStatus(refno, "Success", conn);
            } catch (Exception e) {
                // AppendPersistenceStatus(refno, e.getMessage(), conn);
                e.printStackTrace();
            }

        //}

    }
    
    
    public static void SendPlainEmailAlertsV2(String to, String text, String refno, Connection conn) {
        //  String from = this.getInstitutionEmail(conn);
        String from = "resourcing@mtrh.go.ke";
        System.out.println("MailFrom " + from + " TO " + to);

        System.out.println(from.indexOf("@", 0));;

        System.err.println("Sending email now..");
        final String username = "resourcing";//change accordingly
        final String password = "resourcing2017";//change accordingly
        // Assuming you are sending email through relay.jangosmtp.net
        String host = hostIP();
        //   String host = "www.mtrh.info";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "25");
        //props.put("mail.smtp.socketFactory.port", "465");
        //props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
        //props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", password);

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject("ALERT: " + refno);

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
           // messageBodyPart.setText(text + "\n\n\n\n\n\n Powered by MTRH ICT");
            messageBodyPart.setContent(text+"\n\n\n\n\n\n Powered by MTRH ICT", "text/html");

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            // String filename = fileaddr;
            //DataSource source = new FileDataSource(filename);
            //messageBodyPart.setDataHandler(new DataHandler(source));
            //messageBodyPart.setFileName(filename);
            //multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            // Send message
            //message.setHeader("", username);
            Transport transport = session.getTransport("smtp");
            transport.send(message);

            System.out.println("Sent message successfully to...."+to);

        } catch (MessagingException e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
        }

    }

    public File getStoredPDF(Connection connDB, String documentRefNumber)
            throws FileNotFoundException, com.lowagie.text.DocumentException {
        File tempFile = null;
        File tempFile2 = null;
        try {
            tempFile2 = File.createTempFile("REP" + documentRefNumber + "_", ".pdf");

            com.itextpdf.text.Document document = new com.itextpdf.text.Document();

            PdfCopy copy = null;
            try {
                copy = new PdfCopy(document, new FileOutputStream(tempFile2));
            } catch (com.itextpdf.text.DocumentException ex) {
                ex.printStackTrace();
            }
            document.open();

            PdfReader reader = null;
            try {
                int bytePos = 0;
                PreparedStatement pstmtR = connDB.prepareStatement("SELECT DISTINCT uploaded_file, input_date FROM hr.docs  WHERE ref_no = ? ORDER BY 2");
                pstmtR.setString(1, documentRefNumber);
                ResultSet rs = pstmtR.executeQuery();
                while (rs.next()) {
                    tempFile = File.createTempFile("REP" + documentRefNumber + "_", ".pdf");
                    OutputStream fileIS = null;
                    tempFile.deleteOnExit();

                    fileIS = new FileOutputStream(tempFile);
                    ByteArrayOutputStream byteaStream = new ByteArrayOutputStream();
                    byte[] imgBytes = rs.getBytes(1);

                    byteaStream.write(imgBytes);

                    fileIS.write(imgBytes);

                    fileIS.close();
                    System.out.println(tempFile.getPath());

                    reader = new PdfReader(tempFile.getPath());

                    int n = reader.getNumberOfPages();
                    for (int page = 0; page < n;) {
                        try {
                            copy.addPage(copy.getImportedPage(reader, ++page));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    copy.freeReader(reader);
                    reader.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            document.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return tempFile2;
    }

    public static void SendMailUsingSMPT(InternetAddress[] to, String text, String refno, Connection conn, InternetAddress[] cc, InternetAddress[] bcc) {

        String from = "mtrhalerts@gmail.com";//this.getInstitutionEmail(conn);
        System.out.println("MailFrom " + from + " TO " + to);

        System.out.println(from.indexOf("@", 0));;

        persistNotification(refno, text, from, "", conn, to, cc, bcc);
        final String username = "mtrhalerts";//change accordingly
        final String password = "info27613716";//change accordingly

        // Assuming you are sending email through relay.jangosmtp.net
        String host = "smtp.gmail.com";//this.hostIP();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        //props.put("mail.smtp.socketFactory.port", "465");
        //props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
        //props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", password);

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO, to);

            message.addRecipients(Message.RecipientType.BCC, bcc);
            message.addRecipients(Message.RecipientType.CC, cc);

            // Set Subject: header field
            message.setSubject(refno);

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            //Header
            //  messageBodyPart.setHeader("TRY", "TEST");
            // Now set the actual message
            //   messageBodyPart.setText(text + "\n\n\n\n\n\n Powered by MTRH ICT");
            messageBodyPart.setContent("<h4>" + text + "</h4>", "text/html");

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
//            String fileaddr = "C:\\Users\\owner\\Desktop\\Docs\\LeaveForm.pdf";
//            messageBodyPart = new MimeBodyPart();
//            String filename = fileaddr;
//            DataSource source = new FileDataSource(filename);
//            messageBodyPart.setDataHandler(new DataHandler(source));
//            messageBodyPart.setFileName(refno + ".pdf");
//            multipart.addBodyPart(messageBodyPart);
            // Send the complete message parts
            message.setContent(multipart);

            // Send message
            Transport transport = session.getTransport("smtp");
            transport.send(message);

            System.out.println("Sent message successfully....");
            AppendPersistenceStatus(refno, "Success", conn);
        } catch (MessagingException e) {
            //throw new RuntimeException(e);
            AppendPersistenceStatus(refno, e.getMessage(), conn);
            e.printStackTrace();
        }

    }

    public static void AppendPersistenceStatus(String refno, String message, java.sql.Connection conn) {

        String sql = "UPDATE hr.notifications set status =? WHERE refno = ?";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setObject(1, message);
            pst.setObject(2, refno);
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public static void persistNotification(String refno, String message, String from, String to,  java.sql.Connection conn, InternetAddress[] toad, InternetAddress[] cc, InternetAddress[] bcc) {

        
        String[] allmail = retunAllMail(cc, bcc, toad);
        
        
        String sql = "INSERT INTO hr.notifications(refno, message, mail_from, mail_to) VALUES (?, ?,?,?)";

        for(int i=0;i<allmail.length;i++){
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setObject(1, refno);
            pst.setObject(2, message);
            pst.setObject(3, from);
            pst.setObject(4, allmail[i]);
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        }
    }

    public String hostMail() {

        return "adeptwriter1990@gmail.com";
    }

    public  static String hostIP() {

          String host = PropertiesClass.getpropValue("mail.mtrh").toString();
        // return "smtp.gmail.com";
        //return "172.16.108.86";
      //  return "41.89.183.10";
        ///  return "www.mtrh.info";

           return host;
    }

    public static String getProp() {
        String Br = "";
        Properties prop = System.getProperties();
        System.err.println(prop.getProperty("Br"));
        Br = prop.getProperty("Br");

        // "Thitimaa";
        return Br;

    }

    public java.sql.Connection connect(String user, String password) {
        Connection connection = null;

        try {

            java.lang.Class.forName("org.postgresql.Driver");

        } catch (java.lang.ClassNotFoundException cnf) {

            cnf.printStackTrace();

        }
        try {

            if (dbServerIp == null) {
                // dbServerIp = "localhost"jj;
                dbServerIp = servlets.PropertiesClass.getpropValue("dbServerIpAdd").toString();
            }

            if (dbPort == null) {
                dbPort = servlets.PropertiesClass.getpropValue("dbPort").toString();
            }

            if (activeDatabase == null) {
                activeDatabase = servlets.PropertiesClass.getpropValue("activeDatabase").toString();
            }
            //  System.out.println("jdbc:postgresql://" + this.dbServerIp + ":" + dbPort + "/" + activeDatabase + " " + userName + " " + passWord);
            connection = java.sql.DriverManager.getConnection("jdbc:postgresql://" + this.dbServerIp + ":" + dbPort + "/" + activeDatabase, user, password);

            if (connection != null) {
                System.err.println("connected");
            }
        } catch (java.sql.SQLException sqlExec) {

            //           msg = sqlExec.getMessage().toString();
            System.err.println(System.getProperty("user.dir"));

            sqlExec.printStackTrace();
            //   this.setVisible(true);

        }
        return connection;
    }

    public static String[] retunAllMail(InternetAddress[] to, InternetAddress[] myCcList, InternetAddress[] myBccList) {
        Vector<String> allmail = new Vector<String>();

        for (int i = 0; i < myCcList.length; i++) {
            //     System.err.println(myCcList[i]);

            allmail.add(myCcList[i].getAddress() + ",CC");
        }

        for (int i = 0; i < myBccList.length; i++) {
            // System.err.println(myBccList[i]);

            allmail.add(myCcList[i].getAddress() + ",BCC");
        }

        for (int i = 0; i < to.length; i++) {
            //  System.err.println(to[i]);

            allmail.add(to[i].getAddress() + ",TO");
        }

        String[] allmailarr = allmail.toArray(new String[allmail.size()]);

        for (int i = 0; i < allmailarr.length; i++) {
            System.err.println(allmailarr[i]);
        }

        return allmailarr;

    }

    public static void output(String[] arr) {
        //String joinedString = StringUtils.join(new Object[]{"a", "b", 1}, "-");
//System.out.println(joinedString);
        String[] array = {"cat", "mouse"};
        String delimiter = ",";
        String result = String.join(delimiter, arr);
        System.err.println(result);
    }
    
   

    public static void main(String[] args) {
        //output();
        try {
            InternetAddress[] myBccList = InternetAddress.parse("");
            InternetAddress[] myCcList = InternetAddress.parse("sarahkituyi@gmail.com,adeptwriter1990@gmail.com");
            InternetAddress[] to = InternetAddress.parse("salimmwaura@mtrh.go.ke,dsmwaura@gmail.com");

            String[] allmail = retunAllMail(to, myCcList, myBccList);
            for (int i = 0; i < allmail.length; i++) {
                System.err.println(allmail[i]+" Email Alone "+allmail[i].split(",")[0]);
            }
            //    SendMailUsingSMPT(to, "Salim here, Thank you for being an inspiration", "CC and BCC Working", null, myCcList, myBccList);
        } catch (AddressException ex) {
            ex.printStackTrace();
        }

    }

}
