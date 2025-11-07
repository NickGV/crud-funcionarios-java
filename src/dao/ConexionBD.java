package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/funcionariosdb";
    private static final String USER = "root";
    private static final String PASSWORD = "123456"; // cámbialo por el tuyo

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Error cargando el driver JDBC: " + e.getMessage());
        }
    }
}
