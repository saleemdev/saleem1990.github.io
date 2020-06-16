/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ihrishandshake;

/**
 *
 * @author owner
 */
public class PropertiesFunctions {

    public static String myAppFileUrl = null;
    public static java.util.Properties appProp;
    java.lang.String path2Acrobat;
    java.lang.String defaultlnf;
    java.lang.String cashpoint;
    java.lang.String papersize_width;
    java.lang.String papersize_legnth;
    java.lang.String backgrdimg;
    java.lang.String defaulttheme;
    java.lang.String dbPort;
    java.lang.String font_type_name;
    java.lang.String activeDatabase;
    java.lang.String receiptFontSize;
    java.lang.String receiptTitleFontSize;
    java.lang.String receiptPageMargin;
    java.util.Properties sysProp;
    java.lang.String docsdir;
    java.lang.String defaultSplitPane;
    public String phraseSeparator;
    public String lineCharacter;
    public String dottedLineCharacter;
    public String newLineCharacter;
    public String linesPerPage;
    public String charactersPerLine;
    public String rcptLinesPerPage;
    public String rcptCharsPerPage;
    public String defaultPrinter;
    public String companyLogo;
    public String waterMark;
    private String imageDir;
    private String mailSmtpHost;
    private String claimFromAddress;

    public PropertiesFunctions() {

    }

    public static String propURL() {
        return System.getProperty("user.dir")
                + System.getProperty("file.separator")
                + "hosprop.properties";
    }

    public static void getProperties() {
        java.lang.String myAppFileUrl = null;

        appProp = new java.util.Properties();
        myAppFileUrl = propURL();

        try {
            //catch java.lang.ClassNotFoundException(this.show(This is not true));
            java.io.FileInputStream propInFile = new java.io.FileInputStream(myAppFileUrl);

            //       java.io.FileOutputStream propOutFile = new java.io.FileOutputStream("myapp.properties");
            try {
                System.out.println("Properties file : " + myAppFileUrl);

                appProp.load(propInFile);

//              
                propInFile.close();

                //  return dbServerIp;
            } catch (java.io.IOException ioExec) {

                System.out.println(ioExec.getMessage());

            }

            // return dbServerIp;
        } catch (java.lang.Exception exc) {

            System.out.println(exc.getMessage());            
        }
    }

    public static void storeProperties() {

        myAppFileUrl = propURL();

        try {

            java.io.FileOutputStream propOutFile = new java.io.FileOutputStream(myAppFileUrl);

            //       java.io.FileOutputStream propOutFile = new java.io.FileOutputStream("myapp.properties");
            try {
               // System.out.println("Properties file : " + myAppFileUrl);
                
                appProp.store(propOutFile, "Bespoke properties file");

                propOutFile.close();

                //  return dbServerIp;
            } catch (java.io.IOException ioExec) {

                System.out.println(ioExec.getMessage());

            }

            // return dbServerIp;
        } catch (java.lang.Exception exc) {

            System.out.println(exc.getMessage());

            //    javax.swing.JOptionPane.showMessageDialog(this, "Properties file not found!");
        }
    }

    public static Object getpropValue(String propertyName) {
        String value = null;

        myAppFileUrl = propURL();
        appProp = new java.util.Properties();

        try {
            java.io.FileInputStream propInFile = new java.io.FileInputStream(myAppFileUrl);
            try {

              //  System.out.println("Again, printing my Properties file : " + myAppFileUrl);

                appProp.load(propInFile);

//              
                value = (appProp.getProperty(propertyName));;

                propInFile.close();

            } catch (java.io.IOException ioExec) {

                System.out.println(ioExec.getMessage());

            }
            // return dbServerIp;
        } catch (java.lang.Exception exc) {

            System.out.println(exc.getMessage());

            //    javax.swing.JOptionPane.showMessageDialog(this, "Properties file not found!");
        }

        System.err.println(value);

        return value;
    }

    public static void setpropValue(String propertyName, String val) {
       // String value = null;

        myAppFileUrl = propURL();
        appProp = new java.util.Properties();

        try {
            java.io.FileOutputStream propOutFile = new java.io.FileOutputStream(myAppFileUrl);
            //System.out.println("Again, printing my Properties file : " + myAppFileUrl);
            appProp.setProperty(propertyName, java.lang.String.valueOf(val));
            System.setProperty(propertyName, val.toString());
            propOutFile.close();
            storeProperties();
        } catch (java.lang.Exception exc) {

            System.out.println(exc.getMessage());

            //    javax.swing.JOptionPane.showMessageDialog(this, "Properties file not found!");
        }

        System.err.println(val);

    }

}
