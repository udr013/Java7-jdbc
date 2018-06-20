package connect_crud;

import java.sql.*;

public class UpdateTable {

//    static Connection getConnection() throws SQLException {
//        String url ="jdbc:mySQL://localhost/demo?useSSL=false";
//        Properties properties = new Properties();
//        properties.put("user","student");
//        properties.put("password", "STUDENT");
//        return DriverManager.getConnection(url,properties);
//    }

    public static void main(String[] args) {
        //try with resources, note "()" vs "{}", no close block needed as all implement autoClosable
        try {Connection connection = InsertIntoTable.getConnection();
            Statement statement = connection.createStatement();

            /**
             *take notice of the syntax in this update Statement, spaces are important and no "," in this one
             * also if no WHERE condition, it will SET for all rows  in the table!
             */

            int ret = statement.executeUpdate("UPDATE records SET year_of_release=1988 WHERE album = 'Pauls Boutique'");

            System.out.println("number of rows updated:" + ret);
            displayRecord(connection, "Jimi Hendrix", "Electric LadyLand", 10.0);

            // delete something from database
            ret = statement.executeUpdate("DELETE FROM records WHERE id =2");
            System.out.println("number of rows deleted:" + ret);
            displayRecord(connection, "Jimi Hendrix", "Electric LadyLand", 10.0);


            // delete whole table
            //ret = statement.executeUpdate("DROP TABLE records");



        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    //TODO method needs to be fixed SQL syntax
    private static void displayRecord(Connection connection, String artist, String album, double price) throws SQLException {
        PreparedStatement preparedStatement = null;

        try {
            // Prepare statement
//            preparedStatement = connection
//                    .prepareStatement("select artist, album from records where artist=? and album=? and price >= ?");

            //same as a callablestatement
            preparedStatement = connection                        // we use _ as selectRecords will be stored as selectrecords                artist as same type as records.artist
                    .prepareStatement("CREATE OR REPLACE FUNCTION select_records(p_artist varchar,p_album varchar, p_price float) RETURNS TABLE(artist records.artist % type, album  records.album% type) as $$ BEGIN return QUERY SELECT records.artist,records.album FROM records where records.artist=$1 and records.album=p_album and records.price >=p_price;END; $$ language plpgsql;");
//
            //generate statement in database
            preparedStatement.execute();

            CallableStatement callableStatement = connection.prepareCall("{call select_records(?,?,?)}");
            callableStatement.setString(1, artist);
            callableStatement.setString(2, album);
            callableStatement.setDouble(3, price);

//            callableStatement.registerOutParameter(4, Types.ARRAY);

            // Execute SQL query
            ResultSet resultSet = callableStatement.executeQuery();

            // Process result set
            boolean found = false;
            while (resultSet.next()) {
//                System.out.println(resultSet.getInt(1));
                String theArtist = resultSet.getString("Artist");
                String theAlbum = resultSet.getString("Album");
//
                System.out.printf("Found record: %s by %s\n", theAlbum, theArtist);
                found=true;
            }
            if (!found) {
                System.out.println("Employee NOT FOUND: " + artist + " " + album);
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            close(preparedStatement);
        }

    }

    private static void close(Connection myConn, Statement myStmt) throws SQLException {

        if (myStmt != null) {
            myStmt.close();
        }

        if (myConn != null) {
            myConn.close();
        }
    }

    private static void close(Statement myStmt)
            throws SQLException {

        close(null, myStmt);
    }


}
