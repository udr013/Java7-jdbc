package callablestatements;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CallProcedure {
    public static void main(String[] args) {
        try {
            Connection connection = CreateDatabaseProcedure.getConnection();
            /**
             * here the previously created method for your database (Callable stored Procedure) gets called  */
            CallableStatement callableStatement = connection.prepareCall("{call record_details()}");

            ResultSet resultSet = callableStatement.executeQuery();

            while(resultSet.next()){
                /**here the stored procedure gets printed, notice it only can contain the columns you combined in the procedure*/
                System.out.println(resultSet.getString("artist")+" "+resultSet.getString("new_price")+" "+
                       resultSet.getString("id"));
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
