package view;

import dao.FuncionarioDAO;
import exceptions.DAOException;
import model.Funcionario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Formulario principal para la gestión de funcionarios
 * Implementa interfaz gráfica con Java Swing
 */
public class FrmFuncionario extends JFrame {

    // DAO
    private FuncionarioDAO funcionarioDAO;

    // Componentes del formulario
    private JTextField txtNumeroIdentificacion;
    private JTextField txtNombres;
    private JTextField txtApellidos;
    private JTextField txtDireccion;
    private JTextField txtTelefono;
    private JTextField txtFechaNacimiento;
    private JComboBox<String> cmbTipoIdentificacion;
    private JComboBox<String> cmbEstadoCivil;
    private ButtonGroup btnGroupSexo;
    private JRadioButton rdMasculino;
    private JRadioButton rdFemenino;

    // Botones
    private JButton btnNuevo;
    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;

    // Tabla
    private JTable tableFuncionarios;
    private DefaultTableModel modeloTabla;

    // Variable para almacenar el ID del funcionario seleccionado
    private Integer idFuncionarioSeleccionado = null;

    public FrmFuncionario() {
        funcionarioDAO = new FuncionarioDAO();
        initComponents();
        cargarTabla();
    }

    private void initComponents() {
        setTitle("Gestión de Funcionarios - Talento Humano");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        // Panel principal con BorderLayout
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel superior: Formulario
        JPanel panelFormulario = crearPanelFormulario();

        // Panel del medio: Botones
        JPanel panelBotones = crearPanelBotones();

        // Panel inferior: Tabla
        JPanel panelTabla = crearPanelTabla();

        // Agregar paneles al panel principal
        panelPrincipal.add(panelFormulario, BorderLayout.NORTH);
        panelPrincipal.add(panelBotones, BorderLayout.CENTER);
        panelPrincipal.add(panelTabla, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Funcionario"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tipo de Identificación
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Tipo Identificación:"), gbc);

        gbc.gridx = 1;
        cmbTipoIdentificacion = new JComboBox<>(new String[] { "CC", "CE", "TI", "Pasaporte" });
        panel.add(cmbTipoIdentificacion, gbc);

        // Número de Identificación
        gbc.gridx = 2;
        panel.add(new JLabel("Número Identificación:"), gbc);

        gbc.gridx = 3;
        txtNumeroIdentificacion = new JTextField(15);
        panel.add(txtNumeroIdentificacion, gbc);

        // Nombres
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Nombres:"), gbc);

        gbc.gridx = 1;
        txtNombres = new JTextField(15);
        panel.add(txtNombres, gbc);

        // Apellidos
        gbc.gridx = 2;
        panel.add(new JLabel("Apellidos:"), gbc);

        gbc.gridx = 3;
        txtApellidos = new JTextField(15);
        panel.add(txtApellidos, gbc);

        // Estado Civil
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Estado Civil:"), gbc);

        gbc.gridx = 1;
        cmbEstadoCivil = new JComboBox<>(new String[] { "Soltero", "Casado", "Divorciado", "Viudo", "Unión Libre" });
        panel.add(cmbEstadoCivil, gbc);

        // Sexo
        gbc.gridx = 2;
        panel.add(new JLabel("Sexo:"), gbc);

        gbc.gridx = 3;
        JPanel panelSexo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnGroupSexo = new ButtonGroup();
        rdMasculino = new JRadioButton("Masculino");
        rdFemenino = new JRadioButton("Femenino");
        rdMasculino.setActionCommand("M");
        rdFemenino.setActionCommand("F");
        btnGroupSexo.add(rdMasculino);
        btnGroupSexo.add(rdFemenino);
        panelSexo.add(rdMasculino);
        panelSexo.add(rdFemenino);
        panel.add(panelSexo, gbc);

        // Dirección
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Dirección:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        txtDireccion = new JTextField();
        panel.add(txtDireccion, gbc);

        // Teléfono
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Teléfono:"), gbc);

        gbc.gridx = 1;
        txtTelefono = new JTextField(15);
        panel.add(txtTelefono, gbc);

        // Fecha de Nacimiento
        gbc.gridx = 2;
        panel.add(new JLabel("Fecha Nacimiento (yyyy-MM-dd):"), gbc);

        gbc.gridx = 3;
        txtFechaNacimiento = new JTextField(15);
        panel.add(txtFechaNacimiento, gbc);

        return panel;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        btnNuevo = new JButton("Nuevo");
        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");

        // Configurar botón Nuevo (Azul)
        configurarBoton(btnNuevo, new Color(70, 130, 180), Color.BLACK);

        // Configurar botón Guardar (Verde)
        configurarBoton(btnGuardar, new Color(34, 139, 34), Color.BLACK);

        // Configurar botón Actualizar (Naranja)
        configurarBoton(btnActualizar, new Color(255, 140, 0), Color.BLACK);

        // Configurar botón Eliminar (Rojo)
        configurarBoton(btnEliminar, new Color(220, 20, 60), Color.BLACK);

        // Configurar botón Limpiar (Gris)
        configurarBoton(btnLimpiar, new Color(128, 128, 128), Color.BLACK);

        // Agregar listeners
        btnNuevo.addActionListener(e -> accionNuevo());
        btnGuardar.addActionListener(e -> accionGuardar());
        btnActualizar.addActionListener(e -> accionActualizar());
        btnEliminar.addActionListener(e -> accionEliminar());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        panel.add(btnNuevo);
        panel.add(btnGuardar);
        panel.add(btnActualizar);
        panel.add(btnEliminar);
        panel.add(btnLimpiar);

        // Estado inicial de botones
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);

        return panel;
    }

    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Lista de Funcionarios"));

