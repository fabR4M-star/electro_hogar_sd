package py.una.bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import py.una.entidad.Cliente;

public class ClienteDAO {

    public List<Cliente> seleccionar() {
        String sql = "SELECT tipo_documento, numero_documento, nombre, telefono "
                + "FROM clientes";
        List<Cliente> lista = new ArrayList<Cliente>();

        try (Connection conn = Bd.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearCliente(rs));
            }
        } catch (SQLException ex) {
            System.out.println("Error en la seleccion: " + ex.getMessage());
        }
        return lista;
    }

    public List<Cliente> seleccionarPorNumeroDocumento(String numeroDocumento) {
        String sql = "SELECT tipo_documento, numero_documento, nombre, telefono "
                + "FROM clientes WHERE numero_documento = ?";
        List<Cliente> lista = new ArrayList<Cliente>();

        try (Connection conn = Bd.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, numeroDocumento);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearCliente(rs));
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error en la seleccion: " + ex.getMessage());
        }
        return lista;
    }

    public long insertar(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO clientes "
                + "(tipo_documento, numero_documento, nombre, telefono) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conn = Bd.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            cargarParametros(pstmt, cliente);
            return pstmt.executeUpdate();
        }
    }

    public long actualizar(Cliente cliente) throws SQLException {
        String sql = "UPDATE clientes SET tipo_documento = ?, nombre = ?, "
                + "telefono = ? WHERE numero_documento = ?";

        try (Connection conn = Bd.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cliente.getTipoDocumento());
            pstmt.setString(2, cliente.getNombre());
            pstmt.setString(3, cliente.getTelefono());
            pstmt.setString(4, cliente.getNumeroDocumento());
            return pstmt.executeUpdate();
        }
    }

    public long borrar(String numeroDocumento) throws SQLException {
        String sql = "DELETE FROM clientes WHERE numero_documento = ?";

        try (Connection conn = Bd.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, numeroDocumento);
            return pstmt.executeUpdate();
        }
    }

    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setTipoDocumento(rs.getString("tipo_documento"));
        cliente.setNumeroDocumento(rs.getString("numero_documento"));
        cliente.setNombre(rs.getString("nombre"));
        cliente.setTelefono(rs.getString("telefono"));
        return cliente;
    }

    private void cargarParametros(PreparedStatement pstmt, Cliente cliente)
            throws SQLException {
        pstmt.setString(1, cliente.getTipoDocumento());
        pstmt.setString(2, cliente.getNumeroDocumento());
        pstmt.setString(3, cliente.getNombre());
        pstmt.setString(4, cliente.getTelefono());
    }
}
