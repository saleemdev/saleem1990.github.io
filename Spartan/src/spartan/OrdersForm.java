/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spartan;

import com.itextpdf.text.DocumentException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import spartan.OrderPDF;

/**
 *
 * @author owner
 */
public class OrdersForm {

    private static javax.swing.JFrame MainFrame = new javax.swing.JFrame("Basic Form");
    private static javax.swing.JTable cartTbl;
    private static javax.swing.JTable cataLogTbl;
    private static javax.swing.JTextField itemIDTxt;
    private static javax.swing.JButton jButton1;
    private static javax.swing.JButton jButton2;
    private static javax.swing.JButton jButton3;
    private static javax.swing.JLabel jLabel1;
    private static javax.swing.JLabel jLabel2;
    private static javax.swing.JLabel jLabel4;
    private static javax.swing.JMenu jMenu1;
    private static javax.swing.JMenu jMenu2;
    private static javax.swing.JMenuBar jMenuBar1;
    private static javax.swing.JScrollPane jScrollPane3;
    private static javax.swing.JScrollPane jScrollPane4;
    private static javax.swing.JTextField qtyTxt;
    private static javax.swing.JTextField totalorderTxt;
    private static Object[] rowitem;
    private static String loggedinUser;

    /*
    These are the core system functions
    
     */
    private static Boolean existsInOrder(String item) {
        Boolean stat = false;
        for (int i = 0; i < cartTbl.getRowCount(); i++) {
            if (cartTbl.getValueAt(i, 2).equals(item)) {
                stat = true;
            }
        }

        return stat;
    }

    private static int row2Edit(String item) {
        int row2edit = 0;

        for (int i = 0; i < cartTbl.getRowCount(); i++) {
            if (cartTbl.getValueAt(i, 2).equals(item)) {
                row2edit = i;
            }
        }
        return row2edit;
    }

    private static void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        //      String file ="C:\\Users\\owner\\Documents\\NetBeansProjects\\Spartan\\src\\spartan\\StoreData.txt";

