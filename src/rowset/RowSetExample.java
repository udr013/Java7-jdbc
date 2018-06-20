package rowset;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.SQLException;

public class RowSetExample {
    public static void main(String[] args) {
        JdbcRowSet jdbcRowSet = null;
        try{
            RowSetFactory rowSetFactory = RowSetProvider.newFactory();
            jdbcRowSet =rowSetFactory.createJdbcRowSet();

            jdbcRowSet.setUrl("jdbc:postgresql://localhost/demo?useSSL=false");
            jdbcRowSet.setUsername("student");
            jdbcRowSet.setPassword("STUDENT");
            jdbcRowSet.setCommand("SELECT * FROM records");
            jdbcRowSet.execute();

            while (jdbcRowSet.next()){
                System.out.print(jdbcRowSet.getInt("id")+ " - ");
                System.out.print(jdbcRowSet.getString("artist")+ " - ");
                System.out.print(jdbcRowSet.getString("album")+ " - ");
                System.out.print(jdbcRowSet.getInt("year_of_release")+ " - ");
                System.out.print(jdbcRowSet.getString("label")+ " - ");
                System.out.print(jdbcRowSet.getDouble("price")+ "\n");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        finally {
            try{
                jdbcRowSet.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }
}
