package py.una.entidad;

import java.sql.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class VentaJSON {

    public static String objetoString(Venta venta) {
        return objetoJson(venta).toJSONString();
    }

    @SuppressWarnings("unchecked")
    public static String listaObjetoString(List<Venta> ventas) {
        JSONArray array = new JSONArray();
        for (Venta venta : ventas) {
            array.add(objetoJson(venta));
        }
        return array.toJSONString();
    }

    @SuppressWarnings("unchecked")
    private static JSONObject objetoJson(Venta venta) {
        JSONObject obj = new JSONObject();
        obj.put("idVenta", venta.getIdVenta());
        obj.put("idProducto", venta.getIdProducto());
        obj.put("numeroDocumento", venta.getNumeroDocumento());
        obj.put("fecha", venta.getFecha() == null ? null : venta.getFecha().toString());
        obj.put("cantidad", venta.getCantidad());
        return obj;
    }

    public static Venta stringObjeto(String str) throws Exception {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(str.trim());

        Venta venta = new Venta();
        Number idVenta = (Number) jsonObject.get("idVenta");
        Number cantidad = (Number) jsonObject.get("cantidad");
        venta.setIdVenta(idVenta == null ? null : idVenta.intValue());
        venta.setIdProducto((String) jsonObject.get("idProducto"));
        venta.setNumeroDocumento((String) jsonObject.get("numeroDocumento"));
        venta.setFecha(Date.valueOf((String) jsonObject.get("fecha")));
        venta.setCantidad(cantidad == null ? null : cantidad.intValue());
        return venta;
    }
}
