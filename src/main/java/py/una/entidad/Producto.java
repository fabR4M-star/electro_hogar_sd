package py.una.entidad;
import java.math.BigDecimal;

public class Producto{

	String idProducto;
	String nombre;
	String marca;
	String categoria;
	BigDecimal precio;
	String moneda;
	
	public Producto(){
	}

	public Producto(String pidProducto, String pnombre, String pmarca,
			String pcategoria, BigDecimal pprecio, String pmoneda){
		this.idProducto = pidProducto;
		this.nombre = pnombre;
		this.marca = pmarca;
		this.categoria = pcategoria;
		this.precio = pprecio;
		this.moneda = pmoneda;
	}
	
	public String getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(String idProducto) {
		this.idProducto = idProducto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
}
