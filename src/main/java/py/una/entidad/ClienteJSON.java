package py.una.entidad;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ClienteJSON {

    public static String objetoString(Cliente cliente) {
        return objetoJson(cliente).toJSONString();
    }

    @SuppressWarnings("unchecked")
    public static String listaObjetoString(List<Cliente> clientes) {
        JSONArray array = new JSONArray();
        for (Cliente cliente : clientes) {
            array.add(objetoJson(cliente));
        }
        return array.toJSONString();
    }

    @SuppressWarnings("unchecked")
    private static JSONObject objetoJson(Cliente cliente) {
        JSONObject obj = new JSONObject();
        obj.put("tipoDocumento", cliente.getTipoDocumento());
        obj.put("numeroDocumento", cliente.getNumeroDocumento());
        obj.put("nombre", cliente.getNombre());
        obj.put("telefono", cliente.getTelefono());
        return obj;
    }

    public static Cliente stringObjeto(String str) throws Exception {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(str.trim());

        Cliente cliente = new Cliente();
        cliente.setTipoDocumento((String) jsonObject.get("tipoDocumento"));
        cliente.setNumeroDocumento((String) jsonObject.get("numeroDocumento"));
        cliente.setNombre((String) jsonObject.get("nombre"));
        cliente.setTelefono((String) jsonObject.get("telefono"));
        return cliente;
    }
}
