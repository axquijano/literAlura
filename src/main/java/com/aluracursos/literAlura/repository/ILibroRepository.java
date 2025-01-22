package com.aluracursos.literAlura.repository;

import com.aluracursos.literAlura.modelo.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ILibroRepository extends JpaRepository<Libro, Long> {


    Optional<Libro> findByTitulo (String titulo);

    List<Libro> findByIdioma (String idioma);
}
