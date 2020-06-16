/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spartan;

import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

/**
 *
 * @author owner
 */
public class ShippingWindow {
    
    private static JDialog Shipping = new JDialog();
    
    private static javax.swing.JButton jButton1;
    private static javax.swing.JButton jButton2;
    private static javax.swing.JLabel jLabel1;
    private static javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTable jTable1;
    private static javax.swing.JTextField jTextField1;
    
    public static JTable cartt;public static String loggedinuser;
   
    
    
    
    private static void populateForm(String order, JTable table){
        jTextField1.setText(order);
        jTable1.setModel(table.getModel());
        
        jTable1.removeColumn(jTable1.getColumn(1));
        
    }
     private static void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        Shipping.dispose();
        
    } 
     
     private static void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        String filename = JOptionPane.showInputDialog(Shipping, "Specify a File Name please(Exclude PDF)");

        String userdir = System.getProperty("user.dir");

        if (filename.length() > 0) {

            // String file = userdir + "\\hello.pdf";
            String file = userdir + "\\" + filename + ".pdf";

          
            try {
                new ShippingPDF(file, cartt, loggedinuser);
            } catch (DocumentException ex) {
                Logger.getLogger(OrdersForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(OrdersForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
    }  
    public static  void showGUI(String order_no, JTable table1, String usr){
        //start by assigning variables new data
        cartt =table1;
        loggedinuser =usr;
        
        
        //LEt us add the rest of the elements now and format the GUI
        
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        Shipping.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        Shipping.setAlwaysOnTop(true);
        Shipping.setFocusTraversalPolicyProvider(true);
        Shipping.setMinimumSize(new java.awt.Dimension(400, 300));
        Shipping.getContentPane().setLayout(new java.awt.GridBagLayout());
        Shipping.setLocationRelativeTo(null);
        Shipping.setVisible(true);

        jLabel1.setText("Order Number");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        Shipping.getContentPane().add(jLabel1, gridBagConstraints);

        jTextField1.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        Shipping.getContentPane().add(jTextField1, gridBagConstraints);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 10.0;
        Shipping.getContentPane().add(jScrollPane1, gridBagConstraints);

        jButton1.setText("Ship");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        Shipping.getContentPane().add(jButton1, gridBagConstraints);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Quit");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        Shipping.getContentPane().add(jButton2, gridBagConstraints);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        Shipping.pack();
        
        populateForm(order_no,table1);
        
        
        
    }
    
}
