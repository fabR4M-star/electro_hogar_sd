package py.una.entidad;
import java.math.BigDecimal;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ProductoJSON {


    public static void main(String[] args) throws Exception {

    	System.out.println("Ejemplo de uso 1: pasar de objeto a string");
     	Producto p = new Producto();
    	p.setIdProducto("PROD-001");
    	p.setNombre("Smartphone Galaxy S24");
    	p.setMarca("Samsung");
    	p.setCategoria("Electronica");
    	p.setPrecio(new BigDecimal("6500000"));
    	p.setMoneda("PYG");
    	
    	String r1 = ProductoJSON.objetoString(p);
    	System.out.println(r1);
    	
    	
    	System.out.println("\n*************************************************************************");
    	System.out.println("\nEjemplo de uso 2: pasar de string a objeto");
     	String un_string = "{\"idProducto\":\"PROD-002\",\"nombre\":\"Notebook ThinkPad E14\",\"marca\":\"Lenovo\",\"categoria\":\"Computadoras\",\"precio\":5200000,\"moneda\":\"PYG\"}";
    	
    	Producto r2 = ProductoJSON.stringObjeto(un_string);
    	System.out.println(r2.getIdProducto() + " " + r2.getNombre() + " " + r2.getPrecio());
    }
    
    public static String objetoString(Producto p) {	
        return objetoJson(p).toJSONString();
    }

    public static String listaObjetoString(List<Producto> productos) {
        JSONArray array = new JSONArray();
        for (Producto producto : productos) {
            array.add(objetoJson(producto));
        }
        return array.toJSONString();
    }

    private static JSONObject objetoJson(Producto p) {
        JSONObject obj = new JSONObject();
        obj.put("idProducto", p.getIdProducto());
        obj.put("nombre", p.getNombre());
        obj.put("marca", p.getMarca());
        obj.put("categoria", p.getCategoria());
        obj.put("precio", p.getPrecio());
        obj.put("moneda", p.getMoneda());
        return obj;
    }
    
    
    public static Producto stringObjeto(String str) throws Exception {
    	Producto p = new Producto();
        JSONParser parser = new JSONParser();

        Object obj = parser.parse(str.trim());
        JSONObject jsonObject = (JSONObject) obj;

        p.setIdProducto((String) jsonObject.get("idProducto"));
        p.setNombre((String)jsonObject.get("nombre"));
        p.setMarca((String)jsonObject.get("marca"));
        p.setCategoria((String)jsonObject.get("categoria"));
        p.setPrecio(new BigDecimal(String.valueOf(jsonObject.get("precio"))));
        p.setMoneda((String)jsonObject.get("moneda"));
        return p;
	}

}
