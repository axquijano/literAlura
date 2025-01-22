package com.aluracursos.literAlura.modelo;

import com.aluracursos.literAlura.dto.AutorDTO;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String nombre;
    private int fechaNacimiento;
    private int fechaFallecimiento;

    @ManyToMany(mappedBy = "autores",  cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>();

    public Autor () {}

    public Autor (AutorDTO autorDTO) {
        this.nombre = autorDTO.nombre();
        this.fechaNacimiento = autorDTO.fechaNacimiento();
        this.fechaFallecimiento = autorDTO.fechaFallecimiento();
    }

    @Override
    public String toString() {
        return
                "Autor: " + nombre + "\n" +
                "Fecha de nacimiento: " + fechaNacimiento + "\n" +
                "Fecha de fallecimiento: " + fechaFallecimiento + "\n" +
                "Libros: [" + this.listarLibros() + " ]\n";
    }

    public String listarLibros (){
        return  libros.stream().map(l -> l.getTitulo()).collect(Collectors.joining(" ,"));
    }
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(int fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setFechaFallecimiento(int fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    public void setOneLibro(Libro libro) {
        this.libros.add(libro);
    }
}
