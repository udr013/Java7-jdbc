package transactions;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

public class TransactionSavePoints {

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = TransferFunds.getConnection();

            /**
             *  set autocommit will throw a SQLException if set to true during any transaction when you call
             * any transaction related method like commit() or rollback()
             *
             * com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException: Can't call rollback when autocommit=true
             */
            connection.setAutoCommit(false);
            statement = connection.createStatement();


            //statements including savepoint for account 5555
            int result = statement.executeUpdate("INSERT  INTO transaction VALUES (110,5555,'debit',2099,'2020-10-01')");

            System.out.println("insert 5555: "+result);

            result = statement.executeUpdate("UPDATE bank_account SET balance = balance +2099 WHERE account_nr = 5555");

            System.out.println("update 5555: "+result);

            Savepoint savepoint5555 = connection.setSavepoint();

            //statements including overloaded savepoint method seting name for savepoint for account 7777
            result = statement.executeUpdate("INSERT  INTO transaction VALUES (111,7777,'debit',12099,'2020-10-01')");

            System.out.println("insert 7777: "+result);

            result = statement.executeUpdate("UPDATE bank_account SET balance = balance +12099 WHERE account_nr = 7777");

            System.out.println("update 7777: "+result);

            Savepoint savepoint7777 = connection.setSavepoint("CrSalaryFor7777");

            //statements including overloaded savepoint method seting name for savepoint for account 9999
            result = statement.executeUpdate("INSERT  INTO transaction VALUES (112,9999,'debit',5099,'2020-10-01')");

            System.out.println("insert 9999: "+result);

            result = statement.executeUpdate("UPDATE bank_account SET balance = balance +5099 WHERE account_nr = 9999");

            System.out.println("update 9999: "+result);

            Savepoint savepoint9999 = connection.setSavepoint("CrSalaryFor9999");

            //because rollback is called to 7777 9999 will not be executed
            connection.rollback(savepoint7777);
            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
                //due account 9999 doesn't exist, or id already used/exist in transactions
                System.out.println("rollback performed");
            } catch (SQLException e1) {
                System.out.println(e1);
            }
        }
    }
}
