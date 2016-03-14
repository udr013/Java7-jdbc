package connect_crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class InsertIntoTable {

    static Connection getConnection() throws SQLException{
        String url ="jdbc:postgresql://localhost/demo?useSSL=false";
        Properties properties = new Properties();
        properties.put("user","student");
        properties.put("password", "STUDENT");
        return DriverManager.getConnection(url,properties);
    }

    public static void main(String[] args) {
        try (Connection con = getConnection();
             Statement statement = con.createStatement()){
            /**
            the VARCHAR/CHAR values need to be between ''
            * non of the following will execute twice as "id" already exist and
            * throw MySQLIntegrityConstraintViolationException: Duplicate entry '4' for key 'PRIMARY'
            */

            int ret = statement.executeUpdate("INSERT INTO records(id, album, artist, year_of_release, label, price) VALUES(1,'Electric LadyLand','Jimi Hendrix',1969,'Polydor', 25.50)");

            ret = statement.executeUpdate("INSERT INTO records(id, album, artist, year_of_release, label, price) VALUES(2,'Electric LadyLand','Jimi Hendrix',1969,'Polydor', 18.75)");


            ret = statement.executeUpdate("INSERT INTO records VALUES(3,'Pauls Boutique','Beastie Boys',1989,'Capitol', 17.40)");

            // returns false, if it is an update count or there are no results
             boolean test =statement.execute("INSERT INTO records VALUES(4,'Pauls Boutique','Beastie Boys',1989,'Capitol',8)");

            ret = statement.executeUpdate("DELETE FROM records WHERE id =2");

            System.out.println(test);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
