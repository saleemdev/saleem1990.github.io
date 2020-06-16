/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notification;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfWriter;
import com.sun.security.sasl.ClientFactoryImpl;
import ds.desktop.notify.DesktopNotify;
import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author owner
 */
public class Notification {

    private static Object dbServerIp;
    private static Object dbPort;
    private static Object activeDatabase;

    /**
     * @param args the command line arguments
     */
    public static java.sql.Connection connect(String user, String password) {
        Connection connection = null;

        try {

            java.lang.Class.forName("org.postgresql.Driver");

        } catch (java.lang.ClassNotFoundException cnf) {

            cnf.printStackTrace();

        }

        try {

            if (dbServerIp == null) {
                // dbServerIp = "localhost"jj;
                dbServerIp = notification.PropertiesFunctions.getpropValue("dbServerIpAdd").toString();
            }

            if (dbPort == null) {
                dbPort = notification.PropertiesFunctions.getpropValue("dbPort").toString();
            }

            if (activeDatabase == null) {
                activeDatabase = notification.PropertiesFunctions.getpropValue("activeDatabase").toString();
            }
            //  System.out.println("jdbc:postgresql://" + this.dbServerIp + ":" + dbPort + "/" + activeDatabase + " " + userName + " " + passWord);
            connection = java.sql.DriverManager.getConnection("jdbc:postgresql://" + dbServerIp + ":" + dbPort + "/" + activeDatabase, user, password);
            System.err.print("COnnected successfully");
//            if (connection != null) {
//                JOptionPane.showMessageDialog(new java.awt.Frame(), "connected");
//            }
//            else{
//                  JOptionPane.showMessageDialog(new java.awt.Frame(), "Not connected");
            System.err.println(System.getProperty("user.dir") + System.getProperty("file.separator") + "logo.jpg");
//            }
        } catch (java.sql.SQLException sqlExec) {

            //     msg = sqlExec.getMessage().toString();
            System.err.println(System.getProperty("user.dir"));

            //     Accurate = false;
            //javax.swing.JOptionPane.showMessageDialog(this, "ERROR : Logon denied due to incorrect username & password,\n network disconnection or dataserver not running!\n\nERROR DETAILS : \n[" + sqlExec.getMessage() + "]");
            sqlExec.printStackTrace();
            //   this.setVisible(true);

        }
        return connection;
    }

    public static java.sql.Connection custom_connect(String user, String password, String url, String port, String db) {
        Connection connection = null;

        try {

            java.lang.Class.forName("org.postgresql.Driver");

        } catch (java.lang.ClassNotFoundException cnf) {

            cnf.printStackTrace();

        }

        try {

            // if (dbServerIp == null) {
            // dbServerIp = "localhost"jj;
            dbServerIp = url;
            //}

            //   if (dbPort == null) {
            dbPort = port;
            // }

            //if (activeDatabase == null) {
            activeDatabase = db;
            //}
            //  System.out.println("jdbc:postgresql://" + this.dbServerIp + ":" + dbPort + "/" + activeDatabase + " " + userName + " " + passWord);
            connection = java.sql.DriverManager.getConnection("jdbc:postgresql://" + dbServerIp + ":" + dbPort + "/" + activeDatabase, user, password);
            System.err.print("jdbc:postgresql://" + dbServerIp + ":" + dbPort + "/" + activeDatabase);
//            if (connection != null) {
//                JOptionPane.showMessageDialog(new java.awt.Frame(), "connected");
//            }
//            else{
//                  JOptionPane.showMessageDialog(new java.awt.Frame(), "Not connected");
            System.err.println(System.getProperty("user.dir") + System.getProperty("file.separator") + "logo.jpg");
//            }
        } catch (java.sql.SQLException sqlExec) {

            //     msg = sqlExec.getMessage().toString();
            System.err.println(System.getProperty("user.dir"));

            //     Accurate = false;
            //javax.swing.JOptionPane.showMessageDialog(this, "ERROR : Logon denied due to incorrect username & password,\n network disconnection or dataserver not running!\n\nERROR DETAILS : \n[" + sqlExec.getMessage() + "]");
            sqlExec.printStackTrace();
            //   this.setVisible(true);

        }
        return connection;
    }

    private static boolean netIsAvailable() {
        try {
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            System.out.println("There is internet");
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {

            System.out.println("There is NO internet");
            return false;
        }
    }

    private static String getClassificationByDept(Connection connectDB, String department) {
        String classification = "";
        String sql = "select classification from st_stores_type  where upper(store_type_description) =upper('" + department + "')";

        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            ResultSet re = pst.executeQuery();
            while (re.next()) {
                classification = re.getObject(1).toString();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Notification.class.getName()).log(Level.SEVERE, null, ex);
        }

        return classification;
    }

    private static Object[] getStoresWithClassification(Connection connectDB, String department) {
        Object[] stores = new Object[]{};

        String classification = getClassificationByDept(connectDB, department);
        //System.err.println(department + "/" + classification);
        Vector v = new Vector();
        try {
            PreparedStatement pst = connectDB.prepareStatement("SELECT store_name from st_stores where upper(classification) ='" + classification.toUpperCase() + "'");
            System.err.println("SELECT store_name from st_stores where upper(classification) ='" + classification.toUpperCase() + "'");
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                v.add(rset.getObject(1));
            }

            stores = v.toArray();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return stores;
    }

