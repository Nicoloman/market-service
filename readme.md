# Lomanto Nicolas Franco.
### CoderHouse.

## Market Service App.

Este proyecto es un servicio para la gestión de productos, ventas y usuarios en un mercado.

## Configuración Inicial de la Base de Datos

Antes de iniciar el servicio, asegúrate de inicializar la base de datos. El script necesario para crear las tablas y realizar la configuración inicial se encuentra en la siguiente ubicación:

- [`db_initializer.sql`](src/main/java/com/nqlo/ch/mkt/service/scripts/db_initializer.sql)

## Postman Collection

Para probar el funcionamiento de la API se puede importar la collection de postman para obtener los endpoints con sus respectivos bodys o pathvariables. 

- [`market-service-api`](src/main/java/com/nqlo/ch/mkt/resorces/market-service-api.postman_collection.json)


# Proyecto Final - Modificaciones

## ExceptionHandler
- Se creó una clase GlobalExceptionHandler.java para manejar excepciones de manera global.
- Se añadieron excepciones personalizadas como ResourceNotFoundException.

## Swagger UI

Se implementó Swagger para documentar y probar la API de manera interactiva.
- [`swagger-ui`](http://localhost:8080/swagger-ui.html)

## Receipt
- Se agrego la entidad de 'Receipt', la cual exporta un comprobante al usuario. Esta obtiene la fecha de la api: ['WorldClockAPI']('http://worldclockapi.com/api/json/utc/now') . Si el servicio no responde, genera un objeto DATE y se lo setea en su lugar.

- El comprobante es un registro unico el cual no puede ser actualizado ni eliminado.

- Si una venta luego en Cancelada, se puede volver a generar un COMPROBANTE el cual reflecte esta modificacion.

- Si se invoca al servicio de exportar un comrobante el caul ya fue exportado, el mismo simplemente lo retorna.


