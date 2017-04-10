package stockit;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import yahoofinance.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

public class YahooAPI {

    public static void main(String args[]) throws IOException {

        try {
            // TODO add your handling code here:
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Here");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stockit", "root", "gr00t");
//here sonoo is database name, root is username and password  
            Statement stmt = con.createStatement();

            String[] symbols = new String[]{"INTC", "BABA", "TSLA", "AIR.PA", "YHOO"};
            Map<String, Stock> stocks = YahooFinance.get(symbols); // single request
            Calendar from = Calendar.getInstance();
            Calendar to = Calendar.getInstance();
            from.add(Calendar.YEAR, -1);
            List<HistoricalQuote> stocks_hist = null;
            char ch = '"';
            char ch2 = '\'';

            for (int i = 0; i < symbols.length; i++) {
                try {
                    stmt.execute("INSERT INTO stock(StockID, Publicly_Traded) VALUES (" + ch + stocks.get(symbols[i]).getSymbol() + ch + ", 1)");

                } catch (Exception e) {
                    System.out.println(e);
                    continue;
                }
            }
            for (int j = 0; j < symbols.length; j++) {

                Stock google = YahooFinance.get(symbols[j]);
                List<HistoricalQuote> googleHistQuotes = google.getHistory(from, to, Interval.MONTHLY);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");

                for (int k = 0; k < googleHistQuotes.size(); k++) {
                    Calendar date = googleHistQuotes.get(k).getDate();
                    String stock = stocks.get(symbols[j]).getSymbol();
                    long volume = googleHistQuotes.get(k).getVolume();
                    BigDecimal open = googleHistQuotes.get(k).getOpen();
                    BigDecimal close = googleHistQuotes.get(k).getClose();
                    BigDecimal low = googleHistQuotes.get(k).getLow();
                    BigDecimal high = googleHistQuotes.get(k).getHigh();
                    String currency = "USD";
                    try {
                        stmt.execute("INSERT INTO stock_daily_performance(Date, StockID, Volume, Opening_Price, Closing_Price, High, Low, Currency) VALUES (" + ch2 + sdf.format(date.getTime()) + ch2 + "," + ch + stock + ch + "," + volume + "," + open.doubleValue() + "," + close.doubleValue() + "," + high.doubleValue() + "," + low.doubleValue() + "," + ch + currency + ch + ")");
                    } catch (Exception e) {
                        System.out.println(e);
                        continue;
                    }
                }
            }
            
            for (int i = 0; i < symbols.length; i++) {
                try {
                    stmt.execute("INSERT INTO stock_sector(StockID) VALUES (" + ch + stocks.get(symbols[i]).getSymbol() + ch +")");

                } catch (Exception e) {
                    System.out.println(e);
                    continue;
                }
            }
            con.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
