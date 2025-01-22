# Proyecto de Gestión de Libros - API Gutendex

Este proyecto utiliza la API de [Gutendex](https://gutendex.com/) para buscar libros y almacenarlos en una base de datos. Proporciona una aplicación con funcionalidades para gestionar libros y autores mediante un menú interactivo.

---

## **Características**
El programa ofrece las siguientes opciones:
1. **Buscar libro por título:** Busca un libro en la API de Gutendex y lo guarda en la base de datos.
2. **Listar libros registrados:** Muestra una lista de los libros almacenados en la base de datos.
3. **Listar autores registrados:** Muestra una lista de autores registrados en la base de datos.
4. **Listar autores vivos en un determinado año:** Filtra y muestra los autores que estaban vivos en un año especificado.
5. **Listar libros por idioma:** Muestra los libros registrados en un idioma específico.
6. **Salir:** Finaliza la ejecución del programa.

---

## **Requisitos**
- **Java:** Versión 11 o superior.
- **Maven:** Para gestionar las dependencias del proyecto.
- **Base de datos:** MySQL o cualquier base de datos compatible con JPA.
- **Dependencias principales:**
    - Spring Boot.
    - Spring Data JPA.
    - Hibernate.
    - Biblioteca HTTP (como `RestTemplate` o `HttpClient`).

---

## **Configuración**
### **1. Clonar el repositorio**
```bash
git clone https://github.com/tuusuario/nombre-del-repositorio.git
cd nombre-del-repositorio
