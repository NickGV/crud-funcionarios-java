import view.FrmFuncionario;

import javax.swing.*;

/**
 * Clase principal de la aplicación
 * Sistema de Gestión de Talento Humano
 */
public class Main {
    public static void main(String[] args) {
        // Establecer el Look and Feel del sistema operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("No se pudo establecer el Look and Feel: " + e.getMessage());
        }

        // Crear y mostrar el formulario en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            try {
                FrmFuncionario frame = new FrmFuncionario();
                frame.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Error al iniciar la aplicación: " + e.getMessage() +
                                "\n\nVerifique que:\n" +
                                "1. El archivo db.properties existe en el directorio raíz\n" +
                                "2. La base de datos 'talento_humano' está creada\n" +
                                "3. El servidor MySQL está ejecutándose\n" +
                                "4. Las credenciales en db.properties son correctas",
                        "Error de Inicialización",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                System.exit(1);
            }
        });
    }
}
