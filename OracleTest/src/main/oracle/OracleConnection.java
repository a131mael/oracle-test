package main.oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import main.config.Config;

public abstract class OracleConnection {
	
	private static Connection con;
    private static final String ORACLE_URL = "jdbc:oracle:thin:@" + Config.ORACLE_IP + ":" + Config.ORACLE_PORT + ":" + Config.ORACLE_SID;

    public static Connection getConnection() throws SQLException {
        if (con == null) {
        	con = DriverManager.getConnection(ORACLE_URL, Config.ORACLE_USER, Config.ORACLE_PWD);
        }

        return con;
    }
}
