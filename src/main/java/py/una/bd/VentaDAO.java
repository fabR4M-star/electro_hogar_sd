package py.una.bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import py.una.entidad.Venta;

public class VentaDAO {

    public List<Venta> seleccionar() {
        String sql = "SELECT id_venta, id_producto, numero_documento, fecha, cantidad "
                + "FROM ventas";
        List<Venta> lista = new ArrayList<Venta>();

        try (Connection conn = Bd.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearVenta(rs));
            }
        } catch (SQLException ex) {
            System.out.println("Error en la seleccion: " + ex.getMessage());
        }
        return lista;
    }

    public List<Venta> seleccionarPorIdVenta(Integer idVenta) {
        String sql = "SELECT id_venta, id_producto, numero_documento, fecha, cantidad "
                + "FROM ventas WHERE id_venta = ?";
        List<Venta> lista = new ArrayList<Venta>();

        try (Connection conn = Bd.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idVenta);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearVenta(rs));
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error en la seleccion: " + ex.getMessage());
        }
        return lista;
    }

    public List<Venta> seleccionarPorProducto(String idProducto) {
        return seleccionarPorCampo("id_producto", idProducto);
    }

    public List<Venta> seleccionarPorCliente(String numeroDocumento) {
        return seleccionarPorCampo("numero_documento", numeroDocumento);
    }

    private List<Venta> seleccionarPorCampo(String campo, String valor) {
        String sql = "SELECT id_venta, id_producto, numero_documento, fecha, cantidad "
                + "FROM ventas WHERE " + campo + " = ?";
        List<Venta> lista = new ArrayList<Venta>();

        try (Connection conn = Bd.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, valor);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearVenta(rs));
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error en la seleccion: " + ex.getMessage());
        }
        return lista;
    }

    public long insertar(Venta venta) throws SQLException {
        String sql = "INSERT INTO ventas "
                + "(id_producto, numero_documento, fecha, cantidad) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conn = Bd.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            cargarParametros(pstmt, venta);
            return pstmt.executeUpdate();
        }
    }

    public long actualizar(Venta venta) throws SQLException {
        String sql = "UPDATE ventas SET id_producto = ?, numero_documento = ?, "
                + "fecha = ?, cantidad = ? WHERE id_venta = ?";

        try (Connection conn = Bd.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            cargarParametros(pstmt, venta);
            pstmt.setInt(5, venta.getIdVenta());
            return pstmt.executeUpdate();
        }
    }

    public long borrar(Integer idVenta) throws SQLException {
        String sql = "DELETE FROM ventas WHERE id_venta = ?";

        try (Connection conn = Bd.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idVenta);
            return pstmt.executeUpdate();
        }
    }

    private Venta mapearVenta(ResultSet rs) throws SQLException {
        Venta venta = new Venta();
        venta.setIdVenta(rs.getInt("id_venta"));
        venta.setIdProducto(rs.getString("id_producto"));
        venta.setNumeroDocumento(rs.getString("numero_documento"));
        venta.setFecha(rs.getDate("fecha"));
        venta.setCantidad(rs.getInt("cantidad"));
        return venta;
    }

    private void cargarParametros(PreparedStatement pstmt, Venta venta)
            throws SQLException {
        pstmt.setString(1, venta.getIdProducto());
        pstmt.setString(2, venta.getNumeroDocumento());
        pstmt.setDate(3, venta.getFecha());
        pstmt.setInt(4, venta.getCantidad());
    }
}
