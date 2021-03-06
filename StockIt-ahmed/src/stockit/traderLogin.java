/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockit;

import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author ahmed
 */
public class traderLogin extends javax.swing.JFrame {

    private final String definedTrader = "trader"; //hardcoded values for manage
    private final String definedPassword = "secret";
    private static String title;

    /**
     * Creates new form traderLogin
     */
    public traderLogin() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerLabel = new javax.swing.JLabel();
        username = new javax.swing.JLabel();
        password = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        usernameField = new javax.swing.JTextPane();
        passwordField1 = new javax.swing.JPasswordField();
        login_Button = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(title);

        headerLabel.setText("StockIt");

        username.setText("Username:");
        username.setToolTipText("Enter your username");

        password.setText("Password:");
        password.setToolTipText("Enter your password");

        jScrollPane2.setViewportView(usernameField);

        passwordField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passwordField1KeyPressed(evt);
            }
        });

        login_Button.setText("Login");
        login_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                login_ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(passwordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(username)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(60, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(login_Button)
                        .addGap(144, 144, 144))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(headerLabel)
                        .addGap(161, 161, 161))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(headerLabel)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(password)
                    .addComponent(passwordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(login_Button)
                .addContainerGap(100, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void login_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_login_ButtonActionPerformed
        loginAction();
    }//GEN-LAST:event_login_ButtonActionPerformed

    private void passwordField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordField1KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            loginAction();
        }
    }//GEN-LAST:event_passwordField1KeyPressed

    private void loginAction() {
        try {
            // TODO add your handling code here:
            String usname = usernameField.getText();
            //System.out.println(usname);
            char[] str = passwordField1.getPassword();
            String newStr = new String(str);
            //System.out.println(str);
            DBConnection dbcon = new DBConnection();
            dbcon.establishConnection();
            Statement stmt = dbcon.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT username, password FROM trader_account WHERE username= '" + usname + "' AND password = '" + newStr + "'");
            boolean hasValue = false;
            String[] array = new String[2];
            while (rs.next()) {
                hasValue = true;
                System.out.println("Username: " + rs.getString("username"));
                System.out.println("Password: " + rs.getString("password"));
                array[0] = rs.getString("username");
            }
            ResultSet rs1 = stmt.executeQuery("SELECT Name FROM investment_company, trader, trader_account WHERE investment_company.Id = trader.Investment_Company_ID AND trader.Trader_SSN = trader_account.Trader_SSN AND trader_account.username = \"" + array[0] + "\"");
            while (rs1.next()) {
                System.out.println("Company Name: " + rs1.getString("Name"));
                array[1] = rs1.getString("Name");
            }
            dbcon.con.close();
            //for manager, same goes for trader and clients
            if (hasValue) { //get information from database and see if it exist
                dispose();
                Trader.main(array);
            } else {
                JOptionPane.showMessageDialog(getContentPane(), "Incorrect Username or Password", "Login Error", JOptionPane.ERROR_MESSAGE);
                usernameField.setText("");
                passwordField1.setText("");
            }
        } catch (Exception ex) {
            Logger.getLogger(clientLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(traderLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(traderLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(traderLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(traderLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            title = args[0];
            new traderLogin().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel headerLabel;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton login_Button;
    private javax.swing.JLabel password;
    private javax.swing.JPasswordField passwordField1;
    private javax.swing.JLabel username;
    private javax.swing.JTextPane usernameField;
    // End of variables declaration//GEN-END:variables
}
