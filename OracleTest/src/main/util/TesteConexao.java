package main.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.oracle.OracleConnection;

public class TesteConexao {

    public static void main(String[] args) {

        // String sql = "SELECT 'OK' AS OK FROM DUAL";
        String sql = "SELECT * FROM USER_INDEXES ORDER BY TABLE_NAME, INDEX_NAME";

        try (Statement stmt = OracleConnection.getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = stmt.executeQuery(sql);) {
            while (rs.next()) {
                System.out.println(rs.getString("TABLE_NAME") + "." + rs.getString("INDEX_NAME"));
                // System.out.println(rs.getString("OK"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
