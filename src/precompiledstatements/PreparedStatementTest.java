package precompiledstatements;

import java.sql.*;
import java.util.Properties;

/**
 * The preparedStatement represent precompiled SQL statements. Advantage the execute faster than there noncompiled counterparts
 * Unlike statement you need to specify the relevant SQL statement when you create an object of PreparedStatement
 * More Advantage use placeholders, (see second example)
 */
public class PreparedStatementTest {

    static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost/demo?useSSL=false";
        Properties properties = new Properties();
        properties.put("user", "student");
        properties.put("password", "STUDENT");
        return DriverManager.getConnection(url, properties);
    }


    public static void main(String[] args) {
        try (Connection connection = getConnection();
             /**
              * preparedStatement throws
              * com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column 'exodus' in 'where clause'
              * when album doesn't exist
              * Note: the specified SQL statement in prepareStatement!
              * */
             Statement statement = connection.createStatement();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM records WHERE album = 'Pauls Boutique'");

             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                System.out.println(resultSet.getString("id") + " - " + resultSet.getString("artist") + " - "+
                resultSet.getString("album")+" - "+ resultSet.getString("year_of_release")+" - "+ resultSet.getString("label")+" - "+ resultSet.getDouble(6));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}

