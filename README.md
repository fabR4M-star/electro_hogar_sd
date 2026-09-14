# Laboratorio de sockets - Sistemas distribuidos
## Electro Hogar
### Requisitos
- Maven
- JDK8
- Postgresql
### Pasos para correr el proyecto
- Crear un usuario postgres con contraseña postgres
- Crear una base de datos "bd_sockets"
- El puerto de postgres debe ser 5432 en el localhost
- Ejecutar:
    - "mvn clean package"; <- Para compilar el proyecto desde la carpeta donde está el pom.xml
- Para levantar cliente y servidor ejecutar:
    - "java -jar ./target/<nombre_del_archivo_deseado>"