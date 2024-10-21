# Aplicación CRUD de Gestión de Libros

Este proyecto es una aplicación Java que permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre una base de datos MySQL con una tabla de libros. La interfaz gráfica ha sido desarrollada utilizando **Swing**, y el acceso a la base de datos se gestiona mediante un **ResultSet actualizable**.

## Requisitos del Proyecto

- **Base de datos MySQL** configurada y en funcionamiento.
- **Java** y **Maven** instalados en tu sistema.
- **Eclipse IDE** o cualquier otro entorno compatible con proyectos Maven.
- **Conector MySQL** agregado en el archivo `pom.xml`.

## Configuración de la Base de Datos

Para comenzar, es necesario crear la base de datos y la tabla de libros en MySQL.

### Crear la Base de Datos y la Tabla

Ejecuta el siguiente script SQL para configurar la base de datos:

```sql
CREATE DATABASE libreria;

USE libreria;

CREATE TABLE libros (
    id INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(100),
    autor VARCHAR(100),
    anio_publicacion INT
);
