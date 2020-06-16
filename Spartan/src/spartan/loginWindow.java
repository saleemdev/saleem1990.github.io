/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spartan;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.swing.JDialog;
//import spartan.Orders;

/**
 *
 * @author owner
 */
public class loginWindow {

    /*
    Interface components need to be declared globally so that it is easier to access them in private methods
    
     */
    static JDialog logindialog = new javax.swing.JDialog(new java.awt.Frame(), "Login Frame");
    static javax.swing.JButton jButton1;
    static javax.swing.JButton jButton2;
    static javax.swing.JButton jButton3;
    static javax.swing.JLabel jLabel1;
    static javax.swing.JPasswordField jPasswordField1;
    static javax.swing.JTextField jTextField1;

    private static void createAndShowDialog() {
        java.awt.GridBagConstraints gridBagConstraints;

        logindialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        logindialog.setAlwaysOnTop(true);
        logindialog.getContentPane().setLayout(new java.awt.GridBagLayout());
        logindialog.setVisible(true);
        logindialog.setMinimumSize(new java.awt.Dimension(400, 300));
        logindialog.setModal(true);
        logindialog.setLocationRelativeTo(null);

        //Created an empty Dialog
        jButton2 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();

        jButton2.setText("jButton2");
        //Created and initialized variables

        jTextField1.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jTextField1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Enter UserName here", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(0, 51, 204))); // NOI18N
        jTextField1.setMinimumSize(new java.awt.Dimension(150, 100));
        jTextField1.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextField1CaretUpdate(evt);
            }
        });
        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField1FocusGained(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }

            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        logindialog.getContentPane().add(jTextField1, gridBagConstraints);

        jButton1.setText("Login");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        logindialog.getContentPane().add(jButton1, gridBagConstraints);

        jButton3.setText("Close");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        logindialog.getContentPane().add(jButton3, gridBagConstraints);

        jPasswordField1.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jPasswordField1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Enter Password here", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(204, 0, 51))); // NOI18N
        jPasswordField1.setMinimumSize(new java.awt.Dimension(150, 100));
        jPasswordField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jPasswordField1KeyReleased(evt);
            }

            public void keyTyped(java.awt.event.KeyEvent evt) {
                jPasswordField1KeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        logindialog.getContentPane().add(jPasswordField1, gridBagConstraints);

        jLabel1.setForeground(new java.awt.Color(204, 0, 51));
        jLabel1.setText(".");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.weighty = 1.0;
        logindialog.getContentPane().add(jLabel1, gridBagConstraints);

        logindialog.pack();
    }

    /*This part shows the basic functions of the components
    After declaring the objects, we assigned events  for activities performed on the components, like typing etc
    
     */
    private static void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:

        if (jTextField1.getText().length() > 0 && jPasswordField1.getText().length() > 0) {

            String usr = jTextField1.getText().trim(), pwd = jPasswordField1.getText().trim();

            //   if (jTextField1.getText().trim().equalsIgnoreCase(usr) || jPasswordField1.getText().trim().equalsIgnoreCase(pwd)) {
            logindialog.dispose();

            OrdersForm.createAndShowGUI(usr, pwd);
            

            //  } else {
            // jLabel1.setText("Wrong password");
            //  }
        } else {

            if (jPasswordField1.getText().length() < 1 && jTextField1.getText().length() < 1) {
                jLabel1.setText("Can't login with empty fields");
                jPasswordField1.requestFocusInWindow();
            } else {
                if (jTextField1.getText().length() < 1) {
                    jLabel1.setText("Username Can't be empty");
                    jTextField1.requestFocusInWindow();
                }

                if (jPasswordField1.getText().length() < 1) {
                    jLabel1.setText("Password Can't be empty");
                    jPasswordField1.requestFocusInWindow();
                }
            }
        }

    }

    private static void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        System.exit(0);
    }

    private static void jTextField1FocusGained(java.awt.event.FocusEvent evt) {
        // TODO add your handling code here:
        checkForCaps();

    }

    private static void formKeyTyped(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:

    }

    private static void jTextField1CaretUpdate(javax.swing.event.CaretEvent evt) {
        // TODO add your handling code here:
        checkForCaps();

    }

    private static void checkForCaps() {
        boolean isOn = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);

        if (isOn) {
            jLabel1.setText("Caps Lock is On");
        } else {
            jLabel1.setText("");

        }
    }

    private static void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
        checkForCaps();
    }

    private static void jPasswordField1KeyTyped(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            jButton1.doClick();
        }
    }

    private static void jPasswordField1KeyReleased(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
        checkForCaps();
    }

    private static void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            jButton1.doClick();
        }
    }

    //Ends here
    public static void main(String args[]) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowDialog();
            }
        });
    }
}
