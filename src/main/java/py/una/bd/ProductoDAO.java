package py.una.bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import py.una.entidad.Producto;

public class ProductoDAO {

    public List<Producto> seleccionar() {
        String sql = "SELECT id_producto, nombre, marca, categoria, precio, moneda "
                + "FROM productos";
        List<Producto> lista = new ArrayList<Producto>();

        try (Connection conn = Bd.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearProducto(rs));
            }
        } catch (SQLException ex) {
            System.out.println("Error en la seleccion: " + ex.getMessage());
        }
        return lista;
    }

    public List<Producto> seleccionarPorIdProducto(String idProducto) {
        String sql = "SELECT id_producto, nombre, marca, categoria, precio, moneda "
                + "FROM productos WHERE id_producto = ?";
        List<Producto> lista = new ArrayList<Producto>();

        try (Connection conn = Bd.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, idProducto);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearProducto(rs));
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error en la seleccion: " + ex.getMessage());
        }
        return lista;
    }

    public long insertar(Producto producto) throws SQLException {
        String sql = "INSERT INTO productos "
                + "(id_producto, nombre, marca, categoria, precio, moneda) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Bd.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            cargarParametros(pstmt, producto);
            return pstmt.executeUpdate();
        }
    }

    public long actualizar(Producto producto) throws SQLException {
        String sql = "UPDATE productos SET nombre = ?, marca = ?, categoria = ?, "
                + "precio = ?, moneda = ? WHERE id_producto = ?";

        try (Connection conn = Bd.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, producto.getNombre());
            pstmt.setString(2, producto.getMarca());
            pstmt.setString(3, producto.getCategoria());
            pstmt.setBigDecimal(4, producto.getPrecio());
            pstmt.setString(5, producto.getMoneda());
            pstmt.setString(6, producto.getIdProducto());
            return pstmt.executeUpdate();
        }
    }

    public long borrar(String idProducto) throws SQLException {
        String sql = "DELETE FROM productos WHERE id_producto = ?";

        try (Connection conn = Bd.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, idProducto);
            return pstmt.executeUpdate();
        }
    }

    private Producto mapearProducto(ResultSet rs) throws SQLException {
        Producto producto = new Producto();
        producto.setIdProducto(rs.getString("id_producto"));
        producto.setNombre(rs.getString("nombre"));
        producto.setMarca(rs.getString("marca"));
        producto.setCategoria(rs.getString("categoria"));
        producto.setPrecio(rs.getBigDecimal("precio"));
        producto.setMoneda(rs.getString("moneda"));
        return producto;
    }

    private void cargarParametros(PreparedStatement pstmt, Producto producto)
            throws SQLException {
        pstmt.setString(1, producto.getIdProducto());
        pstmt.setString(2, producto.getNombre());
        pstmt.setString(3, producto.getMarca());
        pstmt.setString(4, producto.getCategoria());
        pstmt.setBigDecimal(5, producto.getPrecio());
        pstmt.setString(6, producto.getMoneda());
    }
}