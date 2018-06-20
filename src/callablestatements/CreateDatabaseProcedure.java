package callablestatements;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class CreateDatabaseProcedure {
    public static void main(String[] args) {
        try{
            Connection connection = getConnection();

            /**
             * create a callable stored procedure with name record_details, you only need to run this once, after that it's
             * permanently stored as a method belonging to the database*/

            PreparedStatement statement =connection.prepareStatement("CREATE procedure record_details() BEGIN SELECT A.id, A.artist, B.new_price FROM  records A, record_new_price B WHERE A.album=B.album; END;");

            int result = statement.executeUpdate();
            System.out.println(result);


        }catch (SQLException e) {
            System.out.println(e);
        }
    }
    static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost/demo?useSSL=false";
        Properties properties = new Properties();
        properties.put("user", "student");
        properties.put("password", "STUDENT");
        return DriverManager.getConnection(url, properties);
    }
}
