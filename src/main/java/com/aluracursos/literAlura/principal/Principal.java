package com.aluracursos.literAlura.principal;

import com.aluracursos.literAlura.dto.AutorDTO;
import com.aluracursos.literAlura.dto.DatosDTO;
import com.aluracursos.literAlura.dto.LibroDTO;
import com.aluracursos.literAlura.modelo.Autor;
import com.aluracursos.literAlura.modelo.Libro;
import com.aluracursos.literAlura.repository.IAutorRepository;
import com.aluracursos.literAlura.repository.ILibroRepository;
import com.aluracursos.literAlura.service.ConsumoAPI;
import com.aluracursos.literAlura.service.ConvierteDatos;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Principal {

    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private Scanner leer = new Scanner(System.in);
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private ILibroRepository repositoryLibro;
    private IAutorRepository repositoryAutor;
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();


    public Principal (ILibroRepository repositoryLibro, IAutorRepository repositoryAutor){

        this.repositoryLibro = repositoryLibro;
        this.repositoryAutor = repositoryAutor;
    }
    public void inicio () {
        menu();
    }

    public void menu (){
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    \n--------------------
                    Elija la opcion a través de su número:  
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = leerOpcion(0,5);
            switch (opcion) {
                case 1:
                    buscarLibroApi();
                    break;
                case 2:
                    listarLibros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    librosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void buscarLibroApi() {

        System.out.println("Ingrese el libro que desea buscar ");
        String nombreLibro = leer.nextLine();
        nombreLibro = URLEncoder.encode(nombreLibro, StandardCharsets.UTF_8);
        String json = consumoAPI.obtenerDatos(URL_BASE+nombreLibro);
        var datos = conversor.obtenerDatos(json, DatosDTO.class);

        if(!datos.resultados().isEmpty()){
            LibroDTO libroDTO  = datos.resultados().get(0);
            Optional<Libro> libroBuscado = repositoryLibro.findByTitulo(libroDTO.titulo());

            if(!libroBuscado.isPresent()){
                Libro libro = new Libro(libroDTO);
                List<Autor> autoresLibro = new ArrayList<>();


                for (int i=0; i< libroDTO.autor().size(); i++ ){
                    AutorDTO autorDTO = libroDTO.autor().get(i);
                    Optional<Autor> autorBuscado = repositoryAutor.findByNombre(autorDTO.nombre());


                    if (!autorBuscado.isPresent()){
                        Autor autor = new Autor(autorDTO);
                        autor.setOneLibro(libro);
                        autoresLibro.add(autor);
                        repositoryAutor.save(autor);
                    }else{
                        autoresLibro.add(autorBuscado.get());
                        System.out.println("Ya esta guardado el autor");
                    }
                }
                libro.setAutores(autoresLibro);
                repositoryLibro.save(libro);
                System.out.println(libro);
            }else{

                System.out.println("Libro ya guardado!\n");
            }
        }else{
            System.out.println(ANSI_RED+"Libro no encontrado\n"+ANSI_RESET);
        }
    }

    private void listarLibros() {
        List<Libro> libros = repositoryLibro.findAll();
        if (!libros.isEmpty()){
            libros.forEach(System.out::println);
        }else{
            System.out.println("No has registrado ningun libro");
        }
    }

    private void listarAutores() {
        List<Autor> autores = repositoryAutor.findAll();

        if (!autores.isEmpty()){
            System.out.println();
            autores.forEach(System.out::println);
        }else{
            System.out.println("No has registrado ningun autor");
        }
    }

    private void listarAutoresVivos(){
        System.out.println("Ingrese el año de vivo de los autor(es) que desea buscar");
        int anio = leerOpcion(0,2025);
        List<Autor> autores = repositoryAutor.autoresVivos(anio);

        if(!autores.isEmpty()){
            System.out.println();
            autores.forEach(System.out::println);
        }else{
            System.out.println("No hay autores registrados que hayan estados vivos en año "+anio);
        }

    }

    private void librosPorIdioma(){

        while (true){
            var menuIdioma = """
                Ingrese el idioma para buscar los libros:
                1. es - español
                2. en - ingles
                3. fr - frances
                4. pt - portugues
                5. Salir
                """;
            System.out.println(menuIdioma);
            int opc = leerOpcion(1,5);
            String idioma  = "";
            switch (opc) {
                case 1 -> idioma = "es";
                case 2 -> idioma = "en";
                case 3 -> idioma = "fr";
                case 4 -> idioma = "pt";
                case 5 -> System.out.println();
                default -> System.out.println(ANSI_RED + "Opción no válida." + ANSI_RESET);
            }

            if (opc == 5) {
                System.out.println("Saliendo del submenu...");
                break;
            }

            List<Libro> libros = repositoryLibro.findByIdioma(idioma);

            if(!libros.isEmpty()){
                libros.stream().forEach(System.out::println);
            }else {
                System.out.println(ANSI_RED+"No hay libros regitrado con ese idioma ["+idioma+"]"+ANSI_RESET);
            }
        }
    }

    private  int leerOpcion( int min, int max ) {
        int opcion = -1;
        while (opcion < min || opcion > max) {
            System.out.print("Ingrese una opción ("+min+"-"+max+"): ");
            String input = leer.nextLine();
            if (esNumeroValido(input, min, max)) {
                opcion = Integer.parseInt(input);
                System.out.println(ANSI_BLUE + "La opción ingresada es: " + opcion + ANSI_RESET);
            } else {
                System.out.println(ANSI_RED + "Opción inválida. Intente nuevamente." + ANSI_RESET);
            }
        }
        return opcion;
    }
    private  boolean esNumeroValido(String valor, double min, double max) {
        if (valor == null || valor.isBlank() || !valor.matches("\\d+(\\.\\d+)?")) {
            return false;
        }
        double numero = Double.parseDouble(valor);
        return numero >= min && numero <= max;
    }

}
