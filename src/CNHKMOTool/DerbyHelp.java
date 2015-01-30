package CNHKMOTool;

import java.lang.reflect.Field;
import java.sql.*;

public class DerbyHelp {
    public DerbyHelp() {
        //empty constructor -- helper class
    }

    public static boolean tableAlreadyExists(SQLException e) {
        boolean exists;
        if(e.getSQLState().equals("X0Y32")) {
            exists = true;
        } else {
            exists = false;
        }
        return exists;
    }
    
}