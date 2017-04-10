/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockit;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ahmed
 */
public class transactionFrame extends javax.swing.JFrame {
    private static String clientName;
    //Timer t;
    final static int interval = 100;
    int i;
    /**
     * Creates new form transactionFrame
     */
    public transactionFrame() {
        initComponents();
        setupTable();
        setupTable2();
    }
    Timer t = new Timer(interval, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(i == 100){
                    t.stop();
                    doneOrNot.setText("Done");
                    int row = table.getSelectedRow();
                    if(row != -1){
                        String stockName = table.getValueAt(row,0).toString();
                        try{
                            DBConnection dbcon = new DBConnection();
                            dbcon.establishConnection();
                            Statement stmt = dbcon.con.createStatement();
                            ResultSet rs = stmt.executeQuery("Select Account_ID\n" +
                                "FROM account as a, client as c\n" +
                                "WHERE a.Client_SSN = c.Client_SSN AND c.Name = '"+clientName+"'");
                            int accountID = 1; 
                            while(rs.next()){
                                accountID = rs.getInt("Account_ID");
                            }        
                           stmt.executeUpdate("INSERT INTO has_positions (Account_ID, Stock_ID, Qty) VALUES("+accountID+",'"+stockName+"', 1) ON DUPLICATE KEY UPDATE\n" +
                                "Stock_ID='"+stockName+"', QTY= QTY + 1");
                           stmt.executeUpdate("UPDATE stock_daily_performance SET Volume = Volume - 1 WHERE stock_daily_performance.StockID = '"+stockName+"'");
                            dbcon.con.close();
                        }catch(Exception ex){
                            Logger.getLogger(clientLogin.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    Sell.setEnabled(true);
                    table1.setEnabled(true);
                    setupTable2();
                    setupTable();
                    sellProgress.setEnabled(true);
                    buyProgress.setValue(0);
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    
                }else{
                    i++;
                    buyProgress.setValue(i);
                }
            }
        
        });
    
    Timer t1 = new Timer(interval, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(i == 100){
                    t1.stop();
                    sellDoneOrNot.setText("Done");
                    int row = table1.getSelectedRow();
                    if(row != -1){
                        String stockName = table1.getValueAt(row,0).toString();
                        try{
                            DBConnection dbcon = new DBConnection();
                            dbcon.establishConnection();
                            Statement stmt = dbcon.con.createStatement();
                             ResultSet rs = stmt.executeQuery("Select Account_ID\n" +
                                "FROM account as a, client as c\n" +
                                "WHERE a.Client_SSN = c.Client_SSN AND c.Name = '"+clientName+"'");
                            int accountID = 1; 
                            while(rs.next()){
                                rs.getInt("Account_ID");
                            } 
                            stmt.executeUpdate("UPDATE has_positions SET Qty = Qty - 1 WHERE has_positions.Account_ID = "+accountID+" AND has_positions.Stock_ID = '"+stockName+"'");
                            stmt.executeUpdate("UPDATE stock_daily_performance SET Volume = Volume + 1 WHERE stock_daily_performance.StockID = '"+stockName+"'");
                            dbcon.con.close();
                        }catch(Exception ex){
                            Logger.getLogger(clientLogin.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    Buy.setEnabled(true);
                    table.setEnabled(true);
                    buyProgress.setEnabled(true);
                    sellProgress.setValue(0);
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    setupTable2();
                    setupTable();
       
                }else{
                    i++;
                    sellProgress.setValue(i);
                }
            }
        
        });
    private void setSelectedStock(){
        int row = table1.getSelectedRow();
        variableStockClient.setText(table1.getValueAt(row, 0).toString());
    }
    private void setSelectedStock2(){
        int row = table.getSelectedRow();
        variableStockChange.setText(table.getValueAt(row, 0).toString());
    }
    public void setupTable(){
        try{
            DBConnection dbcon = new DBConnection();
            dbcon.establishConnection();
            Statement stmt = dbcon.con.createStatement();
            ResultSet rs = stmt.executeQuery("select stock.StockID, stock.Publicly_Traded, stock.StockName, stock_sector.Sector, stock_daily_performance.Volume, stock_daily_performance.Currency, stock_daily_performance.Opening_Price, stock_daily_performance.Closing_Price, stock_daily_performance.High, stock_daily_performance.Low, stock_daily_performance.Date\n" +
                "from stock, stock_sector, stock_daily_performance\n" +
                "WHERE stock.StockID = stock_sector.StockID AND stock_daily_performance.StockID = stock.StockID AND stock_daily_performance.Date IN\n" +
                "( Select * from\n" +
                "(\n" +
                "SELECT max(Date) \n" +
                "FROM stock_daily_performance, stock\n" +
                "where stock_daily_performance.StockID=stock.StockID\n" +
                "group by stock.StockID\n" +
                "ORDER BY Date \n" +
                "\n" +
                ") temp_table)");
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            while(rs.next()){
                model.addRow(new Object[]{rs.getString("StockID"), rs.getString("StockName"), rs.getString("Publicly_Traded"),rs.getString("Sector"), rs.getString("Volume"),rs.getString("Currency"), 
                rs.getString("Opening_Price"), rs.getString("Closing_Price"), rs.getString("High"), rs.getString("Low"), rs.getString("Date")});
            }
            dbcon.con.close();
        }catch(Exception ex){
            Logger.getLogger(clientLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setupTable2(){
        try{
            DBConnection dbcon = new DBConnection();
            dbcon.establishConnection();
            Statement stmt = dbcon.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT stock.stockID, stock.StockName, stock_sector.Sector, stock_daily_performance.Currency, stock_daily_performance.High, stock_daily_performance.Low, stock_daily_performance.Closing_Price, has_positions.Qty, (stock_daily_performance.Opening_Price - stock_daily_performance.Closing_Price) AS Today_Change, ((stock_daily_performance.Opening_Price - stock_daily_performance.Closing_Price) * has_positions.Qty) AS Profit\n" +
"FROM stock, stock_sector, stock_daily_performance, has_positions, account, client\n" +
"WHERE client.Name = '" + clientName + "'" + "AND client.Client_SSN = account.Client_SSN AND account.Account_ID = has_positions.Account_ID AND stock_daily_performance.StockID = has_positions.Stock_ID AND stock.StockID = has_positions.Stock_ID AND stock_sector.StockID = has_positions.Stock_ID AND Date IN\n" +
"( Select * from \n" +
"(\n" +
"SELECT max(Date) \n" +
"FROM stock_daily_performance, stock\n" +
"where stock_daily_performance.StockID=stock.StockID\n" +
"group by stock.StockID\n" +
"ORDER BY Date\n" +
") temp_table)");
            DefaultTableModel model = (DefaultTableModel) table1.getModel();
            model.setRowCount(0);
            while(rs.next()){
                System.out.println(rs.getString("stockID")+ "\t" + rs.getString("StockName")+"\t"+rs.getString("Sector")+"\t"+rs.getString("Currency")+"\t"+rs.getString("Closing_Price")+"\t"+rs.getString("Qty")+rs.getString("Today_Change"));
                model.addRow(new Object[]{rs.getString("stockID"), rs.getString("StockName"), rs.getString("Sector"), rs.getString("Qty"), rs.getString("High"), rs.getString("Low"), rs.getString("Closing_Price"), rs.getString("Today_Change"),rs.getString("Currency"), rs.getString("Profit")});
            }
            dbcon.con.close();
        }catch(Exception ex){
            Logger.getLogger(clientLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        Buy = new javax.swing.JButton();
        Sell = new javax.swing.JButton();
        buyProgress = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        table1 = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        sellProgress = new javax.swing.JProgressBar();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        variableStockChange = new javax.swing.JLabel();
        variableStockClient = new javax.swing.JLabel();
        doneOrNot = new javax.swing.JLabel();
        sellProgressText = new javax.swing.JLabel();
        sellDoneOrNot = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Symbol", "Stock Name", "Public", "Sector", "Volume", "Currency", "Opening Price", "Closing Price", "High", "Low", "Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                tableMouseDragged(evt);
            }
        });
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tableKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(table);

        Buy.setText("Buy");
        Buy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuyActionPerformed(evt);
            }
        });

        Sell.setText("Sell");
        Sell.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SellActionPerformed(evt);
            }
        });

        buyProgress.setStringPainted(true);

        jLabel1.setText("Progress:");

        table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Symbol", "Name", "Sector", "Quantity", "High", "Low", "Last", "Today Change", "Currency", "Profit/Loss"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                table1MouseDragged(evt);
            }
        });
        table1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table1MouseClicked(evt);
            }
        });
        table1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                table1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                table1KeyReleased(evt);
            }
        });
        jScrollPane4.setViewportView(table1);

        sellProgress.setStringPainted(true);

        jLabel2.setText("Stock Market:");

        jLabel3.setText("Clients Stock:");

        jLabel4.setText("Buy/Sell Stocks");

        sellProgressText.setText("Progress:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addComponent(buyProgress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator1)
                            .addComponent(sellProgress, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Buy)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(doneOrNot, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(Sell)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(variableStockChange, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(variableStockClient, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(398, 398, 398)
                                .addComponent(jLabel4))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(sellProgressText)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sellDoneOrNot, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 408, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(variableStockChange, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Buy)
                .addGap(18, 18, 18)
                .addComponent(buyProgress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(doneOrNot, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(variableStockClient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Sell)
                .addGap(18, 18, 18)
                .addComponent(sellProgress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sellProgressText)
                    .addComponent(sellDoneOrNot, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void table1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table1MouseDragged
        // TODO add your handling code here:
        setSelectedStock();
    }//GEN-LAST:event_table1MouseDragged

    private void table1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table1MouseClicked
        // TODO add your handling code here:
        setSelectedStock();
    }//GEN-LAST:event_table1MouseClicked

    private void table1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_table1KeyPressed
        // TODO add your handling code here:
        //up arrow (40), down arrow (38), enter-key (10)
        if(evt.getKeyCode() == 40 || evt.getKeyCode() == 38 || evt.getKeyCode() == 10){
            setSelectedStock();
        }
    }//GEN-LAST:event_table1KeyPressed

    private void table1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_table1KeyReleased
        // TODO add your handling code here:
        //up arrow (40), down arrow (38), enter-key (10)
        if(evt.getKeyCode() == 40 || evt.getKeyCode() == 38 || evt.getKeyCode() == 10){
            setSelectedStock();
            //System.out.println("after: " + StockInfoTable.getSelectedRow());
        }
    }//GEN-LAST:event_table1KeyReleased

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        // TODO add your handling code here:
         setSelectedStock2();
    }//GEN-LAST:event_tableMouseClicked

    private void tableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableKeyPressed
        // TODO add your handling code here:
        //up arrow (40), down arrow (38), enter-key (10)
        if(evt.getKeyCode() == 40 || evt.getKeyCode() == 38 || evt.getKeyCode() == 10){
            setSelectedStock2();
        }
    }//GEN-LAST:event_tableKeyPressed

    private void tableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableKeyReleased
        // TODO add your handling code here:
        //up arrow (40), down arrow (38), enter-key (10)
        if(evt.getKeyCode() == 40 || evt.getKeyCode() == 38 || evt.getKeyCode() == 10){
            setSelectedStock2();
            //System.out.println("after: " + StockInfoTable.getSelectedRow());
        }
    }//GEN-LAST:event_tableKeyReleased

    private void tableMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseDragged
        // TODO add your handling code here:
         setSelectedStock2();
    }//GEN-LAST:event_tableMouseDragged

    private void BuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuyActionPerformed
        // TODO add your handling code here:
        Sell.setEnabled(false);
        table1.setEnabled(false);
        sellProgress.setEnabled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        i =0;
        t.start();
    }//GEN-LAST:event_BuyActionPerformed

    private void SellActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SellActionPerformed
        // TODO add your handling code here:
        Buy.setEnabled(false);
        table.setEnabled(false);
        buyProgress.setEnabled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        i =0;
        t1.start();   
    }//GEN-LAST:event_SellActionPerformed

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
            java.util.logging.Logger.getLogger(transactionFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(transactionFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(transactionFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(transactionFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        clientName = args[0];
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new transactionFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Buy;
    private javax.swing.JButton Sell;
    private javax.swing.JProgressBar buyProgress;
    private javax.swing.JLabel doneOrNot;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel sellDoneOrNot;
    private javax.swing.JProgressBar sellProgress;
    private javax.swing.JLabel sellProgressText;
    private javax.swing.JTable table;
    private javax.swing.JTable table1;
    private javax.swing.JLabel variableStockChange;
    private javax.swing.JLabel variableStockClient;
    // End of variables declaration//GEN-END:variables
}
