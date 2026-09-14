package py.una.entidad;

import java.sql.Date;

public class Venta {

    private Integer idVenta;
    private String idProducto;
    private String numeroDocumento;
    private Date fecha;
    private Integer cantidad;

    public Venta() {
    }

    public Venta(Integer idVenta, String idProducto, String numeroDocumento,
            Date fecha, Integer cantidad) {
        this.idVenta = idVenta;
        this.idProducto = idProducto;
        this.numeroDocumento = numeroDocumento;
        this.fecha = fecha;
        this.cantidad = cantidad;
    }

    public Integer getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Integer idVenta) {
        this.idVenta = idVenta;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
