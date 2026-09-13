package py.una.servidor;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

import py.una.bd.ProductoDAO;
import py.una.entidad.ProductoJSON;

public class VentasUDPServer {
	
	
    public static void main(String[] a){
        
        // Variables
        int puertoServidor = 9876;
        ProductoDAO pdao = new ProductoDAO();
        
        try {
            //1) Creamos el socket Servidor de Datagramas (UDP)
            DatagramSocket serverSocket = new DatagramSocket(puertoServidor);
			System.out.println("Servidor Electro Hogar - UDP ");
			
            //2) buffer de datos a enviar y recibir
            byte[] receiveData = new byte[1024];
            //3) Servidor siempre esperando
            while (true) {

                receiveData = new byte[1024];

                DatagramPacket receivePacket =
                        new DatagramPacket(receiveData, receiveData.length);


                System.out.println("Esperando a algun cliente... ");

                // 4) Receive LLAMADA BLOQUEANTE
                serverSocket.receive(receivePacket);
				
				System.out.println("________________________________________________");
                System.out.println("Aceptamos un paquete");

                // Datos recibidos e Identificamos quien nos envio
                String datoRecibido = new String(receivePacket.getData(), 0,
                    receivePacket.getLength(), StandardCharsets.UTF_8).trim();
                System.out.println("DatoRecibido: " + datoRecibido );

                InetAddress IPAddress = receivePacket.getAddress();

                int port = receivePacket.getPort();

                System.out.println("De : " + IPAddress + ":" + port);
                String respuesta;
                if ("GET /api/catalogo".equals(datoRecibido)) {
                    respuesta = ProductoJSON.listaObjetoString(pdao.seleccionar());
                } else {
                    respuesta = "Petición mal formulada";
                }

                // Enviamos la respuesta inmediatamente a ese mismo cliente
                // Es no bloqueante
                byte[] sendData = respuesta.getBytes(StandardCharsets.UTF_8);
                DatagramPacket sendPacket =
                        new DatagramPacket(sendData, sendData.length, IPAddress,port);

                serverSocket.send(sendPacket);

            }

        } catch (Exception ex) {
        	ex.printStackTrace();
            System.exit(1);
        }

    }
}  

