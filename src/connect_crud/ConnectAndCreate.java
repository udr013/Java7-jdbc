package connect_crud;

import java.sql.*;
import java.util.Properties;

public class ConnectAndCreate {
    public static void main(String[] args) throws SQLException {


        /**
         *  We have three major components (all are interfaces extending autoClosable, so you could use them in a
         * try with resources, here we use a simple try/catch so we add a finally block to close them):
         * Connection to establish the connection
         * Statement to do  various actions on the DB, create, modify, delete, insert, retrieve.. DB objects or tables
         * Resultset to view the DB table
         *
         * initialize them, or the finally block will complain, "might not be initialized"
         */
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet;

        try {

//            connection = DriverManager.getConnection("jdbc:postgresql://localhost:3306/demo?useSSL=false","student", "STUDENT");

            /**
             * or use the local IP and you could use "properties", if default 3306 port is used it can be omitted
             * ?useSSL=false is used to pass the WARN for ssl when establishing the connection
             *user and password are KEYWORDS!
             */
            Properties properties = new Properties();
            properties.put("user", "student");
            properties.put("password", "STUDENT");

            connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1/demo?useSSL=false", properties);

            statement = connection.createStatement();

            /**
            * create a new table
            * throws com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Table 'records' already exists
            *
            * SQL syntax errors are thrown at runtime, as they are defined  as String
            *
            *  executeUpdate()  is used to insert, update and delete new rows, or execute DDL queries, creation, deletion
            *  and  deletion of database objects like tables
            */
//            int result = statement.executeUpdate("CREATE TABLE records(id INT PRIMARY KEY, album VARCHAR (1000), artist CHAR (255), year_of_release INT, label VARCHAR (250), price float8)");

//            System.out.println(result); // 0

            resultSet =statement.executeQuery("select * from records");

            while (resultSet.next()){
                System.out.println(resultSet.getString("artist"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        //connection and statment objects must  be closed, redundant because try with resources?
        finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        //print of  connection,  printing:  com.mysql.jdbc.JDBC4Connection@46f7f36a
        System.out.println(connection);
    }


}

