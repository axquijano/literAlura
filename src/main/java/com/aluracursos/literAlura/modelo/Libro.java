package com.aluracursos.literAlura.modelo;

import com.aluracursos.literAlura.dto.LibroDTO;
import jakarta.persistence.*;

import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Entity
@Table(name="libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    private String idioma;
    private Double numeroDescarga;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "autor_libro",
            joinColumns = @JoinColumn(name = "libro_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id", referencedColumnName = "id")
    )
    private List<Autor> autores ;


    public Libro () {}
    public Libro(LibroDTO libroDTO){
        this.titulo = libroDTO.titulo();
        this.idioma = String.join(",", libroDTO.idioma());
        this.numeroDescarga = libroDTO.numeroDescargas();
    }

    @Override
    public String toString() {
        return  "----- LIBRO -----" +'\n' +
                "Titulo: " + titulo + '\n' +
                "Autor: [ " +this.listarAutores()+ "]\n" +
                "Idioma: [ " + idioma+" ]" +'\n' +
                "Numero de descargas: " + numeroDescarga +'\n' +
                "----------------"+'\n' ;
    }

    public String listarAutores(){
        return autores.stream().map(a -> a.getNombre()).collect(Collectors.joining(" ,"));
    }
    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        autores.forEach(a -> a.setOneLibro(this));
        this.autores = autores;
    }
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(List<String> idioma) {
        this.idioma = String.join(",", idioma);
    }

    public Double getNumeroDescarga() {
        return numeroDescarga;
    }

    public void setNumeroDescarga(Double numeroDescarga) {
        this.numeroDescarga = numeroDescarga;
    }
}
