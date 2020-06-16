/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtrh.fleet.communication;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
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
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
//import sys.LoginDialog;

/**
 *
 * @author saleem
 */
public class EmailFunctions {

    public static Connection connectDB;

    public String getInstitutionEmail(Connection conndb) {

        connectDB = conndb;
        String email = "";
        try {
            java.sql.PreparedStatement pstmtUser = connectDB.prepareStatement("SELECT email FROM institution_profile  ");

            java.sql.ResultSet rsetUser = pstmtUser.executeQuery();

            while (rsetUser.next()) {

                email = rsetUser.getString(1).toLowerCase();

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(new java.awt.Frame(), ex.getMessage());

        }
        return email;
    }

    public void SendOrderEmail(String to, String text, String fileaddr, String orderno, Connection conn) {
// Recipient's email ID needs to be mentioned.
        //  String to = "ruthmax341@gmail.com";

        // Sender's email ID needs to be mentioned
        //   String from = this.hostMail();
        String from = this.getInstitutionEmail(conn);

        System.out.println("MailFrom " + from + " TO " + to);
        System.out.println(from.indexOf("@", 0));;
        final String username = "bizpokalerts";//change accordingly
        final String password = "info27613716";//change accordingly

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
        props.put("mail.smtp.password", password);

        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

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
            message.setSubject("New Order " + orderno);

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setText(text + "\n\n\n\n\n\n Powered by Bizpok Ventures");

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            String filename = fileaddr;
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));

            //messageBodyPart.setFileName(filename);
            messageBodyPart.setFileName("REF_NO " + orderno + ".pdf");
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

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

        // String from = this.getInstitutionEmail(conn);
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
      //  props.put("mail.smtp.port", "25");
        
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
        props.put("mail.smtp.socketFactory.fallback", "false");
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
            message.setSubject("MTRH FMS ALERT: " + refno);

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setText(text + "\n\n\n\n\n\n Powered by Bizpok Ventures");

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            String filename = fileaddr;
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            // messageBodyPart.setFileName(filename);
            messageBodyPart.setFileName("REF_NO/" + refno + ".pdf");
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            // Send message
            Transport transport = session.getTransport("smtp");
            transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
        }

    }

    public void SendNewYrEmailAlerts(String to, String text, String fileaddr, String refno, Connection conn) {

        String from = this.getInstitutionEmail(conn);
        System.out.println("MailFrom " + from + " TO " + to);

        System.out.println(from.indexOf("@", 0));;

        final String username = "bizpokalerts";//change accordingly
        final String password = "info27613716";//change accordingly

        // Assuming you are sending email through relay.jangosmtp.net
        String host = this.hostIP();

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
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject("New Year Message From BIZPOK VENTURES");

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setText(text + "\n\n\n\n\n\n Powered by Bizpok Ventures");

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
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
        }

    }

    public String hostMail() {

        return "adeptwriter1990@gmail.com";
    }

    public String hostIP() {

         return "smtp.gmail.com";
       // return "www.mtrh.info";
       //return "41.89.183.8";
       
       
    //   return "www.mtrh.info";
    }

    public static String getProp() {
        String Br = "";
        Properties prop = System.getProperties();
        System.err.println(prop.getProperty("Br"));
        Br = prop.getProperty("Br");

        // "Thitimaa";
        return Br;

    }

    public void SendPlainEmailAlerts(String to, String text, String refno, Connection conn) {

        //  String from = this.getInstitutionEmail(conn);
        String from = "bizpokalerts@gmail.com";
        System.out.println("MailFrom " + from + " TO " + to);

        System.out.println(from.indexOf("@", 0));;

        final String username = "bizpokalerts";//change accordingly
        final String password = "info27613716";//change accordingly
        // Assuming you are sending email through relay.jangosmtp.net
        String host = this.hostIP();
        //   String host = "www.mtrh.info";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        //props.put("mail.smtp.port", "25");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", host);

        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
        props.put("mail.smtp.socketFactory.fallback", "false");
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
            message.setSubject("MTRH REGISTRY ALERT: " + refno);

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

        } catch (MessagingException e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
        }

    }

    public static void main(String args[]) {
        System.err.println("I am at runtime bitch");
        
        EmailFunctions em = new EmailFunctions();
        
        em.SendPlainEmailAlerts("caremacharia@gmail.com", "Hello Mash\nOur MTRH.GO.KE Mail server has been a real inconvenience, so I am using a native Gmail API to send this email. We will address the issue next week and get it sorted out.", "67736", null);
    }

}