        // Crear modelo de tabla
        String[] columnas = { "ID", "Tipo ID", "Número ID", "Nombres", "Apellidos",
                "Estado Civil", "Sexo", "Dirección", "Teléfono", "Fecha Nacimiento" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };

        tableFuncionarios = new JTable(modeloTabla);
        tableFuncionarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Agregar listener para selección de fila
        tableFuncionarios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarFuncionarioSeleccionado();
            }
        });

        JScrollPane scrollPane = new JScrollPane(tableFuncionarios);
        scrollPane.setPreferredSize(new Dimension(0, 250));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void cargarTabla() {
        try {
            // Limpiar tabla
            modeloTabla.setRowCount(0);

            // Obtener todos los funcionarios
            List<Funcionario> funcionarios = funcionarioDAO.listarTodos();

            // Agregar cada funcionario a la tabla
            for (Funcionario f : funcionarios) {
                Object[] fila = {
                        f.getIdFuncionario(),
                        f.getTipoIdentificacion(),
                        f.getNumeroIdentificacion(),
                        f.getNombres(),
                        f.getApellidos(),
                        f.getEstadoCivil(),
                        f.getSexo(),
                        f.getDireccion(),
                        f.getTelefono(),
                        f.getFechaNacimiento()
                };
                modeloTabla.addRow(fila);
            }

        } catch (DAOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar la tabla: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarFuncionarioSeleccionado() {
        int filaSeleccionada = tableFuncionarios.getSelectedRow();

        if (filaSeleccionada >= 0) {
            idFuncionarioSeleccionado = (Integer) modeloTabla.getValueAt(filaSeleccionada, 0);

            try {
                Funcionario f = funcionarioDAO.buscarPorId(idFuncionarioSeleccionado);

                if (f != null) {
                    cmbTipoIdentificacion.setSelectedItem(f.getTipoIdentificacion());
                    txtNumeroIdentificacion.setText(f.getNumeroIdentificacion());
                    txtNombres.setText(f.getNombres());
                    txtApellidos.setText(f.getApellidos());
                    cmbEstadoCivil.setSelectedItem(f.getEstadoCivil());

                    if ("M".equals(f.getSexo())) {
                        rdMasculino.setSelected(true);
                    } else {
                        rdFemenino.setSelected(true);
                    }

                    txtDireccion.setText(f.getDireccion());
                    txtTelefono.setText(f.getTelefono());
                    txtFechaNacimiento.setText(f.getFechaNacimiento().toString());

                    // Habilitar botones de actualizar y eliminar
                    btnActualizar.setEnabled(true);
                    btnEliminar.setEnabled(true);
                    btnGuardar.setEnabled(false);
                }

            } catch (DAOException e) {
                JOptionPane.showMessageDialog(this,
                        "Error al cargar funcionario: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void accionNuevo() {
        limpiarFormulario();
        txtNumeroIdentificacion.requestFocus();
    }

    private void accionGuardar() {
        try {
            // Validar campos
            if (!validarCampos()) {
                return;
            }

            // Crear nuevo funcionario
            Funcionario funcionario = new Funcionario();
            funcionario.setTipoIdentificacion((String) cmbTipoIdentificacion.getSelectedItem());
            funcionario.setNumeroIdentificacion(txtNumeroIdentificacion.getText().trim());
            funcionario.setNombres(txtNombres.getText().trim());
            funcionario.setApellidos(txtApellidos.getText().trim());
            funcionario.setEstadoCivil((String) cmbEstadoCivil.getSelectedItem());
            funcionario.setSexo(btnGroupSexo.getSelection().getActionCommand());
            funcionario.setDireccion(txtDireccion.getText().trim());
            funcionario.setTelefono(txtTelefono.getText().trim());
            funcionario.setFechaNacimiento(LocalDate.parse(txtFechaNacimiento.getText().trim()));

            // Guardar en la base de datos
            funcionarioDAO.insertar(funcionario);

            JOptionPane.showMessageDialog(this,
                    "Funcionario guardado exitosamente",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);

            limpiarFormulario();
            cargarTabla();

        } catch (DAOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar funcionario: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this,
                    "Formato de fecha inválido. Use yyyy-MM-dd (ejemplo: 2000-12-31)",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionActualizar() {
        if (idFuncionarioSeleccionado == null) {
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar un funcionario de la tabla para actualizar",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Validar campos
            if (!validarCampos()) {
                return;
            }

            // Crear funcionario con datos actualizados
            Funcionario funcionario = new Funcionario();
            funcionario.setIdFuncionario(idFuncionarioSeleccionado);
            funcionario.setTipoIdentificacion((String) cmbTipoIdentificacion.getSelectedItem());
            funcionario.setNumeroIdentificacion(txtNumeroIdentificacion.getText().trim());
            funcionario.setNombres(txtNombres.getText().trim());
            funcionario.setApellidos(txtApellidos.getText().trim());
            funcionario.setEstadoCivil((String) cmbEstadoCivil.getSelectedItem());
            funcionario.setSexo(btnGroupSexo.getSelection().getActionCommand());
            funcionario.setDireccion(txtDireccion.getText().trim());
            funcionario.setTelefono(txtTelefono.getText().trim());
            funcionario.setFechaNacimiento(LocalDate.parse(txtFechaNacimiento.getText().trim()));

            // Actualizar en la base de datos
            funcionarioDAO.actualizar(funcionario);

            JOptionPane.showMessageDialog(this,
                    "Funcionario actualizado exitosamente",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);

            limpiarFormulario();
            cargarTabla();

        } catch (DAOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error al actualizar funcionario: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this,
                    "Formato de fecha inválido. Use yyyy-MM-dd (ejemplo: 2000-12-31)",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionEliminar() {
        if (idFuncionarioSeleccionado == null) {
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar un funcionario de la tabla para eliminar",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar este funcionario?\nEsta acción también eliminará su grupo familiar y estudios.",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                funcionarioDAO.eliminar(idFuncionarioSeleccionado);

                JOptionPane.showMessageDialog(this,
                        "Funcionario eliminado exitosamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);

                limpiarFormulario();
                cargarTabla();

            } catch (DAOException e) {
                JOptionPane.showMessageDialog(this,
                        "Error al eliminar funcionario: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarFormulario() {
        idFuncionarioSeleccionado = null;
        cmbTipoIdentificacion.setSelectedIndex(0);
        txtNumeroIdentificacion.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        cmbEstadoCivil.setSelectedIndex(0);
        btnGroupSexo.clearSelection();
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtFechaNacimiento.setText("");

        tableFuncionarios.clearSelection();

        btnGuardar.setEnabled(true);
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }

    private boolean validarCampos() {
        if (txtNumeroIdentificacion.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "El número de identificación es obligatorio",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            txtNumeroIdentificacion.requestFocus();
            return false;
        }

        if (txtNombres.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Los nombres son obligatorios",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            txtNombres.requestFocus();
            return false;
        }

        if (txtApellidos.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Los apellidos son obligatorios",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            txtApellidos.requestFocus();
            return false;
        }

        if (btnGroupSexo.getSelection() == null) {
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar el sexo",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (txtFechaNacimiento.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "La fecha de nacimiento es obligatoria",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            txtFechaNacimiento.requestFocus();
            return false;
        }

        // Validar formato de fecha
        try {
            LocalDate.parse(txtFechaNacimiento.getText().trim());
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this,
                    "Formato de fecha inválido. Use yyyy-MM-dd (ejemplo: 2000-12-31)",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            txtFechaNacimiento.requestFocus();
            return false;
        }

        return true;
    }

    /**
     * Método auxiliar para configurar el estilo de los botones
     */
    private void configurarBoton(JButton boton, Color colorFondo, Color colorTexto) {
        // Configurar tamaño preferido
        boton.setPreferredSize(new Dimension(120, 35));
        boton.setMinimumSize(new Dimension(100, 30));

        // Configurar colores y apariencia
        boton.setBackground(colorFondo);
        boton.setForeground(colorTexto);
        boton.setOpaque(true);
        boton.setContentAreaFilled(true);
        boton.setBorderPainted(true);
        boton.setFocusPainted(false);

        // Configurar fuente
        boton.setFont(new Font("Segoe UI", Font.BOLD, 12));

        // Asegurar que el texto sea visible
        boton.setHorizontalTextPosition(SwingConstants.CENTER);
        boton.setVerticalTextPosition(SwingConstants.CENTER);

        // Configurar borde
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createRaisedBevelBorder(),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));

        // Efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (boton.isEnabled()) {
                    boton.setBackground(colorFondo.darker());
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (boton.isEnabled()) {
                    boton.setBackground(colorFondo);
                }
            }
        });
    }
}
