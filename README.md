# Laboratorio de sockets - Sistemas distribuidos
## Electro Hogar
### Requisitos
- Maven
- JDK8

Los servidores y la base de datos pertenecen al módulo `inforConf_sd`; iniciar allí el servidor correspondiente antes de usar uno de estos clientes.

### Compilar los clientes
Desde esta carpeta ejecutar:

```sh
mvn clean package
```

Se generan `target/clienteUDP.jar` y `target/clienteTCP.jar`.

### Cliente UDP
Con el servidor UDP de InforConf ejecutándose en el puerto `9876`, iniciar:

```sh
java -jar target/clienteUDP.jar
```

El cliente usa `127.0.0.1` por defecto. Para conectarse a otro host, indicar su dirección como argumento:

```sh
java -jar target/clienteUDP.jar 192.168.1.20
```

Peticiones de ejemplo (documentos disponibles en los datos de prueba de InforConf):

```text
VerificarIdentidad 80012345-6
ConsultarScore 80012345-6
```

Las respuestas JSON se muestran en tablas. En la implementación actual, `VerificarIdentidad` devuelve `nombre`, `tipo_persona` y `estado`; `ConsultarScore` busca por número de documento y devuelve `nombre` y `score`. Escribir `salir` para cerrar el cliente.

### Cliente TCP
Con el servidor TCP de InforConf ejecutándose en `localhost:4444`, iniciar:

```sh
java -jar target/clienteTCP.jar
```

Enviar una consulta de deudas:

```text
ConsultarDeudas 80012345-6
```

La respuesta incluye el nombre, si la persona está en mora, el monto total adeudado y una tabla de sus deudas (`entidad`, `monto` y `dias`). Se pueden probar también los documentos `4567890`, `90098765-4`, `3214567` y `5678901`. Escribir `Bye` para cerrar la conexión. `Terminar todo` activa la señal de cierre global; para detener inmediatamente el servidor TCP, usar `Ctrl+C` en su terminal.