    public static Boolean isMainStore(String store, Connection connectDB) {
        Boolean exists = false;

        try {
            PreparedStatement pst = connectDB.prepareStatement("SELECT CASE WHEN upper('" + store + "') in (select upper(store_name) from st_main_stores) then true else false end");
            ResultSet re = pst.executeQuery();
            while (re.next()) {
                exists = re.getBoolean(1);

                System.err.println("Existing status: " + exists);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return exists;
    }

    public static void SyncFSMaster(Connection connectDB) {
        String sql = "INSERT INTO public.secure_password(\n"
                + "            login_name, fullname, staffid, cgn, time_allocated, allocated_by, \n"
                + "             email, phone,  designation, department) VALUES(?,?,?,?,now(),?,?,?,?,?) ";

        String sqldata = "select '', first_name, employee_no, employee_no, now(),current_user,email_address,'',section, department  from fsmaster WHERE employee_no NOT IN (SELECT staffid FROM secure_password)";

        try {
            PreparedStatement pst = connectDB.prepareStatement(sqldata);
            ResultSet rset = pst.executeQuery();

            while (rset.next()) {
                PreparedStatement pstinsert = connectDB.prepareStatement(sql);
                pstinsert.setObject(1, randomAlphaNumeric(5));
                pstinsert.setObject(2, rset.getObject(1));

                pstinsert.setObject(3, rset.getObject(2));
                pstinsert.setObject(4, rset.getObject(3));
                // pstinsert.setObject(5, rset.getObject(4));
                pstinsert.setObject(6, rset.getObject(5));
                pstinsert.setObject(7, rset.getObject(6));
                pstinsert.setObject(8, rset.getObject(7));
                pstinsert.setObject(9, rset.getObject(8));
                //pstinsert.setObject(10, rset.getObject(9));
                //pstinsert.setObject(2, rset.getObject(1));

            }

            pst.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public static void Sync(Connection connectDB) {
        try {
            PreparedStatement pst = connectDB.prepareStatement("select distinct department from st_master_item where item_code not in (select item_code from st_stock_item)");
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                String department = rset.getObject(1).toString();
                // String itemcode = rset.getObject(2).toString();

                Object[] stores = getStoresWithClassification(connectDB, department);
                System.err.println("[" + department + "]");

                if (stores.length > 0) {

                    for (int i = 0; i < stores.length; i++) {

                        if (isMainStore(stores[i].toString(), connectDB)) {
                            System.err.println(stores[i]);

                            String sql = "INSERT INTO st_stock_item SELECT sub_cat_code, item_code, description, reorder_level, date, units, \n"
                                    + "       '" + stores[i].toString() + "', buying_price, quantity_instock, quantity_toorder, \n"
                                    + "       qty_ordered, packaging, old_stock, strength, genre, admin_mode, \n"
                                    + "       batch_no, expiry_date, max_stock, '00sa', min_stock, consumable, \n"
                                    + "       days, freq, brand, country, prov_code, opd, markup, broad_category, \n"
                                    + "       automated_billing, user_name, now()\n"
                                    + "  FROM st_master_item"
                                    + "   WHERE item_code not in (select item_code from st_stock_item WHERE department ILIKE '" + stores[i].toString() + "') "
                                    + "    and department ilike '" + department + "' ;";

                            System.err.println(sql);
                            PreparedStatement pst1 = connectDB.prepareStatement(sql);
                            pst1.executeUpdate();

                        }
                    }

                }

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void displayTray() throws AWTException, MalformedURLException {
        //Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();

        //If the icon is a file
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        //Alternative (if the icon is on the classpath):
        //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        //Let the system resize the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip("System tray icon demo");
        tray.add(trayIcon);

        trayIcon.displayMessage("Hello, World", "Good evening Salim", TrayIcon.MessageType.INFO);

        trayIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                trayIconActionPerformed(evt);
            }
        });
        //  trayIcon.addMouseListener(listener);
//         trayIcon.addMouseListener(new java.awt.event.MouseListener() {
//            public void actionPerformed(java.awt.event.MouseEvent evt) {
//                trayIconActionPerformed(evt);
//            }
//        });
    }

    private static void trayIconActionPerformed(java.awt.event.MouseEvent evt) {
        JOptionPane.showMessageDialog(null, "Hello");
    }

    private static void sendEmail(Connection connectDB, String fileaddr) {
        try {
            Date date = new Date();

            long time = date.getTime();
            System.out.println("Time in Milliseconds: " + time);

            Timestamp ts = new Timestamp(time);
            System.out.println("Current Time Stamp: " + ts);

            String sql = "SELECT COUNT(distinct (patient_no)) FROM hp_patient_card WHERE billing_time::date=current_date and user_name = 'postgres'";
            PreparedStatement pst = connectDB.prepareStatement(sql);
            //("SELECT COUNT(*) FROM hp_patient_card WHERE billing_time::date=current_date and user_name = 'postgres'");
            ResultSet rset = pst.executeQuery();
            if (rset.next()) {

                String maillist = "SELECT DISTINCT mailaddr FROM asset.notificationlist";

                PreparedStatement pstmaillist = connectDB.prepareStatement(maillist);
                ResultSet rsetmail = pstmaillist.executeQuery();
                while (rsetmail.next()) {

                    String email = rsetmail.getString(1);
                    EmailFunctions emf = new EmailFunctions();
                    int no = rset.getInt(1);
                    if (no > 0) {
                        //emf.SendOrderEmail("salimmwaura@mtrh.go.ke", no+" Records have been updated for bed charges");
                        emf.SendMailUsingSMPT(email, no + " Records have been updated for bed charges", "", connectDB);
                        // emf.SendPlainEmailAlerts(email, no + " Records have been updated for bed charges", "DBR ++ " + ts, connectDB);
                    } else {
                        //emf.SendOrderEmail("salimmwaura@mtrh.go.ke", "No Records have been updated for bed charges");
                        emf.SendMailUsingSMPT(email, "No Records have been updated for bed charges", "DBR--", connectDB);
                        // emf.SendPlainEmailAlerts(email, "No Records have been updated for bed charges", "DBR -- " + ts, connectDB);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    //select oid from st_procurement_plan limit 5

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    private static void fixPhoneNumbers(Connection connectDB) {
//0762 053 4521
// 0795 344 020
//0739 520 413
        String sql = "select distinct phone from secure_password where phone ilike '7%'  order by 1";

        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                Object phone = (rset.getObject(1));

                String updsql = "UPDATE secure_password SET phone = ? WHERE phone ilike ? ";

                PreparedStatement pstupd = connectDB.prepareStatement(updsql);
                pstupd.setObject(1, "0" + phone.toString().trim());
                pstupd.setObject(2, phone);
                pstupd.executeUpdate();

                System.err.println(phone + " to 0" + phone);

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    private static Object[] getMissingUsers(Connection connectDB) {
        Object[] users = new Object[]{};
        Vector v = new Vector();

        String sql = "select distinct pf from secure_test where pf NOT IN (select staffid from secure_password)  order by 1";

        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                v.add(rset.getObject(1));
            }
            users = v.toArray();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return users;
    }

    private static void dropHRUsers(Connection connectDB) {

        String sql = " select distinct rolname from pg_roles WHERE (rolname NOT ILIKE 'admin%') and (rolname NOT ILIKE 'postgres') and (rolname not ilike 'pg%') order by 1";

        try {

            PreparedStatement pst = connectDB.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                connectDB.setAutoCommit(false);
                String role = rset.getObject(1).toString();
                pst = connectDB.prepareStatement("DROP ROLE " + role + "");;
                pst.executeUpdate();

                pst = connectDB.prepareStatement("delete from secure_password where login_name ILIKE '" + role + "'");
                pst.executeUpdate();

                connectDB.commit();
                System.err.println("Dropped " + role);

            }

        } catch (SQLException ex) {

            ex.printStackTrace();
            try {
                connectDB.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Notification.class.getName()).log(Level.SEVERE, null, ex1);
            }

        }

    }

    public static void insert_users(Connection connectDB) {
        // String sql = "SELECT";
        //iptables -A INPUT -p tcp -m tcp --dport 8280 -j ACCEPT

        Object[] users = getMissingUsers(connectDB);

        for (int i = 0; i < users.length; i++) {
            String pfno = users[i].toString();
            String loginid = "id_" + pfno;

//            String sql = "INSERT INTO public.secure_password(login_name, fullname, staffid, cgn, time_allocated, allocated_by, email, phone,  designation, department, section)\n"
//                    + "\n"
//                    + "  SELECT ?,  case when firstname ilike '-' then '' else firstname end ||' '|| case when other ilike '-' then '' else other end ||' '|| case when surname ilike '-' then '' else surname end,"
//                    + "employee_no,employee_no,now(),current_user,email,mobile, designation, department, department from fsmaster3 where employee_no = ?\n"
//                    + "\n"
//                    + "  AND employee_no NOT IN (SELECT staffid FROM secure_password) AND length(employee_no)<5 AND length(employee_no)>2 ;";
            String sql = "INSERT INTO public.secure_password(login_name, fullname, staffid, cgn, time_allocated, allocated_by, email, phone,  designation, department, section)\n"
                    + "\n"
                    + "  SELECT ?,  fullname,"
                    + "pf,pf,now(),current_user,email,phone, designation, department, section from secure_test where pf = ?\n"
                    + "\n"
                    + "  AND pf NOT IN (SELECT staffid FROM secure_password);";
            try {
                PreparedStatement pst = connectDB.prepareStatement(sql);
                pst.setObject(1, loginid);
                pst.setObject(2, pfno);
                pst.executeUpdate();

                sql = "CREATE user " + loginid + " with password '12345678' superuser login";
                pst = connectDB.prepareStatement(sql);
                // pst.setObject(1, loginid);
                pst.executeUpdate();

                System.err.println(loginid + " " + pfno);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
//        String sqlinsert = "INSERT INTO public.secure_password(\n"
//                + "            login_name, fullname, staffid, cgn, time_allocated, allocated_by, \n"
//                + "            logo, email, phone, authid, designation, department)\n"
//                + "    VALUES (?, ?, ?, ?, ?, ?, \n"
//                + "            ?, ?, ?, ?, ?, ?);"
    }

    public static void updateCodes(Connection connectDB) {
        try {
            String //sql = "select oid from st_procurement_plan WHERE financial_year='2018-2019' AND length(item_code)>2";
                    sql = "select distinct item_description from st_procurement_plan where item_code not in (SELECT item_code FROM st_master_item)";
            PreparedStatement pst = connectDB.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                Object oid = rset.getObject(1);
                String random = randomAlphaNumeric(8);

                sql = "UPDATE st_procurement_plan SET item_code = ? WHERE trim(upper(item_description)) = trim(upper(?)) ";
                pst = connectDB.prepareStatement(sql);
                pst.setObject(1, random);
                pst.setObject(2, oid);
                pst.executeUpdate();
                System.err.println(oid + " = " + random);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void UpdateMasterPrices(Connection connectDB) {
        Object[] itemCodes = getCataLogCodes(connectDB);
        System.err.println("Items: " + itemCodes.length);
        Double price = 0.0;
        if (itemCodes.length > 1) {
            for (int i = 0; i < itemCodes.length; i++) {
                //String sql = "select date, price_per_item from st_stock_cardex where item_code ='" + itemCodes[i] + "' and length(order_no)>3 order by date desc limit 1";
                String sql = "SELECT date_due, unit_price FROM st_orders_delivery  where code ='" + itemCodes[i] + "' order by date_due desc limit 1";

                try {
                    PreparedStatement pst = connectDB.prepareStatement(sql);
                    ResultSet rset = pst.executeQuery();
                    if (rset.next()) {
                        price = rset.getDouble(2);

                        if (price < 0.8) {
                            price = 1.0;
                        }

                        String master = "UPDATE st_master_item SET buying_price= ? where trim(item_code) ='" + itemCodes[i] + "'";
                        String stock = "UPDATE st_stock_item SET buying_price= ? where trim(item_code) ='" + itemCodes[i] + "'";

                        PreparedStatement psUpdate = connectDB.prepareStatement(master);
                        psUpdate.setObject(1, price);
                        psUpdate.executeUpdate();

                        psUpdate = connectDB.prepareStatement(stock);
                        psUpdate.setObject(1, price);
                        psUpdate.executeUpdate();

                        System.err.println(itemCodes[i] + ": " + price);

                    } else {
                        // sql = "select trans_date,round(price/1.33::numeric(5,2),0) from st_sub_stores where item_code ='"+itemCodes[i]+"' and issuing >0 order by trans_date desc limit 1";
//
//                        sql = "Select buying_price from st_stock_item26Jul18 where item_code ='" + itemCodes[i] + "' order by buying_price asc LIMIT 1";
//                        PreparedStatement pstPrice = connectDB.prepareStatement(sql);
//                        ResultSet rstPrice = pstPrice.executeQuery();
//
//                        while (rstPrice.next()) {
//                            price = rstPrice.getDouble(1);
//                            String master = "UPDATE st_master_item SET buying_price= ? where trim(item_code) ='" + itemCodes[i] + "'";
//                            String stock = "UPDATE st_stock_item SET buying_price= ? where trim(item_code) ='" + itemCodes[i] + "'";
//
//                            PreparedStatement psUpdate = connectDB.prepareStatement(master);
//                            psUpdate.setObject(1, price);
//                            psUpdate.executeUpdate();
//
//                            psUpdate = connectDB.prepareStatement(stock);
//                            psUpdate.setObject(1, price);
//                            psUpdate.executeUpdate();
//
                        System.err.println(itemCodes[i] + ":(noexist, restore) " + price);

                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        }
    }

    public static Object[] getCataLogCodes(Connection connectDB) {

        String sql = "select distinct item_code from st_master_item WHERE item_code IN (SELECT code from st_orders_delivery) AND department ilike 'DRE%' ORDER by 1";
        Vector v = new Vector();
        try {
            PreparedStatement pst1 = connectDB.prepareStatement(sql);
            ResultSet rset = pst1.executeQuery();
            while (rset.next()) {
                v.add(rset.getObject(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return v.toArray();
    }

    private static void generateLPAD(Connection connectDB, String prefix, String index, int indexLength) {

        String sql = "select '" + prefix + "'|| lpad('" + index + "'," + indexLength + ", '0' )";

        try {
            PreparedStatement pst = connectDB.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                System.err.println(rset.getObject(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void fixDRSCodes(Connection connectDB) {

        String sql
                = //"select * from st_master_item where item_code ilike 'DRS%' AND date > current_date-14 AND length(item_code)>7 AND (user_name ILIKE 'salim%' or user_name ilike 'nix%')";
                "SELECT DISTINCT item_code  from st_procurement_plan where item_code NOT IN (select DISTINCT item_code FROM ST_master_item) and item_code ilike 'LAB%'";
        //1053

        int final_code = 12407;//131
        int i = 0;
        try {

            PreparedStatement pst1 = connectDB.prepareStatement(sql);
            ResultSet rset = pst1.executeQuery();
            while (rset.next()) {

                final_code += 1;
                i += 1;
                String old_code = rset.getObject("item_code").toString();
                String new_code = "LAB000" + String.valueOf(final_code);

                ///////////////////////////////
                String updsql1, updsql2;

                updsql1 = "INSERT INTO public.st_master_item(\n"
                        + "            sub_cat_code, item_code, description, reorder_level, date, units, \n"
                        + "            department, buying_price, quantity_instock, quantity_toorder, \n"
                        + "            qty_ordered, packaging, old_stock, strength,\n"
                        + "            max_stock, old_code, min_stock, consumable, \n"
                        + "            prov_code, opd, markup, broad_category, \n"
                        + "            automated_billing, user_name, data_capture_time)\n"
                        + " \n"
                        + "select 'Lab','" + final_code + "', item_description,100, current_date,unit_of_issue,\n"
                        + "'INVENTORY',estimated_unit_cost,0,0,\n"
                        + "0,1,0,'-',\n"
                        + "100,item_code, 100,true,\n"
                        + "'A',true,1.33,'INVENTORY',\n"
                        + "false,current_user,now()\n"
                        //  + " from st_procurement_plan WHERE item_code ilike 'inv%' AND item_code NOT IN (select DISTINCT item_code FROM ST_master_item) AND item_code = '" + old_code + "' limit 1";
                        + " from st_procurement_plan WHERE item_code ilike 'INV%' AND item_code NOT IN (select DISTINCT item_code FROM ST_master_item) AND item_code = '" + old_code + "' limit 1";

                updsql2 = "UPDATE st_procurement_plan SET item_code = ? WHERE item_code = ?";

                connectDB.setAutoCommit(false);

                PreparedStatement pstupd = connectDB.prepareStatement(updsql1);

                //    pstupd.executeUpdate();
                pstupd = connectDB.prepareStatement(updsql2);
                pstupd.setObject(1, new_code);
                pstupd.setObject(2, old_code);
                // pstupd.executeUpdate();

                /////////////////////////////////////////
                System.err.println(old_code + "-" + new_code);

                //  connectDB.commit();
            }
            System.err.println("Total: " + i);
        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                connectDB.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
        }
        //  return v.toArray();
    }

    private static void print() {
        Document document = new Document(PageSize.A4.rotate());
        try {
            java.io.File tempFile = java.io.File.createTempFile("REP_", ".pdf");
            PdfWriter writer
                    = PdfWriter.getInstance(document, new FileOutputStream(tempFile));

            document.open();
            String path2Logo = System.getProperty("user.dir") + System.getProperty("file.separator") + "COMPANY_LOGO.jpg";
            //    Watermark wmk = new Watermark(com.lowagie.text.Image.getInstance(path2Logo), 0, 0);
            //  document.add(wmk);

            String path2Logo2 = System.getProperty("user.dir") + System.getProperty("file.separator") + "kebs-logo.JPG";

            com.lowagie.text.pdf.PdfPTable table1 = new com.lowagie.text.pdf.PdfPTable(7);

            table1.getDefaultCell().setColspan(7);
            table1.getDefaultCell().setHorizontalAlignment(PdfCell.ALIGN_CENTER);
            table1.getDefaultCell().setFixedHeight(70);
            Phrase phrase = new Phrase("MOI TEACHING AND REFERRAL HOSPITAL");
            table1.addCell(phrase);
            document.add(table1);

            document.close();
            Desktop.getDesktop().open(tempFile);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

    }

    private static Object[] getSpecialDayes(Connection connectDB) {
        Object[] days = new String[]{};
        Vector v = new Vector();

        try {
            PreparedStatement pst = connectDB.prepareStatement("SELECT DISTINCT date FROM hr.holidays");

            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                v.add(rset.getObject(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        days = v.toArray();

        return days;
    }

    private static Boolean amIASpecialDate(String date2check, Connection conn) {
        Boolean stat = false;
        Object[] specialDates = getSpecialDayes(conn);//new String[]{"2018-11-07","2018-10-05"};
        for (int i = 0; i < specialDates.length; i++) {
            if (date2check.equalsIgnoreCase(specialDates[i].toString())) {
                stat = true;
            }
        }
        return stat;
    }

    private static String dateConverter() {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String date = "";

        Date yourDate = new Date("2019/01/16");
        date = format1.format(yourDate).toString();

        System.err.println(date);
        return date;
    }

    public static String getLeaveEnd(Connection conn, String date, int days, String leavetype) {
        String finaldate = "";
        String startdate = "";

        try {
            if (!leavetype.toUpperCase().contains("MATERNITY") || !leavetype.toUpperCase().contains("TERMINAL")) {
                System.err.println("My day is " + date);
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

                Date yourDate = format1.parse(date);
                Calendar c = Calendar.getInstance();
                c.setTime(yourDate);
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

                int i = 0;
                startdate = format1.format(yourDate).toString();

                while (i < days - 1) {
                    dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

                    c.add(Calendar.DATE, 1);

                    String sqldate = format1.format(c.getTime());

                    if (dayOfWeek != 6 && dayOfWeek != 7 && !amIASpecialDate(sqldate, conn)) {
                        System.err.println(c.getTime() + " " + dayOfWeek + " " + sqldate);

                        i += 1;
                        finaldate = sqldate;
                    }

                }
            } else {
                String sql = "SELECT '" + date + "'::date + " + days + ";";
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rset = pst.executeQuery();
                while (rset.next()) {
                    finaldate = rset.getObject(1).toString();
                }

            }
//        for(int i=0;i<30;i++){
//             c.add(Calendar.DATE, 1);
            System.err.println("Finally From " + startdate + " to " + finaldate + " a total of " + days + " days");
//        }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return finaldate;
        //String day = String.valueOf(dayOfWeek);
    }

    private static void getLeaveEnd(Connection conn) {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date yourDate = new Date("01/02/2019");
        Calendar c = Calendar.getInstance();
        c.setTime(yourDate);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        int i = 0;
        String startdate = format1.format(yourDate).toString();
        String finaldate = "";

        System.err.println("Starting:: " + c.getTime() + " " + dayOfWeek + " " + format1.format(c.getTime()));
        while (i < 34) {
            dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

            c.add(Calendar.DATE, 1);

            String sqldate = format1.format(c.getTime());
            //if (dayOfWeek != 6 && dayOfWeek != 7 && !sqldate.equalsIgnoreCase("2018-11-09")) {
            if (dayOfWeek != 6 && dayOfWeek != 7 && !amIASpecialDate(sqldate, conn)) {
                System.err.println(c.getTime() + " " + dayOfWeek + " " + sqldate);

                i += 1;
                finaldate = sqldate;
            }

        }

//        for(int i=0;i<30;i++){
//             c.add(Calendar.DATE, 1);
        System.err.println("Finally From " + startdate + " to " + finaldate);
//        }
        //String day = String.valueOf(dayOfWeek);
    }

    private static String getDOW() {
        Date yourDate = new Date("2020/03/01");
        Calendar c = Calendar.getInstance();
        c.setTime(yourDate);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        String day = String.valueOf(dayOfWeek);
        System.err.println(getDOWEEk(dayOfWeek));
        return day;

    }

    public static String getDOWEEk(int day) {
        String dayString = "";
        switch (day) {
            case 1:
                dayString = "SUNDAY";
                break;
            case 2:
                dayString = "MONDAY";
                break;
            case 3:
                dayString = "TUESDAY";
                break;
            case 4:
                dayString = "WEDNESDAY";
                break;
            case 5:
                dayString = "THURSDAY";
                break;
            case 6:
                dayString = "FRIDAY";
                break;
            case 7:
                dayString = "SATURDAY";
                break;
        }

        return dayString;
    }

    public static Object[] getApprovers(Connection conn, String level) {
        Object[] pfs = new Object[]{};
        Vector v = new Vector();
        String sql = "";
        switch (level) {
            case "SUPERVISORS":
                sql = "SELECT DISTINCT immediatestaff FROM secure_password WHERE length(immediatestaff) > 0 ";
                break;
            case "HODS":
                // sql = "select distinct username from hr.approval_levels where level ilike '2.HOD/Immediate Supervisor'";
                sql = "SELECT DISTINCT hodstaff FROM secure_password WHERE length(hodstaff) > 0 ";
                break;
            case "SNR":
                sql = "SELECT DISTINCT directoratestaff FROM secure_password WHERE length(directoratestaff) > 0";
                break;

        }
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                v.add(rset.getObject(1));
            }

            pfs = v.toArray();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return pfs;
    }

    private static String UserEmailByID(String key, Connection conn) {

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

    private static String FullnameByID(String key, Connection conn) {

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

    //getPFByLogin
    private static String getPFByLogin(String key, Connection conn) {

        String login = "";

        String sql = "";

        // sql = "select login_name from secure_password where  staffid = ?";
        sql = "select staffid  from uname_mapping where u_name ILIKE ?";

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

    private static String userdeptByStaffID(String key, Connection conn) {

        String login = "";

        String sql = "";

        // sql = "select login_name from secure_password where  staffid = ?";
        sql = "select department from secure_password where staffid = ?";

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

    private static int getWorkloadCount(Connection conn, String approverpf, String level) {
        int content = 0;
        String sql = "";
        switch (level) {
            case "SUPERVISORS":
                sql = "SELECT count(distinct(refno)) FROM hr.leave_application where pfno in (SELECT empno FROM myImediateStaff('" + approverpf + "')) and covering is not true and cancelled is not true";
                break;
            case "HODS":;
                // String dept = userdeptByStaffID(approverpf, conn);
                //sql = "SELECT count(distinct(refno)) FROM hr.leave_application where pfno in (select empno from myDeptStaff('" + approverpf + "')) and covering  is true and hodsupervisor is not true and cancelled is not true";

                sql = "SELECT count(distinct(refno)) FROM hr.leave_application where pfno in (select empno from mydeptstaffcrystal('" + approverpf + "')) and covering  is true and hodsupervisor is not true and cancelled is not true";
                break;
            case "SENIOR MANAGEMENT":
                sql = "SELECT count(distinct(refno)) FROM hr.leave_application where pfno in (SELECT empno FROM myDirectorateStaff('" + approverpf + "')) and hresourcing is true and snrmgr is not true and cancelled is not true";
                break;

        }

        System.err.println(sql);
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();

            // System.err.println("Level: "+level+" Approver: "+approverpf+" Workload:"+rowcount);
            // if (rset.next()) {
            //       //Staffdet,LeaveType,DaysRequested,LeaveStart
            while (rset.next()) {
                content = rset.getInt(1);
            }
            // }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return content;
    }

    private static String getWorkload(Connection conn, String approverpf, String level) {
        String content = "-";
        String sql = "";
        switch (level) {
            case "SUPERVISORS":
                sql = "SELECT secure_password.fullname||'/'||pfno, leavetype, daysrequested, leavestart FROM hr.leave_application, secure_password where secure_password.staffid=hr.leave_application.pfno\n"
                        + "\n"
                        + "and hr.leave_application.pfno in (SELECT empno FROM myImediateStaff('" + approverpf + "')) and covering is not true and cancelled is not true";
                break;
            case "HODS":;
                // String dept = userdeptByStaffID(approverpf, conn);
                sql = "SELECT secure_password.fullname||'/'||pfno, leavetype, daysrequested, leavestart FROM hr.leave_application, secure_password where secure_password.staffid=hr.leave_application.pfno\n"
                        + "\n"
                        + "and hr.leave_application.pfno in (select empno from mydeptstaffcrystal('" + approverpf + "')) and covering  is true and hodsupervisor is not true and cancelled is not true";
                break;
            case "SENIOR MANAGEMENT":
                sql = "SELECT secure_password.fullname||'/'||pfno, leavetype, daysrequested, leavestart FROM hr.leave_application, secure_password where secure_password.staffid=hr.leave_application.pfno\n"
                        + "\n"
                        + "and hr.leave_application.pfno in (SELECT empno FROM myDirectorateStaff('" + approverpf + "')) and hresourcing is true and snrmgr is not true and cancelled is not true";
                break;

        }
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();
            StringBuilder sb1 = new StringBuilder(200);
            sb1.append("<html><body>" + "<table style='border: 1px solid #ddd; width: 100%;'>");

            sb1.append("<th style='padding-top: 12px;\n"
                    + "  padding-bottom: 12px;\n"
                    + "  text-align: left;\n"
                    + "  background-color: #4CAF50;\n"
                    + "  color: white;'>")
                    .append("Staff Name")
                    .append("<td style='padding-top: 12px;\n"
                            + "  padding-bottom: 12px;\n"
                            + "  text-align: left;\n"
                            + "  background-color: #4CAF50;\n"
                            + "  color: white;'>")
                    .append("Leavetype")
                    .append("</td><td style='padding-top: 12px;\n"
                            + "  padding-bottom: 12px;\n"
                            + "  text-align: left;\n"
                            + "  background-color: #4CAF50;\n"
                            + "  color: white;' >")
                    .append("Days Requested")
                    .append("</td><td style='padding-top: 12px;\n"
                            + "  padding-bottom: 12px;\n"
                            + "  text-align: left;\n"
                            + "  background-color: #4CAF50;\n"
                            + "  color: white;'>")
                    .append("Start");
            sb1.append("</th>");

            // System.err.println("Level: "+level+" Approver: "+approverpf+" Workload:"+rowcount);
            //     if (rset.next()) {
            //Staffdet,LeaveType,DaysRequested,LeaveStart
            while (rset.next()) {
                sb1.append("<tr style='padding: 8px;nth-child(even){background-color: #f2f2f2; border: 1px solid #dddddd;'>");
                sb1.append(rset.getObject(1))
                        .append("<td>")
                        .append(rset.getObject(2))
                        .append("</td><td>")
                        .append(rset.getObject(3))
                        .append("</td><td>")
                        .append(rset.getObject(4))
                        .append("</td><td>");
//                            .append(cat.getOwner())
//                            .append("</td><td>")
//                            .append(cat.getOwnerGroup())
//                            .append("</td><td>")
//                            .append(cat.getSeverity());
                sb1.append("</tr>");
                //   }
            }

            sb1.append("</table></body></html>");

            content = sb1.toString();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return content;
    }

    public static String getMonth(Connection conn, String date) {
        String month = "";

        String sql = "SELECT EXTRACT(MONTH FROM DATE '" + date + "');";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {

                month = rset.getString(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return month;
    }

    public static String getFormattedDate(Connection conn, String date) {
        String dateF = "";

        String sql = "select humanreadabledate('" + date + "');";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {

                dateF = rset.getString(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return dateF;
    }

    public static String[] getStaffProceedingForLeave(Connection conn, String hodpf, String level) {

        String[] refs = new String[]{};
        Vector<String> v = new Vector<String>();

        String sql = "";
        if (level.contains("HOD")) {
            sql = "SELECT * FROM hr.leave_application where pfno in \n"
                    + "(select empno FROM mydeptstaffcrystal('" + hodpf + "')) and approved is true and leavestart::date between current_date and current_date + 7";
        } else {
            sql = "SELECT * FROM hr.leave_application where pfno in \n"
                    + "(select empno FROM MYIMEDIATESTAFF('" + hodpf + "')) and approved is true and leavestart::date > current_date";
        }
        System.err.println(sql);
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                v.add(rset.getObject("refno").toString());
            }
        } catch (SQLException ex) {
            Logger.getLogger(Notification.class.getName()).log(Level.SEVERE, null, ex);
        }

        refs = v.toArray(new String[v.size()]);

        return refs;
    }

    public static String[] getStaffResumingFromLeave(Connection conn, String hodpf, String level) {
        String[] refs = new String[]{};
        Vector<String> v = new Vector<String>();

        String sql = "";
        if (level.contains("HOD")) {
            sql = "SELECT * FROM hr.leave_application where pfno in \n"
                    + "(select empno FROM mydeptstaffcrystal('" + hodpf + "')) and approved is true and leavestart::date < current_date ";
        } else {
            sql = "SELECT * FROM hr.leave_application where pfno in \n"
                    + "(select empno FROM MYIMEDIATESTAFF('" + hodpf + "')) and approved is true and leavestart::date < current_date ";
        }
        System.err.println(sql);
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                String leavestart = rset.getObject("leavestart").toString().replace("/", "").trim();
                String leavetype = rset.getObject("leavetype").toString();
                int daysapproved = Integer.valueOf(rset.getObject("daysapproved").toString());

                System.err.println("The leave type: " + leavetype + ", ref: " + rset.getObject("refno").toString() + " Starting " + leavestart);

                String leavend = LeaveFactory.getLeaveEnd(conn, leavestart, daysapproved, leavetype);

                System.err.println("END: " + leavend);

                String resumption = LeaveFactory.getResumptionDate(conn, leavend);

                System.err.println("RESUMPTION: " + resumption);

                Boolean stat = isOnleave(conn, resumption);

                //  System.err.println(leavend + " " + resumption);
                if (stat) {
                    v.add(rset.getObject("refno").toString());

                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        refs = v.toArray(new String[v.size()]);
        return refs;
    }

    public static Boolean isOnleave(Connection conn, String date) {

        Boolean stat = false;

        // String sql = "SELECT case when '" + date + "' between current_date and current_date + 7 then true else false end";
        String sql = "SELECT case when '" + date + "'::date > current_date then true else false end";

        //  System.err.println(sql);
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                stat = rset.getBoolean(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Notification.class.getName()).log(Level.SEVERE, null, ex);
        }

        return stat;
    }

    public static void getLeaveReturnsSupervisors(Connection conn) {
        Object[] hods = new Object[]{"6422"};//getApprovers(conn, "SUPERVISORS"); //Get all SUPERVISORS

        String emailcontent = "";

        for (int i = 0; i < hods.length; i++) {
            String supf = hods[i].toString();
            String supemail = UserEmailByID(supf, conn);
            String supname = FullnameByID(supf, conn);

            //Content 1:
            String[] content1 = getStaffProceedingForLeave(conn, supf, "SUPERVISORS");
            if (content1.length > 0) {
                System.err.println(FullnameByID(supf, conn) + "'s return\nStaff Proceeding this month: " + content1.length);

                String sql = "SELECT pfno, leavetype, leavestart, humanreadabledate(leavestart::text), daysapproved FROM hr.leave_application WHERE refno = ?";//"SELECT pfno, leavetype, leavestart FROM hr.leave_application WHERE refno = ?";

                StringBuilder sb1 = new StringBuilder(200);
                sb1.append("<html><body>" + "<table style='border: 1px solid #ddd; width: 100%;'>");

                sb1.append("<th style='padding-top: 12px;\n"
                        + "  padding-bottom: 12px;\n"
                        + "  text-align: left;\n"
                        + "  background-color: #4CAF50;\n"
                        + "  color: white;'>")
                        .append("Staff Name")
                        .append("<td style='padding-top: 12px;\n"
                                + "  padding-bottom: 12px;\n"
                                + "  text-align: left;\n"
                                + "  background-color: #4CAF50;\n"
                                + "  color: white;'>")
                        .append("Leavetype")
                        .append("</td><td style='padding-top: 12px;\n"
                                + "  padding-bottom: 12px;\n"
                                + "  text-align: left;\n"
                                + "  background-color: #4CAF50;\n"
                                + "  color: white;' >")
                        .append("Days Requested")
                        .append("</td><td style='padding-top: 12px;\n"
                                + "  padding-bottom: 12px;\n"
                                + "  text-align: left;\n"
                                + "  background-color: #4CAF50;\n"
                                + "  color: white;'>")
                        .append("Start")
                        .append("</td><td style='padding-top: 12px;\n"
                                + "  padding-bottom: 12px;\n"
                                + "  text-align: left;\n"
                                + "  background-color: #4CAF50;\n"
                                + "  color: white;'>")
                        .append("Resume");
                sb1.append("</th>");
                for (int j = 0; j < content1.length; j++) {
                    try {
                        PreparedStatement pst = conn.prepareStatement(sql);
                        pst.setObject(1, content1[j]);
                        ResultSet rset = pst.executeQuery();

                        // System.err.println("Level: "+level+" Approver: "+approverpf+" Workload:"+rowcount);
                        //     if (rset.next()) {
                        //Staffdet,LeaveType,DaysRequested,LeaveStart
                        while (rset.next()) {
                            String fullname = FullnameByID(rset.getObject(1).toString(), conn);
                            String leavetype = rset.getObject(2).toString();
                            String start = rset.getObject(3).toString();
                            String startr = rset.getObject(4).toString();
                            int days = Integer.valueOf(rset.getObject(5).toString());
                            String leavend = LeaveFactory.getLeaveEnd(conn, start, days, leavetype);
                            String resume = LeaveFactory.getResumptionDate(conn, leavend);
                            String resumer = getFormattedDate(conn, resume);
                            sb1.append("<tr style='padding: 8px;nth-child(even){background-color: #f2f2f2; border: 1px solid #dddddd;'>");
                            sb1.append(fullname)
                                    .append("<td>")
                                    .append(leavetype)
                                    .append("</td><td>")
                                    .append(days)
                                    .append("</td><td>")
                                    .append(startr)
                                    .append("</td><td>")
                                    .append(resumer)
                                    .append("</td><td>");
//                            .append(cat.getOwner())
//                            .append("</td><td>")
//                            .append(cat.getOwnerGroup())
//                            .append("</td><td>")
//                            .append(cat.getSeverity());
                            sb1.append("</tr>");
                            //   }
                        }

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                }

                sb1.append("</table></body></html>");
                emailcontent = sb1.toString();
                //Send Email--
                EmailFunctions em = new EmailFunctions();
                String subject = "LEAVE RETURNS - STAFF PROCEEDING FOR LEAVE:(SUPERVISOR) " + randomAlphaNumeric(7);
                em.SendPlainEmailAlerts(supemail, "<p><h4>Dear " + supname + ", the following is a list of staff under your supervision, who are due for leave(with approval)</p>" + emailcontent + "", subject, conn);

            }
            //Content 2
            String[] content2 = getStaffResumingFromLeave(conn, supf, "SUPERVISOR");

            if (content2.length > 0) {

                System.err.println(FullnameByID(supf, conn) + "'s return\nStaff returning this month: " + content2.length);

                String sql = "SELECT pfno, leavetype, leavestart, humanreadabledate(leavestart::text), daysapproved FROM hr.leave_application WHERE refno = ?";//"SELECT pfno, leavetype, leavestart FROM hr.leave_application WHERE refno = ?";

                StringBuilder sb1 = new StringBuilder(200);
                sb1.append("<html><body>" + "<table style='border: 1px solid #ddd; width: 100%;'>");

                sb1.append("<th style='padding-top: 12px;\n"
                        + "  padding-bottom: 12px;\n"
                        + "  text-align: left;\n"
                        + "  background-color: #4CAF50;\n"
                        + "  color: white;'>")
                        .append("Staff Name")
                        .append("<td style='padding-top: 12px;\n"
                                + "  padding-bottom: 12px;\n"
                                + "  text-align: left;\n"
                                + "  background-color: #4CAF50;\n"
                                + "  color: white;'>")
                        .append("Leavetype")
                        .append("</td><td style='padding-top: 12px;\n"
                                + "  padding-bottom: 12px;\n"
                                + "  text-align: left;\n"
                                + "  background-color: #4CAF50;\n"
                                + "  color: white;' >")
                        .append("Days Approved")
                        .append("</td><td style='padding-top: 12px;\n"
                                + "  padding-bottom: 12px;\n"
                                + "  text-align: left;\n"
                                + "  background-color: #4CAF50;\n"
                                + "  color: white;'>")
                        .append("Started on")
                        .append("</td><td style='padding-top: 12px;\n"
                                + "  padding-bottom: 12px;\n"
                                + "  text-align: left;\n"
                                + "  background-color: #4CAF50;\n"
                                + "  color: white;'>")
                        .append("To Resume");
                sb1.append("</th>");
                for (int j = 0; j < content2.length; j++) {
                    try {
                        PreparedStatement pst = conn.prepareStatement(sql);
                        pst.setObject(1, content2[j]);
                        ResultSet rset = pst.executeQuery();

                        // System.err.println("Level: "+level+" Approver: "+approverpf+" Workload:"+rowcount);
                        //     if (rset.next()) {
                        //Staffdet,LeaveType,DaysRequested,LeaveStart
                        while (rset.next()) {
                            String fullname = FullnameByID(rset.getObject(1).toString(), conn);
                            String leavetype = rset.getObject(2).toString();
                            String start = rset.getObject(3).toString();
                            String startr = rset.getObject(4).toString();
                            int days = Integer.valueOf(rset.getObject(5).toString());
                            String leavend = LeaveFactory.getLeaveEnd(conn, start, days, leavetype);
                            String resume = LeaveFactory.getResumptionDate(conn, leavend);
                            String resumer = getFormattedDate(conn, resume);
                            sb1.append("<tr style='padding: 8px;nth-child(even){background-color: #f2f2f2; border: 1px solid #dddddd;'>");
                            sb1.append(fullname)
                                    .append("<td>")
                                    .append(leavetype)
                                    .append("</td><td>")
                                    .append(days)
                                    .append("</td><td>")
                                    .append(startr)
                                    .append("</td><td>")
                                    .append(resumer)
                                    .append("</td><td>");
//                            .append(cat.getOwner())
//                            .append("</td><td>")
//                            .append(cat.getOwnerGroup())
//                            .append("</td><td>")
//                            .append(cat.getSeverity());
                            sb1.append("</tr>");
                            //   }
                        }

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                }

                sb1.append("</table></body></html>");
                emailcontent = sb1.toString();
                //Send Email--
                EmailFunctions em = new EmailFunctions();
                String subject = "LEAVE RETURNS - STAFF RESUMING FROM LEAVE:(SUPERVISOR) " + randomAlphaNumeric(7);
                em.SendPlainEmailAlerts(supemail, "<p><h4>Dear " + supname + ", the following is a list of staff under your supervision, who are to resume from leave soon.</p>" + emailcontent + "", subject, conn);

            }

        }

    }

    public static void getLeaveReturns(Connection conn) {

        Object[] hods = getApprovers(conn, "HODS"); //Get all HODs

        String emailcontent = "";

        for (int i = 0; i < hods.length; i++) {
            String hodpf = hods[i].toString();
            String hodemail = UserEmailByID(hodpf, conn);
            String hodname = FullnameByID(hodpf, conn);

            //Content 1:
            String[] content1 = getStaffProceedingForLeave(conn, hodpf, "HOD");
            if (content1.length > 0) {
                System.err.println(FullnameByID(hodpf, conn) + "'s return\nStaff Proceeding this month: " + content1.length);

                String sql = "SELECT pfno, leavetype, leavestart, humanreadabledate(leavestart::text), daysapproved FROM hr.leave_application WHERE refno = ?";//"SELECT pfno, leavetype, leavestart FROM hr.leave_application WHERE refno = ?";

                StringBuilder sb1 = new StringBuilder(200);
                sb1.append("<html><body>" + "<table style='border: 1px solid #ddd; width: 100%;'>");

                sb1.append("<th style='padding-top: 12px;\n"
                        + "  padding-bottom: 12px;\n"
                        + "  text-align: left;\n"
                        + "  background-color: #4CAF50;\n"
                        + "  color: white;'>")
                        .append("Staff Name")
                        .append("<td style='padding-top: 12px;\n"
                                + "  padding-bottom: 12px;\n"
                                + "  text-align: left;\n"
                                + "  background-color: #4CAF50;\n"
                                + "  color: white;'>")
                        .append("Leavetype")
                        .append("</td><td style='padding-top: 12px;\n"
                                + "  padding-bottom: 12px;\n"
                                + "  text-align: left;\n"
                                + "  background-color: #4CAF50;\n"
                                + "  color: white;' >")
                        .append("Days Requested")
                        .append("</td><td style='padding-top: 12px;\n"
                                + "  padding-bottom: 12px;\n"
                                + "  text-align: left;\n"
                                + "  background-color: #4CAF50;\n"
                                + "  color: white;'>")
                        .append("Start")
                        .append("</td><td style='padding-top: 12px;\n"
                                + "  padding-bottom: 12px;\n"
                                + "  text-align: left;\n"
                                + "  background-color: #4CAF50;\n"
                                + "  color: white;'>")
                        .append("Resume");
                sb1.append("</th>");
                for (int j = 0; j < content1.length; j++) {
                    try {
                        PreparedStatement pst = conn.prepareStatement(sql);
                        pst.setObject(1, content1[j]);
                        ResultSet rset = pst.executeQuery();

                        // System.err.println("Level: "+level+" Approver: "+approverpf+" Workload:"+rowcount);
                        //     if (rset.next()) {
                        //Staffdet,LeaveType,DaysRequested,LeaveStart
                        while (rset.next()) {
                            String fullname = FullnameByID(rset.getObject(1).toString(), conn);
                            String leavetype = rset.getObject(2).toString();
                            String start = rset.getObject(3).toString();
                            String startr = rset.getObject(4).toString();
                            int days = Integer.valueOf(rset.getObject(5).toString());
                            String leavend = LeaveFactory.getLeaveEnd(conn, start, days, leavetype);
                            String resume = LeaveFactory.getResumptionDate(conn, leavend);
                            String resumer = getFormattedDate(conn, resume);
                            sb1.append("<tr style='padding: 8px;nth-child(even){background-color: #f2f2f2; border: 1px solid #dddddd;'>");
                            sb1.append(fullname)
                                    .append("<td>")
                                    .append(leavetype)
                                    .append("</td><td>")
                                    .append(days)
                                    .append("</td><td>")
                                    .append(startr)
                                    .append("</td><td>")
                                    .append(resumer)
                                    .append("</td><td>");
//                            .append(cat.getOwner())
//                            .append("</td><td>")
//                            .append(cat.getOwnerGroup())
//                            .append("</td><td>")
//                            .append(cat.getSeverity());
                            sb1.append("</tr>");
                            //   }
                        }

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                }

                sb1.append("</table></body></html>");
                emailcontent = sb1.toString();
                //Send Email--
                EmailFunctions em = new EmailFunctions();
                String subject = "LEAVE RETURNS - STAFF PROCEEDING FOR LEAVE:(HOD) " + randomAlphaNumeric(7);
                em.SendPlainEmailAlerts(hodemail, "<p><h4>Dear " + hodname + ", the following is a list of staff under your supervision, who are due for leave(with approval)</p>" + emailcontent + "", subject, conn);

            }
            //Content 2
            String[] content2 = getStaffResumingFromLeave(conn, hodpf, "HOD");

            if (content2.length > 0) {

                System.err.println(FullnameByID(hodpf, conn) + "'s return\nStaff returning this month: " + content2.length);

                String sql = "SELECT pfno, leavetype, leavestart, humanreadabledate(leavestart::text), daysapproved FROM hr.leave_application WHERE refno = ?";//"SELECT pfno, leavetype, leavestart FROM hr.leave_application WHERE refno = ?";

                StringBuilder sb1 = new StringBuilder(200);
                sb1.append("<html><body>" + "<table style='border: 1px solid #ddd; width: 100%;'>");

                sb1.append("<th style='padding-top: 12px;\n"
                        + "  padding-bottom: 12px;\n"
                        + "  text-align: left;\n"
                        + "  background-color: #4CAF50;\n"
                        + "  color: white;'>")
                        .append("Staff Name")
                        .append("<td style='padding-top: 12px;\n"
                                + "  padding-bottom: 12px;\n"
                                + "  text-align: left;\n"
                                + "  background-color: #4CAF50;\n"
                                + "  color: white;'>")
                        .append("Leavetype")
                        .append("</td><td style='padding-top: 12px;\n"
                                + "  padding-bottom: 12px;\n"
                                + "  text-align: left;\n"
                                + "  background-color: #4CAF50;\n"
                                + "  color: white;' >")
                        .append("Days Approved")
                        .append("</td><td style='padding-top: 12px;\n"
                                + "  padding-bottom: 12px;\n"
                                + "  text-align: left;\n"
                                + "  background-color: #4CAF50;\n"
                                + "  color: white;'>")
                        .append("Started on")
                        .append("</td><td style='padding-top: 12px;\n"
                                + "  padding-bottom: 12px;\n"
                                + "  text-align: left;\n"
                                + "  background-color: #4CAF50;\n"
                                + "  color: white;'>")
                        .append("To Resume");
                sb1.append("</th>");
                for (int j = 0; j < content2.length; j++) {
                    try {
                        PreparedStatement pst = conn.prepareStatement(sql);
                        pst.setObject(1, content2[j]);
                        ResultSet rset = pst.executeQuery();

                        // System.err.println("Level: "+level+" Approver: "+approverpf+" Workload:"+rowcount);
                        //     if (rset.next()) {
                        //Staffdet,LeaveType,DaysRequested,LeaveStart
                        while (rset.next()) {
                            String fullname = FullnameByID(rset.getObject(1).toString(), conn);
                            String leavetype = rset.getObject(2).toString();
                            String start = rset.getObject(3).toString();
                            String startr = rset.getObject(4).toString();
                            int days = Integer.valueOf(rset.getObject(5).toString());
                            String leavend = LeaveFactory.getLeaveEnd(conn, start, days, leavetype);
                            String resume = LeaveFactory.getResumptionDate(conn, leavend);
                            String resumer = getFormattedDate(conn, resume);
                            sb1.append("<tr style='padding: 8px;nth-child(even){background-color: #f2f2f2; border: 1px solid #dddddd;'>");
                            sb1.append(fullname)
                                    .append("<td>")
                                    .append(leavetype)
                                    .append("</td><td>")
                                    .append(days)
                                    .append("</td><td>")
                                    .append(startr)
                                    .append("</td><td>")
                                    .append(resumer)
                                    .append("</td><td>");
//                            .append(cat.getOwner())
//                            .append("</td><td>")
//                            .append(cat.getOwnerGroup())
//                            .append("</td><td>")
//                            .append(cat.getSeverity());
                            sb1.append("</tr>");
                            //   }
                        }

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                }

                sb1.append("</table></body></html>");
                emailcontent = sb1.toString();
                //Send Email--
                EmailFunctions em = new EmailFunctions();
                String subject = "LEAVE RETURNS - STAFF RESUMING FROM LEAVE:(HOD) " + randomAlphaNumeric(7);
                em.SendPlainEmailAlerts(hodemail, "<p><h4>Dear " + hodname + ", the following is a list of staff under your supervision, who are to resume from leave soon.</p>" + emailcontent + "", subject, conn);

            }

        }

    }

    public static void SendNotifications(Connection conn, Object[] approvers, String level) {
        String ipadress = "http://" + PropertiesFunctions.getpropValue("webappip").toString() + ":8280/mtportal/";
        if (approvers.length > 0) {//We don't care if there are no approvers, Don't want null content.
            switch (level) {
                case "SUPERVISORS":
                    for (int i = 0; i < approvers.length; i++) {
                        String email = UserEmailByID(approvers[i].toString(), conn);

                        String content = getWorkload(conn, approvers[i].toString(), level);

                        String fullname = FullnameByID(approvers[i].toString(), conn);
                        int content2 = getWorkloadCount(conn, approvers[i].toString(), level);
                        System.err.println(level + " Workload for " + approvers[i] + "= " + content2);
                        String phone = UserPhone(approvers[i].toString(), conn);

                        if (content2 > 0) {
                            new SendSms(phone, level + " INTRAY: \nDear " + fullname + ", you have " + content2 + " tasks for your action on e-Leave\nPlease login to your MTPORTAL Dashboard for more details.", "E-LEAVE ALERT: " + level, conn);

                            String subject = "PENDING LEAVES [SUPERVISOR INTRAY]-" + randomAlphaNumeric(5);

                            EmailFunctions em = new EmailFunctions();

                            em.SendPlainEmailAlerts(email, "<p><h4>Dear " + fullname + ", you have the following tasks in your "
                                    + "dashboard. Please login to the MTPORTAL "
                                    + "dashboard to act on them</h4></p>" + content + "<p><h5><a href='" + ipadress + "'>1. Click me to open the portal<a/> and log in with your credentials</h5></p><p>2. Click Show my Tasks then Click on '1.Immediate Supervisor' button</p>", subject, conn);
                            System.err.println(ipadress.replace("", ""));
                        }
                    }
                    break;
                case "HODS":
                    for (int i = 0; i < approvers.length; i++) {
                        System.err.println("New day..");
                        String email = UserEmailByID(approvers[i].toString(), conn);
                        String content = getWorkload(conn, approvers[i].toString(), level);

                        String subject = "PENDING LEAVES [HOD INTRAY]-" + randomAlphaNumeric(5);
                        String fullname = FullnameByID(approvers[i].toString(), conn);

                        int content2 = getWorkloadCount(conn, approvers[i].toString(), level);
                        String phone = UserPhone(approvers[i].toString(), conn);
                        System.err.println(level + " Workload for " + approvers[i] + "= " + content2);
                        if (content2 > 0) {
                            new SendSms(phone, level + " INTRAY: \nDear " + fullname + ", you have " + content2 + " tasks for your action on e-Leave\nPlease login to your MTPORTAL Dashboard for more details.", "E-LEAVE ALERT: " + level, conn);

                            EmailFunctions em = new EmailFunctions();

                            em.SendPlainEmailAlerts(email, "<p><h4>Dear " + fullname + ", you have the following tasks in your "
                                    + "dashboard. Please login to the MTPORTAL "
                                    + "dashboard to act on them</h4></p>" + content + "<p><h5><a href='" + ipadress + "'>1. Click me to open the portal<a/> and log in with your credentials</h5></p><p>2. Click Show my Tasks then Click on '2.HOD Approval' button</p>", subject, conn);
                            System.err.println(ipadress.replace("", ""));
                        }
                    }
                    break;
                case "SENIOR MANAGEMENT":;
                    for (int i = 0; i < approvers.length; i++) {
                        String email = UserEmailByID(approvers[i].toString(), conn);
                        String content = getWorkload(conn, approvers[i].toString(), level);

                        String subject = "PENDING LEAVES [SENIOR MANAGEMENT INTRAY]-" + randomAlphaNumeric(5);

                        String fullname = FullnameByID(approvers[i].toString(), conn);

                        int content2 = getWorkloadCount(conn, approvers[i].toString(), level);
                        String phone = UserPhone(approvers[i].toString(), conn);
                        System.err.println(level + " Workload for " + approvers[i] + "= " + content2);
                        if (content2 > 0) {
                            new SendSms(phone, level + " INTRAY:\nDear " + fullname + ", you have " + content2 + " tasks for your action on e-Leave\nPlease login to your MTPORTAL Dashboard for more details.", "E-LEAVE ALERT: " + level, conn);

                            EmailFunctions em = new EmailFunctions();

                            em.SendPlainEmailAlerts(email, "<p><h4>Dear " + fullname + ", you have the following tasks in your "
                                    + "dashboard. Please login to the MTPORTAL "
                                    + "dashboard to act on them</h4></p>" + content + "<p><h5><a href='" + ipadress + "'>1. Click me to open the portal<a/> and log in with your credentials</h5></p><p>2. Click Show my Tasks then Click on '4.Senior Management Approval' button</p>", subject, conn);
                            System.err.println(ipadress.replace("", ""));
                        }
                    }
                    break;

            }

            new SendSms("0722810063", "MTRH E-Leave Alerts:\n", "All sent" + randomAlphaNumeric(5), conn);
        }
    }

    public static Double MinimumRequestLimit(Connection connectDB, String item, String level) {
        Double limit = Double.valueOf(0.0D);
        try {
            PreparedStatement pst = connectDB.prepareStatement("SELECT minimum_approval_value FROM public.st_approval_threshold WHERE item =? AND approval_level =? ");
            pst.setObject(1, item);
            pst.setObject(2, level);

            ResultSet rst = pst.executeQuery();
            if (rst.next()) {
                limit = Double.valueOf(rst.getDouble(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            //    JOptionPane.showMessageDialog(new Frame(), ex.getMessage());
        }
        return limit;
    }

    public static Double MaximumRequestLimit(Connection connectDB, String item, String level) {
        Double limit = Double.valueOf(0.0D);
        try {
            PreparedStatement pst = connectDB.prepareStatement("SELECT maximum_approval_value FROM public.st_approval_threshold WHERE item =? AND approval_level =? ");
            pst.setObject(1, item);
            pst.setObject(2, level);

            ResultSet rst = pst.executeQuery();
            if (rst.next()) {
                limit = Double.valueOf(rst.getDouble(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            //  JOptionPane.showMessageDialog(new Frame(), ex.getMessage());
        }
        return limit;
    }

    public static Double RequisitionValue(Connection connectDB, String reqno) {
        Double reqtotal = Double.valueOf(0.0D);
        try {
            PreparedStatement pst = connectDB.prepareStatement("SELECT sum(quantity*price) FROM public.st_receive_requisation where requisition_no ='" + reqno + "'");

            ResultSet rst = pst.executeQuery();
            while (rst.next()) {
                reqtotal = Double.valueOf(rst.getDouble(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            //JOptionPane.showMessageDialog(new Frame(), ex.getMessage());
        }
        return reqtotal;
    }

    public static Object[] getRQApprovers(Connection conn, String level) {
        Object[] pfs = new Object[]{};
        Vector v = new Vector();
        String sql = "";
        sql = "SELECT DISTINCT user_name FROM section_allocation WHERE type ILIKE 'MGR' and designation ILIKE '" + level + "' ";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                v.add(rset.getObject(1));
            }

            pfs = v.toArray();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return pfs;
    }

    public static Object[] getRQApprovalLevel(Connection conn) {
        Object[] pfs = new Object[]{};
        Vector v = new Vector();
        String sql = "";
        sql = "SELECT DISTINCT designation FROM section_allocation WHERE type ILIKE 'MGR'  ";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                v.add(rset.getObject(1));
            }

            pfs = v.toArray();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return pfs;
    }

    public static int getWorkloadOnDashboardBasedOnLimit(Double max, Double min, String login, Connection conn) {
        int workload = 0;
        String sql = "SELECT rq_id, forwarded_by FROM public.st_mgt_approval where STATUS = false AND voted = true  AND rq_id IN (SELECT distinct UPPER(requisition_no) \n"
                + "FROM st_receive_requisation WHERE upper(cost_center) IN (SELECT upper(stores) from store_allocation WHERE user_name ILIKE '" + login + "' AND type ilike 'Approve PRQ/SRQ'))  \n"
                + "AND req_value <= " + max + "  \n"
                + "        UNION SELECT DISTINCT order_no,ordered_by FROM st_orders   WHERE chief_approval = true and aie_holder_approval = true AND ordered = true \n"
                + "       AND fm_approval = true and sad_approval = false and  miu_approval = false    AND requisition_no IN (SELECT distinct UPPER(requisition_no) \n"
                + "       FROM st_receive_requisation WHERE upper(cost_center) IN (SELECT upper(stores) from store_allocation WHERE user_name ILIKE '" + login + "' \n"
                + "       AND type ilike 'Approve PRQ/SRQ'))   AND net_value <=" + max + "\n"
                + "         ORDER BY 1";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                workload += 1;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // System.err.println(sql);
        return workload;
    }

    public static void sendRQNotifications(String level, Connection conn) {
        Object[] approverslogins = getRQApprovers(conn, level);
        Double max = MaximumRequestLimit(conn, "REQUISITION", level);

        Double min = MinimumRequestLimit(conn, "REQUISITION", level);

        Connection bespoke_conn = custom_connect("admin", "12345678", "172.16.103.200", "5433", "bespoke");

        for (int i = 0; i < approverslogins.length; i++) {

            String login = approverslogins[i].toString();

            int workload = getWorkloadOnDashboardBasedOnLimit(max, min, login, conn);
            String pfno = getPFByLogin(login, conn);//From uname table

            // Connection provisconn = connect(pfno, pfno)
            String tel = UserPhone(pfno, bespoke_conn);

            String fullname = FullnameByID(pfno, bespoke_conn);

            if (workload > 0) {
                String msg = "Dear " + fullname + ", you have " + workload + " tasks for your action on e-Procurement. Kindly log in to your Funsoft HMIS Account for more details.\n";
                new SendSms(tel, "MTRH E-procurement Alert:\n" + msg, "E-PROCUREMENT ALERT: " + randomAlphaNumeric(5), bespoke_conn);
            }

        }
        new SendSms("0722810063", "MTRH E-procurement Alerts(SNR):\n", "All sent" + randomAlphaNumeric(5), bespoke_conn);

    }

    public static void sendSCMNotifications(Connection conn) {
        String sql = "SELECT count(distinct(requisition_no)) FROM st_receive_requisation WHERE (requisition_no ILIKE 'PRQ%' OR requisition_no ILIKE 'SRQ%') AND analysed=TRUE AND approval_status=FALSE AND requisition_no IN (SELECT distinct rq_id FROM st_mgt_approval WHERE voted = true AND status = true) ";

        String sql2 = "SELECT count(distinct (order_no))  from st_orders st where dispatch=false and ordered=false and chief_approval=false and aie_holder_approval=true and sad_approval=false  and miu_approval=false and order_no is not null AND order_no ilike '%ord%'";

        int workload = 0;
        int workloadLPO = 0;

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                workload = rset.getInt(1);
            }
            //------------------------------
            pst = conn.prepareStatement(sql2);
            rset = pst.executeQuery();
            while (rset.next()) {
                workloadLPO = rset.getInt(1);
            }
            ///--------------
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        String pfno = getPFByLogin("sainah7809", conn);

        Connection bespoke_conn = custom_connect("admin", "12345678", "172.16.103.200", "5433", "bespoke");

        String tel = UserPhone(pfno, bespoke_conn);

        String fullname = FullnameByID(pfno, bespoke_conn);

        if (workload > 0) {
            String msg = "Dear " + fullname + ", you have " + workload + " PRQs for your action on e-Procurement. Kindly log in to your Funsoft HMIS Account for more details.\n";
            new SendSms(tel, "MTRH E-procurement Alert:\n" + msg, "E-PROCUREMENT ALERT:(PRQs) " + randomAlphaNumeric(5), bespoke_conn);
        }

        if (workloadLPO > 0) {
            String msg = "Dear " + fullname + ", you have " + workloadLPO + " LPOs for your action on e-Procurement. Kindly log in to your Funsoft HMIS Account for more details.\n";
            new SendSms(tel, "MTRH E-procurement Alert:\n" + msg, "E-PROCUREMENT ALERT:(PRQs) " + randomAlphaNumeric(5), bespoke_conn);
        }
        new SendSms("0722810063", "MTRH E-procurement Alerts(SCM):\n", "All sent" + randomAlphaNumeric(5), bespoke_conn);
    }

    public static void SendVBNotifications(Connection conn) {

        //String sql = "SELECT count(distinct(order_no)) from st_orders st where dispatch=false and ordered=true and chief_approval=true and aie_holder_approval=true and sad_approval=false  and miu_approval=false and order_no ilike '%ord%'";
        String sql = "select count(distinct (order_no)) from st_orders where ordered=false and dispatch=false and aie_holder_approval is true and chief_approval is true\n"
                + "and order_no ilike '%ord%'";
        String sql2 = "select count(distinct(rq_id)) FROM public.st_mgt_approval WHERE status=false and voted = false --order by 1 desc ;";

        int workloadLPO = 0;
        int workloadRQ = 0;

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                workloadLPO = rset.getInt(1);
            }

            pst = conn.prepareStatement(sql2);
            ResultSet rset2 = pst.executeQuery();
            while (rset2.next()) {
                workloadRQ = rset2.getInt(1);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        String pfno = getPFByLogin("charles6878", conn);

        String pfno1 = getPFByLogin("cyril4089", conn);

        // String pfno2 = getPFByLogin("daniel4801", conn);
        Connection bespoke_conn = custom_connect("admin", "12345678", "172.16.103.200", "5433", "bespoke");

        String tel = UserPhone(pfno, bespoke_conn);

        String tel1 = UserPhone(pfno1, bespoke_conn);

        //   String tel2 = UserPhone(pfno2, bespoke_conn);
        String fullname = FullnameByID(pfno, bespoke_conn);
        String fullname1 = FullnameByID(pfno1, bespoke_conn);
        // String fullname2 = FullnameByID(pfno2, bespoke_conn);

        if (workloadLPO > 0) {
            String msg = "Dear " + fullname + ", you have (" + workloadLPO + ") e-orders and (" + workloadRQ + ") e-requests for your action on e-Procurement. Kindly log in to your Funsoft HMIS Account for more details.\n";
            new SendSms(tel, "MTRH E-procurement Alert:\n" + msg, "E-PROCUREMENT ALERT: " + randomAlphaNumeric(5), bespoke_conn);

            String msg1 = "Dear " + fullname1 + ", you have (" + workloadLPO + ") e-orders and (" + workloadRQ + ") e-requests for your action on e-Procurement. Kindly log in to your Funsoft HMIS Account for more details.\n";
            new SendSms(tel1, "MTRH E-procurement Alert:\n" + msg1, "E-PROCUREMENT ALERT: " + randomAlphaNumeric(5), bespoke_conn);

        }

        new SendSms("0722810063", "MTRH E-procurement Alerts(VB):\n", "All sent" + randomAlphaNumeric(5), bespoke_conn);

    }

    public static void SendFMNotifications(Connection conn) {

        //String sql = "SELECT count(distinct(order_no)) from st_orders st where dispatch=false and ordered=true and chief_approval=true and aie_holder_approval=true and sad_approval=false  and miu_approval=false and order_no ilike '%ord%'";
        String sql = "SELECT distinct count(distinct(order_no)) from st_orders st where dispatch=false and ordered=true and \n"
                + "chief_approval=true and aie_holder_approval=true and sad_approval=false and (order_no ilike 'ORD%' OR order_no ilike 'CAN%' ) \n"
                + " and miu_approval=false and fm_approval = false and order_no is not null";

        int workload = 0;

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                workload = rset.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        String pfno = getPFByLogin("tkngetich", conn);

        String pfno1 = getPFByLogin("maraba6029", conn);

        String pfno2 = getPFByLogin("daniel4801", conn);

        Connection bespoke_conn = custom_connect("admin", "12345678", "172.16.103.200", "5433", "bespoke");

        String tel = UserPhone(pfno, bespoke_conn);

        String tel1 = UserPhone(pfno1, bespoke_conn);

        String tel2 = UserPhone(pfno2, bespoke_conn);

        String fullname = FullnameByID(pfno, bespoke_conn);
        String fullname1 = FullnameByID(pfno1, bespoke_conn);
        String fullname2 = FullnameByID(pfno2, bespoke_conn);

        if (workload > 0) {
            String msg = "Dear " + fullname + ", you have " + workload + " tasks for your action on e-Procurement. Kindly log in to your Funsoft HMIS Account for more details.\n";
            new SendSms(tel, "MTRH E-procurement Alert:\n" + msg, "E-PROCUREMENT ALERT: " + randomAlphaNumeric(5), bespoke_conn);

            String msg1 = "Dear " + fullname1 + ", you have " + workload + " tasks for your action on e-Procurement. Kindly log in to your Funsoft HMIS Account for more details.\n";
            new SendSms(tel1, "MTRH E-procurement Alert:\n" + msg1, "E-PROCUREMENT ALERT: " + randomAlphaNumeric(5), bespoke_conn);

            String msg2 = "Dear " + fullname2 + ", you have " + workload + " tasks for your action on e-Procurement. Kindly log in to your Funsoft HMIS Account for more details.\n";
            new SendSms(tel2, "MTRH E-procurement Alert:\n" + msg2, "E-PROCUREMENT ALERT: " + randomAlphaNumeric(5), bespoke_conn);
        }

        new SendSms("0722810063", "MTRH E-procurement Alerts(FM):\n", "All sent" + randomAlphaNumeric(5), bespoke_conn);

    }

    public static Object[] approvedOrRejectedLeaves(Connection conn) {
        Object[] myLoad = new String[]{};
        Vector v = new Vector();
        try {
            String sql = "select distinct refno from hr.leave_application where (approved is true or cancelled is true) and (sent is false or backup is false);";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                v.add(rset.getObject(1));
            }
        } catch (Exception ex) {
        }
        myLoad = v.toArray();

        return myLoad;
    }

    public static void UpdateSentStatus(String leaveid, Connection conn) {
        try {
            String sql = "UPDATE hr.leave_application SET sent = true, backup = true where refno=?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setObject(1, leaveid);
            pst.executeUpdate();

        } catch (Exception ex) {
        }
    }

    public static void upload2NMS(Connection connect2Bes) {
        String sql = "select refno, pfno, secure_password.fullname, leavetype, daysapproved, leavestart "
                + "FROM hr.leave_application, secure_password where pfno IN (select staffid from secure_password where department ilike 'nursing%')\n"
                + "and approved is true and hr.leave_application.pfno = secure_password.staffid AND nms IS false";

        Connection conn = custom_connect("postgres", "1234", "172.16.108.86", "5435", "nmsdb");//USERNAME, PW,URL,PORT,DBNAME

        try {
            PreparedStatement pst = connect2Bes.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                String leaveid = rset.getObject("refno").toString();
                String pfno = rset.getObject("pfno").toString();
                int days = rset.getInt(5);
                String date = rset.getObject("leavestart").toString();
                String leavetype = rset.getObject("leavetype").toString();

                String fullname = rset.getObject(3).toString();
                String leavend = LeaveFactory.getLeaveEnd(connect2Bes, date, days, leavetype);
//iNSERT THE ENTRY
                String sql2 = "INSERT INTO nms_leavedump values(?,?,?,?,?,?::date,?::date)";
                PreparedStatement pstinsert = conn.prepareStatement(sql2);
                pstinsert.setObject(1, leaveid);
                pstinsert.setObject(2, pfno);
                pstinsert.setObject(3, fullname);
                pstinsert.setObject(4, leavetype);
                pstinsert.setObject(5, days);
                pstinsert.setObject(6, date);
                pstinsert.setObject(7, leavend);
                pstinsert.executeUpdate();
                //fLAG THE LEAVEID TRUE
                pstinsert = connect2Bes.prepareStatement("UPDATE hr.leave_application SET nms = true WHERE REFNO = ?");
                pstinsert.setObject(1, leaveid);
                pstinsert.executeUpdate();
            }
            //  pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    private static void generateCalendar() {
        int Y = 2016;
        int startDayOfMonth = 5;
        int spaces = startDayOfMonth;

        String[] months = {"",
            "January", "February", "March",
            "April", "May", "June",
            "July", "August", "September",
            "October", "November", "December"};
        int[] days = {
            0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
        };

        for (int M = 1; M <= 12; M++) {
            if ((((Y % 4 == 0) && (Y % 100 != 0)) || (Y % 400 == 0)) && M == 2) {
                days[M] = 29;

            }
        }

    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    private static String bioCompleteness(Connection conn, String applicantref) {
        String sql = "select case when length(fname)>1 then 1 else 0 end as fname,\n"
                + "case when length(mname)>1 then 1 else 0 end as mname,\n"
                + "case when length(lname)>1 then 1 else 0 end as lname,\n"
                + "case when length(maintel)>5 then 1 else 0 end as maintel,\n"
                + "case when length(email)>1 then 1 else 0 end as email,\n"
                + "case when length(pin)>4 then 1 else 0 end as pin,\n"
                + "case when length(dob::text)>3 then 1 else 0 end as dob,\n"
                + "case when length(gender)>3 then 1 else 0 end as gender,\n"
                + "case when length(homecounty)> 3 then 1 else 0 end as homecounty\n"
                + "\n"
                + "\n"
                + "\n"
                + "--select *\n"
                + "from vacancies.applicant_bio where \n"
                + "idno=? limit 1 ;";
        String result = "0.0";
        Double total = 0.0;
        Double percent = 0.0;
        int size = 0;
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setObject(1, applicantref);
            ResultSet rset = pst.executeQuery();

            // if(rset!=null){
            if (rset.next()) {
                size = rset.getMetaData().getColumnCount();

                System.err.println(size);
                for (int i = 1; i <= size; i++) {
                    //  System.err.println(rset.getString(i));
                    total += Double.valueOf(rset.getString(i));
                }
                //   Double total = Double.valueOf(rset.getString(1)) + Double.valueOf(rset.getString(2)) + Double.valueOf(rset.getString(3)) + Double.valueOf(rset.getString(4)) + Double.valueOf(rset.getString(5))
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //  }
        percent = total / size;
        result = String.valueOf(total + "--" + round(percent, 2) * 100 + "%");

        return result;
    }

    public static Connection mysqldbconn() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (ClassNotFoundException ex) {
            System.err.println("Unable to load MySQL Driver");
        }

        try {
            //String jdbcUrl = "jdbc:mysql://192.185.129.109/combretu_test?user=combretu_test&password=combretudbpwd";
            String jdbcUrl = "jdbc:mysql://69.16.238.106:3306/sallytes_can";

            System.err.println(jdbcUrl);
            //  Connection con = DriverManager.getConnection(jdbcUrl,"combretu_salim","saleem2013");
            Connection con = DriverManager.getConnection(jdbcUrl, "sallytes_can", "jambokenya@");
            System.out.println("Connected!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return conn;
    }

    public static Object[] staffMetrics(String pfno, Connection connectDB) {
        Object[] metrics = new Object[]{};

        Vector v = new Vector();
//        Connection connectDB = connect("postgres", "sequence");
        try {
            PreparedStatement pst = connectDB.prepareStatement("SELECT * FROM secure_password WHERE staffid=?");
            pst.setObject(1, pfno);
            ResultSet rset = pst.executeQuery();

            while (rset.next()) {
                v.add(rset.getString("fullname"));
                v.add(rset.getString("login_name"));
                v.add(rset.getString("designation"));
                v.add(rset.getString("phone"));
                v.add(rset.getString("email"));
                v.add(rset.getString("section"));
                v.add(rset.getString("department"));
                v.add(rset.getString("staffid"));

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return v.toArray();
    }

    public static Object[] items2Bill(String items, Connection connectDB) {
        Object[] items2bill = new String[]{};
        Vector v = new Vector();
        //
        try {
            PreparedStatement pst = connectDB.prepareStatement("SELECT * FROM pb_services WHERE department=?");
            pst.setObject(1, items);
            ResultSet rset = pst.executeQuery();

            while (rset.next()) {
                v.add(rset.getString("code"));
                v.add(rset.getString("service_type"));
                v.add(rset.getString("normal_rate"));

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        items2bill = v.toArray();
        //
        return items2bill;
    }

    public static Object[] enrollment(String items, Connection connectDB) {
        Object[] items2bill = new String[]{};
        Vector v = new Vector();
        //
        try {
            PreparedStatement pst = connectDB.prepareStatement("SELECT * FROM kipkabuslist");
            //pst.setObject(1, items);
            ResultSet rset = pst.executeQuery();

            while (rset.next()) {
                v.add(rset.getString("clientname") + "-" + rset.getString("clientno") + "-" + rset.getString("sex"));

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        items2bill = v.toArray();
        //
        return items2bill;
    }

    public static void newBill(String clientno, String visitno, String billno, String transaction, String ttype, String billeditem, Double qty, Double unit, Double debit, Double credit, String reference, String department, String paymode, Connection connectDB) {

        //java.sql.Connection connectDB;
        //connectDB = MainFrame.connectDB;
        try {

            java.sql.PreparedStatement pstmt = connectDB.prepareStatement("INSERT INTO public.client_ledger(\n"
                    + "            customer_no, visitno, bill_no, transaction_no, transaction_type, \n"
                    + "            billed_item, qty_sold, unit_cost, debit, credit, ref_no, department, \n"
                    + "            paymode)\n"
                    + "    VALUES (?, ?, ?, ?, ?, \n"
                    + "            ?, ?, ?, ?, ?, ?, ?, \n"
                    + "            ?);");
            pstmt.setString(1, clientno);
            pstmt.setString(2, visitno);
            pstmt.setObject(3, billno);
            pstmt.setObject(4, transaction);
            pstmt.setObject(5, ttype);

            pstmt.setObject(6, billeditem);

            pstmt.setDouble(7, qty);
            pstmt.setDouble(8, unit);
            pstmt.setDouble(9, debit);
            pstmt.setDouble(10, credit);

            pstmt.setObject(11, reference);
            pstmt.setObject(12, department);
            pstmt.setObject(13, paymode);

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            try {
                connectDB.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Notification.class.getName()).log(Level.SEVERE, null, ex1);
            }
            ex.printStackTrace();
        }
    }

    public static void newpatientRegister(String clientno, String fnname, String othnames, String fno, String id, String sex, String mobile, String trans, String adm, Connection connectDB) {

        //java.sql.Connection connectDB;
        //connectDB = MainFrame.connectDB;
        try {
            String clientNumber = null;

            connectDB.setAutoCommit(false);
//        java.sql.Statement stmtTransNo = connectDB.createStatement();
//        java.sql.ResultSet rss = stmtTransNo.executeQuery("select lpad(nextval('client_no')::text,7,0::text)");
//        while (rss.next()) {
//            clientNumber = rss.getObject(1).toString();
//
//        }

            // clientno = clientNumber;
            // clientno =
            // clientnotxtfld.setText(clientNumber);
            java.sql.Statement CUSTNO = connectDB.createStatement();
            java.sql.ResultSet rssCUST = CUSTNO.executeQuery("SELECT file_no FROM client_register WHERE file_no LIKE '%" + fno + "%'");
            if (rssCUST.next()) {
                javax.swing.JOptionPane.showMessageDialog(new java.awt.Frame(), "PLEASE ENSURE THAT ENTER THE CORRECT FILE NUMBER IS NEW AND NOT REVISIT.", "ERROR MESSAGE", javax.swing.JOptionPane.ERROR_MESSAGE);

            } else {
                java.sql.PreparedStatement pstmt = connectDB.prepareStatement(""
                        + "INSERT INTO client_register("
                        + "            client_no, first_name, other_name, file_no, id_no, sex, tel_no, "
                        + "            user_name, creation_time)"
                        + "    VALUES (?, ?, ?, ?, ?, ?, ?,current_user, localtimestamp);");
                pstmt.setString(1, clientno);
                pstmt.setString(2, fnname);
                pstmt.setObject(3, othnames);
                pstmt.setObject(4, fno);
                pstmt.setObject(5, id);

                pstmt.setObject(6, sex);

                pstmt.setObject(7, mobile);
                pstmt.executeUpdate();

                java.sql.PreparedStatement pstmtvisit = connectDB.prepareStatement(""
                        + "INSERT INTO client_visits("
                        + "            client_no, visit_id, payment_mode,adm_date)"
                        + "    VALUES (?, ?,'CASH',?::date);");
                pstmtvisit.setString(1, clientno);
                pstmtvisit.setString(2, trans);
                pstmtvisit.setObject(3, adm);

                pstmtvisit.executeUpdate();
            }

            // connectDB.setAutoCommit(true);
            connectDB.commit();

        } catch (SQLException ex) {
            try {
                connectDB.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Notification.class.getName()).log(Level.SEVERE, null, ex1);
            }
            ex.printStackTrace();
        }
    }

    public static Object[] feeitems(String items, Connection connectDB) {
        Object[] items2bill = new String[]{};
        Vector v = new Vector();
        //
        try {
            PreparedStatement pst = connectDB.prepareStatement("SELECT clientno, payment3 FROM kipkabusfees");
            //pst.setObject(1, items);
            ResultSet rset = pst.executeQuery();

            while (rset.next()) {
                v.add(rset.getString(1) + "-" + rset.getString(2));

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        items2bill = v.toArray();
        //
        return items2bill;
    }

    public static Object[] getClientVisitDetails(java.sql.Connection connectDB, String client_no) {
        Object[] details = null;
        Vector cDetails = new java.util.Vector<>();

        try {
            java.sql.Statement pst = connectDB.createStatement();
            java.sql.ResultSet rstdet = pst.executeQuery("select * from client_visits WHERE client_no ='" + client_no + "' AND discharge IS FALSE");

            if (rstdet.next()) {
                for (int i = 1; i <= 14; i++) {
                    cDetails.add(rstdet.getObject(i));
                    //System.err.println(rstdet.getObject(i) + "" + i);
                }

            }

            details = cDetails.toArray();
        } catch (SQLException sq) {
            sq.printStackTrace();
        }
        return details;

    }

    public static void newVisit(Connection connectDB, String client, String visit) {

        try {
            connectDB.setAutoCommit(false);
            java.sql.PreparedStatement pstmtvisit = connectDB.prepareStatement("UPDATE client_visits SET DISCHARGE = TRUE, check_out=true where client_no=?;");
            pstmtvisit.setString(1, client);
            pstmtvisit.executeUpdate();

            pstmtvisit = connectDB.prepareStatement(""
                    + "INSERT INTO client_visits("
                    + "            client_no, visit_id, payment_mode, adm_date)"
                    + "    VALUES (?, ?,'CASH',current_date::date);");
            pstmtvisit.setString(1, client);
            pstmtvisit.setString(2, visit);

            pstmtvisit.executeUpdate();

            pstmtvisit = connectDB.prepareStatement("UPDATE client_ledger SET visitno=? WHERE customer_no=?");
            pstmtvisit.setString(1, visit);
            pstmtvisit.setString(2, client);
            pstmtvisit.executeUpdate();

            pstmtvisit = connectDB.prepareStatement("UPDATE academics.studentreg SET upd=true WHERE studentid=?");
            // pstmtvisit.setString(1, visit);
            pstmtvisit.setString(1, client);
            pstmtvisit.executeUpdate();

            System.err.println("Assigned " + visit + " to " + client);
            connectDB.commit();

        } catch (SQLException ex) {
            try {
                connectDB.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
            ex.printStackTrace();
        }
    }

    private static void updatevisitIDs(Connection conn) {
//select studentid, admissionno from academics.studentreg where length(admissionno)>3
        String sql = "select studentid, admissionno from academics.studentreg where length(admissionno)>3 and upd is false";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rset = pst.executeQuery();
            while (rset.next()) {
                String id = rset.getString(1);
                String adm = rset.getString(2);
                newVisit(conn, id, adm);
                System.err.println("Starting payload processing ...." + id + " " + adm);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    private static void runscript(Connection conn, String script) {
//select studentid, admissionno from academics.studentreg where length(admissionno)>3
        String sql = script;
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) {

        String fileaddr = System.getProperty("user.dir") + System.getProperty("file.separator") + "logo.jpg";

        //    mysqldbconn();
        // Connection connectDB = connect("postgres", "sequence");
//         Connection connectDB = custom_connect("postgres", "sequence", "172.16.103.200", "5433", "bespoke");
        // Connection connectDB = custom_connect("postgres", "sequence", "192.168.1.97", "5432", "bespoke");
        //  Connection connectDB = custom_connect("postgres", "sequence", "172.16.103.200", "5433", "bespoke");
        Connection connectDB = custom_connect("postgres", "sequence", "localhost", "5432", "bespoke");
//        //   Connection connectDB = custom_connect("postgres", "sequence", "localhost", "5433", "bespoke");
//        //   Connection connectDB = custom_connect("salim7915", "info27613716", "172.16.106.1", "5432", "funsoft");
//        //  Connection connectDB = custom_connect("postgres", "info27613716", "localhost", "5432", "funsoft");
        if (connectDB == null) {
        } else {
            System.err.println("Starting upgrade business now...");

            String apikey = "69pJq6iTBSwfAaoL4BU7yHi361dGLkqQ1MJYHQF/lJI=";//69pJq6iTBSwfAaoL4BU7yHi361dGLkqQ1MJYHQF/lJI=
            String clientid = "8055c2c9-489b-4440-b761-a0cc27d1e119";//8055c2c9-489b-4440-b761-a0cc27d1e119
            String senderid = "MTRH";
            
          //  insert_users(connectDB);
            new OnfonGateway("", "").sendUsingOkHTTP("254722810063", "Hellos", apikey,clientid,senderid);

//new OnfonGateway("", "").sendDefaultOKHTTP();
//            runscript(connectDB, "-- Table: public.client_ledger_recycle_bin\n"
//                    + "\n"
//                    + "-- DROP TABLE public.client_ledger_recycle_bin;\n"
//                    + "\n"
//                    + "CREATE TABLE public.client_ledger_recycle_bin\n"
//                    + "(\n"
//                    + "  customer_no character varying NOT NULL,\n"
//                    + "  visitno character varying NOT NULL,\n"
//                    + "  bill_no character varying NOT NULL,\n"
//                    + "  transaction_no character varying NOT NULL,\n"
//                    + "  transaction_type character varying,\n"
//                    + "  billed_item character varying,\n"
//                    + "  qty_sold numeric(30,2) DEFAULT 0,\n"
//                    + "  unit_cost numeric(30,2) DEFAULT 0,\n"
//                    + "  debit numeric(30,2) DEFAULT 0,\n"
//                    + "  credit numeric(30,2) DEFAULT 0,\n"
//                    + "  ref_no character varying(100),\n"
//                    + "  department character varying(200),\n"
//                    + "  paymode character varying(100) NOT NULL,\n"
//                    + "  user_name character varying NOT NULL DEFAULT \"current_user\"(),\n"
//                    + "  transaction_time timestamp with time zone DEFAULT now(),\n"
//                    + "  invoice_no character varying DEFAULT 0,\n"
//                    + "  CONSTRAINT credit_check CHECK (credit >= 0::numeric),\n"
//                    + "  CONSTRAINT debit CHECK (debit >= 0::numeric)\n"
//                    + ")\n"
//                    + "WITH (\n"
//                    + "  OIDS=TRUE\n"
//                    + ");\n"
//                    + "ALTER TABLE public.client_ledger_recycle_bin\n"
//                    + "  OWNER TO admin;\n"
//                    + "\n"
//                    + "-- Trigger: stockreducetrigger on public.client_ledger_recycle_bin\n"
//                    + "\n"
//                    + "-- DROP TRIGGER stockreducetrigger ON public.client_ledger_recycle_bin;\n"
//                    + "\n"
//                    + "CREATE TRIGGER stockreducetrigger\n"
//                    + "  AFTER INSERT\n"
//                    + "  ON public.client_ledger_recycle_bin\n"
//                    + "  FOR EACH ROW\n"
//                    + "  EXECUTE PROCEDURE public.debitsalesttrigger();\n"
//                    + "");
//
//        } else {

            // Object[] billthis = items2Bill("YR3/MOD2", connectDB);
            //  Object[] billthis = enrollment("YR3/MOD2", connectDB);
//            Object[] billthis = feeitems("YR3/MOD2", connectDB);
//
//            for (int i = 0; i < billthis.length; i++) {
//
//                String fullstring = billthis[i].toString();
//                String customerno = fullstring.split("-")[0].toString();
//                Double fee = Double.valueOf(fullstring.split("-")[1].toString());
//
//                Object[] visitdetails = getClientVisitDetails(connectDB, customerno);
//                if (visitdetails.length > 0) {
//                    String visitid = visitdetails[1].toString();
//                    System.err.println(i + 1 + ". " + customerno + " ,  " + fee + "-" + visitid);
//
//                    if (fee > 0) {
//                        newBill(customerno, visitid, "B-" + randomAlphaNumeric(4), "T-" + randomAlphaNumeric(5), "BILL SETTLEMENT", "PAYMENT 3", 1.00, fee, 0.0, fee, "R-" + randomAlphaNumeric(5), "JOURNAL", "BANKDEPOSIT", connectDB);
//                    }
//
//                }
//
//            }
//            for (int i = 0; i < billthis.length; i++) {
//                //  System.err.println(billthis[i]);
//                String item = billthis[i].toString();
//
//                String[] indiv = item.split("-");
//
//                String clientno = indiv[0];
//                String fullname = indiv[1];
//                String sex = indiv[2];
//                String fname = "";
//                String othername = "";
//
//                int namelength = fullname.split(" ").length;
//                if (namelength == 3) {
//                    othername = fullname.split(" ")[1] + " " + fullname.split(" ")[2];
//                    fname = fullname.split(" ")[0];
//                }else{
//                    fname = fullname.split(" ")[0];
//                    othername = fullname.split(" ")[1];
//                }
//                
//                newpatientRegister(clientno, fname, othername, "F-"+randomAlphaNumeric(7), "N/A", sex, "N/A", "V-"+randomAlphaNumeric(7), "2019-10-28", connectDB);
//
//                
//                
//                
//            }
            //    mysqldbconn();
//        Object[] metrics = new Object[]{};
//        
//        metrics = staffMetrics("7915", connectDB);
//        
//        for(int i=0;i<metrics.length;i++){
//            System.err.println(metrics[i]);
//        }
            //System.err.println("Comleteness: "+bioCompleteness(connectDB,"27613716"));
//            int score =4, max = 6;
//            Double weight = Double.valueOf(((Double.valueOf(score)/Double.valueOf(max)))*100);
//            System.err.print(Double.valueOf(score)/Double.valueOf(max));
//            
//            System.err.println("Score: "+String.valueOf(round(weight, 2))+"% ["+score+"/"+max+"]");
////            
//            getLeaveReturns(connectDB);
//            System.err.println("Starting supervisors now...");
//            getLeaveReturnsSupervisors(connectDB);
            //  String leavend = LeaveFactory.getLeaveEnd(connectDB, "2019-05-29", 6, "MATERNITY");
            // System.err.println("Ending:.."+leavend);
            //  String leavend = LeaveFactory.getLeaveEnd(connectDB, '2019-04-01', 20, "TERMINAL LEAVE");
            // new SendSms("0705521339", "Hello, love");
            // upload2NMS(connectDB);
            //            Object[] leaves = approvedOrRejectedLeaves(connectDB);
            //            
            //            /////////////////////////---------///////////////////////--------/////////////////////////////
            //            
            //            for (int i = 0; i < leaves.length; i++) {
            //                System.out.println("Here: " + leaves[i]);
            //                String refno = leaves[i].toString();
            //
            //     LeaveApplicationPDF pdf = new LeaveApplicationPDF();
            //                pdf.RequestPDF(connectDB, refno);
            //
            //                System.err.println(new EmailFunctions().getEmailSentStatus());
            //                
            //                String stat = new EmailFunctions().getEmailSentStatus();
            //                if (stat.contains("Success")) {
            //                     (refno, connectDB);
            //                }
            //            }
            //            try {
            //                Desktop.getDesktop().open(new File(path));
            //                } catch (IOException ex) {
            //                Logger.getLogger(Notification.class.getName()).log(Level.SEVERE, null, ex);
            ////            }
//            SendFMNotifications(connectDB);//FMApproval
//            sendSCMNotifications(connectDB);//SCM Approval
//            SendVBNotifications(connectDB);
//            Object[] approvallevels = getRQApprovalLevel(connectDB);//SNRManagerApproval
//
//            for (int i = 0; i < approvallevels.length; i++) {
//                System.err.println(approvallevels[i].toString());
//                sendRQNotifications(approvallevels[i].toString(), connectDB);
//            }
            //
            //            Integer value = 20190219;
            //            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");
            //            try {
            //                Date date = originalFormat.parse(value.toString());
            //                SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
            //                String formatedDate = newFormat.format(date);
            //                
            //                System.err.println(formatedDate);
            //            } catch (ParseException ex) {
            //                ex.printStackTrace();
            //            }
//               String leavend =  LeaveFactory.getLeaveEnd(connectDB, "2019-05-02", 17, "ANNUAL LEAVE");
//               
//               System.err.println(leavend);
//             String resumption = LeaveFactory.getResumptionDate(connectDB, leavend);
//             System.err.println(resumption);
            // System.err.println(leavend+"-"+resumption);
            //    getLeaveEnd(connectDB,LeaveFactory.getLeaveEnd(connectDB, leavestart, days,leavetype););
            //
            ////
//            connectDB = custom_connect("postgres", "sequence", "172.16.103.200", "5433", "bespoke");
//            Object[] sups = getApprovers(connectDB, "SUPERVISORS");
//            Object[] hods = getApprovers(connectDB, "HODS");
//            Object[] snrs = getApprovers(connectDB, "SNR");
//
//            SendNotifications(connectDB, sups, "SUPERVISORS");
//            SendNotifications(connectDB, hods, "HODS");
//            SendNotifications(connectDB, snrs, "SENIOR MANAGEMENT");
            //UploadedSigsPDF sig = new UploadedSigsPDF();
            //sig.UploadedSigsPDF(connectDB);
            //            ApproversListPDF sig = new ApproversListPDF();
            //            sig.ApproversListPDF(connectDB);
            //  LeavesGone sig = new LeavesGone();
            //    sig.UploadedSigsPDF(connectDB);
            //     generateLPAD(connectDB, "INV", ""+max+"0", 5);
            //SyncFSMaster(connectDB);
            //   sendEmail(connectDB, fileaddr);
            // Sync(connectDB);
            //  fixDRSCodes(connectDB);
            //   UpdateMasterPrices(connectDB);
            //   updateCodes(connectDB);
            //  getDOW();
            //      getLeaveEnd'(connectDB);
            //    dropHRUsers(connectDB);
            //  getLeaveEnd(connectDB);
            //dateConverter();
            //     insert_users(connectDB);
            // fixPhoneNumbers(connectDB);
            //DesktopNotify.showDesktopMessage("Hello Dear?\n\n\n\n \n", "I love you so much.\n\n \n", DesktopNotify.INPUT_REQUEST, 14000L);
            //            if (SystemTray.isSupported()) {
            //               
            //                try {
            //                    displayTray();
            //                } catch (AWTException ex) {
            //                    Logger.getLogger(Notification.class.getName()).log(Level.SEVERE, null, ex);
            //                } catch (MalformedURLException ex) {
            //                    Logger.getLogger(Notification.class.getName()).log(Level.SEVERE, null, ex);
            //                }
            //            } else {
            //                System.err.println("System tray not supported!");
            //            }
        }

    }

}
