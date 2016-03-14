package callablestatements;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

/**
 * This one is executing the previously created stored Procedure count
 */
public class CallProcedureWithParameters {
    public static void main(String[] args) {


        try {
            Connection con = CreateDatabaseProcedure.getConnection();
            CallableStatement cs = con.prepareCall("{call proc_album_count(?,?)}");

            String artist = "Beastie Boys";
            //String artist = "Jimi Hendrix";

            //set IN parameter
            cs.setString(1, artist);
            //redundant, this seems to do nothing?
            cs.registerOutParameter(2, Types.NUMERIC);

            cs.execute();

            //get OUT of second parameter
            String out = cs.getString(2); //or cs.getInt(2)

            System.out.println("count: " + out);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
