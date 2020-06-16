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

//import com.connectpool.DatabaseConnection;
public class EmailFunctions {

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
    public void SendPlainEmail(String to, String text) {
// Recipient's email ID needs to be mentioned.
        //  String to = "ruthmax341@gmail.com";

        // Sender's email ID needs to be mentioned
        //   String from = this.hostMail();
        // String from ="elvistarus@mtrh.go.ke";
        final String from = this.getUserEmail();
        final String pass_word = this.getUsePassword();

        System.out.println("MailFrom " + from + " TO " + to);
        System.out.println(from.indexOf("@", 0));;
        //final String username = "mtrhalerts";//change accordingly
        final String password = "80885147";//change accordingly

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
                return new PasswordAuthentication(from, pass_word);
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
            messageBodyPart.setText(text + "\n\n\n\n\n\n MTRH Electronic Round Management System ");

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

            // Send message
            Transport transport = session.getTransport("smtp");
            transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
    }

    public static void SendPlainEmailAlerts(String to, String text, String refno, Connection conn) {
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
    //WithFile
    public static String EmailSentStatus;
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
            setEmailSentStatus("Success");

        } catch (MessagingException e) {
            //throw new RuntimeException(e);
            setEmailSentStatus("Failure");
            e.printStackTrace();
        }

    }

    public void SendEmailAlerts(String to, String text, String fileaddr, String refno, Connection conn) {

        //  String from = this.getInstitutionEmail(conn);
        String from = "usersupport@mtrh.go.ke";
        System.out.println("MailFrom " + from + " TO " + to);

        System.out.println(from.indexOf("@", 0));;

        final String username = "usersupport";//change accordingly
        final String password = "80885147";//change accordingly

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

    public static void SendMailUsingSMPT(String to, String text, String refno, Connection conn) {

        String from = "mtrhalerts@gmail.com";//this.getInstitutionEmail(conn);
        System.out.println("MailFrom " + from + " TO " + to);

        System.out.println(from.indexOf("@", 0));;

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
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

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
//            messageBodyPart = new MimeBodyPart();
//            String filename = fileaddr;
//            DataSource source = new FileDataSource(filename);
//            messageBodyPart.setDataHandler(new DataHandler(source));
//            messageBodyPart.setFileName(filename);
//            multipart.addBodyPart(messageBodyPart);
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

    public String hostMail() {

        return "adeptwriter1990@gmail.com";
    }

    public static String hostIP() {

        // return "smtp.gmail.com";
    //    return "41.89.183.10";
        return PropertiesFunctions.getpropValue("mail.smtp.host").toString();
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

   

    public static void main(String[] args) {

        // SendMailUsingSMPT("dsmwaura@gmail.com", "Salim here, Been testing this Native Mail from Java, So I sent this from my code","Hi Sarah", null);
        SendPlainEmailAlerts("dsmwaura@gmail.com", "My test", "72674", null);
    }

    /**
     * @return the EmailSentStatus
     */
    public static String getEmailSentStatus() {
        return EmailSentStatus;
    }

    /**
     * @param aEmailSentStatus the EmailSentStatus to set
     */
    public static void setEmailSentStatus(String aEmailSentStatus) {
        EmailSentStatus = aEmailSentStatus;
    }

}
