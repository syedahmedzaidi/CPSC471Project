/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ahmed
 */
public class DBConnection {
    public Connection con;
    public void establishConnection() throws Exception{
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/stockit","root","gr00t");
        }catch(ClassNotFoundException | SQLException e){
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
    public void closeConnection() throws SQLException{
        con.close();
    }
}
