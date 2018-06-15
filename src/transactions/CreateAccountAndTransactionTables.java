package transactions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class CreateAccountAndTransactionTables {

    public static void main(String[] args) throws SQLException {

        Connection connection = null;
        Statement statement = null;


        try {

            Properties properties = new Properties();
            properties.put("user", "student");
            properties.put("password", "STUDENT");

            connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1/demo?useSSL=false", properties);

            statement = connection.createStatement();

            // delete whole table
            //int delete = statement.executeUpdate("DROP TABLE bank_account");
            //delete = statement.executeUpdate("DROP TABLE transaction");

            int createAccount = statement.executeUpdate("CREATE TABLE if not exists bank_account (account_nr INT, account_name VARCHAR (1000), " +
                    " balance float8)");

            int createTransaction = statement.executeUpdate("CREATE TABLE if not exists transaction (id INT PRIMARY KEY, " +
                    "account_nr INT, " +
                    "type VARCHAR (10), " +
                    " amount float8," +
                    "date DATE )");


            int firstAccount = statement.executeUpdate("INSERT INTO bank_account VALUES (5555,'Selvan',999)");
            int secondAccount = statement.executeUpdate("INSERT into bank_account VALUES (7777,'Paul',100)");
            int thirdAccount = statement.executeUpdate("INSERT INTO bank_account VALUES (9999,'Mark',0)");


        } catch (SQLException e) {
            System.out.println(e);
        }
        //connection and statment objects must  be closed
        finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

    }


}



