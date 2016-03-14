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

            System.out.println("number of rows updated:"+ret);
            displayRecord(connection,"jimi Hendrix", "Electric Ladyland");

            // delete something from database
            ret = statement.executeUpdate("DELETE FROM records WHERE id =2");
            System.out.println("number of rows deleted:"+ret);
            displayRecord(connection,"jimi Hendrix", "Electric Ladyland");


            // delete whole table
            //ret = statement.executeUpdate("DROP TABLE records");



        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    //TODO method needs to be fixed SQL syntax
    private static void displayRecord(Connection connection, String artist, String album) throws SQLException {
        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        try {
            // Prepare statement
            myStmt = connection
                    .prepareStatement("select artist, album, from records where artist=? and album=?");

            myStmt.setString(1, album);
            myStmt.setString(2, artist);

            // Execute SQL query
            myRs = myStmt.executeQuery();

            // Process result set
            boolean found = false;

            while (myRs.next()) {
                String theArtist = myRs.getString("Artist");
                String theAlbum = myRs.getString("Album");


                System.out.printf("Found employee: %s %s\n", theAlbum, theArtist);
                found=true;
            }

            if (!found) {
                System.out.println("Employee NOT FOUND: " + artist + " " + album);
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            close(myStmt, myRs);
        }

    }	private static void close(Connection myConn, Statement myStmt,
                                   ResultSet myRs) throws SQLException {
        if (myRs != null) {
            myRs.close();
        }

        if (myStmt != null) {
            myStmt.close();
        }

        if (myConn != null) {
            myConn.close();
        }
    }

    private static void close(Statement myStmt, ResultSet myRs)
            throws SQLException {

        close(null, myStmt, myRs);
    }


}