        if (qtyTxt.getText().length() > 0) {
            if (existsInOrder(itemIDTxt.getText())) {

                int row = row2Edit(itemIDTxt.getText());

                System.err.println("curr val: " + Integer.valueOf(cartTbl.getValueAt(row, 0).toString()));

                Integer addition = Integer.valueOf(cartTbl.getValueAt(row, 0).toString()) + Integer.valueOf(qtyTxt.getText());

                //rowitem[0] = addition;
                cartTbl.setValueAt(addition, row, 0);

            } else {
                rowitem[0] = Integer.valueOf(qtyTxt.getText());

                addRowData(cartTbl, rowitem);

            }

            CalculateOrderTotal();

        } else {
            JOptionPane.showMessageDialog(MainFrame, "Please enter the qty first");
            qtyTxt.requestFocusInWindow();
        }

    }

    private static void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        //   Now we start printing
        String filename = JOptionPane.showInputDialog(MainFrame, "Specify a File Name please(Exclude PDF)");

        String userdir = System.getProperty("user.dir");

        if (filename.length() > 0) {

            // String file = userdir + "\\hello.pdf";
            String file = userdir + "\\" + filename + ".pdf";

          
            try {
                new OrderPDF(file, cartTbl, loggedinUser);
            } catch (DocumentException ex) {
                Logger.getLogger(OrdersForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(OrdersForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
    }

    private static void cataLogTblMouseClicked(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:

        int sel = cataLogTbl.getSelectedRow();

        rowitem = new Object[]{0, cataLogTbl.getValueAt(sel, 1), cataLogTbl.getValueAt(sel, 0), cataLogTbl.getValueAt(sel, 2)};

        String itemID = cataLogTbl.getValueAt(sel, 0).toString();

        itemIDTxt.setText(itemID);
        qtyTxt.requestFocusInWindow();
        qtyTxt.selectAll();

    }

    private static void qtyTxtKeyReleased(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:

        if (qtyTxt.getText().length() > 0) {
            try {
                int x = Integer.parseInt(qtyTxt.getText());
            } catch (NumberFormatException nfe) {

                JOptionPane.showMessageDialog(MainFrame, nfe.getMessage() + "\nInvalid entry");
                qtyTxt.setText("0");
                qtyTxt.selectAll();
            }
        }
    }

    private static void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        removeSelectedRows(cartTbl);
        CalculateOrderTotal();
    }

    private static void cartTblKeyReleased(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
        if (cartTbl.isEditing()) {
            cartTbl.getCellEditor().stopCellEditing();
        }
        CalculateOrderTotal();
    }

    /*
    These Function assist in transaction processing
     */
    private static void CalculateOrderTotal() {
        Double total = 0.0;
        for (int i = 0; i < cartTbl.getRowCount(); i++) {
            Double qty = Double.valueOf(cartTbl.getValueAt(i, 0).toString());
            Double price = Double.valueOf(cartTbl.getValueAt(i, 1).toString());
            Double amt = qty * price;

            total = total + amt;

        }
        DecimalFormat df = new DecimalFormat("####0.00");
        totalorderTxt.setText("$" + df.format(total));
    }

    public static void removeSelectedRows(javax.swing.JTable tableName) {

        int rows2Delete = tableName.getSelectedRowCount();

        int[] selectedRows = tableName.getSelectedRows();

        if (rows2Delete < 1) {

            java.awt.Toolkit.getDefaultToolkit().beep();

            javax.swing.JOptionPane.showMessageDialog(null, "There are no selected rows to delete!");

        } else if (rows2Delete > 1) {

            for (int i = 0; i < selectedRows.length; i++) {

                javax.swing.table.DefaultTableModel defTableModel = (javax.swing.table.DefaultTableModel) tableName.getModel();

                defTableModel.removeRow(selectedRows[i]);

            }

        } else {

            javax.swing.table.DefaultTableModel defTableModel = (javax.swing.table.DefaultTableModel) tableName.getModel();

            defTableModel.removeRow(tableName.getSelectedRow());
        }

    }

    private static void startBusiness() {
        String userdir = System.getProperty("user.dir");
        System.err.println(userdir);;
        String file = userdir + "\\StoreData.txt";
        System.err.println(file);

        populateTbl(file);
    }

    public static void addRowData(javax.swing.JTable tableName, Object[] data) {

        javax.swing.table.DefaultTableModel defTableModel = (javax.swing.table.DefaultTableModel) tableName.getModel();

        defTableModel.addRow(data);

    }

    private static void populateTbl(String fileName) {

        File file = new File(fileName);
        try {
            BufferedReader bR = new BufferedReader(new FileReader(file));
            String firstline = bR.readLine().trim();

            javax.swing.table.DefaultTableModel defTableModel = (javax.swing.table.DefaultTableModel) cataLogTbl.getModel();
            Object[] data = bR.lines().toArray();
            for (int i = 0; i < 5; i++) {
                String lineItem = data[i].toString().trim();
                System.err.println(lineItem);

                String[] columndata = lineItem.split("\t");

                defTableModel.addRow(columndata);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void createAndShowGUI(String user, String id) {

        /*
        This method creates and shows the GUI Form
        It sets the components
         */
        loggedinUser = user;
        java.awt.GridBagConstraints gridBagConstraints;

        MainFrame.setTitle("Logged In user: " + user);
        MainFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        MainFrame.setTitle("Logged In User: " + user);
        MainFrame.setMinimumSize(new java.awt.Dimension(700, 600));
        MainFrame.setPreferredSize(new java.awt.Dimension(700, 600));
        MainFrame.getContentPane().setLayout(new java.awt.GridBagLayout());
        MainFrame.setVisible(true);
        MainFrame.setLocationRelativeTo(null);

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        itemIDTxt = new javax.swing.JTextField();
        qtyTxt = new javax.swing.JTextField();
        totalorderTxt = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        cartTbl = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        cataLogTbl = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        jLabel1.setText("Item #");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        MainFrame.getContentPane().add(jLabel1, gridBagConstraints);

        jLabel2.setText("Qty");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weighty = 1.0;
        MainFrame.getContentPane().add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        MainFrame.getContentPane().add(itemIDTxt, gridBagConstraints);

        qtyTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                qtyTxtKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 1.0;
        MainFrame.getContentPane().add(qtyTxt, gridBagConstraints);

        totalorderTxt.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        MainFrame.getContentPane().add(totalorderTxt, gridBagConstraints);

        jButton1.setText("Add");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        MainFrame.getContentPane().add(jButton1, gridBagConstraints);

        jButton2.setText("Remove");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        MainFrame.getContentPane().add(jButton2, gridBagConstraints);

        jButton3.setText("Done");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        MainFrame.getContentPane().add(jButton3, gridBagConstraints);

        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder("Cart"));

        cartTbl.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cartTbl.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "", "", "", ""
                }
        ) {
            boolean[] canEdit = new boolean[]{
                true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        cartTbl.setShowHorizontalLines(false);
        cartTbl.setShowVerticalLines(false);
        cartTbl.getTableHeader().setReorderingAllowed(false);
        cartTbl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cartTblKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(cartTbl);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 10.0;
        MainFrame.getContentPane().add(jScrollPane3, gridBagConstraints);

        jScrollPane4.setBorder(javax.swing.BorderFactory.createTitledBorder("Catalog"));

        cataLogTbl.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "", "", ""
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        cataLogTbl.setShowHorizontalLines(false);
        cataLogTbl.setShowVerticalLines(false);
        cataLogTbl.getTableHeader().setReorderingAllowed(false);
        cataLogTbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cataLogTblMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(cataLogTbl);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 10.0;
        MainFrame.getContentPane().add(jScrollPane4, gridBagConstraints);

        jLabel4.setText("Order Total");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        MainFrame.getContentPane().add(jLabel4, gridBagConstraints);

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        MainFrame.setJMenuBar(jMenuBar1);

        MainFrame.pack();

        startBusiness();
    }

}
