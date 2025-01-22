package com.aluracursos.literAlura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LibroDTO (
    @JsonAlias("title")
    String titulo,
    @JsonAlias("authors")
    List<AutorDTO> autor,
    @JsonAlias("languages")
    List<String> idioma,
    @JsonAlias("download_count")
    Double numeroDescargas

){
}
