package precompiledstatements;

import java.sql.*;

public class PreparedStatementWithPlaceholders {

    static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost/demo?useSSL=false";
        String user = "student";
        String password = "STUDENT";
        return DriverManager.getConnection(url, user, password);

    }

    public static void main(String[] args) {

        try {
            Connection connection = getConnection();
            //prepare statement for what entry(s) to update , set price for record which meet the where condition ("WHERE" ? and ?)
            PreparedStatement recordUpdateStat = connection.prepareStatement("UPDATE records SET price=? WHERE album=? AND price<= ?");
            //prepare statement on with what to update
            PreparedStatement preparedStatementNewPrice = connection.prepareStatement("SELECT new_price,album FROM record_new_price");

            ResultSet resultset = preparedStatementNewPrice.executeQuery();
            //System.out.println(resultset.getString(2));

            while (resultset.next()) {
                recordUpdateStat.setDouble(1, resultset.getDouble("new_price"));
                recordUpdateStat.setString(2, resultset.getString("album"));
                recordUpdateStat.setString(3, resultset.getString("price"));
                recordUpdateStat.executeUpdate();

            }
        } catch (SQLException e) {
            System.out.println(e);
        }

    }
}

