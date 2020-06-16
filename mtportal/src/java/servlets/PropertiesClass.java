/*     */ package servlets;
/*     */ 
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Properties;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PropertiesClass
/*     */ {
/*  14 */   public static String myAppFileUrl = null;
/*     */   
/*     */   public static Properties appProp;
/*     */   
/*     */   String path2Acrobat;
/*     */   
/*     */   String defaultlnf;
/*     */   String cashpoint;
/*     */   String papersize_width;
/*     */   String papersize_legnth;
/*     */   String backgrdimg;
/*     */   String defaulttheme;
/*     */   String dbPort;
/*     */   String dbServerIP;
/*     */   String font_type_name;
/*     */   String activeDatabase;
/*     */   String receiptFontSize;
/*     */   String receiptTitleFontSize;
/*     */   String receiptPageMargin;
/*     */   Properties sysProp;
/*     */   String docsdir;
/*     */   String defaultSplitPane;
/*     */   public String phraseSeparator;
/*     */   public String lineCharacter;
/*     */   public String dottedLineCharacter;
/*     */   public String newLineCharacter;
/*     */   public String linesPerPage;
/*     */   public String charactersPerLine;
/*     */   public String rcptLinesPerPage;
/*     */   public String rcptCharsPerPage;
/*     */   public String defaultPrinter;
/*     */   public String companyLogo;
/*     */   public String waterMark;
/*     */   private String imageDir;
/*     */   private String mailSmtpHost;
/*     */   private String claimFromAddress;
/*     */   
/*     */   public static String propURL()
/*     */   {
/*  53 */     return 
/*  54 */       System.getProperty("user.dir") + System.getProperty("file.separator") + "hosprop.properties";
/*     */   }
/*     */   
/*     */   public static void getProperties()
/*     */   {
/*  59 */     String myAppFileUrl = null;
/*     */     
/*  61 */     appProp = new Properties();
/*  62 */     myAppFileUrl = propURL();
/*     */     
/*     */     try
/*     */     {
/*  66 */       FileInputStream propInFile = new FileInputStream(myAppFileUrl);
/*     */       
/*     */       try
/*     */       {
/*  70 */         System.out.println("Properties file : " + myAppFileUrl);
/*     */         
/*  72 */         appProp.load(propInFile);
/*     */         
/*     */ 
/*  75 */         propInFile.close();
/*     */ 
/*     */       }
/*     */       catch (IOException ioExec)
/*     */       {
/*  80 */         System.out.println(ioExec.getMessage());
/*     */       }
/*     */       
/*     */ 
/*     */     }
/*     */     catch (Exception exc)
/*     */     {
/*  87 */       System.out.println(exc.getMessage());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void storeProperties()
/*     */   {
/*  95 */     myAppFileUrl = propURL();
/*     */     
/*     */     try
/*     */     {
/*  99 */       FileOutputStream propOutFile = new FileOutputStream(myAppFileUrl);  
/*     */       
/*     */       try
/*     */       {
/* 103 */         System.out.println("Properties file : " + myAppFileUrl);
/*     */         
/* 105 */         appProp.store(propOutFile, "Bespoke properties file");
/*     */         
/* 107 */         propOutFile.close();
/*     */ 
/*     */       }
/*     */       catch (IOException ioExec)
/*     */       {
/* 112 */         System.out.println(ioExec.getMessage());
/*     */       }
/*     */       
/*     */ 
/*     */     }
/*     */     catch (Exception exc)
/*     */     {
/* 119 */       System.out.println(exc.getMessage());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static Object getpropValue(String propertyName)
/*     */   {
/* 126 */     String value = null;
/*     */     
/* 128 */     myAppFileUrl = propURL();
/* 129 */     appProp = new Properties();
/*     */     try
/*     */     {
/* 132 */       FileInputStream propInFile = new FileInputStream(myAppFileUrl);
/*     */       try
/*     */       {
/* 135 */         System.out.println("Again, printing my Properties file : " + myAppFileUrl);
/*     */         
/* 137 */         appProp.load(propInFile);
/*     */         
/*     */ 
/* 140 */         value = appProp.getProperty(propertyName);
/*     */         
/* 142 */         propInFile.close();
/*     */       }
/*     */       catch (IOException ioExec)
/*     */       {
/* 146 */         System.out.println(ioExec.getMessage());
/*     */       }
/*     */       
/*     */     }
/*     */     catch (Exception exc)
/*     */     {
/* 152 */       System.out.println(exc.getMessage());
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 157 */     System.err.println(value);
/*     */     
/* 159 */     return value;
/*     */   }
/*     */   
/*     */   public void setDB() {
/* 163 */     this.activeDatabase = getpropValue("activeDatabase").toString();
/*     */   }
/*     */   
/*     */   public String getDB() {
/* 167 */     setDB();
/* 168 */     return this.activeDatabase;
/*     */   }
/*     */   
/*     */   public void setDBPort() {
/* 172 */     this.dbPort = getpropValue("dbPort").toString();
/*     */   }
/*     */   
/*     */   public String getDBPort() {
/* 176 */     setDBPort();
/* 177 */     return this.dbPort;
/*     */   }
/*     */   
/*     */   public void setPath2DB() {
/* 181 */     this.dbServerIP = getpropValue("dbServerIpAdd").toString();
/*     */   }
/*     */   
/*     */   public String getPath2DB() {
/* 185 */     setPath2DB();
/*     */     
/* 187 */     return this.dbServerIP;
/*     */   }
/*     */ }


/* Location:              C:\Program Files\Trial Folder\SmallPOS.jar!\smallpos\PropertiesFunctions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */