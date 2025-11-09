package dao;

import exceptions.DAOException;
import model.Funcionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para la entidad Funcionario
 * Implementa el patrón DAO separando la lógica de acceso a datos
 */
public class FuncionarioDAO {

    /**
     * Inserta un nuevo funcionario en la base de datos
     */
    public void insertar(Funcionario funcionario) throws DAOException {
        String sql = "INSERT INTO funcionario (tipo_identificacion, numero_identificacion, nombres, " +
                "apellidos, estado_civil, sexo, direccion, telefono, fecha_nacimiento) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, funcionario.getTipoIdentificacion());
            pstmt.setString(2, funcionario.getNumeroIdentificacion());
            pstmt.setString(3, funcionario.getNombres());
            pstmt.setString(4, funcionario.getApellidos());
            pstmt.setString(5, funcionario.getEstadoCivil());
            pstmt.setString(6, funcionario.getSexo());
            pstmt.setString(7, funcionario.getDireccion());
            pstmt.setString(8, funcionario.getTelefono());
            pstmt.setDate(9, Date.valueOf(funcionario.getFechaNacimiento()));

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas == 0) {
                throw new DAOException("No se pudo insertar el funcionario, no se afectaron filas.");
            }

            // Obtener el ID generado
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    funcionario.setIdFuncionario(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                throw new DAOException("El número de identificación ya existe en el sistema.", e);
            }
            throw new DAOException("Error al insertar funcionario: " + e.getMessage(), e);
        }
    }

    /**
     * Actualiza un funcionario existente en la base de datos
     */
    public void actualizar(Funcionario funcionario) throws DAOException {
        String sql = "UPDATE funcionario SET tipo_identificacion = ?, numero_identificacion = ?, " +
                "nombres = ?, apellidos = ?, estado_civil = ?, sexo = ?, direccion = ?, " +
                "telefono = ?, fecha_nacimiento = ? WHERE id_funcionario = ?";

        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, funcionario.getTipoIdentificacion());
            pstmt.setString(2, funcionario.getNumeroIdentificacion());
            pstmt.setString(3, funcionario.getNombres());
            pstmt.setString(4, funcionario.getApellidos());
            pstmt.setString(5, funcionario.getEstadoCivil());
            pstmt.setString(6, funcionario.getSexo());
            pstmt.setString(7, funcionario.getDireccion());
            pstmt.setString(8, funcionario.getTelefono());
            pstmt.setDate(9, Date.valueOf(funcionario.getFechaNacimiento()));
            pstmt.setInt(10, funcionario.getIdFuncionario());

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas == 0) {
                throw new DAOException("No se pudo actualizar el funcionario, el ID no existe.");
            }

        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                throw new DAOException("El número de identificación ya existe en el sistema.", e);
            }
            throw new DAOException("Error al actualizar funcionario: " + e.getMessage(), e);
        }
    }

    /**
     * Elimina un funcionario de la base de datos por su ID
     */
    public void eliminar(int idFuncionario) throws DAOException {
        String sql = "DELETE FROM funcionario WHERE id_funcionario = ?";

        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idFuncionario);

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas == 0) {
                throw new DAOException("No se pudo eliminar el funcionario, el ID no existe.");
            }

        } catch (SQLException e) {
            throw new DAOException("Error al eliminar funcionario: " + e.getMessage(), e);
        }
    }

    /**
     * Busca un funcionario por su ID
     */
    public Funcionario buscarPorId(int idFuncionario) throws DAOException {
        String sql = "SELECT * FROM funcionario WHERE id_funcionario = ?";

        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idFuncionario);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extraerFuncionarioDeResultSet(rs);
                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            throw new DAOException("Error al buscar funcionario por ID: " + e.getMessage(), e);
        }
    }

    /**
     * Busca un funcionario por su número de identificación
     */
    public Funcionario buscarPorNumeroIdentificacion(String numeroIdentificacion) throws DAOException {
        String sql = "SELECT * FROM funcionario WHERE numero_identificacion = ?";

        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, numeroIdentificacion);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extraerFuncionarioDeResultSet(rs);
                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            throw new DAOException("Error al buscar funcionario por número de identificación: " + e.getMessage(), e);
        }
    }

    /**
     * Lista todos los funcionarios de la base de datos
     */
    public List<Funcionario> listarTodos() throws DAOException {
        String sql = "SELECT * FROM funcionario ORDER BY apellidos, nombres";
        List<Funcionario> funcionarios = new ArrayList<>();

        try (Connection conn = ConexionBD.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                funcionarios.add(extraerFuncionarioDeResultSet(rs));
            }

            return funcionarios;

        } catch (SQLException e) {
            throw new DAOException("Error al listar funcionarios: " + e.getMessage(), e);
        }
    }

    /**
     * Método auxiliar para extraer un objeto Funcionario de un ResultSet
     */
    private Funcionario extraerFuncionarioDeResultSet(ResultSet rs) throws SQLException {
        Funcionario funcionario = new Funcionario();
        funcionario.setIdFuncionario(rs.getInt("id_funcionario"));
        funcionario.setTipoIdentificacion(rs.getString("tipo_identificacion"));
        funcionario.setNumeroIdentificacion(rs.getString("numero_identificacion"));
        funcionario.setNombres(rs.getString("nombres"));
        funcionario.setApellidos(rs.getString("apellidos"));
        funcionario.setEstadoCivil(rs.getString("estado_civil"));
        funcionario.setSexo(rs.getString("sexo"));
        funcionario.setDireccion(rs.getString("direccion"));
        funcionario.setTelefono(rs.getString("telefono"));

        Date fechaNacimiento = rs.getDate("fecha_nacimiento");
        if (fechaNacimiento != null) {
            funcionario.setFechaNacimiento(fechaNacimiento.toLocalDate());
        }

        return funcionario;
    }
}
