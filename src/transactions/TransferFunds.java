package transactions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class TransferFunds {

    static Connection getConnection()throws SQLException{
        String url ="jdbc:postgresql://127.0.0.1/demo?useSSL=false";
        Properties properties = new Properties();
        properties.put("user","student");
        properties.put("password", "STUDENT");
        return DriverManager.getConnection(url,properties);
    }

    public static void main(String[] args) {
        Connection con = null;
        Statement stat =null;

        try{
            con = getConnection();
            /**
             * to start a transaction of multiple statements the connection.setAutoCommit is set to false, so developer has control over
             * commit and rollback. (default value of setAutoCommit(true))
             * to check if al statements are executed, commit() get called to check if any SQLExceptions where encountered
             * before they get persisted in the database, if not
             * a rollback will be preformed if an SQLException is encountered
             *
             * note: savepoints might be the better solution
             */
            con.setAutoCommit(false);
            stat =con.createStatement();

            // note DateField is between  '' quotes
            int transfer = stat.executeUpdate("INSERT INTO transaction VALUES (1,5555,'debit',55,'2016-03-14')");

            System.out.println(transfer);

            transfer = stat.executeUpdate("INSERT INTO transaction VALUES (2,7777,'credit',55,'2016-03-14')");

            System.out.println(transfer);

            transfer = stat.executeUpdate("UPDATE bank_account SET  balance = 944 WHERE account_nr=5555");

            System.out.println(transfer);

            transfer = stat.executeUpdate("UPDATE bank_account SET  balance = 155 WHERE account_nr=7777");

            System.out.println(transfer);

            //commit gets called to persist all if no SQLException occurred
            con.commit();


        } catch (SQLException e) {
            System.out.println(e);
            try {
                //NON of the statements get executed if SQLException occurs
                con.rollback();
                System.out.println("rollback executed");
            } catch (SQLException e1) {
                System.out.println(e1);
            }
        }
    }
}
