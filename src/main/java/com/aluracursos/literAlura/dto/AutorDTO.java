package com.aluracursos.literAlura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AutorDTO(
    @JsonAlias("name")
    String nombre,
    @JsonAlias("birth_year")
    int fechaNacimiento,
    @JsonAlias("death_year")
    int fechaFallecimiento
) {
}
