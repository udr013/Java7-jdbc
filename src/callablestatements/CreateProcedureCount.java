package callablestatements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateProcedureCount {
    public static void main(String[] args) {
        try{
            Connection  con = CreateDatabaseProcedure.getConnection();
            /**
             * Here we create a stored procedure with an OUT parameter
             * ("CREATE PROCEDURE <name for Procedure>(IN<what we put in> OUT <what we want back>)BEGIN<the procedure>;END;")
             * Notice the ; behind the statement and once more behind END;*/
            PreparedStatement preparedStatementCount = con.prepareCall("CREATE PROCEDURE proc_album_count(IN artist_Name CHAR(50), OUT count INT )" +
                    "BEGIN SELECT COUNT(*)INTO  count FROM records WHERE artist = artist_Name;END; ");

            int result = preparedStatementCount.executeUpdate();
            System.out.println(result);
        }catch (SQLException e){
            System.out.println(e);
        }
    }
}
