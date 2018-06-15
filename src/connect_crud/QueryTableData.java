package connect_crud;

import java.sql.*;

public class QueryTableData {

//        static Connection getConnection() throws SQLException {
//        String url ="jdbc:mySQL://localhost/demo?useSSL=false";
//        Properties properties = new Properties();
//        properties.put("user","student");
//        properties.put("password", "STUDENT");
//        return DriverManager.getConnection(url,properties);
//    }

    public static void main(String[] args) {
        //try with resources, note "()" vs "{}", no close block needed as all implement autoClosable
        try {
            Connection connection = InsertIntoTable.getConnection();
            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery("SELECT * FROM records ");

//            result = statement.executeQuery("SELECT * FROM records  WHERE year_of_release =1988");
            ///prints com.mysql.jdbc.JDBC42ResultSet@5ce65a89
            System.out.println(result);

            while (result.next()) {
                System.out.println(result.getInt("id") + " - " + result.getString("artist") + " - " + result.getString("album") + " - " + result.getString("year_of_release")
                        + " - " + result.getString("label"));
            }

            result = statement.executeQuery("SELECT * FROM records ");  // * == all columns

            while (result.next()) {
                System.out.println(result.getInt("id") + " - " + result.getString("artist") + " - " + result.getString("album") + " - " + result.getString("year_of_release")
                        + " - " + result.getString("label"));
            }
            /**
             * only can print the selected fields chosen in The SELECT statement
             */
            result = statement.executeQuery("SELECT id, album, artist, year_of_release FROM records WHERE artist ='Jimi Hendrix' ");

            while (result.next()) {
                System.out.println(result.getInt("id") + " - " + result.getString("artist") + " - " + result.getString("album") + " - " + result.getString("year_of_release")
                        //  +" - "+result.getString("label")
                );
            }

            result = statement.executeQuery("SELECT id,album,artist,year_of_release FROM records WHERE artist ='Jimi Hendrix' ");

            while (result.next()) {
                //ResultSet is 1 based
                System.out.println(result.getString(1) + " - " + result.getString(3) + " - " + result.getString(2) + " - " + result.getString(4)
                        //  +" - "+result.getString("label")
                );
            }

            result = statement.executeQuery("SELECT id,artist,year_of_release FROM records WHERE artist ='Jimi Hendrix' ");
            while (result.next()) {
                /**
                 * numberFields are also accessible using getInt(), getDouble(),getLong(),getFloat()
                 * also note column are based on SELECT, which has only three args now, against previous 4,
                 * the index 4 will give: java.sql.SQLException: Column Index out of range, 4 > 3.
                 *
                 *  System.out.println(result.getDouble(1)+" - "+result.getString(2)+" - "+result.getFloat(4));
                 *
                 * the correct  way for this query:
                 * */
                System.out.println(result.getDouble(1) + " - " + result.getString(2) + " - " + result.getFloat(3));
            }

            result = statement.executeQuery("SELECT id,artist,year_of_release FROM records WHERE artist ='Bob Marley' ");

//
//            //java.sql.SQLException: Illegal operation on empty result set.
//            System.out.println(result.getDouble(1) + " - " + result.getString(2) + " - " + result.getFloat(3));


            System.out.println(result); //com.mysql.jdbc.JDBC42ResultSet@25f38edc
            ResultSetMetaData resultSetMetaData = result.getMetaData();
            System.out.println(resultSetMetaData); //com.mysql.jdbc.ResultSetMetaData@1a86f2f1 - Field level information: blabla
            System.out.println("\nDiplay resultset MetaData");
            System.out.println("columnLabel: "+resultSetMetaData.getColumnLabel(1)); //columnLabel: id
            System.out.println("CatalogName: "+resultSetMetaData.getCatalogName(1)); //CatalogName: demo
            System.out.println("ColumnCount: "+resultSetMetaData.getColumnCount()); //ColumnCount: 3
            System.out.println("ColumnName: "+resultSetMetaData.getColumnName(1)); //ColumnName: id
            System.out.println("ColumnDisplaySize: "+resultSetMetaData.getColumnDisplaySize(1)); //ColumnDisplaySize: 11
            System.out.println("ColumnType: "+resultSetMetaData.getColumnType(1)); //ColumnType: 4
            System.out.println("ColumnClassName: "+resultSetMetaData.getColumnClassName(1)); //ColumnClassName: java.lang.Integer
            System.out.println("TableName: "+resultSetMetaData.getTableName(1)); //TableName: records
            System.out.println("ColumnTypeName: "+resultSetMetaData.getColumnTypeName(1)); //ColumnTypeName: INT
            System.out.println("Precision: "+resultSetMetaData.getPrecision(1)); //Precision: 11
            System.out.println("Scale: "+resultSetMetaData.getScale(1)); //Scale: 0
            System.out.println("SchemaName: "+resultSetMetaData.getSchemaName(1)); //SchemaName:

            DatabaseMetaData databaseMetaData = connection.getMetaData();
            System.out.println("\nDiplay Database MetaData");
            System.out.println("DatabaseProductName: "+databaseMetaData.getDatabaseProductName());
            System.out.println("DatabaseProductVersion: "+databaseMetaData.getDatabaseProductVersion());
            System.out.println("DriverName: "+databaseMetaData.getDriverName());
            System.out.println("getDriverVersion: "+databaseMetaData.getDriverVersion());
            System.out.println("Catalogs: "+databaseMetaData.getCatalogs());
            System.out.println("CatalogTerm: "+databaseMetaData.getCatalogTerm());
            System.out.println("CatalogSeparator: "+databaseMetaData.getCatalogSeparator());
            System.out.println("NumericFunctions: "+databaseMetaData.getNumericFunctions());
            System.out.println("ExtraNameCharacters: "+databaseMetaData.getExtraNameCharacters());
            System.out.println("IdentifierQuoteString: "+databaseMetaData.getIdentifierQuoteString());
            System.out.println("URL: "+databaseMetaData.getURL());
            System.out.println("getProcedureTerm: "+databaseMetaData.getProcedureTerm());
            System.out.println("getSchemaTerm: "+databaseMetaData.getSchemaTerm());
            System.out.println("getSearchStringEscape: "+databaseMetaData.getSearchStringEscape());
            System.out.println("getSQLKeywords: "+databaseMetaData.getSQLKeywords());
            System.out.println("getStringFunctions: "+databaseMetaData.getStringFunctions());
            System.out.println("getSystemFunctions: "+databaseMetaData.getSystemFunctions());
            System.out.println("getTimeDateFunctions: "+databaseMetaData.getTimeDateFunctions());
            System.out.println("getUserName: "+databaseMetaData.getUserName());



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
