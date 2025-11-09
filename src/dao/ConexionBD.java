package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionBD {
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    static {
        cargarConfiguracion();
    }

    private static void cargarConfiguracion() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("db.properties")) {
            props.load(fis);
            URL = props.getProperty("db.url");
            USER = props.getProperty("db.user");
            PASSWORD = props.getProperty("db.password");
        } catch (IOException e) {
            System.err.println("Error al cargar db.properties: " + e.getMessage());
            System.err.println("Asegúrate de que el archivo db.properties existe en el directorio raíz del proyecto.");
            // Valores por defecto en caso de error
            URL = "jdbc:mysql://localhost:3306/talento_humano";
            USER = "root";
            PASSWORD = "";
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            // En JDBC 4.0+ (Java 6+), el driver se carga automáticamente
            // No es necesario Class.forName(), pero lo intentamos por compatibilidad
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                // Si falla, intentamos con el driver antiguo
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException ex) {
                    throw new SQLException(
                            "Error: No se encontró el driver JDBC de MySQL.\n" +
                                    "Asegúrate de que 'mysql-connector-j-9.5.0.jar' esté en el classpath.\n" +
                                    "En IntelliJ: File → Project Structure → Libraries → Agregar JAR\n" +
                                    "Ruta esperada: lib/mysql-connector-j-9.5.0.jar",
                            ex);
                }
            }

            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new SQLException(
                    "Error de conexión a la base de datos:\n" +
                            "URL: " + URL + "\n" +
                            "Usuario: " + USER + "\n" +
                            "Verifica:\n" +
                            "1. Que el servidor MySQL esté ejecutándose\n" +
                            "2. Que la base de datos 'talento_humano' exista\n" +
                            "3. Que las credenciales en db.properties sean correctas\n" +
                            "Error original: " + e.getMessage(),
                    e);
        }
    }
}
