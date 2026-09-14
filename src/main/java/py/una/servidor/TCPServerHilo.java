package py.una.servidor;

import java.net.*;
import java.io.*;
import java.sql.Date;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import py.una.bd.ClienteDAO;
import py.una.bd.ProductoDAO;
import py.una.bd.VentaDAO;
import py.una.entidad.Cliente;
import py.una.entidad.ClienteJSON;
import py.una.entidad.Venta;

public class TCPServerHilo extends Thread {

    private Socket socket = null;

    VentasTCPMultiServer servidor;
    
    public TCPServerHilo(Socket socket, VentasTCPMultiServer servidor ) {
        super("TCPServerHilo");
        this.socket = socket;
        this.servidor = servidor;
    }

    public void run() {

        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                    socket.getInputStream()));
            out.println("Bienvenido!");
            String inputLine, outputLine;

            while ((inputLine = in.readLine()) != null) {
                System.out.println("Mensaje recibido: " + inputLine);
                
                //out.println(inputLine);
                
                //to-do: utilizar json
                if (inputLine.equals("Bye")) {
                    outputLine = "Usted apago el hilo";
                    break;
                    
                }else if (inputLine.equals("Terminar todo")) {
                    servidor.listening = false;
                    outputLine = "Usted apago todo";
                    break;
                }else if (inputLine.startsWith("POST /api/ventas {")) {
                    try {
                        String json = inputLine.substring("POST /api/ventas ".length());
                        JSONObject solicitud = (JSONObject) new JSONParser().parse(json);
                        JSONObject clienteJson = (JSONObject) solicitud.get("cliente");
                        String idProducto = (String) solicitud.get("id_producto");
                        Number cantidad = (Number) solicitud.get("cantidad");

                        ProductoDAO productoDAO = new ProductoDAO();
                        ClienteDAO clienteDAO = new ClienteDAO();
                        VentaDAO ventaDAO = new VentaDAO();

                        if (clienteJson == null || idProducto == null || cantidad == null
                                || cantidad.intValue() <= 0
                                || productoDAO.seleccionarPorIdProducto(idProducto).isEmpty()) {
                            outputLine = "Venta rechazada";
                        } else {
                            Cliente cliente = ClienteJSON.stringObjeto(
                                    clienteJson.toJSONString());

                            if (clienteDAO.seleccionarPorNumeroDocumento(
                                    cliente.getNumeroDocumento()).isEmpty()) {
                                clienteDAO.insertar(cliente);
                            }

                            Venta venta = new Venta(null, idProducto,
                                    cliente.getNumeroDocumento(),
                                    new Date(System.currentTimeMillis()),
                                    cantidad.intValue());
                            ventaDAO.insertar(venta);
                            outputLine = "Venta registrada";
                        }
                    } catch (Exception e) {
                        outputLine = "Venta rechazada";
                    }
                }else {
                	outputLine = "Peticion mal formulada" ;
                }
                
                
                out.println(outputLine);
            }
            out.close();
            in.close();
            socket.close();
            System.out.println("Finalizando Hilo");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
