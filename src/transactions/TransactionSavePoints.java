package transactions;

import java.sql.*;
import java.util.Properties;

public class TransactionSavePoints {

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement;
        Savepoint savepoint = null;

        try {
            connection = getConnection();

            /**
             *  set autocommit will throw a SQLException if set to true during any transaction when you call
             * any transaction related method like commit() or rollback()
             *
             * com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException: Can't call rollback when autocommit=true
             */
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            int result = statement.executeUpdate("UPDATE bank_account SET balance = 7 WHERE account_nr = 5555");

            System.out.println("update 5555: "+result);

            savepoint = connection.setSavepoint("1");

            result = statement.executeUpdate("UPDATE bank_account SET balance = 8 WHERE account_nr = 7777");

            System.out.println("update 7777: "+result);

            savepoint = connection.setSavepoint("2");

            result = statement.executeUpdate("INSERT  INTO transaction VALUES (113,9999,'debit',5779,'2020-10-01')");

            System.out.println("insert 9999: "+result);

            result = statement.executeUpdate("UPDATE bank_account SET balance = 'bla' WHERE account_nr = 9999");

            System.out.println("update 9999: "+result);

            savepoint = connection.setSavepoint("3");

            connection.commit();

        } catch (SQLException e) {
            try {
                if(savepoint != null){
                    System.out.println(savepoint.getSavepointName());
                }
                connection.rollback(savepoint);
                System.out.println("rollback performed");
                connection.commit();
            } catch (SQLException e1) {
                System.out.println(e1);
            }
        }
    }

    static Connection getConnection()throws SQLException{
        String url ="jdbc:postgresql://127.0.0.1/demo?useSSL=false";
        Properties properties = new Properties();
        properties.put("user","student");
        properties.put("password", "STUDENT");
        return DriverManager.getConnection(url,properties);
    }
}
