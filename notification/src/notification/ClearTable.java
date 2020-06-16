/*     */ package notification;
/*     */ 
/*     */ import java.awt.Toolkit;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.table.DefaultTableModel;
/*     */ import javax.swing.table.TableModel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClearTable
/*     */ {
/*     */   public static void clearthisTable(JTable tableName)
/*     */   {
/*  27 */     for (int k = 0; k < tableName.getModel().getRowCount(); k++) {
/*  28 */       for (int r = 0; r < tableName.getModel().getColumnCount(); r++) {
/*  29 */         tableName.getModel().setValueAt(null, k, r);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static void removeSelectedRows(JTable tableName)
/*     */   {
/*  37 */     int rows2Delete = tableName.getSelectedRowCount();
/*     */     
/*  39 */     int[] selectedRows = tableName.getSelectedRows();
/*     */     
/*  41 */     if (rows2Delete < 1)
/*     */     {
/*  43 */       Toolkit.getDefaultToolkit().beep();
/*     */       
/*  45 */       JOptionPane.showMessageDialog(null, "There are no selected rows to delete!");
/*     */     }
/*  47 */     else if (rows2Delete > 1)
/*     */     {
/*  49 */       for (int i = 0; i < selectedRows.length; i++)
/*     */       {
/*  51 */         DefaultTableModel defTableModel = (DefaultTableModel)tableName.getModel();
/*     */         
/*  53 */         defTableModel.removeRow(selectedRows[i]);
/*     */       }
/*     */       
/*     */     }
/*     */     else
/*     */     {
/*  59 */       DefaultTableModel defTableModel = (DefaultTableModel)tableName.getModel();
/*     */       
/*  61 */       defTableModel.removeRow(tableName.getSelectedRow());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static void removeRowIndex(JTable tableName, int index)
/*     */   {
/*  68 */     DefaultTableModel defTableModel = (DefaultTableModel)tableName.getModel();
/*     */     
/*  70 */     defTableModel.removeRow(index);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ /*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void ClearTextFieldsInClass(String classname)
/*     */   {
/*     */     try
/*     */     {
/*  81 */       Class classMain = Class.forName(classname);
/*     */       
/*  83 */       Field[] fieldsArray = classMain.getDeclaredFields();
/*     */       /*     */       
/*  85 */       for (int n = 0; n < fieldsArray.length; n++) {
/*  86 */         if (fieldsArray[n].toString().endsWith("xt")) {
/*     */           try {
/*  88 */             JTextField disText = (JTextField)fieldsArray[n].get(classMain);
/*  89 */             disText.setText(null);
/*     */           }
/*     */           catch (IllegalArgumentException ex) {
/*  92 */             Logger.getLogger(ClearTable.class.getName()).log(Level.SEVERE, null, ex);
/*     */           } catch (IllegalAccessException ex) {
/*  94 */             Logger.getLogger(ClearTable.class.getName()).log(Level.SEVERE, null, ex);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (ClassNotFoundException ex)
/*     */     {
/* 101 */       Logger.getLogger(ClearTable.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files\Trial Folder\SmallPOS.jar!\lib\ClearTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */