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

            result = statement.executeQuery("SELECT * FROM records  WHERE year_of_release =1988");
            ///prints com.mysql.jdbc.JDBC42ResultSet@5ce65a89
            System.out.println(result);

            while (result.next()) {
                System.out.println(result.getInt("id") + " - " + result.getString("artist") + " - " + result.getString("album") + " - " + result.getString("year_of_release")
                        + " - " + result.getString("label"));
            }

            result = statement.executeQuery("SELECT * FROM records ");

            while (result.next()) {
                System.out.println(result.getInt("id") + " - " + result.getString("artist") + " - " + result.getString("album") + " - " + result.getString("year_of_release")
                        + " - " + result.getString("label"));
            }
            /**
             * only can print the selected fields chosen in The SELECT statement
             */
            result = statement.executeQuery("SELECT id,album,artist,year_of_release FROM records WHERE artist ='Jimi Hendrix' ");

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

//            result = statement.executeQuery("SELECT id,artist,year_of_release FROM records WHERE artist ='Bob Marley' ");
//
//            //java.sql.SQLException: Illegal operation on empty result set.
//            System.out.println(result.getDouble(1) + " - " + result.getString(2) + " - " + result.getFloat(3));


            System.out.println(result); //com.mysql.jdbc.JDBC42ResultSet@25f38edc
            ResultSetMetaData resultMSD = result.getMetaData();
            System.out.println(resultMSD); //com.mysql.jdbc.ResultSetMetaData@1a86f2f1 - Field level information: blabla
            System.out.println("\nDiplay resultset MetaData");
            System.out.println("columnLabel: "+resultMSD.getColumnLabel(1)); //columnLabel: id
            System.out.println("CatalogName: "+resultMSD.getCatalogName(1)); //CatalogName: demo
            System.out.println("ColumnCount: "+resultMSD.getColumnCount()); //ColumnCount: 3
            System.out.println("ColumnName: "+resultMSD.getColumnName(1)); //ColumnName: id
            System.out.println("ColumnDisplaySize: "+resultMSD.getColumnDisplaySize(1)); //ColumnDisplaySize: 11
            System.out.println("ColumnType: "+resultMSD.getColumnType(1)); //ColumnType: 4
            System.out.println("ColumnClassName: "+resultMSD.getColumnClassName(1)); //ColumnClassName: java.lang.Integer
            System.out.println("TableName: "+resultMSD.getTableName(1)); //TableName: records
            System.out.println("ColumnTypeName: "+resultMSD.getColumnTypeName(1)); //ColumnTypeName: INT
            System.out.println("Precision: "+resultMSD.getPrecision(1)); //Precision: 11
            System.out.println("Scale: "+resultMSD.getScale(1)); //Scale: 0
            System.out.println("SchemaName: "+resultMSD.getSchemaName(1)); //SchemaName:

            DatabaseMetaData dbMeta = connection.getMetaData();
            System.out.println("\nDiplay Database MetaData");
            System.out.println("DatabaseProductName: "+dbMeta.getDatabaseProductName());
            System.out.println("DatabaseProductVersion: "+dbMeta.getDatabaseProductVersion());
            System.out.println("DriverName: "+dbMeta.getDriverName());
            System.out.println("getDriverVersion: "+dbMeta.getDriverVersion());
            System.out.println("Catalogs: "+dbMeta.getCatalogs());
            System.out.println("CatalogTerm: "+dbMeta.getCatalogTerm());
            System.out.println("CatalogSeparator: "+dbMeta.getCatalogSeparator());
            System.out.println("NumericFunctions: "+dbMeta.getNumericFunctions());
            System.out.println("ExtraNameCharacters: "+dbMeta.getExtraNameCharacters());
            System.out.println("IdentifierQuoteString: "+dbMeta.getIdentifierQuoteString());
            System.out.println("URL: "+dbMeta.getURL());
            System.out.println("getProcedureTerm: "+dbMeta.getProcedureTerm());
            System.out.println("getSchemaTerm: "+dbMeta.getSchemaTerm());
            System.out.println("getSearchStringEscape: "+dbMeta.getSearchStringEscape());
            System.out.println("getSQLKeywords: "+dbMeta.getSQLKeywords());
            System.out.println("getStringFunctions: "+dbMeta.getStringFunctions());
            System.out.println("getSystemFunctions: "+dbMeta.getSystemFunctions());
            System.out.println("getTimeDateFunctions: "+dbMeta.getTimeDateFunctions());
            System.out.println("getUserName: "+dbMeta.getUserName());



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
